public class Dealer extends Player {
    public Dealer() {
        super(0); // Dealer might not need a balance
    }

    public boolean shouldDraw() {
        return getHandValue() < 17;
    }
    public void clearHand() {
        hand.clear();
    }
}
