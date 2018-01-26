package com.jwinslow.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jwinslow.game.Handler;

public class Fonts {
    
    //--- Propreties
    private static Handler handler;
    private static String message;
    
    public static final BitmapFont 
            font1 = new BitmapFont(Gdx.files.internal("customFont4.fnt")), 
            pixelFont1 = new BitmapFont(Gdx.files.internal("customPixelFont1.fnt")),
            buttonFont = new BitmapFont(Gdx.files.internal("customPixelFont1.fnt"));
    
    //--- Constructor
    public Fonts(Handler handler) {
        Fonts.handler = handler;
    }
    
    
    //--- Methods
    public static void init(Handler handler) {
        Fonts.handler = handler;
        message = "";
        //font1 = new BitmapFont(Gdx.files.internal("customFont4.fnt"));
        font1.setColor(Color.RED);
        pixelFont1.setColor(Color.RED);
        pixelFont1.getData().scale(1);
        
    }
    
    public static void write(Batch batch, BitmapFont font, String message, float x, float y) {
        font.draw(batch, message, x, y);
    }
    
    public static void write(Batch batch, BitmapFont font, float x, float y) {
        font.draw(batch, message, x, y);
    }
    
    public static void dispose() {
        font1.dispose();
        pixelFont1.dispose();
    }
    
    //--- Getters and Setters
    
    
}
