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

            createdObjects[id] = deserializeJsonClass(jsonObject);
        }

        ArrayList<ArrayList<Integer>> references = connectReferences(objectArray);

        objects = instantiateObjectsViaReferences(createdObjects, references);


        return objects;
    }

    private static ArrayList<Object> instantiateObjectsViaReferences(Object[] allObjects, ArrayList<ArrayList<Integer>> references) {
        ArrayList<Object> result = new ArrayList<>();

        for(int i = 0; i < allObjects.length; i++) {
            if(allObjects[i] != null) {
                String className = allObjects[i].getClass().getSimpleName();

                //ArrayList is [ID][reference value]
                if(className.equals("ArrayPrimitives")) {
                    allObjects[i] = stitchReferences(i, references);
                    result.add(allObjects[i]);
                }
                else if(className.equals("ArrayReferences")) {
                    allObjects[i] = stitchReferences(i, references);
                    result.add(allObjects[i]);
                }
                else if(className.equals("ComplexWithReferences")) {
                    allObjects[i] = stitchReferences(i, references);

                    result.add(allObjects[i]);
                }
                else if(className.equals("InstanceJavaCollection")) {
                    allObjects[i] = stitchReferences(i, references);
                    result.add(allObjects[i]);
                }
                else if(className.equals("AllPrimitive")) {
                    //since it only holds primitives, we don't need to connect any references
                    allObjects[i] = stitchReferences(i, references);
                    result.add(allObjects[i]);
                }
            }
        }
        return result;
    }

    public static String debugOutput(ArrayList<Object> result) {
        String outputResult = "";
        outputResult += "DEBUG*****DEBUG*****DEBUG\n";
        for(Object obj : result) {
            outputResult += obj.toString() + "\n";
        }
        outputResult += "DEBUG*****DEBUG*****DEBUG\n";

        return outputResult;
    }

    /**
     * Stitches the object with id based on its references to other objects
     * @param id
     * @param references
     * @return
     */
    private static Object stitchReferences(int id, ArrayList<ArrayList<Integer>> references) {
        String className = createdObjects[id].getClass().getSimpleName();

        if(className.equals("ArrayPrimitives")) {   //only has int[]
            int referenceID = references.get(id).get(0);
            ((ArrayPrimitives)createdObjects[id]).setMyArr((int[])createdObjects[referenceID]);
        }
        else if(className.equals("ArrayReferences")) {  //only Object[]
            int referenceID = references.get(id).get(0);
            ((ArrayReferences)createdObjects[id]).setMyArr((Object[])stitchReferences(referenceID, references));
        }
        else if(className.equals("ComplexWithReferences")) {    //AllPrimitive obj1, AllPrimitive obj2, ArrayPrimitive arr1
            int obj1RefID = references.get(id).get(0);
            int obj2RefID = references.get(id).get(1);
            int arr1RefID = references.get(id).get(2);

            ((ComplexWithReferences)createdObjects[id]).setObj1((AllPrimitive)stitchReferences(obj1RefID, references));
            ((ComplexWithReferences)createdObjects[id]).setObj2((AllPrimitive)stitchReferences(obj2RefID, references));
            ((ComplexWithReferences)createdObjects[id]).setArr1((ArrayPrimitives) stitchReferences(arr1RefID, references));

        }
        else if(className.equals("InstanceJavaCollection")) {
            int listRefID = references.get(id).get(0);
            int aRefID = references.get(id).get(1);
            int bRefID = references.get(id).get(2);
            int cRefID = references.get(id).get(3);
            int arrRefID = references.get(id).get(4);

            ((InstanceJavaCollection)createdObjects[id]).setA((AllPrimitive)stitchReferences(aRefID, references));
            ((InstanceJavaCollection)createdObjects[id]).setB((AllPrimitive)stitchReferences(bRefID, references));
            ((InstanceJavaCollection)createdObjects[id]).setC((AllPrimitive)stitchReferences(cRefID, references));
            ((InstanceJavaCollection)createdObjects[id]).setArr((ArrayPrimitives) stitchReferences(arrRefID, references));
            //since arrayList has private components, I cannot stitch that back together
            ArrayList<Object> newList = new ArrayList<>();
            newList.add(((InstanceJavaCollection)createdObjects[id]).getA());
            newList.add(((InstanceJavaCollection)createdObjects[id]).getB());
            newList.add(((InstanceJavaCollection)createdObjects[id]).getC());
            newList.add(((InstanceJavaCollection)createdObjects[id]).getArr());
            ((InstanceJavaCollection)createdObjects[id]).setList(newList);

        }
        else if(className.equals("Object[]")) {
            ArrayList<Integer> referencesList = references.get(id);
            for(int i = 0; i < referencesList.size(); i++) {
                int referenceID = referencesList.get(i);
                ((Object[])createdObjects[id])[i] = stitchReferences(referenceID, references);
            }
        }

        return createdObjects[id];
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
                fieldEntries = jsonObject.getJsonArray("fields");
            }
            else if (jsonObject.getString("type").equals("array")) {
                fieldEntries = jsonObject.getJsonArray("entries");
            }

            for(int j = 0; j < fieldEntries.size(); j++) {
                JsonObject singleFieldEntry = fieldEntries.getJsonObject(j);
                if(singleFieldEntry.containsKey("reference")) {
                    Integer referenceValue;
                    try {
                        referenceValue = Integer.parseInt(String.valueOf(singleFieldEntry.get("reference")));
                    } catch (NumberFormatException e) {
                        referenceValue = -1;
                    }
                    references.get(id).add(referenceValue);
                }
            }
        }
        return references;
    }

    public static void debugConnectedPrintout(ArrayList<ArrayList<Integer>> references) {
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
        else if(className.equals("[Ljava.lang.Object;")) {
            JsonArray entries = jsonObject.getJsonArray("entries");
            result = deserializeJsonObjectArray(entries);
        }
        else if(className.equals("java.util.ArrayList")) {
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

    private static Object[] deserializeJsonObjectArray(JsonArray array) {
        Object[] result = new Object[array.size()];
        for(int i = 0; i < result.length; i++) {
            result [i] = new Object();
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

        for(int i = 0; i < objArr.length; i++) {
            objArr[i] = new Object();
        }

        result = new ArrayReferences(objArr);
        return result;
    }

    private static InstanceJavaCollection deserializeJsonInstanceJavaCollection () {
        InstanceJavaCollection result = null;
        result = new InstanceJavaCollection();
        return result;
    }
}