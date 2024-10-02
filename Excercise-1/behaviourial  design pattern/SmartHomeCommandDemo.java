import java.util.Scanner;

// Command interface
interface Command {
    void execute();
    void undo();
}

// Concrete Commands
class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}

// Receiver
class Light {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}

// Invoker
class RemoteControl {
    Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }

    public void pressUndo() {
        command.undo();
    }
}

// Client
public class SmartHomeCommandDemo {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();

        LightOnCommand lightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand lightOff = new LightOffCommand(livingRoomLight);

        RemoteControl remote = new RemoteControl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1 to turn ON the light, 2 to turn OFF the light, or 3 to UNDO the last action:");
        int choice = scanner.nextInt();

        if (choice == 1) {
            remote.setCommand(lightOn);
            remote.pressButton();
        } else if (choice == 2) {
            remote.setCommand(lightOff);
            remote.pressButton();
        } else if (choice == 3) {
            remote.pressUndo();
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
