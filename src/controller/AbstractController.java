package controller;

public abstract class AbstractController {
    public static final String BARCODE = "Barcode";
    public static final String NAME = "Name";
    public static final String IMAGE = "Image";
    public static final String PRICE = "Price";
    public static final String STOCK = "Stock";

    public abstract void setModelProperty(/*KeyValuePair data*/);
    public abstract void upDateView(/*KeyValuePair data*/);

    public void swapModel(int index){}

}
