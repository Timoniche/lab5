package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static lab3.TspAlg.GENERATIONS;

public class TspMutation implements EvolutionaryOperator<TspSolution> {
    private int generationNumber;

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public double getDecayCoefficient() {
        return (generationNumber * 1.) / GENERATIONS;
    }

    private enum MutationStrategy {
        SWAP,
        INVERSION
    }

    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        MutationStrategy mutationStrategy = chooseMutationStrategy(random);

        return apply(population, random, mutationStrategy);
    }

    private MutationStrategy chooseMutationStrategy(Random random) {
        double prob = random.nextDouble();
        double decay = getDecayCoefficient();
        double explorationThreshold = 1 - decay * decay;

        if (prob <= explorationThreshold) {
            return MutationStrategy.INVERSION;
        } else {
            return MutationStrategy.SWAP;
        }
    }

    private List<TspSolution> apply(
            List<TspSolution> population,
            Random random,
            MutationStrategy strategy
    ) {
        for (TspSolution specimen : population) {
            List<Integer> tour = specimen.getPermutation();
            switch (strategy) {
                case SWAP:
                    swapMutation(tour, random);
                    break;
                case INVERSION:
                    inversionMutation(tour, random);
                    break;
            }
        }
        return population;
    }

    public static Point generateTwoRandomInts(int dimension, Random random) {
        assert dimension > 1 : "Dimension must be greater than 1";

        int i = random.nextInt(dimension);
        int j;
        do {
            j = random.nextInt(dimension);
        } while (j == i);

        return new Point(i, j);
    }

    //exploration
    private void inversionMutation(List<Integer> tour, Random random) {
        int dimension = tour.size();
        Point twoRandomAlleles = generateTwoRandomInts(dimension, random);
        int from = twoRandomAlleles.getX();
        int to = twoRandomAlleles.getY();
        for (int i = from; i <= from + (to - from) / 2; i++) {
            Collections.swap(tour, i, to - i + from);
        }
    }

    //exploitation
    private void swapMutation(List<Integer> tour, Random random) {
        int dimension = tour.size();
        Point twoRandomAlleles = generateTwoRandomInts(dimension, random);

        Collections.swap(tour, twoRandomAlleles.getX(), twoRandomAlleles.getY());
    }
}
