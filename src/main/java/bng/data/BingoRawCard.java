package bng.data;

import bng.util.BingoUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This is called Raw Card because only a list of numbers is produced.
 * Not the complete Card is produced in this class.
 * */
public class BingoRawCard {
    // this holds the Bingo card containing 6 tickets for each card.
    // Map structure is as follows; <Ticket, <Column, List of values>>
    // List of values for a column is limited to 3, sşnce we olny have 3 rows
    private Map<Integer, Map<Integer, List<Integer>>> tickets;

    // Initiate Raw Card.
    public BingoRawCard() {
        tickets = new HashMap<>();
        for (int i = 0; i < BingoUtilities.TICKET_COUNT; i++) {
            tickets.put(i + 1, new HashMap<>());
        }
    }

    public void addValue(int ticketNumber, int value) {
        // Find the column for each value which is value / 10
        int column = value / 10;
        // For value == 90, the column will be 9 but it must be 8
        if (column == 9) {
            column--;
        }

        // Create the array for column
        if (!tickets.get(ticketNumber).containsKey(column)) {
            tickets.get(ticketNumber).put(column, new ArrayList<>());
        }
        // add value to the column array
        tickets.get(ticketNumber).get(column).add(value);
    }

    // getTickets for Card
    public Map<Integer, Map<Integer, List<Integer>>> getTickets() {
        return tickets;
    }

    // Eventually this must be 15 at most
    public int getTicketSize(int ticketNumber) {
        // for each column, add the size of Lists
        int ticketSize = 0;
        for (int i = 0; i < BingoUtilities.COLUMN_COUNT; i++) {
            ticketSize += tickets.get(ticketNumber).get(i).size();
        }
        return ticketSize;
    }

    /*
     * PRINT METHODS
     * */
    public void printCard(int cardNumber) {
        StringBuilder sb = (new StringBuilder("Printing Card...")).append(cardNumber);
        sb.append(System.getProperty("line.separator"));

        for (int i = 1; i <= BingoUtilities.TICKET_COUNT; i++) {
            sb.append("Ticket Number: ").append(i).append(System.getProperty("line.separator"));
            sb.append(tickets.get(i).toString());
            sb.append(System.getProperty("line.separator"));
        }

        System.out.println(sb.toString());
    }
}
