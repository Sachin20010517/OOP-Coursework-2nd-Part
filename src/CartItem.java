public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1; // Initialize quantity to 1 when adding the product to the cart
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        if (product.getNumberOfAvailableItem()>getQuantity()){
            quantity++;
        }


    }

    public double getTotalPrice() {
        return quantity * product.getPrice();
    }
}

/*Using a separate CartItem class provides a clear structure for handling shopping cart-related
functionality and allows for better scalability if you decide to add more features to your shopping cart in the future.
It also adheres to the principles of object-oriented design, where each class has a clear responsibility.*/