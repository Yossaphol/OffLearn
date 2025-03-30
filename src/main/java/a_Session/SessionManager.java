package a_Session;

public class SessionManager {
    private static SessionManager instance;
    private String username;
    private String userID;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUserID(String userID) { this.userID = userID; }

    public String getUserID() { return userID; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }


    public void clearSession() {
        username = null;
        userID = null;
    }

}
