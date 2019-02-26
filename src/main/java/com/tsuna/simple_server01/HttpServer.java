package com.tsuna.simple_server01;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    //the webroot for static resources
    public static final String WEB_ROOT=System.getProperty("user.dir")+ File.separator+"webroot";

    //the shutdown command
    private static final String SHUTDOWN_COMMAND="/SHUTDOWN";

    //the shutdown command received
    private boolean shutdown=false;

    public void await() throws IOException {
        ServerSocket serverSocket=null;
        int port=8088;
        try{
            serverSocket=new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        while (!shutdown){
            Socket socket=null;
            InputStream inputStream=null;
            OutputStream outputStream=null;
            try {
                socket=serverSocket.accept();
                inputStream=socket.getInputStream();
                outputStream=socket.getOutputStream();

                //Initialize the Request object
                Request request=new Request(inputStream);
                request.parse();

                //Initialize the Response object
                Response response=new Response(outputStream);
                response.setRequest(request);
                response.sendStaticResource();

                //Close the socket
                socket.close();

                shutdown=request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                inputStream.close();
                outputStream.close();
            }
        }
    }

    public static void main(String... args) throws IOException {
        HttpServer httpServer=new HttpServer();
        httpServer.await();
    }
}
