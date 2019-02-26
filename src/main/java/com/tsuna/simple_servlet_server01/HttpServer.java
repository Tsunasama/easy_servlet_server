package com.tsuna.simple_servlet_server01;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private boolean shutdown = false;

    public static void main(String... args) throws IOException {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8088, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!shutdown) {
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            socket=serverSocket.accept();


            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }
}
