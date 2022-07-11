package Kanban.service;

import java.util.*;
import Kanban.task.*;

public class TaskManager {
    private int taskid;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public TaskManager() {
        taskid = 0;
    }

    public void addTask(Task task) {
        task.setid(++taskid);
        tasks.put(task.getid(), task);
    }

    public List<Task> getTasks() {
        Collection<Task> values = tasks.values();
        return new ArrayList<>(values);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getid())) {
            tasks.replace(task.getid(), task);
        }
    }

    public Task getTaskByid(int id) {
        return tasks.get(id);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void addEpic(Epic epic) {
        epic.setid(++taskid);
        epics.put(taskid, epic);
    }

    public List<Epic> getEpics() {
        Collection<Epic> values = epics.values();
        return new ArrayList<>(values);
    }

    public Epic getEpicByid(int id) {
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getid())) {
            Epic tempEpic = epics.get(epic.getid());
            tempEpic.setTitle(epic.getTitle());
            tempEpic.setDescription(epic.getDescription());
        }
    }

    public void removeEpic(int id) {
        Epic epic = epics.get(id);
        for (int subtask : epic.getSubtasks()) {
            subtasks.remove(subtask);
        }
        epics.remove(id);
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Integer subtask : epics.get(id).getSubtasks()) {
            subtasks.add(this.subtasks.get(subtask));
        }
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtask.setid(++taskid);
        subtasks.put(taskid, subtask);
        epics.get(subtask.getEpicid()).addSubtask(subtask);
        updateEpicStatus(epics.get(subtask.getEpicid()));
    }

    public List<Subtask> getSubtasks() {
        Collection<Subtask> values = subtasks.values();
        return new ArrayList<>(values);
    }

    public Subtask getSubtaskByid(int id) {
        return subtasks.get(id);
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getid())) {
            Subtask tempSubtask = subtasks.get(subtask.getid());
            tempSubtask.setTitle(subtask.getTitle());
            tempSubtask.setDescription(subtask.getDescription());
            tempSubtask.setStatus(subtask.getStatus());
            updateEpicStatus(epics.get(subtask.getEpicid()));
        }
    }

    public void removeSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        epics.get(subtask.getEpicid()).removeSubtask(subtask);
        subtasks.remove(id);
        updateEpicStatus(epics.get(subtask.getEpicid()));
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus("NEW");
            return;
        }
        boolean statusNew = false;
        boolean statusProgress = false;
        boolean statusDone = false;
        for (Integer subtask : epic.getSubtasks()) {
            switch (subtasks.get(subtask).getStatus()) {
                case "NEW":
                    statusNew = true;
                    break;
                case "IN_PROGRESS":
                    statusProgress = true;
                    break;
                case "DONE":
                    statusDone = true;
                    break;
            }
        }
        if (!statusProgress && !statusNew) {
            epic.setStatus("DONE");
        } else if (!statusProgress && !statusDone) {
            epic.setStatus("NEW");
        } else {
            epic.setStatus("IN_PROGRESS");
        }
    }

    public void clear() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        taskid = 0;
    }

    public void clearTasks() {
        tasks = new HashMap<>();
    }

    public void clearEpics() {
        for (Epic epic : epics.values()) {
            if (!epic.getSubtasks().isEmpty()) {
                for (Integer subtask : epic.getSubtasks()) {
                    subtasks.remove(subtask);
                }
            }
        }
        epics = new HashMap<>();
    }

    public void clearSubtasks() {
        subtasks = new HashMap<>();
        for (Epic epic : epics.values()) {
            epic.removeSubtasks();
            updateEpicStatus(epic);
        }
    }
}
