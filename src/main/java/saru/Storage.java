package saru;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String FILE_PATH = "data/saru.txt";

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();

        try {
            Path path = Paths.get(FILE_PATH);

            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                return tasks;
            }

            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            while ((line = br.readLine()) != null) {
                tasks.add(parseTask(line));
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Failed to load data.");
        }

        return tasks;
    }

    public void save(List<Task> tasks) {
        assert tasks != null : "tasks list should not be null";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task t : tasks) {
                bw.write(t.toFileString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to save data.");
        }
    }

    private Task parseTask(String line) {
        assert line != null : "line should not be null";
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Expected at least 3 fields";
        assert parts[0] != null && parts[0].length() == 1 : "Invalid task type field";
        assert parts[1].equals("0") || parts[1].equals("1") : "Invalid done flag";
        char type = parts[0].charAt(0);
        boolean done = parts[1].equals("1");

        Task task;
        switch (type) {
            case 'T':
                task = new Todo(parts[2]);
                break;
            case 'D':
                task = new Deadline(parts[2], LocalDate.parse(parts[3]));
                break;
            case 'E':
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Corrupted file");
        }

        if (done) {
            task.markDone();
        }
        return task;
    }
}
