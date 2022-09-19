package Kanban.service;


import Kanban.constants.Status;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @BeforeEach
    void beforeEach() {
        taskManager  = new InMemoryTaskManager();
    }
    @Test
    void epicStatusWOSubtasksTest() {
        taskManager.addEpic(new Epic("ep1", "ep1"));
        assertEquals(Status.NEW, taskManager.getEpicById(1).getStatus());
    }

    @Test
    void epicStatusWNewSubtasksTest() {
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask = new Subtask("st1", "st1", now, 10);
        subtask.setEpicid(1);
        taskManager.addSubtask(subtask);
        subtask = new Subtask("st2", "st2", now, 10);
        subtask.setEpicid(epic.getid());
        taskManager.addSubtask(subtask);
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    void epicStatusWDoneSubtasksTest() {
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask = new Subtask("st1", "st1", now, 10);
        subtask.setStatus(Status.DONE);
        subtask.setEpicid(epic.getid());
        taskManager.addSubtask(subtask);
        subtask = new Subtask("st2", "st2", now, 10);
        subtask.setStatus(Status.DONE);
        subtask.setEpicid(epic.getid());
        taskManager.addSubtask(subtask);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void epicStatusWDoneAndNewSubtasksTest() {
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask = new Subtask("st1", "st1", now, 10);
        subtask.setStatus(Status.DONE);
        subtask.setEpicid(epic.getid());
        taskManager.addSubtask(subtask);
        subtask = new Subtask("st2", "st2", now, 10);
        subtask.setStatus(Status.NEW);
        subtask.setEpicid(epic.getid());
        taskManager.addSubtask(subtask);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void epicStatusWIn_progressSubtaskTest() {
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask = new Subtask("st1", "st1", now, 10);
        subtask.setStatus(Status.IN_PROGRESS);
        subtask.setEpicid(epic.getid());
        taskManager.addSubtask(subtask);
        subtask = new Subtask("st2", "st2", now, 10);
        subtask.setStatus(Status.IN_PROGRESS);
        subtask.setEpicid(epic.getid());
        taskManager.addSubtask(subtask);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void addTaskAndGetTasksAndGetTaskByIdTest() {
        assertEquals(new ArrayList<Task>(), taskManager.getTasks());
        assertEquals(null, taskManager.getTaskById(1));
        initTasks();
        assertEquals(3, taskManager.getTasks().size());
        Task task = new Task("Задача 1", "Создать задачу 1", now, 10);
        task.setid(1);
        assertEquals(task, taskManager.getTaskById(1));
        assertNotEquals(task, taskManager.getTaskById(4));
    }

    @Test
    void updateTaskTest() {
        taskManager.updateTask(new Task("Задача 1", "Создать задачу 1", now, 10));
        assertEquals(0, taskManager.getTasks().size());
        initTasks();
        Task task = new Task("Задача 5", "Создать задачу 1", now, 10);
        task.setid(1);
        taskManager.updateTask(task);
        assertEquals(task, taskManager.getTaskById(1));
        assertNotEquals(task, taskManager.getTaskById(6));
    }

    @Test
    void removeTaskTest() {
        taskManager.removeTask(1);
        assertEquals(0, taskManager.getTasks().size());
        initTasks();
        taskManager.removeTask(1);
        assertEquals(2, taskManager.getTasks().size());
        taskManager.removeTask(1);
        assertEquals(2, taskManager.getTasks().size());
    }



}