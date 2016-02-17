package evolca.utils;

import org.jenetics.CharacterChromosome;
import org.junit.Before;
import org.junit.Test;
import org.yakimovich.evolca.utils.ArrayUtils;
import org.yakimovich.evolca.utils.JeneticsUtils;

import static org.junit.Assert.assertEquals;

public class JeneticsUtilsTest {
    public static final int SIZE_1 = 20;
    public static final int SIZE_2 = 30;
    public static final char RAND_ARRAY_STATES = 4;
    public static final double[] THRESHOLDS = {0.8, 0.9, 0.95};

    char[][] randArray2D;
    char[][][][][] randArray5D;



    @Before
    public void setUp(){
        randArray2D = ArrayUtils.createRandom2DCharArray(SIZE_1, SIZE_2, RAND_ARRAY_STATES);
        randArray5D = ArrayUtils.createRandomMatrix5D(THRESHOLDS);
    }

    @Test
    public void testChromosomeTo2DArrayAndReverse(){
        CharacterChromosome ch = JeneticsUtils.array2DToChromosome(randArray2D);
        char[][] array = JeneticsUtils.chromosomeTo2DArray(ch, SIZE_1, SIZE_2);
        assertEquals(0, ArrayUtils.difference2D(array, randArray2D));
    }

    @Test
    public void testChromosomeTo5DArrayAndReverse(){
        CharacterChromosome ch = JeneticsUtils.array5DToChromosome(randArray5D);
        char[][][][][] array = JeneticsUtils.chromosomeTo5DArray(ch, RAND_ARRAY_STATES);
        assertEquals(0, ArrayUtils.difference5D(array, randArray5D));
    }
}
