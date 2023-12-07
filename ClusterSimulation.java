import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClusterSimulation {
  public static final int TOTAL_NODES = 13;
  public static Map<Decision, Integer> proper;

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(TOTAL_NODES);
    List<Node> nodes = new ArrayList<>();

    // Inicializa os nós
    for (int i = 0; i < TOTAL_NODES; i++) {
      Node node = new Node(i);
      nodes.add(node);
    }

    // Define os nós conectados
    for (Node node : nodes) {
      node.setConnectedNodes(nodes);
      executor.execute(node);
    }

    // Simula o envio de mensagens entre nós
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Inicializado: ");
      Message initialMessage = new Message(Decision.randomDecision(), 4, nodes.get(0));
      nodes.get(0).processMessage(initialMessage);
      
      
      Thread.sleep(5000);
      executor.shutdownNow();
    } catch (Exception e) {
      // TODO: handle exception
    }
    
    // Encerra o executor quando a simulação terminar
    executor.shutdown();

    System.out.println("DONE");
    System.out.println("Proper: " + proper);
    System.out.println("Corum: " + Node.getCorum());
  }
}