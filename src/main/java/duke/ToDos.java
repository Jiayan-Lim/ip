package duke;

/**
 * ToDo type of task.
 */
public class ToDos extends Task{
    /**
     * Constructor to create a ToDos task.
     * @param description
     */
    public ToDos(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
