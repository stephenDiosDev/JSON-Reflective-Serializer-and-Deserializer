package program;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    public static void printDeserializerOutput(ArrayList<Object> objectList) {
        ArrayList<Integer> hashValues = new ArrayList<>();
        for(Object obj : objectList) {
            inspect(obj, hashValues);
            System.out.println();
        }
    }

    /*
    All code below this comment was taken straight from my assignment 2 and modified for this assignment
     */

    private static void inspect(Object obj, ArrayList<Integer> hashValues) {
        Class c = obj.getClass();
        if(!hashValues.contains(obj.hashCode())) {
            hashValues.add(obj.hashCode());
            inspectClass(c, obj, 0, hashValues);
        }
    }

    //the method that inspects the CLASS
    private static void inspectClass(Class c, Object obj, int depth, ArrayList<Integer> hashValues) {

        if(!c.getName().equals("java.lang.reflect.Field")) {     //we don't want to inspect Field class
            hashValues.add(c.hashCode());

            String tabs = "";
            for(int i = 0; i < depth; i++) {
                tabs += "\t";
            }
            System.out.println(tabs + "CLASS");
            System.out.println(tabs + "Class: " + c.getName());


            System.out.print(tabs + "SUPERCLASS -> ");
            if(c.getSuperclass() != null) { //superclass found
                System.out.println(c.getSuperclass().getName());
                int newDepth = depth + 1;
                inspectClass(c.getSuperclass(), obj, newDepth, hashValues);
            }
            else {
                System.out.println("NONE");
            }

            inspectInterface(c, obj, depth);
            //Finds any interfaces and calls inspectInterface on it according to recursive and depth

            inspectFields(c, obj, depth);
        }
    }

    private static void inspectInterface(Class c, Object obj, int depth) {
        String tabs = "";
        for(int i = 0; i < depth; i++) {
            tabs += "\t";
        }
        System.out.println(tabs + "INTERFACES ( " + c.getName() + " )");

        if(c.getInterfaces().length > 0) {  //interfaces exist
            System.out.println(tabs + "Interfaces ->");
            Class[] interfaceList = c.getInterfaces();
            for(int i = 0; i < interfaceList.length; i++) {
                int newDepth = depth + 1;
                System.out.println(tabs + interfaceList[i].getName());
            }
        }
        else {
            System.out.println(tabs + "Interfaces -> NONE");
        }
    }

    private static void inspectFields(Class c, Object obj, int depth) {
        String tabs = "";
        for(int i = 0; i < depth; i++) {
            tabs += "\t";
        }

        System.out.println(tabs + "FIELDS ( " + c.getName() + " )");
        Field[] fields = c.getDeclaredFields();
        System.out.print(tabs + "Fields -> ");

        if(fields.length > 0) { //fields found
            System.out.println();
            for(Field field : fields) {
                field.setAccessible(true);

                System.out.println(tabs + " FIELD");
                System.out.println(tabs + "  Name: " + field.getName());


                System.out.println(tabs + "  Type: " + field.getType());
                System.out.println(tabs + "  Modifiers: " + Modifier.toString(field.getModifiers()));
                if(field.getType().isPrimitive()) {
                    try {
                        System.out.print(tabs + "  Value: ");
                        System.out.println(field.get(obj));
                    } catch (IllegalAccessException e) {
                        System.out.println("ILLEGAL ACCESS EXCEPTION: UNABLE TO OBTAIN FIELD VALUE");
                    }
                }
                else {  //reference/object/array type
                    if(field.getType().isArray()) {
                        System.out.println(tabs + "  Component Type: " + field.getType());
                        try{
                            System.out.println(tabs + "  Length: " + Array.getLength(field.get(obj)));
                            Object[] arrayContents;
                            System.out.println(tabs + "  Array Entries-> ");
                            if(field.get(obj) instanceof Object[]) {   //not primitive
                                arrayContents = (Object[]) field.get(obj);
                                for(Object ob : arrayContents) {
                                    System.out.print(tabs + "   Value: ");
                                    try {
                                        System.out.println(ob.getClass().getName());
                                    } catch (NullPointerException e) {
                                        System.out.println("null");
                                    }
                                }
                            }
                            else {  //primitive
                                arrayContents = new Object[Array.getLength(field.get(obj))];
                                for(int i = 0; i < Array.getLength(field.get(obj)); i++) {
                                    arrayContents[i] = Array.get(field.get(obj), i);
                                    System.out.println(tabs + "   Value: " + String.valueOf(arrayContents[i]));
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else {  //not array
                        System.out.println(tabs + "  " + field.getClass().getName() + "@" + field.hashCode());
                    }
                }
            }
        }
        else {
            System.out.println("NONE");
        }
    }
}
