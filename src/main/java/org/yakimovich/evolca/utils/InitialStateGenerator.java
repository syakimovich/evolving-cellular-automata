package org.yakimovich.evolca.utils;

public class InitialStateGenerator {
    public static char[][] create3ColorsRectangular(int width, int height){
        char[][] result = new char[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(i < (height / 4) || i > (3 * height / 4) || j < (width / 4) || j > (3 * width / 4)){
                    result[i][j] = 0;
                } else if (i < (height / 2)){
                    result[i][j] = 1;
                } else if (j < (width / 2)){
                    result[i][j] = 2;
                } else {
                    result[i][j] = 3;
                }
            }
        }
        return result;
    }

    public static char[][] create1Point(int width, int height){
        char[][] result = new char[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                result[i][j] = 0;
            }
        }
        result[height / 2][width / 2] = 1;
        return result;
    }
}
