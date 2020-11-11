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

    }

    @Test
    public void testHelpCommand() {

    }

    //reverts system.out to normal and allows us to access whats stored in baos
    public static void cleanup() {

    }

}
