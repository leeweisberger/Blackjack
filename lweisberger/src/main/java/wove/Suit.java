package wove;

/** The suit of a playing card. */
public enum Suit {
    HEARTS("\u2665"), SPADES("\u2660"), DIAMONDS("\u2666"), CLUBS("\u2663");

    private String symbol;

    private Suit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}