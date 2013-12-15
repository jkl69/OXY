package com.jkl.game.gdx.oxy;

public interface PlatformResolver {

	public void create();
	public float getGx();
	public float getGy();
	public void render();
	public void update();
	public void dispose();
}
