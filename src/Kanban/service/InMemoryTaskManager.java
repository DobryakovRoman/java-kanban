package Kanban.service;

import Kanban.constants.Status;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected int taskid;
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();
    protected Set<Task> everyTask = new TreeSet<>((Task o1, Task o2) -> {
        if ((o1.getStartTime() == null) && (o2.getStartTime() != null)) {
            return 1;
        } else if ((o1.getStartTime() != null) && (o2.getStartTime() == null))  {
            return -1;
        } else if ((o1.getStartTime() == null) && (o2.getStartTime() == null))  {
            return 0;
        } else {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    });
    protected HistoryManager<Task> history = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        taskid = 0;
    }

    @Override
    public void addTask(Task task) {
        validateTask(task);
        task.setid(++taskid);
        tasks.put(task.getid(), task);
        everyTask.add(task);
    }

    @Override
    public List<Task> getTasks() {
        Collection<Task> values = tasks.values();
        return new ArrayList<>(values);
    }

    @Override
    public void updateTask(Task task) {
        validateTask(task);
        if (tasks.containsKey(task.getid())) {
            tasks.replace(task.getid(), task);
        }
    }

    @Override
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            history.add(tasks.get(id));
            return tasks.get(id);
        } else return null;
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
        history.remove(id);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setid(++taskid);
        epics.put(taskid, epic);
    }

    @Override
    public List<Epic> getEpics() {
        Collection<Epic> values = epics.values();
        return new ArrayList<>(values);
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            history.add(epics.get(id));
            return epics.get(id);
        } else return null;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getid())) {
            Epic tempEpic = epics.get(epic.getid());
            tempEpic.setTitle(epic.getTitle());
            tempEpic.setDescription(epic.getDescription());
        }
    }

    @Override
    public void removeEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (int subtask : epic.getSubtasks()) {
                subtasks.remove(subtask);
                history.remove(subtask);
            }
            epics.remove(id);
            history.remove(id);
        }
    }

    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int id) {
        if (epics.get(id) != null) {
            ArrayList<Subtask> subtasks = new ArrayList<>();
            for (Integer subtask : epics.get(id).getSubtasks()) {
                subtasks.add(this.subtasks.get(subtask));
            }
            return subtasks;
        } else return null;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        validateTask(subtask);
        subtask.setid(++taskid);
        subtasks.put(taskid, subtask);
        epics.get(subtask.getEpicid()).addSubtask(subtask);
        updateEpicStatus(epics.get(subtask.getEpicid()));
        updateEpicStartTimeAndEndTime(epics.get(subtask.getEpicid()));
        everyTask.add(subtask);
    }

    @Override
    public List<Subtask> getSubtasks() {
        Collection<Subtask> values = subtasks.values();
        return new ArrayList<>(values);
    }

    @Override
    public Subtask getSubtaskByid(int id) {
        if (subtasks.containsKey(id)) {
            history.add(subtasks.get(id));
            return subtasks.get(id);
        } else return null;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        validateTask(subtask);
        if (subtasks.containsKey(subtask.getid())) {
            Subtask tempSubtask = subtasks.get(subtask.getid());
            tempSubtask.setTitle(subtask.getTitle());
            tempSubtask.setDescription(subtask.getDescription());
            tempSubtask.setStatus(subtask.getStatus());
            tempSubtask.setStartTime(subtask.getStartTime());
            tempSubtask.setDuration(subtask.getDuration());
            updateEpicStatus(epics.get(subtask.getEpicid()));
        }
    }

    @Override
    public void removeSubtask(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            epics.get(subtask.getEpicid()).removeSubtask(subtask);
            subtasks.remove(id);
            history.remove(id);
            updateEpicStatus(epics.get(subtask.getEpicid()));
            updateEpicStartTimeAndEndTime(epics.get(subtask.getEpicid()));
        }
    }

    @Override
    public void clear() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        taskid = 0;
        history = Managers.getDefaultHistory();
    }

    @Override
    public void clearTasks() {
        for (Integer taskId : tasks.keySet()) {
            history.remove(taskId);
        }
        tasks = new HashMap<>();
    }

    @Override
    public void clearEpics() {
        for (Epic epic : epics.values()) {
            if (!epic.getSubtasks().isEmpty()) {
                for (Integer subtask : epic.getSubtasks()) {
                    subtasks.remove(subtask);
                    history.remove(subtask);
                }
            }
            history.remove(epic.getid());
        }
        epics = new HashMap<>();
    }

    @Override
    public void clearSubtasks() {
        for (Integer subtaskId : subtasks.keySet()) {
            history.remove(subtaskId);
        }
        subtasks = new HashMap<>();
        for (Epic epic : epics.values()) {
            epic.removeSubtasks();
            updateEpicStatus(epic);
            updateEpicStartTimeAndEndTime(epic);
        }
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(everyTask);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history.getHistory());
    }

    private void validateTask(Task task) {
        if (task.getStartTime() == null || task.getEndTime() == null) {
            return;
        }
        ArrayList<Task> prioritizedTasks = (ArrayList<Task>) getPrioritizedTasks();
        for (Task prioritizedTask : prioritizedTasks) {
            if (prioritizedTask.getStartTime() == null) {
                return;
            }
            if (task.getEndTime().isAfter(prioritizedTask.getStartTime()) &&
                    task.getStartTime().isBefore(prioritizedTask.getStartTime())) {
                throw new RuntimeException("Задачи пересекаются!");
            }
            if (task.getStartTime().isBefore(prioritizedTask.getEndTime()) &&
                    task.getEndTime().isAfter(prioritizedTask.getEndTime())) {
                throw new RuntimeException("Задачи пересекаются!");
            }
        }
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        boolean statusNew = false;
        boolean statusProgress = false;
        boolean statusDone = false;
        for (Integer subtask : epic.getSubtasks()) {
            switch (subtasks.get(subtask).getStatus()) {
                case NEW:
                    statusNew = true;
                    break;
                case IN_PROGRESS:
                    statusProgress = true;
                    break;
                case DONE:
                    statusDone = true;
                    break;
            }
        }
        if (!statusProgress && !statusNew) {
            epic.setStatus(Status.DONE);
        } else if (!statusProgress && !statusDone) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private void updateEpicStartTimeAndEndTime(Epic epic) {
        if (epic.getSubtasks() != null) {
            int epicDuration = 0;
            for (Integer epicSubtask : epic.getSubtasks()) {
                if (epic.getStartTime() == null || epic.getStartTime().isAfter(subtasks.get(epicSubtask).getStartTime())) {
                    epic.setStartTime(subtasks.get(epicSubtask).getStartTime());
                }
                if (epic.getEndTime() == null || epic.getEndTime().isBefore(subtasks.get(epicSubtask).getEndTime())) {
                    epic.setEndTime(subtasks.get(epicSubtask).getEndTime());
                }
                epicDuration += subtasks.get(epicSubtask).getDuration();
            }
            epic.setDuration(epicDuration);
        } else {
            epic.setStartTime(null);
            epic.setDuration(0);
            epic.setEndTime(null);
        }
    }
}
