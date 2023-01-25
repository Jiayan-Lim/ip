package duke;

/**
 * Parser to parse user input.
 */
public class Parser {
    /**
     * Parse the user input command and response it correspondingly.
     *
     * @param input user input command
     * @return corresponding command given a specific user input.
     * @throws DukeUnknownCommandException indicate that an unknown command has been passed.
     * @throws DukeEmptyArgumentException indicate that a command has been passed an empty argument.
     * @throws DukeInvalidArgumentException indicate that a command has been passed an illegal argument.
     */
    public static Command parse(String input) throws DukeUnknownCommandException,
            DukeEmptyArgumentException, DukeInvalidArgumentException {
        String[] split = input.split(" ", 2);
        String cmd = split[0];
        Command c;

        switch (cmd) {
        case "exit":
            c = new ExitCommand();
            break;
        case "list":
            c = new ListCommand();
            break;
        case "delete":
            c = new DeleteCommand(split);
            break;
        case "mark":
            c = new MarkCommand(split);
            break;
        case "unmark":
            c = new UnmarkCommand(split);
            break;
        case "todo":
        case "deadline":
        case "event":
            c = new AddCommand(split);
            break;
        case "find":
            c = new FindCommand(split);
            break;
        default:
            throw new DukeUnknownCommandException("I'm sorry, but I don't know what that means :-(");
        }

        return c;
    }
}
