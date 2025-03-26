package Database;

public class User {
    private String fname;
    private String lname;
    private String user;
    private String password;
    private String email;
    private String profile;

    public User(String firstname, String lastname, String username, String email, String password, String profile) {
        this.fname = firstname;
        this.lname = lastname;
        this.user = username;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public String getUsername() {
        return user;
    }

    public String getFirstname() {
        return fname;
    }

    public String getLastname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }
}
