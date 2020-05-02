import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author Jimmy Sallenback
 * @version 1.1
 */
public class Player
{
    private Hand hand;   // the hand containing the players cards
    private int chips;   // the chips the player has, does not include the chips in the bet
    private int bet;     // the players current bet
    private String name; // the name of the player

    /**
     * Create a player with given name
     */
    public Player(String name)
    {
        // init instance variables
        this.name = name;
        this.hand = new Hand();
    }
    
    /**
     * Get the players name
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * A string representaitio nof the player and it's hand
     */
    public String toString()
    {
        return this.name + ":\n"+ this.hand.toString();
    }
    
    /**
     * Add a card to the players hand.
     */
    public void addCard(Card card)
    {
        this.hand.addCard(card);
    }
    
    /**
     * Add multiple cards to the players hand
     */
    public void addCards(List<Card> cards)
    {
        this.hand.addCards(cards);
    }
    
    /**
     * Remove all cards from the player.
     * 
     * @return the removed cards
     */
    public ArrayList<Card> removeCards()
    {
        return this.hand.removeCards();
    }
    
    /**
     * Remove bet from chips and store bet.
     * The old bet will be returned to this players chip count.
     */
    public int bet(int newBet)
    {
        chips += this.bet;
        // If the newBet is more chips than this player has
        if (newBet > chips) {
            // Place the largest possible bet
            newBet = chips;
        }
        this.bet = newBet;
        chips -= newBet;
        return newBet;
    }
    
    /**
     * Get this players chip count.
     */
    public int getChips()
    {
        return chips;
    }
    
    /**
     * Get this players current bet.
     */
    public int getBet()
    {
        return this.bet;
    }
    
    /**
     * Add chips to this players chip count.
     */
    public void addChips(int chips)
    {
        this.chips += chips;
    }
    
    /**
     * Reset this players bet. 
     * The bet will not be returned to the chip count.
     * 
     * @return the players bet before being reset
     */
    public int confiscateBet()
    {
        int oldBet = bet;
        bet = 0;
        return oldBet;
    }
    
    /**
     * Get the cards in the players hand.
     */
    public ArrayList<Card> getCards()
    {
        return hand.getCards();
    }
    
    /**
     * Get the amount of cards in this players hand.
     */
    public int size()
    {
        return hand.size();
    }
}
