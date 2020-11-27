package tests;

import org.junit.Before;
import org.junit.Test;
import program.Deserializer;
import program.Sender;
import program.Visualizer;

import javax.json.JsonObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class VisualizerOutputTest {
    private static Deserializer deserializer;
    private static Sender sender;

    /*
    The code to capture System.out is taken from here:
    https://openwritings.net/pg/java/how-capture-and-store-console-output-systemoutprintln
     */
    ByteArrayOutputStream newConsole;

    @Before
    public void setupSender() {
        sender = new Sender(false);
        deserializer = new Deserializer();
    }

    @Test
    public void testSingleAllPrimitive() {
        String data = "create object1\n" + "22\n" + "69.9\n" + "true\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        ArrayList<Object> list = deserializer.deserializeObject(jsonObject.toString());

        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        Visualizer.printDeserializerOutput(list);

        assertEquals("CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 22\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 69.9\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: true\n" +
                "\n", newConsole.toString());
    }

    @Test
    public void testMultipleAllPrimitive() {
        String data = "create object1\n" + "22\n" + "69.9\n" + "true\n" +
                "create object1\n" + "13\n" + "102.76\n" + "false\n" +
                "create object1\n" + "-2076\n" + "-2.76\n" + "false\n" +
                "create object1\n" + "0\n" + "0.0\n" + "true\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        ArrayList<Object> list = deserializer.deserializeObject(jsonObject.toString());

        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        Visualizer.printDeserializerOutput(list);

        assertEquals("CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 22\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 69.9\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: true\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 13\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 102.76\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: false\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: -2076\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: -2.76\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: false\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 0\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 0.0\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: true\n" +
                "\n", newConsole.toString());
    }

    @Test
    public void testSinglePrimitiveArray() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                "7\n" + "end\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        ArrayList<Object> list = deserializer.deserializeObject(jsonObject.toString());

        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        Visualizer.printDeserializerOutput(list);

        assertEquals("CLASS\n" +
                "Class: program.ArrayPrimitives\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ArrayPrimitives )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ArrayPrimitives )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: myArr\n" +
                "  Type: class [I\n" +
                "  Modifiers: private\n" +
                "  Component Type: class [I\n" +
                "  Length: 8\n" +
                "  Array Entries-> \n" +
                "   Value: 0\n" +
                "   Value: 1\n" +
                "   Value: 2\n" +
                "   Value: 3\n" +
                "   Value: 4\n" +
                "   Value: 5\n" +
                "   Value: 6\n" +
                "   Value: 7\n" +
                "\n", newConsole.toString());
    }

    @Test
    public void testMultiplePrimitiveArray() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                "7\n" + "end\n" + "create object3\n" + "12\n" + "end\n" + "create object3\n" + "99\n" + "95\n" +
                "54\n" + "end\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        ArrayList<Object> list = deserializer.deserializeObject(jsonObject.toString());

        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        Visualizer.printDeserializerOutput(list);

        assertEquals("CLASS\n" +
                "Class: program.ArrayPrimitives\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ArrayPrimitives )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ArrayPrimitives )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: myArr\n" +
                "  Type: class [I\n" +
                "  Modifiers: private\n" +
                "  Component Type: class [I\n" +
                "  Length: 8\n" +
                "  Array Entries-> \n" +
                "   Value: 0\n" +
                "   Value: 1\n" +
                "   Value: 2\n" +
                "   Value: 3\n" +
                "   Value: 4\n" +
                "   Value: 5\n" +
                "   Value: 6\n" +
                "   Value: 7\n" +
                "\n" +

                "CLASS\n" +
                "Class: program.ArrayPrimitives\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ArrayPrimitives )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ArrayPrimitives )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: myArr\n" +
                "  Type: class [I\n" +
                "  Modifiers: private\n" +
                "  Component Type: class [I\n" +
                "  Length: 1\n" +
                "  Array Entries-> \n" +
                "   Value: 12\n" +
                "\n" +

                "CLASS\n" +
                "Class: program.ArrayPrimitives\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ArrayPrimitives )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ArrayPrimitives )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: myArr\n" +
                "  Type: class [I\n" +
                "  Modifiers: private\n" +
                "  Component Type: class [I\n" +
                "  Length: 3\n" +
                "  Array Entries-> \n" +
                "   Value: 99\n" +
                "   Value: 95\n" +
                "   Value: 54\n" +
                "\n", newConsole.toString());
    }

    @Test
    public void testSingleComplexWithReferences() {
        String data = "create object2\n" + "12\n" + "22.2\n" + "true\n" + "69\n" + "102.54\n" + "false\n" +
                "23\n" + "24\n" + "87\n" + "10000\n" + "end\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        ArrayList<Object> list = deserializer.deserializeObject(jsonObject.toString());

        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        Visualizer.printDeserializerOutput(list);

        assertEquals("CLASS\n" +
                "Class: program.ComplexWithReferences\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ComplexWithReferences )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ComplexWithReferences )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: obj1\n" +
                "  Type: class program.AllPrimitive\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-799620434\n" +
                " FIELD\n" +
                "  Name: obj2\n" +
                "  Type: class program.AllPrimitive\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-799620433\n" +
                " FIELD\n" +
                "  Name: arr1\n" +
                "  Type: class program.ArrayPrimitives\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-800530108\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 12\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 22.2\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: true\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 69\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 102.54\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: false\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.ArrayPrimitives\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ArrayPrimitives )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ArrayPrimitives )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: myArr\n" +
                "  Type: class [I\n" +
                "  Modifiers: private\n" +
                "  Component Type: class [I\n" +
                "  Length: 4\n" +
                "  Array Entries-> \n" +
                "   Value: 23\n" +
                "   Value: 24\n" +
                "   Value: 87\n" +
                "   Value: 10000\n" +
                "\n", newConsole.toString());
    }

    @Test
    public void testMultipleComplexWithReferences() {
        String data1 = "create object2\n" + "1\n" + "2.2\n" + "true\n" + "100\n" + "202.22\n" + "true\n" +
                "99\n" + "100001\n" + "101\n" + "end\n";
        String data2 = "create object2\n" + "-11\n" + "-4.2\n" + "false\n" + "-60\n" + "-902.22\n" + "true\n" +
                "1010\n" + "end\n" + "send\n";

        String data = data1 + data2;

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        ArrayList<Object> list = deserializer.deserializeObject(jsonObject.toString());

        newConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newConsole));

        Visualizer.printDeserializerOutput(list);

        assertEquals("CLASS\n" +
                "Class: program.ComplexWithReferences\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ComplexWithReferences )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ComplexWithReferences )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: obj1\n" +
                "  Type: class program.AllPrimitive\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-799620434\n" +
                " FIELD\n" +
                "  Name: obj2\n" +
                "  Type: class program.AllPrimitive\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-799620433\n" +
                " FIELD\n" +
                "  Name: arr1\n" +
                "  Type: class program.ArrayPrimitives\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-800530108\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 1\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 2.2\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: true\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: 100\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: 202.22\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: true\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.ArrayPrimitives\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ArrayPrimitives )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ArrayPrimitives )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: myArr\n" +
                "  Type: class [I\n" +
                "  Modifiers: private\n" +
                "  Component Type: class [I\n" +
                "  Length: 3\n" +
                "  Array Entries-> \n" +
                "   Value: 99\n" +
                "   Value: 100001\n" +
                "   Value: 101\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.ComplexWithReferences\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ComplexWithReferences )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ComplexWithReferences )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: obj1\n" +
                "  Type: class program.AllPrimitive\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-799620434\n" +
                " FIELD\n" +
                "  Name: obj2\n" +
                "  Type: class program.AllPrimitive\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-799620433\n" +
                " FIELD\n" +
                "  Name: arr1\n" +
                "  Type: class program.ArrayPrimitives\n" +
                "  Modifiers: private\n" +
                "  java.lang.reflect.Field@-800530108\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: -11\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: -4.2\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: false\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.AllPrimitive\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.AllPrimitive )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.AllPrimitive )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: a\n" +
                "  Type: int\n" +
                "  Modifiers: private\n" +
                "  Value: -60\n" +
                " FIELD\n" +
                "  Name: b\n" +
                "  Type: double\n" +
                "  Modifiers: private\n" +
                "  Value: -902.22\n" +
                " FIELD\n" +
                "  Name: c\n" +
                "  Type: boolean\n" +
                "  Modifiers: private\n" +
                "  Value: true\n" +
                "\n" +
                "CLASS\n" +
                "Class: program.ArrayPrimitives\n" +
                "SUPERCLASS -> java.lang.Object\n" +
                "\tCLASS\n" +
                "\tClass: java.lang.Object\n" +
                "\tSUPERCLASS -> NONE\n" +
                "\tINTERFACES ( java.lang.Object )\n" +
                "\tInterfaces -> NONE\n" +
                "\tFIELDS ( java.lang.Object )\n" +
                "\tFields -> NONE\n" +
                "INTERFACES ( program.ArrayPrimitives )\n" +
                "Interfaces -> NONE\n" +
                "FIELDS ( program.ArrayPrimitives )\n" +
                "Fields -> \n" +
                " FIELD\n" +
                "  Name: myArr\n" +
                "  Type: class [I\n" +
                "  Modifiers: private\n" +
                "  Component Type: class [I\n" +
                "  Length: 1\n" +
                "  Array Entries-> \n" +
                "   Value: 1010\n" +
                "\n", newConsole.toString());
    }
}
