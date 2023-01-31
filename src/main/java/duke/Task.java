package duke;

/**
 * Task that given by the user and was recorded in the database.
 */
public class Task {
    /**
     * Description of the task.
     */
    protected String description;
    /**
     * Status of the task, whether is has been done by the user or not.
     */
    protected boolean isDone;

    /**
     * Constructor to create a task.
     *
     * @param description description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Return 1 if the task has been marked as done, otherwise 0.
     *
     * @return 1 if the task is done, otherwise 0.
     */
    public String getStatusIcon() {
        return (isDone ? "1" : "0"); // mark done task with X
    }


    public String getDescription() {
        return description;
    }

    public void setDone(Boolean b) {
        isDone = b;
    }

    /**
     * String representation of the task instance.
     *
     * @return string representation of the task instance
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
