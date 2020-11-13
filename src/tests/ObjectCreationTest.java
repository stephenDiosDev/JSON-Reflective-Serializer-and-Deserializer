package tests;

import program.Sender;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ObjectCreationTest {
    private static Sender send;

    @Before
    public void setup() {
        send = new Sender(false);
    }

    @Test
    public void testObject1SingleObject() {
        String data = "create object1\n" + "22\n" + "69.9\n" + "true\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("[a: 22] [b: 69.9] [c: true]\n", send.toString());
    }

    @Test
    public void testObject1MultipleObject() {
        String data = "create object1\n" + "22\n" + "69.9\n" + "true\n" +
                      "create object1\n" + "13\n" + "102.76\n" + "false\n" +
                      "create object1\n" + "-2076\n" + "-2.76\n" + "false\n" +
                      "create object1\n" + "0\n" + "0.0\n" + "true\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("[a: 22] [b: 69.9] [c: true]\n" +
                "[a: 13] [b: 102.76] [c: false]\n" +
                "[a: -2076] [b: -2.76] [c: false]\n" +
                "[a: 0] [b: 0.0] [c: true]\n", send.toString());
    }

    @Test
    public void testObject3SingleObject() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                        "7\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("[Index: 0]: 0\n" + "[Index: 1]: 1\n" + "[Index: 2]: 2\n" + "[Index: 3]: 3\n" +
                "[Index: 4]: 4\n" + "[Index: 5]: 5\n" + "[Index: 6]: 6\n" + "[Index: 7]: 7\n\n", send.toString());
    }

    //reverts system.out to normal and allows us to access whats stored in baos
    public static void cleanup() {

    }

}
