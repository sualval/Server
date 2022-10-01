import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static int port;
    private static UserList userList;
    private static String fileName = "src/main/java/settings.txt";

    public static void main(String[] args) throws IOException {
        userList = new UserList();

        port = PortReader();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    new Server(socket, userList);
                } catch (Exception e) {
                    socket.close();
                }
            }
        }
    }

    public static int PortReader() {
        int port = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            port = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | IOException exception) {
            System.out.println("Number format exception");
        }
        return port;
    }
}
