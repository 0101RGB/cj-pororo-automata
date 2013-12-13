package pororo.com.game.puzzle;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.game.puzzle.object.PieceData;
import pororo.com.game.puzzle.object.Pororo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import org.dvb.ui.DVBColor;


// 게임 화면을 그린다
public final class ImageDraw {
	// 남은시간 게이지 좌표
	public int timeGageX;
	public int timeGageY;
	public int timeGageW;
	public int timeGageH;

	// Puzzle 좌표
	public int puzzleX;
	public int puzzleY;

	// Puzzle 좌표
	public int miniX;
	public int miniY;

	// score 좌표
	public int scoreX;
	public int scoreY;
	public int scoreOffset;

	// score 좌표
	public int stageX;
	public int stageY;
	public int stageOffset;

	private static ImageDraw instance = new ImageDraw();

	public Image curPororo = null;
	public Image curTube = null;
	public int curPororopos = 0;
	// 컬리젼 역역 그리기
	public boolean collisionRect;

	public static ImageDraw getInstance() {
		if (instance == null) {
			instance = new ImageDraw();
		}
		return instance;
	}

	public ImageDraw() {
	}

	public void draw(int state, Graphics2D g) {
		// 게임 상태에 따라 적절한 이미지 호출
		switch (GameData.getInstance().gameState) {
		case GameData.LOADING:// 이미지 로딩
			loading(g);
			break;
		case GameData.INIT:// 준비시작 문구
			play(g);
			init(g);
			break;
		case GameData.PLAY:// 게임 실행
			//System.out.println("================posIndex" + GameData.getInstance().pororo.posIndex);
			play(g);
			break;
		case GameData.FINISH:// 게임 성공 문구
			//System.out.println("================posIndex" + GameData.getInstance().pororo.posIndex);
			play(g);
			finish(g);
			break;
		case GameData.GAMEOVER:// 게임종료 문구

			play(g);
			gameover(g);
			break;
		case GameData.EXIT_POPUP:// 게임종료 문구
			play(g);
			exitPopup(g);
			break;
		case GameData.FINISH_POPUP:// 게임종료 문구
			play(g);
			finishPopup(g);
			break;
		case GameData.ERROR:// 게임종료 문구
			error(g);
			break;
		}
	}

	public void loading(Graphics2D g) {
		// 이미지 로딩
		g.drawImage(Resource.getInstance().img_load[0],
				480 - 287, 464, null);
		g.setClip(193, 464, (int) ((float) 574 * ((float) Resource
				.getInstance().state / (float) Resource.MAX_COUNT)), 38);
		g.drawImage(Resource.getInstance().img_load[1],
				480 - 287, 464, null);
		g.setClip(0, 0, 960, 540);
	}

	public void init(Graphics2D g) {
		// 게임 시작시 문구
		switch (GameData.getInstance().textState) {
		case GameData.TEXT_READY:
			if (GameData.getInstance().textIndex[0] < 10) {

				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								GameData.getInstance().alphaJun[GameData
										.getInstance().textIndex[0]]));
				// 준 그림
				g.drawImage(Resource.getInstance().text[0], 373,
						213 - GameData.getInstance().textJun[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 2) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaJun[GameData
									.getInstance().textIndex[1]]));
					// 비 그림
					g.drawImage(Resource.getInstance().text[1], 508,
							226 - GameData.getInstance().textJun[GameData
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
						225 - GameData.getInstance().textSi[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 7) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaJak[GameData
									.getInstance().textIndex[1]]));
					// 작 그림
					g.drawImage(Resource.getInstance().text[3], 504,
							217 - GameData.getInstance().textJak[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}
			break;
		case GameData.TEXT_GAMEOVER:
			/*
			if (GameData.getInstance().textIndex[0] < 14) {
				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,
								GameData.getInstance().alphaGame[GameData
										.getInstance().textIndex[0]]));
				// 게임 그림
				g.drawImage(Resource.getInstance().text[4], 318,
						216 - GameData.getInstance().textSi[GameData
								.getInstance().textIndex[0]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
				if (GameData.getInstance().textIndex[0] >= 5) {
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaFinish[GameData
									.getInstance().textIndex[1]]));
					// 종료 그림
					g.drawImage(Resource.getInstance().text[5], 484,
							211 - GameData.getInstance().textJak[GameData
									.getInstance().textIndex[1]], null);
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 1));
				}
			}*/
			break;
			
			/*
		case GameData.TEXT_SECCESS:
			if (GameData.getInstance().textIndex[0] < 10) {
				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_OVER,1));
				// 퍼 그림
				g.drawImage(Resource.getInstance().text[6], 316,
						207 - GameData.getInstance().textPu[GameData
								.getInstance().textIndex[0]], null);
				// 즐 그림
				g.drawImage(Resource.getInstance().text[6], 397,
						207 - GameData.getInstance().textPu[GameData
								.getInstance().textIndex[1]], null);
				// 완 그림
				g.drawImage(Resource.getInstance().text[6], 484,
						207 - GameData.getInstance().textPu[GameData
								.getInstance().textIndex[2]], null);
				// 성 그림
				g.drawImage(Resource.getInstance().text[6], 569,
						207 - GameData.getInstance().textPu[GameData
								.getInstance().textIndex[3]], null);
			}
			break;
			*/
		}
	}

	public void play(Graphics2D g) {
		// 게임 플레이 중
		// 스테이지 표시
		if (Resource.getInstance().numberStage != null) {
			int tmp = GameData.getInstance().stage +1;
			if (tmp >= 0
					&& Resource.getInstance().numberStage[tmp % 10] != null)
				g.drawImage(Resource.getInstance().numberStage[tmp % 10], stageX, stageY, null);
			if (tmp / 10 > 0
					&& Resource.getInstance().numberStage[(tmp / 10) % 10] != null)
				g.drawImage(Resource.getInstance().numberStage[(tmp / 10) % 10], stageX - stageOffset,
						stageY, null);
		}
		// *
		// 남은 시간표시
		if (Resource.getInstance().gage != null) {
			if (GameData.getInstance().time > 0) {
				g.setClip(timeGageX, timeGageY,
						(int) ((float) timeGageW * ((float) (GameData
								.getInstance().time) / (float) (GameData
								.getInstance().totalTime))), timeGageH);
				g.drawImage(Resource.getInstance().gage, timeGageX, timeGageY,
						null);
				g.setClip(0, 0, 960, 540);
			}
		}

		// 점수표시
		int point = GameData.getInstance().score;

		if (Resource.getInstance().numberScore != null) {
			if (point >= 0
					&& Resource.getInstance().numberScore[point % 10] != null)
				g.drawImage(Resource.getInstance().numberScore[point % 10],
						scoreX, scoreY, null);
			if (point / 10 > 0
					&& Resource.getInstance().numberScore[(point / 10) % 10] != null)
				g.drawImage(
						Resource.getInstance().numberScore[(point / 10) % 10],
						scoreX - scoreOffset, scoreY, null);
			if (point / 100 > 0
					&& Resource.getInstance().numberScore[(point / 100) % 10] != null)
				g.drawImage(
						Resource.getInstance().numberScore[(point / 100) % 10],
						scoreX - scoreOffset * 2, scoreY, null);
			if (point / 1000 > 0
					&& Resource.getInstance().numberScore[(point / 1000) % 10] != null)
				g
						.drawImage(
								Resource.getInstance().numberScore[(point / 1000) % 10],
								scoreX - scoreOffset * 3, scoreY, null);
			if (point / 10000 > 0
					&& Resource.getInstance().numberScore[(point / 10000) % 10] != null)
				g
						.drawImage(
								Resource.getInstance().numberScore[(point / 10000) % 10],
								scoreX - scoreOffset * 4, scoreY, null);
			if (point / 100000 > 0
					&& Resource.getInstance().numberScore[(point / 100000) % 10] != null)
				g
						.drawImage(
								Resource.getInstance().numberScore[(point / 100000) % 10],
								scoreX - scoreOffset * 5, scoreY, null);
		}
		// 퍼즐 그리기
		// *
		g.drawImage(Resource.getInstance().map, puzzleX, puzzleY, null);
		g.drawImage(Resource.getInstance().miniMap, miniX, miniY, null);
		/**/

		// *
		for (int i = 0; i < GameData.getInstance().piece.size(); i++) {
			PieceData cnt = (PieceData) GameData.getInstance().piece
					.elementAt(i);
			if (cnt.show) {
				g.drawImage(Resource.getInstance().piece[i], puzzleX
						+ (cnt.posX * 40) - 40 - 2, puzzleY + (cnt.posY * 40)
						- 2, null);
			}
		}
		/**/
		// 충돌영역 그리기
		// *
		// collisionRect = false;
		if (collisionRect)
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 7; j++) {
					g.setColor(Color.RED);
					g.drawRect(puzzleX + (i * 40) + 1, puzzleY + (j * 40) + 1,
							38, 38);
					for (int k = 0; k < 4; k++) {
						if (GameData.getInstance().mapData.map[i][j].piece[k] == GameData
								.getInstance().pororo.puzzleIndex) {
							if (GameData.getInstance().pororo.collision) {
								g.setColor(Color.WHITE);
							} else {
								g.setColor(Color.RED);
							}
							g.drawRect(puzzleX + (i * 40) + 1, puzzleY
									+ (j * 40) + 1, 38, 38);
							g
									.drawString(
											""
													+ GameData.getInstance().mapData.map[i][j].piece[k],
											puzzleX + (i * 40) + 15, puzzleY
													+ (j * 40) + 25);
						}
					}
				}
			}
		/**/

		Pororo pororo = GameData.getInstance().pororo;
		// tube 그리기
		switch (pororo.aniStateTube) {
		case Pororo.TUBE_IDEL:
			curTube = Resource.getInstance().tube[pororo.idelTube[pororo.aniIndexTube]];
			//g
			//		.drawImage(
			//				Resource.getInstance().tube[pororo.idelTube[pororo.aniIndexTube]],
			//				pororo.posX + (pororo.indexX * 20) - 6, 448, null);
			break;
		case Pororo.TUBE_DOWN:
			curTube = Resource.getInstance().tube[pororo.jumpTube[pororo.aniIndexTube]];
			//g
			//		.drawImage(
			//				Resource.getInstance().tube[pororo.jumpTube[pororo.aniIndexTube]],
			//				pororo.posX + (pororo.indexX * 20) - 6, 448, null);
			break;
		}
		g.drawImage(curTube, pororo.posX + (pororo.indexX * 20) - 6, 448, null);

		//int pororotmppos = 0;
		// 뽀로로 그리기
		switch (pororo.aniState) {
		case Pororo.PAUSE:
			//pororotmppos = 
			//curPororo = Resource.getInstance().idel[pororo.idel[pororo.aniIndex]];
			//g.drawImage(
			//		Resource.getInstance().idel[pororo.idel[pororo.aniIndex]],
			//		pororo.posX + (pororo.indexX * 20), pororo.posY
			//				- pororo.playPos[pororo.posIndex], null);
			break;
		case Pororo.IDEL:
			curPororopos = pororo.posY- pororo.playPos[pororo.posIndex];
			curPororo = Resource.getInstance().idel[pororo.idel[pororo.aniIndex]];
			//g.drawImage(
			//		Resource.getInstance().idel[pororo.idel[pororo.aniIndex]],
			//		pororo.posX + (pororo.indexX * 20), pororo.posY
			//				- pororo.playPos[pororo.posIndex], null);
			break;
		case Pororo.JUMP:
			curPororopos = pororo.posY- pororo.playPos[pororo.posIndex];
			curPororo = Resource.getInstance().jump[pororo.jump[pororo.aniIndex]];
			//g.drawImage(
			//		Resource.getInstance().jump[pororo.jump[pororo.aniIndex]],
			//		pororo.posX + (pororo.indexX * 20), pororo.posY
			//				- pororo.playPos[pororo.posIndex], null);
			break;
		case Pororo.DOWN:
			curPororopos = pororo.posY- pororo.playPos[pororo.posIndex];

			switch (pororo.downState) {
			case Pororo.NONE_DOWN:
				curPororo = Resource.getInstance().down[pororo.down[0]];
				//g.drawImage(Resource.getInstance().down[pororo.down[0]],
				//		pororo.posX + (pororo.indexX * 20), pororo.posY
				//				- pororo.playPos[pororo.posIndex], null);
				break;
			case Pororo.GOOD_DOWN:
				curPororo = Resource.getInstance().down[pororo.down[1]];
				//g.drawImage(Resource.getInstance().down[pororo.down[1]],
				//		pororo.posX + (pororo.indexX * 20), pororo.posY
				//				- pororo.playPos[pororo.posIndex], null);
				break;
			case Pororo.FAIL_DOWN:
				curPororo = Resource.getInstance().down[pororo.down[2]];
				//g.drawImage(Resource.getInstance().down[pororo.down[2]],
				//		pororo.posX + (pororo.indexX * 20), pororo.posY
				//				- pororo.playPos[pororo.posIndex], null);
				break;
			}
			break;
		case Pororo.FAIL:
			curPororopos = pororo.posY- pororo.playPos[pororo.posIndex];

			curPororo = Resource.getInstance().turn[pororo.fail[pororo.aniIndex]];
			//g.drawImage(
			//		Resource.getInstance().turn[pororo.fail[pororo.aniIndex]],
			//		pororo.posX + (pororo.indexX * 20), pororo.posY
			//				- pororo.playPos[pororo.posIndex], null);
			break;
		case Pororo.GOOD:
			curPororopos = pororo.posY- pororo.playPos[pororo.posIndex];
			curPororo = Resource.getInstance().turn[pororo.good[pororo.aniIndex]];
			//g.drawImage(
			//		Resource.getInstance().turn[pororo.good[pororo.aniIndex]],
			//		pororo.posX + (pororo.indexX * 20), pororo.posY
			//				- pororo.playPos[pororo.posIndex], null);
			break;
		case Pororo.GAMEOVER:
			
			if (!pororo.isEnd)
			{
				curPororopos = pororo.posY- pororo.playPos[pororo.posIndex];
				curPororo = Resource.getInstance().gameover[pororo.gameover[pororo.aniIndex]];
				//g.drawImage(
				//				Resource.getInstance().gameover[pororo.gameover[pororo.aniIndex]],
				//				pororo.posX + (pororo.indexX * 20), pororo.posY
				//						- pororo.playPos[pororo.posIndex], null);
			}
			else
			{
				curPororopos = pororo.posY- pororo.end[pororo.posIndex];
				
				curPororo = Resource.getInstance().gameover[pororo.gameover[pororo.aniIndex]];
				//g.drawImage(
				//				Resource.getInstance().gameover[pororo.gameover[pororo.aniIndex]],
				//				pororo.posX + (pororo.indexX * 20), pororo.posY
				//						- pororo.end[pororo.posIndex], null);
			}
			break;
		case Pororo.SUCESS:
			if (!pororo.isEnd)
			{
				curPororopos = pororo.posY- pororo.playPos[pororo.posIndex];
				curPororo = Resource.getInstance().sucdcess[pororo.sucess[pororo.aniIndex]];
				//g.drawImage(
				//				Resource.getInstance().sucdcess[pororo.sucess[pororo.aniIndex]],
				//				pororo.posX + (pororo.indexX * 20), pororo.posY
				//						- pororo.playPos[pororo.posIndex], null);
			}
			else
			{
				curPororopos = pororo.posY- pororo.end[pororo.posIndex];
				//System.out.println("================posIndex" + pororo.posIndex);
				
				curPororo = Resource.getInstance().sucdcess[pororo.sucess[pororo.aniIndex]];
				//g.drawImage(
				//				Resource.getInstance().sucdcess[pororo.sucess[pororo.aniIndex]],
				//				pororo.posX + (pororo.indexX * 20), pororo.posY
				//						- pororo.end[pororo.posIndex], null);
			}
			break;
		}

		
		g.drawImage(curPororo,
				pororo.posX + (pororo.indexX * 20),curPororopos, null);
		if(pororo.aniState == Pororo.FAIL)
		{
			g.drawImage(
					Resource.getInstance().effectFail[pororo.aniIndex],
					pororo.posX + (pororo.indexX * 20) +42, pororo.posY
							- pororo.playPos[pororo.posIndex] -4, null);

		}
		else if(pororo.aniState == Pororo.GOOD)
		{
			g.drawImage(
					Resource.getInstance().effectGood[pororo.aniIndex],
					pororo.posX + (pororo.indexX * 20) +42, pororo.posY
							- pororo.playPos[pororo.posIndex] -4, null);

		}
		switch (pororo.aniState) {
		case Pororo.IDEL:
		case Pororo.JUMP:
		case Pororo.PAUSE:
		case Pororo.DOWN:
			// *
			if (pororo.puzzleIndex > 0
					&& Resource.getInstance().piece.length > pororo.puzzleIndex - 1
					&& Resource.getInstance().piece[pororo.puzzleIndex - 1] != null) {
				// 들고있는 조각 그리기
				int height = Resource.getInstance().piece[pororo.puzzleIndex - 1]
						.getHeight(null);
				switch (height) {
				case 44:
					g
							.drawImage(
									Resource.getInstance().piece[pororo.puzzleIndex - 1],
									pororo.posX + (pororo.indexX * 20) + 55,
									pororo.posY
											- pororo.playPos[pororo.posIndex]
											+ 29, null);
					if (collisionRect)
						// 충돌영역 그리기
						g
								.drawRect(
										pororo.posX + (pororo.indexX * 20) + 55
												+ 40 + 2,
										pororo.posY
												- pororo.playPos[pororo.posIndex]
												+ 29 + 2,
										Resource.getInstance().piece[pororo.puzzleIndex - 1]
												.getWidth(null) - 40 - 4,
										Resource.getInstance().piece[pororo.puzzleIndex - 1]
												.getHeight(null) - 4);
					break;
				case 84:
					g
							.drawImage(
									Resource.getInstance().piece[pororo.puzzleIndex - 1],
									pororo.posX + (pororo.indexX * 20) + 55,
									pororo.posY
											- pororo.playPos[pororo.posIndex]
											+ 9, null);
					if (collisionRect)
						// 충돌영역 그리기
						g
								.drawRect(
										pororo.posX + (pororo.indexX * 20) + 55
												+ 40 + 2,
										pororo.posY
												- pororo.playPos[pororo.posIndex]
												+ 9 + 2,
										Resource.getInstance().piece[pororo.puzzleIndex - 1]
												.getWidth(null) - 40 - 4,
										Resource.getInstance().piece[pororo.puzzleIndex - 1]
												.getHeight(null) - 4);
					break;
				case 124:
					g
							.drawImage(
									Resource.getInstance().piece[pororo.puzzleIndex - 1],
									pororo.posX + (pororo.indexX * 20) + 55,
									pororo.posY
											- pororo.playPos[pororo.posIndex]
											- 11, null);
					if (collisionRect)
						// 충돌영역 그리기
						g
								.drawRect(
										pororo.posX + (pororo.indexX * 20) + 55
												+ 40 + 2,
										pororo.posY
												- pororo.playPos[pororo.posIndex]
												- 11 + 2,
										Resource.getInstance().piece[pororo.puzzleIndex - 1]
												.getWidth(null) - 40 - 4,
										Resource.getInstance().piece[pororo.puzzleIndex - 1]
												.getHeight(null) - 4);
					break;
				}
			}
			/**/
			break;
		}
	}

	public void finish(Graphics2D g) {
		// 게임 성공 실패 문구
		if (GameData.getInstance().textIndex[0] < 10) {
			g.setComposite(AlphaComposite
					.getInstance(AlphaComposite.SRC_OVER,1));
			// 퍼 그림
			g.drawImage(Resource.getInstance().text[6], 316,
					207 - GameData.getInstance().textPu[GameData
							.getInstance().textIndex[0]], null);
			// 즐 그림
			g.drawImage(Resource.getInstance().text[7], 397,
					207 - GameData.getInstance().textPu[GameData
							.getInstance().textIndex[1]], null);
			// 완 그림
			g.drawImage(Resource.getInstance().text[8], 484,
					207 - GameData.getInstance().textPu[GameData
							.getInstance().textIndex[2]], null);
			// 성 그림
			g.drawImage(Resource.getInstance().text[9], 569,
					207 - GameData.getInstance().textPu[GameData
							.getInstance().textIndex[3]], null);
			
			// 2012.06.15 YJY 축구놀이의 OK 버튼을 퍼즐놀이에 추가
			g.drawImage(Resource.getInstance().img_btn_ok[mb_btnAniId], 654, 250, null);
			mb_btnAniId++;
			if(mb_btnAniId > 1)
				mb_btnAniId = 0;
		}
	}
	private int mb_btnAniId;

	public void gameover(Graphics2D g) {
		// 게임 성공 실패 문구
		if (GameData.getInstance().textIndex[0] < 14) {
			g.setComposite(AlphaComposite
					.getInstance(AlphaComposite.SRC_OVER,
							GameData.getInstance().alphaGame[GameData
									.getInstance().textIndex[0]]));
			// 게임 그림
			g.drawImage(Resource.getInstance().text[4], 318,
					216 - GameData.getInstance().textSi[GameData
							.getInstance().textIndex[0]], null);
			g.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 1));
			if (GameData.getInstance().textIndex[0] >= 5) {
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER,
						GameData.getInstance().alphaFinish[GameData
								.getInstance().textIndex[1]]));
				// 종료 그림
				g.drawImage(Resource.getInstance().text[5], 484,
						211 - GameData.getInstance().textJak[GameData
								.getInstance().textIndex[1]], null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
			}
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
		// 나가기 팝업
	}

	public void finishPopup(Graphics2D g) {
		// 게임 종료 팝업
		g.drawImage(Resource.getInstance().finishPop, 280, 116, null);

		if (GameData.getInstance().finishCur) {
			g.drawImage(Resource.getInstance().btnFin[0], 314, 326, null);
			g.drawImage(Resource.getInstance().btnRe[1], 491, 326, null);
		} else {
			g.drawImage(Resource.getInstance().btnFin[1], 314, 326, null);
			g.drawImage(Resource.getInstance().btnRe[0], 491, 326, null);
		}

		// 점수
		int point = GameData.getInstance().score;

		//for (int i = 0; i < GameData.getInstance().score.length; i++) {
		//	point += GameData.getInstance().successScore[i];
		//}

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
		if (point >= 260000) {
			imageIndex = 3;
		} else if (point >= 230000) {
			imageIndex = 2;
		} else if (point >= 190000) {
			imageIndex = 1;
		}
		g
				.drawImage(Resource.getInstance().endRank[imageIndex], 317,
						200 - GameData.getInstance().endRank[GameData
								.getInstance().rankAni], null);
		
	}

	public void error(Graphics2D g) {
		// 게임 에러 팝업
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