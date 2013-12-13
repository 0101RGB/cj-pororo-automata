package pororo.com.game.maze;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.dvb.ui.DVBColor;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.game.maze.object.Crong;
import pororo.com.game.maze.object.Trap;

public final class ImageDraw {
	
	// 각종 문구 위치 에니메이션 정의
	private int textJun[] = { 7, 3, 0, -2, -1, 0, 0, 0, 0, 0 };// new int[10];
	private int textSi[] = { -7, -3, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };// new
	// int[16];
	private int textJak[] = { -7, -3, 0, 2, 1, 0, 0, 0, 0 };// new int[9];
	// int[14];
	private int textSeong[] = { -30, -5, 20, 10, 0, 0, 0, 0 };// new int[8];
	private int textGong[] = { -30, -5, 20, 10, 0 };// new int[5];

	// 각종 문구 에니메이션 알파값 정의
	private float alphaJun[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 0.75f, 0.5f,
			0.25f };// new int[10];
	private float alphaSi[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f,
			1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new int[16];
	private float alphaJak[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 0.75f, 0.5f,
			0.25f };// new int[9];
	private float alphaGame[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f,
			1f, 0.75f, 0.5f, 0.25f };// new int[14];
	private float alphaFinish[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 0.75f, 0.5f,
			0.25f };// new int[9];

	public static ImageDraw instance = new ImageDraw();
	public int okIndex = 0;
	
	public static ImageDraw getInstance() {
		if (instance == null) {
			instance = new ImageDraw();
		}
		return instance;
	}

	public ImageDraw() {
	}

	public void loading(Graphics2D g) {
		if (Resource.getInstance().isLoading) {
			g.drawImage(Resource.getInstance().img_load[0],
					480 - 287, 464, null);
			g
					.setClip(
							480 - 287,
							464,
							(int) (Resource.getInstance().img_load[1]
									.getWidth(null) * ((float) Resource
									.getInstance().loadingStep / (float) Resource.MAX_COUNT)),
							Resource.getInstance().img_load[1].getHeight(null));
			g.drawImage(Resource.getInstance().img_load[1],
					480 - 287, 464, null);
			g.setClip(0, 0, 960, 540);
		}
	}

	public void init(Graphics2D g) {
		// 준비 시작
		switch (GameData.getInstance().textState) {
		case GameData.TEXT_READY:
			if (GameData.getInstance().textIndex[0] < 10) {

				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								alphaJun[GameData
										.getInstance().textIndex[0]]));
				// 준 그림
				g.drawImage(Resource.getInstance().text[0], 377 - 50,
						212 - textJun[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 2) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							alphaJun[GameData
									.getInstance().textIndex[1]]));
					// 비 그림
					g.drawImage(Resource.getInstance().text[1], 509 - 50,
							228 - textJun[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}
			break;
		case GameData.TEXT_START:
			if (GameData.getInstance().textIndex[0] < 16) {
				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								alphaSi[GameData
										.getInstance().textIndex[0]]));
				// 시 그림
				g.drawImage(Resource.getInstance().text[2], 374 - 50,
						228 - textSi[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 7) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							alphaJak[GameData
									.getInstance().textIndex[1]]));
					// 작 그림
					g.drawImage(Resource.getInstance().text[3], 503 - 50,
							220 - textJak[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}
			break;
		case GameData.TEXT_GAMEOVER:
			if (GameData.getInstance().textIndex[0] < 14) {
				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								alphaGame[GameData
										.getInstance().textIndex[0]]));
				// 게임 그림
				g.drawImage(Resource.getInstance().text[4], 314 - 50,
						217 - textSi[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 5) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							alphaFinish[GameData
									.getInstance().textIndex[1]]));
					// 종료 그림
					g.drawImage(Resource.getInstance().text[5], 489 - 50,
							212 - textJak[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}
			break;
		case GameData.TEXT_SECCESS:
			if (GameData.getInstance().textIndex[0] < 8) {
				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								alphaSi[GameData
										.getInstance().textIndex[0]]));
				// 성 그림
				g.drawImage(Resource.getInstance().text[6], 319,
						214 - textSeong[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 3) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							alphaJak[GameData
									.getInstance().textIndex[1]]));
					// 공 그림
					g.drawImage(Resource.getInstance().text[7], 454,
							214 - textGong[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}
			break;
		}
	}

	public void play(Graphics2D g) {
		mapItem(g);
		GameData.getInstance().bgEffect.draw(g);
		GameData.getInstance().cr.draw(g);
		GameData.getInstance().pr.draw(g);
		for (int i = 0; i < 3; i++) {
			GameData.getInstance().item[i].draw(g);
			GameData.getInstance().effect[i].draw(g);
		}
		GameData.getInstance().ticker.draw(g);
		if (GameData.getInstance().cr.aniState == Crong.CRONG_GIVEITEM) {
			g.drawImage(Resource.getInstance().crTalk[1], 784, 206, null);
		}
		if (!GameData.getInstance().pr.firstMove) {
			g.drawImage(Resource.getInstance().crTalk[0], 784, 206, null);
		}

		if (GameData.getInstance().pr.cellIndexX == 20
				&& GameData.getInstance().pr.cellIndexY == 8
				&& GameData.getInstance().cr.aniState != Crong.CRONG_GIVEITEM
				&& !GameData.getInstance().itemCheck
				&& GameData.getInstance().cr.aniState != Crong.CRONG_SUCCESS) {
			g.drawImage(Resource.getInstance().ok[okIndex], 784, 206, null);
		}

		// 점수 그리기
		int point = 0;

		for (int i = 0; i < GameData.getInstance().successScore.length; i++) {
			point += GameData.getInstance().successScore[i];
		}

		if (point >= 0)
			g.drawImage(Resource.getInstance().numberS[point % 10], 896, 450,
					null);
		if (point / 10 > 0)
			g.drawImage(Resource.getInstance().numberS[(point / 10) % 10],
					896 - 12, 450, null);
		if (point / 100 > 0)
			g.drawImage(Resource.getInstance().numberS[(point / 100) % 10],
					896 - 12 * 2, 450, null);
		if (point / 1000 > 0)
			g.drawImage(Resource.getInstance().numberS[(point / 1000) % 10],
					896 - 12 * 3, 450, null);
		if (point / 10000 > 0)
			g.drawImage(Resource.getInstance().numberS[(point / 10000) % 10],
					896 - 12 * 4, 450, null);
		if (point / 100000 > 0)
			g.drawImage(Resource.getInstance().numberS[(point / 100000) % 10],
					896 - 12 * 5, 450, null);

		// 남은시간 그리기
		if (GameData.getInstance().stageTime > 0) {
			g
					.setClip(
							794,
							472,
							(int) ((float) Resource.getInstance().gage
									.getWidth(null) * ((float) (GameData
									.getInstance().stageTime) / (float) (GameData
									.getInstance().successTime[GameData
									.getInstance().stage]))), Resource
									.getInstance().gage.getHeight(null));
			g.drawImage(Resource.getInstance().gage, 794, 472, null);
			g.setClip(0, 0, 960, 540);
		}
		// 스테이지 등급 표시
		for (int i = 0; i < GameData.getInstance().stage; i++) {
			int score = GameData.getInstance().successScore[i];
			// 등급표시
			int index = 0;
			if (score >= 4000) {
				index = 3;
			} else if (score >= 3000) {
				index = 2;
			} else if (score >= 2250) {
				index = 1;
			}
			g.drawImage(Resource.getInstance().string[index], 794 + (i * 24),
					423, null);
		}
		// 현재 스테이지 표시
		g.drawImage(Resource.getInstance().stage[GameData.getInstance().stage],
				794 + (GameData.getInstance().stage * 24), 423, null);
	}

	public void exitPopup(Graphics2D g) {
		g.setColor(new DVBColor(0, 0, 0, 80));
//		g.fillRect(0, 0, 960, 540);  // 메모리

		g.drawImage(Resource.getInstance().exitPop, 304, 123, null);

		if (GameData.getInstance().exitCur) {
			g.drawImage(Resource.getInstance().btnYes[0], 356, 325, null);
			g.drawImage(Resource.getInstance().btnNo[1], 477, 325, null);
		} else {
			g.drawImage(Resource.getInstance().btnYes[1], 356, 325, null);
			g.drawImage(Resource.getInstance().btnNo[0], 477, 325, null);
		}
	}

	public void finishPopup(Graphics2D g) {
		g.drawImage(Resource.getInstance().finishPop, 280, 116, null);

		if (GameData.getInstance().finishCur) {
			g.drawImage(Resource.getInstance().btnFin[0], 314, 326, null);
			g.drawImage(Resource.getInstance().btnRe[1], 491, 326, null);
		} else {
			g.drawImage(Resource.getInstance().btnFin[1], 314, 326, null);
			g.drawImage(Resource.getInstance().btnRe[0], 491, 326, null);
		}

		// 점수
		int point = 0;

		for (int i = 0; i < GameData.getInstance().successScore.length; i++) {
			point += GameData.getInstance().successScore[i];
		}

		g.drawImage(Resource.getInstance().numberL[point % 10], 567, 243, null);
		if (point / 10 > 0)
			g.drawImage(Resource.getInstance().numberL[(point / 10) % 10],
					567 - 26, 243, null);
		if (point / 100 > 0)
			g.drawImage(Resource.getInstance().numberL[(point / 100) % 10],
					567 - 26 * 2, 243, null);
		if (point / 1000 > 0)
			g.drawImage(Resource.getInstance().numberL[(point / 1000) % 10],
					567 - 26 * 3, 243, null);
		if (point / 10000 > 0)
			g.drawImage(Resource.getInstance().numberL[(point / 10000) % 10],
					567 - 26 * 4, 243, null);
		if (point / 100000 > 0)
			g.drawImage(Resource.getInstance().numberL[(point / 100000) % 10],
					567 - 26 * 5, 243, null);

		int imageIndex = 0;
		if (point >= 17250) {
			imageIndex = 3;
		} else if (point >= 14500) {
			imageIndex = 2;
		} else if (point >= 9500) {
			imageIndex = 1;
		}

		g
				.drawImage(Resource.getInstance().endRank[imageIndex], 317,
						200 - GameData.getInstance().endRank[GameData
								.getInstance().rankAni], null);
	}

	public void error(Graphics2D g) {
		g.setColor(new DVBColor(0, 0, 0, 80));
		g.fillRect(0, 0, 960, 540);
		g.drawImage( SceneManager.getInstance().imgPopupA, 221, 96, null);
		SceneManager.getInstance().drawButton(g, 0, 420, 360);
		g.setColor(new Color(53, 53, 53));
		g.setFont(new Font("Bold", 0, 20));
		SceneManager.DrawStr(g, "알     림", 480, 152);
		g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
		g.setFont(new Font("Bold", 0, 17));
		g.setColor(Color.red);
		SceneManager.DrawStr(g, "통신상태가 원활하지 않습니다.", 480, 208 + (2 * 22));
		SceneManager.DrawStr(g, "잠시 후 다시 이용해 주시기 바랍니다.", 480, 208 + (3 * 22));
	}

	public void mapItem(Graphics2D g) {
		g.drawImage(Resource.getInstance().map, GameData.MAP_X, GameData.MAP_Y,
				null);
		// 나무에 있는 아이템 이미지
		if (MapData.getInstance().item[GameData.getInstance().stage][0] != -1)
			g
					.drawImage(
							Resource.getInstance().item[MapData.getInstance().item[GameData
									.getInstance().stage][0]][2], 833, 186,
							null);
		if (MapData.getInstance().item[GameData.getInstance().stage][1] != -1)
			g
					.drawImage(
							Resource.getInstance().item[MapData.getInstance().item[GameData
									.getInstance().stage][1]][2], 813, 233,
							null);
		if (MapData.getInstance().item[GameData.getInstance().stage][2] != -1)
			g
					.drawImage(
							Resource.getInstance().item[MapData.getInstance().item[GameData
									.getInstance().stage][2]][2], 841, 278,
							null);

		// 맵위의 아이템 표시
		for (int i = 0; i < GameData.getInstance().trap.size(); i++) {
			((Trap) (GameData.getInstance().trap.elementAt(i))).draw(g);
		}
		for (int i = 0; i < GameData.MAP_WIDTH; i++) {
			for (int j = 0; j < GameData.MAP_HEIGHT; j++) {
				int itemIndex = GameData.getInstance().getItemIndex(i, j);
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
					// 메인아이템
					if (!MapData.getInstance().map[GameData.getInstance()
							.getMapIndex()][i][j].isItemHide)
						g.drawImage(Resource.getInstance().item[itemIndex][1],
								i * 32 + GameData.MAP_X, j * 32
										+ GameData.MAP_Y - 7, null);
					break;
				case GameData.ITEM_SCORE_50:
				case GameData.ITEM_SCORE_100:
				case GameData.ITEM_SCORE_200:
					if (!MapData.getInstance().map[GameData.getInstance()
							.getMapIndex()][i][j].isItemHide)
						g.drawImage(
								Resource.getInstance().star[itemIndex - 20], i
										* 32 + GameData.MAP_X - 1, j * 32
										+ GameData.MAP_Y - 7, null);
					// 점수 아이템
					break;
				case GameData.ITEM_HIDE_ICE:
				case GameData.ITEM_HIDE_SCORE:
				case GameData.ITEM_HIDE_TIME:
					if (!MapData.getInstance().map[GameData.getInstance()
							.getMapIndex()][i][j].isItemHide)
						g.drawImage(Resource.getInstance().random, i * 32
								+ GameData.MAP_X - 3, j * 32 + GameData.MAP_Y
								- 7, null);
					// 물음표 아이템
					break;
				}
			}
		}
	}

	public void destroy() {
		instance = null;
	}
}
