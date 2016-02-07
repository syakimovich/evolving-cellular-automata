package org.yakimovich.evolca.measures;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.ArrayUtils;

public class AvgNeighborColourIndex implements Measure {

    final public char OUT_VALUE = 0;

    @Override
    public String getName() {
        return "ANCI";
    }

    @Override
    public double getValue(Universe u) {
        int numberOfStates = u.getNumberOfStates();
        double[] num = new double[numberOfStates];
        for(int i = 0; i < num.length; i++){
            num[i] = 0;
        }
        double[][] sum = new double[numberOfStates][numberOfStates + 1];
        for(int i = 0; i < sum.length; i++){
            for(int j = 0; j < sum.length + 1; j++){
                sum[i][j] = 0;
            }
        }
        char[][] cells = u.getCells();
        for(int i = 0; i < u.getWidth(); i++){
            for(int j = 0; j < u.getHeight(); j++){
                int p = i;
                int q = j;
                int a0 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                num[a0]++;

                p = i - 1;
                q = j - 1;
                int a1 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                sum[a0][a1]++;

                p = i + 1;
                q = j - 1;
                int a2 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                sum[a0][a2]++;

                p = i - 1;
                q = j + 1;
                int a3 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                sum[a0][a3]++;

                p = i + 1;
                q = j + 1;
                int a4 = ArrayUtils.getSafeValue2D(p, q, cells, u.isCircular(), OUT_VALUE);
                sum[a0][a4]++;

            }
        }
        double result = 0;
        for(int i = 0; i < sum.length; i++){
            double sum1 = 0;
            for(int j = 0; j < sum.length + 1; j++){
                sum1 += sum[i][j];
            }
            double avg1 = sum1/sum.length;
            double divSqSum = 0;
            for(int j = 0; j < sum.length + 1; j++){
                divSqSum += (sum[i][j] - avg1) * (sum[i][j] - avg1);
            }

            double divSqSumW = Math.sqrt(divSqSum);
            if(num[i] > 0){
                divSqSumW = Math.sqrt(divSqSum)/num[i];
            }

            result += divSqSumW;
        }
        return result;
    }
}
