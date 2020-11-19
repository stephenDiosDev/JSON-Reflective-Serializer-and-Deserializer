package program;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import javax.json.*;

public class Serializer {
    /* TODO
    Create json object and store the object
     */

    public static String serializeObject(Object source, IdentityHashMap hashMap, ArrayList<String> jsonStrings) {
        return jsonObject(source, hashMap, jsonStrings);
    }

    private static String jsonObject(Object src, IdentityHashMap hashMap, ArrayList<String> jsonStrings) {
        if(hashMap.containsValue(src.hashCode())) {
            //json object and json stringwriter setup and start writing stuff in
        }
        else {  //duplicate
            return "";
        }
    }

    private static String jsonField(Object source, Field field, int depth) {
        String tabs = tabs(depth);  //tab level for field elements
        String tabInfo = tabs + "\t" + "\t";
        //array and any other reference will need to have its own "object" entry
        String result = tabInfo;
        result += "\"name\": \"" + field.getName() + "\",\n";
        result += tabInfo + "\"declaring class\": \"" + field.getDeclaringClass().getName() + "\",\n";
        //check if it is value or reference
        if(field.getType().isPrimitive()) { //primitive, use value
            field.setAccessible(true);
            try{
                result += tabInfo + "\"value\": \"" + field.get(source).toString() + "\"\n";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        else {  //reference

        }


        return result;
    }

}