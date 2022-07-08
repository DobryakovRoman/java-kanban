public class Main {

    public static void main(String[] args) {
        /*Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.*/
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

        /*Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)*/
        System.out.println(taskManager.tasksToString());
        System.out.println(taskManager.epicsToString());
        System.out.println(taskManager.subtasksToString());

        /*Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.*/


        /*И, наконец, попробуйте удалить одну из задач и один из эпиков.*/
    }
}
