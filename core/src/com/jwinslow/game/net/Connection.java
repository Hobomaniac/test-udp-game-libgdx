package com.jwinslow.game.net;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jwinslow.game.Handler;
import com.jwinslow.game.gameobjects.OnlinePlayer;
import com.jwinslow.game.gameobjects.Player;
import com.jwinslow.game.states.State;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.ButtonEffects;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by jalen on 12/29/2017.
 */

public class Connection {

    public static final int PORT = 11321;

    public enum MStates {
        CREATE_PLAYER_FROM_CLIENT,
        CREATE_PLAYER_FROM_SERVER,
        REMOVE_PLAYER,
        REMOVE_SERVER_PLAYER,
        GAME,
        PLAYER_NAME,
        ALL_PLAYERS_GAME,
        ALL_PLAYERS_INFO,
        ALL_PLAYERS_CREATE,
        PLAY,
        WAIT
    }

    private static Handler handler;
    public static String receivedInfo;
    public static String sentInfo;
    public static boolean msgReady;
    public static int currentPid;
    public static MStates msgState;
    public static UDPServer udpServer;
    public static UDPClient udpClient;
    public static float elapsedTime;
    //public static TCPServer tcpServer;
    //public static TCPClient tcpClient;

    public static void init(Handler handler) {
        Connection.handler = handler;
        receivedInfo = "";
        sentInfo = "";
        msgReady = false;
        currentPid = 0;
        msgState = MStates.WAIT;
        elapsedTime = 0;
    }

    public static void create(int type) {
        // 0 - UDPServer
        // 1 - UDPClient
        // 2 - TCPServer
        // 3 - TCPClient
        switch (type) {
            case 0:createUDPServer();break;
            case 1:createUDPClient();break;
            case 2:createTCPServer();break;
            case 3:createTCPClient();break;
            default:
                handler.debugger.msg = "No connection type.";
        }
    }

    public static void update(float dt) {
        String m = new String(getReceivedInfo());
        setReceivedInfo("");
        if (!m.equalsIgnoreCase("")) {
            String[] message = m.split(" ");
            receive(message);
        }
        String s = "";
        elapsedTime += dt;
        if (elapsedTime >= 1f/30f) {
            s = send(msgState);
            elapsedTime -= 1f/30f;
        }
        if (!s.equalsIgnoreCase("")) {
            setSentInfo(s);
        }
        if (getMsgReady()) sendWhere();
    }

    public static void receive(String[] message) {
        String type = message[0];
        if (type.equalsIgnoreCase("cs")) {
            createPlayer(currentPid = Player.playerid, message[1]);
            Player.playerid++;
            msgState = MStates.ALL_PLAYERS_CREATE;

        } else if (type.equalsIgnoreCase("c")) {
           createPlayer(Integer.parseInt(message[1]), message[2]);

        } else if (type.equalsIgnoreCase("r")) {
            removePlayer(currentPid = Integer.parseInt(message[1]));
            msgState = MStates.REMOVE_PLAYER;

        } else if (type.equalsIgnoreCase("g")) {
            updatePlayer(Integer.parseInt(message[1]),
                    Float.parseFloat(message[2]), Float.parseFloat(message[3]),
                    Float.parseFloat(message[4]), Integer.parseInt(message[5]));

        } else if (type.equalsIgnoreCase("p")) {
            updatePlayerName(Integer.parseInt(message[1]), message[2]);

        } else if (type.equalsIgnoreCase("2")) { // 2 players
            int numOfPlayers = 2;
            if (message[1].equalsIgnoreCase("g")) {
                updateGamePlayers(numOfPlayers, message);

            } else if (message[1].equalsIgnoreCase("p")) {
                updateNamePlayers(numOfPlayers, message);

            } else if (message[1].equalsIgnoreCase("c")) {
                updateCreatePlayers(numOfPlayers, message);}

        } else if (type.equalsIgnoreCase("3")) {  // 3 players
            int numOfPlayers = 3;
            if (message[1].equalsIgnoreCase("g")) {
                updateGamePlayers(numOfPlayers, message);

            } else if (message[1].equalsIgnoreCase("p")) {
                updateNamePlayers(numOfPlayers, message);

            } else if (message[1].equalsIgnoreCase("c")) {
                updateCreatePlayers(numOfPlayers, message);}

        } else if (type.equalsIgnoreCase("4")) {
            int numOfPlayers = 4;
            if (message[1].equalsIgnoreCase("g")) {
                updateGamePlayers(numOfPlayers, message);

            } else if (message[1].equalsIgnoreCase("p")) {
                updateNamePlayers(numOfPlayers, message);

            } else if (message[1].equalsIgnoreCase("c")) {
                updateCreatePlayers(numOfPlayers, message);

            }
        } else if (type.equalsIgnoreCase("play")){// *** Something wrong here for android
            System.out.println("Playing game now.");
            ButtonEffects.update(ButtonEffects.PLAY_GAME);
        }
    }

    public static String send(MStates mState) {
        String message = "";

        DecimalFormat df = new DecimalFormat("#.#");

        switch (mState) {
            case ALL_PLAYERS_GAME:
                message = handler.getPlayers().size + " g ";
                for (int i = 0; i < handler.getPlayers().size; i++) {
                    message += handler.getPlayers().get(i).getPid() + " " +
                            df.format(handler.getPlayers().get(i).getX()) + " " +
                            df.format(handler.getPlayers().get(i).getY()) + " " +
                            df.format(handler.getPlayers().get(i).getDir()) + " " +
                            handler.getPlayers().get(i).getIdle();
                    if (i+1 != handler.getPlayers().size) {
                        message += " ";
                    }
                }
                setMsgReady(true);
                break;
            case ALL_PLAYERS_INFO:
                message = handler.getPlayers().size + " p ";
                for (int i = 0; i < handler.getPlayers().size; i++) {
                    message += handler.getPlayers().get(i).getPid() + " " +
                            handler.getPlayers().get(i).getName();
                    if (i+1 != handler.getPlayers().size) {
                        message += " ";
                    }
                }
                setMsgReady(true);
                break;
            case ALL_PLAYERS_CREATE:
                message = handler.getPlayers().size + " c ";
                for (int i = 0; i < handler.getPlayers().size; i++) {
                    message += handler.getPlayers().get(i).getPid() + " " +
                            handler.getPlayers().get(i).getName();
                    if (i+1 != handler.getPlayers().size) {
                        message += " ";
                    }
                }
                setMsgReady(true);
                break;
            case CREATE_PLAYER_FROM_CLIENT:
                message = "cs " + handler.getPlayers().get(0).getName();
                setMsgReady(true);
                break;
            case CREATE_PLAYER_FROM_SERVER:
                for (int i = 0; i < handler.getPlayers().size; i++) {
                    if (currentPid == handler.getPlayers().get(i).getPid()) {
                        message = "c " + currentPid + " " + handler.getPlayers().get(i).getName();
                        break;
                    }
                }
                setMsgReady(true);
                break;
            case REMOVE_PLAYER:
                message = "r " + currentPid;
                setMsgReady(true);
                break;
            case REMOVE_SERVER_PLAYER:

                break;
            case GAME:
                message = "g " + handler.getPlayers().get(0).getPid() + " " +
                        df.format(handler.getPlayers().get(0).getX()) + " " +
                        df.format(handler.getPlayers().get(0).getY()) + " " +
                        df.format(handler.getPlayers().get(0).getDir()) + " " +
                        handler.getPlayers().get(0).getIdle();
                setMsgReady(true);
                break;
            case PLAYER_NAME:
                message = "p " + handler.getPlayers().get(0).getPid() + " " +
                        handler.getPlayers().get(0).getName();
                setMsgReady(true);
                break;
            case PLAY:
                message = "play";
                setMsgReady(true);
                break;
            case WAIT:
                if (!getMsgReady()) message = "";
                break;
            default:

                break;
        }
        if (State.getCurrentState() != handler.getGameState())
            msgState = MStates.WAIT;
        else if (State.getCurrentState() == handler.getGameState()) {
            if (udpServer != null)
                msgState = MStates.ALL_PLAYERS_GAME;
            else if (udpClient != null)
                msgState = MStates.GAME;
        }
        return message;
    }

    private static void sendWhere() {
        try {
            if (udpServer != null) udpServer.updateSend();
            if (udpClient != null) udpClient.updateSend();
        } catch (IOException ex) {
            System.out.println("Could not send message.");
        }

    }

    private static void createUDPServer() {
        handler.debugger.msg = "UDP Server";
        udpServer = new UDPServer(handler, PORT);
        udpServer.start();
    }

    private static void createUDPClient() {
        //handler.debugger.msg = "UDP Client";
        udpClient = new UDPClient(handler, PORT);
        udpClient.start();
    }

    private static void createTCPServer() {
        handler.debugger.msg = "TCP Server";
    }

    private static void createTCPClient() {
        handler.debugger.msg = "TCP Client";
    }

    private static void createPlayer(int pid, String name) {
        boolean check = false;
        for (int i = 0; i < handler.getPlayers().size; i++) {
            if (handler.getPlayers().get(i).getName().equalsIgnoreCase(name)) {
                handler.getPlayers().get(i).setPid(pid);
                check = true;
                break;
            }
        }
        if (!check)
            handler.getGameObjectHandler().addPlayer(new OnlinePlayer(handler, 0, 0, new TextureRegion(Assets.player2), pid, name));
    }

    private static void removePlayer(int pid) {
        for (int i = 0; i < handler.getPlayers().size; i++) {
            if (handler.getPlayers().get(i).getPid() == pid) {
                handler.getGameObjectHandler().removePlayer(handler.getPlayers().get(i));
                break;
            }
        }
    }

    private static void updatePlayer(int pid, float x, float y, float dir, int idle) {
        for (int i = 0; i < handler.getPlayers().size; i++) {
            if (i == 0) continue;
            if (handler.getPlayers().get(i).getPid() == pid) {
                handler.getPlayers().get(i).setX(x);
                handler.getPlayers().get(i).setY(y);
                handler.getPlayers().get(i).setDir(dir);
                handler.getPlayers().get(i).setIdle(idle);
                break;
            }
        }
    }

    private static void updatePlayerName(int pid, String name) {
        for (int i = 0; i < handler.getPlayers().size; i++) {
            if (handler.getPlayers().get(i).getPid() == pid) {
                handler.getPlayers().get(i).setName(name);
                break;
            }
        }
    }

    private static void updateGamePlayers(int numOfPlayers, String[] message) {
        String[][] m = new String[numOfPlayers][5];
        int index = 2;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] = message[index];
                index++;
            }
        }
        for (int i = 0; i < numOfPlayers; i++) {
            updatePlayer(Integer.parseInt(m[i][0]),
                    Float.parseFloat(m[i][1]), Float.parseFloat(m[i][2]),
                    Float.parseFloat(m[i][3]), Integer.parseInt(m[i][4]));
        }
    }

    private static void updateNamePlayers(int numOfPlayers, String[] message) {
        String[][] m = new String[numOfPlayers][2];
        int index = 2;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] = message[index];
                index++;
            }
        }
        for (int i = 0; i < numOfPlayers; i++) {
            updatePlayerName(Integer.parseInt(m[i][0]),
                    m[i][1]);
        }
    }

    private static void updateCreatePlayers(int numOfPlayers, String[] message) {
        String[][] m = new String[numOfPlayers][2];
        int index = 2;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] = message[index];
                index++;
            }
        }
        for (int i = 0; i < numOfPlayers; i++) {
            createPlayer(Integer.parseInt(m[i][0]),
                    m[i][1]);
        }
    }

    public static void dispose() {
        System.out.println("Disposing of networks.");
        if (udpServer != null) {udpServer.stop();}
        if (udpClient != null) {udpClient.stop();}
    }

    public static synchronized void setReceivedInfo(String m) {Connection.receivedInfo = m;}
    public static synchronized String getReceivedInfo() {return Connection.receivedInfo;}

    public static synchronized void setSentInfo(String m) {Connection.sentInfo = m;}
    public static synchronized String getSentInfo() {return Connection.sentInfo;}

    public static synchronized void setMsgReady(boolean ready) {Connection.msgReady = ready;}
    public static synchronized boolean getMsgReady() {return Connection.msgReady;}

}
