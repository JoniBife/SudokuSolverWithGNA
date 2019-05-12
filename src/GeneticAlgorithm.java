import java.util.ArrayList;
import java.util.Arrays;

public class GeneticAlgorithm {

    public static Chromosome[] naturalSelection(Population population){
        //The two fittest will be at the two first positions of the chromosomes array due to sort by fitness function
        return new Chromosome[]{population.chromosomes[0],population.chromosomes[1]};
    }

    public static void rouletteWheelSelection(Population population,int rouletteSize,Chromosome scheme,Double mutationRate){
        ArrayList<Chromosome> roulette = new ArrayList<>(rouletteSize);

        for (int i = 0; i < population.chromosomes.length ; i++) {
            //Calculates how many entries of the roulette will be of the current chromosome
            int numberOfEntries = (int)(population.chromosomes[i].fitness() * rouletteSize);
            for (int j = 0; j < numberOfEntries ; j++) {
                roulette.add(population.chromosomes[i]);
            }
        }

        //Selection, Crossover and mutation
        Chromosome a = selectRandom(null,roulette);
        Chromosome b = selectRandom(a,roulette);
        Chromosome[] children = uniformCrossOver(a,b,scheme);
        mutate(children[0],scheme,mutationRate);
        mutate(children[1],scheme,mutationRate);

        population.chromosomes[population.populationSize - 2] = children[0];
        population.chromosomes[population.populationSize - 1] = children[1];


        /*for (int i = 0; i < population.populationSize; i++) {
            if(a.equals(population.chromosomes[i])) {
                population.chromosomes[i] = children[0];
                break;
            }
        }
        for (int i = 0; i < population.populationSize; i++) {
            if(a.equals(population.chromosomes[i])) {
                population.chromosomes[i] = children[1];
                break;
            }
        }*/

    }

    public static Chromosome selectRandom(Chromosome previous,ArrayList<Chromosome> roulette){
        int randomSelection = (int)(Math.random() * roulette.size());
        Chromosome selection = roulette.get(randomSelection);
        if(previous != null) {
            while (previous.equals(selection)) {
                randomSelection = (int) (Math.random() * roulette.size());
                selection = roulette.get(randomSelection);
            }
        }
        return selection;
    }

    public static Chromosome[] uniformCrossOver(Chromosome a,Chromosome b,final Chromosome scheme){
        int[] schemeGenes = new int[scheme.genes().length];
        for (int i = 0; i < scheme.genes().length ; i++) {
            schemeGenes[i] = scheme.genes()[i];
        }
        int[] schemeGenes2 = new int[scheme.genes().length];
        for (int i = 0; i < scheme.genes().length ; i++) {
            schemeGenes2[i] = scheme.genes()[i];
        }
        int[] mask = new int[schemeGenes.length];
        for (int i = 0; i < scheme.CHROMOSOME_SIZE; i++) {
            mask[i] = Math.random() > 0.5 ? 1 : 0;
        }
        Chromosome[] childs = new Chromosome[2];
        childs[0] = new Chromosome(schemeGenes,scheme.BLOCK_SIDE);
        childs[1] = new Chromosome(schemeGenes2,scheme.BLOCK_SIDE);
        for (int i = 0; i < scheme.CHROMOSOME_SIZE; i++) {
            if(childs[0].genes()[i] == 0) {
                if(mask[i] == 1)
                    childs[0].setGene(i,a.genes()[i]);
                else
                    childs[0].setGene(i,b.genes()[i]);
            }
        }
        for (int i = 0; i < scheme.CHROMOSOME_SIZE; i++) {
            if(childs[0].genes()[i] == 0) {
                if(mask[i] == 0)
                    childs[0].setGene(i,a.genes()[i]);
                else
                    childs[0].setGene(i,b.genes()[i]);
            }
        }
        return childs;
    }

    public static void mutate(Chromosome child,Chromosome scheme,Double mutationRate){
        for (int i = 0; i < child.CHROMOSOME_SIZE; i++) {
            if(!(child.genes()[i] == scheme.genes()[i])) {
                if (Math.random() <= mutationRate) {
                    child.setGene(i,(int) (Math.random() * child.BLOCK_SIZE) + 1);
                }
            }
        }
    }
}
