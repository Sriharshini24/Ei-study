import java.util.Scanner;

// Product interface
interface Vehicle {
    void manufacture();
}

// Concrete Products
class Car implements Vehicle {
    @Override
    public void manufacture() {
        System.out.println("Manufacturing a Car.");
    }
}

class Bike implements Vehicle {
    @Override
    public void manufacture() {
        System.out.println("Manufacturing a Bike.");
    }
}

// Creator
abstract class VehicleFactory {
    public abstract Vehicle createVehicle();
}

// Concrete Creator
class CarFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}

class BikeFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Bike();
    }
}

// Client
public class VehicleManufacturingDemo {
    public static void main(String[] args) {
        VehicleFactory factory = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1 to manufacture a Car, or 2 to manufacture a Bike:");
        int choice = scanner.nextInt();

        if (choice == 1) {
            factory = new CarFactory();
        } else if (choice == 2) {
            factory = new BikeFactory();
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        Vehicle vehicle = factory.createVehicle();
        vehicle.manufacture();

        scanner.close();
    }
}
