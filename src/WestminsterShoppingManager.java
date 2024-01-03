import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class WestminsterShoppingManager implements ShoppingManager{

    private static ArrayList<Product>  product_arrayList;
    private static final int maximumProduct=50;

    public WestminsterShoppingManager(){
        product_arrayList=new ArrayList<Product>();
    }



    @Override
    public void addProduct(Product _product) {
        if (product_arrayList.size()<maximumProduct){
            product_arrayList.add(_product);
            System.out.println("The item has been successfully added to the store.\n");
        }
        else {
            System.out.println("--The maximum product limit has been reached. Unable to add additional products--.\n    Try Again Later!\n");
        }

    }

    @Override
    public void deleteProduct(String _productId) {
        Product foundProduct = null;
        for (Product product : product_arrayList) {
            if (product.getProductId().equals(_productId)) {
                foundProduct = product;
                break; // Exit the loop once the product is found
            }
        }

        if (foundProduct != null) {
            product_arrayList.remove(foundProduct);
            System.out.println("Product was successfully deleted!\n     Removed Product Details:");
            displayProductInfo(foundProduct);
            System.out.println("Total number of products left: " + product_arrayList.size());
        } else {
            System.out.println("Product with ID " + _productId + " not found.");
        }
//        for (Product product : product_arrayList) {
//            if (product.getProductId().equals(_product)){
//                product_arrayList.remove(product);
//            }
//        }
//        System.out.println("Successfully deleted "+_product+" product");
    }


    public void printProduct() {
//        if (product_arrayList.isEmpty()){
//            System.out.println("\nThe store is completely empty!!!.\n");
//        }
//        else{
//            System.out.println("          List of Products:\n");
//            for (Product product : product_arrayList) {
//                displayProductInfo(product);
//            }
//
//        }
        if (product_arrayList.isEmpty()) {   // Check if the product list is empty
            System.out.println("\nThe store is completely empty!!!.\n");
        } else {
            // Sort the product_arrayList alphabetically by product ID
            product_arrayList.sort(Comparator.comparing(Product::getProductId));

            System.out.println("          List of Products (Sorted by Product ID):\n");

            // Iterate through the sorted product list and display product details
            for (Product product : product_arrayList) {
                displayProductInfo(product);
            }
        }

        //Reference : https://stackoverflow.com/questions/2839137/how-to-use-comparator-in-java-to-sort


    }

    private void displayProductInfo(Product product) {   //This method is used for printProduct() and deleteProduct() both
        if (product.getProductType().equals("Electronic")){
            System.out.println("Product Type: "+product.getProductType());
            System.out.println("Product Id  : "+product.getProductId());
            System.out.println("Product Name: "+product.getProductName());
            System.out.println("Brand       : " + ((Electronics) product).getBrand());
            System.out.println("Warranty Period(Years): " + ((Electronics) product).getWarrentyPeriod());

        }

        else if (product.getProductType().equals("Clothing")) {
            System.out.println("Product Type: "+product.getProductType());
            System.out.println("Product Id  : "+product.getProductId());
            System.out.println("Product Name: "+product.getProductName());
            System.out.println("Size: " + ((Clothing) product).getSize());
            System.out.println("Color: " + ((Clothing) product).getColor());
        }


        System.out.println("Available Items       : " + product.getNumberOfAvailableItem());
        System.out.println("Price       : " + product.getPrice());
        System.out.println("    ******************\n");

//        if(product.getProductType().equals("Electronic")){
//            System.out.println("I got this.");
//        }

        /*if (product instanceof Electronics) {
            System.out.println("Electronics - " + product.getProductName() + " (ID: " + product.getProductId() + ")");
            System.out.println("Brand: " + ((Electronics) product).getBrand());
            System.out.println("Warranty Period: " + ((Electronics) product).getWarrentyPeriod());
        } else if (product instanceof Clothing) {
            System.out.println("Clothing - " + product.getProductName() + " (ID: " + product.getProductId() + ")");
            System.out.println("Size: " + ((Clothing) product).getSize());
            System.out.println("Color: " + ((Clothing) product).getColor());
        }
        */
    }



    @Override
    public void saveProduct() {
        try {
            FileWriter myWriter = new FileWriter("product.txt");

            for (Product product : product_arrayList) {
                // Assuming each line in the file represents a serialized product
                String serializedProduct = writingProduct(product);
                myWriter.write(serializedProduct+"\n");

            }
            myWriter.close();

            System.out.println("Product list saved successfully!");


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private String writingProduct(Product product) {   //I used this method as private, because it made for only use within this class
        if (product.getProductType().equals("Electronic")) {
            return "E|" + product.getProductId() + "|" + product.getProductName() + "|" +
                    product.getNumberOfAvailableItem() + "|" + product.getPrice() + "|" +
                    ((Electronics) product).getBrand() + "|" + ((Electronics) product).getWarrentyPeriod();
        } else if (product.getProductType().equals("Clothing")) {
            return "C|" + product.getProductId() + "|" + product.getProductName() + "|" +
                    product.getNumberOfAvailableItem() + "|" + product.getPrice() + "|" +
                    ((Clothing) product).getSize() + "|" + ((Clothing) product).getColor();
        }
        return "";
    }

    public void loadProduct(){
        try {
            File file = new File("product.txt");
            Scanner file_reader= new Scanner(file);
            while (file_reader.hasNextLine()){
                String _line = file_reader.nextLine();
                Product product = textReader(_line);
                if (product!=null){
                    product_arrayList.add(product);
                }
            }
            System.out.println("The product list has been successfully loaded!");
        }catch (IOException e){
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

    }

    private Product textReader(String serializedProduct) {
        String[] tokens = serializedProduct.split("\\|");
        if (tokens.length >= 6) {
            String type = tokens[0];
            String productId = tokens[1];
            String productName = tokens[2];
            int availableItems = Integer.parseInt(tokens[3]);
            double price = Double.parseDouble(tokens[4]);

            if (type.equals("E") && tokens.length == 7) {
                String brand = tokens[5];
                int warrantyPeriod = Integer.parseInt(tokens[6]);
                return new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
            } else if (type.equals("C") && tokens.length == 7) {
                String size = tokens[5];
                return new Clothing(productId, productName, availableItems, price, size, tokens[6]);
            }
        }
        return null;
    }
    public ArrayList<Product> runGUI(){
        return product_arrayList;
    }




}
