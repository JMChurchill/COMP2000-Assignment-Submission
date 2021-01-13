package view;

import controller.*;
import model.Product;
import model.ProductDataManager;
import model.ScannedProduct;
import model.ScannedProducts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JLabel lblImgage;
    private JPanel placeHolderPanel;
    private JButton btnHint;
    private JButton btnPinHint;
    private JTextField tFEditWSPrice;
    private JLabel lblDetailsWSPrice;

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
        rightAdPanel.add(placeHolderPanel,"4");

        rightAdCl.show(rightAdPanel,"4");


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
                    displayMessage("Sorry this Item is Out of Stock");
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
                    displayMessage("Please scan an item");
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
                ScannedProducts products = new ScannedProducts();
                String customersPin = tFieldPin.getText();
                boolean pinOk = CheckoutViewController.cardPayment(customersPin);
                if (pinOk){
                    //ask if user wants receipt
                    displayReceipt(false, products,0);
                    rightCl.show(rightPanel,"1");
                    clearScannedProducts(products.getAll());
                }else {
                    displayMessage("Incorrect pin" + customersPin);
                }
            }
        });
        btnPinHint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMessage("pins: 1234 , 1010 , 2222");
            }
        });
        btnPayCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalPaid;
                //todo cash payment functions
                ScannedProducts products = new ScannedProducts();
                double totalPrice = ScannedProducts.getTotalPrice();
                //get text from field
                try {
                    totalPaid = Double.parseDouble(tFieldCashPaid.getText());
                } catch (Exception ex){
                    totalPaid = 0;
                }
                double changeDue = totalPaid-totalPrice;
                if (changeDue>=0){
                    CheckoutViewController.cashPayment(products,totalPaid, totalPrice, changeDue);
                    //ask if user wants receipt
                    displayReceipt(true, products,totalPaid);
                    rightCl.show(rightPanel,"1");
                    clearScannedProducts(products.getAll());
                } else{
                    displayMessage("Please insert more cash");
                }
            }
        });
        btnAdminView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceCl.show(interfacePanel,"2");
                showAdminPanel();
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
        btnHint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMessage("Hint: username = user and password = password");
            }
        });

        btnExitAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginController.logOut();
                interfaceCl.show(interfacePanel,"1");
            }
        });
        btnExitAdmin2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginController.logOut();
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
                lblOrderCostPProd.setText(lblDetailsWSPrice.getText());

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
                double wholesalePrice = ((Product) jListLowStock.getSelectedValue()).getWholesalePrice();

                displayProductDetails(Barcode,Name,Img,Price,Stock,wholesalePrice);
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
                double wholesalePrice = ((Product)jListAllProducts.getSelectedValue()).getWholesalePrice();

                displayProductDetails(Barcode,Name,Img,Price,Stock,wholesalePrice);
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
                String image = tFEditImg.getText();
                double wholesalePrice = Double.parseDouble(tFEditWSPrice.getText());

                saveEditChanges(name,barcode,stock,price,image,wholesalePrice);
            }
        });
        btnOrderProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get product details and num ordering
                String productOrdering = lblOrderBarcode.getText().replaceAll("\\D+","");//parse lbl for numbers
                int numOrdering = Integer.parseInt(tFNumOrder.getText());
                double price = Double.parseDouble(lblOrderSoldPrice.getText().replaceAll("[^\\\\.0123456789]",""));
                double wholesalePrice = Double.parseDouble(lblOrderCostPProd.getText().replaceAll("[^\\\\.0123456789]",""));

                orderProduct(productOrdering,numOrdering,price,wholesalePrice);
            }
        });
//        tFNumOrder.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                String numOrdered = tFNumOrder.getText();
//                try {
//                    int intNumOrdered = Integer.parseInt(tFNumOrder.getText());
//                    double wholesalePrice = Double.parseDouble(lblOrderCostPProd.getText().replaceAll("[^\\\\.0123456789]",""));
//                    double total = intNumOrdered * wholesalePrice;
//                    lblOrderTotal.setText(String.valueOf(total));
//                } catch (Exception ex){
//
//                }
//
//            }
//        });
    }

    private void initialiseComponents(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,800));
        setTitle("Checkout");
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
    public void populateScannedJList(ArrayList<ScannedProduct> allScanned){
        ScannedListModel.clear();
        for (ScannedProduct i:allScanned) {
            ScannedListModel.addElement(i);
            ScannedItemJList.setCellRenderer(new ScannedItemRenderer());
            ScannedItemJList.setModel(ScannedListModel);
        }
         totalValueLbl.setText(String.format("£%.2f",ScannedProducts.getTotalPrice()));
    }
    public void clearScannedProducts(ArrayList<ScannedProduct> scannedArray){
        scannedArray.clear();
        populateScannedJList(scannedArray);
    }
    public void displayReceipt(boolean isCash, ScannedProducts products,double totalPaid){
        int rQuestion = JOptionPane.showConfirmDialog(null,
                "Would you like a receipt?", "Receipt?",
                JOptionPane.YES_NO_OPTION);
        if (rQuestion == 0){
            String message = CheckoutViewController.createReceiptMessage(isCash, products, totalPaid);
            //display popup
            JOptionPane.showMessageDialog(null, message, "Receipt", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void login(String userName, String password){
        boolean isFound;
        LoginController login = new LoginController();
        isFound = login.logIntoAccount(userName, password);

        if (isFound){
            //show admin database view
            showAdminPanel();
            updateStockLists();
        }else {
            displayMessage("Unable to login please try again");
        }
    }

    public void displayProductDetails(String Barcode, String Name, String Img, double Price, int Stock,double wholesalePrice){
        lblDetailsName.setText(Name);
        lblDetailsBarcode.setText("Barcode: " + Barcode);
        lblDetailsStock.setText("Stock: " + Stock);
        lblDetailsPrice.setText(String.format("Price: £%.2f", Price));
        lblDetailsWSPrice.setText(String.format("Wholesale Price: £%.2f",wholesalePrice));

        ImageRenderer renderer = new ImageRenderer();
        lblImgage.setIcon(new ImageIcon(renderer.urlToImage(Img)));

        rightAdCl.show(rightAdPanel,"1");
    }

    public void getSelectedEditDetails(){
        String name = lblDetailsName.getText();
        String barcode = lblDetailsBarcode.getText().replaceAll("\\D+","");
        int stock = Integer.parseInt(lblDetailsStock.getText().replaceAll("\\D+",""));
        double price = Double.parseDouble(lblDetailsPrice.getText().replaceAll("[^\\\\.0123456789]",""));
        double wholesalePrice = Double.parseDouble(lblDetailsWSPrice.getText().replaceAll("[^\\\\.0123456789]",""));

        populateEditView(name,barcode,stock,price,wholesalePrice);
    }
    public void populateEditView(String name,String barcode,int stock,double price,double wholesalePrice){
        String imageUrl = AdminViewController.getImageUrl(barcode);

        tFEditName.setText(name);
        tFEditBarcode.setText(barcode);
        tFEditStock.setText(String.valueOf(stock));
        tFEditPrice.setText(String.valueOf(price));
        tFEditImg.setText(imageUrl);
        tFEditWSPrice.setText(String.valueOf(wholesalePrice));
    }

    public void updateStockLists(){
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
        //low stock
        jListLowStock.setCellRenderer(new ItemSelectRenderer());
        jListLowStock.setModel(lowStockListModel);
        //all
        jListAllProducts.setCellRenderer(new ItemSelectRenderer());
        //populate low stock
        jListAllProducts.setModel(allListModel);
    }

    public void saveEditChanges(String name, String barcode, int stock, double price,String image,double wholesalePrice){
        int answer = JOptionPane.showConfirmDialog(null,"Are you sure you want to change this product?","Confirm",JOptionPane.YES_NO_OPTION);
        if (answer == 0){
            boolean isFound = AdminViewController.saveEditChanges(name,barcode,stock,price,image,wholesalePrice);
            if (isFound){
                updateStockLists();
                //change card to details Panel
                rightAdCl.show(rightAdPanel,"4");
            }
        }
    }
    public void orderProduct(String productOrdering, int numOrdering, double price, double wholesalePrice){
        double total = numOrdering * wholesalePrice;
        //check if user is sure
        int answer = JOptionPane.showConfirmDialog(null,String.format("The total price will be: £%.2f. Do you still want to order?", total),"Confirm",JOptionPane.YES_NO_OPTION);
        if (answer == 0){
            boolean isFound = AdminViewController.orderProduct(productOrdering,numOrdering,price,wholesalePrice);
            if (isFound){
                displayMessage("Product updated");
                rightAdCl.show(rightAdPanel,"4");
                updateStockLists();
            } else {
                displayMessage("Product not found");
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
    public void showAdminPanel(){
        if (LoginController.isLoggedIn()){
            adminCl.show(adminPanel,"2");
        } else {
            tFieldUserName.setText("");
            tFieldPassword.setText("");
            adminCl.show(adminPanel,"1");
        }
    }

}