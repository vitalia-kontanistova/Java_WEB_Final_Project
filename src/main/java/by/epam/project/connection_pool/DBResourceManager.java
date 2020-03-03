package by.epam.project.connection_pool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();
    private final static String BASE_NAME = "db";
    private ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME);

    public static DBResourceManager getInstance() {
        return instance;
    }

    private DBResourceManager() {
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}