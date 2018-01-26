package com.jwinslow.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jwinslow.game.Handler;
import com.jwinslow.game.Main;
import com.jwinslow.game.gameobjects.Player;
import com.jwinslow.game.utils.Controls;

public class PointerAnchor extends Actor {
    
    //--- Propreties
    private Handler handler;
    private TextureRegion image;
    private float x, y;
    private Rectangle bounds;
    //private Player player;
    
    //--- Constructor
    public PointerAnchor(Handler handler, TextureRegion image, float x, float y, Player player) {
        this.image = image;
        this.handler = handler;
        this.x = x;
        this.y = y;
        bounds = new Rectangle(x, y, Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10);
        //this.player = player;
        setVisible(false);
    }
    
    
    //--- Methods
    public void update(float dt) {
        if (Controls.sx != 0 && Controls.sy != 0) {
            x = Controls.sx;
            y = Controls.sy;
            bounds.x = x;
            bounds.y = y;
            setVisible(true);
        } else {
            x = -1;
            y = -1;
            bounds.x = x;
            bounds.y = y;
            setVisible(false);
        }
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //System.out.println("drawing anchor");
        Color color = getColor();
	batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(image, bounds.x - bounds.width/2, bounds.y - bounds.height/2, bounds.width, bounds.height);
    }
    
    //--- Getters and Setters
    
    
}
