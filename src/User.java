import java.util.ArrayList;
import java.util.List;

public class User{
    private String userName;
    private String password;
    private List<Product> purchaseHistory;
    private boolean isFirstPurchase;

    public User(){

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.purchaseHistory = new ArrayList<>();
        this.isFirstPurchase = true;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getPurchaseHistory() {
        return purchaseHistory;
    }
    public boolean isFirstPurchase() {
        return isFirstPurchase;
    }

    public void setFirstPurchase(boolean isFirstPurchase) {
        this.isFirstPurchase = isFirstPurchase;
    }
    public void addPurchaseToHistory(Product product) {
        purchaseHistory.add(product);
    }
    public String getPurchaseHistoryAsString() {
        StringBuilder purchaseHistoryString = new StringBuilder();
        for (Product product : purchaseHistory) {
            purchaseHistoryString.append(product.getProductId()).append(",");
        }
        return purchaseHistoryString.toString();
    }
    public void addProductToPurchaseHistory(Product product) {
        purchaseHistory.add(product);
    }


}
