import java.util.ArrayList;

public class Player {
    protected ArrayList<Card> hand;
    protected int balance;

    public Player(int balance) {
        this.hand = new ArrayList<>();
        this.balance = balance;
    }

    public void addCard(Card card) {
        hand.add(card);
        adjustForAce();
    }

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getBlackJackValue();
        }
        return value;
    }

    public void printHand() {
        for (Card card : hand) {
            System.out.println(card);
        }
        System.out.println("Total hand value: " + getHandValue());
    }

    private void adjustForAce() {
        int handValue = getHandValue();
        for (Card card : hand) {
            if (card.getValue().equals("Ace") && handValue > 21) {
                handValue -= 10;  // Change Ace value from 11 to 1
                if (handValue <= 21) break;
            }
        }
    }

    public void clearHand() {
        hand.clear();
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }
}
