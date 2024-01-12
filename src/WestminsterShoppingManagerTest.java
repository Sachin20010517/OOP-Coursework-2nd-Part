import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    @Test
    void testAddProductToArrayListSuccess(){
        var manager = new WestminsterShoppingManager();

        //assertEquals(manager.addProduct(product);
        Product product = new Clothing("C001", "Shirt", 10, 25.99, "Red", "M");
        manager.addProduct(product);

        assertTrue(WestminsterShoppingManager.getProductArrayList().contains(product));
    }

    @Test
    void testAddProductMaxLimitReached() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        // Fill the product ArrayList up to the maximum limit
        for (int i = 0; i < WestminsterShoppingManager.getMaximumProduct(); i++) {
            Product product = new Clothing("C" + i, "Product" + i, 10, 25.99, "Color", "Size");
            manager.addProduct(product);
        }

        // Try to add a new product when the maximum limit is reached
        Product newProduct = new Clothing("C999", "New Product", 10, 25.99, "Blue", "L");
        manager.addProduct(newProduct);

        // Check if the product is not added when the maximum limit is reached
        assertFalse(WestminsterShoppingManager.getProductArrayList().contains(newProduct));

        // You can add more assertions based on your specific requirements
    }

    @Test
    void testDeleteProductSuccess() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        Product productToDelete = new Clothing("C001", "Shirt", 10, 25.99, "Red", "M");

        manager.addProduct(productToDelete);

        // Check if the product is initially in the ArrayList
        assertTrue(WestminsterShoppingManager.getProductArrayList().contains(productToDelete));

        // Delete the product
        manager.deleteProduct("C001");

        // Check if the product is removed from the ArrayList
        assertFalse(WestminsterShoppingManager.getProductArrayList().contains(productToDelete));
    }

    @Test
    void testPrintProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        // Add some products to the ArrayList
        Product product1 = new Clothing("C001", "Shirt", 10, 25.99, "Red", "M");
        Product product2 = new Electronics("E001", "Laptop", 5, 899.99, "Dell", 24);

        manager.addProduct(product1);
        manager.addProduct(product2);

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Print the products
        manager.printProduct();

        // Reset System.out
        System.setOut(System.out);

        // Check if the printed output contains the details of the added products
        assertTrue(outputStream.toString().contains("Product Id  : C001"));
        assertTrue(outputStream.toString().contains("Product Name: Shirt"));
        assertTrue(outputStream.toString().contains("Brand       : Dell"));
        assertTrue(outputStream.toString().contains("Warranty Period(Years): 24"));
    }

    @Test
    void testSaveProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        // Add some products to the ArrayList
        Product product1 = new Clothing("C001", "Shirt", 10, 25.99, "Red", "M");
        Product product2 = new Electronics("E001", "Laptop", 5, 899.99, "Dell", 24);

        manager.addProduct(product1);
        manager.addProduct(product2);

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Save the products to a file
        manager.saveProduct();

        // Reset System.out
        System.setOut(System.out);

        // Check if the printed output indicates a successful save
        assertTrue(outputStream.toString().contains("Product list saved successfully!"));
    }

    @Test
    void testLoadProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.loadProduct();

        // Check if the product list is not empty
        assertFalse(manager.getProductArrayList().isEmpty(), "Product list should not be empty after loading products");

        // Check each product in the list
        for (Product product : manager.getProductArrayList()) {
            assertNotNull(product.getProductId(), "Product ID should not be null");
            assertNotNull(product.getProductName(), "Product name should not be null");
            assertTrue(product.getNumberOfAvailableItem() >= 0, "Available items should be non-negative");
            assertTrue(product.getPrice() >= 0, "Price should be non-negative");

            // Check product type-specific attributes
            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                assertNotNull(electronics.getBrand(), "Brand should not be null for Electronics");
                assertTrue(electronics.getWarrantyPeriod() >= 0, "Warranty period should be non-negative for Electronics");
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                assertNotNull(clothing.getSize(), "Size should not be null for Clothing");
                assertNotNull(clothing.getColor(), "Material should not be null for Clothing");
            }
        }
    }





}