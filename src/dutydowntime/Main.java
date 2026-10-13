package dutydowntime;

import java.util.Scanner;

public class Main
{
    private Scanner scanner;
    private DutyRecommendation controller;

    public Main()
    {
        scanner = new Scanner(System.in);
        controller = new DutyRecommendation();
    }

    public static void main(String[] args)
    {
        Main app = new Main();
        app.run();
    }

    public void run()
    {
        System.out.println("=========================================");
        System.out.println(" Welcome to Duty & Downtime Task Balancer ");
        System.out.println("=========================================");

        boolean running = true;
        while (running)
        {
            printMenu();
            int choice = readMenuChoice();
            switch (choice)
            {
                case 1:
                    handleAddAcademic();
                    break;
                case 2:
                    handleAddHobby();
                    break;
                case 3:
                    System.out.println("\n" + controller.toString());
                    break;
                case 4:
                    handleComplete();
                    break;
                case 5:
                    handleRemove();
                    break;
                case 6:
                    System.out.println("\n" + controller.getNextRecommendation());
                    break;
                case 7:
                    handleSearch();
                    break;
                case 8:
                    System.out.println("Exiting Duty & Downtime. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("[Error] Selection out of range.");
                    break;
            }
        }
    }

    private void printMenu()
    {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Academic Task");
        System.out.println("2. Add Hobby Task");
        System.out.println("3. View All Tasks");
        System.out.println("4. Complete a Task");
        System.out.println("5. Remove a Task");
        System.out.println("6. Get Work-Then-Play Recommendation");
        System.out.println("7. Search Tasks (Stretch Goal)");
        System.out.println("8. Exit");
        System.out.print("Select an option (1-8): ");
    }

    private void handleAddAcademic()
    {
        System.out.println("\n[Adding Academic Task]");
        String course = readNonBlank("Enter Course Code (e.g., CS 2114): ");
        String name = readNonBlank("Enter Assignment Name: ");
        String date = readNonBlank("Enter Due Date (e.g., 10/15): ");
        double hours = readPositiveHours("Enter Estimated Hours: ");

        try
        {
            controller.addAcademicTask(course, name, date, hours);
            System.out.println("[Success] Academic task recorded.");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("[Input Error] " + e.getMessage());
        }
    }

    private void handleAddHobby()
    {
        System.out.println("\n[Adding Hobby Task]");
        String cat = readNonBlank("Enter Hobby Category (e.g., Fitness): ");
        String desc = readNonBlank("Enter Activity Description: ");
        String goal = readNonBlank("Enter Session Goal: ");
        double hours = readPositiveHours("Enter Target Hours: ");

        try
        {
            controller.addHobbyTask(cat, desc, hours, goal);
            System.out.println("[Success] Hobby task recorded.");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("[Input Error] " + e.getMessage());
        }
    }

    private void handleComplete()
    {
        System.out.println("\nWhich task category do you want to complete?");
        System.out.println("1. Academic Task");
        System.out.println("2. Hobby Task");
        int catChoice = readIntRange("Select (1-2): ", 1, 2);

        if (catChoice == 1)
        {
            if (controller.getAcademicCount() == 0)
            {
                System.out.println("[Notice] Academic list is empty.");
                return;
            }
            int pos = readPosition(controller.getAcademicCount());
            System.out.println(controller.completeAcademicTask(pos));
        }
        else
        {
            if (controller.getHobbyCount() == 0)
            {
                System.out.println("[Notice] Hobby list is empty.");
                return;
            }
            int pos = readPosition(controller.getHobbyCount());
            System.out.println(controller.completeHobbyTask(pos));
        }
    }

    private void handleRemove()
    {
        System.out.println("\nWhich task category do you want to remove from?");
        System.out.println("1. Academic Task");
        System.out.println("2. Hobby Task");
        int catChoice = readIntRange("Select (1-2): ", 1, 2);

        if (catChoice == 1)
        {
            if (controller.getAcademicCount() == 0)
            {
                System.out.println("[Notice] Academic task list is empty.");
                return;
            }
            String course = readNonBlank("Enter Course Code: ");
            String name = readNonBlank("Enter Assignment Name: ");
            boolean removed = controller.removeAcademicTask(course, name);
            if (removed)
            {
                System.out.println("[Success] Task removed from chain.");
            }
            else
            {
                System.out.println("[Notice] No matching task found.");
            }
        }
        else
        {
            if (controller.getHobbyCount() == 0)
            {
                System.out.println("[Notice] Hobby task list is empty.");
                return;
            }
            String cat = readNonBlank("Enter Category: ");
            String desc = readNonBlank("Enter Activity Description: ");
            boolean removed = controller.removeHobbyTask(cat, desc);
            if (removed)
            {
                System.out.println("[Success] Hobby removed from chain.");
            }
            else
            {
                System.out.println("[Notice] No matching task found.");
            }
        }
    }

    private void handleSearch()
    {
        String query = readNonBlank("Enter search keyword: ");
        System.out.println("\n" + controller.searchTasks(query));
    }

    private int readMenuChoice()
    {
        while (true)
        {
            String line = scanner.nextLine().trim();
            try
            {
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= 8)
                {
                    return choice;
                }
                System.out.print("[Error] Please enter a choice (1-8): ");
            }
            catch (NumberFormatException e)
            {
                System.out.print(
                    "[Error] Invalid input. Please enter a number (1-8): ");
            }
        }
    }

    private int readIntRange(String prompt, int min, int max)
    {
        System.out.print(prompt);
        while (true)
        {
            String line = scanner.nextLine().trim();
            try
            {
                int val = Integer.parseInt(line);
                if (val >= min && val <= max)
                {
                    return val;
                }
                System.out.print(
                    "[Error] Choice out of range. Enter ("
                    + min + "-" + max + "): ");
            }
            catch (NumberFormatException e)
            {
                System.out.print("[Error] Please enter a valid number: ");
            }
        }
    }

    private int readPosition(int listSize)
    {
        System.out.print("Enter item number (1-" + listSize + "): ");
        while (true)
        {
            String line = scanner.nextLine().trim();
            try
            {
                int val = Integer.parseInt(line);
                if (val >= 1 && val <= listSize)
                {
                    return val - 1;
                }
                System.out.print(
                    "[Error] Number outside list bounds. Enter (1-"
                    + listSize + "): ");
            }
            catch (NumberFormatException e)
            {
                System.out.print("[Error] Invalid integer. Please try again: ");
            }
        }
    }

    private double readPositiveHours(String prompt)
    {
        System.out.print(prompt);
        while (true)
        {
            String line = scanner.nextLine().trim();
            try
            {
                double val = Double.parseDouble(line);
                if (val > 0.0)
                {
                    return val;
                }
                System.out.print(
                    "[Input Error] Hours must be strictly greater than 0.0. "
                    + "Re-enter: ");
            }
            catch (NumberFormatException e)
            {
                System.out.print(
                    "[Input Error] Invalid number format. Re-enter hours: ");
            }
        }
    }

    private String readNonBlank(String prompt)
    {
        System.out.print(prompt);
        while (true)
        {
            String line = scanner.nextLine();
            if (line != null && !line.trim().isEmpty())
            {
                return line.trim();
            }
            System.out.print(
                "[Input Error] Field cannot be blank. Please try again: ");
        }
    }
}
