package program;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Deserializer {
    private static Object[] createdObjects;
    /**
     * takes json string object and creates the object instances and returns it
     * @param source The json string object
     * @return
     */
    public static ArrayList<Object> deserializeObject(String source) {
        ArrayList<Object> objects = new ArrayList<>();

        JsonReader jsonReader = Json.createReader(new StringReader(source));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        //this now contains the cells in the master object array for easier parsing
        JsonArray objectArray = jsonObject.getJsonArray("objects");

        //debug prints
        //System.out.println(objectArray.toString().replace("\\", ""));
        //System.out.println("Size of object array: " + objectArray.size());

        //the object ID is the index in this array for that object
        //Ex: object with ID = 0 is at index 0
        createdObjects = new Object[objectArray.size()];

        for(int i = 0; i < objectArray.size(); i++) {
            jsonReader = Json.createReader(new StringReader(objectArray.getString(i)));
            jsonObject = jsonReader.readObject();
            System.out.println(jsonObject.toString().replace("\\", ""));

            int id = jsonObject.getInt("id");
            //System.out.println("Object ID: " + id);

            createdObjects[id] = deserializeJsonClass(jsonObject);

        }

        //debug
        System.out.println(getArrayListPrintOut());

        return objects;
    }

    public static String getArrayListPrintOut() {
        String result = "";

        for(int j = 0; j < createdObjects.length; j++) {
            if(createdObjects[j] == null || createdObjects[j].getClass().isArray())
                result += "\nIndex [" + j + "]: " + Arrays.toString((int[]) createdObjects[j]);
            else
                result += "\nIndex [" + j + "]: " + createdObjects[j].toString();
        }

        return result;
    }

    /**
     * Does the actual deserialization and reflection process
     * @param jsonObject The input jsonObject to be deserialized
     * @return The instanced object described by the jsonObject
     */
    private static Object deserializeJsonClass(JsonObject jsonObject) {
        /*
        String className
        int id
        String type (object or array only)
        fields
         */
        Object result = null;
        String className = jsonObject.getString("class");
        className = className.replace("program.", "");


        //System.out.println(jsonObject.toString().replace("\\", ""));

        if(className.matches("AllPrimitive")) { //object 1
            JsonArray fields = jsonObject.getJsonArray("fields");
            result = new AllPrimitive(deserializeJsonAllPrimitive(fields));
        }
        else if(className.matches("ArrayPrimitives")) { //object 3
            JsonArray fields = jsonObject.getJsonArray("fields");
            result = new ArrayPrimitives(deserializeJsonArrayPrimitives(fields));
        }
        else if(className.matches("ArrayReferences")) {

        }
        else if(className.matches("ComplexWithReference")) {    //object 2
            JsonArray fields = jsonObject.getJsonArray("fields");
            result = new ComplexWithReferences(deserializeJsonComplexWithReferences(fields));
        }
        else if(className.matches("InstanceJavaCollection")) {

        }
        else if(className.equals("[I")) {   //int array
            JsonArray entries = jsonObject.getJsonArray("entries");
            result = deserializeJsonIntArray(entries);
        }


        return result;
    }

    //breaks down the AllPrimitive jsonString
    private static AllPrimitive deserializeJsonAllPrimitive (JsonArray jsonFields) {
        AllPrimitive result = null;

        //set primitive values since they won't occur later in the json string
        int a;
        double b;
        boolean c;

        a = Integer.parseInt(jsonFields.getJsonObject(0).getString("value"));
        b = Double.parseDouble(jsonFields.getJsonObject(1).getString("value"));
        c = Boolean.parseBoolean(jsonFields.getJsonObject(2).getString("value"));

        result = new AllPrimitive(a,b,c);

        return result;
    }

    private static ArrayPrimitives deserializeJsonArrayPrimitives (JsonArray jsonFields) {
        //deserialize the int[] inside of the ArrayPrimitives class
        ArrayPrimitives result = null;
        //set up array result, will be linked later
        int[] arr = new int[0];

        result = new ArrayPrimitives(arr);
        return result;
    }

    private static int[] deserializeJsonIntArray(JsonArray array) {
        int[] result = new int[array.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(array.getJsonObject(i).getString("value"));
        }


        return result;
    }

    private static ComplexWithReferences deserializeJsonComplexWithReferences (JsonArray jsonFields) {
        ComplexWithReferences result = null;

        AllPrimitive obj1 = new AllPrimitive(null);
        AllPrimitive obj2 = new AllPrimitive(null);

        ArrayPrimitives arr1 = new ArrayPrimitives((int[]) null);

        result = new ComplexWithReferences(obj1, obj2, arr1);

        return result;
    }
}