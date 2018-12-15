package wove;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Represents a single blackjack hand. */
public class Hand {
    public static final int BLACKJACK = 21;
    private static final int ACE_VALUE = 11;

    private List<Card> cards = new ArrayList<>();
    private int value = 0;
    private boolean hasAce = false;
    private int bet = 0;

    public Hand(Card[] cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }

    /**
     * Hits this hand from the supplied deck. Returns false if the hit results in a
     * bust.
     */
    public boolean hit(Deck deck) {
        Card card = deck.dealCard();
        InputUtil.printSlowly(String.format("hit: %s", card));
        addCard(card);
        if (isBust()) {
            System.out.println(String.format("Bust with %s", getValue()));
            return false;
        }
        return true;
    }

    /** Splits this hand into two hands. */
    public Hand[] split(Deck deck) {
        Hand hand1 = new Hand(new Card[] { getCards().get(0) });
        hand1.addBet(getBet());
        Hand hand2 = new Hand(new Card[] { getCards().get(1) });
        hand2.addBet(getBet());
        InputUtil.printSlowly("Hitting first split...");
        hand1.hit(deck);
        InputUtil.printSlowly("Hitting second split...");
        hand2.hit(deck);
        return new Hand[] { hand1, hand2 };
    }

    /** Adds a bet to this hand. */
    public void addBet(int bet) {
        this.bet += bet;
    }

    /** Returns the total bet made on this hand. */
    public int getBet() {
        return bet;
    }

    /** Return whether this hand beats the given hand. */
    public HandResult beats(Hand hand) {
        if (this.isBlackjack()) {
            return HandResult.BLACKJACK;
        } else if (hand.isBust() && this.isBust()) {
            return HandResult.PUSH;
        } else if (hand.isBust()) {
            return HandResult.WIN;
        } else if (this.isBust()) {
            return HandResult.BUST;
        } else if (hand.getValueIncludingAce() == this.getValueIncludingAce()) {
            return HandResult.PUSH;
        } else if (hand.getValueIncludingAce() > this.getValueIncludingAce()) {
            return HandResult.LOSE;
        } else {
            return HandResult.WIN;
        }

    }

    /** Whether the hand represents a bust. */
    public boolean isBust() {
        return value > BLACKJACK;
    }

    /** Whether the hand represents a blackjack. */
    public boolean isBlackjack() {
        return getValueIncludingAce() == BLACKJACK && this.cards.size() == 2;
    }

    /** Gets the value of the hand, with all aces treated as 1. */
    public int getValue() {
        return value;
    }

    /**
     * Gets the maximum value of the hand, with aces treated as 11 if applicable.
     */
    public int getValueIncludingAce() {
        return shouldConsiderAce() ? value + ACE_VALUE - Rank.ACE.getValue() : value;
    }

    /** Whether this hand can be split. */
    public boolean canSplit() {
        return cards.size() == 2 && cards.get(0).getRank().equals(cards.get(1).getRank());
    }

    /** Whether this hand can be doubled down. */
    public boolean canDouble() {
        return cards.size() == 2;
    }

    public List<Card> getCards() {
        return cards;
    }

    private boolean shouldConsiderAce() {
        return hasAce && value <= BLACKJACK - ACE_VALUE + Rank.ACE.getValue();
    }

    /** Adds a card to the hand. */
    private void addCard(Card card) {
        hasAce = hasAce || card.getRank().equals(Rank.ACE);
        value += card.getRank().getValue();
        this.cards.add(card);
    }

    @Override
    public String toString() {
        String cardsString = cards.stream().map(card -> card.toString()).collect(Collectors.joining(", "));
        String valueString = String.valueOf(value);
        if (shouldConsiderAce()) {
            valueString = String.format("%s or %s", valueString, String.valueOf(getValueIncludingAce()));
        }
        return String.format("%s - %s", cardsString, valueString);

    }
}