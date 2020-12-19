package controller;

import javax.swing.*;

public class ImgAndText {
    private String name;
    private Icon img;

    public ImgAndText(String text, Icon icon){
        this.name=text;
        this.img=icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getImg() {
        return img;
    }

    public void setImg(Icon img) {
        this.img = img;
    }
}
