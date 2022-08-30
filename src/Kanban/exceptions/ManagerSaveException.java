package Kanban.exceptions;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(String message) {
        super(message);
    }

}
