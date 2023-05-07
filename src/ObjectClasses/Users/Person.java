package ObjectClasses.Users;

import java.util.Date;

// Person class, basic information about a person, allows for further expansion for the program if needed
public class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String address;

    /** Constructor for a person object */
    public Person(String firstName, String lastName, String phoneNumber, String email, Date dateOfBirth, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }


    /** get the first name of the person */
    public String getFirstName() { // get first name
        return firstName;
    }

    /** get the last name of the person */
    public String getLastName() { // get last name
        return lastName;
    }
}


