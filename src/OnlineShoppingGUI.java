import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class OnlineShoppingGUI extends JFrame {

    private ArrayList<Product> productList;
    private JTable productTable;
    private JTextArea productTextArea;

    private JButton shoppingCartButton;

    private JComboBox<String> productTypeComboBox;

    private JButton addToCartButton;
    private JButton sortButton;
    private JTable cartTable;
    private JLabel totalCostLabel;
    private JLabel threeItemDiscountLabel;
    private JLabel totalWithDiscountLabel;
    private JLabel firstPurchaseDiscountLabel;




    public OnlineShoppingGUI(ArrayList<Product> productList) {
        this.productList = productList;
        setTitle("          Westminster Shopping Centre");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        setResizable(false);  //prevent frame from being resized

        ImageIcon image= new ImageIcon("westminster.jpg");
        setIconImage(image.getImage());

        // Initialize components

        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.setFont(new Font("Times New Roman",Font.BOLD,20));

        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronic", "Clothing"}); // Drop-down menu for product categories

        MyTableModel myTableModel = new MyTableModel(productList);
        productTable = new JTable(myTableModel);



        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBackground(Color.CYAN);

        //AddToCartButton
        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.setFont(new Font("Times New Roman",Font.HANGING_BASELINE,17));
        addToCartButton.setBackground(new Color(0,76,153));
        addToCartButton.setForeground(Color.WHITE);  // Set the font color to white
        addToCartButton.setPreferredSize(new Dimension(200, 30));


        productTextArea = new JTextArea();

        //Sort Button
        sortButton = new JButton("Sort");
        sortButton.setBackground(Color.BLACK);
        sortButton.setForeground(Color.WHITE);
        sortButton.setFont(new Font("Times New Roman",Font.BOLD,17));

        // Set up layout of the JFrame
        setLayout(new FlowLayout());


        //Set up a new panel
        JPanel topButtonPanel = new JPanel();             //topButtonPanel has been included shoppingCartBtnPanel
        topButtonPanel.setPreferredSize(new Dimension(700, 75));
        topButtonPanel.setLayout(new BorderLayout());
        topButtonPanel.setBackground(Color.white);
        //topButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,30,30));
        topButtonPanel.setLayout(new BorderLayout());

        JPanel shoppingCartBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  //For moving right "'Shopping cart' button
        shoppingCartBtnPanel.add(shoppingCartButton);
        shoppingCartBtnPanel.setBackground(Color.WHITE);
        shoppingCartButton.setBackground(Color.GREEN);
        topButtonPanel.add(shoppingCartBtnPanel, BorderLayout.NORTH);

        JPanel dropDownPanel = new JPanel();  //For moving center to drop-down menu
        dropDownPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dropDownPanel.add(new JLabel("Select Product Category :  "));
        dropDownPanel.setBackground(Color.WHITE);
        dropDownPanel.add(productTypeComboBox);
        topButtonPanel.add(dropDownPanel, BorderLayout.CENTER);


        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        scrollPane.setPreferredSize(new Dimension(700, 80));
        middlePanel.setPreferredSize(new Dimension(800, 100));
        scrollPane.setBackground(Color.WHITE);
        middlePanel.add(scrollPane);


        JPanel sortBtnPanel = new JPanel(new BorderLayout());
        sortBtnPanel.setPreferredSize(new Dimension(700, 30));
        sortBtnPanel.setBackground(Color.WHITE);
        sortBtnPanel.add(sortButton, BorderLayout.WEST);


        JPanel bottomPanel = new JPanel();//bottomPannel has been included TextArea and addToCart button
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  //This is not necessary. Because  FlowLayout is the default layout manager for every JPanel
        bottomPanel.setPreferredSize(new Dimension(800, 300));
        bottomPanel.setBackground(Color.WHITE);
        productTextArea.setPreferredSize(new Dimension(700, 250));
        productTextArea.setBackground(Color.LIGHT_GRAY);

        bottomPanel.add(productTextArea);
        bottomPanel.add(addToCartButton);


        //Adding vertical line to the GUI
        JSeparator separator=new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(800, 20));
        separator.setBackground(Color.black);

        add(topButtonPanel);
        add(middlePanel);
        add(sortBtnPanel);
        // Add an empty space between sortButton and separator
        add(Box.createRigidArea(new Dimension(0, 60)));
        add(separator);
        add(new JLabel("Select Product Details")); //Making & adding label for 'Select Product Details'
        add(bottomPanel);

        UserAuthenticationGUI  userName= new UserAuthenticationGUI();
        MouseHandler mouseHandler = new MouseHandler(userName.sendUserName());
        shoppingCartButton.addMouseListener(mouseHandler);
        productTable.addMouseListener(mouseHandler);
        addToCartButton.addMouseListener(mouseHandler);
        sortButton.addMouseListener(mouseHandler);


        // After initializing the components
//        shoppingCartButton.setFocusable(true);
//        productTable.setFocusable(true);
//        addToCartButton.setFocusable(true);

        KeyHandlerclass keyHandler = new KeyHandlerclass();

        shoppingCartButton.addKeyListener(keyHandler);
        productTable.addKeyListener(keyHandler);
        addToCartButton.addKeyListener(keyHandler);


        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
    }

    private void updateTable() {
        // Get the selected category from the combo box
        String selectedCategory = (String) productTypeComboBox.getSelectedItem();
        System.out.println("Selected Category: " + selectedCategory);

        // Create a list to store filtered products based on the selected category
        ArrayList<Product> filteredProducts = new ArrayList<>();

        // If "All" is selected, add all products to the filtered list
        if ("All".equals(selectedCategory)) {
            filteredProducts.addAll(productList);
        } else {
            // Filter the products based on the selected category
            filteredProducts.addAll(
                    productList.stream()
                            .filter(product -> selectedCategory.equals(product.getProductType()))
                            .collect(Collectors.toList())
            );
        }

        // Create a new table model with the filtered products and set it to the product table
        MyTableModel newModel = new MyTableModel(filteredProducts);
        productTable.setModel(newModel);

        // Set a custom cell renderer to highlight items with less than 3 available
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Check if the "Items Available" column is being rendered
                if (column == 4 && value instanceof String) {
                    String itemsAvailableString = (String) value;
                    int itemsAvailable = Integer.parseInt(itemsAvailableString);

                    if (itemsAvailable < 3) {
                        // If less than 3 items available, set the background color to red
                        cellComponent.setBackground(Color.RED);
                    } else {
                        // Otherwise, use the default background color
                        cellComponent.setBackground(table.getBackground());
                    }
                } else {
                    // For other columns, use the default background color
                    cellComponent.setBackground(table.getBackground());
                }

                return cellComponent;
            }
        });
    }

    private void sortTableByProductId() {
        MyTableModel model = (MyTableModel) productTable.getModel();
        model.sortByProductId();
    }


    private class MouseHandler extends ShoppingCart implements MouseListener, MouseMotionListener {
        private final String customerName;
        private static double discount;
        public MouseHandler(String customerName) {
            this.customerName = customerName;
        }
        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getSource() == productTable) {
                // Get the selected row index in the product table
                int selectedRow = productTable.getSelectedRow();
                Product selectedProduct;

                // Check the selected category in the combo box
                String selectedCategory = (String) productTypeComboBox.getSelectedItem();

                // If "All" is selected, get the product directly from the original list
                if ("All".equals(selectedCategory)) {
                    selectedProduct = productList.get(selectedRow);
                } else {
                    // Filter the list based on the selected category
                    ArrayList<Product> filteredList = (ArrayList<Product>) productList.stream()
                            .filter(product -> selectedCategory.equals(product.getProductType()))
                            .collect(Collectors.toList());
                    // Get the product from the filtered list based on the selected row
                    selectedProduct = filteredList.get(selectedRow);
                }

                // Display details in productTextArea
                productTextArea.setText(
                        "\n     Product ID: " + selectedProduct.getProductId() + "\n\n" +
                                "     Category  : " + selectedProduct.getProductType() + "\n\n" +
                                "     Name      : " + selectedProduct.getProductName() + "\n\n" +
                                ((selectedProduct instanceof Clothing) ? "     Size           : " + ((Clothing) selectedProduct).getSize() + "\n\n" : "") +
                                ((selectedProduct instanceof Clothing) ? "     Product Color  : " + ((Clothing) selectedProduct).getColor() + "\n\n" : "") +
                                ((selectedProduct instanceof Electronics) ? "     Product Brand  : " + ((Electronics) selectedProduct).getBrand() + "\n\n" : "") +
                                ((selectedProduct instanceof Electronics) ? "    Warranty Period: " + ((Electronics) selectedProduct).getWarrantyPeriod() + "\n\n" : "") +
                                "     Items Available: " + selectedProduct.getNumberOfAvailableItem() + "\n\n"
                );

            } else if (e.getSource() == shoppingCartButton) {
                shoppingCartButton.setBackground(Color.RED);
                JFrame newFrame = new JFrame("Shopping Cart");
                newFrame.setResizable(false);  //prevent frame from being resized

                ImageIcon image= new ImageIcon("westminster.jpg");
                newFrame.setIconImage(image.getImage());

                newFrame.setSize(600, 500);
                newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                setLayout(new FlowLayout(FlowLayout.CENTER));
                newFrame.getContentPane().setBackground(Color.white);



                JPanel containerPanel = new JPanel();
                containerPanel.setLayout(new FlowLayout());
                containerPanel.setPreferredSize(new Dimension(600, 450));
                containerPanel.setBackground(Color.white);

                JPanel panel_1 = new JPanel();
                panel_1.setPreferredSize(new Dimension(500, 150));
                panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); //(FlowLayout.CENTER,0,40) was used for making a vertical gap from head
                panel_1.setBackground(Color.white);

                JPanel panel_2 = new JPanel();
                panel_2.setLayout(new GridLayout(4, 2, 20, 20));
                panel_2.setPreferredSize(new Dimension(400, 150));
                panel_2.setBackground(Color.white);

                //JTextArea cartTextArea = new JTextArea();
                //cartTextArea.setBackground(Color.ORANGE);
                //cartTextArea.setPreferredSize(new Dimension(50,5));
                //cartTextArea.setEditable(false);
                //cartTextArea.setText(String.valueOf(calculateTotalCost()));  //  // Update cartTextArea with the total cost


                //JScrollPane cartScrollPane = new JScrollPane(cartTextArea);


//                CartItem cart = new ShoppingCart();
//                cart.setShoppingCartList(getShoppingCartList());

                //cartTextArea.setText(cart.calculateTotalCost() + "\n\n");
                //cart.printProduct();

                cartTable = new JTable(getCartTableModel());
                cartTable.setEnabled(false);

                JScrollPane scrollPane = new JScrollPane(cartTable);
                scrollPane.setPreferredSize(new Dimension(500, 100));
                scrollPane.setBackground(Color.white);


                //newFrame.add(cartScrollPane);

                panel_1.add(scrollPane);

//                panel_2.add(new JLabel("Total"));
//                panel_2.add(cartTextArea);
//                panel_2.add(new JLabel("First Purchase Discount(10%)"));
//                panel_2.add(new JLabel("Final Total"));

                JPanel newPanel1 = new JPanel();
                newPanel1.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel1.setPreferredSize(new Dimension(20,3));
                newPanel1.add(new JLabel("Total"));
                newPanel1.setBackground(Color.white);

                JPanel newPanel2 = new JPanel();
                newPanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
                newPanel2.setBackground(Color.white);


                double totalCost = calculateTotalCostFromTable(getCartTableModel());
                totalCostLabel= new JLabel();
                totalCostLabel.setText(""+ totalCost + " 0£");
                newPanel2.add(totalCostLabel);

                JPanel newPanel3 = new JPanel();
                newPanel3.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel3.setPreferredSize(new Dimension(20,3));
                newPanel3.add(new JLabel("First Purchase Discount(10%)"));
                newPanel3.setBackground(Color.white);




                JPanel newPanel4 = new JPanel();
                newPanel4.setLayout(new FlowLayout(FlowLayout.LEADING));
                newPanel4.setBackground(Color.white);




                 //Check if it's the customer's first purchase
                UserAuthenticationGUI currentUser =new UserAuthenticationGUI();
                String customerName = currentUser.sendUserName();
                boolean isFirstPurchase = isCustomerFirstPurchase(customerName);

                if (isFirstPurchase) {
                    // Apply the 10% bonus for the first purchase
                    discount += totalCost * 0.10;
//                    newPanel4.add(new JLabel("-" + discount +"£"));
                    // Save the cart information to a text file
                    //saveCartToFile();
                }
                else {
                    //saveCartToFile();
                    discount=0;
                }

                firstPurchaseDiscountLabel=new JLabel(""+discount+"0.00£");
                newPanel4.add(firstPurchaseDiscountLabel);






                JPanel newPanel5 = new JPanel();
                newPanel5.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel3.setPreferredSize(new Dimension(20,3));
                newPanel5.add(new JLabel("Three items in same Category Discount(20%)"));
                newPanel5.setBackground(Color.white);


                JPanel newPanel6 = new JPanel();
                newPanel6.setLayout(new FlowLayout(FlowLayout.LEADING));
                newPanel6.setBackground(Color.white);


                //double three_item_discount = totalCost * 0.20;

                threeItemDiscountLabel=new JLabel("0.00£");
                newPanel6.add(threeItemDiscountLabel);
//
//                if (getClothingQuantity() >= 3 || getElectronicQuantity() >= 3) {
//                    newPanel6.add(new JLabel("-" + three_item_discount + "£"));
//                }
//                else {
//                    newPanel6.add(new JLabel("-0.00£"));
//                }


                JPanel newPanel7 = new JPanel();
                newPanel7.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel3.setPreferredSize(new Dimension(20,3));
                newPanel7.add(new JLabel("Final Total"));
                newPanel7.setBackground(Color.white);


                JPanel newPanel8 = new JPanel(new FlowLayout(FlowLayout.LEADING));
                totalWithDiscountLabel=new JLabel(String.valueOf(calculateTotalCost()));
                newPanel8.add(totalWithDiscountLabel);
                newPanel8.setBackground(Color.white);



                addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Save cart details when the cart frame is closed
                        saveCartToFile();
                    }
                });



//                double total_1 = totalCost - three_item_discount-discount;
//                double total_2 = totalCost-discount;
//                double total_3 = totalCost-three_item_discount;



// Calculate total with discounts
                //double totalWithDiscounts = totalCost - threeItemDiscount - firstPurchaseDiscount;
//
//                newPanel8.setLayout(new FlowLayout(FlowLayout.LEADING));
//                if ((getClothingQuantity() >= 3 || getElectronicQuantity() >= 3) && isFirstPurchase) {
//                    newPanel8.add(new JLabel(total_1 + "£"));
//                }
//                else if ((getClothingQuantity() >= 3 || getElectronicQuantity() >= 3) && !(isFirstPurchase) ) {
//                    newPanel8.add(new JLabel(total_3 + "£"));
//                }
////                else if (isFirstPurchase) {
////                    newPanel8.add(new JLabel(total_2 + "£"));
////                }
//                else if ((getClothingQuantity() < 3 || getElectronicQuantity() < 3) && isFirstPurchase) {
//                    newPanel8.add(new JLabel(total_2 + "£"));
//                    //System.out.println(getElectronicQuantity()+" , "+getClothingQuantity());
//                }
//                else {
//                    newPanel8.add(new JLabel(calculateTotalCost() + "£"));
//                }


                panel_2.add(newPanel1);
                panel_2.add(newPanel2);
                panel_2.add(newPanel3);
                panel_2.add(newPanel4);
                panel_2.add(newPanel5);
                panel_2.add(newPanel6);
                panel_2.add(newPanel7);
                panel_2.add(newPanel8);


                containerPanel.add(panel_1);
                containerPanel.add(panel_2);

                newFrame.add(containerPanel);


                newFrame.setVisible(true);


            }



            else if (e.getSource() == addToCartButton) {
                // Get the selected category from the combo box
                String selectedCategory = (String) productTypeComboBox.getSelectedItem();

                // Get the selected row in the product table
                int selectedRow = productTable.getSelectedRow();

                if (selectedRow >= 0) {
                    // Get the selected product directly from the filtered list
                    ArrayList<Product> filteredProducts = getFilteredProducts(selectedCategory);

                    if (selectedRow < filteredProducts.size()) {
                        Product selectedProductFromList = filteredProducts.get(selectedRow);

                        // Check if the number of available items is greater than 0 before adding to cart
                        if (selectedProductFromList.getNumberOfAvailableItem() > 0) {
                            // Decrease the number of available items
                            selectedProductFromList.setNumberOfAvailableItem(selectedProductFromList.getNumberOfAvailableItem() - 1);

                            // Update productTextArea with the added product
                            updateProductTextArea(selectedProductFromList);

                            // Add the selected product to the shopping cart
                            UserAuthenticationGUI currentUser = new UserAuthenticationGUI();
                            CartItem sel_product = new CartItem(selectedProductFromList, currentUser.sendUserName());
                            //System.out.println(currentUser.sendUserName() + ", " + sel_product.getProduct());

                            addProduct(selectedProductFromList, currentUser.sendUserName());

//                            cartTable.setModel(getCartTableModel());
                            // Initialize cartTable if it is null
                            if (cartTable == null) {
                                cartTable = new JTable();
                            }

                            // Update cartTable
                            cartTable.setModel(getCartTableModel());



                            // Refresh the displayed total cost
                            updateTotalCostLabel(getClothingQuantity(),getElectronicQuantity(),currentUser.sendUserName());

                            //update purchase history
                            //saveCartToFile();

                        }  else {
                            // Display an error message if the number of available items is 0
                            JOptionPane.showMessageDialog((Component) e.getSource(),
                                    "Error: No available items for '" + selectedProductFromList.getProductName()+"' at this moment.",
                                    "    Out of Stock!! ", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }





            else if (e.getSource() == sortButton) {
                sortTableByProductId();
            }


            //Table


        }

        private void saveCartToFile() {
            String fileName = "cart.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                DefaultTableModel cartTableModel = (DefaultTableModel) cartTable.getModel();

                for (int row = 0; row < cartTableModel.getRowCount(); row++) {
                    String _Product = (String) cartTableModel.getValueAt(row, 0);
                    String productName = (String) cartTableModel.getValueAt(row, 1);
                    int quantity = (int) cartTableModel.getValueAt(row, 2);
                    double totalPrice = (double) cartTableModel.getValueAt(row, 3);

//                    writer.println("Customer: " + customerName);
//                    writer.println("Product: " + productName);
//                    writer.println("Quantity: " + quantity);
//                    writer.println("Total Price: " + totalPrice);
//                    writer.println("--------------------");

                    writer.println("Customer: " + customerName);
                    writer.println("Product: " + _Product);
                    writer.println("Quantity: " + quantity);
                    writer.println("Total Price: " + totalPrice);
                    writer.println("--------------------");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Error saving shopping cart to " + fileName);
            }
        }


        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }


    public void openWestminsterGUI() {
        // Initialize and display the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Set the GUI visibility to true, making it visible to the user
            //SwingUtilities.invokeLater(() -> {...}: This code ensures that the GUI is constructed and modified on the Event Dispatch Thread (EDT), which is the thread responsible for handling Swing components. It prevents potential thread-safety issues in Swing.
            setVisible(true);
        });
    }

    // Add a method to get the filtered products based on the selected category
//    private ArrayList<Product> getFilteredProducts(String selectedCategory) {
//        ArrayList<Product> filteredProducts = new ArrayList<>();
//
//        for (Product product : productList) {
//            if ("All".equals(selectedCategory) || product.getProductType().equals(selectedCategory)) {
//                filteredProducts.add(product);
//            }
//        }
//
//        return filteredProducts;
//    }
    // Modify the getFilteredProducts method to consider the sorting order
    // Modify the getFilteredProducts method to consider the sorting order
    private ArrayList<Product> getFilteredProducts(String selectedCategory) {
        ArrayList<Product> filteredProducts = new ArrayList<>();

        for (Product product : productList) {
            if ("All".equals(selectedCategory) || product.getProductType().equals(selectedCategory)) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }

    public class KeyHandlerclass extends ShoppingCart implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // Do nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                handleEnterKey();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Do nothing
        }

        private void handleEnterKey() {

            if (productTable.isFocusOwner()) {
                // Get the selected row in the product table
                int selectedRow = productTable.getSelectedRow();

                // Check the selected category in the combo box
                String selectedCategory = (String) productTypeComboBox.getSelectedItem();

                // If "All" is selected, get the product directly from the original list
                Product selectedProduct;
                if ("All".equals(selectedCategory)) {
                    selectedProduct = productList.get(selectedRow);
                } else {
                    // Filter the list based on the selected category
                    ArrayList<Product> filteredList = (ArrayList<Product>) productList.stream()
                            .filter(product -> selectedCategory.equals(product.getProductType()))
                            .collect(Collectors.toList());
                    // Get the product from the filtered list based on the selected row
                    selectedProduct = filteredList.get(selectedRow);
                }

                // Display details in productTextArea
                productTextArea.setText(
                        "\nProduct ID: " + selectedProduct.getProductId() + "\n\n" +
                                "Category  : " + selectedProduct.getProductType() + "\n\n" +
                                "Name      : " + selectedProduct.getProductName() + "\n\n" +
                                ((selectedProduct instanceof Clothing) ? "Size           : " + ((Clothing) selectedProduct).getSize() + "\n\n" : "") +
                                ((selectedProduct instanceof Clothing) ? "Product Color  : " + ((Clothing) selectedProduct).getColor() + "\n\n" : "") +
                                ((selectedProduct instanceof Electronics) ? "Product Brand  : " + ((Electronics) selectedProduct).getBrand() + "\n\n" : "") +
                                ((selectedProduct instanceof Electronics) ? "Warranty Period: " + ((Electronics) selectedProduct).getWarrantyPeriod() + "\n\n" : "") +
                                "Items Available: " + selectedProduct.getNumberOfAvailableItem() + "\n\n"
                );
            }


        }

    }
    private boolean isCustomerFirstPurchase(String customerName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Customer: " + customerName)) {
                    // Customer name already exists in the file
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Customer name doesn't exist in the file
        return true;
    }

    private void updateProductTextArea(Product addedProduct) {
        // Clear existing content in productTextArea
        productTextArea.setText("");

        // Append the information of the added product to the productTextArea
        productTextArea.append(
                "\n     Product ID: " + addedProduct.getProductId() + "\n\n" +
                        "     Category  : " + addedProduct.getProductType() + "\n\n" +
                        "     Name      : " + addedProduct.getProductName() + "\n\n" +
                        ((addedProduct instanceof Clothing) ? "     Size           : " + ((Clothing) addedProduct).getSize() + "\n\n" : "") +
                        ((addedProduct instanceof Clothing) ? "     Product Color  : " + ((Clothing) addedProduct).getColor() + "\n\n" : "") +
                        ((addedProduct instanceof Electronics) ? "     Product Brand  : " + ((Electronics) addedProduct).getBrand() + "\n\n" : "") +
                        ((addedProduct instanceof Electronics) ? "    Warranty Period: " + ((Electronics) addedProduct).getWarrantyPeriod() + "\n\n" : "") +
                        "     Items Available: " + addedProduct.getNumberOfAvailableItem() + "\n\n"
        );
    }

    public double calculateTotalCostFromTable(DefaultTableModel cartTableModel) {
        double totalCost = 0;

        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            int quantity = (int) cartTableModel.getValueAt(i, 2); // Assuming Quantity column is at index 2
            double price = (double) cartTableModel.getValueAt(i, 3); // Assuming Price column is at index 3
            totalCost += quantity * price;
        }

        return totalCost;
    }
    private void updateTotalCostLabel(int clothingQuantity, int electronicQuantity,String customerName) {
        // Check if totalCostLabel is null before updating its text
        final boolean isFirstPurchase = isCustomerFirstPurchase(customerName);
        double totalCost = calculateTotalCostFromTable((DefaultTableModel) cartTable.getModel());
        if (totalCostLabel != null) {
            totalCostLabel.setText("" + totalCost + " £");
        }
        if (firstPurchaseDiscountLabel != null) {
            if (isFirstPurchase){
                firstPurchaseDiscountLabel.setText("" + totalCost*0.10 + " £");
            }
            else {
                firstPurchaseDiscountLabel.setText("0.00£");
            }

        }
        // Update three item discount label
        double threeItemDiscount = (clothingQuantity >= 3 || electronicQuantity >= 3) ? totalCost * 0.20 : 0.0;
        if (threeItemDiscountLabel != null) {
            threeItemDiscountLabel.setText(String.format("-%.2f £", threeItemDiscount));
        }
        //Updating totalWithDiscounts label
        if (isFirstPurchase && (clothingQuantity>=3 || electronicQuantity>=3)){
            if (totalWithDiscountLabel != null) {
                totalWithDiscountLabel.setText(""+(totalCost-threeItemDiscount-totalCost*0.10));
            }
        }
        else if (!(isFirstPurchase) && (clothingQuantity>=3 ||electronicQuantity>=3)) {

            if (totalWithDiscountLabel != null) {
                totalWithDiscountLabel.setText(""+(totalCost-threeItemDiscount));
            }
        }
        else if (isFirstPurchase && (clothingQuantity<3 && electronicQuantity<3)) {

            if (totalWithDiscountLabel != null) {
                totalWithDiscountLabel.setText(""+(totalCost-totalCost*0.10));
            }
        }
        else if(!(isFirstPurchase) && (clothingQuantity<3 && electronicQuantity<3)){
            if (totalWithDiscountLabel != null) {
                totalWithDiscountLabel.setText(""+totalCost);
            }
        }

        // Update first purchase discount label

//        else {
//            System.out.println("Error: totalCostLabel is null");
//        }
    }







}