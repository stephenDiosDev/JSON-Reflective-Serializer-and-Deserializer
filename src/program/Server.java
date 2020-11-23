package program;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void receiveAndPrint() {
        try {
            int port = 4444;
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            Client.connectAndSend("Hello, World!");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object object = inputStream.readObject();
            socket.close();
            System.out.println("SERVER RECEIVED: " + object.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
