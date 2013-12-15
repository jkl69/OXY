package com.jkl.game.gdx.oxy.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jkl.game.gdx.oxy.Oxy;
import com.jkl.game.gdx.oxy.screens.OxyLevel;

public class MenuStage extends Stage {
    
	private BitmapFont whiteFont;
	private TextureAtlas atlas;
	private Skin skin;
	private TextButton textButton;
	private Oxy game;
	
    public MenuStage (Oxy g) {
    	
    	this.game =  g;
    	
    	atlas = new TextureAtlas("UI/oxy.pack");
    	skin = new Skin(atlas);
    	
    	whiteFont = new BitmapFont(Gdx.files.internal("font/white.fnt"),false);
    	
    	whiteFont.setColor(Color.RED);
    	
    	skin.add("default",whiteFont);
    	
// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
//        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
//        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.up = skin.getDrawable("Button_up");
        textButtonStyle.down = skin.getDrawable("Button_dn");
        
//        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
//        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
 
        textButtonStyle.font = skin.getFont("default");    	
    	
//        textButton=new TextButton("PLAY",skin);
        textButton=new TextButton("PLAY LEVEL",textButtonStyle);
        textButton.setPosition(getWidth() /2 -125, 200);
        textButton.setSize(250, 100);
        
        textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				game.gamestatus.Gamelevel++;
				game.doEvent();
			}
        });

        addActor(textButton);
    }

    @Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		whiteFont.dispose();
		skin.dispose();
		atlas.dispose();		
	}
	

}
