package controller;

import model.Product;
import model.ProductDataManager;
import model.ScannedProduct;
import model.ScannedProducts;

import java.util.ArrayList;

public class CheckoutViewController {
    public static ArrayList<ScannedProduct> addProductToScanned(String barcode, String name, String img, double price){
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

//    public static boolean cashPayment(ScannedProducts products,double totalPaid, double totalPrice){
//        boolean isPaid = false;
//        double changeDue = totalPaid-totalPrice;
//        if (changeDue >=0){
//            isPaid = true;
//        }
//
//        ProductDataManager pData = new ProductDataManager();
//
//        updateStock(pData,products);
//        return isPaid;
//    }

//    public static boolean cardPayment(String customersPin){
//        boolean pinOk = checkPin(customersPin);
//        if (pinOk){
//            //add up all scanned products prices
//            ProductDataManager pData = new ProductDataManager();
//            ScannedProducts products = new ScannedProducts();
//            double totalPrice = ScannedProducts.getTotalPrice();
//            updateStock(pData,products);
//        }
//        return pinOk;
//    }
    
//    public static boolean checkPin(String customersPin){
//        boolean pinCorrect = false;
//        String[] arrayOfPins = {"1010","1234","2222"};
//        //check if pin correct
//        for (String pin:arrayOfPins) {
//            if (customersPin.equals(pin)){
//                pinCorrect = true;
//                break;
//            }
//        }
//        return pinCorrect;
//    }

//    public static void updateStock(ProductDataManager pData,ScannedProducts products){
//        for (ScannedProduct sP:products.getAll()) {
//            for (Product p: ProductDataManager.getAllProducts()) {
//                if (p.getBarcode().equals(sP.getBarcode())){
//                    p.setStock(p.getStock() - sP.getQuantityScanned());
//                }
//            }
//        }
//        pData.save();
//    }

//    public static String createReceiptMessage(boolean isCash, ScannedProducts products, double totalPaid){
//        double price = 0;
//        double total = 0;
//        //create receipt message
//        String message ="<html><u>Receipt</u></html> \n";
//
//        for (ScannedProduct sp:products.getAll()) {
//            price = sp.getPrice() * sp.getQuantityScanned();
//            total += price;
//            message += sp.getName();
//            message += String.format(" x " + sp.getQuantityScanned() + " £%.2f", price);
//            message += "\n";
//        }
//        message += "===========\n";
//        message += String.format("Total: £%.2f", total);
//
//        if (isCash){
//            double change = totalPaid - total;
//            message += "\n-----------\n";
//            message += String.format("Cash: £%.2f", totalPaid);
//            message += String.format("\nChange: £%.2f", change);
//        }
//        return message;
//    }
}
