import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
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


    public OnlineShoppingGUI(ArrayList<Product> productList) {
        this.productList =productList;
        setTitle("                       Online Shopping GUI");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components

        shoppingCartButton = new JButton("Shopping Cart");

        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronic", "Clothing"}); // Drop-down menu for product categories

        MyTableModel myTableModel= new MyTableModel(productList);
        productTable = new JTable(myTableModel);
        JScrollPane scrollPane=new JScrollPane(productTable);

        //AddToCartButton
        addToCartButton=new JButton("Add to Shopping Cart");
        addToCartButton.setBackground(Color.ORANGE);

        productTextArea= new JTextArea();

        //Sort Button
        sortButton = new JButton("Sort by Product Id");
        sortButton.setBackground(Color.gray);

        // Set up layout of the JFrame
        setLayout(new FlowLayout());



        //Set up a new panel
        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setPreferredSize(new Dimension(700,75));
        topButtonPanel.setLayout(new BorderLayout());
        topButtonPanel.setBackground(Color.LIGHT_GRAY);
        //topButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,30,30));
        topButtonPanel.setLayout(new BorderLayout());

        JPanel shoppingCartBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  //For moving right "'Shopping cart' button
        shoppingCartBtnPanel.add(shoppingCartButton);
        shoppingCartButton.setBackground(Color.GREEN);
        topButtonPanel.add(shoppingCartBtnPanel,BorderLayout.NORTH);

        JPanel dropDownPanel = new JPanel();  //For moving center to drop-down menu
        dropDownPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dropDownPanel.add(new JLabel("Select Product Category :  "));
        dropDownPanel.add(productTypeComboBox);
        topButtonPanel.add(dropDownPanel,BorderLayout.CENTER);



        JPanel middlePanel= new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        scrollPane.setPreferredSize(new Dimension(700,80));
        middlePanel.setPreferredSize(new Dimension(800,100));
        //productTable.setPreferredSize(new Dimension(700,100));
        middlePanel.add(scrollPane);


        JPanel sortBtnPanel = new JPanel(new BorderLayout());
        sortBtnPanel.setPreferredSize(new Dimension(700,30));
        sortBtnPanel.add(sortButton,BorderLayout.WEST);




        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  //This is not necessary. Because  FlowLayout is the default layout manager for every JPanel
        bottomPanel.setPreferredSize(new Dimension(800,300));
        productTextArea.setPreferredSize(new Dimension(700,250));
        bottomPanel.add(productTextArea);
        bottomPanel.add(addToCartButton);



        MouseHandler mouseHandler= new MouseHandler();
        shoppingCartButton.addMouseListener(mouseHandler);
        productTable.addMouseListener(mouseHandler);
        addToCartButton.addMouseListener(mouseHandler);
        sortButton.addMouseListener(mouseHandler);





        add(topButtonPanel);
        add(middlePanel);
        add(sortBtnPanel);
        add(new JLabel("Select Product Details")); //Making & adding label for 'Select Product Details'
        add(bottomPanel);

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
        }
        else {
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


    private class MouseHandler extends ShoppingCart implements MouseListener, MouseMotionListener{

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
                        "\nProduct ID: " + selectedProduct.getProductId() + "\n\n" +
                                "Category  : " + selectedProduct.getProductType() + "\n\n" +
                                "Name      : " + selectedProduct.getProductName() + "\n\n" +
                                ((selectedProduct instanceof Clothing) ? "Size           : " + ((Clothing) selectedProduct).getSize() + "\n\n" : "") +
                                ((selectedProduct instanceof Clothing) ? "Product Color  : " + ((Clothing) selectedProduct).getColor() + "\n\n" : "") +
                                ((selectedProduct instanceof Electronics) ? "Product Brand  : " + ((Electronics) selectedProduct).getBrand() + "\n\n" : "") +
                                ((selectedProduct instanceof Electronics) ? "Warranty Period: " + ((Electronics) selectedProduct).getWarrentyPeriod() + "\n\n" : "") +
                                "Items Available: " + selectedProduct.getNumberOfAvailableItem() + "\n\n"
                );

            }


            else if (e.getSource()==shoppingCartButton){
                shoppingCartButton.setBackground(Color.RED);
                JFrame newFrame = new JFrame("Shopping Cart");
                newFrame.setSize(600, 500);
                newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                setLayout(new FlowLayout(FlowLayout.CENTER));

                JPanel containerPanel = new JPanel();
                containerPanel.setLayout(new FlowLayout());
                containerPanel.setPreferredSize(new Dimension(600,450));

                JPanel panel_1=new JPanel();
                panel_1.setPreferredSize(new Dimension(500,150));
                panel_1.setLayout(new FlowLayout(FlowLayout.CENTER,0,20)); //(FlowLayout.CENTER,0,40) was used for making a vertical gap from head

                JPanel panel_2=new JPanel();
                panel_2.setLayout(new GridLayout(4,2,20,20));
                panel_2.setPreferredSize(new Dimension(400,150));

                JTextArea cartTextArea = new JTextArea();
                cartTextArea.setBackground(Color.ORANGE);
                //cartTextArea.setPreferredSize(new Dimension(50,5));
                cartTextArea.setEditable(false);
                cartTextArea.setText(String.valueOf(calculateTotalCost()));  //  // Update cartTextArea with the total cost

                //JScrollPane cartScrollPane = new JScrollPane(cartTextArea);


//                CartItem cart = new ShoppingCart();
//                cart.setShoppingCartList(getShoppingCartList());

                //cartTextArea.setText(cart.calculateTotalCost() + "\n\n");
                //cart.printProduct();

                JTable cartTable = new JTable(getTableModel());
                cartTable.setEnabled(false);
                JScrollPane scrollPane = new JScrollPane(cartTable);
                scrollPane.setPreferredSize(new Dimension(500,100));


                //newFrame.add(cartScrollPane);

                panel_1.add(scrollPane);

//                panel_2.add(new JLabel("Total"));
//                panel_2.add(cartTextArea);
//                panel_2.add(new JLabel("First Purchase Discount(10%)"));
//                panel_2.add(new JLabel("Final Total"));

                JPanel newPanel1=new JPanel();
                newPanel1.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel1.setPreferredSize(new Dimension(20,3));
                newPanel1.add(new JLabel("Total"));

                JPanel newPanel2=new JPanel();
                newPanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
                //newPanel2.setPreferredSize(new Dimension(20,5));
                newPanel2.add(cartTextArea);

                JPanel newPanel3=new JPanel();
                newPanel3.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel3.setPreferredSize(new Dimension(20,3));
                newPanel3.add(new JLabel("First Purchase Discount(10%)"));

                JPanel newPanel4=new JPanel();
                newPanel4.setLayout(new FlowLayout(FlowLayout.LEADING));

                double totalCost = calculateTotalCost();
                double discount = totalCost * 0.10; // 10% discount
                //newPanel4.setPreferredSize(new Dimension(20,3));
                newPanel4.add(new JLabel("-"+discount+"£"));

                JPanel newPanel5=new JPanel();
                newPanel5.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel3.setPreferredSize(new Dimension(20,3));
                newPanel5.add(new JLabel("Three items in same Category Discount(20%)"));

                JPanel newPanel6=new JPanel();
                newPanel6.setLayout(new FlowLayout(FlowLayout.LEADING));
                double three_item_discount=calculateTotalCost() * 0.20;
                if (getClothingQuantity()>=3 || getElectronicQuantity()>=3){
                    newPanel6.add(new JLabel("-"+three_item_discount+"£"));
                }
                else {
                    newPanel6.add(new JLabel("-"+0+"£"));
                }


                JPanel newPanel7=new JPanel();
                newPanel7.setLayout(new FlowLayout(FlowLayout.TRAILING));
                //newPanel3.setPreferredSize(new Dimension(20,3));
                newPanel7.add(new JLabel("Total"));

                JPanel newPanel8=new JPanel();
                double total_1= calculateTotalCost()-three_item_discount-discount;
                double total_2=calculateTotalCost()-discount;
                newPanel8.setLayout(new FlowLayout(FlowLayout.LEADING));
                if (getClothingQuantity()>=3 || getElectronicQuantity()>=3){
                    newPanel8.add(new JLabel(total_1+"£"));
                }
                else {
                    newPanel8.add(new JLabel(total_2+"£"));

                }




                panel_2.add(newPanel1);
                panel_2.add(newPanel2);
                panel_2.add(newPanel3 );
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
                        Product selectedProduct = filteredProducts.get(selectedRow);

                        // Add the selected product to the shopping cart
                        addProduct(selectedProduct);
                        System.out.println("The item has been successfully added to the cart");
                    }
                }
            }
            else if (e.getSource()==sortButton){
                 sortTableByProductId();
            }


            //Table






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




    public void openWestminsterGUI(){
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

        int[] sortedRows = productTable.getSelectedRows(); // Get the selected rows in sorted order

        for (int sortedRow : sortedRows) {
            if (sortedRow >= 0 && sortedRow < productTable.getRowCount()) {
                int modelRow = productTable.convertRowIndexToModel(sortedRow); // Convert to model index
                Product product = ((MyTableModel) productTable.getModel()).getProductAtRow(modelRow);
                if ("All".equals(selectedCategory) || product.getProductType().equals(selectedCategory)) {
                    filteredProducts.add(product);
                }
            }
        }

        return filteredProducts;
    }


}