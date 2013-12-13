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
		
		manager = new TweenManager();
		//For android		Tween.se.setPoolEnabled(true);
		
//		setScreen(new Splash());
//		setScreen(new OxyLevel());
		setScreen(new Menue(this));
		}

/*
	private void logmap(TiledMap map2) {
		System.out.println("map layers:"+map.getLayers().getCount());
		TiledMapTileLayer tl = (TiledMapTileLayer) map.getLayers().get(0);
		System.out.println("layer_name:"+tl.getName());
//		int tileid = tl.getCell(19,30).getTile().getId();
		for (int px=0;px<49;px++) {
			for (int py=0;py<49;py++) {
				Cell cell = tl.getCell(px,py);
				if (cell != null) {
					System.out.println(px+"-"+py+" TileID "+cell.getTile().getId());
				}
			}
//			log.info(String.valueOf(tileid.getTile().getId()));
		}
	}
*/
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {	
		super.render();
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
