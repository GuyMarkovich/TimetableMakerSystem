package ObjectClasses.ScheduleAlgorithm;

import ObjectClasses.TimeTable.ClassSchedule;
import ObjectClasses.Users.GlobalsTemp;

public class GAFunctions
{
   private Individual[] population;
   private int populationSize;

   private int classId;

   private final double mutationProbability = 0.05; // probability of mutation

    public GAFunctions(int populationSize, int classId)
    {
        this.populationSize = populationSize;
        this.classId = classId;
        population = new Individual[populationSize];
    }

    public void initializePopulation()
    {
        for (int i = 0; i < populationSize; i++)
        {
            population[i] = new Individual(classId);
            population[i].calculateFitness();
        }
    }

    public int returnFitness(int index)
    {
        return population[index].getFitness();
    }


    // cross over function for two individuals

    public Individual crossOver(int index1, int index2) // index1 and index2 are the indexes of the individuals to be crossed over
    {
        Individual child = new Individual(classId, true);
        int crossOverCoordX = (int) (Math.random() * GlobalsTemp.DAYS_IN_WEEK); // random coordinates for crossover
        int crossOverCoordY = (int) (Math.random() * GlobalsTemp.PERIODS_IN_DAY); // random coordinates for crossover
        for (int i = 0; i < GlobalsTemp.DAYS_IN_WEEK; i++)
        {
            for (int j = 0; j < GlobalsTemp.PERIODS_IN_DAY; j++)
            {
                if (i < crossOverCoordX || (i == crossOverCoordX && j <= crossOverCoordY)) // if the coordinates are less than the crossover coordinates then copy from parent 1
                {
                    // check if the subject is already present in the child, if no more lessons are available for that subject then do not copy
                    if (population[index1].getClassSchedule().getLesson(i,j) != null)
                        if (child.getClassSchedule().getHoursPerSubject(population[index1].getClassSchedule().getLesson(i,j).getSubject().getSubjectId()) != 0)
                            child.getClassSchedule().placeLesson(population[index1].getClassSchedule().getLesson(i, j),i,j);

                }
                else // if the coordinates are greater than the crossover coordinates then copy from parent 2
                {
                    // check if the subject is already present in the child, if no more lessons are available for that subject then do not copy
                    if (population[index2].getClassSchedule().getLesson(i,j) != null)
                        if (child.getClassSchedule().getHoursPerSubject(population[index2].getClassSchedule().getLesson(i,j).getSubject().getSubjectId()) != 0)
                            child.getClassSchedule().placeLesson(population[index2].getClassSchedule().getLesson(i, j),i,j);

                }
            }
        }
        // if the hash map is not empty then fill the remaining lessons by finding the first available empty slot
        child.getClassSchedule().fillRemainingSchedule();


        child.calculateFitness();
        return child;
    }

    // function to return a boolean on whether to mutate or not
    public boolean shouldMutate()
    {
        return Math.random() < mutationProbability; // return true if random number is less than mutation probability
    }

    //swap mutation function, swaps two lessons in the schedule randomly if the mutation probability is true
    public void swapMutation(Individual individual)
    {
        int day1 = (int) (Math.random() * GlobalsTemp.DAYS_IN_WEEK); // random coordinates for mutation
        int period1 = (int) (Math.random() * GlobalsTemp.PERIODS_IN_DAY); // random coordinates for mutation
        int day2 = (int) (Math.random() * GlobalsTemp.DAYS_IN_WEEK); // random coordinates for mutation
        int period2 = (int) (Math.random() * GlobalsTemp.PERIODS_IN_DAY); // random coordinates for mutation
        individual.getClassSchedule().swapLessons(day1,period1,day2,period2);
        individual.calculateFitness();
    }



}
