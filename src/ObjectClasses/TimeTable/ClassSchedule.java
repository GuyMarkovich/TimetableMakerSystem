package ObjectClasses.TimeTable;

import ObjectClasses.Users.GlobalsTemp;

import java.util.HashMap;

// class holds the schedule for a class or a teacher
public class ClassSchedule extends BasicLesson {
    private BasicLesson[][] schedule;
    private int classId; // id of the class

    private HashMap<Integer, Integer> hoursPerSubject; // holds the number of hours per subject


    // Constructor for a class schedule, teacherId is null and the schedule starts empty
    public ClassSchedule(int id) {
        this.classId = id;
        this.hoursPerSubject = new HashMap<Integer, Integer>();
        // deep clone globalsTemp.hoursPerSubject
        for (int key : GlobalsTemp.hoursPerWeek.keySet()) {
            this.hoursPerSubject.put(key, GlobalsTemp.hoursPerWeek.get(key));
        }

        schedule = new BasicLesson[GlobalsTemp.PERIODS_IN_DAY][GlobalsTemp.DAYS_IN_WEEK];
    }


    // setter for schedule
    public void setSchedule(BasicLesson[][] schedule) {
        this.schedule = schedule;
    }
    // getter for schedule
    public BasicLesson[][] getSchedule() {
        return this.schedule;
    }

    public void addLesson(BasicLesson lesson, int day, int period) {
        if (this.isEmpty(day, period)) // check if the slot is empty
            schedule[day][period] = lesson; // add the lesson to the slot
        else // if the slot is not empty
            System.out.println("The slot is not empty"); // print an error message
    }

    public void removeLesson(int day, int period) {
        if (this.isEmpty(day, period)) // check if the slot is empty
            System.out.println("The slot is already empty"); // print an error message
        else // if the slot is not empty
            schedule[day][period] = null;
    }

    public BasicLesson getLesson(int day, int period) {
        if (this.isEmpty(day, period)) // check if the slot is empty
            return null; // return null if the slot is empty
        // if the slot is not empty (has a lesson
        return schedule[period][day]; // return the lesson in the slot
    }

    public boolean isEmpty(int day, int period) { // check if a slot in the schedule is empty
        return schedule[period][day] == null; // return true if the slot is empty
    }


    // functions for filling the schedule with lessons

    // function to pick a random subject from the list of subjects
    public static int chooseRandomSubject() {
        // choose random number between 1-8 using math.random
        // return the number
        int rndInt = (int) (Math.floor(Math.random() * 8) + 1);
        return rndInt;
    }

    // function to pick a random period in the day
    public static int chooseRandomPeriod() {
        // choose random number between 1-8 using math.random
        // return the number
        int rndInt = (int) (Math.floor(Math.random() * 8) + 1);
        return rndInt;
    }

    // function to pick a random day in the week
    public static int chooseRandomDay() {
        // choose random number between 1-5 using math.random
        // return the number
        int rndInt = (int) (Math.floor(Math.random() * 5) + 1);
        return rndInt;
    }



    public static int getTeacherBySubject(int subjectId) {
        return GlobalsTemp.teacherBySubject.get(subjectId);
    }




    // function to fill the schedule randomly, with no constraints taken into account
    public void fillScheduleRandomly() {

        int day, period, subject, room;
        //BasicLesson[][] schedule = new BasicLesson[GlobalsTemp.DAYS_IN_WEEK][GlobalsTemp.PERIODS_IN_DAY];
        for (int i = 0; i < GlobalsTemp.totalWeekHours;) {
            day = chooseRandomDay();
            period = chooseRandomPeriod();
            subject = chooseRandomSubject();
            if ((this.schedule[period-1][day-1] == null) && (this.hoursPerSubject.get(subject) > 0)) {
                this.schedule[period-1][day-1] = new BasicLesson(GlobalsTemp.subjectsObj.get(subject), this.classId,  getTeacherBySubject(subject));
                this.hoursPerSubject.put(subject, this.hoursPerSubject.get(subject)-1);
                i++;
            }
        }
    }


    // function to return the schedule in a 2D array of strings, simplified for display in the GUI
    public String[][] returnDisplaySchedule() {
        String[][] displaySchedule = new String[GlobalsTemp.PERIODS_IN_DAY][GlobalsTemp.DAYS_IN_WEEK];
        for (int i = 0; i < GlobalsTemp.PERIODS_IN_DAY; i++) {
            for (int j = 0; j < GlobalsTemp.DAYS_IN_WEEK; j++) {
                if (this.schedule[i][j] == null) {
                    displaySchedule[i][j] = "Free";
                } else {
                    displaySchedule[i][j] = this.schedule[i][j].getSubject().getSubjectName();
                }
            }
        }
        return displaySchedule;
    }


}


