package Kanban.service;

import Kanban.constants.Status;
import Kanban.constants.TaskType;
import Kanban.exceptions.ManagerSaveException;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager{
    private final Path path;

    public FileBackedTaskManager(File file) {
        this.path = file.toPath();
        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(file);
                 BufferedReader br = new BufferedReader(reader)) {
                while (br.ready()) {
                    String line = br.readLine();
                    if (line.isEmpty()) {
                        line = br.readLine();
                        if (line != null) {
                            for (Integer id : historyFromString(line)) {
                                if (tasks.containsKey(id))
                                    history.add(tasks.get(id));
                                else if (epics.containsKey(id))
                                    history.add(epics.get(id));
                                else if (subtasks.containsKey(id))
                                    history.add(subtasks.get(id));

                            }
                        }
                        continue;
                    }
                    Task task = taskFromString(line);
                    if (task != null) {
                        switch (task.getTaskType()) {
                            case TASK:
                                tasks.put(task.getid(), task);
                                taskid = task.getid();
                                break;
                            case EPIC:
                                epics.put(task.getid(), (Epic) task);
                                taskid = task.getid();
                                break;
                            case SUBTASK:
                                Subtask subtask = (Subtask) task;
                                taskid = task.getid();
                                subtasks.put(task.getid(), subtask);
                                epics.get(subtask.getEpicid()).addSubtask(subtask);
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    private static Task taskFromString(String s) {
        String[] data = s.split(",");
        switch (data[1]) {
            case "type":
            default:
                return null;
            case "TASK":
                Task task = new Task(
                        data[2],
                        data[4],
                        Integer.parseInt(data[0]),
                        Status.valueOf(data[3]),
                        TaskType.TASK
                );
                return task;
            case "EPIC":
                Epic epic = new Epic(
                        data[2],
                        data[4],
                        Integer.parseInt(data[0]),
                        Status.valueOf(data[3]),
                        TaskType.EPIC
                );
                return epic;
            case "SUBTASK":
                Subtask subtask = new Subtask(
                        data[2],
                        data[4],
                        Integer.parseInt(data[0]),
                        Status.valueOf(data[3]),
                        TaskType.SUBTASK,
                        Integer.parseInt(data[5])
                );
                return subtask;
        }
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        for (Object task : manager.getHistory()) {
            history.append(String.format("%d,", ((Task) task).getid()));
        }
        if (history.indexOf(",") > 0)
            return history.toString().substring(0,history.lastIndexOf(","));
        else
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
