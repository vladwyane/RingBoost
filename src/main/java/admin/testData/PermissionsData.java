package admin.testData;

/**
 * Created by bigdrop on 2/18/2019.
 */
public enum PermissionsData {

    CREATE_POSTS ("create-post", "Create Posts", "create new blog posts"),
    CREATE_USERS ("create-users", "Create Users", "create new users"),
    TEST_UPDATE_NAME ("all-can-new", "AllCanNew", "all permissions new"),
    PERMISSION_ROLE ("permission-can-new", "RoleCanNew", "Permissions Role"),
    ALL_CAN ("all-can", "All Can", "all permissions");

    private String name;
    private String display_name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    PermissionsData(String name, String display_name, String description) {
        this.name = name;
        this.display_name = display_name;
        this.description = description;
    }

}
