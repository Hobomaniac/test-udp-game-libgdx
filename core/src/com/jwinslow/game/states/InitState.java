package com.jwinslow.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jwinslow.game.Handler;
import com.jwinslow.game.Main;
import com.jwinslow.game.utils.Assets;
import com.badlogic.gdx.Gdx;

public class InitState extends State{
    
    //--- Propreties
    
    
    //--- Constructor
    public InitState(Handler handler) {
        super(handler);
    }
    
    
    //--- Methods
    @Override
    public void init() {
        Assets.loadAllTextures();
        
        //handler.getCam().zoom = 0.5f;
        handler.getCam().position.x = handler.getCam().viewportWidth/2+5;//Added 5 because it start out of collision there.
        handler.getCam().position.y = handler.getCam().viewportHeight/2+5;
        
        //State.setCurrentState(handler.getMenuState());
        //State.setCurrentState(handler.getGameState());
        //State.getCurrentState().init();
    }
    
    @Override
    public void update(float dt) {
        //handler.getMain().resize(Main.WIDTH, Main.HEIGHT);
        State.setCurrentState(handler.getMenuState());
        //State.setCurrentState(handler.getGameState());
        State.getCurrentState().init();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        
    }
    
    @Override
    public void dispose() {
        
    }
    
    //--- Getters and Setters
    
    
}
