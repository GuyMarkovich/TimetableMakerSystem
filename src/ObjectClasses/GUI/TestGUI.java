package ObjectClasses.GUI;

import ObjectClasses.ScheduleAlgorithm.GAFunctions;
import ObjectClasses.ScheduleAlgorithm.Individual;
import ObjectClasses.TimeTable.ClassSchedule;
import ObjectClasses.Data.Globals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/* public class TestGUI {

   public static void main(String[] args) {

        GAFunctions gaFunctions = new GAFunctions(Globals.POPULATION_SIZE, 1);
        gaFunctions.initializePopulation();
        gaFunctions.evolve();


//        for (int i = 0; i < GlobalsTemp.POPULATION_SIZE; i++)
//        {
//            System.out.println("Individual number:" + i + " grade:" + gaFunctions.returnFitness(i) + " pick probability:" + gaFunctions.getPickProbability(i));
//        }




        Individual child = new Individual(gaFunctions.getBestIndividual());
        child.findClassConflicts();
        ClassSchedule foundSchedule = new ClassSchedule(child.getClassSchedule());
        String[][] schedule = foundSchedule.returnDisplaySchedule();




        //--------------------------------------------------------------------------------------------------------------
        // GUI CODE
        //--------------------------------------------------------------------------------------------------------------


        // table model to make the table non-editable, so that the user cannot edit the schedule manually
        NoEditTableModel model = new NoEditTableModel(schedule, new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"});
        JTable table = new JTable(model); // create a table to display the schedule
        table.setBounds(0, 0, 550, 750);
        table.setRowHeight(50);
        table.setRowSelectionAllowed(false); // disable row selection
        table.setColumnSelectionAllowed(false); // disable column selection
        table.setCellSelectionEnabled(false); // disable cell selection
        table.setLayout(null); // set layout to null
        table.setVisible(true); // set table to visible

        // Create a scroll pane to hold the table
        JScrollPane scrollPane = new JScrollPane(table); // scroll pane to hold the table
        scrollPane.setBounds(210, 0, 550, 750); // set the scroll pane's bounds


        // Create a side menu panel to hold the buttons and other components
        JPanel menupanel = new JPanel(); // side menu panel
        menupanel.setBackground(Color.gray); // set the side menu panel's background color to gray
        menupanel.setBounds(0, 0, 200, 750);
        menupanel.setLayout(null);






        // Add a text box under the "Teacher View" button to allow the user to enter the teacher's ID
        JTextField textBox = new JTextField(); // text box to enter teacher ID
        textBox.setBounds(10, 160, 180, 30);

        // Add a focus listener to the text field to display the hint text when the field is empty and not in focus
        textBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { // when the text field gains focus (is clicked on)
                // empty the text field when it gains focus
                textBox.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) { // when the text field loses focus (is clicked off of without entering any text)
                // Display the hint text when the text field loses focus and is empty
                if (textBox.getText().isEmpty()) {
                    textBox.setText("Enter Teacher ID");
                }
            }
        });
        menupanel.add(textBox); // add text box to side menu panel

        // Add a button to the side menu panel to allow the user to set the teacher's availability
        JButton teacherView = new JButton("Set Teacher Availability"); // button to set teacher availability
        teacherView.setBounds(10, 100, 180, 50);
        teacherView.setBackground(Color.white);
        teacherView.setBorderPainted(true);
        teacherView.setFocusPainted(false);
        menupanel.add(teacherView); // add button to side menu panel
        teacherView.addActionListener(e -> processTeacherViewInput(textBox)); // add action listener to button to process input from text box


        // Main window
        JFrame frame = new JFrame();
        frame.setSize(790, 750  );
        frame.setTitle("Scheduler"); // set title of window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation to exit the program when the window is closed
        frame.setResizable(false); // disable resizing of the window
        frame.setLayout(null); // set layout to null so that we can manually set the position of each component
        frame.add(menupanel); // add side menu panel to main window
        frame.add(scrollPane); // add scroll pane to main window







        // Login window
        LoginDialog loginDialog = new LoginDialog(frame);
        loginDialog.setVisible(true);

        if (loginDialog.isLoginSuccessful()) {
            frame.setVisible(true); // make the window visible if the login was successful
        } else {
            System.exit(0);
        }





    }

    // Function to display an error message in a pop-up window fit for any error because it is generic and has a customizable message
    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE); //create a pop-up window with the error message
    }


    // Function to process the input from the text field when the "Teacher View" button is clicked
    private static void processTeacherViewInput(JTextField textField) {
        String input = textField.getText();
        if (input.isEmpty()) {
            showError("Please enter a valid input.");
        }
        else if (!Globals.teachersObj.containsKey(Integer.valueOf(input))){ // Check if teacher is in system by checking if their ID is in the HashMap
            showError("Teacher not in System.");
        }

        else {
            System.out.println("Teacher found");
        }
    }


}
*/