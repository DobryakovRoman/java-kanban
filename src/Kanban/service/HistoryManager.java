package Kanban.service;

import Kanban.task.Task;

import java.util.ArrayList;

public interface HistoryManager<T extends Task> {
    public void add(T t);
    public ArrayList<T> getHistory();
}
