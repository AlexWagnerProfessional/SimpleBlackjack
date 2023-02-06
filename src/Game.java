import java.util.ArrayList;
import java.util.Scanner;

/**
* This class keeps track of several important things for the game state,
* such as lists of all players, and a way to display them.
*/
public class Game {
    // Attributes
    // Primarily public since the Main method will have to access them a lot, and this
    // will save typing a lot of "get" and parentheses.
    public Player dealer;
    public ArrayList<Player> playersHuman;
    public ArrayList<Player> playersComputer;
    public ArrayList<Player> playersFinished; // Players will be moved here if they have stood or busted
    public int currentTurn; // The index in playersHuman for the player whose turn it is
    public Deck masterDeck;
    
    private int playerCapHuman = 3;
    private int playerCapComputer = 3;
    
    // Constructor
    public Game(){
        // Start with...
        dealer = new Player("Dealer"); // Dealer with no cards yet
        playersHuman = new ArrayList<Player>(); // Empty lists of players
        playersComputer = new ArrayList<Player>();
        playersFinished = new ArrayList<Player>();
        currentTurn = 0; // Starting with player 0
        masterDeck = new Deck(); 
        masterDeck.fillDeck(); // A full deck from which to draw
    }
    
    // Methods
    public void initializePlayers(Scanner scanny){
        String input;
        int playerCountHuman;
        int playerCountComputer;
        
        // java.lang.NumberFormatException
        // Ask how many human players to generate
        try{
            System.out.println("How many human players? Min 0, max " + playerCapHuman + ".");
            input = scanny.nextLine();
            playerCountHuman = Integer.parseInt(input);
        }
        catch (NumberFormatException e){
            System.out.println("Sorry, that response couldn't be parsed as an integer. Defaulting to 1 human player.");
            playerCountHuman = 1;
        }
        
        // Cap at a certain number of players
        if (playerCountHuman > playerCapHuman){
            System.out.println("Sorry, that is too many human players. Defaulting to the cap of " + playerCapHuman + " players.");
            playerCountHuman = playerCapHuman;
        }
        
        // Ask how many extra computer players to generate (there will always be a dealer)
        System.out.println("How many extra computer players (in addition to the dealer)? Min 0, max " + playerCapComputer + ".");
        
        try{
            input = scanny.nextLine();
            playerCountComputer = Integer.parseInt(input);
        }
        catch (NumberFormatException e){
            System.out.println("Sorry, that response couldn't be parsed as an integer. Defaulting to 0 extra computer players.");
            playerCountComputer = 0;
        }
        
        // Cap at a certain number of players
        if (playerCountComputer > playerCapComputer){
            System.out.println("Sorry, that is too many human players. Defaulting to the cap of " + playerCapComputer + " players.");
            playerCountComputer = playerCapComputer;
        }
        
        for(int i = 1; i <= playerCountHuman; i++){
            // Grab a user input
            System.out.println("Enter human player " + i + " name: ");
            input = scanny.nextLine();

            // Don't allow empty strings
            if(input.equals("")){
                input = "No-Name";
            }
            

            playersHuman.add(new Player(input));
        }
        
        for(int i = 1; i <= playerCountComputer; i++){
            // Grab a user input
            System.out.println("Enter computer player " + i + " name: ");
            input = scanny.nextLine();

            // Don't allow empty strings
            if(input.equals("")){
                input = "No-Name";
            }

            playersComputer.add(new Player(input));
        }
    }
    
    /**
    * Present the current game state in the console.
    */
    public void printGameState(){
        System.out.println("\n------------------");
        
        // Print finished players' names
        String finished = "Finished players: ";
        for(int i = 0; i < playersFinished.size(); i++){
            finished += playersFinished.get(i).getName();
            if(i < playersFinished.size() - 1){
                finished +=  ", "; // Add commas for all but the last one
            }
        }
        
        // Only print finished players if there is at least one
        if(!playersFinished.isEmpty()){
            System.out.println(finished);
        }
        
        // Mostly this will consist of printing everyone's face-up cards.
        // Dealer
        System.out.println(dealer.toStringFaceUp());
        
        // Computer players
        for (Player p: playersComputer){
            System.out.println(p.toStringFaceUp());
        }
        
        // Humans who are not on their turn
        for (int i = 0; i < playersHuman.size(); i++){
            if(i != currentTurn){
                System.out.println(playersHuman.get(i).toStringFaceUp());
            }
        }
        
        // The human whose turn it is
        if(!playersHuman.isEmpty()){
            System.out.println("~~~");
            System.out.println(playersHuman.get(currentTurn).getName() + ", it is your turn!");
            System.out.println(playersHuman.get(currentTurn)); // Only the player whose turn it is sees their own face-down cards
        }
        
    }
    
    /**
     * Prints the winner(s) of the Blackjack game to the console.
     */
    public void detectWinner(){
        int currentHighScore = 0; // Will keep track of the highest sum at or below 21 yet found
        //Player currentBestPlayer = dealer; // Initialize as the dealer by default

        // Make a list to hold everyone who ties for the best number
        ArrayList<Player> scoreWinners = new ArrayList<Player>(); 

        // Make a list to hold everyone who got 5 cards without busting
        ArrayList<Player> deckWinners = new ArrayList<Player>();
        
        // Find the highest number at or below 21 among all players
        for (Player p: playersFinished){
            if (p.getDeck().sum() > currentHighScore && p.getDeck().sum() <= 21){
                currentHighScore = p.getDeck().sum();
                //currentBestPlayer = p;
            }
        }
        // By the end of the loop, the player with the highest sum not greater than 21
        // should be the currentBestPlayer.
        //scoreWinners.add(currentBestPlayer);

        // Tie detection!
        // Now that we know the definitive high score, we look for others with exactly that score.
        for (Player p: playersFinished){
            if (p.getDeck().sum() == currentHighScore){
                scoreWinners.add(p);
            }
        }
        
        // We also find those who got 5 cards without busting.
        // Their victory takes priority over those who got a high score,
        // as it is more rare and special.
        for (Player p: playersFinished){
            if(p.getDeck().size() >= 5 && p.getDeck().sum() <= 21){
                deckWinners.add(p);
            }
        }
        
        if(deckWinners.isEmpty()){
            // SCORE VICTORY: Print those who tied for the high score
            System.out.println("The player(s) with the highest number at or below 21 win!");
            if(scoreWinners.size() >= 2){
                System.out.println("A tie occurred!");
            }
            for(Player w: scoreWinners){
                System.out.println(w.getName() + " wins with the following deck!");
                System.out.println(w);
            }
        }
        else{
            // DECK VICTORY: Print those who got 5 cards without busting
            System.out.println("The player(s) who got 5 cards without busting win!");
            if(deckWinners.size() >= 2){
                System.out.println("A tie occurred!");
            }
            for(Player w: deckWinners){
                System.out.println(w.getName() + " wins with the following deck!");
                System.out.println(w);
            }
        }
        
    }

    
}
