package org.example;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListCompare {

    private static final int INITIAL_INVOKES_COUNT = 1000;
    private static final int MAX_INVOKES_COUNT = 4000;
    private static final int INVOKES_COUNT_MULTIPLIER = 2;
    private static final int COLLECTION_SIZE = 100000;

    /**
     * Prints tables to the CLI
     * with execution times of the main ArrayList and LinkedList
     * methods' calls in many times (1000, 2000, 4000 times).
     * Tested methods: get, add, remove (from different position).
     * Displays time in nanoseconds.
     */
    public static void main(String[] args) {
        var arrayList = new ArrayList<Integer>(COLLECTION_SIZE);
        var linkedList = new LinkedList<Integer>();
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            arrayList.add(0);
            linkedList.add(0);
        }
        int invokesCount = INITIAL_INVOKES_COUNT;
        do {
            compareListImplementations(arrayList, linkedList, invokesCount);
            System.out.println();
            invokesCount *= INVOKES_COUNT_MULTIPLIER;
        } while (invokesCount <= MAX_INVOKES_COUNT);
    }

    private static void compareListImplementations(ArrayList<Integer> arrayList, LinkedList<Integer> linkedList, int invokesCount) {
        var arrayListAddToHeadTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.add(0));
        var linkedListAddToHeadTime = calculateManyExecutionsNanoTime(invokesCount, () -> linkedList.add(0));

        var arrayListRemoveFromHeadTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.remove(0));
        var linkedListRemoveFromHeadTime = calculateManyExecutionsNanoTime(invokesCount, () -> linkedList.remove(0));

        var arrayListAddToMiddleTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.add(arrayList.size() / 2, 0));
        var linkedListAddToMiddleTime = calculateManyExecutionsNanoTime(invokesCount, () -> linkedList.add(linkedList.size() / 2, 0));

        var arrayListRemoveFromMiddleTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.remove(arrayList.size() / 2));
        var linkedListRemoveFromMiddleTime = calculateManyExecutionsNanoTime(invokesCount, () -> linkedList.remove(linkedList.size() / 2));

        var arrayListAddToEndTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.add(arrayList.size() - 1, 0));
        var linkedListAddToEndTime = calculateManyExecutionsNanoTime(invokesCount, () -> linkedList.add(linkedList.size() - 1, 0));

        var arrayListRemoveFromEndTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.remove(arrayList.size() - 1));
        var linkedListRemoveFromEndTime = calculateManyExecutionsNanoTime(invokesCount, () -> linkedList.remove(linkedList.size() - 1));

        var arrayListGetFromHeadTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.get(0));
        var linkedListGetFromHeadTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.get(0));

        var arrayListGetFromMiddleTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.get(arrayList.size() / 2));
        var linkedListGetFromMiddleTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.get(linkedList.size() / 2));

        var arrayListGetFromEndTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.get(arrayList.size() - 1));
        var linkedListGetFromEndTime = calculateManyExecutionsNanoTime(invokesCount, () -> arrayList.get(linkedList.size() - 1));

        System.out.printf("Invokes Count: %s\n", invokesCount);
        System.out.print("Operation       | Array List | Linked List\n");
        System.out.printf("Add to head     | %10s | %11s\n", arrayListAddToHeadTime, linkedListAddToHeadTime);
        System.out.printf("Add to middle   | %10s | %11s\n", arrayListAddToMiddleTime, linkedListAddToMiddleTime);
        System.out.printf("Add to end      | %10s | %11s\n", arrayListAddToEndTime, linkedListAddToEndTime);
        System.out.format("Remove from head| %10s | %11s\n", arrayListRemoveFromHeadTime, linkedListRemoveFromHeadTime);
        System.out.printf("Remove from mid | %10s | %11s\n", arrayListRemoveFromMiddleTime, linkedListRemoveFromMiddleTime);
        System.out.printf("Remove from end | %10s | %11s\n", arrayListRemoveFromEndTime, linkedListRemoveFromEndTime);
        System.out.printf("Get from head   | %10s | %11s\n", arrayListGetFromHeadTime, linkedListGetFromHeadTime);
        System.out.printf("Get from middle | %10s | %11s\n", arrayListGetFromMiddleTime, linkedListGetFromMiddleTime);
        System.out.printf("Get from end    | %10s | %11s\n", arrayListGetFromEndTime, linkedListGetFromEndTime);
    }

    private static long calculateManyExecutionsNanoTime(int invokesCount, Runnable block) {
        return calculateExecutionNanoTime(() -> {
            for (int i = 0; i < invokesCount; ++i) {
                block.run();
            }
        });
    }

    private static long calculateExecutionNanoTime(Runnable block) {
        long startTime = System.nanoTime();
        block.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
