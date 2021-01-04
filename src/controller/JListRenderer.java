package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class JListRenderer extends DefaultListCellRenderer {
    public BufferedImage resizeImg(int width, int height,Image oldImage){
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        try {
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(oldImage, 0, 0, width, height, null);
            graphics2D.dispose();
            return resizedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizedImage;
    }
    public int calcNewWidth(Image img){
        float currentWidth = img.getWidth(null);
        float currentHeight = img.getHeight(null);
        int newHeight = 100;
        int newWidth = Math.round(newHeight/(currentHeight/currentWidth));
        return newWidth;
    }

    public void setBackForeground(boolean isSelected, JList<?> list){
        //set background and foreground colours to custom list row
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
    }
}
