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
    private static final ArrayList<Product> products = new ArrayList<>();

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

                float priceToFloat = Float.parseFloat(productData[3]);
                product.setPrice(priceToFloat);

                int quantityToInt = Integer.parseInt(productData[4]);
                product.setStock(quantityToInt);

                products.add(product);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter(filePath);

            for (int i=0; i < products.size(); i++){
                String data = "";

                if (i>0){
                    data += "\n";
                }

                data += products.get(i).getBarcode();
                data += "|";

                data += products.get(i).getName();
                data += "|";

                data += products.get(i).getImage();


                String priceToString = Float.toString(products.get(i).getPrice());
                data += "|" + priceToString;

                String QuantityToString = Integer.toString(products.get(i).getStock());
                data += "|" + QuantityToString;

                writer.write(data);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product getProductAt(int index){
        if (index >= products.size()){
            return null;
        }
        return products.get(index);
    }

    public void addProduct(Product newProduct){
        products.add(newProduct);
    }

    public void removeProduct(Product oldProduct){
        products.remove(oldProduct);
    }

    public ArrayList<Product> getAllProducts(){
        return products;
    }
}
