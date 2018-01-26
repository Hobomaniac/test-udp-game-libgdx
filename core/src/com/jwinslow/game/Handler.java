package com.jwinslow.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.jwinslow.game.gameobjects.GameObject;
import com.jwinslow.game.gameobjects.GameObjectHandler;
import com.jwinslow.game.gameobjects.Player;
import com.jwinslow.game.net.Connection;
import com.jwinslow.game.states.*;
import com.jwinslow.game.ui.UIDebug;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.ButtonEffects;
import com.jwinslow.game.utils.CameraHandler;
import com.jwinslow.game.utils.Controls;
import com.jwinslow.game.utils.Fonts;
import com.jwinslow.game.utils.TextInputListener;

public class Handler {
    
    //--- Propreties
    private float elapsedTime;

    public UIDebug debugger;
    private TextInputListener til;
    private CameraHandler camHandler;
    private GameObjectHandler gameObjectHandler;
    private State initState;
    private State gameState;
    private State menuState;
    private State pauseState;
    private State connectState;
    private Main main;
    
    private BitmapFont font;
    public String message;
    
    //--- Constructor
    public Handler(Main main) {
        this.main = main;
        font = new BitmapFont(Gdx.files.internal("customPixelFont1.fnt"));
        font.setColor(Color.RED);
        font.getData().scale(0.01f);
        message = "";
        elapsedTime = 0;
    }
    
    
    //--- Methods
    public void init() {
        debugger = new UIDebug(this);
        getStage().addActor(debugger);
        debugger.msg = "Debugger:";

        Connection.init(this);
        til = new TextInputListener(this);

        camHandler = new CameraHandler(this, main.getCam());
        gameObjectHandler = new GameObjectHandler(this);
        initState = new InitState(this);
        gameState = new GameState(this);
        menuState = new MenuState(this);
        pauseState = new PauseState(this);
        connectState = new ConnectState(this, -1);
        State.setCurrentState(initState);
        State.getCurrentState().init();
        Controls.initControls();
        Controls.setControllerType(Controls.TOUCH);
        ButtonEffects.init(this);
        Fonts.init(this);
    }
    
    public void update(float dt) {
        Connection.update(dt);
        elapsedTime += dt;
        //message = "" + Gdx.graphics.getWidth();//(int) elapsedTime;

        debugger.update(dt);
        Controls.update();
        camHandler.update(dt);
        if (State.getCurrentState() != null) State.getCurrentState().update(dt);
        //main.stage.act(dt);
    }
    
    public void render(SpriteBatch batch) {
        if (State.getCurrentState() != null) State.getCurrentState().render(batch);
        font.draw(batch, message, getCam().position.x-main.cam.viewportWidth/2+2, getCam().position.y-main.cam.viewportHeight/2+15);
    } 
    
    public void dispose() {
        gameObjectHandler.dispose();
        System.out.println("Disposing of textures.");
        initState.dispose();
        gameState.dispose();
        menuState.dispose();
        pauseState.dispose();
        connectState.dispose();
        Assets.disposeAllTextures();
        font.dispose();
        Fonts.dispose();
        debugger.dispose();
        Connection.dispose();
    }
    
    public void addGameObject(GameObject o) {
        gameObjectHandler.add(o);
    }
    
    //--- Getters and Setters
    public Main getMain() {return main;}
    public InitState getInitState() {return (InitState)initState;}
    public GameState getGameState() {return (GameState) gameState;}
    public MenuState getMenuState() {return (MenuState) menuState;}
    public PauseState getPauseState() {return (PauseState) pauseState;}
    public ConnectState getConnectState() {return (ConnectState) connectState;}
    public OrthographicCamera getCam() {return main.getCam();}
    public CameraHandler getCamHandler() {return camHandler;}
    public GameObjectHandler getGameObjectHandler() {return gameObjectHandler;}
    public Array<Player> getPlayers() {return gameObjectHandler.getPlayers();}
    public Stage getStage() {return main.stage;}
    public TextInputListener getTil() {return til;}
}
