import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackjackLogic {
    private List<String> deck;
    private List<String> playerHand;
    private List<String> dealerHand;
    private int playerFunds;
    private int playerBet;
    private int totalBets;
    private int wins;
    private int losses;
    private boolean gameOver;

    public BlackjackLogic() {
        this.playerFunds = 1000; // Starting funds
        this.totalBets = 0;
        this.wins = 0;
        this.losses = 0;
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        this.deck = new ArrayList<>();
        shuffleAndRecreateDeck();
        this.gameOver = true;
    }

    private void shuffleAndRecreateDeck() {
        deck.clear();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (String rank : ranks) {
            for (int i = 0; i < 4; i++) { // Assuming four suits, without distinguishing them
                deck.add(rank);
            }
        }
        Collections.shuffle(deck);
    }

    public void initializeGame() {
        if (deck.size() < 10) { // Shuffle the deck if it's running low
            shuffleAndRecreateDeck();
        }
        playerHand.clear();
        dealerHand.clear();
        gameOver = false;
    }

    public void placeBetAndDeal(int bet) {
        if (bet > 0 && bet <= playerFunds) {
            playerBet = bet;
            playerFunds -= bet;
            totalBets += bet;
            dealCards();
        } else {
            throw new IllegalArgumentException("Bet must be within your available funds.");
        }
    }

    private void dealCards() {
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        gameOver = false;
    }

    public void hit() {
        if (!gameOver && !deck.isEmpty()) {
            playerHand.add(deck.remove(0));
            if (calculateHandValue(playerHand) > 21) {
                gameOver = true;
                losses++;
            }
        }
    }

    public void stand() {
        while (calculateHandValue(dealerHand) < 17) {
            dealerHand.add(deck.remove(0));
        }
        gameOver = true;
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);
        if (playerValue > 21 || (dealerValue <= 21 && dealerValue > playerValue)) {
            losses++;
        } else if (playerValue <= 21 && playerValue > dealerValue || dealerValue > 21) {
            wins++;
            playerFunds += playerBet * 2; // Pay 1:1 on the player's bet
        }
    }

    private int calculateHandValue(List<String> hand) {
        int value = 0;
        int aceCount = 0;
        for (String card : hand) {
            switch (card) {
                case "J":
                case "Q":
                case "K":
                    value += 10;
                    break;
                case "A":
                    aceCount++;
                    value += 11;
                    break;
                default:
                    value += Integer.parseInt(card);
                    break;
            }
        }
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }

    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Player's hand: ").append(playerHand)
                .append(" (Total: ").append(calculateHandValue(playerHand)).append(")\n")
                .append("Dealer's showing: ").append(dealerHand.isEmpty() ? "" : dealerHand.get(0))
                .append("\nCurrent Bet: ").append(playerBet)
                .append("\nAvailable Funds: ").append(playerFunds)
                .append("\nTotal Bets: ").append(totalBets)
                .append("\nWins: ").append(wins)
                .append("\nLosses: ").append(losses);
        return status.toString();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<String> getPlayerHand() {
        return new ArrayList<>();
    }
}
