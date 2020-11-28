package program;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import javax.json.*;

public class Serializer {
    public static String serializeObject(Object source, IdentityHashMap hashMap, ArrayList<String> jsonStrings) {
        return jsonObject(source, hashMap, jsonStrings);
    }

    private static String jsonObject(Object src, IdentityHashMap hashMap, ArrayList<String> jsonStrings) {
        String result = "";

        if (!hashMap.containsValue(src.hashCode())) {
            //json object and json stringwriter setup and start writing stuff in
            JsonObject jsonObject;
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("class", src.getClass().getName());


            //add this object to the hashmap
            int hashID = hashMap.size();
            hashMap.put(hashID, src.hashCode());
            jsonObjectBuilder.add("id", hashID);

            //type check
            if (!src.getClass().isArray()) { //object type
                jsonObjectBuilder.add("type", "object");

                Field[] fields = src.getClass().getDeclaredFields();

                JsonArrayBuilder jsonFieldArray = Json.createArrayBuilder();
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        if (field.getType().isPrimitive()) {
                            jsonFieldArray.add(Json.createObjectBuilder()
                                    .add("name", field.getName())
                                    .add("declaringclass", field.getDeclaringClass().getName())
                                    .add("value", field.get(src).toString()));
                        }
                        else if (field.get(src) == null) {   //references a null
                            jsonFieldArray.add(Json.createObjectBuilder()
                                    .add("name", field.getName())
                                    .add("declaringclass", field.getDeclaringClass().getName())
                                    .add("reference", "null"));
                        }
                        else {  //non null reference
                            //serialize the parent
                            Object fieldInstance = field.get(src);
                            jsonStrings.add(serializeObject(fieldInstance, hashMap, jsonStrings));

                            int parentID = (int) getKeyByValue(hashMap, fieldInstance.hashCode());

                            jsonFieldArray.add(Json.createObjectBuilder()
                                    .add("name", field.getName())
                                    .add("declaringclass", field.getDeclaringClass().getName())
                                    .add("reference", parentID));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                jsonObjectBuilder.add("fields", jsonFieldArray.build());

            }
            else {  //array type
                //name and id already added to json
                jsonObjectBuilder.add("type", "array");
                jsonObjectBuilder.add("length", Array.getLength(src));

                JsonArrayBuilder jsonFieldArray = Json.createArrayBuilder();

                if(src.getClass().getComponentType().isPrimitive()) {   //array of primitives
                    //make an array for the entries and their value
                    for(int i = 0; i < Array.getLength(src); i++) {
                        jsonFieldArray.add(Json.createObjectBuilder()
                                .add("value", Array.get(src, i).toString()));
                    }
                    jsonObjectBuilder.add("entries", jsonFieldArray.build());
                }
                else {  //array of objects, further serialization needed
                    //still have to add entries
                    for(int i = 0; i < Array.getLength(src); i++) {
                        if(Array.get(src, i) != null) {
                            jsonFieldArray.add(Json.createObjectBuilder()
                            .add("reference", hashMap.size()));
                            jsonStrings.add(serializeObject(Array.get(src, i), hashMap, jsonStrings));
                        }
                        else {  //null reference, don't serialize
                            jsonFieldArray.add(Json.createObjectBuilder()
                                    .add("reference", "null"));
                        }
                    }
                    jsonObjectBuilder.add("entries", jsonFieldArray.build());
                }
            }
            //build and write jsonObject to the stringWriter
            jsonObject = jsonObjectBuilder.build();
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = Json.createWriter(stringWriter);
            jsonWriter.writeObject(jsonObject);
            result = stringWriter.toString();
        }

        return result;
    }

    //find the hashID given some value
    //since my map is 1 to 1, the first matching result is the only matching result
    //code taken from here: https://stackoverflow.com/a/2904266
    private static <T, E> T getKeyByValue(Map<T, E> hashMap, E hashValue) {
        for(Map.Entry<T, E> entry : hashMap.entrySet()) {
            if(Objects.equals(hashValue, entry.getValue()))
                return entry.getKey();
        }
        return null;
    }

}