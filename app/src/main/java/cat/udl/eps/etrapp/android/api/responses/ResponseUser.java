package cat.udl.eps.etrapp.android.api.responses;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ResponseUser extends RealmObject {
    @PrimaryKey
    private long id;
    private String username;
    private String avatarUrl;
    private String email;
    private String firstName;
    private String lastName;
    private String token;

    public String getToken() {
        return token;
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
