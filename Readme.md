09.09.2022

BingoCardGen application is to generate Bingo cards of 6 Tickets.

Create a BingoRawCard object. 

Create a BingoRawBuilder object to build a raw bingo card first.

Call splitCardNumbersToTickets method in BingoRawBuilder object to split random numbers to raw cards. Keep returning List of remaining numbers.

Call splitRemainingsToTickets method of same object to add the remaining numbers to the raw card.

We have a map for Tickets in object of BingoRawBuilder. Each Ticket map contains another map of numbers in the columns of the Ticket.

Create a BingoCardBuilder object to create resulting Bingo Cards.

Call buildTickets method of BingoCardBuilder object to convert the raw card into real playable Bingo Cards. This will redistribute the numbers into Tickets by applying maximum Row number count and minimum Column number count rules (The later rule is already applied while preparing raw cards).