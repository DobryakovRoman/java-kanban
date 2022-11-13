package Kanban.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import Kanban.HTTP.KVTaskClient;
import Kanban.task.Epic;
import Kanban.task.Subtask;
import Kanban.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HTTPTaskManager extends FileBackedTaskManager {
    KVTaskClient kvTaskClient = new KVTaskClient();
    Gson gson = Managers.getGson();

    public HTTPTaskManager() {
        super(null);
    }

    public void load() {
        try {
            String jsonTasks = kvTaskClient.load("tasks");
            String jsonEpics = kvTaskClient.load("epics");
            String jsonSubTasks = kvTaskClient.load("subtasks");
            String jsonHistory = kvTaskClient.load("history");

            tasks = gson.fromJson(jsonTasks, new TypeToken<HashMap<Integer, Task>>(){}.getType());
            epics = gson.fromJson(jsonEpics, new TypeToken<HashMap<Integer, Epic>>(){}.getType());
            subtasks = gson.fromJson(jsonSubTasks, new TypeToken<HashMap<Integer, Subtask>>(){}.getType());

            List<Integer> history = gson.fromJson(jsonHistory, new TypeToken<List<Integer>>(){}.getType());
            if (history != null) {
                for (Integer id : history) {
                    if (getTaskById(id) == null) {
                        if (getEpicById(id) == null) {
                            getSubtaskByid(id);
                        }
                    }
                }
            }
            if (tasks == null) {
                tasks = new HashMap<>();
            }
            if (epics == null) {
                epics = new HashMap<>();
            }
            if (subtasks == null) {
                subtasks = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        String jsonTasks = gson.toJson(tasks);
        kvTaskClient.put("tasks", jsonTasks);

        String jsonEpics = gson.toJson(epics);
        kvTaskClient.put("epics", jsonEpics);

        String jsonSubTasks = gson.toJson(subtasks);
        kvTaskClient.put("subtasks", jsonSubTasks);

        List<Integer> historyId = new ArrayList<>();
        for (Task task : getHistory()) {
            historyId.add(task.getid());
        }
        String jsonHistory = gson.toJson(historyId);
        kvTaskClient.put("history", jsonHistory);
    }
}
