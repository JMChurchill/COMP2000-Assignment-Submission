package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductDataManagerTest {

    @Test
    public void load() {
        ProductDataManager productDataManager = new ProductDataManager();

        int sizeBefore = ProductDataManager.getAllProducts().size();

        productDataManager.load();

        int sizeAfter = ProductDataManager.getAllProducts().size();

        assertNotEquals("Compare sizes",sizeBefore,sizeAfter);
    }
}