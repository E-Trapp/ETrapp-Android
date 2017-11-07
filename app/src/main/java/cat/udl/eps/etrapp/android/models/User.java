package cat.udl.eps.etrapp.android.models;

public class User {

    private long id;
    private String username;
    private String imageUrl;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
