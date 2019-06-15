import java.util.ArrayList;
import java.util.Arrays;

public class GeneticAlgorithm {

    public static Chromosome[] rouletteWheelSelection(Population population,int rouletteSize){
        ArrayList<Chromosome> roulette = new ArrayList<>(rouletteSize);

        for (int i = 0; i < population.chromosomes.length ; i++) {
            //Calculates how many entries of the roulette will be of the current chromosome
            int numberOfEntries = (int)(population.chromosomes[i].fitness() * rouletteSize);
            for (int j = 0; j < numberOfEntries ; j++) {
                roulette.add(population.chromosomes[i]);
            }
        }

        Chromosome a = selectRandom(null,roulette);
        Chromosome b = selectRandom(a,roulette);

        return new Chromosome[]{a,b};
    }

    public static Chromosome[] tournamentSelection(Population population,int tournamentSize){
        Chromosome[] participants = new Chromosome[tournamentSize];

        for (int i = 0; i < tournamentSize; i++) {
            int randomSelection = (int)(Math.random() * population.populationSize);
            participants[i] = population.chromosomes[randomSelection];
        }

        Arrays.sort(participants, (chrs1, chrs2) -> {
            if(chrs1.fitness() > chrs2.fitness())
                return -1;
            else if(chrs1.fitness() < chrs2.fitness())
                return 1;
            return 0;
        });

        return new Chromosome[]{participants[0],participants[1]};
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

    public static Chromosome[] uniformCrossOver(Chromosome a,Chromosome b,Chromosome scheme){
        int[] schemeGenes = new int[scheme.genes().length];
        int[] schemeGenes2 = new int[scheme.genes().length];
        for (int i = 0; i < scheme.genes().length ; i++) {
            schemeGenes[i] = scheme.genes()[i];
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
                if(mask[i] == 1) {
                    childs[0].setGene(i, a.genes()[i]);
                    childs[1].setGene(i, b.genes()[i]);
                } else {
                    childs[0].setGene(i, b.genes()[i]);
                    childs[1].setGene(i, a.genes()[i]);
                }
            }
        }
        return childs;
    }

    public static Chromosome[] singlePointCrossOver(Chromosome a, Chromosome b, Chromosome scheme){
        int chromosomeSize = scheme.CHROMOSOME_SIZE;
        int[] schemeGenes = new int[scheme.genes().length];
        int[] schemeGenes2 = new int[scheme.genes().length];
        for (int i = 0; i < chromosomeSize ; i++) {
            schemeGenes[i] = scheme.genes()[i];
            schemeGenes2[i] = scheme.genes()[i];
        }
        Chromosome[] childs = new Chromosome[2];
        childs[0] = new Chromosome(schemeGenes,scheme.BLOCK_SIDE);
        childs[1] = new Chromosome(schemeGenes2,scheme.BLOCK_SIDE);

        //random crossover point
        int midPoint = (int)(Math.random()*chromosomeSize);

        for (int i = 0; i < chromosomeSize ; i++) {
            childs[0].genes()[i] =  i > midPoint ? a.genes()[i] : b.genes()[i];
            childs[1].genes()[i] =  i > midPoint ? b.genes()[i] : a.genes()[i];
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

    public static void addToPopulation(Chromosome[] children,Population population){
        //Double random = Math.random();

        population.chromosomes[population.populationSize - 2] = children[0];
        population.chromosomes[population.populationSize - 1] = children[1];
        /*} else {
            population.chromosomes[(int) (Math.random() * population.populationSize)] = children[0];
            population.chromosomes[(int) (Math.random() * population.populationSize)] = children[1];
        }*/


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
}
