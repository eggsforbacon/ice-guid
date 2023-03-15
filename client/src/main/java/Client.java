import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.net.Inet4Address;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        try(Communicator communicator = Util.initialize(args, "client.cfg")) {
            ObjectPrx basePrx = communicator.propertyToProxy("Service.Proxy");
            Demo.PrinterPrx printerPrx = Demo.PrinterPrx.checkedCast(basePrx);

            if (printerPrx == null) {
                throw new Error("Invalid proxy");
            } try {
                String hostname = Inet4Address.getLocalHost().getHostName();
                System.out.printf("hostname: %s%n", hostname);
                printerPrx.printString("%s says: Hello world!%n".formatted(hostname));
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }
}
