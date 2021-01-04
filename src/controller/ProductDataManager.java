package controller;

import model.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductDataManager {
//    public String filePath = "resources/ProductData.txt";
    public String filePath = "resources/ProductDataTest.txt";
    public String separator = "\\|";
//may not be static
//    private static final ArrayList<Product> products = new ArrayList<>();

    //get data from ProductData
    public void load(){
        try {
            File file = new File(filePath);

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String data = scanner.nextLine();

                String[] productData = data.split(separator);

                Product product = new Product();

                product.setBarcode(productData[0]);

                product.setName(productData[1]);

                product.setImage(productData[2]);

                double priceToDouble = Double.parseDouble(productData[3]);
                product.setPrice(priceToDouble);

                int quantityToInt = Integer.parseInt(productData[4]);
                product.setStock(quantityToInt);

                //products.add(product);
                addProduct(product);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter(filePath);

            for (int i=0; i < Product.getAllProducts().size(); i++){
                String data = "";

                if (i>0){
                    data += "\n";
                }

                data += Product.getAllProducts().get(i).getBarcode();
                data += "|";

                data += Product.getAllProducts().get(i).getName();
                data += "|";

                data += Product.getAllProducts().get(i).getImage();


                String priceToString = Double.toString(Product.getAllProducts().get(i).getPrice());
                data += "|" + priceToString;

                String QuantityToString = Integer.toString(Product.getAllProducts().get(i).getStock());
                data += "|" + QuantityToString;

                writer.write(data);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product newProduct){
        Product.add(newProduct);

//        products.add(newProduct);
    }

    public void removeProduct(Product oldProduct){
        Product.remove(oldProduct);

        //products.remove(oldProduct);
    }

    //todo remove this method
    public static ArrayList<Product> getAllProducts(){
        return Product.getAllProducts();

//        return products;
    }
}
