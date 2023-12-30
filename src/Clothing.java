public class Clothing extends Product {
    private String color;
    private String size;

    public Clothing(){

    }

    public Clothing(String color, String size) {
        this.color = color;
        this.size = size;
    }

    public Clothing(String productId, String prouctName, int availableItems, double price, String color, String size) {
        super(productId,prouctName,availableItems,price);
        this.color = color;
        this.size = size;
    }



    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductType(){
        return "Clothing";
    }
}
