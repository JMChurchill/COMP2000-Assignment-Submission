package model;

import model.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductDataManager implements DataManager{
//    public String filePath = "resources/ProductData.txt";
    public String filePath = "resources/ProductDataTest.txt";
    public String separator = "\\|";
//may not be static
    private static final ArrayList<Product> products = new ArrayList<>();

    //get data from ProductData
    @Override
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

                double wSPriceToDouble = Double.parseDouble(productData[5]);
                product.setWholesalePrice(wSPriceToDouble);

                products.add(product);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
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

                String priceToString = Double.toString(products.get(i).getPrice());
                data += "|" + priceToString;

                String QuantityToString = Integer.toString(products.get(i).getStock());
                data += "|" + QuantityToString;

                String wSPriceToString = Double.toString(products.get(i).getWholesalePrice());
                data += "|" + wSPriceToString;

                writer.write(data);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addProduct(Product newProduct){
        products.add(newProduct);
    }

    public void removeProduct(Product oldProduct){
        products.remove(oldProduct);
    }

    public static ArrayList<Product> getAllProducts(){
        return products;
    }
}
