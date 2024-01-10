public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(){

    }

    public Electronics(String brand, int warranty){
        this.brand=brand;
        warrantyPeriod =warranty;
    }

    public Electronics(String productId, String productName, int availableItems, double price, String brand, int warranty){
        super(productId,productName,availableItems,price);
        this.brand=brand;
        warrantyPeriod =warranty;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getProductType(){
        return "Electronic";
    }
}
