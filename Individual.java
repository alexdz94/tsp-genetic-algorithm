import java.util.ArrayList;
import java.util.Collections;

public class Individual {

    public ArrayList<Gene> genes = new ArrayList<Gene>();
    public double fitness;
    public static int problemSize ;

    public Individual(ArrayList<Gene> genes)
    {
        this.genes.addAll(genes);
        // On mélange l'ordre des villes pour construire une premiére solution aléatoire
        Collections.shuffle(this.genes);
        calculateFitness();
    }

    public Individual(Individual individual)
    {
        for(int i = 0 ; i< individual.genes.size() ; i++)
        {
            genes.add(individual.genes.get(i));
        }
        calculateFitness();
    }

    public void calculateFitness()
    {
        double fullDistance = 0;
        for (int i = 0 ; i < genes.size();i++)
        {
            if (i+1==genes.size()){
                fullDistance+=genes.get(i-1).distanceBetweenTwoGenes(genes.get(i));
            }else{
                fullDistance+=genes.get(i).distanceBetweenTwoGenes(genes.get(i+1));
            }
        }
        fullDistance+=genes.get(genes.size()-1).distanceBetweenTwoGenes(genes.get(0));
        this.fitness = fullDistance;
    }

    /*
    * Pour afficher le chemin
    * */
    public String printIndividual()
    {
        String str = "";
        str+="{ ";
        for (Gene gene:genes) {
            str+=gene.name+" ";
        }
        str+=" }";

        return str;
    }

}
