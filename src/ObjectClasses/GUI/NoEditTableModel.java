package ObjectClasses.GUI;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class NoEditTableModel extends DefaultTableModel {
    public NoEditTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
}
