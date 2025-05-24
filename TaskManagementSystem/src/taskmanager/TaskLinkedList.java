package taskmanager;

public class TaskLinkedList {
    public Node head;

    public TaskLinkedList() { // initializes a new empty linked list
        this.head = null;
    }

    //method to print Tasks
    public void printTasks() {
        Node current = head; //temporary pointer is created and pointed it to the head
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    //Sorting Tasks

    // creating a duplicate of the original list for sorting
    public TaskLinkedList TaskLinkedListCopy() {
        TaskLinkedList newList = new TaskLinkedList(); // creating the newList object
        Node current = this.head; // assigning the temporary variable to the head of the original list
        while (current != null) {
            newList.addTask(current.data); // adding to the new list
            current = current.next;
        }
        return newList;
    }

    // Method to get a new linked list sorted by priority
    public TaskLinkedList getSortedByPriority() {
        TaskLinkedList sortedList = this.TaskLinkedListCopy(); // creates a new object names sorted list and assigns a duplicate list to that
        sortedList.head = sortedList.mergeSortByPriority(sortedList.head);
        return sortedList;
    }

    //Merge sort method to sort Tasks by priority
    private Node mergeSortByPriority(Node head) {
        if (head == null || head.next == null) { // check if the list is empty or has only one task
            return head;
        }

        Node middle = getMiddle(head); // finding the middle and splitting the set to two subsets
        Node nextOfMiddle = middle.next;
        middle.next = null;

        Node left = mergeSortByPriority(head); // calls mergeSortByPriority for left subset
        Node right = mergeSortByPriority(nextOfMiddle); // calls mergeSortByPriority for right subset

        return mergeByPriority(left, right); // merging two sorted subsets
    }

    private Node mergeByPriority(Node left, Node right) { // merges two already sorted linked lists into one
        Node result = null; // initializes the head
        Node tail = null; // initializes the pointer

        while (left != null && right != null) { // loops until the sub sets are empty
            if (left.data.getPriority().compareTo(right.data.getPriority()) <= 0) { //checks if the priority of the task at the head of the left sub set is less than or equal to the priority of the task at the head of the right sub-list.
                if (result == null) {
                    result = left;
                } else {
                    tail.next = left;
                }
                tail = left;
                left = left.next;
            } else {
                if (result == null) {
                    result = right;
                } else {
                    tail.next = right;
                }
                tail = right;
                right = right.next;
            }
        }

        if (left != null) {
            if (result == null) {
                result = left;
            } else {
                tail.next = left;
            }
        }

        if (right != null) {
            if (result == null) {
                result = right;
            } else {
                tail.next = right;
            }
        }
        return result;
    }

    // Method to get a new linked list sorted by ID
    public TaskLinkedList getSortedByID() {
        TaskLinkedList sortedList = this.TaskLinkedListCopy();
        sortedList.head = sortedList.mergeSortById(sortedList.head);
        return sortedList;
    }

    //Merge sort method to sort Tasks by ID
    public void mergeSortById() {
        head = mergeSortById(head);
    }

    private Node mergeSortById(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.next;
        middle.next = null;

        Node left = mergeSortById(head);
        Node right = mergeSortById(nextOfMiddle);

        return mergeById(left, right);
    }

    private Node mergeById(Node left, Node right) {
        Node result = null;
        Node tail = null;

        while (left != null && right != null) {
            if (left.data.getId() <= right.data.getId()) {
                if (result == null) {
                    result = left;
                } else {
                    tail.next = left;
                }
                tail = left;
                left = left.next;
            } else {
                if (result == null) {
                    result = right;
                } else {
                    tail.next = right;
                }
                tail = right;
                right = right.next;
            }
        }

        if (left != null) {
            if (result == null) {
                result = left;
            } else {
                tail.next = left;
            }
        }

        if (right != null) {
            if (result == null) {
                result = right;
            } else {
                tail.next = right;
            }
        }

        return result;
    }

    private Node getMiddle(Node head) {
        if (head == null) {
            return head;
        }

        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    //Method to search a task by ID
    public Task findTaskById(int id) {
        Node current = head; // current is assigned to head
        while (current != null) { // loops until the current.next is null
            if (current.data.getId() == id) {
                return current.data;
            }
            current = current.next; // current is assigned with current.next
        } return null; // Task not found
    }

    //Method to search a task by title
    public Task findTaskByTitle(String title) {
        Node current = head;
        while (current != null) {
            if (current.data.getTitle().equals(title)) {
                return current.data;
            }
            current = current.next;
        }
        return null; // Task not found
    }

    //method to add a Task
    public void addTask(Task task) {
        Node newNode = new Node(task); // creates a new node called newNode and assigns the task to it
        if (head == null) {
            head = newNode;
        }
        else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    //method to delete a Task
    public void deleteTask(Task task) {
        if (head.data.equals(task)) {
            head = head.next;
            return;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(task)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    //method to delete the last Task
    public void deleteLastTask() {
        if (head.next == null) { // check if there is only one task
            head = null;
            return;
        }

        Node current = head;
        while (current.next.next != null) { // Traverse to second-to-last node
            current = current.next;
        }
        current.next = null; // Remove last node
    }

    //Method to mark a task as completed
    public void markTaskCompleted(int id) {
        Node current = head;
        while (current != null) {
            if (current.data.getId() == id) {
                current.data.setCompleted(true);
                System.out.println("Task marked as completed.");
                return;
            }
            current = current.next;
        }
        System.out.println("\nTask with ID " + id + " not found.");
    }

    //method to check if the user input contains only digits
    public static boolean isDigitsOnly(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
