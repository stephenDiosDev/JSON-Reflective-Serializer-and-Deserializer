package program;

import java.util.ArrayList;
import java.util.Scanner;

public class Sender {
    private Serializer serializer;
    private Visualizer visualizer;

    private ArrayList<Object> objects;  //holds the users declared objects

    public Sender() {
        this.driver();
    }

    private void driver() {
        Scanner in = new Scanner(System.in);
        serializer = new Serializer();
        visualizer = new Visualizer();
        objects = visualizer.userSelectionMenu(in);

        //networking code
    }
}