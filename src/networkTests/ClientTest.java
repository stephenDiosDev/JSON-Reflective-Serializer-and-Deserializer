package networkTests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTest extends Thread{
    public void run() {
        int port = 7777;
        try {
            Socket socket = new Socket("localhost", port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            output.writeUTF("Hello server!");
            output.flush();

            System.out.println(input.readUTF());

            input.close();
            output.close();

        } catch (Exception e) {
            System.out.println("Unable to create client socket");
        }
    }

    public static void main(String[] args) {
        ClientTest client = new ClientTest();
        client.run();
    }
}
