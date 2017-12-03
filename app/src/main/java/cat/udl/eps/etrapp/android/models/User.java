package cat.udl.eps.etrapp.android.models;

public class User {

    public long id;
    public String username;
    public String password;
    public String imageUrl;
    public String email;
    public String firstName;
    public String lastName;

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
