package wove;

import java.util.List;

/** A class that deals with collecting and doling out monetary transactions. */
public class Bank {

    public static int pay(HandResult handResult, int bet, Player player) {
        int payout = 0;
        if (handResult.equals(HandResult.BLACKJACK)) {
            payout = bet + (int) (bet * 1.5);
        } else if (handResult.equals(HandResult.WIN)) {
            payout = bet * 2;
        } else if (handResult.equals(HandResult.PUSH)) {
            payout = bet;
        }
        player.pay(payout);
        return payout;
    }

    public static void collectBets(List<Player> players) {
        for (Player player : players) {
            System.out.println(String.format("Enter your bet %s:", player));
            collectBet(player);
        }
    }

    private static void collectBet(Player player) {
        while (true) {
            int bet = InputUtil.getIntInput();
            if (player.makeBet(bet)) {
                break;
            }
        }
    }
}