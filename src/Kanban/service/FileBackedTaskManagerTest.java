package Kanban.service;

import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{

    final private File fileName = new File("data.csv");

    @BeforeEach
    void beforeEach() {
        taskManager  = new FileBackedTaskManager(fileName);
    }

    @AfterEach
    void afterEach() {
        fileName.delete();
    }

    @Test
    void addTask() {
        File fileNameAddTask = new File("data_addTask.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameAddTask);
        assertNotEquals(taskManager.getTasks(), taskManagerExpected.getTasks());
        initTasks();
        assertEquals(taskManager.getTasks(), taskManagerExpected.getTasks());
    }

    @Test
    void updateTask() {
        taskManager.updateTask(new Task("Задача 5", "Создать задачу 1", now, 10));
        File fileNameUpdateTask = new File("data_updateTask.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameUpdateTask);
        assertNotEquals(taskManager.getTasks(), taskManagerExpected.getTasks());
        initTasks();
        Task task = new Task("Задача 5", "Создать задачу 1", now, 10);
        task.setid(1);
        taskManager.updateTask(task);
        assertEquals(taskManager.getTasks(), taskManagerExpected.getTasks());
    }

    @Test
    void getTaskById() {
        assertNull(taskManager.getTaskById(1));
        initTasks();
        File fileNameGetTaskByIdTask = new File("data_getTaskById.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameGetTaskByIdTask);
        assertEquals(taskManager.getTaskById(1), taskManagerExpected.getTaskById(1));
    }

    @Test
    void removeTask() {
        File fileNameRemoveTask = new File("data_removeTask.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameRemoveTask);
        assertNotEquals(taskManager.getTasks(), taskManagerExpected.getTasks());
        initTasks();
        taskManager.removeTask(1);
        assertEquals(taskManager.getTasks(), taskManagerExpected.getTasks());
    }

    @Test
    void addEpic() {
        File fileNameAddEpic = new File("data_addEpic.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameAddEpic);
        assertNotEquals(taskManager.getEpics(), taskManagerExpected.getEpics());
        taskManager.addEpic(new Epic("ep1", "ep1"));
        assertEquals(taskManager.getEpics(), taskManagerExpected.getEpics());
    }

    @Test
    void getEpicById() {
        File fileNameGetEpicById = new File("data_getEpicById.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameGetEpicById);
        assertNull(taskManager.getEpicById(1));
        taskManager.addEpic(new Epic("ep1", "ep1"));
        assertEquals(taskManager.getEpicById(1), taskManagerExpected.getEpicById(1));
    }

    @Test
    void updateEpic() {
        File fileNameUpdateEpic = new File("data_updateEpic.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameUpdateEpic);
        taskManager.updateEpic(new Epic("ep1", "ep1"));
        assertNull(taskManager.getEpicById(1));
        taskManager.addEpic(new Epic("ep1", "ep1"));
        Epic epic = new Epic("ep2", "ep1");
        epic.setid(1);
        taskManager.updateEpic(epic);
        assertEquals(taskManager.getEpics(), taskManagerExpected.getEpics());
    }

    @Test
    void removeEpic() {
        File fileNameRemoveEpic = new File("data_removeEpic.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameRemoveEpic);
        Epic epic = new Epic("ep2", "ep2");
        epic.setid(1);
        taskManager.addEpic(epic);
        taskManager.removeEpic(2);
        assertEquals(1, taskManager.getEpics().size());
        epic = new Epic("ep2", "ep2");
        epic.setid(2);
        taskManager.addEpic(epic);
        assertEquals(2, taskManager.getEpics().size());
        taskManager.removeEpic(2);
        assertEquals(1, taskManager.getEpics().size());
        assertEquals("ep2", taskManager.getEpicById(1).getTitle());
        assertEquals(taskManager.getEpics(), taskManagerExpected.getEpics());
    }

    @Test
    void addSubtask() {
        File fileNameAddSubtask = new File("data_addSubtask.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameAddSubtask);
        assertEquals(0, taskManager.getSubtasks().size());
        initSubtask();
        assertEquals(1, taskManager.getSubtasks().size());
        assertEquals(taskManager.getSubtasks(), taskManagerExpected.getSubtasks());
    }

    @Test
    void getSubtaskByid() {
        File fileNameGetSubtaskById = new File("data_getSubtaskById.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameGetSubtaskById);
        assertNull(taskManager.getSubtaskByid(2));
        initSubtask();
        assertEquals("st1", taskManager.getSubtaskByid(2).getTitle());
        assertEquals(taskManager.getSubtasks(), taskManagerExpected.getSubtasks());
    }

    @Test
    void updateSubtask() {
        File fileNameUpdateSubtask = new File("data_updateSubtask.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameUpdateSubtask);
        assertNull(taskManager.getSubtaskByid(2));
        initSubtask();
        Subtask subtask = taskManager.getSubtaskByid(2);
        subtask.setDescription("st2");
        taskManager.updateSubtask(subtask);
        assertEquals("st1", taskManager.getSubtaskByid(2).getTitle());
        assertEquals(taskManager.getSubtasks(), taskManagerExpected.getSubtasks());
    }

    @Test
    void removeSubtask() {
        File fileNameRemoveSubtask = new File("data_removeSubtask.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameRemoveSubtask);
        assertNull(taskManager.getSubtaskByid(2));
        initSubtask();
        assertEquals("st1", taskManager.getSubtaskByid(2).getTitle());
        taskManager.removeSubtask(2);
        assertNull(taskManager.getSubtaskByid(2));
        assertEquals(taskManager.getSubtasks(), taskManagerExpected.getSubtasks());
    }

    @Test
    void clear() {
        File fileNameClear = new File("data_clear.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameClear);
        assertEquals(0, taskManager.getEpics().size());
        assertEquals(0, taskManager.getTasks().size());
        assertEquals(0, taskManager.getSubtasks().size());
        initAll();
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(3, taskManager.getTasks().size());
        assertEquals(1, taskManager.getSubtasks().size());
        taskManager.clear();
        assertEquals(taskManager.getSubtasks(), taskManagerExpected.getSubtasks());
    }

    @Test
    void clearTasks() {
        File fileNameClearTasks = new File("data_clearTasks.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameClearTasks);
        assertEquals(0, taskManager.getTasks().size());
        initTasks();
        assertEquals(3, taskManager.getTasks().size());
        taskManager.clearTasks();
        assertEquals(taskManager.getTasks(), taskManagerExpected.getTasks());
    }

    @Test
    void clearEpics() {
        File fileNameClearEpics = new File("data_clearEpics.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameClearEpics);
        assertEquals(0, taskManager.getEpics().size());
        initAll();
        assertEquals(1, taskManager.getEpics().size());
        taskManager.clearEpics();
        assertEquals(taskManager.getEpics(), taskManagerExpected.getEpics());
    }

    @Test
    void clearSubtasks() {
        File fileNameClearSubtasks = new File("data_clearSubtasks.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameClearSubtasks);
        assertEquals(0, taskManager.getSubtasks().size());
        initAll();
        assertEquals(1, taskManager.getSubtasks().size());
        taskManager.clearSubtasks();
        assertEquals(taskManager.getSubtasks(), taskManagerExpected.getSubtasks());
    }

    @Test
    void getPrioritizedTasksTest() {
        initTasks();
        assertEquals(3, taskManager.getPrioritizedTasks().size());
    }
}