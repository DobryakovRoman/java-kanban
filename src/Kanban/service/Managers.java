package Kanban.service;

import Kanban.task.Task;

import java.util.ArrayList;

public class Managers {
    private ArrayList<TaskManager> managers = new ArrayList<>();

    public Managers(TaskManager taskManager) {
        managers.add(taskManager);
    }

    public TaskManager getDefault() {
        return managers.get(0);
    }

    public static HistoryManager<Task> getDefaultHistory() {
        return new InMemoryHistoryManager<>();
    }
}
