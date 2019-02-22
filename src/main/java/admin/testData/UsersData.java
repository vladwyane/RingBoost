package admin.testData;

/**
 * Created by bigdrop on 2/19/2019.
 */
public enum UsersData {

    ADMIN ("admin@admin.com", "admin", "adminadmin", "adminadmin"),
    VLAD ("vladyslav.chesalov@bigdropinc.com", "QA", "JR6GMs4ywG", "JR6GMs4ywG"),
    USER_ROLE ("user.role@bigdropinc.com", "user-role", "JR6GMs4ywG", "JR6GMs4ywG"),
    VLAD_UPDATE ("vladyslav.chesalov+1@bigdropinc.com", "QAnew", "JR6GMs4ywG", "JR6GMs4ywG"),
    INVALID_NAME ("vladyslav.chesalov@bigdropinc.com", "Q", "JR6GMs4ywG", "JR6GMs4ywG"),
    INVALID_EMAIL ("vladyslavChesalov", "QA", "JR6GMs4ywG", "JR6GMs4ywG"),
    INVALID_PASSWORD ("vladyslav.chesalov@bigdropinc.com", "QA", "JR6GM", "JR6GM"),
    INVALID_CONFIRM_PASSWORD ("vladyslav.chesalov@bigdropinc.com", "QA", "JR6GMs4ywG", "JR6GMs4yw");

    private String email;
    private String name;
    private String password;
    private String c_password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getC_password() {
        return c_password;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }

    UsersData(String email, String name, String password, String c_password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.c_password = c_password;
    }

}
