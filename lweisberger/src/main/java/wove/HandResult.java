package wove;

/** The result of a hand. */
public enum HandResult {
    WIN("Win"), LOSE("Loss"), BUST("Bust"), BLACKJACK("Blackjack"), PUSH("Push");

    private String descriptor;

    private HandResult(String descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public String toString() {
        return descriptor;
    }
}