package com.jwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jwinslow.game.Handler;
import com.jwinslow.game.Main;
import com.jwinslow.game.gameobjects.OnlinePlayer;
import com.jwinslow.game.gameobjects.Player;
import com.jwinslow.game.net.Connection;
import com.jwinslow.game.ui.UIButton;
import com.jwinslow.game.ui.UITextArea;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.ButtonEffects;

/**
 * Created by jalen on 1/8/2018.
 */

public class ConnectState extends State {

    private int connectionType;

    private UIButton playButton, mainMenuButton;
    private UITextArea ipAddressArea, playersArea, playerNameArea;

    public ConnectState(Handler handler, int connectionType) {
        super(handler);
        this.connectionType = connectionType;
    }

    @Override
    public void init() {
        if (connectionType == -1) {
            handler.getGameObjectHandler().addPlayer(new Player(handler, Assets.background.getWidth()/2, Assets.background.getHeight()/2,
                    new TextureRegion(Assets.player1)));
            State.setCurrentState(handler.getGameState());
            handler.getConnectState().dispose();
            State.getCurrentState().init();
            return;
        }

        Connection.init(handler);

        boolean editText = false;
        String ipAddress = "IP Address here.";
        ButtonEffects be = ButtonEffects.CONNECT;
        boolean canBePressed = true;
        String buttonMessage = "No message";
        switch (connectionType) {
            case 0:
                editText = false;
                handler.getGameObjectHandler().addPlayer(new Player(handler, 0, 0,
                        new TextureRegion(Assets.player1)));
                handler.getPlayers().get(0).setPid(Player.playerid);
                Player.playerid++;
                be = ButtonEffects.PLAY_GAME;
                buttonMessage = "Play";
                canBePressed = false;
                Connection.create(connectionType);
                break;
            case 1:
                editText = true;
                handler.getGameObjectHandler().addPlayer(new Player(handler, 0, 0,
                        new TextureRegion(Assets.player1)));
                buttonMessage = "Connect";
                break;
            case 2: editText = false; break;
            case 3: editText = true; break;
            default: break;
        }

        playButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()*3/4, Gdx.graphics.getHeight()/8, buttonMessage,
                be, 0.75f, 2.5f * (Gdx.graphics.getWidth()/ Main.WIDTH), true, canBePressed);
        mainMenuButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/8, "Back",
                ButtonEffects.MAIN_MENU, 0.75f, 2.5f * (Gdx.graphics.getWidth()/ Main.WIDTH), true, true);

        playerNameArea = new UITextArea(handler, new TextureRegion(Assets.textArea),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*8/9, 1.25f, 0.2f,
                2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), 1, 12, "Enter Name", true);
        playerNameArea.setTitle("Enter Player Name:");
        playerNameArea.setHint("Name here");

        ipAddressArea = new UITextArea(handler, new TextureRegion(Assets.textArea),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*7/9, 1.25f, 0.2f,
                2f * (Gdx.graphics.getWidth()/Main.WIDTH), 7, 15, ipAddress, editText);
        ipAddressArea.setTitle("Enter IP Address:");
        ipAddressArea.setHint("Ex: 192.168.0.1");

        playersArea = new UITextArea(handler, new TextureRegion(Assets.textArea),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*4/9, 1.5f, 0.65f,
                2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), false);

        handler.getStage().addActor(playButton);
        handler.getStage().addActor(mainMenuButton);
        handler.getStage().addActor(ipAddressArea);
        handler.getStage().addActor(playersArea);
        handler.getStage().addActor(playerNameArea);
    }

    @Override
    public void update(float dt) {
        playButton.update(dt);
        mainMenuButton.update(dt);
        ipAddressArea.update(dt);
        playersArea.update(dt);
        playerNameArea.update(dt);

        if (!playerNameArea.getMessage().equalsIgnoreCase("Enter Name") && handler.getPlayers().size != 0) {
            handler.getPlayers().get(0).setName(playerNameArea.getMessage());
        }

        if (handler.getPlayers().size > 1) {
            playButton.setCanBePressed(true);
        }

        String message = "Players:";
        for (int i = 0; i < handler.getPlayers().size; i++) {
            Player player = handler.getPlayers().get(i);
            message += "\n"+player.getName();
        }
        playersArea.setMessage(message);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Assets.background, 0, 0);
    }

    @Override
    public void dispose() {
        if (playButton != null) playButton.dispose();
        if (mainMenuButton != null) mainMenuButton.dispose();
        if (ipAddressArea != null) ipAddressArea.dispose();
        if (playersArea != null) playersArea.dispose();
        if (playerNameArea != null) playerNameArea.dispose();
    }

    public void addOnlinePlayer(int pid, String name) {
        handler.getGameObjectHandler().addPlayer(new OnlinePlayer(handler, Assets.background.getWidth()/2, Assets.background.getHeight()/2,
                new TextureRegion(Assets.player2), pid, name));
    }

    //Getters and Setters
    public int getConnectionType() {return connectionType;}
    public UIButton getPlayButton() {return playButton;}
    public UIButton getMainMenuButton() {return mainMenuButton;}
    public UITextArea getIpAddressArea() {return ipAddressArea;}
    public UITextArea getPlayersArea() {return playersArea;}
    public UITextArea getPlayerNameArea() {return playerNameArea;}

    public void setConnectionType(int type) {this.connectionType = type;}
}
