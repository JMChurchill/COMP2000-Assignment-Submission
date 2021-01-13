package controller;

import model.ProductDataManager;
import model.ScannedProducts;

import javax.swing.*;

public class CardPayment extends Payment{

    boolean doCashPayment() {return false;}

    @Override
    boolean cardPayment(String customersPin) {
        boolean pinOk = checkPin(customersPin);
        return pinOk;
    }

    @Override
    boolean cashPayment(double totalPaid, double totalPrice) { return false;}


    public boolean checkPin(String customersPin){
        JOptionPane.showMessageDialog(null,"checking pin: "+ customersPin);
        boolean pinCorrect = false;
        String[] arrayOfPins = {"1010","1234","2222"};
        //Todo contact card payment api -> if pin correct take money from account and return true
        //check if pin correct
        for (String pin:arrayOfPins) {
            if (customersPin.equals(pin)){
                pinCorrect = true;
                break;
            }
        }
        return pinCorrect;
    }
}
