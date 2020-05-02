import java.util.ArrayList;
import java.util.List;
/**
 * Holds cards for a player.
 *
 * @author Jimmy Sallenback
 * @version 1.0
 */
public class Hand
{
    // instance variables
    private ArrayList<Card> hand;
    private int value;

    /**
     * Creates empty hand.
     */
    public Hand()
    {
        // initialise instance variables
        hand = new ArrayList();
    }
    
    /**
     * Creates hand with cards in it.
     */
    public Hand(List<Card> cards)
    {
        hand = new ArrayList();
        hand.addAll(cards);
    }

    /**
     * Adds card to end of hand
     *
     * @param card     card to add
     */
    public void addCard(Card card)
    {
        value += card.getValue();
        hand.add(card);
    }
    
    /**
     * Adds multiple cards to the end of the hand
     */
    public void addCards(List<Card> cards)
    {
        for (Card card : cards) {
            addCard(card);
        }
    }
    
    /**
     * Returns the amount of cards in the hand
     * 
     * @return size of hand
     */
    public int size()
    {
        return hand.size();
    }
    
    /**
     * Returns true if size is greater than 0
     * 
     * @return true if cards in hand
     */
    public boolean hasCards()
    {
        return (hand.size() > 0);
    }
    
    /**
     * Gets and removes the first card in the hand
     * 
     * @return first card in hand
     */
    public Card getTopCard()
    {
        return hand.remove(0);
    }
    
    public ArrayList<Card> getCards()
    {
        return new ArrayList<Card>(hand);
    }
    
    public ArrayList<Card> removeCards()
    {
        ArrayList<Card> oldHand = this.hand;
        this.hand = new ArrayList();
        this.value = 0;
        return oldHand;
    }
    
    public String toString()
    {
        ArrayList<Card> cards = new ArrayList<Card>(this.hand);
        String toReturn;
        if (cards.size() == 0)
        {
            return "";
        }
        
        toReturn = cards.remove(0).toString();
        
        for (Card card : cards)
        {
            toReturn = toReturn + "\n" + card;
        }
        return toReturn;
    }
}
