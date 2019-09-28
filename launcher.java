import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class launcher {
    public static void main(String[]args)
    {
        /*JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(new JFrame());*/
        //File inputFile = fileChooser.getSelectedFile();
        File inputFile = new File("/Users/alex/Desktop/MRP/tsp_example.txt");
        System.out.println("Selected file : "+inputFile.getAbsolutePath());
        ArrayList<Gene> genes = new ArrayList<Gene>();
        try{
            Scanner input = new Scanner(inputFile);
            while (input.hasNextLine())
            {
                String line = input.nextLine();
                String[] line_array = line.split(",");
                genes.add(new Gene(Double.parseDouble(line_array[0]),Double.parseDouble(line_array[1]),line_array[2]));
            }
        }catch (Exception e)
        {
            System.out.println("Error : "+e.getMessage() +" !");
        }
        /*
        * Jusqu'ici, c'est la lecture du fichier et le remplissage de la liste des villes
        * */
        Individual individual = new Individual(genes);
        Individual.problemSize  = genes.size();
        // Initialisation de la premiére solution
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(new Population(individual));
        geneticAlgorithm.population.printIndividuals();
        geneticAlgorithm.startAlgorithm();
        System.out.println("Final population : ");
        geneticAlgorithm.population.printIndividuals();
    }
}
