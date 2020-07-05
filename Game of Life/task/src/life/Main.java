package life;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Main {

    static class LifeGenerator {
        private final Random rndGenerator;
        private int aliveCount = 0;

        public int getAliveCount() {
            return aliveCount;
        }

        public LifeGenerator() {
            rndGenerator = new Random();
        }

        public char[][] initUniverseRnd(int size) {
            aliveCount = 0;
            char[][] retUniverse = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (rndGenerator.nextBoolean()) {
                        retUniverse[i][j] = 'O';
                        aliveCount++;
                    } else {
                        retUniverse[i][j] = ' ';
                    }
                }
            }
            return retUniverse;
        }

        private int getNeighborCount(int i, int j, char[][] currentUniverse) {
            int size = currentUniverse.length;
            int neighborCounter = 0;
            //three upper neighbors
            if (i == 0) {       //for top line
                if (currentUniverse[size - 1][j] == 'O') { neighborCounter++;} //top
                if (j == 0) {                               //top-left
                    if (currentUniverse[size - 1][size - 1] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[size - 1][j - 1] == 'O') { neighborCounter++; }
                }
                if (j == size - 1) {                    //top-right
                    if (currentUniverse[size - 1][0] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[size - 1][j + 1] == 'O') { neighborCounter++; }
                }
            } else {        //for non top lines
                if (currentUniverse[i - 1][j] == 'O') { neighborCounter++;} //top
                if (j == 0) {                           //top-left
                    if (currentUniverse[i - 1][size - 1] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[i - 1][j - 1] == 'O') { neighborCounter++; }
                }
                if (j == size - 1) {                    //top-right
                    if (currentUniverse[i - 1][0] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[i - 1][j + 1] == 'O') { neighborCounter++; }
                }
            }
            //left neighbor
            if (j == 0) {
                if (currentUniverse[i][size - 1] == 'O') { neighborCounter++; }
            } else {
                if (currentUniverse[i][j - 1] == 'O') { neighborCounter++; }
            }
            // right neighbor
            if (j == size - 1) {
                if (currentUniverse[i][0] == 'O') { neighborCounter++; }
            } else {
                if (currentUniverse[i][j + 1] == 'O') { neighborCounter++; }
            }
            //three of the neighbors below
            if (i == size - 1) {       //for bottom line
                if (currentUniverse[0][j] == 'O') { neighborCounter++;} //bottom
                if (j == 0) {                               //bottom-left
                    if (currentUniverse[0][size - 1] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[0][j - 1] == 'O') { neighborCounter++; }
                }
                if (j == size - 1) {                    //bottom-right
                    if (currentUniverse[0][0] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[0][j + 1] == 'O') { neighborCounter++; }
                }
            } else {        //for non bottom lines
                if (currentUniverse[i + 1][j] == 'O') { neighborCounter++;} //bottom
                if (j == 0) {                           //bottom-left
                    if (currentUniverse[i + 1][size - 1] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[i + 1][j - 1] == 'O') { neighborCounter++; }
                }
                if (j == size - 1) {                    //bottom-right
                    if (currentUniverse[i + 1][0] == 'O') { neighborCounter++; }
                } else {
                    if (currentUniverse[i + 1][j + 1] == 'O') { neighborCounter++; }
                }
            }
            return neighborCounter;
        }

        public char[][] nextGeneration(int size, char[][] currentUniverse) {
            char[][] retUniverse = new char[size][size];
            int neighborCounter;
            aliveCount = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    neighborCounter = getNeighborCount(i, j, currentUniverse);
                    if (neighborCounter < 2 || neighborCounter > 3) {
                        retUniverse[i][j] = ' ';
                    } else if (neighborCounter == 3) {
                        retUniverse[i][j] = 'O';
                        aliveCount++;
                    } else {
                        if (currentUniverse[i][j] == 'O') {aliveCount++;}
                        retUniverse[i][j] = currentUniverse[i][j];
                    }
                }
            }
            return retUniverse;
        }
    }

    static class Universe {
        private char[][] evenUniverse;
        private char[][] oddUniverse;
        private final int universeSize;
        LifeGenerator lifeGenerator;
        private int countGenerations;

        public int getCountGenerations() {
            return countGenerations;
        }

        public int getAliveCount() {
            return lifeGenerator.getAliveCount();
        }

        public Universe(int size) {
            this.universeSize = size;
            evenUniverse = new char[universeSize][universeSize];
            oddUniverse = new char[universeSize][universeSize];
            lifeGenerator = new LifeGenerator();
        }

        public char[][] initUniverse() {
            countGenerations = 1;
            oddUniverse = lifeGenerator.initUniverseRnd(universeSize).clone();
            return oddUniverse.clone();
        }

        public char[][] liveUniverse() {
            if (countGenerations % 2 != 0) {
                evenUniverse = lifeGenerator.nextGeneration(universeSize, oddUniverse).clone();
                countGenerations++;
                return evenUniverse;
            } else {
                oddUniverse = lifeGenerator.nextGeneration(universeSize, evenUniverse).clone();
                countGenerations++;
                return oddUniverse;
            }
        }
    }

    public static void main(String[] args)  {
        try {
            new GameOfLife();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

