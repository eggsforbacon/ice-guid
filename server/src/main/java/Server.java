import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Server {
    public static void main(String[] args) {
        try(Communicator communicator = Util.initialize(args, "server.cfg")) {
            ObjectAdapter adapter = communicator.createObjectAdapter("PrinterAdapter");
            com.zeroc.Ice.Object iceObject = new PrinterI();
            adapter.add(iceObject, Util.stringToIdentity("Printer"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
