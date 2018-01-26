package com.jwinslow.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.jwinslow.game.Handler;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.Controls;

/**
 * Created by jalen on 1/12/2018.
 */

public class UITextArea extends UIObject{

    public static final int MAX_CHAR_LIMIT = 256, MIN_CHAR_LIMIT = 0;

    private String message;
    private boolean editable;
    private BitmapFont font;
    private float fontX, fontY;
    private int maxCharLimit, minCharLimit;
    private int alignment;
    private String title, defaultMessage, hint;

    //Constructors
    public UITextArea(Handler handler, TextureRegion image, float x, float y, float widthScale, float heightScale, float fontScale, boolean editable) {
        this(handler, image, x, y, widthScale, heightScale, fontScale, MIN_CHAR_LIMIT, MAX_CHAR_LIMIT, Align.center, "", editable);
    }

    public UITextArea(Handler handler, TextureRegion image, float x, float y, float widthScale, float heightScale,
                      float fontScale, String message, boolean editable) {
        this(handler, image, x, y, widthScale, heightScale, fontScale, MIN_CHAR_LIMIT, MAX_CHAR_LIMIT, Align.center, message, editable);
    }

    public UITextArea(Handler handler, TextureRegion image, float x, float y, float widthScale, float heightScale,
                      float fontScale, int minCharLimit, int maxCharLimit, String message, boolean editable) {
        this(handler, image, x, y, widthScale, heightScale, fontScale, minCharLimit, maxCharLimit, Align.center, message, editable);
    }

    public UITextArea(Handler handler, TextureRegion image, float x, float y, float widthScale, float heightScale,
                      float fontScale, int minCharLimit, int maxCharLimit, int alignment, String message, boolean editable) {
        super(handler, image, x, y);
        bounds = new Rectangle(x, y, (Gdx.graphics.getWidth()/2)*widthScale, (Gdx.graphics.getHeight()/2)*heightScale);
        this.bounds.x = x - bounds.width/2;
        this.bounds.y = y - bounds.height/2;
        this.message = message;
        this.editable = editable;
        setVisible(true);

        font = new BitmapFont(Gdx.files.internal(Assets.pixelFontFile1));
        font.getData().scale(fontScale);
        font.setColor(Color.BLACK);
        font.getData().down = -32 * (fontScale/2.5f);
        this.fontX = this.x;
        this.fontY = this.y+this.bounds.height/2;
        this.minCharLimit = minCharLimit;
        this.maxCharLimit = maxCharLimit;
        this.alignment = alignment;

        this.title = "";
        this.defaultMessage = "";
        this.hint = "";
    }

    @Override
    public void update(float dt) {
        if (editable && !message.equalsIgnoreCase("") && !messageWithinLimits()) {
            message = "Invalid";
        }
        if (editable && bounds.contains(Controls.jx, Controls.jy)) {
            handler.getTil().setCurrentArea(this);
            Gdx.input.getTextInput(handler.getTil(), title, defaultMessage, hint);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, bounds.x, bounds.y, bounds.width, bounds.height);

        font.draw(batch, message, fontX, fontY, 0, alignment, true);
    }

    public boolean messageWithinLimits() {
        char[] msg = message.toCharArray();
        return (msg.length >= minCharLimit && msg.length <= maxCharLimit);
    }

    public void dispose() {
        font.dispose();
        remove();
    }

    //Getters and Setters
    public String getMessage() {return message;}
    public boolean isEditable() {return editable;}
    public float getFontX() {return fontX;}
    public float getFontY() {return fontY;}
    public int getMinCharLimit() {return minCharLimit;}
    public int getMaxCharLimit() {return maxCharLimit;}
    public int getAlignment() {return alignment;}
    public String getTitle() {return title;}
    public String getDefaultMessage() {return defaultMessage;}
    public String getHint() {return hint;}

    public void setMessage(String message) {this.message = message;}
    public void setEditable(boolean editable) {this.editable = editable;}
    public void setFontX(float x) {this.fontX = x;}
    public void setFontY(float y) {this.fontY = y;}
    public void setMinCharLimit(int limit) {this.minCharLimit = limit;}
    public void setMaxCharLimit(int limit) {this.maxCharLimit = limit;}
    public void setAlignment(int align) {this.alignment = align;}
    public void setTitle(String title) {this.title = title;}
    public void setDefaultMessage(String message) {this.defaultMessage = message;}
    public void setHint(String hint) {this.hint = hint;}

}
