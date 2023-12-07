import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
// import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Node implements Runnable {
  private final long nodeId;
  private final List<Node> connectedNodes;
  private final LinkedBlockingQueue<Message> messageQueue;
  private FowardFloodingSerialListTTL protocol;
  private long lastSerialNumber;

  private DecisionMap proper;
  private static Decision corum;

  public Node(int nodeId) {
    this.nodeId = nodeId;
    this.connectedNodes = new ArrayList<>();
    this.messageQueue = new LinkedBlockingQueue<>();
    this.protocol = new FowardFloodingSerialListTTL();

    this.proper = new DecisionMap();
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

    // Random rand = new Random(nodeId);
    // int total = rand.nextInt(this.connectedNodes.size() - 1);
    // for (int i = 0; i < total; i++) {
    //   int node = rand.nextInt(this.connectedNodes.size());
    //   this.connectedNodes.remove(node);
    // }
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
    protocol.doRountingProtocol(message, Decision.randomDecision(), this);
  }

  public void processMessage(Message message) {
    // Simula o processamento da mensagem recebida
    // pacote "morreu"
    if (message.getTimeToLive() <= 0)
      return;
    // já decidido
    // if(corum != null)
    //   return;

    this.proper.insert(message.getMessage());

    if(proper.verifyCorum())
      corum = this.proper.corum();
    
    sendMessage(message);
    corum = this.proper.corum();
  }

  public boolean offer(Message message) {
    return this.messageQueue.offer(message);
  }

  public static Decision getCorum() {
    return corum;
  }

  public Map<Decision, Integer> getProper() {
    return proper.getDecisionMap();
  }

  @Override
  public void run() {
    while (true) {
      try {
        Message message = messageQueue.take(); // Espera por uma mensagem
        // System.out.println(message);
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
