package program;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import javax.json.*;

public class Serializer {
    /* TODO
    Create json object and store the object
     */

    public static String serializeObject(Object source) {
        IdentityHashMap hashMap = new IdentityHashMap();
        int depth = 1;
        String result = "";

        //turn this into a loop for all objects so it doesnt print the above multiple times
        result += jsonObject(source, depth + 1, hashMap);

        return result;
    }

    private static String tabs(int depth) {
        String tabs = "";

        for(int i = 0; i < depth; i++) {
            tabs += "\t";
        }

        return tabs;
    }

    private static String jsonObject(Object src, int depth, IdentityHashMap hashMap) {
        String tabs = tabs(depth);
        String result = "\n" + tabs + "{\n";

        JsonObject jsonObject;
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("class", src.getClass().getName());
        jsonObjectBuilder.add("id", 0);

        
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stringWriter);

        jsonWriter.writeObject(jsonObject.set);

        //do class, id, type, fields
        String tabInfo = tabs + "\t";
        result += tabInfo;  //setup new tab amount

        //id check
        if(!hashMap.containsValue(src.hashCode())) {
            //class name
            result += "\"class\": \"" + src.getClass().getName() + "\",\n";

            result += tabInfo;

            int id = hashMap.size();
            hashMap.put(id, src.hashCode());
            result += "\"id\": \"" + id + "\",\n";


            if(src.getClass().isArray()) {
                String type = "array";
            }
            else {
                String type = "object";

                result += tabInfo + "\"type\": \"" + type + "\",\n";
                result += tabInfo + "\"fields\": [\n";

                //fields call
                Field[] fields = src.getClass().getDeclaredFields();

                for(int i = 0; i < fields.length; i++) {
                    result += tabInfo + "\t{\n";
                    result += jsonField(src, fields[i], depth + 1);
                    result += tabInfo + "\t}";
                    if(i != fields.length - 1)  //last element dont put comma
                        result += ",";
                    result += "\n";
                }
            }


            result += tabInfo + "]\n";




        }


        result += tabs + "}\n";
        return result;
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