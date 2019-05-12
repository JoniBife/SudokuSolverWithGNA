public class App {

    public static final double MUTATION_RATE = 0.05;
    public static final int POPULATION_SIZE = 30;

    public static final Chromosome SCHEME4X4 = new Chromosome(
            new int[]{
                    0,2,0,0,
                    4,0,0,2,
                    0,0,2,0,
                    0,1,0,0
            },2);
    public static final Chromosome SCHEME9X9 = new Chromosome(
            new int[]{
                    5,3,0,0,7,0,0,0,0,
                    6,0,0,1,9,5,0,0,0,
                    0,9,8,0,0,0,0,6,0,
                    8,0,0,0,6,0,0,0,3,
                    4,0,0,8,0,3,0,0,1,
                    7,0,0,0,2,0,0,0,6,
                    0,6,0,0,0,0,2,8,0,
                    0,0,0,4,1,9,0,0,5,
                    0,0,0,0,8,0,0,7,9},
        3);

    public static final Chromosome EASYSCHEME9X9 = new Chromosome(
            new int[]{
                    4,1,0,2,7,0,8,0,5,
                    0,8,5,1,4,6,0,9,7,
                    0,7,0,5,8,0,0,4,0,
                    9,2,7,4,5,1,3,8,6,
                    5,3,8,6,9,7,4,1,2,
                    1,6,4,3,2,8,7,5,9,
                    8,5,2,7,0,4,9,0,0,
                    0,9,0,8,0,2,5,7,4,
                    7,4,0,9,6,5,0,2,8},
            3);

    public static void main(String[] args) {

        Population p = new Population(POPULATION_SIZE,EASYSCHEME9X9);
        p.calculateFitness();
        p.sortByFitness();
        p.sumOfFitness();

        int i = 0;
        System.out.println(p);
        while(!checkSolution(p)){
            GeneticAlgorithm.rouletteWheelSelection(p,1000,EASYSCHEME9X9,MUTATION_RATE);
            p.calculateFitness();
            p.sortByFitness();
            //p.sumOfFitness();
            ++i;
            if(i == 15000) {
                p = new Population(POPULATION_SIZE, EASYSCHEME9X9);
                p.calculateFitness();
                p.sortByFitness();
                i = 0;
            }
            System.out.println(p);
        }
        System.out.println("---------------------- GENERATION " + i + " ----------------------" );

    }

    public static boolean checkSolution(Population population){
        Chromosome[] chromosomes = population.chromosomes;
        for (int i = 0; i < chromosomes.length; i++) {
            if(chromosomes[i].conflicts() == 0) {
                System.out.println();
                System.out.println(" -----------------THE FUCKING SOLUTION-----------------");
                System.out.println(chromosomes[i]);
                return true;
            }
        }
        return false;
    }
}
