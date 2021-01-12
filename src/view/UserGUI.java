package view;

import controller.CheckoutViewController;
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
    private JButton btnAdminView;
    private JPanel rightPanel;
    private JPanel LeftPanel;
    private JPanel itemSelectPanel;
    private JPanel cardPaymentPanel;
    private JPanel NumPadJPanel;
    private JButton btnGo;
    private JTextField tFieldPin;
    private JButton btnCancelCard;
    private JPanel cashPaymentPanel;
    private JTextField tFieldCashPaid;
    private JButton btnCancelCash;
    private JButton btnPayCash;
    private JLabel lblRemaining;
    private JPanel userPanel;
    private JPanel adminPanel;
    private JTextField tFieldUserName;
    private JTextField tFieldPassword;
    private JPanel loginPanel;
    private JPanel interfacePanel;
    private JButton btnLogin;
    private JPanel adminViewPanel;
    private JButton btnExitAdmin;
    private JPanel leftAdPanel;
    private JPanel rightAdPanel;
    private JTabbedPane leftTabbedPane;
    private JPanel detailsPanel;
    private JPanel editDetailsPanel;
    private JButton btnExitAdmin2;
    private JButton btnNewOrder;
    private JButton btnEditDetailsV;
    private JPanel orderPanel;
    private JTextField tFEditName;
    private JTextField tFEditBarcode;
    private JTextField tFEditStock;
    private JTextField tFEditPrice;
    private JTextField tFEditImg;
    private JButton btnBackEdit;
    private JButton btnConfirmEdit;
    private JTextField tFNumOrder;
    private JButton btnOrderingBack;
    private JButton btnOrderProduct;
    private JLabel lblDetailsName;
    private JLabel lblDetailsBarcode;
    private JLabel lblDetailsStock;
    private JLabel lblDetailsPrice;
    private JLabel lblOrderName;
    private JLabel lblOrderBarcode;
    private JLabel lblOrderSoldPrice;
    private JLabel lblOrderCurStock;
    private JLabel lblOrderCostPProd;
    private JLabel lblOrderTotal;
    private JList jListAllProducts;
    private JList jListLowStock;

    //for switching between jPanels
    private final CardLayout interfaceCl = new CardLayout();
    private final CardLayout rightCl = new CardLayout();
    private final CardLayout adminCl = new CardLayout();
    private final CardLayout rightAdCl = new CardLayout();


    DefaultListModel listModel = new DefaultListModel();
    DefaultListModel ScannedListModel = new DefaultListModel();
    DefaultListModel allListModel = new DefaultListModel();
    DefaultListModel lowStockListModel = new DefaultListModel();


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

        //setup adminInterfaces
        adminPanel.setLayout(adminCl);
        adminPanel.add(loginPanel,"1");
        adminPanel.add(adminViewPanel,"2");

        adminCl.show(adminPanel,"1");

        //setup  rightAdPanel
        rightAdPanel.setLayout(rightAdCl);
        rightAdPanel.add(detailsPanel,"1");
        rightAdPanel.add(editDetailsPanel,"2");
        rightAdPanel.add(orderPanel,"3");

        rightAdCl.show(rightAdPanel,"1");


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
                    populateScannedJList(CheckoutViewController.addProductToScanned(Barcode, Name, Img, Price));
                } else{
                    JOptionPane.showMessageDialog(null,"Sorry this Item is Out of Stock");
                }
            }
        });
        ScannedItemJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //values
                String barcode = ((ScannedProduct)ScannedItemJList.getSelectedValue()).getBarcode();
                int QuantityScanned;

                int response = JOptionPane.showConfirmDialog(null,"Would you like to delete this item?");//todo remove cancel option
                //if yes remove item/reduce quantity of item from array
                if (response == 0){
                    populateScannedJList(CheckoutViewController.removeProductFromScanned(barcode));
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
                            "Card or Cash", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[2]);
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
                    totalPaid = Double.parseDouble(tFieldCashPaid.getText());
                } catch (Exception ex){
                    totalPaid = 0;
                }
                double changeDue = totalPaid-totalPrice;
                //todo make so cash paid is saved to a variable and if user has not paid enough allow them to add more to the current they have inserted
                if (changeDue>=0){
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
        btnAdminView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceCl.show(interfacePanel,"2");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get inputs
                String userName = tFieldUserName.getText();
                String password = tFieldPassword.getText();

                login(userName, password);
            }
        });
        btnExitAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceCl.show(interfacePanel,"1");
            }
        });
        btnExitAdmin2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceCl.show(interfacePanel,"1");
            }
        });
        btnNewOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display orderPanel
                rightAdCl.show(rightAdPanel,"3");
                //populate orderPanel
                lblOrderName.setText(lblDetailsName.getText());
                lblOrderBarcode.setText(lblDetailsBarcode.getText());
                lblOrderSoldPrice.setText(lblDetailsPrice.getText());
                lblOrderCurStock.setText(lblDetailsStock.getText());
                lblOrderCostPProd.setText("Cost per Product: N/A");//add later

                //todo create a listener that updates order total whenever tFNumOrder changes
            }
        });
        btnEditDetailsV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightAdCl.show(rightAdPanel,"2");
                getSelectedEditDetails();
            }
        });
        btnBackEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightAdCl.show(rightAdPanel,"1");
            }
        });

        btnOrderingBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightAdCl.show(rightAdPanel,"1");
            }
        });
        jListLowStock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String Barcode = ((Product)jListLowStock.getSelectedValue()).getBarcode();
                String Name = ((Product)jListLowStock.getSelectedValue()).getName();
                String Img = ((Product)jListLowStock.getSelectedValue()).getImage();
                double Price = ((Product)jListLowStock.getSelectedValue()).getPrice();
                int Stock = ((Product)jListLowStock.getSelectedValue()).getStock();

                rightAdCl.show(rightAdPanel,"1");

                displayProductDetails(Barcode,Name,Img,Price,Stock);
            }
        });
        jListAllProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String Barcode = ((Product)jListAllProducts.getSelectedValue()).getBarcode();
                String Name = ((Product)jListAllProducts.getSelectedValue()).getName();
                String Img = ((Product)jListAllProducts.getSelectedValue()).getImage();
                double Price = ((Product)jListAllProducts.getSelectedValue()).getPrice();
                int Stock = ((Product)jListAllProducts.getSelectedValue()).getStock();

                rightAdCl.show(rightAdPanel,"1");

                displayProductDetails(Barcode,Name,Img,Price,Stock);
            }
        });
        btnConfirmEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo make so can edit barcode (currently relies on barcode to identify product being edited)
                //get all text field values
                String name = tFEditName.getText();
                String barcode = tFEditBarcode.getText();//need to enable editable when converting method to edit barcode
                int stock = Integer.parseInt(tFEditStock.getText());
                double price = Double.parseDouble(tFEditPrice.getText());
//                tFEditImg.getText();//todo make so can edit img

                saveEditChanges(name,barcode,stock,price);
            }
        });
        btnOrderProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get product details and num ordering
                String productOrdering = lblOrderBarcode.getText().replaceAll("\\D+","");//parse lbl for numbers
                int numOrdering = Integer.parseInt(tFNumOrder.getText());
                double price = Double.parseDouble(lblOrderSoldPrice.getText().replaceAll("[^\\\\.0123456789]",""));

                orderProduct(productOrdering,numOrdering,price);
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
        ProductDataManager dataManager = new ProductDataManager();
        dataManager.load();
        //get products from products array and add them to listModel
        for (Product p:ProductDataManager.getAllProducts()) {
            listModel.addElement(p);
        }
        //Render Images and text
        ItemSelectJList.setCellRenderer(new ItemSelectRenderer());
        ItemSelectJList.setModel(listModel);
    }
    public void AddProductToScanned(String barcode, String name, String img, double price){//unused
        //ScannedListModel.clear();
        int quantityScanned = 1;

        ScannedProducts scannedArray = new ScannedProducts();
        if (scannedArray.getAll().isEmpty()){
            ScannedProduct tempProduct = new ScannedProduct(barcode, name, img, price, quantityScanned);
            scannedArray.addProduct(tempProduct);
        }else{
            for (ScannedProduct sP:scannedArray.getAll()) {
                if (sP.getBarcode().equals(barcode)){
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
        ScannedListModel.clear();
        for (ScannedProduct i:allScanned) {
            ScannedListModel.addElement(i);
            ScannedItemJList.setCellRenderer(new ScannedItemRenderer());
            ScannedItemJList.setModel(ScannedListModel);
        }
         totalValueLbl.setText(String.format("£%.2f",ScannedProducts.getTotalPrice()));
    }

    public void updateStock(ProductDataManager pData,ScannedProducts products){
        for (ScannedProduct sP:products.getAll()) {
            for (Product p: ProductDataManager.getAllProducts()) {
                if (p.getBarcode().equals(sP.getBarcode())){
                    p.setStock(p.getStock() - sP.getQuantityScanned());
                }
            }
        }
        pData.save();
    }
    public void clearScannedProducts(ArrayList<ScannedProduct> scannedArray){
        scannedArray.clear();
        //ScannedListModel.clear();
        populateScannedJList(scannedArray);
    }
    public void displayReceipt(boolean isCash, ScannedProducts products,double totalPaid){
        int rQuestion = JOptionPane.showConfirmDialog(
                null,
                "Would you like a receipt?",
                "Receipt?",
                JOptionPane.YES_NO_OPTION);
        if (rQuestion == 0){
//            //if yes display receipt in new window
//            double price = 0;
//            double total = 0;
//            //create receipt message
//            String message ="<html><u>Receipt</u></html> \n";
//
//            for (ScannedProduct sp:products.getAll()) {
//                price = sp.getPrice() * sp.getQuantityScanned();
//                total += price;
//                message += sp.getName();
//                message += String.format(" x " + sp.getQuantityScanned() + " £%.2f", price);
//                message += "\n";
//            }
//            message += "===========\n";
//            message += String.format("Total: £%.2f", total);
//
//            if (isCash){
//                double change = totalPaid - total;
//                message += "\n-----------\n";
//                message += String.format("Cash: £%.2f", totalPaid);
//                message += String.format("\nChange: £%.2f", change);
//            }
            String message = CheckoutViewController.createReceiptMessage(isCash, products, totalPaid);

            //display popup
            JOptionPane.showMessageDialog(null,
                    message,
                    "Receipt",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void login(String userName, String Password){
        boolean isFound = true; //todo change to false
        //todo hash password

        //todo search flat database for user with matching username and password -> if found return true

        if (isFound){
            //show admin database view
            adminCl.show(adminPanel,"2");
            //populate all products
            allListModel.clear();
            lowStockListModel.clear();
            //get products from products array and add them to listModel
            for (Product p: ProductDataManager.getAllProducts()) {
                allListModel.addElement(p);
                //if stock less than 20 add to lowStock
                if (p.getStock()<=20){
                    lowStockListModel.addElement(p);
                }
            }
            //Render Images and text
            //low stock
            jListLowStock.setCellRenderer(new ItemSelectRenderer());
            jListLowStock.setModel(lowStockListModel);
            //all
            jListAllProducts.setCellRenderer(new ItemSelectRenderer());
            jListAllProducts.setModel(allListModel);
            //populate low stock

        }else {
            //else return wrong password message
            displayMessage("Unable to login please try again");
        }
    }

    public void displayProductDetails(String Barcode, String Name, String Img, double Price, int Stock){
        lblDetailsName.setText(Name);
        lblDetailsBarcode.setText("Barcode: " + Barcode);
        lblDetailsStock.setText("Stock: " + Stock);
        lblDetailsPrice.setText(String.format("Price: £%.2f", Price));
        //todo display img
    }

    public void getSelectedEditDetails(){
        String name = lblDetailsName.getText();
        String barcode = lblDetailsBarcode.getText().replaceAll("\\D+","");
        int stock = Integer.parseInt(lblDetailsStock.getText().replaceAll("\\D+",""));
        double price = Double.parseDouble(lblDetailsPrice.getText().replaceAll("[^\\\\.0123456789]",""));
        //todo add photo

        populateEditView(name,barcode,stock,price);
    }
    public void populateEditView(String name,String barcode,int stock,double price){
        tFEditName.setText(name);
        tFEditBarcode.setText(barcode);
        tFEditStock.setText(String.valueOf(stock));
        tFEditPrice.setText(String.valueOf(price));

    }
    public void saveEditChanges(String name, String barcode, int stock, double price){
        boolean isFound = false;
        int answer = JOptionPane.showConfirmDialog(null,"Are you sure you want to change this product?","Confirm",JOptionPane.YES_NO_OPTION);
        if (answer == 0){
            //loop through array and search for matching barcode
            for (Product p:ProductDataManager.getAllProducts()) {
                if (p.getBarcode().equals(barcode)){
                    //edit product details with edited values
                    p.setName(name);
                    p.setStock(stock);
                    p.setPrice(price);
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                //save product array to text file
                ProductDataManager pdata = new ProductDataManager();
                pdata.save();
                JOptionPane.showMessageDialog(null,"Product Edited");

            }else{
                //display error message
                JOptionPane.showMessageDialog(null,"The product you were trying to edit was not found");
            }
            //todo refresh rightAdPanel

            //change card to details Panel
            rightAdCl.show(rightAdPanel,"1");
        }
    }
    public void orderProduct(String productOrdering, int numOrdering, double price){
        boolean isFound = false;
        double total = numOrdering * price;
        //check if user is sure
        int answer = JOptionPane.showConfirmDialog(null,String.format("The total price will be: £%.2f. Do you still want to order?", total),"Confirm",JOptionPane.YES_NO_OPTION);
        if(answer == 0){
            //edit file
            for (Product p:ProductDataManager.getAllProducts()) {
                if (p.getBarcode().equals(productOrdering)){
                    int stock = numOrdering + p.getStock();
                    p.setStock(stock);
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                ProductDataManager pData = new ProductDataManager();
                pData.save();
                JOptionPane.showMessageDialog(null,"Product updated");
                rightAdCl.show(rightAdPanel,"1");
            }else{
                JOptionPane.showMessageDialog(null,"Product not found");
            }
        }
    }

    public void displayMessage(String message){
        JOptionPane.showMessageDialog(null,message);
    }
    public int displayConfirmDialog(String message,String title){
        int answer = JOptionPane.showConfirmDialog(null,message,title,JOptionPane.YES_NO_OPTION);
        return answer;
    }

}