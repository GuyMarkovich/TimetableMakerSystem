package ObjectClasses.ScheduleAlgorithm;

import ObjectClasses.TimeTable.ClassSchedule;
import ObjectClasses.Users.GlobalsTemp;

public class GAFunctions
{
   private Individual[] population; // population of individuals
   private int populationSize; // size of the population to be generated

   private int classId; // id of the class to be scheduled

   private final double mutationProbability = 0.05; // probability of mutation

   private int totalFitness; // total fitness of the population


    // Constructor
    public GAFunctions(int populationSize, int classId)
    {
        this.populationSize = populationSize; // set population size
        this.classId = classId; // set class id
        population = new Individual[populationSize]; // initialize population
        this.totalFitness = 0; // initialize total fitness to 0
    }

    public void initializePopulation()
    {
        for (int i = 0; i < populationSize; i++)
        {
            population[i] = new Individual(classId); // create a new individual
            population[i].calculateFitness(); // calculate fitness of the individual
            this.totalFitness += population[i].getFitness(); // add the fitness to the total fitness
        }
        this.setPickProbability(); // set the pick probability of each individual in the population
    }



    // return fitness of the individual at index
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


    public Individual crossOver2(Individual parent1, Individual parent2) {
        Individual child = new Individual(classId, true);
        int crossOverCoordX = (int) (Math.random() * GlobalsTemp.DAYS_IN_WEEK);
        int crossOverCoordY = (int) (Math.random() * GlobalsTemp.PERIODS_IN_DAY);

        for (int i = 0; i < GlobalsTemp.DAYS_IN_WEEK; i++) {
            for (int j = 0; j < GlobalsTemp.PERIODS_IN_DAY; j++) {
                if (i < crossOverCoordX || (i == crossOverCoordX && j <= crossOverCoordY)) {
                    if (parent1.getClassSchedule().getLesson(i, j) != null) {
                        if (child.getClassSchedule().getHoursPerSubject(parent1.getClassSchedule().getLesson(i, j).getSubject().getSubjectId()) != 0) {
                            child.getClassSchedule().placeLesson(parent1.getClassSchedule().getLesson(i, j), i, j);
                        }
                    }
                } else {
                    if (parent2.getClassSchedule().getLesson(i, j) != null) {
                        if (child.getClassSchedule().getHoursPerSubject(parent2.getClassSchedule().getLesson(i, j).getSubject().getSubjectId()) != 0) {
                            child.getClassSchedule().placeLesson(parent2.getClassSchedule().getLesson(i, j), i, j);
                        }
                    }
                }
            }
        }
        child.getClassSchedule().fillRemainingSchedule();
        child.calculateFitness();
        return child;
    }







    // function to return a boolean on whether to mutate or not based on the mutation probability
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

    // function to set the pick probability of each individual in the population
    public void setPickProbability()
    {
        for (int i = 0; i < populationSize; i++)
        {
            population[i].setPickProbability((double) population[i].getFitness() / (double) this.totalFitness); // set pick probability of each individual
        }
    }

    // function to return the total fitness of the population
    public int getTotalFitness()
    {
        return this.totalFitness;
    }

    // function to return the pick probability of the individual at index
    public double getPickProbability(int index)
    {
        return population[index].getPickProbability();
    }


    // function to pick an individual from the population based on the pick probability
    public Individual spinRouletteWheel() {
        double spin = Math.random();
        double cumulativeProbability = 0.0;
        for (Individual individual : population) {
            cumulativeProbability += individual.getPickProbability();
            if (spin <= cumulativeProbability) {
                return individual;
            }
        }
        return null;
    }


    public void cycle(){
        // create a new population
        Individual[] newPopulation = new Individual[populationSize];
        // set the total fitness to 0
        this.totalFitness = 0;
        // for each individual in the population
        for (int i = 0; i < populationSize; i++)
        {
            // pick two individuals from the population
            Individual parent1 = this.spinRouletteWheel();
            Individual parent2 = this.spinRouletteWheel();
            // cross over the two individuals
            Individual child = this.crossOver2(parent1,parent2);
            // if the child should mutate then mutate it
            if (this.shouldMutate())
                this.swapMutation(child);
            // add the child to the new population
            newPopulation[i] = child;
            // add the fitness of the child to the total fitness
            this.totalFitness += child.getFitness();
        }

        // set the population to the new population
        for (int i = 0; i < populationSize; i++)
        {
            population[i] = new Individual(newPopulation[i]); // copy the new population to the old population by value
        }
        // set the pick probability of each individual in the new population
        this.setPickProbability();
    }


    // function to find the best individual in the population
    public Individual getBestIndividual()
    {
        int bestFitness = 0;
        int bestIndex = 0;
        for (int i = 0; i < populationSize; i++)
        {
            if (population[i].getFitness() > bestFitness)
            {
                bestFitness = population[i].getFitness();
                bestIndex = i;
            }
        }
        System.out.println("Best fitness: " + bestFitness);
        return population[bestIndex];
    }


    //function to evolve 1000 generations
    public void evolve()
    {
        for (int i = 0; i < GlobalsTemp.MAX_GENERATIONS; i++)
        {
            if (this.getBestIndividual().getFitness() == GlobalsTemp.MAX_FITNESS) {
                System.out.println("Solution found in " + i + " generations");
                break;
            }
            this.cycle(); // cycle the population
        }

    }


}
