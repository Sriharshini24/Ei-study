import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Component
interface FileSystemComponent {
    void showDetails();
}

// Leaf
class File implements FileSystemComponent {
    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void showDetails() {
        System.out.println("File: " + name);
    }
}

// Composite
class Folder implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }

    @Override
    public void showDetails() {
        System.out.println("Folder: " + name);
        for (FileSystemComponent component : components) {
            component.showDetails();
        }
    }
}

// Client
public class FileSystemCompositeDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a root folder
        Folder rootFolder = new Folder("Root");

        while (true) {
            System.out.println("Enter 1 to add a file, 2 to add a folder, 3 to display the structure, 0 to exit:");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                System.out.println("Enter the file name:");
                String fileName = scanner.nextLine();
                FileSystemComponent file = new File(fileName);
                rootFolder.addComponent(file);
            } else if (choice == 2) {
                System.out.println("Enter the folder name:");
                String folderName = scanner.nextLine();
                Folder folder = new Folder(folderName);
                rootFolder.addComponent(folder);

                // Optionally, you can also ask to add files inside the newly created folder
                System.out.println("Do you want to add files to this folder? (yes/no):");
                String addFiles = scanner.nextLine();

                if (addFiles.equalsIgnoreCase("yes")) {
                    System.out.println("How many files do you want to add to " + folderName + "?");
                    int numFiles = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    for (int i = 0; i < numFiles; i++) {
                        System.out.println("Enter the name of file " + (i + 1) + ":");
                        String newFileName = scanner.nextLine();
                        folder.addComponent(new File(newFileName));
                    }
                }
            } else if (choice == 3) {
                rootFolder.showDetails();
            } else if (choice == 0) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
