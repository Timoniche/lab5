package lab3;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {
    private int bestHits = Integer.MAX_VALUE;

    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        int fitness = 0;
        List<Integer> ids = solution.getPermutation();

        for (int x1 = 1; x1 <= ids.size(); x1++) {
            int y1 = ids.get(x1 - 1);

            for (int x2 = x1 + 1; x2 <= ids.size(); x2++) {
                int y2 = ids.get(x2 - 1);

                if (hitEachOtherDiagonally(x1, y1, x2, y2)) {
                    fitness++;
                }
            }
        }

        if (fitness < bestHits) {
            bestHits = fitness;
        }

        return fitness;
    }

    private boolean hitEachOtherDiagonally(int x1, int y1, int x2, int y2) {
        if (x2 <= x1) {
            throw new IllegalArgumentException("x2 must be greater than x1");
        }

        return x2 - x1 == Math.abs(y2 - y1);
    }

    public boolean isNatural() {
        return false;
    }

    public int getBestHits() {
        return bestHits;
    }

    public static void main(String[] args) {
        /*
          hits := 1
          010
          100
          001
         */
        List<Integer> queens = new ArrayList<>(); //column positions
        queens.add(2);
        queens.add(1);
        queens.add(3);
        TspSolution tspSolution = new TspSolution(queens);

        TspFitnessFunction fitnessFunction = new TspFitnessFunction();
        fitnessFunction.getFitness(tspSolution, Collections.emptyList());
        int hits = fitnessFunction.getBestHits();
        System.out.println(hits == 1);

        /*
          hits := 4
          0100
          1000
          0001
          0010
         */
        queens.clear();
        queens.add(2);
        queens.add(1);
        queens.add(4);
        queens.add(3);
        tspSolution = new TspSolution(queens);
        fitnessFunction = new TspFitnessFunction();
        fitnessFunction.getFitness(tspSolution, Collections.emptyList());
        hits = fitnessFunction.getBestHits();
        System.out.println(hits == 4);
    }
}
