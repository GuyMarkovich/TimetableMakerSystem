package ObjectClasses.GUI;


import javax.swing.table.DefaultTableModel;

public class NoEditTableModel extends DefaultTableModel { // This class is used to make a table that is uneditable, meant to be used for displaying the schedule
    public NoEditTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    } // This method is used to make the table uneditable





}
