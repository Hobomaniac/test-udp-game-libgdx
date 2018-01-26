package com.jwinslow.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jwinslow.game.Handler;

public class GameObjectHandler {
    
    //--- Propreties
    private Handler handler;
    private Array<GameObject> gameObjects;
    private Array<Player> players;
    
    //--- Constructor
    public GameObjectHandler(Handler handler) {
        this.handler = handler;
        gameObjects = new Array<GameObject>();
        players = new Array<Player>();
        
    }
    
    
    //--- Methods
    public void update(float dt) {
        sort();
        for (GameObject o : gameObjects) {
            o.update(dt);
        }
        //System.out.println("Updateing gameObjects.");
    }
    
    public void render(SpriteBatch batch) {
        for (GameObject o : gameObjects) {
            o.render(batch);
        }
        //System.out.println("rendering gameObjects " + gameObjects.size);
    }
    
    public void dispose() {
        gameObjects.clear();
    }
    
    public void sort() {
        for (int i = 0; i < gameObjects.size; i++) {
            if (i+1 == gameObjects.size) break;
            if (gameObjects.get(i).getDepth() < gameObjects.get(i+1).getDepth()) {
                gameObjects.swap(i, i+1);
            }
        }
    }
    
    public void add(GameObject o) {
        gameObjects.add(o);
    }
    
    public void addAll(GameObject[] objects) {
        for (GameObject o: objects) {
            add(o);
        }
    }
    
    public void remove(GameObject o) {
        gameObjects.removeValue(o, false);
    }
    
    public void addPlayer(Player player) {
        players.add(player);
        add(player);
        /*
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                players[i] = player;
                add(players[i]);
                break;
            }
        }//*/
    }
    
    public void removePlayer(Player player) {
        players.removeValue(player, false);
        remove(player);/*
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(player)) {
                remove(player);
                players[i] = null;
                break;
            }
        }//*/
    }
    
    //--- Getters and Setters
    public Array<GameObject> getGameObjects() {return gameObjects;}
    public Array<Player> getPlayers() {return players;}
    
}
