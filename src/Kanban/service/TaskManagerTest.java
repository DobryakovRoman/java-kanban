package Kanban.service;

import Kanban.task.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;
    protected LocalDateTime now = LocalDateTime.now();

    protected void initTasks() {
        taskManager.addTask(new Task("Задача 1", "Создать задачу 1", now, 10));
        taskManager.addTask(new Task("Задача 2", "Создать задачу 2", now.plusMinutes(30), 20));
        taskManager.addTask(new Task("Задача 3", "Создать задачу 3", now.plusMinutes(50), 30));
    }

    protected void callTasks() {
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);
    }

}