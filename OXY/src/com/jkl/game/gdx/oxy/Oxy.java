package com.jkl.game.gdx.oxy;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Logger;
import com.jkl.game.gdx.oxy.screens.OxyLevel;
import com.jkl.game.gdx.oxy.screens.Splash;
import com.jkl.game.gdx.oxy.stages.MenuStage;
import com.jkl.game.gdx.oxy.test.MenuTest;


//public class Oxy implements ApplicationListener {
public class Oxy extends Game {

	public static Resouces gamestatus = new Resouces();
	
	public static Logger log =  new Logger("OXY",Logger.DEBUG);
	public static FPSLogger fpslog ;
	public static TweenManager manager;
	private OxyLevel oxylevel;
	
	static MenuStage menu ;
	
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
		
		menu =new MenuStage(this);
		Gdx.input.setInputProcessor(menu);
		
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
		if (gamestatus.isShowmenu()) {
			menu.act();
//			Table.drawDebug(menu);
			menu.draw();
		}
	}

	public void doEvent() {
		if (gamestatus.Gamelevel == 0) {
			gamestatus.showplatform =false;
			setScreen(new Splash());
		} 
		if (gamestatus.Gamelevel > 0) {
			log.debug("doEvent");
			
			if (getScreen() != null)
				if (getScreen().getClass() == Splash.class) {
				getScreen().dispose();
				log.debug("doEvent destroy splashScreen create levelSceen");
				setScreen(oxylevel);
			} 
			if (getScreen() == null) {
				log.debug("doEvent create levelSceen");
				setScreen(oxylevel);
			}
			if (gamestatus.pause) {
				gamestatus.pause =false;
				gamestatus.setShowmenu(false);
				gamestatus.showplatform = gamestatus.platformrender;
				return;
			}
			if (oxylevel.loadLevel()) {
				gamestatus.setShowmenu(false);
				gamestatus.showplatform = gamestatus.platformrender;
			} else {
				menu.tButton.setDisabled(true);
			}
//			setScreen(oxylevel);
		}
	}
	
	@Override
	public void pause() {
		log.debug("Pause");
		super.pause();
		gamestatus.pause =true;
		Oxy.gamestatus.message = "OXY Pause";
		Oxy.gamestatus.setShowmenu(true);
	}

	@Override
	public void resume() {
		super.resume();
		}
	
	@Override
	public void dispose() {
		log.debug("Dispose");
		super.dispose();
		if (platformresolver != null) {
			platformresolver.dispose();
		}
		menu.dispose();
		oxylevel.dispose();
	}
}
