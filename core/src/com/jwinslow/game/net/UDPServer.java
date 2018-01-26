package com.jwinslow.game.net;

import com.badlogic.gdx.utils.Array;
import com.jwinslow.game.Handler;
import com.jwinslow.game.gameobjects.OnlinePlayer;
import com.jwinslow.game.gameobjects.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DecimalFormat;

/**
 * Created by jalen on 1/13/2018.
 */

public class UDPServer implements Runnable {

    enum States {
        CONNECT,
        GAME
    }

    enum sendStates {
        CREATE_PLAYER,
        REMOVE_PLAYER,
        PLAYER_POS,
        PLAYER_NAME,
        WAIT
    }

    private Handler handler;
    private Thread t;
    private int port;
    private Array<InetAddress> addresses;
    private InetAddress currentAddr;
    private States state;
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private boolean bExit;


    public UDPServer(Handler handler, int port) {
        this.handler = handler;
        this.port = port;
        addresses = new Array<InetAddress>();
        state = States.CONNECT;
        bExit = false;
    }

    @Override
    public void run() {

        try {
            socket = new DatagramSocket(port);
            while (!bExit) {
                try {
                    if (socket.isClosed()) socket = new DatagramSocket(port);
                    handler.debugger.msg = "Running UDP server";
                    System.out.println("Running UDP server.");
                    updateReceive(socket);
                } catch (IOException ex) {
                    handler.debugger.msg = "UDP Server stopped working. IOException";
                    ex.printStackTrace();
                } catch (RuntimeException ex) {
                    handler.debugger.msg = "UDP Server stopped working. RuntimeException";
                    ex.printStackTrace();
                }
            socket.close();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        stop();
    }

    public synchronized void start() {
        t = new Thread(this);
        t.start();
    }

    public synchronized void stop() {
        System.out.println("Stopping UDP Server.");
        handler.debugger.msg = "Stopping UDP Server.";
        bExit = true;
    }

    public void updateReceive(DatagramSocket socket) throws IOException {
        DatagramPacket request = new DatagramPacket(new byte[512], 512);
        socket.receive(request);
        currentAddr = request.getAddress();
        if (!addresses.contains(currentAddr, false)) {
            addresses.add(currentAddr);
        }
        String message = new String(request.getData(), 0, request.getLength());
        System.out.println("Server-Message receieved is: " + message);
        Connection.setReceivedInfo(message);

    }

    public void updateSend() throws IOException, RuntimeException {
        new Thread(new Runnable() {
           @Override
            public void run() {
               DatagramSocket s;
               try {
                   s = new DatagramSocket(port+1);
                   String data = "";
                   if (Connection.getMsgReady()) {
                       data = Connection.getSentInfo();
                       Connection.setSentInfo("");
                       Connection.setMsgReady(false);
                   }
                   if (data != null && !data.equalsIgnoreCase("")) {
                       System.out.println("Server-Message sent is: " + data);
                       for (int i = 0; i < addresses.size; i++) {
                           s.send(new DatagramPacket(data.getBytes(), data.getBytes().length, addresses.get(i), port));
                       }
                   }
                   s.close();
               } catch (SocketException ex) {
                   System.out.println("Could not create socket. UDPServer.updateSend()");
                   ex.printStackTrace();
               } catch (IOException ex) {
                   ex.printStackTrace();
               }
           }
        }).start();


    }

    //Getters and Setters
    public int getPort() {return port;}
    public States getState() {return state;}
    public InetAddress getIpAddress() {return ipAddress;}

    public void setPort(int port) {this.port = port;}
    public void setState(States state) {this.state = state;}
}
