package taskmanager;

import java.util.Scanner;
import static taskmanager.TaskLinkedList.isDigitsOnly;

public class TaskManagerApp {
    public static void main(String[] args) {
        TaskLinkedList taskList = new TaskLinkedList(); // creating an instance of the linked list to store tasks
        Scanner scanner = new Scanner(System.in); // scanner object to read inputs
        String choice_str; // a String variable to handle validation
        int choice = 0; //initializing choice to 0 as an integer to make sure the loop will run at least once

        do {
            System.out.println("\n--------------------------");
            System.out.println("| Task Management System |");
            System.out.println("--------------------------");

            // main menu options
            System.out.println("\n 1. View Task List");
            System.out.println(" 2. Search a Task by ID or Title");
            System.out.println(" 3. Add Tasks");
            System.out.println(" 4. Delete Tasks");
            System.out.println(" 5. Mark a Task as completed");
            System.out.println(" 6. Exit");
            System.out.print("\nEnter your choice: ");

            choice_str = scanner.nextLine(); // reading input as a string
            if (isDigitsOnly(choice_str)) { // checking if the input contains only digits. process will continue if isDigitsOnly return TRUE
                try { // try catch block to handle empty inputs
                    choice = Integer.parseInt(choice_str); // converting the input as an integer to continue processing
                    switch (choice) {
                        case 1:
                            if (taskList.head != null) { // checking if the list is not empty
                                String choice_2_str; // a String variable to handle validation
                                int choice_2 = 0; //initializing choice_2 to 0 as an integer to make sure the loop will run at least once
                                System.out.println("\nCurrent Tasks:");
                                taskList.printTasks();
                                do {
                                    System.out.println("\n1.Sort Tasks by Priority: ");
                                    System.out.println("2.Sort Tasks by ID: ");
                                    System.out.println("3.Back to Main Menu: ");
                                    System.out.print("Enter your choice: ");

                                    choice_2_str = scanner.nextLine(); // reading input as a string

                                    if (isDigitsOnly(choice_2_str)) { // checking if the input contains only digits
                                        try {
                                            choice_2 = Integer.parseInt(choice_2_str);
                                            switch (choice_2) {
                                                case 1:
                                                    TaskLinkedList sortedByPriority = taskList.getSortedByPriority();
                                                    System.out.println("\nTasks sorted by Priority:");
                                                    sortedByPriority.printTasks();
                                                    break;
                                                case 2:
                                                    TaskLinkedList sortedByID = taskList.getSortedByID();
                                                    System.out.println("\nTasks sorted by ID:");
                                                    sortedByID.printTasks();
                                                    break;
                                                case 3:
                                                    break;
                                                default:
                                                    System.out.println("Invalid choice. Please enter 1, 2 or 3.");
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                                        }
                                    } else {
                                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                                    }
                                } while (choice_2 != 3 || !isDigitsOnly(choice_2_str));
                                /* The loop will continue as long as:
                                 * - The input does NOT contain only digits, OR
                                 * - The input is NOT equal to "3".
                                 *
                                 * The loop will ONLY stop when:
                                 * - The input CONTAINS only digits AND
                                 * - The input IS equal to "3".
                                 */
                            } else {
                                System.out.println("\n -- No tasks to display. List is empty. --");
                            }
                            break;
                        case 2:
                            if (taskList.head != null){
                                TaskManagerService.findTask(taskList, scanner);
                            } else {
                                System.out.println("\n -- No tasks to search. List is empty. --");
                            }
                            break;
                        case 3:
                            TaskManagerService.addTask(taskList, scanner);
                            break;
                        case 4:
                            if (taskList.head != null){
                                System.out.println("\nCurrent Tasks");
                                taskList.printTasks();
                                TaskManagerService.deleteTask(taskList, scanner);
                            } else {
                                System.out.println("\n -- No tasks to delete. List is empty. --");
                            }
                            break;
                        case 5:
                            if (taskList.head != null){
                                System.out.println("\nCurrent Tasks (according to priority):");
                                TaskLinkedList sortedByPriority = taskList.getSortedByPriority();
                                sortedByPriority.printTasks();
                                TaskManagerService.markTaskCompleted(taskList, scanner);
                            } else {
                                System.out.println("\n -- No tasks to mark. List is empty. --");
                            }
                            break;
                        case 6:
                            System.out.println("Exiting Task Management System.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6 || !isDigitsOnly(choice_str)); // will continue as long as the choice is not 6 OR the input is not only digits
          scanner.close();
    }
}