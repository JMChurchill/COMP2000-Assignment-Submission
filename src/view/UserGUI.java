package view;

import controller.ItemSelectRenderer;
import controller.ProductDataManager;
import controller.ScannedItemRenderer;
import model.Product;
import model.ScannedProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UserGUI extends JFrame {
    private JPanel mainPanel;
    private JLabel totalLbl;
    private JButton finishBtn;
    private JList ScannedItemJList;
    private JList ItemSelectJList;
    private JLabel totalValueLbl;
    private JButton AdminLoginBtn;

    DefaultListModel listModel = new DefaultListModel();
    DefaultListModel ScannedListModel = new DefaultListModel();



    public UserGUI() {
        initialiseComponents();
        ItemSelectJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String Barcode = ((Product)ItemSelectJList.getSelectedValue()).getBarcode();
                String Name = ((Product)ItemSelectJList.getSelectedValue()).getName();
                String Img = ((Product)ItemSelectJList.getSelectedValue()).getImage();
                Float Price = ((Product)ItemSelectJList.getSelectedValue()).getPrice();
                int Stock = ((Product)ItemSelectJList.getSelectedValue()).getStock();

                if (Stock == 0){
                    //JOptionPane.showMessageDialog(null, ((Product)ItemSelectJList.getSelectedValue()).getName() + " Barcode: " + ((Product)ItemSelectJList.getSelectedValue()).getBarcode());
                    AddProductToScanned(Barcode, Name, Img, Price);
                } else{
                    JOptionPane.showMessageDialog(null,"Sorry this Item is Out of Stock");
                }

            }
        });
    }

    private void initialiseComponents(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,800));
        setTitle("Checkout");
//        ImageIcon image = new ImageIcon("");//create an image icon
//        setIconImage(image.getImage()); //change icon of frame
        pack();
        populateListModel();

    }
    public void populateListModel(){
        listModel.clear();
        ArrayList<Product> allProducts = new ArrayList<>();
        ProductDataManager dataManager = new ProductDataManager();
        dataManager.load();
        allProducts = dataManager.getAllProducts();

//        //get products from products array and add them to listModel
        for (Product p:allProducts) {
            listModel.addElement(p);
        }

        //Render Images and text
        ItemSelectJList.setCellRenderer(new ItemSelectRenderer());
        ItemSelectJList.setModel(listModel);
    }
    //TODO: Stop multiple of the same type of items from showing up in ScannedItemJList (make so it increases a count for quantity)
    public void AddProductToScanned(String barcode, String name, String img, float price){
        int quantityScanned = 1;

        ArrayList<ScannedProduct> allScanned = new ArrayList<>();
        //todo: change to new class allScanned
        ScannedProduct Sc = new ScannedProduct();
        allScanned = Sc.getAll();
        for (ScannedProduct sP:allScanned) {
            if (sP.getBarcode() == barcode){
                quantityScanned = sP.getQuantityScanned() + 1;
                //quantityScanned++;
                sP.setQuantityScanned(quantityScanned);
                break;
            }
        }

        if (quantityScanned == 1){
            ScannedProduct tempProduct = new ScannedProduct(barcode, name, img, price, quantityScanned);
            ScannedListModel.addElement(tempProduct);
            ScannedItemJList.setCellRenderer(new ScannedItemRenderer());
            ScannedItemJList.setModel(ScannedListModel);
        }


        //ScannedProduct tempProduct = new ScannedProduct(barcode, name, img, price, quantityScanned);

        //Product tempProduct = new Product(barcode, name, img, price, quantity);

//        ScannedListModel.addElement(tempProduct);
//
//        ScannedItemJList.setCellRenderer(new ItemSelectRenderer());
//        ScannedItemJList.setModel(ScannedListModel);
    }


    public static void main(String[] args) {
        UserGUI page = new UserGUI();
        page.setVisible(true);

    }
}