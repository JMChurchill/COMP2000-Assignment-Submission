import javax.swing.*;
import java.awt.*;

public class UserGUI extends JFrame {
    private JPanel mainPanel;

    public UserGUI() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500,500));
        pack();
    }


    public static void main(String[] args) {
        UserGUI page = new UserGUI();
        page.setVisible(true);
    }
}