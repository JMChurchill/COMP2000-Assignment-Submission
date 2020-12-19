package controller;

import model.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemSelectRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object>{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        //return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        //assign value that is passed

        Product is = (Product) value;
        setText(is.getName() + " Quantity Remaining: " + is.getQuantity() + " Price: £" + is.getPrice());

        try {
            //convert string to url
            URL url = new URL(is.getImage());
            //convert url to image
            Image urlToImg = ImageIO.read(url);
            setIcon(new ImageIcon(urlToImg));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //set background and foreground colours to custom list row
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setEnabled(true);
        setFont(list.getFont());
        return this;
    }

//    private Image resizeImg(int width, int height,Image oldImage){
//        Image newImage = null;
//        try {
//            ImageIcon a = new ImageIcon(oldImage);
//            newImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
//            Graphics2D g2d = (Graphics2D) newImage.createGraphics();
//            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
//            g2d.drawImage(a.getImage(), 0, 0, WIDTH, HEIGHT, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return newImage;
//    }

}
