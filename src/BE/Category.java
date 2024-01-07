package BE;

public class Category {
    private int categoryId;
    private String name;

    public Category(int id, String name) {
        this.categoryId = id;
        this.name = name;
    }

    public int getId() {
        return this.categoryId;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
