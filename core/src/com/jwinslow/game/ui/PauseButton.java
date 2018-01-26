package com.jwinslow.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jwinslow.game.Handler;
import com.jwinslow.game.utils.ButtonEffects;
import com.jwinslow.game.utils.Controls;

/**
 * Created by jalen on 12/27/2017.
 */

public class PauseButton extends UIObject{


    public PauseButton(Handler handler, TextureRegion image) {
        super(handler, image, Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/8);
        bounds = new Rectangle(x, y, Gdx.graphics.getWidth()/8, Gdx.graphics.getWidth()/8);
    }


    @Override
    public void update(float dt) {
        if (bounds.contains(Controls.jx, Controls.jy)) {
            ButtonEffects.update(ButtonEffects.PAUSE_GAME);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, bounds.x, bounds.y , bounds.width, bounds.height);
    }
}
