package tests;

import program.Visualizer;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ObjectCreationTest {
    private static Visualizer visualizer;
    private static ByteArrayOutputStream baos;
    private static ByteArrayInputStream in;
    private static PrintStream ps;
    private static PrintStream old;
    private static Scanner scan;

    @Before
    public void setup() {
        in  = new ByteArrayInputStream("".getBytes());
        System.setIn(in);
        scan = new Scanner(System.in);
        visualizer = new Visualizer();
        visualizer.userSelectionMenu(scan);

        //This code to capture System.out was taken from this stackoverflow answer:
        //https://stackoverflow.com/a/8708357
        //it is also used in this.cleanup()
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        old = System.out;
        System.setOut(ps);
    }

    @Test
    public void testHelpCommand() {
        try{
            in.read("create object1\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("a: 43 b: 12.6 c: true", ps.toString());
    }

    //reverts system.out to normal and allows us to access whats stored in baos
    public static void cleanup() {
        System.out.flush();
        System.setOut(old);
    }

}
