package pororo.com.game.maze;

import java.awt.Graphics2D;

import org.havi.ui.event.HRcEvent;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;
import pororo.com.game.GameTitle;
import pororo.com.game.maze.object.Crong;

public class MazeGPlay extends Scene implements Listener {
	private static final int GAME_LOADING = 0;
	private static final int GAME_INIT = 1;
	public static final int GAME_PLAY = 2;
	public static final int GAME_SUCCESS = 3;
	public static final int GAME_SUCCESS_FINISH = 4;
	public static final int GAME_OVER = 5;
	public static final int GAME_FINISH_POPUP = 6;
	private static final int GAME_EXIT_POPUP = 7;
	public static final int GAME_ERROR = 8;

	private int state = GAME_LOADING;

	private boolean isSeccess = false;

	public MazeGPlay() {
		state = GAME_LOADING;
		GameData.getInstance().setScene(this);
	}

	public void draw(Graphics2D g) {
		switch (state) {
		case GAME_LOADING:
			// 로딩게이지
			ImageDraw.getInstance().loading(g);
			break;
		case GAME_INIT:
			// 로딩게이지
			ImageDraw.getInstance().play(g);
			ImageDraw.getInstance().init(g);
			break;
		case GAME_PLAY:
			// 게임 실행
			ImageDraw.getInstance().play(g);
			break;
		case GAME_SUCCESS:
			ImageDraw.getInstance().play(g);
			ImageDraw.getInstance().init(g);
			break;
		case GAME_OVER:
			ImageDraw.getInstance().play(g);
			ImageDraw.getInstance().init(g);
			break;
		case GAME_FINISH_POPUP:
			ImageDraw.getInstance().play(g);
			ImageDraw.getInstance().finishPopup(g);
			break;
		case GAME_EXIT_POPUP:
			ImageDraw.getInstance().play(g);
			ImageDraw.getInstance().exitPopup(g);
			break;
		case GAME_ERROR:
			ImageDraw.getInstance().error(g);
			break;
		}
	}

	public void process(int i) {
		switch (state) {
		case GAME_LOADING:
			// 리소스 로딩
			if (!Resource.getInstance().loading()) {
				GameData.getInstance().start();
				state = GAME_INIT;
			}
			break;
		case GAME_INIT:
			GameData.getInstance().init();
			break;
		case GAME_PLAY:
			// 게임 실행
			GameData.getInstance().play(true);
			break;
		case GAME_SUCCESS:
			GameData.getInstance().play(false);
			GameData.getInstance().init();
			break;
		case GAME_OVER:
			GameData.getInstance().play(false);
			GameData.getInstance().init();
			break;
		case GAME_FINISH_POPUP:
			GameData.getInstance().finishPopup();
			GameData.getInstance().play(false);
			break;
		case GAME_EXIT_POPUP:
			break;
		}
	}

	public void processKey(Object nn, int event) {
		if(state == GAME_LOADING) {
			return;
		}
		switch (event) { // 핫키
		/*
		case HRcEvent.VK_U:
			if (GameData.getInstance().stage < 4) {
				GameData.getInstance().stage++;
				GameData.getInstance().changeMap();
			}
			break;
		case HRcEvent.VK_D:
			if (GameData.getInstance().stage > 0) {
				GameData.getInstance().stage--;
				GameData.getInstance().changeMap();
			}
			break;
		case HRcEvent.VK_R:
			GameData.getInstance().setData();
			GameData.getInstance().start();
			state = GAME_INIT;
			break;
		/**/
		case HRcEvent.VK_ESCAPE:
		case Constant.KEY_PREV:
			if (state == GAME_PLAY) {
				state = GAME_EXIT_POPUP;
				GameData.getInstance().pause();
			}
			break;
			/*
		case HRcEvent.VK_F:
			if (state != GAME_FINISH_POPUP) {
				GameData.getInstance().textState = GameData.TEXT_NONE;
				GameData.getInstance().textState = GameData.TEXT_NONE;
				state = GAME_FINISH_POPUP;
				GameData.getInstance().pause();
			}
			break;
			/**/
		default:
			switch (state) {
			case GAME_INIT:
				break;
			case GAME_PLAY:
				// 게임 실행
				KeyEvent.getInstance().playKeyEvent(event);
				break;
			case GAME_SUCCESS:
				if (isSeccess) {
					switch (event) {
					case HRcEvent.VK_ENTER:
						if (GameData.getInstance().stage < 4) {
							GameData.getInstance().stage++;
							GameData.getInstance().changeMap();
							GameData.getInstance().textState = GameData.TEXT_READY;
							GameData.getInstance().textIndex[0] = 0;
							GameData.getInstance().textIndex[1] = 0;
							GameData.getInstance().cr.aniState  = Crong.CRONG_IDEL;
							GameData.getInstance().cr.aniIndex = 0;
							state = GAME_INIT;
						}
						break;
					}
				}
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
					}
					else {
						GameData.getInstance().setData();
						GameData.getInstance().start();
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
						GameData.getInstance().reStart();
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

	public void destroyScene() {
		// 종료시 모든처리(리소스 해제)
		GameData.getInstance().destroy();
		ImageDraw.getInstance().destroy();
		KeyEvent.getInstance().destroy();
		MapData.getInstance().destroy();
		Resource.getInstance().destroy1();
		Resource.getInstance().destroy2();
	}

	public void listener(int s) {
		switch (s) {
		case GAME_LOADING:
			break;
		case GAME_INIT:
			break;
		case GAME_PLAY:
			state = GAME_PLAY;
			break;
		case GAME_OVER:
			state = GAME_OVER;
		case GAME_SUCCESS:
			state = GAME_SUCCESS;
			break;
		case GAME_SUCCESS_FINISH:
			if (GameData.getInstance().stage >= 4) {
				if (state != GAME_FINISH_POPUP) {
					// 점수를 서버로 전송
					 int scoreSum = 0;
					 for(int i = 0 ; i < GameData.getInstance().successScore.length ; i++) {
						 scoreSum += GameData.getInstance().successScore[i];
					 }
					SceneManager.getInstance().sendScore_gravity(scoreSum);
					SceneManager.getInstance().send_gravity(11);
					GameData.getInstance().textState = GameData.TEXT_NONE;
					GameData.getInstance().textState = GameData.TEXT_NONE;
					state = GAME_FINISH_POPUP;
					GameData.getInstance().pause();
				}
			}
			else {
				isSeccess = true;
			}
			break;
		case GAME_FINISH_POPUP:
			if (state != GAME_FINISH_POPUP) {
				GameData.getInstance().rankAni = 0;
				state = GAME_FINISH_POPUP;
				GameData.getInstance().pause();
			}
			break;
		case GAME_ERROR:
			state = GAME_ERROR;
			break;
		}
	}
}
