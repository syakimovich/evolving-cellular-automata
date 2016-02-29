package org.yakimovich.evolca.example.universesum;

import org.jenetics.*;
import org.jenetics.engine.Engine;
import org.jenetics.util.Factory;
import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.UniverseSum;
import org.yakimovich.evolca.measures.AvgNeighborColorIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.measures.NonZeroPercentage;
import org.yakimovich.evolca.ui.UniversesWindow;
import org.yakimovich.evolca.utils.ArrayUtils;
import org.yakimovich.evolca.utils.JeneticsUtils;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;

/**
 * Example of using Jenetics in a search for interesting UniverseSum. 10 universes are found and displayed.
 * It may take some time to calculate.
 */
public class SimpleJeneticsExample {

    private static boolean isCircular = true;
    private static char numberOfStates = 16;
    private static int numberOfNeighbors = 4;

    private static int size = 100;
    private static int evolutions = 10;
    private static int cellsChromosomeLength = size * size;
    private static int thresholdsChromosomeLength = numberOfStates * (numberOfStates - 1);

    private static UniverseSum genotypeToUniverseSum(Genotype<IntegerGene> gt){
        IntegerChromosome cellsChromosome = (IntegerChromosome) gt.getChromosome(0);
        IntegerChromosome thresholdsChromosome = (IntegerChromosome)gt.getChromosome(1);
        int[][] cells = JeneticsUtils.chromosomeTo2DArray(cellsChromosome, size, size);
        int[][] thresholds = JeneticsUtils.chromosomeTo2DArray(thresholdsChromosome, numberOfStates, numberOfStates - 1);
        return new UniverseSum(ArrayUtils.intArrayToCharArray(cells),
                numberOfNeighbors, thresholds, numberOfStates, isCircular);
    }

    private static Double evaluate(final Genotype<IntegerGene> gt) {

        AvgNeighborColorIndex5 anci = new AvgNeighborColorIndex5();
        NonZeroPercentage nzp = new NonZeroPercentage();

        UniverseSum u = genotypeToUniverseSum(gt);
        u.tick(100);
        double anci1 = nzp.getValue(u);
        u.tick(10);
        double anci2 = anci.getValue(u);

        return anci2 - Math.abs(anci2 - anci1) * 5;
    }

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        Universe[] universes = new Universe[10];

        double fitnessSum = 0;
        for(int i = 0; i < evolutions; i++){
            final Factory<Genotype<IntegerGene>> gtf = Genotype.of(
                    new IntegerChromosome(0, (int) numberOfStates - 1, cellsChromosomeLength),
                    new IntegerChromosome(0, (numberOfStates - 1) * numberOfNeighbors, thresholdsChromosomeLength)
            );

            final Engine<IntegerGene, Double> engine = Engine
                    .builder(SimpleJeneticsExample::evaluate, gtf)
                    .populationSize(50)
                    .survivorsSelector(new StochasticUniversalSelector<>())
                    .alterers(
                            new Mutator<>(0.001)
                            , new SwapMutator<>()
                            ,new SinglePointCrossover<>(0.9)
                    )
                    .build();
            Phenotype<IntegerGene, Double> ph = engine.stream().limit(100).collect(toBestPhenotype());
            System.out.print((i + 1) + "/" + evolutions + " ");
            fitnessSum += ph.getFitness();
            universes[i] = genotypeToUniverseSum(ph.getGenotype());
        }

        double avgFitness = fitnessSum / (double) evolutions;
        UniversesWindow mainWindow = new UniversesWindow(universes, "Simple Jenetics example");
        mainWindow.addMeasure(new AvgNeighborColorIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.addMeasure(new NonZeroPercentage());

        long endTime = System.currentTimeMillis();
        long fullTime = endTime - startTime;
        System.out.println();
        System.out.println("completed in " + fullTime/1000.0 + " s, average fitness = " + avgFitness);
    }
}
