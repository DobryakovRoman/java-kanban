package Tests;


import Kanban.constants.Status;
import Kanban.service.InMemoryTaskManager;
import Kanban.service.Managers;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @BeforeEach
    void beforeEach() {
        taskManager  = new InMemoryTaskManager();
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

    @Test
    void addEpicTest() {
        taskManager.addEpic(new Epic("ep1", "ep1"));
        assertEquals(1, taskManager.getEpics().size());
        assertEquals("ep1", taskManager.getEpics().get(0).getTitle());
    }

    @Test
    void getEpicsTest() {
        assertEquals(0, taskManager.getEpics().size());
        taskManager.addEpic(new Epic("ep1", "ep1"));
        taskManager.addEpic(new Epic("ep2", "ep3"));
        assertEquals(2, taskManager.getEpics().size());
        assertEquals("ep2", taskManager.getEpics().get(1).getTitle());
    }

    @Test
    void getEpicByIdTest() {
        assertEquals(null, taskManager.getEpicById(1));
        taskManager.addEpic(new Epic("ep2", "ep4"));
        assertEquals("ep2", taskManager.getEpicById(1).getTitle());
        assertEquals(null, taskManager.getEpicById(2));
    }

    @Test
    void updateEpicTest() {
        taskManager.updateEpic(new Epic("ep1", "ep1"));
        assertEquals(0, taskManager.getEpics().size());
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = new Epic("ep2", "ep2");
        epic.setid(taskManager.getEpicById(1).getid());
        taskManager.updateEpic(epic);
        assertEquals("ep2", taskManager.getEpicById(1).getTitle());
        taskManager.updateEpic(new Epic("ep1", "ep1"));
        assertEquals("ep2", taskManager.getEpicById(1).getTitle());
    }

    @Test
    void removeEpic() {
        taskManager.removeEpic(1);
        assertEquals(0, taskManager.getEpics().size());
        taskManager.addEpic(new Epic("ep1", "ep1"));
        taskManager.addEpic(new Epic("ep2", "ep2"));
        taskManager.removeEpic(1);
        assertEquals(1, taskManager.getEpics().size());
        taskManager.removeEpic(1);
        assertEquals(1, taskManager.getEpics().size());
    }

    @Test
    void getSubtasksOfEpicTest() {
        assertEquals(null, taskManager.getSubtasksOfEpic(1));
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("st2", "st2", now, 10);
        subtask2.setEpicid(epic.getid());
        taskManager.addSubtask(subtask2);
        assertEquals(2, taskManager.getSubtasksOfEpic(1).size());
        assertEquals(subtask1, taskManager.getSubtasksOfEpic(1).get(0));
        assertEquals(subtask2, taskManager.getSubtasksOfEpic(1).get(1));
        assertEquals(null, taskManager.getSubtasksOfEpic(2));
    }

    @Test
    void addSubtaskAndGetSubtasksTest() {
        assertEquals(0, taskManager.getSubtasks().size());
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        assertEquals(1, taskManager.getSubtasks().size());
    }

    @Test
    void getSubtaskByidTest() {
        assertEquals(null, taskManager.getSubtaskByid(2));
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        assertEquals(subtask1, taskManager.getSubtaskByid(2));
        assertEquals(null, taskManager.getSubtaskByid(3));
    }

    @Test
    void updateSubtaskTest() {
        LocalDateTime now = LocalDateTime.now();
        taskManager.updateSubtask(new Subtask("st1", "st1", now, 10));
        assertEquals(0, taskManager.getSubtasks().size());
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("st2", "st2", now, 10);
        subtask2.setid(subtask1.getid());
        subtask2.setEpicid(epic.getid());
        taskManager.updateSubtask(subtask2);
        assertEquals(subtask2, taskManager.getSubtaskByid(2));
        assertEquals(null, taskManager.getSubtaskByid(3));
    }

    @Test
    void removeSubtask() {
        assertEquals(0, taskManager.getSubtasks().size());
        taskManager.removeSubtask(1);
        assertEquals(0, taskManager.getSubtasks().size());
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        taskManager.removeSubtask(subtask1.getid());
        assertEquals(0, taskManager.getSubtasks().size());
    }

    @Test
    void clearTest() {
        //assertEquals(0, taskManager.taskid);
        assertEquals(new ArrayList<>(), taskManager.getEpics());
        assertEquals(new ArrayList<>(), taskManager.getTasks());
        assertEquals(new ArrayList<>(), taskManager.getSubtasks());
        assertEquals(Managers.getDefaultHistory().getHistory(), taskManager.getHistory());
        initTasks();
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(4);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        //assertEquals(5, taskManager.taskid);
        assertEquals(3, taskManager.getTasks().size());
        assertEquals(1, taskManager.getSubtasks().size());
        assertEquals(1, taskManager.getEpics().size());
        taskManager.clear();
        //assertEquals(0, taskManager.taskid);
        assertEquals(new ArrayList<>(), taskManager.getEpics());
        assertEquals(new ArrayList<>(), taskManager.getTasks());
        assertEquals(new ArrayList<>(), taskManager.getSubtasks());
        assertEquals(Managers.getDefaultHistory().getHistory(), taskManager.getHistory());
    }

    @Test
    void clearTasksTest() {
        assertEquals(0, taskManager.getTasks().size());
        initTasks();
        assertEquals(3, taskManager.getTasks().size());
        taskManager.clearTasks();
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void clearEpicsTest() {
        assertEquals(0, taskManager.getEpics().size());
        assertEquals(0, taskManager.getSubtasks().size());
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("st2", "st2", now.plusMinutes(50), 10);
        subtask2.setEpicid(epic.getid());
        taskManager.addSubtask(subtask2);
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(2, taskManager.getSubtasks().size());
        taskManager.clearEpics();
        assertEquals(0, taskManager.getEpics().size());
        assertEquals(0, taskManager.getSubtasks().size());
    }

    @Test
    void clearSubtasksTest() {
        assertEquals(0, taskManager.getSubtasks().size());
        LocalDateTime now = LocalDateTime.now();
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = taskManager.getEpicById(1);
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("st2", "st2", now, 10);
        subtask2.setEpicid(epic.getid());
        taskManager.addSubtask(subtask2);
        assertEquals(2, taskManager.getSubtasks().size());
        taskManager.clearSubtasks();
        assertEquals(0, taskManager.getSubtasks().size());
    }

    @Test
    void getHistoryTest() {
        assertEquals(new ArrayList<>(), taskManager.getHistory());
        initTasks();
        callTasks();
        assertEquals(new ArrayList<>(taskManager.getTasks()), taskManager.getHistory());
        LocalDateTime now = LocalDateTime.now();
        taskManager.addTask(new Task("Задача 1", "Создать задачу 1", now, 10));
        assertNotEquals(new ArrayList<>(taskManager.getTasks()), taskManager.getHistory());
    }

    @Test
    void getPrioritizedTasksTest() {
        initTasks();
        assertEquals(3, taskManager.getPrioritizedTasks().size());
    }
}