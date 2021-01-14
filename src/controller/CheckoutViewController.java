package controller;

import model.Product;
import model.ProductDataManager;
import model.ScannedProduct;
import model.ScannedProducts;

import javax.swing.*;
import java.util.ArrayList;

public class CheckoutViewController {
    public static ArrayList<ScannedProduct> addProductToScanned(String barcode, String name, String img, double price,int stock){
        int quantityScanned = 1;

        ScannedProducts scannedArray = new ScannedProducts();
        if (scannedArray.getAll().isEmpty()){
            ScannedProduct tempProduct = new ScannedProduct(barcode, name, img, price, quantityScanned);
            scannedArray.addProduct(tempProduct);
        }else{
            for (ScannedProduct sP:scannedArray.getAll()) {
                if (sP.getBarcode().equals(barcode)){
                    quantityScanned = sP.getQuantityScanned() + 1;
                    //check if quantity scanned is greater than stock
                    if (quantityScanned <= stock){
                        sP.setQuantityScanned(quantityScanned);
                    }else {
                        JOptionPane.showMessageDialog(null, "No more stock");
                    }
                    break;
                }
            }
            if (quantityScanned == 1){
                ScannedProduct newScanned = new ScannedProduct(barcode, name, img, price, quantityScanned);
                scannedArray.addProduct(newScanned);
            }
        }
        return scannedArray.getAll();
    }

    public static ArrayList<ScannedProduct> removeProductFromScanned(String barcode){
        int QuantityScanned;
        ArrayList<ScannedProduct> allScanned = new ArrayList<>();
        ScannedProducts Sc = new ScannedProducts();
        allScanned = Sc.getAll();

        for (ScannedProduct sP:allScanned) {
            if (sP.getBarcode().equals(barcode)){
                QuantityScanned = sP.getQuantityScanned();
                if(QuantityScanned == 1){
                    Sc.remove(sP);
                }else{
                    sP.setQuantityScanned(QuantityScanned - 1);
                }
                break;
            }
        }
        return allScanned;
    }


}
