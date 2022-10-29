package Kanban.service;

import Kanban.service.TaskManager;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;
    protected LocalDateTime now = LocalDateTime.parse("2022-09-28T23:41:00.625776800");

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

    protected void initSubtask() {
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
    }

    protected void initAll() {
        taskManager.addTask(new Task("Задача 1", "Создать задачу 1", now, 10));
        taskManager.addTask(new Task("Задача 2", "Создать задачу 2", now.plusMinutes(30), 20));
        taskManager.addTask(new Task("Задача 3", "Создать задачу 3", now.plusMinutes(50), 30));
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(4);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
    }


}