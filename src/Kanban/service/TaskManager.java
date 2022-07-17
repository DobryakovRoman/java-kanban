package Kanban.service;

import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
        public void addTask(Task task);
        public List<Task> getTasks();
        public void updateTask(Task task);
        public Task getTaskById(int id);
        public void removeTask(int id);
        public void addEpic(Epic epic);
        public List<Epic> getEpics();
        public Epic getEpicById(int id);
        public void updateEpic(Epic epic);
        public void removeEpic(int id);
        public ArrayList<Subtask> getSubtasksOfEpic(int id);
        public void addSubtask(Subtask subtask);
        public List<Subtask> getSubtasks();
        public Subtask getSubtaskByid(int id);
        public void updateSubtask(Subtask subtask);
        public void removeSubtask(int id);
        public void updateEpicStatus(Epic epic);
        public void clear();
        public void clearTasks();
        public void clearEpics();
        public void clearSubtasks();
        public List<Task> getHistory();
        public void updateHistory(Task task);
}
