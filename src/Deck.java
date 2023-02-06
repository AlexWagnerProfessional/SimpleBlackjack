import java.util.ArrayList;
import java.util.Random;

/**
* This object will hold an ArrayList of Card objects for use in blackjack.
*/
public class Deck {
    // Attributes
    private ArrayList<Card> cards;
    
    // Constructor
    public Deck(){
        cards = new ArrayList<Card>();
    }
    
    // Methods
    /**
    * Adds a given card to the end of this deck.
    * Simply uses the add() method from ArrayLists, with no index specified.
    * @param cardToAdd The card that will now appear in this deck.
    */
    public void add(Card cardToAdd){
        cards.add(cardToAdd);
    }
    
    
    /**
    * Reset this Deck to have all 52 standard cards and no others.
    * Good for the masterDeck, not so good for players, whose decks are meant to be small.
    */
    public void fillDeck(){
        // Start fresh
        cards.clear();
        
        // Add each of the 52 standard cards, one suit at a time
        String[] suits = {" of Hearts", " of Clubs", " of Spades", " of Diamonds"};
        String[] names = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        
        for (String suit : suits){
            for (String namey : names){
                cards.add(new Card(namey + suit, true));
            }
        }
    }
    
    /**
    * Sums up the values of all cards in the deck. Useful for the player.
    * The value 11 is special, as it is recognized as an ace card.
    * The sum will normally add 11 for aces, but will add 1 instead if the total
    * would be higher than 21.
    * @return
    */
    public int sum(){
        int acesFound = 0;
        int total = 0;
        for (Card card : cards){
            total += card.getValue();
            // Keep track of the number of aces and reduce the total below 21 if needed
            if (card.getValue() == 11){
                acesFound++;
            }
        }
        
        while(acesFound > 0 && total > 21){
            // Use aces' special property to avoid a bust if needed
            acesFound--;
            total -= 10;
        }
        
        return total;
    }
    
    /**
    * Sums up the values of all face-up cards in a deck. Useful for other players
    * besides the one whose turn it is.
    * @return
    */
    public int sumFaceUp(){
        int acesFound = 0;
        int total = 0;
        for (Card card : cards){
            if (card.getFaceUp()){
                total += card.getValue();
                // Keep track of the number of aces and reduce the total below 21 if needed
                if (card.getValue() == 11){
                    acesFound++;
                }
            }
            
        }
        
        while(acesFound > 0 && total > 21){
            // Use aces' special property to avoid a bust if needed
            acesFound--;
            total -= 10;
        }
        
        return total;
    }
    
    /**
    * Picks out a random card from the deck, without removing anything.
    * @return A Card object chosen pseudo-randomly from this deck.
    */
    public Card getRandomCard(){
        // Pick a random index from 0 to deck size - 1
        Random randomizer = new Random();
        int indexChoice = randomizer.nextInt(cards.size());
        
        // Find the card at that index and return it
        return cards.get(indexChoice);
    }
    
    
    
    /**
    * Transfers a random card from this deck to a recipient deck.
    * @param recipient The Deck that the card will be placed into.
    * @param faceUp Specify whether the card should be face up (true) or face down (false) when placed in the recipient deck. Usually true is best, but the first card for each player should be false instead.
    */
    public void transferRandomCard(Deck recipient, boolean faceUp){
        // Any random card will do.
        Card cardToMove = this.getRandomCard();
        
        // Set whether it is face up or not depending on the parameter the user plugged in.
        cardToMove.setFaceUp(faceUp);
        
        // Put the chosen card into the other deck and remove it from this one.
        recipient.add(cardToMove);
        cards.remove(cardToMove);
    }
    
    /**
    * A version of toString that shows face-up cards normally but
    * only shows "A face-down card" for face-down cards.
    * Good for displaying opponents' cards.
    * @return A string that shows the deck with face-down cards obscured.
    */
    public String toStringFaceUp(){
        String returnMe = "[";
        for (int i = 0; i < cards.size(); i++){
            // Show either the card or its back
            if (cards.get(i).getFaceUp()){
                returnMe += cards.get(i).toString();
            }
            else{
                returnMe += "A face-down card";
            }
            
            // For all but the last one, add a comma and space
            if (i < cards.size() - 1){
                returnMe += ", ";
            }
        }
        
        // Finish off with an end bracket to make it consistent with the normal printing
        returnMe += "]";
        
        return returnMe;
    }
    
    // Down here, we borrow a few functionalities from ArrayLists, by the same names.
    public int size(){
        return cards.size();
    }
    
    public boolean isEmpty(){
        return cards.isEmpty();
    }
    
    @Override
    public String toString(){
        return cards.toString();
    }
}
