
/**
 * Write a description of class Shoe here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Shoe extends Deck
{
    // instance variables - replace the example below with your own
    private int shoeCounter;
    private int players;
    
    /**
     * Creates mutiple decks
     * 
     * @param decks    the amount of decks to create
     * @param players  the number of players playing 
     *                     (to calculate how many cards are needed per round)
     */
    public Shoe(int decks, int players)
    {
        // initialise instance variables
        super(1, Card.Mode.BLACKJACK);
        for (int deck = 1; deck < decks; deck++) {
            this.addCards(new Deck(1, Card.Mode.BLACKJACK).getAll());
        }
        this.players = players;
        resetCounter();
    }

    /**
     * Determine if there are enough cards to play 
     * another round without shuffling
     */
    public boolean hasEnoughCards()
    {
        // put your code here
        return (shoeCounter > 0);
    }
    
    /**
     * {@inheritDoc}
     * And subtract one from the "how many cards can be 
     * taken out until a reshuffle is needed" counter
     */
    public Card deal()
    {
        shoeCounter--;
        return super.deal();
    }
    
    /**
     * Reset the shoeCounter which counts how many 
     * more cards can be taken out without a reshuffle.
     */
    private void resetCounter()
    {
        shoeCounter = super.getSize() - (int) ((players+1) * 3.78);
    }
    
    /**
     * {@inheritDoc}
     */
    public void shuffle()
    {
        super.shuffle();
        resetCounter();
    }
    
    /**
     * Change how many players are playing to adjust the shoe calculation
     */
    public void changePlayers(int players)
    {
        this.players = players;
        resetCounter();
    }
    
    /**
     * Determine the minimum number of decks needed for a given number of 
     * players to play without running out of cards in the middle of a round.
     */
    public static int minimumDecks(int players)
    {
        return (int) Math.ceil(((players+1) * 3.78) / 52.0);
    }
}
