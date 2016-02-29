package org.yakimovich.evolca.utils;

import org.jenetics.CharacterChromosome;
import org.jenetics.CharacterGene;
import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.yakimovich.evolca.Universe5;

/**
 * Helpful functions to work with Jenetics library.
 */
public class JeneticsUtils {
    public static char[][] chromosomeTo2DArray(CharacterChromosome ch, int size1, int size2){
        char[][] result = new char[size1][size2];
        for(int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                result[i][j] = ch.getGene(i * size2 + j).charValue();
            }
        }
        return result;
    }

    public static int[][] chromosomeTo2DArray(IntegerChromosome ch, int size1, int size2){
        int[][] result = new int[size1][size2];
        for(int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                result[i][j] = ch.getGene(i * size2 + j).intValue();
            }
        }
        return result;
    }

    public static CharacterChromosome array2DToChromosome(char[][] array2D){
        char[] array1D = new char[array2D.length * array2D[0].length];
        for(int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < array2D[0].length; j++) {
                array1D[i * array2D[0].length + j] = array2D[i][j];
            }
        }
        CharacterChromosome result = CharacterChromosome.of(String.valueOf(array1D));
        return result;
    }

    public static char[][][][][] chromosomeTo5DArray(CharacterChromosome ch, char numberOfStates){
        char[][][][][] result = new char[numberOfStates][numberOfStates][numberOfStates][numberOfStates][numberOfStates];
        for(int i1 = 0; i1 < numberOfStates; i1++) {
            for(int i2 = 0; i2 < numberOfStates; i2++) {
                for(int i3 = 0; i3 < numberOfStates; i3++) {
                    for(int i4 = 0; i4 < numberOfStates; i4++) {
                        for(int i5 = 0; i5 < numberOfStates; i5++) {
                            int position =
                                    i1 * numberOfStates * numberOfStates * numberOfStates * numberOfStates +
                                    i2 * numberOfStates * numberOfStates * numberOfStates +
                                    i3 * numberOfStates * numberOfStates +
                                    i4 * numberOfStates +
                                    i5;
                            result[i1][i2][i3][i4][i5] = ch.getGene(position).charValue();
                        }
                    }
                }
            }
        }
        return result;
    }

    public static CharacterChromosome array5DToChromosome(char[][][][][] array5D){
        char numberOfStates = (char)array5D.length;
        char[] array1D = new char[numberOfStates * numberOfStates * numberOfStates * numberOfStates * numberOfStates];
        for(int i1 = 0; i1 < numberOfStates; i1++) {
            for(int i2 = 0; i2 < numberOfStates; i2++) {
                for(int i3 = 0; i3 < numberOfStates; i3++) {
                    for(int i4 = 0; i4 < numberOfStates; i4++) {
                        for(int i5 = 0; i5 < numberOfStates; i5++) {
                            int position =
                                    i1 * numberOfStates * numberOfStates * numberOfStates * numberOfStates +
                                            i2 * numberOfStates * numberOfStates * numberOfStates +
                                            i3 * numberOfStates * numberOfStates +
                                            i4 * numberOfStates +
                                            i5;
                            array1D[position] = array5D[i1][i2][i3][i4][i5];
                        }
                    }
                }
            }
        }
        CharacterChromosome result = CharacterChromosome.of(String.valueOf(array1D));
        return result;
    }

    public static Universe5 genotypeToUniverse5(Genotype<CharacterGene> gt,
                                                char numberOfStates, int size1, int size2, boolean isCircular){
        CharacterChromosome cellsChromosome = (CharacterChromosome) gt.getChromosome(0);
        CharacterChromosome matrixChromosome = (CharacterChromosome)gt.getChromosome(1);
        char[][] cells = JeneticsUtils.chromosomeTo2DArray(cellsChromosome, size1, size2);
        char[][][][][] matrix = JeneticsUtils.chromosomeTo5DArray(matrixChromosome, numberOfStates);
        return new Universe5(cells, matrix, numberOfStates, isCircular);
    }
}
