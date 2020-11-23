package networkTests;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public void run() {
        int port = 7777;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            System.out.println(input.readUTF());

            output.writeUTF("Hello client!");
            output.flush();

            input.close();
            output.close();

        } catch (IOException e) {
            System.out.println("Unable to create new ServerSocket");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerTest server = new ServerTest();
        server.run();
    }
}
