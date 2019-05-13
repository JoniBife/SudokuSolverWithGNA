import java.util.Arrays;

public class Chromosome {

    private float fitness = 0;
    private int conflicts = 0;
    private int[] genes;
    public final int CHROMOSOME_SIZE;
    public final int BLOCK_SIZE;
    public final int BLOCK_RATIO;
    public final int BLOCK_SIDE;

    public Chromosome(int blockSide, Chromosome scheme){
        CHROMOSOME_SIZE = blockSide*blockSide*blockSide*blockSide;
        BLOCK_SIDE = blockSide;
        BLOCK_SIZE = blockSide*blockSide;
        BLOCK_RATIO = BLOCK_SIZE - blockSide;
        genes = new int[CHROMOSOME_SIZE];
        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
            if(scheme.genes[i] == 0)
                //if Math.Random generates 0, I am not putting zero
                genes[i] = (int)(Math.random() * BLOCK_SIZE) + 1;
            else
                genes[i] = scheme.genes[i];
        }
    }

    public Chromosome(int[] genes,int blockSide){
        CHROMOSOME_SIZE = genes.length;
        BLOCK_SIZE = blockSide * blockSide;
        BLOCK_RATIO = BLOCK_SIZE - blockSide;
        BLOCK_SIDE = blockSide;
        this.genes = genes;
    }

    public void calculateConflicts() {
        conflicts = 0;
        checkBlocks();
        checkLines();
        checkCols();
    }

    public void checkBlocks(){
        int i = 0;
        int x = 1;
        while(i < CHROMOSOME_SIZE) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                int[] block = new int[BLOCK_SIZE];
                    //This fills the block
                for (int k = 0; k < BLOCK_SIDE; k++) {
                    for (int f = 0; f < BLOCK_SIDE; f++,i++,j++) {
                        block[j] = genes[i];
                    }
                    --i;
                    if(k < BLOCK_SIDE - 1)
                        i += BLOCK_SIDE*(BLOCK_SIDE-1) + 1;
                }
                if(x == BLOCK_SIDE) {
                    ++i;
                    x = 1;
                } else {
                    i -= BLOCK_SIZE*(BLOCK_SIDE-1) - 1;
                    ++x;
                }
                checkLine(block);
            }
        }

    }

    private void checkCols(){
        for (int i = 0; i < BLOCK_SIZE; i++) {
            int[] col = new int[BLOCK_SIZE];
            int k = 0;
            for (int j = i; j < CHROMOSOME_SIZE; j+=BLOCK_SIZE) {
                col[k] = genes[j];
                ++k;
            }
            checkLine(col);
        }
    }

    private void checkLines(){
        for (int i = 0; i < CHROMOSOME_SIZE; i+=BLOCK_SIZE) {
            int[] line = new int[BLOCK_SIZE];
            System.arraycopy(genes,i,line,0,BLOCK_SIZE);
            checkLine(line);
        }
    }

    private void checkLine(int[] line){
        for (int i = 0; i < line.length-1; i++) {
            for (int j = i+1; j < line.length; j++) {
                if (line[i] == line[j]) {
                    ++conflicts;
                }
            }
        }
    }

    public int conflicts(){ return conflicts;}

    public float fitness() {
        return fitness;
    }
    public void setFitness(float fitness){
        this.fitness = fitness;
    }
    public int[] genes() {
        return genes;
    }

    public void setGene(int i,int value){
        genes[i] = value;
    }


    @Override
    public String toString() {
        String tostr = "";
        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
            if(i % BLOCK_SIZE == 0)
                System.out.println();
            System.out.print(genes[i] + ",");
        }
        return tostr + "\n\n fitness = " + fitness*100 +
                "% and conflicts = " + conflicts;
    }
}
