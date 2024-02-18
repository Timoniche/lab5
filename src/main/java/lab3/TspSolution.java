package lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TspSolution {
    private final List<Integer> permutation;

    public static TspSolution randomSolution(int n) {
        ArrayList<Integer> randomPermutation = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            randomPermutation.add(i);
        }
        Collections.shuffle(randomPermutation);

        return new TspSolution(randomPermutation);
    }

    public TspSolution(List<Integer> permutation) {
        this.permutation = new ArrayList<>(permutation);
    }

    public List<Integer> getPermutation() {
        return permutation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int verboseThreshold = 5;
        sb.append('[');
        for (int i = 0; i < Math.min(verboseThreshold, permutation.size()); i++) {
            sb.append(permutation.get(i));
            sb.append(' ');
        }
        if (permutation.size() > verboseThreshold) {
            sb.append("... ");
            sb.append(permutation.get(permutation.size() - 1));
        }
        sb.append(']');

        return sb.toString();
    }

    public static void main(String[] args) {
        TspSolution randomSolution = randomSolution(5);
        System.out.println(randomSolution.getPermutation());
    }
}
