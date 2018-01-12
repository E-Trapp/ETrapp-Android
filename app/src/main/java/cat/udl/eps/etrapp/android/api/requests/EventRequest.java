package cat.udl.eps.etrapp.android.api.requests;

import cat.udl.eps.etrapp.android.models.Event;

public class EventRequest {

    public static EventRequest fromEvent(Event event) {
        EventRequest eventRequest = new EventRequest();

        eventRequest.owner = event.getOwner();
        eventRequest.title = event.getTitle();
        eventRequest.category = event.getCategory();
        eventRequest.description = event.getDescription();
        eventRequest.imageUrl = event.getImageUrl();
        eventRequest.startsAt = event.getStartsAt();
        eventRequest.location = event.getLocation();

        return eventRequest;
    }


    private long owner;
    private String title;
    private String description;
    private long category;
    private String imageUrl;
    private long startsAt;
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

    public long getCategory() { return category; }

    public void setCategory(long category) { this.category = category; }

}
