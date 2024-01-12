import javax.swing.table.DefaultTableModel;
import java.io.File;
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

    public double calculateTotalCost(){
        double totalCost  =0;

        for (CartItem _product: shoppingCartList){
            totalCost+=_product.getTotalPrice();
        }
        return totalCost;
    }
    public ArrayList<CartItem> getCartItems() {
        return shoppingCartList;
    }

    public int getElectronicQuantity() {
        int electronicQuantity = 0;
        for (CartItem item : shoppingCartList) {
            if (item.getProduct().getProductType().equals("Electronic")){
                electronicQuantity += item.getQuantity();
            }
        }
        return electronicQuantity;
    }

    public int getClothingQuantity() {
        int clothingQuantity = 0;
        for (CartItem item : shoppingCartList) {
            if (item.getProduct().getProductType().equals("Clothing")){
                clothingQuantity += item.getQuantity();
            }
        }
        return clothingQuantity;
    }




    public DefaultTableModel getCartTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product");
        model.addColumn("Category");
        model.addColumn("Quantity");
        model.addColumn("Price($)");

        for (CartItem item : shoppingCartList) {
            Object[] rowData = {
                    "      " + item.getProduct().getProductId() + ", " + item.getProduct().getProductName(),
                    item.getProduct().getProductType(),
                    item.getQuantity(),
                    item.getTotalPrice()
            };
            model.addRow(rowData);
        }

        return model;
    }

// ...

    public void saveCartToFile() {
        String fileName = "cart.txt"; // Default file name
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            for (CartItem item : shoppingCartList) {
                writer.println("Customer: " + item.getCustomerName());
                writer.println("Product: " + item.getProduct().getProductName());
                writer.println("Quantity: " + item.getQuantity());
                writer.println("Total Price: " + item.getTotalPrice());
                writer.println("--------------------");
            }
            // System.out.println("Shopping cart saved to " + fileName);
        } catch (IOException e) {
            // If the file doesn't exist, create it and try saving again
            try {
                File file = new File(fileName);
                if (file.createNewFile()) {
                    // System.out.println("File created: " + fileName);
                    // Retry saving after file creation
                    saveCartToFile();
                } else {
                    // System.err.println("Error creating file: " + fileName);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                // System.err.println("Error creating file: " + ex.getMessage());
            }
        }
    }



}


