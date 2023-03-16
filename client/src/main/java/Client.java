import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import javax.management.InvalidAttributeValueException;
import java.io.*;
import java.util.UUID;

public class Client {
    public static void main(String[] args) {
        try(Communicator communicator = Util.initialize(args, "client.cfg")) {
            Demo.PrinterPrx twoway = Demo.PrinterPrx.checkedCast(
                    communicator.propertyToProxy("Service.Proxy")).ice_twoway().ice_secure(false);
            Demo.PrinterPrx printerPrx = twoway.ice_twoway();

            if (printerPrx == null) {
                throw new Error("Invalid proxy");
            }

            final int length = args.length;
            String packet = "-1,Invalid input from client";
            switch (length) {
                default:
                    System.out.println("You've entered more than two arguments. Please check your input.");
                    break;
                case 0:
                    System.out.println("You've entered no arguments. Please check your input.");
                    break;
                case 1:
                    packet = noFile(args[0]);
                    break;
                case 2:
                    packet = fromFile(args[0], args[1]);
                    break;
            }
            final int response = printerPrx.printString(packet);
            System.out.printf("Response from server: %d%n%n", response);
        }
    }

    public static String noFile(String argument) {
        try {
            final int number = Integer.parseInt(argument);
            if (number < 1) throw new InvalidAttributeValueException();

            String generatedUUID = UUID.randomUUID().toString();
            return (number + "," + generatedUUID);
        } catch (NumberFormatException nfe) {
            System.out.printf("Cannot parse parameter '%s' to type Integer.%nPlease provide an integer greater than or equal to 1.%n", argument);
        } catch (InvalidAttributeValueException iave) {
            System.out.printf("Value %s is not within range.%nPlease provide an integer value greater than or equal to 1%n", argument);
        }
        return "-1,Invalid input from client";
    }

    public static String fromFile(String argument1, String argument2) {
        try {
            final int number = Integer.parseInt(argument1);
            if (number < 1) throw new InvalidAttributeValueException();

            File providedFile = new File(argument2);
            if (providedFile.exists() && providedFile.canRead()) {
                BufferedReader br = new BufferedReader(new FileReader(providedFile));
                final String providedUUID = br.readLine();
                br.close();
                return (number + "," + providedUUID);
            } else System.out.printf("File '%s' does not exist or can't be read from. Please check your input and try again.", argument2);
        } catch (NumberFormatException nfe) {
            System.out.printf("Cannot parse parameter '%s' to type Integer.%nPlease provide an integer greater than or equal to 1.%n", argument1);
        } catch (InvalidAttributeValueException iave) {
            System.out.printf("Value %s is not within range.%nPlease provide an integer value greater than or equal to 1%n", argument1);
        } catch (FileNotFoundException fnfe) {
            System.out.printf("File '%s' is not found.%nPlease provide an existing value path.%n", argument2);
        } catch (IOException ioe) {
            System.out.println("An unknown I/O exception has occurred.\nStacktrace:\n");
            ioe.printStackTrace();
        }
        return "-1,Invalid input from client";
    }
}
