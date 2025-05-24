package taskmanager;

import java.util.Scanner;
import static taskmanager.TaskLinkedList.isDigitsOnly;

public class TaskManagerService {

    public static TaskLinkedListStack deletedTasksStack = new TaskLinkedListStack(50); // creating an instance of the stack to store the deleted tasks

    // searching a Task
    public static void findTask(TaskLinkedList taskList, Scanner scanner) {
        System.out.print("Enter Task ID or Title: ");
        String input = scanner.nextLine(); // reading the user input

        try {
            int id = Integer.parseInt(input); // tries to parse the input as an integer (ID)
            Task foundTask = taskList.findTaskById(id); // calling the findTaskById method
            if (foundTask != null) { // if the task was found
                System.out.println("Found Task: " + foundTask); // it will get printed
            } else {
                System.out.println("Task with ID " + id + " not found."); // otherwise the not found message.
            }
        } catch (NumberFormatException e) { // if the input is not an integer this error will be raised
            String upperInput = input.toUpperCase(); // converts the user input to upper case
            Task foundTask = taskList.findTaskByTitle(upperInput); // findTaskByTitle is called,if parsing fails
            if (foundTask != null) { // if the task was found
                System.out.println("Found Task: " + foundTask); // it will get printed
            } else {
                System.out.println("Task with title " + input + " not found."); // otherwise the not found message.
            }
        }
    }

    // creating the Task object and adding that
    public static void addTask(TaskLinkedList taskList, Scanner scanner) {
        String addMore; // to be used in the loop to add tasks continuously
        do {
            //Task ID
            String iD = "";
            boolean validID = false;
            int iDInt = 0; //iDint variable is created to convert the user input to integer

            while (!validID) {
                System.out.println(); //empty line for spacing
                System.out.print("Enter Task ID: ");
                iD = scanner.nextLine(); // getting the ID from the user
                if (isDigitsOnly(iD)) { // validate the input contains only numbers
                    try {
                        iDInt = Integer.parseInt(iD); //parse the ID to integer
                        if (taskList.findTaskById(iDInt) == null) { //check for duplicate IDs
                            validID = true;
                        } else {
                            System.out.println("Task with ID " + iDInt + " already exists. Please enter new one.");
                        }
                    } catch (NumberFormatException e) { //error handling to prevent very large values
                        System.out.println("Invalid ID format. Try again avoiding large values.");
                    }
                } else {
                    //handles the invalid formats like whitespaces and special characters
                    System.out.println("Invalid ID format.Try again avoiding special characters, whitespaces and letters.");
                }
            }

            //Task Title
            System.out.print("Enter Task Title: ");
            String title = scanner.nextLine().toUpperCase(); //getting the title from the user and converting it to uppercase


            //Task Priority
            String priorityStr = ""; //initializing an empty variable to be used in validation process
            boolean validPriority = false;
            Task.Priority priority = null; // Declare priority here

            while (!validPriority) {
                System.out.print("Enter Task Priority (HIGH, MEDIUM, LOW): ");
                priorityStr = scanner.nextLine().toUpperCase(); // Converting the user input to upper case

                try {
                    priority = Task.Priority.valueOf(priorityStr); //converting the String to enum
                    validPriority = true;
                } catch (IllegalArgumentException e) { //handling invalid inputs
                    System.out.println("Invalid input. Please enter either HIGH, MEDIUM or LOW.");
                }
            }

            //Creating a new task object with validated input.
            Task newTask = new Task(iDInt, title, priority, false); //default Status is false since the task is new
            taskList.addTask(newTask);
            System.out.println("\nTask with ID " + iDInt + " added.");
            System.out.println("Enter U to Undo.");

            System.out.println(); //empty line for spacing
            System.out.print("Do you want to add another task? [Y for yes / N for no / U for undo]: ");
            String answer; // a string variable to check the validations
            do {
                answer = scanner.nextLine().toUpperCase();
                if (!answer.equals("Y") && !answer.equals("N") && !answer.equals("U")) { // checking if user input contains anything other than Y or N or U
                    System.out.println("Invalid input. Please enter either Y or N or U.");
                    System.out.println(); //empty line for spacing
                    System.out.print("Do you want to add another task? [Y for yes / N for no / U for undo]: ");
                }
            } while (!answer.equals("Y") && !answer.equals("N") && !answer.equals("U")); // loop will continue as long as user input not Y AND not N AND not U

            if (answer.equals("U")) {
                taskList.deleteLastTask();
                System.out.println("\nLast added Task with ID " + iDInt + " deleted.");
                addMore = "Y";
            } else {
                addMore = answer; // if user enter Y or N user input is assigned to addMore
            }

        } while (addMore.equals("Y")); // the loop will run as long as user enter Y
        System.out.println("Finished adding task(s)."); // after the loop ends
    }

    //Deleting a Task
    public static void deleteTask(TaskLinkedList taskList, Scanner scanner) {
        String deleteMore;

        do {
            String inputId;
            boolean validId = false;
            int id = 0; // Initialize id

            //validating process
            while (!validId) {
                System.out.println(("\nEnter M to return to main menu"));
                System.out.println("Enter Task ID to delete");
                System.out.print("Your choice: ");
                inputId = scanner.nextLine().toUpperCase();

                if (inputId.equals("M")) {
                    System.out.println("Returning to main menu.");
                    return;
                } else if (isDigitsOnly(inputId)) { //validate the input contains only numbers
                    try {
                        id = Integer.parseInt(inputId);
                        validId = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            }

            //deleting process
            Task taskToDelete = taskList.findTaskById(id); //Retrieve Task object by ID assigning it to taskToDelete variable  .
            if (taskToDelete != null) {
                deletedTasksStack.push(new Task(taskToDelete.getId(), taskToDelete.getTitle(), taskToDelete.getPriority(), taskToDelete.isCompleted())); // pushing the task to the stack
                taskList.deleteTask(taskToDelete); //deleting the task
                System.out.println("\nTask with ID " + id + " deleted.");
                System.out.println("Enter U to Undo.");
            } else {
                System.out.println("\nTask with ID " + id + " not found.");
            }

            System.out.println();
            System.out.println("Do you want to delete another task? [Y for yes / N for no / U for undo]: ");
            String answer_2;

            do {
                answer_2 = scanner.nextLine().toUpperCase();
                if (!answer_2.equals("Y") && !answer_2.equals("N") && !answer_2.equals("U")) {
                    System.out.println("Invalid input. Please enter either Y or N or U.");
                    System.out.println();
                    System.out.print("Do you want to delete another task? [Y for yes / N for no / U for undo]: ");
                }
            } while (!answer_2.equals("Y") && !answer_2.equals("N") && !answer_2.equals("U"));

            if (answer_2.equals("U")) {
                Task restoredTask = deletedTasksStack.pop(); // Pop the task from the stack
                if (restoredTask != null) {
                    taskList.addTask(restoredTask); // Add it back to the linked list
                    System.out.println("Last deleted task restored: " + restoredTask);
                } else {
                    System.out.println("No tasks to undo.");
                }
                deleteMore = "Y"; // Allow the user to continue deleting or undoing
            } else {
                deleteMore = answer_2;
            }
            validId = false;

        } while (deleteMore.equals("Y")); // the loop will run as long as user enter Y
        System.out.println("Finished deleting task(s)."); // after the loop ends
    }

    //Marking a Task as completed
    public static void markTaskCompleted(TaskLinkedList taskList, Scanner scanner) {
        String inputId;
        boolean validId = false;
        int id = 0;

        while (!validId) {
            System.out.println(("\nEnter M to return to main menu"));
            System.out.println("Enter Task ID to mark completed");
            System.out.print("Your choice: ");
            inputId = scanner.nextLine().toUpperCase();

            if (inputId.equals("M")) {
                System.out.println("Returning to main menu.");
                return;
            } else if (isDigitsOnly(inputId)) {
                try {
                    id = Integer.parseInt(inputId);
                    validId = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        taskList.markTaskCompleted(id);
    }
}
