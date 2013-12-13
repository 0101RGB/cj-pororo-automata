package pororo.com.game.fishing.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import pororo.com.game.fishing.Resource;

public class Item extends GameObject {
	private static final int NONE_MOVE = -1;
	private static final int DOWN_MOVE = 0;
	private static final int UP_MOVE = 1;
	private static final int LEFT_MOVE = 2;
	private static final int RIGHT_MOVE = 3;
	
//	private static final int ITEM_TIME = 0;
//	private static final int ITEM_SPEED = 1;
//	private static final int ITEM_SUPER = 2;
	
//	private static final int MOVE_SPEED = 0;
	private static final Random rnd = new Random();
	

	private int aniCount = 0;
	
	private boolean state = false;
	private int posX;
	private int posY;
	private int centerX;
	private int centerY;
	private int width;
	private int height;

	private int moveState = NONE_MOVE;
	private int kind;
	private int itemVisableCnt;
//	private static Rectangle boundArea = new Rectangle(30, 77, 900, 452-18);

	public void draw(Graphics2D g) {
//		if(state) {
			if(kind < 3) {
				if(aniCount > itemVisableCnt) {
					aniCount = 0;
					state = false;
					return;
				}
				g.drawImage(Resource.getInstance().item[kind], posX, posY, null);
			}
			else if(kind == 3) {
				if(aniCount >= Resource.getInstance().effectTime.length) {
					aniCount = 0;
					state = false;
					return;
				}
				g.drawImage(Resource.getInstance().effectTime[aniCount], posX, posY, null);
			}
			else if(kind == 4) {
				// 스피드 아이템의 이펙트
//				if(aniCount >= Resource.getInstance().effectSpeed.length) {
//					aniCount = 0;
//					state = false;
//					return;
//				}
//				g.drawImage(Resource.getInstance().effectSpeed[aniCount], posX, posY, null);
			}
			else if(kind == 5) {
				if(aniCount >= Resource.getInstance().effectSuper.length) {
					aniCount = 0;
					state = false;
					return;
				}
				g.drawImage(Resource.getInstance().effectSuper[aniCount], posX, posY, null);
			}
			else if(kind == 6) {
				if(aniCount >= Resource.getInstance().effectEnd.length) {
					aniCount = 0;
					state = false;
					return;
				}
				g.drawImage(Resource.getInstance().effectEnd[aniCount], posX, posY, null);
			}
//		}

	}

	public int getPosX() {
		return posX;
	}
	public void setPosX(int x) {
		posX = x;
	}

	public int getPosY() {
		return posY;
	}
	public void setPosY(int y) {
		posY = y;
	}
	public int getKind() {
		return this.kind;
	}

	public boolean isKeyInput() {
		return false;
	}

	public void keyEvent(int event) {
	}

	public void pause() {
	}

	public void process() {
		if(state) {
			aniCount++;
			switch (moveState) {
			case DOWN_MOVE:
				break;
			case UP_MOVE:
				break;
			case LEFT_MOVE:
				break;
			case RIGHT_MOVE:
				break;
			case NONE_MOVE:
				break;
			}
		}
	}

	public int randomPos(int scope) {
		return (Math.abs(rnd.nextInt()) % scope);
	}
	
	public void reStart() {
	}
	public void setAnimation(int aniState) {
		if(aniState == 0) {	// 아이템 보이기
			aniCount = 0;
			state = true;
		}
		else {	// 아이템 안보이기
			aniCount = 0;
			state = false;
		}
		
	}
	public boolean isState() {
		return state;
	}
	
	public boolean collisionCheck(int x, int y, int wid, int mode) {
		
		double r12 = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
//		System.out.println("Point1("+x+","+y+"), Point2("+centerX+","+centerY+") : " + r12);
		if(r12 < ((wid-30+height-20)/2)) {
			return true;
		}
		return false;
	}
	
	public int createItemType(int level) {
		if(level >= 1 && level <= 3) {
			return 0;
		}
		else if(level >= 4 && level <= 6) {
			return randomPos(2);
		}
		else {
			return randomPos(3);
		}
	}

	public void start() {
		aniCount = 0;
	}
	public void start(int level) {
		kind = createItemType(level);
		if (kind == 1)  // CJ에서 스피드(빨간망치)는 시간(별)로 대체함.
			kind = 0;
		
		aniCount = 0;
		itemVisableCnt = 70;
		posX = 50 + randomPos(800);
		posY = 100 + randomPos(300);
		
		width = Resource.getInstance().item[kind].getWidth(null);
		height = Resource.getInstance().item[kind].getHeight(null);
		
		centerX = posX + (int)(width / 2);
		centerY = posY + (int)(height / 2);
	}
	
	public void startEffect(int mode) {
		kind = mode+2;	// 3, 4, 5, 6
		aniCount = 0;
		itemVisableCnt = 4;
	}
	
	public void destroy() {
	}

}
