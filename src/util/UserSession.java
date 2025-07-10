package util;

import model.User;

public class UserSession {
    private static UserSession instance;
    private User currentUser;

    private UserSession(User user) {
        this.currentUser = user;
    }

    public static void init(User user) {
        if (instance == null) {
            instance = new UserSession(user);
        }
    }

    public static User getUser() {
        return instance != null ? instance.currentUser : null;
    }

    public static void clear() {
        instance = null;
    }
}
