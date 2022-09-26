package Kanban.service;

import Kanban.constants.Status;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void add() {
        assertEquals(Managers.getDefaultHistory(), historyManager);
        Epic epic = new Epic("ep1", "ep1");
        taskManager.addEpic(epic);
        taskManager.getEpicById(1);
        assertEquals(epic, taskManager.getHistory().get(0));

    }

    @Test
    void remove() {
    }

    @Test
    void getHistory() {
    }
}