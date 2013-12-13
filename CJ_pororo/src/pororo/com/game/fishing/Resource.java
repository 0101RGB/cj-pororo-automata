package pororo.com.game.fishing;

import java.awt.Image;
import java.net.URL;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.framework.Sound;
import pororo.com.game.fishing.GameData;
import pororo.com.game.fishing.Listener;
import pororo.com.game.fishing.FishingGPlay;

public final class Resource {

	private static Resource instance = new Resource();

	public static final int MAX_COUNT = 10;

	private static final int IMAGE_BG = 0;
	private static final int IMAGE_CHAR = 1;
	private static final int IMAGE_CHAR_RED = 2;
	private static final int IMAGE_CHAR_ABS = 3;
	private static final int IMAGE_ITEM = 4;
	private static final int IMAGE_MONSTER_BAD = 5;
	private static final int IMAGE_MONSTER_SAFE = 6;
	private static final int IMAGE_COMMON = 7;
	private static final int IMAGE_EFFECT = 8;
	private static final int SOUND_TOTAL = 9;

	public static final int SOUND_START = 0;
	public static final int SOUND_GAMEOVER = 1;
	public static final int SOUND_SUCESS = 2;
	public static final int SOUND_SAFE0 = 3;
	public static final int SOUND_BAD0 = 9;
	public static final int SOUND_BAD1 = 10;
	public static final int SOUND_ITEM_TIME = 11;

	public int loadingStep = IMAGE_BG;
	private boolean isLoading = true;
	private boolean isLoadingError = false;// 에러가 났는지 확인

	private String url = StateValue.liveResource;
	// 로딩게이지
	public Image img_load[];

	// IMAGE_CHAR
	public Image prLeftEnd;
	public Image prRightEnd;

	public Image prLeftMove[];
	public Image prLeftAct[];
	public Image prLeftDmgN;
	public Image prLeftDmgE;
	public Image prRightMove[];
	public Image prRightAct[];
	public Image prRightDmgN;
	public Image prRightDmgE;

//	public Image prLeftRedMove[];
//	public Image prLeftRedAct[];
//	public Image prLeftRedDmgN;
//	public Image prLeftRedDmgE;
//	public Image prRightRedMove[];
//	public Image prRightRedAct[];
//	public Image prRightRedDmgN;
//	public Image prRightRedDmgE;

	public Image prLeftAbsMove[];
	public Image prLeftAbsAct[];
	public Image prLeftAbsDmgN;
	public Image prLeftAbsDmgE;
//	public Image prRightAbsMove[];
//	public Image prRightAbsAct[];
//	public Image prRightAbsDmgN;
//	public Image prRightAbsDmgE;

	// IMAGE_ITEM
	public Image item[];

	// IMAGE_MONSTER
	public Image monLeftBadA[];
//	public Image monRightBadA[];
	public Image monLeftBadB[];
//	public Image monRightBadB[];
	public Image monLeftBadC[];
//	public Image monRightBadC[];
	public Image monLeftBadD[];
//	public Image monRightBadD[];
	public Image monLeftBadE[];
//	public Image monRightBadE[];

	public Image monLeftSafeA[];
//	public Image monRightSafeA[];
	public Image monLeftSafeB[];
//	public Image monRightSafeB[];
	public Image monLeftSafeC[];
//	public Image monRightSafeC[];
	public Image monLeftSafeD[];
//	public Image monRightSafeD[];
	public Image monLeftSafeE[];
//	public Image monRightSafeE[];
	public Image monLeftSafeF[];
//	public Image monRightSafeF[];

	// IMAGE_COMMON
	public Image ok[];
	public Image score[];
	public Image number[];
	public Image numberL[];

	public Image guage;
	public Image itemGuage[];
	public Image string[];
	public Image text[];
	public Image endRank[];

	// IMAGE_Popup
	public Image exitPop;
	public Image btnYes[];
	public Image btnNo[];
	public Image finishPop;
	public Image btnRe[];
	public Image btnFin[];

	// IMAGE_EFFECT
	public Image effectEnd[];
//	public Image effectSpeed[];
	public Image effectTime[];
	public Image effectSuper[];

	// Sound
	public Sound sound;

	public static Resource getInstance() {
		if (instance == null) {
			instance = new Resource();
		}
		return instance;
	}

	Resource() {
		img_load = new Image[2];

		img_load[0] = loadImage("img/load1.png");
		img_load[1] = loadImage("img/load2.png");
		if(StateValue.isUrlLive) {
			url = StateValue.liveResource;
		}
		else {
			url = StateValue.testResource;
		}
	}

	public Image loadImage(String s) {

		return SceneManager.getInstance().getImage(s);
	}

	public boolean loading() {
		// System.out.println("loading");
		switch (loadingStep) {
		case IMAGE_BG:
			loadingStep++;
			break;
		case IMAGE_CHAR:
			loadingStep++;
			loadingChar();
			break;
		case IMAGE_CHAR_RED:
			loadingStep++;
			loadingCharRed();
			break;
		case IMAGE_CHAR_ABS:
			loadingStep++;
			loadingCharAbs();
			break;
		case IMAGE_ITEM:
			loadingStep++;
			loadingItem();
			break;
		case IMAGE_MONSTER_BAD:
			loadingStep++;
			loadingMonsterBad();
			break;
		case IMAGE_MONSTER_SAFE:
			loadingStep++;
			loadingMonsterSafe();
			break;
		case IMAGE_COMMON:
			loadingStep++;
			loadingCommon();
			break;
		case IMAGE_EFFECT:
			loadingStep++;

			loadingEffect();

			break;
		case SOUND_TOTAL:
			loadingStep++;
			isLoading = !isLoading;
			loadingSound();
			isLoading = false;
			break;
		/*
		 * case IMAGE_Effact: loadingStep++; loadingEffect(); break; case
		 * SOUND_TOTAL: loadingStep++; isLoading = !isLoading; loadingSound();
		 * isLoading = false; break;
		 */
		}
		return isLoading;
	}

	private Image loadUrlImage(URL url) {
		Image img;
		img = SceneManager.getInstance().getImage(url);
		if (img != null) {
			if (img.getWidth(null) <= 0)
				((Listener) GameData.getInstance().game)
						.listener(FishingGPlay.GAME_ERROR);
		} else
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
		return img;
	}

	private void loadingChar() {
		try {
			prLeftEnd = loadUrlImage(new URL(url
					+ "fishing/img/char/lp00_end.png"));
			prRightEnd = loadUrlImage(new URL(url
					+ "fishing/img/char/rp00_end.png"));

			prLeftMove = new Image[5];
			for (int i = 0; i < prLeftMove.length; i++) {
				prLeftMove[i] = loadUrlImage(new URL(url
						+ "fishing/img/char/lp01_a0" + i + ".png"));
			}
			prLeftAct = new Image[3];
			for (int i = 0; i < prLeftAct.length; i++) {
				prLeftAct[i] = loadUrlImage(new URL(url
						+ "fishing/img/char/lp01_b0" + i + ".png"));
			}
			prLeftDmgN = loadUrlImage(new URL(url
					+ "fishing/img/char/lp01_c.png"));
			prLeftDmgE = loadUrlImage(new URL(url
					+ "fishing/img/char/lp01_d.png"));

			prRightMove = new Image[5];
			for (int i = 0; i < prRightMove.length; i++) {
				prRightMove[i] = loadUrlImage(new URL(url
						+ "fishing/img/char/rp01_a0" + i + ".png"));
			}
			prRightAct = new Image[3];
			for (int i = 0; i < prRightAct.length; i++) {
				prRightAct[i] = loadUrlImage(new URL(url
						+ "fishing/img/char/rp01_b0" + i + ".png"));
			}
			prRightDmgN = loadUrlImage(new URL(url
					+ "fishing/img/char/rp01_c.png"));
			prRightDmgE = loadUrlImage(new URL(url
					+ "fishing/img/char/rp01_d.png"));
		} catch (Exception e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingCharRed() {
//		try {
//			prLeftRedMove = new Image[5];
//			for (int i = 0; i < prLeftRedMove.length; i++) {
//				prLeftRedMove[i] = loadUrlImage(new URL(url
//						+ "fishing/img/char/lp02_a0" + i + ".png"));
//			}
//			prLeftRedAct = new Image[3];
//			for (int i = 0; i < prLeftRedAct.length; i++) {
//				prLeftRedAct[i] = loadUrlImage(new URL(url
//						+ "fishing/img/char/lp02_b0" + i + ".png"));
//			}
//			prLeftRedDmgN = loadUrlImage(new URL(url
//					+ "fishing/img/char/lp02_c.png"));
//			prLeftRedDmgE = loadUrlImage(new URL(url
//					+ "fishing/img/char/lp02_d.png"));
//
//			prRightRedMove = new Image[5];
//			for (int i = 0; i < prRightRedMove.length; i++) {
//				prRightRedMove[i] = loadUrlImage(new URL(url
//						+ "fishing/img/char/rp02_a0" + i + ".png"));
//			}
//			prRightRedAct = new Image[3];
//			for (int i = 0; i < prRightRedAct.length; i++) {
//				prRightRedAct[i] = loadUrlImage(new URL(url
//						+ "fishing/img/char/rp02_b0" + i + ".png"));
//			}
//			prRightRedDmgN = loadUrlImage(new URL(url
//					+ "fishing/img/char/rp02_c.png"));
//			prRightRedDmgE = loadUrlImage(new URL(url
//					+ "fishing/img/char/rp02_d.png"));
//
//		} catch (Exception e) {
//			isLoadingError = true;
//			((Listener) GameData.getInstance().game)
//					.listener(FishingGPlay.GAME_ERROR);
//			e.printStackTrace();
//		}
	}

	private void loadingCharAbs() {
		try {
			prLeftAbsMove = new Image[5];
			for (int i = 0; i < prLeftAbsMove.length; i++) {
				prLeftAbsMove[i] = loadUrlImage(new URL(url
						+ "fishing/img/char/lp03_a0" + i + ".png"));
			}
			prLeftAbsAct = new Image[3];
			for (int i = 0; i < prLeftAbsAct.length; i++) {
				prLeftAbsAct[i] = loadUrlImage(new URL(url
						+ "fishing/img/char/lp03_b0" + i + ".png"));
			}
			prLeftAbsDmgN = loadUrlImage(new URL(url
					+ "fishing/img/char/lp03_c.png"));
			prLeftAbsDmgE = loadUrlImage(new URL(url
					+ "fishing/img/char/lp03_d.png"));

//			prRightAbsMove = new Image[5];
//			for (int i = 0; i < prRightAbsMove.length; i++) {
//				prRightAbsMove[i] = loadUrlImage(new URL(url
//						+ "fishing/img/char/rp03_a0" + i + ".png"));
//			}
//			prRightAbsAct = new Image[3];
//			for (int i = 0; i < prRightAbsAct.length; i++) {
//				prRightAbsAct[i] = loadUrlImage(new URL(url
//						+ "fishing/img/char/rp03_b0" + i + ".png"));
//			}
//			prRightAbsDmgN = loadUrlImage(new URL(url
//					+ "fishing/img/char/rp03_c.png"));
//			prRightAbsDmgE = loadUrlImage(new URL(url
//					+ "fishing/img/char/rp03_d.png"));

		} catch (Exception e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingItem() {
		try {
			item = new Image[3];
			item[0] = loadUrlImage(new URL(url
					+ "fishing/img/item/item_time.png"));
			item[1] = loadUrlImage(new URL(url
					+ "fishing/img/item/item_spd.png"));
			item[2] = loadUrlImage(new URL(url
					+ "fishing/img/item/item_super.png"));

		} catch (Exception e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingMonsterBad() {
		try {
			monLeftBadA = new Image[2];
//			monRightBadA = new Image[2];
			for (int i = 0; i < monLeftBadA.length; i++) {
				monLeftBadA[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/bad_l_a0" + i + ".png"));
//				monRightBadA[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/bad_r_a0" + i + ".png"));
			}

			monLeftBadB = new Image[4];
//			monRightBadB = new Image[4];
			for (int i = 0; i < monLeftBadB.length; i++) {
				monLeftBadB[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/bad_l_b0" + i + ".png"));
//				monRightBadB[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/bad_r_b0" + i + ".png"));
			}

			monLeftBadC = new Image[2];
//			monRightBadC = new Image[2];
			for (int i = 0; i < monLeftBadC.length; i++) {
				monLeftBadC[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/bad_l_c0" + i + ".png"));
//				monRightBadC[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/bad_r_c0" + i + ".png"));
			}

			monLeftBadD = new Image[2];
//			monRightBadD = new Image[2];
			for (int i = 0; i < monLeftBadD.length; i++) {
				monLeftBadD[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/bad_l_d0" + i + ".png"));
//				monRightBadD[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/bad_r_d0" + i + ".png"));
			}

			monLeftBadE = new Image[2];
//			monRightBadE = new Image[2];
			for (int i = 0; i < monLeftBadE.length; i++) {
				monLeftBadE[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/bad_l_e0" + i + ".png"));
//				monRightBadE[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/bad_r_e0" + i + ".png"));
			}

		} catch (Exception e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingMonsterSafe() {
		try {
			monLeftSafeA = new Image[2];
//			monRightSafeA = new Image[2];
			for (int i = 0; i < monLeftSafeA.length; i++) {
				monLeftSafeA[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/sf_l_a0" + i + ".png"));
//				monRightSafeA[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/sf_r_a0" + i + ".png"));
			}

			monLeftSafeB = new Image[2];
//			monRightSafeB = new Image[2];
			for (int i = 0; i < monLeftSafeB.length; i++) {
				monLeftSafeB[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/sf_l_b0" + i + ".png"));
//				monRightSafeB[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/sf_r_b0" + i + ".png"));
			}

			monLeftSafeC = new Image[2];
//			monRightSafeC = new Image[2];
			for (int i = 0; i < monLeftSafeC.length; i++) {
				monLeftSafeC[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/sf_l_c0" + i + ".png"));
//				monRightSafeC[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/sf_r_c0" + i + ".png"));
			}

			monLeftSafeD = new Image[2];
//			monRightSafeD = new Image[2];
			for (int i = 0; i < monLeftSafeD.length; i++) {
				monLeftSafeD[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/sf_l_e0" + i + ".png"));
//				monRightSafeD[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/sf_r_e0" + i + ".png"));
			}

			monLeftSafeE = new Image[2];
//			monRightSafeE = new Image[2];
			for (int i = 0; i < monLeftSafeE.length; i++) {
				monLeftSafeE[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/sf_l_d0" + i + ".png"));
//				monRightSafeE[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/sf_r_d0" + i + ".png"));
			}

			monLeftSafeF = new Image[2];
//			monRightSafeF = new Image[2];
			for (int i = 0; i < monLeftSafeF.length; i++) {
				monLeftSafeF[i] = loadUrlImage(new URL(url
						+ "fishing/img/obj/sf_l_f0" + i + ".png"));
//				monRightSafeF[i] = loadUrlImage(new URL(url
//						+ "fishing/img/obj/sf_r_f0" + i + ".png"));
			}

		} catch (Exception e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingCommon() {
		try {
			guage = loadUrlImage(new URL(url + "fishing/img/bg/lifegage.png"));

			itemGuage = new Image[2];
			for (int i = 0; i < itemGuage.length; i++) {
				itemGuage[i] = loadUrlImage(new URL(url + "fishing/img/bg/tg0"
						+ i + ".png"));
			}

			number = new Image[10];
			for (int i = 0; i < number.length; i++) {
				number[i] = loadUrlImage(new URL(url + "fishing/img/bg/num0"
						+ i + ".png"));
			}

			numberL = new Image[10];
			for (int i = 0; i < numberL.length; i++) {
				numberL[i] = loadUrlImage(new URL(url + "pop_result/end_" + i
						+ ".png"));
			}
			text = new Image[6];
			for (int i = 0; i < text.length; i++) {
				text[i] = loadUrlImage(new URL(url + "fishing/img/bg/txt_" + i
						+ ".png"));
			}
			//			
			// gage = loadUrlImage(new URL(url + "maze/bg/gage.png"));
			// stage = new Image[5];
			// for (int i = 0; i < stage.length; i++) {
			// stage[i] = loadUrlImage(new URL(url + "maze/bg/st_" + (i + 1) +
			// ".png"));
			// }
			exitPop = loadUrlImage(new URL(url + "pop_end/pop_end.png"));
			btnYes = new Image[2];
			btnYes[0] = loadUrlImage(new URL(url + "pop_end/btn_yes_on.png"));
			btnYes[1] = loadUrlImage(new URL(url + "pop_end/btn_yes_off.png"));

			btnNo = new Image[2];
			btnNo[0] = loadUrlImage(new URL(url + "pop_end/btn_no_on.png"));
			btnNo[1] = loadUrlImage(new URL(url + "pop_end/btn_no_off.png"));

			finishPop = loadUrlImage(new URL(url + "pop_result/end_popup_4.png"));
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
			endRank[0] = loadUrlImage(new URL(url + "pop_result/end_rank_d.png"));
			endRank[1] = loadUrlImage(new URL(url + "pop_result/end_rank_c.png"));
			endRank[2] = loadUrlImage(new URL(url + "pop_result/end_rank_b.png"));
			endRank[3] = loadUrlImage(new URL(url + "pop_result/end_rank_a.png"));

		} catch (Exception e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingEffect() {
		try {

			effectEnd = new Image[4];
			for (int i = 0; i < effectEnd.length; i++) {
				effectEnd[i] = loadUrlImage(new URL(url
						+ "fishing/img/effect/ef_boom0" + i + ".png"));
			}

//			effectSpeed = new Image[4];
//			for (int i = 0; i < effectSpeed.length; i++) {
//				effectSpeed[i] = loadUrlImage(new URL(url
//						+ "fishing/img/effect/ef_spd0" + i + ".png"));
//			}

			effectTime = new Image[4];
			for (int i = 0; i < effectTime.length; i++) {
				effectTime[i] = loadUrlImage(new URL(url
						+ "fishing/img/effect/ef_time0" + i + ".png"));
			}

			effectSuper = new Image[4];
			for (int i = 0; i < effectSuper.length; i++) {
				effectSuper[i] = loadUrlImage(new URL(url
						+ "fishing/img/effect/ef_super0" + i + ".png"));
			}

		} catch (Exception e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingSound() {
		try {
			sound = new Sound(14);
			sound.loadSound(new URL(url + "fishing/snd/start.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/gameover.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/success.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/safe0.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/safe1.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/safe2.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/safe3.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/safe4.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/safe5.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/bad0.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/bad1.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/item_time.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/item_speed.mp2"));
			sound.loadSound(new URL(url + "fishing/snd/item_super.mp2"));

			if (StateValue.isUrlLive) {
				if (!SceneManager.getInstance().loadBackgroundImage(
						new URL(StateValue.liveResource
								+ "bg/fishing/game_bg.gif"))) {
				}
			} else {
				if (!SceneManager.getInstance().loadBackgroundImage(
						new URL(StateValue.testResource
								+ "bg/fishing/game_bg.gif"))) {
				}
			}

		} catch (Throwable e) {
			isLoadingError = true;
			((Listener) GameData.getInstance().game)
					.listener(FishingGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	public void destroy1() {

		if (img_load != null) {
			for (int i = 0; i < img_load.length; i++) {
				SceneManager.getInstance().removeImage(img_load[i]);
			}
			img_load = null;
		}
		if (prLeftMove != null) {
			for (int i = 0; i < prLeftMove.length; i++) {
				SceneManager.getInstance().removeImage(prLeftMove[i]);
			}
			prLeftMove = null;
		}
		if (prLeftAct != null) {
			for (int i = 0; i < prLeftAct.length; i++) {
				SceneManager.getInstance().removeImage(prLeftAct[i]);
			}
			prLeftAct = null;
		}
		if (prRightMove != null) {
			for (int i = 0; i < prRightMove.length; i++) {
				SceneManager.getInstance().removeImage(prRightMove[i]);
			}
			prRightMove = null;
		}
		if (prRightAct != null) {
			for (int i = 0; i < prRightAct.length; i++) {
				SceneManager.getInstance().removeImage(prRightAct[i]);
			}
			prRightAct = null;
		}

		if (prLeftDmgN != null) {
			SceneManager.getInstance().removeImage(prLeftDmgN);
			prLeftDmgN = null;
		}
		if (prLeftDmgE != null) {
			SceneManager.getInstance().removeImage(prLeftDmgE);
			prLeftDmgE = null;
		}
		if (prRightDmgN != null) {
			SceneManager.getInstance().removeImage(prRightDmgN);
			prRightDmgN = null;
		}
		if (prRightDmgE != null) {
			SceneManager.getInstance().removeImage(prRightDmgE);
			prRightDmgE = null;
		}

//		if (prLeftRedMove != null) {
//			for (int i = 0; i < prLeftRedMove.length; i++) {
//				SceneManager.getInstance().removeImage(prLeftRedMove[i]);
//			}
//			prLeftRedMove = null;
//		}
//		if (prLeftRedAct != null) {
//			for (int i = 0; i < prLeftRedAct.length; i++) {
//				SceneManager.getInstance().removeImage(prLeftRedAct[i]);
//			}
//			prLeftRedAct = null;
//		}
//		if (prRightRedMove != null) {
//			for (int i = 0; i < prRightRedMove.length; i++) {
//				SceneManager.getInstance().removeImage(prRightRedMove[i]);
//			}
//			prRightRedMove = null;
//		}
//		if (prRightRedAct != null) {
//			for (int i = 0; i < prRightRedAct.length; i++) {
//				SceneManager.getInstance().removeImage(prRightRedAct[i]);
//			}
//			prRightRedAct = null;
//		}
//		if (prLeftRedDmgN != null) {
//			SceneManager.getInstance().removeImage(prLeftRedDmgN);
//			prLeftRedDmgN = null;
//		}
//		if (prLeftRedDmgE != null) {
//			SceneManager.getInstance().removeImage(prLeftRedDmgE);
//			prLeftRedDmgE = null;
//		}
//		if (prRightRedDmgN != null) {
//			SceneManager.getInstance().removeImage(prRightRedDmgN);
//			prRightRedDmgN = null;
//		}
//		if (prRightRedDmgE != null) {
//			SceneManager.getInstance().removeImage(prRightRedDmgE);
//			prRightRedDmgE = null;
//		}
	}
	
	public void destroy2() {
		if (prLeftAbsMove != null) {
			for (int i = 0; i < prLeftAbsMove.length; i++) {
				SceneManager.getInstance().removeImage(prLeftAbsMove[i]);
			}
			prLeftAbsMove = null;
		}
		if (prLeftAbsAct != null) {
			for (int i = 0; i < prLeftAbsAct.length; i++) {
				SceneManager.getInstance().removeImage(prLeftAbsAct[i]);
			}
			prLeftAbsAct = null;
		}
//		if (prRightAbsMove != null) {
//			for (int i = 0; i < prRightAbsMove.length; i++) {
//				SceneManager.getInstance().removeImage(prRightAbsMove[i]);
//			}
//			prRightAbsMove = null;
//		}
//		if (prRightAbsAct != null) {
//			for (int i = 0; i < prRightAbsAct.length; i++) {
//				SceneManager.getInstance().removeImage(prRightAbsAct[i]);
//			}
//			prRightAbsAct = null;
//		}
		if (prLeftAbsDmgN != null) {
			SceneManager.getInstance().removeImage(prLeftAbsDmgN);
			prLeftAbsDmgN = null;
		}
		if (prLeftAbsDmgE != null) {
			SceneManager.getInstance().removeImage(prLeftAbsDmgE);
			prLeftAbsDmgE = null;
		}
//		if (prRightAbsDmgN != null) {
//			SceneManager.getInstance().removeImage(prRightAbsDmgN);
//			prRightAbsDmgN = null;
//		}
//		if (prRightAbsDmgE != null) {
//			SceneManager.getInstance().removeImage(prRightAbsDmgE);
//			prRightAbsDmgE = null;
//		}
		if (prLeftEnd != null) {
			SceneManager.getInstance().removeImage(prLeftEnd);
			prLeftEnd = null;
		}
		if (prRightEnd != null) {
			SceneManager.getInstance().removeImage(prRightEnd);
			prRightEnd = null;
		}

		if (item != null) {
			for (int i = 0; i < item.length; i++) {
				SceneManager.getInstance().removeImage(item[i]);
			}
			item = null;
		}

		if (monLeftBadA != null) {
			for (int i = 0; i < monLeftBadA.length; i++) {
				SceneManager.getInstance().removeImage(monLeftBadA[i]);
			}
			monLeftBadA = null;
		}
//		if (monRightBadA != null) {
//			for (int i = 0; i < monRightBadA.length; i++) {
//				SceneManager.getInstance().removeImage(monRightBadA[i]);
//			}
//			monRightBadA = null;
//		}

		if (monLeftBadB != null) {
			for (int i = 0; i < monLeftBadB.length; i++) {
				SceneManager.getInstance().removeImage(monLeftBadB[i]);
			}
			monLeftBadB = null;
		}

//		if (monRightBadB != null) {
//			for (int i = 0; i < monRightBadB.length; i++) {
//				SceneManager.getInstance().removeImage(monRightBadB[i]);
//			}
//			monRightBadB = null;
//		}

		if (monLeftBadC != null) {
			for (int i = 0; i < monLeftBadC.length; i++) {
				SceneManager.getInstance().removeImage(monLeftBadC[i]);
			}
			monLeftBadC = null;
		}
//		if (monRightBadC != null) {
//			for (int i = 0; i < monRightBadC.length; i++) {
//				SceneManager.getInstance().removeImage(monRightBadC[i]);
//			}
//			monRightBadC = null;
//		}

		if (monLeftBadD != null) {
			for (int i = 0; i < monLeftBadD.length; i++) {
				SceneManager.getInstance().removeImage(monLeftBadD[i]);
			}
			monLeftBadD = null;
		}
//		if (monRightBadD != null) {
//			for (int i = 0; i < monRightBadD.length; i++) {
//				SceneManager.getInstance().removeImage(monRightBadD[i]);
//			}
//			monRightBadD = null;
//		}

		if (monLeftBadE != null) {
			for (int i = 0; i < monLeftBadE.length; i++) {
				SceneManager.getInstance().removeImage(monLeftBadE[i]);
			}
			monLeftBadE = null;
		}
//		if (monRightBadE != null) {
//			for (int i = 0; i < monRightBadE.length; i++) {
//				SceneManager.getInstance().removeImage(monRightBadE[i]);
//			}
//			monRightBadE = null;
//		}

		if (monLeftSafeA != null) {
			for (int i = 0; i < monLeftSafeA.length; i++) {
				SceneManager.getInstance().removeImage(monLeftSafeA[i]);
			}
			monLeftSafeA = null;
		}
//		if (monRightSafeA != null) {
//			for (int i = 0; i < monRightSafeA.length; i++) {
//				SceneManager.getInstance().removeImage(monRightSafeA[i]);
//			}
//			monRightSafeA = null;
//		}

		if (monLeftSafeB != null) {
			for (int i = 0; i < monLeftSafeB.length; i++) {
				SceneManager.getInstance().removeImage(monLeftSafeB[i]);
			}
			monLeftSafeB = null;
		}
//		if (monRightSafeB != null) {
//			for (int i = 0; i < monRightSafeB.length; i++) {
//				SceneManager.getInstance().removeImage(monRightSafeB[i]);
//			}
//			monRightSafeB = null;
//		}

		if (monLeftSafeC != null) {
			for (int i = 0; i < monLeftSafeC.length; i++) {
				SceneManager.getInstance().removeImage(monLeftSafeC[i]);
			}
			monLeftSafeC = null;
		}
//		if (monRightSafeC != null) {
//			for (int i = 0; i < monRightSafeC.length; i++) {
//				SceneManager.getInstance().removeImage(monRightSafeC[i]);
//			}
//			monRightSafeC = null;
//		}

		if (monLeftSafeD != null) {
			for (int i = 0; i < monLeftSafeD.length; i++) {
				SceneManager.getInstance().removeImage(monLeftSafeD[i]);
			}
			monLeftSafeD = null;
		}
//		if (monRightSafeD != null) {
//			for (int i = 0; i < monRightSafeD.length; i++) {
//				SceneManager.getInstance().removeImage(monRightSafeD[i]);
//			}
//			monRightSafeD = null;
//		}

		if (monLeftSafeE != null) {
			for (int i = 0; i < monLeftSafeE.length; i++) {
				SceneManager.getInstance().removeImage(monLeftSafeE[i]);
			}
			monLeftSafeE = null;
		}
//		if (monRightSafeE != null) {
//			for (int i = 0; i < monRightSafeE.length; i++) {
//				SceneManager.getInstance().removeImage(monRightSafeE[i]);
//			}
//			monRightSafeE = null;
//		}

		if (monLeftSafeF != null) {
			for (int i = 0; i < monLeftSafeF.length; i++) {
				SceneManager.getInstance().removeImage(monLeftSafeF[i]);
			}
			monLeftSafeF = null;
		}
//		if (monRightSafeF != null) {
//			for (int i = 0; i < monRightSafeF.length; i++) {
//				SceneManager.getInstance().removeImage(monRightSafeF[i]);
//			}
//			monRightSafeF = null;
//		}

		if (guage != null) {
			SceneManager.getInstance().removeImage(guage);
			guage = null;
		}
		
		if (itemGuage != null) {
			for (int i = 0; i < itemGuage.length; i++) {
				SceneManager.getInstance().removeImage(itemGuage[i]);
			}
			itemGuage = null;
		}

		if (number != null) {
			for (int i = 0; i < number.length; i++) {
				SceneManager.getInstance().removeImage(number[i]);
			}
			number = null;
		}

		if (numberL != null) {
			for (int i = 0; i < numberL.length; i++) {
				SceneManager.getInstance().removeImage(numberL[i]);
			}
			numberL = null;
		}

		if (text != null) {
			for (int i = 0; i < text.length; i++) {
				SceneManager.getInstance().removeImage(text[i]);
			}
			text = null;
		}

		if (exitPop != null) {
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
		if (finishPop != null) {
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
		if (effectEnd != null) {
			for (int i = 0; i < effectEnd.length; i++) {
				SceneManager.getInstance().removeImage(effectEnd[i]);
			}
			effectEnd = null;
		}
//		if (effectSpeed != null) {
//			for (int i = 0; i < effectSpeed.length; i++) {
//				SceneManager.getInstance().removeImage(effectSpeed[i]);
//			}
//			effectSpeed = null;
//		}
		if (effectTime != null) {
			for (int i = 0; i < effectTime.length; i++) {
				SceneManager.getInstance().removeImage(effectTime[i]);
			}
			effectTime = null;
		}
		if (effectSuper != null) {
			for (int i = 0; i < effectSuper.length; i++) {
				SceneManager.getInstance().removeImage(effectSuper[i]);
			}
			effectSuper = null;
		}

		if (sound != null) {
			sound.destroySound();
			sound = null;
		}

		instance = null;
	}
}
