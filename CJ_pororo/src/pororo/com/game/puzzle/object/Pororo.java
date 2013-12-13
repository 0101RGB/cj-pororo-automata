package pororo.com.game.puzzle.object;

import pororo.com.SceneManager;
import pororo.com.game.puzzle.GameData;
import pororo.com.game.puzzle.Resource;

import java.awt.Rectangle;
import java.util.Random;


//뽀로로 에니메이션
public class Pororo {
	public static final int LEFT2 = 0; // 리소스 로딩
	public static final int LEFT1 = 1; // 리소스 로딩
	public static final int NONE = 2; // 준비시작 문구
	public static final int RIGHT1 = 3; // 게임 실행
	public static final int RIGHT2 = 4; // 게임 실행

	public static final int IDEL = 0; // 일반상태
	public static final int JUMP = 1; // 점프시
	public static final int DOWN = 2; // 내려올때
	public static final int FAIL = 3; // 실패 턴
	public static final int GOOD = 4; // 성공턴
	public static final int GAMEOVER = 5; // 게임실패종료
	public static final int SUCESS = 6; // 게임성공종료
	public static final int PAUSE = -1; // 게임성공종료
	

	public static final int TUBE_IDEL = 0; // Tube 평상시
	public static final int TUBE_DOWN = 1; // Tube Down
	
	public static final int NONE_DOWN = 0; // 일반상태
	public static final int GOOD_DOWN = 1; // 맟추고 내려옴
	public static final int FAIL_DOWN = 2; // 틀리고 내려옴

	// 뽀로로 위치
	public int posX = 0;
	public int posY = 0;

	// 좌우로 이동할 것이 더 남았는가
	public int horizontal = NONE;

	// 뽀로로 처음 시작 위치
	public int indexX = 22;

	// 에니메이션 상태
	public int aniState = IDEL;
	
	//정지상태를 위해 저장해 둠
	public int subaniState = PAUSE;
	// 에니메이션 인덱스
	public int aniIndex = 0;
	// 이펙트 하기전 에니메이션
	public int aniBefore = IDEL;
	// 이펙트 하기전 에니메이션 인덱스
	public int aniIndexBefore = 0;

	// 뽀로로 세로 위치
	public int posIndex = 0;

	// 마출 퍼즐 조각
	public int puzzleIndex = 0;

	public boolean isEnd = false;// 내려가기가 완료된 상태인가
	
	// 현재 내려가기 상태
	public int downState = NONE_DOWN;

	// 에니메이션 동작 순서
	public int[] idel = { 0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1, 0, 3, 4, 1 };
	public int[] jump = { 0, 0, 0, 0, 0, 0, 0, 1, 1 };
	public int[] down = { 0, 1, 2 };
	public int[] fail = { 0, 1, 2, 3, 5 };
	public int[] good = { 0, 1, 2, 3, 4 };
	public int[] gameover = { 0, 1, 2, 3 };
	public int[] sucess = { 0, 1, 2, 1 };

	// tube
	public int aniStateTube = TUBE_IDEL;
	public int aniIndexTube = 0;
	public int[] idelTube = { 0, 1, 2 };
	public int[] jumpTube = { 0, 3, 4, 5, 4, 3, 0 };

	// 점프 이동 좌표
	int offset = 20;
	public int[] playPos = { 0, -9, -17, -20, -17, -9, 0, offset, offset * 2,
			offset * 3, offset * 4, offset * 5, offset * 6, offset * 7,
			offset * 8, offset * 9, offset * 10, offset * 11, offset * 12,
			offset * 13, offset * 14, offset * 14 + 9, offset * 14 + 17,
			offset * 14 + 20, offset * 14 + 17, offset * 14 + 9, offset * 14,
			offset * 13, offset * 12, offset * 11, offset * 10, offset * 9,
			offset * 8, offset * 7, offset * 6, offset * 5, offset * 4,
			offset * 3, offset * 2, offset };

	// 게임 종료시 좌표위치
	public int[] end = { 0, -9, -17, -20, -17, -9, 0, offset, offset * 2,
			offset * 3, offset * 3 + 9, offset * 3 + 17, offset * 3 + 20,
			offset * 3 + 17, offset * 3 + 9, offset * 3, offset * 2, offset };

	// 충돌여부
	public boolean collision = false;
	
	// 내려올때 성공상태인가
	public int isGood = NONE_DOWN;

	Random rnd = new Random();

	// 콤보
	int combo = 0;
	// 오답 갯수
	int failCount = 0;

	public void process() {
		// 뽀로로 정보 처리
		
		switch (aniState) {
		case FAIL:
			// 실패시 에니메이션
			aniIndex++;
			if (aniIndex >= fail.length) {
				aniState = aniBefore;
				aniIndex = aniIndexBefore;
				randomPiece();
			}
			break;
		case GOOD:
			// 성공시 에니메이션
			aniIndex++;
			if (aniIndex >= good.length) {
				aniState = aniBefore;
				aniIndex = aniIndexBefore;
				randomPiece();
				if (puzzleIndex == -2) {
					aniState = SUCESS;
					aniIndex = 0;
					GameData.getInstance().gameState = GameData.FINISH;
					GameData.getInstance().textState = GameData.TEXT_SECCESS;
					Resource.getInstance().sound.playSound(Resource.SOUND_CLEAR);
					GameData.getInstance().textIndex[0]= 0;
					GameData.getInstance().textIndex[1]= 0;
					GameData.getInstance().textIndex[2]= 0;
					GameData.getInstance().textIndex[3]= 0;
					GameData.getInstance().scoretimebonus();
					
					if(GameData.getInstance().mapIndex.length == (GameData.getInstance().stage+1))
					{
						SceneManager.getInstance().sendScore_gravity(GameData.getInstance().score);
						SceneManager.getInstance().send_gravity(11);
					}
					
				}
			}
			break;

		default:
			switch (aniState) {
			case IDEL:
				aniIndex++;
				aniIndex = aniIndex % idel.length;
				break;
			case JUMP:
				aniIndex++;
				aniIndex = aniIndex % jump.length;
				break;
			case DOWN:
				break;
			case GAMEOVER:
				aniIndex++;
				aniIndex = aniIndex % gameover.length;
				break;
			case SUCESS:
				aniIndex++;
				aniIndex = aniIndex % sucess.length;
				break;
			}
			// 튜브 내림
			if (posIndex == 0 ) {
				if(GameData.getInstance().gameState != GameData.INIT)
				{

				
				//System.out.println("========tube_down");
				aniStateTube = TUBE_DOWN;
				aniIndexTube = 0;
				}
			}
			//System.out.println("============ aniState" + aniState);
			if(aniState != PAUSE)
				if(GameData.getInstance().gameState != GameData.INIT)
				{

				posIndex++;
				//System.out.println(posIndex);
				}

			// 게임 진행/종료 상황 위치
			if (!isEnd)
				posIndex = posIndex % playPos.length;
			else
				posIndex = posIndex % end.length;

			// 튜브 에니메이션
			if(aniState != PAUSE)
				aniIndexTube++;
			switch (aniStateTube) {
			case TUBE_IDEL:
				aniIndexTube = aniIndexTube % idelTube.length;
				break;
			case TUBE_DOWN:
				aniIndexTube = aniIndexTube % jumpTube.length;
				break;
			}

			// 게임 진행/종료 상황 에니메이션
			if (!isEnd) {
				if(GameData.getInstance().gameState != GameData.INIT)
				{

				if (posIndex == 0) {
					// 튜브 내림
					aniStateTube = TUBE_DOWN;
					aniIndexTube = 0;
					//if(GameData.getInstance().gameState == GameData.PLAY)
					//{
					//	if (puzzleIndex >= 0) {
					//		Resource.getInstance().sound.playSound(Resource.SOUND_JUMP);
					//	}
					//}
				} else if (posIndex == 2){
					if(GameData.getInstance().gameState == GameData.PLAY)
					{
						if (puzzleIndex >= 0) {
							Resource.getInstance().sound.playSound(Resource.SOUND_JUMP);
						}
					}
				} else if (posIndex == 6) {
					// 점프
					if (puzzleIndex >= 0) {
						downState = NONE_DOWN;
						aniState = JUMP;
						aniIndex = 0;
						//if(GameData.getInstance().gameState == GameData.PLAY)
						//{
						//	Resource.getInstance().sound.playSound(Resource.SOUND_JUMP);
						//}
					}
					aniStateTube = TUBE_IDEL;
					aniIndexTube = 0;
				} else if (posIndex == 22) {
					// 내려오기
					if (puzzleIndex >= 0) {
						if (aniState != DOWN) {
							aniState = DOWN;
							aniIndex = 0;
						}
					}
				} else if (posIndex == playPos.length - 1) {
					// 내려올때 들고 있는게 없을때
					if (puzzleIndex == -2) {
						//if(aniState == SUCESS)
						//{

							//GameData.getInstance().gameState = GameData.FINISH;
							//GameData.getInstance().textState = GameData.TEXT_SECCESS;

						//}
						posIndex = 0;
						isEnd = true;
					}
				}
				}
			} else {
				// 종료 에니메이션
				if (posIndex == 0) {
					aniStateTube = TUBE_DOWN;
					aniIndexTube = 0;
				} else if (posIndex == 6) {
					aniStateTube = TUBE_IDEL;
					aniIndexTube = 0;
				}
			}

			// 좌우 이동
			switch (horizontal) {
			case LEFT2:
				indexX--;
				horizontal = LEFT1;
				break;
			case LEFT1:
				indexX--;
				horizontal = NONE;
				break;
			case NONE:
				break;
			case RIGHT2:
				indexX++;
				horizontal = RIGHT1;
				break;
			case RIGHT1:
				indexX++;
				horizontal = NONE;
				break;
			}
			break;
		}
		
		// 들고잇는 퍼즐조각 위치 세팅
		if (puzzleIndex > 0
				&& Resource.getInstance().piece.length > puzzleIndex - 1
				&& Resource.getInstance().piece[puzzleIndex - 1] != null) {
			int width = Resource.getInstance().piece[puzzleIndex - 1]
					.getWidth(null) - 40 - 4;
			int height = Resource.getInstance().piece[puzzleIndex - 1]
					.getHeight(null) - 4;
			int x = posX + (indexX * 20) + 55 + 40 + 2;
			int y = 0;
			switch (height) {
			case 40:
				y = posY - playPos[posIndex] + 29 + 2;
				break;
			case 80:
				y = posY - playPos[posIndex] + 9 + 2;
				break;
			case 120:
				y = posY - playPos[posIndex] - 11 + 2;
				break;
			}
			collision = GameData.getInstance().mapData.collision(puzzleIndex,
					new Rectangle(x, y, width, height));
		}
	}

	public void start() {
		// 최초 시작
		randomPiece();
	}

	public void pause() {
		subaniState = aniState;
		aniState = PAUSE;
		
	}
	
	public void Npause() {
		//System.out.println("#####################");
		GameData.getInstance().currentTime = System.currentTimeMillis();
		aniState = subaniState;
		subaniState = PAUSE;
	}
	public void restart() {
		// 다시 시작
		horizontal = NONE;

		indexX = 22;

		aniState = IDEL;
		aniBefore = IDEL;
		aniIndexBefore = 0;
		aniIndex = 0;
		posIndex = 0;

		puzzleIndex = 0;

		isEnd = false;
		aniStateTube = TUBE_IDEL;
		aniIndexTube = 0;

		collision = false;

		randomPiece();
	}

	public void randomPiece() {
		boolean live = false;
		for (int i = 0; i < GameData.getInstance().piece.size(); i++) {
			if (!((PieceData) (GameData.getInstance().piece.elementAt(i))).show) {
				live = true;
			}
		}
		while (live) {
			puzzleIndex = Math.abs(rnd.nextInt()
					% GameData.getInstance().piece.size());
			System.out.println("puzzleIndex : " + puzzleIndex);
			if (!((PieceData) (GameData.getInstance().piece
					.elementAt(puzzleIndex))).show) {
				puzzleIndex++;
				live = false;
				return;
			}
		}
		puzzleIndex = -2;
	}

	public void destroy() {
	}
}