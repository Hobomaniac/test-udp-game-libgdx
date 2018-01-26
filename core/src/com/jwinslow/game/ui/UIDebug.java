package com.jwinslow.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jwinslow.game.Handler;

/**
 * Created by jalen on 1/6/2018.
 */

public class UIDebug extends UIObject {

    private BitmapFont font;
    public String msg;

    public UIDebug(Handler handler) {
        super(handler, null, 0, Gdx.graphics.getHeight());
        font = new BitmapFont(Gdx.files.internal("customPixelFont1.fnt"));
        font.setColor(Color.RED);
        font.getData().scale(2);
        msg = "";

        setVisible(true);
    }

    @Override
    public void update(float dt) {
        if (getZIndex() < handler.getStage().getActors().size) {
            setZIndex(getZIndex()+1);
        }
    }

    @Override
    public void draw(Batch batch , float parentAlpha) {
        font.draw(batch, msg, x, y);
    }

    public void dispose() {
        font.dispose();
        remove();
    }

    //Getters and Setters

}
