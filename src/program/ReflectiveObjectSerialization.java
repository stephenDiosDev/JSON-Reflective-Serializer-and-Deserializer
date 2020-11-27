package program;

public class ReflectiveObjectSerialization {
    private static Sender sender;
    private static Receiver receiver;

    public static void main(String[] args) {
        sender = new Sender(true);
        //setup client connection in receiver
        receiver = new Receiver();
        Thread clientThread = receiver;
        clientThread.start();
        //call sender.run
        sender.sendJson();

        //continue operations on receiver
        //visualizer
    }
}
