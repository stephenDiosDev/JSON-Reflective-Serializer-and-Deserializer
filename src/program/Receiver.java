package program;

import javax.json.*;
import java.io.DataInputStream;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;

public class Receiver extends Thread{
    private String serverInput;

    private JsonObject jsonObject;

    public void run() {
        this.setupClientConnection();
    }

    public void setupClientConnection() {
        int port = 7777;

        try {
            Socket socket = new Socket("localhost", port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            System.out.println("Client connected on separate thread! Waiting for data...");

            //put code to receive object here from server
            serverInput = input.readUTF();       //should be fully json ready


            JsonReader jsonReader = Json.createReader(new StringReader(serverInput));
            JsonObject jsonObject = jsonReader.readObject();
            System.out.println("JSON Object received! Closing connection...");
            jsonReader.close();

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