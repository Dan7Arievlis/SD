import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

class Node implements Runnable {
    private final int nodeId;
    private final List<Node> connectedNodes;
    private final LinkedBlockingQueue<Message> messageQueue;

    public Node(int nodeId) {
        this.nodeId = nodeId;
        this.connectedNodes = new ArrayList<>();
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    public void setConnectedNodes(List<Node> connectedNodes) {
        this.connectedNodes.addAll(connectedNodes);
    }

    public void sendMessage(Message message) {
        // Simula o envio de uma mensagem para outros nós
        for (Node recipient : connectedNodes) {
            if (recipient != message.getSender()) {
                recipient.messageQueue.offer(message);
            }
        }
        System.out.println("Node " + nodeId + " enviando mensagem: " + message.getMessage());
    }

    public void processMessage(Message message) {
        // Simula o processamento da mensagem recebida
        System.out.println("Node " + nodeId + " processando mensagem: " + message.getMessage());
        if (message.getTimeToLive() > 0) {
            message.setTimeToLive(message.getTimeToLive() - 1);
            sendMessage(message);
        }
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
}

class Message {
    private final String message;
    private int timeToLive;
    private final Node sender;

    public Message(String message, int timeToLive, Node sender) {
        this.message = message;
        this.timeToLive = timeToLive;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Node getSender() {
        return sender;
    }
}

public class ClusterSimulation {
    public static void main(String[] args) {
        int numNodes = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numNodes);
        List<Node> nodes = new ArrayList<>();

        // Inicializa os nós
        for (int i = 0; i < numNodes; i++) {
            Node node = new Node(i);
            nodes.add(node);
        }

        // Define os nós conectados
        for (Node node : nodes) {
            node.setConnectedNodes(nodes);
            executor.execute(node);
        }

        // Simula o envio de mensagens entre nós
        Message initialMessage = new Message("Olá, Cluster!", 3, null);
        nodes.get(0).sendMessage(initialMessage);

        // Encerra o executor quando a simulação terminar
        executor.shutdown();
    }
}
