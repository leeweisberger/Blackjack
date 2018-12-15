package wove;

/** The player who represents the dealer. */
public class Dealer implements Person {
    private static final String DEALER_NAME = "Dealer";
    private Hand hand;

    public Dealer(Hand hand) {
        this.hand = hand;
    }

    /** Whether the dealer should hit. */
    public boolean doesHit() {
        // The dealer does not hit on soft 17.
        return hand.getValueIncludingAce() < 17;
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public String getName() {
        return DEALER_NAME;
    }
}