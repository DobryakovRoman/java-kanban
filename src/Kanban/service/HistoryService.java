package Kanban.service;

import java.util.ArrayList;

public class HistoryService<T> {
    public static final int HISTORY_LENGTH = 10;
    ArrayList<T> history = new ArrayList<>();

    public void add(T t) {
        if (history.size() < 10) {
            history.add(t);
        } else {
            history.remove(0);
            history.add(t);
        }
    }

    public ArrayList<T> getHistory() {
        return history;
    }
}
