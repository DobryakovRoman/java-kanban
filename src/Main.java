import Kanban.HTTP.HTTPTaskServer;
import Kanban.HTTP.KVServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new KVServer().start();
            new HTTPTaskServer().start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
