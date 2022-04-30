package com.humiture;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TcpClient {
    private static TcpClient instance;
    private final String ipAddress = "192.168.4.1";
    private final int port = 8080;
    Float[] TaH = {0f, 0f};
    private Socket socket;
    private InputStream inputStream;
    private OnReceiveCallbackBlock receivedCallback;

    private TcpClient() {
        super();
    }

    public static TcpClient sharedCenter() {
        if (instance == null) {
            synchronized (TcpClient.class) {
                if (instance == null) {
                    instance = new TcpClient();
                }
            }
        }
        return instance;
    }

    public void connect() {
        Thread thread = new Thread(() -> {
            try {
                socket = new Socket(ipAddress, port);
                if (isConnected()) {
                    inputStream = socket.getInputStream();
                    receive();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void receive() {
        while (isConnected()) {
            try {
                byte[] bt = new byte[64];
                int length = inputStream.read(bt);
                if (length <= 0) {
                    connect();
                    return;
                }
                String str = new String(bt, 0, length);
                if (str.contains("TEMP:") || str.contains("HUM:")) {
                    TaH[0] = Float.parseFloat(StringUtils.substringBefore(StringUtils.substringAfter(str, ":"), "C"));
                    TaH[1] = Float.parseFloat(StringUtils.substringBeforeLast(StringUtils.substringAfterLast(str, ":"), "%"));
                    receivedCallback.callback(TaH);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setReceivedCallback(OnReceiveCallbackBlock receivedCallback) {
        this.receivedCallback = receivedCallback;
    }

    public interface OnReceiveCallbackBlock {
        void callback(Float[] receivedMessage);
    }
}
