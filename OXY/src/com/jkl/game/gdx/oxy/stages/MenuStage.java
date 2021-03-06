package com.jkl.game.gdx.oxy.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jkl.game.gdx.oxy.Oxy;

public class MenuStage extends Stage {
    
	private BitmapFont whiteFont,bmpfont;
	private TextureAtlas atlas;
	private Skin skin;
	public TextButton tButton;
	private Label l;
	private Oxy game;
	private TextButton eButton;
	private Table table;
	
    public MenuStage (Oxy g) {
    	this.game =  g;
    	
    	atlas = new TextureAtlas("UI/oxy.pack");
     	skin = new Skin(Gdx.files.internal("UI/oxy.json"),atlas);
     	
     	table = new Table(skin);
    	table.debug();
    	table.setBounds(0, 0, getWidth(),  getHeight());
    	
    	bmpfont = new BitmapFont(Gdx.files.internal("font/font1.fnt"),false);
    	
//    	whiteFont.setScale(0.8f);
    	bmpfont.setScale(2f);
    	bmpfont.setColor(Color.GREEN);
    	
//    	whiteFont.setColor(Color.RED);
	
 //   	skin.add("default",whiteFont);

    	LabelStyle ls = new LabelStyle();
//        ls.font = whiteFont;
        ls.font = bmpfont;
        
        skin.getFont("cool").setScale(2f);
        l = new Label("test\nTest",skin);
//        l = new Label("test\nTest",ls);
        l.setPosition(getWidth() /2 - l.getWidth()/2, getHeight() /2 +l.getHeight());
//    	l.setColor(1,0,0,1);
    	
//        textButton=new TextButton("PLAY",skin);
        tButton=new TextButton("PLAY",skin);
        eButton = new  TextButton("EXIT",skin);
//        tButton=new TextButton("PLAY",textButtonStyle);
//        eButton = new  TextButton("EXIT",textButtonStyle);
        tButton.pad(10);
        eButton.pad(10);
//        textButton.setSize(150, 80);
//        tButton.setSize(tButton.getWidth()+6, tButton.getHeight() +6);
//        tButton.setPosition(getWidth() /2 -tButton.getWidth() /2, 15 +tButton.getHeight()*2);
        
        eButton.setSize(tButton.getWidth(), tButton.getHeight() );
        eButton.setPosition(tButton.getX() , tButton.getY() - tButton.getHeight()-2);
//        textButton.setd.d
        
        eButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Gdx.app.exit();
			}
        });
        
        tButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
//				game.gamestatus.Gamelevel++;
				System.out.println("EVENT"+event.getTarget());
				game.doEvent();
			}
        });
        
        l.setText("hhh");
//        addActor(tButton);
        table.add(l);
        table.getCell(l).spaceBottom(100);
        table.row();
//        addActor(eButton);
        table.add(tButton);
        table.row();
        table.getCell(tButton).spaceBottom(10);
        table.add(eButton);
//        addActor(l);
        addActor(table);
    }

    public void update() {
    	l.setText(Oxy.gamestatus.message);
    }
    
    @Override
	public void dispose() {
		// TODO Auto-generated method stub
		Oxy.log.debug("Stage.Dispose");
		super.dispose();
//		whiteFont.dispose();
		bmpfont.dispose();
		skin.dispose();
		atlas.dispose();	
	}
	

}
