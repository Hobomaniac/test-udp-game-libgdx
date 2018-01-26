package com.jwinslow.game.utils;

import com.badlogic.gdx.Input;
import com.jwinslow.game.Handler;
import com.jwinslow.game.ui.UITextArea;

/**
 * Created by jalen on 1/12/2018.
 */

public class TextInputListener implements Input.TextInputListener {

    private Handler handler;
    private String input;
    private UITextArea currentArea;

    public TextInputListener(Handler handler) {
        this.handler = handler;
        this.input = "";
    }


    @Override
    public void input(String text) {
        currentArea.setMessage(text);
        currentArea = null;
    }

    @Override
    public void canceled() {
        input = "";
    }

    public String getInput() {return input;}
    public UITextArea getCurrentArea() {return currentArea;}

    public void setCurrentArea(UITextArea ta) {this.currentArea = ta;}
}
