package cat.udl.eps.etrapp.android.models;

public class User {

    private long id;
    private String username;
    private String password;
    private String imageUrl;
    private String email;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String username, String password, String email, String firstName, String lastname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastname;
    }

    public User(long id, String username, String password, String email, String firstName, String lastname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastname;
    }

    @Override public String toString() {
        return "User {" +
                "id=" + id +
                ", username=" + username +
                ", password='" + password + '\'' +
                ", email=" + email +
                ", firstName=" + firstName +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
