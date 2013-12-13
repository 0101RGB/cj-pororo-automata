package pororo.com.game.maze;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.game.maze.object.Crong;
import pororo.com.game.maze.object.Effect;
import pororo.com.game.maze.object.Pororo;
import pororo.com.game.maze.object.Ticker;
import pororo.com.game.maze.object.Trap;

// 게임에 관한 정보를 가지고 있는다
public final class GameData {
	// 미로찾기 무적모드
	public static boolean isWall = false;

	public static final int ITEM_NONE = -1;
	public static final int ITEM_A = 0;
	public static final int ITEM_B = 1;
	public static final int ITEM_C = 2;
	public static final int ITEM_D = 3;
	public static final int ITEM_E = 4;
	public static final int ITEM_F = 5;
	public static final int ITEM_G = 6;
	public static final int ITEM_H = 7;
	public static final int ITEM_I = 8;
	public static final int ITEM_J = 9;

	public static final int ITEM_TRAP_B = 10;
	public static final int ITEM_TRAP_R = 11;

	public static final int ITEM_SCORE_50 = 20;
	public static final int ITEM_SCORE_100 = 21;
	public static final int ITEM_SCORE_200 = 22;

	public static final int ITEM_HIDE_ICE = 30;
	public static final int ITEM_HIDE_TIME = 31;
	public static final int ITEM_HIDE_SCORE = 32;

	public static final int MAP_SCOPE = 70;
	public static final int MAP_WIDTH = 20;
	public static final int MAP_HEIGHT = 11;
	public static final int TILE_SIZE = 32;

	public static final int MAP_X = 124;
	public static final int MAP_Y = 119;

	public static final int PORORO_X = 7;
	public static final int PORORO_Y = 58;

	public static final int MOVE_SPEED = 8;

	public static final int TEXT_NONE = -1;
	public static final int TEXT_READY = 0;
	public static final int TEXT_START = 1;
	public static final int TEXT_GAMEOVER = 2;
	public static final int TEXT_SECCESS = 3;

	private static GameData instance = new GameData();

	private static final Random rnd = new Random();

	// 아이템
	public Ticker[] item = new Ticker[3];
	// 문구/점수
	public Ticker ticker = new Ticker();
	// 이펙트
	public Effect[] effect = new Effect[3];
	public Effect bgEffect = new Effect();

	// 크롱
	public Crong cr = new Crong();
	// 뽀로로
	public Pororo pr = new Pororo();

	// 함정
	public Vector trap;

	// 랜덤 생성 맵 저장
	private int mapIndex[];

	// 플레이 시간
	public int successTime[] = new int[5];
	// 플레이 점수
	public int successScore[] = new int[5];

	// 시간 컨트롤
	private boolean isFirstTime = true;
	public int stageTime = 0;
	private double nowTime = 0;

	// 아이템 위치
	public int itemPos[][] = new int[3][2];

	// 현재 스테이지
	public int stage = 0;

	//획득한 아이템 수량
	private int itemFinishCount = 0;

	// public int mainItem = 3;
	// 아이템 갯수
	private int mainItemOrder[] = new int[3];
	private int subItemOrder[][] = new int[3][3];
	private int trapItemOrder[][] = new int[5][2];

	public Scene game = null;

	public boolean exitCur = true;
	public boolean finishCur = true;

	
	// 팝업 에니메이션
	public int textIndex[] = { 0, 0 };

	public int endRank[] = { 6, 4, 2, 0, 2, 4 };

	public int rankAni = 0;

	public int textState = TEXT_READY;

	public boolean itemCheck = false;

	public int cnt = 0;

	public GameData() {
		for (int i = 0; i < 3; i++) {
			item[i] = new Ticker();
			effect[i] = new Effect();
		}
		itemPos[0][0] = 833;
		itemPos[0][1] = 186;

		itemPos[1][0] = 813;
		itemPos[1][1] = 233;

		itemPos[2][0] = 841;
		itemPos[2][1] = 278;

		successTime[0] = 170000;
		successTime[1] = 160000;
		successTime[2] = 150000;
		successTime[3] = 135000;
		successTime[4] = 120000;

		int index = 0;
		subItemOrder[index][0] = 5;
		subItemOrder[index][1] = 5;
		subItemOrder[index][2] = 0;
		index++;
		subItemOrder[index][0] = 0;
		subItemOrder[index][1] = 5;
		subItemOrder[index][2] = 5;
		index++;
		subItemOrder[index][0] = 0;
		subItemOrder[index][1] = 0;
		subItemOrder[index][2] = 10;

		index = 0;
		trapItemOrder[index][0] = 2;
		trapItemOrder[index][1] = 0;
		index++;
		trapItemOrder[index][0] = 2;
		trapItemOrder[index][1] = 1;
		index++;
		trapItemOrder[index][0] = 2;
		trapItemOrder[index][1] = 2;
		index++;
		trapItemOrder[index][0] = 2;
		trapItemOrder[index][1] = 3;
		index++;
		trapItemOrder[index][0] = 1;
		trapItemOrder[index][1] = 5;
	}

	public void setData() {
		textIndex[0] = 0;
		textIndex[1] = 0;

		textState = TEXT_READY;

		if (mapIndex != null) {
			mapIndex = null;
		}
		mapIndex = new int[5];

		for (int i = 0; i < mapIndex.length; i++) {
			boolean live = true;
			while (live) {
				mapIndex[i] = randemMap(MAP_SCOPE);
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
			
			// System.out.println("Creat Map Index : " + mapIndex[i]);
		}
		MapData.getInstance().mapDataCreate(mapIndex);
	}

	public static GameData getInstance() {
		if (instance == null)
			instance = new GameData();
		return instance;
	}

	public int randemMap(int scope) {
		return (Math.abs(rnd.nextInt()) % scope);
	}

	public void randemMainItem(int mapIndex) {
		// System.out.println("메인아이템 설치");
		// 메인아이템 설치
		for (int i = 0; i < mainItemOrder.length; i++) {
			boolean live = true;
			while (live) {
				mainItemOrder[i] = Math.abs(rnd.nextInt()) % 10;
				switch (i) {
				case 0:
					live = false;
					break;
				case 1:
					if (mainItemOrder[0] != mainItemOrder[1]) {
						live = false;
					}
					break;
				case 2:
					if (mainItemOrder[0] != mainItemOrder[2]
							&& mainItemOrder[1] != mainItemOrder[2]) {
						live = false;
					}
					break;
				}
			}
		}
		for (int i = 0; i < mainItemOrder.length; i++) {
			boolean live = true;
			while (live) {
				if (MapData.getInstance().checkMap(mapIndex,
						Math.abs(rnd.nextInt()) % 19,
						(Math.abs(rnd.nextInt() % 10) + 1), mainItemOrder[i])) {
					for (int j = 0; j < MapData.getInstance().item[mapIndex].length; j++) {
						if (MapData.getInstance().item[mapIndex][i] == GameData.ITEM_NONE) {
							MapData.getInstance().item[mapIndex][i] = mainItemOrder[i];
							break;
						}
					}
					live = false;
				}
			}
		}
	}

	public void randemSubItem(int mapIndex, int levelIndex) {
		// System.out.println("서브 점수 아이템  설치");
		// 서브 점수 아이템 설치
		// subItemOrder
		int itemCount = 0;
		for (int i = 0; i < subItemOrder[levelIndex].length; i++) {
			for (int j = 0; j < subItemOrder[levelIndex][i]; j++) {
				boolean live = true;
				while (live) {
					int x = Math.abs(rnd.nextInt()) % 19;
					int y = (Math.abs(rnd.nextInt() % 10) + 1);
					if (MapData.getInstance()
							.checkMap(mapIndex, x, y, (20 + i))) {
						itemCount++;
						live = false;
					}
				}
			}
		}
//		System.out.println("itemCount : " + itemCount);
	}

	public void randemTrapItem(int mapIndex) {
		// System.out.println("함정 설치");
		// 함정 설치

		for (int i = 0; i < trapItemOrder[mapIndex].length; i++) {
			for (int j = 0; j < trapItemOrder[mapIndex][i]; j++) {
				boolean live = true;
				while (live) {
					if (MapData.getInstance().checkMap(mapIndex,
							Math.abs(rnd.nextInt()) % 19,
							(Math.abs(rnd.nextInt() % 10) + 1), (10 + i))) {
						live = false;
					}
				}
			}
		}
	}

	public void randemHideItem(int mapIndex) {
		// System.out.println("물음표 아이템");
		// 물음표 아이템 설치
		int random = 0;
		switch (Math.abs(rnd.nextInt()) % 10) {
		case 0:
		case 1:
		case 2:
		case 3:
			random = 1;
			break;
		case 4:
		case 5:
		case 6:
		case 7:
			random = 2;
			break;
		case 8:
		case 9:
			random = 0;
			break;
		}
		boolean live = true;
		while (live) {
			if (MapData.getInstance().checkMap(mapIndex,
					Math.abs(rnd.nextInt()) % 19,
					(Math.abs(rnd.nextInt() % 10) + 1), (30 + random))) {
				live = false;
			}
		}
	}

	public void init() {
		// 준비 시작
		switch (textState) {
		case TEXT_READY:
			if (textIndex[0] >= 14) {
				textState = TEXT_START;
				textIndex[0] = 0;
				textIndex[1] = 0;
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
				pr.firstMove = false;
				nowTime = System.currentTimeMillis();
				((Listener) game).listener(MazeGPlay.GAME_PLAY);
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
				((Listener) game).listener(MazeGPlay.GAME_FINISH_POPUP);
				return;
			} else {
				if (textIndex[0] >= 5) {
					textIndex[1]++;
				}
				textIndex[0]++;
			}

			break;
		case TEXT_SECCESS:
			if (textIndex[0] >= 7) {
				((Listener) game).listener(MazeGPlay.GAME_SUCCESS_FINISH);
				return;
			} else {
				if (textIndex[0] >= 3) {
					textIndex[1]++;
				}
				textIndex[0]++;
			}
			break;
		}
	}

	public void finishPopup() {
		rankAni++;
		rankAni = rankAni % endRank.length;
	}

	public void play(boolean state) {
		if (state) {
			if (GameData.getInstance().pr.cellIndexX == 20
					&& GameData.getInstance().pr.cellIndexY == 8
					&& GameData.getInstance().cr.aniState != Crong.CRONG_GIVEITEM
					&& !GameData.getInstance().itemCheck) {
				ImageDraw.getInstance().okIndex = (ImageDraw.getInstance().okIndex + 1) % 2;
			}
			if (pr.aniState != Pororo.PORORO_SUCCESS
					&& pr.aniState != Pororo.PORORO_GAMEOVER
					&& pr.aniState != Pororo.PORORO_GAMEOVER_END) {
				if (stageTime > 0) {
					if (isFirstTime) {
						if (stageTime < 20000) {
							isFirstTime = false;
							Resource.getInstance().sound
									.playSound(Resource.SOUND_HURRY);
						}
					}
					double temp = System.currentTimeMillis();
					stageTime -= (int) (temp - nowTime);
					nowTime = temp;
				} else if (stageTime <= 0) {
					stageTime = 0;
					pr.aniState = Pororo.PORORO_GAMEOVER;
					pr.aniIndex = 0;
					cr.aniState = Crong.CRONG_GAMEOVER;
					cr.aniIndex = 0;
					textState = TEXT_GAMEOVER;
					// 점수를 서버로 전송
					 int scoreSum = 0;
					 for(int i = 0 ; i < GameData.getInstance().successScore.length ; i++) {
						 scoreSum += GameData.getInstance().successScore[i];
					 }
					SceneManager.getInstance().sendScore_gravity(scoreSum);
					SceneManager.getInstance().send_gravity(11);
				}
			}
		}
		bgEffect.process();
		cr.process();
		pr.process();
		if (state) {
			for (int i = 0; i < 3; i++) {
				if (!item[i].process()) {
					if (item[i].getFinish()) {
						// 나무 이펙트
						itemFinishCount++;
						effect[i].setImage(itemPos[i][0] - 18,
								itemPos[i][1] - 16,
								Resource.getInstance().effactGift, false);
					}
				}
			}
			for (int i = 0; i < 3; i++) {
				if (!effect[i].process()) {
					if (effect[i].getFinish()) {
						cnt--;
					}
				}
			}
			if (cnt == 0) {
				if (itemCheck) {
					if (itemFinishCount == 3) {
						Resource.getInstance().sound
								.playSound(Resource.SOUND_SUCESS);
						itemCheck = false;
						cr.setAnimation(Crong.CRONG_SUCCESS);
						pr.aniState = Pororo.PORORO_SUCCESS;
						pr.aniIndex = 0;
						itemFinishCount = 0;
						textState = TEXT_SECCESS;
						
						int score = GameData.getInstance().successScore[GameData.getInstance().stage];
						// 등급표시
						int bonus = 1;
						if (score >= 11501) {
							bonus = 8;
						} else if (score >= 6951) {
							bonus = 4;
						} else if (score >= 4501) {
							bonus = 2;
						}
						successScore[stage] += (bonus * (int) (stageTime / 1000));
						// 스테이지 등급 표시
						((Listener) game).listener(MazeGPlay.GAME_SUCCESS);
					} else {
						itemCheck = false;
						GameData.getInstance().cr
								.setAnimation(Crong.CRONG_GIVEITEM);
						GameData.getInstance().pr.aniState = Pororo.PORORO_DOWN_IDEL;
						GameData.getInstance().pr.aniIndex = 0;
						System.out.println("Item not full");
					}
				}
			}
		}
		ticker.process();
		// 트랩
		for (int i = 0; i < trap.size(); i++) {
			((Trap) (trap.elementAt(i))).process();
			switch (pr.aniState) {
			case Pororo.PORORO_UP_IDEL:
			case Pororo.PORORO_DOWN_IDEL:
			case Pororo.PORORO_LEFT_IDEL:
			case Pororo.PORORO_RIGHT_IDEL:
				int damage = ((Trap) (GameData.getInstance().trap.elementAt(i)))
						.isCollision(pr.cellIndexX, pr.cellIndexY);
				if (damage > 0) {
					switch (damage) {
					case 10:
						Resource.getInstance().sound
								.playSound(Resource.SOUND_TRAT_BLUE);
						break;
					case 20:
						Resource.getInstance().sound
								.playSound(Resource.SOUND_TRAP_RED);
						break;
					}

					pr.aniState = Pororo.PORORO_BAD;
					pr.aniIndex = 0;
					GameData.getInstance().stageTime -= (damage * 1000);
					GameData.getInstance().bgEffect.setImage(pr.posX
							+ GameData.MAP_X - GameData.PORORO_X - 14, pr.posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 46, Resource
							.getInstance().effactSmog, false);
					GameData.getInstance().effect[0].setImage(pr.posX
							+ GameData.MAP_X - GameData.PORORO_X - 16, pr.posY
							+ GameData.MAP_Y - GameData.PORORO_Y + 35, Resource
							.getInstance().effactBad, false);
				}
				break;
			default:
				break;
			}
		}
	}

	public void start() {
		// System.out.println("GameData start");
		setData();

		for (int i = 0; i < successScore.length; i++) {
			successScore[i] = 0;
		}
		
		exitCur = true;
		finishCur = true;

		textIndex[0] = 0;
		textIndex[1] = 0;

		textState = TEXT_READY;

		itemCheck = false;
		isFirstTime = true;

		stage = 0;

		for (int i = 0; i < mapIndex.length; i++) {
			randemMainItem(i);
			randemHideItem(i);
			randemTrapItem(i);
			// 트랩
		}
		Resource.getInstance().loadingMap(mapIndex[stage]);
		stageTime = successTime[stage];
		// 트랩 시작
		if (trap != null) {
			trap.removeAllElements();
			trap = null;
		}
		trap = new Vector();
		for (int i = 0; i < MapData.getInstance().map[stage].length; i++) {
			for (int j = 0; j < MapData.getInstance().map[stage][i].length; j++) {
				int item = MapData.getInstance().getItem(i, j);
				if (item == ITEM_TRAP_B || item == ITEM_TRAP_R) {
					Trap addTrap = new Trap();
					switch (MapData.getInstance().getItem(i, j)) {
					case ITEM_TRAP_B:
						addTrap.setData(i, j, 10);
						break;
					case ITEM_TRAP_R:
						addTrap.setData(i, j, 20);
						break;
					}
					trap.addElement(addTrap);
				}
			}
		}
		itemFinishCount = 0;
		cr.start();
		pr.start();
		try {
			if (StateValue.isUrlLive) {
				if (!SceneManager.getInstance()
						.loadBackgroundImage(
								new URL(StateValue.liveResource
										+ "bg/maze/game_bg.gif"))) {
				}

			} else {
				if (!SceneManager.getInstance()
						.loadBackgroundImage(
								new URL(StateValue.testResource
										+ "bg/maze/game_bg.gif"))) {
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void changeMap() {
		cr.start();
		pr.start();
		MapData.getInstance().mapReset();
		Resource.getInstance().loadingMap(mapIndex[stage]);
		stageTime = successTime[stage];
		nowTime = System.currentTimeMillis();
		// 트랩 시작
		if (trap != null) {
			trap.removeAllElements();
			trap = null;
		}
		trap = new Vector();
		for (int i = 0; i < MapData.getInstance().map[stage].length; i++) {
			for (int j = 0; j < MapData.getInstance().map[stage][i].length; j++) {
				int item = MapData.getInstance().getItem(i, j);
				if (item == ITEM_TRAP_B || item == ITEM_TRAP_R) {
					Trap addTrap = new Trap();
					switch (MapData.getInstance().getItem(i, j)) {
					case ITEM_TRAP_B:
						addTrap.setData(i, j, 10);
						break;
					case ITEM_TRAP_R:
						addTrap.setData(i, j, 20);
						break;
					}
					trap.addElement(addTrap);
				}
			}
		}
	}

	public int getMapIndex() {
		return stage;
	}

	public int getItemIndex(int x, int y) {
		return MapData.getInstance().getItem(x, y);
	}

	public void reStart() {
		exitCur = true;
		finishCur = true;
		pr.reStart();
		for (int i = 0; i < trap.size(); i++) {
			((Trap) (trap.elementAt(i))).reStart();
		}
		nowTime = System.currentTimeMillis();
	}

	public void pause() {
		pr.pause();
		for (int i = 0; i < trap.size(); i++) {
			((Trap) (trap.elementAt(i))).pause();
		}
	}

	public void destroy()
	{
		bgEffect = null;
		
		if(cr != null)
		{
			cr.destroy();
			cr = null;
		}

		if(pr != null)
		{
			pr.destroy();
			pr = null;
		}

		if(trap != null)
		{
			trap.removeAllElements();
			trap = null;
		}

		instance = null;
	}

	public void setScene(Scene s) {
		game = s;
	}
}