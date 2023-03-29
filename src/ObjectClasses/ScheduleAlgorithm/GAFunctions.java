package ObjectClasses.ScheduleAlgorithm;

import ObjectClasses.TimeTable.ClassSchedule;

public class GAFunctions
{
   private Individual[] population;
   private int populationSize;

   private int classId;


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


}
