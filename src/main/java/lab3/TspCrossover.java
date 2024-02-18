package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static lab3.TspMutation.generateTwoRandomInts;

public class TspCrossover extends AbstractCrossover<TspSolution> {
    protected TspCrossover() {
        super(1);
    }

    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int i, Random random) {
        List<TspSolution> children = new ArrayList<>();
        children.add(orderCrossover(p1, p2, random));
        children.add(orderCrossover(p2, p1, random));

        return children;
    }

    @Deprecated
    private static TspSolution commonCrossover(TspSolution p1, TspSolution p2, Random random) {
        List<Integer> tour1 = p1.getPermutation();
        List<Integer> tour2 = p2.getPermutation();
        assert tour1.size() == tour2.size();

        int n = tour1.size();

        int empty = -1;
        List<Integer> tour = new ArrayList<>(Collections.nCopies(n, empty));
        Set<Integer> used = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            used.add(i);
        }
        for (int i = 0; i < n; i++) {
            if (tour1.get(i).equals(tour2.get(i))) {
                tour.set(i, tour1.get(i));
                used.remove(tour1.get(i));
            }
        }
        List<Integer> left = new ArrayList<>(used);
        Collections.shuffle(left, random);
        int iter = 0;
        for (int i = 0; i < n; i++) {
            if (tour.get(i) == empty) {
                tour.set(i, left.get(iter));
                iter++;
            }
        }

        return new TspSolution(tour);
    }

    private TspSolution orderCrossover(TspSolution p1, TspSolution p2, Random random) {
        List<Integer> tour1 = p1.getPermutation();
        List<Integer> tour2 = p2.getPermutation();
        assert tour1.size() == tour2.size();
        int dimension = tour1.size();
        Point twoRandomAlleles = generateTwoRandomInts(dimension, random);

        int a = twoRandomAlleles.getX();
        int b = twoRandomAlleles.getY();
        List<Integer> child = orderCrossover(tour1, tour2, a, b);

        return new TspSolution(child);
    }

    private static List<Integer> orderCrossover(
            List<Integer> baseParent,
            List<Integer> traverseParent,
            int a,
            int b
    ) {
        int dimension = baseParent.size();
        List<Integer> child = new ArrayList<>(Collections.nCopies(dimension, 0));
        Set<Integer> used = new HashSet<>();
        for (int i = a; i <= b; i++) {
            int cur = baseParent.get(i);
            used.add(cur);
            child.set(i, cur);
        }
        int insertIndex = (b + 1) % dimension;
        for (int i = 0; i < dimension; i++) {
            int curIndex = (b + 1 + i) % dimension;
            int cur = traverseParent.get(curIndex);
            if (!used.contains(cur)) {
                child.set(insertIndex, cur);
                insertIndex = (insertIndex + 1) % dimension;
            }
        }

        return child;
    }

    public static void main(String[] args) {
//        testCommonCrossover();
        testOrderCrossover();
    }

    private static void testCommonCrossover() {
        List<Integer> p1 = new ArrayList<>();
        int[] rawP1 = {3, 8, 5, 7, 6, 2, 1, 4};
        for (int v : rawP1) {
            p1.add(v);
        }
        TspSolution sol1 = new TspSolution(p1);

        List<Integer> p2 = new ArrayList<>();
        int[] rawP2 = {3, 8, 4, 6, 5, 2, 1, 7};
        for (int v : rawP2) {
            p2.add(v);
        }
        TspSolution sol2 = new TspSolution(p2);

        TspSolution child1 = commonCrossover(sol1, sol2, new Random());
        System.out.println(child1.getPermutation());
        TspSolution child2 = commonCrossover(sol1, sol2, new Random());
        System.out.println(child2.getPermutation());
        TspSolution child3 = commonCrossover(sol1, sol2, new Random());
        System.out.println(child3.getPermutation());
    }

    private static void testOrderCrossover() {
        List<Integer> p1 = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            p1.add(i);
        }
        List<Integer> p2 = new ArrayList<>();
        int[] rawP2 = {3, 7, 5, 2, 8, 1, 4, 9, 6};
        for (int v : rawP2) {
            p2.add(v);
        }

        System.out.println(p1);
        System.out.println(p2);

        int a = 3;
        int b = 6;
        List<Integer> child = orderCrossover(p1, p2, a, b);

        System.out.println(child);

        int[] correctCrossover = {2, 8, 1, 4, 5, 6, 7, 9, 3};
        boolean correct = true;
        for (int i = 0; i < correctCrossover.length; i++) {
            correct &= correctCrossover[i] == child.get(i);
        }
        System.out.println(correct);
    }
}
