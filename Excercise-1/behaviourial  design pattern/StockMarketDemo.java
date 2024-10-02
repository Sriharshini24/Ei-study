import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Subject
class StockMarket {
    private List<StockObserver> observers = new ArrayList<>();
    private double price;

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    public void setPrice(double price) {
        this.price = price;
        notifyAllObservers();
    }

    public void notifyAllObservers() {
        for (StockObserver observer : observers) {
            observer.update(price);
        }
    }
}

// Observer interface
interface StockObserver {
    void update(double price);
}

// Concrete Observer
class Investor implements StockObserver {
    private String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(double price) {
        System.out.println("Notifying " + name + ": Stock price updated to $" + price);
    }
}

// Client
public class StockMarketDemo {
    public static void main(String[] args) {
        StockMarket market = new StockMarket();
        Investor investor1 = new Investor("Alice");
        Investor investor2 = new Investor("Bob");

        market.addObserver(investor1);
        market.addObserver(investor2);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new stock price:");
        double price = scanner.nextDouble();

        market.setPrice(price);
        scanner.close();
    }
}
