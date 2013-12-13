package com.jkl.game.gdx.oxy.client;

import com.jkl.game.gdx.oxy.Oxy;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
//		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480, 320);
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(800, 600);
		return cfg;
	}
	
	@Override
	public ApplicationListener getApplicationListener () {
		Oxy.setPlatformresolver(new WebResolver());
		return new Oxy();
	}
	
}