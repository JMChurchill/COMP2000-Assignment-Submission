package controller;

import model.Product;

import javax.swing.*;

public class AdminViewController {
    public static boolean orderProduct(String productOrdering, int numOrdering, double price){
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
//                JOptionPane.showMessageDialog(null,"Product updated");
//                rightAdCl.show(rightAdPanel,"1");
        }else{
            JOptionPane.showMessageDialog(null,"Product not found");
        }
        return isFound;
    }
    public void saveEditChanges(){

    }
}
