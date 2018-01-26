package com.jwinslow.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jwinslow.game.Handler;
import com.jwinslow.game.Main;
import com.jwinslow.game.ui.UIButton;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.ButtonEffects;

/**
 * Created by jalen on 12/27/2017.
 */

public class PauseState extends State {

    private UIButton continueButton, mainMenuButton, exitGameButton;

    public PauseState(Handler handler) {
        super(handler);
    }

    @Override
    public void init() {
        continueButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*3/4, "Continue",
                ButtonEffects.CONTINUE_GAME, 2.5f * (Gdx.graphics.getWidth()/ Main.WIDTH), true);
        mainMenuButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, "Main Menu",
                ButtonEffects.MAIN_MENU, 2.5f * (Gdx.graphics.getWidth()/ Main.WIDTH), true);
        exitGameButton = new UIButton(handler, new TextureRegion(Assets.button),
                Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4, "Exit\nGame",
                ButtonEffects.EXIT_GAME, 2.5f * (Gdx.graphics.getWidth()/Main.WIDTH), true);
        handler.getStage().addActor(continueButton);
        handler.getStage().addActor(mainMenuButton);
        handler.getStage().addActor(exitGameButton);
    }

    @Override
    public void update(float dt) {
        continueButton.update(dt);
        mainMenuButton.update(dt);
        exitGameButton.update(dt);
    }

    @Override
    public void render(SpriteBatch batch) {

        handler.message = "Pause.";
        batch.setColor(1, 1, 1, 1);
        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(Assets.background, 0 , 0);
    }

    @Override
    public void dispose() {
        handler.message = "";
        if (continueButton != null) continueButton.dispose();
        if (mainMenuButton != null) mainMenuButton.dispose();
        if (exitGameButton != null) exitGameButton.dispose();
    }
}
