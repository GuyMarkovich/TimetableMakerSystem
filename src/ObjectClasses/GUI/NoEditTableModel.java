package ObjectClasses.GUI;


import javax.swing.table.DefaultTableModel;

public class NoEditTableModel extends DefaultTableModel { // This class is used to make a table that is uneditable, meant to be used for displaying the schedule

    /**
     * Constructor for NoEditTableModel
     * @param data the data to be displayed in the table
     * @param columnNames the column names for the table
     */
    public NoEditTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    /**
     * This method is used to make the table uneditable
     * @param row the row of the table
     * @param column the column of the table
     * @return false because the table is uneditable in any cell
     */
    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    } // This method is used to make the table uneditable





}
