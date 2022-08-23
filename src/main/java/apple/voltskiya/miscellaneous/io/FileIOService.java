package apple.voltskiya.miscellaneous.io;

import apple.utilities.threading.service.queue.TaskHandlerQueue;

public class FileIOService {
    private static final TaskHandlerQueue instance = new TaskHandlerQueue(10, 0, 0);

    public static TaskHandlerQueue get() {
        return instance;
    }
}
