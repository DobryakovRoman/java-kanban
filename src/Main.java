public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Задача 1", "Создать задачу 1");
        Task task2 = new Task("Задача 2", "Создать задачу 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        Epic epic1 = new Epic("Эпик 1", "С двумя подзадачами");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Для эпика 1");
        subtask1.setEpic(epic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Для эпика 1");
        subtask2.setEpic(epic1);


        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        Epic epic2 = new Epic("Эпик 2", "С одной подзадачей");
        taskManager.addEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача 1", "Для эпика 2");
        subtask3.setEpic(epic2);
        taskManager.addSubtask(subtask3);

        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        for (Epic epic : taskManager.getEpics()) {
            System.out.println(epic);
        }
        for (Subtask subtask : taskManager.getSubtasks()) {
            System.out.println(subtask);
        }

        task2.setStatus("IN_PROGRESS");
        taskManager.updateTask(task2);
        subtask1.setStatus("DONE");
        taskManager.updateSubtask(subtask1);

        taskManager.removeTask(task1.getid());
        taskManager.removeEpic(epic2.getid());

        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        for (Epic epic : taskManager.getEpics()) {
            System.out.println(epic);
        }
        for (Subtask subtask : taskManager.getSubtasks()) {
            System.out.println(subtask);
        }

    }
}
