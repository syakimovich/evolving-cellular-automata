package org.yakimovich.evolca.utils;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtils{
    public static char[][] copy2DArray(char[][] original){
        char[][] result = new char[original.length][];
        for(int i = 0; i < original.length; i++){
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    public static int[][] copy2DArray(int[][] original){
        int[][] result = new int[original.length][];
        for(int i = 0; i < original.length; i++){
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    public static char[][] intArrayToCharArray(int[][] intArray){
        char[][] result = new char[intArray.length][intArray[0].length];
        for(int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[0].length; j++) {
                result[i][j] = (char) intArray[i][j];
            }
        }
        return result;
    }

    public static int difference2D(char[][] first, char[][] second){
        int result = 0;
        for(int i = 0; i < first.length; i++){
            for(int j = 0; j < first[0].length; j++){
                if(first[i][j] != second[i][j]){
                    result++;
                }
            }
        }
        return result;
    }

    public static int difference5D(char[][][][][] first, char[][][][][] second){
        int result = 0;
        for(int i1 = 0; i1 < first.length; i1++){
            for(int i2 = 0; i2 < first.length; i2++){
                for(int i3 = 0; i3 < first.length; i3++){
                    for(int i4 = 0; i4 < first.length; i4++){
                        for(int i5 = 0; i5 < first.length; i5++){
                            if(first[i1][i2][i3][i4][i5] != second[i1][i2][i3][i4][i5]){
                                result++;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static char[][] createRandom2DCharArray(int n, int m, char numberOfStates){
        Random rand = new Random();
        char[][] result = new char[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                result[i][j] = (char) rand.nextInt(numberOfStates);
            }
        }
        return result;
    }

    public static int[][] createRandom2DIntArray(int n, int m, int numberOfStates){
        Random rand = new Random();
        int[][] result = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                result[i][j] = rand.nextInt(numberOfStates);
            }
        }
        return result;
    }

    public static int[] calcCellsInStates(char[][] cells, char numberOfStates){
        int[] result = new int[numberOfStates];

        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                result[cells[i][j]]++;
            }
        }

        return result;
    }

    public static char getSafeValue2D(int p, int q, char[][] cells, boolean isCircular, char def){
        if(isCircular){
            if(p < 0){
                p += cells.length;
            }
            if(p >= cells.length){
                p -= cells.length;
            }
            if(q < 0){
                q += cells[0].length;
            }
            if(q >= cells[0].length){
                q -= cells[0].length;
            }
            return cells[p][q];
        } else {
            if ((p >= 0) && (q >= 0) && (p < cells.length) && (q < cells[0].length)) {
                return cells[p][q];
            }
            return def;
        }
    }

    public static char[][][][][] createRandomMatrix5D(double[] thresholds){
        Random rand = new Random();
        int numberOfStates = thresholds.length + 1;

        char[][][][][] matrix = new char[numberOfStates][numberOfStates][numberOfStates][numberOfStates][numberOfStates];

        for(int a0 = 0; a0 < numberOfStates; a0++){
            for(int a1 = 0; a1 < numberOfStates; a1++){
                for(int a2 = 0; a2 < numberOfStates; a2++){
                    for(int a3 = 0; a3 < numberOfStates; a3++){
                        for(int a4 = 0; a4 < numberOfStates; a4++){
                            double rr = rand.nextDouble();
                            matrix[a0][a1][a2][a3][a4] = 0;
                            for(int i = thresholds.length - 1; i >= 0; i--){
                                if(rr > thresholds[i]){
                                    matrix[a0][a1][a2][a3][a4] = (char)(i + 1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return matrix;
    }

    public static char[][][][][] copy5DArray(char[][][][][] original){
        char[][][][][] result = new char[original.length][][][][];
        for(int i = 0; i < original.length; i++){
            result[i] = new char[original[i].length][][][];
            for(int j = 0; j < original[i].length; j++){
                result[i][j] = new char[original[i][j].length][][];
                for(int k = 0; k < original[i][j].length; k++){
                    result[i][j][k] = copy2DArray(original[i][j][k]);
                }
            }
        }
        return result;
    }
}
