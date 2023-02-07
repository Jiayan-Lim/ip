package duke;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int LINE_NUMBER;

    /**
     * Constructor to create a mark command.
     *
     * @param fullCommand User input command.
     * @throws DukeEmptyArgumentException indicate that a command has been passed an empty argument.
     * @throws DukeInvalidArgumentException indicate that a command has been passed an illegal argument.
     */
    public MarkCommand(String[] fullCommand) throws DukeEmptyArgumentException, DukeInvalidArgumentException {
        try {
            LINE_NUMBER = Integer.parseInt(fullCommand[1]) - 1;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeEmptyArgumentException("The description of mark command cannot be empty.");
        } catch (NumberFormatException e) {
            throw new DukeInvalidArgumentException("Delete command require integer to execute!");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public String execute(TaskList task, Ui ui, Storage storage) throws DukeIoException, DukeInvalidArgumentException {
        if (LINE_NUMBER >= task.size()) {
            throw new DukeInvalidArgumentException("There are only " + task.size()
                    + " tasks in list, but want to mark " + (LINE_NUMBER + 1) + "th task.");
        }
        Task t = task.getTaskAt(LINE_NUMBER);

        t.setDone(true);
        storage.updateData(LINE_NUMBER, 1);
        return ui.responseToMarkTaskCommand(t);
    }
}
