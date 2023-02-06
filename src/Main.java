import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws Exception {
        // TEST: Deck creation
        // Succeeded!
        /*
        Deck decky = new Deck();
        System.out.println(decky);
        
        decky.fillDeck();
        System.out.println(decky);
        System.out.println(decky.size());
        //*/
        
        // TEST: Take random card
        // Succeeded!
        /*
        Deck decky = new Deck();
        decky.fillDeck();
        
        for (int i = 0; i < 52; i++){
            System.out.println(decky.takeRandomCard());
        }
        System.out.println(decky);
        */
        
        // TEST: Transfer between decks, and print only face-up cards
        // Succeeded!
        /*
        Deck decky = new Deck();
        decky.fillDeck();
        
        Deck recky = new Deck();
        decky.transferRandomCard(recky, false);
        decky.transferRandomCard(recky, true);
        decky.transferRandomCard(recky, true);
        decky.transferRandomCard(recky, false);
        System.out.println(recky.toStringFaceUp());
        //*/
        
        
        // TEST: Player creation
        // Succeeded!
        /*
        Player anni = new Player("Anni");
        Deck masterDeck = new Deck();
        masterDeck.fillDeck();
        
        anni.hit(masterDeck);
        anni.hit(masterDeck);
        anni.hit(masterDeck);
        System.out.println(anni.toStringFaceUp());
        System.out.println(anni);
        //*/
        
        // PART 1: Create and display multiple players
        Game game = new Game(); // Initialize the object that keeps track of the game state
        // Recall that the "try-with" block below prevents resource leaks; it should include everything that may need the scanner, as it will be close after the end brace.
        try (Scanner scanny = new Scanner(System.in)) {
            game.initializePlayers(scanny);
            
            
            // PART 2: Make each player hit twice to start the game
            // Succeeded!
            game.dealer.hit(game.masterDeck);
            game.dealer.hit(game.masterDeck);
            
            for (Player c: game.playersComputer){
                c.hit(game.masterDeck);
                c.hit(game.masterDeck);
            }
            for (Player h: game.playersHuman){
                h.hit(game.masterDeck);
                h.hit(game.masterDeck);
            }
            
            game.printGameState();
            
            String actionChoice; // Initialize for later
            
            // PART 3: Loop and let the players decide what to do
            // The loop should run until everyone besides dealer is in playersFinished
            while(!game.playersHuman.isEmpty() || !game.playersComputer.isEmpty()){
                // First, computer players go
                for (Player c: game.playersComputer){
                    // AI: The computer player simply hits below a certain threshold or stands otherwise
                    if (c.getDeck().sum() < 16){
                        // HIT: Draw another card and remain in the game
                        c.hit(game.masterDeck);
                    }
                    else{
                        // STAND: Transfer this player to the finished players
                        game.playersFinished.add(c);
                    }
                }
                
                // Once computer players have finished, eliminate them from the list of active computer players
                // The Java ArrayList removeAll() method removes all the elements from the arraylist that are also present in the specified collection. The syntax of the removeAll() method is: arraylist. removeAll(Collection c);
                game.playersComputer.removeAll(game.playersFinished);
                
                // Then human players make decisions
                
                for(game.currentTurn = 0; game.currentTurn < game.playersHuman.size(); game.currentTurn++){
                    game.printGameState();
                    System.out.println(game.playersHuman.get(game.currentTurn).getName() + ", type a response containing the word Hit to take another card, or anything else to Stand, taking no other cards and declaring yourself done.");
                    actionChoice = scanny.nextLine();
                    
                    if(actionChoice.toLowerCase().contains("hit")){
                        // HIT: Draw another card and remain in the game
                        game.playersHuman.get(game.currentTurn).hit(game.masterDeck);
                        System.out.println(game.playersHuman.get(game.currentTurn)); // Show the player's deck again now that they've hit
                    }
                    else{
                        // STAND: Transfer this player to the finished players
                        game.playersFinished.add(game.playersHuman.get(game.currentTurn));
                    }
                }
                
                // All human players who have finished will now be eliminated from the list of active ones.
                game.playersHuman.removeAll(game.playersFinished);
                
                // PART 4: Detect players who have busted and eliminate them before the next loop
                // This includes anyone who has 5 cards, who should win automatically
                // It also eliminates everyone if they've somehow emptied the master deck (useful if the maximum number of players is set excessively high)
                for (Player c: game.playersComputer){
                    if(c.getDeck().sum() > 21 || c.getDeck().size() >= 5){
                        // BUST: Player is eliminated
                        // Or 5 cards: Player should win
                        // Either way, transfer them to the finished players
                        game.playersFinished.add(c);
                    }
                    
                }
                
                for (Player h: game.playersComputer){
                    if(h.getDeck().sum() > 21 || h.getDeck().size() >= 5){
                        // BUST: Player is eliminated
                        // Or 5 cards: Player should win
                        // Either way, transfer them to the finished players
                        game.playersFinished.add(h);
                    }
                    
                }
                
                // Eliminate these busted or overloaded players, and after that, the next loop begins if there are any left
                game.playersComputer.removeAll(game.playersFinished);
                game.playersHuman.removeAll(game.playersFinished);
            }
        }
        
        
        
        
        // PART 5: Once everyone else is finished, dealer goes last
        // Dealer continues taking turns until ready to stand
        while(game.dealer.getDeck().sum() < 16 && game.dealer.getDeck().size() < 5){
            game.dealer.hit(game.masterDeck);
        }
        // Once the loop is over, the dealer is finished
        game.playersFinished.add(game.dealer);
        
        // PART 6: Once everyone is finished, detect the winner
        // This includes those who have 5 cards without busting
        System.out.println("\n~~~GAME FINISHED!~~~");
        
        game.detectWinner();
        
        System.out.println("Thank you for playing Blackjack!");
    }
}
