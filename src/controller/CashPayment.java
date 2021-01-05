package controller;

import model.ScannedProduct;
import model.ScannedProducts;
import view.UserGUI;

import javax.swing.*;

//todo make work

public class CashPayment {
    double change;

    ProductDataManager pData;
    ScannedProducts products;
    UserGUI GUI;


//    public CashPayment(Model model. View view){
//        this.paymentDue = paymentDue;
//        this.totalPaid = totalPaid;
//    }

    public boolean ProcessPayment(double paymentDue, double totalPaid){
        change = totalPaid - paymentDue;

        if (change>=0){
            GUI.updateStock(pData,products);//todo change when updateStock is moved out of gui
            //ask if user wants receipt
            displayReceipt(true, products,totalPaid);
//            rightCl.show(rightPanel,"1");
            GUI.clearScannedProducts(products.getAll());
            return true;
        }else{
            GUI.displayMessage("Please insert more cash");
            return false;
        }
    }


    public void displayReceipt(boolean isCash, ScannedProducts products, double totalPaid){
        int answer = GUI.displayConfirmDialog("Would you like a receipt?","Receipt?");
        if (answer == 0){
            //if yes display receipt in new window
            double price = 0;
            double total = 0;
            //create receipt message
            String message ="<html><u>Receipt</u></html> \n";
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
            //display popup
//            JOptionPane.showMessageDialog(null, message, "Receipt", JOptionPane.PLAIN_MESSAGE);
            GUI.displayMessage(message);
        }
    }


}
