package model;

public class Product {


    private String Barcode;
    private String Name;
    private String Image;
    private float Price;
    private int Quantity;
    public Product() {}

    public Product(String name, String image, float price, int quantity) {
        Name = name;
        Image = image;
        Price = price;
        Quantity = quantity;
    }
    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        this.Barcode = barcode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }


}
