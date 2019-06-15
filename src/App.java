public class App {

    public static final double MUTATION_RATE = 0.08;
    public static final int POPULATION_SIZE = 500;
    public static final int ROULETTE_SIZE = POPULATION_SIZE*50;
    public static final int TOURNAMENT_SIZE = POPULATION_SIZE/10;

    public static final Chromosome SCHEME4X4 = new Chromosome(
            new int[]{
                    0,2,0,0,
                    4,0,0,0,
                    0,0,2,0,
                    0,1,0,0
            },2);

    public static final Chromosome SCHEME4X4EMPTY = new Chromosome(
            new int[]{
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0
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

    public static final Chromosome EMPTYSCHEME9X9 = new Chromosome(
            new int[]{
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0},
            3);

    public static void main(String[] args) {
        //solveSudoku(SCHEME4X4);
        //solveSudoku(SCHEME4X4EMPTY);
        //solveSudoku(EASYSCHEME9X9);
        solveSudoku(SCHEME9X9);
        //solveSudoku(EMPTYSCHEME9X9);
    }
    public static void solveSudoku(Chromosome scheme){
        Population p = new Population(POPULATION_SIZE,scheme);
        p.calculateFitness();
        p.sortByFitness();

        int i = 0;
        int j = 0;
        System.out.println(p);
        while(!checkSolution(p)){

            //Selection
            Chromosome[] parents = GeneticAlgorithm.rouletteWheelSelection(p,ROULETTE_SIZE);
            //Chromosome[] parents = GeneticAlgorithm.tournamentSelection(p,TOURNAMENT_SIZE);
            //Crossover
            Chromosome[] children = GeneticAlgorithm.uniformCrossOver(parents[0],parents[1],scheme);
            //Mutation
            GeneticAlgorithm.mutate(children[0],scheme,MUTATION_RATE);
            GeneticAlgorithm.mutate(children[1],scheme,MUTATION_RATE);
            //Adding new children to population
            GeneticAlgorithm.addToPopulation(children,p);
            //Recalculating fitness of the new population and sorting according to it
            p.calculateFitness();
            p.sortByFitness();
            ++i;
            System.out.println(p + " generation = " + i);
        }
        System.out.println("---------------------- GENERATION " + i + " ----------------------" );
        System.out.println("Took " + j + "  retries");
    }

    public static boolean checkSolution(Population population){
            if(population.chromosomes[0].conflicts() == 0) {
                System.out.println();
                System.out.println(" -----------------THE SOLUTION-----------------");
                System.out.println(population.chromosomes[0]);
                return true;
            }
            return false;
    }

    public static Population newElites(Population population,int numbOfElites){
        Population p = new Population(POPULATION_SIZE, SCHEME9X9);
        p.sortByFitness();

        int j = POPULATION_SIZE - 1;
        for (int i = 0 ; i < numbOfElites; i++,j--) {
            p.chromosomes[j] = population.chromosomes[i];
        }

        return p;
    }
}
