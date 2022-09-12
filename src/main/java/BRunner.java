import bng.biz.BingoCardBuilder;
import bng.biz.BingoRawBuilder;
import bng.data.BingoRawCard;
import bng.util.BingoUtilities;

import java.util.List;
import java.util.Map;

public class BRunner {
    public static void main(String[] args) {
        BingoUtilities bingoUtilities = new BingoUtilities();
        BingoRawCard card = new BingoRawCard();
        BingoRawBuilder bingoRawBuilder = new BingoRawBuilder(bingoUtilities);
        BingoCardBuilder bingoCardBuilder = new BingoCardBuilder(bingoUtilities);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            List<Integer> remainingNumbers = bingoRawBuilder.splitCardNumbersToTickets(card);
            bingoRawBuilder.splitRemainingsToTickets(card, remainingNumbers);
            Map<Integer, Map<Integer, Map<Integer, Integer>>> result = bingoCardBuilder.buildTickets(card);

            bingoCardBuilder.printCard(result);
        }
        long end = System.currentTimeMillis();

        System.out.println("Total time = " + (end - start));
    }
}
