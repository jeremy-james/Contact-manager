package ContactManagerPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ContactPhonebook {


    public static void showInterface(){
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
        }else if (selection.startsWith("5")){
            System.exit(0);
        }else{
            System.out.println();
            System.out.println("Invalid response. Please enter a correct response");
            System.out.println();
            showInterface();
        }

        pickAnotherOption();
//        System.out.println("\nPick another option? yes | no");
//        String userPickAnotherOption = scanner.nextLine();
//        if(userPickAnotherOption.startsWith("Y") || userPickAnotherOption.startsWith("y")){
//            showInterface();
//        }else if(userPickAnotherOption.startsWith("N") || userPickAnotherOption.startsWith("n")){
//                System.exit(0);
//        }else{
//            //pick another option
//        }
    }

    public static void pickAnotherOption(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPick another option? yes | no");
        String userPickAnotherOption = scanner.nextLine();
        if(userPickAnotherOption.startsWith("Y") || userPickAnotherOption.startsWith("y")){
            showInterface();
        }else if(userPickAnotherOption.startsWith("N") || userPickAnotherOption.startsWith("n")){
            System.exit(0);
        }else{
            pickAnotherOption();
        }
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
            Iterator<String> iter = contacts.iterator();

            while (iter.hasNext()) {
                String string = iter.next();

                if(string.contains(nameToDelete)) {
                    System.out.println("Are you sure you want to delete this contact? yes | no");
                    String confirmDelete = scanner.nextLine();

                    if (confirmDelete.equalsIgnoreCase("yes")) {
                        iter.remove();
                    }
                }
            }

            Files.write(
                    Paths.get("data", "info.txt"), contacts);
            System.out.println(nameToDelete + " has been removed from your contacts.");
            System.out.println("Keep going! You are one step closer to having no friends at all!");


        } catch(IOException ioe) {
            System.out.println(ioe);
        } catch(ConcurrentModificationException ccme) {
            System.out.println("still catching");
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
        String addNumber;
        String phoneNumber = "";

        do{
            System.out.println("What is the phone number of your new contact?");
            addNumber = scanner.nextLine();

            if (addNumber.length() == 10) {
                phoneNumber = addNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                System.out.println(phoneNumber);
                System.out.println("Congrats! You finally found a friend. " + addContact +
                        " has been added to your contact list.");
            } else {
                System.out.println("Please enter a 10 digit phone number to include the area code");
            }
        }while(addNumber.length() > 10 || addNumber.length() < 10);

        Contact newContact = new Contact(addContact, phoneNumber);



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
                    Arrays.asList("Name: " + newContact.getName() + "    Phone #: " + newContact.getPhoneNum()), //Mike helped me with this!! initialize into empty String
                    StandardOpenOption.APPEND
            );

        } catch (IOException ioe) {
            System.out.println("Something went wrong :(");
            System.out.println(ioe);
        }

    }
}
