package com.jwinslow.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.jwinslow.game.Handler;
import com.jwinslow.game.net.Connection;
import com.jwinslow.game.states.State;

public enum ButtonEffects {
    
    //--- Propreties
    START,
    PLAY_GAME,
    START_GAME,
    NETWORK_SWITCH,
    EXIT_GAME,
    BACK,
    PAUSE_GAME,
    CONTINUE_GAME,
    MAIN_MENU,
    CONNECT;
    
    
    private static Handler handler;
    
    
    //--- Constructor
    
    
    //--- Methods
    public static void init(Handler handler) {
        ButtonEffects.handler = handler;
    }
    
    public static void update(ButtonEffects buttonEffect) {
        switch (buttonEffect) {
            case START:
                handler.getMenuState().getStartGameButton().setVisible(false);
                handler.getMenuState().getExitGameButton().setVisible(false);
                handler.getMenuState().getBackButton().setVisible(true);
                handler.getMenuState().getChangeConnectionButton().setVisible(true);
                handler.getMenuState().getPlayGameButton().setVisible(true);
                break;
            case PLAY_GAME:
                if (Connection.udpServer != null) Connection.msgState = Connection.MStates.PLAY;
                State.setCurrentState(handler.getGameState());
                handler.getConnectState().dispose();
                State.getCurrentState().init();
                break;
            case START_GAME:
                String m2 = handler.getMenuState().getChangeConnectionButton().getMessage();
                int type = -1;
                State.setCurrentState(handler.getConnectState());
                if (m2.equalsIgnoreCase("Create\nUDP Server")) {type = 0;}
                else if (m2.equalsIgnoreCase("Create\nUDP Client")) {type = 1;}
                else if (m2.equalsIgnoreCase("Create\nTCP Server")) {type = 2;}
                else if (m2.equalsIgnoreCase("Create\nTCP Client")) {type = 3;}
                else {
                    //State.setCurrentState(handler.getGameState());
                }
                handler.getConnectState().setConnectionType(type);
                handler.getMenuState().dispose();
                State.getCurrentState().init();
                break;
            case NETWORK_SWITCH:
                String s = handler.getMenuState().getChangeConnectionButton().getMessage();
                if (s.equalsIgnoreCase("Create\nUDP Server"))
                    s = "Create\nUDP Client";
                else if (s.equalsIgnoreCase("Create\nUDP Client")) {
                    s = "Create\nTCP Server";
                } else if (s.equalsIgnoreCase("Create\nTCP Server"))
                    s = "Create\nTCP Client";
                else if (s.equalsIgnoreCase("Create\nTCP Client"))
                    s = "Don't\nConnect";
                else if (s.equalsIgnoreCase("Don't\nConnect"))
                    s = "Create\nUDP Server";
                handler.getMenuState().getChangeConnectionButton().setMessage(s);
                break;
            case EXIT_GAME:
                Gdx.app.exit();
                break;
            case BACK:
                handler.getMenuState().getStartGameButton().setVisible(true);
                handler.getMenuState().getExitGameButton().setVisible(true);
                handler.getMenuState().getBackButton().setVisible(false);
                handler.getMenuState().getChangeConnectionButton().setVisible(false);
                handler.getMenuState().getPlayGameButton().setVisible(false);
                break;
            case PAUSE_GAME:
                handler.getGameState().getPointer().setVisible(false);
                handler.getGameState().getPauseButton().setVisible((false));
                State.setCurrentState(handler.getPauseState());
                State.getCurrentState().init();
                break;
            case CONTINUE_GAME:
                handler.message = "";
                State.getCurrentState().dispose();
                handler.getGameState().getPointer().setVisible(true);
                handler.getGameState().getPauseButton().setVisible(true);
                State.setCurrentState(handler.getGameState());
                break;
            case MAIN_MENU:
                State.getCurrentState().dispose();
                handler.getGameObjectHandler().dispose();
                handler.getPlayers().clear();
                handler.getCamHandler().setObjOfFocus(null);
                handler.getCamHandler().reset();
                State.setCurrentState(handler.getMenuState());
                State.getCurrentState().init();
                break;
            case CONNECT:
                Connection.create(handler.getConnectState().getConnectionType());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {ex.printStackTrace();}
                Connection.msgState = Connection.MStates.CREATE_PLAYER_FROM_CLIENT;
                handler.getConnectState().getPlayButton().setVisible(false);
                break;
            default:
                
                break;
        }
    } 
    
    //--- Getters and Setters
    public static void setHandler(Handler handler) {ButtonEffects.handler = handler;}
    
}
