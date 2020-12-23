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
    private JButton finishBtn;
    private JList ScannedItemJList;
    private JList ItemSelectJList;
    private JLabel totalValueLbl;
    private JButton AdminLoginBtn;
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
    private JTextField tFieldPasswordTextField;
    private JButton btnCancelCard;
    private JPanel cashPaymentPanel;
    private JTextField textFieldCashPaid;
    private JButton btnCancelCash;

    //for switching between jPanels
    private CardLayout cl = new CardLayout();


    DefaultListModel listModel = new DefaultListModel();
    DefaultListModel ScannedListModel = new DefaultListModel();

    public UserGUI() {
        //setup setLayout
        rightPanel.setLayout(cl);
        rightPanel.add(itemSelectPanel,"1");
        rightPanel.add(cardPaymentPanel,"2");
        rightPanel.add(cashPaymentPanel,"3");

        cl.show(rightPanel,"1");

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
        finishBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO finish and pay function
                //ScannedProducts usersProducts = new ScannedProducts();

                Object[] options ={"Cash","Card","Cancel"};
                int response = JOptionPane.showOptionDialog(null,"Would you Like to pay Cash or Card?",
                        "Cash or Card", JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                        options,options[2]);
                //if cash
                if (response == 0){
                    //show cardPaymentPanel
                    cl.show(rightPanel,"2");
                } else if (response == 1){  //if card
                    //show cashPaymentPanel
                    cl.show(rightPanel,"3");
                }
            }
        });
        btnCancelCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(rightPanel,"1");
            }
        });
        btnCancelCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(rightPanel,"1");
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
    public void AddProductToScanned(String barcode, String name, String img, float price){
        ScannedListModel.clear();
        int quantityScanned = 1;

        ArrayList<ScannedProduct> allScanned = new ArrayList<>();
        ScannedProducts Sc = new ScannedProducts();
        allScanned = Sc.getAll();
        if (allScanned.isEmpty()){
            ScannedProduct tempProduct = new ScannedProduct(barcode, name, img, price, quantityScanned);
            allScanned.add(tempProduct);
        }else{
            for (ScannedProduct sP:allScanned) {
                if (sP.getBarcode() == barcode){
                    quantityScanned = sP.getQuantityScanned() + 1;
                    sP.setQuantityScanned(quantityScanned);
                    break;
                }
            }
            if (quantityScanned == 1){
                ScannedProduct newScanned = new ScannedProduct(barcode, name, img, price, quantityScanned);
                allScanned.add(newScanned);
            }
        }
        populateScannedJList(allScanned);
    }
    public void populateScannedJList(ArrayList<ScannedProduct> allScanned){
        for (ScannedProduct i:allScanned) {
            ScannedListModel.addElement(i);
            ScannedItemJList.setCellRenderer(new ScannedItemRenderer());
            ScannedItemJList.setModel(ScannedListModel);
        }
    }



    public static void main(String[] args) {
        UserGUI page = new UserGUI();
        page.setVisible(true);
    }
}