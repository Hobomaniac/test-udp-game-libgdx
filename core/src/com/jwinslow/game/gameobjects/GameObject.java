package com.jwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jwinslow.game.Handler;

public abstract class GameObject {
    
    //--- Propreties
    protected Handler handler;
    protected float x;
    protected float y;
    protected float depth;
    protected TextureRegion image;
    protected Rectangle bounds;
    
    //--- Constructor
    public GameObject(Handler handler, float x, float y, TextureRegion image) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.depth = y;
        this.image = image;
        this.bounds = new Rectangle();
    }
    
    
    //--- Methods
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
    
    
    //--- Getters and Setters
    public float getX() {return x;}
    public float getY() {return y;}
    public float getDepth() {return depth;}
    public Rectangle getBounds() {return bounds;}
    
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void setPosition(float x, float y) {this.x = x; this.y = y;}
    public void setDepth(float depth) {this.depth = depth;}
    public void setBounds(Rectangle bounds) {this.bounds = bounds;}
    
    
}
