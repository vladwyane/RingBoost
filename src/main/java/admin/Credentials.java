package admin;

/**
 * Created by bigdrop on 12/18/2018.
 */

public enum Credentials {

    ADMIN ("admin@admin.com", "adminadmin");

    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
