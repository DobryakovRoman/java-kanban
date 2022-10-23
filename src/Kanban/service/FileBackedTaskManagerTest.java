package Kanban.service;

import Kanban.task.Epic;
import Kanban.task.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{

    final private File fileName = new File("data.csv");

    @BeforeEach
    void beforeEach() {

        taskManager  = new FileBackedTaskManager(fileName);

    }

    @AfterEach
    void afterEach() {

        //fileName.delete();

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
        File fileNameGetEpicById = new File("data_updateEpic.csv");
        FileBackedTaskManager taskManagerExpected = new FileBackedTaskManager(fileNameGetEpicById);
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

    }

    @Test
    void addSubtask() {
    }

    @Test
    void getSubtaskByid() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void removeSubtask() {
    }

    @Test
    void clear() {
    }

    @Test
    void clearTasks() {
    }

    @Test
    void clearEpics() {
    }

    @Test
    void clearSubtasks() {
    }
}