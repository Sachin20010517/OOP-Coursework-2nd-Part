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
    private static String username;

    public UserAuthenticationGUI(){

    }
    public UserAuthenticationGUI(ArrayList<User> userList, ArrayList<Product> products) {
        this.products=products;
        this.userList = userList;
        this.authenticated = false;


        loadUserDetails(); // Load user details from the file

        setTitle("User Authentication");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //getContentPane().setBackground(Color.white);
        setResizable(false);  //prevent frame from being resized

        ImageIcon image= new ImageIcon("westminster.jpg");
        setIconImage(image.getImage());

        initializeComponents();

        JPanel northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setPreferredSize(new Dimension(400,70));
        northPanel.setBackground(Color.white);
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
        usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(150,20));

        passwordField = new JPasswordField(15);
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
        signInPanel.setBackground(Color.white);

        JPanel registerPanel= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        registerPanel.add(registerButton);
        registerPanel.setBackground(Color.white);

        JPanel usernameFieldPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernameFieldPanel.add(usernameField);
        //usernameFieldPanel.setPreferredSize(new Dimension(100,30));
        usernameFieldPanel.setBackground(Color.white);


        JPanel passwordFieldPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordFieldPanel.add(passwordField);
        passwordFieldPanel.setBackground(Color.white);


        JPanel usernameLabelPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Times New Roman",Font.BOLD,17));;
        usernameLabelPanel.add(username);
        //usernameFieldPanel.setPreferredSize(new Dimension(100,30));
        usernameLabelPanel.setBackground(Color.white);


        JPanel passwordLabelPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel password= new JLabel("Password: ");
        passwordLabelPanel.add(password);
        passwordLabelPanel.setBackground(Color.white);
        password.setFont(new Font("Times New Roman",Font.BOLD,17));



        mainPanel.add(usernameLabelPanel);
        mainPanel.add(usernameFieldPanel);
        mainPanel.add(passwordLabelPanel);
        mainPanel.add(passwordFieldPanel);
        mainPanel.add(signInPanel);
        mainPanel.add(registerPanel);

        mainPanel.setBackground(Color.white);
        containerPanel.setBackground(Color.white);
        containerPanel.add(mainPanel);

        return containerPanel;
    }

    private void signIn() {
        // Retrieve entered username and password
        username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Find the user with the entered username
        User user = findUser(username);


        // Check if the user exists and the password matches
        if (user != null && user.getPassword().equals(password)) {
            authenticated = true;
            //openShoppingGUI();
            OnlineShoppingGUI gui_1= new OnlineShoppingGUI(products);
            gui_1.openWestminsterGUI();
        } else {
            // Display an error message for invalid username or password
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
        }
    }

    public String sendUserName() {
        return username;
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


    private void loadUserDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            userList.clear(); // Clear existing user list
            String line;
            while ((line = reader.readLine()) != null) { // Read each line from the file
                String[] parts = line.split(",");
                if (parts.length == 2) {  // Ensure each line has the expected format (username,password)
                    String username = parts[0];
                    String password = parts[1];
                    // Create a User object and add it to the userList
                    userList.add(new User(username, password));
                }
            }
        } catch (FileNotFoundException e) {  // If the file is not found, it means no users are registered yet.
            System.out.println("User details file not found. Creating a new file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUserDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            // Write each user's details to a new line in the file
            for (User user : userList) {
                writer.write(user.getUserName() + "," + user.getPassword());
                writer.newLine(); // Move to the next line for the next user
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
