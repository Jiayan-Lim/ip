package duke;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the file to store the task list.
 */
public class Storage {
    private final String filePath;
    private final Path path;

    /**
     * Constructor to create a storage.
     *
     * @param filepath string representation of the path.
     * @throws DukeFileNotFoundException indicate attempt to open the file denoted by a specified pathname has failed.
     */
    public Storage(String filepath) throws DukeFileNotFoundException {
        try {
            this.filePath = filepath;
            this.path = Path.of(filepath);
        } catch (InvalidPathException e) {
            throw new DukeFileNotFoundException("File is not found!");
        }
    }

    /**
     * Set a default storage if the file or folder is not found.
     */
    protected static void setDefaultStorage() {
        File folder = new File("data/");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File("data/duke.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("IO Exception occur");
            }
        }
    }

    /**
     * Insert new task into the file.
     *
     * @param t task to be inserted.
     * @throws DukeIOException indicate failed or interrupted I/O operations occurred.
     */
    protected void updateData(Task t) throws DukeIOException {
        try {
            List<String> allLine =  Files.readAllLines(path);
            String s = t.toString().charAt(1) + " | " + t.getStatusIcon() + " | " + t.description;
            if (t instanceof Deadlines) {
                Deadlines d =  (Deadlines) t;
                s += " | " + d.getBy();
            } else if (t instanceof Events) {
                Events e = (Events) t;
                s += " | " + e.getStart() + " | " + e.getEnd();
            }
            allLine.add(s);
            Files.write(path, allLine);
        } catch (IOException e) {
            throw new DukeIOException("Cannot read from " + filePath + " data file");
        }
    }

    /**
     * Update the task in the file with its new state.
     *
     * @param lineNumber line number in the file to be toggled.
     * @param status status of file that indicate whether the task is done or not.
     * @throws DukeIOException indicate failed or interrupted I/O operations occurred.
     */
    protected void updateData(int lineNumber, int status) throws DukeIOException {
        try {
            List<String> allLine = Files.readAllLines(path);

            // overwrite the duke.txt file
            String line = allLine.get(lineNumber);
            String s = line.substring(0, 4) + status + line.substring(5);
            allLine.set(lineNumber, s);

            Files.write(path, allLine);
        } catch (IOException e) {
            throw new DukeIOException("Cannot read from " + filePath + " data file");
        }
    }

    /**
     * Remove specific task from the file.
     *
     * @param lineNumber line number in the file to be deleted.
     * @throws DukeIOException indicate failed or interrupted I/O operations occurred.
     */
    protected void removeData(int lineNumber) throws DukeIOException {
        try {
            List<String> allLine = Files.readAllLines(path);

            // remove the line from duke.txt
            allLine.remove(lineNumber);
            Files.write(path, allLine);
        } catch (IOException e) {
            throw new DukeIOException("Cannot read from " + filePath + " data file");
        }
    }

    /**
     * Load the task from the file at the beginning of the program execution.
     *
     * @return list of task that stored in the file before.
     * @throws DukeIOException indicate failed or interrupted I/O operations occurred.
     * @throws DukeInvalidArgumentException indicate that a command has been passed an illegal argument.
     */
    protected ArrayList<Task> load() throws DukeIOException, DukeInvalidArgumentException {
        ArrayList<Task> taskList= new ArrayList<>();
        try {
            List<String> allLine = Files.readAllLines(path);

            if (allLine.isEmpty()) {
                return taskList;
            }

            for (String taskDescription : allLine) {
                String[] s = taskDescription.split(" \\| ");

                Task t = null;
                boolean isDone = s[1].equals("1");
                switch (s[0]) {
                case "T":
                    t = new ToDos(s[2]);
                    break;
                case "D":
                    t = new Deadlines(s[2], s[3]);
                    break;
                case "E":
                    t = new Events(s[2], s[3], s[4]);
                    break;
                }
                t.setDone(isDone);
                taskList.add(t);
            }

            return taskList;
        } catch (IOException e) {
            throw new DukeIOException("Cannot read from " + filePath + " data file");
        }
    }

    protected TaskList findDataFromFile(String keyword) throws DukeIOException, DukeInvalidArgumentException {
        TaskList result = new TaskList();
        try {
            List<String> allLine = Files.readAllLines(path);

            if (allLine.isEmpty()) {
                return result;
            }

            for (String taskDescription: allLine) {
                if (taskDescription.contains(keyword)) {

                    String[] s = taskDescription.split(" \\| ");

                    Task task = null;
                    boolean isDone = s[1].equals("1");
                    switch (s[0]) {
                    case "T":
                        task = new ToDos(s[2]);
                        break;
                    case "D":
                        task = new Deadlines(s[2], s[3]);
                        break;
                    case "E":
                        task = new Events(s[2], s[3], s[4]);
                        break;
                    }
                    task.setDone(isDone);
                    result.add(task);
                }
            }

            return result;
        } catch (IOException e) {
            throw new DukeIOException("Cannot read from " + filePath + " data file");
        }
    }

    /**
     * Empty all the task that stored in the file.
     *
     * @throws DukeIOException indicate failed or interrupted I/O operations occurred.
     */
    protected void emptyStorage() throws DukeIOException {
        try {
            List<String> emptyLine = new ArrayList<>();
            Files.write(path, emptyLine);
        } catch (IOException e) {
            throw new DukeIOException("Cannot read from " + filePath + " data file");
        }
    }
}
