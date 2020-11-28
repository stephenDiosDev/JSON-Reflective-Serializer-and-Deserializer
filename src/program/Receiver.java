package program;

import javax.json.*;
import java.io.DataInputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Receiver{
    private JsonObject jsonObject;

    public static void main(String[] args) {
        setupClientConnection();
    }

    public static void setupClientConnection() {
        String serverInput = "";
        int port = 7777;
        String host = "192.168.1.73";
        try {
            Socket socket = new Socket(host, port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            System.out.println("Client connected! Waiting for data...");

            //put code to receive object here from server
            serverInput = input.readUTF();       //should be fully json ready


            JsonReader jsonReader = Json.createReader(new StringReader(serverInput));
            JsonObject jsonObject = jsonReader.readObject();
            System.out.println("JSON Object received! Closing connection...");
            jsonReader.close();
            //debug
            //System.out.println(jsonObject.toString().replace("\\", ""));
            //System.out.println(serverInput.replace("\\", ""));

            input.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Unable to create client socket");
            e.printStackTrace();
        }

        //finished receiving the json string
        ArrayList<Object> objectList = Deserializer.deserializeObject(serverInput);
        System.out.println("Printing visualizer output below...\n");
        Visualizer.printDeserializerOutput(objectList);
    }
}