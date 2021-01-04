package model;

import java.util.ArrayList;

public class ScannedProducts {
    private static final ArrayList<ScannedProduct> allScanned = new ArrayList<>();

    public ArrayList<ScannedProduct> getAll(){
        return allScanned;
    }

    public void add(ScannedProduct newScan){
        //totalPrice = totalPrice + newScan.getPrice();
        allScanned.add(newScan);

    }

    public void remove(ScannedProduct oldScan){
        //totalPrice = totalPrice - oldScan.getPrice();
        allScanned.remove(oldScan);
    }

    public static double getTotalPrice(){
        double totalPrice = 0;
        if (allScanned.isEmpty()){
            totalPrice = 0;
        }else{
            for (ScannedProduct product:allScanned) {
                totalPrice = totalPrice + (product.getPrice() * product.getQuantityScanned());
            }
        }
        return totalPrice;
    }

}
