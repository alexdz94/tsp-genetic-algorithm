import java.util.*;

public class Population {

    public ArrayList<Individual> populationList = new ArrayList<Individual>();

    public Population(Individual individual)
    {
        for(int i = 0 ; i< GAConstants.populationSize ; i++)
        {
            Collections.shuffle(individual.genes);
            populationList.add(new Individual(individual));
        }
    }

    public void updatePopulationList(ArrayList<Individual> populationList)
    {
        this.populationList = new ArrayList<Individual>();
        this.populationList.addAll(populationList);
    }

    public void crossOver()
    {
        Random rand = new Random();
        ArrayList<Individual> newPopulation = new ArrayList<Individual>();

        bigLoop :
        while(newPopulation.size() < populationList.size())
        {
            calculateAllFitness();
            // Selection de 2 individus (parents) // J'ai choisis la selection par tournoi

            Individual individual1 = new Individual(tournamentSelect());
            Individual individual2 = new Individual(tournamentSelect());

            while(isTheSameParents(individual1,individual2)){
                individual1 = new Individual(tournamentSelect());
                individual2 = new Individual(tournamentSelect());
            }

            System.out.println("_____________________________________________________________________________");
            System.out.println("Parent 1 : " + individual1.printIndividual()+" { "+individual1.fitness+ " km } ");
            System.out.println("Parent 2 : " + individual2.printIndividual()+" { "+individual2.fitness+ " km } ");

            double prob = new Random().nextDouble();

            if((prob/100) > GAConstants.acceptMutationProbability)
            {
                System.out.println("Mutation ....");
                // mutation (On fait une mutuation d'un nombre aléatoire des génes)
                Individual finalIndiv1 = mutation(individual1);
                Individual finalIndiv2 = mutation(individual2);
                System.out.println("Mutation Result 1: " + finalIndiv1.printIndividual()+" { "+finalIndiv1.fitness+ " km } ");
                System.out.println("Mutation Result 2: " + finalIndiv2.printIndividual()+" { "+finalIndiv2.fitness+ " km } ");
                newPopulation.add(finalIndiv1);
                if(newPopulation.size()==this.populationList.size()) break bigLoop;
                newPopulation.add(finalIndiv2);
                if(newPopulation.size()==this.populationList.size()) break bigLoop;
            }else{
                // crossover
                ArrayList<Individual> crossed = cross2individual(individual1,individual2);
                for(Individual ind : crossed)
                {
                    newPopulation.add(ind);
                    if(newPopulation.size()==this.populationList.size()) break bigLoop;
                }
            }
        }
        updatePopulationList(newPopulation);
        System.out.println("New Population Created!");
    }

    public Individual mutation(Individual individual)
    {
        int r1 = 0 ;
        int r2 = 0 ;
        while(r1==r2)
        {
            r1 = new Random().nextInt(individual.genes.size()-1);
            r2 = new Random().nextInt(individual.genes.size()-1);
        }
        Gene gene_1 = individual.genes.get(r1);
        Gene gene_2 = individual.genes.get(r2);
        individual.genes.set(r1,gene_2);
        individual.genes.set(r2,gene_1);
        return new Individual(individual);
    }

    public void sortByFitness()
    {
        int n = this.populationList.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (this.populationList.get(j).fitness > this.populationList.get(j+1).fitness)
                {
                    Individual temp = this.populationList.get(j);
                    this.populationList.set(j,this.populationList.get(j+1));
                    this.populationList.set(j+1,temp);
                }
    }

    public void printIndividuals()
    {
        for(Individual individual : this.populationList)
        {
            System.out.println(individual.printIndividual()+" - "+(individual.fitness));
        }
    }

    public void calculateAllFitness()
    {
        for(int i  = 0 ; i< this.populationList.size()-1 ; i++)
        {
            this.populationList.get(i).calculateFitness();
        }
    }

    public Individual getBestCandidate(ArrayList<Individual> candidate)
    {
        Individual individual = candidate.get(0);
        for(int i = 1 ; i < candidate.size()-1;i++)
        {
            if(candidate.get(i).fitness > individual.fitness)
                individual = candidate.get(i);
        }
        return individual;
    }

    public int getGeneIndex(String name,Individual individual)
    {
        int index = 0 ;
        for(int i =0 ; i < individual.genes.size();i++)
        {
            if (individual.genes.get(i).name.equals(name))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    public ArrayList<Individual> cross2individual(Individual individual1 , Individual individual2) {
        int index = new Random().nextInt(individual1.genes.size()-1);
        ArrayList<Individual> childs = new ArrayList<Individual>();
        for(int i = 0 ; i < index ; i++)
        {
            int random1 = new Random().nextInt(individual1.genes.size()-1);
            Gene gene1 = individual1.genes.get(random1);
            int random2 = new Random().nextInt(individual2.genes.size()-1);
            Gene gene2 = individual2.genes.get(random2);
            //
            int indexInd1 = getGeneIndex(gene2.name,individual1);
            int indexInd2 = getGeneIndex(gene1.name,individual2);
            individual1.genes.set(indexInd1,gene2);
            individual2.genes.set(indexInd2,gene1);
        }
        System.out.println("####### Crossed : "+index+" genes.");
        System.out.println("####### Childs : ");
        individual1 = new Individual(individual1.genes);
        individual2 = new Individual(individual2.genes);
        System.out.println("Child 1 : "+individual1.printIndividual()+" { "+individual1.fitness+ " km } ");
        System.out.println("Child 2 : "+individual2.printIndividual()+" { "+individual2.fitness+ " km } ");
        System.out.println("________________________________________________");
        childs.add(new Individual(individual1));
        childs.add(new Individual(individual2));
        return childs;
    }

    public Individual tournamentSelect()
    {
        ArrayList<Individual> candidates = new ArrayList<Individual>();
        ArrayList<Integer> buffer = new ArrayList<Integer>();

        for (int i = 0 ; i < GAConstants.tournamentSelect ;i++)
        {
            int r = new Random().nextInt(this.populationList.size()-1);
            while (buffer.contains(r)) r = new Random().nextInt(this.populationList.size()-1);
            candidates.add(this.populationList.get(r));
        }

        Individual best = getBestCandidate(candidates);
        return best;
    }

    public boolean isTheSameParents(Individual individual1 , Individual individual2)
    {
        int counter = 0 ;
        for(int i = 0 ; i < individual1.genes.size();i++)
        {
            if(individual1.genes.get(i).name.equals(individual2.genes.get(i).name))
                counter++;
        }
        return (counter == individual1.genes.size());
    }
}
