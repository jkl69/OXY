package com.jkl.game.gdx.oxy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

//public class Target extends RectangleMapObject {
public class OxyTarget extends MapObject {
//public class Target {
	
	public RectangleMapObject RectangleObject;
	public Color color;
	public Boolean show = false;
	public Boolean solved = false;
	
	OxyTarget (RectangleMapObject ro) {
		this.RectangleObject = ro;
		
	}

}
