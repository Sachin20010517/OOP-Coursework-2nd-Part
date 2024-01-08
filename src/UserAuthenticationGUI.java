import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class UserAuthenticationGUI extends JFrame {
    private static final String USER_FILE_PATH = "user_details.txt";

    private ArrayList<User> userList;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton registerButton;

    private boolean authenticated;
    private ArrayList<Product> products;
    private ShoppingCart shoppingCart;

    public UserAuthenticationGUI(ArrayList<User> userList, ArrayList<Product> products) {
        this.products=products;
        this.userList = userList;
        this.authenticated = false;
        this.shoppingCart = new ShoppingCart();

        loadUserDetails(); // Load user details from the file

        setTitle("User Authentication");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();

        JPanel northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setPreferredSize(new Dimension(400,50));
        JLabel label_1=new JLabel();
        label_1.setText("Welcome!");
        label_1.setForeground(new Color(255, 0, 0));
        label_1.setFont(new Font("Times New Roman",Font.BOLD,30));
        northPanel.add(label_1);


        setLayout(new BorderLayout());
        add(createContainerPanel(), BorderLayout.CENTER);
        add(northPanel,BorderLayout.NORTH);

        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }

    private void initializeComponents() {
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        signInButton = new JButton("Sign In");
        signInButton.setBackground(Color.lightGray);
        signInButton.setFont(new Font("Times New Roman",Font.BOLD,20));
        registerButton = new JButton("Register");
        registerButton.setBackground(Color.lightGray);
        registerButton.setFont(new Font("Times New Roman",Font.BOLD,20));

        // Add ActionListeners to the buttons to handle button click events
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signIn();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
    }

    private JPanel createContainerPanel() {

        JPanel containerPanel=new JPanel();
        containerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //containerPanel.setPreferredSize(new Dimension(400,200));


        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(400,250));
        mainPanel.setLayout(new GridLayout(3, 2, 10, 30));

        JPanel signInPanel=new JPanel();
        signInPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        signInPanel.add(signInButton);

        JPanel registerPanel= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        registerPanel.add(registerButton);

        mainPanel.add(new JLabel("Username:"));
        mainPanel.add(usernameField);
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);
        mainPanel.add(signInPanel);
        mainPanel.add(registerPanel);

        mainPanel.setBackground(Color.GRAY);
        containerPanel.setBackground(Color.ORANGE);
        containerPanel.add(mainPanel);

        return containerPanel;
    }

    private void signIn() {
        // Retrieve entered username and password
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Find the user with the entered username
        User user = findUser(username);

        // Check if the user exists and the password matches
        if (user != null && user.getPassword().equals(password)) {
            if (user.getPurchaseHistory().isEmpty()) {
                // Apply 10% discount for the first purchase
                applyFirstPurchaseDiscount(user);
            }
            authenticated = true;
            //openShoppingGUI();
            OnlineShoppingGUI gui_1= new OnlineShoppingGUI(products);
            gui_1.openWestminsterGUI();
        } else {
            // Display an error message for invalid username or password
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check for empty fields
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.");
            return;
        }

        // Check if the username already exists
        if (findUser(username) != null) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.");
            return;
        }

        // Create a new User object
        User newUser = new User(username, password);
        userList.add(newUser); //add User object  to the userList Arraylist
        saveUserDetails(); // Save user details to the file

        JOptionPane.showMessageDialog(this, "Registration successful. You can now sign in.");
    }

    private User findUser(String username) {
        // Iterate through the userList to find a user with the given username
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                return user;  // Return the user if found
            }
        }
        return null; // Return null if the user is not found
    }

//    private void openShoppingGUI() {
//        SwingUtilities.invokeLater(() -> {
//            OnlineShoppingGUI shoppingGUI = new OnlineShoppingGUI(new ArrayList<>());
//            shoppingGUI.openWestminsterGUI();
//            dispose();
//        });
//    }

//    private void loadUserDetails() {
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USER_FILE_PATH))) {
//            userList = (ArrayList<User>) objectInputStream.readObject();
//        } catch (FileNotFoundException e) {
//            System.out.println("User details file not found. Creating a new file.");
//            userList = new ArrayList<>();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void saveUserDetails() {
//        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE_PATH))) {
//            objectOutputStream.writeObject(userList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void loadUserDetails() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
//            userList.clear(); // Clear existing user list
//            String line;
//            while ((line = reader.readLine()) != null) { // Read each line from the file
//                String[] parts = line.split(",");
//                if (parts.length == 2) {  // Ensure each line has the expected format (username,password)
//                    String username = parts[0];
//                    String password = parts[1];
//                    // Create a User object and add it to the userList
//                    userList.add(new User(username, password));
//                }
//            }
//        } catch (FileNotFoundException e) {  // If the file is not found, it means no users are registered yet.
//            System.out.println("User details file not found. Creating a new file.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void saveUserDetails() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
//            // Write each user's details to a new line in the file
//            for (User user : userList) {
//                writer.write(user.getUserName() + "," + user.getPassword());
//                writer.newLine(); // Move to the next line for the next user
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//private void saveUserDetails() {
//    try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
//        // Write each user's details to a new line in the file
//        for (User user : userList) {
//            writer.write(user.getUserName() + "," + user.getPassword() + "," + user.isFirstPurchase());
//
//            // If user has a purchase history, append it to the line
//            if (!user.getPurchaseHistory().isEmpty()) {
//                writer.write(",[");
//                for (Product product : user.getPurchaseHistory()) {
//                    writer.write(product.getProductId() + ",");
//                }
//                writer.write("]");
//            }
//
//            writer.newLine(); // Move to the next line for the next user
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}

    private void saveUserDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            for (User user : userList) {
                // Save username, password, first purchase status, and purchase history
                writer.write(user.getUserName() + "," + user.getPassword() + "," +
                        user.isFirstPurchase() + "," + user.getPurchaseHistoryAsString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadUserDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            userList.clear(); // Clear existing user list
            String line;
            while ((line = reader.readLine()) != null) { // Read each line from the file
                String[] parts = line.split(",");
                if (parts.length == 3) {  // Ensure each line has the expected format (username,password,isFirstPurchase)
                    String username = parts[0];
                    String password = parts[1];
                    boolean isFirstPurchase = Boolean.parseBoolean(parts[2]);
                    // Create a User object and add it to the userList
                    User user = new User(username, password);
                    user.setFirstPurchase(isFirstPurchase);
                    userList.add(user);
                }
            }
        } catch (FileNotFoundException e) {  // If the file is not found, it means no users are registered yet.
            System.out.println("User details file not found. Creating a new file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




//    private void applyFirstPurchaseDiscount(User user) {29
//        if (user.isFirstPurchase()) {
//            double totalCost = calculateTotalCost(); // Replace with your logic to calculate the total cost
//            double discount = totalCost * 0.10; // 10% discount
//            // Update the total cost or shopping cart with the discount
//            // ...
//
//            JOptionPane.showMessageDialog(this, "Congratulations! You've received a 10% discount on your first purchase.");
//
//            // Add the purchased products to the user's purchase history
//            user.getPurchaseHistory().addAll(products);
//
//            // Update user's first purchase status
//            user.setFirstPurchase(false);
//            saveUserDetails();
//        } else {
//            JOptionPane.showMessageDialog(this, "Welcome back! You've already received the first purchase discount.");
//        }
//    }

    private void applyFirstPurchaseDiscount(User user) {
        if (user.isFirstPurchase()) {
            // Existing code...

            // Add the purchased products to the user's purchase history
            for (CartItem cartItem : shoppingCart.getCartItems()) {
                Product product = cartItem.getProduct();
                user.addProductToPurchaseHistory(product);
            }

            // Update user's first purchase status
            user.setFirstPurchase(false);
            saveUserDetails();
        } else {
            // Existing code...
        }
    }




    private double calculateTotalCost() {
        // Replace with your logic to calculate the total cost
        return 0.0; // Placeholder value, update with your calculation
    }





    public boolean isAuthenticated() {
        return authenticated;
    }

//    public static void main(String[] args) {
//        new UserAuthenticationGUI(new ArrayList<>());
//    }
}