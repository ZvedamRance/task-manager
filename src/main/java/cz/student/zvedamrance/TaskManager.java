package org.example;
import org.apache.commons.lang3.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class TaskManager {
    private static final File file = new File("tasks.csv");
    private static String[][] tasks;
    private static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadTasks();

        while (true) {
            System.out.println(ConsoleColors.BLUE + "\nPlease select an option: " + ConsoleColors.RESET);
            for (String option : OPTIONS) {
                System.out.println("-> " + option);
            }

            final String input = scanner.nextLine();
            switch (input) {
                case "add" -> add();
                case "remove" -> remove();
                case "list" -> list();
                case "exit" -> exit();
                default -> System.out.println(ConsoleColors.RED + "Please select a correct option." + ConsoleColors.RESET);
            }
        }
    }

    public static void add() {
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        System.out.println("Please enter the name of the task you would like to add:");
        final String name = scanner.nextLine();
        System.out.println("Please enter the date (format YYYY-MM-DD):");
        final String date = scanner.nextLine();
        System.out.println("Please enter if the task is done or not (true/false):");
        final String isDone = scanner.nextLine();
        tasks[tasks.length - 1] = new String[] {name, date, isDone};
    }

    public static void remove() {
        System.out.println("Please, enter the number of the task you would like to remove:");
        int number = -1;
        while (true) {
            final String input = scanner.nextLine();
            try{
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
            }
            if (number >= 0 && number < tasks.length) {
                break;
            } else {
                System.out.println(ConsoleColors.RED + "Please, enter a number between 0 and " + (tasks.length - 1) + "." + ConsoleColors.RESET);
            }
        }
        tasks = ArrayUtils.remove(tasks, number);
    }


    private static void list() {
        System.out.println(ConsoleColors.PURPLE + "Id:[Note, Date, IsDone]");
        System.out.println(ConsoleColors.RESET + "-----------------");
        for (int i= 0; i < tasks.length; i++) {
            System.out.println( i + ":"  + Arrays.toString(tasks[i]));
        }
    }

    public static void exit() {
        saveTasks();
        System.out.println(ConsoleColors.RED + "\nProgram terminated.");
        scanner.close();
        System.exit(0);
    }
    public static void saveTasks () {
        try (Writer writer = new FileWriter(file)) {
            for (int i = 0; i < tasks.length; i++) {
                for (int j =0 ; j < 3; j++) {
                    if (j < 2) {
                        writer.write( tasks[i][j]+ ", ");
                    } else {
                        writer.write(tasks[i][j] + "\n");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to save tasks into file.");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
    public static void loadTasks () {
        final ArrayList<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Failed to load tasks.csv");
            e.printStackTrace(System.err);
            System.exit(1);
        }
        tasks = new String[lines.size()][3];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < 3; j++) {
                String [] temp = lines.get(i).split(",");
                tasks[i][j] = temp [j].trim();
            }
        }
    }
}
