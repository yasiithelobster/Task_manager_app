package taskmanager;

public class Task {
    private int id;
    private String title;
    private Priority priority;
    private boolean completed;

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    // constructor to initialize variables when a new task object is created
    public Task(int id, String title, Priority priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.completed = completed;
    }

    //getter method to access the value of id
    public int getId() {
        return id;
    }

    //getter method to access the title
    public String getTitle() {
        return title;
    }

    //getter method to access the priority
    public Priority getPriority() {
        return priority;
    }

    // setter method to change the status
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    //getter method to access the status of the task
    public boolean isCompleted() {
        return completed;
    }

    // method to represent the task object as a String
    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Priority: " + priority + " | Completed? " + completed;
    }
}
