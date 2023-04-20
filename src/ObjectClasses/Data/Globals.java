package ObjectClasses.Data;
import ObjectClasses.TimeTable.Subject;
import ObjectClasses.Users.Teacher;

import java.util.Date;
import java.util.HashMap;


// Class that is meant to represent the data available in the database for the application


public class Globals {


    //----------------- TimeTable constraints-----------------
    public static final int DAYS_IN_WEEK = 5; // 5 days in a week to be scheduled
    public static final int PERIODS_IN_DAY = 8; // 8 periods in a day

    public static final int MINIMUM_HOURS_PER_TEACHER = 22; // minimum number of hours a teacher should be available for
    //--------------------------------------------------------


    //--------------- Genetic Algorithm parameters-----------------
    public static final int POPULATION_SIZE = 1000; // number of chromosomes in a population
    public static final int MAX_GENERATIONS = 10000; // maximum number of generations to evolve
    public static final int MAX_FITNESS = 100; // maximum fitness value of a chromosome
    //-----------------------------------------------------------




    //--------------- Data for creating a schedule-----------------


    static int[][] availableHours1 = {
            {1, 0, 0, 1, 1},
            {1, 0, 0, 1, 1}, //22
            {1, 1, 0, 1, 1},
            {1, 1, 0, 1, 1},
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0}
    };

    static int[][] availableHours2 = {
            {0, 0, 1, 1, 0}, //24
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 1},
            {0, 1, 1, 1, 1},
            {0, 1, 1, 1, 1},
            {0, 1, 1, 0, 1},
            {0, 0, 1, 0, 1},
            {0, 0, 1, 0, 1}
    };

    static int[][] availableHours3 = {
            {1, 0, 0, 1, 0},
            {1, 0, 1, 1, 1}, //22
            {1, 0, 1, 1, 1},
            {1, 0, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0}
    };

    static int[][] availableHours4 = {
            {1, 1, 0, 1, 0},
            {1, 1, 1, 1, 0},
            {1, 1, 1, 1, 0}, //22
            {1, 1, 1, 1, 0},
            {1, 1, 1, 0, 0},
            {1, 0, 1, 0, 0},
            {1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0}
    };


    // hash map to hold all the teachers by their id without their available hours (need to add hours when launching the program)
    /*public static HashMap<Integer, Teacher> teachersObj = new HashMap<Integer, Teacher>();
    static {
        teachersObj.put(1,new Teacher("John", "Smith", "123456789", "jsmit@gmail.com", new Date(1990, 1, 1), "123 Main St", 1, new int[]{1, 2}));
        teachersObj.put(2,new Teacher("John", "NotSmith", "123456790", "jnsmit@gmail.com", new Date(1990, 1, 1), "124 Main St", 1, new int[]{4, 7}));
        teachersObj.put(3,new Teacher("Bob", "Jones", "124567509", "bjones@outlook.com", new Date(1991, 12, 12), "21 OS St", 3, new int[]{5, 8}));
        teachersObj.put(4,new Teacher("Sally", "Smith", "983002213", "Ss@gmail.com", new Date(1993, 4, 14), "24 py Dr", 4, new int[]{ 3,6}));

    }*/

    // hash map to hold all the teachers by their id with their available hours (no need to add hours when launching the program, meant mostly for testing
    public static HashMap<Integer, Teacher> teachersObj = new HashMap<Integer, Teacher>();
    static {
        teachersObj.put(1,new Teacher("John", "Smith", "123456789", "jsmit@gmail.com", new Date(1990, 1, 1), "123 Main St", 1, new int[]{1, 2}, availableHours1));
        teachersObj.put(2,new Teacher("John", "NotSmith", "123456790", "jnsmit@gmail.com", new Date(1990, 1, 1), "124 Main St", 1, new int[]{4, 7}, availableHours2));
        teachersObj.put(3,new Teacher("Bob", "Jones", "124567509", "bjones@outlook.com", new Date(1991, 12, 12), "21 OS St", 3, new int[]{5, 8}, availableHours3));
        teachersObj.put(4,new Teacher("Sally", "Smith", "983002213", "Ss@gmail.com", new Date(1993, 4, 14), "24 py Dr", 4, new int[]{ 3,6}, availableHours4));

    }




    //hash map to hold all subjects by their id
    public static HashMap<Integer, Subject> subjectsObj = new HashMap<Integer, Subject>();
    static {
        subjectsObj.put(1, new Subject(1, "Intro to Computer Science", 1));
        subjectsObj.put(2, new Subject(2, "C language", 1));
        subjectsObj.put(3, new Subject(3, "Java", 4));
        subjectsObj.put(4, new Subject(4, "Assembly", 2));
        subjectsObj.put(5, new Subject(5, "Data Structures", 3));
        subjectsObj.put(6, new Subject(6, "Algorithms", 4));
        subjectsObj.put(7, new Subject(7, "SQL", 2));
        subjectsObj.put(8, new Subject(8, "Python", 3));
    }

    public static HashMap<Integer, Integer> teacherBySubject = new HashMap<Integer, Integer>();
    static {
        teacherBySubject.put(1, 1);
        teacherBySubject.put(2, 1);
        teacherBySubject.put(3, 4);
        teacherBySubject.put(4, 2);
        teacherBySubject.put(5, 3);
        teacherBySubject.put(6, 4);
        teacherBySubject.put(7, 2);
        teacherBySubject.put(8, 3);
    }


    // add the number of required hours per week for each subject place in a hashmap, key is subject id
    public static HashMap<Integer, Integer> hoursPerWeek = new HashMap<Integer, Integer>();
    static {
        hoursPerWeek.put(1, 1);
        hoursPerWeek.put(2, 5);
        hoursPerWeek.put(3, 3);
        hoursPerWeek.put(4, 3);
        hoursPerWeek.put(5, 4);
        hoursPerWeek.put(6, 2);
        hoursPerWeek.put(7, 3);
        hoursPerWeek.put(8, 2);
    }

    public static int totalWeekHours = 23; // total number of hours per week for all subjects
    //---------------------------------------------------------------

    //--------------------Login data--------------------------------
    public static HashMap<String, String> loginData = new HashMap<String, String>();
    static{
        loginData.put("admin", "admin");
    }
    //---------------------------------------------------------------

}
