package pororo.com.game.maze.object;

import java.awt.Graphics2D;
import java.awt.Image;

import pororo.com.game.maze.GameData;
import pororo.com.game.maze.Resource;

public class Item extends GameObject {
	private static final int NONE_MOVE = -1;
	private static final int DOWN_MOVE = 0;
	private static final int UP_MOVE = 1;
	private static final int LEFT_MOVE = 2;
	private static final int RIGHT_MOVE = 3;

	// 에니메이션 위치 정의
	private int aniPos[] = { 0, 4, 6, 7, 4 };
	private int aniCount = 0;

	
	// 따라 다니는 아이템 위치
	private int posX;
	private int posY;
	private int moveX = 0;
	private int moveY = 0;
	public int cellIndexX;
	public int cellIndexY;

	public int itemIndex = GameData.ITEM_NONE;

	private int moveState = NONE_MOVE;

	public void destroy() {

	}

	public void draw(Graphics2D g) {
		if (itemIndex != GameData.ITEM_NONE) {
			g.drawImage(Resource.getInstance().item[itemIndex][0], posX
					+ GameData.MAP_X, posY - aniPos[aniCount] - 7
					+ GameData.MAP_Y, null);
		}

	}

	public int getPosX() {
		return (posX + GameData.MAP_X);
	}

	public int getPosY() {
		return (posY - aniPos[aniCount] - 7 + GameData.MAP_Y);
	}
	
	public Image getImage() {
		return Resource.getInstance().item[itemIndex][1];
	}

	public boolean isKeyInput() {
		return false;
	}

	public void keyEvent(int event) {}

	public void pause() {}

	public void process() {
		if (itemIndex == GameData.ITEM_NONE)
			return;

		aniCount = (aniCount + 1) % aniPos.length;

		if (moveState == NONE_MOVE)
			return;

		switch (moveState) {
		case DOWN_MOVE:
			posY += GameData.MOVE_SPEED;
			break;
		case UP_MOVE:
			posY -= GameData.MOVE_SPEED;
			break;
		case LEFT_MOVE:
			posX -= GameData.MOVE_SPEED;
			break;
		case RIGHT_MOVE:
			posX += GameData.MOVE_SPEED;
			break;
		}
	}

	public void reStart() {

	}

	public void start() {

	}

	public void setMove(int x, int y) {
		moveX = x * 32;
		moveY = y * 32;
		if (cellIndexX < x) {// 오른쪽 이동
			moveState = RIGHT_MOVE;
		} else if (cellIndexX > x) {// 왼쪽 이동
			moveState = LEFT_MOVE;
		} else if (cellIndexY < y) {// 아래 이동
			moveState = DOWN_MOVE;
		} else if (cellIndexY > y) {// 위 이동
			moveState = UP_MOVE;
		}
	}

	public void setCell(int x, int y) {
		cellIndexX = x;
		cellIndexY = y;

		posX = cellIndexX * 32;
		posY = cellIndexY * 32;

		moveX = posX;
		moveX = posY;

		moveState = NONE_MOVE;
	}

	public void setItem(int item) {
		itemIndex = item;
		aniCount = 0;
	}

	public void reset() {
		cellIndexX = 0;
		cellIndexY = 0;

		posX = cellIndexX * 32;
		posY = cellIndexY * 32;

		moveX = posX;
		moveX = posY;

		moveState = NONE_MOVE;

		itemIndex = GameData.ITEM_NONE;
	}
}
