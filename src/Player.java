/**
* This class keeps track of a few pieces of information important for each player.
* Namely, their personal deck, and a method that lets them hit or stay.
*/
public class Player {
    // Attributes
    private Deck deck;
    private String name;
    
    // Constructor
    public Player(String name){
        this.deck = new Deck();
        this.name = name;
    }
    
    // Methods
    /**
    * A means of providing a single new card to a player, drawing from any deck, but it is intended to work with the main deck containing all the remaining dealable cards.
    * @param masterDeck Please plug in the masterDeck as a parameter so the correct deck is drawn from.
    */
    public void hit(Deck masterDeck){
        // Safeguard: Only do anything if the masterDeck is not empty.
        if(!masterDeck.isEmpty()){
            // If the player has no cards, it is assumed that the game is starting and their first card should be face-down.
            if(this.deck.isEmpty()){
                masterDeck.transferRandomCard(this.deck, false);
            }
            
            // Otherwise, the card is dealt face-up.
            else{
                masterDeck.transferRandomCard(this.deck, true);
            }
        }

        else{
            System.out.println("Cannot hit from an empty deck. No cards drawn.");
        }
        
    }
    
    public String getName(){
        return name;
    }

    public Deck getDeck(){
        return deck;
    }
    
    /**
    * A version of toString that obscures face-down cards.
    * Good for showing opponent status.
    * @return A string mostly like toString() output but obscuring face-up cards and the full sum.
    */
    public String toStringFaceUp(){
        String returnMe = name + " has " + deck.size() + " cards totaling some number higher than " + deck.sumFaceUp() + ": ";
        returnMe += "\n";
        returnMe += deck.toStringFaceUp();
        returnMe += "\n";
        return returnMe;
    }
    
    @Override
    public String toString(){
        String returnMe = name + " has " + deck.size() + " cards totaling " + deck.sum() + ": ";
        returnMe += "\n";
        returnMe += deck.toString();
        returnMe += "\n";
        return returnMe;
    }
}
