package com.jwinslow.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.jwinslow.game.Main;

public class Controls {
    
    //--- Propreties
    public static final int KEYBOARD = 0, TOUCH = 1;
        
    private static int controlType;
    public static boolean up, down, left, right;
    private static int key_up, key_down, key_left, key_right;
    public static float sx, sy, ex, ey;
    public static float jx, jy;
    private static boolean touched;
    
    //--- Constructor
    
    //--- Methods
    public static void initControls() {
        key_up = Keys.W;
        key_down = Keys.S;
        key_left = Keys.A;
        key_right = Keys.D;
    }
    
    public static void update() {
        switch (controlType) {
            case KEYBOARD:
                updateKeyboard();
                updateTouch();
                break;
            case TOUCH:
                updateTouch();
                break;
            default:
                controlType = KEYBOARD;
                break;
        }
    }

    private static void updateKeyboard() {
            up = Gdx.input.isKeyPressed(key_up);
            down = Gdx.input.isKeyPressed(key_down);
            left = Gdx.input.isKeyPressed(key_left);
            right = Gdx.input.isKeyPressed(key_right);
    }

    private static void updateTouch() {
        if (Gdx.input.isTouched()) {
            if (!touched) {
                sx = Gdx.input.getX();
                sy = Gdx.graphics.getHeight()-Gdx.input.getY();
                ex = sx;
                ey = sy;
                touched = true;
            } else {
                ex = Gdx.input.getX();
                ey = Gdx.graphics.getHeight()-Gdx.input.getY();
            }
        } else {
            sx = 0; sy = 0;
            ex = 0; ey = 0;
            touched = false;
        }
        
        if (Gdx.input.justTouched()) {
            jx = Gdx.input.getX();
            jy = Gdx.graphics.getHeight()-Gdx.input.getY();
        } else {
            jx = -1;
            jy = -1;
        }
    }
    
    //--- Getters and Setters
    public static int getControllerType() {return controlType;}
    
    public static void setControllerType(int controlType) {Controls.controlType = controlType;}
    public static void setKey_up(int key) {Controls.key_up = key;}
    public static void setKey_down(int key) {Controls.key_down = key;}
    public static void setKey_left(int key) {Controls.key_left = key;}
    public static void setKey_right(int key) {Controls.key_right = key;}
    
}
