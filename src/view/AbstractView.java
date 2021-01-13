package view;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JFrame {
    protected void initialise(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1500,800));
        setTitle("Checkout");
        pack();
    }
}
