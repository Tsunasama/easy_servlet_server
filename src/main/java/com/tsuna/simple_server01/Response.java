package com.tsuna.simple_server01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private OutputStream outputStream;

    private Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response(OutputStream outputStream){
        this.outputStream=outputStream;
    }

    public void sendStaticResource() throws IOException {
        final int BUFFER_SIZE=1024;
        byte[] buffer=new byte[BUFFER_SIZE];
        FileInputStream fileInputStream=null;
        try{
            File file=new File(HttpServer.WEB_ROOT,request.getUri());
            if(file.exists()){
                fileInputStream=new FileInputStream(file);
                int count;
                while ((count=fileInputStream.read(buffer,0,BUFFER_SIZE))!=-1){
                    outputStream.write(buffer,0,count);
                }
            }else {
                //file not found
                String errorMessage="HTTP/1.1 404 File not found\r\n"+
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                outputStream.write(errorMessage.getBytes());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                fileInputStream.close();
            }
        }
    }
}
