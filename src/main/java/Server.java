import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private DataOutputStream out;
    private DataInputStream in;
    private String exit = "/exit";
    private String userName;
    private Socket socket;
    private UserList userList;

    protected Server(Socket socket, UserList userList) throws IOException {
        this.userList = userList;
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        start();
    }

    private void addLogin() {
        try {
            out.writeUTF("Enter username: ");
            userName = in.readUTF();
            out.writeUTF("Hi " + userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userList.addUser(userName, this);
        sendMassage("New user connect :" + userName);

    }

    @Override
    public void run() {
        addLogin();
        String text;

        try {
            while (!socket.isClosed()) {
                text = in.readUTF();
                if (text.equals(exit)) {
                    closeSocket();
                    break;
                } else {
                    sendMassage(text);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendMassage(String text) {
        writeLog(text);
        userList.getMap().forEach((key, value) -> {
            try {
                if (!key.equals(this.userName)) {
                    value.out(text);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void closeSocket() {
        writeLog(this.userName + " exit");
        try {
            if (!socket.isClosed()) {
                out(exit);
                socket.close();
                in.close();
                out.close();
                userList.removeUser(this.userName);
            }
        } catch (IOException ignored) {
        }
    }

    private void out(String text) throws IOException {
        out.writeUTF(text);

    }

    private void writeLog(String text) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("fileServer.log", true))) {
            bufferedWriter.write(text + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
