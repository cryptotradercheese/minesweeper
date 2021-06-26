package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int minesCount = scanner.nextInt();

        Field field = new Field(minesCount);
        field.print();

        int i = 0;
        while (!field.isGameFinished()) {
            System.out.print("Set/unset mine marks or claim a cell as free: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String action = scanner.next();
            System.out.println();
            if (i == 0) {
                field.regenMine(y - 1, x - 1);
                i++;
            }

            if ("free".equals(action)) {
                if (!field.explore(y - 1, x - 1)) {
                    field.print();
                    System.out.println("You stepped on a mine and failed!");
                    break;
                }
            } else if ("mine".equals(action)) {
                try {
                    field.switchMark(y - 1, x - 1);
                } catch (Exception e) {
                    System.out.println("There is a number here!");
                    continue;
                }
            }

            field.print();
        }

        System.out.println("Congratulations! You found all the mines!");
    }
}