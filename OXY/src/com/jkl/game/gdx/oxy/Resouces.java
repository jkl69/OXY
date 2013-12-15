package com.jkl.game.gdx.oxy;

public class Resouces {
	public static int Gamelevel=0;
	private boolean showmenu = false;
	public boolean platformrender =true;
	public boolean showplatform =true;
	public String message ="OXY";
	public String ERROR ="";
	
	public float cameraWidth = 26f;
	public float cameraHeight = 19.5f;
	public boolean pause = false;
	
	public boolean isShowmenu() {
		return showmenu;
	}
	public void setShowmenu(boolean showmenu) {
		Oxy.menu.update();
		this.showplatform = false;
		this.showmenu = showmenu;
	}

}
