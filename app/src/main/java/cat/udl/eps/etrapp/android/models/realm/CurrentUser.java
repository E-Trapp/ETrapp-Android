package cat.udl.eps.etrapp.android.models.realm;

import cat.udl.eps.etrapp.android.models.User;

public class CurrentUser extends User {

    // Stub class just to differentiate User from CurrentUser in local DB.

    private CurrentUser(User user) {
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
}
