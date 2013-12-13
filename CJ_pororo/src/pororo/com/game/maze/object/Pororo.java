package pororo.com.game.maze.object;

import java.awt.Graphics2D;

import org.havi.ui.event.HRcEvent;

import pororo.com.framework.TimeController;
import pororo.com.game.maze.GameData;
import pororo.com.game.maze.ImageDraw;
import pororo.com.game.maze.Listener;
import pororo.com.game.maze.MapData;
import pororo.com.game.maze.MazeGPlay;
import pororo.com.game.maze.Resource;

public class Pororo extends GameObject {
	public static final int PORORO_DOWN_IDEL = 0;
	public static final int PORORO_DOWN_MOVE = 1;
	public static final int PORORO_UP_IDEL = 2;
	public static final int PORORO_UP_MOVE = 3;
	public static final int PORORO_LEFT_IDEL = 4;
	public static final int PORORO_LEFT_MOVE = 5;
	public static final int PORORO_RIGHT_IDEL = 6;
	public static final int PORORO_RIGHT_MOVE = 7;
	public static final int PORORO_HAVE = 8;
	public static final int PORORO_GOOD = 9;
	public static final int PORORO_BAD = 10;
	public static final int PORORO_ICE = 11;
	public static final int PORORO_ICE_SUB = 12;
	public static final int PORORO_GAMEOVER = 13;
	public static final int PORORO_GAMEOVER_END = 14;
	public static final int PORORO_SUCCESS = 15;
	public static final int PORORO_FAILED = 16;
	public static final int PORORO_CHECK = 17;

	// 뽀로로 에니메이션 정의
	private int orderIdel[] = { 0, 1, 0, 1, 0, 1, 0, 1, 2, 3 };
	private int orderIdelSub[] = { 0, 1 };
	private int orderMove[] = { 1, 0, 2, 0 };

	private int orderSuccess[] = { 0, 1, 0, 2, 3, 4, 3, 2 };

	private int orderHave[] = { 0, 3, 0, 4 };
	private int orderGood[] = { 0, 1, 2, 3, 4 };
	private int orderBad[] = { 0, 1, 2, 3, 4 };

	private int orderIce[] = { 0, 1, 2, 3 };
	private int orderIceSub[] = { 4, 5 };
	private int orderGameover[] = { 0, 1, 2, 3, 4, 5, 4, 6 };
	private int orderGameoverEnd[] = { 4, 7, 8 };

	private Resource resource = Resource.getInstance();

	// 따라 다니는 아이템
	private Item item[];

	
	public boolean isKeyControll;

	// 에니메이션 컨트롤
	public int aniState = PORORO_DOWN_IDEL;
	public int aniIndex = 0;
	private int aniCount = 0;

	// 시간 조정
	private TimeController cont = new TimeController();

	// 뽀로로 위치
	public int posX;
	public int posY;
	public int moveX;
	public int moveY;
	public int cellIndexX;
	public int cellIndexY;

	// 아이템 전체 수량
	private int itemCount;
	// 얻은 아이템수
	private int giveItemCount;

	// 시간 조정용 변수
	private int iceTime;
	private double nowTime;
	private int iceCount;

	// 첫이동 여부
	public boolean firstMove = true;

	// 점수아이템 갯수
	public int scoreItem = 0;

	// 점수아이템 스폰레벨
	public int scoreLevel = 0;

	public Pororo() {

	}

	public void destroy() {
		cont = null;
		resource = null;
	}

	public void draw(Graphics2D g) {
		if (item != null) {
			for (int i = 0; i < item.length; i++) {
				item[i].draw(g);
			}
		}
		switch (aniState) {
		case PORORO_DOWN_IDEL:
		case PORORO_CHECK:
			g.drawImage(resource.prIdle[orderIdel[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_DOWN_MOVE:
			g.drawImage(resource.prMove[orderMove[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_UP_IDEL:
			g.drawImage(resource.prBackdle[orderIdelSub[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_UP_MOVE:
			g.drawImage(resource.prBackMove[orderMove[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_LEFT_IDEL:
			g.drawImage(resource.prLeftIdle[orderIdel[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_LEFT_MOVE:
			g.drawImage(resource.prLeftMove[orderMove[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_RIGHT_IDEL:
			g.drawImage(resource.prRightIdle[orderIdel[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_RIGHT_MOVE:
			g.drawImage(resource.prRightMove[orderMove[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_HAVE:
			g.drawImage(resource.prHave[orderHave[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_GOOD:
			g.drawImage(resource.prHave[orderGood[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_BAD:
			g.drawImage(resource.prBad[orderBad[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_ICE:
			g.drawImage(resource.prIce[orderIce[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_ICE_SUB:
			g.drawImage(resource.prIce[orderIceSub[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_GAMEOVER:
			g.drawImage(resource.prGameover[orderGameover[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_GAMEOVER_END:
			g.drawImage(resource.prGameover[orderGameoverEnd[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		case PORORO_SUCCESS:
			g.drawImage(resource.prSuccess[orderSuccess[aniIndex]], posX
					+ GameData.MAP_X - GameData.PORORO_X, posY + GameData.MAP_Y
					- GameData.PORORO_Y, null);
			break;
		}
	}

	public boolean isKeyInput() {
		// System.out.println("Pororo aniState : " + aniState);
		switch (aniState) {
		case PORORO_UP_IDEL:
		case PORORO_DOWN_IDEL:
		case PORORO_LEFT_IDEL:
		case PORORO_RIGHT_IDEL:
			// case PORORO_FAILED:
			// System.out.println("Pororo isKeyInput : true");
			return true;
		}
		// System.out.println("Pororo isKeyInput : false");
		return false;
	}

	public void keyEvent(int event) {
		if (GameData.getInstance().cr.aniState == Crong.CRONG_GIVEITEM) {
			if (event == HRcEvent.VK_ENTER) {
				GameData.getInstance().cr.aniState = Crong.CRONG_IDEL;
			}
			return;
		}

		switch (event) {
		case HRcEvent.VK_UP:
			if (!MapData.getInstance().map[GameData.getInstance().stage][cellIndexX][cellIndexY].up
					&& !GameData.isWall) {
				break;
			}
			if (cellIndexY > 0) {
				if (GameData.getInstance().cr.aniState == Crong.CRONG_GAMEOVER) {
					GameData.getInstance().cr.aniState = Crong.CRONG_IDEL;
					GameData.getInstance().cr.aniIndex = 0;
				}
				resource.sound.playSound(Resource.SOUND_MOVE);
				item[2].setMove(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setMove(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setMove(cellIndexX, cellIndexY);
				aniState = PORORO_UP_MOVE;
				moveY = (cellIndexY - 1) * GameData.TILE_SIZE;
				aniIndex = 0;
			}
			break;
		case HRcEvent.VK_DOWN:
			firstMove = true;
			if (!MapData.getInstance().map[GameData.getInstance().stage][cellIndexX][cellIndexY].down
					&& !GameData.isWall) {
				break;
			}
			if (cellIndexY < 10) {
				if (GameData.getInstance().cr.aniState == Crong.CRONG_GAMEOVER) {
					GameData.getInstance().cr.aniState = Crong.CRONG_IDEL;
					GameData.getInstance().cr.aniIndex = 0;
				}
				resource.sound.playSound(Resource.SOUND_MOVE);
				item[2].setMove(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setMove(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setMove(cellIndexX, cellIndexY);
				aniState = PORORO_DOWN_MOVE;
				moveY = (cellIndexY + 1) * GameData.TILE_SIZE;
				aniIndex = 0;
			}
			break;
		case HRcEvent.VK_LEFT:
			if (!MapData.getInstance().map[GameData.getInstance().stage][cellIndexX][cellIndexY].left
					&& !GameData.isWall) {
				break;
			}
			if (cellIndexX > 0) {
				if (GameData.getInstance().cr.aniState == Crong.CRONG_GAMEOVER) {
					GameData.getInstance().cr.aniState = Crong.CRONG_IDEL;
					GameData.getInstance().cr.aniIndex = 0;
				}
				resource.sound.playSound(Resource.SOUND_MOVE);
				item[2].setMove(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setMove(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setMove(cellIndexX, cellIndexY);
				aniState = PORORO_LEFT_MOVE;
				moveX  = (cellIndexX - 1) * GameData.TILE_SIZE;
				aniIndex = 0;
			}
			break;
		case HRcEvent.VK_RIGHT:
			if (!MapData.getInstance().map[GameData.getInstance().stage][cellIndexX][cellIndexY].right
					&& !GameData.isWall) {
				break;
			}
			if (cellIndexX < 20) {
				if (GameData.getInstance().cr.aniState == Crong.CRONG_GAMEOVER) {
					GameData.getInstance().cr.aniState = Crong.CRONG_IDEL;
					GameData.getInstance().cr.aniIndex = 0;
				}
				resource.sound.playSound(Resource.SOUND_MOVE);
				item[2].setMove(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setMove(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setMove(cellIndexX, cellIndexY);
				aniState = PORORO_RIGHT_MOVE;
				moveX = (cellIndexX + 1) * GameData.TILE_SIZE;
				aniIndex = 0;
			}
			if (cellIndexX == 20 && cellIndexY == 8) {
				ImageDraw.getInstance().okIndex = 0;
				GameData.getInstance().itemCheck = false;
			}
			break;
		case HRcEvent.VK_ENTER:
			if (cellIndexX == 20 && cellIndexY == 8
					&& !GameData.getInstance().itemCheck) {
				// 아이템 체크

				GameData.getInstance().itemCheck = true;
				GameData.getInstance().cnt = giveItemCount;
				giveItemCount = 0;

				aniState = PORORO_CHECK;
				cont.start(100);
			} else {
				int itemIndex = MapData.getInstance().getItem(cellIndexX,
						cellIndexY);

				switch (itemIndex) {
				case GameData.ITEM_A:
				case GameData.ITEM_B:
				case GameData.ITEM_C:
				case GameData.ITEM_D:
				case GameData.ITEM_E:
				case GameData.ITEM_F:
				case GameData.ITEM_G:
				case GameData.ITEM_H:
				case GameData.ITEM_I:
				case GameData.ITEM_J:
					resource.sound.playSound(Resource.SOUND_GIVEITEM);
					GameData.getInstance().successScore[GameData.getInstance().stage] += 500;
					// 메인아이템
					for (int i = 0; i < item.length; i++) {
						if (item[i].itemIndex == GameData.ITEM_NONE) {
							item[i].setItem(itemIndex);

							GameData.getInstance().ticker.setImage(posX
									+ GameData.MAP_X - GameData.PORORO_X - 14,
									posY + GameData.MAP_Y - GameData.PORORO_Y
											- 9, posX + GameData.MAP_X
											- GameData.PORORO_X - 14, posY
											+ GameData.MAP_Y
											- GameData.PORORO_Y - 9 - 22, 5,
									resource.score[3], false, 0.5F);
							MapData.getInstance().setItem(cellIndexX,
									cellIndexY, GameData.ITEM_NONE);
							break;
						}
					}
					giveItemCount++;
					aniState = PORORO_HAVE;
					aniIndex = 0;
					break;
				case GameData.ITEM_SCORE_200:
				case GameData.ITEM_SCORE_100:
				case GameData.ITEM_SCORE_50:
					// 점수 아이템
					switch (itemIndex) {
					case GameData.ITEM_SCORE_50:
						GameData.getInstance().successScore[GameData.getInstance().stage] += 50;
						resource.sound.playSound(Resource.SOUND_STAR1);
						break;
					case GameData.ITEM_SCORE_100:
						GameData.getInstance().successScore[GameData.getInstance().stage] += 100;
						resource.sound.playSound(Resource.SOUND_STAR2);
						break;
					case GameData.ITEM_SCORE_200:
						GameData.getInstance().successScore[GameData.getInstance().stage] += 200;
						resource.sound.playSound(Resource.SOUND_STAR3);
						break;
					}
					scoreItem++;
					if (scoreItem >= 10) {
						scoreItem = 0;
						if (scoreLevel < 2) {
							scoreLevel++;
						}
						MapData.getInstance().removeScore(
								GameData.getInstance().stage);
						GameData.getInstance().randemSubItem(
								GameData.getInstance().stage, scoreLevel);
					}
					// 이펙트 세팅
					int[][] temp = { { 0, 0, 0, 0, 0 }, { 0, 7, 13, 18, 22 } };
					GameData.getInstance().ticker.setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 9, posY
							+ GameData.MAP_Y - GameData.PORORO_Y - 7, temp,
							resource.score[itemIndex - 20], false, 0.5F);
					GameData.getInstance().bgEffect.setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 8, posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 46,
							resource.effactSmok, false);
					GameData.getInstance().effect[0].setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 14, posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 43,
							resource.effactStar, false);
					MapData.getInstance().setItem(cellIndexX, cellIndexY,
							GameData.ITEM_NONE);
					aniState = PORORO_GOOD;
					aniIndex = 0;
					break;
				case GameData.ITEM_HIDE_SCORE:
					// 점수 아이템
					// 이펙트 세팅
					resource.sound.playSound(Resource.SOUND_GOODITEM);
					int[][] temp1 = { { 0, 0, 0, 0, 0 }, { 0, 7, 13, 18, 22 } };
					GameData.getInstance().ticker.setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 9, posY
							+ GameData.MAP_Y - GameData.PORORO_Y - 7, temp1,
							resource.score[3], false, 0.5F);
					GameData.getInstance().bgEffect.setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 8, posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 46,
							resource.effactSmok, false);
					GameData.getInstance().effect[0].setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 14, posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 43,
							resource.effactStar, false);
					MapData.getInstance().setItem(cellIndexX, cellIndexY,
							GameData.ITEM_NONE);
					aniState = PORORO_GOOD;
					aniIndex = 0;
					GameData.getInstance().successScore[GameData.getInstance().stage] += 500;
					break;
				case GameData.ITEM_HIDE_TIME:
					// 시간 랜덤 아이템
					// 이펙트 세팅
					resource.sound.playSound(Resource.SOUND_GOODITEM);
					int[][] temp2 = { { 0, 0, 0, 0, 0 }, { 0, 7, 13, 18, 22 } };
					GameData.getInstance().ticker.setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 14, posY
							+ GameData.MAP_Y - GameData.PORORO_Y - 9, temp2,
							resource.effactTimeUp, false, 0.5F);
					GameData.getInstance().bgEffect.setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 8, posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 46,
							resource.effactSmok, false);
					GameData.getInstance().effect[0].setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 20, posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 31,
							resource.effactTime, false);
					MapData.getInstance().setItem(cellIndexX, cellIndexY,
							GameData.ITEM_NONE);
					aniState = PORORO_GOOD;
					aniIndex = 0;
					GameData.getInstance().stageTime += 20000;
					if (GameData.getInstance().stageTime > GameData
							.getInstance().successTime[GameData.getInstance().stage]) {
						GameData.getInstance().stageTime = GameData
								.getInstance().successTime[GameData
								.getInstance().stage];
					}
					break;
				case GameData.ITEM_HIDE_ICE:
					// 얼음 랜덤 아이템
					// 이펙트 세팅
					resource.sound.playSound(Resource.SOUND_ICE_JUMP);
					GameData.getInstance().bgEffect.setImage(posX
							+ GameData.MAP_X - GameData.PORORO_X - 8, posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 46,
							resource.effactSmok, false);
					MapData.getInstance().setItem(cellIndexX, cellIndexY,
							GameData.ITEM_NONE);
					aniState = PORORO_ICE;
					aniIndex = 0;
					break;
				}
			}
			break;
		}
		isKeyControll = false;
	}

	public void process() {
		switch (aniState) {
		case PORORO_UP_IDEL:
			aniIndex = (aniIndex + 1) % orderIdelSub.length;
			break;
		case PORORO_DOWN_IDEL:
		case PORORO_LEFT_IDEL:
		case PORORO_RIGHT_IDEL:
			aniIndex = (aniIndex + 1) % orderIdel.length;
			break;
		case PORORO_UP_MOVE:
			posY -= GameData.MOVE_SPEED;
			aniIndex = (aniIndex + 1) % orderMove.length;
			if (posY == moveY) {
				item[2].setCell(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setCell(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setCell(cellIndexX, cellIndexY);
				aniState = PORORO_UP_IDEL;
				aniIndex = 0;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					((Trap) (GameData.getInstance().trap.elementAt(i)))
							.setCollision(cellIndexX, cellIndexY);
				}
				cellIndexY--;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					int damage = ((Trap) (GameData.getInstance().trap
							.elementAt(i))).isCollision(cellIndexX, cellIndexY);
					if (damage > 0) {
						aniState = PORORO_BAD;
						GameData.getInstance().stageTime -= (damage * 1000);
						GameData.getInstance().bgEffect.setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 14, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 46,
								resource.effactSmog, false);
						GameData.getInstance().effect[0].setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 16, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 35,
								resource.effactBad, false);
					}
				}
				posX = cellIndexX * GameData.TILE_SIZE;
				posY = cellIndexY * GameData.TILE_SIZE;
			}
			break;
		case PORORO_DOWN_MOVE:
			posY += GameData.MOVE_SPEED;
			aniIndex = (aniIndex + 1) % orderMove.length;
			if (posY == moveY) {
				item[2].setCell(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setCell(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setCell(cellIndexX, cellIndexY);
				aniState = PORORO_DOWN_IDEL;
				aniIndex = 0;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					((Trap) (GameData.getInstance().trap.elementAt(i)))
							.setCollision(cellIndexX, cellIndexY);
				}
				cellIndexY++;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					int damage = ((Trap) (GameData.getInstance().trap
							.elementAt(i))).isCollision(cellIndexX, cellIndexY);
					if (damage > 0) {
						aniState = PORORO_BAD;
						GameData.getInstance().stageTime -= (damage * 1000);
						GameData.getInstance().bgEffect.setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 14, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 46,
								resource.effactSmog, false);
						GameData.getInstance().effect[0].setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 16, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 35,
								resource.effactBad, false);
					}
				}
				posX = cellIndexX * GameData.TILE_SIZE;
				posY = cellIndexY * GameData.TILE_SIZE;
			}
			break;
		case PORORO_LEFT_MOVE:
			posX -= GameData.MOVE_SPEED;
			aniIndex = (aniIndex + 1) % orderMove.length;
			if (posX == moveX) {
				item[2].setCell(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setCell(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setCell(cellIndexX, cellIndexY);
				aniState = PORORO_LEFT_IDEL;
				aniIndex = 0;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					((Trap) (GameData.getInstance().trap.elementAt(i)))
							.setCollision(cellIndexX, cellIndexY);
				}
				cellIndexX--;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					int damage = ((Trap) (GameData.getInstance().trap
							.elementAt(i))).isCollision(cellIndexX, cellIndexY);
					if (damage > 0) {
						aniState = PORORO_BAD;
						GameData.getInstance().stageTime -= (damage * 1000);
						GameData.getInstance().bgEffect.setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 14, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 46,
								resource.effactSmog, false);
						GameData.getInstance().effect[0].setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 16, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 35,
								resource.effactBad, false);
					}
				}
				posX = cellIndexX * GameData.TILE_SIZE;
				posY = cellIndexY * GameData.TILE_SIZE;
			}
			break;
		case PORORO_RIGHT_MOVE:
			posX += GameData.MOVE_SPEED;
			aniIndex = (aniIndex + 1) % orderMove.length;
			if (posX == moveX) {
				item[2].setCell(item[1].cellIndexX, item[1].cellIndexY);
				item[1].setCell(item[0].cellIndexX, item[0].cellIndexY);
				item[0].setCell(cellIndexX, cellIndexY);
				aniState = PORORO_RIGHT_IDEL;
				aniIndex = 0;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					((Trap) (GameData.getInstance().trap.elementAt(i)))
							.setCollision(cellIndexX, cellIndexY);
				}
				cellIndexX++;
				for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
					int damage = ((Trap) (GameData.getInstance().trap
							.elementAt(i))).isCollision(cellIndexX, cellIndexY);
					if (damage > 0) {
						aniState = PORORO_BAD;
						GameData.getInstance().stageTime -= (damage * 1000);
						GameData.getInstance().bgEffect.setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 14, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 46,
								resource.effactSmog, false);
						GameData.getInstance().effect[0].setImage(posX
								+ GameData.MAP_X - GameData.PORORO_X - 16, posY
								+ GameData.MAP_Y - GameData.PORORO_Y + 35,
								resource.effactBad, false);
					}
				}
				posX = cellIndexX * GameData.TILE_SIZE;
				posY = cellIndexY * GameData.TILE_SIZE;
			}
			break;
		case PORORO_HAVE:
			aniIndex++;
			if (aniIndex >= orderHave.length) {
				aniState = PORORO_DOWN_IDEL;
				aniIndex = 0;
			}
			break;
		case PORORO_GOOD:
			aniIndex++;
			if (aniIndex >= orderGood.length) {
				aniState = PORORO_DOWN_IDEL;
				aniIndex = 0;
			}
			break;
		case PORORO_BAD:
			aniIndex++;
			if (aniIndex >= orderBad.length) {
				aniState = PORORO_DOWN_IDEL;
				aniIndex = 0;
			}
			break;
		case PORORO_ICE:
			aniIndex++;
			if (aniIndex >= orderIce.length) {
				resource.sound.playSound(Resource.SOUND_ICE_COUNT);
				iceTime = 5000;
				nowTime = System.currentTimeMillis();
				iceCount = 5;
				aniState = PORORO_ICE_SUB;
				aniIndex = 0;
			}
			break;
		case PORORO_ICE_SUB:
			aniIndex = (aniIndex + 1) % orderIceSub.length;
			if (iceCount > (int) (iceTime / 1000) && iceCount > 0) {
				// 시간이팩트
				int[][] temp = { { 0, 0, 0, 0, 0, 0 }, { 0, 3, 3, 3, 2, 2 } };

				GameData.getInstance().ticker.setImage(posX + GameData.MAP_X
						- GameData.PORORO_X + 15, posY + GameData.MAP_Y
						- GameData.PORORO_Y - 10, temp,
						resource.effactIceNum[iceCount - 1], false, 0.5F);
				// System.out.println("count : " + iceCount);
				iceCount--;
			}
			// 시간체크
			double temp = System.currentTimeMillis();
			iceTime -= (int) (temp - nowTime);
			nowTime = temp;
			if (iceTime <= 0) {
				resource.sound.playSound(Resource.SOUND_ICE_FINISH);
				GameData.getInstance().effect[0].setImage(posX + GameData.MAP_X
						- GameData.PORORO_X - 7, posY + GameData.MAP_Y
						- GameData.PORORO_Y + 40, resource.effactIce, false);
				aniState = PORORO_BAD;
				aniIndex = 0;
				break;
			}
			break;
		case PORORO_GAMEOVER:
			aniIndex++;
			if (aniIndex >= orderGameover.length) {
				GameData.getInstance().textState = GameData.TEXT_GAMEOVER;
				aniState = PORORO_GAMEOVER_END;
				aniIndex = 0;
			}
			if (aniIndex == 4) {
				// 이펙트
				GameData.getInstance().bgEffect.setImage(posX + GameData.MAP_X
						- GameData.PORORO_X - 14, posY + GameData.MAP_Y
						- GameData.PORORO_Y + 46, resource.effactSmog, false);
			}
			break;
		case PORORO_GAMEOVER_END:
			aniIndex++;
			// if (aniIndex >= orderGameoverEnd.length) {
			// ((Listener)
			// GameData.getInstance().game).listener(MazeGPlay.GAME_FINISH_POPUP);
			// }
			aniIndex = aniIndex % orderGameoverEnd.length;
			aniCount++;
			if (aniCount == orderGameoverEnd.length * 4) {
				((Listener) GameData.getInstance().game)
						.listener(MazeGPlay.GAME_OVER);
				Resource.getInstance().sound
				.playSound(Resource.SOUND_GAMEOVER);
			} else if (aniCount > orderGameoverEnd.length * 4) {

			}
			break;
		case PORORO_SUCCESS:
			aniIndex = (aniIndex + 1) % orderSuccess.length;
			// aniState = PORORO_DOWN_IDEL;
			break;
		}
		switch (aniState) {
		case PORORO_GAMEOVER:
		case PORORO_GAMEOVER_END:
		case PORORO_SUCCESS:
			break;
		case PORORO_CHECK:
			if (cont.process()) {
				for (int i = 0; i < item.length; i++) {
					for (int j = 0; j < GameData.getInstance().item.length; j++) {
						if (item[i].itemIndex == MapData.getInstance().item[GameData
								.getInstance().stage][j]) {
							resource.sound.playSound(Resource.SOUND_MOVEITEM);
							GameData.getInstance().cr
									.setState(Crong.CRONG_CHEERING);
							GameData.getInstance().item[j].setImage(item[i]
									.getPosX(), item[i].getPosY(), GameData
									.getInstance().itemPos[j][0], GameData
									.getInstance().itemPos[j][1], 6, item[i]
									.getImage(), true, 1);
							item[i].reset();
							cont.start(700);
							itemCount++;
							return;
						}
					}
				}
			}
			break;
		default:
			for (int i = 0; i < item.length; i++) {
				item[i].process();
			}
			break;
		}

	}

	public void pause() {
		cont.pause();
	}

	public void reStart() {
		cont.reStart();
	}

	public void start() {
		scoreLevel = 0;

		scoreItem = 0;

		firstMove = true;
		aniState = PORORO_DOWN_IDEL;
		aniIndex = 0;
		aniCount = 0;

		cellIndexX = 0;
		cellIndexY = 0;

		posX = cellIndexX * GameData.TILE_SIZE;
		posY = cellIndexY * GameData.TILE_SIZE;

		moveX = cellIndexX * GameData.TILE_SIZE;
		moveY = cellIndexY * GameData.TILE_SIZE;

		isKeyControll = true;

		itemCount = 0;
		giveItemCount = 0;
		if (item != null) {
			item = null;
		}
		item = new Item[3];
		for (int i = 0; i < item.length; i++) {
			item[i] = new Item();
			item[i].reset();
		}
		for (int i = 0; i < item.length; i++) {
			GameData.getInstance().item[i].hide();
		}
		MapData.getInstance().removeScore(GameData.getInstance().stage);
		GameData.getInstance().randemSubItem(GameData.getInstance().stage, 0);
	}
}
