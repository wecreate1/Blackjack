import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Component;
/**
 * Write a description of class BlackJackUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BlackJackUI
{
    enum Action 
    {
        HIT, 
        STAND
    }
    
    // instance variables - replace the example below with your own
    private PlayerPanel[] playerPanels = new PlayerPanel[2];
    private JPanel[] buttonPanels = new JPanel[2];
    private JPanel buttonPanel;
    private JFrame frame;

    /**
     * Constructor for objects of class BlackJackUI
     */
    public BlackJackUI(BlackJackPlayer playerOne, BlackJackPlayer playerTwo, ActionListener listener)
    {
        playerPanels[0] = new PlayerPanel(playerOne);
        playerPanels[1] = new PlayerPanel(playerTwo);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(playerPanels[0]);
        panel.add(playerPanels[1]);
        
        buttonPanels[0] = new JPanel();
        buttonPanels[0].setLayout(new BoxLayout(buttonPanels[0], BoxLayout.X_AXIS));
        
        JButton hitButton = new JButton("Hit");
        hitButton.addActionListener(listener);
        buttonPanels[0].add(hitButton);
        
        JButton standButton = new JButton("Stand");
        standButton.addActionListener(listener);
        buttonPanels[0].add(standButton);
        
        buttonPanels[1] = new JPanel();
        buttonPanels[1].setLayout(new BoxLayout(buttonPanels[1],BoxLayout.X_AXIS));
        
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(listener);
        buttonPanels[1].add(nextButton);
        
        buttonPanel = new JPanel();
        buttonPanel.add(buttonPanels[0]);
        
        panel.add(buttonPanel);
        
        frame = new JFrame("Play");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(200,300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void updatePlayer(int slot)
    {
        if (slot < 0 || slot > 1)
            slot = 1;
        playerPanels[slot].update();
    }
    
    public void changePlayer(int slot, BlackJackPlayer player)
    {
        if (slot < 0 || slot > 1)
            slot = 1;
        playerPanels[slot].changePlayer(player);
    }
    
    public void changeButtonLayout(int layout)
    {
        if (layout < 0 || layout > 1)
            layout = 0;
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();
        
        buttonPanel.add(buttonPanels[layout]);
    }
    
    public void showDealerBustMessage()
    {
        String str = "The dealer busted. All players that have not exceeded 21 will win.";
        JOptionPane.showMessageDialog(frame, str);
    }
    
    public void showPlayerWinsMessage(String name, int bet, int recieve, boolean hasBlackJack)
    {
        String reason;
        if (hasBlackJack)
            reason = "has blackjack";
        else
            reason = "has won";
            
        String str = String.format("%s %s. They bet %d chip(s), and will recieve %d chips", 
                                    name, reason,   bet,                         recieve);
        JOptionPane.showMessageDialog(frame, str);
    }
    
    public void askForBet(BlackJackPlayer player) 
    {
        String input;
        int bet = 0;
        String message = String.format("%s has %d chip(s). How many chips should they bet",
                                        player.getName(), player.getChips());
        String validRangeMessage = String.format("Bet must be between 1 and %d (inclusive).",
                                                                          player.getChips());
        while (bet == 0) {
            input = JOptionPane.showInputDialog(frame, message);
            try {
                bet = Integer.parseInt(input);
                if (bet <= 0 || bet > player.getChips()) {
                    bet = 0;
                    JOptionPane.showMessageDialog(frame, validRangeMessage);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Bet must be an integer.");
            }
        }
        player.bet(bet);
    }
    
    public int askForDecks(int minDecks)
    {
        int decks = 0;
        String validRangeMessage = "Number of decks must be greater than " +
                                   "or equal to " + minDecks;
        String input;
        while (decks <= 0) {
            try {
                input = JOptionPane.showInputDialog("How many decks should be used?");
                decks = Integer.parseInt(input);
                if (decks < minDecks) {
                    decks = 0;
                    JOptionPane.showMessageDialog(null, validRangeMessage);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Number of decks must be an integer");
            }
        }
        return decks;
    }
    
    public int askForStartChips()
    {
        String message = "Enter the amount of chips players should start with";
        String validRangeMessage = "Chips should be greater than 0.";
        String input;
        int chips = 0;
        while (chips <= 0) {
            input = JOptionPane.showInputDialog(null, message);
            try {
                chips = Integer.parseInt(input);
                if (chips <= 0) {
                    JOptionPane.showMessageDialog(null, validRangeMessage);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Chips must be an Integer");
            }
        }
        return chips;
    }
    
    public static int askForNumOfPlayers()
    {
        int players = 0;
        String input;
        String validRangeMessage = "Number of players must be between 1 and 7 (inclusive)";
        while (players <= 0) {
            try {
                input = JOptionPane.showInputDialog("How many players are playing?");
                players = Integer.parseInt(input);
                if (players < 1 || players > 7) {
                    players = 0;
                    JOptionPane.showMessageDialog(null, validRangeMessage);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Number of players must be an integer");
            }
        }
        return players;
    }
    
    public void showShuffledMessage()
    {
        JOptionPane.showMessageDialog(frame, "Deck shuffled.");
    }
    
    public void showPlayerLosesMessage(String name, int bet, boolean busted)
    {
        String reason;
        if (busted)
            reason = "busted";
        else
            reason = "lost";
        String message = String.format("%s %s. They had bet %d chip(s).", 
                                        name, reason,       bet);
        JOptionPane.showMessageDialog(frame, message);
    }
    
    public void showPlayerOutOfChipsMessage(String name)
    {
        String message = name + " is out of chips and will not be able to play again.";
        JOptionPane.showMessageDialog(frame, message);
    }
    
    public void showNoPlayersMessage()
    {
        String message = "All players are out of chips and cannot play. ";
        JOptionPane.showMessageDialog(frame, message);
    }
    
    public void close()
    {
        frame.setVisible(false);
        frame.dispose();
        System.exit(0);
    }
    
    public boolean askKeepPlaying()
    {
        String[] options = {"Yes", "No"};
        int answer = JOptionPane.showOptionDialog(frame, 
                                                  "Do you want to keep playing?",
                                                  "Keep Playing Prompt",
                                                  JOptionPane.YES_NO_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE, 
                                                  null, 
                                                  options, 
                                                  options[0]);
        return (answer == 0);
    }
    
    public void showEndStats(int winsLosses, ArrayList<BlackJackPlayer> players)
    {
        JFrame statsFrame = new JFrame("Stats");
        JPanel statsPanel = new JPanel();
        JPanel statsLabelsPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        JPanel buttonPanel = new JPanel();
        JLabel label;
        String tableWinOrLose;
        
        if (winsLosses >= 0) {
            tableWinOrLose = "won";
        } else {
            tableWinOrLose = "lost";
            winsLosses *= -1;
        }
        
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsLabelsPanel.setLayout(new BoxLayout(statsLabelsPanel, BoxLayout.Y_AXIS));
        
        statsPanel.add(statsLabelsPanel);
        statsFrame.getContentPane().add(statsPanel);
        
        label = new JLabel(String.format("The table %s %d chip(s).",
                                                tableWinOrLose, 
                                                winsLosses));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsLabelsPanel.add(label);
        
        for (BlackJackPlayer player : players) {
            label = new JLabel(String.format("%s ended with %d chip(s).",
                                             player.getName(), 
                                             player.getChips()));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            statsLabelsPanel.add(label);
        }
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statsFrame.setVisible(false);
                statsFrame.dispose();
                close();
            }
        });
        
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonPanel.add(exitButton);
        statsPanel.add(buttonPanel);
        
        statsFrame.pack();
        statsFrame.setLocationRelativeTo(frame);
        
        frame.setVisible(false);
        statsFrame.setVisible(true);
    }
}
