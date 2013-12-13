package pororo.com.game;

import java.awt.Image;

import pororo.com.SceneManager;


public class MenuData {
	
	public String gameCode;
	public int playCnt = 0;
	public Image icon;
	public int isFree;
	
	public MenuData( String gameCode , int playCnt , int isFree , Image img ) {
		this.gameCode = gameCode;
		this.playCnt = playCnt;
		this.isFree = isFree;
		this.icon = img;
	}
	
	public Image getImage() {
		return this.icon;
	}
	
	public void dispose() {
		gameCode = null;
		SceneManager.getInstance().removeImage(icon);
	}
}
