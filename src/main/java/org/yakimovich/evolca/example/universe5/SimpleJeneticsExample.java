package org.yakimovich.evolca.example.universe5;

import org.jenetics.*;
import org.jenetics.engine.Engine;
import org.jenetics.util.CharSeq;
import org.jenetics.util.Factory;
import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.Universe5;
import org.yakimovich.evolca.measures.AvgNeighborColorIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.measures.NonZeroPercentage;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.JeneticsUtils;

import java.util.ArrayList;
import java.util.List;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;

public class SimpleJeneticsExample {

    private static boolean isCircular = true;
    private static char numberOfStates = 4;
    private static int size = 100;
    private static int ticks = 25;
    private static int evolutions = 10;
    private static int cellsChromosomeLength = size * size;
    private static int matrixChromosomeLength = numberOfStates * numberOfStates *
            numberOfStates * numberOfStates * numberOfStates;

    private static Universe5 genotypeToUniverse5(Genotype<CharacterGene> gt){
        CharacterChromosome cellsChromosome = (CharacterChromosome) gt.getChromosome(0);
        CharacterChromosome matrixChromosome = (CharacterChromosome)gt.getChromosome(1);
        char[][] cells = JeneticsUtils.chromosomeTo2DArray(cellsChromosome, size, size);
        char[][][][][] matrix = JeneticsUtils.chromosomeTo5DArray(matrixChromosome, numberOfStates);
        return new Universe5(cells, matrix, numberOfStates, isCircular);
    }

    private static Double evaluate(final Genotype<CharacterGene> gt) {

        AvgNeighborColorIndex5 anci = new AvgNeighborColorIndex5();
        NonZeroPercentage nzp = new NonZeroPercentage();

        Universe5 u = genotypeToUniverse5(gt);
        u.tick(25);
        double nzp1 = nzp.getValue(u);
        u.tick(10);
        double nzp2 = nzp.getValue(u);
        double anci2 = anci.getValue(u);

        return anci2 - Math.abs(nzp2 - nzp1) * 2 - Math.abs(nzp2 - 10);
    }

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        final CharSeq chars = CharSeq.of((char)0, (char) (numberOfStates - 1));
        List<Universe> universes = new ArrayList<Universe>();

        double fitnessSum = 0;
        for(int i = 0; i < evolutions; i++){
            final Factory<Genotype<CharacterGene>> gtf = Genotype.of(
                    new CharacterChromosome(chars, cellsChromosomeLength),
                    new CharacterChromosome(chars, matrixChromosomeLength)
            );

            final Engine<CharacterGene, Double> engine = Engine
                    .builder(SimpleJeneticsExample::evaluate, gtf)
                    .populationSize(50)
                    .survivorsSelector(new StochasticUniversalSelector<>())
                    .alterers(
                            new Mutator<>(0.001)
                            , new SwapMutator<>()
                            ,new SinglePointCrossover<>(0.9)
                    )
                    .build();
            Phenotype<CharacterGene, Double> ph = engine.stream().limit(100).collect(toBestPhenotype());
            System.out.print((i + 1) + "/" + evolutions + " ");
            fitnessSum += ph.getFitness();
            Universe u = genotypeToUniverse5(ph.getGenotype());
            universes.add(u);
        }

        double avgFitness = fitnessSum / (double) evolutions;
        MainWindow mainWindow = new MainWindow("Simple Jenetics example");
        mainWindow.addMeasure(new NonZeroPercentage());
        mainWindow.addMeasure(new AvgNeighborColorIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.setSleepTimeInMilliseconds(100);
        mainWindow.setUniverses(universes);

        long endTime = System.currentTimeMillis();
        long fullTime = endTime - startTime;
        System.out.println();
        System.out.println("completed in " + fullTime/1000.0 + " s, average fitness = " + avgFitness);
    }
}
