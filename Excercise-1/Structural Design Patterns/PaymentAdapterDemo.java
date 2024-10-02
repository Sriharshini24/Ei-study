import java.util.Scanner;

// Target interface
interface PaymentGateway {
    void processPayment(double amount);
}

// Adaptee
class LegacyPaymentSystem {
    public void makePayment(double amount) {
        System.out.println("Payment of $" + amount + " made using legacy system.");
    }
}

// Adapter
class PaymentAdapter implements PaymentGateway {
    private LegacyPaymentSystem legacyPaymentSystem;

    public PaymentAdapter(LegacyPaymentSystem legacyPaymentSystem) {
        this.legacyPaymentSystem = legacyPaymentSystem;
    }

    @Override
    public void processPayment(double amount) {
        legacyPaymentSystem.makePayment(amount);
    }
}

// Client
public class PaymentAdapterDemo {
    public static void main(String[] args) {
        LegacyPaymentSystem legacySystem = new LegacyPaymentSystem();
        PaymentGateway gateway = new PaymentAdapter(legacySystem);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the amount to process payment:");
        double amount = scanner.nextDouble();

        gateway.processPayment(amount);
        scanner.close();
    }
}
