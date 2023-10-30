import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Node implements Runnable {
  private final long nodeId;
  private final List<Node> connectedNodes;
  private final LinkedBlockingQueue<Message> messageQueue;
  private FowardFloodingSerialListTTL protocol;
  private long lastSerialNumber;

  public Node(int nodeId) {
    this.nodeId = nodeId;
    this.connectedNodes = new ArrayList<>();
    this.messageQueue = new LinkedBlockingQueue<>();
    this.protocol = new FowardFloodingSerialListTTL();
  }
  
  public long getNodeId() {
    return this.nodeId;
  }

  public long getLastSerialNumber() {
    return lastSerialNumber;
  }

  public void setLastSerialNumber(long lastSerialNumber) {
    this.lastSerialNumber = lastSerialNumber;
  }

  public List<Node> getConnectedNodes() {
    return Collections.unmodifiableList(connectedNodes);
  }

  public void setConnectedNodes(List<Node> nodes) {
    this.connectedNodes.addAll(nodes);
    this.removeNode(this);
    
    Random rand = new Random(nodeId);
    int total = rand.nextInt(this.connectedNodes.size() - 1);
    for (int i = 0; i < total; i++) {
      int node = rand.nextInt(this.connectedNodes.size());
      this.connectedNodes.remove(node);
    }
  }

  public void addNode(Node node) {
    if(!connectedNodes.contains(node))
      connectedNodes.add(node);
  }

  public void removeNode(Node node) {
    if(connectedNodes.contains(node))
      connectedNodes.remove(node);
  }

  public void sendMessage(Message message) {
    // Simula o envio de uma mensagem para outros nós
    protocol.doRountingProtocol(message, this);
  }

  public void processMessage(Message message) {
    // Simula o processamento da mensagem recebida
    // PROCESSAMENTO:
    // System.out.println("Node " + nodeId + " processando mensagem de " + message.getSender() + ": " + message.getMessage());
    sendMessage(message);
  }

  public boolean offer(Message message) {
    return this.messageQueue.offer(message);
  }

  @Override
  public void run() {
    while (true) {
      try {
        Message message = messageQueue.take(); // Espera por uma mensagem
        processMessage(message);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      }
    }
  }

  @Override
  public String toString() {
    return "" + this.nodeId;
  }
}
