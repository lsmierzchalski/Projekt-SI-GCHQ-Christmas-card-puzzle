package sample;

import java.util.ArrayList;

public class PuzzleData {
    ArrayList<ArrayList<Integer>> rowConstraints, columnConstraints,knownPosition;

    PuzzleData(ArrayList<ArrayList<Integer>> r, ArrayList<ArrayList<Integer>> c, ArrayList<ArrayList<Integer>> kP){
        rowConstraints = r;
        columnConstraints = c;
        knownPosition = kP;
    }

}
