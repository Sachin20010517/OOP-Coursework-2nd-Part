import java.util.ArrayList;
import java.util.List;

public class User{
    private String userName;
    private String password;
    private List<ArrayList<Product>> purchaseHistory;
    private boolean isFirstPurchase;

    public User(){

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.purchaseHistory = new ArrayList<ArrayList<Product>>();
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

    public List<ArrayList<Product>> getPurchaseHistory() {
        return purchaseHistory;
    }
    public boolean isFirstPurchase() {
        return isFirstPurchase;
    }

    public void setFirstPurchase(boolean isFirstPurchase) {
        this.isFirstPurchase = isFirstPurchase;
    }
    public void addPurchaseToHistory(ArrayList<Product> product) {
        purchaseHistory.add(product);
    }


}



