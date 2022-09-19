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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager{
    private final Path path;

    public FileBackedTaskManager(File file) {
        path = file.toPath();
        load(path);
    }

    private void load(Path path) {
        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toFile());
                 BufferedReader br = new BufferedReader(reader)) {
                while (br.ready()) {
                    String line = br.readLine();
                    if (line.isEmpty()) {
                        line = br.readLine();
                        if (line != null) {
                            for (Integer id : historyFromString(line)) {
                                if (tasks.containsKey(id)) {
                                    history.add(tasks.get(id));
                                }
                                else if (epics.containsKey(id)) {
                                    history.add(epics.get(id));
                                }
                                else if (subtasks.containsKey(id)) {
                                    history.add(subtasks.get(id));
                                }
                            }
                        }
                        continue;
                    }
                    Task task = taskFromString(line);
                    if (task != null) {
                        if (task.getid() > taskid) {
                            taskid = task.getid();
                        }
                        switch (task.getTaskType()) {
                            case TASK:
                                tasks.put(task.getid(), task);
                                break;
                            case EPIC:
                                epics.put(task.getid(), (Epic) task);
                                break;
                            case SUBTASK:
                                Subtask subtask = (Subtask) task;
                                subtasks.put(task.getid(), subtask);
                                epics.get(subtask.getEpicid()).addSubtask(subtask);
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                throw new ManagerSaveException(e.getMessage());
            }
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public Subtask getSubtaskByid(int id) {
        Subtask subtask = super.getSubtaskByid(id);
        save();
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public void clear() {
        super.clear();
        save();
    }

    @Override
    public void clearTasks() {
        super.clearTasks();
        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }

    private void save() {
        try (Writer writer = new FileWriter(path.toString())) {
            if (Files.exists(Paths.get(path.toString()))) {
                writer.write("id,type,name,status,description,duration,startTime,epic\n");
                for (Task task : tasks.values()) {
                    writer.write(taskToString(task));
                }
                for (Epic epic : epics.values()) {
                    writer.write(taskToString(epic));
                }
                for (Subtask subtask : subtasks.values()) {
                    writer.write(taskToString(subtask));
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
            case EPIC:
                return String.format("%d,%s,%s,%s,%s,%d,%s,%s\n",
                        task.getid(),
                        task.getTaskType().toString(),
                        task.getTitle(),
                        task.getStatus().toString(),
                        task.getDescription(),
                        task.getDuration(),
                        (task.getStartTime() == null) ? "" : task.getStartTime().toString(),
                        (task.getEndTime() == null) ? "" : task.getEndTime().toString()
                );
            case TASK:
            default:
                return String.format("%d,%s,%s,%s,%s,%d,%s\n",
                        task.getid(),
                        task.getTaskType().toString(),
                        task.getTitle(),
                        task.getStatus().toString(),
                        task.getDescription(),
                        task.getDuration(),
                        (task.getStartTime() == null) ? "" : task.getStartTime().toString()
                );
            case SUBTASK:
                return String.format("%d,%s,%s,%s,%s,%d,%d,%s\n",
                        task.getid(),
                        task.getTaskType().toString(),
                        task.getTitle(),
                        task.getStatus().toString(),
                        task.getDescription(),
                        ((Subtask) task).getEpicid(),
                        task.getDuration(),
                        (task.getStartTime() == null) ? "" : task.getStartTime().toString()
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
                return new Task(
                        data[2],
                        data[4],
                        Integer.parseInt(data[0]),
                        Status.valueOf(data[3]),
                        TaskType.TASK,
                        Integer.parseInt(data[5]),
                        LocalDateTime.parse(data[6])
                );
            case "EPIC":
                return new Epic(
                        data[2],
                        data[4],
                        Integer.parseInt(data[0]),
                        Status.valueOf(data[3]),
                        TaskType.EPIC,
                        Integer.parseInt(data[5]),
                        LocalDateTime.parse(data[6]),
                        LocalDateTime.parse(data[7])
                );
            case "SUBTASK":
                return new Subtask(
                        data[2],
                        data[4],
                        Integer.parseInt(data[0]),
                        Status.valueOf(data[3]),
                        TaskType.SUBTASK,
                        Integer.parseInt(data[6]),
                        LocalDateTime.parse(data[7]),
                        Integer.parseInt(data[5])
                );
        }
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        for (Object task : manager.getHistory()) {
            history.append(String.format("%d,", ((Task) task).getid()));
        }
        if (history.indexOf(",") > 0)
            return history.substring(0, history.lastIndexOf(","));
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
