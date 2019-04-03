package ContactManagerPackage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ContactManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.println("Enter a number option:");
        String selection = scanner.nextLine();

        if(selection.startsWith("1")){
            viewContact();
        }
        else if(selection.startsWith("2")){
            createContact();
        } else if (selection.startsWith("3")) {
            searchContact();
        }else if(selection.startsWith("4")){
            deleteContact();
        }
    }

    public static void deleteContact(){
        Scanner scanner = new Scanner(System.in);

//        String removeContact = scanner.nextLine();
//        System.out.println(removeContact);
//
//        String directory = "./data";
//        String filename = "info.txt";
//
//        Path dataDirectory = Paths.get(directory);
//        Path dataFile = Paths.get(directory, filename);
        Files contactRemove = searchContact();
        System.out.println("Do you wish to delete this contact? Y/N");
        String removeContact = scanner.nextLine();
        if(removeContact.startsWith("Y")|| removeContact.startsWith("y")){
            Files.delete(contactRemove);
        }else {
            deleteContact();
        }

    }

    public static String searchContact(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name or phone number of desired contact");
        String search = scanner.nextLine();

        String directory = "./data";
        String filename = "info.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);
        try {
            List<String> contacts = Files.readAllLines(dataFile);
            for (String contact : contacts) {
                if(contact.contains(search)){
                    System.out.println(contact);
                }
            }
        }catch(IOException ioe){
            System.out.println("ITs there stupid");
            System.out.println(ioe);
        }
        return null;
    }

    public static void viewContact(){
        String directory = "./data";
        String filename = "info.txt";

        Path dataDirectory = Paths.get(directory);
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
        Contact contact = new Contact(addContact, addNumber);
        System.out.println(contact);


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
//            ArrayList<String> contact = new ArrayList<>();
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
