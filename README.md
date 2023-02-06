## Acknowledgements

This program is based on an assignment created by Professor Ben Nye at Colorado College. Some of the text in this readme is in his words, taken from the assignment description.

The code belongs to me, Alex Wagner.

Made in Java 18.0.1, using Visual Studio Code on Windows 10.

## Usage

Run Main.java using an IDE of your choice. You may want to create a project folder for yourself and drop the source files into it if you use an editor other than Visual Studio Code.

If you prefer to use the command line instead, don't include any arguments after main.java.

While playing the game, simply enter the information you are asked for in the console, such as number of players, player names, and whether to Hit or Stand.

## The Game

This program simulates a game of blackjack. Here's a quick overview of the way the (slightly simplified) game is played:

    Each person is dealt two cards, the first of which is face down and only visible to that player.
    Proceeding around the table, each person plays out their hand by repeatedly drawing an additional face up card from the deck until either:
        They decide to stop voluntarily, or
        The total of their hand (including the face down card) is greater than 21 and they "bust", disqualifying them from the round.
    At the end of the round after each person has played their hand, whoever has the highest hand without busting wins.

Hand scoring:

    Number cards are worth their pip value
    Face cards are worth 10
    Aces are worth 11 unless the hand would bust, in which case they are worth 1 instead
    If you get 5 cards without busting, you win automatically

If multiple players tie, all winning players are displayed together at the end.
