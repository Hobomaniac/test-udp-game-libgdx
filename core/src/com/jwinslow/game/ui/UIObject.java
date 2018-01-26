package com.jwinslow.game.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jwinslow.game.Handler;

public abstract class UIObject extends Actor {
    
    //--- Propreties
    protected Handler handler;
    protected TextureRegion image;
    protected float x, y;
    protected Rectangle bounds;
    
    //--- Constructor
    public UIObject(Handler handler, TextureRegion image, float x, float y) {
        this.handler = handler;
        this.image = image;
        this.x = x;
        this.y = y;
        
    }
    
    
    //--- Methods
    public abstract void update(float dt);
    @Override
    public abstract void draw(Batch batch, float parentAlpha);
    
    public boolean insideBounds(float x, float y) {
        return bounds.contains(x, y);
    }
    
    //--- Getters and Setters
    public TextureRegion getImage() {return image;}
    @Override public float getX() {return x;}
    @Override public float getY() {return y;}
    public Rectangle getRectangle() {return bounds;}
    
    public void setImage(TextureRegion image) {this.image = image;}
    @Override public void setX(float x) {this.x = x;}
    @Override public void setY(float y) {this.y = y;}
    public void setRectangle(Rectangle bounds) {this.bounds = bounds;}
    
    
}
