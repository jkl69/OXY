package com.jkl.game.gdx.oxy;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
	     
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "OXY";
		cfg.useGL20 = false;
//		cfg.useGL20 = true;
//		cfg.width = 480;
//		cfg.height = 320;
		cfg.width = 1024;
		cfg.height = 768;
		
		Oxy.setPlatformresolver(new DesktopResolver());
		new LwjglApplication(new Oxy(), cfg);
	}
}
