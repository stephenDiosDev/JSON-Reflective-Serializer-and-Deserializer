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
        String input = scan.nextLine();
        while(!input.contains("send")) {
            if(input.contains("help")) {
                this.listCommands();
            }
            else if(input.contains("create object1")) {
                if(userObjects.add(this.createObject1(scan)))
                    System.out.println("Successfully created object1!");
                else
                    System.out.println("Unable to create object1!");
            }
            else if(input.contains("create object2")) {
                if(userObjects.add(this.createObject2(scan)))
                    System.out.println("Successfully created object2!");
                else
                    System.out.println("Unable to create object2!");
            }
            else if(input.contains("create object3")) {
                if(userObjects.add(this.createObject3(scan)))
                    System.out.println("Successfully created object3!");
                else
                    System.out.println("Unable to create object3!");
            }
            else if(input.contains("create object4")) {
                if(userObjects.add(this.createObject4(scan)))
                    System.out.println("Successfully created object4!");
                else
                    System.out.println("Unable to create object4!");
            }
            else if(input.contains("create object5")) {
                if(userObjects.add(this.createObject5(scan)))
                    System.out.println("Successfully created object5!");
                else
                    System.out.println("Unable to create object5!");
            }

            input = scan.nextLine();
        }

        scan.close();
        System.out.println("\nFinished creating objects...");
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

        System.out.print("\nPlease enter a boolean value (true or false): ");
        boolValue = scan.nextBoolean();

        return objectCreator.createAllPrimitive(intValue, doubleValue, boolValue);
    }

    private Object createObject2(Scanner scan) {
        AllPrimitive a = (AllPrimitive) this.createObject1(scan);
        scan.reset();
        AllPrimitive b = (AllPrimitive) this.createObject1(scan);
        scan.reset();
        ArrayPrimitives c = (ArrayPrimitives) this.createObject3(scan);
        scan.reset();

        return objectCreator.createComplexWithReferences(a, b, c);
    }

    private Object createObject3(Scanner scan) {
        int arr[];
        ArrayList<Integer> nums = new ArrayList<>();

        System.out.print("\nPlease enter integer values and press enter after one, write \"end\" without quotes to finish entering: ");

        String input = scan.nextLine();
        while(!input.contains("end")) {
            if(!input.equals(""))
                nums.add(Integer.parseInt(input));

            input = scan.nextLine();

        }

        //fill array
        arr = new int[nums.size()];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = nums.get(i);
        }

        return objectCreator.createArrayPrimitives(arr);
    }

    private Object createObject4(Scanner scan) {
        Object[] arr;
        ArrayList<Object> objs = new ArrayList<>();

        System.out.println("\nPlease enter \"create objectx\" commands with x subbed for a number. Write \"finish\" when you are done");

        String input = scan.nextLine();
        while(!input.contains("finish")) {
            if(!input.equals("")) {
                if(input.contains("create object1"))
                    objs.add(createObject1(scan));
                else if(input.contains("create object2"))
                    objs.add(createObject2(scan));
                else if(input.contains("create object3"))
                    objs.add(createObject3(scan));
                else if(input.contains("create object4"))
                    objs.add(createObject4(scan));
                else if(input.contains("create object5"))
                    objs.add(createObject5(scan));
                else
                    System.out.println("Invalid command. Enter \"create objectx\" commands, and \"finish\" when done");
            }

            input = scan.nextLine();
        }

        //populate object array
        arr = new Object[objs.size()];
        for(int i = 0; i < objs.size(); i++) {
            arr[i] = objs.get(i);
        }

        return objectCreator.createArrayReferences(arr);
    }

    private Object createObject5(Scanner scan) {
        AllPrimitive a;
        AllPrimitive b;
        AllPrimitive c;
        ArrayPrimitives arr;

        a = (AllPrimitive) createObject1(scan);
        b = (AllPrimitive) createObject1(scan);
        c = (AllPrimitive) createObject1(scan);

        arr = (ArrayPrimitives) createObject3(scan);

        return objectCreator.createInstanceJavaCollection(a, b, c, arr);
    }

    public String toString() {
        String result = "";
        for(Object o : userObjects) {
            result += o.toString();
            result += "\n";
        }

        return result;
    }
}
