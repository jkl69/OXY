package com.jkl.game.gdx.oxy;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.jkl.game.gdx.oxy.screens.OxyLevel;

public class Map2World {
	
	final Color[] targetcolors = {Color.BLUE,Color.CYAN,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.RED,Color.PINK,Color.YELLOW};
	int[]   usedcolor = {0,0,0,0,0,0,0,0};
	Color[] colortable =new Color[8];
	
	private Logger log = new Logger("Map2World",Logger.DEBUG);
//	private Map map;
	private MapObjects mapobjects;
	
	public MapObjects targets = null;
	
	private World world;
	
	public Body player;
	private BodyDef bodyDef =new BodyDef();
	private FixtureDef fixtureDef = new FixtureDef();

	private static int wallcounter;
	private Random  ramdom;
	
	public Map2World(World w) {
		this.world = w;
		ramdom = new Random();
		targets = new MapObjects();
//		createWorld(m);
	}
	
	private void setusedColors() {
		for (int i :usedcolor) {
			usedcolor[i] =0;
		}
		int rand;
		boolean used;
		int colorindex=0;
		do {
			log.debug("choose Color "+colorindex);
			do {
				rand = ramdom.nextInt(targetcolors.length);
				used = isColorused(targetcolors[rand]);
				log.debug("ColNo:"+rand+" color:"+targetcolors[rand].toString()+" used:"+String.valueOf(used));
			} while (used);
			//(usedcolor[rand] != 0);
			colortable[colorindex]=targetcolors[rand];
			colorindex++;
		} while (colorindex < OxyLevel.ColorNumbers );
		
		String txt = "";
		for (Color c : colortable) {
			if (c == null) {
				txt += "null;";
			} else {
				txt += c.toString()+";";
			}
		}
		log.info("usedColors: "+txt);		
	}
	
	private boolean isColorused(Color c1) {
		for (Color c : colortable) {
//			log.info("check color used");
			if (c == c1) return true;
		}
		return false;
	}

	public boolean createWorld(Map map) {
		
		if (map.getLayers().getCount() < 2) {
			log.error("Object Layer missing Layers:"+String.valueOf(map.getLayers().getCount()));
			return false;
		} 
		
		String colors =(String) map.getProperties().get("colors");
		String size =(String) map.getProperties().get("groupsize");
		log.info("MAP_COLORS :"+colors+"  MAP_GROUPSIZE :"+ size);
		if ((size == null) || (colors == null)) {
			return false;
		}
		OxyLevel.ColorNumbers = Integer.parseInt(colors);
		OxyLevel.Groupsize = Integer.parseInt(size);
		setusedColors();
		
		MapLayer layer = map.getLayers().get(1);	
		log.debug(layer.getName());
		mapobjects = (layer.getObjects());
		log.info("MapObjects: "+String.valueOf(mapobjects.getCount()));
		
		for (MapObject o: mapobjects ) {
			log.debug("Found Object _"+o.getName()+"_"+o.getClass().toString());

			if (o.getClass() == RectangleMapObject.class) {
//				RectangleMapObject ro = (RectangleMapObject) o;
				addRectangle((RectangleMapObject) o);
			}
			if ((o.getClass() == EllipseMapObject.class) && (o.getName().equals("player"))) {
				log.info("Found Player ");
				createPlayer((EllipseMapObject) o);
			}

		}
		return true;
	}
	
	private void createPlayer(EllipseMapObject o) {
		
		log.info("Player_x: "+o.getEllipse().x);
		
		bodyDef.type = BodyType.DynamicBody;
		
//		bodyDef.position.set(25, 25);
		bodyDef.position.set(o.getEllipse().x /32,o.getEllipse().y / 32);
		
		bodyDef.linearDamping = .5f;
		bodyDef.angularDamping = .3f;

		this.player = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(.5f);

		// Create a fixture definition to apply our shape to
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit
		
		player.createFixture(fixtureDef);
		player.getFixtureList().get(0).setUserData("Player");
		
		circle.dispose();
	}

	private void addRectangle(RectangleMapObject ro) {

		Fixture f;
		
		PolygonShape shape = new PolygonShape();
		
		float x,y,width,height;
		x = ro.getRectangle().getX() /32;
		y = ro.getRectangle().getY() / 32;
		width = ro.getRectangle().getWidth() /32;
		height = ro.getRectangle().getHeight() /32;
		
		log.debug("addRectangle "+String.valueOf(x)+","+String.valueOf(y)+","+String.valueOf(width)+","+String.valueOf(height));
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x+ width /2, y+ height /2 ); 
		shape.setAsBox(width /2 , height /2);

		//	B2Rect = world.createBody(bodyDef);
		f = world.createBody(bodyDef).createFixture(shape,0.0f);

		if (ro.getName() == null) {
			wallcounter++;
			f.setUserData("wall"+String.valueOf(wallcounter));
		} else {

			OxyTarget t =  new OxyTarget(ro);
			f.setUserData(t);
			
			targets.add(t);
			
			int rand;
			do {
				rand = ramdom.nextInt(OxyLevel.ColorNumbers);
				log.info("Can i use Color No."+rand+"?");
			} while (usedcolor[rand] >= OxyLevel.Groupsize);
			
			log.info("use Color No:"+rand);
			t.color = colortable[rand];
			usedcolor[rand]++;
		}

		shape.dispose();
	}

}
