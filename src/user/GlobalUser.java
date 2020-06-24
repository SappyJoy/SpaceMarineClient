package user;

public class GlobalUser {

    public static User user;

    private GlobalUser() {
    }

    public static void setUser(User user1) {
        user = user1;
    }

    public static User getUser() {
        return user;
    }
}
