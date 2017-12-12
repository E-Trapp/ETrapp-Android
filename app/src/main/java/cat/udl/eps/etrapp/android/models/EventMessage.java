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

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventMessage that = (EventMessage) o;

        if (getEventId() != that.getEventId()) return false;
        if (getTimestamp() != that.getTimestamp()) return false;
        if (getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() != null)
            return false;
        return getKey().equals(that.getKey());
    }

    @Override public int hashCode() {
        int result = getMessage() != null ? getMessage().hashCode() : 0;
        result = 31 * result + (int) (getEventId() ^ (getEventId() >>> 32));
        result = 31 * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
        return result;
    }
}
