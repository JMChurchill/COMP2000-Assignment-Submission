package model;

import java.util.ArrayList;

public class ScannedProducts {
    private static final ArrayList<ScannedProduct> allScanned = new ArrayList<>();

    public ArrayList<ScannedProduct> getAll(){
        return allScanned;
    }

    public void add(ScannedProduct newScan){
        allScanned.add(newScan);
    }
}
