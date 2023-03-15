import com.zeroc.Ice.Current;

import java.util.regex.Pattern;

public class PrinterI implements Demo.Printer {
    public int printString(String message, Current current) {

        String[] digest = message.split(",");
        int number = Integer.parseInt(digest[0]);
        String promptedUUID = digest[1];

        if (isValidUUID(promptedUUID)) {
            System.out.println(Colors.ANSI_GREEN.value() + Colors.ANSI_BOLD.value() + promptedUUID + Colors.ANSI_UNBOLD.value() + "VALID REQUEST" + Colors.ANSI_RESET);
            System.out.println("-------------------------------------------\nClosest smallest prime greater than" + Colors.ANSI_YELLOW.value() + number + ":" + Colors.ANSI_RESET);
            final int closestPrime = getClosestPrime(number);
            System.out.println(Colors.ANSI_BOLD.value() + closestPrime + Colors.ANSI_RESET.value());
            return closestPrime;
        } else {
            System.out.println(Colors.ANSI_RED.value() + Colors.ANSI_BOLD.value() + promptedUUID + Colors.ANSI_UNBOLD.value() + Colors.ANSI_BRIGHT_RED.value() + "INVALID REQUEST" + Colors.ANSI_RESET);
            return 1;
        }
    }

    private int getClosestPrime(int number) {
        int closestPrime = number + 1;
        while (!isPrime(closestPrime)) closestPrime++;
        return closestPrime;
    }

    private boolean isPrime(int number) {
        // Base case
        if (number <= 1) return false;

        // Only check until square root since further is redundant
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) return false;
        }

        return true;
    }

    public boolean isValidUUID(String uuid) {
        final Pattern uuidPattern = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        return uuidPattern.matcher(uuid).matches();
    }
}
