package program;

import java.util.ArrayList;

public class Sender {
    private Serializer serializer;
    private Visualizer visualizer;

    private ArrayList<Object> objects;  //holds the users declared objects

    public Sender(boolean run) {
        serializer = new Serializer();
        visualizer = new Visualizer();

        if(run)
            this.driver();
    }

    public void driver() {
        objects = visualizer.userSelectionMenu();
    }

    public String toString() {
        return visualizer.toString();
    }
}