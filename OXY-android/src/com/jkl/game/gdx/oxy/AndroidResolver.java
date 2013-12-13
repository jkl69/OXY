package com.jkl.game.gdx.oxy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AndroidResolver implements PlatformResolver {
	
	private SpriteBatch batch ;
	private BitmapFont font;
	private float x,y;
	
	@Override
	public float getGx() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public float getGy() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void render() {
		font = new BitmapFont();
		batch= new SpriteBatch();
//		sr= new SpriteBatch();
		batch.begin();
//		sr.setColor(1, 0, 0, 1);
		font.setColor(1, 0, 0, 1);
//		font.draw(batch,"X:"+String.valueOf(x)+"  Y:"+String.valueOf(y), originX*1.8f, originY*1.8f);
		font.draw(batch,"X:"+String.valueOf(x)+"  Y:"+String.valueOf(y), 10,Gdx.graphics.getHeight() * 0.9f);
		batch.end();
		
		batch.dispose();
		font.dispose();
	}

	@Override
	public void update() {
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
			x = Gdx.input.getAccelerometerY() ;
			y = Gdx.input.getAccelerometerX() *-1 ;		
//			x = Gdx.input.getAccelerometerY() * 2f;
//			y = Gdx.input.getAccelerometerX() * -2f;		
		}
	}

}
