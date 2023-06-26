package sdfs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Controller {

  ServerSocket serverSocket;
  int cport, timeout, rebalancePeriod;
  // need a list of active connections
  List<DstoreConnection> dstoreConnections = Collections.synchronizedList(new ArrayList<DstoreConnection>());

  public Controller(int cport, int timeout, int rebalancePeriod) {

    this.cport = cport;
    this.timeout = timeout;
    this.rebalancePeriod = rebalancePeriod;

  }

  void create_server() throws IOException {

    ServerSocket server = new ServerSocket(cport);
    Socket newConnection;
    while (!server.isClosed()) {

      newConnection = server.accept();

      handle_new_connection(newConnection);

    }

    server.close();
  }

  void handle_new_connection(Socket connection) {

    while (true) {
      String message;
      try {

        // Reading initial message of new connection
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        message = reader.readLine();

        String[] args = message.split(" ");

        // New dstore has joined
        if (message.startsWith("JOIN")) {
          int port = Integer.parseInt(message.split(" ")[2]);
          connection.setKeepAlive(true);
          DstoreConnection dstoreConn = new DstoreConnection(connection, port, reader);
          dstoreConnections.add(dstoreConn);
        } else {
          handle_new_client(connection, args);
        }

      } catch (IOException e) {
        // Handle IO exception here
        e.printStackTrace();
      }

    }

  }

  private void handle_new_client(Socket socket, String[] args) throws IOException {

    ClientConnection connection = new ClientConnection(socket);

    if (args[0].startsWith("STORE")) {
      request_store(connection);
    }
    if (args[0].startsWith("LIST")) {
      request_list();
    }
    if (args[0].startsWith("LOAD")) {
      request_load(args[1], connection, false);
    }
    if (args[0].startsWith("RELOAD")) {
      request_load(args[1], connection, true);
    }
    if (args[0].startsWith("REMOVE")) {
      request_remove(connection, args[1]);
    } else {
      // Malformed message
    }
  }

  private void request_remove(ClientConnection connection, String string) {
  }

  private void request_load(String string, ClientConnection connection, boolean b) {
  }

  private void request_load(String string, Connection socket) {
  }

  private void request_list() {
  }

  void request_store(ClientConnection connection) {

  }

}
