package sdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

interface Connection {

    void send_message(String msg);

    String receive_message() throws IOException;

}

class ClientConnection implements Connection {

    final int port;
    final BufferedReader reader;
    final PrintWriter writer;
    final Socket socket;

    ClientConnection(Socket socket) throws IOException {

        this.socket = socket;
        this.port = socket.getPort();
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);

    }

    @Override
    public void send_message(String msg) {

        this.writer.print(msg);
        this.writer.flush();

    }

    @Override
    public String receive_message() throws IOException {

        return this.reader.readLine();
    }

}

/**
 * DstoreConnection
 */
class DstoreConnection implements Connection {

    final int port;
    final int serverPort;
    final BufferedReader reader;
    final PrintWriter writer;
    final Socket socket;

    DstoreConnection(Socket socket, int serverPort) throws IOException {

        this.socket = socket;
        this.serverPort = serverPort;
        this.port = socket.getPort();
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);

    }

    @Override
    public void send_message(String msg) {

        this.writer.print(msg);
        this.writer.flush();

    }

    @Override
    public String receive_message() throws IOException {

        return this.reader.readLine();
    }

}
