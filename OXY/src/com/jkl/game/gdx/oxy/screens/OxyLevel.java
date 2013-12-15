package com.jkl.game.gdx.oxy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.jkl.game.gdx.oxy.Map2World;
import com.jkl.game.gdx.oxy.Oxy;
import com.jkl.game.gdx.oxy.OxyContactListener;
import com.jkl.game.gdx.oxy.OxyTarget;

public class OxyLevel implements Screen {

	public static Logger log =  new Logger("OxyLevel",Logger.DEBUG);
	private OrthographicCamera camera;
	private SpriteBatch batch, fontbatch;
	private Texture texture;
	private Sprite sprite;
	private ShapeRenderer shape;
	private BitmapFont font;
	private float LevelTime = 50000f;
	private TiledMap map;
//	public MapObjects targets;
	
	private OrthogonalTiledMapRenderer maprender;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Map2World map2world;
	
	public Body player;
	private Vector2 move = new Vector2();
	
	private boolean LevelSolved;
	
	public static int ColorNumbers =2;
	public static int Groupsize=2;
	
	public static int opentargets=0;
	
	private String getlevelfile() {
		return String.format("map/OjXY%03d.tmx",Oxy.gamestatus.Gamelevel); 
//		return "map/OXY00"+String.valueOf(Oxy.gamestatus.Gamelevel)+".tmx"; 
	}

	private void renderTarget(int x, int y,Color c) {
	      shape.begin(ShapeType.Filled);
	      shape.setColor(c);
	      shape.rect(x, 49-y, 1, 1);
	      shape.end();
	}
	
	private void renderTargets() {
		if (opentargets == Groupsize){
			log.info("all Targegts open");
			for (MapObject o: map2world.targets) {
				OxyTarget t = (OxyTarget) o;
				if (t.show) {
					t.solved =true;
				}
			}
			opentargets =0;
		}
		
		for (MapObject o: map2world.targets) {
				
				OxyTarget t = (OxyTarget) o;
			int x= (int) (t.RectangleObject.getRectangle().x /32);
			int y = (int) (49-t.RectangleObject.getRectangle().y/32);
			
			if (t.show || t.solved) {
				renderTarget(x,y,t.color);
			}
		}
	}
	
	private Boolean isSolved() {
		boolean result=true;
		for (MapObject o: map2world.targets) {
			OxyTarget t = (OxyTarget) o;
			if (!t.solved) result = false;
		}
		return result;
	}
	
	private void updateCamera() {
		float p = 0.80f;
		float diff = 0 ;
//		if (player.getPosition().y > camera.position.y+11) {
		if (player.getPosition().y > camera.position.y + (camera.viewportHeight /2 *p)) {
		   diff = (camera.position.y+(camera.viewportHeight /2 *p) - player.getPosition().y) ;
			log.info("up:"+String.valueOf(player.getPosition().y)+":"+String.valueOf(camera.position.y)+":"+String.valueOf(diff));
			camera.position.add(0,-diff,0);
		}
		if (player.getPosition().y < camera.position.y-(camera.viewportHeight /2 *p)) {
			   diff = camera.position.y-(camera.viewportHeight /2 *p) - player.getPosition().y;
				log.info("down:"+String.valueOf(player.getPosition().y)+":"+String.valueOf(camera.position.y)+":"+String.valueOf(diff));
				camera.position.add(0,-diff,0);
		}
		if (player.getPosition().x > camera.position.x +(camera.viewportWidth /2 *p)) {
			   diff = camera.position.x+(camera.viewportWidth /2 *p) - player.getPosition().x;
				log.info("right:"+String.valueOf(player.getPosition().x)+":"+String.valueOf(camera.position.x)+":"+String.valueOf(diff));
				camera.position.add(-diff,0,0);
		}
		if (player.getPosition().x < camera.position.x -(camera.viewportWidth /2 *p)) {
			   diff = camera.position.x-(camera.viewportWidth /2 *p) - player.getPosition().x;
				log.info("left:"+String.valueOf(player.getPosition().x)+":"+String.valueOf(camera.position.x)+":"+String.valueOf(diff));
				camera.position.add(-diff,0,0);
		}
		
		if (diff != 0 ) {
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			shape.setProjectionMatrix(camera.combined);
			maprender.setView(camera);
		}
	}

	private void renderPlayer() {
		if (player!= null) {
			player.applyForceToCenter(move, true);
			batch.begin();
			sprite.setPosition(player.getPosition().x-.5f,player.getPosition().y-.5f);
			sprite.setRotation(player.getAngle()*MathUtils.radiansToDegrees);
			sprite.draw(batch);
			batch.end();
			
			updateCamera();
		}
	}

	private void renderStatus() {
		
	}
	
	@Override
	public void render(float delta) {
//		log.debug("render()");
//		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (Oxy.gamestatus.isShowmenu())  return;

		if (map != null) maprender.render();
//		debugRenderer.render(world, camera.combined);
			
		if (Oxy.getPlatformresolver() != null) {
			Oxy.getPlatformresolver().update();
			move.x = Oxy.getPlatformresolver().getGx();
			move.y = Oxy.getPlatformresolver().getGy();
		}
		
		renderStatus();
		Gdx.gl.glEnable(GL10.GL_BLEND);
		renderPlayer();
		renderTargets();
		
		Oxy.fpslog.log();
		
		if ((!LevelSolved) && (isSolved())) {
//			setLastError("LEVEL SOLVED");
			LevelSolved= true;
			Oxy.gamestatus.Gamelevel++;
			Oxy.gamestatus.message ="LEVEL "+(Oxy.gamestatus.Gamelevel-1)+"is solved\nNEXT LEVEL "+Oxy.gamestatus.Gamelevel;
			Oxy.gamestatus.setShowmenu(true);
			log.info("Level Solved: next is " +getlevelfile());
//			loadLevel();
		} 
		
		LevelTime -= delta;
		world.step(delta, 6, 2);

	}

	@Override
	public void resize(int width, int height) {
		float aratio = (float)height / (float)width;
		log.debug("resize "+width+":"+height+" aspectRatio:"+aratio);
		Oxy.gamestatus.cameraHeight = Oxy.gamestatus.cameraWidth * aratio;
		
		camera.viewportWidth  =Oxy.gamestatus.cameraWidth;
		camera.viewportHeight  =Oxy.gamestatus.cameraHeight;
//		camera = new OrthographicCamera(Oxy.gamestatus.cameraWidth , Oxy.gamestatus.cameraHeight);	
		camera.update();
		log.info("Set Camera.Width "+String.valueOf(camera.viewportWidth)+" Heigth "+String.valueOf(camera.viewportHeight));
		shape.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
	}
	
	private void clearLevel() {
		log.debug("ClearLevel");
		if (map != null) {
			map.dispose();
			maprender.dispose();
		}
		if (world != null) {
			world.dispose();
		}
		LevelSolved = false;
	}
	
	public boolean loadLevel() {
		log.debug("loadLevel");	
		
		FileHandle file = Gdx.files.internal(getlevelfile());
		if(file.exists()){
			log.error("FILE OK");
		} else {
			Oxy.gamestatus.message = "Level NOT found";
			log.error("Level NOT found");
			Oxy.gamestatus.setShowmenu(true);
			return false;
		}
		clearLevel();
		
		map = new TmxMapLoader().load(getlevelfile());
		maprender = new OrthogonalTiledMapRenderer(map,1/32f,batch); 
		maprender.setView(camera);
		
		world = new World(new Vector2(0, 0), true);
		world.setContactListener( new OxyContactListener());
		
		if (! map2world.createWorld(map, world)) {
			Oxy.gamestatus.message = "LEVEL ERROR";
			log.error("LEVEL ERROR");
			Oxy.gamestatus.setShowmenu(true);
			return false;
		};
		this.player = map2world.player;
		
		log.info("set Camera to PlayerPos: "+player.getPosition().x+":"+(49 - player.getPosition().y));
//		camera.position.set((player.getPosition().x / 32) +camera.viewportWidth , 49-player.getPosition().y ,0);
		camera.position.set(25,24,0);
		updateCamera();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shape.setProjectionMatrix(camera.combined);
		maprender.setView(camera);
//		camera.position.set(0,0,0);
		return true;
	}
		
	@Override
	public void show() {
//		camera = new OrthographicCamera(30,25);	
		
//		camera.position.set(26,25,0);
		shape = new ShapeRenderer();

		fontbatch = new SpriteBatch();
		font =new BitmapFont();
		
		batch = new SpriteBatch();

		camera = new OrthographicCamera();	
		
		map2world = new Map2World();

		debugRenderer = new Box2DDebugRenderer();

		texture = new Texture(Gdx.files.internal("data/player.png"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
		sprite.setOrigin(.5f,.5f);
    	sprite.setSize(1,1);

	}
	

	@Override
	public void hide() {
		log.debug("Hide");
//		dispose();
	}

	@Override
	public void pause() {
		log.debug("Pause");
		Oxy.gamestatus.message = "Level Pause";
		Oxy.gamestatus.setShowmenu(true);
	}
	

	@Override
	public void resume() {
		Oxy.log.debug("Level.resume");
	}

	@Override
	public void dispose() {
		log.debug("Dispose");
		if (map != null) {
			map.dispose();
			maprender.dispose();
		}
		if (world != null) {
			world.dispose();
			debugRenderer.dispose();
		}
		if (texture != null) {
			texture.dispose();
			font.dispose();
			batch.dispose();
			fontbatch.dispose();
		}
	}

}
