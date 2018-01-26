package com.jwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jwinslow.game.Handler;
import com.jwinslow.game.Main;
import com.jwinslow.game.gameobjects.Player;
import com.jwinslow.game.ui.PauseButton;
import com.jwinslow.game.ui.PointerAnchor;
import com.jwinslow.game.utils.Assets;

public class GameState extends State {
    
    //--- Propreties
    private PointerAnchor pointer;
    private PauseButton pauseButton;
    
    //--- Constructor
    public GameState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {

        handler.getCamHandler().setObjOfFocus(handler.getPlayers().get(0));
        pointer = new PointerAnchor(handler, new TextureRegion(Assets.touchAnchor), 0, 0, handler.getPlayers().get(0));
        pauseButton = new PauseButton(handler, new TextureRegion(Assets.pauseButton));
        handler.getStage().addActor(pointer);
        handler.getStage().addActor(pauseButton);
        System.out.println("Creating player and adding to gameObjects" + handler.getGameObjectHandler().getGameObjects().size);
    }
    
    @Override
    public void update(float dt) {
        handler.getGameObjectHandler().update(dt);
        pointer.update(dt);
        pauseButton.update(dt);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Assets.background, 0, 0);
        handler.getGameObjectHandler().render(batch);
    }
    
    @Override
    public void dispose() {
        //if (pointer != null) pointer.dispose();  //no need for this

    }
    
    //--- Getters and Setters
    public PointerAnchor getPointer() {return pointer;}
    public PauseButton getPauseButton() {return pauseButton;}

    
}
