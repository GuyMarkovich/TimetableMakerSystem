package ObjectClasses.ScheduleAlgorithm;

import ObjectClasses.TimeTable.ClassSchedule;
import ObjectClasses.Data.Globals;

public class Individual {

    private ClassSchedule classSchedule; // the schedule of the individual
    private int fitness; // the fitness of the individual

    private double pickProbability; // the probability of the individual to be picked for crossover

    /** random filled constructor */
    public Individual(int classId)
    {
        this.classSchedule = new ClassSchedule(classId); // create a new schedule for the individual
        this.classSchedule.fillScheduleRandomly(); // fill the schedule randomly
        this.fitness = 100; // set the fitness to 100
        this.pickProbability = 0; // set the pick probability to 0
    }

    /** child constructor, schedule is not filled randomly */
    public Individual(int classId, boolean child)
    {
        this.classSchedule = new ClassSchedule(classId); // create a new schedule for the individual
        this.fitness = 100; // set the fitness to 100
        this.pickProbability = 0; // set the pick probability to 0
    }

    /**clone constructor */
    public Individual(Individual individual){ // clone constructor
        this.classSchedule = new ClassSchedule(individual.classSchedule); // create a new schedule for the individual and clone the schedule of the given individual
        this.fitness = individual.fitness; // set the fitness to the fitness of the given individual
        this.pickProbability = individual.pickProbability; // set the pick probability to the pick probability of the given individual
    }


    /** function to calculate the fitness of the individual */
    public void calculateFitness(){ // calculate fitness of the individual
        this.fitness = 100; // set the fitness to 100
        for (int period = 0; period < Globals.PERIODS_IN_DAY; period++){ // loop through all periods
            for (int day = 0; day < Globals.DAYS_IN_WEEK; day++){ // loop through all days
                if (this.classSchedule.getLesson(day,period) != null) { // if lesson exists in current period
                    if (this.classSchedule.getLesson(day, period).getSubject().getSubjectId() != 0) { // if subject is not free
                        this.fitness -= checkClassConflict(this.classSchedule.getLesson(day, period).getTeacherId(), period, day); // check for conflicts and  subtract the score to the fitness
                    }
                }
            }
        }
        for (int day = 0; day < Globals.DAYS_IN_WEEK; day++){ // loop through all days
            this.fitness -= countSubjectsMultipleNonConsecutiveLessons(day); // count subjects with multiple non-consecutive lessons in a day and subtract the score to the fitness
        }
        for (int day = 0; day < Globals.DAYS_IN_WEEK; day++){ // loop through all days
            this.fitness -= countDailyEmptyLessons(day); // count empty lessons in a day and subtract the score to the fitness (doesn't count empty lessons after the last lesson of the day)
        }
    }

    /** function to check if a class conflict exists in the schedule with the given teacher in the given period and day */
    public int checkClassConflict(int teacherId, int period, int day){ // check for conflicts in the schedule with the given teacher in the given period and day
        if (Globals.teachersObj.get(teacherId).checkAvailableHour(day, period) == 0) // if conflict exists return 5
            return 3; // score to be deducted from fitness for each conflict
        return 0; // if no conflict return 0
    }


    // find conflicts in the schedule and print them to the console for debugging
    public void findClassConflicts(){
        for (int period = 0; period < Globals.PERIODS_IN_DAY; period++){
            for (int day = 0; day < Globals.DAYS_IN_WEEK; day++){
                if (this.classSchedule.getLesson(day,period) != null) {
                    if (Globals.teachersObj.get(this.classSchedule.getLesson(day, period).getTeacherId()).checkAvailableHour(day, period) == 0) { // if conflict exists
                        System.out.println(" conflict for teacher " + this.classSchedule.getLesson(day, period).getTeacherId() + " in period " + period + " day " + day); // print the conflict to the console
                    }
                }
            }
        }
    }



    /** count subjects with multiple non-consecutive lessons in a day
     * meant to be used in the fitness function
     */
    public int countSubjectsMultipleNonConsecutiveLessons(int day) {
        int count = 0;
        for (int subject = 1; subject < 9; subject++) { // loop through all subjects
            boolean multipleLessons = false; // flag to check if there are multiple lessons of the same subject in a day
            boolean nonConsecutive = false; // flag to check if there are multiple non-consecutive lessons of the same subject in a day
            int numLessons = 0;  // number of lessons of the subject in the day
            int previousPeriod = -2; // the previous period of the subject
            for (int period = 0; period < Globals.PERIODS_IN_DAY; period++) {
                if (this.classSchedule.getLesson(day , period) != null){ // if lesson exists in current period
                    if (this.classSchedule.getLesson(day , period).getSubject().getSubjectId() == subject) {
                        numLessons++; // increment the number of lessons of the subject in the day
                        if (numLessons > 1) {  // if there are multiple lessons of the same subject in a day
                            multipleLessons = true; // set the flag to true
                            if (previousPeriod != period - 1) { // if the previous lesson of the subject is not consecutive to the current lesson
                                nonConsecutive = true; // set the flag to true
                            }
                        }
                        previousPeriod = period; // set the previous period to the current period
                    }
                }
            }
            if (multipleLessons && nonConsecutive) { // if there are multiple non-consecutive lessons of the same subject in a day
                count++; // increment the count
            }
        }
        return count * 5; // returns total score to be deducted from fitness, 8 is the score to be deducted for each subject with multiple non-consecutive lessons
    }


    /** count empty lessons in a day and add a penalty for each empty lesson, extra penalty for multiple empty lessons in a row */
    public int countDailyEmptyLessons(int day){
        int count = 0, penalty = 0;
        boolean endOfDayPassed = false; // flag to check if we have passed the end of the day (reached the first non-empty lesson from the end)
        boolean previousLessonEmpty = false;
        for (int period = Globals.PERIODS_IN_DAY - 1; period >= 0; period--) {
            if ((this.classSchedule.getLesson(day, period) != null) && !endOfDayPassed) {
                endOfDayPassed = true; // if we have reached the first non-empty lesson from the end, we set the flag to true
            }
            if (endOfDayPassed && (this.classSchedule.getLesson(day, period) == null)) { // if empty lesson
                count++;
                previousLessonEmpty = true; // if multiple empty lessons in a row, we add an extra penalty
            } else {
                previousLessonEmpty = false; // if not an empty lesson, we reset the flag
            }
            // if previous lesson was empty and this is the current lesson is empty we add an extra penalty
            if ((previousLessonEmpty) && (endOfDayPassed && (this.classSchedule.getLesson(day, period) == null)))
                penalty += 1;

        }
        penalty += count * 2; // returns total score to be deducted from fitness, 2 is the score to be deducted for each empty lesson
        return penalty; // returns total score to be deducted from fitness, 2 is the score to be deducted for each empty lesson
    }


    /** function to retrieve the schedule */
    public ClassSchedule getClassSchedule() {
        return classSchedule;
    } // get the class schedule of the chromosome


    /** function to retrieve the fitness */
    public int getFitness() {
        return fitness;
    } // get the fitness of the chromosome


    /** function to get the pick probability */
    public double getPickProbability() {
        return pickProbability;
    } // get the pick probability of the chromosome

    /** function to set the pick probability */
    public void setPickProbability(double pickProbability) {
        this.pickProbability = pickProbability;
    } // set the pick probability of the chromosome
















}
