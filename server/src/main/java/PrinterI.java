import com.zeroc.Ice.Current;

public class PrinterI implements Demo.Printer {
    public void printString(String message, Current current) {
        System.out.println(message);
    }
}
