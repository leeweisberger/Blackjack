package wove;

/** The rank of a playing card. */
public enum Rank {
    TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9),
    TEN("10", 10), JACK("J", 10), QUEEN("Q", 10), KING("K", 10), ACE("A", 1);

    private String displayName;
    private int value;

    private Rank(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getValue() {
        return this.value;
    }
}