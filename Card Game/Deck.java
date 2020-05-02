import java.util.ArrayList;
import java.util.Collections;

/**
 * A Deck of 52 playing cards.
 *
 * @author Jimmy Sallenback
 * @version 1.0
 */
public class Deck
{
    // instance variables
    private ArrayList<Card> cards = new ArrayList<Card>(52);

    /**
     * Creates a nomral unshuffled Deck of 52 playing cards.
     */
    public Deck()
    {
        this(1, Card.Mode.NORMAL);
    }
    
    /**
     * Creates a normal unshuffled Deck containing 
     * the the cards of 1-7 52-card deck(s)
     */
    public Deck(int decks)
    {
        this(decks, Card.Mode.NORMAL);
    }
    
    /**
     * Creates an unshuffled Deck containing 
     * the the cards of 1-7 52-card deck(s),
     * with cards set to a given mode.
     */
    public Deck(int decks, Card.Mode mode) 
    {
        if (decks < 1 || decks > 6) {
            decks = 1;
        }
        // Creates each card in a 52-card playing deck
        // and places them in order in cards list.
        for (int deck = 0; deck < decks; deck++) {
            for (int card = 0; card < 52; card++) {
                cards.add(card, new Card(card, mode));
            }
        }
    }
    
    /**
     * Return a list of the cards in this deck in
     * the order that they appear in this deck
     */
    public ArrayList<Card> getAll()
    {
        return new ArrayList<Card>(cards);
    }
    
    /**
     * Adds multiple cards to the bottom of this deck
     */
    public void addCards(ArrayList<Card> cards)
    {
        this.cards.addAll(cards);
    }
    
    /**
     * Shuffles the deck.
     */
    public void shuffle()
    {
       Collections.shuffle(cards);
    }

    /**
     * Prints each card in this deck in 
     * the order that they appear in this deck.
     */
    public void printDeck()
    {
        for (Card card : cards) {
            System.out.println(card.toString());
        }
    }
    
    /**
     * Gets the top card of the deck and removes it from the deck.
     */
    public Card deal() 
    {
        return cards.remove(0);
    }
    
    /**
     * Gets the amount of cards in the deck
     */
    public int getSize() 
    {
        return cards.size();
    }
    
    /**
     * Returns comma-seperated string of the cards 
     * in this deck in the order they appear in the deck
     */
    public String toString() {
        String toReturn = "";
        for (Card card : cards) {
            toReturn = toReturn + card + ", ";
        }
        return toReturn;
    }
}
