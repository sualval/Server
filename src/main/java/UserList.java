import java.util.HashMap;
import java.util.Map;

public class UserList {
    private static Map<String, Server> userMap = new HashMap<>();

    public Map<String, Server> getMap() {
        return userMap;
    }

    protected void addUser(String nameUser, Server connection) {
        userMap.put(nameUser, connection);
    }

    protected void removeUser(String nameUser) {
        userMap.get(nameUser).interrupt();
        userMap.remove(nameUser);
    }
}
