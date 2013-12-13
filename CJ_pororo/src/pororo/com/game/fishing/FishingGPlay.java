// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MazeGPlay.java

package pororo.com.game.fishing;

import java.awt.Graphics2D;

import org.havi.ui.event.HRcEvent;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;
import pororo.com.game.GameTitle;
import pororo.com.game.fishing.Resource;
import pororo.com.game.fishing.ImageDraw;
import pororo.com.game.fishing.GameData;
import pororo.com.game.fishing.KeyEvent;
import pororo.com.log.Log;

public class FishingGPlay extends Scene implements Listener{
	private static final int GAME_LOADING = 0;
	private static final int GAME_INIT = 1;
	public static final int GAME_PLAY = 2;
	private static final int GAME_OVER = 3;
	private static final int GAME_SUCCESS = 4;
	private static final int GAME_END = 5;
	public static final int GAME_FINISH_POPUP = 6;
	private static final int GAME_EXIT_POPUP = 7;
	public static final int GAME_ERROR = 8;
	
	// 이미지
	private Resource resource = Resource.getInstance();
	// 화면그리기
	private ImageDraw imageDraw = ImageDraw.getInstance();
	// 키이벤트
	private KeyEvent keyEvent = KeyEvent.getInstance();
	// 게임정보
	private GameData gameData = GameData.getInstance();

	private int state = GAME_LOADING;

	public FishingGPlay() {
		System.out.println("Fishing Game Start");
		gameData.setScene(this);
	}

	public void draw(Graphics2D g) {
		// System.out.println("MazeGPlay");
		switch (state) {
		case GAME_LOADING:
			// 로딩게이지
			imageDraw.loading(g);
			break;
		case GAME_INIT:
			imageDraw.intro(g);
			break;
		case GAME_PLAY:
			// 게임 실행
			imageDraw.play(g);
			break;
		case GAME_OVER:
			imageDraw.gameover(g);
			break;
		case GAME_FINISH_POPUP:
//			imageDraw.play(g);
			imageDraw.finishPopup(g);
			break;
		case GAME_EXIT_POPUP:
			imageDraw.play(g);
			imageDraw.exitPopup(g);
			break;
		case GAME_SUCCESS:
			imageDraw.success(g);
			break;
		case GAME_END:
			imageDraw.end(g);
			break;
		case GAME_ERROR:
			imageDraw.error(g);
			break;
		}
	}

	public void process(int i) {
		// System.out.println("process");
		switch (state) {
		case GAME_LOADING:
			// 리소스 로딩
			if (!resource.loading()) {
				if(state != GAME_ERROR) {
					gameData.intro();
					state = GAME_INIT;
				}
			}
			break;
		case GAME_INIT:
			gameData.introProc();
			break;
		case GAME_PLAY:
			// 게임 실행
			// System.out.println("!!!!!!!!!!process:"+i);
			gameData.process();
			if(gameData.isGamePlay() == 1) {
				gameData.textState = gameData.TEXT_GAMEOVER;
				state = GAME_OVER;
				// 점수를 서버로 전송
				SceneManager.getInstance().sendScore_gravity(gameData.pr.getGrade());
				SceneManager.getInstance().send_gravity(11);
			}
			break;
		case GAME_OVER:
			gameData.introProc();
			break;
		case GAME_FINISH_POPUP:
			gameData.finishPopup();
			break;
		case GAME_EXIT_POPUP:
			break;
		case GAME_SUCCESS:
			break;
		case GAME_END:
			break;
		}
	}

	public void processKey(java.lang.Object nn, int event) {
		// resource.loadingMap();
		if(state == GAME_LOADING) {
			return;
		}

		switch (event) {
		case HRcEvent.VK_ESCAPE:
			// 랭킹나오게 종료
//			SceneManager.getInstance().load_kind = true;
//			SceneManager.getInstance().setChangeScene(
//					SceneManager.SATAE_GAMETITLE, GameTitle.STATE_TITLE_RANK);
//			// 점수를 서버로 전송
//			// SceneManager.getInstance().sendScore_gravity(2, mn_Score);
//			break;
		case Constant.KEY_PREV:
			// 랭킹없이 종료
			if (state == GAME_PLAY) {
				if (state != GAME_EXIT_POPUP) {
					state = GAME_EXIT_POPUP;
				}
			}
			break;
		default:
			switch (state) {
			case GAME_LOADING:
				break;
			case GAME_PLAY:
				keyEvent.playKeyEvent(event);
				break;
			case GAME_OVER:
				break;
			case GAME_SUCCESS:
				break;
			case GAME_END:
				break;
			case GAME_FINISH_POPUP:
				switch (event) {
				case HRcEvent.VK_RIGHT:
					GameData.getInstance().finishCur = !GameData.getInstance().finishCur;
					break;
				case HRcEvent.VK_LEFT:
					GameData.getInstance().finishCur = !GameData.getInstance().finishCur;
					break;
				case HRcEvent.VK_ENTER:
					if(!GameData.getInstance().finishCur) {
						 SceneManager.getInstance().load_kind=true; //120523_1
						 SceneManager.getInstance().setChangeScene(SceneManager.SATAE_GAME_MENU,
						 GameTitle.STATE_TITLE_RANK);
						// 점수를 서버로 전송
//						SceneManager.getInstance().sendScore_gravity(4, gameData.pr.getGrade());
					}
					else {
						GameData.getInstance().setData();
						GameData.getInstance().intro();
						GameData.getInstance().textState = gameData.TEXT_READY;
						state = GAME_INIT;
					}
					break;
				}
				
				break;
			case GAME_EXIT_POPUP:
				switch (event) {
				case HRcEvent.VK_RIGHT:
					GameData.getInstance().exitCur = !GameData.getInstance().exitCur;
					break;
				case HRcEvent.VK_LEFT:
					GameData.getInstance().exitCur = !GameData.getInstance().exitCur;
					break;
				case HRcEvent.VK_ENTER:
					if(GameData.getInstance().exitCur) {
						 SceneManager.getInstance().load_kind=true; //120523_1
						 SceneManager.getInstance().setChangeScene(SceneManager.SATAE_GAME_MENU);
					}
					else {
//						GameData.getInstance().reStart();
						gameData.exitCur = true;
						gameData.finishCur = true;
						state = GAME_PLAY;
					}
					break;
				}
				break;
			case GAME_ERROR:
				System.out.println("BoardGPlay STATE_ERROR");
				switch (event) {
				case HRcEvent.VK_ENTER:
				case Constant.KEY_PREV:
					SceneManager.getInstance().load_kind = true; //120523_1
					SceneManager.getInstance().setChangeScene(
							SceneManager.SATAE_PORTAL_MENU);
					break;
				}
				break;
			}
			break;
		}
	}
	
	public void listener(int s) {
		switch (s) {
		case GAME_LOADING:
			break;
		case GAME_INIT:
			break;
		case GAME_PLAY:
			gameData.start();
			state = GAME_PLAY;
			break;
		case GAME_OVER:
			state = GAME_OVER;
			break;
		case GAME_FINISH_POPUP:
			if (state != GAME_FINISH_POPUP) {
				state = GAME_FINISH_POPUP;
//				GameData.getInstance().pause();
				System.out.println("GAME_FINISH_POPUP Finish Popup");
			}
			break;
		case GAME_SUCCESS:
			state = GAME_SUCCESS;
			break;
			
		case GAME_ERROR:
			state = GAME_ERROR;
			break;
		
		}
	}

	public void destroyScene() {
		// 종료시 모든처리(리소스 해제)
		Log.trace("---fish destroy-- 1");
		gameData.destroy();
		gameData=null;
		
		Log.trace("---fish destroy-- 2");
		imageDraw.destroy();
		imageDraw = null;
		
		Log.trace("---fish destroy-- 3");
		keyEvent.destroy();
		keyEvent = null;
		
		Log.trace("---fish destroy-- 4");
		resource.destroy1();
		resource.destroy2();
		resource = null;
		Log.trace("---fish destroy-- 5");
	}

}
