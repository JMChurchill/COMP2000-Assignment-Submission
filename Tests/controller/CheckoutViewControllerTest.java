package controller;

import model.Product;
import model.ScannedProduct;
import model.ScannedProducts;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckoutViewControllerTest {

    @Before
    public void setUp() throws Exception {
        //reset all scanned products
        ScannedProducts scannedProducts = new ScannedProducts();
        scannedProducts.getAll().clear();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addProductToScannedTest(){
        String barcode = "11111";
        String name = "TestProduct1";
        String img = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg/220px-Ash_Tree_-_geograph.org.uk_-_590710.jpg";
        double price = 2.3;
        int stock = 10;

        ScannedProducts scannedArray = new ScannedProducts();
        //get size of array list
        int numProductsBeforeAdd = scannedArray.getAll().size();

        //run method
        CheckoutViewController.addProductToScanned(barcode,name,img,price,stock);
        //get size of array list
        int numProductsAfterAdd = scannedArray.getAll().size();

        assertNotSame(numProductsBeforeAdd,numProductsAfterAdd);

    }

    @Test
    public void checkCorrectProductAddedToScannedTest() {
        boolean productMatched = false;

        String barcode = "11111";
        String name = "TestProduct1";
        String img = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg/220px-Ash_Tree_-_geograph.org.uk_-_590710.jpg";
        double price = 2.3;
        int stock = 10;
        //run method
        CheckoutViewController.addProductToScanned(barcode,name,img,price,stock);
        //get product from arrayList (will only be one)
        ScannedProducts scannedArray = new ScannedProducts();
        ScannedProduct product = scannedArray.getProductAt(0);

        //check if all match


        if (barcode.equals(product.getBarcode()) && name.equals(product.getName()) && img.equals(product.getImage()) && price == product.getPrice()){
            productMatched = true;
        }
        assertEquals(true,productMatched);
    }

    @Test
    public void addTwoSameProductsToScanned(){
        String barcode = "33333";
        String name = "TestProduct1";
        String img = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg/220px-Ash_Tree_-_geograph.org.uk_-_590710.jpg";
        double price = 2.3;
        int stock = 10;
        //run method
        CheckoutViewController.addProductToScanned(barcode,name,img,price,stock);
        CheckoutViewController.addProductToScanned(barcode,name,img,price,stock);
        //get products from arrayList
        ScannedProducts scannedArray = new ScannedProducts();
        ScannedProduct product = scannedArray.getProductAt(0);
        int quantity = product.getQuantityScanned();

        assertEquals(2,quantity);
    }


    @Test
    public void removeProductFromScannedTest() {

        String barcode = "1234";
        String name = "TestProduct2";
        String img = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg/220px-Ash_Tree_-_geograph.org.uk_-_590710.jpg";
        double price = 1.3;
        int stock = 10;
        //run method
        CheckoutViewController.addProductToScanned(barcode,name,img,price,stock);

        ScannedProducts prod = new ScannedProducts();
        int numProductsBeforeRemove = prod.getAll().size();

        String productToRemoveBarcode = "1234";

        CheckoutViewController.removeProductFromScanned(productToRemoveBarcode);

        int numProductsAfterRemove = prod.getAll().size();

        assertNotSame(numProductsBeforeRemove,numProductsAfterRemove);
    }

    @Test
    public void checkRemovesCorrectProduct(){
        String remainingItemBC;
        //product 1
        String barcode1 = "11111";
        String name1 = "TestProduct1";
        String img1 = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg/220px-Ash_Tree_-_geograph.org.uk_-_590710.jpg";
        double price1 = 1.1;
        int stock1 = 10;
        //product 2
        String barcode2 = "22222";
        String name2 = "TestProduct2";
        String img2 = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg/220px-Ash_Tree_-_geograph.org.uk_-_590710.jpg";
        double price2 = 1.2;
        int stock2 = 20;


        //run method
        CheckoutViewController.addProductToScanned(barcode1,name1,img1,price1,stock1);
        CheckoutViewController.addProductToScanned(barcode2,name2,img2,price2,stock2);

        //ScannedProducts prod = new ScannedProducts();

        String productToRemoveBarcode = barcode1;

        CheckoutViewController.removeProductFromScanned(productToRemoveBarcode);


        ScannedProducts prod = new ScannedProducts();
        ScannedProduct scannedProd = new ScannedProduct();
        if (prod.getAll().size() == 1){
            scannedProd = prod.getProductAt(0);
            remainingItemBC = scannedProd.getBarcode();
        } else {
            //if array != 1 make force assertSame
            remainingItemBC = productToRemoveBarcode;
        }

        assertNotSame(productToRemoveBarcode,remainingItemBC);
    }
}