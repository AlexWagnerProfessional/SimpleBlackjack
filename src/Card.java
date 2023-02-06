/**
 * This class represents a single one of the 52 standard cards in Blackjack.
 */
public class Card {
    // Attributes
    private String name; // e.g. "King of Spades"
    private int value; // A number from 2 to 11 depending on the name
    private boolean faceUp; // True for most cards, false for the first card dealt to each player
    
    // Constructor
    public Card(String name, boolean faceUp){
        // Name and whether it's face-up or not are determined by the programmer;
        // value is determined by name
        this.name = name;
        this.faceUp = faceUp;
        
        this.value = nameToValue(name);
    }
    
    
    // Methods
    /**
    * Converts a card's name to an integer value for how much it's worth in blackjack.
    * @param name The name of the card, e.g. "Jack of Hearts"
    * @return An integer from 2 to 11 based on card value in blackjack
    */
    private int nameToValue(String name){
        int value;
        
        // Split by spaces; only the first word is needed.
        String[] splitName = name.split(" ");
        
        // First catch if it's a non-numerical name.
        // Ace = 11
        if(splitName[0].equals("Ace")){
            value = 11;
        }
        
        // 
        else if (splitName[0].equals("Jack") || splitName[0].equals("King") || splitName[0].equals("Queen")){
            value = 10;
        }
        
        // If it gets down here, presumably it is a numerical name that can be
        // converted to an integer.
        else{
            value = Integer.parseInt(splitName[0]);
        }
        
        return value;
    }
    
    
    // Getters and a setter
    
    public String getName(){
        return name;
    }
    
    public int getValue(){
        return value;
    }
    
    public boolean getFaceUp(){
        return faceUp;
    }
    
    public void setFaceUp(boolean faceUp){
        this.faceUp = faceUp;
    }
    
    @Override
    public String toString(){
        return String.valueOf(value) + ": " + name;
    }
}
