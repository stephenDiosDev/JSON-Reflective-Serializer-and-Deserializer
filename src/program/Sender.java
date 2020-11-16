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
        //maybe move hashmap out here and pass it in? Probably the best solution
        String result = "{\n\t\"objects\": [";
        for(Object ob : objects) {
            result += Serializer.serializeObject(ob);
        }

        //TODO: fix slight formatting issue with newlines and commas between separate objects in above loop

        result += "\t]\n}";

        System.out.println(result);
    }

    public String toString() {
        return visualizer.toString();
    }
}