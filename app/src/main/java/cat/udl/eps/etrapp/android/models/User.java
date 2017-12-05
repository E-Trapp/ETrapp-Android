package cat.udl.eps.etrapp.android.models;

import cat.udl.eps.etrapp.android.models.realm.CurrentUser;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private long id;
    private String username;
    private String avatarUrl;
    private String email;
    private String firstName;
    private String lastName;

    public User() {}

    private User(CurrentUser user) {
        this.avatarUrl = user.getAvatarUrl();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public static User current(CurrentUser user){
        return new User(user);
    }


    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
