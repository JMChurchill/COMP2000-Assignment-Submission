package controller;

import model.ProductDataManager;

public class CashPayment extends Payment{

    boolean doCardPayment() {return false;}

    @Override
    boolean cardPayment(String customersPin) { return false; }

    @Override
    boolean cashPayment(double totalPaid, double totalPrice) {
        boolean isPaid = false;
        double changeDue = totalPaid-totalPrice;
        if (changeDue >=0){
            isPaid = true;
        }
        //todo make so cash paid is saved to a variable and if user has not paid enough allow them to add more to the current they have inserted

        return isPaid;
    }
}
