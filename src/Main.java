public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Задача 1", "Создать задачу 1");
        Task task2 = new Task("Задача 2", "Создать задачу 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        Epic epic1 = new Epic("Эпик 1", "С двумя подзадачами");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Для эпика 1", epic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Для эпика 1", epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        Epic epic2 = new Epic("Эпик 2", "С одной подзадачей");
        taskManager.addEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача 1", "Для эпика 2", epic2);
        taskManager.addSubtask(subtask3);

        System.out.println(taskManager.tasksToString());
        System.out.println(taskManager.epicsToString());
        System.out.println(taskManager.subtasksToString());

        task2.setStatus("IN_PROGRESS");
        taskManager.updateTask(task2.getTaskID(), task2);
        subtask1.setStatus("DONE");
        taskManager.updateSubtask(subtask1.getTaskID(), subtask1);

        taskManager.removeTask(task1.getTaskID());
        taskManager.removeEpic(epic2.getTaskID());

        System.out.println(taskManager.tasksToString());
        System.out.println(taskManager.epicsToString());
        System.out.println(taskManager.subtasksToString());

    }
}
