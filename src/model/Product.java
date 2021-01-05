package model;

import java.util.ArrayList;

public class Product extends AbstractSubject{


    private String barcode;
    private String name;
    private String image;
    private double price;
    private int stock;

    private static final ArrayList<Product> products = new ArrayList<>();

    public Product() {}

    public Product(String barcode,String name, String image, double price, int quantity) {
        this.barcode = barcode;
        this.name = name;
        this.image = image;
        this.price = price;
        stock = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public static void add(Product newProduct){
        products.add(newProduct);
    }

    public static void remove(Product oldProduct){
        products.remove(oldProduct);
    }

    public static ArrayList<Product> getAllProducts(){
        return products;
    }

}
