package Kanban.service;

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

    }

    @Test
    void addTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void removeTask() {
    }

    @Test
    void addEpic() {
    }

    @Test
    void getEpicById() {
    }

    @Test
    void updateEpic() {
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