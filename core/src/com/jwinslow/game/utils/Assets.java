package com.jwinslow.game.utils;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    
    //--- Propreties
    public static Texture player1;
    public static Texture player2;
    public static Texture touchAnchor;
    public static Texture background;
    public static Texture button;
    public static String pixelFontFile1;
    public static Texture pauseButton;
    public static Texture textArea;
    
    //--- Constructor
    public Assets() {
        
    }
    
    
    //--- Methods
    public static void loadAllTextures() {
        player1 = new Texture("b&w_DodgeGame_man1.png");
        player2 = new Texture("b&w_DodgeGame_woman1.png");
        touchAnchor = new Texture("touch_start.png");
        background = new Texture("b&w_DodgeGame_background2.png");
        button = new Texture("b&w_DodgeGame_buttons1.png");
        pixelFontFile1 = "customPixelFont1.fnt";
        pauseButton = new Texture("pauseButton.png");
        textArea = new Texture("b&w_textArea.png");
    }
    
    public static void disposeAllTextures() {
        player1.dispose();
        player2.dispose();
        touchAnchor.dispose();
        background.dispose();
        button.dispose();
        pauseButton.dispose();
        textArea.dispose();
    }
    
    //--- Getters and Setters
    
    
}
