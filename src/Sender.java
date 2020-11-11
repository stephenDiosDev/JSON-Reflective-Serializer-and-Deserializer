import java.util.ArrayList;

public class Sender {
    private Serializer serializer;
    private Visualizer visualizer;

    private ArrayList<Object> objects;  //holds the users declared objects

    public Sender() {
        this.driver();
    }

    private void driver() {
        serializer = new Serializer();
        visualizer = new Visualizer();
        objects = visualizer.userSelectionMenu();

        //networking code
    }
}