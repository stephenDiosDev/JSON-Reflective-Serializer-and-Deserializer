package program;

import javax.json.*;
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

        //send each individual json object for deserialization
        for(int i = 0; i < objectArray.size(); i++) {
            jsonReader = Json.createReader(new StringReader(objectArray.getString(i)));
            jsonObject = jsonReader.readObject();

            //detailed jsonObject printout
            //System.out.println(jsonObject.toString().replace("\\", ""));

            int id = jsonObject.getInt("id");
            //System.out.println("Object ID: " + id);

            createdObjects[id] = deserializeJsonClass(jsonObject);
        }

        //debug
/*        for(Object obj : createdObjects) {
            if(obj == null)
                System.out.println("Object is null");
            else
                System.out.println(obj.toString());
        }
//*/

        //debug
        //System.out.println(getArrayListPrintOut());

        ArrayList<ArrayList<Integer>> references = connectReferences(objectArray);

        objects = instantiateObjectsViaReferences(createdObjects, references);


        return objects;
    }

    private static ArrayList<Object> instantiateObjectsViaReferences(Object[] allObjects, ArrayList<ArrayList<Integer>> references) {
        ArrayList<Object> result = new ArrayList<>();

        for(int i = 0; i < allObjects.length; i++) {
            if(allObjects[i] != null) {
                String className = allObjects[i].getClass().getSimpleName();
                System.out.println(allObjects[i].getClass().getSimpleName());
                //variable order in object is order of references in references arraylist
                if(className.equals("ArrayPrimitives")) {

                }
                else if(className.equals("ArrayReferences")) {

                }
                else if(className.equals("ComplexWithReferences")) {

                }
                else if(className.equals("InstanceJavaCollection")) {

                }
                else if(className.equals("int[]")) {

                }
                else if(className.equals("ArrayList")) {
                    
                }
            }
            else {  //caused by elements of ArrayList
                System.out.println("null class");
            }
        }

        return result;
    }

    /**
     * Connects all the reference objects by reference ID to the ID object it belongs to
     */
    private static ArrayList<ArrayList<Integer>> connectReferences(JsonArray jsonArray) {
        ArrayList<ArrayList<Integer>> references = new ArrayList<>();     //[ID][references for that ID]
        JsonReader jsonReader;
        JsonObject jsonObject;

        for(int i = 0; i < jsonArray.size(); i++) {
            references.add(new ArrayList<>());
        }

        for(int i = 0; i < jsonArray.size(); i++) {
            jsonReader = Json.createReader(new StringReader(jsonArray.getString(i)));
            jsonObject = jsonReader.readObject();

            int id = jsonObject.getInt("id");
            JsonArray fieldEntries = null;

            if(jsonObject.getString("type").equals("object")) {
                //System.out.println("[ID: " + id + "] [Type (Object): " + jsonObject.getString("type") + "]");
                fieldEntries = jsonObject.getJsonArray("fields");
            }
            else if (jsonObject.getString("type").equals("array")) {
                //System.out.println("[ID: " + id + "] [Type (Array): " + jsonObject.getString("type") + "]");
                fieldEntries = jsonObject.getJsonArray("entries");
            }

            for(int j = 0; j < fieldEntries.size(); j++) {
                JsonObject singleFieldEntry = fieldEntries.getJsonObject(j);
                if(singleFieldEntry.containsKey("reference")) {
                    //System.out.println("[ID: " + id + "] contains a reference!");
                    Integer referenceValue;
                    try {
                        referenceValue = Integer.parseInt(String.valueOf(singleFieldEntry.get("reference")));
                    } catch (NumberFormatException e) {
                        referenceValue = -1;
                    }
                    //System.out.println("[ID: " + id + "] contains [Reference: " + referenceValue + "]!");
                    references.get(id).add(referenceValue);
                }
            }
        }
        //debugConnectedPrintout(references);
        return references;
    }

    public static void debugConnectedPrintout(ArrayList<ArrayList<Integer>> references) {
        System.out.println("PRINTING REFERENCES LIST DEBUG:\n");
        for(int ID = 0; ID < references.size(); ID++) {
            for(Integer ref : references.get(ID)) {
                System.out.println("[ID: " + ID + "] has [Reference: " + ref + "]");
            }
        }
    }

    /**
     * Checks the nums list for duplicate of target
     * @param nums
     * @param target
     * @return
     */
    public static boolean checkForDuplicates(ArrayList<Integer> nums, int target) {
        int occurances = 0;
        for(Integer i : nums) {
            if (i == target)
                occurances++;
        }

        if(occurances < 2)
            return false;
        else
            return true;
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
        Object result = null;
        String className = jsonObject.getString("class");
        className = className.replace("program.", "");


        System.out.println(jsonObject.toString().replace("\\", ""));
        JsonArray fields = null;
        if(!className.equals("[I"))
            fields = jsonObject.getJsonArray("fields");

        if(className.matches("AllPrimitive")) { //object 1
            result = new AllPrimitive(deserializeJsonAllPrimitive(fields));
        }
        else if(className.matches("ArrayPrimitives")) { //object 3
            result = new ArrayPrimitives(deserializeJsonArrayPrimitives());
        }
        else if(className.matches("ArrayReferences")) { //object 4
            result = new ArrayReferences(deserializeJsonArrayReferences(fields));
        }
        else if(className.matches("ComplexWithReferences")) {    //object 2
            result = new ComplexWithReferences(deserializeJsonComplexWithReferences());
        }
        else if(className.matches("InstanceJavaCollection")) {  //object 5
            result = new InstanceJavaCollection(deserializeJsonInstanceJavaCollection());
        }
        else if(className.equals("[I")) {   //int array
            JsonArray entries = jsonObject.getJsonArray("entries");
            result = deserializeJsonIntArray(entries);
        }
        else if(className.equals("java.util.ArrayList")) {
            //might have to do something fancy for this, maybe not TODO
            result = new ArrayList<>();
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

    private static ArrayPrimitives deserializeJsonArrayPrimitives () {
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

    private static ComplexWithReferences deserializeJsonComplexWithReferences () {
        ComplexWithReferences result = new ComplexWithReferences();

        AllPrimitive obj1 = new AllPrimitive();
        AllPrimitive obj2 = new AllPrimitive();

        ArrayPrimitives arr1 = new ArrayPrimitives();

        result = new ComplexWithReferences(obj1, obj2, arr1);

        return result;
    }

    private static ArrayReferences deserializeJsonArrayReferences (JsonArray jsonFields) {
        ArrayReferences result = null;

        Object[] objArr = new Object[jsonFields.size()];

        for(Object obj : objArr)
            obj = new Object();

        result = new ArrayReferences(objArr);
        return result;
    }

    private static InstanceJavaCollection deserializeJsonInstanceJavaCollection () {
        InstanceJavaCollection result = null;
        result = new InstanceJavaCollection();
        return result;
    }
}