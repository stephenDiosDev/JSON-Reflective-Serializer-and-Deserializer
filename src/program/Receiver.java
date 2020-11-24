package program;

import javax.json.*;
import java.io.DataInputStream;
import java.io.StringReader;
import java.net.Socket;

public class Receiver extends Thread{
    private Deserializer deserializer;

    private JsonObject jsonObject;

    public void run() {
        this.setupClientConnection();
    }

    public void setupClientConnection() {
        int port = 7777;

        try {
            Socket socket = new Socket("localhost", port);
            DataInputStream input = new DataInputStream(socket.getInputStream());

            //put code to receive object here from server
            String stringInput = input.readUTF();       //should be fully json ready

            JsonReader jsonReader = Json.createReader(new StringReader(stringInput));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            //debug
            System.out.println(jsonObject.toString().replace("\\", ""));

            input.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Unable to create client socket");
            e.printStackTrace();
        }

    }
}