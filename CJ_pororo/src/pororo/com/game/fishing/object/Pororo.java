package pororo.com.game.fishing.object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import org.havi.ui.event.HRcEvent;

import pororo.com.StateValue;
import pororo.com.automata.Constant;
import pororo.com.framework.TimeController;
import pororo.com.game.fishing.Resource;

public class Pororo extends GameObject {

	private static final int PORORO_DOWN_IDEL = 0;
	private static final int PORORO_DOWN_MOVE = 1;
	private static final int PORORO_UP_IDEL = 2;
	private static final int PORORO_UP_MOVE = 3;
	private static final int PORORO_LEFT_IDEL = 4;
	private static final int PORORO_LEFT_MOVE = 5;
	private static final int PORORO_RIGHT_IDEL = 6;
	private static final int PORORO_RIGHT_MOVE = 7;
	private static final int PORORO_ACT = 8;
	private static final int PORORO_DMG = 9;
	private static final int PORORO_DMG_E = 10;
	private static final int PORORO_GAMEOVER = 11;
	private static final int PORORO_SUCCES = 12;
	private static final int DMG_TIME = 3;
//	public static Rectangle boundArea = new Rectangle(46, 95, 868, 408);
	private static Rectangle boundArea = new Rectangle(30, 77, 900, 452-18);
	
	// 960 - 46 - 158 = 960 - 204 = 756
	// 540 - 37 - 162 = 540 - 199 = 341

	private Resource resource = Resource.getInstance();
	private TimeController cont = new TimeController();
	
	private int aniImgIndex[] = {
			0, 2, 4, 2,
			0, 2, 4, 2,
			0, 2, 4, 2,
			1, 3, 4, 2
	};

//	private boolean isKeyControll;

	private int aniState = PORORO_DOWN_IDEL;
	private int aniIndex;
	private int actAniState = 0;

	private int posX;
	private int posY;
	public int centerX;
	public int centerY;
	public int width;
	public int height;
	
	private int speedX;
	private int speedY;

	public int mode;

	public int dir;
	private int grade;
	private int guage;
	private int modeGuage;
	private int speedLevel[][] = {
			{17, 17, 20, 20},
			{23, 23, 28, 28},
			{28, 28, 32, 32}
	};

	public void destroy() {
		resource = null;
		cont = null;
	}

	public void draw(Graphics2D g) {
		switch (aniState) {
		case PORORO_DOWN_IDEL:
		case PORORO_DOWN_MOVE:
		case PORORO_UP_IDEL:
		case PORORO_UP_MOVE:
		case PORORO_LEFT_IDEL:
		case PORORO_LEFT_MOVE:
		case PORORO_RIGHT_IDEL:
		case PORORO_RIGHT_MOVE:
			if(modeGuage > 0) {
				drawModeGuage(g);
			}
			
			if (actAniState == 0) {
				drawMove(g);
			} else if(actAniState == PORORO_ACT) {
				drawAct(g);
			} else if(actAniState == PORORO_DMG) {
				drawDmg(g);
			} else if(actAniState == PORORO_DMG_E) {
				drawDmgE(g);
			}
			break;
		case PORORO_GAMEOVER:
			drawGameover(g);
			break;
		case PORORO_SUCCES:
			break;
		}

	}
	
	public void drawModeGuage(Graphics2D g) {
		if (modeGuage > 0 && modeGuage < 706) {
			g.drawImage(resource.itemGuage[1], 127, 480, null);
			g.drawImage(resource.itemGuage[0], 127, 480, 127+modeGuage, 480+26, 0, 0, modeGuage, 26, null);
			if(modeGuage == 5) {
				setMode(4);
			}
//			g.drawImage(resource.guage, 150, 51, life, 17, null);
		}
	}

	public void drawMove(Graphics2D g) {
		if (mode == 0 || mode == 1 || mode == 2) { // 일반 상태   // 빨간망치 추가(mode 2)
			if (dir == -1) {
//				if (aniIndex >= resource.prLeftMove.length)
				if (aniIndex >= aniImgIndex.length)
					aniIndex = 0;
				g.drawImage(resource.prLeftMove[aniImgIndex[aniIndex]], posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= aniImgIndex.length)
					aniIndex = 0;
				g.drawImage(resource.prRightMove[aniImgIndex[aniIndex]], posX, posY, null);
			}
		} 
//		else if (mode == 2) { // 빨간 망치
//			if (dir == -1) {
//				if (aniIndex >= aniImgIndex.length)
//					aniIndex = 0;
//				g.drawImage(resource.prLeftRedMove[aniImgIndex[aniIndex]], posX, posY, null);
//			} else if (dir == 1) {
//				if (aniIndex >= aniImgIndex.length)
//					aniIndex = 0;
//				g.drawImage(resource.prRightRedMove[aniImgIndex[aniIndex]], posX, posY, null);
//			}
//		} 
		else if (mode == 3) { // 무적 망치
			if (dir == -1) {
				if (aniIndex >= aniImgIndex.length)
					aniIndex = 0;
				g.drawImage(resource.prLeftAbsMove[aniImgIndex[aniIndex]], posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= aniImgIndex.length)
					aniIndex = 0;
				// 2012.05.17 YJY 무적일때는 Left 이미지만 가지고 반전하도록 수정함.
//				g.drawImage(resource.prRightAbsMove[aniImgIndex[aniIndex]], posX, posY, null);
				int ww = resource.prLeftAbsMove[aniImgIndex[aniIndex]].getWidth(null);
				int hh = resource.prLeftAbsMove[aniImgIndex[aniIndex]].getHeight(null);
				drawImageHR(g, resource.prLeftAbsMove[aniImgIndex[aniIndex]], posX, posY, 
						0, 0, ww, hh, null);
			}
		}
	}
	
	// my favorite drawing function.. / horizontal reverse
    private void drawImageHR(Graphics2D g, Image img, int x, int y, int crx, int cry, int w, int h, ImageObserver ob) {
		g.drawImage(img, x, y, x+w, y+h, crx+w-1, cry, crx, cry+h, ob);
	}

	
	public void drawGameover(Graphics2D g) {
		if (dir == -1) {
			g.drawImage(resource.prLeftEnd, posX, posY, null);
		} else if (dir == 1) {
			g.drawImage(resource.prRightEnd, posX, posY, null);
		}
	}

	public void drawAct(Graphics2D g) {
		if (mode == 1 || mode == 2) { // 일반 상태    // 빨간 망치 (mode 2) 추가
			if (dir == -1) {
				if (aniIndex >= resource.prLeftAct.length) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prLeftAct[aniIndex], posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= resource.prRightAct.length) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prRightAct[aniIndex], posX, posY, null);
			}
		}
//		else if(mode == 2) {	// 빨간 망치
//			if (dir == -1) {
//				if (aniIndex >= resource.prLeftRedAct.length) {
//					aniIndex = 0;
//					actAniState = 0;
//					drawMove(g);
//					return;
//				}
//				g.drawImage(resource.prLeftRedAct[aniIndex], posX, posY, null);
//			} else if (dir == 1) {
//				if (aniIndex >= resource.prRightRedAct.length) {
//					aniIndex = 0;
//					actAniState = 0;
//					drawMove(g);
//					return;
//				}
//				g.drawImage(resource.prRightRedAct[aniIndex], posX, posY, null);
//			}
//		}
		else if(mode == 3) {	// 무적 망치
			if (dir == -1) {
				if (aniIndex >= resource.prLeftAbsAct.length) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prLeftAbsAct[aniIndex], posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= resource.prLeftAbsAct.length) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				// 2012.05.17 YJY 무적일때는 Left 이미지만 가지고 사용하도록 수정함.
				//g.drawImage(resource.prRightAbsAct[aniIndex], posX, posY, null);
				int ww = resource.prLeftAbsAct[aniIndex].getWidth(null);
				int hh = resource.prLeftAbsAct[aniIndex].getHeight(null);
				drawImageHR(g, resource.prLeftAbsAct[aniIndex], posX, posY, 
						0, 0, ww, hh, null);
			}
		}
	}
	
	public void drawDmg(Graphics2D g) {
		if (mode == 0 || mode == 1 || mode == 2) { // 일반 상태    // 빨간 망치 (mode 2) 추가
			if (dir == -1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prLeftDmgN, posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prRightDmgN, posX, posY, null);
			}
		}
//		else if(mode == 2) {	// 빨간 망치
//			if (dir == -1) {
//				if (aniIndex >= DMG_TIME) {
//					aniIndex = 0;
//					actAniState = 0;
//					drawMove(g);
//					return;
//				}
//				g.drawImage(resource.prLeftRedDmgN, posX, posY, null);
//			} else if (dir == 1) {
//				if (aniIndex >= DMG_TIME) {
//					aniIndex = 0;
//					actAniState = 0;
//					drawMove(g);
//					return;
//				}
//				g.drawImage(resource.prRightRedDmgN, posX, posY, null);
//			}
//		}
		else if(mode == 3) {	// 무적 망치
			if (dir == -1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prLeftAbsDmgN, posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				//g.drawImage(resource.prRightAbsDmgN, posX, posY, null);
				int ww = resource.prLeftAbsDmgN.getWidth(null);
				int hh = resource.prLeftAbsDmgN.getHeight(null);
				drawImageHR(g, resource.prLeftAbsDmgN, posX, posY, 
						0, 0, ww, hh, null);
			}
		}
	}
	
	public void drawDmgE(Graphics2D g) {
		if (mode == 0 || mode == 1 || mode == 2) { // 일반 상태   // 빨간 망치 (mode 2) 추가
			if (dir == -1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prLeftDmgE, posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prRightDmgE, posX, posY, null);
			}
		}
//		else if(mode == 2) {	// 빨간 망치
//			if (dir == -1) {
//				if (aniIndex >= DMG_TIME) {
//					aniIndex = 0;
//					actAniState = 0;
//					drawMove(g);
//					return;
//				}
//				g.drawImage(resource.prLeftRedDmgE, posX, posY, null);
//			} else if (dir == 1) {
//				if (aniIndex >= DMG_TIME) {
//					aniIndex = 0;
//					actAniState = 0;
//					drawMove(g);
//					return;
//				}
//				g.drawImage(resource.prRightRedDmgE, posX, posY, null);
//			}
//		}
		else if(mode == 3) {	// 무적 망치
			if (dir == -1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				g.drawImage(resource.prLeftAbsDmgE, posX, posY, null);
			} else if (dir == 1) {
				if (aniIndex >= DMG_TIME) {
					aniIndex = 0;
					actAniState = 0;
					drawMove(g);
					return;
				}
				//g.drawImage(resource.prRightAbsDmgE, posX, posY, null);
				int ww = resource.prLeftAbsDmgE.getWidth(null);
				int hh = resource.prLeftAbsDmgE.getHeight(null);
				drawImageHR(g, resource.prLeftAbsDmgE, posX, posY, 
						0, 0, ww, hh, null);
			}
		}
	}

	public boolean isKeyInput() {
		// System.out.println("Pororo aniState : " + aniState);
		return true;
	}

	public void keyEvent(int event) {
		// System.out.println("Pororo aniState : " + aniState);
		// System.out.println("pr width:" +
		// resource.prRightMove[0].getWidth(null));
		// System.out.println("pr height:" +
		// resource.prRightMove[0].getHeight(null));

		switch (event) {
		case HRcEvent.VK_UP:
			aniState = PORORO_UP_MOVE;
			break;
		case HRcEvent.VK_DOWN:
			aniState = PORORO_DOWN_MOVE;
			break;
		case HRcEvent.VK_LEFT:
			aniState = PORORO_LEFT_MOVE;
			break;
		case HRcEvent.VK_RIGHT:
			aniState = PORORO_RIGHT_MOVE;
			break;
		case Constant.KEY_PREV:
			break;
		case HRcEvent.VK_ENTER:
			break;
		}
//		isKeyControll = true;
	}

	public void process() {
		aniIndex++;
		if(modeGuage > 0) modeGuage -= 5;

		switch (aniState) {
		case PORORO_UP_MOVE:
//			posY -= speedY;
			setPosY(-1);
			if (posY <= boundArea.y) {
				posY = boundArea.y;
				centerX = posX + (int)(width/2);
				centerY = posY + (int)(height/2);
				aniState = PORORO_UP_IDEL;
			}
			break;
		case PORORO_DOWN_MOVE:
			// 540 - 37 - 162 = 540 - 199 = 341
//			posY += speedY;
			setPosY(1);
			if (posY >= (boundArea.y + boundArea.height - 142)) { // 162
				posY = (boundArea.y + boundArea.height - 142); // 341;
				centerX = posX + (int)(width/2);
				centerY = posY + (int)(height/2);
				aniState = PORORO_DOWN_IDEL;
			}
			break;
		case PORORO_LEFT_MOVE:
//			posX -= speedX;
			setPosX(-1);
			dir = -1;
			if (posX <= boundArea.x) {
				posX = boundArea.x;
				centerX = posX + (int)(width/2);
				centerY = posY + (int)(height/2);
				aniState = PORORO_LEFT_IDEL;
			}
			break;
		case PORORO_RIGHT_MOVE:
//			posX += speedX;
			setPosX(1);
			dir = 1;
			// 960, 540
			// 960-46-158 = 960-204 = 756
			if (posX >= (boundArea.x + boundArea.width - 158)) {
				posX = (boundArea.x + boundArea.width - 158); // 756;
				centerX = posX + (int)(width/2);
				centerY = posY + (int)(height/2);
				aniState = PORORO_RIGHT_IDEL;
			}
			break;
		case PORORO_GAMEOVER:
			posY -= speedY;
			speedY += 5;
			
			if (posY <= -200) {
				posY = -200;
			}
			if(speedY > 50) {
				speedY = 50;
			}
			
			break;
		}
	}

	public void setAnimation(int num) {
		switch (num) {
		case 0: // Action
			if (actAniState != PORORO_ACT) {
				actAniState = PORORO_ACT;
				aniIndex = 0;
			}
			break;
		case 1:	// 데미지 일반
			if (actAniState != PORORO_DMG) {
				actAniState = PORORO_DMG;
				aniIndex = 0;
				Resource.getInstance().sound.playSound(Resource.SOUND_BAD0);
			}
			break;
		case 2: // 데미지 전기
			if (actAniState != PORORO_DMG_E) {
				actAniState = PORORO_DMG_E;
				aniIndex = 0;
				Resource.getInstance().sound.playSound(Resource.SOUND_BAD1);
			}
			break;
		case 3:
			if (actAniState != PORORO_GAMEOVER) {
				actAniState = PORORO_GAMEOVER;
				aniState = PORORO_GAMEOVER;
				speedY = 5;
				aniIndex = 0;
				modeGuage = 0;
//				isKeyControll = false;
			}
			break;
		}
	}

	public void pause() {
	}

	public void reStart() {
	}
	public int getMode() {
		return this.mode;
	}
	
	public void setMode(int mod) {
		switch(mod) {
		case 0:
			guage += 40;
			modeGuage = 0;
			mode = 1;
			speedX = speedLevel[mod][2];
			speedY = speedLevel[mod][0];
			break;
		case 1:
			mode = 2;
			modeGuage = 705;
			speedX = speedLevel[mod][2];
			speedY = speedLevel[mod][0];
			break;
		case 2:
			mode = 3;
			modeGuage = 705;
			speedX = speedLevel[mod][2];
			speedY = speedLevel[mod][0];
			break;
		default:
			mode = 1;
			modeGuage = 0;
			speedX = speedLevel[0][2];
			speedY = speedLevel[0][0];
			break;
		}
	}
	
	public int getPosX() {
		return this.posX;
	}
	public void setPosX(int dir) {
		if(dir == 1) {
			posX += speedX;
			centerX = posX + (int)(width/2);
			centerY = posY + (int)(height/2);
		}
		else if(dir == -1) {
			posX -= speedX;
			centerX = posX + (int)(width/2);
			centerY = posY + (int)(height/2);
		}
	}

	public int getPosY() {
		return this.posY;
	}
	public void setPosY(int dir) {
		if(dir == 1) {
			posY += speedY;
			centerX = posX + (int)(width/2);
			centerY = posY + (int)(height/2);
		}
		else if(dir == -1) {
			posY -= speedY;
			centerX = posX + (int)(width/2);
			centerY = posY + (int)(height/2);
		}
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int num) {
		grade += num;
//		System.out.println("grade:"+grade);
	}
	public int getGuage() {
		return guage;
	}
	public void setGuage(int num) {
		guage += num;
//		System.out.println("guage:"+guage);
		if(guage >= 200) guage = 200;
		else if(guage <= 0) guage = 0;
	}
	
	public void start() {
		aniIndex = 0;
		actAniState = 0;

		aniState = PORORO_DOWN_IDEL;
		
		posX = 389;
		posY = 185;
		width = resource.prLeftMove[0].getWidth(null);
		height = resource.prLeftMove[0].getHeight(null);
		centerX = posX + (int)(width/2);
		centerY = posY + (int)(height/2);
		
		speedX = 20;
		speedY = 17;
		mode = 1;
		grade = 0;
		guage = 200;
		modeGuage = 0;

		dir = 1;

//		isKeyControll = true;

		cont.start(200);
	}
	
	public void introMove() {
		aniIndex++;
		posX += speedX;
		if (posX >= 389) {
			posX = 389; // 756;
			aniState = PORORO_DOWN_IDEL;
		}
	}
	public void intro() {
		aniIndex = 0;
		actAniState = 0;
		aniState = PORORO_RIGHT_MOVE;
		posX = -200;
		posY = 185;
//		posX = 389;
//		posY = 185;
		width = resource.prLeftMove[0].getWidth(null);
		height = resource.prLeftMove[0].getHeight(null);
		
//		System.out.println("pororo x:"+posX + ", y:"+posY+", centerX:"+centerX+", centerY:"+centerY);
//		System.out.println("pororo width:"+width+", height:"+height);

		speedX = 20;
		speedY = 17;
		mode = 1;
		grade = 0;
		guage = 0;
		modeGuage = 0;

		dir = 1;
	}
}
