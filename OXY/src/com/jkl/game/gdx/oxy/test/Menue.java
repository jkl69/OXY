package com.jkl.game.gdx.oxy.test;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jkl.game.gdx.oxy.Oxy;
import com.jkl.game.gdx.oxy.screens.OxyLevel;
import com.jkl.game.gdx.oxy.screens.Splash;
import com.jkl.game.gdx.oxy.screens.SplashActor;

public class Menue implements Screen {
    Skin skin,skin2;
    Stage stage;
    SpriteBatch batch;
    Sprite splash;
    Game g;
    BitmapFont whiteFont;
    TextureAtlas atlas;
	private boolean fadein;
	
	Table table;
	    
    public Menue(Game g){
        create();
        this.g=g;
    }
 
    public Menue(){
        create();
    }
    
    public void create(){
    	
		Tween.registerAccessor(Sprite.class, new SplashActor());
		
		batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
		
//        atlas = new TextureAtlas(Gdx.files.internal("UI/oxy.pack"));
        atlas = new TextureAtlas("UI/oxy.pack");
        skin2 = new Skin(atlas);
        
        table = new Table(skin2);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        splash = new Sprite(new Texture("data/Splash.png"));
		splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();
        
        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(64, 64, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
 
        skin.add("white", new Texture(pixmap));
 
        // Store the default libgdx font under the name "default".
        BitmapFont bfont=new BitmapFont();
        whiteFont = new BitmapFont(Gdx.files.internal("font/white.fnt"),false);
        
        bfont.scale(1);
        skin.add("default",bfont);
        skin.add("whitefont",whiteFont);
 
        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
//        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
//        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.up = skin2.getDrawable("Button_up");
        textButtonStyle.down = skin2.getDrawable("Button_dn");
        
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
 
//        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.font = skin.getFont("whitefont");
 
        skin.add("default", textButtonStyle);
 
        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton textButton=new TextButton("PLAY",textButtonStyle);
        textButton.setPosition(200, 200);
//        textButton.padLeft(10.0f);
        textButton.pad(10.0f);
//        textButton.getBackground().setLeftWidth(20);
//        textButton.pad(10.0f, 10.0f, 10.0f, 10.0f);

        textButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
                textButton.setText("Starting new game");
                g.setScreen( new OxyLevel());
			}
        });

       table.add(textButton);
        stage.addActor(table);
//        stage.addActor(textButton);
	
        Tween.set(splash, SplashActor.ALPHA).target(0).start(Oxy.manager);
//		Tween.to(splash , SplashActor.ALPHA, 3).target(1).start(Oxy.manager);
        
		Tween.to(splash , SplashActor.ALPHA, 3).target(1).setCallback(new TweenCallback(){
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				fadein= true;
//				((Game) Gdx.app.getApplicationListener()).setScreen(new OxyLevel());
			}
		}).start(Oxy.manager);
    
    }
 
    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
		batch.begin();
		splash.draw(batch);
		batch.end();        
		
		Oxy.manager.update(delta);
		
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (fadein) stage.draw();
        
        Table.drawDebug(stage);
    }
 
    @Override
    public void resize (int width, int height) {
        stage.setViewport(width, height, false);
    }
 
    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
    }
 
    @Override
    public void show() {
        // TODO Auto-generated method stub
 
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
}