package ObjectClasses.GUI;
import ObjectClasses.Data.Globals;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class TeacherAvailabilityTable extends JDialog {
    private JTable table;

    private int[][] teacherAvailability = new int[8][5];
    public TeacherAvailabilityTable(int teacherID) {
        this.setModal(true);
        setTitle("Teacher Availability for: " + this.getTeacherName(teacherID) +" ID: "+  teacherID);
        setSize(500, 350);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create table model with 8 rows and 5 columns
        DefaultTableModel model = new DefaultTableModel(8, 5) {
            @Override
            public String getColumnName(int column) {
                String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
                return daysOfWeek[column];
            }
        };
        table = new JTable(model);

        // Set table cell renderer and editor
        table.setDefaultRenderer(Object.class, new TableCellRenderer());
        table.setDefaultEditor(Object.class, new TableCellEditor());

        table.setRowHeight(40); // Set row height

        JScrollPane scrollPane = new JScrollPane(table); // Added scroll pane to contain table

        // Add a submit button to the bottom of the frame
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);
        submitButton.addActionListener(e -> {
            handleSubmitButton();
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class TableCellRenderer implements javax.swing.table.TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel();
            int cellNum = row + 1;
            JLabel label = new JLabel(String.valueOf(cellNum));
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
            boolean cellValue = (value != null && (Boolean) value);
            panel.setBackground(cellValue ? Color.GREEN : Color.RED);
            return panel;
        }
    }

    private class TableCellEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private final JPanel panel;
        private Boolean value;

        public TableCellEditor() {
            panel = new JPanel();
            panel.setPreferredSize(new Dimension(60, 30)); // Set cell size
            panel.setBackground(Color.RED);
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    value = !value;
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.value = (Boolean) value;
            if (this.value == null) {
                this.value = false;
            }
            panel.setBackground(this.value ? Color.GREEN : Color.RED);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return value;
        }
    }


    // method to handle the submit button
    private void handleSubmitButton() {
        int numRows = table.getRowCount(); // get the number of rows
        int numCols = table.getColumnCount(); // get the number of columns
        // loop through the table and get the values
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Boolean cellValue = (Boolean) table.getValueAt(row, col);
                this.teacherAvailability[row][col] = cellValue != null && cellValue ? 1 : 0;
            }
        }


        dispose();
    }



    // getter for the 2D array
    public int[][] getTeacherAvailability() { // returns the 2D array
        return this.teacherAvailability;
    }


    // helper method to get the teacher name by id
    public String getTeacherName(int id){
        return Globals.teachersObj.get(id).getFirstName() + " " + Globals.teachersObj.get(id).getLastName();
    }


}
