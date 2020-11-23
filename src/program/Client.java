package program;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This entire class was taken from old TA slides. These slides were originally
 * shown to us by the course TA.
 * Link: http://pages.cpsc.ucalgary.ca/~seyedarshia.hosseini/courses/cpsc501/slides/Tut13-A3.pdf
 */
public class Client {
    public static void connectAndSend(String str) {
        String serverAddress = "localhost";
        int serverPort = 4444;
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ObjectOutputStream outputStream = new ObjectOutputStream((socket.getOutputStream()));
            outputStream.writeObject(str);
            outputStream.flush();
            socket.close();
            System.out.println("CLIENT SENT: " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
