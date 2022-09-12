package bng.biz;

import bng.data.BingoRawCard;
import bng.util.BingoUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BingoRawBuilderTest {
    BingoRawBuilder bngBuilder;
    BingoUtilities bingoUtilities;

    @Before
    public void reset() {
        bingoUtilities = new BingoUtilities();
        bngBuilder = new BingoRawBuilder(bingoUtilities);
    }

    @Test
    public void testSplitCardNumbersToTickets() {
        BingoRawCard card = new BingoRawCard();
        List<Integer> remainingNumbers = bngBuilder.splitCardNumbersToTickets(card);

        Assert.assertEquals(card.getTickets().size(), 6);
        Assert.assertEquals(remainingNumbers.size(), 36);

        Assert.assertEquals(card.getTickets().get(1).size(), 9);
        Assert.assertEquals(card.getTickets().get(2).size(), 9);
        Assert.assertEquals(card.getTickets().get(3).size(), 9);
        Assert.assertEquals(card.getTickets().get(4).size(), 9);
        Assert.assertEquals(card.getTickets().get(5).size(), 9);
        Assert.assertEquals(card.getTickets().get(6).size(), 9);
    }

    @Test
    public void testSplitCardNumbersToTicketsUNIQUE_NOTCOMPLETE() {
        BingoRawCard card = new BingoRawCard();
        List<Integer> remainingNumbers = bngBuilder.splitCardNumbersToTickets(card);

        Assert.assertEquals(card.getTickets().size(), 6);
        Assert.assertEquals(remainingNumbers.size(), 36);

        for (int j = 1; j <= BingoUtilities.TICKET_COUNT; j++) {
            for (int i = 1; i <= BingoUtilities.ROW_COUNT; i++) {
                for (int k = 0; k < BingoUtilities.COLUMN_COUNT; k++) {
                    if (card.getTickets().get(j).get(i).contains(k)) {
                        int randomValue = card.getTickets().get(j).get(i).get(k);
                        int randomValueCount = 0;

                        for (int t = 1; t <= BingoUtilities.TICKET_COUNT; t++) {
                            for (int r = 1; r <= BingoUtilities.ROW_COUNT; r++) {
                                for (int c = 0; c < BingoUtilities.COLUMN_COUNT; c++) {
                                    if (card.getTickets().get(t).get(r).contains(c)) {
                                        if (card.getTickets().get(t).get(r).get(c) == randomValue) {
                                            randomValueCount++;
                                        }
                                    }
                                }
                            }
                        }
                        Assert.assertEquals(randomValueCount, 1);
                    }
                }
            }
        }
    }

    @Test
    public void testTicketsDoNotHaveRemainingNumbers() {
        BingoRawCard card = new BingoRawCard();
        List<Integer> remainingNumbers = bngBuilder.splitCardNumbersToTickets(card);

        Assert.assertEquals(card.getTickets().size(), 6);
        Assert.assertEquals(remainingNumbers.size(), 36);

        for (int i = 0; i < remainingNumbers.size(); i++) {
            int remainingValue = remainingNumbers.get(i);
            int remainingValueCount = 0;
            for (int t = 1; t <= BingoUtilities.TICKET_COUNT; t++) {
                for (int r = 1; r <= BingoUtilities.ROW_COUNT; r++) {
                    for (int c = 0; c < BingoUtilities.COLUMN_COUNT; c++) {
                        if (card.getTickets().get(t).get(r).contains(c)) {
                            if (card.getTickets().get(t).get(r).get(c) == remainingValue) {
                                remainingValueCount++;
                            }
                        }
                    }
                }
            }
            Assert.assertEquals(remainingValueCount, 0);
        }
    }

    @Test
    public void testSplitRemainingsToTicketsSIZE() {
        BingoRawCard card = new BingoRawCard();
        List<Integer> remainingNumbers = bngBuilder.splitCardNumbersToTickets(card);

        bngBuilder.splitRemainingsToTickets(card, remainingNumbers);

        Assert.assertEquals(card.getTickets().size(), 6);

        for (int i = 1; i <= BingoUtilities.TICKET_COUNT; i++) {
            Assert.assertEquals(card.getTickets().get(i).size(), 9);
            for (int j = 0; j < card.getTickets().get(i).size(); j++) {
                Assert.assertTrue(card.getTickets().get(i).containsKey(j));
            }
        }
    }

    @Test
    public void testSplitCardNumbersToTicketsUNIQUE_COMPLETE() {
        BingoRawCard card = new BingoRawCard();
        List<Integer> remainingNumbers = bngBuilder.splitCardNumbersToTickets(card);

        bngBuilder.splitRemainingsToTickets(card, remainingNumbers);

        for (int j = 1; j <= BingoUtilities.TICKET_COUNT; j++) {
            for (int i = 1; i <= BingoUtilities.ROW_COUNT; i++) {
                for (int k = 0; k < BingoUtilities.COLUMN_COUNT; k++) {
                    if (card.getTickets().get(j).get(i).contains(k)) {
                        int randomValue = card.getTickets().get(j).get(i).get(k);
                        int randomValueCount = 0;

                        for (int t = 1; t <= BingoUtilities.TICKET_COUNT; t++) {
                            for (int r = 1; r <= BingoUtilities.ROW_COUNT; r++) {
                                for (int c = 0; c < BingoUtilities.COLUMN_COUNT; c++) {
                                    if (card.getTickets().get(t).get(r).contains(c)) {
                                        if (card.getTickets().get(t).get(r).get(c) == randomValue) {
                                            randomValueCount++;
                                        }
                                    }
                                }
                            }
                        }
                        Assert.assertEquals(randomValueCount, 1);
                    }
                }
            }
        }
    }

    @Test
    public void testBuildingCard_ONECARD_TIME() {
        long start = System.currentTimeMillis();

        BingoRawCard card = new BingoRawCard();
        List<Integer> remainingNumbers = bngBuilder.splitCardNumbersToTickets(card);

        bngBuilder.splitRemainingsToTickets(card, remainingNumbers);

        Assert.assertEquals(card.getTickets().size(), 6);
        card.printCard(1);

        long end = System.currentTimeMillis();

        Assert.assertTrue((end - start) < (1000));
    }

    @Test
    public void testBuildingCard_MANYCARD_TIME() {
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 10_000; i++) {
            BingoRawCard card = new BingoRawCard();
            List<Integer> remainingNumbers = bngBuilder.splitCardNumbersToTickets(card);

            bngBuilder.splitRemainingsToTickets(card, remainingNumbers);
            card.printCard(i);
        }

        long end = System.currentTimeMillis();

        Assert.assertTrue((end - start) < (1000));
    }
}
