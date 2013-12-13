package pororo.com.game.fishing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.dvb.ui.DVBColor;

import pororo.com.SceneManager;

public final class ImageDraw {
	private static ImageDraw instance = new ImageDraw();
	// 이미지 리소스
	private Resource resource = Resource.getInstance();
	// 게임정보
	private GameData gameData = GameData.getInstance();

	public static ImageDraw getInstance() {
		if (instance == null) {
			instance = new ImageDraw();
		}
		return instance;
	}

	public void loading(Graphics2D g) {
		g.drawImage(resource.img_load[0], 480 - 287, 464, null);
		g.clipRect(
						480 - 287,
						464,
						(int) (resource.img_load[1].getWidth(null) * ((float) resource.loadingStep / (float) resource.MAX_COUNT)),
						resource.img_load[1].getHeight(null));
		g.drawImage(resource.img_load[1], 480 - 287, 464,
				resource.img_load[1].getWidth(null), resource.img_load[1]
						.getHeight(null), null);
		g.clipRect(0, 0, 960, 540);
	}

	public void intro(Graphics2D g) {
		gameData.pr.draw(g);
		guageDraw(g);

		switch (GameData.getInstance().textState) {
		case GameData.TEXT_READY:
			if (GameData.getInstance().textIndex[0] < 10) {

				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								GameData.getInstance().alphaJun[GameData
										.getInstance().textIndex[0]]));
				// 준 그림
				g.drawImage(Resource.getInstance().text[0], 377,
						212 - GameData.getInstance().textJun[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 2) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaJun[GameData
									.getInstance().textIndex[1]]));
					// 비 그림
					g.drawImage(Resource.getInstance().text[1], 509,
							228 - GameData.getInstance().textJun[GameData
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
								GameData.getInstance().alphaSi[GameData
										.getInstance().textIndex[0]]));
				// 시 그림
				g.drawImage(Resource.getInstance().text[2], 374,
						220 - GameData.getInstance().textSi[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 7) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaJak[GameData
									.getInstance().textIndex[1]]));
					// 작 그림
					g.drawImage(Resource.getInstance().text[3], 503,
							220 - GameData.getInstance().textJak[GameData
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
								GameData.getInstance().alphaGame[GameData
										.getInstance().textIndex[0]]));
				// 게임 그림
				g.drawImage(Resource.getInstance().text[4], 314,
						217 - GameData.getInstance().textSi[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 5) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaFinish[GameData
									.getInstance().textIndex[1]]));
					// 종료 그림
					g.drawImage(Resource.getInstance().text[5], 489,
							212 - GameData.getInstance().textJak[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}
			break;
		}
	}

	public void play(Graphics2D g) {
		if(gameData.item.isState()) {
			gameData.item.draw(g);
		}
		scoreDraw(g);
		guageDraw(g);

		for (int i = 0; i < gameData.mon.length; i++) {
			if (gameData.mon[i].isWorking()) {
				gameData.mon[i].draw(g);
			}
		}
		for (int i = 0; i < gameData.monSp.length; i++) {
			if (gameData.monSp[i].isWorking()) {
				gameData.monSp[i].draw(g);
			}
		}
		if (gameData.monSpBig.isWorking()) {
			gameData.monSpBig.draw(g);
		}
		gameData.pr.draw(g);
		
		if(gameData.effect.isState()) {
			gameData.effect.draw(g);
		}
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

		g.drawImage(Resource.getInstance().finishPop, 280, 116, null);

		if (GameData.getInstance().finishCur) {
			g.drawImage(Resource.getInstance().btnFin[0], 314, 326, null);
			g.drawImage(Resource.getInstance().btnRe[1], 491, 326, null);
		} else {
			g.drawImage(Resource.getInstance().btnFin[1], 314, 326, null);
			g.drawImage(Resource.getInstance().btnRe[0], 491, 326, null);
		}
		
		// 점수
		int point = gameData.pr.getGrade();
		
		g.drawImage(resource.numberL[point%10], 567, 243, null);
		if(point/10 > 0)
			g.drawImage(resource.numberL[(point/10)%10], 567-26, 243, null);
		if(point/100 > 0)
			g.drawImage(resource.numberL[(point/100)%10], 567-26*2, 243, null);
		if(point/1000 > 0)
			g.drawImage(resource.numberL[(point/1000)%10], 567-26*3, 243, null);
		if(point/10000 > 0)
			g.drawImage(resource.numberL[(point/10000)%10], 567-26*4, 243, null);
		if(point/100000 > 0)
			g.drawImage(resource.numberL[(point/100000)%10], 567-26*5, 243, null);
		
		// 등급표시
		int imageIndex = 0;
		if(point >= 20001){
			imageIndex = 3;
		}else if(point >= 13001) {
			imageIndex = 2;
		}
		else if(point >= 5001) {
			imageIndex = 1;		
		}
		
		g.drawImage(Resource.getInstance().endRank[imageIndex],
				317, 200 - GameData.getInstance().endRank[GameData.getInstance().rankAni],
				null);
		
	}

	public void scoreDraw(Graphics2D g) {
		int point = gameData.pr.getGrade();

		g.drawImage(resource.number[point % 10], 870, 47, null);
		if (point / 10 > 0)
			g.drawImage(resource.number[(point / 10) % 10], 870 - 20, 47, null);
		if (point / 100 > 0)
			g.drawImage(resource.number[(point / 100) % 10], 870 - 20 * 2, 47,
					null);
		if (point / 1000 > 0)
			g.drawImage(resource.number[(point / 1000) % 10], 870 - 20 * 3, 47,
					null);
		if (point / 10000 > 0)
			g.drawImage(resource.number[(point / 10000) % 10], 870 - 20 * 4,
					47, null);
		if (point / 100000 > 0)
			g.drawImage(resource.number[(point / 100000) % 10], 870 - 20 * 5,
					47, null);
	}

	public void guageDraw(Graphics2D g) {
		int life = gameData.pr.getGuage();

		if (life > 0) {
			g.drawImage(resource.guage, 151, 53, 151+life, 53+17, 0, 0, life, 17, null);
//			g.drawImage(resource.guage, 150, 51, life, 17, null);
		}
	}

	public void gameover(Graphics2D g) {
		gameData.pr.draw(g);
		if(gameData.delayIndex >= 5) {
			if (GameData.getInstance().textIndex[0] < 14) {
				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								GameData.getInstance().alphaGame[GameData
										.getInstance().textIndex[0]]));
				// 게임 그림
				g.drawImage(Resource.getInstance().text[4], 314,
						217 - GameData.getInstance().textSi[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 5) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaFinish[GameData
									.getInstance().textIndex[1]]));
					// 종료 그림
					g.drawImage(Resource.getInstance().text[5], 489,
							212 - GameData.getInstance().textJak[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}
		}
		scoreDraw(g);
		guageDraw(g);
		if(gameData.effect.isState()) {
			gameData.effect.draw(g);
		}
	}

	public void success(Graphics2D g) {}
	public void end(Graphics2D g) {}

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

	public void destroy() {
		instance = null;
	}
}
