package cat.udl.eps.etrapp.android.models;

public class Event {

    private long id;
    private long owner;
    private String title;
    private String description;
    private String imageUrl;
    private long startsAt;
    private boolean isFeatured;

    public long getId() {
        return id;
    }

    public long getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getStartsAt() {
        return startsAt;
    }

    public boolean isFeatured() {
        return isFeatured;
    }
}
