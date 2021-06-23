package minesweeper;

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // write your code here
        final Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int minesCount = scanner.nextInt();

        Field field = new Field();
        field.fillWithMines(minesCount);
        field.displayNumbers();
        field.print();

        while (!field.isGameFinished()) {
            System.out.print("Set/delete mine marks (x and y coordinates): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            System.out.println();

            try {
                field.switchMine(x, y);
            } catch (Exception e) {
                System.out.println("There is a number here!");
                continue;
            }
            field.print();
        }

        System.out.println("Congratulations! You found all the mines!");
    }
}

class Field {
    final char[][] field;
    int[][] minesCoordinates;
    int markedCellsCount = 0;

    Field() {
        this.field = this.generateField();
    }

    void fillWithMines(int minesCount) {
        minesCoordinates = new int[minesCount][2];
        final Random random = new Random();
        for (int i = 0; i < minesCount; i++) {
            int randRow;
            int randColumn;
            do {
                randRow = random.nextInt(this.field.length);
                randColumn = random.nextInt(this.field[0].length);
            } while (this.field[randRow][randColumn] != '.');

            this.field[randRow][randColumn] = 'X';
            minesCoordinates[i] = new int[] {randColumn, randRow};
        }
    }

    void displayNumbers() {
        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field[0].length; j++) {
                if (this.field[i][j] == '.') {
                    int k;
                    int l;
                    int kLimit;
                    int lLimit;
                    if (i == 0 && j == 0) {
                        k = i;
                        l = j;
                        kLimit = i + 1;
                        lLimit = j + 1;
                    } else if (i == 0 && j == 8) {
                        k = i;
                        l = j - 1;
                        kLimit = i + 1;
                        lLimit = j;
                    } else if (i == 8 && j == 0) {
                        k = i - 1;
                        l = j;
                        kLimit = i;
                        lLimit = j + 1;
                    } else if (i == 8 && j == 8) {
                        k = i - 1;
                        l = j - 1;
                        kLimit = i;
                        lLimit = j;
                    } else if (i == 0) {
                        k = i;
                        l = j - 1;
                        kLimit = i + 1;
                        lLimit = j + 1;
                    } else if (i == 8) {
                        k = i - 1;
                        l = j - 1;
                        kLimit = i;
                        lLimit = j + 1;
                    } else if (j == 0) {
                        k = i - 1;
                        l = j;
                        kLimit = i + 1;
                        lLimit = j + 1;
                    } else if (j == 8) {
                        k = i - 1;
                        l = j - 1;
                        kLimit = i + 1;
                        lLimit = j;
                    } else {
                        k = i - 1;
                        l = j - 1;
                        kLimit = i + 1;
                        lLimit = j + 1;
                    }

                    int mines = 0;
                    for (; k <= kLimit; k++) {
                        for (int newL = l; newL <= lLimit; newL++) {
                            if (this.field[k][newL] == 'X') {
                                mines++;
                            }
                        }
                    }

                    if (mines != 0) {
                        this.field[i][j] = String.valueOf(mines).charAt(0);
                    }
                }
            }
        }

        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field[0].length; j++) {
                if (field[i][j] == 'X') {
                    field[i][j] = '.';
                }
            }
        }
    }

    void switchMine(int x, int y) throws Exception {
        if (String.valueOf(field[y - 1][x - 1]).matches("\\d")) {
            throw new Exception("Cell is occupied");
        } else if (field[y - 1][x - 1] == '.') {
            field[y - 1][x - 1] = '*';
            markedCellsCount++;
        } else if (field[y - 1][x - 1] == '*') {
            field[y - 1][x - 1] = '.';
            markedCellsCount--;
        }
    }

    boolean isGameFinished() {
        if (minesCoordinates.length != markedCellsCount) {
            return false;
        }

        for (int[] mineCoordinate : minesCoordinates) {
            if (field[mineCoordinate[1]][mineCoordinate[0]] != '*') {
                return false;
            }
        }
        return true;
    }

    void print() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < this.field.length; i++) {
            StringBuilder row = new StringBuilder();
            row.append(i + 1 + "|");
            for (int j = 0; j < this.field[0].length; j++) {
                row.append(this.field[i][j]);
            }
            row.append("|");
            System.out.println(row);
        }
        System.out.println("-|---------|");
    }

    private char[][] generateField() {
        char[][] field = new char[9][9];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = '.';
            }
        }
        return field;
    }
}