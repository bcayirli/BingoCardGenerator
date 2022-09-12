package bng.biz;

import bng.data.BingoRawCard;
import bng.util.BingoUtilities;

import java.util.ArrayList;
import java.util.List;

public class BingoRawBuilder {
    private final BingoUtilities bingoUtilities;

    public BingoRawBuilder(BingoUtilities bingoUtilities) {
        this.bingoUtilities = bingoUtilities;
    }

    // Add the remaining numbers to tickets.
    public void splitRemainingsToTickets(BingoRawCard rawCard, List<Integer> remainingNumbers) {
        for (int remainingNumber : remainingNumbers) {
            // We already guaranteed that all columns in all tickets have at least one number
            // Starting from 1 find the less numbered Ticket.
            // we will reach all of them eventually
            int ticketIndex = 1;
            int minSize = rawCard.getTicketSize(ticketIndex);
            for (int i = 1; i <= BingoUtilities.TICKET_COUNT; i++) {
                if (rawCard.getTicketSize(i) < minSize) {
                    ticketIndex = i;
                }
            }

            // Add the to ticket.
            rawCard.addValue(ticketIndex, remainingNumber);
        }
    }

    // Startng method, First call this one.
    // Provide an empty BingoRawCard object
    public List<Integer> splitCardNumbersToTickets(BingoRawCard rawCard) {
        // shuffled numbers are the shuffled numbers from 0 to 9. 0 is at the end
        // always. This provides randomness.
        int[] shuffledNumbers = bingoUtilities.shuffleNumbers();
        List<Integer> remainingNumbers = new ArrayList<>();

        // add first 6 (ticket count) numbers produced from shuffled array
        // to each ticket.
        // this loop produces a number from shuffled array for each column
        // of each ticket. Hence, each ticket will have at least 6 numbers,
        // meaning each column will have at least one number.
        // Since we hold the added numbers in a List for each column; check BingoRawCard class
        // we do not know the rows yet.
        int columnCounter = 0;
        while (columnCounter < BingoUtilities.COLUMN_COUNT) {
            for (int i = 0; i < shuffledNumbers.length; i++) {
                int value = shuffledNumbers[i] + (columnCounter * 10);
                if (value > 0) {
                    if (i < BingoUtilities.TICKET_COUNT) {
                        rawCard.addValue(i + 1, value);
                    } else {
                        // The ones we are not added yet. Add them after adding
                        // first six numbers
                        remainingNumbers.add(value);
                    }
                }
            }

            columnCounter++;
        }

        // 90 is not produced by above loop. Add it for not to lose it.
        remainingNumbers.add(90);
        return remainingNumbers;
    }
}
