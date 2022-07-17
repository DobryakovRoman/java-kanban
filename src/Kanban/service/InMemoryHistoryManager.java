package Kanban.service;

import Kanban.task.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager<T extends Task> implements HistoryManager<T> {
    public static final int HISTORY_LENGTH = 10;
    ArrayList<T> history = new ArrayList<>();

    @Override
    public void add(T t) {
        if (history.size() < 10) {
            history.add(t);
        } else {
            history.remove(0);
            history.add(t);
        }
    }

    @Override
    public ArrayList<T> getHistory() {
        return history;
    }
}
