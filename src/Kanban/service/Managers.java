package Kanban.service;

import Kanban.task.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager<Task> getDefaultHistory() {
        return new InMemoryHistoryManager<>();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        try {
            if (Files.exists(file.toPath())){
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                while (br.ready()) {

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskManager;
    }
}
