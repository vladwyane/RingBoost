package admin.testData;

/**
 * Created by bigdrop on 2/26/2019.
 */
public enum CategoriesData {

    DEFAULT ("default", 0),
    CHRISTMAS ("christmas", 120),
    CATEGORY_UPDATE ("category-update", 140);

    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    CategoriesData(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
