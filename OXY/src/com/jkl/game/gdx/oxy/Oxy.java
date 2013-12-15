package com.jkl.game.gdx.oxy;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.Logger;
import com.jkl.game.gdx.oxy.screens.OxyLevel;
import com.jkl.game.gdx.oxy.screens.Splash;
import com.jkl.game.gdx.oxy.test.Menue;


//public class Oxy implements ApplicationListener {
public class Oxy extends Game {
	
	public class GameStatus {
		public int Gamelevel=1;
		
	}
	
	public static GameStatus gamestatus = new Oxy().new GameStatus();
	
	public static Logger log =  new Logger("OXY",Logger.DEBUG);
	public static FPSLogger fpslog ;
	public static TweenManager manager;
	
	static PlatformResolver platformresolver;
	public static PlatformResolver getPlatformresolver() {
		return platformresolver;
	}

	public static void setPlatformresolver(PlatformResolver platformresolver) {
		Oxy.platformresolver = platformresolver;
	}
	

	@Override
	public void create() {		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
//		Gdx.app.setLogLevel(Application.LOG_INFO);
		fpslog = new FPSLogger();
		
//		GameStatus g = new GameStatus();
		
		manager = new TweenManager();
		
		//For android		Tween.se.setPoolEnabled(true);
		if (platformresolver != null) {
			platformresolver.create();
		}
		
		setScreen(new Splash());
//		setScreen(new OxyLevel());
//		setScreen(new Menue(this));
		}

	
	@Override
	public void dispose() {
		super.dispose();
		if (platformresolver != null) {
			platformresolver.dispose();
		}
	}

	@Override
	public void render() {	
		super.render();
		if (platformresolver != null) {
			platformresolver.render();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
		}
}
