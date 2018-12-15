package wove;

import java.util.ArrayList;
import java.util.List;

/** A human player. */
public class Player implements Person {
    private static final int STARTING_MONEY = 1000;

    private String name;
    private int money;
    private List<Hand> hands = new ArrayList<>();
    private int initialBet;

    public Player(String name) {
        this.name = name;
        this.money = STARTING_MONEY;
    }

    /**
     * Makes a bet of the given amount. The best is not tied to a specific hand.
     * Returns whether the bet is made.
     */
    public boolean makeBet(int bet) {
        if (!canMakeBet(bet)) {
            String error = money == 0 ? "You have no money left!" : String.format("You only have $%s left!", money);
            System.out.println(error);
            return false;
        }
        this.money -= bet;
        if (this.hands.isEmpty()) {
            this.initialBet = bet;
        }
        return true;
    }

    /**
     * Makes a bet of the given amount on the given hand. Returns whether the bet is
     * made.
     */
    public boolean makeBet(int bet, Hand hand) {
        boolean betMade = makeBet(bet);
        if (betMade) {
            hand.addBet(bet);
        }
        return betMade;
    }

    /** Pays the player for winning the hand. */
    public void pay(int payment) {
        this.money += payment;
    }

    /** Add a hand to the player's list of hands. */
    public void addHand(Hand hand) {
        if (hands.isEmpty()) {
            // This is the first hand. Put the initial bet on it.
            hand.addBet(this.initialBet);
        }
        hands.add(hand);
    }

    /** Clears the current hands. */
    public void clearHands() {
        this.hands.clear();
    }

    public int getMoney() {
        return money;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private boolean canMakeBet(int bet) {
        return money >= bet;
    }
}