package admin.testData;

/**
 * Created by bigdrop on 2/27/2019.
 */
public enum PatternsData {

    BIG_DROP ("bigdrop", "admin", 1, 100),
    TEST_11_SYMBOLS ("bigdropUpdat", "admin11", 2, 200),
    UPDATE_PRICE ("bigdrop", "admin", 1, 300);

    private String pattern;
    private String category_type;
    private int category_id;
    private int price;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    PatternsData(String pattern, String category_type, int category_id, int price) {
        this.pattern = pattern;
        this.category_type = category_type;
        this.category_id = category_id;
        this.price = price;
    }
}
