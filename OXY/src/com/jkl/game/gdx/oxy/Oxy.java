package com.jkl.game.gdx.oxy;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.Logger;
import com.jkl.game.gdx.oxy.screens.OxyLevel;
import com.jkl.game.gdx.oxy.screens.Splash;
import com.jkl.game.gdx.oxy.stages.MenuStage;
import com.jkl.game.gdx.oxy.test.Menue;


//public class Oxy implements ApplicationListener {
public class Oxy extends Game {

	public static Resouces gamestatus = new Resouces();
	
	public static Logger log =  new Logger("OXY",Logger.DEBUG);
	public static FPSLogger fpslog ;
	public static TweenManager manager;
	private OxyLevel oxylevel;
	
	MenuStage m ;
	
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
		
		m =new MenuStage(this);
		Gdx.input.setInputProcessor(m);
		
		manager = new TweenManager();
		//For android		Tween.se.setPoolEnabled(true);
		oxylevel =new OxyLevel();
		
		if (platformresolver != null) {
			platformresolver.create();
		}	
		
		doEvent();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void render() {	
		super.render();
		if ((gamestatus.showplatform) && (platformresolver != null)) {
			platformresolver.render();
		}
		if (gamestatus.showmenu) {
			m.act();
			m.draw();
		}
	}

	public void doEvent() {
		if (gamestatus.Gamelevel == 0) {
			setScreen(new Splash());
		} 
		if (gamestatus.Gamelevel > 0) {
			if (getScreen() != null)
				if (getScreen().getClass() == Splash.class) {
				getScreen().dispose();
			}
			gamestatus.showplatform = true;
			gamestatus.showmenu = false;
			setScreen(oxylevel);
		}
	}
	
	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
		}
	
	@Override
	public void dispose() {
		super.dispose();
		if (platformresolver != null) {
			platformresolver.dispose();
		}
		m.dispose();
		oxylevel.dispose();
	}
}
