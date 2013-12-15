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
import com.jkl.game.gdx.oxy.Map2World;
import com.jkl.game.gdx.oxy.Oxy;
import com.jkl.game.gdx.oxy.OxyContactListener;
import com.jkl.game.gdx.oxy.OxyTarget;

public class OxyLevel implements Screen {

	private OrthographicCamera camera;

	private SpriteBatch batch, fontbatch;

	private Texture texture;
	private Sprite sprite;
	private ShapeRenderer shape;
	private BitmapFont font;
	
	private TiledMap map;
//	public MapObjects targets;
	
	private OrthogonalTiledMapRenderer maprender;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Map2World map2world;
	
	public Body player;
	private Vector2 move = new Vector2();
	
	private boolean LevelSolved,LevelPause;
	
	public static int ColorNumbers =2;
	public static int Groupsize=2;
	
	public static int opentargets=0;
	
	private String lastError =null;
	
	private String getlevelfile() {
//		return String.format("map/OXY%03d.tmx",gamelevel); 
		return "map/OXY00"+String.valueOf(Oxy.gamestatus.Gamelevel)+".tmx"; 
	}

	private void renderTarget(int x, int y,Color c) {
	      shape.begin(ShapeType.Filled);
	      shape.setColor(c);
	      shape.rect(x, 49-y, 1, 1);
	      shape.end();
	}
	
	private void renderTargets() {
		if (opentargets == Groupsize){
			Oxy.log.info("all Targegts open");
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
		float diff = 0 ;
		if (player.getPosition().y > camera.position.y+11) {
		   diff = (camera.position.y+11 - player.getPosition().y) ;
			Oxy.log.info("up:"+String.valueOf(player.getPosition().y)+":"+String.valueOf(camera.position.y)+":"+String.valueOf(diff));
			camera.position.add(0,-diff,0);
		}
		if (player.getPosition().y < camera.position.y-11) {
			   diff = camera.position.y-11 - player.getPosition().y;
				Oxy.log.info("down:"+String.valueOf(player.getPosition().y)+":"+String.valueOf(camera.position.y)+":"+String.valueOf(diff));
				camera.position.add(0,-diff,0);
		}
		if (player.getPosition().x > camera.position.x +13.5f) {
			   diff = camera.position.x+13.5f - player.getPosition().x;
				Oxy.log.info("right:"+String.valueOf(player.getPosition().x)+":"+String.valueOf(camera.position.x)+":"+String.valueOf(diff));
				camera.position.add(-diff,0,0);
		}
		if (player.getPosition().x < camera.position.x -13.5f) {
			   diff = camera.position.x-13.5f - player.getPosition().x;
				Oxy.log.info("left:"+String.valueOf(player.getPosition().x)+":"+String.valueOf(camera.position.x)+":"+String.valueOf(diff));
				camera.position.add(-diff,0,0);
		}
		
		if (diff != 0 ) {
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			shape.setProjectionMatrix(camera.combined);
			maprender.setView(camera);
		}
	}
	
	private void setLastError(String s) {
		this.lastError  =s;
		if (s ==null) {
			font.setColor(1,1,1,1);
			font.scale(0.5f);
		} else{
			font.setColor(1,0,0,1);
			font.scale(2.0f);
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
		fontbatch.begin();
		if (lastError != null) {
			font.draw(fontbatch, lastError,Gdx.graphics.getWidth() /2-100, Gdx.graphics.getHeight() /2 );
		} else		font.draw(fontbatch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 20, 25);
		fontbatch.end();	
	}
	
	@Override
	public void render(float delta) {
//		Oxy.log.debug("render()");
//		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		maprender.render();
//		debugRenderer.render(world, camera.combined);
			
		if (Oxy.getPlatformresolver() != null) {
			Oxy.getPlatformresolver().update();
			move.x = Oxy.getPlatformresolver().getGx();
			move.y = Oxy.getPlatformresolver().getGy();
		}
		
		renderStatus();
		if (LevelPause)  return;
		renderPlayer();
		renderTargets();
		
		Oxy.fpslog.log();
		
		if ((!LevelSolved) && (isSolved())) {
			setLastError("LEVEL SOLVED");
			LevelSolved= true;
			Oxy.gamestatus.Gamelevel++;
			Oxy.log.info("Level Solved: next is " +getlevelfile());
			loadLevel();
		} 
		
		world.step(delta, 6, 2);

	}

	@Override
	public void resize(int width, int height) {
		
	}

	private void loadLevel() {
		FileHandle file = Gdx.files.internal(getlevelfile());
		if(file.exists()){
			Oxy.log.error("FILE OK");
		} else {
			setLastError("File FAULTEY");
			Oxy.log.error("FILE FAULTY");
			Oxy.gamestatus.Gamelevel =1;
		}
		map = new TmxMapLoader().load(getlevelfile());
		maprender = new OrthogonalTiledMapRenderer(map,1/32f,batch); 
		maprender.setView(camera);

		if (! map2world.createWorld(map)) {
			setLastError("LEVEL ERROR");
			Oxy.log.error("LEVEL ERROR");
			LevelPause = true;
			return;
		};
		this.player = map2world.player;

//		setLastError("Level No. "+gamelevel);
//		LevelPause = true;
	}
		
	@Override
	public void show() {
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		camera = new OrthographicCamera(30,25);	
		camera.position.set(26,25,0);
		camera.update();
		
		shape = new ShapeRenderer();
		shape.setProjectionMatrix(camera.combined);

		fontbatch = new SpriteBatch();
		font =new BitmapFont();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		world = new World(new Vector2(0, 0), true);
		world.setContactListener( new OxyContactListener());
		map2world = new Map2World(world);

		debugRenderer = new Box2DDebugRenderer();

//		OxyContactListener oxycontactlistener = new OxyContactListener();

		texture = new Texture(Gdx.files.internal("data/player.png"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite = new Sprite(texture);
		sprite.setOrigin(.5f,.5f);
    	sprite.setSize(1,1);
    	
    	loadLevel();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		map.dispose();
		maprender.dispose();
		debugRenderer.dispose();
		world.dispose();
		texture.dispose();
		font.dispose();
		batch.dispose();
		fontbatch.dispose();
	}

}
