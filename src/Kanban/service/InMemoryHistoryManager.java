package Kanban.service;

import Kanban.task.Task;

import java.util.*;

public class InMemoryHistoryManager<T extends Task> implements HistoryManager<T> {
    private static final int HISTORY_LENGTH = 10;
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "task=" + task +
                    ", prev=" + (prev != null ? prev.task : null) +
                    ", next=" + (next != null ? next.task : null) +
                    '}';
        }
    }

    @Override
    public void add(T t) {
        removeNode(t.getid());
        linkLast(t);
        nodeMap.put(last.task.getid(), last);
    }

    private void linkLast(T t) {
        Node node = new Node (t, last, null);
        if (first == null) {
            first = node;
            last = node;
        } else {
            last.next = node;
            last = node;
        }
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    private void removeNode(int id) {
        Node remove = nodeMap.remove(id);
        if (remove == null) {
            return;
        } else {
            if (first == remove) {
                first = remove.next;
                first.prev = null;
                return;
            }
            if (last == remove) {
                last = remove.prev;
                last.next = null;
                return;
            }
            remove.next.prev = remove.prev;
            remove.prev.next = remove.next;
        }
    }

    @Override
    public List<T> getHistory() {
        List<T> history = new ArrayList<>();
        Node current = first;
        while (current != null) {
            history.add((T) current.task);
            current = current.next;
        }
        return history;
    }
}
