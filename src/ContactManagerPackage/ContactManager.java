package src.ContactManagerPackage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ContactManager {
    public static void main(String[] args) {

        String userPickAnotherOption;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. View contacts");
            System.out.println("2. Add a new contact");
            System.out.println("3. Search a contact by name or phone number");
            System.out.println("4. Delete an existing contact");
            System.out.println("5. Exit");
            System.out.println("Enter a number option:");
            String selection = scanner.nextLine();

            if (selection.startsWith("1")) {
                viewContacts();
            } else if (selection.startsWith("2")) {
                createContact();
            } else if (selection.startsWith("3")) {
                searchContact();
            } else if (selection.startsWith("4")) {
                deleteContact();
            }

            System.out.println("\nPick another option? yes | no");
            userPickAnotherOption = scanner.nextLine();

        }while(userPickAnotherOption.equalsIgnoreCase("yes"));
    }

    public static void deleteContact(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("These are all of your contacts: \n");
        viewContacts();
        System.out.println("Enter a name to delete the contact.");
        String nameToDelete = scanner.nextLine();


        String directory = "./data";
        String filename = "info.txt";
        Path dataFile = Paths.get(directory, filename);

        try{
            List<String> contacts = Files.readAllLines(dataFile);
//            List<String> updatedContacts = new ArrayList<>();
//            updatedContacts = contacts; // Makes an exact replica of our current contacts list
            for (String contact : contacts) {
                if(contact.contains(nameToDelete)){
                    System.out.println("Are you sure you want to delete this contact? yes | no");
                    String confirmDelete = scanner.nextLine();
                    if (confirmDelete.equalsIgnoreCase("yes")) {
                        contacts.remove(contact);
                        Files.write(
                                Paths.get("data", "info.txt"), contacts);
                        System.out.println(nameToDelete + " has been removed from your contacts.");
                        System.out.println("Keep going! You are one step closer to having no friends at all!");
                    } else {
                        System.out.println("No contacts were deleted. \n");
                    }
                }
            }
        } catch(IOException ioe) {
            System.out.println(ioe);
        } catch(ConcurrentModificationException ccme) {
            System.out.println();
        }

    }

    public static String searchContact(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name or phone number of desired contact");
        String search = scanner.nextLine();

        String directory = "data";
        String filename = "info.txt";
        Path dataFile = Paths.get(directory, filename);

        try {
            List<String> contacts = Files.readAllLines(dataFile);
            for (String contact : contacts) {
                if(contact.contains(search)){
                    System.out.println(contact);
                }
            }
        }catch(IOException ioe){
            System.out.println(ioe);
        }
        return null;
    }

    public static void viewContacts(){
        String directory = "./data";
        String filename = "info.txt";
        Path dataFile = Paths.get(directory, filename);
    try {
        List<String> contacts = Files.readAllLines(dataFile);
        for (String contact : contacts) {
            System.out.println(contact);
        }
    }catch(IOException ioe){
        System.out.println("ITs there stupid");
        System.out.println(ioe);
    }

    }

    public static void createContact() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the name of your contact?");
        String addContact = scanner.nextLine();
        System.out.println("What is the phone number of your new contact?");
        String addNumber = scanner.nextLine();
        System.out.println("Congrats! You finally found a friend. " + addContact +
                " has been added to your contact list.");


        String directory = "./data";
        String filename = "info.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);


        try {
            if (Files.notExists(dataDirectory)) {
                System.out.println("does not exist.  creating file");
                Files.createDirectories(dataDirectory); //creates directory if it dont exist
            }
            if (!Files.exists(dataFile)) {
                Files.createFile(dataFile); //create file if it don't exist
            }

            Files.write(
                    Paths.get("data", "info.txt"),
                    Arrays.asList("Name: " + addContact + " Phone #: " + addNumber),
                    StandardOpenOption.APPEND
            );


        } catch (IOException ioe) {
            System.out.println("Something went wrong :(");
            System.out.println(ioe);
        }

    }


}
