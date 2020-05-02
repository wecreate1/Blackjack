import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class BlackJackPlayer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BlackJackPlayer extends Player
{
    private int value; // The sum of the hands cards values

    /**
     * Create a blackjack player with given name.
     */
    public BlackJackPlayer(String name)
    {
        super(name);
    }
    
    /**
     * Returns if this player's hand contains a 
     * card with a rank that can be reduced.
     * 
     * @see Card#canDemoteRank()
     */
    public boolean canReduceValue()
    {
        // For each card in this player's hand
        for (Card card : super.getCards()) {
            // Check if the card's rank can be reduced
            if (card.canDemoteRank()) {
                // If it can, than the value of this player's hand can be reduced
                return true;
            }
        }
        return false;
    }

    /**
     * Reduces this hands values, if possible, when the hands value is over 21.
     * 
     * @return the new value of the hand
     */
    private int optimizeValue()
    {
        // If the value of this player's hand is greater than 21
        if (value > 21) {
            // Try to reduce this player's hand's value by
            // Checking each card in the player's hand and
            for (Card card : super.getCards()) {
                // If a card's value can be reduced
                if (card.canDemoteRank()) {
                    // Reduce the cards rank and update this hand's value
                    value += card.demoteRank();
                    // Since the value has changed, if it is less than or equal to 21
                    if (value <= 21) {
                        // Stop attempting to reduce this player's hand's value
                        break;
                    }
                }
            }
        }
        return value;
    }
    
    /**
     * {@inheritDoc}
     * And optimize the hand's value so it does not go over 21, if possible.
     */
    public void addCard(Card card)
    {
        super.addCard(card);
        value += card.getRank();
        optimizeValue();
    }
    
    /**
     * {@inheritDoc}
     * And optimize the hand's value so it does not go over 21, if possible.
     */
    public void addCards(List<Card> cards)
    {
        super.addCards(cards);
        // Update this player's hand value with the rank of each card being added
        for (Card card : cards) {
            value += card.getRank();
        }
    }
    
    /**
     * Get this player's hand value, 
     * which has been adjusted so it not over 21, if possible.
     */
    public int getValue()
    {
        return value;
    }
    
    /**
     * {@inheritDoc}
     * And reset the player's hand value, since there will be no cards.
     */
    public ArrayList<Card> removeCards()
    {
        this.value = 0;
        return super.removeCards();
    }
}
