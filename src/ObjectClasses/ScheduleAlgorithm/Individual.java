package ObjectClasses.ScheduleAlgorithm;

import ObjectClasses.TimeTable.ClassSchedule;
import ObjectClasses.Users.GlobalsTemp;

public class Individual {

    private ClassSchedule classSchedule;
    private int fitness;

    public Individual(int classId)
    {
        this.classSchedule = new ClassSchedule(classId);
        this.classSchedule.fillScheduleRandomly();
        this.fitness = 100;
    }

    public void calculateFitness(){ // calculate fitness of the individual
        this.fitness = 100;
        for (int period = 0; period < GlobalsTemp.PERIODS_IN_DAY; period++){
            for (int day = 0; day < GlobalsTemp.DAYS_IN_WEEK; day++){
                if (this.classSchedule.getLesson(day,period) != null) {
                    if (this.classSchedule.getLesson(day, period).getSubject().getSubjectId() != 0) {
                        this.fitness -= checkClassConflict(this.classSchedule.getLesson(day, period).getTeacherId(), period, day);
                    }
                }
            }
        }
        for (int day = 0; day < GlobalsTemp.DAYS_IN_WEEK; day++){
            this.fitness -= countSubjectsMultipleNonConsecutiveLessons(day);
        }
        for (int day = 0; day < GlobalsTemp.DAYS_IN_WEEK; day++){
            this.fitness -= checkEmptyLessons(day);
        }
        for (int day = 0; day < GlobalsTemp.DAYS_IN_WEEK; day++){
            this.fitness -= checkEmptyConsecutiveLessons(day);
        }
    }

    public int checkClassConflict(int teacherId, int period, int day){
        if (GlobalsTemp.teachersObj.get(teacherId).checkAvailableHour(day, period) != 0) // if conflict exists return 5
            return 5; // score to be deducted from fitness for each conflict
        return 0; // if no conflict return 0
    }

    // count subjects with multiple non consecutive lessons in a day
    public int countSubjectsMultipleNonConsecutiveLessons(int day) {
        int count = 0;
        for (int subject = 1; subject < 9; subject++) {
            boolean multipleLessons = false;
            boolean nonConsecutive = false;
            int numLessons = 0;
            int previousPeriod = -2;
            for (int period = 0; period < GlobalsTemp.PERIODS_IN_DAY; period++) {
                if (this.classSchedule.getLesson(day , period) != null){ // if lesson exists
                    if (this.classSchedule.getLesson(day , period).getSubject().getSubjectId() == subject) {
                        numLessons++;
                        if (numLessons > 1) {
                            multipleLessons = true;
                            if (previousPeriod != period - 1) {
                                nonConsecutive = true;
                            }
                        }
                        previousPeriod = period;
                    }
                }
            }
            if (multipleLessons && nonConsecutive) {
                count++;
            }
        }
        return count * 8; // returns total score to be deducted from fitness, 8 is the score to be deducted for each subject with multiple non consecutive lessons
    }

    public int checkEmptyLessons(int day){
        int count = 0;
        for (int period = 0; period < GlobalsTemp.PERIODS_IN_DAY; period++){
            if (this.classSchedule.getLesson(day, period) == null){
                count++;
            }
        }
        return count*2; // returns total score to be deducted from fitness, 2 is the score to be deducted for each empty lesson
    }

    public int checkEmptyConsecutiveLessons(int day){
        int count = 0;
        int consecutiveCount = 0;
        for (int period = 0; period < GlobalsTemp.PERIODS_IN_DAY; period++){
            if (this.classSchedule.getLesson(day, period) == null){

                consecutiveCount++;
                if (consecutiveCount == 2) {
                    count += 7;
                }
            } else {
                consecutiveCount = 0;
            }
        }
        return count; // returns total score to be deducted from fitness, 2 is the score to be deducted for each empty lesson
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    public int getFitness() {
        return fitness;
    }



}
