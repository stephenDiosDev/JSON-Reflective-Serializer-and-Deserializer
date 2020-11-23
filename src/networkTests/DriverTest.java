package networkTests;

public class DriverTest {
    public static void main(String[] args) {
        ServerTest server = new ServerTest();

        try {
            ClientTest client = new ClientTest();
            Thread t = client;
            t.start();
            server.run();
        } catch (Exception e) {

        }
    }
}
