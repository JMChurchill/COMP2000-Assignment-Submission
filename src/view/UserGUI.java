package view;

import controller.ItemSelectRenderer;
import controller.ProductDataManager;
import controller.ScannedItemRenderer;
import model.Product;
import model.ScannedProduct;
import model.ScannedProducts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class UserGUI extends JFrame {
    private JPanel mainPanel;
    private JLabel totalLbl;
    private JButton btnFinish;
    private JList ScannedItemJList;
    private JList ItemSelectJList;
    private JLabel totalValueLbl;
    private JButton btnAdminLogin;
    private JPanel rightPanel;
    private JPanel LeftPanel;
    private JPanel itemSelectPanel;
    private JPanel cardPaymentPanel;
    private JPanel NumPadJPanel;
    private JButton btn1;
    private JButton btn2;
    private JButton btn8;
    private JButton btn7;
    private JButton btn4;
    private JButton btn6;
    private JButton btn5;
    private JButton btn9;
    private JButton btnGo;
    private JButton btn0;
    private JButton btnBackspace;
    private JButton btn3;
    private JTextField tFieldPin;
    private JButton btnCancelCard;
    private JPanel cashPaymentPanel;
    private JTextField textFieldCashPaid;
    private JButton btnCancelCash;
    private JButton btnPayCash;
    private JLabel lblRemaining;
    private JPanel userPanel;
    private JPanel adminPanel;
    private JTextField tFieldUserName;
    private JTextField tFieldPassword;
    private JPanel LoginPanel;
    private JPanel interfacePanel;
    private JButton btnLogin;

    //for switching between jPanels
    private final CardLayout interfaceCl = new CardLayout();
    private final CardLayout rightCl = new CardLayout();

    DefaultListModel listModel = new DefaultListModel();
    DefaultListModel ScannedListModel = new DefaultListModel();

    public UserGUI() {
        //setup Interface
        interfacePanel.setLayout(interfaceCl);
        interfacePanel.add(userPanel,"1");
        interfacePanel.add(adminPanel,"2");

        interfaceCl.show(interfacePanel,"1");

        //setup rightPanel
        rightPanel.setLayout(rightCl);
        rightPanel.add(itemSelectPanel,"1");
        rightPanel.add(cardPaymentPanel,"2");
        rightPanel.add(cashPaymentPanel,"3");

        rightCl.show(rightPanel,"1");

        initialiseComponents();
        ItemSelectJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String Barcode = ((Product)ItemSelectJList.getSelectedValue()).getBarcode();
                String Name = ((Product)ItemSelectJList.getSelectedValue()).getName();
                String Img = ((Product)ItemSelectJList.getSelectedValue()).getImage();
                double Price = ((Product)ItemSelectJList.getSelectedValue()).getPrice();
                int Stock = ((Product)ItemSelectJList.getSelectedValue()).getStock();

                if (Stock > 0){
                    //JOptionPane.showMessageDialog(null, ((Product)ItemSelectJList.getSelectedValue()).getName() + " Barcode: " + ((Product)ItemSelectJList.getSelectedValue()).getBarcode());
                    AddProductToScanned(Barcode, Name, Img, Price);
                } else{
                    JOptionPane.showMessageDialog(null,"Sorry this Item is Out of Stock");
                }
            }
        });
        ScannedItemJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                //values
                String barcode = ((ScannedProduct)ScannedItemJList.getSelectedValue()).getBarcode();
                int QuantityScanned;

                int response = JOptionPane.showConfirmDialog(null,"Would you like to delete this item");
                //if yes remove item/reduce quantity of item from array
                if (response == 0){
                    ScannedListModel.clear();
                    ArrayList<ScannedProduct> allScanned = new ArrayList<>();
                    ScannedProducts Sc = new ScannedProducts();
                    allScanned = Sc.getAll();

                    for (ScannedProduct sP:allScanned) {
                        if (sP.getBarcode() == barcode){
                            QuantityScanned = sP.getQuantityScanned();
                            if(QuantityScanned == 1){
                                Sc.remove(sP);
                            }else{
                                sP.setQuantityScanned(QuantityScanned - 1);
                            }
                            break;
                        }
                    }
                    populateScannedJList(allScanned);
                }
            }
        });
        btnFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO finish and pay function
                //ScannedProducts usersProducts = new ScannedProducts();
                if(ScannedProducts.getTotalPrice() > 0){
                    Object[] options ={"Card","Cash","Cancel"};
                    int response = JOptionPane.showOptionDialog(null,"Would you Like to pay Cash or Card?",
                            "Card or Cash", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                            options,options[2]);
                    //if cash
                    if (response == 0){ //if card
                        //show cardPaymentPanel
                        rightCl.show(rightPanel,"2");
                    } else if (response == 1){ //if cash
                        //show cashPaymentPanel
                        rightCl.show(rightPanel,"3");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Please scan an item");
                }
            }
        });
        btnCancelCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightCl.show(rightPanel,"1");
            }
        });
        btnCancelCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightCl.show(rightPanel,"1");
            }
        });
        //press go on the card machine
        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo card payment functions
                //add up all scanned products prices
                ProductDataManager pData = new ProductDataManager();
                ScannedProducts products = new ScannedProducts();
                double totalPrice = ScannedProducts.getTotalPrice();
                //todo check if pin correct -> contact card payment api -> if pin correct take money from account and return true
                String customersPin = tFieldPin.getText();
                //subtract bought items from stock (in flat database)
                updateStock(pData,products);
                //ask if user wants receipt
                displayReceipt(false, products,0);
                rightCl.show(rightPanel,"1");
                clearScannedProducts(products.getAll());
            }
        });
        btnPayCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalPaid;
                //todo cash payment functions
                //add up all scanned products prices
                ProductDataManager pData = new ProductDataManager();
                ScannedProducts products = new ScannedProducts();
                double totalPrice = ScannedProducts.getTotalPrice();
                //get text from field
                try {
                    totalPaid = Double.parseDouble(textFieldCashPaid.getText());
                } catch (Exception ex){
                    totalPaid = 0;
                }
                double changeDue = totalPaid-totalPrice;

                JOptionPane.showMessageDialog(null,"total paid: "+totalPaid + " total price: "+totalPrice + " Change Due: "+changeDue);
                //todo make so cash paid is saved to a variable and if user has not paid enough allow them to add more to the current they have inserted
                if (changeDue>=0){
                    JOptionPane.showMessageDialog(null,"correct");
                    updateStock(pData,products);
                    //ask if user wants receipt
                    displayReceipt(true, products,totalPaid);
                    rightCl.show(rightPanel,"1");
                    clearScannedProducts(products.getAll());
                } else{
                    JOptionPane.showMessageDialog(null,"Please insert more cash");
                }

            }
        });
        //todo add login functionality
        btnAdminLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceCl.show(interfacePanel,"2");
            }
        });
    }

    private void initialiseComponents(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,800));
        setTitle("Checkout");
//        ImageIcon image = new ImageIcon(""); //create an image icon
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
        //get products from products array and add them to listModel
        for (Product p:allProducts) {
            listModel.addElement(p);
        }
        //Render Images and text
        ItemSelectJList.setCellRenderer(new ItemSelectRenderer());
        ItemSelectJList.setModel(listModel);
    }
    public void AddProductToScanned(String barcode, String name, String img, double price){
        ScannedListModel.clear();
        int quantityScanned = 1;

        ScannedProducts scannedArray = new ScannedProducts();
        if (scannedArray.getAll().isEmpty()){
            ScannedProduct tempProduct = new ScannedProduct(barcode, name, img, price, quantityScanned);
            scannedArray.addProduct(tempProduct);
        }else{
            for (ScannedProduct sP:scannedArray.getAll()) {
                if (sP.getBarcode() == barcode){
                    quantityScanned = sP.getQuantityScanned() + 1;
                    sP.setQuantityScanned(quantityScanned);
                    break;
                }
            }
            if (quantityScanned == 1){
                ScannedProduct newScanned = new ScannedProduct(barcode, name, img, price, quantityScanned);
                scannedArray.addProduct(newScanned);
            }
        }
        populateScannedJList(scannedArray.getAll());
    }
    public void populateScannedJList(ArrayList<ScannedProduct> allScanned){
        for (ScannedProduct i:allScanned) {
            ScannedListModel.addElement(i);
            ScannedItemJList.setCellRenderer(new ScannedItemRenderer());
            ScannedItemJList.setModel(ScannedListModel);
        }
         totalValueLbl.setText(String.format("£%.2f",ScannedProducts.getTotalPrice()));
    }
    public void updateStock(ProductDataManager pData,ScannedProducts products){
        for (ScannedProduct sP:products.getAll()) {
            for (Product p:pData.getAllProducts()) {
                if (p.getBarcode()==sP.getBarcode()){
                    p.setStock(p.getStock() - sP.getQuantityScanned());
                }
            }
        }
        pData.save();
    }
    public void clearScannedProducts(ArrayList<ScannedProduct> scannedArray){
        scannedArray.clear();
        ScannedListModel.clear();
        populateScannedJList(scannedArray);
    }
    public void displayReceipt(boolean isCash, ScannedProducts products,double totalPaid){
        int rQuestion = JOptionPane.showConfirmDialog(
                null,
                "Would you like a receipt?",
                "Receipt?",
                JOptionPane.YES_NO_OPTION);
        if (rQuestion == 0){
            //if yes display receipt in new window
            double price = 0;
            double total = 0;
            //create receipt message
            String message ="<html><u>Receipt</u></html> \n";

            for (ScannedProduct sp:products.getAll()) {
                price = sp.getPrice() * sp.getQuantityScanned();
                total += price;
                message += sp.getName();
                message += String.format(" x " + sp.getQuantityScanned() + " £%.2f", price);
                message += "\n";
            }
            message += "===========\n";
            message += String.format("Total: £%.2f", total);

            if (isCash){
                double change = totalPaid - total;
                message += "\n-----------\n";
                message += String.format("Cash: £%.2f", totalPaid);
                message += String.format("\nChange: £%.2f", change);
            }
            //display popup
            JOptionPane.showMessageDialog(null,
                    message,
                    "Receipt",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        UserGUI page = new UserGUI();
        page.setVisible(true);
    }
}