package model;

import java.util.ArrayList;

public class ScannedProduct {
    private String Barcode;
    private String Name;
    private String Image;
    private float Price;
    private int QuantityScanned;

    public ScannedProduct(){}

    public ScannedProduct(String barcode, String name, String image, float price, int quantityScanned) {
        Barcode = barcode;
        Name = name;
        Image = image;
        Price = price;
        QuantityScanned = quantityScanned;
    }

//    private static final ArrayList<ScannedProduct> allScanned = new ArrayList<>();

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
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

    public int getQuantityScanned() {
        return QuantityScanned;
    }

    public void setQuantityScanned(int quantityScanned) {
        QuantityScanned = quantityScanned;
    }

//    public ArrayList<ScannedProduct> getAll(){
//        return allScanned;
//    }

}
