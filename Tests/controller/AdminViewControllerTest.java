package controller;

import model.Product;
import model.ProductDataManager;
import model.ScannedProducts;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class AdminViewControllerTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        ProductDataManager productDataManager = new ProductDataManager();
        productDataManager.load();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void orderProductTest() {
        int stockBeforeOrder = 0;
        int stockAfterOrder = 0;

        String productOrdering = "123456";
        int numOrdering = 10;
        double price = 1.25;
        double wholesalePrice = 0.98;


        for (Product p: ProductDataManager.getAllProducts()) {
            if (p.getBarcode().equals(productOrdering)){
                stockBeforeOrder = p.getStock();
                break;
            }
        }

        AdminViewController.orderProduct(productOrdering, numOrdering, price, wholesalePrice);

        for (Product p: ProductDataManager.getAllProducts()) {
            if (p.getBarcode().equals(productOrdering)){
                stockAfterOrder = p.getStock();
                break;
            }
        }

        assertEquals("Ordering Product method",stockBeforeOrder + numOrdering, stockAfterOrder);
    }

    @Test
    public void saveEditChangesTest() {
        String nameBefore = "";
        String newName = "";
        //ProductChanging
        String productEditingBarcode = "123456";
        int stock = 99;
        double price = 1.25;
        String image ="https://images.freeimages.com/images/small-previews/b38/bread-1-1329979.jpg";
        double wholesalePrice = 0.7;

        //get current name
        for (Product p: ProductDataManager.getAllProducts()) {
            if (p.getBarcode().equals(productEditingBarcode)){
                nameBefore = p.getName();
                break;
            }
        }

        //add 1 after name
        String changeNameTo = nameBefore + "1";

        AdminViewController.saveEditChanges(changeNameTo, productEditingBarcode, stock, price, image, wholesalePrice);

        for (Product p: ProductDataManager.getAllProducts()) {
            if (p.getBarcode().equals(productEditingBarcode)){
                newName = p.getName();
                break;
            }
        }

        assertNotEquals("Changing Product name method",nameBefore,newName);


    }

}