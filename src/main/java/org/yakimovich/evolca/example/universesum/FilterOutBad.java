package org.yakimovich.evolca.example.universesum;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.UniverseSum;
import org.yakimovich.evolca.measures.AvgNeighborColorIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.measures.NonZeroPercentage;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;
import org.yakimovich.evolca.utils.InitialStateGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FilterOutBad {

	public static void main(String[] args) throws InterruptedException {
        char size = 100;
        char numberOfStates = 64;
        int numberOfNeighbors = 4;
        double initialStateToZeroProbability = 0;
        double resultStateToZeroProbability = 0.8;
        Random r = new Random();

        if(args != null && args.length >= 1){
            numberOfStates = (char) Integer.parseInt(args[0]);

            if(args.length >= 2){
                numberOfNeighbors = (char) Integer.parseInt(args[1]);
            }
        }

        final boolean isCircular = true;

        List<Universe> universes = new ArrayList<Universe>();

        int goodUniverses = 0;
search: while(goodUniverses < 10){
            System.out.println("Searching for new universe, " + goodUniverses + " already found");
            char[][] initialCells = InitialStateGenerator.createRandom(size, size, numberOfStates);
            initialCells = ArrayUtils.replaceWithZeros(initialCells, initialStateToZeroProbability);

            int[][] thresholds = ArrayUtils.createRandom2DIntArray(numberOfStates, numberOfStates - 1,
                    (numberOfStates - 1) * numberOfNeighbors);
            char[][] resultStates = ArrayUtils.createRandom2DCharArray(numberOfStates,numberOfStates, numberOfStates);
            resultStates = ArrayUtils.replaceWithZeros(resultStates, resultStateToZeroProbability);

            resultStates[0][0] = 0;

            Universe u = new UniverseSum(initialCells, numberOfNeighbors, thresholds, resultStates, numberOfStates, isCircular);

            NonZeroPercentage nzp = new NonZeroPercentage();
            Gini gini = new Gini();
            AvgNeighborColorIndex5 anci = new AvgNeighborColorIndex5();



            for(int i = 0; i < 5; i++) {
                u.tick(20);
                char[][] c1 = u.getCells();
                int age1 = u.getAge();
                char[][] c2;
                for (int j = 0; j < 10; j++) {
                    u.tick();
                    c2 = u.getCells();
                    if (ArrayUtils.difference2D(c1, c2) == 0) {
                        System.out.println("repeated state between ages " + age1 + " and " + u.getAge() + ", skipped");
                        continue search;
                    }
                }

            }


            for(int i = 0; i < 5; i++) {
                double nzp1 = nzp.getValue(u);
                u.tick(20);
                double nzp2 = nzp.getValue(u);
                if (nzp2 < 0.5) {
                    System.out.println("Non zero percentage = " + nzp2 + " below 0.5, skipped");
                    continue search;
                }

                if (nzp2 >= 0.999) {
                    System.out.println("Non zero percentage = " + nzp2 + " more than 0.999, skipped");
                    continue search;
                }

                if (Math.abs(nzp2 - nzp1) > 0.3) {
                    System.out.println("Non zero percentage change = " + (nzp2 - nzp1) + " more than 0.3, skipped");
                    continue search;
                }
                char[][] c1 = u.getCells();
                int age1 = u.getAge();
                char[][] c2;
                for (int j = 0; j < 10; j++) {
                    u.tick();
                    c2 = u.getCells();
                    if (ArrayUtils.difference2D(c1, c2) == 0) {
                        System.out.println("repeated state between ages " + age1 + " and " + u.getAge() + ", skipped");
                        continue search;
                    }
                }

            }

            goodUniverses++;

            u.resetToInitialState();
            universes.add(u);
        }

        MainWindow mainWindow = new MainWindow("Simple random example");
        mainWindow.addMeasure(new AvgNeighborColorIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.addMeasure(new NonZeroPercentage());
        mainWindow.setSleepTimeInMilliseconds(100);
        mainWindow.setUniverses(universes);

	}

}
