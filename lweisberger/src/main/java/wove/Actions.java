package wove;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Actions {

    /** Prompts the user to select an action and returns that action. */
    public static Action promptForAction(Hand hand) {
        List<Action> possibleActions = getPossibleActions(hand);
        String actionPrompt = possibleActions.stream().map(action -> action.getPrompt())
                .collect(Collectors.joining(", "));

        while (true) {
            System.out.println(actionPrompt);
            String input = System.console().readLine();
            Optional<Action> matchingAction = possibleActions.stream()
                    .filter(action -> action.getCommand().equals(input)).findFirst();
            if (matchingAction.isPresent()) {
                return matchingAction.get();
            } else {
                System.out.println("Select a valid action.");
            }
        }

    }

    private static List<Action> getPossibleActions(Hand hand) {
        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(Action.HIT);
        possibleActions.add(Action.STAND);
        if (hand.canDouble()) {
            possibleActions.add(Action.DOUBLE);
        }
        if (hand.canSplit()) {
            possibleActions.add(Action.SPLIT);
        }
        possibleActions.add(Action.CHEAT);
        return possibleActions;
    }
}

enum Action {
    HIT("(h)it", "h"), STAND("(s)tand", "s"), DOUBLE("(d)ouble", "d"), SPLIT("s(p)lit", "p"), CHEAT("(c)heat", "c");

    String prompt;
    String command;

    private Action(String prompt, String command) {
        this.prompt = prompt;
        this.command = command;
    }

    String getPrompt() {
        return prompt;
    }

    String getCommand() {
        return command;
    }
}