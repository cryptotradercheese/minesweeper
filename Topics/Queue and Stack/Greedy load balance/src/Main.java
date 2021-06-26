import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        final Scanner scanner = new Scanner(System.in);
        Queue<Integer> first = new ArrayDeque<>();
        int firstLoad = 0;
        Queue<Integer> second = new ArrayDeque<>();
        int secondLoad = 0;

        int tasksCount = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < tasksCount; i++) {
            String[] task = scanner.nextLine().split(" ");
            if (firstLoad <= secondLoad) {
                first.offer(Integer.parseInt(task[0]));
                firstLoad += Integer.parseInt(task[1]);
            } else {
                second.offer(Integer.parseInt(task[0]));
                secondLoad += Integer.parseInt(task[1]);
            }
        }

        while (first.size() != 0) {
            System.out.print(first.poll() + " ");
        }

        System.out.println();
        while (second.size() != 0) {
            System.out.print(second.poll() + " ");
        }
    }
}