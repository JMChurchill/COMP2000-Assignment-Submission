package controller;

import model.Product;
import model.ProductDataManager;
import model.ScannedProduct;
import model.ScannedProducts;

import javax.swing.*;

public abstract class Payment {
    protected boolean isCash = false;
    protected boolean isPaid = false;
    protected String message = null;

    public final String makePayment( String customersPin, double totalPaid, double totalPrice){
        isPaid = false;
        isCash = false;
        ProductDataManager pData = new ProductDataManager();
        ScannedProducts products = new ScannedProducts();

        if (doCardPayment()){
            isCash = false;
            isPaid = cardPayment(customersPin);
        }

        if (doCashPayment()){
            isCash = true;
            isPaid = cashPayment(totalPaid, totalPrice);
        }

        if (isPaid){
            updateStock(pData,products);
            message = createReceiptMessage(isCash, products, totalPaid);
        }

        return message;
    }

    abstract boolean cardPayment(String customersPin);
    abstract boolean cashPayment(double totalPaid, double totalPrice);

    //hooks
    boolean doCardPayment(){return true;}
    boolean doCashPayment(){return true;}


    //methods always ran
    public void updateStock(ProductDataManager pData, ScannedProducts products){
        for (ScannedProduct sP:products.getAll()) {
            for (Product p: ProductDataManager.getAllProducts()) {
                if (p.getBarcode().equals(sP.getBarcode())){
                    p.setStock(p.getStock() - sP.getQuantityScanned());
                }
            }
        }
        pData.save();
    }


    public String createReceiptMessage(boolean isCash, ScannedProducts products, double totalPaid){
        String message = null;
        int rQuestion = JOptionPane.showConfirmDialog(null,
                "Would you like a receipt?", "Receipt?",
                JOptionPane.YES_NO_OPTION);

        if (rQuestion == 0){
            double price = 0;
            double total = 0;
            //create receipt message
            message ="<html><u>Receipt</u></html> \n";

            for (ScannedProduct sp:products.getAll()) {
                price = sp.getPrice() * sp.getQuantityScanned();
                total += price;
                message += sp.getName();
                message += String.format(" x " + sp.getQuantityScanned() + " £%.2f", price);
                message += "\n";
            }
            message += "===========\n";
            message += String.format("Total: £%.2f", total);

            if (isCash){
                double change = totalPaid - total;
                message += "\n-----------\n";
                message += String.format("Cash: £%.2f", totalPaid);
                message += String.format("\nChange: £%.2f", change);
            }
        }
        return message;
    }
    public boolean isPaidFor(){
        return isPaid;
    }
}
