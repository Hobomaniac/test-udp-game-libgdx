package com.jwinslow.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jwinslow.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = Main.WIDTH;
                config.height = Main.HEIGHT;
                config.resizable = false;
		new LwjglApplication(new Main(), config);
	}
}
