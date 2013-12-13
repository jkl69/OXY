package com.jkl.game.gdx.oxy.client;

import com.badlogic.gdx.Gdx;
import com.jkl.game.gdx.oxy.PlatformResolver;

public class WebResolver implements PlatformResolver {
	
	private float x,y;
	private float originX,originY;
	
	@Override
	public float getGx() {
		return x;
	}

	@Override
	public float getGy() {
		return y;
	}

	@Override
	public void render() {
		
	}

	@Override
	public void update() {
		originX = Gdx.graphics.getWidth() /2;
		originY = Gdx.graphics.getHeight() /2;		

		int posX = Gdx.input.getX();
		x = (posX - originX) ;
		x = x / originX *10;

		int posY = Gdx.input.getY();
		y = (posY - originY) *-1 ;
		y = y / originY *10;
	}

}
