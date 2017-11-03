package cat.udl.eps.etrapp.android.models;

public class Event {

    private long id;
    private String title;
    private long created_at;
    private long updated_at;
    private String description;
    private long datetime;

    public Event(long id, String title, String description, long datetime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.created_at = System.currentTimeMillis();
        this.updated_at = created_at;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getCreated_at() {
        return created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public String getDescription() {
        return description;
    }

    public long getDatetime() {
        return datetime;
    }
}
