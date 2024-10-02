import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// Task class representing a single task
class Task {
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private String priority;
    private boolean isCompleted;

    public Task(String description, LocalTime startTime, LocalTime endTime, String priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.isCompleted = false;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d - %02d:%02d: %s [%s]", startTime.getHour(), startTime.getMinute(),
                endTime.getHour(), endTime.getMinute(), description, priority);
    }
}

// Factory class to create tasks
class TaskFactory {
    public static Task createTask(String description, String startTimeStr, String endTimeStr, String priority) {
        LocalTime startTime;
        LocalTime endTime;
        try {
            startTime = LocalTime.parse(startTimeStr);
            endTime = LocalTime.parse(endTimeStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Please use HH:mm.");
        }

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time.");
        }

        return new Task(description, startTime, endTime, priority);
    }
}

// Singleton class to manage the schedule
class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;
    private static final Logger logger = Logger.getLogger(ScheduleManager.class.getName());

    private ScheduleManager() {
        tasks = new ArrayList<>();
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    // Observer method to check for conflicts
    private void notifyConflict(Task newTask) {
        for (Task task : tasks) {
            if (newTask.getStartTime().isBefore(task.getEndTime()) && newTask.getEndTime().isAfter(task.getStartTime())) {
                System.out.println("Error: Task conflicts with existing task \"" + task.getDescription() + "\".");
                return;
            }
        }
    }

    public void addTask(Task task) {
        notifyConflict(task);
        tasks.add(task);
        logger.log(Level.INFO, "Task added successfully: " + task.getDescription());
    }

    public void removeTask(String description) {
        boolean found = tasks.removeIf(task -> task.getDescription().equalsIgnoreCase(description));
        if (found) {
            logger.log(Level.INFO, "Task removed successfully: " + description);
        } else {
            System.out.println("Error: Task not found.");
        }
    }

    public void markTaskAsCompleted(String description) {
        for (Task task : tasks) {
            if (task.getDescription().equalsIgnoreCase(description)) {
                task.markAsCompleted();
                System.out.println("Task marked as completed: " + task.getDescription());
                return;
            }
        }
        System.out.println("Error: Task not found.");
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void viewTasksByPriority(String priority) {
        tasks.stream().filter(task -> task.getPriority().equalsIgnoreCase(priority)).sorted(Comparator.comparing(Task::getStartTime))
                .forEach(System.out::println);
    }
}

// Main class for interactive console application
public class AstronautDailyScheduleOrganizer {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ScheduleManager manager = ScheduleManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoice();
            handleChoice(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Astronaut Daily Schedule Organizer ===");
        System.out.println("1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. View All Tasks");
        System.out.println("4. View Tasks by Priority");
        System.out.println("5. Mark Task as Completed");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 6.");
            return -1;
        }
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1:
                addTask();
                break;
            case 2:
                removeTask();
                break;
            case 3:
                viewTasks();
                break;
            case 4:
                viewTasksByPriority();
                break;
            case 5:
                markTaskAsCompleted();
                break;
            case 6:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addTask() {
        try {
            System.out.print("Enter task description: ");
            String description = scanner.nextLine();

            System.out.print("Enter start time (HH:mm): ");
            String startTime = scanner.nextLine();

            System.out.print("Enter end time (HH:mm): ");
            String endTime = scanner.nextLine();

            System.out.print("Enter priority (Low/Medium/High): ");
            String priority = scanner.nextLine();

            Task task = TaskFactory.createTask(description, startTime, endTime, priority);
            manager.addTask(task);
            System.out.println("Task added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeTask() {
        System.out.print("Enter task description to remove: ");
        String description = scanner.nextLine();
        manager.removeTask(description);
    }

    private static void viewTasks() {
        System.out.println("All Tasks:");
        manager.viewTasks();
    }

    private static void viewTasksByPriority() {
        System.out.print("Enter priority (Low/Medium/High): ");
        String priority = scanner.nextLine();
        System.out.println("Tasks with priority " + priority + ":");
        manager.viewTasksByPriority(priority);
    }

    private static void markTaskAsCompleted() {
        System.out.print("Enter task description to mark as completed: ");
        String description = scanner.nextLine();
        manager.markTaskAsCompleted(description);
    }
}