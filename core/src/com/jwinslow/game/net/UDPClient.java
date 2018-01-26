package com.jwinslow.game.net;

import com.jwinslow.game.Handler;
import com.jwinslow.game.gameobjects.OnlinePlayer;
import com.jwinslow.game.gameobjects.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import javax.xml.crypto.Data;

/**
 * Created by jalen on 1/14/2018.
 */

public class UDPClient implements Runnable {

    enum States {
        CONNECTING,
        CONNECTED,
        GAME;
    }

    private Handler handler;
    private Thread t;
    private InetAddress serverAddress;
    private int port;
    private States state;
    private DatagramSocket socket;
    private boolean bExit;

    public UDPClient(Handler handler, int port) {
        this.handler = handler;
        this.port = port;
        state = States.CONNECTING;
        bExit = false;
    }


    @Override
    public void run() {
        try {
            socket = new DatagramSocket(port);
            serverAddress = InetAddress.getByName(handler.getConnectState().getIpAddressArea().getMessage());
            while (!bExit) {
                try {
                    if (socket.isClosed()) socket = new DatagramSocket(port);
                    System.out.println("Running UDP Client");
                    updateReceive(socket);
                } catch (IOException ex) {
                    handler.debugger.msg = "UDP Client stopped working.";
                } catch (RuntimeException ex) {
                    handler.debugger.msg = "UDP Client stopped working.";
                }
            }
            socket.close();
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

        stop();
    }

    public synchronized void start() {
        t = new Thread(this);
        t.start();
    }

    public synchronized void stop() {
        System.out.println("Stopping UDP Client.");
        handler.debugger.msg = "Stopping UDP Client.";
        bExit = true;
    }

    public void updateSend() throws IOException, RuntimeException {
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    DatagramSocket s = new DatagramSocket(port+1);
                    String data = "";
                    if (Connection.getMsgReady()) {
                        data = Connection.getSentInfo();
                        Connection.setSentInfo("");
                        Connection.setMsgReady(false);
                    }
                    if (data != null && !data.equalsIgnoreCase("")) {
                        System.out.println("Client-Message sent is: " + data);
                        s.send(new DatagramPacket(data.getBytes(), data.getBytes().length, serverAddress, port));
                        handler.debugger.msg = serverAddress.getHostAddress() + " " + port;
                    }
                    s.close();
                } catch (SocketException ex) {
                    System.out.println("Could not create socket. UDPClient.updateSend()");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }).start();

    }

    public void updateReceive(DatagramSocket socket) throws IOException, RuntimeException {
        DatagramPacket request = new DatagramPacket(new byte[512], 512);
        socket.receive(request);
        String message = new String(request.getData(), 0, request.getLength());
        System.out.println("Client - Message received is: " + message);
        Connection.setReceivedInfo(message);
    }

}
