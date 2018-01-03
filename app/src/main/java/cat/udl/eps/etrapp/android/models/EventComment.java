package cat.udl.eps.etrapp.android.models;

public class EventComment {

    private long id;
    private String comment;
    private String key;
    private long userId;
    private long eventId;
    private long timestamp;

    public EventComment() {

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

        EventComment that = (EventComment) o;

        if (getId() != that.getId()) return false;
        if (getUserId() != that.getUserId()) return false;
        if (getEventId() != that.getEventId()) return false;
        if (!getComment().equals(that.getComment())) return false;
        return getKey() != null ? getKey().equals(that.getKey()) : that.getKey() == null;
    }

    @Override public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getComment().hashCode();
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (int) (getEventId() ^ (getEventId() >>> 32));
        return result;
    }
}
