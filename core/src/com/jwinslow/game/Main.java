package com.jwinslow.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    
    public static final int WIDTH = 320, HEIGHT = 480;

	SpriteBatch batch;
	Handler handler;
        OrthographicCamera cam;
        Stage stage;
        
	@Override
	public void create () {
		batch = new SpriteBatch();
                handler = new Handler(this);
                stage = new Stage(new ScreenViewport(), batch);
                stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
                
                
                cam = new OrthographicCamera(Main.WIDTH/4, Main.HEIGHT/4);
                cam.setToOrtho(false, cam.viewportWidth, cam.viewportHeight);
                handler.init();
                
                cam.update();
	}
        
        public void resize() {

            stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            cam.update();
        }

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
                float dt = Gdx.graphics.getDeltaTime();
                cam.update();
                batch.setProjectionMatrix(cam.combined);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                handler.update(dt);
                //stage.act(dt);
                
		batch.begin();
                handler.render(batch);
                
		batch.end();
        batch.setColor(1, 1, 1, 1);
                stage.draw();
	}
	
	@Override
	public void dispose () {
            handler.dispose();
		batch.dispose();
            stage.dispose();
	}
        
        public OrthographicCamera getCam() {return cam;}
        
        public void setCam(OrthographicCamera cam) {this.cam = cam;}
}
