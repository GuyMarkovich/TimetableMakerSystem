package ObjectClasses.Users;

import ObjectClasses.Data.Globals;

import java.time.LocalDate;
import java.util.Date;

// Teacher class, holds information about a teacher, extends Person class
public class Teacher extends Person {
    private int teacherId;
    private int[] subjects; //subject this teacher teaches, could be more than one, holds subjectId

    private int[][] availableHours; // holds the available hours for each day of the week
    // 0 - not available
    // 1 - available


    /** constructor for teacher without available hours */
    public Teacher(String firstName, String lastName, String phoneNumber, String email, LocalDate dateOfBirth, String address, int teacherId, int[] subjects) {
        super(firstName, lastName, phoneNumber, email,dateOfBirth, address);
        this.teacherId = teacherId;
        this.subjects = new int[subjects.length];
        for (int idx = 0; idx < subjects.length; idx++) {
            this.subjects[idx] = subjects[idx];
        }
        this.availableHours = new int[Globals.PERIODS_IN_DAY][Globals.DAYS_IN_WEEK];
        for (int period = 0; period < Globals.PERIODS_IN_DAY; period++) {
            for (int day = 0; day < Globals.DAYS_IN_WEEK; day++) {
                this.availableHours[period][day] = 0;
            }
        }

    }


    /**constructor for teacher with available hours */
    public Teacher(String firstName, String lastName, String phoneNumber, String email, LocalDate dateOfBirth, String address, int teacherId, int[] subjects, int[][] availableHours) {
        super(firstName, lastName, phoneNumber, email,dateOfBirth, address);
        this.teacherId = teacherId;
        this.subjects = new int[subjects.length];
        for (int idx = 0; idx < subjects.length; idx++) {
            this.subjects[idx] = subjects[idx];
        }

        this.availableHours = new int[Globals.PERIODS_IN_DAY][Globals.DAYS_IN_WEEK];
        for (int period = 0; period < Globals.PERIODS_IN_DAY; period++) {
            for (int day = 0; day < Globals.DAYS_IN_WEEK; day++) {
                this.availableHours[period][day] = availableHours[period][day];
            }
        }

    }


    /** Functions for Genetic Algorithm */

    /** check if the teacher is available for a specific day and period */
    public int checkAvailableHour(int day, int period) {
        return this.availableHours[period][day];
    }

    /** update the available hours for a specific day and period, 0 for not available, 1 for available*/
    public void updateAvailableHours(int day, int period, int value) {
        this.availableHours[period][day] = value;
    }


    /** check how many hours the teacher has available, used in determining if teacher schedule is suitable */
    public int getAvailableHours() {
        int availableHours = 0;
        for (int period = 0; period < Globals.PERIODS_IN_DAY; period++) {
            for (int day = 0; day < Globals.DAYS_IN_WEEK; day++) {
                if (this.availableHours[period][day] == 1) {
                    availableHours++;
                }
            }
        }
        return availableHours;
    }

    /** check empty days for teacher, used in determining if teacher schedule is suitable */
    public int getEmptyDays() {
        int emptyDays = 0;
        for (int day = 0; day < Globals.DAYS_IN_WEEK; day++) {
            int emptyPeriods = 0;
            for (int period = 0; period < Globals.PERIODS_IN_DAY; period++) {
                if (this.availableHours[period][day] == 0) {
                    emptyPeriods++;
                }
            }
            if (emptyPeriods == Globals.PERIODS_IN_DAY) { // if all periods are empty, add 1 to emptyDays
                emptyDays++;
            }
        }
        return emptyDays; // return the number of empty days
    }


    /** check if teacher schedule is suitable for the algorithm */
    public int isScheduleSuitable() {
        if (this.getAvailableHours() < Globals.MINIMUM_HOURS_PER_TEACHER) { // check if teacher has enough available hours
            return 1;
        }
        if (this.getEmptyDays() > 1) { // check if teacher has more than 1 empty days
            return 2;
        }
        return 0; // if all checks passed, return true
    }

    /** get the teachers available hours */
    public int[][] getAvailability() {
        int[][] availability = new int[Globals.PERIODS_IN_DAY][Globals.DAYS_IN_WEEK];
        for (int period = 0; period < Globals.PERIODS_IN_DAY; period++) {
            for (int day = 0; day < Globals.DAYS_IN_WEEK; day++) {
                availability[period][day] = this.availableHours[period][day];
            }
        }
        return availability;
    }
}
