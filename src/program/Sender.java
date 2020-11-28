package program;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.IdentityHashMap;

public class Sender {
    private Visualizer visualizer;

    private ArrayList<Object> objects;  //holds the users declared objects

    private JsonObject jsonObject;  //holds the complete json objects array

    public Sender(boolean run) {
        visualizer = new Visualizer();

        if(run) {
            this.driver();
        }
    }

    public void driver() {
        objects = visualizer.userSelectionMenu();
        IdentityHashMap hashMap = new IdentityHashMap();
        ArrayList<String> jsonStrings = new ArrayList<>();

        ArrayList<String> outputStrings = new ArrayList<>();


        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonFieldArray = Json.createArrayBuilder();

        for(Object ob : objects) {
            //jsonFieldArray.add(Serializer.serializeObject(ob, hashMap, jsonStrings));
            jsonStrings.add(Serializer.serializeObject(ob, hashMap, jsonStrings));
        }

        //remove any empty strings
        for(String s : jsonStrings) {
            if(!s.equals(""))
                outputStrings.add(s);
        }

        //add the actual filled json strings to the objects array
        for(String st : outputStrings) {
            jsonFieldArray.add(st);
        }
        jsonObjectBuilder.add("objects", jsonFieldArray.build());
        jsonObject = jsonObjectBuilder.build();
        //System.out.println(jsonObject.toString());    //debug, but not pretty to look at
    }

    /**
     * Sends the json object to the receiver
     */
    public void sendJson() {
        /*
        Open server connection
        Wait until the client (receiver) connects
        Send the jsonObject (which contains EVERYTHING)
        close connection
         */

        int port = 8888;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server socket open, awaiting connections");
            Socket socket = serverSocket.accept();
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());


            //writes output but also removes the escape slashes that occur during toString()
            //output.writeUTF(jsonObject.toString().replace("\\", ""));
            output.writeUTF(jsonObject.toString());
            output.flush();
            output.close();
            serverSocket.close();
            System.out.println("Server connection closing...");
        } catch (IOException e) {
            System.out.println("Unable to create new ServerSocket");
            e.printStackTrace();
        }

    }

    public String toString() {
        return visualizer.toString();
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }
}