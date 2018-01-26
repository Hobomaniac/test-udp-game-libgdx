package com.jwinslow.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.jwinslow.game.Handler;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.ButtonEffects;
import com.jwinslow.game.utils.Controls;
import com.jwinslow.game.utils.Fonts;

public class UIButton extends UIObject {
    
    //--- Propreties
    private static float timer = 0;
    
    private String message;
    private ButtonEffects buttonEffect;
    private TextureRegion[] images;
    private BitmapFont font;
    private float alpha;
    private boolean canBePressed;
    
    //--- Constructor
    public UIButton(Handler handler, TextureRegion image, float x, float y, String message, ButtonEffects buttonEffect, float fontScale, boolean visible) {
        this(handler, image, x, y, message, buttonEffect, 1, fontScale, visible, true);
    }
    public UIButton(Handler handler, TextureRegion image, float x, float y, String message, ButtonEffects buttonEffect, float buttonScale, float fontScale, boolean visible, boolean canBePressed) {
        super(handler, image, x, y);
        this.bounds = new Rectangle(x, y, (Gdx.graphics.getWidth()*5/8)*buttonScale, (Gdx.graphics.getHeight()*5/32)*buttonScale);
        this.bounds.x = this.x-this.bounds.width/2;
        this.bounds.y = y - bounds.height/2;
        this.message = message;
        this.buttonEffect = buttonEffect;
        createImages();
        super.setVisible(visible);
        this.font = new BitmapFont(Gdx.files.internal(Assets.pixelFontFile1));//Fonts.buttonFont;
        font.getData().scale(fontScale);
        font.setColor(Color.BLACK);
        font.getData().down = -32 * (fontScale/2.5f);
        alpha = 1;
        this.canBePressed = canBePressed;
    }
    
    
    //--- Methods
    @Override
    public void update(float dt) {
        if (timer > 0) timer -= dt;
        if (timer <= 0 && canBePressed && isVisible() && bounds.contains(Controls.jx, Controls.jy)) {
            ButtonEffects.update(buttonEffect);
            timer = 1;
        }
        if (canBePressed) {
            alpha = 1;
        } else alpha = 0.5f;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 1, 1, alpha);
        batch.draw(images[0], bounds.x, bounds.y, bounds.width, bounds.height);
        font.draw(batch, message, x, y+bounds.height/2, 0, Align.center, true);
        batch.setColor(1, 1, 1, 1);
    }
    
    private void createImages() {
        TextureRegion[][] t = image.split(image.getRegionWidth(), image.getRegionHeight()/3);
        images = new TextureRegion[t.length * t[0].length];
        for (int j = 0; j < t.length; j++) {
            for (int i = 0; i < t[j].length; i++) {
                images[j] = t[j][i];
            }
        }
    }
    
    public void dispose() {
        font.dispose();
        remove();
    }
    
    //--- Getters and Setters
    public String getMessage() {return message;}
    public ButtonEffects getButtonEffect() {return buttonEffect;}
    public boolean isCanBePressed() {return canBePressed;}
    
    public void setMessage(String message) {this.message = message;}
    public void setButtonEffect(ButtonEffects buttonEffect) {this.buttonEffect = buttonEffect;}
    public void setCanBePressed(boolean pressed) {this.canBePressed = pressed;}
    
}
