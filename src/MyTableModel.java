import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyTableModel extends    AbstractTableModel{
    private String[] columnNames={"Product ID", "Name" , "Category", "Price($)", "Info"};
    private ArrayList<Product> productList;

    public MyTableModel(ArrayList<Product> productList){
        this.productList=productList;

    }

    @Override
    public int getRowCount() {
        return  productList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex==0){
            return productList.get(rowIndex).getProductId();
        }
        else if (columnIndex==1){
            return productList.get(rowIndex).getProductName();
        }
        else if (columnIndex==2){
            return productList.get(rowIndex).getProductType();
        }
        else if (columnIndex==3){
            return productList.get(rowIndex).getPrice();
        }
        else if(columnIndex==4){
            if (productList.get(rowIndex).getProductType().equals("Electronic")){
                return ((Electronics) productList.get(rowIndex)).getBrand()+ ", "+ ((Electronics) productList.get(rowIndex)).getWarrentyPeriod()+" weeks warranty";

            }
            else {
                return ((Clothing) productList.get(rowIndex)).getSize()+ ", "+ ((Clothing) productList.get(rowIndex)).getColor();
            }
            //return productList.get(rowIndex).getNumberOfAvailableItem();
        }
        else {
            return null;
        }

    }

    public String getColumnName(int col){
       return columnNames[col];
    }

    public Class getColumnClass(int columnIndex){
        if (columnIndex==3){
            return Double.class;
        }
        else if (columnIndex==4){
            return Integer.class;
        }
        else {
            return String.class;
        }
    }

    public void sortByProductId() {
        Collections.sort(productList, Comparator.comparing(Product::getProductId));
        fireTableDataChanged();
    }


}
