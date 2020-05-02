import java.util.ArrayList;
import java.util.List;
/**
 * A child of the BlackJackPlayer class, but the first card can be hidden.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Dealer extends BlackJackPlayer
{
    private Card hiddenCard; // The fisrt card in the dealers hand.
    private boolean hidden = true; // If the first card should be hidden
    
    /**
     * Creates a dealer.
     */
    public Dealer()
    {
        super("Dealer");
    }
    
    /**
     * {@inheritDoc}
     * And if the card given is the dealers first card, hide it.
     */
    public void addCard(Card card)
    {
        // If the dealer does not have any cards 
        if (hiddenCard == null) {
            // Then this card is the first card
            // And should be the hidden card
            hiddenCard = card;
            // If the first card should be hidden
            if (hidden) {
                // Give the parent object an anonymous card
                super.addCard(Card.getEmptyCard());
            } else {
                // Else give the parent object the actual card
                super.addCard(card);
            }
        } else {
            super.addCard(card);
        }
    }
    
    /**
     * {@inheritDoc}
     * And if the first card given will be the dealers first card, hide it.
     */
    public void addCards(List<Card> cards)
    {
        if (hiddenCard == null) {
            hiddenCard = cards.get(0);
            cards.set(0, Card.getEmptyCard());
        }
        super.addCards(cards);
    }
    
    /**
     * {@inheritDoc}
     */
    public ArrayList<Card> removeCards()
    {
        ArrayList<Card> oldCards;
        if (hidden) {
            oldCards = new ArrayList<Card>();
            if (hiddenCard == null)
                return oldCards;
            oldCards.add(hiddenCard);
            oldCards.addAll(super.removeCards());
            oldCards.remove(1);
            return oldCards;
        } else {
            hiddenCard = null;
            return super.removeCards();
        }
    }
    
    /**
     * Make the first card visible.
     */
    public void showCard()
    {
        ArrayList<Card> superCards = super.removeCards();
        super.addCard(hiddenCard);
        super.addCards(superCards.subList(1, superCards.size()));
        hidden = false;
    }
    
    /**
     * Hide the first card.
     */
    public void hideCard()
    {
        ArrayList<Card> superCards = super.removeCards();
        if (hiddenCard != null) {
            super.addCard(Card.getEmptyCard());
            super.addCards(superCards.subList(1, superCards.size()));
        }
        hidden = true;
    }
}
