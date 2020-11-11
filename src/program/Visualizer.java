package program;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the menu and display of objects.
 */
public class Visualizer {
    private ObjectCreator objectCreator;
    public ArrayList<Object> userObjects = new ArrayList<>();

    public Visualizer() {
        objectCreator = new ObjectCreator();
    }
    /**
     * Run the user menu, collecting and processing input and returning the arraylist
     * containing the user's selections to Sender.java
     */
    public ArrayList<Object> userSelectionMenu() {
        Scanner scan = new Scanner(System.in);

        this.printIntro();
        while(!scan.nextLine().equalsIgnoreCase("send")) {
            if(scan.nextLine().equalsIgnoreCase("help")) {
                this.listCommands();
            }
            else if(scan.nextLine().equalsIgnoreCase("create object1")) {
                if(userObjects.add(this.createObject1(scan)))
                    System.out.println("Successfully created object1!");
                else
                    System.out.println("Unable to create object1!");
            }
            else if(scan.nextLine().equalsIgnoreCase("create object2")) {

            }
            else if(scan.nextLine().equalsIgnoreCase("create object3")) {

            }
            else if(scan.nextLine().equalsIgnoreCase("create object4")) {

            }
            else if(scan.nextLine().equalsIgnoreCase("create object5")) {

            }
        }

        return userObjects;
    }

    private void printIntro() {
        System.out.println("============= Welcome to the Reflective Object Serializer! =============");
        this.listCommands();
        System.out.println("Please keep it to one command per line, and don't leave extra spaces!");
        System.out.println("Object types correspond to the object on page 3 of the assignment description");
        System.out.println("==========================================================");
    }

    private void listCommands() {
        System.out.println("help --> lists all commands (this list)");
        System.out.println("send --> takes the current list of created objects and sends it to the receiver");
        System.out.println("create object1 --> create an object of type object1");
        System.out.println("create object2 --> create an object of type object2");
        System.out.println("create object3 --> create an object of type object3");
        System.out.println("create object4 --> create an object of type object4");
        System.out.println("create object5 --> create an object of type object5");
        System.out.println();

    }

    private Object createObject1(Scanner scan) {
        int intValue;
        double doubleValue;
        boolean boolValue;

        System.out.print("\nPlease enter an integer value: ");
        intValue = scan.nextInt();

        System.out.print("\nPlease enter a double value: ");
        doubleValue = scan.nextDouble();

        System.out.println("\nPlease enter a boolean value (true or false): ");
        boolValue = scan.nextBoolean();

        return objectCreator.createAllPrimitive(intValue, doubleValue, boolValue);
    }
}
