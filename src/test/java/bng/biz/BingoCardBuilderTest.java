package bng.biz;

import bng.data.BingoRawCard;
import bng.util.BingoUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class BingoCardBuilderTest {
    BingoUtilities bingoUtilities;
    BingoRawCard card;
    BingoRawBuilder bingoRawBuilder;
    BingoCardBuilder bingoCardBuilder;

    @Before
    public void reset() {
        bingoUtilities = new BingoUtilities();
        bingoRawBuilder = new BingoRawBuilder(bingoUtilities);
        card = new BingoRawCard();
        List<Integer> remainingNumbers = bingoRawBuilder.splitCardNumbersToTickets(card);
        bingoRawBuilder.splitRemainingsToTickets(card, remainingNumbers);
        bingoCardBuilder = new BingoCardBuilder(bingoUtilities);
    }

    @Test
    public void testBuildTickets_TIME() {
        long start = System.currentTimeMillis();
        Map<Integer, Map<Integer, Map<Integer, Integer>>> result = bingoCardBuilder.buildTickets(card);

        Assert.assertEquals(result.size(), 6);

        bingoCardBuilder.printCard(result);

        long end = System.currentTimeMillis();

        Assert.assertTrue((end - start) < (1000));
    }

    @Test
    public void testBuildTickets_1000_TIME() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            Map<Integer, Map<Integer, Map<Integer, Integer>>> result = bingoCardBuilder.buildTickets(card);

            Assert.assertEquals(result.size(), 6);

            bingoCardBuilder.printCard(result);
        }

        long end = System.currentTimeMillis();

        Assert.assertTrue((end - start) < (1000));
    }

    @Test
    public void testBuildTickets_10000_TIME() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10_000; i++) {
            Map<Integer, Map<Integer, Map<Integer, Integer>>> result = bingoCardBuilder.buildTickets(card);

            Assert.assertEquals(result.size(), 6);

            //bingoCardBuilder.printCard(result);
        }

        long end = System.currentTimeMillis();

        Assert.assertTrue((end - start) < (1000));
    }

    @Test
    public void testBuildTickets_SIZE() {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> result = bingoCardBuilder.buildTickets(card);

        Assert.assertEquals(result.size(), 6);

        for (int i = 1; i <= result.size(); i++) {
            Map<Integer, Map<Integer, Integer>> ticket = result.get(i);

            Assert.assertEquals(ticket.size(), 3);

            for (int j = 1; j <= BingoUtilities.ROW_COUNT; j++) {
                Assert.assertEquals(ticket.get(j).size(), 5);

                Assert.assertEquals(bingoCardBuilder.getRowCount(ticket, j), 5);
            }
        }
    }

    @Test
    public void testBuildTickets_UNIQUE_VALUES() {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> result = bingoCardBuilder.buildTickets(card);

        Assert.assertEquals(result.size(), 6);

        for (int i = 1; i <= result.size(); i++) {
            Map<Integer, Map<Integer, Integer>> ticket = result.get(i);

            for (int j = 1; j <= BingoUtilities.ROW_COUNT; j++) {
                for (int k = 0; k < BingoUtilities.COLUMN_COUNT; k++) {
                    if (result.get(i).get(j).containsKey(k)) {
                        int valueToCheckForUniqueness = result.get(i).get(j).get(k);
                        int countOfValueToCheckForUniqueness = 0;

                        // starting from first ticket
                        for (int t = 1; t <= result.size(); t++) {
                            // for each row
                            for (int r = 1; r <= BingoUtilities.ROW_COUNT; r++) {
                                // for each column
                                for (int c = 0; c < BingoUtilities.COLUMN_COUNT; c++) {
                                    if (result.get(t).get(r).containsKey(c)) {
                                        if (result.get(t).get(r).get(c) == valueToCheckForUniqueness) {
                                            countOfValueToCheckForUniqueness++;
                                        }
                                    }
                                }
                            }
                        }

                        Assert.assertEquals(countOfValueToCheckForUniqueness, 1);
                    }
                }
            }
        }
    }
}
