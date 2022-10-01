import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


class ServerTest {
    Socket socket;

    @BeforeEach

    public void start() {

    }

    @Test
    void d() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(12345)
        ) {
            Socket servsocket = serverSocket.accept();

            new Server(servsocket, new UserList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        socket = new Socket("localhost", 12345);


        {
            try {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());




              //  outputStream.writeUTF("123");
                System.out.println(inputStream.readUTF());
            } catch (Exception w) {
                w.printStackTrace();
            }
        }


    }
}