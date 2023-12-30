public abstract class Product {

    private String productId;
    private String productName;
    private int numberOfAvailableItem;
    private double price;

    public Product(){

    }

    public Product(String productId, String productName, int numberOfAvailableItem, double price){
        this.productId=productId;
        this.productName=productName;
        this.numberOfAvailableItem=numberOfAvailableItem;
        this.price=price;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumberOfAvailableItem() {
        return numberOfAvailableItem;
    }

    public double getPrice() {
        return price;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNumberOfAvailableItem(int numberOfAvailableItem) {
        this.numberOfAvailableItem = numberOfAvailableItem;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract String getProductType();


}
