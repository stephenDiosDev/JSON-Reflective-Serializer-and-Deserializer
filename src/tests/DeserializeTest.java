package tests;

import org.junit.Before;
import org.junit.Test;
import program.Deserializer;
import program.Sender;

import javax.json.JsonObject;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Tests the deserialize results (their toString statements) to ensure the objects are correct
 */
public class DeserializeTest {
    private static Deserializer deserializer;
    private static Sender sender;

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
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: [a: 22] [b: 69.9] [c: true]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testMultipleAllPrimitive() {
        String data = "create object1\n" + "22\n" + "69.9\n" + "true\n" +
                "create object1\n" + "13\n" + "102.76\n" + "false\n" +
                "create object1\n" + "-2076\n" + "-2.76\n" + "false\n" +
                "create object1\n" + "0\n" + "0.0\n" + "true\n" + "send\n";;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: [a: 22] [b: 69.9] [c: true]" +
                "\nIndex [1]: [a: 13] [b: 102.76] [c: false]" +
                "\nIndex [2]: [a: -2076] [b: -2.76] [c: false]" +
                "\nIndex [3]: [a: 0] [b: 0.0] [c: true]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testSinglePrimitiveArray() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                "7\n" + "end\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: No elements" +
                "\nIndex [1]: [0, 1, 2, 3, 4, 5, 6, 7]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testMultiplePrimitiveArray() {
        String data = "create object3\n" + "0\n" + "1\n" + "2\n" + "3\n" + "4\n" + "5\n" + "6\n" +
                "7\n" + "end\n" + "create object3\n" + "12\n" + "end\n" + "create object3\n" + "99\n" + "95\n" +
                "54\n" + "end\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: No elements" +
                "\nIndex [1]: [0, 1, 2, 3, 4, 5, 6, 7]" +
                "\nIndex [2]: No elements" +
                "\nIndex [3]: [12]" +
                "\nIndex [4]: No elements" +
                "\nIndex [5]: [99, 95, 54]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testSingleAComplexWithReferences() {
        String data = "create object2\n" + "12\n" + "22.2\n" + "true\n" + "69\n" + "102.54\n" + "false\n" +
                "23\n" + "24\n" + "87\n" + "10000\n" + "end\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: [a: 5] [b: 1.24] [c: false]\n" +
                "[a: 5] [b: 1.24] [c: false]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 2\n" +
                "[Index: 2]: 3\n" +
                "[Index: 3]: 4\n" +
                "[Index: 4]: 5\n" +
                "[Index: 5]: 6\n" +
                "\nIndex [1]: [a: 12] [b: 22.2] [c: true]" +
                "\nIndex [2]: [a: 69] [b: 102.54] [c: false]" +
                "\nIndex [3]: No elements" +
                "\nIndex [4]: [23, 24, 87, 10000]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testMultipleAComplexWithReferences() {
        String data1 = "create object2\n" + "1\n" + "2.2\n" + "true\n" + "100\n" + "202.22\n" + "true\n" +
                "99\n" + "100001\n" + "101\n" + "end\n";
        String data2 = "create object2\n" + "-11\n" + "-4.2\n" + "false\n" + "-60\n" + "-902.22\n" + "true\n" +
                "1010\n" + "end\n" + "send\n";

        String data = data1 + data2;

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: [a: 5] [b: 1.24] [c: false]\n" +
                "[a: 5] [b: 1.24] [c: false]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 2\n" +
                "[Index: 2]: 3\n" +
                "[Index: 3]: 4\n" +
                "[Index: 4]: 5\n" +
                "[Index: 5]: 6\n" +
                "\nIndex [1]: [a: 1] [b: 2.2] [c: true]" +
                "\nIndex [2]: [a: 100] [b: 202.22] [c: true]" +
                "\nIndex [3]: No elements" +
                "\nIndex [4]: [99, 100001, 101]" +
                "\nIndex [5]: [a: 5] [b: 1.24] [c: false]\n" +
                "[a: 5] [b: 1.24] [c: false]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 2\n" +
                "[Index: 2]: 3\n" +
                "[Index: 3]: 4\n" +
                "[Index: 4]: 5\n" +
                "[Index: 5]: 6\n" +
                "\nIndex [6]: [a: -11] [b: -4.2] [c: false]" +
                "\nIndex [7]: [a: -60] [b: -902.22] [c: true]" +
                "\nIndex [8]: No elements" +
                "\nIndex [9]: [1010]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testSingleArrayReferences() {
        String data = "create object4\n" + "create object1\n" + "1\n" + "-2.02\n" + "false\n" +
                "create object1\n" + "-65\n" + "300.1\n" + "false\n" +
                "create object3\n" + "1\n" + "2\n" + "3\n" + "end\n" +
                "create object2\n" + "12\n" + "22.2\n" + "true\n" + "69\n" + "102.54\n" + "false\n" +
                "23\n" + "24\n" + "87\n" + "10000\n" + "end\n" + "finish\n" + "send\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: All elements null" +
                "\nIndex [1]: null" +
                "\nIndex [2]: [a: 1] [b: -2.02] [c: false]" +
                "\nIndex [3]: [a: -65] [b: 300.1] [c: false]" +
                "\nIndex [4]: No elements" +
                "\nIndex [5]: [1, 2, 3]" +
                "\nIndex [6]: [a: 5] [b: 1.24] [c: false]\n" +
                "[a: 5] [b: 1.24] [c: false]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 2\n" +
                "[Index: 2]: 3\n" +
                "[Index: 3]: 4\n" +
                "[Index: 4]: 5\n" +
                "[Index: 5]: 6\n" +
                "\nIndex [7]: [a: 12] [b: 22.2] [c: true]" +
                "\nIndex [8]: [a: 69] [b: 102.54] [c: false]" +
                "\nIndex [9]: No elements" +
                "\nIndex [10]: [23, 24, 87, 10000]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testMultipleArrayReferences() {
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
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: All elements null" +
                "\nIndex [1]: null" +
                "\nIndex [2]: [a: 1] [b: -2.02] [c: false]" +
                "\nIndex [3]: [a: -65] [b: 300.1] [c: false]" +
                "\nIndex [4]: No elements" +
                "\nIndex [5]: [1, 2, 3]" +
                "\nIndex [6]: [a: 5] [b: 1.24] [c: false]\n" +
                "[a: 5] [b: 1.24] [c: false]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 2\n" +
                "[Index: 2]: 3\n" +
                "[Index: 3]: 4\n" +
                "[Index: 4]: 5\n" +
                "[Index: 5]: 6\n" +
                "\nIndex [7]: [a: 12] [b: 22.2] [c: true]" +
                "\nIndex [8]: [a: 69] [b: 102.54] [c: false]" +
                "\nIndex [9]: No elements" +
                "\nIndex [10]: [23, 24, 87, 10000]" +
                "\nIndex [11]: All elements null" +
                "\nIndex [12]: null" +
                "\nIndex [13]: [a: 1] [b: -2.02] [c: false]" +
                "\nIndex [14]: All elements null" +
                "\nIndex [15]: null" +
                "\nIndex [16]: [a: -65] [b: 300.1] [c: false]" +
                "\nIndex [17]: No elements" +
                "\nIndex [18]: [1, 2, 3]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testNestedArrayReferences() {
        String data = "create object4\n" + "create object1\n" + "1\n" + "-2.02\n" + "false\n" +
                "create object4\n" + "create object1\n" + "-65\n" + "300.1\n" + "false\n" + "finish\n" +
                "create object3\n" + "1\n" + "2\n" + "3\n" + "end\n" + "finish\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: All elements null" +
                "\nIndex [1]: null" +
                "\nIndex [2]: [a: 1] [b: -2.02] [c: false]" +
                "\nIndex [3]: All elements null" +
                "\nIndex [4]: null" +
                "\nIndex [5]: [a: -65] [b: 300.1] [c: false]" +
                "\nIndex [6]: No elements" +
                "\nIndex [7]: [1, 2, 3]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testSingleInstanceJavaCollection() {
        String data = "create object5\n" + "1\n" + "22.2\n" + "false\n" + "67\n" + "6.9\n" + "false\n" +
                "99\n" + "101.1\n" + "true\n" + "1\n" + "3\n" + "5\n" + "7\n" + "end\n" + "send\n";

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: [a: 1] [b: 2.0] [c: true]\n" +
                "[a: -1] [b: -2.0] [c: false]\n" +
                "[a: 3] [b: 5069.8] [c: true]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 3\n" +
                "[Index: 2]: 5\n" +
                "[Index: 3]: 7\n" +
                "[Index: 4]: 11\n" +
                "[Index: 5]: 13\n" +
                "[Index: 6]: 17\n" +
                "[Index: 7]: 19\n" +
                "\n" +
                "\nIndex [1]: null" +
                "\nIndex [2]: null" +
                "\nIndex [3]: null" +
                "\nIndex [4]: null" +
                "\nIndex [5]: [a: 1] [b: 22.2] [c: false]" +
                "\nIndex [6]: [a: 67] [b: 6.9] [c: false]" +
                "\nIndex [7]: [a: 99] [b: 101.1] [c: true]" +
                "\nIndex [8]: No elements" +
                "\nIndex [9]: [1, 3, 5, 7]" +
                "\nIndex [10]: [a: 1] [b: 22.2] [c: false]" +
                "\nIndex [11]: [a: 67] [b: 6.9] [c: false]" +
                "\nIndex [12]: [a: 99] [b: 101.1] [c: true]" +
                "\nIndex [13]: No elements" +
                "\nIndex [14]: [1, 3, 5, 7]", Deserializer.getArrayListPrintOut());
    }

    @Test
    public void testMultipleInstanceJavaCollection() {
        String data1 = "create object5\n" + "1\n" + "22.2\n" + "false\n" + "67\n" + "6.9\n" + "false\n" +
                "99\n" + "101.1\n" + "true\n" + "1\n" + "3\n" + "5\n" + "7\n" + "end\n";

        String data2 = "create object5\n" + "3\n" + "69.420\n" + "true\n" + "6\n" + "4.20\n" + "true\n" +
                "99\n" + "100\n" + "false\n" + "99\n" + "end\n" + "send\n";

        String data = data1 + data2;

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        sender.driver();

        JsonObject jsonObject = sender.getJsonObject();
        deserializer.deserializeObject(jsonObject.toString());

        assertEquals("\nIndex [0]: [a: 1] [b: 2.0] [c: true]\n" +
                "[a: -1] [b: -2.0] [c: false]\n" +
                "[a: 3] [b: 5069.8] [c: true]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 3\n" +
                "[Index: 2]: 5\n" +
                "[Index: 3]: 7\n" +
                "[Index: 4]: 11\n" +
                "[Index: 5]: 13\n" +
                "[Index: 6]: 17\n" +
                "[Index: 7]: 19\n\n" +
                "\nIndex [1]: null" +
                "\nIndex [2]: null" +
                "\nIndex [3]: null" +
                "\nIndex [4]: null" +
                "\nIndex [5]: [a: 1] [b: 22.2] [c: false]" +
                "\nIndex [6]: [a: 67] [b: 6.9] [c: false]" +
                "\nIndex [7]: [a: 99] [b: 101.1] [c: true]" +
                "\nIndex [8]: No elements" +
                "\nIndex [9]: [1, 3, 5, 7]" +
                "\nIndex [10]: [a: 1] [b: 22.2] [c: false]" +
                "\nIndex [11]: [a: 67] [b: 6.9] [c: false]" +
                "\nIndex [12]: [a: 99] [b: 101.1] [c: true]" +
                "\nIndex [13]: No elements" +
                "\nIndex [14]: [1, 3, 5, 7]" +
                "\nIndex [15]: [a: 1] [b: 2.0] [c: true]\n" +
                "[a: -1] [b: -2.0] [c: false]\n" +
                "[a: 3] [b: 5069.8] [c: true]\n" +
                "\n" +
                "[Index: 0]: 1\n" +
                "[Index: 1]: 3\n" +
                "[Index: 2]: 5\n" +
                "[Index: 3]: 7\n" +
                "[Index: 4]: 11\n" +
                "[Index: 5]: 13\n" +
                "[Index: 6]: 17\n" +
                "[Index: 7]: 19\n\n" +
                "\nIndex [16]: null" +
                "\nIndex [17]: null" +
                "\nIndex [18]: null" +
                "\nIndex [19]: null" +
                "\nIndex [20]: [a: 3] [b: 69.42] [c: true]" +
                "\nIndex [21]: [a: 6] [b: 4.2] [c: true]" +
                "\nIndex [22]: [a: 99] [b: 100.0] [c: false]" +
                "\nIndex [23]: No elements" +
                "\nIndex [24]: [99]" +
                "\nIndex [25]: [a: 3] [b: 69.42] [c: true]" +
                "\nIndex [26]: [a: 6] [b: 4.2] [c: true]" +
                "\nIndex [27]: [a: 99] [b: 100.0] [c: false]" +
                "\nIndex [28]: No elements" +
                "\nIndex [29]: [99]", Deserializer.getArrayListPrintOut());
    }
}
