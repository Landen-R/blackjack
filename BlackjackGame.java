import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

public class BlackjackGame {
    private JFrame frame;
    private JTextArea statusArea;
    private JButton hitButton, standButton, setBetButton, newGameButton;
    private JTextField betField;
    private BlackjackLogic gameLogic;

    public BlackjackGame() {
        gameLogic = new BlackjackLogic();
        initializeUI();
        updateStatus("Welcome to Blackjack! Please place your bet.");
    }

    private void initializeUI() {
        frame = new JFrame("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        statusArea = new JTextArea(10, 40);
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel betPanel = new JPanel();
        betField = new JTextField(5);
        betField.setText("100");  // Default bet amount
        setBetButton = new JButton("Set Bet");
        setBetButton.addActionListener(e -> {
            try {
                int newBet = Integer.parseInt(betField.getText());
                gameLogic.placeBetAndDeal(newBet);
                updateStatus(gameLogic.getStatus());
                hitButton.setEnabled(true);
                standButton.setEnabled(true);
                newGameButton.setEnabled(false); // Disable during the game
            } catch (NumberFormatException ex) {
                updateStatus("Invalid number format for bet.");
            }
        });

        betPanel.add(new JLabel("Bet:"));
        betPanel.add(betField);
        betPanel.add(setBetButton);
        frame.add(betPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        hitButton.addActionListener(e -> {
            gameLogic.hit();
            updateStatus(gameLogic.getStatus());
            if (gameLogic.isGameOver()) {
                finalizeGame();
            }
        });
        hitButton.setEnabled(false);

        standButton = new JButton("Stand");
        standButton.addActionListener(e -> {
            gameLogic.stand();
            updateStatus(gameLogic.getStatus());
            finalizeGame();
        });
        standButton.setEnabled(false);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            gameLogic.initializeGame();
            updateStatus("New game started. Place your bet!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            newGameButton.setEnabled(false);
            setBetButton.setEnabled(true);
        });
        newGameButton.setEnabled(false);  // Enabled only after a game ends

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void finalizeGame() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(true);
        setBetButton.setEnabled(false);
    }

    private void updateStatus(String message) {
        statusArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlackjackGame::new);
    }
}

