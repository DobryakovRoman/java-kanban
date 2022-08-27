package Kanban.service;

import Kanban.exceptions.ManagerSaveException;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager{
    private final Path path;

    public FileBackedTaskManager(File file) {
        this.path = Paths.get(file.getPath());
        //this.path = Paths.get("data.csv");
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Subtask getSubtaskByid(int id) {
        Subtask subtask = super.getSubtaskByid(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        super.clear();
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    private void save() throws ManagerSaveException {
        try (Writer writer = new FileWriter(path.toString())) {
            if (Files.exists(path)) Files.delete(path);
            if (Files.exists(Paths.get(path.toString()))) {
                writer.write("id,type,name,status,description,epic\n");
                for (Task task : tasks.values()) {
                    writer.write(taskToString(task));
                }
                for (Epic epic : epics.values()) {
                    writer.write(taskToString(epic));
                    if (epic.getSubtasks() != null) {
                        for (Integer subtaskId : epic.getSubtasks()) {
                            Subtask subtask = subtasks.get(subtaskId);
                            writer.write(taskToString(subtask));
                        }
                    }
                }
                writer.write("\n");
                writer.write(historyToString(history));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить информацию.");
        }
    }

    private static String taskToString(Task task) {
        switch (task.getTaskType()) {
            case TASK:
            case EPIC:
            default:
                return String.format("%d,%s,%s,%s,%s\n",
                        task.getid(),
                        task.getTaskType().toString(),
                        task.getTitle(),
                        task.getStatus().toString(),
                        task.getDescription()
                );
            case SUBTASK:
                return String.format("%d,%s,%s,%s,%s,%d\n",
                        task.getid(),
                        task.getTaskType().toString(),
                        task.getTitle(),
                        task.getStatus().toString(),
                        task.getDescription(),
                        ((Subtask) task).getEpicid()
                );
        }
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        for (Object task : manager.getHistory()) {
            history.append(String.format("%d,", ((Task) task).getid()));
        }
        return history.toString();
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        for (String s : value.split(",")) {
            history.add(Integer.parseInt(s));
        }
        return history;
    }
}
