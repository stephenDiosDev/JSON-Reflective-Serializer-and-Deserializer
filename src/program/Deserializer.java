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
        System.out.println(objectArray.toString().replace("\\", ""));
        System.out.println("Size of object array: " + objectArray.size());

        //the object ID is the index in this array for that object
        //Ex: object with ID = 0 is at index 0
        createdObjects = new Object[objectArray.size()];

        //send each individual json object for deserialization
        for(int i = 0; i < objectArray.size(); i++) {
            jsonReader = Json.createReader(new StringReader(objectArray.getString(i)));
            jsonObject = jsonReader.readObject();
            //System.out.println(jsonObject.toString().replace("\\", ""));

            int id = jsonObject.getInt("id");
            //System.out.println("Object ID: " + id);

            createdObjects[id] = deserializeJsonClass(jsonObject);
        }

        //debug
        //for(Object obj : createdObjects)
            //System.out.println(obj.toString());

        //debug
        System.out.println(getArrayListPrintOut());

        connectReferences(objectArray);

        return objects;
    }

    /**
     * Connects all the reference objects by reference ID to the ID object it belongs to
     */
    private static void connectReferences(JsonArray jsonArray) {
        ArrayList<ArrayList<Integer>> references = new ArrayList<>();     //[ID][references for that ID]
        JsonReader jsonReader;
        JsonObject jsonObject;
        for(int i = 0; i < createdObjects.length; i++) {
            int ID = i;
            references.add(new ArrayList<Integer>());

            for(int j = 0; j < jsonArray.size(); j++) {
                //grabs the {"class":...} string
                jsonReader = Json.createReader(new StringReader(jsonArray.getString(i)));
                jsonObject = jsonReader.readObject();

                //find the ID in the jsonArray so we can read any references
                if(ID == jsonObject.getInt("id")) {
                    //now we must figure out if this is an object or array
                    if(jsonObject.getString("type").equals("object")) { //have fields
                        JsonArray fields = jsonObject.getJsonArray("fields");
                        //go through all fields and find any references
                        for(int k = 0; k < fields.size(); k++) {
                            if(fields.getJsonObject(k).containsKey("reference")) {
                                int fieldID = fields.getJsonObject(k).getInt("reference");
                                if (!checkForDuplicates(references.get(ID), fieldID))   //bug in this, redo TODO
                                    references.get(ID).add(fields.getJsonObject(k).getInt("reference"));
                            }
                        }
                    }
                    else if(jsonObject.getString("type").equals("array")) { //have entries

                    }


                }
                //once ID is found, it will either have a reference block or it wont IN THE FIELDS/ENTRIES
                    //if the field has a value; skip. If the field has a null reference; set -2 in the references
                    //if the field has a non null reference; do references.get(i).add(reference number)
            }
            for(int g = 0; g < references.get(ID).size(); g++)
                System.out.println("[ID: " + ID + "] has [Reference: " + references.get(ID).get(g) + "]");
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


        //System.out.println(jsonObject.toString().replace("\\", ""));
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
        else if(className.matches("ComplexWithReference")) {    //object 2
            result = new ComplexWithReferences(deserializeJsonComplexWithReferences());
        }
        else if(className.matches("InstanceJavaCollection")) {  //object 5
            result = new InstanceJavaCollection(deserializeJsonInstanceJavaCollection());
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
        ComplexWithReferences result = null;

        AllPrimitive obj1 = new AllPrimitive(null);
        AllPrimitive obj2 = new AllPrimitive(null);

        ArrayPrimitives arr1 = new ArrayPrimitives((int[]) null);

        result = new ComplexWithReferences(obj1, obj2, arr1);

        return result;
    }

    private static ArrayReferences deserializeJsonArrayReferences (JsonArray jsonFields) {
        ArrayReferences result = null;

        Object[] objArr = new Object[jsonFields.size()];

        for(Object obj : objArr)
            obj = null;

        result = new ArrayReferences(objArr);
        return result;
    }

    private static InstanceJavaCollection deserializeJsonInstanceJavaCollection () {
        InstanceJavaCollection result = null;
        result = new InstanceJavaCollection(null, null, null, null);
        return result;
    }
}