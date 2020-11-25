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

        assertEquals("\nIndex [0]: \n[a: 22] [b: 69.9] [c: true]", Deserializer.getArrayListPrintOut());
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

        assertEquals("\nIndex [0]: \n[a: 22] [b: 69.9] [c: true]" +
                "\nIndex [1]: \n[a: 13] [b: 102.76] [c: false]" +
                "\nIndex [2]: \n[a: -2076] [b: -2.76] [c: false]" +
                "\nIndex [3]: \n[a: 0] [b: 0.0] [c: true]", Deserializer.getArrayListPrintOut());
    }
}
