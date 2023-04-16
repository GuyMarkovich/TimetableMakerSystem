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

    // clone constructor
    public ClassSchedule(ClassSchedule classSchedule) {
        this.classId = classSchedule.classId;
        this.hoursPerSubject = new HashMap<Integer, Integer>();
        // deep clone classSchedule.hoursPerSubject
        for (int key : classSchedule.hoursPerSubject.keySet()) {
            this.hoursPerSubject.put(key, classSchedule.hoursPerSubject.get(key));
        }

        schedule = new BasicLesson[GlobalsTemp.PERIODS_IN_DAY][GlobalsTemp.DAYS_IN_WEEK];
        // deep clone classSchedule.schedule
        for (int i = 0; i < GlobalsTemp.DAYS_IN_WEEK; i++) {
            for (int j = 0; j < GlobalsTemp.PERIODS_IN_DAY; j++) {
                if (classSchedule.schedule[j][i] != null)
                    this.schedule[j][i] = new BasicLesson(classSchedule.schedule[j][i]);
            }
        }
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
        // if the slot is not empty (has a lesson)
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

    // fill the rest of the schedule with remaining hours from the hashmap when applicable
    // this also represents a form of mutation in the genetic algorithm as it will change the schedule regardless of both parents
    public void fillRemainingSchedule(){
        for (Integer key : this.hoursPerSubject.keySet()){
            while (this.hoursPerSubject.get(key) > 0){
                int[] earliestFreePeriod = new int[2];
                System.arraycopy(this.findRandomEmptyPeriod(), 0, earliestFreePeriod, 0, 2);
                this.schedule[earliestFreePeriod[0]][earliestFreePeriod[1]] = new BasicLesson(GlobalsTemp.subjectsObj.get(key), this.classId, getTeacherBySubject(key)); // add the lesson to the slot
                this.hoursPerSubject.put(key, this.hoursPerSubject.get(key)-1); // decrease the number of hours left for the subject
            }
        }
    }

    // find the earliest free period in the schedule and return it as an array of ints (period, day)
    public int[] findEarliestFreePeriod(){
        int[] earliestFreePeriod = new int[2];
        for (int i = 0; i < GlobalsTemp.PERIODS_IN_DAY; i++) {
            for (int j = 0; j < GlobalsTemp.DAYS_IN_WEEK; j++) {
                if (this.schedule[i][j] == null) {
                    earliestFreePeriod[0] = i;
                    earliestFreePeriod[1] = j;
                    return earliestFreePeriod;
                }
            }
        }
        return null;
    }

    public int[] findRandomEmptyPeriod(){
        boolean found = false;
        while (!found) {
            for (int i = 0; i < GlobalsTemp.PERIODS_IN_DAY; i++) {
                for (int j = 0; j < GlobalsTemp.DAYS_IN_WEEK; j++) {
                    if (this.schedule[i][j] == null) {
                        int[] period = {i, j};
                        if (Math.random() < 0.5) {
                            return period;
                        }
                    }
                }
            }
        }
        return null;
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


    // function to place a lesson in the schedule at a given day and period
    public void placeLesson(BasicLesson lesson, int day, int period) {
        if (this.hoursPerSubject.get(lesson.getSubject().getSubjectId()) != 0) {
            this.schedule[period][day] = new BasicLesson(lesson);
            this.hoursPerSubject.put(lesson.getSubject().getSubjectId(), this.hoursPerSubject.get(lesson.getSubject().getSubjectId())-1); // decrease the number of hours for the subject left to be scheduled
        }

    }



    //----------------------------------------------------------------------------------------------------
    // functions for swapping lessons in the schedule, used in the genetic algorithm for swap mutation
    //----------------------------------------------------------------------------------------------------

    // function to return a lesson from the schedule at a given day and period, utilizes deep cloning, returns a new lesson object
    public BasicLesson returnLesson(int day, int period){
        if (this.schedule[period][day] == null){
            return null;
        }
        BasicLesson lesson = new BasicLesson(this.schedule[period][day]); // create a new lesson object with the same values as the lesson in the schedule
        return lesson;
    }


    // function to insert a lesson into the schedule at a given day and period, utilizes deep cloning
    public void insertSwappedLesson(BasicLesson lesson, int day, int period){
        if (lesson == null){
            this.schedule[period][day] = null;
            return;
        }
        this.schedule[period][day] = new BasicLesson(lesson);
    }


    // function to swap two lessons in the schedule
    public void swapLessons(int day1, int period1, int day2, int period2){
        BasicLesson lesson1 = this.returnLesson(day1, period1);
        BasicLesson lesson2 = this.returnLesson(day2, period2);
        this.insertSwappedLesson(lesson2, day1, period1);
        this.insertSwappedLesson(lesson1, day2, period2);
    }

    //----------------------------------------------------------------------------------------------------




    // function to return the hours per subject hashmap
    public HashMap<Integer, Integer> getHoursPerSubject2() {
        return this.hoursPerSubject;
    }

    public int getHoursPerSubject(int subjectId) {
        return this.hoursPerSubject.get(subjectId);
    }

}


