package program;

import java.util.ArrayList;

public class Sender {
    private Visualizer visualizer;

    private ArrayList<Object> objects;  //holds the users declared objects

    public Sender(boolean run) {
        visualizer = new Visualizer();

        if(run)
            this.driver();
    }

    public void driver() {
        objects = visualizer.userSelectionMenu();

        //figure out how this will translate into the hashmap thing
        //maybe move hashmap out here and pass it in?
        for(Object ob : objects) {
            System.out.print(Serializer.serializeObject(ob));
        }
    }

    public String toString() {
        return visualizer.toString();
    }
}