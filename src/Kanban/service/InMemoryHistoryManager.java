package Kanban.service;

import Kanban.task.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager<T extends Task> implements HistoryManager<T> {
    private static final int HISTORY_LENGTH = 10;
    List<T> history = new LinkedList<>();

    @Override
    public void add(T t) {
        if (history.size() >= HISTORY_LENGTH) {
            history.remove(0);
        }
        history.add(t);
    }

    @Override
    public List<T> getHistory() {
        return history;
    }
}
