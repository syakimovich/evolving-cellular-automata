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

    public static char[][] createRandom2DArray(int n, int m, char numberOfStates){
        Random rand = new Random();
        char[][] result = new char[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                result[i][j] = (char) rand.nextInt(numberOfStates);
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
}
