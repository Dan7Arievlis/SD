public class FowardFloodingSerialListTTL {
  public void doRountingProtocol(Message message, Node node) {
    if (message.getSerialNumber() > node.getLastSerialNumber()) {
      for (Node recipient : node.getConnectedNodes()) {
        if (recipient != message.getSender()) {
          Message newMessage = new Message(message);
          newMessage.decrementTTL();

          node.setLastSerialNumber(message.getSerialNumber());
          newMessage.setSender(node);
          recipient.offer(newMessage);

        }
      }
    }
  }

  public void doRountingProtocol(Message message, Decision newDecision, Node node) {
    // pacote "morreu"
    if (message.getTimeToLive() <= 0)
      return;

    if (message.getSerialNumber() > node.getLastSerialNumber()) {
      for (Node recipient : node.getConnectedNodes()) {
        if (recipient != message.getSender()) {
          Message newMessage = new Message(message, newDecision);
          newMessage.decrementTTL();

          node.setLastSerialNumber(message.getSerialNumber());
          newMessage.setSender(node);
          recipient.offer(newMessage);

        }
      }
    }
  }

  @Override
  public String toString() {
    return "Foward Flooding Serial List TTL Protocol";
  }
}