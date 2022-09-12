package bng.biz;

import bng.data.BingoRawCard;
import bng.util.BingoUtilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BingoCardBuilder {
    private final BingoUtilities bingoUtilities;

    public BingoCardBuilder(BingoUtilities bingoUtilities) {
        this.bingoUtilities = bingoUtilities;
    }

    // Get the amount of  numbers in a row
    public int getRowCount(Map<Integer, Map<Integer, Integer>> result, int rowIndex) {
        int numbersInRow = 0;
        for (int j = 0; j < BingoUtilities.COLUMN_COUNT; j++) {
            if (result.get(rowIndex).containsKey(j)) {
                numbersInRow++;
            }
        }

        return numbersInRow;
    }

    // Gets a raw card and turns it into a resulting Bingo Card with 6 Tickets
    public Map<Integer, Map<Integer, Map<Integer, Integer>>> buildTickets(BingoRawCard rawCard) {
        // Resulting Card structure
        // <Ticket Number, <Row Number, Column Number, Value>>>
        Map<Integer, Map<Integer, Map<Integer, Integer>>> resultCard = new HashMap<>();

        // For internal use
        Map<Integer, Map<Integer, List<Integer>>> tickets = rawCard.getTickets();

        for (int i = 1; i <= BingoUtilities.TICKET_COUNT; i++) {
            // Ticket Structure, included in resultCard defined above
            // <Row Number, Column Number, Value>>
            Map<Integer, Map<Integer, Integer>> resultTicket = new HashMap<>();
            for (int j = 1; j <= BingoUtilities.ROW_COUNT; j++) {
                resultTicket.put(j, new HashMap<>());
            }

            // We have a List for each column in ticketMap.
            Map<Integer, List<Integer>> ticketMap = tickets.get(i);

            // If there are already 3 numbers in a column, just add them
            // to resultTicket with the same column number.
            // They will not change, this is a fixed column.
            for (int j = 0; j < BingoUtilities.COLUMN_COUNT; j++) {
                if (ticketMap.get(j).size() == 3) {
                    for (int k = 0; k < ticketMap.get(j).size(); k++) {
                        resultTicket.get(k + 1).put(j, ticketMap.get(j).get(k));
                    }
                }
            }

            // If we have 2 numbers in a column the following possibilities may occur
            // Possibilities 1    2    3
            // Values
            //               XX   46   46
            //               46   XX   48
            //               48   48   XX

            // There are only 3 rows, no need to define a loop here.
            for (int j = 0; j < BingoUtilities.COLUMN_COUNT; j++) {
                if (ticketMap.get(j).size() == 2) {
                    int firstRowSize = getRowCount(resultTicket, 1);
                    int secondRowSize = getRowCount(resultTicket, 2);
                    int thirdRowSize = getRowCount(resultTicket, 3);

                    // There is more space in first row. Add the first element there
                    if (firstRowSize < secondRowSize) {
                        resultTicket.get(1).put(j, ticketMap.get(j).get(0));
                        // Check for available spaces in 2nd and 3rd rows
                        if (secondRowSize < thirdRowSize) {
                            // second row is more spacious
                            resultTicket.get(2).put(j, ticketMap.get(j).get(1));
                        } else {
                            // third row is more spacious
                            resultTicket.get(3).put(j, ticketMap.get(j).get(1));
                        }
                    } else {
                        // If first row is less spacious then start from second one.
                        resultTicket.get(2).put(j, ticketMap.get(j).get(0));
                        resultTicket.get(3).put(j, ticketMap.get(j).get(1));
                    }
                }
            }

            // Single numbered columns are easiest. Find the most spacious row
            // and add the number there.
            for (int j = 0; j < BingoUtilities.COLUMN_COUNT; j++) {
                if (ticketMap.get(j).size() == 1) {
                    int startingRowIndex = 1;
                    int minRowSize = 9;

                    for (int l = startingRowIndex; l <= BingoUtilities.ROW_COUNT; l++) {
                        int currentRowSize = getRowCount(resultTicket, l);
                        if (currentRowSize < minRowSize) {
                            minRowSize = currentRowSize;
                            startingRowIndex = l;
                        }
                    }
                    resultTicket.get(startingRowIndex).put(j, ticketMap.get(j).get(0));
                }
            }

            // Add Ticket to the Card
            resultCard.put(i, new HashMap<>(resultTicket));
        }

        return resultCard;
    }

    public void printCard(Map<Integer, Map<Integer, Map<Integer, Integer>>> result) {
        StringBuilder sb = new StringBuilder("PRINTING CARD...");
        sb.append("\n");
        for (int i = 1; i <= BingoUtilities.TICKET_COUNT; i++) {
            sb.append("TICKET ").append(i).append("\n");
            for (int j = 1; j <= BingoUtilities.ROW_COUNT; j++) {
                for (int k = 0; k < BingoUtilities.COLUMN_COUNT; k++) {
                    if (result.get(i).get(j).containsKey(k)) {
                        sb.append(String.format("%02d", result.get(i).get(j).get(k))).append(" ");
                    } else {
                        sb.append("XX").append(" ");
                    }
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
