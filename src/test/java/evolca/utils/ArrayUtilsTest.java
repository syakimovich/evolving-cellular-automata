package evolca.utils;

import org.junit.Before;
import org.junit.Test;
import org.yakimovich.evolca.utils.ArrayUtils;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayUtilsTest {

    public static final int SIZE_1 = 20;
    public static final int SIZE_2 = 30;

    public static final char ONE = 1;
    public static final char TWO = 2;
    public static final char RAND_ARRAY_STATES = 4;

    char[][] zeroArray2D;
    char[][] oneArray2D;
    char[][] randArray2D;

    @Before
    public void setUp(){
        zeroArray2D = new char[SIZE_1][SIZE_2];
        oneArray2D = new char[SIZE_1][SIZE_2];
        for(int i = 0; i < SIZE_1; i++){
            for(int j = 0; j < SIZE_2; j++){
                zeroArray2D[i][j] = 0;
                oneArray2D[i][j] = 1;
            }
        }
        randArray2D = ArrayUtils.createRandom2DArray(SIZE_1, SIZE_2, RAND_ARRAY_STATES);
    }

    @Test
    public void testDifference2D(){
        int zeroToZeroDiff = ArrayUtils.difference2D(zeroArray2D, zeroArray2D);
        assertEquals(0, zeroToZeroDiff);
        int zeroToOneDiff = ArrayUtils.difference2D(zeroArray2D, oneArray2D);
        assertEquals(SIZE_1 * SIZE_2 , zeroToOneDiff);
    }

    @Test
    public void copy2DArray(){
        char[][] array1 = ArrayUtils.copy2DArray(zeroArray2D);
        array1[0][1] = 1;
        array1[1][2] = 2;
        assertEquals(2, ArrayUtils.difference2D(zeroArray2D, array1));
    }

    @Test
    public void testCalcCellsInStates(){
        int[] numbers0 = ArrayUtils.calcCellsInStates(zeroArray2D, ONE);
        assertEquals(ONE, numbers0.length);
        assertEquals(SIZE_1 * SIZE_2, numbers0[0]);

        int[] numbers1 = ArrayUtils.calcCellsInStates(oneArray2D, TWO);
        assertEquals(TWO, numbers1.length);
        assertEquals(0, numbers1[0]);
        assertEquals(SIZE_1 * SIZE_2, numbers1[1]);

        int[] numbers = ArrayUtils.calcCellsInStates(randArray2D, RAND_ARRAY_STATES);
        int sum = 0;
        for(int i = 0; i < RAND_ARRAY_STATES; i++){
            sum += numbers[i];
        }
        assertEquals(SIZE_1 * SIZE_2, sum);
    }

    @Test
    public void testCreateRandom2DArray(){
        int[] numbers = ArrayUtils.calcCellsInStates(randArray2D, RAND_ARRAY_STATES);
        for(int i = 0; i < RAND_ARRAY_STATES; i++){
            double rate = (double) numbers[i] / (SIZE_1 * SIZE_2);
            assertTrue(rate > 0.1);
            assertTrue(rate < 0.5);
        }
    }

    @Test
    public void testGetSafeValue2D(){
        int a1 = 3;
        int a2 = 4;
        char defValue = 4;
        assertEquals(randArray2D[a1][a2],
                ArrayUtils.getSafeValue2D(a1, a2, randArray2D, false, defValue));
        assertEquals(randArray2D[a1][a2],
                ArrayUtils.getSafeValue2D(a1, a2, randArray2D, true, defValue));
        int b1 = SIZE_1;
        int b2 = SIZE_2;
        assertEquals(defValue,
                ArrayUtils.getSafeValue2D(b1, b2, randArray2D, false, defValue));
        assertEquals(randArray2D[0][0],
                ArrayUtils.getSafeValue2D(b1, b2, randArray2D, true, defValue));
        int c1 = 3;
        int c2 = SIZE_2;
        assertEquals(defValue,
                ArrayUtils.getSafeValue2D(c1, c2, randArray2D, false, defValue));
        assertEquals(randArray2D[c1][0],
                ArrayUtils.getSafeValue2D(c1, c2, randArray2D, true, defValue));
        int d1 = -1;
        int d2 = 1;
        assertEquals(defValue,
                ArrayUtils.getSafeValue2D(d1, d2, randArray2D, false, defValue));
        assertEquals(randArray2D[d1 + SIZE_1][d2],
                ArrayUtils.getSafeValue2D(d1, d2, randArray2D, true, defValue));
    }
}
