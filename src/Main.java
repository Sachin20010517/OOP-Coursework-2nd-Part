import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        WestminsterShoppingManager manager= new WestminsterShoppingManager();
        //OnlineShoppingGUI gui_1= new OnlineShoppingGUI();

        System.out.println("\n      ---  Welcome to the Westminster Shopping System  ---");
        Scanner input = new Scanner(System.in);


        int choice;
        do{

            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Please select an option");
            System.out.println("1) Add a Product");
            System.out.println("2) Delete a Product");
            System.out.println("3) Print the List of the Product");
            System.out.println("4) Save in a File ");
            System.out.println("5) Load the file ");
            System.out.println("6) Switch to GUI");
            System.out.println("6) Switch to GUI for users");
            System.out.println("    0) Quit");
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println();

            System.out.print("\nPlease Enter Your Option : _ _ _ _ ");
            choice=input.nextInt();

            switch (choice){

                case 1:
                    System.out.print("\nWhich product do you want to add, clothing or electronics?\nSelect (1) if it's an electronic product; if not, select (2): ");
                    int productType= input.nextInt();
                    if (productType==1){
                        Electronics electronic = addElectronics();
                        manager.addProduct(electronic);
                    }

                    else if (productType==2){
                        Clothing clothes = addClothes();
                        manager.addProduct(clothes);
                    }
                    else {
                        System.out.println("Please Enter Valid Number\n");
                    }
                    break;

                case 2:
                    System.out.print("\nType in the product ID to be removed :");
                    String productID_to_delete = input.next();
                    manager.deleteProduct(productID_to_delete);
                    break;

                case 3:
                    manager.printProduct();
                    break;

                case 4:
                    manager.saveProduct();
                    break;

                case 5:
                    manager.loadProduct();

                    break;
                case 6:
                    ArrayList<Product> arrayList=manager.runGUI();
                    OnlineShoppingGUI gui_1= new OnlineShoppingGUI(arrayList);
                    gui_1.openWestminsterGUI();
                    break;
                case 7:
                    ArrayList<User> userList = new ArrayList<>(); // List to hold user details
                    UserAuthenticationGUI userAuthGUI = new UserAuthenticationGUI(userList);
                    userAuthGUI.setVisible(true);
                    break;
                case 0:
                    System.out.println("Getting Out of the Online Store. Goodbye!");
                    break;

                default:
                    System.out.println("Not a valid option. Please make sure you are selecting a valid option.\n");

            }

        }while(choice!=0);


//        OnlineShoppingGUI newGUI = new OnlineShoppingGUI();
//        newGUI.setTitle("Westminster Shopping Center");
//        newGUI.setSize(800,600);
//        newGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        newGUI.setVisible(true);


    }



    private static Clothing addClothes() {
        System.out.println("\nEnter Clothing Details:");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Product ID: ");
        String productId = scanner.nextLine();

        System.out.print("Product Name: ");
        String productName = scanner.nextLine();

        System.out.print("Available Items: ");
        int availableItems = scanner.nextInt();

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character .This helps avoid errors when reading the string input later on.

        System.out.print("Size: ");
        String size = scanner.next();
        scanner.nextLine(); // Consume the newline character .This helps avoid errors when reading the string input later on.

        System.out.print("Color: ");
        String color = scanner.next();

        return new Clothing(productId, productName, availableItems, price,color,size);

    }

    private static Electronics addElectronics() {
        System.out.println("\nEnter Electronics Details:");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Product ID: ");
        String productId = scanner.nextLine();

        System.out.print("Product Name: ");
        String productName = scanner.nextLine();

        System.out.print("Available Items: ");
        int availableItems = scanner.nextInt();

//        System.out.print("Price: ");
//        double price = scanner.nextDouble();
//        scanner.nextLine(); // Consume the newline character .This helps avoid errors when reading the string input later on.



        double price = 0;
        boolean validPrice = false;

        while (!validPrice) {
            try {
                System.out.print("Price: ");
                price = Double.parseDouble(scanner.next());  // Attempt to parse the input as a double
                validPrice = true;   // If parsing is successful, set the flag to true to exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number for the price.");
            }
        }
        scanner.nextLine(); // Consume the newline character .This helps avoid errors when reading the string input later on.


        System.out.print("Brand: ");
        String brand = scanner.nextLine();

        System.out.print("Warranty Period(Years): ");
        int warrantyPeriod = scanner.nextInt();

        return new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
    }
}