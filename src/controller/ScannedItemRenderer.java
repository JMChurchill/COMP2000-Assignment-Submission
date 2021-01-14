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

        BufferedImage img = urlToImage(is.getImage());
        if (img != null){
            setIcon(new ImageIcon(img));
        } else {
            setIcon(new ImageIcon("resources/images/imageNotFoundSmall.png"));
        }

        setBackForeground(isSelected,list);

        setEnabled(true);
        setFont(list.getFont());
        return this;
    }

    public BufferedImage urlToImage(String imageURL){
        //convert string to url
        try {
            URL url = new URL(imageURL);
            //convert url to image
            Image urlToImg = ImageIO.read(url);
            //calculate new width given height = 200
            int newWidth = calcNewWidth(urlToImg,100);
            BufferedImage img = resizeImg(newWidth, 100, urlToImg);

            return img;
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            return null;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
