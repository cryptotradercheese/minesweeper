package minesweeper;

import java.util.Random;
import java.util.ArrayList;

public class Field {
    final ArrayList<CellState>[][] field;

    Field(int minesCount) {
        this.field = this.generateField();
        fillWithMines(minesCount);
    }

    int countMinesAround(int row, int column) {
        int mines = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (
                        i >= 0 &&
                        j >= 0 &&
                        i < this.field.length &&
                        j < this.field[0].length &&
                        this.field[i][j].contains(CellState.MINE)
                ) {
                    mines++;
                }
            }
        }

        return mines;
    }

    void switchMark(int row, int column) throws Exception {
        if (this.field[row][column].contains(CellState.FIGURE)) {
            throw new Exception("Cell is occupied");
        } else if (!this.field[row][column].contains(CellState.MARKED)) {
            this.field[row][column].add(CellState.MARKED);
        } else if (this.field[row][column].contains(CellState.MARKED)) {
            this.field[row][column].remove(CellState.MARKED);
        }
    }

    boolean explore(int row, int column) {
        this.field[row][column].remove(CellState.UNEXPLORED);
        if (this.field[row][column].contains(CellState.MARKED)) {
            this.field[row][column].remove(CellState.MARKED);
        }

        if (this.field[row][column].contains(CellState.MINE)) {
            revealAllMines();
            return false;
        } else {
            if (countMinesAround(row, column) == 0) {
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = column - 1; j <= column + 1; j++) {
                        if (
                                i >= 0 &&
                                j >= 0 &&
                                i < this.field.length &&
                                j < this.field[0].length &&
                                !this.field[i][j].contains(CellState.FIGURE) &&
                                this.field[i][j].contains(CellState.UNEXPLORED)
                        ) {
                            explore(i, j);
                        }
                    }
                }
            } else if (countMinesAround(row, column) > 0) {
                this.field[row][column].add(CellState.FIGURE);
            }

            return true;
        }
    }

    boolean isGameFinished() {
        int mines = 0;
        int unexplored = 0;
        boolean isAllMinesMarked = true;
        boolean steppedOnMine = false;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].contains(CellState.MINE) && !field[i][j].contains(CellState.MARKED)) {
                    isAllMinesMarked = false;
                }

                if (field[i][j].contains(CellState.MINE)) {
                    mines++;
                }

                if (field[i][j].contains(CellState.UNEXPLORED)) {
                    unexplored++;
                }

                if (field[i][j].contains(CellState.MINE) && !field[i][j].contains(CellState.UNEXPLORED)) {
                    steppedOnMine = true;
                }
            }
        }

        return mines == unexplored || isAllMinesMarked || steppedOnMine;
    }

    void print() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < this.field.length; i++) {
            StringBuilder row = new StringBuilder();
            row.append(i + 1 + "|");
            for (int j = 0; j < this.field[0].length; j++) {
                if (this.field[i][j].contains(CellState.FIGURE)) {
                    row.append(countMinesAround(i, j));
                } else if (this.field[i][j].contains(CellState.MARKED)) {
                    row.append(CellState.MARKED.symbol);
                } else if (this.field[i][j].contains(CellState.UNEXPLORED)) {
                    row.append(CellState.UNEXPLORED.symbol);
                } else if (this.field[i][j].contains(CellState.EMPTY)) {
                    row.append(CellState.EMPTY.symbol);
                } else if (this.field[i][j].contains(CellState.MINE)) {
                    row.append(CellState.MINE.symbol);
                }
            }
            row.append("|");
            System.out.println(row);
        }
        System.out.println("-|---------|");
    }






    private ArrayList<CellState>[][] generateField() {
        ArrayList<CellState>[][] field = new ArrayList[9][9];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new ArrayList<>();
                field[i][j].add(CellState.EMPTY);
                field[i][j].add(CellState.UNEXPLORED);
            }
        }
        return field;
    }

    private void fillWithMines(int minesCount) {
        final Random random = new Random();
        for (int i = 0; i < minesCount; i++) {
            int randRow;
            int randColumn;
            do {
                randRow = random.nextInt(this.field.length);
                randColumn = random.nextInt(this.field[0].length);
            } while (this.field[randRow][randColumn].contains(CellState.MINE));

            this.field[randRow][randColumn].remove(CellState.EMPTY);
            this.field[randRow][randColumn].add(CellState.MINE);
        }
    }

    void regenMine(int row, int column) {
        if (this.field[row][column].contains(CellState.MINE)) {
            this.field[row][column].remove(CellState.MINE);
            this.field[row][column].add(CellState.EMPTY);

            it:
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[0].length; j++) {
                    if (this.field[i][j].contains(CellState.EMPTY)) {
                        this.field[row][column].remove(CellState.EMPTY);
                        this.field[row][column].add(CellState.MINE);
                        break it;
                    }
                }
            }
        }
    }

    private void revealAllMines() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].contains(CellState.MINE)) {
                    if (field[i][j].contains(CellState.UNEXPLORED)) {
                        field[i][j].remove(CellState.UNEXPLORED);
                    }

                    if (field[i][j].contains(CellState.MARKED)) {
                        field[i][j].remove(CellState.MARKED);
                    }
                }
            }
        }
    }
}