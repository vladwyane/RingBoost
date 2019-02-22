package admin.testData;

/**
 * Created by bigdrop on 2/15/2019.
 */
public enum RolesData {

    ADMIN ("ringboost admin", "Ringboost Admin", "Admin"),
    TEST_4_SYMBOLS ("test", "Test", "Test"),
    TEST_UPDATE_NAME ("newQA", "UpdateQa", "NewQA"),
    ROLE_USER ("roleQA", "UserQa", "NewRoleUser"),
    ROLE_PERMISSION ("permissionQA", "roleQa", "NewRolePermission"),
    TEST_5_SYMBOLS ("qa+/-", "QA+/-", "Engin");

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

    RolesData(String name, String display_name, String description) {
        this.name = name;
        this.display_name = display_name;
        this.description = description;
    }
}
