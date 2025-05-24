package taskmanager;

public class Node {
    Task data; //Task object as the data of the class
    Node next; //Reference to the next node in the list

    //Constructor
    public Node(Task data) {
        this.data = data; //Assigns the passed task to instance of data variable
        this.next = null; //Initialize the first node to null
    }
}
