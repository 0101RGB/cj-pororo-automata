package pororo.com.game.fishing.object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.image.ImageObserver;

import pororo.com.game.fishing.Resource;

public class Monster extends GameObject {
//	int state; // view state // 0:ready, 1:working, 2:finish
	private int type; // 물고기 타입 // 0:safe, 1:bad
	public int kind; // 물고기 종류
	/*
	 * 0: 해파리 [safe] 1: 노란물고기 [safe] 2: 빨간가재 [safe] 3: 연두물고기 [safe] 4: 청새치
	 * [safe] 5: 개복치 [safe]
	 * 
	 * 6: 복어 [bad] 7: 전기뱀장어 [bad] 8: 물뱀 [bad] 9: 성게 [bad] 10: 상어 [bad]
	 */
	private int speedX; // 속도(레벨에 따라 상이)
	private int speedY; // 속도(레벨에 따라 상이)
	private int ai; // 인공지능(회피 확률)
	private boolean ai_flag = true;
//	private int animation; // 
	private int dir;
	private int dirY;
//	private Resource resource = Resource.getInstance();
	private static final Random rnd = new Random();

	private static final int MONSTER_IDLE = 0;
	private static final int MONSTER_MOVE = 1;
	private static final int MONSTER_ACT = 2;
	private static final int MONSTER_SUCCESS = 3;

	private int cnt = 0;
	private int actCnt = 0;
	private int posX;
	private int posY;
	private int centerX;
	private int centerY;
	private int moveX;
	private int moveY;

	private int aniState = MONSTER_IDLE;
	private int aniIndex = 0;
	public int startTime;
	private int width;
	private int height;
	private int monLevel;
	private int actGoalX = 70;
	private int actGoalY = 30;
	
	private int startPos[][] = {	// line: 4
			{ -60, 158, 1 }, // posX, posY, dir
			{ -60, 254, 1 }, 
			{ -60, 351, 1 }, 
			{ -60, 448, 1 }, 
			{ 970, 158, -1 }, 
			{ 970, 254, -1 },
			{ 970, 351, -1 }, 
			{ 970, 448, -1 } 
			};
	
	// 레벨 시간
	private int levelCnt[] = { 3, 19, 34, 49, 64, 79, 94, 119, 139, 169, 209, 259, 319 };
	// 레벨에 따른 착한 몬스터 출연 비율
	private int levelType[] = { 0, 100, 90, 80, 70, 60, 55, 50, 45, 35, 25, 15, 0, 0 };
	// 레벨별 몬스터 속도
	private int speedLev[][] = {
			{ 10, 10, 10, 11, 12, 13, 14, 15, 16, 17, 17, 17, 17, 18 },
			{ 12, 12, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19, 20 },
			{ 15, 15, 15, 15, 16, 17, 18, 19, 20, 21, 21, 21, 21, 22 },
			{ 19, 19, 19, 19, 19, 19, 20, 21, 22, 23, 23, 23, 23, 24 },
			{ 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 },
			{ 13, 13, 13, 13, 13, 16, 17, 18, 19, 20, 20, 20, 20, 22 },
			{ 11, 11, 11, 12, 13, 14, 15, 16, 17, 18, 21, 24, 27, 30 },	// 복어
			{ 15, 15, 15, 15, 16, 17, 18, 19, 20, 21, 24, 27, 30, 33 },	// 전기뱀장어
			{ 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 27, 30, 33, 36 },	// 물뱀
			{ 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 16, 19, 22},	// 성게
			{ 19, 19, 19, 19, 19, 19, 19, 19, 19, 21, 24, 27, 30, 33 } };	// 상어

	// 에너지 게이지 추가
	private int guage[][] = { { 0, 0 }, // 해파리 -- 점수 물고기 --
			{ 1, 1 }, // 노란 물고기
			{ 1, 1 }, // 빨간 가재
			{ 2, 2 }, // 연두색 물고기
			{ 4, 4 }, // 청새치
			{ 10, 10 }, // 개복치
			{ -3, 2 }, // 복어 -- 방해 물고기 --
			{ -10, 3 }, // 전기 뱀장어
			{ -15, 3 }, // 물뱀
			{ -4, 4 }, // 성게
			{ -25, 5 } // 상어
	};

	// 점수
	private int grade[] = { 20, 50, 100, 200, 500, 1000, 200, 300, 300, 200, 200 };

	// 인공지능
	private int aiValue[] = { 0, 5, 7, 10, 0, 20, 40, 40, 0, 0, 0 };

	public void destroy() {
	}

	// my favorite drawing function.. / horizontal reverse
    private void drawImageHR(Graphics2D g, Image img, int x, int y, int crx, int cry, int w, int h, ImageObserver ob) {
		g.drawImage(img, x, y, x+w, y+h, crx+w-1, cry, crx, cry+h, ob);
	}
	
	public void draw(Graphics2D g) {
		switch (aniState) {
		case MONSTER_IDLE:
			break;
		case MONSTER_MOVE:
			if (kind == 0) {
				if (aniIndex >= Resource.getInstance().monLeftSafeA.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeA[aniIndex], posX, posY,	null);
					int ww = Resource.getInstance().monLeftSafeA[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeA[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeA[aniIndex], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeA[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 1) {
				if (aniIndex >= Resource.getInstance().monLeftSafeB.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeB[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeB[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeB[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeB[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeB[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 2) {
				if (aniIndex >= Resource.getInstance().monLeftSafeC.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeC[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeC[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeC[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeC[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeC[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 3) {
				if (aniIndex >= Resource.getInstance().monLeftSafeD.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeD[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeD[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeD[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeD[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeD[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 4) {
				if (aniIndex >= Resource.getInstance().monLeftSafeE.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeE[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeE[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeE[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeE[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeE[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 5) {
				if (aniIndex >= Resource.getInstance().monLeftSafeF.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeF[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeF[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeF[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeF[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeF[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 6) { // bad fish
				if (aniIndex >= Resource.getInstance().monLeftBadA.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadA[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadA[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftBadA[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadA[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadA[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 7) {
				if (aniIndex >= Resource.getInstance().monLeftBadB.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadB[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadB[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftBadB[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadB[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadB[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 8) {
				if (aniIndex >= Resource.getInstance().monLeftBadC.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadC[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadC[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftBadC[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadC[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadC[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 9) {
				if (aniIndex >= Resource.getInstance().monLeftBadD.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadD[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadD[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftBadD[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadD[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadD[aniIndex], posX, posY,
							null);
				}
			} else if (kind == 10) { // 상어
				if (aniIndex >= Resource.getInstance().monLeftBadE.length)
					aniIndex = 1;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadE[aniIndex], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadE[aniIndex].getWidth(null);
					int hh = Resource.getInstance().monLeftBadE[aniIndex].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadE[aniIndex], posX, posY, 
							0, 0, ww, hh, null);
					
				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadE[aniIndex], posX, posY,
							null);
				}
			}
			// g.drawImage(Resource.getInstance().crCheering[aniIndex], posX, posY, null);
			break;
		case MONSTER_ACT:
			float alpha = (float)0.5F - actCnt/30;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					alpha));
			if (kind == 0) {
				if (aniIndex >= Resource.getInstance().monLeftSafeA.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeA[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeA[0].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeA[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeA[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeA[0], posX, posY, null);
				}
			} else if (kind == 1) {
				if (aniIndex >= Resource.getInstance().monLeftSafeB.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeB[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeB[0].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeB[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeB[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeB[0], posX, posY, null);
				}
			} else if (kind == 2) {
				if (aniIndex >= Resource.getInstance().monLeftSafeC.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeC[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeC[0].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeC[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeC[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeC[0], posX, posY, null);
				}
			} else if (kind == 3) {
				if (aniIndex >= Resource.getInstance().monLeftSafeD.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeD[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeD[0].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeD[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeD[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeD[0], posX, posY, null);
				}
			} else if (kind == 4) {
				if (aniIndex >= Resource.getInstance().monLeftSafeE.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeE[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeE[0].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeE[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeE[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeE[0], posX, posY, null);
				}
			} else if (kind == 5) {
				if (aniIndex >= Resource.getInstance().monLeftSafeF.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightSafeF[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftSafeF[0].getWidth(null);
					int hh = Resource.getInstance().monLeftSafeF[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftSafeF[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftSafeF[0], posX, posY, null);
				}
			} else if (kind == 6) { // bad fish
				if (aniIndex >= Resource.getInstance().monLeftBadA.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadA[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadA[0].getWidth(null);
					int hh = Resource.getInstance().monLeftBadA[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadA[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadA[0], posX, posY, null);
				}
			} else if (kind == 7) {
				if (aniIndex >= Resource.getInstance().monLeftBadB.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadB[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadB[0].getWidth(null);
					int hh = Resource.getInstance().monLeftBadB[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadB[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadB[0], posX, posY, null);
				}
			} else if (kind == 8) {
				if (aniIndex >= Resource.getInstance().monLeftBadC.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadC[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadC[0].getWidth(null);
					int hh = Resource.getInstance().monLeftBadC[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadC[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadC[0], posX, posY, null);
				}
			} else if (kind == 9) {
				if (aniIndex >= Resource.getInstance().monLeftBadD.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadD[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadD[0].getWidth(null);
					int hh = Resource.getInstance().monLeftBadD[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadD[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadD[0], posX, posY, null);
				}
			} else if (kind == 10) { // 상어
				if (aniIndex >= Resource.getInstance().monLeftBadE.length)
					aniIndex = 0;
				if (dir > 0) {
//					g.drawImage(Resource.getInstance().monRightBadE[0], posX, posY, null);
					int ww = Resource.getInstance().monLeftBadE[0].getWidth(null);
					int hh = Resource.getInstance().monLeftBadE[0].getHeight(null);
					drawImageHR(g, Resource.getInstance().monLeftBadE[0], posX, posY, 
							0, 0, ww, hh, null);

				} else if (dir < 0) {
					g.drawImage(Resource.getInstance().monLeftBadE[0], posX, posY, null);
				}
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1));
			
			if(actCnt > 10) {
				aniState = MONSTER_SUCCESS;
				actCnt = 0;
			}
			// g.drawImage(Resource.getInstance().crGameover[aniIndex], posX, posY, null);
			break;
		case MONSTER_SUCCESS:
			// g.drawImage(Resource.getInstance().crSuccess, posX, posY, null);
			break;
		}
	}

	public boolean isKeyInput() {
		return false;
	}

	public void keyEvent(int event) {
	}

	public void process() {
		aniIndex++;
		cnt++;
		switch (aniState) {
		case MONSTER_IDLE:
			// cnt++;
			if (startTime > 0 && cnt > startTime) {
				aniState = MONSTER_MOVE;
			}
			break;
		case MONSTER_MOVE:
			// posX += dir*speedX;
			setPosX();

			if (posX <= ((-10)-width)) {
				aniState = MONSTER_SUCCESS;
				// dir = dir * -1;
			} else if (posX >= 1000) {
				aniState = MONSTER_SUCCESS;
				// dir = dir * -1;
			} else if (posY >= 550) {
				aniState = MONSTER_SUCCESS;
			}
			break;
		case MONSTER_ACT:
			actCnt++;
			
//			if(actCnt < 5) {
//				posX += dir * (speedX + (int)(speedX/5) - actCnt);
//				posY -= speedY - (speedY - (int)(speedY/5) - actCnt);
//				if(posY < actGoalY-10) {
//					posY = actGoalY;
//				}
//				if(posX < actGoalX-10) {
//					posX = actGoalX;
//				}
//			}
//			else {
				posX += dir * speedX;
				posY -= speedY;
				if(posY < actGoalY-10) {
					posY = actGoalY;
				}
				if(posX < actGoalX-10) {
					posX = actGoalX;
				}
//			}
			
			break;
		case MONSTER_SUCCESS:
			posX = (-10)-width;
			posY = 100;
			centerX = 0;
			centerY = 0;
			aniIndex = 0;
			break;
		}
	}

	public void setPosX() {
		if (kind == 9) {
			if (cnt % 10 < 5) {
				posX += (int) (speedX / 2);
				posY += (int) (speedX / 2);
				centerX = posX + (int) (width / 2);
				centerY = posY + (int) (height / 2);
			} else {
				posX -= (int) (speedX / 2);
				posY += (int) (speedX / 2);
				centerX = posX + (int) (width / 2);
				centerY = posY + (int) (height / 2);
			}
		} else if (kind == 10) {
			posX += dir * speedX;
			posY += dirY * speedY;
			centerX = posX + (int) (width / 2);
			centerY = posY + (int) (height / 2);
		} else {
			posX += dir * speedX;
			centerX = posX + (int) (width / 2);
			centerY = posY + (int) (height / 2);
		}
		increaseAi();
	}

	public void increaseAi() {
		if (kind == 2 || kind == 3 || kind == 5 || kind == 6 || kind == 7) {
			ai += 2;
			if (ai > aiValue[kind] + monLevel) {
				ai = aiValue[kind] + monLevel;
			}
		}
	}

	public int checkAi(int x, int y, int wid, int mode) {
		switch (mode) {
		case 1: // 일반 상태
			if (kind < 6) {
				double r12 = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
				if(r12 < ((wid+height+20)/2)) {
					if (randomPos(100) <= ai) {
						dir = dir * -1;
						ai_flag = false;
					}
				}
			}
			break;
		case 2: // 빨간 망치
//			if (kind != 7 && kind != 9 && kind != 10) {
			if (kind < 7) {
				double r12 = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
				if(r12 < ((wid+height+20)/2)) {
					if (randomPos(100) <= ai) {
						dir = dir * -1;
						ai_flag = false;
					}
				}
			}
			break;
		case 3: // 무적 망치
			double r12 = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
			if(r12 < ((wid+height+20)/2)) {
				// if( randomPos(100) <= ai) {
				dir = dir * -1;
				ai_flag = false;
				// }
			}
			break;
		}
		return 0;
	}

	public boolean colCheck(int x, int y, int mode) {
		// System.out.println("거리:" + (Math.sqrt(Math.pow(Math.abs(x -
		// centerX),2)+ Math.pow(Math.abs(y - centerY),2))) );
		// if(Math.sqrt(Math.pow(Math.abs(x - centerX),2)+ Math.pow(Math.abs(y -
		// centerY),2)) < ((width/2)+ 60)) {
		// return true;
		// }
		if (kind == 10) { // 상어 이동 타겟 좌표
			if (aniState == MONSTER_IDLE) {
				if (cnt == startTime) {
					moveX = (int) (Math.abs(x - posX) / speedX);
					speedY = (int) (Math.abs(y - posY) / moveX);
					moveX = x;
					moveY = y;
					if (y < posY)
						dirY = -1;

				}
			}
		}
		
		if (ai_flag) {
			checkAi(x, y, 100, mode);
		}

		if (posX >= x + 10 && posX <= x + 120) {
			if (posY >= y + 10 && posY <= y + 100) {
				return true;
			}
		}

		return false;
	}

	public boolean collisionCheck(int x, int y, int wid, int mode) {
		
		if (kind == 10) { // 상어 이동 타겟 좌표
			if (aniState == MONSTER_IDLE) {
				if (cnt == startTime) {
					moveX = (int) (Math.abs(x - posX) / speedX);
					speedY = (int) (Math.abs(y - posY) / moveX);
					moveX = x;
					moveY = y;
					if (y < posY)
						dirY = -1;

				}
			}
		}
		if (ai_flag) {
			checkAi(x, y, wid, mode);
		}
		double r12 = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
//		System.out.println("Point1("+x+","+y+"), Point2("+centerX+","+centerY+") : " + r12);
		if(r12 < ((wid-30+height-40)/2)) {
			return true;
		}
		return false;
	}

	public void setAnimation(int State) {
		switch (State) {
		case 0: // 잡혔을때 액션
			MoveActionVec();
			aniState = MONSTER_ACT;
			break;
		case 1:
			break;

		}
		// this.aniState = aniState;
		aniIndex = 1;
	}

	public int getGuage(int flag) { // 0:방해 점수, 1:잡은 점수
		if (flag == 1) {
			return guage[kind][1];
		} else {
			return guage[kind][0];
		}
	}

	public int getGrade(int flag) { // 0:방해 점수, 1:잡은 점수
		return grade[kind];
	}

	public void pause() {
	}

	public boolean isWorking() {
		if (aniState == MONSTER_SUCCESS) {
			return false;
		} else {
			return true;
		}
	}

	public void reStart() {
	}
	
	public void MoveActionVec() {
		speedX = (int) (Math.abs(actGoalX - posX) / 10);
		speedY = (int) (Math.abs(actGoalY - posY) / 10);
		
		centerX = 1100;
		centerY = -100;
		if(actGoalX - posX < 0) dir = -1;
		else dir = 1;
	}

	public boolean setPosition() {
		System.out.println("setPosition:" + startPos.length);
		int pos = randomPos(startPos.length); // start position
		if (pos < startPos.length && pos >= 0) {
			posX = startPos[pos][0];
			if(posX < 0) {
				posX = startPos[pos][0] - width + 50;
			}
			centerX = posX + (int) (width / 2);
			posY = startPos[pos][1] - (int) (height / 2);
			centerY = posY + (int) (height / 2);
			dir = startPos[pos][2];
			
			return true;
		}
		return false;
	}

	public int randomPos(int scope) {
		return (Math.abs(rnd.nextInt()) % scope + 1);
	}

	public int timeToLevel(int time) {
		int typeLevel = 0;

		if (time < (levelCnt[levelCnt.length - 1] * 10)) {
			for (int i = 0; i < levelCnt.length - 1; i++) {
				if (time >= levelCnt[i] * 10 && time < levelCnt[i + 1] * 10)
					typeLevel = i;
			}
		} else {
			typeLevel = 13;
		}

		return typeLevel;
	}

	public int monsterType(int lev) {
		if (randomPos(100) < levelType[lev])
			return 0;
		else
			return 1;
	}

	public int monsterKind(int lev) {
		int res = 0;
		int ranNum = randomPos(100);
		if (type == 0) {
			if (lev == 0) {
				res = 0;
			} else if (lev == 1) {
				res = 0;
			} else if (lev == 2) {
				if (ranNum < 60)
					res = 0;
				else
					res = 1;
			} else if (lev == 3) {
				if (ranNum < 30)
					res = 0;
				else if (ranNum >= 30 && ranNum < 70)
					res = 1;
				else
					res = 2;
			} else if (lev == 4) {
				if (ranNum < 20)
					res = 0;
				else if (ranNum >= 20 && ranNum < 60)
					res = 1;
				else
					res = 2;
			} else if (lev == 5) {
				if (ranNum < 10)
					res = 0;
				else if (ranNum >= 10 && ranNum < 40)
					res = 1;
				else if (ranNum >= 40 && ranNum < 80)
					res = 2;
				else
					res = 3;
			} else if (lev == 6) {
				if (ranNum < 5)
					res = 0;
				else if (ranNum >= 5 && ranNum < 30)
					res = 1;
				else if (ranNum >= 30 && ranNum < 60)
					res = 2;
				else
					res = 3;
			} else if (lev == 7) {
				if (ranNum < 5)
					res = 0;
				else if (ranNum >= 5 && ranNum < 20)
					res = 1;
				else if (ranNum >= 20 && ranNum < 50)
					res = 2;
				else
					res = 3;
			} else if (lev == 8) {
				if (ranNum < 15)
					res = 1;
				else if (ranNum >= 15 && ranNum < 50)
					res = 2;
				else
					res = 3;
			} else {
				if (ranNum < 15)
					res = 1;
				else if (ranNum >= 15 && ranNum < 50)
					res = 2;
				else
					res = 3;
			}

		} else if (type == 1) {
			if (lev == 0) {
				res = 0;
			} else if (lev == 1) {
				res = 0;
			} else if (lev == 2) {
				res = 6;
			} else if (lev == 3) {
				if (ranNum < 80)
					res = 6;
				else
					res = 7;
			} else if (lev == 4) {
				if (ranNum < 60)
					res = 6;
				else if (ranNum >= 60 && ranNum < 90)
					res = 7;
				else
					res = 8;
			} else if (lev == 5) {
				if (ranNum < 40)
					res = 6;
				else if (ranNum >= 40 && ranNum < 80)
					res = 7;
				else if (ranNum >= 80 && ranNum < 90)
					res = 8;
				else
					res = 9;
			} else if (lev == 6) {
				if (ranNum < 30)
					res = 6;
				else if (ranNum >= 30 && ranNum < 60)
					res = 7;
				else if (ranNum >= 60 && ranNum < 75)
					res = 8;
				else if (ranNum >= 75 && ranNum < 90)
					res = 9;
				else
					res = 10;
			} else if (lev == 7) {
				if (ranNum < 20)
					res = 6;
				else if (ranNum >= 20 && ranNum < 45)
					res = 7;
				else if (ranNum >= 45 && ranNum < 60)
					res = 8;
				else if (ranNum >= 60 && ranNum < 80)
					res = 9;
				else
					res = 10;
			} else if (lev == 8) {
				if (ranNum < 20)
					res = 6;
				else if (ranNum >= 20 && ranNum < 40)
					res = 7;
				else if (ranNum >= 40 && ranNum < 55)
					res = 8;
				else if (ranNum >= 55 && ranNum < 75)
					res = 9;
				else
					res = 10;
			} else {
				if (ranNum < 20)
					res = 6;
				else if (ranNum >= 20 && ranNum < 40)
					res = 7;
				else if (ranNum >= 40 && ranNum < 55)
					res = 8;
				else if (ranNum >= 55 && ranNum < 75)
					res = 9;
				else
					res = 10;
			}
		}
		return res;
	}

	public int getWidth() {
		int result = 0;
		if (kind == 0) {
			result = Resource.getInstance().monLeftSafeA[0].getWidth(null);
		} else if (kind == 1) {
			result = Resource.getInstance().monLeftSafeB[0].getWidth(null);
		} else if (kind == 2) {
			result = Resource.getInstance().monLeftSafeC[0].getWidth(null);
		} else if (kind == 3) {
			result = Resource.getInstance().monLeftSafeD[0].getWidth(null);
		} else if (kind == 4) {
			result = Resource.getInstance().monLeftSafeE[0].getWidth(null);
		} else if (kind == 5) {
			result = Resource.getInstance().monLeftSafeF[0].getWidth(null);
		} else if (kind == 6) { // bad fish
			result = Resource.getInstance().monLeftBadA[0].getWidth(null);
		} else if (kind == 7) {
			result = Resource.getInstance().monLeftBadB[0].getWidth(null);
		} else if (kind == 8) {
			result = Resource.getInstance().monLeftBadC[0].getWidth(null);
		} else if (kind == 9) {
			result = Resource.getInstance().monLeftBadD[0].getWidth(null);
		} else if (kind == 10) { // 상어
			result = Resource.getInstance().monLeftBadE[0].getWidth(null);
		}
		return result;
	}

	public int getHeight() {
		int result = 0;
		if (kind == 0) {
			result = Resource.getInstance().monLeftSafeA[0].getHeight(null);
		} else if (kind == 1) {
			result = Resource.getInstance().monLeftSafeB[0].getHeight(null);
		} else if (kind == 2) {
			result = Resource.getInstance().monLeftSafeC[0].getHeight(null);
		} else if (kind == 3) {
			result = Resource.getInstance().monLeftSafeD[0].getHeight(null);
		} else if (kind == 4) {
			result = Resource.getInstance().monLeftSafeE[0].getHeight(null);
		} else if (kind == 5) {
			result = Resource.getInstance().monLeftSafeF[0].getHeight(null);
		} else if (kind == 6) { // bad fish
			result = Resource.getInstance().monLeftBadA[0].getHeight(null);
		} else if (kind == 7) {
			result = Resource.getInstance().monLeftBadB[0].getHeight(null);
		} else if (kind == 8) {
			result = Resource.getInstance().monLeftBadC[0].getHeight(null);
		} else if (kind == 9) {
			result = Resource.getInstance().monLeftBadD[0].getHeight(null);
		} else if (kind == 10) { // 상어
			result = Resource.getInstance().monLeftBadE[0].getHeight(null);
		}
		return result;
	}

	public void start() {
		aniIndex = 1;
		startTime = 100;
		type = 0;
		kind = 0;
		aniState = MONSTER_IDLE;

		width = getWidth();
		height = getHeight();

		if (!setPosition()) {
			posX = 961;
			posY = 333;
			centerX = 1000;
			centerY = 350;
			dir = -1;
		}
		speedX = speedLev[kind][0];
		cnt = 0;
		actCnt = 0;
	}

	public void start(int sTime) {
		aniIndex = 1;

		monLevel = timeToLevel(sTime);
		type = monsterType(monLevel);
		kind = monsterKind(monLevel);

		startTime = sTime;
		aniState = MONSTER_IDLE;
		ai_flag = true;
		ai = aiValue[kind];

		width = getWidth();
		height = getHeight();

		if (kind == 9) {
			posX = 50 + randomPos(800);
			posY = -100;
			centerX = posX + (int) (width / 2);
			centerY = posY + (int) (height / 2);
			dir = 1;
		} else {
			if (!setPosition()) {
				posX = 961;
				posY = 333;
				centerX = 1000;
				centerY = 350;
				dir = -1;
			}
		}
		if (kind == 10) {
			speedY = 0;
			dirY = 1;
		}
		moveX = 0;
		moveY = 0;
		
		actCnt = 0;

		speedX = speedLev[kind][monLevel];
		// cnt = 0;
	}

	public void startSp(int sType, int sTime) {
		aniIndex = 1;

		monLevel = timeToLevel(sTime);
		type = 0;

		if (sType < 5) {
			kind = 4;
		} else {
			kind = 5;
		}

		startTime = sTime;
		aniState = MONSTER_IDLE;
		ai_flag = true;
		ai = aiValue[kind];

		width = getWidth();
		height = getHeight();

		if (!setPosition()) {
			posX = 961;
			posY = 333;
			centerX = 1000;
			centerY = 350;
			dir = -1;
		}

		moveX = 0;
		moveY = 0;
		
		actCnt = 0;

		speedX = speedLev[kind][monLevel];
		// cnt = 0;
	}

	public void setData() {
		aniIndex = 0;
		monLevel = 0;

		startTime = 100;
		aniState = MONSTER_IDLE;
		ai_flag = true;

		moveX = 0;
		moveY = 0;
		
		actCnt = 0;

		cnt = 0;
	}
}
