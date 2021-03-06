package org.yakimovich.evolca.example.universe5;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.Universe5;
import org.yakimovich.evolca.measures.AvgNeighborColorIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.measures.NonZeroPercentage;
import org.yakimovich.evolca.ui.UniversesWindow;
import org.yakimovich.evolca.utils.ArrayUtils;
import org.yakimovich.evolca.utils.InitialStateGenerator;

/**
 * Randomly generates 10 Universe5 universes. You can specify parameters to the main function to replace defaults:
 * args[0] - number of states (currently 4 or 8)
 * args[1] - initial state: "random", "point" or "rectangular" (3 colored rectangles)
 */
public class RandomExample {
    public static void main(String[] args) throws Exception {
        int size = 100;
        char numberOfStates = 4;
        double[] thresholds;
        double[] thresholds4 = {0.8, 0.9, 0.95};
        double[] thresholds8 = {0.825, 0.850, 0.875, 0.9, 0.925, 0.95, 0.975};
        boolean isCircular = true;
        String initialState = "";

        if(args != null && args.length >= 1){
            numberOfStates = (char) Integer.parseInt(args[0]);

            if(args.length >= 2){
                initialState = args[1];
            }
        }

        if(numberOfStates == 4){
            thresholds = thresholds4;
        } else if(numberOfStates == 8){
            thresholds = thresholds8;
        } else {
            throw new Exception("Number of states must be 4 or 8.");
        }

        Universe[] universes = new Universe[10];
        for(int i = 0; i < 10; i++){
            char[][] initialCells;
            if(initialState.equalsIgnoreCase("point")){
                initialCells = InitialStateGenerator.create1Point(size, size, (char)1);
            } else if(initialState.equalsIgnoreCase("rectangular")){
                initialCells = InitialStateGenerator.create3ColorsRectangular(size, size);
            } else {
                initialCells = InitialStateGenerator.createRandom(size, size, numberOfStates);
            }

            char[][][][][] matrix = ArrayUtils.createRandomMatrix5D(thresholds);
            universes[i] = new Universe5(initialCells, matrix, numberOfStates, isCircular);
        }

        UniversesWindow mainWindow = new UniversesWindow(universes, "Random example");
        mainWindow.addMeasure(new AvgNeighborColorIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.addMeasure(new NonZeroPercentage());
    }
}
