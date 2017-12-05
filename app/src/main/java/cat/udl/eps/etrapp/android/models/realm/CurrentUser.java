package cat.udl.eps.etrapp.android.models.realm;


import cat.udl.eps.etrapp.android.api.responses.ResponseUser;
import cat.udl.eps.etrapp.android.models.User;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentUser extends RealmObject {

    @PrimaryKey
    private long id;
    private String username;
    private String avatarUrl;
    private String email;
    private String firstName;
    private String lastName;

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

    // Stub class just to differentiate User from CurrentUser in local DB.

    public CurrentUser() {}

    private CurrentUser(User user) {
        this.avatarUrl = user.getAvatarUrl();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.username = user.getUsername();
    }

    private CurrentUser(ResponseUser user) {
        this.avatarUrl = user.getAvatarUrl();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public static CurrentUser fromUser(User user) {
        return new CurrentUser(user);
    }
    public static CurrentUser fromResponse(ResponseUser user) {
        return new CurrentUser(user);
    }


}
