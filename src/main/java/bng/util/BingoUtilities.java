package bng.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BingoUtilities {
    public static int NUMBER_COUNT_FOR_A_CARD = 90;
    public static int TICKET_COUNT = 6;
    public static int COLUMN_COUNT = 9;
    public static int ROW_COUNT = 3;

    private int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    Random random;

    public BingoUtilities() {
        random = new Random();
    }

    public int[] shuffleNumbers() {
        int[] tmpArray = numbers;
        for (int i = 0; i < tmpArray.length; i++) {
            int randomIndexToSwap = random.nextInt(tmpArray.length);
            if (tmpArray[randomIndexToSwap] != 0 && tmpArray[i] != 0) {
                int temp = tmpArray[randomIndexToSwap];
                tmpArray[randomIndexToSwap] = tmpArray[i];
                tmpArray[i] = temp;
            }
        }

        return tmpArray;
    }

    public int[] getShuffledCardNumbers() {
        int[] shuffledCardNumbers = new int[NUMBER_COUNT_FOR_A_CARD];
        for (int i = 1; i <= NUMBER_COUNT_FOR_A_CARD; i++) {
            int randomIndex = random.nextInt(NUMBER_COUNT_FOR_A_CARD);
            if (shuffledCardNumbers[randomIndex] == 0) {
                shuffledCardNumbers[randomIndex] = i;
            } else {
                i--;
            }
        }
        return shuffledCardNumbers;
    }

    public List<Integer> getShuffledCardNumbersList() {
        int[] shuffledCardNumbers = getShuffledCardNumbers();
        List<Integer> shuffledCardNumbersList = new ArrayList<>(NUMBER_COUNT_FOR_A_CARD);
        for (int i = 0; i < shuffledCardNumbers.length; i++) {
            shuffledCardNumbersList.add(shuffledCardNumbers[i]);
        }
        return shuffledCardNumbersList;
    }

    public int[] getOrderedCardNumbers() {
        int[] orderedCardNumbers = new int[NUMBER_COUNT_FOR_A_CARD];
        for (int i = 1; i <= NUMBER_COUNT_FOR_A_CARD; i++) {
            orderedCardNumbers[i - 1] = i;
        }
        return orderedCardNumbers;
    }

    public List<Integer> getOrderedCardNumbersList() {
        List<Integer> orderedNumbers = new ArrayList<>();
        for (int i = 1; i <= NUMBER_COUNT_FOR_A_CARD; i++) {
            orderedNumbers.add(i);
        }
        return orderedNumbers;
    }

    public int getRandom(int min, int max) {
        return random.nextInt(min, max);
    }

    public void printStripMap(Map<Integer, List<Integer>> strips) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry e : strips.entrySet()) {
            List<Integer> list = (List<Integer>) e.getValue();
            sb.append(list.size()).append("  --  ");
            for (int i : list) {
                sb.append(String.format("%02d", i)).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
}
