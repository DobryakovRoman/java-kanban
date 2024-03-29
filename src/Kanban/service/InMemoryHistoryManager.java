package Kanban.service;

import Kanban.task.Task;

import java.util.*;

public class InMemoryHistoryManager<T extends Task> implements HistoryManager<T> {
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    private class Node {
        private final T task;
        private Node prev;
        private Node next;

        public Node(T task, Node prev, Node next) {
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

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public List<T> getHistory() {
        List<T> history = new ArrayList<>();
        Node current = first;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    private void linkLast(T t) {
        Node node = new Node (t, last, null);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    private void removeNode(int id) {
        Node remove = nodeMap.remove(id);
        if (nodeMap.size() == 0) {
            first = null;
            last = null;
            return;
        }
        if (remove != null) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InMemoryHistoryManager<?> that = (InMemoryHistoryManager<?>) o;

        if (nodeMap != null ? !nodeMap.equals(that.nodeMap) : that.nodeMap != null) return false;
        if (!Objects.equals(first, that.first)) return false;
        return Objects.equals(last, that.last);
    }

    @Override
    public int hashCode() {
        int result = nodeMap != null ? nodeMap.hashCode() : 0;
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (last != null ? last.hashCode() : 0);
        return result;
    }
}
