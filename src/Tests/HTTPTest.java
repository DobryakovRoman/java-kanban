package Tests;

import Kanban.HTTP.HTTPTaskServer;
import Kanban.HTTP.KVServer;
import Kanban.task.Task;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPTest {

    private KVServer kvServer;
    private HTTPTaskServer httpTaskServer;

    protected LocalDateTime now = LocalDateTime.parse("2022-09-28T23:41:00.625776800");

    @BeforeEach
    void beforeEach() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HTTPTaskServer();
        httpTaskServer.start();
    }

    @Test
    void addTaskTest() throws IOException, InterruptedException {
        Task task1 = new Task("Задача 1", "Создать задачу 1", now, 10);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Gson gson = new Gson();
        String json = gson.toJson(task1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
        httpTaskServer.stop();
    }

}
