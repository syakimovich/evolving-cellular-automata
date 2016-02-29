package org.yakimovich.evolca.measures;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;

/**
 * Gini coefficient of inequality of the number of cells in different states.
 */
public class Gini implements Measure {

    @Override
    public String getName() {
        return "Gini";
    }

    @Override
    public double getValue(Universe u) {
        int numberOfStates = u.getNumberOfStates();
        double[] numberArray = new double[numberOfStates];
        for(int i = 0; i < numberOfStates; i++){
            numberArray[i] = 0;
        }

        char[][] cells = u.getCells();
        for(int i = 0; i < u.getHeight(); i++) {
            for (int j = 0; j < u.getWidth(); j++) {
                numberArray[cells[i][j]]++;
            }
        }
        double absDiffSum = 0;
        for(int i = 0; i < numberOfStates; i++){
            for(int j = 0; j < numberOfStates; j++){
                absDiffSum += Math.abs(numberArray[i] - numberArray[j]);
            }
        }
        return 100.0 * absDiffSum / (double) ((numberOfStates - 1.0) * u.getWidth() * u.getHeight() * 2.0);
    }
}
