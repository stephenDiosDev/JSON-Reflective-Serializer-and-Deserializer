package program;

import java.util.IdentityHashMap;

public class Serializer {
    /* TODO
    Create json object and store the object
     */

    public static String serializeObject(Object source) {
        IdentityHashMap hashMap = new IdentityHashMap();
        int depth = 1;
        String result = "{\n\t\"objects\": [";

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
        String result = tabs + "{\n";
        result += tabs + "\t" +;




        result += tabs + "},\n";
        return result;
    }

}