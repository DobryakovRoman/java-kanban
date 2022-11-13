package Kanban.HTTP;

import Kanban.service.*;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HTTPTaskServer {

    HTTPTaskManager manager = new HTTPTaskManager();

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    private final HttpServer httpServer;
    private static final Charset DEFAULT_CHARSET = UTF_8;
    private static final int PORT = 8080;

    public HTTPTaskServer() throws IOException {
        manager.load();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
    }

    public void start() {
        System.out.println("HTTP-сервер запущен на порту: " + PORT);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }

    class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) {
            try {
                String path = httpExchange.getRequestURI().getPath();
                String requestMethod = httpExchange.getRequestMethod();
                String query = httpExchange.getRequestURI().getRawQuery();
                String[] endPoints = path.split("/");
                int endPointsCount = endPoints.length;
                int id = 0;
                String[] splitId = new String[]{""};
                if (query != null) {
                    splitId = query.split("=");
                    id = Integer.parseInt(splitId[splitId.length - 1]);
                }

                switch (requestMethod) {
                    case "GET": {
                        if (path.endsWith("tasks") && endPointsCount == 2) {
                            String prioritizedTasksJson = gson.toJson(manager.getPrioritizedTasks());
                            sendText(httpExchange, prioritizedTasksJson);
                        }
                        if (path.endsWith("tasks/task") && endPointsCount == 3) {
                            String tasksJson = gson.toJson(manager.getTasks());
                            sendText(httpExchange, tasksJson);
                        }
                        if (path.endsWith("epic") && endPointsCount == 3) {
                            String epicsJson = gson.toJson(manager.getEpics());
                            sendText(httpExchange, epicsJson);
                        }
                        if (path.endsWith("subtask") && endPointsCount == 3) {
                            String subtasksJson = gson.toJson(manager.getSubtasks());
                            sendText(httpExchange, subtasksJson);
                        }
                        if (endPoints[endPoints.length - 1].equals("history")) {
                            String historyJson = gson.toJson(manager.getHistory());
                            sendText(httpExchange, historyJson);
                        }
                        if (endPoints[endPoints.length - 1].equals("task") && (query != null)) {

                            String taskJson = gson.toJson(manager.getTaskById(id));
                            sendText(httpExchange, taskJson);
                        }
                        if (endPoints[endPoints.length - 1].equals("epic") && (query != null)) {
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            String epicJson = gson.toJson(manager.getEpicById(id));
                            sendText(httpExchange, epicJson);
                        }
                        if (endPoints[endPoints.length - 1].equals("subtask") && (query != null)) {
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            String subTaskJson = gson.toJson(manager.getSubtaskByid(id));
                            sendText(httpExchange, subTaskJson);
                        }
                        break;
                    }
                    case "POST": {
                        if (endPoints[endPoints.length - 1].equals("task")) {
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newTask = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();
                            Task task = gson.fromJson(newTask, Task.class);
                            if (manager.getTasks().contains(task)) {
                                manager.updateTask(task);
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                manager.addTask(task);
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                        }
                        if (endPoints[endPoints.length - 1].equals("epic")) {
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newEpic = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Epic epic = gson.fromJson(newEpic, Epic.class);
                            if (manager.getEpics().contains(epic)) {
                                manager.updateEpic(epic);
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                manager.addEpic(epic);
                                httpExchange.sendResponseHeaders(201, 0);
                            }

                        }

                        if (endPoints[endPoints.length - 1].equals("subtask") && (query == null)) {
                            InputStream inputStream = httpExchange.getRequestBody();
                            String newSubtask = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            inputStream.close();

                            Subtask subtask = gson.fromJson(newSubtask, Subtask.class);
                            if (manager.getSubtasks().contains(subtask)) {
                                manager.updateSubtask(subtask);
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                manager.addSubtask(subtask);
                                httpExchange.sendResponseHeaders(201, 0);
                            }
                        }
                        break;
                    }
                    case "DELETE": {
                        if (endPoints[endPoints.length - 1].equals("tasks") && (query == null)) {
                            manager.clearTasks();
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                        if (endPoints[endPoints.length - 1].equals("epics") && (query == null)) {
                            manager.clearEpics();
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                        if (endPoints[endPoints.length - 1].equals("Subtasks") && (query == null)) {
                            manager.clearEpics();
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                        if (endPoints[endPoints.length - 1].equals("task") && (query != null)) {
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            manager.removeTask(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                        if (endPoints[endPoints.length - 1].equals("epic") && (query != null)) {
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            manager.removeEpic(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                        if (endPoints[endPoints.length - 1].equals("Subtask") && (query != null)) {
                            id = Integer.parseInt(splitId[splitId.length - 1]);
                            manager.removeSubtask(id);
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpExchange.close();
            }
        }
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(DEFAULT_CHARSET);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

}