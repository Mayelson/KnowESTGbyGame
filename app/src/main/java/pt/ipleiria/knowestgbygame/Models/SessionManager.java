package pt.ipleiria.knowestgbygame.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SessionManager {
    private static final SessionManager instance = new SessionManager();

    public static SessionManager manager() {
        return instance;
    }

    private FirebaseUser authUser;
    private User user;

    private SessionManager() {
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User(authUser.getDisplayName(), authUser.getEmail(), authUser.getUid());
        if (authUser.getPhotoUrl() != null){
            user.setAvatar(authUser.getPhotoUrl().toString());
        }
    }


    public FirebaseUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(FirebaseUser authUser) {
        this.authUser = authUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
