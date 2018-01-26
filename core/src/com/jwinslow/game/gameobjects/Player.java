package com.jwinslow.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.jwinslow.game.Handler;
import com.jwinslow.game.Main;
import com.jwinslow.game.sprites.PlayerSprite;
import com.jwinslow.game.utils.Assets;
import com.jwinslow.game.utils.Controls;

import java.text.DecimalFormat;

public class Player extends GameObject {
    
    //--- Propreties
    public static int playerid = 1;
    public static final float SPEED = 0.7f;
    
    private float vecX, vecY;
    private float speed;
    protected float dir;
    protected PlayerSprite sprite;
    protected int pid;
    protected int idle;
    protected String name;
    //private PlayerControls Controls;
    
    
    //--- Constructor
    public Player(Handler handler, float x, float y, TextureRegion image) {
        super(handler, x, y, image);
        this.bounds = new Rectangle(x, y, 16, 16);
        this.vecX = 0;
        this.vecY = 0;
        this.dir = 0;
        this.speed = 0;
        this.sprite = new PlayerSprite(handler, bounds.x, bounds.y, bounds.width, bounds.height, image, this);
        this.pid = 0;
        this.idle = 0;
        this.name = "Player";
        //this.Controls = new PlayerControls(PlayerControls.TOUCH);
    }
    
    
    //--- Methods
    
    @Override
    public void update(float dt) {
        handleInput();
        handleCollision();
        handleAnimation();

        x += vecX;
        y += vecY;
        bounds.x = x;
        bounds.y = y;
        depth = y;
        sprite.update(dt);

    }
    
    @Override
    public void render(SpriteBatch batch) {
        sprite.render(batch);
    }
    
    @Override
    public void dispose() {
        
    }
    
    @Override
    public String toString() {return "Player";}
    
    public void handleInput() {
        //Controls.update();
        
        if (Controls.getControllerType() == Controls.KEYBOARD) {
            if ((Controls.left && Controls.right) || (!Controls.left && !Controls.right)) {
                vecX = 0;
            } else if (Controls.left) {
                vecX = -SPEED;
            } else if (Controls.right) {
                vecX = SPEED;
            }
            
            if ((Controls.up && Controls.down) || (!Controls.up && !Controls.down)) {
                vecY = 0;
            } else if (Controls.up) {
                vecY = SPEED;
            } else if (Controls.down) {
                vecY = -SPEED;
            }
        }
        
        if (Controls.getControllerType() == Controls.TOUCH) {
            float startX = Controls.sx;
            float startY = Controls.sy;
            float endX = Controls.ex;
            float endY = Controls.ey;
            
            vecX = SPEED * (endX-startX) / 50;
            vecY = SPEED * (endY-startY) / 50;
            
            float starter = 0.2f * (Gdx.graphics.getWidth()/ Main.WIDTH);
            if (vecX < starter && vecX > -starter) vecX = 0;
            if (vecY < starter && vecY > -starter) vecY = 0;
            
            if (vecX > SPEED) vecX = SPEED;
            else if (vecX < -SPEED) vecX = -SPEED;
            if (vecY > SPEED) vecY = SPEED;
            else if (vecY < -SPEED) vecY = -SPEED;

        }
    }
    
    public void handleAnimation() {
        if (vecX != 0 || vecY != 0)
            dir = MathUtils.atan2(vecY, vecX) * MathUtils.radiansToDegrees;
        if (dir <= 45 && dir >= -45) sprite.setAnimCurrent(sprite.getAnimRight());
        if (dir > 45 && dir < 135) sprite.setAnimCurrent(sprite.getAnimUp());
        if (dir >= 135 && dir <= 225) sprite.setAnimCurrent(sprite.getAnimLeft());
        if (dir > -135 && dir < -45) sprite.setAnimCurrent(sprite.getAnimDown());
        
        if (vecX == 0 && vecY == 0) {
            idle = 1;
            sprite.setManual(true);
            sprite.setCurrentFrame(sprite.getAnimCurrent().getKeyFrame(0));
        } else {
            idle = 0;
            sprite.setManual(false);
        }
    }
    
    public void handleCollision() {
        if (bounds.x + bounds.width + vecX > Assets.background.getWidth()) {
            vecX = 0;
        }
        if (bounds.x + vecX < 0) vecX = 0;
        if (bounds.y + bounds.height + vecY > Assets.background.getHeight() ||
                bounds.y + vecY < 0) {
            vecY = 0;
        }
    }
    
    //--- Getters and Setters
    public float getDir() {return dir;}
    public int getPid() {return pid;}
    public int getIdle() {return idle;}
    public String getName() {return name;}
    //public PlayerControls getControls() {return Controls;}
    
    public void setDir(float dir) {this.dir = dir;}
    public void setPid(int pid) {this.pid = pid;}
    public void setIdle(int idle) {this.idle = idle;}
    public void setName(String name) {this.name = name;}
    
    
    public class PlayerControls {
        
        public static final int KEYBOARD = 0, TOUCH = 1;
        
        private int controlType;
        public boolean up, down, left, right;
        private int key_up, key_down, key_left, key_right;
        public float sx, sy, ex, ey;
        public float jx, jy;
        private boolean touched;
        
        public PlayerControls(int controlType) {
            this.controlType = controlType;
            initControls();
        }
        
        public void update() {
            switch (controlType) {
                case KEYBOARD:
                    updateKeyboard();
                    break;
                case TOUCH:
                    updateTouch();
                    break;
                default:
                    controlType = KEYBOARD;
                    break;
            }
        }
        
        private void updateKeyboard() {
            up = Gdx.input.isKeyPressed(key_up);
            down = Gdx.input.isKeyPressed(key_down);
            left = Gdx.input.isKeyPressed(key_left);
            right = Gdx.input.isKeyPressed(key_right);
        }
        
        private void updateTouch() {
            if (Gdx.input.isTouched()) {
                if (!touched) {
                    sx = Gdx.input.getX();
                    sy = Gdx.input.getY();
                    ex = sx;
                    ey = sy;
                    touched = true;
                } else {
                    ex = Gdx.input.getX();
                    ey = Gdx.input.getY();
                }
                
            } else {
                sx = 0; sy = 0;
                ex = 0; ey = 0;
                touched = false;
            }
            
            if (Gdx.input.justTouched()) {
                jx = Gdx.input.getX();
                jy = Gdx.input.getY();
            } else {
                jx = -1;
                jy = -1;
            }
        }
        
        private void initControls() {
            key_up = Keys.W;
            key_down = Keys.S;
            key_left = Keys.A;
            key_right = Keys.D;
        }
        
        public int getControllerType() {return controlType;}
        public float getSx() {return sx;}
        public float getSy() {return sy;}
        public float getEx() {return ex;}
        public float getEy() {return ey;}
        public float getJx() {return jx;}
        public float getJy() {return jy;}
        public boolean getUp() {return up;}
        public boolean getDown() {return down;}
        public boolean getLeft() {return left;}
        public boolean getRight() {return right;}
        
        public void setControllerType(int controlType) {this.controlType = controlType;}
        public void setKey_up(int key) {this.key_up = key;}
        public void setKey_down(int key) {this.key_down = key;}
        public void setKey_left(int key) {this.key_left = key;}
        public void setKey_right(int key) {this.key_right = key;}
        
    }
    
}
