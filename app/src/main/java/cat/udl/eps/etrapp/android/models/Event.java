package cat.udl.eps.etrapp.android.models;

import java.util.Random;

public class Event {

    private long id;
    private long owner;
    private String title;
    private long created_at;
    private long updated_at;
    private String description;
    private String imageUrl;
    private long datetime;
    private boolean isFeatured;

    public Event(long id, String title, String description, long datetime, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.created_at = System.currentTimeMillis();
        this.updated_at = created_at;
        this.imageUrl = imageUrl;
        owner = Math.abs(new Random().nextInt() % 3);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getOwner() {
        return owner;
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

    public boolean isFeatured() {
        return isFeatured;
    }

    @Override public String toString() {
        return "Event{" +
                "id=" + id +
                ", owner=" + owner +
                ", title='" + title + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", datetime=" + datetime +
                ", isFeatured=" + isFeatured +
                '}';
    }
}
