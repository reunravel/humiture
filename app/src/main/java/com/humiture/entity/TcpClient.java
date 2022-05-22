package com.humiture.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TcpClient {
    private static TcpClient tcpClient;
    private final String ipAddress = "192.168.4.1";
    private final int port = 8080;
    private Socket socket;
    private InputStream inputStream;
    private OnReceiveCallbackBlock receiveCallbackBlock;
    private OnReceiveCallbackBlock receiveCallbackBlockBox;

    private TcpClient() {
        super();
    }

    public static TcpClient sharedCenter() {
        if (tcpClient == null) {
            tcpClient = new TcpClient();
        }
        return tcpClient;
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
                try {
                    Thread.sleep(1000);
                    connect();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
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
                    Float[] TaH = {0f, 0f};
                    TaH[0] = Float.parseFloat(StringUtils.substringBefore(StringUtils.substringAfter(str, ":"), "C"));
                    TaH[1] = Float.parseFloat(StringUtils.substringBeforeLast(StringUtils.substringAfterLast(str, ":"), "%"));
                    receiveCallbackBlock.callback(TaH);
                    receiveCallbackBlockBox.callback(TaH);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setReceiveCallbackBlock(OnReceiveCallbackBlock receiveCallbackBlock) {
        this.receiveCallbackBlock = receiveCallbackBlock;
    }

    public void setReceiveCallbackBlockBox(OnReceiveCallbackBlock receiveCallbackBlockBox) {
        this.receiveCallbackBlockBox = receiveCallbackBlockBox;
    }

    public interface OnReceiveCallbackBlock {
        void callback(Float[] receivedMessage);
    }
}
