import Kanban.constants.Status;
import Kanban.service.InMemoryTaskManager;
import Kanban.service.Managers;
import Kanban.service.TaskManager;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Managers managers = new Managers(inMemoryTaskManager);
        inMemoryTaskManager = managers.getDefault();
        Task task1 = new Task("Задача 1", "Создать задачу 1");
        Task task2 = new Task("Задача 2", "Создать задачу 2");
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        Epic epic1 = new Epic("Эпик 1", "С двумя подзадачами");
        inMemoryTaskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Для эпика 1");
        subtask1.setEpicid(epic1.getid());
        Subtask subtask2 = new Subtask("Подзадача 2", "Для эпика 1");
        subtask2.setEpicid(epic1.getid());

        inMemoryTaskManager.addSubtask(subtask1);
        inMemoryTaskManager.addSubtask(subtask2);
        Epic epic2 = new Epic("Эпик 2", "С одной подзадачей");
        inMemoryTaskManager.addEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача 1", "Для эпика 2");
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
    }
}
