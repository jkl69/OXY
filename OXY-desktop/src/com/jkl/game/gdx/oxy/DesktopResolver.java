package com.jkl.game.gdx.oxy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DesktopResolver implements PlatformResolver {
	
	private SpriteBatch batch ;
	private BitmapFont font;
	private float x,y;
	private float originX,originY;
//	private int width,height;
	
	DesktopResolver(){
//		font = new BitmapFont();
//		batch= new SpriteBatch();
		originX = Gdx.graphics.getWidth() /2;
		originY = Gdx.graphics.getWidth() /2;		
//		update();
		}
	
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
//		System.out.println("render width:"+originX*2);

		font = new BitmapFont();
		batch= new SpriteBatch();
//		sr= new SpriteBatch();
		batch.begin();
//		sr.setColor(1, 0, 0, 1);
		font.setColor(1, 0, 0, 1);
//		font.draw(batch,"X:"+String.valueOf(x)+"  Y:"+String.valueOf(y), originX*1.8f, originY*1.8f);
		font.draw(batch,"X:"+String.valueOf(x)+"  Y:"+String.valueOf(y), 10,originY * 1.5f);
		batch.end();
		
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
