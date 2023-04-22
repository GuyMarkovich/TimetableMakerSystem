package ObjectClasses.GUI;

import ObjectClasses.Data.Globals; // import the Globals class to get the number of days in a week and the number of periods in a day
import ObjectClasses.ScheduleAlgorithm.GAFunctions; // import the GAFunctions class to use the genetic algorithm functions
import ObjectClasses.ScheduleAlgorithm.Individual; // import the Individual class to use the Individual class
import ObjectClasses.TimeTable.ClassSchedule; // import the ClassSchedule class to use the ClassSchedule class

import javax.swing.*; // import the swing package to use the swing components for the GUI
import java.awt.*; // import the awt package to use the awt components for the GUI
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


// This class is used to create a frame that displays the schedule and allows all the other functions of the program, this is the main frame of the program
public class ScheduleFrame extends JFrame {
    private String[][] schedule;
    private NoEditTableModel model; // table model to make the table non-editable, so that the user cannot edit the schedule manually, we keep a reference to this so that we can update the table when the user clicks the "Generate Schedule" button


    public ScheduleFrame() {
        this.schedule = new String[Globals.PERIODS_IN_DAY][Globals.DAYS_IN_WEEK];
        for (int i = 0; i < Globals.PERIODS_IN_DAY; i++) {
            for (int j = 0; j < Globals.DAYS_IN_WEEK; j++) {
                schedule[i][j] = " ";
            }
        }



        // table model to make the table non-editable, so that the user cannot edit the schedule manually
        this.model = new NoEditTableModel(schedule, new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"});
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






        // Add a text box under the "Teacher Availability" button to allow the user to enter the teacher's ID
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
        JButton teacherAvailability = new JButton("Set Teacher Availability"); // button to set teacher availability
        teacherAvailability.setBounds(10, 100, 180, 50);
        teacherAvailability.setBackground(Color.white);
        teacherAvailability.setBorderPainted(true);
        teacherAvailability.setFocusPainted(false);
        menupanel.add(teacherAvailability); // add button to side menu panel
        teacherAvailability.addActionListener(e -> processTeacherViewInput(textBox)); // add action listener to button to process input from text box


        //Button to launch the Algorithm
        JButton runAlgorithm = new JButton("Generate Schedule");
        runAlgorithm.setBounds(10, 300, 180, 50);
        runAlgorithm.setBackground(Color.white);
        runAlgorithm.setBorderPainted(true);
        runAlgorithm.setFocusPainted(false);
        menupanel.add(runAlgorithm);
        runAlgorithm.addActionListener(e -> {
            processRunAlgorithmInput();
            model = new NoEditTableModel(schedule, new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}); // update the table model
            table.setModel(model);
        });



        setSize(790, 750  );
        setTitle("Scheduler"); // set title of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation to exit the program when the window is closed
        setResizable(false); // disable resizing of the window
        setLayout(null); // set layout to null so that we can manually set the position of each component
        add(menupanel); // add side menu panel to main window
        add(scrollPane); // add scroll pane to main window


        // Login window
        LoginDialog loginDialog = new LoginDialog(this); // create a login dialog
        loginDialog.setVisible(true); // make the login dialog visible

        if (loginDialog.isLoginSuccessful()) { // if the login was successful
            setVisible(true); // make the window visible if the login was successful
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
        int[][] availability; // create a 2D array to hold the teacher's availabilityJDialog dialog = new JDialog();
        String input = textField.getText();
        if (input.isEmpty() || input.equals("Enter Teacher ID")) { // Check if the text field is empty
            showError("Please enter a valid input.");
        }
        else if (!Globals.teachersObj.containsKey(Integer.valueOf(input))){ // Check if teacher is in system by checking if their ID is in the HashMap
            showError("Teacher not in System.");
        }

        else {

            // Create a new dialog to display the teacher's availability
            TeacherAvailabilityTable table = new TeacherAvailabilityTable(Integer.valueOf(input)); // create a new table to display the teacher's availability

            availability = table.getTeacherAvailability(); // get result array from ClickableTable
            for (int i = 0; i < Globals.PERIODS_IN_DAY; i++) {
                for (int j = 0; j < Globals.DAYS_IN_WEEK; j++) {
                    Globals.teachersObj.get(Integer.valueOf(input)).updateAvailableHours(j, i, availability[i][j]);
                }
            }



            //TEST
            // Do something with the 2D array, such as print it to the console
            for (int i = 0; i < Globals.PERIODS_IN_DAY; i++) {
                for (int j = 0; j < Globals.DAYS_IN_WEEK; j++) {
                    System.out.print(availability[i][j] + " ");
                }
                System.out.println();
            }

        }
    }


    private void processRunAlgorithmInput() {
        for (int key : Globals.teachersObj.keySet()) {
            int result=  Globals.teachersObj.get(key).isScheduleSuitable(); // result of check
            if (result != 0) { // check if the schedule is suitable for all teachers in the system
                showError("Teacher with ID: " + key + " doesn't have a suitable schedule. Reason: " + Globals.errorMessages.get(result));
                return; // return from the function if the schedule is not suitable for any teacher
            }
        }
        // if the schedule is suitable for all teachers, generate the schedule
        GAFunctions gaFunctions = new GAFunctions(Globals.POPULATION_SIZE, 1); // create a new GAFunctions object
        gaFunctions.initializePopulation(); // initialize the population
        gaFunctions.evolve(); // evolve the population to find the best schedule

        Individual child = new Individual(gaFunctions.getBestIndividual()); // create a new individual object to hold the best schedule
        ClassSchedule foundSchedule = new ClassSchedule(child.getClassSchedule()); // create a new ClassSchedule object to display the best schedule
        this.schedule = foundSchedule.returnDisplaySchedule(); // get the best schedule as a 2D array of strings

    }




}



