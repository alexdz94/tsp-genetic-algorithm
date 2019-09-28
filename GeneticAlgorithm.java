public class GeneticAlgorithm {

    public Population population;


    public GeneticAlgorithm(Population population)
    {
        this.population = population;
    }

    public void startAlgorithm()
    {
        this.population.calculateAllFitness();
        int counter = 0 ;
        while(counter < GAConstants.stopCondition)
        {
            this.population.calculateAllFitness();
            this.population.crossOver();
            updatePopulation(this.population);
            counter++;
            System.out.println("Iteration : "+counter);
        }

    }

    public void updatePopulation(Population population)
    {
        this.population = population;
    }

}
