package ObjectClasses.GUI;
import ObjectClasses.Data.Globals;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class TeacherAvailabilityTable extends JDialog { // This class is used to create a dialog that displays the teacher availability for a given teacher and allows the user to edit the availability for the teacher
    private JTable table; // table to display the teacher availability

    private int[][] teacherAvailability = new int[Globals.PERIODS_IN_DAY][Globals.DAYS_IN_WEEK];
    public TeacherAvailabilityTable(int teacherID) {
        this.setModal(true);
        setTitle("Teacher Availability for: " + this.getTeacherName(teacherID) +" ID: "+  teacherID);
        setSize(500, 350);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Set the close operation to dispose so that the frame is closed and the program continues to run

        // Create table model with 8 rows and 5 columns
        DefaultTableModel model = new DefaultTableModel(Globals.PERIODS_IN_DAY, Globals.DAYS_IN_WEEK) {
            @Override
            public String getColumnName(int column) {
                String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}; // Array of days of the week to be used as column names
                return daysOfWeek[column]; // Return the day of the week for the given column
            }
        };
        table = new JTable(model); // Create a table with the model

        // Set table cell renderer and editor
        table.setDefaultRenderer(Object.class, new TableCellRenderer()); // Set the table cell renderer to the custom TableCellRenderer class
        table.setDefaultEditor(Object.class, new TableCellEditor()); // Set the table cell editor to the custom TableCellEditor class

        table.setRowHeight(40); // Set row height

        JScrollPane scrollPane = new JScrollPane(table); // Added scroll pane to contain table

        // Add a submit button to the bottom of the frame
        JPanel buttonPanel = new JPanel(); // Create a panel to hold the button
        JButton submitButton = new JButton("Submit"); // Create a submit button
        buttonPanel.add(submitButton); // Add the button to the panel
        submitButton.addActionListener(e -> { // Add an action listener to the button
            handleSubmitButton(); // Call the handleSubmitButton method when the button is clicked
        });

        getContentPane().setLayout(new BorderLayout()); // Set the layout of the frame to border layout
        getContentPane().add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the frame
        getContentPane().add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the bottom of the frame

        setVisible(true); // Set the frame to visible
    }

    private class TableCellRenderer implements javax.swing.table.TableCellRenderer { // Custom table cell renderer class
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel();
            int cellNum = row + 1;
            JLabel label = new JLabel(String.valueOf(cellNum));
            label.setHorizontalAlignment(JLabel.CENTER); // Center the text in the label
            panel.add(label);
            boolean cellValue = (value != null && (Boolean) value);
            panel.setBackground(cellValue ? Color.GREEN : Color.RED); // Set the background color of the cell based on the value
            return panel;
        }
    }

    private class TableCellEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor { // Custom table cell editor class
        private final JPanel panel;
        private Boolean value;

        public TableCellEditor() {  // Constructor
            panel = new JPanel();
            panel.setPreferredSize(new Dimension(60, 30)); // Set cell size
            panel.setBackground(Color.RED); // Set default cell color to red
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { // When the cell is clicked, toggle the value and stop editing
                    value = !value;
                    fireEditingStopped(); // Stop editing
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { // Method to get the table cell editor component
            this.value = (Boolean) value;
            if (this.value == null) {
                this.value = false;
            }
            panel.setBackground(this.value ? Color.GREEN : Color.RED); // Set the background color of the cell based on the value
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return value;
        } // Method to get the cell editor value
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
