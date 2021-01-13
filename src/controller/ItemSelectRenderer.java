package controller;

import model.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemSelectRenderer extends JListRenderer implements ListCellRenderer<Object>{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        //assign value that is passed
        Product is = (Product) value;
        setText(is.getName() + " Quantity Remaining: " + is.getStock() + String.format(" Price: £%.2f", is.getPrice()));

        try {
            //convert string to url
            URL url = new URL(is.getImage());
            //convert url to image
            Image urlToImg = ImageIO.read(url);
            //calculate new width given height = 100
            int newWidth = calcNewWidth(urlToImg,100);
            //resize image
            BufferedImage img = resizeImg(newWidth,100,urlToImg);

            setIcon(new ImageIcon(img));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackForeground(isSelected,list);

        setEnabled(true);
        setFont(list.getFont());
        return this;
    }
}
