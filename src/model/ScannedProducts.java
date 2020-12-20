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

    public void remove(ScannedProduct oldScan){
        allScanned.remove(oldScan);
    }

    public ScannedProduct getScannedAt(int index){
        if (index >= allScanned.size()){
            return null;
        }
        return allScanned.get(index);
    }

}
