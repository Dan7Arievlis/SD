import java.util.concurrent.atomic.AtomicLong;

public class Message {
    private final String message;
    private int timeToLive;
    private Node sender;
    private static AtomicLong lastSerialNumber = new AtomicLong(0);
    private long serialNumber;

    public Message(String message, int timeToLive, Node sender) {
        long value = lastSerialNumber.incrementAndGet();
        this.serialNumber = value;

        this.message = message;
        this.timeToLive = timeToLive;
        this.sender = sender;
    }

    public Message(Message message) {
        this.sender = message.sender;
        this.message = message.message;
        this.timeToLive = message.timeToLive;
        this.serialNumber = message.serialNumber;
    }

    public String getMessage() {
        return message;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void decrementTTL() {
        this.timeToLive -= 1;
    }

    public Node getSender() {
        return sender;
    }

    public void setSender(Node node) {
        this.sender = node;
    }

    public long getSerialNumber() {
      return serialNumber;
    }
}
