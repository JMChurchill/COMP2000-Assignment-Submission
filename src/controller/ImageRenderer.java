package controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageRenderer extends JListRenderer{

    public BufferedImage urlToImage(String imageURL){
        //convert string to url
        try {
            URL url = new URL(imageURL);
            //convert url to image
            Image urlToImg = ImageIO.read(url);
            //calculate new width given height = 200
            int newWidth = calcNewWidth(urlToImg,200);

            BufferedImage img = resizeImg(newWidth, 200, urlToImg);

            //resize image
            //BufferedImage img = (BufferedImage) urlToImg;
            return img;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
