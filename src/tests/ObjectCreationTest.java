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

        assertEquals("\n[a: 22] [b: 69.9] [c: true]\n", send.toString());
    }

    @Test
    public void testObject1MultipleObject() {
        String data = "create object1\n" + "22\n" + "69.9\n" + "true\n" +
                      "create object1\n" + "13\n" + "102.76\n" + "false\n" +
                      "create object1\n" + "-2076\n" + "-2.76\n" + "false\n" +
                      "create object1\n" + "0\n" + "0.0\n" + "true\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n[a: 22] [b: 69.9] [c: true]\n" +
                "\n[a: 13] [b: 102.76] [c: false]\n" +
                "\n[a: -2076] [b: -2.76] [c: false]\n" +
                "\n[a: 0] [b: 0.0] [c: true]\n", send.toString());
    }

    @Test
    public void testObject3SingleObject() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                        "7\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n[Index: 0]: 0\n" + "[Index: 1]: 1\n" + "[Index: 2]: 2\n" + "[Index: 3]: 3\n" +
                "[Index: 4]: 4\n" + "[Index: 5]: 5\n" + "[Index: 6]: 6\n" + "[Index: 7]: 7\n\n", send.toString());
    }

    @Test
    public void testObject3MultipleObjects() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                "7\n" + "end\n" + "create object3\n" + "12\n" + "end\n" + "create object3\n" + "99\n" + "95\n" +
                "54\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n[Index: 0]: 0\n" + "[Index: 1]: 1\n" + "[Index: 2]: 2\n" + "[Index: 3]: 3\n" +
                "[Index: 4]: 4\n" + "[Index: 5]: 5\n" + "[Index: 6]: 6\n" + "[Index: 7]: 7\n\n" + "\n[Index: 0]: 12\n\n"
                + "\n[Index: 0]: 99\n" + "[Index: 1]: 95\n" + "[Index: 2]: 54\n\n", send.toString());
    }

    @Test
    public void testObjectComplexWithReferencesSingleObject() {
        String data = "create object2\n" + "12\n" + "22.2\n" + "true\n" + "69\n" + "102.54\n" + "false\n" +
                "23\n" + "24\n" + "87\n" + "10000\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n[a: 12] [b: 22.2] [c: true]\n" +
                        "\n[a: 69] [b: 102.54] [c: false]\n" +
                        "\n[Index: 0]: 23\n" + "[Index: 1]: 24\n" + "[Index: 2]: 87\n" + "[Index: 3]: 10000\n\n",
                        send.toString());
    }

    @Test
    public void testObjectComplexWithReferencesMultipleObjects() {
        String data1 = "create object2\n" + "1\n" + "2.2\n" + "true\n" + "100\n" + "202.22\n" + "true\n" +
                        "99\n" + "100001\n" + "101\n" + "end\n";
        String data2 = "create object2\n" + "-11\n" + "-4.2\n" + "false\n" + "-60\n" + "-902.22\n" + "true\n" +
                        "1010\n" + "end\n" + "send\n";

        String data = data1 + data2;

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n[a: 1] [b: 2.2] [c: true]\n" +
                    "\n[a: 100] [b: 202.22] [c: true]\n" +
                    "\n[Index: 0]: 99" + "\n[Index: 1]: 100001" + "\n[Index: 2]: 101\n\n" +
                    "\n[a: -11] [b: -4.2] [c: false]\n" +
                    "\n[a: -60] [b: -902.22] [c: true]\n" +
                    "\n[Index: 0]: 1010\n\n", send.toString());
    }

    @Test
    public void testArrayReferencesSingleObject() {
        //single array
        String data = "create object4\n" + "create object1\n" + "1\n" + "-2.02\n" + "false\n" +
                    "create object1\n" + "-65\n" + "300.1\n" + "false\n" +
                    "create object3\n" + "1\n" + "2\n" + "3\n" + "end\n" +
                    "create object2\n" + "12\n" + "22.2\n" + "true\n" + "69\n" + "102.54\n" + "false\n" +
                "23\n" + "24\n" + "87\n" + "10000\n" + "end\n" + "finish\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n\n[a: 1] [b: -2.02] [c: false]\n" +
                            "\n[a: -65] [b: 300.1] [c: false]\n\n" +
                            "[Index: 0]: 1\n" + "[Index: 1]: 2\n" + "[Index: 2]: 3\n\n" +
                            "\n[a: 12] [b: 22.2] [c: true]\n" +
                            "\n[a: 69] [b: 102.54] [c: false]\n" +
                            "\n[Index: 0]: 23\n" + "[Index: 1]: 24\n" + "[Index: 2]: 87\n" + "[Index: 3]: 10000\n\n\n",
                            send.toString());
    }

    @Test
    public void testArrayReferenceNestedObject() {
        //array of references with a nested array of references as an element
        String data = "create object4\n" + "create object1\n" + "1\n" + "-2.02\n" + "false\n" +
                        "create object4\n" + "create object1\n" + "-65\n" + "300.1\n" + "false\n" + "finish\n" +
                        "create object3\n" + "1\n" + "2\n" + "3\n" + "end\n" + "finish\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n\n[a: 1] [b: -2.02] [c: false]\n" +
                            "\n\n[a: -65] [b: 300.1] [c: false]\n\n" +
                            "\n[Index: 0]: 1" + "\n[Index: 1]: 2" + "\n[Index: 2]: 3\n\n\n", send.toString());
    }

    @Test
    public void testArrayReferencesMultipleObjects() {
        //multiple array of references (but all separate)
    }

    //reverts system.out to normal and allows us to access whats stored in baos
    public static void cleanup() {

    }

}
