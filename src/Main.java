public class Main {

    public static void main(String[] args) {
        /*Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.*/
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Задача 1", "Создать задачу 1");
        taskManager.addTask("Задача 2", "Создать задачу 2");
        taskManager.addEpic("Эпик 1", "С двумя подзадачами");
        taskManager.addSubtask("Подзадача 1", "Для эпика 1", taskManager.getLastCreatedEpic());
        taskManager.addSubtask("Подзадача 2", "Для эпика 1", taskManager.getLastCreatedEpic());
        taskManager.addEpic("Эпик 2", "С одной подзадачей");
        taskManager.addSubtask("Подзадача 1", "Для эпика 2", taskManager.getLastCreatedEpic());

        /*Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)*/
        System.out.println(taskManager.tasksToString());
        System.out.println(taskManager.epicsToString());
        System.out.println(taskManager.subtasksToString());

        /*Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.*/


        /*И, наконец, попробуйте удалить одну из задач и один из эпиков.*/
    }
}
