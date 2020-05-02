import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 * Write a description of class BlackJack here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BlackJack implements ActionListener
{
    private Shoe shoe;
    private ArrayList<BlackJackPlayer> activePlayers; // All players that have not run out of chips.
    private ArrayList<BlackJackPlayer> players;       // All players that are, and or have played.
    private Dealer dealer;  // The dealer
    private ArrayList<BlackJackPlayer> notFinishedPlayers; // All players that have not busted, or choose to stand, this round.
    private BlackJackUI ui; // The object handling the GUI interface
    private int currentPlayerIndex; // The index of the currently playing player in notFinishedPlayers
    private boolean roundOver = true; // If there are no more players that can play this round.
    private int winsAndLosses = 0; // The total chips that players lost. If a player wins chips, the amount should be subtracted here
    
    /**
     * Create a blackjack game
     */
    public BlackJack()
    {
        // initialise instance variables
        makePlayers();
        dealer = new Dealer();
        
        ui = new BlackJackUI(dealer, activePlayers.get(0), this);
        
        shoe = new Shoe(ui.askForDecks(Shoe.minimumDecks(activePlayers.size())), activePlayers.size());
        shoe.shuffle();
        
        addStartChips();
        
        startRound();
    }
    
    /**
     * Ask user for the amount of chips players should start with, 
     * and give each player that amount of chips
     */
    private void addStartChips()
    {
        int chips = ui.askForStartChips();
        for (BlackJackPlayer player : activePlayers) {
            player.addChips(chips);
        }
    }
    
    /**
     * Give a specified amount of cards to the dealer.
     */
    private void dealToDealer(int amount)
    {
        for (int i = 0; i < amount; i++) {
            dealer.addCard(shoe.deal());
        }
    }
        
    /**
     * Set the currentPlayerIndex to the next valid index
     * 
     * @param playerRemoved  if the current player or a player before 
     *  the current player in the notFinishedPlayers list was removed
     */
    private void nextPlayer(boolean playerRemoved)
    {
        if (notFinishedPlayers.size() > 0) {
            if (!playerRemoved) {
                currentPlayerIndex++;
            }
            if (currentPlayerIndex >= notFinishedPlayers.size()) {
                currentPlayerIndex = 0;
            }
        } else {
            dealerPlay();
            ui.updatePlayer(0);
            roundOver = true;
        }
    }

    /**
     * Make the dealer play according to blackjack rules.
     * The dealer will: always hit when the value of its hand is 16 or lower,
     *                  and hit when the value of its hand is 17 only when the hand is "soft" (when it has an Ace counted as an 11)
     */
    public void dealerPlay()
    {
        dealer.showCard();
        while (dealer.getValue() < 17 || (dealer.getValue() == 17 && dealer.canReduceValue())) {
            dealer.addCard(shoe.deal());
        }
        ui.changeButtonLayout(1);
    }
    
    /**
     * Handles button presses from the main ui frame.
     * The buttons are: Hit, Stand, and Next
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = ((JButton) e.getSource()).getActionCommand();
        if (action.equals("Hit")) {
            hit();
        } else if (action.equals("Stand")) {
            stand();
        } else if (action.equals("Next")) {
            if (roundOver) {
                endRound();
            } else {
                ui.changePlayer(1, getCurrentPlayer());
                ui.changeButtonLayout(0);
            }
        }
    }
    
    /**
     * Returns the player object at currenPlayerIndex in notFinishedPlayers.
     */
    private BlackJackPlayer getCurrentPlayer()
    {
        return notFinishedPlayers.get(currentPlayerIndex);
    }
    
    /**
     * Deals a card to the current player and advances the nextPlayerIndex.
     */
    private void hit()
    {
        BlackJackPlayer player = getCurrentPlayer();
        player.addCard(shoe.deal());
        ui.changeButtonLayout(1);
        ui.updatePlayer(1);
        if (player.getValue() > 21) {
            notFinishedPlayers.remove(currentPlayerIndex);
            playerLoses(player, true);
            nextPlayer(true);
        } else {
            nextPlayer(false);
        }
    }
    
    /**
     * Remove the player from notFinishedPlayers
     */
    private void stand()
    {
        BlackJackPlayer player = getCurrentPlayer();
        notFinishedPlayers.remove(currentPlayerIndex);
        nextPlayer(true);
        if (!roundOver) {
            ui.changePlayer(1, getCurrentPlayer());
        }
    }
    
    /**
     * Finalize the round by: finding which player wins and losses, 
     * and remove them from the game if they are out of chips,
     * then start the next round.
     */
    private void endRound()
    {
        ArrayList<BlackJackPlayer> playersToRemove = new ArrayList<BlackJackPlayer>();
        
        if (dealer.getValue() > 21) {
            ui.showDealerBustMessage();
            for (BlackJackPlayer player : activePlayers) {
                if (player.getValue() <= 21 && player.getBet() > 0) {
                    playerWins(player, false);
                }
            }
        } else {
            for (BlackJackPlayer player : activePlayers) {
                if (player.getValue() <= 21 && player.getBet() > 0) {
                    if (player.getValue() > dealer.getValue()) {
                        playerWins(player, false);
                    } else {
                        playerLoses(player, false);
                    }
                }
            }
        }
        
        for (BlackJackPlayer player : activePlayers) {
            shoe.addCards(player.removeCards());
            if (player.getChips() <= 0) {
                ui.showPlayerOutOfChipsMessage(player.getName());
                playersToRemove.add(player);
            }
        }
        
        for (BlackJackPlayer player : playersToRemove) {
            activePlayers.remove(player);
        }
        
        shoe.addCards(dealer.removeCards());
        
        if (activePlayers.size() == 0) {
            ui.showNoPlayersMessage();
            ui.showEndStats(winsAndLosses, players);
        } else if (ui.askKeepPlaying()) {
            startRound();
        } else {
            ui.showEndStats(winsAndLosses, players);
        }
    }
    
    /**
     * Give the player their winnings.
     */
    private void playerWins(BlackJackPlayer player, boolean hasBlackJack)
    {
        int bet = player.confiscateBet();
        int winnings = bet * 2;
        if (hasBlackJack)
            winnings *= 1.5;
        ui.changePlayer(1, player);
        ui.showPlayerWinsMessage(player.getName(), bet, winnings, hasBlackJack);
        winsAndLosses += bet - winnings;
        player.addChips(winnings);
    }
    
    /**
     * Take the player's bet.
     */
    private void playerLoses(BlackJackPlayer player, boolean busted)
    {
        int bet = player.confiscateBet();
        winsAndLosses += bet;
        ui.showPlayerLosesMessage(player.getName(), bet, busted);
    }
    
    /**
     * Initialize the round by: Checking there are enough cards to play, 
     * deal ther starting cards, and checking for blackjacks.
     */
    private void startRound()
    {
        dealer.hideCard();
        
        if (!shoe.hasEnoughCards()) {
            shoe.shuffle();
            ui.showShuffledMessage();
        }
        
        for (BlackJackPlayer player : activePlayers) {
            ui.askForBet(player);
        }
        
        dealToDealer(2);
        deal(2);
        notFinishedPlayers = new ArrayList<BlackJackPlayer>(activePlayers);
        currentPlayerIndex = 0;
        
        for (BlackJackPlayer player : activePlayers) {
            if (player.getValue() == 21) {
                ui.changePlayer(1, player);
                playerWins(player, true);
                notFinishedPlayers.remove(player);
            }
        }
        
        if (notFinishedPlayers.size() > 0) {
            ui.changePlayer(0, dealer);
            ui.changePlayer(1, notFinishedPlayers.get(0));
            ui.changeButtonLayout(0);
            roundOver = false;
        } else {
            startRound();
        }
    }
    
    /**
     * Deal one card to all the players (not including the dealer)
     * 
     * @see #dealToDealer(int)
     */
    private void deal()
    {
        deal(1);
    }
    
    /**
     * Deal a given amount of cards to all the players (not including the dealer)
     * 
     * @see #dealToDealer(int)
     */
    private void deal(int amount)
    {
        for (int i = 0; i < amount; i++) {
            for (BlackJackPlayer player : activePlayers) {
                player.addCard(shoe.deal());
            }
        }
    }
    
    /**
     * Ask the user for the number of players playing, 
     * and create them and place them in this.players ans this.activePlayers
     */
    private void makePlayers()
    {
        int numOfPlayers = BlackJackUI.askForNumOfPlayers();
        
        this.players = new ArrayList<BlackJackPlayer>();
        
        for (int player = 1; player <= numOfPlayers; player++) {
            players.add(new BlackJackPlayer("PLAYER " + player));
        }
        
        this.activePlayers = new ArrayList<BlackJackPlayer>(players);
    }
}
