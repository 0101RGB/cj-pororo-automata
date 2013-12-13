package pororo.com.game.maze;

import java.awt.Image;
import java.net.URL;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.framework.Sound;

public final class Resource {

	private static Resource instance = new Resource();

	public static final int MAX_COUNT = 7;

	private static final int IMAGE_BG = 0;
	private static final int IMAGE_CHAR_CRONG = 1;
	private static final int IMAGE_CHAR_PORORO = 2;
	private static final int IMAGE_ITEM = 3;
	private static final int IMAGE_COMMON = 4;
	private static final int IMAGE_EFFACT = 5;
	private static final int SOUND_TOTAL = 6;

	public static final int SOUND_START = 0;
	public static final int SOUND_GAMEOVER = 1;
	public static final int SOUND_SUCESS = 2;
	public static final int SOUND_MOVE = 3;
	public static final int SOUND_STAR1 = 4;
	public static final int SOUND_STAR2 = 5;
	public static final int SOUND_STAR3 = 6;
	public static final int SOUND_HURRY = 7;
	public static final int SOUND_MOVEITEM = 8;
	public static final int SOUND_ICE_JUMP = 9;
	public static final int SOUND_ICE_COUNT = 10;
	public static final int SOUND_ICE_FINISH = 11;
	public static final int SOUND_GOODITEM = 12;
	public static final int SOUND_TRAP_RED = 13;
	public static final int SOUND_TRAT_BLUE = 14;
	public static final int SOUND_GIVEITEM = 15;

	public int loadingStep = IMAGE_BG;
	public boolean isLoading = true;

	private String url = StateValue.testResource;
	// 로딩게이지
	public Image img_load[];
	// IMAGE_BG
	public Image map;

	// IMAGE_CHAR
	public Image cr[];
	public Image crCheering[];
	public Image crGameover[];
	public Image crSuccess[];

	public Image crTalk[];

	public Image prIdle[];
	public Image prMove[];
	public Image prRightIdle[];
	public Image prRightMove[];
	public Image prLeftIdle[];
	public Image prLeftMove[];
	public Image prBackdle[];
	public Image prBackMove[];
	public Image prHave[];
	public Image prBad[];
	public Image prIce[];
	public Image prGameover[];
	public Image prSuccess[];

	// IMAGE_ITEM
	public Image item[][];
	public Image trap[][];
	public Image star[];

	public Image random;

	// IMAGE_COMMON
	public Image ok[];
	public Image score[];
	public Image numberS[];
	public Image numberL[];

	// IMAGE_Popup
	public Image exitPop;
	public Image btnYes[];
	public Image btnNo[];
	public Image finishPop;
	public Image btnRe[];
	public Image btnFin[];
	
	public Image endRank[];

	public Image gage;
	public Image stage[];
	public Image string[];
	public Image text[];

	// IMAGE_Effact
	public Image effactBad[];
	public Image effactGift[];
	public Image effactIce[];
	public Image effactIceNum[];
	public Image effactSmog[];
	public Image effactSmok[];
	public Image effactStar[];
	public Image effactTimeUp;
	public Image effactTime[];

	public Sound sound;

	public static Resource getInstance() {
		if (instance == null) {
			instance = new Resource();
		}
		return instance;
	}

	public Resource() {
		if(StateValue.isUrlLive) {
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
		// System.out.println("loadingStep : " + loadingStep);
		switch (loadingStep) {
		case IMAGE_BG:
			loadingStep++;
			break;
		case IMAGE_CHAR_CRONG:
			loadingStep++;
			loadingCharCrong();
			break;
		case IMAGE_CHAR_PORORO:
			loadingStep++;
			loadingCharPororo();
			break;
		case IMAGE_ITEM:
			loadingStep++;
			loadingItem();
			break;
		case IMAGE_COMMON:
			loadingStep++;
			loadingCommon();
			break;
		case IMAGE_EFFACT:
			loadingStep++;
			loadingEffact();
			break;
		case SOUND_TOTAL:
			loadingSound();
			loadingStep = 0;
			isLoading = false;
			break;
		}
		return isLoading;
	}

	private Image loadUrlImage(URL url) {
		Image img;
		img = SceneManager.getInstance().getImage(url);
		if (img != null) {
			if (img.getWidth(null) <= 0) {
				((Listener) GameData.getInstance().game)
						.listener(MazeGPlay.GAME_ERROR);
			}
		} else {
			((Listener) GameData.getInstance().game)
			.listener(MazeGPlay.GAME_ERROR);
		}
		return img;
	}

	public void loadingMap(int index) {
		// System.out.println("Map Image index : " + index);
		try {
			// System.out.println("imageDraw.BG();");
			// while(true) {
			if (map != null) {
				SceneManager.getInstance().removeImage(map);
			}
			map = loadUrlImage(new URL(url + "maze/img/obj_maze/maze"
					+ (index + 1) + ".png"));
			// 아이템들 세팅
			// if(map != null) {
			// return;
			// }
			// }
		} catch (Exception e) {
			((Listener) GameData.getInstance().game)
					.listener(MazeGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingCharCrong() {
		try {
			// *
			cr = new Image[5];
			for (int i = 0; i < cr.length; i++) {
				cr[i] = loadUrlImage(new URL(url + "maze/img/char/cr_a0" + i
						+ ".png"));
			}
			/**/
			crCheering = new Image[6];
			for (int i = 0; i < crCheering.length; i++) {
				crCheering[i] = loadUrlImage(new URL(url
						+ "maze/img/char/cr_cheering_0" + i + ".png"));
			}
			crGameover = new Image[2];
			for (int i = 0; i < crGameover.length; i++) {
				crGameover[i] = loadUrlImage(new URL(url
						+ "maze/img/char/cr_gameover_0" + (i + 1) + ".png"));
			}

			crSuccess = new Image[3];
			for (int i = 0; i < crSuccess.length; i++) {
				crSuccess[i] = loadUrlImage(new URL(url
						+ "maze/img/char/cr_success0" + i + ".png"));
			}
		} catch (Exception e) {
			((Listener) GameData.getInstance().game)
					.listener(MazeGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingCharPororo() {
		try {
			// *
			prIdle = new Image[4];
			for (int i = 0; i < prIdle.length; i++) {
				prIdle[i] = loadUrlImage(new URL(url + "maze/img/char/pr_aa0"
						+ i + ".png"));
			}
			prMove = new Image[3];
			for (int i = 0; i < prMove.length; i++) {
				prMove[i] = loadUrlImage(new URL(url + "maze/img/char/pr_ab0"
						+ i + ".png"));
			}

			prLeftIdle = new Image[4];
			for (int i = 0; i < prLeftIdle.length; i++) {
				prLeftIdle[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_ba0" + i + ".png"));
			}
			prLeftMove = new Image[3];
			for (int i = 0; i < prLeftMove.length; i++) {
				prLeftMove[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_bb0" + i + ".png"));
			}

			prRightIdle = new Image[4];
			for (int i = 0; i < prRightIdle.length; i++) {
				prRightIdle[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_ca0" + i + ".png"));
			}
			prRightMove = new Image[3];
			for (int i = 0; i < prRightMove.length; i++) {
				prRightMove[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_cb0" + i + ".png"));
			}

			prBackdle = new Image[2];
			for (int i = 0; i < prBackdle.length; i++) {
				prBackdle[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_da0" + i + ".png"));
			}
			prBackMove = new Image[3];
			for (int i = 0; i < prBackMove.length; i++) {
				prBackMove[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_db0" + i + ".png"));
			}

			prHave = new Image[5];
			for (int i = 0; i < prHave.length; i++) {
				prHave[i] = loadUrlImage(new URL(url + "maze/img/char/pr_have0"
						+ i + ".png"));
			}

			prBad = new Image[5];
			for (int i = 0; i < prBad.length; i++) {
				prBad[i] = loadUrlImage(new URL(url + "maze/img/char/pr_f_0"
						+ i + ".png"));
			}

			prIce = new Image[6];
			for (int i = 0; i < prIce.length; i++) {
				prIce[i] = loadUrlImage(new URL(url + "maze/img/char/pr_g_0"
						+ i + ".png"));
			}

			prGameover = new Image[9];
			for (int i = 0; i < prGameover.length; i++) {
				prGameover[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_gameover_0" + i + ".png"));
			}

			prSuccess = new Image[5];
			for (int i = 0; i < prSuccess.length; i++) {
				prSuccess[i] = loadUrlImage(new URL(url
						+ "maze/img/char/pr_success0" + i + ".png"));
			}

		} catch (Exception e) {
			((Listener) GameData.getInstance().game)
					.listener(MazeGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingItem() {
		try {
			item = new Image[10][3];
			for (int i = 0; i < item[0].length; i++) {
				item[0][i] = loadUrlImage(new URL(url + "maze/img/obj/item_a0"
						+ i + ".png"));
				item[1][i] = loadUrlImage(new URL(url + "maze/img/obj/item_b0"
						+ i + ".png"));
				item[2][i] = loadUrlImage(new URL(url + "maze/img/obj/item_c0"
						+ i + ".png"));
				item[3][i] = loadUrlImage(new URL(url + "maze/img/obj/item_d0"
						+ i + ".png"));
				item[4][i] = loadUrlImage(new URL(url + "maze/img/obj/item_e0"
						+ i + ".png"));
				item[5][i] = loadUrlImage(new URL(url + "maze/img/obj/item_f0"
						+ i + ".png"));
				item[6][i] = loadUrlImage(new URL(url + "maze/img/obj/item_g0"
						+ i + ".png"));
				item[7][i] = loadUrlImage(new URL(url + "maze/img/obj/item_h0"
						+ i + ".png"));
				item[8][i] = loadUrlImage(new URL(url + "maze/img/obj/item_i0"
						+ i + ".png"));
				item[9][i] = loadUrlImage(new URL(url + "maze/img/obj/item_j0"
						+ i + ".png"));
			}
			trap = new Image[2][6];
			for (int i = 0; i < trap[0].length; i++) {
				trap[0][i] = loadUrlImage(new URL(url + "maze/img/obj/bad_a0"
						+ i + ".png"));
				trap[1][i] = loadUrlImage(new URL(url + "maze/img/obj/bad_b0"
						+ i + ".png"));
			}
			star = new Image[3];
			for (int i = 0; i < star.length; i++) {
				star[i] = loadUrlImage(new URL(url + "maze/img/obj/star_" + i
						+ ".png"));
			}

			random = loadUrlImage(new URL(url + "maze/img/obj/random.png"));
		} catch (Exception e) {
			((Listener) GameData.getInstance().game)
					.listener(MazeGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingCommon() {
		try {
			ok = new Image[2];
			for (int i = 0; i < ok.length; i++) {
				ok[i] = loadUrlImage(new URL(url + "maze/img/char/ok0" + i
						+ ".png"));
			}
			score = new Image[5];
			for (int i = 0; i < score.length; i++) {
				score[i] = loadUrlImage(new URL(url + "maze/img/number/b" + i
						+ ".png"));
			}
			numberS = new Image[10];
			for (int i = 0; i < numberS.length; i++) {
				numberS[i] = loadUrlImage(new URL(url + "maze/img/number/s" + i
						+ ".png"));
			}

			numberL = new Image[10];
			for (int i = 0; i < numberL.length; i++) {
				numberL[i] = loadUrlImage(new URL(url + "pop_result/end_" + i
						+ ".png"));
			}

			crTalk = new Image[2];
			for (int i = 0; i < crTalk.length; i++) {
				crTalk[i] = loadUrlImage(new URL(url + "maze/img/char/talk_0"
						+ i + ".png"));
			}

			gage = loadUrlImage(new URL(url + "maze/img/bg/gage.png"));
			stage = new Image[5];
			for (int i = 0; i < stage.length; i++) {
				stage[i] = loadUrlImage(new URL(url + "maze/img/bg/st_"
						+ (i + 1) + ".png"));
			}
			string = new Image[4];
			for (int i = 0; i < string.length; i++) {
				string[i] = loadUrlImage(new URL(url + "maze/img/bg/str_" + i
						+ ".png"));
			}
			text = new Image[8];
			for (int i = 0; i < text.length; i++) {
				text[i] = loadUrlImage(new URL(url + "maze/img/bg/txt_" + i
						+ ".png"));
			}

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

		} catch (Exception e) {
			((Listener) GameData.getInstance().game)
					.listener(MazeGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	public void loadingEffact() {
		try {
			effactBad = new Image[6];
			for (int i = 0; i < effactBad.length; i++) {
				effactBad[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/bad_ef0" + (i + 1) + ".png"));
			}
			effactGift = new Image[5];
			for (int i = 0; i < effactGift.length; i++) {
				effactGift[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/gift0" + (i + 1) + ".png"));
			}
			effactIce = new Image[6];
			for (int i = 0; i < effactIce.length; i++) {
				effactIce[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/ice0" + (i + 1) + ".png"));
			}
			effactIceNum = new Image[5];
			for (int i = 0; i < effactIceNum.length; i++) {
				effactIceNum[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/ice_num" + (i + 1) + ".png"));
			}
			effactSmog = new Image[5];
			for (int i = 0; i < effactSmog.length; i++) {
				effactSmog[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/smog0" + (i + 1) + ".png"));
			}
			effactSmok = new Image[4];
			for (int i = 0; i < effactSmok.length; i++) {
				effactSmok[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/smoke_0" + (i + 1) + ".png"));
			}
			effactStar = new Image[7];
			for (int i = 0; i < effactStar.length; i++) {
				effactStar[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/star_ef0" + (i + 1) + ".png"));
			}
			effactTimeUp = loadUrlImage(new URL(url
					+ "maze/img/effect/time_add.png"));

			effactTime = new Image[7];
			for (int i = 0; i < effactTime.length; i++) {
				effactTime[i] = loadUrlImage(new URL(url
						+ "maze/img/effect/time_ef0" + (i + 1) + ".png"));
			}
		} catch (Exception e) {
			((Listener) GameData.getInstance().game)
					.listener(MazeGPlay.GAME_ERROR);
			e.printStackTrace();
		}
	}

	private void loadingSound() {
		// 사운드 로딩

		try {
			/*
			 * public static final int SOUND_START = 0; public static final int
			 * SOUND_GAMEOVER = 1; public static final int SOUND_SUCESS = 2;
			 * public static final int SOUND_MOVE = 3; public static final int
			 * SOUND_STAR1 = 4; public static final int SOUND_STAR2 = 5; public
			 * static final int SOUND_STAR3 = 6; public static final int
			 * SOUND_HURRY = 7; public static final int SOUND_MOVEITEM = 8;
			 * public static final int SOUND_ICE_JUMP = 9; public static final
			 * int SOUND_ICE_COUNT = 10; public static final int
			 * SOUND_ICE_FINISH = 11; public static final int SOUND_GOODITEM =
			 * 12; public static final int SOUND_TRAP_RED = 13; public static
			 * final int SOUND_TRAT_BLUE = 14; public static final int
			 * SOUND_GIVEITEM = 15;
			 */
			sound = new Sound(16);
			sound.loadSound(new URL(url + "maze/snd/start.mp2"));
			sound.loadSound(new URL(url + "maze/snd/gameover.mp2"));
			sound.loadSound(new URL(url + "maze/snd/success.mp2"));
			sound.loadSound(new URL(url + "maze/snd/move.mp2"));
			sound.loadSound(new URL(url + "maze/snd/star1.mp2"));
			sound.loadSound(new URL(url + "maze/snd/star2.mp2"));
			sound.loadSound(new URL(url + "maze/snd/star3.mp2"));
			sound.loadSound(new URL(url + "maze/snd/hurry.mp2"));
			sound.loadSound(new URL(url + "maze/snd/move_item.mp2"));
			sound.loadSound(new URL(url + "maze/snd/ice_jump.mp2"));
			sound.loadSound(new URL(url + "maze/snd/ice_count.mp2"));
			sound.loadSound(new URL(url + "maze/snd/ice_finish.mp2"));
			sound.loadSound(new URL(url + "maze/snd/good_item.mp2"));
			sound.loadSound(new URL(url + "maze/snd/trap_red.mp2"));
			sound.loadSound(new URL(url + "maze/snd/trap_blue.mp2"));
			sound.loadSound(new URL(url + "maze/snd/give_item.mp2"));

		} catch (Throwable e) {
			((Listener) GameData.getInstance().game)
					.listener(MazeGPlay.GAME_ERROR);
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
		if (map != null) {
			SceneManager.getInstance().removeImage(map);
			map = null;
		}
		if (cr != null) {
			for (int i = 0; i < cr.length; i++) {
				SceneManager.getInstance().removeImage(cr[i]);
			}
			cr = null;
		}
		if (crCheering != null) {
			for (int i = 0; i < crCheering.length; i++) {
				SceneManager.getInstance().removeImage(crCheering[i]);
			}
			crCheering = null;
		}
		if (crGameover != null) {
			for (int i = 0; i < crGameover.length; i++) {
				SceneManager.getInstance().removeImage(crGameover[i]);
			}
			crGameover = null;
		}
		if (crSuccess != null) {
			for (int i = 0; i < crSuccess.length; i++) {
				SceneManager.getInstance().removeImage(crSuccess[i]);
			}
			crSuccess = null;
		}
		if (prIdle != null) {

			for (int i = 0; i < prIdle.length; i++) {
				SceneManager.getInstance().removeImage(prIdle[i]);
			}
			prIdle = null;
		}
		if (prMove != null) {
			for (int i = 0; i < prMove.length; i++) {
				SceneManager.getInstance().removeImage(prMove[i]);
			}
			prMove = null;
		}
		if (prLeftIdle != null) {
			for (int i = 0; i < prLeftIdle.length; i++) {
				SceneManager.getInstance().removeImage(prLeftIdle[i]);
			}
			prLeftIdle = null;
		}
		if (prLeftMove != null) {
			for (int i = 0; i < prLeftMove.length; i++) {
				SceneManager.getInstance().removeImage(prLeftMove[i]);
			}
			prLeftMove = null;
		}
		if (prRightIdle != null) {
			for (int i = 0; i < prRightIdle.length; i++) {
				SceneManager.getInstance().removeImage(prRightIdle[i]);
			}
			prRightIdle = null;
		}
		if (prRightMove != null) {
			for (int i = 0; i < prRightMove.length; i++) {
				SceneManager.getInstance().removeImage(prRightMove[i]);
			}
			prRightMove = null;
		}
		if (prBackdle != null) {
			for (int i = 0; i < prBackdle.length; i++) {
				SceneManager.getInstance().removeImage(prBackdle[i]);
			}
			prBackdle = null;
		}
		if (prBackMove != null) {
			for (int i = 0; i < prBackMove.length; i++) {
				SceneManager.getInstance().removeImage(prBackMove[i]);
			}
			prBackMove = null;
		}
		if (prHave != null) {
			for (int i = 0; i < prHave.length; i++) {
				SceneManager.getInstance().removeImage(prHave[i]);
			}
			prHave = null;
		}
		if (prBad != null) {
			for (int i = 0; i < prBad.length; i++) {
				SceneManager.getInstance().removeImage(prBad[i]);
			}
			prBad = null;
		}
		if (prIce != null) {
			for (int i = 0; i < prIce.length; i++) {
				SceneManager.getInstance().removeImage(prIce[i]);
			}
			prIce = null;
		}
		if (prGameover != null) {
			for (int i = 0; i < prGameover.length; i++) {
				SceneManager.getInstance().removeImage(prGameover[i]);
			}
			prGameover = null;
		}
		if (prSuccess != null) {
			for (int i = 0; i < prSuccess.length; i++) {
				SceneManager.getInstance().removeImage(prSuccess[i]);
			}
			prSuccess = null;
		}
	}
	
	public void destroy2() {
		if (item != null) {
			for (int i = 0; i < item.length; i++) {
				for (int j = 0; j < item[i].length; j++) {
					SceneManager.getInstance().removeImage(item[i][j]);
				}
				item[i] = null;
			}
			item = null;
		}
		if (trap != null) {
			for (int i = 0; i < trap.length; i++) {
				for (int j = 0; j < trap[i].length; j++) {
					SceneManager.getInstance().removeImage(trap[i][j]);
				}
				trap[i] = null;
			}
			trap = null;
		}
		if (star != null) {
			for (int i = 0; i < star.length; i++) {
				SceneManager.getInstance().removeImage(star[i]);
			}
			star = null;
		}
		if (random != null) {
			SceneManager.getInstance().removeImage(random);
			random = null;
		}
		if (ok != null) {
			for (int i = 0; i < ok.length; i++) {
				SceneManager.getInstance().removeImage(ok[i]);
			}
			ok = null;
		}
		if (score != null) {
			for (int i = 0; i < score.length; i++) {
				SceneManager.getInstance().removeImage(score[i]);
			}
			score = null;
		}
		if (numberS != null) {
			for (int i = 0; i < numberS.length; i++) {
				SceneManager.getInstance().removeImage(numberS[i]);
			}
			numberS = null;
		}

		if (numberL != null) {
			for (int i = 0; i < numberL.length; i++) {
				SceneManager.getInstance().removeImage(numberL[i]);
			}
			numberL = null;
		}

		if (crTalk != null) {
			for (int i = 0; i < crTalk.length; i++) {
				SceneManager.getInstance().removeImage(crTalk[i]);
			}
			crTalk = null;
		}
		if (gage != null) {

			SceneManager.getInstance().removeImage(gage);
			gage = null;
		}
		if (stage != null) {
			for (int i = 0; i < stage.length; i++) {
				SceneManager.getInstance().removeImage(stage[i]);
			}
			stage = null;
		}
		if (string != null) {
			for (int i = 0; i < string.length; i++) {
				SceneManager.getInstance().removeImage(string[i]);
			}
			string = null;
		}
		if (text != null) {
			for (int i = 0; i < text.length; i++) {
				SceneManager.getInstance().removeImage(text[i]);
			}
			text = null;
		}
		SceneManager.getInstance().removeImage(exitPop);
		if (btnYes != null) {
			for (int i = 0; i < btnYes.length; i++) {
				SceneManager.getInstance().removeImage(btnYes[i]);
			}
			btnYes = null;
		}
		if (btnNo != null) {
			for (int i = 0; i < btnNo.length; i++) {
				SceneManager.getInstance().removeImage(btnNo[i]);
			}
			btnNo = null;
		}
		SceneManager.getInstance().removeImage(finishPop);
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

		if (effactBad != null) {
			for (int i = 0; i < effactBad.length; i++) {
				SceneManager.getInstance().removeImage(effactBad[i]);
			}
			effactBad = null;
		}
		if (effactGift != null) {
			for (int i = 0; i < effactGift.length; i++) {
				SceneManager.getInstance().removeImage(effactGift[i]);
			}
			effactGift = null;
		}
		if (effactIce != null) {
			for (int i = 0; i < effactIce.length; i++) {
				SceneManager.getInstance().removeImage(effactIce[i]);
			}
			effactIce = null;
		}
		if (effactIceNum != null) {
			for (int i = 0; i < effactIceNum.length; i++) {
				SceneManager.getInstance().removeImage(effactIceNum[i]);
			}
			effactIceNum = null;
		}
		if (effactSmog != null) {
			for (int i = 0; i < effactSmog.length; i++) {
				SceneManager.getInstance().removeImage(effactSmog[i]);
			}
			effactSmog = null;
		}
		if (effactSmok != null) {
			for (int i = 0; i < effactSmok.length; i++) {
				SceneManager.getInstance().removeImage(effactSmok[i]);
			}
			effactSmok = null;
		}
		if (effactStar != null) {
			for (int i = 0; i < effactStar.length; i++) {
				SceneManager.getInstance().removeImage(effactStar[i]);
			}
			effactStar = null;
		}
		if (effactTimeUp != null) {
			SceneManager.getInstance().removeImage(effactTimeUp);
			effactTimeUp = null;
		}
		if (effactTime != null) {
			for (int i = 0; i < effactTime.length; i++) {
				SceneManager.getInstance().removeImage(effactTime[i]);
			}
			effactTime = null;
		}
		instance = null;
		if (sound != null) {
			sound.destroySound();
			sound = null;
		}
	}
}
