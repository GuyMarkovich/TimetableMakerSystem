package ObjectClasses.GUI;


import ObjectClasses.Data.Globals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog implements ActionListener {
    private JTextField usernameField; // Text field for username
    private JPasswordField passwordField; // Password field for password
    private JButton loginButton; // Login button
    private boolean loginSuccessful; // True if login is successful and false otherwise

    public LoginDialog(JFrame parent) { // Constructor, receives the parent frame as a parameter (the frame that created this dialog) for centering the dialog on the parent frame
        super(parent, "Login", true);

        JPanel panel = new JPanel(new GridLayout(3, 2)); //create login window

        JLabel usernameText = new JLabel("Username:"); // label for username field
        panel.add(usernameText);

        usernameField = new JTextField(); // Use JTextField for username field
        panel.add(usernameField); // Add the text field to the panel

        JLabel passwordText = new JLabel("Password:"); // label for password field
        panel.add(passwordText);

        passwordField = new JPasswordField(); // Use JPasswordField instead of JTextField for password field so that the password is not visible
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this); // Add action listener to the login button (adding the actionPerformed method)
        getContentPane().add(loginButton, BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.CENTER);

        pack(); // Resize the dialog to fit the content
        setLocationRelativeTo(parent); // Center the dialog on the parent frame
    }


    // This method is called when the login button is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) { // Check if the source of the event is the login button
            String username = usernameField.getText(); // Get the username from the text field
            String password = new String(passwordField.getPassword());  // Get the password from the password field


            // compare the username and password with the data in the database
            for (String key : Globals.loginData.keySet()) {
                if ((username.equals(key))&& (password.equals(Globals.loginData.get(key)))) {
                    loginSuccessful = true; // Set loginSuccessful to true
                    dispose(); // Close the dialog after login is successful
                }
            }
            // if no match is found, display an error message
            if (!loginSuccessful)
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Returns true if login is successful and false otherwise
    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}

