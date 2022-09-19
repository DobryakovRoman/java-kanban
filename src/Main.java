import Kanban.constants.Status;
import Kanban.service.InMemoryTaskManager;
import Kanban.service.Managers;
import Kanban.service.TaskManager;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        LocalDateTime now = LocalDateTime.now();
        Task task1 = new Task("Задача 1", "Создать задачу 1", now, 10);
        Task task2 = new Task("Задача 2", "Создать задачу 2", now.plusMinutes(20), 10);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        Epic epic1 = new Epic("Эпик 1", "С двумя подзадачами");
        inMemoryTaskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Для эпика 1", now.plusMinutes(40), 10);
        subtask1.setEpicid(epic1.getid());
        Subtask subtask2 = new Subtask("Подзадача 2", "Для эпика 1", now.plusMinutes(50), 10);
        subtask2.setEpicid(epic1.getid());

        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        Epic epic2 = new Epic("Эпик 2", "С одной подзадачей");
        inMemoryTaskManager.addEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача 1", "Для эпика 2", now.plusMinutes(10), 10);
        subtask3.setEpicid(epic2.getid());
        inMemoryTaskManager.addSubtask(subtask3);

        for (Task task : inMemoryTaskManager.getTasks()) {
            System.out.println(task);
        }
        for (Epic epic : inMemoryTaskManager.getEpics()) {
            System.out.println(epic);
        }
        for (Subtask subtask : inMemoryTaskManager.getSubtasks()) {
            System.out.println(subtask);
        }

        task2.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.updateTask(task2);
        subtask1.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subtask1);

        inMemoryTaskManager.removeTask(task1.getid());
        inMemoryTaskManager.removeEpic(epic2.getid());

        for (Task task : inMemoryTaskManager.getTasks()) {
            System.out.println(task);
        }
        for (Epic epic : inMemoryTaskManager.getEpics()) {
            System.out.println(epic);
        }
        for (Subtask subtask : inMemoryTaskManager.getSubtasks()) {
            System.out.println(subtask);
        }


        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubtaskByid(4);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubtaskByid(4);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getSubtaskByid(4);
        inMemoryTaskManager.getTaskById(2);

        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.removeEpic(3);
        System.out.println(inMemoryTaskManager.getHistory());

        String fileName = "data.csv";
        TaskManager fileBackedTaskManager = Managers.loadFromFile(new File(fileName));
        Task task3 = new Task("Задача 1", "Создать задачу 1", now, 10);
        Task task4 = new Task("Задача 2", "Создать задачу 2", now.plusMinutes(20), 10);
        fileBackedTaskManager.addTask(task3);
        fileBackedTaskManager.addTask(task4);
        Epic epic3 = new Epic("Эпик 1", "С двумя подзадачами");
        fileBackedTaskManager.addEpic(epic3);
        Subtask subtask4 = new Subtask("Подзадача 1", "Для эпика 1", now.plusMinutes(40), 10);
        subtask4.setEpicid(epic3.getid());
        Subtask subtask5 = new Subtask("Подзадача 2", "Для эпика 1", now.plusMinutes(50), 10);
        subtask5.setEpicid(epic3.getid());

        fileBackedTaskManager.addSubtask(subtask4);
        fileBackedTaskManager.addSubtask(subtask5);
        Epic epic4 = new Epic("Эпик 2", "С одной подзадачей");
        fileBackedTaskManager.addEpic(epic4);
        Subtask subtask6 = new Subtask("Подзадача 1", "Для эпика 2", now.plusMinutes(10), 10);
        subtask6.setEpicid(epic4.getid());
        fileBackedTaskManager.addSubtask(subtask6);

        fileBackedTaskManager.getTaskById(2);
        fileBackedTaskManager.getEpicById(3);
        fileBackedTaskManager.getSubtaskByid(4);
        fileBackedTaskManager.getTaskById(2);
        fileBackedTaskManager.getTaskById(2);
        fileBackedTaskManager.getEpicById(3);
        fileBackedTaskManager.getSubtaskByid(4);
        fileBackedTaskManager.getTaskById(2);
        fileBackedTaskManager.getTaskById(2);
        fileBackedTaskManager.getEpicById(3);
        fileBackedTaskManager.getSubtaskByid(4);
        fileBackedTaskManager.getTaskById(2);

        System.out.println(fileBackedTaskManager.getHistory());
        fileBackedTaskManager.removeEpic(3);
        System.out.println(fileBackedTaskManager.getHistory());
    }
}
