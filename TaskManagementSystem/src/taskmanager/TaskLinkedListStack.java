package taskmanager;

public class TaskLinkedListStack {
    private int maxSize;
    private int top;
    private Task[] stackArray;

    public TaskLinkedListStack(int max) {
        maxSize = max;
        top = -1;
        stackArray = new Task [maxSize]; // initializes the array that will be used to store the tasks
    }

    public void push(Task task) {
        if (top == maxSize - 1) {
            System.out.println("Stack Overflow! Cannot push task.");
            return;
        }
        stackArray[++top] = task;
    }

    public Task pop() {
        if (top == -1) {
            System.out.println("Stack Underflow! Cannot pop element.");
            return null;
        }
        Task poppedElement = stackArray[top];
        stackArray[top] = null; 
        top--;
        return poppedElement;
    }
}

