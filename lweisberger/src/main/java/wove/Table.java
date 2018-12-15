package wove;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * Keeps track of the members of the table and presides over the game. The
 * "Table" serves the role of the human dealer.
 */
public class Table {
    private static final int INITIAL_HAND_SIZE = 2;

    private List<Player> players;
    private Deck deck;
    private Dealer dealer;

    public Table(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    /** Deals out cards from the table's deck to the players at the table. */
    public void deal() {
        System.out.println("Dealing:");
        System.out.println();
        for (Player player : players) {
            Hand hand = new Hand(deck.dealCards(INITIAL_HAND_SIZE));
            player.addHand(hand);
            InputUtil.printSlowly(player);
            InputUtil.printSlowly(hand);
            if (hand.isBlackjack()) {
                System.out.println("Blackjack!!!");
            }
            System.out.println();
        }

        dealer = new Dealer(new Hand(deck.dealCards(INITIAL_HAND_SIZE)));
        InputUtil.printSlowly(dealer.getName());
        InputUtil.printSlowly(dealer.getHand().getCards().get(0));
    }

    /**
     * Plays a round of blackjack, allowing each player to play their hands followed
     * by the dealer.
     */
    public void playRound() {
        for (Player player : players) {
            if (player.getHands().get(0).isBlackjack()) {
                // A player who has already gotten blackjack is out of this round.
                continue;
            }
            playRound(player);
        }

        if (players.stream()
                .allMatch(player -> player.getHands().stream().allMatch(hand -> hand.isBust() || hand.isBlackjack()))) {
            System.out.println(String.format("%s doesn't draw", dealer.getName()));
            return;
        }

        System.out.println(String.format("%s is up", dealer.getName()));
        System.out.println();
        System.out.println(dealer.getHand());
        while (dealer.doesHit()) {
            dealer.getHand().hit(deck);
        }
    }

    /** Removes bankrupt players from the table. */
    public void removeBankruptPlayers() {
        players = players.stream().filter(player -> player.getMoney() > 0).collect(Collectors.toList());
    }

    /** Resets the table after a round. */
    public void reset() {
        players.stream().forEach(player -> player.clearHands());
    }

    private void playRound(Player player) {
        System.out.println(String.format("%s is up!", player));
        ListIterator<Hand> handIterator = player.getHands().listIterator();
        while (handIterator.hasNext()) {
            Hand hand = handIterator.next();
            while (true) {
                if (hand.getValueIncludingAce() == Hand.BLACKJACK) {
                    break;
                }
                System.out.println();
                System.out.println(hand);
                Action action = Actions.promptForAction(hand);
                if (action.equals(Action.STAND)) {
                    break;
                } else if (action.equals(Action.HIT)) {
                    if (!hand.hit(deck)) {
                        break;
                    }
                } else if (action.equals(Action.SPLIT)) {
                    if (player.makeBet(hand.getBet())) {
                        handIterator.remove();
                        Hand[] splitHands = hand.split(deck);
                        handIterator.add(splitHands[0]);
                        handIterator.add(splitHands[1]);
                        // Go back to the first split.
                        handIterator.previous();
                        handIterator.previous();
                        hand = handIterator.next();
                    }
                } else if (action.equals(Action.DOUBLE)) {
                    if (player.makeBet(hand.getBet(), hand)) {
                        if (hand.hit(deck)) {
                            System.out.println(hand);
                        }
                        break;
                    }
                } else if (action.equals(Action.CHEAT)) {
                    System.out.println(deck.peak());
                }
            }
        }
        InputUtil.printSeparator();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public Dealer getDealer() {
        return dealer;
    }
}