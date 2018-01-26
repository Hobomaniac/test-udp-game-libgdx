package com.jwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jwinslow.game.Handler;

/**
 * Created by jalen on 1/12/2018.
 */

public class OnlinePlayer extends Player {

    public OnlinePlayer(Handler handler, float x, float y, TextureRegion image, int pid, String name) {
        super(handler, x, y, image);
        this.pid = pid;
        this.name = name;
    }

    @Override
    public void update(float dt) {
        handleAnimation();
        bounds.x = x;
        bounds.y = y;
        sprite.update(dt);
    }

    @Override
    public void handleAnimation() {
        if (dir <= 45 && dir >= -45) sprite.setAnimCurrent(sprite.getAnimRight());
        if (dir > 45 && dir < 135) sprite.setAnimCurrent(sprite.getAnimUp());
        if (dir >= 135 && dir <= 225) sprite.setAnimCurrent(sprite.getAnimLeft());
        if (dir > -135 && dir < -45) sprite.setAnimCurrent(sprite.getAnimDown());

        if (idle == 1) {
            sprite.setManual(true);
            sprite.setCurrentFrame(sprite.getAnimCurrent().getKeyFrame(0));
        } else {
            sprite.setManual(false);
        }
    }

    @Override
    public String toString() {return "OnlinePlayer";}


}
