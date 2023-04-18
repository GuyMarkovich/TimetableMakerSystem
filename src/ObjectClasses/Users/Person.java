package ObjectClasses.Users;

import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String address;

    // Constructor for a person object
    public Person(String firstName, String lastName, String phoneNumber, String email, Date dateOfBirth, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }


    public String getFirstName() { // get first name
        return firstName;
    }

    public String getLastName() { // get last name
        return lastName;
    }
}


