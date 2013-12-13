package com.jkl.game.gdx.oxy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//public class MouseAccelerometer {
public class MouseAccelerometer extends ShapeRenderer {
	
	float shape_faktor = 90;
	float x;
	float y;
	private Vector2 pos;
	private Vector2 origin;
	private Rectangle viewport; 
	private Rectangle shape;
	
//	public ShapeRenderer shapeRenderer;//= new ShapeRenderer(); 
	
//	ShapeRenderer shapeRenderer ;
	
	MouseAccelerometer() {

		pos = new Vector2();
		origin = new Vector2();
		shape = new Rectangle();
		
		shape.width = 90f;
		shape.height = 90f;
		
//		shape.x = (Gdx.graphics.getWidth() /100 * (100-shape_faktor)) /2;
//		shape.y = (Gdx.graphics.getHeight() /100 * (100-shape_faktor)) /2;
		shape.x = (origin.x - shape.width /2);
		shape.y = (origin.y - shape.height /2);

//		shapeRenderer.setProjectionMatrix(matrix)
	}
	
     public void init() {
//    	 shapeRenderer= new ShapeRenderer(); 
     }
     
//	public void update(Rectangle v) {	
	public void update() {
	
//		this.viewport = v;
		pos.x = Gdx.input.getX();
		pos.y = Gdx.input.getY();
		x = (pos.x - origin.x) ;
		y = (pos.y - origin.y) *-1;
		
//		log.debug("origin:mouse_x"+x+" _y"+y);
		x = x/ (Gdx.graphics.getWidth() /2)*10;
		y = y/ (Gdx.graphics.getHeight() /2)*10;
		
	}

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
		
		Gdx.gl.glLineWidth(5);
	
		begin(ShapeType.Line);
		setColor(1, 0, 0, 1);
//		shapeRenderer.setColor(Color.DARK_GRAY);
		line(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		end();
		System.out.println("render width:"+Gdx.graphics.getWidth()+" height:"+Gdx.graphics.getHeight());
		
		begin(ShapeType.Filled);
		setColor(1, 0, 0, 1);		
		circle(500,350,500);
		
		end();
		
		Gdx.gl.glLineWidth(1);
	}
	
	
}
