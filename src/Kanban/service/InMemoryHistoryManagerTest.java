package Kanban.service;

import Kanban.constants.Status;
import Kanban.service.HistoryManager;
import Kanban.service.Managers;
import Kanban.service.TaskManager;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager<Task> historyManager;
    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        historyManager = Managers.getDefaultHistory();
        taskManager = Managers.getDefault();
    }

    @Test
    void addAndRemoveTest() {
        assertEquals(Managers.getDefaultHistory(), historyManager);
        Epic epic = new Epic("ep1", "ep1");
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(epic, taskManager.getHistory().get(0));
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task("Задача 1", "Создать задачу 1", now, 10);
        taskManager.addTask(task);
        taskManager.getTaskById(2);
        assertEquals(task, taskManager.getHistory().get(1));
        Subtask subtask1 = new Subtask("st1", "st1", now, 10);
        subtask1.setEpicid(epic.getid());
        taskManager.addSubtask(subtask1);
        taskManager.getSubtaskByid(3);
        assertEquals(subtask1, taskManager.getHistory().get(2));
        taskManager.removeEpic(1);
        assertEquals(1, taskManager.getHistory().size());
        assertEquals(task, taskManager.getHistory().get(0));
        RuntimeException ex = assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                taskManager.getHistory().get(1);
            }
        });
        assertEquals("Index 1 out of bounds for length 1", ex.getMessage());
    }

    @Test
    void getHistoryTest() {
        assertEquals(Managers.getDefaultHistory(), historyManager);
        Epic epic = new Epic("ep1", "ep1");
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(epic, taskManager.getHistory().get(0));
        RuntimeException ex = assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() {
                taskManager.getHistory().get(1);
            }
        });
        assertEquals("Index 1 out of bounds for length 1", ex.getMessage());
    }
}