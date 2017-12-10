package cat.udl.eps.etrapp.android.models;

public class EventMessage {

    private long id;
    private String message;
    private String key;
    private long userId;
    private long eventId;
    private long timestamp;

    public EventMessage() {

    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
