package com.jwinslow.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jwinslow.game.Handler;
import com.jwinslow.game.gameobjects.GameObject;

public class PlayerSprite extends Sprite{
    
    //--- Propreties
    private Animation<TextureRegion> animLeft, animRight, animUp, animDown, animCurrent;
    private boolean manual;
    private float elapsedTime;
    
    //--- Constructor
    public PlayerSprite(Handler handler, float x, float y, float width, float height, TextureRegion image, GameObject obj) {
        super(handler, x, y , width, height, image, obj);
        this.manual = false;
        createAnimation();
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        elapsedTime += dt;
        bounds.x = gameObj.getX();
        bounds.y = gameObj.getY();
        if (!manual)
            currentFrame = animCurrent.getKeyFrame(elapsedTime);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    @Override
    public void dispose() {
        
    }
    
    private void createAnimation() {
        TextureRegion[][] images = image.split(16, 16);
        frames = new TextureRegion[images.length * images[0].length];
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[i].length; j++) {
                frames[(i*images.length)+j] = images[i][j];
            }
        }
        Array<TextureRegion> animFrames = new Array<TextureRegion>();
        for (int i = 0; i < images[0].length; i++) {
            animFrames.add(images[0][i]);
        }
        animDown = new Animation<TextureRegion>(0.1f, animFrames, PlayMode.LOOP);
        animFrames.clear();
        
        for (int i = 0; i < images[1].length; i++) {
            animFrames.add(images[1][i]);
        }
        animUp = new Animation<TextureRegion>(0.1f, animFrames, PlayMode.LOOP);
        animFrames.clear();
        
        for (TextureRegion t : images[2]) {
            animFrames.add(t);
        }
        animRight = new Animation<TextureRegion>(0.1f, animFrames, PlayMode.LOOP);
        animFrames.clear();
        
        for (TextureRegion t : images[3]) {
            animFrames.add(t);
        }
        animLeft = new Animation<TextureRegion>(0.1f, animFrames, PlayMode.LOOP);
        animFrames.clear();
        
        animCurrent = animDown;
        currentFrame = animCurrent.getKeyFrame(0);
    }
    
    
    //--- Getters and Setters
    public Animation<TextureRegion> getAnimDown() {return animDown;}
    public Animation<TextureRegion> getAnimUp() {return animUp;}
    public Animation<TextureRegion> getAnimRight() {return animRight;}
    public Animation<TextureRegion> getAnimLeft() {return animLeft;}
    public Animation<TextureRegion> getAnimCurrent() {return animCurrent;}
    public boolean getManual() {return manual;}
    
    public void setAnimCurrent(Animation anim) {this.animCurrent = anim;}
    public void setManual(boolean manual) {this.manual = manual;}
    
}
