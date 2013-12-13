package pororo.com.game.puzzle;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.framework.Sound;

import java.awt.Image;
import java.net.URL;


// 게임 리소스를 관리 한다
public final class Resource {

	public static final int MAX_COUNT = 8;

	public static final int IMAGE_CHAR_0 = 0;
	public static final int IMAGE_CHAR_1 = 1;
	public static final int IMAGE_CHAR_2 = 2;
	public static final int IMAGE_COMMON = 3;
	public static final int IMAGE_TEXT = 4;
	public static final int IMAGE_NUMBER = 5;
	public static final int IMAGE_EFFACT = 6;
	public static final int SOUND_TOTAL = 7;

	public static final int SOUND_START = 0;
	public static final int SOUND_GAMEOVER = 1;
	public static final int SOUND_CLEAR = 2;
	public static final int SOUND_JUMP = 3;
	public static final int SOUND_FAIL = 4;
	public static final int SOUND_GOOD = 5;

	private static Resource instance = new Resource();

	// Pororo
	public Image[] idel=null;// 처음시작시 점프전
	public Image[] jump=null;// 처음 점프
	public Image[] down=null;// 내려갈때

	public Image[] turn=null;// 맞는그림

	public Image[] sucdcess=null;// 게임 성공시
	public Image[] gameover=null;// 게임 실패시
	// Common
	public Image gage=null;
	public Image[] img_load=null;
	public Image numberL[];
	
	// Text
	public Image[] text=null;// Text
	public Image[] tube=null;// Text
	// Number
	public Image[] numberStage=null;// 스테이지 넘버
	public Image[] numberScore=null;// 점수 넘버
	// Effect
	public Image[] effectGood=null;// 맞는 이펙트
	public Image[] effectFail=null;// 틀린 이펙트

	// Map
	public Image map=null;
	public Image miniMap=null;
	public Image[] piece=null;

	// Sound
	public Sound sound;

	// IMAGE_Popup
	public Image exitPop=null;
	public Image btnYes[]=null;
	public Image btnNo[]=null;
	public Image finishPop=null;
	public Image btnRe[]=null;
	public Image btnFin[]=null;
	public Image endRank[];
	
	public Image img_btn_ok[];
	
	public String url = StateValue.testResource;

	public int state = IMAGE_CHAR_0;

	public static Resource getInstance() {
		if (instance == null) {
			instance = new Resource();
		}
		return instance;
	}

	public Resource() {
		if (StateValue.isUrlLive) {
			url = StateValue.liveResource;
		}

		img_load = new Image[2];

		img_load[0] = loadImage("img/load1.png");
		img_load[1] = loadImage("img/load2.png");
	}

	public Image loadImage(String s) {
		return SceneManager.getInstance().getImage(s);
	}

	public boolean loading() {
		switch (state) {
		case IMAGE_CHAR_0:
			state = IMAGE_CHAR_1;
			loadingChar0();
			break;
		case IMAGE_CHAR_1:
			state = IMAGE_CHAR_2;
			loadingChar1();
			break;
		case IMAGE_CHAR_2:
			state = IMAGE_COMMON;
			loadingChar2();
			break;
		case IMAGE_COMMON:
			state = IMAGE_TEXT;
			loadingCommon();
			break;
		case IMAGE_TEXT:
			state = IMAGE_NUMBER;
			loadingText();
			break;
		case IMAGE_NUMBER:
			state = IMAGE_EFFACT;
			loadingNumber();
			break;
		case IMAGE_EFFACT:
			state = SOUND_TOTAL;
			loadingEffect();
			break;
		case SOUND_TOTAL:
			System.out.println("=================sound loading");
			state = IMAGE_CHAR_0;
			loadingSound();
			return false;
		}
		return true;
	}

	private Image loadUrlImage(URL url) {
		Image img;
		img = SceneManager.getInstance().getImage(url);
		if (img != null) {
			if (img.getWidth(null) <= 0) {
				GameData.getInstance().gameState = GameData.ERROR;
			}
		} else {
			GameData.getInstance().gameState = GameData.ERROR;
		}
		return img;
	}

	public void map(int stage, int pieceCount) {
		try {
			if (map != null) {
				SceneManager.getInstance().removeImage(map);
				map = null;
			}
			if(miniMap != null) {
				SceneManager.getInstance().removeImage(miniMap);
				//miniMap.flush();
				miniMap = null;
			}
			if(piece != null)
			{
				for (int i = 0; i < piece.length; i++) {
					SceneManager.getInstance().removeImage(piece[i]);
					//piece[i].flush();
					//piece[i] = null;
				}
				piece = null;
			}
			
			map = loadUrlImage(new URL(url + "puzzle/puzzle_map/img/" + stage
					+ "/bg.png"));
			miniMap = loadUrlImage(new URL(url + "puzzle/puzzle_map/img/"
					+ stage + "/mini.png"));

			
			piece = new Image[pieceCount];
			for (int i = 0; i < piece.length; i++) {
				piece[i] = loadUrlImage(new URL(url + "puzzle/puzzle_map/img/"
						+ stage + "/" + (i + 1) + ".png"));
			}
		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	private void loadingChar0() {
		try {
			idel = new Image[5];
			for (int i = 0; i < idel.length; i++) {
				idel[i] = loadUrlImage(new URL(url + "puzzle/img/char/cha"
						+ (i + 1) + ".png"));
			}
			jump = new Image[2];
			for (int i = 0; i < jump.length; i++) {
				jump[i] = loadUrlImage(new URL(url + "puzzle/img/char/chb"
						+ (i + 1) + ".png"));
			}
			down = new Image[3];
			for (int i = 0; i < down.length; i++) {
				down[i] = loadUrlImage(new URL(url + "puzzle/img/char/chc"
						+ (i + 1) + ".png"));
			}
		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	private void loadingChar1() {
		try {
			turn = new Image[6];
			for (int i = 0; i < turn.length; i++) {
				turn[i] = loadUrlImage(new URL(url + "puzzle/img/char/chd"
						+ (i + 1) + ".png"));
			}
		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	private void loadingChar2() {
		try {
			sucdcess = new Image[3];
			for (int i = 0; i < sucdcess.length; i++) {
				sucdcess[i] = loadUrlImage(new URL(url + "puzzle/img/char/chf"
						+ (i + 1) + ".png"));
			}
			gameover = new Image[4];
			for (int i = 0; i < gameover.length; i++) {
				gameover[i] = loadUrlImage(new URL(url + "puzzle/img/char/chg"
						+ (i + 1) + ".png"));
			}
		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	private void loadingCommon() {
		try {
			gage = loadUrlImage(new URL(url + "puzzle/img/bg/puzgage.png"));
			exitPop = loadUrlImage(new URL(url + "pop_end/pop_end.png"));
			btnYes = new Image[2];
			btnYes[0] = loadUrlImage(new URL(url + "pop_end/btn_yes_on.png"));
			btnYes[1] = loadUrlImage(new URL(url + "pop_end/btn_yes_off.png"));

			btnNo = new Image[2];
			btnNo[0] = loadUrlImage(new URL(url + "pop_end/btn_no_on.png"));
			btnNo[1] = loadUrlImage(new URL(url + "pop_end/btn_no_off.png"));

			finishPop = loadUrlImage(new URL(url + "pop_result/end_popup_5.png"));
			btnRe = new Image[2];
			for (int i = 0; i < btnRe.length; i++) {
				btnRe[i] = loadUrlImage(new URL(url + "pop_result/end_bt_fin0"
						+ (i + 1) + ".png"));
			}

			btnFin = new Image[2];
			for (int i = 0; i < btnFin.length; i++) {
				btnFin[i] = loadUrlImage(new URL(url + "pop_result/end_bt_re0"
						+ (i + 1) + ".png"));
			}
			
			endRank = new Image[4];
			endRank[0] = loadUrlImage(new URL(url
					+ "pop_result/end_rank_d.png"));
			endRank[1] = loadUrlImage(new URL(url
					+ "pop_result/end_rank_c.png"));
			endRank[2] = loadUrlImage(new URL(url
					+ "pop_result/end_rank_b.png"));
			endRank[3] = loadUrlImage(new URL(url
					+ "pop_result/end_rank_a.png"));
			
			// 2012.06.12 YJY 축구놀이의 OK버튼을 퍼즐놀이에 추가
			img_btn_ok = new Image[2];
			img_btn_ok[0] = loadUrlImage(new URL(url+"soccer/img/btn_ok_up.png"));
			img_btn_ok[1] = loadUrlImage(new URL(url+"soccer/img/btn_ok_push.png"));

		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	private void loadingText() {
		try {
			text = new Image[10];
			for (int i = 0; i < text.length; i++) {
				text[i] = loadUrlImage(new URL(url + "puzzle/img/bg/txt_" + i
						+ ".png"));
			}
			tube = new Image[6];
			for (int i = 0; i < tube.length; i++) {
				tube[i] = loadUrlImage(new URL(url + "puzzle/img/char/tube"
						+ (i + 1) + ".png"));
			}
		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	private void loadingNumber() {
		try {
			numberStage = new Image[10];
			for (int i = 0; i < numberStage.length; i++) {
				numberStage[i] = loadUrlImage(new URL(url + "puzzle/img/bg/puz"
						+ i + ".png"));
			}
			numberScore = new Image[10];
			for (int i = 0; i < numberScore.length; i++) {
				numberScore[i] = loadUrlImage(new URL(url
						+ "puzzle/img/bg/score" + i + ".png"));
			}
			numberL = new Image[10];
			for (int i = 0; i < numberL.length; i++) {
				numberL[i] = loadUrlImage(new URL(url + "pop_result/end_" + i
						+ ".png"));
			}

		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	private void loadingEffect() {
		try {
			effectGood = new Image[5];
			for (int i = 0; i < effectGood.length; i++) {
				effectGood[i] = loadUrlImage(new URL(url
						+ "puzzle/img/effect/egood" + (i + 1) + ".png"));
			}
			effectFail = new Image[5];
			for (int i = 0; i < effectFail.length; i++) {
				effectFail[i] = loadUrlImage(new URL(url
						+ "puzzle/img/effect/efail" + (i + 1) + ".png"));
			}
		} catch (Exception e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}



	private void loadingSound() {
		// 사운드 로딩
		try {
			sound = new Sound(6);
			sound.loadSound(new URL(url + "puzzle/snd/start.mp2"));
			sound.loadSound(new URL(url + "puzzle/snd/gameover.mp2"));
			sound.loadSound(new URL(url + "puzzle/snd/clear.mp2"));
			sound.loadSound(new URL(url + "puzzle/snd/jump.mp2"));
			sound.loadSound(new URL(url + "puzzle/snd/fail.mp2"));
			sound.loadSound(new URL(url + "puzzle/snd/good.mp2"));

		} catch (Throwable e) {
			GameData.getInstance().gameState = GameData.ERROR;
			e.printStackTrace();
		}
	}

	public void destroy1() {

		// 이미지 초기화
		if(idel != null) {
			for (int i = 0; i < idel.length; i++) {
				SceneManager.getInstance().removeImage(idel[i]);
				//idel[i].flush();
				//idel[i] = null;
			}
			idel = null;
		}

		if(jump != null) {
			for (int i = 0; i < jump.length; i++) {
				SceneManager.getInstance().removeImage(jump[i]);
				//jump[i].flush();
				//jump[i] = null;
			}
			jump = null;
		}

		if(down != null) {
			for (int i = 0; i < down.length; i++) {
				SceneManager.getInstance().removeImage(down[i]);
				//down[i].flush();
				//down[i] = null;
			}
			down = null;
		}

		if(turn != null) {
			for (int i = 0; i < turn.length; i++) {
				SceneManager.getInstance().removeImage(turn[i]);
				//turn[i].flush();
				//turn[i] = null;
			}
			turn = null;
		}

		if(sucdcess != null) {
			for (int i = 0; i < sucdcess.length; i++) {
				SceneManager.getInstance().removeImage(sucdcess[i]);
				//sucdcess[i].flush();
				//sucdcess[i] = null;
			}
			sucdcess = null;
		}

		if(gameover != null) {
			for (int i = 0; i < gameover.length; i++) {
				SceneManager.getInstance().removeImage(gameover[i]);
				//gameover[i].flush();
				//gameover[i] = null;
			}
			gameover = null;
		}
	}
	
	public void destroy2() {
		if(gage != null)
		{
			SceneManager.getInstance().removeImage(gage);
			//gage.flush();
			gage = null;
		}

		if(img_load != null) {
			for (int i = 0; i < img_load.length; i++) {
				SceneManager.getInstance().removeImage(img_load[i]);
				//img_load[i].flush();
				//img_load[i] = null;
			}
			img_load = null;
		}

		
		if(text != null) {
			for (int i = 0; i < text.length; i++) {
				SceneManager.getInstance().removeImage(text[i]);
				//text[i].flush();
				//text[i] = null;
			}
			text = null;
		}

		if(tube != null) {
			for (int i = 0; i < tube.length; i++) {
				SceneManager.getInstance().removeImage(tube[i]);
				//tube[i].flush();
				//tube[i] = null;
			}
			tube = null;
		}

		if(numberStage != null) {
			for (int i = 0; i < numberStage.length; i++) {
				SceneManager.getInstance().removeImage(numberStage[i]);
				//numberStage[i].flush();
				//numberStage[i] = null;
			}
			numberStage = null;
		}

		if (numberL != null) {
			for (int i = 0; i < numberL.length; i++) {
				SceneManager.getInstance().removeImage(numberL[i]);
			}
			numberL = null;
		}
		
		if(numberScore != null) {
			for (int i = 0; i < numberScore.length; i++) {
				SceneManager.getInstance().removeImage(numberScore[i]);
				//numberScore[i].flush();
				//numberScore[i] = null;
			}
			numberScore = null;
		}

		
		if(effectGood != null) {
			for (int i = 0; i < effectGood.length; i++) {
				SceneManager.getInstance().removeImage(effectGood[i]);
				//effectGood[i].flush();
				//effectGood[i] = null;
			}
			effectGood = null;
		}

		if(effectFail != null) {
			for (int i = 0; i < effectFail.length; i++) {
				SceneManager.getInstance().removeImage(effectFail[i]);
				//effectFail[i].flush();
				//effectFail[i] = null;
			}
			effectFail = null;
		}

		if(map != null) {
			SceneManager.getInstance().removeImage(map);
			//map.flush();
			map = null;
		}

		if(miniMap != null) {
			SceneManager.getInstance().removeImage(miniMap);
			//miniMap.flush();
			miniMap = null;
		}
		
		if(exitPop != null)
		{
			SceneManager.getInstance().removeImage(exitPop);
			exitPop = null;
		}
		
		if (btnYes != null) {
			for (int i = 0; i < btnYes.length; i++) {
				SceneManager.getInstance().removeImage(btnYes[i]);
			}
			text = null;
		}
		if (btnNo != null) {
			for (int i = 0; i < btnNo.length; i++) {
				SceneManager.getInstance().removeImage(btnNo[i]);
			}
			btnNo = null;
		}
		
		if(finishPop !=null)
		{
			SceneManager.getInstance().removeImage(finishPop);
			finishPop = null;
		}
		
		if (btnRe != null) {
			for (int i = 0; i < btnRe.length; i++) {
				SceneManager.getInstance().removeImage(btnRe[i]);
			}
			btnRe = null;
		}
		if (btnFin != null) {
			for (int i = 0; i < btnFin.length; i++) {
				SceneManager.getInstance().removeImage(btnFin[i]);
			}
			btnFin = null;
		}
		
		if (endRank != null) {
			for (int i = 0; i < endRank.length; i++) {
				SceneManager.getInstance().removeImage(endRank[i]);
			}
			endRank = null;
		}
		
		if(img_btn_ok != null)
		{
			for(int i=0; i<2; i++) {
				SceneManager.getInstance().removeImage(img_btn_ok[i]);
				img_btn_ok[i] = null;
			}
			img_btn_ok = null;
		}
		
		if(piece != null)
		{
			for (int i = 0; i < piece.length; i++) {
				SceneManager.getInstance().removeImage(piece[i]);
				//piece[i].flush();
				//piece[i] = null;
			}
			piece = null;
		}

		// 사운드 초기화
		if (sound != null) {
			sound.destroySound();
			sound = null;
		}

		
		instance = null;
	}
}