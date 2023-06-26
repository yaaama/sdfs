package sdfs;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class DStore implements Connection {

    int port, cport, timeout;
    String fileFolder;

    Socket socket; // Socket to communicate with Controller using
    PrintWriter socketWriter; // Writes to Controller socket
    BufferedReader socketReader; // Reads from Controller socket
    ServerSocket server; // Server socket to allow other Dstores and Clients to conect to.

    public DStore(int port, int cport, int timeout, String fileFolder) {

        this.port = port;
        this.cport = cport;
        this.timeout = timeout;
        this.fileFolder = fileFolder;

    }

    void connect_to_controller(int cport) throws UnknownHostException, IOException {

        socket = new Socket("localhost", cport);
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketWriter = new PrintWriter(socket.getOutputStream(), true);

        String message = "JOIN %d", port;
        send_message(message);

    }

    @Override
    public void send_message(String msg) {
        socketWriter.print(msg);
        socketWriter.flush();
    }

    @Override
    public String receive_message() {
        // TODO Auto-generated method stub
        return null;
    }

}
