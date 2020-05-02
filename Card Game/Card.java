
/**
 * Represnts a playing card from a 52-card playing deck.
 *
 * @author Jimmy Sallenback
 * @version 2
 */
public class Card implements Comparable<Card>
{
    // String representation of eah card suit
    static public final String[] CARD_SUITS        = {"Spades", "Hearts", "Diamonds", "Clubs"};
    
    // String representation of each face values a card can have (0-12)
    static public final String[] CARD_VALUES       = {"Ace", "Two", "Three", "Four", "Five",
                                                      "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
                                                      "Queen", "King"};
                                                      
    // 2 letter string representations of each face values a card can have (0-12)
    static public final String[] CARD_VALUES_SHORT = {" A", " 2", " 3", " 4", " 5", " 6", " 7", 
                                                      " 8", " 9", "10", " J", " Q", " K"};

    // Different modes for a card. Modes change the values the card rank. 
    public enum Mode {
        NORMAL,
        BLACKJACK
    }
    
    // instance variables
    private int card;  // The number of the card in a 52-card playing deck (0-51)
    private Mode mode; // The mode this card is in
    private int cardValue;  // The face value of the card (0-12)
    private int cardSuit;   // The suit of this card (0-3)
    protected int cardRank; // The rank of this card based on the mode
    private boolean visible = true; // If this card shoud reveal its value and suit

    /**
     * Creates a normal card
     * 
     * @param card  the number of the card in a 52-card playing deck, (0-51 inclusive)
     */
    public Card(int card)
    {
        this(card, Mode.NORMAL);
    }
    
    /**
     * Creates a card with specialized rank values
     * 
     * @param card  the number of the card in a 52-card playing deck, (0-51 inclusive)
     * @param mode  the mode that defines the rank values of the card
     */
    public Card(int card, Mode mode)
    {
        // initializes instance variables
        this.mode = mode;
        
        // Validates 0 <= card < 52
        if (card < 52 && card >= 0) {
            // decodes suit from (0-51), 0-12 is Spades...
            cardSuit = card / 13;
            // decodes face value from (0-51), {0, 13, 26, 39} is Ace...
            cardValue = (card % 13) + 1;
        } else {
            // If passed card value is not possible, init this to Ace of Spades
            cardSuit = 0;  // init suit to Spades
            cardValue = 1; // init value to Ace
        }
        
        // Set rank to value, the default behaviour
        cardRank = cardValue;
        
        // Use special rules to set rank for blackjack cards
        if (mode == Mode.BLACKJACK) {
            // For face cards (Jack, Queen, King), set rank to 10 
            if (cardValue > 10) {
                cardRank = 10;
                
            // For Aces, the starting rank is 11
            } else if (cardValue == 1) {
                cardRank = 11;
            }
        }
    }
    
    /**
     * Determine if this card can have multiple ranks,
     * and if the rank can be reduced
     */
    public boolean canDemoteRank() {
        return (mode == Mode.BLACKJACK && cardValue == 1 && cardRank != 1);
    }
    
    /**
     * Reduce this card's rank if possible
     * 
     * @return the amount the rank has changed (new rank - old rank)
     */
    public int demoteRank()
    {
        int oldRank = cardRank;
        if (mode == Mode.BLACKJACK) {
            if (cardValue == 1) {
                cardRank = 1;
            }
        }
        return cardRank - oldRank;
    }
    
    /**
     * Returns this cards rank (returns 0 if this card is not visible)
     */
    public int getRank()
    {
        if (visible)
            return cardRank;
        else
            return 0;
    }
    
    /**
     * Return the number this card is in a 52-card playing deck
     */
    public int getCardId()
    {
        return this.card;
    }
    
    /**
     * Gets the face value value of the card.
     */
    public int getValue()
    {
        if (visible)
            return cardValue;
        else
            return 0;
    }

    /**
     * Gets the suit of the card.
     * retuns -1 if this card is not visible
     */
    public int getSuit() 
    {
        if (visible)
            return cardSuit;
        else
            return -1;
    }

    /**
     * Gets string representing the value of this card.
     * The string is left-padded with spaces
     * so the string is fixed length 2.
     * 
     * returns "#" if this card is not visible
     */
    public String toStringValue()
    {
        if (visible)
            return CARD_VALUES_SHORT[cardValue-1];
        else
            return " #";
    }

    /**
     * Gets the string representation of this card
     * in the format "Value of Suits"
     * 
     * returns "#" if this card is not visible
     */
    public String toString() 
    {
        if (visible)
            return CARD_VALUES[cardValue-1] + " of " + CARD_SUITS[cardSuit];
        else
            return "######";
    }
    
    /**
     * Hides card's value and suit when set false.
     */
    public void setVisibility(boolean visibility)
    {
        visible = visibility;
    }
    
    /**
     * Compares this cards to another card, 
     * by face value, then by suit
     */
    public int compareTo(Card card) 
    {
        int valueCompare = this.cardValue - card.getValue();
        if (valueCompare != 0)
            return valueCompare;
        else
            return this.cardSuit - card.getSuit();
    }
    
    /**
     * Determine if this card has the same 
     * face value and suit as another card
     */
    public boolean equals(Card card)
    {
        return (this.card == card.getCardId());
    }
    
    /**
     * Get a new Ace of Spades with visibilty set to false
     */
    public static Card getEmptyCard()
    {
        Card card = new Card(0);
        card.setVisibility(false);
        return card;
    }
}
