public class Electronics extends Product {
    private String brand;
    private int warrentyPeriod;

    public Electronics(){

    }

    public Electronics(String brand, int warrenty){
        this.brand=brand;
        warrentyPeriod=warrenty;
    }

    public Electronics(String productId, String prouctName, int availableItems, double price, String brand, int warrenty){
        super(productId,prouctName,availableItems,price);
        this.brand=brand;
        warrentyPeriod=warrenty;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrentyPeriod() {
        return warrentyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrentyPeriod(int warrentyPeriod) {
        this.warrentyPeriod = warrentyPeriod;
    }

    public String getProductType(){
        return "Electronic";
    }
}
