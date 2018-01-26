package com.jwinslow.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jwinslow.game.Handler;
import com.jwinslow.game.gameobjects.GameObject;

public abstract class Sprite {
    
    //--- Propreties
    protected Handler handler;
    protected TextureRegion image;
    protected TextureRegion currentFrame;
    protected TextureRegion[] frames;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected int currentFrameIndex;
    protected int numOfFrames;
    protected Rectangle bounds;
    protected GameObject gameObj;
    
    //--- Constructor
    public Sprite(Handler handler, TextureRegion image) {
        this.handler = handler;
        this.image = image;
        this.currentFrame = new TextureRegion();
        this.frames = new TextureRegion[0];
        this.bounds = new Rectangle();
    }
    public Sprite(Handler handler, float x, float y, float width, float height, TextureRegion image, GameObject obj) {
        this.handler = handler;
        this.image = image;
        this.currentFrame = new TextureRegion();
        this.frames = new TextureRegion[0];
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        this.bounds = new Rectangle(x, y, width, height);
        this.gameObj = obj;
    }
    
    
    //--- Methods
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
    
    //--- Getters and Setters
    public TextureRegion getImage() {return image;}
    public TextureRegion getCurrentFrame() {return currentFrame;}
    public TextureRegion[] getFrames() {return frames;}
    public float getX() {return x;}
    public float getY() {return y;}
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public int getCurrentFrameIndex() {return currentFrameIndex;}
    public int getNumOfFrames() {return numOfFrames;}
    
    public void setImage(TextureRegion image) {this.image =image;}
    public void setCurrentFrame(TextureRegion frame) {this.currentFrame = frame;} 
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void setWidth(float width) {this.width = width;}
    public void setHeight(float height) {this.height = height;}
    
}
