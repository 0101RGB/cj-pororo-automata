package pororo.com.game.puzzle;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.game.puzzle.object.MapData;
import pororo.com.game.puzzle.object.Pororo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;


// 게임에 관한 정보를 가지고 있는다
public final class GameData {
	public static final int LOADING = 0; // 리소스 로딩
	public static final int INIT = 1; // 준비시작 문구
	public static final int PLAY = 2; // 게임 실행
	public static final int FINISH = 3; // 게임 성공
	public static final int GAMEOVER = 4; // 게임종료
	public static final int EXIT_POPUP = 5; // 나가기 팝업
	public static final int FINISH_POPUP = 6; // 종료팝업
	public static final int ERROR = 7; // 에러

	
	public static final int TEXT_NONE = -1;
	public static final int TEXT_READY = 0;
	public static final int TEXT_START = 1;
	public static final int TEXT_GAMEOVER = 2;
	public static final int TEXT_SECCESS = 3;
	
	public boolean exitCur = true;
	public boolean finishCur = true;
	
	public int gameState = LOADING; // 게임의 현재 진행 상태

	public int maxStage;// 스테이지수
	
	public int score = 0;// 점수
	public int successtime = 0;
	public int time;//남은시간
	public int failTime;
	public int totalTime;//남은시간
	public double currentTime;// 경과시간 계산용
	
	public int textIndex[] = { 0, 0,0 ,0 };
	//public int[] ready;// 준비
	//public int[] start;// 시작
	//public int[] gameover;// 게임종료
	//public int[] clear;// 퍼즐완성
	//public int[] bonus;// 획득점수
	
	//public float[] readyAlpha;// 준비
	//public float[] startAlpha;// 시작
	//public float[] gameoverAlpha;// 게임종료
	//public float[] clearAlpha;// 퍼즐완성
	//public float[] bonusAlpha;// 획득점수
	
	// 각종 문구 위치 에니메이션 정의
	public int textJun[] = { 7, 3, 0, -2, -1, 0, 0, 0, 0, 0 };// new int[10];
	public int textBi[] = { 7, 3, 0, -2, -1, 0, 0, 0 };// new int[8];
	public int textSi[] = { -7, -3, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };// new
	// int[16];
	public int textJak[] = { -7, -3, 0, 2, 1, 0, 0, 0, 0 };// new int[9];
	public int textGame[] = { 6, 2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };// new
	// int[14];
	public int textFinish[] = { 7, 3, 0, -2, -1, 0, 0, 0, 0 };// new int[9];
	//public int textSeong[] = { -30, -5, 20, 10, 0, 0, 0, 0 };// new int[8];
	//public int textGong[] = { -30, -5, 20, 10, 0 };// new int[5];
	public int textPu[] = { 51, 24, -3, -7, -4, 0, -1, -2, -1, 0 };// new int[10];
	public int textzzle[] = { -51, -24, 3, 7, 4, 0, 1, 2, 1, 0 };// new int[10];
	public int textWan[] = { 51, 24, -3, -7, -4, 0, -1, -2, -1, 0 };// new int[10];
	public int textSung[] = { -51, -24, 3, 7, 4, 0, 1, 2, 1, 0 };// new int[10];

	// 각종 문구 에니메이션 알파값 정의
	public float alphaJun[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 0.75f, 0.5f,
			0.25f };// new int[10];
	public float alphaBi[] = { 0.25f, 0.63f, 1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new
	// int[8];
	public float alphaSi[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f,
			1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new int[16];
	public float alphaJak[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 0.75f, 0.5f,
			0.25f };// new int[9];
	public float alphaGame[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f,
			1f, 0.75f, 0.5f, 0.25f };// new int[14];
	public float alphaFinish[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 0.75f, 0.5f,
			0.25f };// new int[9];
	//public float alphaSeong[] = { 0.25f, 0.5f, 75f, 1f, 1f, 1f, 1f, 1f };// new
	// int[8];
	//public float alphaGong[] = { 0.25f, 0.5f, 75f, 1f, 1f, 1f };// new int[5];

	public int textState = TEXT_READY;
	public int endRank[] = { 6, 4, 2, 0, 2, 4 };
	public Pororo pororo = new Pororo();
	
	public MapData mapData = new MapData();
	public int tileCount = 0;//타일 갯수
	public int rankAni = 0;
	
	private static GameData instance = new GameData();

	// 랜덤 생성 맵 저장
	private static final int maplength = 10;
	
	public int mapIndex[];
	private static final Random rnd = new Random();
	
	public int stage = 0;//스테이지
	
	public Vector piece;//퍼즐조각정보
	
	public static GameData getInstance() {
		if (instance == null) {
			instance = new GameData();
		}
		return instance;
	}

	public int randemMap(int scope) {
		return (Math.abs(rnd.nextInt()) % scope);
	}

	
	public void setData()
	{
		textIndex[0] = 0;
		textIndex[1] = 0;
		textIndex[2] = 0;
		textIndex[3] = 0;

		textState = TEXT_READY;
		
		exitCur = true;
		finishCur = true;
		
		if (mapIndex != null) {
			mapIndex = null;
		}
		
		mapIndex = new int[maplength];
		int scope = 20;
		
		for (int i = 0; i < mapIndex.length; i++) {
			boolean live = true;
			while (live) {
				int tmp = 0;
				if(i<=2)
				{
					scope = 20;
					tmp = randemMap(scope) +1;
				}
				else if(i>2 && i<7)
				{
					scope = 30;
					tmp = randemMap(scope) + 20;
					
				}
				else 
				{
					scope = 20;
					tmp = randemMap(scope) + 50;
				}
				mapIndex[i] = tmp;
				if (i <= 0) {
					live = false;
				} else {
					for (int j = 0; j < i; j++) {
						if (mapIndex[i] == mapIndex[j]) {
							break;
						}
						if (j == i - 1) {
							live = false;
						}
					}
				}
			}
			
		}

	}
	public void start() {
		// 최초 시작
		// 스테이지를 maxStage범위에서 랜덤 생성
		

		try {
			if (StateValue.isUrlLive) {
				if (!SceneManager.getInstance()
						.loadBackgroundImage(
								new URL(StateValue.liveResource
										+ "bg/puzzle/game_bg.gif"))) {
				}

			} else {
				if (!SceneManager.getInstance()
						.loadBackgroundImage(
								new URL(StateValue.testResource
										+ "bg/puzzle/game_bg.gif"))) {
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		pororo.start();
	}

	public void restart() {
		// 다시 시작
		
		textIndex[0] = 0;
		textIndex[1] = 0;
		textIndex[2] = 0;
		textIndex[3] = 0;

		textState = TEXT_READY;
		
		exitCur = true;
		finishCur = true;
		stage = 0;
		
		// 맵 정보 로딩
		setData();
		mapData.loadMap(mapIndex[stage]);
		// 맵 이미지 로딩
		Resource.getInstance().map(mapIndex[stage], mapData.pieceCount);
		gameState = INIT;
		System.out.println("gameState : INIT");

		
		mapData.loadMap(mapIndex[stage]);
		Resource.getInstance().map(mapIndex[stage], mapData.pieceCount);
		start();
		score = 0;
		successtime = 0;
		
		time = totalTime;
		gameState = INIT;
		
		pororo.restart();
	}

	public void nextstageStart() {
		// 다시 시작
		


		textState = TEXT_READY;
		
		exitCur = true;
		finishCur = true;
		stage = stage +1;
		mapData.loadMap(mapIndex[stage]);
		Resource.getInstance().map(mapIndex[stage], mapData.pieceCount);
		//score = 0;
		successtime = 0;
		
		time = totalTime;
		gameState = INIT;
		
		textIndex[0] = 0;
		textIndex[1] = 0;
		textIndex[2] = 0;
		textIndex[3] = 0;
		
		pororo.restart();
	}
	
	public void changemap()
	{
		
	}
	
	public void process() {
		// 게임정보 처리
		switch (gameState) {
		case LOADING:// 이미지 로딩

			System.out.println("gameState : LOADING");
			if (!Resource.getInstance().loading()) {
				// 맵 정보 로딩
				setData();
				//test
				//mapIndex[0] = 54;
				mapData.loadMap(mapIndex[stage]);
				// 맵 이미지 로딩
				Resource.getInstance().map(mapIndex[stage], mapData.pieceCount);
				start();
				gameState = INIT;
				System.out.println("gameState : INIT");
			}
			break;
		case INIT:// 준비시작 문구
			init();
			pororo.process();
			//gameState = PLAY;
			currentTime = System.currentTimeMillis();
			//System.out.println("gameState : PLAY");
			break;
		case PLAY:// 게임 실행
			play();
			pororo.process();
			break;
		case FINISH:// 게임 성공 문구
			finish();
			play();
			pororo.process();
			break;
		case GAMEOVER:// 게임종료 문구
			gameover();
			play();
			pororo.process();
			break;
		case EXIT_POPUP:// 나가기 팝업 문구
			exitPopup();
			break;
		case FINISH_POPUP:// 게임종료 팝업
			finishPopup();
			break;
		}
	}
	public void init() {
		// 게임시작 문구
		// 준비 시작
		switch (textState) {
		case TEXT_READY:
			if (textIndex[0] >= 14) {
				textState = TEXT_START;
				textIndex[0] = 0;
				textIndex[1] = 0;
				textIndex[2] = 0;
				textIndex[3] = 0;
				return;
			} else {
				if(textIndex[0] == 0) {
					Resource.getInstance().sound.playSound(Resource.SOUND_START);
				}
				if (textIndex[0] >= 2) {
					textIndex[1]++;
				}
				textIndex[0]++;
			}
			break;
		case TEXT_START:
			if (textIndex[0] >= 15) {
				textState = TEXT_NONE;
				textIndex[0] = 0;
				textIndex[1] = 0;
				//pr.firstMove = false;
				//nowTime = System.currentTimeMillis();
				//((Listener) game).listener(MazeGPlay.GAME_PLAY);
				gameState = PLAY;
				//pororo.aniIndex=0;
				//pororo.aniStateTube = pororo.TUBE_DOWN;
				return;
			} else {
				if (textIndex[0] >= 7) {
					textIndex[1]++;
				}
				textIndex[0]++;
			}
			break;
		case TEXT_GAMEOVER:
			if (textIndex[0] >= 13) {
				textIndex[0] = 0;
				textIndex[1] = 0;
				//((Listener) game).listener(MazeGPlay.GAME_FINISH_POPUP);
				
				gameState = FINISH_POPUP;
				return;
			} else {
				if (textIndex[0] >= 5) {
					textIndex[1]++;
				}
				textIndex[0]++;
			}

			break;
		}
		//pororo.process();
	}

	public void play() {
		if(gameState == PLAY && pororo.aniState != pororo.PAUSE) {
		double t = System.currentTimeMillis();
		time -= (t - currentTime);
		//System.out.println("=========" + time);
		currentTime = t;
		if( time <= 0 && gameState != GAMEOVER){
			gameState = GAMEOVER;
			SceneManager.getInstance().sendScore_gravity(GameData.getInstance().score);
			SceneManager.getInstance().send_gravity(11);
			// 게임종료 문구 세팅
			pororo.aniState = Pororo.GAMEOVER;
			pororo.puzzleIndex = -2;
			System.out.println("gameState : GAMEOVER");
		}
		}
		// 게임 성공시 성공 문구 세팅
		//pororo.process();
	}

	public void finish() {
		
		if (textIndex[0] >= 9) {
			if(mapIndex.length == (stage+1))
			{
				gameState = FINISH_POPUP;
				return;
			}
			textIndex[0] = 6;
			textIndex[1] = 6;
			textIndex[2] = 6;
			textIndex[3] = 6;
			//((Listener) game).listener(MazeGPlay.GAME_SUCCESS_FINISH);
			return;
		} else {
			//if(textIndex[0] == 0)
				//Resource.getInstance().sound.playSound(Resource.SOUND_CLEAR);
			textIndex[0]++;
			textIndex[1]++;
			textIndex[2]++;
			textIndex[3]++;
		}
		//pororo.process();
	}
	
	public void gameover() {
		if (textIndex[0] >= 13) {
			textIndex[0] = 0;
			textIndex[1] = 0;
			gameState = FINISH_POPUP;
			rankAni = 0;
			//((Listener) game).listener(MazeGPlay.GAME_FINISH_POPUP);
			return;
		} else {
			if(textIndex[0] == 0)
				Resource.getInstance().sound.playSound(Resource.SOUND_GAMEOVER);
			if (textIndex[0] >= 5) {
				textIndex[1]++;
			}
			textIndex[0]++;
		}
		//pororo.process();
	}
	
	public void setscoreGood()
	{
		int bonus = 0;
		successtime = successtime +1;
		bonus = (successtime -1) *100 + 500;
		if(mapData.pieceCount == successtime)
			bonus = bonus + 5000;
		
		score = score + bonus;
	}
	
	public void setscoreFail()
	{
		successtime = 0;
	}
	
	public void scoretimebonus() {
		int tmp = time /10;
		System.out.println("score = " + score);
		System.out.println("time = " + time + " " + tmp);
		score = score + tmp;
		System.out.println("total = " + score);
	}
	
	public void exitPopup() {
		pororo.process();
	}
	
	public void finishPopup() {
			rankAni++;
			rankAni = rankAni % endRank.length;
		pororo.process();
	}

	public void destroy() {
		if(piece != null) {
			piece.removeAllElements();
			piece = null;
		}
		instance = null;
	}
}