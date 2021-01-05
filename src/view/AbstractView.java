package view;

import controller.AbstractController;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JFrame {
    protected AbstractController controller;

    protected void initialise(){
//        put this before initialisation call in userGUI
//        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,800));
        setTitle("Checkout");
        pack();
    }
}
