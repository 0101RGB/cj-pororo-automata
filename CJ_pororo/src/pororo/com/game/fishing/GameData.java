package pororo.com.game.fishing;

import java.util.Random;

import pororo.com.Scene;
import pororo.com.game.fishing.object.Item;
import pororo.com.game.fishing.object.Monster;
import pororo.com.game.fishing.object.Pororo;
import pororo.com.game.fishing.Resource;

// 게임에 관한 정보를 가지고 있는다
public final class GameData {
	public int textState = TEXT_READY;
	private static final int TEXT_NONE = -1;
	public static final int TEXT_READY = 0;
	public static final int TEXT_START = 1;
	public static final int TEXT_GAMEOVER = 2;
	private static final int TEXT_SECCESS = 3;
	
	public boolean finishCur = true;
	public boolean exitCur = true;
	
	public Scene game = null;
	
	private static GameData instance = new GameData();
	
	private static final Random rnd = new Random();
	
	public Monster mon[] = new Monster[20];
	public Monster monSp[] = new Monster[3];
	public Monster monSpBig = new Monster();
	public Item	item = new Item();
	public Item	effect = new Item();
//	public GameObject pr = new Pororo();
	public Pororo pr = new Pororo();
	private int cnt;
	private int monCnt;
	private int cntTime;
	private double nowTime = 0;
	private int level = 0;
//	public int levelCnt[] = { 3, 19, 69, 109, 149, 189, 229, 269, 319, 369, 419, 499 };
	private int levelCnt[] = { 3, 19, 34, 49, 64, 79, 94, 119, 139, 169, 209, 259, 319 };
	private int curLevel = 0;
	public int delayIndex = 0;
	
	public int textJun[] = { 7, 3, 0, -2, -1, 0, 0, 0, 0, 0 };// new int[10];
	public int textBi[] = { 7, 3, 0, -2, -1, 0, 0, 0 };// new int[8];
	public int textSi[] = { -7, -3, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };// new
	public int textJak[] = { -7, -3, 0, 2, 1, 0, 0, 0, 0 };// new int[9];
//	private int textGame[] = { 6, 2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };// new
//	private int textFinish[] = { 7, 3, 0, -2, -1, 0, 0, 0, 0 };// new int[9];
	public float alphaJun[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new int[10];
//	private float alphaBi[] = { 0.25f, 0.63f, 1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new
	public float alphaSi[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new int[16];
	public float alphaJak[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new int[9];
	public float alphaGame[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new int[14];
	public float alphaFinish[] = { 0.25f, 0.63f, 1f, 1f, 1f, 1f, 0.75f, 0.5f, 0.25f };// new int[9];
	public int textIndex[] = { 0, 0 };
	
	public int endRank[] = {6, 4, 2, 0, 2, 4};
	public int rankAni = 0;
	
	public int gradeCnt = 0;
	
	public GameData(){
		cnt = 0;
		monCnt = 0;
		cntTime = 0;
		nowTime = 0;
		level = 0;
		curLevel = 0;
		for (int i = 0; i < mon.length; i++) {
			mon[i] = new Monster();
		}
		for (int i = 0; i < monSp.length; i++) {
			monSp[i] = new Monster();
		}
		
	}
	public static  GameData getInstance(){
		if(instance == null) {
			instance = new GameData();
		}
		return instance;
	}
	public int isGamePlay() {
		if(pr.getGuage() <= 0) {
			pr.setAnimation(3);	// GameOver
			Resource.getInstance().sound.playSound(Resource.SOUND_SUCESS);
			effect.startEffect(4);
			effect.setPosX(pr.getPosX());
			effect.setPosY(pr.getPosY());
			effect.setAnimation(0);	// GameOver effect
			return 1;
		}
		return 0;
	}
	
	public int randemMap(int scope) {
		return (Math.abs(rnd.nextInt())%scope + 1);
	}
	
	public void introProc() {
		
		switch (textState) {
		case TEXT_READY:
			cnt++;
			pr.setGuage(10);
			pr.introMove();
			if (textIndex[0] >= 14) {
				textState = TEXT_START;
				textIndex[0] = 0;
				textIndex[1] = 0;
				return;
			} else {
				if (textIndex[0] >= 2) {
					textIndex[1]++;
				}
				textIndex[0]++;
			}
			break;
		case TEXT_START:
			cnt++;
			pr.setGuage(10);
			pr.introMove();
			if (textIndex[0] >= 15) {
				textState = TEXT_NONE;
				textIndex[0] = 0;
				textIndex[1] = 0;
//				pr.firstMove = false;
				nowTime = System.currentTimeMillis();
				((Listener) game).listener(FishingGPlay.GAME_PLAY);
				return;
			} else {
				if (textIndex[0] >= 7) {
					textIndex[1]++;
				}
				textIndex[0]++;
			}
			break;
		case TEXT_GAMEOVER:
			if(effect.isState()) {
				effect.process();
				effect.setPosX(pr.getPosX());
				effect.setPosY(pr.getPosY());
			}
			if (textIndex[0] >= 13) {
				textIndex[0] = 0;
				textIndex[1] = 0;
				((Listener) game).listener(FishingGPlay.GAME_FINISH_POPUP);
				return;
			} else {
				pr.process();
				if(delayIndex == 10) {
					Resource.getInstance().sound.playSound(Resource.SOUND_GAMEOVER);
				}
				if(delayIndex <= 10) {
					delayIndex++;
				}
				
				if(delayIndex >= 10) {
					if (textIndex[0] >= 5) {
						textIndex[1]++;
					}
					textIndex[0]++;
				}
				
			}

			break;
		}
	}

	public void process() {
		if(cnt % 2 == 0) { // 일정간격 에너지 게이지 감소
//			pr.setGuage(-1);
		}
		cnt++;
		if(cnt >= 6) {	// 일정간격 에너지 게이지 감소
			cnt = 0;
//			pr.setGuage(-1);
		}
		
		monCnt++;
		if(monCnt >= mon.length) monCnt = 0;
		
		cntTime++;
		
		int temp = 4000 + (gradeCnt*4000);//개복치 생성
		if(pr.getGrade()>=temp){
			monSpBig.startTime = cntTime + 10;
			gradeCnt+=1;
		}
		
		if(cntTime%500 == 0) {	// 아이템 생성
			item.start(level);
			item.setAnimation(0);
		}
		
		if(level > levelCnt.length-1) {
			System.out.println("level over:"+level);
			if(cntTime > curLevel*30) {
				curLevel = level;
				level = (cntTime -(cntTime%260)) / 260;
			}
		}
		else if(cntTime > (levelCnt[level] * 10) ) {
			curLevel = level;
			level++;
			System.out.println("level:"+level);
		}
		
		
		pr.process();
		
		if(effect.isState()) {	// 이펙트 체크
			effect.process();
		}
		
		if(item.isState()) {	// 아이템 체크
			item.process();
			if(item.collisionCheck(pr.centerX, pr.centerY, pr.width, pr.mode) ) {
				System.out.println("===item kind:"+ item.getKind());
				pr.setMode(item.getKind());
				effect.startEffect(pr.getMode());
				effect.setPosX(item.getPosX()-33);
				effect.setPosY(item.getPosY()-47);
				effect.setAnimation(0);
				item.setAnimation(1);
				Resource.getInstance().sound.playSound(Resource.SOUND_ITEM_TIME + item.getKind());
			}
		}
		
		
		for(int i = 0 ; i < /*monCnt*/mon.length; i++) {
			mon[i].process();
			if(mon[i].collisionCheck(pr.centerX, pr.centerY, pr.width, pr.mode) ) {
				checkAction(i);	// 잡는 액션 or 데미지 액션 인지 구분
			}
			
			if(!mon[i].isWorking()) {	// monster initialize..
				System.out.println("monster["+ i +"] working!!!");
				mon[i].start(mon[i].startTime + 290 /*580*/ );
				System.out.println("-- new cntTime:"+cntTime+",mon.startTime:"+mon[i].startTime);
			}
		}
		
		for(int i = 0 ; i < monSp.length; i++) {
			monSp[i].process();
			if(monSp[i].collisionCheck(pr.centerX, pr.centerY, pr.width, pr.mode) ) {
				checkActionSp(i);	// 잡는 액션 or 데미지 액션 인지 구분
			}
			
			if(!monSp[i].isWorking()) {	// monster initialize..
				System.out.println("monsterSp["+ i +"] working!!!");
				monSp[i].startSp(i, cntTime - (cntTime%600)+600+randemMap(600) );
			}
		}
		
		monSpBig.process();
		if(monSpBig.collisionCheck(pr.centerX, pr.centerY, pr.width, pr.mode) ) {
			pr.setAnimation(0);
			monSpBig.setAnimation(0);
			pr.setGrade(monSpBig.getGrade(1));	// 점수 증가
			pr.setGuage(monSpBig.getGuage(1));	// 게이지 증가
			Resource.getInstance().sound.playSound(Resource.SOUND_SAFE0+monSpBig.kind);
		}
		
		if(!monSpBig.isWorking()) {	// monster initialize..
			System.out.println("monsterSpBig working!!!");
			monSpBig.startSp(5, -1);
		}
		
	}
	public void checkActionSp(int num) {
		if(monSp[num].kind < 6) {	// 점수 물고기
			pr.setAnimation(0);
			monSp[num].setAnimation(0);
			pr.setGrade(monSp[num].getGrade(1));	// 점수 증가
			pr.setGuage(monSp[num].getGuage(1));	// 게이지 증가
			Resource.getInstance().sound.playSound(Resource.SOUND_SAFE0+monSp[num].kind);
		}
	}
	public void checkAction(int num) {
		if(mon[num].kind < 6) {	// 점수 물고기
			pr.setAnimation(0);
			mon[num].setAnimation(0);
			Resource.getInstance().sound.playSound(Resource.SOUND_SAFE0+mon[num].kind);
			pr.setGrade(mon[num].getGrade(1));	// 점수 증가
			pr.setGuage(mon[num].getGuage(1));	// 게이지 증가
		}
		else if(mon[num].kind >= 6) {	// 방해 물고기 일때
			if(pr.mode == 3) {	// 무적 망치 상태
				pr.setAnimation(0);
				mon[num].setAnimation(0);
				pr.setGrade(mon[num].getGrade(1));
				pr.setGuage(mon[num].getGuage(1));
				Resource.getInstance().sound.playSound(Resource.SOUND_SAFE0);
			}
			else if(pr.mode == 2) {	// 빨간 망치 상태
//				if(mon[num].kind == 6) { // || mon[num].kind == 8) {	// 복어, 물뱀
//					pr.setAnimation(0);
//					mon[num].setAnimation(0);
//					pr.setGrade(mon[num].getGrade(1));
//					pr.setGuage(mon[num].getGuage(1));
//					Resource.getInstance().sound.playSound(Resource.SOUND_SAFE0);
//				}
//				else 
				if(mon[num].kind == 7) {	// 전기 뱀장어
					pr.setAnimation(2);
//					pr.setGrade(mon[num].getGrade(1));
					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
				}
//				else if(mon[num].kind == 8) {	// 물뱀	// 충돌시 사라지게...
//					pr.setAnimation(1);
//					mon[num].setAnimation(0);
//					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
//					Resource.getInstance().sound.playSound(Resource.SOUND_BAD0);
//				}
//				else if(mon[num].kind == 10) {	// 상어	// 충돌시 사라지게...
//					pr.setAnimation(1);
//					mon[num].setAnimation(0);
//					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
//					Resource.getInstance().sound.playSound(Resource.SOUND_BAD1);
//				}
				else if(mon[num].kind == 10) {	// 상어
					pr.setAnimation(1);
					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
					Resource.getInstance().sound.playSound(Resource.SOUND_BAD1);
				}
				else {
					pr.setAnimation(1);
					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
					Resource.getInstance().sound.playSound(Resource.SOUND_BAD0);
				}
			}
			else {	// 일반 상태
				if(mon[num].kind == 7) {	// 전기 뱀장어
					pr.setAnimation(2);
					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
				} 
//				else if(mon[num].kind == 6) {// || mon[num].kind == 8) {	// 복어, 물뱀 // 충돌시 사라지게..물뱀
//					pr.setAnimation(1);
//					mon[num].setAnimation(0);
//					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
//					Resource.getInstance().sound.playSound(Resource.SOUND_BAD0);
//				}
//				else if(mon[num].kind == 10) {	// 상어	// 충돌시 사라지게..
//					pr.setAnimation(1);
//					mon[num].setAnimation(0);
//					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
//					Resource.getInstance().sound.playSound(Resource.SOUND_BAD1);
//				}
				else {
					pr.setAnimation(1);
					pr.setGuage(mon[num].getGuage(0));	// 게이지 감소
//					Resource.getInstance().sound.playSound(Resource.SOUND_BAD0);
				}
			}
		}
	}
	public void finishPopup() {
		rankAni++;
		rankAni = rankAni%endRank.length;	
	}
	public void start() {
		finishCur = true;
		exitCur = true;
		cntTime = 0;
		delayIndex = 0;
		pr.start();
		for(int i = 0 ; i < mon.length ; i++) {
			mon[i].start(i*15+cntTime+10);
			System.out.println("mon["+i+"] start:"+(i*15+cntTime+40));
		}
		for(int i = 0; i < monSp.length; i++) {
			monSp[i].startSp(i,cntTime+10+randemMap(600));
		}
		monSpBig.startSp(5, -1);
		
		item.start(0);
		effect.startEffect(0);
		rankAni = 0;
		
		gradeCnt = 0;
	}
	public void intro() {
		pr.intro();
		Resource.getInstance().sound.playSound(Resource.SOUND_START);
	}
	public void setScene(Scene s) {
		game = s;
	}
	public void setData() {
		cnt = 0;
		monCnt = 0;
		cntTime = 0;
		nowTime = 0;
		level = 0;
		curLevel = 0;
		delayIndex = 0;
		for(int i = 0 ; i < mon.length ; i++) {
			mon[i].setData();
		}
		for(int i = 0 ; i < monSp.length ; i++) {
			monSp[i].setData();
		}
		monSpBig.setData();
		item.start(0);
		effect.startEffect(0);
		rankAni = 0;
		exitCur = true;
		finishCur = true;
	}
	
	public void destroy() {
		for(int i=0; i < mon.length; i++) {
			mon[i] = null;
		}
		mon = null;
		for(int i=0; i < monSp.length; i++) {
			monSp[i] = null;
		}
		monSp = null;
		monSpBig = null;
		
		if(pr != null)
		{
			pr.destroy();
			pr = null;
		}
		
		item = null;
		effect = null;
		
		instance = null;
	}
}

