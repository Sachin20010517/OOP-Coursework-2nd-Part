import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<CartItem> shoppingCartList;

    public ShoppingCart(){
        this.shoppingCartList=new ArrayList<>();
    }

    public void addProduct(Product _product, String customerName){
        // Check if the product is already in the cart
        for (CartItem item : shoppingCartList) {
            if (item.getProduct().equals(_product)) {
                // Product already exists, increase the quantity
                item.increaseQuantity();
                return;
            }
        }

        // Product is not in the cart, add a new CartItem

        shoppingCartList.add(new CartItem(_product,customerName));
    }

    public void removeProduct(Product _product){
        this.shoppingCartList.remove(_product);
    }

    public double calculateTotalCost(){
        double totalCost  =0;

        for (CartItem _product: shoppingCartList){
            totalCost+=_product.getTotalPrice();
        }
        return totalCost;
    }

//    public void setShoppingCartList(ArrayList<Product> shoppingCartList) {
//        this.shoppingCartList = shoppingCartList;
//    }
//
//    public ArrayList<Product> getShoppingCartList() {
//        return shoppingCartList;
//    }

    public ArrayList<CartItem> getCartItems() {
        return shoppingCartList;
    }

    public int getElectronicQuantity() {
        int electronicQuantity = 0;
        for (CartItem item : shoppingCartList) {
//            if ("Electronic".equals(item.getProduct().getProductType())) {
//                electronicQuantity += item.getQuantity();
//            }
            if (item.getProduct().getProductType().equals("Electronic")){
                electronicQuantity += item.getQuantity();
            }
        }
        return electronicQuantity;
    }

    public int getClothingQuantity() {
        int clothingQuantity = 0;
        for (CartItem item : shoppingCartList) {
//            if ("Clothing".equals(item.getProduct().getProductType())) {
//                clothingQuantity += item.getQuantity();
//            }
            if (item.getProduct().getProductType().equals("Clothing")){
                clothingQuantity += item.getQuantity();
            }
        }
        return clothingQuantity;
    }




    public DefaultTableModel getTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product");
        model.addColumn("Category");
        model.addColumn("Quantity");
        model.addColumn("Price($)");
       // model.addColumn("Info");

        for (CartItem item : shoppingCartList) {
            Object[] rowData = {
                    "      "+item.getProduct().getProductId()+", "+item.getProduct().getProductName(),
                    item.getProduct().getProductType(),
                    item.getQuantity(),
                    item.getTotalPrice()
                    //item.getNumberOfAvailableItem()
            };
            model.addRow(rowData);
        }

        return model;
    }

    public void saveCartToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            for (CartItem item : shoppingCartList) {
                writer.println("Customer: " + item.getCustomerName());
                writer.println("Product: " + item.getProduct().getProductName());
                writer.println("Quantity: " + item.getQuantity());
                writer.println("Total Price: " + item.getTotalPrice());
                writer.println("--------------------");
            }
            System.out.println("Shopping cart saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving shopping cart to file: " + e.getMessage());
        }
    }


}
