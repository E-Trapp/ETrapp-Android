package cat.udl.eps.etrapp.android.models;

public class Event {

    private long id;
    private long owner;
    private String title;
    private long category;
    private String description;
    private String imageUrl;
    private long startsAt;
    private boolean isFeatured;
    private String location;

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStartsAt(long startsAt) {
        this.startsAt = startsAt;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

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

    public long getCategory() { return category; }

    public void setCategory(long category) { this.category = category; }
}
