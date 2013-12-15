package com.jkl.game.gdx.oxy.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jkl.game.gdx.oxy.Oxy;

public class Splash implements Screen {
	
	private SpriteBatch batch;
	private Sprite splash;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Oxy.manager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SplashActor());

		batch = new SpriteBatch();
		splash = new Sprite(new Texture("data/Splash.png"));
		splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		Tween.set(splash, SplashActor.ALPHA).target(0).start(Oxy.manager);
//		Tween.to(splash , SplashActor.ALPHA, 3).target(1).start(Oxy.manager);
		Tween.to(splash , SplashActor.ALPHA, 3).target(1).setCallback(new TweenCallback(){
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				Oxy.gamestatus.showmenu = true;
//				((Game) Gdx.app.getApplicationListener()).setScreen(new OxyLevel());
			}
		}).start(Oxy.manager);
	}
	

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}

}
