package tests;

import program.Sender;
import org.junit.*;

import java.io.ByteArrayInputStream;

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

        assertEquals("\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 22] [b: 69.9] [c: true]\n", send.toString());
    }

    @Test
    public void testObject1MultipleObject() {
        String data = "create object1\n" + "22\n" + "69.9\n" + "true\n" +
                      "create object1\n" + "13\n" + "102.76\n" + "false\n" +
                      "create object1\n" + "-2076\n" + "-2.76\n" + "false\n" +
                      "create object1\n" + "0\n" + "0.0\n" + "true\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 22] [b: 69.9] [c: true]\n" +
                "\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 13] [b: 102.76] [c: false]\n" +
                "\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: -2076] [b: -2.76] [c: false]\n" +
                "\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 0] [b: 0.0] [c: true]\n", send.toString());
    }

    @Test
    public void testObject3SingleObject() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                        "7\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 0\n" +
                "\t[Index: 1]: 1\n" +
                "\t[Index: 2]: 2\n" +
                "\t[Index: 3]: 3\n" +
                "\t[Index: 4]: 4\n" +
                "\t[Index: 5]: 5\n" +
                "\t[Index: 6]: 6\n" +
                "\t[Index: 7]: 7\n" +
                "\n", send.toString());
    }

    @Test
    public void testObject3MultipleObjects() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                "7\n" + "end\n" + "create object3\n" + "12\n" + "end\n" + "create object3\n" + "99\n" + "95\n" +
                "54\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 0\n" +
                "\t[Index: 1]: 1\n" +
                "\t[Index: 2]: 2\n" +
                "\t[Index: 3]: 3\n" +
                "\t[Index: 4]: 4\n" +
                "\t[Index: 5]: 5\n" +
                "\t[Index: 6]: 6\n" +
                "\t[Index: 7]: 7\n" +
                "\n" +
                "\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 12\n" +
                "\n" +
                "\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 99\n" +
                "\t[Index: 1]: 95\n" +
                "\t[Index: 2]: 54\n\n", send.toString());
    }

    @Test
    public void testObjectComplexWithReferencesSingleObject() {
        String data = "create object2\n" + "12\n" + "22.2\n" + "true\n" + "69\n" + "102.54\n" + "false\n" +
                "23\n" + "24\n" + "87\n" + "10000\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n" +
                        "[Complex With References (Object2)]\n" +
                        "\t\n" +
                        "[AllPrimitives (Object1)]\n" +
                        "\t[a: 12] [b: 22.2] [c: true]\n" +
                        "\t\n" +
                        "[AllPrimitives (Object1)]\n" +
                        "\t[a: 69] [b: 102.54] [c: false]\n" +
                        "\t\n" +
                        "[Array of Primitives (Object3)]\n" +
                        "\t[Index: 0]: 23\n" +
                        "\t[Index: 1]: 24\n" +
                        "\t[Index: 2]: 87\n" +
                        "\t[Index: 3]: 10000\n" +
                        "\n",
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

        assertEquals("\n" +
                "[Complex With References (Object2)]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 1] [b: 2.2] [c: true]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 100] [b: 202.22] [c: true]\n" +
                "\t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 99\n" +
                "\t[Index: 1]: 100001\n" +
                "\t[Index: 2]: 101\n" +
                "\n" +
                "\n" +
                "[Complex With References (Object2)]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: -11] [b: -4.2] [c: false]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: -60] [b: -902.22] [c: true]\n" +
                "\t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 1010\n" +
                "\n", send.toString());
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

        assertEquals("\n" +
                        "[Array of References (Object4)]\n" +
                        "\t[Index: 0]: \t\n" +
                        "[AllPrimitives (Object1)]\n" +
                        "\t[a: 1] [b: -2.02] [c: false]\n" +
                        "\t[Index: 1]: \t\n" +
                        "[AllPrimitives (Object1)]\n" +
                        "\t[a: -65] [b: 300.1] [c: false]\n" +
                        "\t[Index: 2]: \t\n" +
                        "[Array of Primitives (Object3)]\n" +
                        "\t[Index: 0]: 1\n" +
                        "\t[Index: 1]: 2\n" +
                        "\t[Index: 2]: 3\n" +
                        "\n" +
                        "\t[Index: 3]: \t\n" +
                        "[Complex With References (Object2)]\n" +
                        "\t\n" +
                        "[AllPrimitives (Object1)]\n" +
                        "\t[a: 12] [b: 22.2] [c: true]\n" +
                        "\t\n" +
                        "[AllPrimitives (Object1)]\n" +
                        "\t[a: 69] [b: 102.54] [c: false]\n" +
                        "\t\n" +
                        "[Array of Primitives (Object3)]\n" +
                        "\t[Index: 0]: 23\n" +
                        "\t[Index: 1]: 24\n" +
                        "\t[Index: 2]: 87\n" +
                        "\t[Index: 3]: 10000\n" +
                        "\n" +
                        "\n",
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

        assertEquals( "\n" +
                "[Array of References (Object4)]\n" +
                "\t[Index: 0]: \t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 1] [b: -2.02] [c: false]\n" +
                "\t[Index: 1]: \t\n" +
                "[Array of References (Object4)]\n" +
                "\t[Index: 0]: \t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: -65] [b: 300.1] [c: false]\n" +
                "\n" +
                "\t[Index: 2]: \t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 1\n" +
                "\t[Index: 1]: 2\n" +
                "\t[Index: 2]: 3\n" +
                "\n" +
                "\n", send.toString());
    }

    @Test
    public void testArrayReferencesMultipleObjects() {
        //multiple array of references (but all separate)
        String data1 = "create object4\n" + "create object1\n" + "1\n" + "-2.02\n" + "false\n" +
                "create object1\n" + "-65\n" + "300.1\n" + "false\n" +
                "create object3\n" + "1\n" + "2\n" + "3\n" + "end\n" +
                "create object2\n" + "12\n" + "22.2\n" + "true\n" + "69\n" + "102.54\n" + "false\n" +
                "23\n" + "24\n" + "87\n" + "10000\n" + "end\n" + "finish\n";

        String data2 = "create object4\n" + "create object1\n" + "1\n" + "-2.02\n" + "false\n" +
                "create object4\n" + "create object1\n" + "-65\n" + "300.1\n" + "false\n" + "finish\n" +
                "create object3\n" + "1\n" + "2\n" + "3\n" + "end\n" + "finish\n" + "send\n";

        String data = data1 + data2;

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n" +
                "[Array of References (Object4)]\n" +
                "\t[Index: 0]: \t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 1] [b: -2.02] [c: false]\n" +
                "\t[Index: 1]: \t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: -65] [b: 300.1] [c: false]\n" +
                "\t[Index: 2]: \t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 1\n" +
                "\t[Index: 1]: 2\n" +
                "\t[Index: 2]: 3\n" +
                "\n" +
                "\t[Index: 3]: \t\n" +
                "[Complex With References (Object2)]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 12] [b: 22.2] [c: true]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 69] [b: 102.54] [c: false]\n" +
                "\t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 23\n" +
                "\t[Index: 1]: 24\n" +
                "\t[Index: 2]: 87\n" +
                "\t[Index: 3]: 10000\n" +
                "\n" +
                "\n" +
                "\n" +
                "[Array of References (Object4)]\n" +
                "\t[Index: 0]: \t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 1] [b: -2.02] [c: false]\n" +
                "\t[Index: 1]: \t\n" +
                "[Array of References (Object4)]\n" +
                "\t[Index: 0]: \t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: -65] [b: 300.1] [c: false]\n" +
                "\n" +
                "\t[Index: 2]: \t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 1\n" +
                "\t[Index: 1]: 2\n" +
                "\t[Index: 2]: 3\n" +
                "\n" +
                "\n", send.toString());
    }

    @Test
    public void testInstanceJavaCollectionSingleObject() {
        String data = "create object5\n" + "1\n" + "22.2\n" + "false\n" + "67\n" + "6.9\n" + "false\n" +
                    "99\n" + "101.1\n" + "true\n" + "1\n" + "3\n" + "5\n" + "7\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n" +
                "[Instance Java Collection (Object5)]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 1] [b: 22.2] [c: false]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 67] [b: 6.9] [c: false]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 99] [b: 101.1] [c: true]\n" +
                "\t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 1\n" +
                "\t[Index: 1]: 3\n" +
                "\t[Index: 2]: 5\n" +
                "\t[Index: 3]: 7\n" +
                "\n" +
                "\n", send.toString());
    }

    @Test
    public void testInstanceJavaCollectionMultipleObjects() {
        String data1 = "create object5\n" + "1\n" + "22.2\n" + "false\n" + "67\n" + "6.9\n" + "false\n" +
                "99\n" + "101.1\n" + "true\n" + "1\n" + "3\n" + "5\n" + "7\n" + "end\n";

        String data2 = "create object5\n" + "3\n" + "69.420\n" + "true\n" + "6\n" + "4.20\n" + "true\n" +
                        "99\n" + "100\n" + "false\n" + "99\n" + "end\n" + "send\n";

        String data = data1 + data2;

        System.setIn(new ByteArrayInputStream(data.getBytes()));

        send.driver();

        assertEquals("\n" +
                "[Instance Java Collection (Object5)]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 1] [b: 22.2] [c: false]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 67] [b: 6.9] [c: false]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 99] [b: 101.1] [c: true]\n" +
                "\t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 1\n" +
                "\t[Index: 1]: 3\n" +
                "\t[Index: 2]: 5\n" +
                "\t[Index: 3]: 7\n" +
                "\n" +
                "\n" +
                "\n" +
                "[Instance Java Collection (Object5)]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 3] [b: 69.42] [c: true]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 6] [b: 4.2] [c: true]\n" +
                "\t\n" +
                "[AllPrimitives (Object1)]\n" +
                "\t[a: 99] [b: 100.0] [c: false]\n" +
                "\t\n" +
                "[Array of Primitives (Object3)]\n" +
                "\t[Index: 0]: 99\n\n\n", send.toString());
    }

    //reverts system.out to normal and allows us to access whats stored in baos
    public static void cleanup() {

    }

}
