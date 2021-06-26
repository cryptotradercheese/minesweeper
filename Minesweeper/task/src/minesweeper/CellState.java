package minesweeper;

public enum CellState {
    UNEXPLORED('.'),
    MARKED('*'),
    FIGURE('?'),
    EMPTY('/'),
    MINE('X');


    char symbol;

    CellState(char symbol) {
        this.symbol = symbol;
    }
}
