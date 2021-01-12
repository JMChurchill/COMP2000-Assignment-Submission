package controller;

import model.ScannedProduct;
import model.ScannedProducts;

import java.util.ArrayList;

public class CheckoutViewController {
    public static ArrayList<ScannedProduct> addProductToScanned(String barcode, String name, String img, double price){
        //ScannedListModel.clear();
        int quantityScanned = 1;

        ScannedProducts scannedArray = new ScannedProducts();
        if (scannedArray.getAll().isEmpty()){
            ScannedProduct tempProduct = new ScannedProduct(barcode, name, img, price, quantityScanned);
            scannedArray.addProduct(tempProduct);
        }else{
            for (ScannedProduct sP:scannedArray.getAll()) {
                if (sP.getBarcode().equals(barcode)){
                    quantityScanned = sP.getQuantityScanned() + 1;
                    sP.setQuantityScanned(quantityScanned);
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


//    public void populateScannedJList(ArrayList<ScannedProduct> allScanned){
//        ScannedListModel.clear();
//        for (ScannedProduct i:allScanned) {
//            ScannedListModel.addElement(i);
//            ScannedItemJList.setCellRenderer(new ScannedItemRenderer());
//            ScannedItemJList.setModel(ScannedListModel);
//        }
//        totalValueLbl.setText(String.format("£%.2f",ScannedProducts.getTotalPrice()));
//    }
}
