package controller;

import model.Product;

import javax.swing.*;

public class AdminViewController {
    public static boolean orderProduct(String productOrdering, int numOrdering, double price,double wholesalePrice){
        boolean isFound = false;
        //edit file
        for (Product p:ProductDataManager.getAllProducts()) {
            if (p.getBarcode().equals(productOrdering)){
                int stock = numOrdering + p.getStock();
                p.setStock(stock);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            ProductDataManager pData = new ProductDataManager();
            pData.save();
        }else{
            JOptionPane.showMessageDialog(null,"Product not found");
        }
        return isFound;
    }
    public static String getImageUrl(String barcode){
        String imageUrl = null;

        for (Product p:ProductDataManager.getAllProducts()) {
            if (p.getBarcode().equals(barcode)){
                imageUrl = p.getImage();
                break;
            }
        }
        return imageUrl;
    }

    public static boolean saveEditChanges(String name, String barcode, int stock, double price,String image, double wholesalePrice){
        boolean isFound = false;
        //loop through array and search for matching barcode
        for (Product p:ProductDataManager.getAllProducts()) {
            if (p.getBarcode().equals(barcode)){
                //edit product details with edited values
                p.setName(name);
                p.setStock(stock);
                p.setPrice(price);
                p.setImage(image);
                p.setWholesalePrice(wholesalePrice);
                isFound = true;
                break;
            }
        }
        if (isFound){
            //save product array to text file
            ProductDataManager pdata = new ProductDataManager();
            pdata.save();
            JOptionPane.showMessageDialog(null,"Product Edited");

        }else{
            //display error message
            JOptionPane.showMessageDialog(null,"The product you were trying to edit was not found");
        }
        return isFound;
    }
}
