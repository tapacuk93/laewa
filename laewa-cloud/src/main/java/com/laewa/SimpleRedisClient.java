package com.laewa;

import java.io.*;
import java.net.Socket;

public class SimpleRedisClient {
    private static final SimpleRedisClient instance;

    static {
        try {
            instance = new SimpleRedisClient("localhost", 6379);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SimpleRedisClient instance() {
        return instance;
    }


    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public SimpleRedisClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    // Send a command to Redis
    private void sendCommand(String... args) throws IOException {
        writer.write("*" + args.length + "\r\n");
        for (String arg : args) {
            writer.write("$" + arg.length() + "\r\n");
            writer.write(arg + "\r\n");
        }
        writer.flush();
    }

    // Read a single-line response
    private String readResponse() throws IOException {
        return reader.readLine();
    }

    public void set(String key, String value) throws IOException {
        sendCommand("SET", key, value);
        System.out.println("SET Response: " + readResponse());
    }

    public String get(String key) throws IOException {
        sendCommand("GET", key);
        readResponse(); // skip the "$<length>" line
        return readResponse(); // actual value
    }

    public static void main(String[] args) {
        try {
            SimpleRedisClient redis = new SimpleRedisClient("localhost", 6379);

            redis.set("mykey", "HelloWorld");
            String value = redis.get("mykey");
            System.out.println("Value from Redis: " + value);

            redis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}