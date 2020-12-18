package view;

import javax.swing.*;
import java.awt.*;

public class UserGUI extends JFrame {
    private JPanel mainPanel;
    private JLabel totalLbl;
    private JButton finishBtn;
    private JList ScannedItemJList;
    private JList ItemSelectJList;
    private JLabel totalValueLbl;
    private JButton AdminLoginBtn;

    public UserGUI() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,800));
        setTitle("Checkout");

//        setResizable(false);
//        ImageIcon image = new ImageIcon("");//create an image icon
//        setIconImage(image.getImage()); //change icon of frame
        getContentPane().setBackground(Color.black);//change background colour




        pack();
    }


    public static void main(String[] args) {
        UserGUI page = new UserGUI();
        page.setVisible(true);


//        JPanel redPanel = new JPanel(); //create panel
//        redPanel.setBackground(Color.red);//set background colour
//        redPanel.setBounds(0,0,250,250);//position and size
//        page.add(redPanel);
//
//        JPanel bluePanel = new JPanel(); //create panel
//        bluePanel.setBackground(Color.blue);//set background colour
//        bluePanel.setBounds(0,250,250,250);//position and size
//        page.add(bluePanel);


    }
}