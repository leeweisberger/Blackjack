package wove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of cards. Each deck can contain multiple "standard" (52
 * card) decks.
 */
public class Deck {
    private List<Card> cards;
    private int numStandardDecks;

    public Deck(int numStandardDecks) {
        this.numStandardDecks = numStandardDecks;
        init();
    }

    /** Returns whether the deck has no cards left in it. */
    public boolean isEmpty() {
        return cards.size() == 0;
    }

    /** Deals the provided number of cards */
    public Card[] dealCards(int numCards) {
        Card[] cardsToDeal = new Card[numCards];
        for (int i = 0; i < numCards; i++) {
            if (isEmpty()) {
                System.out.println("Reshuffling...");
                init();
            }
            cardsToDeal[i] = cards.remove(cards.size() - 1);
        }
        return cardsToDeal;
    }

    /** Deals a single card */
    public Card dealCard() {
        return dealCards(1)[0];
    }

    /** Peaks at the top card in the deck without dealing it. */
    public Card peak() {
        Card top = dealCard();
        cards.add(top);
        return top;
    }

    private void init() {
        cards = new ArrayList<>();
        for (int i = 0; i < numStandardDecks; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }
}