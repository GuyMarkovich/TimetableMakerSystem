package ObjectClasses.Users;

import ObjectClasses.Data.Globals;

import java.util.Date;

public class Teacher extends Person {
    private int teacherId;
    private int[] subjects; //subject this teacher teaches, could be more than one, holds subjectId

    private int[][] availableHours; // holds the available hours for each day of the week
    // 0 - not available
    // 1 - available
    // 2 - available but currently occupied

    // constructor for teacher without available hours
    public Teacher(String firstName, String lastName, String phoneNumber, String email, Date dateOfBirth, String address, int teacherId, int[] subjects) {
        super(firstName, lastName, phoneNumber, email,dateOfBirth, address);
        this.teacherId = teacherId;
        this.subjects = new int[subjects.length];
        for (int idx = 0; idx < subjects.length; idx++) {
            this.subjects[idx] = subjects[idx];
        }
        this.availableHours = new int[Globals.PERIODS_IN_DAY][Globals.DAYS_IN_WEEK];

    }


    //constructor for teacher with available hours, used for testing
    public Teacher(String firstName, String lastName, String phoneNumber, String email, Date dateOfBirth, String address, int teacherId, int[] subjects, int[][] availableHours) {
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


    // Functions for Genetic Algorithm

    // check if the teacher is available for a specific day and period
    public int checkAvailableHour(int day, int period) {
        return this.availableHours[period][day];
    }

    // update the available hours for a specific day and period, 0 for not available, 1 for available, 2 for available but occupied
    public void updateAvailableHours(int day, int period, int value) {
        this.availableHours[period][day] = value;
    }


    // check how many hours the teacher has available:
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

    // check empty days for teacher
    public int getEmptyDays() {
        int emptyDays = 0;
        for (int day = 0; day < Globals.DAYS_IN_WEEK; day++) {
            int emptyPeriods = 0;
            for (int period = 0; period < Globals.PERIODS_IN_DAY; period++) {
                if (this.availableHours[period][day] == 0) {
                    emptyPeriods++;
                }
            }
            if (emptyPeriods == Globals.PERIODS_IN_DAY) {
                emptyDays++;
            }
        }
        return emptyDays;
    }


    // check if teacher schedule is suitable for the algorithm
    public boolean isScheduleSuitable() {
        if (this.getAvailableHours() < Globals.MINIMUM_HOURS_PER_TEACHER) {
            return false;
        }
        if (this.getEmptyDays() > 1) {
            return false;
        }
        return true;
    }

}
