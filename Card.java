public class Card {
    private String suit;
    private String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public int getBlackJackValue() {
        switch (value) {
            case "Jack":
            case "Queen":
            case "King":
                return 10;
            case "Ace":
                return 11;  // Ace can alternatively be 1, which will be handled in Player.adjustForAce()
            default:
                return Integer.parseInt(value);
        }
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}