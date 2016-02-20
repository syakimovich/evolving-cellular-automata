package org.yakimovich.evolca.measures;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.ArrayUtils;

/**
 * How different are average neighbors for different states. For each state there are only 4 neighbors;
 */
public class AvgNeighborColourIndex5 implements Measure {

    final public char OUT_VALUE = 0;
    final public double ZERO_AVOID = 0.000000001;

    @Override
    public String getName() {
        return "ANCI";
    }

    @Override
    public double getValue(Universe u) {
        int numberOfStates = u.getNumberOfStates();
        double totalSum = 0; // Total number of neighbors pairs.
        double[] numberArray = new double[numberOfStates]; // Number of neighbors pairs for each state as first.
        for(int i = 0; i < numberOfStates; i++){
            numberArray[i] = 0;
        }

        // Number of neighbors pairs for each state as first and second.
        double[][] neighborsArray = new double[numberOfStates][numberOfStates];
        for(int i = 0; i < numberOfStates; i++){
            for(int j = 0; j < numberOfStates; j++){
                neighborsArray[i][j] = 0;
            }
        }


        char[][] cells = u.getCells();
        for(int i = 0; i < u.getHeight(); i++){
            for(int j = 0; j < u.getWidth(); j++){
                int p = i;
                int q = j;
                int a0 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                numberArray[a0] += 4;
                totalSum += 4;

                p = i - 1;
                q = j - 1;
                int a1 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                neighborsArray[a0][a1]++;

                p = i + 1;
                q = j - 1;
                int a2 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                neighborsArray[a0][a2]++;

                p = i - 1;
                q = j + 1;
                int a3 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                neighborsArray[a0][a3]++;

                p = i + 1;
                q = j + 1;
                int a4 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                neighborsArray[a0][a4]++;

            }
        }

        double sum = 0;

        double[] rates1D = new double[numberOfStates];
        for(int i = 0; i < numberOfStates; i++) {
            rates1D[i] = numberArray[i] / totalSum;
        }

        double[][] rates2D = new double[numberOfStates][numberOfStates];
        for(int i = 0; i < numberOfStates; i++) {
            for (int j = 0; j < numberOfStates; j++) {
                rates2D[i][j] = neighborsArray[i][j] / totalSum;
            }
        }

        for(int i = 0; i < numberOfStates; i++) {
            for (int j = 0; j < numberOfStates; j++) {
                double diff = ((rates1D[i] * rates1D[j]) - rates2D[i][j]) / (rates1D[i] * rates1D[j] + rates2D[i][j] + ZERO_AVOID);
                sum += diff * diff ;
            }
        }

        return sum;
    }
}
