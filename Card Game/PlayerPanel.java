import javax.swing.*;
import java.awt.*;
/**
 * Write a description of class PlayerPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerPanel extends JPanel
{
    // instance variables - replace the example below with your own
    private BlackJackPlayer player;
    private JPanel cardsPanel;
    private JLabel valueLabel;
    private JLabel nameLabel;
    

    /**
     * Constructor for objects of class PlayerPanel
     */
    public PlayerPanel(BlackJackPlayer player)
    {       
        GridBagConstraints c;
        
        this.player = player;
        
        setLayout(new GridBagLayout());
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 0;
        
        nameLabel = new JLabel(player.getName());
        
        nameLabel.setFont(new Font(nameLabel.getName(), Font.BOLD, 15));
        
        add(nameLabel, c);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        
        updateCards();
        
        add(cardsPanel, c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        
        valueLabel = new JLabel(Integer.toString(player.getValue()));
        
        valueLabel.setFont(new Font(valueLabel.getName(), Font.PLAIN, 50));
        
        add(valueLabel, c);
    }
    
    private void updateCards()
    {
        for (Card card : player.getCards()) {
            JLabel cardLabel = new JLabel(card.toString());
            if (card.canDemoteRank()) {
                cardLabel.setForeground(new Color(0,195,0));
            }
            cardsPanel.add(cardLabel);
        }
    }
    
    public void update()
    {
        nameLabel.setText(player.getName());
        
        cardsPanel.removeAll();
        updateCards();
        
        valueLabel.setText(Integer.toString(player.getValue()));
    }
    
    public void changePlayer(BlackJackPlayer player)
    {
        this.player = player;
        update();
    }
}
