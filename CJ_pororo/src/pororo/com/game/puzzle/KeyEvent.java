package pororo.com.game.puzzle;

import pororo.com.automata.Constant;
import pororo.com.game.GameTitle;
import pororo.com.SceneManager;
import pororo.com.game.puzzle.object.PieceData;
import pororo.com.game.puzzle.object.Pororo;

import org.havi.ui.event.HRcEvent;


// 게임 키 이벤트를 처리 한다
public final class KeyEvent {
	private static KeyEvent instance = new KeyEvent();

	// 게임정보
	// private GameData gameData = GameData.getInstance();

	public static KeyEvent getInstance() {
		if (instance == null)
			instance = new KeyEvent();
		return instance;
	}

	public void processKey(int event) {

		if (GameData.getInstance().gameState != GameData.LOADING
				&& ImageDraw.getInstance().collisionRect) {// 컬리젼 영역이 안나오면 게임 핫키가 안먹힌다
			switch (event) {
			case HRcEvent.VK_W:
				if (GameData.getInstance().stage < (GameData.getInstance().maxStage))
					GameData.getInstance().stage++;
				GameData.getInstance().restart();
				break;
			case HRcEvent.VK_S:
				if (GameData.getInstance().stage > 0)
					GameData.getInstance().stage--;
				GameData.getInstance().restart();
				break;
			}
		}
		switch (GameData.getInstance().gameState) {
		case GameData.LOADING:// 이미지 로딩
			break;
		case GameData.INIT:// 준비시작 문구
			break;
		case GameData.PLAY:// 게임 실행
			play(event);
			break;
		case GameData.FINISH:// 게임 성공 문구
			finish(event);
			break;
		case GameData.GAMEOVER:// 게임종료 문구
			break;
		case GameData.EXIT_POPUP:// 게임종료 문구
			exitpopup(event);
			break;
		case GameData.FINISH_POPUP:// 게임종료 문구
			finishpopup(event);
			break;
		case GameData.ERROR:// 게임종료 문구
			System.out.println("BoardGPlay STATE_ERROR");
			switch (event) {
			case HRcEvent.VK_ENTER:
			case Constant.KEY_PREV:
				SceneManager.getInstance().load_kind = true; //120523_1
				SceneManager.getInstance().setChangeScene(
						SceneManager.SATAE_GAME_MENU);
				break;
			}
			break;
		}
	}
	
	public void finish(int event)
	{
		//switch (event) {

		//case HRcEvent.VK_ENTER:
		if(GameData.getInstance().mapIndex.length != (GameData.getInstance().stage+1))
		{
			GameData.getInstance().nextstageStart();
		}

		//	break;
		//}
	}
	public void exitpopup(int event)
	{
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
				//GameData.getInstance().reStart();
				Pororo pororo = GameData.getInstance().pororo;
				pororo.Npause();
				GameData.getInstance().gameState = GameData.PLAY;
			}
			break;
		}
	}
	
	public void finishpopup(int event)
	{
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
				//GameData.getInstance().setData();
				GameData.getInstance().restart();
				//state = GAME_INIT;
			}
			break;
		}
	
	}
	
	public void play(int event) {
		// 플레이중 키 이벤트
		switch (event) {
		case HRcEvent.VK_UP:
			if (!ImageDraw.getInstance().collisionRect) {// 컬리젼 영역이 안나오면 게임 핫키가 안먹힌다
				break;
			}
			// GameData.getInstance().pororo.posIndex++;
			GameData.getInstance().pororo.puzzleIndex--;
			if (GameData.getInstance().pororo.puzzleIndex <= 0) {
				GameData.getInstance().pororo.puzzleIndex = GameData
						.getInstance().mapData.pieceCount;
			}
			GameData.getInstance().tileCount = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 7; j++) {
					for (int k = 0; k < 4; k++) {
						if (GameData.getInstance().mapData.map[i][j].piece[k] == GameData
								.getInstance().pororo.puzzleIndex) {
							GameData.getInstance().tileCount++;
						}
					}
				}
			}
			GameData.getInstance().pororo.collision = false;
			break;
		case HRcEvent.VK_DOWN:
			if (!ImageDraw.getInstance().collisionRect) {// 컬리젼 영역이 안나오면 게임 핫키가 안먹힌다
				break;
			}
			// GameData.getInstance().pororo.posIndex--;
			GameData.getInstance().pororo.puzzleIndex++;
			if (GameData.getInstance().pororo.puzzleIndex > GameData
					.getInstance().mapData.pieceCount) {
				GameData.getInstance().pororo.puzzleIndex = 1;
			}
			GameData.getInstance().tileCount = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 7; j++) {
					for (int k = 0; k < 4; k++) {
						if (GameData.getInstance().mapData.map[i][j].piece[k] == GameData
								.getInstance().pororo.puzzleIndex) {
							GameData.getInstance().tileCount++;
						}
					}
				}
			}
			GameData.getInstance().pororo.collision = false;
			break;
		case HRcEvent.VK_RIGHT:
			if (GameData.getInstance().pororo.indexX < 36) {
				switch (GameData.getInstance().pororo.horizontal) {
				case Pororo.NONE:
					GameData.getInstance().pororo.horizontal = Pororo.RIGHT2;
					break;
				case Pororo.LEFT1:
					GameData.getInstance().pororo.horizontal = Pororo.RIGHT1;
				}
			}
			break;
		case HRcEvent.VK_LEFT:
			if (GameData.getInstance().pororo.indexX > 0) {
				switch (GameData.getInstance().pororo.horizontal) {
				case Pororo.NONE:
					GameData.getInstance().pororo.horizontal = Pororo.LEFT2;
					break;
				case Pororo.RIGHT1:
					GameData.getInstance().pororo.horizontal = Pororo.LEFT1;
				}
			}
			break;
		case HRcEvent.VK_ENTER:
			
			if(GameData.getInstance().pororo.aniState == GameData.getInstance().pororo.FAIL ||
					GameData.getInstance().pororo.aniState == GameData.getInstance().pororo.GOOD)
				break;
			if (GameData.getInstance().pororo.collision) {
				System.out.println("collision Yes");
				// 조각 붙이기
				if (GameData.getInstance().pororo.puzzleIndex > 0) {
					((PieceData) (GameData.getInstance().piece
							.elementAt(GameData.getInstance().pororo.puzzleIndex - 1))).show = true;
				}
				
				// 성공 이펙트
				Resource.getInstance().sound.playSound(Resource.SOUND_GOOD);

				GameData.getInstance().pororo.aniBefore = GameData
						.getInstance().pororo.aniState;
				GameData.getInstance().pororo.aniState = Pororo.GOOD;
				GameData.getInstance().pororo.aniIndex = 0;
				GameData.getInstance().pororo.downState = Pororo.GOOD_DOWN;
				GameData.getInstance().setscoreGood();
			} else {
				System.out.println("collision No");
				Resource.getInstance().sound.playSound(Resource.SOUND_FAIL);
				// 시간 감소
				GameData.getInstance().time -= GameData.getInstance().failTime;
				// 실패 이펙트
				GameData.getInstance().pororo.aniBefore = GameData
						.getInstance().pororo.aniState;
				GameData.getInstance().pororo.aniState = Pororo.FAIL;
				GameData.getInstance().pororo.aniIndex = 0;
				GameData.getInstance().pororo.downState = Pororo.FAIL_DOWN;
				GameData.getInstance().setscoreFail();
			}
			break;
		//case HRcEvent.VK_ESCAPE:
		case Constant.KEY_PREV:
			GameData.getInstance().exitCur = true;
			GameData.getInstance().finishCur = true;
			Pororo pororo = GameData.getInstance().pororo;
			pororo.pause();
			GameData.getInstance().gameState = GameData.EXIT_POPUP;
			break;
		}
		
	}

	public void destroy() {
		instance = null;
	}
}