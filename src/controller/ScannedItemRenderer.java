package controller;

import model.Product;
import model.ScannedProduct;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ScannedItemRenderer extends JListRenderer implements ListCellRenderer<Object> {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        //assign value that is passed
        ScannedProduct is = (ScannedProduct) value;
        setText(String.format(is.getName() + "    Price: £%.2f", is.getPrice()) + "   X " + is.getQuantityScanned() + String.format("   Total: £%.2f",(is.getQuantityScanned()*is.getPrice())));

        try{
            //convert string to url
            URL url = new URL(is.getImage());
            //convert url to image
            Image urlToImg = ImageIO.read(url);
            //calculate new width given height = 220
            int newWidth = calcNewWidth(urlToImg);
                //resize image
            BufferedImage img = resizeImg(newWidth, 100, urlToImg);
            setIcon(new ImageIcon(img));
//            setIcon(new ImageIcon(urlToImg));

        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackForeground(isSelected,list);

        setEnabled(true);
        setFont(list.getFont());
        return this;
    }
}
