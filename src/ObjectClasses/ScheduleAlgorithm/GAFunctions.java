package ObjectClasses.ScheduleAlgorithm;

import ObjectClasses.Data.Globals;

public class GAFunctions
{
   private Individual[] population; // population of individuals
   private int populationSize; // size of the population to be generated

   private int classId; // id of the class to be scheduled

   private final double mutationProbability = 0.05; // probability of mutation

   private int totalFitness; // total fitness of the population


    /** Constructor
     * @param populationSize size of the population to be generated
     * @param classId id of the class to be scheduled
     */
    public GAFunctions(int populationSize, int classId)
    {
        this.populationSize = populationSize; // set population size
        this.classId = classId; // set class id
        population = new Individual[populationSize]; // initialize population
        this.totalFitness = 0; // initialize total fitness to 0
    }


    /**
     *  function to initialize the population, create individuals and calculate their fitness, and set their pick probability
     */
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






    /** function to perform crossover of two individuals */

    public Individual crossOver2(Individual parent1, Individual parent2) { // index1 and index2 are the indexes of the individuals to be crossed over
        Individual child = new Individual(classId, true); // create a new individual
        int crossOverCoordX = (int) (Math.random() * Globals.DAYS_IN_WEEK); // random coordinates for crossover
        int crossOverCoordY = (int) (Math.random() * Globals.PERIODS_IN_DAY); // random coordinates for crossover

        for (int i = 0; i < Globals.DAYS_IN_WEEK; i++) {
            for (int j = 0; j < Globals.PERIODS_IN_DAY; j++) {
                if (i < crossOverCoordX || (i == crossOverCoordX && j <= crossOverCoordY)) { // if the coordinates are less than the crossover coordinates then copy from parent 1
                    if (parent1.getClassSchedule().getLesson(i, j) != null) {
                        if (child.getClassSchedule().getHoursPerSubject(parent1.getClassSchedule().getLesson(i, j).getSubject().getSubjectId()) != 0) { // check if the subject is already present in the child, if no more lessons are available for that subject then do not copy
                            child.getClassSchedule().placeLesson(parent1.getClassSchedule().getLesson(i, j), i, j); // place the lesson in the child
                        }
                    }
                } else {
                    if (parent2.getClassSchedule().getLesson(i, j) != null) {
                        if (child.getClassSchedule().getHoursPerSubject(parent2.getClassSchedule().getLesson(i, j).getSubject().getSubjectId()) != 0) { // check if the subject is already present in the child, if no more lessons are available for that subject then do not copy
                            child.getClassSchedule().placeLesson(parent2.getClassSchedule().getLesson(i, j), i, j); // place the lesson in the child
                        }
                    }
                }
            }
        }
        child.getClassSchedule().fillRemainingSchedule(); // if the hash map is not empty then fill the remaining lessons by finding an available empty slot
        child.calculateFitness(); // calculate the fitness of the child
        return child; // return the child
    }






    /** function to determine if an individual should mutate based  on the mutation probability*/
    public boolean shouldMutate()
    {
        return Math.random() < mutationProbability; // return true if random number is less than mutation probability
    }

    /** function to perform swap mutation on an individual
     *  switches the position of two lessons in the schedule
     */
    public void swapMutation(Individual individual)
    {
        int day1 = (int) (Math.random() * Globals.DAYS_IN_WEEK); // random coordinates for mutation
        int period1 = (int) (Math.random() * Globals.PERIODS_IN_DAY); // random coordinates for mutation
        int day2 = (int) (Math.random() * Globals.DAYS_IN_WEEK); // random coordinates for mutation
        int period2 = (int) (Math.random() * Globals.PERIODS_IN_DAY); // random coordinates for mutation
        individual.getClassSchedule().swapLessons(day1,period1,day2,period2);
        individual.calculateFitness();
    }

    /** set pick probability of an individual  */
    public void setPickProbability()
    {
        for (int i = 0; i < populationSize; i++)
        {
            population[i].setPickProbability((double) population[i].getFitness() / (double) this.totalFitness); // set pick probability of each individual
        }
    }



    /** function to pick an individual for crossover based on pick probability, simulates a roulette wheel */
    public Individual spinRouletteWheel() { // returns an individual from the population based on the pick probability, mimics the roulette wheel selection
        double spin = Math.random(); // spin the roulette wheel
        double cumulativeProbability = 0.0; // cumulative probability
        for (Individual individual : population) { // for each individual in the population
            cumulativeProbability += individual.getPickProbability(); // add the pick probability to the cumulative probability
            if (spin <= cumulativeProbability) { // if the spin is less than the cumulative probability then return the current individual
                return individual; // return the individual
            }
        }
        return null; // return null if no individual is found, this should never happen
    }



    /** function to perform one cycle of crossover and mutation on the population */
    public void cycle(){
        // create a new population
        Individual[] newPopulation = new Individual[populationSize];
        // set the total fitness to 0
        this.totalFitness = 0;
        // current best individual
        Individual currentBest = this.getBestIndividual();
        // insert the current best individual into the new population
        newPopulation[0] = currentBest;
        // for each individual in the population
        for (int i = 1; i < populationSize; i++)
        {
            // pick two individuals from the population based on pick probability and a random spin
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


    /** function to find the best individual in the population */
    public Individual getBestIndividual()
    {
        int bestFitness = 0; // best fitness
        int bestIndex = 0; // index of the best individual
        for (int i = 0; i < populationSize; i++) // for each individual in the population
        {
            if (population[i].getFitness() > bestFitness) // if the fitness of the current individual is greater than the best fitness
            {
                bestFitness = population[i].getFitness(); // set the best fitness to the current fitness
                bestIndex = i; // set the best index to the current index
            }
        }
        System.out.println("Best fitness: " + bestFitness); // print the best fitness for testing purposes
        return population[bestIndex]; // return the best individual
    }


    /** function to evolve generations until a solution is found or the maximum number of generations is reached */
    public void evolve()
    {
        int cnt = 0; // counter for the number of generations
        boolean solutionFound = false; // boolean to check if a solution has been found
        while (cnt < Globals.MAX_GENERATIONS && !solutionFound)
        {
            if (this.getBestIndividual().getFitness() == Globals.MAX_FITNESS) { // if the best individual has a fitness of 100 then a solution has been found
                System.out.println("Solution found in " + (cnt+1) + " generations");
                solutionFound = true;
            }
            if (!solutionFound) {
                this.cycle(); // cycle the population
                cnt++; // increment the counter
            }
        }

    }


}
