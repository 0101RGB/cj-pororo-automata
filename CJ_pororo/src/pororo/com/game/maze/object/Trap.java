package pororo.com.game.maze.object;

import java.awt.Graphics2D;

import pororo.com.framework.TimeController;
import pororo.com.game.maze.GameData;
import pororo.com.game.maze.Resource;

public class Trap extends GameObject {
	
	private static final int TRAP_NONE = -1;
	private static final int TRAP_UP = 0;
	private static final int TRAP_ATTACK = 1;
	private static final int TRAP_DOWN = 2;

	private boolean isCollision = false;

	private int damage; // 데미지에 따라 이미지 변경

	private int[] orderNone = { 0 }; // 8초 유지
	private int[] orderUp = { 1, 2, 3, 4, 5 }; // 들락날락
	private int[] orderDown = { 5, 4, 3, 2, 1 }; // 들락날락
	private int[] orderAttack = { 5 }; // 2초 유지

	private int aniState;
	private int aniIndex;

	private int cellIndexX;
	private int cellIndexY;

	private TimeController cont = new TimeController();
	
	public void destroy() {
		cont = null;
	}

	public void draw(Graphics2D g) {
		switch(aniState){
		case TRAP_NONE:
			g.drawImage(Resource.getInstance().trap[(damage/10) - 1][orderNone[aniIndex]], cellIndexX * 32 + GameData.MAP_X + 5, cellIndexY * 32 + GameData.MAP_Y - 7, null);
			break;
		case TRAP_UP:
			g.drawImage(Resource.getInstance().trap[(damage/10) - 1][orderUp[aniIndex]], cellIndexX * 32 + GameData.MAP_X + 5, cellIndexY * 32 + GameData.MAP_Y - 7, null);
			break;
		case TRAP_ATTACK:
			g.drawImage(Resource.getInstance().trap[(damage/10) - 1][orderAttack[aniIndex]], cellIndexX * 32 + GameData.MAP_X + 5, cellIndexY * 32 + GameData.MAP_Y - 7, null);
			break;
		case TRAP_DOWN:
			g.drawImage(Resource.getInstance().trap[(damage/10) - 1][orderDown[aniIndex]], cellIndexX * 32 + GameData.MAP_X + 5, cellIndexY * 32 + GameData.MAP_Y - 7, null);
			break;
		}
	}

	public boolean isKeyInput() {
		return false;
	}

	public void keyEvent(int event) {

	}

	public void pause() {
		cont.pause();
	}

	public void process() {
		if(cont.process()) {
			//각종 상태 변경
			switch(aniState){
			case TRAP_NONE:
				aniState = TRAP_UP;
				aniIndex = 0;
				break;
			case TRAP_ATTACK:
				aniState = TRAP_DOWN;
				aniIndex = 0;
				break;
			}
		}
		switch(aniState){
		case TRAP_NONE:
			break;
		case TRAP_UP:
			aniIndex++;
			if (aniIndex >= orderUp.length) {
				aniState = TRAP_ATTACK;
				aniIndex = 0;
				cont.start(2000);
			}
			if(aniIndex == 2) {
				isCollision = true;
			}
			break;
		case TRAP_ATTACK:
			break;
		case TRAP_DOWN:
			aniIndex++;
			aniIndex++;
			if (aniIndex >= orderDown.length) {
				aniState = TRAP_NONE;
				aniIndex = 0;
				switch(damage) {
				case 10:
					cont.start(8000);
					break;
				case 20:
					cont.start(4000);
					break;
				}
			}
			if(aniIndex == 4) {
				isCollision = false;
			}
			break;
		}
	}

	public void reStart() {
		cont.reStart();
	}

	public void setData(int posX, int posY, int damage) {
		isCollision = false;
		aniState = TRAP_NONE;
		aniIndex = 0;
		
		switch(damage) {
		case 10:
			cont.start(8000);
			break;
		case 20:
			cont.start(4000);
			break;
		}
		
		cellIndexX = posX;
		cellIndexY = posY;
		this.damage = damage;
	}

	public void start() {
		
	}
	
	public int isCollision(int x, int y) {
		if(cellIndexX == x && cellIndexY == y && isCollision) {
			isCollision = false;
			return damage;
		}
		return 0;
	}
	public void setCollision(int x, int y) {
		if(cellIndexX == x && cellIndexY == y) {
			switch(aniState){
			case TRAP_NONE:
				break;
			case TRAP_UP:
				if(aniIndex > 1) {
					isCollision = true;
				}
				break;
			case TRAP_ATTACK:
				isCollision = true;
				break;
			case TRAP_DOWN:
				if(aniIndex < 4) {
					isCollision = true;
				}
				break;
			}
		}
	}
}
