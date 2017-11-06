package cat.udl.eps.etrapp.android.models;

public class StreamMessage {

    private long timestamp;
    private String message;

    public StreamMessage(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
