package wove;

/** Utility functions dealing with file input/output. */
public class InputUtil {
    public static int getIntInput() {
        while (true) {
            String input = System.console().readLine();
            try {
                int intInput = Integer.parseInt(input);
                if (intInput > 0) {
                    return intInput;
                } else  {
                    System.out.println("Please enter a number greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void printSeparator() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println();
    }

    /** Prints the object to the console after a short delay. */
    public static void printSlowly(Object obj) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println(obj);
    }
}
