package program;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;

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

            //hard coded here since I know none of my objects are of type array
            String type = "object";

            result += tabInfo + "\"type\": \"" + type + "\",\n";
            result += tabInfo + "\"fields\": [\n";

            //fields call
            Field[] fields = src.getClass().getDeclaredFields();

            for(int i = 0; i < fields.length; i++) {
                result += tabInfo + "\t{\n";
                result += jsonField(fields[i], depth + 1);
                result += tabInfo + "\t}";
                if(i != fields.length - 1)  //last element dont put comma
                    result += ",";
                result += "\n";
            }

            result += tabInfo + "]\n";




        }


        result += tabs + "}\n";
        return result;
    }

    private static String jsonField(Field field, int depth) {
        String tabs = tabs(depth);  //tab level for field elements
        String tabInfo = tabs + "\t";
        //array and any othe reference will need to have its own "object" entry
        String result = tabs + "{\n" + tabInfo;
        result += "\"name\": \"" + field.getName() + "\",\n";
        result += tabInfo + "\"declaring class\": \"" + field.getDeclaringClass() + "\",\n";
        //check if it is value or reference
        if(field.getType().isPrimitive()) { //primitive, use value

        }
        else {  //reference

        }


        return result;
    }

}