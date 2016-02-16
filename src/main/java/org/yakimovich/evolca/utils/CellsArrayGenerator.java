package org.yakimovich.evolca.utils;

public class CellsArrayGenerator {
    public static char[][] create3ColorsSquare(int size){
        char[][] result = new char[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(i < (size / 4) || i > (3 * size / 4) || j < (size / 4) || j > (3 * size / 4)){
                    result[i][j] = 0;
                } else if (i < (size / 2)){
                    result[i][j] = 1;
                } else if (j < (size / 2)){
                    result[i][j] = 2;
                } else {
                    result[i][j] = 3;
                }
            }
        }
        return result;
    }

    public static char[][] create1Point(int size){
        char[][] result = new char[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                result[i][j] = 0;
            }
        }
        result[size/2][size/2] = 1;
        return result;
    }
}
