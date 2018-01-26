package com.jwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jwinslow.game.Handler;
import com.jwinslow.game.Main;
import com.jwinslow.game.ui.UIButton;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.ButtonEffects;
import com.jwinslow.game.utils.Controls;

public class MenuState extends State {
    
    //--- Propreties
    private UIButton startGameButton, exitGameButton, playGameButton, backButton, changeConnectionButton;
    
    //--- Constructor
    public MenuState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        handler.getCam().position.set(Assets.background.getWidth()/2, Assets.background.getHeight()/2, 0);
        startGameButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, "Start\nGame",
                ButtonEffects.START, 2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), true);
        exitGameButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4, "Exit\nGame",
                ButtonEffects.EXIT_GAME, 2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), true);
        playGameButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, "Play\nGame",
                ButtonEffects.START_GAME, 2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), false);
        backButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4, "Back",
                ButtonEffects.BACK, 2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), false);
        changeConnectionButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*3/4, "Create\nUDP Server",
                ButtonEffects.NETWORK_SWITCH, 2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), false);
        handler.getStage().addActor(startGameButton);
        handler.getStage().addActor(exitGameButton);
        handler.getStage().addActor(playGameButton);
        handler.getStage().addActor(backButton);
        handler.getStage().addActor(changeConnectionButton);
    }
    
    @Override
    public void update(float dt) {
        startGameButton.update(dt);
        //handler.message = "" /*+ startGameButton.getRectangle().x //*/+ ", " + Controls.sx + ", " + Controls.sy;
        exitGameButton.update(dt);
        playGameButton.update(dt);
        backButton.update(dt);
        changeConnectionButton.update(dt);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        
        batch.draw(Assets.background, 0 , 0);
    }
    
    @Override
    public void dispose() {
        if (startGameButton != null) startGameButton.dispose();
        if (exitGameButton != null) exitGameButton.dispose();
        if (playGameButton != null) playGameButton.dispose();
        if (backButton != null) backButton.dispose();
        if (changeConnectionButton != null) changeConnectionButton.dispose();
    }
    
    //--- Getters and Setters
    public UIButton getStartGameButton() {return startGameButton;}
    public UIButton getExitGameButton() {return exitGameButton;}
    public UIButton getPlayGameButton() {return playGameButton;}
    public UIButton getBackButton() {return backButton;}
    public UIButton getChangeConnectionButton() {return changeConnectionButton;}
    
}
