package Kanban.service;

import Kanban.task.Task;

import java.util.List;

public interface HistoryManager<T extends Task> {
    public void add(T t);
    public List<T> getHistory();
}
