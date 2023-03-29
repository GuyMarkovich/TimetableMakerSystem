package ObjectClasses.Users;

import ObjectClasses.Users.Person;

import java.util.Date;

public class Teacher extends Person {
    private int teacherId;
    private int[] subjects; //subject this teacher teaches, could be more than one, holds subjectId

    private int[][] availableHours; // holds the available hours for each day of the week

    // constructor for teacher without available hours
    public Teacher(String firstName, String lastName, String phoneNumber, String email, Date dateOfBirth, String address, int teacherId, int[] subjects) {
        super(firstName, lastName, phoneNumber, email,dateOfBirth, address);
        this.teacherId = teacherId;
        this.subjects = new int[subjects.length];
        for (int idx = 0; idx < subjects.length; idx++) {
            this.subjects[idx] = subjects[idx];
        }
        this.availableHours = new int[GlobalsTemp.PERIODS_IN_DAY][GlobalsTemp.DAYS_IN_WEEK];

    }


    //constructor for teacher with available hours
    public Teacher(String firstName, String lastName, String phoneNumber, String email, Date dateOfBirth, String address, int teacherId, int[] subjects, int[][] availableHours) {
        super(firstName, lastName, phoneNumber, email,dateOfBirth, address);
        this.teacherId = teacherId;
        this.subjects = new int[subjects.length];
        for (int idx = 0; idx < subjects.length; idx++) {
            this.subjects[idx] = subjects[idx];
        }

        this.availableHours = new int[GlobalsTemp.PERIODS_IN_DAY][GlobalsTemp.DAYS_IN_WEEK];
        for (int period = 0; period < GlobalsTemp.PERIODS_IN_DAY; period++) {
            for (int day = 0; day < GlobalsTemp.DAYS_IN_WEEK; day++) {
                this.availableHours[period][day] = availableHours[period][day];
            }
        }

    }




    public int checkAvailableHour(int day, int period) {
        return this.availableHours[period][day];
    }

    // update the available hours for a specific day and period, 0 for not available, 1 for available, 2 for available but occupied
    public void updateAvailableHours(int day, int period, int value) {
        this.availableHours[period][day] = value;
    }
}
