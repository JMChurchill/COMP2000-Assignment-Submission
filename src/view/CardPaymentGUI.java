package view;

import javax.swing.*;
import java.awt.*;

public class CardPaymentGUI extends JFrame {
    private JPanel mainPage;
    private JLabel lblTotalValue;
    private JList ScannedItemJList;
    private JButton AdminLoginBtn;
    private JTextField tFieldPasswordTextField;
    private JPanel NumPadJPanel;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton btn0;
    private JButton btnBackspace;
    private JButton btnGo;
    private JButton btnCancel;

    public CardPaymentGUI(){
        setContentPane(mainPage);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,800));
        setTitle("Checkout| Card Payment");
        pack();

    }
}
