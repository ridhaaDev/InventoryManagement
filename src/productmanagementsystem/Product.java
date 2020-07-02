package productmanagementsystem;

public class Product {
    // Properties
    private int id;
    private String name;
    private float price;
    private String addDate;
    private byte[] picture;
    
    // Parameterised constructor
    public Product(int id, String name, float price, String addDate, byte[] picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.addDate = addDate;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    
    
    
}
