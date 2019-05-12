import java.util.Arrays;

public class Population {

    public Chromosome[] chromosomes;
    public final int populationSize;

    //Given a scheme generates genotypes based off that scheme
    public Population(int length, Chromosome scheme){
        populationSize = length;
        chromosomes = new Chromosome[length];
        for (int i = 0; i < length; i++) {
            chromosomes[i] = new Chromosome(scheme.BLOCK_SIDE,scheme);
        }
    }

    public  Population(Chromosome[] chromosomes){
        this.chromosomes = chromosomes;
        this.populationSize = chromosomes.length;
    }

    public void calculateFitness(){
        int sumOFConflicts = 0;
        for (int i = 0; i < populationSize; i++) {
            chromosomes[i].calculateConflicts();
            sumOFConflicts += chromosomes[i].conflicts();
        }
        float[] notnormalizedfitness = new float[populationSize];
        for (int i = 0; i < populationSize; i++) {
            notnormalizedfitness[i] = 1 - ((float)chromosomes[i].conflicts()/(float)sumOFConflicts);
        }
        float sumOfNotnormalizedfitness = 0;
        for (int i = 0; i < populationSize; i++) {
            sumOfNotnormalizedfitness += notnormalizedfitness[i];
        }
        for (int i = 0; i < populationSize; i++) {
            chromosomes[i].setFitness(notnormalizedfitness[i]/sumOfNotnormalizedfitness);
        }
    }

    public void sortByFitness(){
        Arrays.sort(chromosomes, (chrs1, chrs2) -> {
            if(chrs1.fitness() > chrs2.fitness())
                return -1;
            else if(chrs1.fitness() < chrs2.fitness())
                return 1;
            return 0;
        });
    }

    public int sumOfConflicts(){
        int sum = 0;
        for (int i = 0; i < chromosomes.length; i++) {
            sum += chromosomes[i].conflicts();
        }
        return sum;
    }

    public void sumOfFitness() {
        float sum = 0;
        for (int i = 0; i < chromosomes.length; i++) {
            sum += chromosomes[i].fitness();
        }
    }

    @Override
    public String toString() {
        /*String ret = "";
        for (int i = 0; i < chromosomes.length; i++) {
            ret += chromosomes[i].toString() + "\n";
        }
        return ret;*/
        return " fitness = " + chromosomes[0].fitness()*100 +
                "% and conflicts = " + chromosomes[0].conflicts();
    }
}
