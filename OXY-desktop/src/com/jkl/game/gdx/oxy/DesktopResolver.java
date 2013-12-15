package com.jkl.game.gdx.oxy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class DesktopResolver implements PlatformResolver {
	
	private SpriteBatch batch ;
	private BitmapFont font;
	private ShapeRenderer render;
	
	private float x,y;
	private float originX,originY;
	private Rectangle shape;
	float shape_faktor = 50;
//	DesktopResolver(){		}
	
	@Override
	public void create() {
		
		font = new BitmapFont();
		batch= new SpriteBatch();
		render = new ShapeRenderer();
		shape = new Rectangle();
		
//		shape.width = 90f;
//		shape.height = 90f;
		float pxsizex = Gdx.graphics.getWidth() /100 * shape_faktor;
		float pxsizey = Gdx.graphics.getHeight() /100 * shape_faktor;
		
		shape.width = pxsizex;
		shape.height = pxsizey;
		
		shape.x = (Gdx.graphics.getWidth() -pxsizex) /2;
		shape.y = (Gdx.graphics.getHeight() -pxsizey) /2;
//		shape.x = (origin.x - shape.width /2);
//		shape.y = (origin.y - shape.height /2);
		
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
		float x1 =shape.x;
		float x2= shape.x + shape.width;
		float x3= shape.x + shape.width;
		float x4= shape.x;
		float y1= shape.y;
		float y2= shape.y;
		float y3= shape.y +shape.height;
		float y4= shape.y +shape.height;
		float f = 4;
		
		if (x>0) {
			y1 = shape.y - x*f;
//			y2 = shape.y;
//			y3 = shape.y + shape.height;
			y4 = shape.y + shape.height + x*f;
		} else {
//			y1 = shape.y;
			y2 = shape.y + x*f;
			y3 = shape.y+ shape.height - x*f;
//			y4 = shape.y+ shape.height;
		}
		
		if (y>0) {
			x1 = shape.x - y*f*2;
			x2 = shape.x + shape.width + y*f*2;
//			x3 = shape.x + shape.width;
//			x4 = shape.x ;
		} else {
//			x1 = shape.x ;
//			x2 = shape.x + shape.width;
			x3 = shape.x+ shape.width - y*f*2;
			x4 = shape.x  + y*f*2;
		}
		
//		batch.begin();
//		batch.setColor(1, 0, 0, 1);
//		font.setColor(1, 0, 0, 1);
//		font.draw(batch,"X:"+String.valueOf(x)+"  Y:"+String.valueOf(y), 10,originY * 1.5f);
//		batch.end();
		
		Gdx.gl.glLineWidth(5);
		Gdx.gl.glEnable(GL10.GL_BLEND);
		
		render.begin(ShapeType.Line);
//		render.setColor(Color.DARK_GRAY);
		render.setColor(1,0,0,.15f);
/*		
		render.line(x1,y1,x2,y2);
		render.line(x2,y2,x3,y3);
		render.line(x3,y3,x4,y4);
		render.line(x4,y4,x1,y1);
//		render.line(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
*/		
//		render.polygon(new float[] {0,0,200,100,100,200});
		render.polygon(new float[] {x1,y1,x2,y2,x3,y3,x4,y4} );
		
		render.end();
		Gdx.gl.glLineWidth(1);
	}

	@Override
	public void update() {
//		Oxy.log.debug("UPDATE");
		originX = Gdx.graphics.getWidth() /2;
		originY = Gdx.graphics.getHeight() /2;		

		int posX = Gdx.input.getX();
		x = (posX - originX) ;
		x = x / originX *10;

		int posY = Gdx.input.getY();
		y = (posY - originY) *-1 ;
		y = y / originY *10;

	}

	@Override
	public void dispose() {
		render.dispose();
		font.dispose();
		batch.dispose();
	}

}
