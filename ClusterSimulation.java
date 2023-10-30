import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClusterSimulation {
  public static void main(String[] args) {
    int numNodes = 10;
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
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Inicializado: ");
      String messageString = scanner.nextLine();
      while (messageString.compareTo("") != 0) {
        Message initialMessage = new Message(messageString, 5, nodes.get(0));
        nodes.get(0).sendMessage(initialMessage);

        System.out.println("DONE");
        messageString = scanner.nextLine();
      }

      executor.shutdownNow();
    } catch (Exception e) {
      // TODO: handle exception
    }

    // Encerra o executor quando a simulação terminar
    executor.shutdown();
  }
}