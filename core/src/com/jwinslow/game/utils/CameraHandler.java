package com.jwinslow.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.jwinslow.game.Handler;
import com.jwinslow.game.gameobjects.GameObject;

public class CameraHandler {
    
    //--- Propreties
    public static final float CAM_SPEED = 1;
    
    private float vecX, vecY;
    private Handler handler;
    private Rectangle bounds;
    private OrthographicCamera cam;
    private GameObject objOfFocus;
    
    //--- Constructor
    public CameraHandler(Handler handler, OrthographicCamera cam) {
        this.handler = handler;
        this.cam = cam;
        bounds = new Rectangle(cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2,
                cam.viewportWidth, cam.viewportHeight);
        objOfFocus = null;
    }
    public CameraHandler(Handler handler, OrthographicCamera cam, GameObject objOfFocus) {
        this.handler = handler;
        this.cam = cam;
        bounds = new Rectangle(cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2,
                cam.viewportWidth, cam.viewportHeight);
        this.objOfFocus = objOfFocus;
    }
    
    
    //--- Methods
    public void update(float dt) { //Actually updating the camera takes place in the Main class.
        updateFocus();
        bounds.x = cam.position.x-cam.viewportWidth/2;
        bounds.y = cam.position.y-cam.viewportHeight/2;
        outOfAreaCollision();
        if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) printData();
    }
    
    public void resetBounds() {
        bounds.x = cam.position.x-cam.viewportWidth/2;
        bounds.y = cam.position.y-cam.viewportHeight/2;
        bounds.width = cam.viewportWidth;
        bounds.height = cam.viewportHeight;
    }
    
    public void resetBoundsPos() {
        bounds.x = cam.position.x-cam.viewportWidth/2;
        bounds.y = cam.position.y-cam.viewportHeight/2;
    }
    
    public void reset() {
        cam.zoom = 1;
        if (objOfFocus != null) {
            cam.position.x = objOfFocus.getX();
            cam.position.y = objOfFocus.getY();
        } else {
            cam.position.x = Assets.background.getWidth()/2;
            cam.position.y = Assets.background.getHeight()/2;
        }
    }
    
    private void updateFocus() {
        if (objOfFocus != null) {
            cam.position.x = objOfFocus.getX()+objOfFocus.getBounds().width/2;
            cam.position.y = objOfFocus.getY()+objOfFocus.getBounds().height/2;
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) objOfFocus = null;
        } else handleCam();
    }
    
    private void handleCam() {
        
        if (Gdx.input.isKeyPressed(Keys.I)) {vecY = CAM_SPEED;}
        if (Gdx.input.isKeyPressed(Keys.K)) {vecY = -CAM_SPEED;}
        if (Gdx.input.isKeyPressed(Keys.J)) {vecX = -CAM_SPEED;}
        if (Gdx.input.isKeyPressed(Keys.L)) {vecX = CAM_SPEED;}
        cam.translate(vecX, vecY);
        if (Gdx.input.isKeyPressed(Keys.U)) {cam.zoom -= 0.01; resetBounds();}
        if (Gdx.input.isKeyPressed(Keys.O)) {cam.zoom += 0.01; resetBounds();}
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            reset();
        }
    }
    
    public void outOfAreaCollision() {
        if (bounds.x + bounds.width + CAM_SPEED > Assets.background.getWidth()) {
            cam.position.x = Assets.background.getWidth()-cam.viewportWidth/2;
            bounds.x = cam.position.x-cam.viewportWidth/2;
        }
            
        if (bounds.x - CAM_SPEED < 0) {
            //System.out.println("Hello");
            cam.position.x = cam.viewportWidth/2;
            bounds.x = cam.position.x-cam.viewportWidth/2;
        }
        if (bounds.y + bounds.height + CAM_SPEED > Assets.background.getHeight()) {
            cam.position.y = Assets.background.getHeight()-cam.viewportHeight/2;
            bounds.y = cam.position.y-cam.viewportHeight/2;
        }
            
        if (bounds.y - CAM_SPEED < 0) {
            cam.position.y = cam.viewportHeight/2;
            bounds.y = cam.position.y-cam.viewportHeight/2;
        }
    }
    
    public void printData() {
        String message = "Camera:\n\tX: " + cam.position.x + " Y: " + cam.position.y +
                "\n\tWidth: " + cam.viewportWidth + " Height: " + cam.viewportHeight;
        System.out.println(message);
    }
    
    //--- Getters and Setters
    public Rectangle getBounds() {return bounds;}
    public OrthographicCamera getCam() {return cam;}
    public GameObject getObjOfFocus() {return objOfFocus;}
    
    public void setBounds(Rectangle bounds) {this.bounds = bounds;}
    public void setCam(OrthographicCamera cam) {this.cam = cam;}
    public void setObjOfFocus(GameObject obj) {this.objOfFocus = obj;}
    
}
