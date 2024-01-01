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

    public UserAuthenticationGUI(ArrayList<User> userList, ArrayList<Product> products) {
        this.products=products;
        this.userList = userList;
        this.authenticated = false;

        loadUserDetails(); // Load user details from the file

        setTitle("User Authentication");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();

        JPanel northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setPreferredSize(new Dimension(500,100));
        JLabel label_1=new JLabel();
        label_1.setText("Welcome to Westminster Shopping Center");
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
        registerButton = new JButton("Register");

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
        containerPanel.setPreferredSize(new Dimension(400,200));


        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(300,200));
        mainPanel.setLayout(new GridLayout(3, 2, 10, 30));

        mainPanel.add(new JLabel("Username:"));
        mainPanel.add(usernameField);
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);
        mainPanel.add(signInButton);
        mainPanel.add(registerButton);

        mainPanel.setBackground(Color.GRAY);
        containerPanel.setBackground(Color.ORANGE);
        containerPanel.add(mainPanel);

        return containerPanel;
    }

    private void signIn() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = findUser(username);

        if (user != null && user.getPassword().equals(password)) {
            authenticated = true;
            //openShoppingGUI();
            OnlineShoppingGUI gui_1= new OnlineShoppingGUI(products);
            gui_1.openWestminsterGUI();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.");
            return;
        }

        if (findUser(username) != null) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.");
            return;
        }

        User newUser = new User(username, password);
        userList.add(newUser);
        saveUserDetails(); // Save user details to the file

        JOptionPane.showMessageDialog(this, "Registration successful. You can now sign in.");
    }

    private User findUser(String username) {
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

//    private void openShoppingGUI() {
//        SwingUtilities.invokeLater(() -> {
//            OnlineShoppingGUI shoppingGUI = new OnlineShoppingGUI(new ArrayList<>());
//            shoppingGUI.openWestminsterGUI();
//            dispose();
//        });
//    }

    private void loadUserDetails() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USER_FILE_PATH))) {
            userList = (ArrayList<User>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("User details file not found. Creating a new file.");
            userList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveUserDetails() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE_PATH))) {
            objectOutputStream.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

//    public static void main(String[] args) {
//        new UserAuthenticationGUI(new ArrayList<>());
//    }
}
