package wove;

import java.util.ArrayList;
import java.util.List;

/** A game of blackjack. Contains the main game loop. */
public class Game {
    private static final int MAX_PLAYERS = 10;
    private static final String[] PLAYER_NAMES = { "Billy the Kid", "Card Slingin Carlos", "Blackjack Bertha",
            "Ted Twenty-One", "Annie Gimme-Aces", "Winning Willy", "Casino Cass", "Vegas Victor", "No-Name Nancy",
            "Last-Player Louis" };

    public static void main(String[] args) {
        play();
    }

    private static void play() {
        Table table = newGame();

        while (true) {
            InputUtil.printSeparator();

            Bank.collectBets(table.getPlayers());
            InputUtil.printSeparator();

            table.deal();
            InputUtil.printSeparator();

            table.playRound();
            InputUtil.printSeparator();

            printResults(table);
            
            table.removeBankruptPlayers();
            table.reset();

            if (table.getPlayers().isEmpty()) {
                System.out.println("You do know that the house always wins... thanks for playing!!!");
                return;
            }
        }

    }

    private static int getNumPlayers() {
        while (true) {
            int num = InputUtil.getIntInput();
            if (num > MAX_PLAYERS) {
                System.out.println(String.format("A maximum of %s players can sit at the table.", MAX_PLAYERS));
            } else {
                return num;
            }
        }
    }

    private static String getPlayerName(int i) {
        System.out.println(String.format("Player %s, what should I call you? (%s)", i + 1, PLAYER_NAMES[i]));
        String name = System.console().readLine();
        return name.isEmpty() ? PLAYER_NAMES[i] : name;
    }

    private static Table newGame() {
        System.out.println("Welcome to Lee's Blackjack! How many players do we have here?");
        int numPlayers = getNumPlayers();

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(getPlayerName(i)));
        }
        Deck deck = new Deck(5);

        System.out.println("Let's get to it! We'll all start with $1000 and (hopefully) move up from there!");
        return new Table(players, deck);
    }

    private static void printResults(Table table) {
        System.out.println("Results:");
        System.out.println();
        InputUtil.printSlowly(table.getDealer().getName());
        InputUtil.printSlowly(table.getDealer().getHand());

        for (Player player : table.getPlayers()) {
            System.out.println();
            System.out.println(player);
            for (Hand hand : player.getHands()) {
                HandResult result = hand.beats(table.getDealer().getHand());
                Bank.pay(result, hand.getBet(), player);
                InputUtil.printSlowly(String.format("%s - %s", hand, result));
            }
            InputUtil.printSlowly(String.format("Money remaining: $%s", player.getMoney()));
            if (player.getMoney() == 0) {
                InputUtil.printSlowly("Sorry, you are out of money!!!");
            }
        }
    }
}