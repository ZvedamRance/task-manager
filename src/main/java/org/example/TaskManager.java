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
    static File file = new File("tasks.csv");
    static String[][] tasks;
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    public static void showMainMenu() {
        System.out.println(ConsoleColors.BLUE + "\nPlease select an option: " + ConsoleColors.RESET);
        for (String option : OPTIONS) {
            System.out.println("-> " + option);
        }
        menuButtons();
    }

    public static void menuButtons() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        switch (input) {
            case "add":
                add();
                break;
            case "remove":
                remove();
                break;
            case "list":
                list();
                break;
            case "exit":
                exit();
                break;
            default:
                System.out.println(ConsoleColors.RED + "Please select a correct option." + ConsoleColors.RESET);
                menuButtons();
                break;
        }
    }

    public static void add() {
        Scanner scanner = new Scanner(System.in);
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        System.out.println("Please enter the name of the task you would like to add:");
        String name = scanner.nextLine();
        System.out.println("Please enter the date (format YYYY-MM-DD):");
        String date = scanner.nextLine();
        System.out.println("Please enter if the task is done or not (true/false):");
        String bool = scanner.nextLine();
        tasks[tasks.length - 1] = new String[] {name, date, bool};
        showMainMenu();
    }

    public static void remove() {
        System.out.println("Please, enter the number of the task you would like to remove:");
        int number = -1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            try{
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
            }
            if (number >= 0 && number < tasks.length-1) {
                break;
            } else {
                System.out.println(ConsoleColors.RED + "Please, enter a number between 0 and " + (tasks.length - 1) + "." + ConsoleColors.RESET);
            }
        }
        tasks = ArrayUtils.remove(tasks, number);
        showMainMenu();
    }

    public static void list() {
        for (int i = 0; i < tasks.length; i++) {
            for (int j =0 ; j < 3; j++) {
                if (j < 2) {
                    System.out.print(ConsoleColors.RESET + tasks[i][j]+ ", ");
                } else {
                    System.out.print(ConsoleColors.RESET + tasks[i][j]);
                }
            }
            System.out.println();
        }
        showMainMenu();
    }
    public static void exit() {
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
            e.printStackTrace();
        }
        System.out.println(ConsoleColors.RED + "\nProgram terminated.");
        System.exit(0);
    }
    public static void retrievingData () {
        ArrayList<String> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tasks = new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < 3; j++) {
                String [] temp = list.get(i).split(",");
                tasks[i][j] = temp [j].trim();
            }
        }
    }

    public static void main(String[] args) {
        retrievingData();
        showMainMenu();
    }
}