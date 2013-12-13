package pororo.com.game.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.dvb.ui.DVBColor;
import org.havi.ui.event.HRcEvent;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;
import pororo.com.framework.Sound;
import pororo.com.game.GameTitle;
import pororo.com.log.Log;

public class BoardGPlay extends Scene {
	// align
	private static final int Center_X = 480;

	private String imgurl = "";
	private String imgurl1 = "";
	// String imgurl2="http://125.147.46.109/kt/";

	private int GState = 0;
	private final int STATE_LODAING = 0;
	private final int STATE_READY = 10;
	private final int STATE_GAME = 20;
	private final int STATE_OVER = 30;
	private final int STATE_RESULT = 40;
	private final int STATE_ERROR = 50;

	private Font myFont;

	private int GFrame = 0;
	private boolean g_pause = false;
	private byte g_endCur;
	private int ETime;

	// public int End

	private int GTime = 0;
	private int NowTime = 0;
	private int SaveTime = 0;
	private int GScore = 0;
	private byte GGrade = 0;
	private int GLife = 0;
	// public int G_Level[]= {270, 670, 1790, 2670, 5440, 7440};
	private int G_Level[] = { 300, 600, 900, 1500, 2000, 3000, 4000, 5500, 7500 };
	private int O_Speed[] = { 10, 13, 15, 18, 20, 23, 25, 28, 30 };
	// public int O_Speed[]= {5, 10, 15, 20, 25, 30};
	private int PLevel = 0;

	private int p_x;
	private int p_y;
	private byte p_state;
	private byte p_action;
	private int move_direction;
	private int p_frame;
	private boolean item_state;
	private boolean space_state;
	private int p_time;
	private int p_btime;

	private int Ani_Frame;
	private int[][] p_info;
	private int p_position;

	private boolean cloud_state = false;
	private int cloud_time = 0;
	private int cloud_btime = 0;

	private final byte // p_state
			BASICS = 0,
			SHIELD = 1, SUPER = 2, ICE = 3, REVERSE = 4,
			ICEREVERSE = 5,
			SPACE = 6;

	private final byte // p_action
			WAIT = 0,
			LEFT = 1, RIGHT = 2, NICE = 3, COLLISION = 4, START = 5,
			RUN = 6,
			END = 7, DIE = 8, DIE1 = 9;

	// HIT = 5,

	private class Objects {
		int o_Kind; // 적 속성
		int o_State; // 현재 상태
		int o_x; // x좌표
		int o_y; // y좌표
		// int o_Frame[] =new int[2];

	}

	private Objects objects[];

	private int objNum = 0;
	private int obj_kind = 0;
	// int obitime =0;
	private int e_attribute[][] = new int[20][5];
	private int attributenum = 0;
	private byte obj_type[][] = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 },
			{ 1, 1, 0 }, { 1, 0, 1 }, { 0, 1, 1 }

	};

	private final int O_NONE = 0, O_MOVE = 1, O_DISAPPEAR = 2;

	private final byte // item
			ITEM_STAR = 0,
			ITEM_GOLD = 1, ITME_BOMB = 2, ITME_SHIELD = 3,
			ITME_SUPER = 4,
			ITME_LIFE = 5, ITME_SPACE = 6, ITME_ICEBERG = 7,
			ITME_REVERSE = 8,
			ITME_ICE = 9, ITEM_CLOUD = 10, ITEM_NONE = 100;

	private boolean img_check = false;

	private int reverseTic;  // 거꾸로 이동 표시 (오리 대신) 카운트
	
	private Image img_reverse[];  // 거꾸로 이동 표시 (오리 대신)
	private Image img_load[];
	private Image img_num[];
	private Image img_txt[];
	private Image img_pororo[];
	private Image Img_Obj[];
	private Image img_effect[];
	private Image img_life;
	private Image img_Item[];
	private Image img_endPop;
	private Image img_btnYes[];
	private Image img_btnNo[];
	private Image img_endnum[];
	private Image img_endbtnY[];
	private Image img_endbtnN[];
	private Image img_endrank[];

	private Image img_endbox;

	private Sound m_sound;

	private boolean sound_kind = false;

	private final byte // item
			S_BAD0 = 0,
			S_BAD1 = 1, S_GOOD0 = 2, S_GOOD1 = 3, S_PORORO = 4,
			S_ICE = 5,
			S_CLOUD = 6, S_START = 7, S_END = 8, S_OVER = 9;

	public BoardGPlay() {
		Imgload_loadingbar();
		if (img_load[1] != null) {
			GState = STATE_LODAING;
			GTime = 0;
			img_check = false;
		}
		// g_pause=false;
		// g_endCur = 0;
		// Btime=0;
	}

	public void process(int elapsedtime) {
		GFrame += elapsedtime;
		switch (GState) {
		case STATE_LODAING:
			process_loading();
			break;
		case STATE_READY:
			if (!g_pause)
				GTime = SaveTime + (GFrame - NowTime) / 100;
			break;
		case STATE_GAME:
			if (!g_pause)
				process_game();
			break;
		case STATE_OVER:
			GTime = (GFrame - NowTime) / 100;
			break;
		case STATE_RESULT:
			GTime = (GFrame - NowTime) / 100;

			if (GFrame > 100) {
				ETime++;
				if (ETime > 5)
					ETime = 0;
			}
			break;
		}
	}

	public void draw(Graphics2D g) {
		if (GState == STATE_LODAING) {
			draw_loading(g);
			return;
		}
		switch (GState) {
		case STATE_READY:
			draw_ready(g);
			break;
		case STATE_GAME:
			draw_game(g);
			break;
		case STATE_OVER:
			draw_over(g);
			break;
		case STATE_RESULT:
			draw_result(g);
			break;
		case STATE_ERROR:
			g.setColor(new DVBColor(0, 0, 0, 80));
			g.fillRect(0, 0, 960, 540);
			g.drawImage( SceneManager.getInstance().imgPopupA, 221, 96, null);
			SceneManager.getInstance().drawButton(g, 0, 420, 360);
			g.setColor(new Color(53, 53, 53));
			g.setFont(new Font("Bold", 0, 20));
			SceneManager.DrawStr(g, "알     림", 480, 152);
			g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
			g.setFont(new Font("Bold", 0, 17));
			g.setColor(Color.red);
			SceneManager.DrawStr(g, "통신상태가 원활하지 않습니다.", 480, 208 + (2 * 22));
			SceneManager.DrawStr(g, "잠시 후 다시 이용해 주시기 바랍니다.", 480, 208 + (3 * 22));
			break;
		}
		if (GState != STATE_ERROR)
			draw_UI(g);
		if (g_pause)
			drawEndPop(g);

	}

	public void draw_UI(Graphics g) {
		// GScore=0;
		draw_num(g, 0, GScore, 860, 64, 22);
		for (int i = 0; i < GLife; i++) {
			DrawImg(g, img_life, 186 - (i * 44), 60, SceneManager.TL);
		}

		// draw_num(g, 0, PLevel, 300, 64, 22);
	}

	public void drawEndPop(Graphics g) {
		draw_bgalpha(g);
		g.drawImage(img_endPop, 304, 123, null);

		if (g_endCur == 0) {
			g.drawImage(img_btnYes[0], 356, 325, null);
			g.drawImage(img_btnNo[1], 477, 325, null);
		} else {
			g.drawImage(img_btnYes[1], 356, 325, null);
			g.drawImage(img_btnNo[0], 477, 325, null);
		}
	}

	public void processKey(Object nn, int key) {
		System.out.println("BoardGPlay processKey : " + key);
		switch (GState) {
		case STATE_READY:
			System.out.println("BoardGPlay STATE_READY");
			key_ready(key);
			break;
		case STATE_GAME:
			System.out.println("BoardGPlay STATE_GAME");
			key_game(key);
			break;
		case STATE_OVER:
			System.out.println("BoardGPlay STATE_OVER");
			break;
		case STATE_RESULT:
			System.out.println("BoardGPlay STATE_RESULT");
			key_result(key);
			break;
		case STATE_ERROR:
			System.out.println("BoardGPlay STATE_ERROR");
			switch (key) {
			case HRcEvent.VK_ENTER:
			case Constant.KEY_PREV:
				SceneManager.getInstance().load_kind = true; // 120523_1
				SceneManager.getInstance().setChangeScene(
						SceneManager.SATAE_PORTAL_MENU);
				break;
			}
			break;

		}
	}

	public void destroyScene() {
		Log.trace("---board destroy-- 1");
		myFont = null;
		p_info = null;
		objects = null;
		e_attribute = null;
		Log.trace("---board destroy-- 2");
		
		if(m_sound != null)
		{
			m_sound.destroySound();
			m_sound = null;
		}
		
		Log.trace("---board destroy-- 3");
		if(img_num != null)
		{
			for (int i = 0; i < 10; i++) {

				if (img_num[i] != null) {
					SceneManager.getInstance().removeImage(img_num[i]);
					img_num[i] = null;
				}
			}
		}
		if(img_endnum != null)
		{
			for (int i = 0; i < 10; i++) {
				if (img_endnum[i] != null) {
					SceneManager.getInstance().removeImage(img_endnum[i]);
					img_endnum[i] = null;
				}
			}

		}
	
		Log.trace("---board destroy-- 4");
		for (int i = 0; i < 2; i++) {
			if (img_load != null && img_load[i] != null) {
				SceneManager.getInstance().removeImage(img_load[i]);
				img_load[i] = null;
			}
			if (img_endbtnY != null && img_endbtnY[i] != null) {
				SceneManager.getInstance().removeImage(img_endbtnY[i]);
				img_endbtnY[i] = null;
			}
			if (img_endbtnN != null && img_endbtnN[i] != null) {
				SceneManager.getInstance().removeImage(img_endbtnN[i]);
				img_endbtnN[i] = null;
			}
			if (img_btnYes != null && img_btnYes[i] != null) {
				SceneManager.getInstance().removeImage(img_btnYes[i]);
				img_btnYes[i] = null;
			}
			if (img_btnNo != null && img_btnNo[i] != null) {
				SceneManager.getInstance().removeImage(img_btnNo[i]);
				img_btnNo[i] = null;
			}
		}
		Log.trace("---board destroy-- 5");
		
		if(img_pororo != null)
		{
			for (int i = 0; i < 91; i++) {
				if (img_pororo[i] != null) {
					SceneManager.getInstance().removeImage(img_pororo[i]);
					img_pororo[i] = null;
				}
			}
		}
		Log.trace("---board destroy-- 6");
		
		if (null != img_reverse) {
			for (int i=0; i<2; i++) {
				if (null != img_reverse[i]) {
					SceneManager.getInstance().removeImage(img_reverse[i]);
					img_reverse[i] = null;
				}
			}
		}
		
		if(img_txt != null)
		{
			for (int i = 0; i < 6; i++) {
				if (img_txt[i] != null) {
					SceneManager.getInstance().removeImage(img_txt[i]);
					img_txt[i] = null;
				}
			}
		}

		if(Img_Obj != null)
		{
			for (int i = 0; i < 11; i++) {
				if (Img_Obj[i] != null) {
					SceneManager.getInstance().removeImage(Img_Obj[i]);
					Img_Obj[i] = null;
				}
			}
		}
		Log.trace("---board destroy-- 7");
		if(img_effect != null)
		{
			for (int i = 0; i < 18; i++) {
				if (img_effect[i] != null) {
					SceneManager.getInstance().removeImage(img_effect[i]);
					img_effect[i] = null;
				}
			}
		}

		if(img_endrank != null)
		{
			for (int i = 0; i < 4; i++) {
				if (img_endrank[i] != null) {
					SceneManager.getInstance().removeImage(img_endrank[i]);
					img_endrank[i] = null;
				}
			}
		}
		Log.trace("---board destroy-- 8");
		if(img_Item != null)
		{
			for (int i = 0; i < 3; i++) {
				if (img_Item[i] != null) {
					SceneManager.getInstance().removeImage(img_Item[i]);
					img_Item[i] = null;
				}
			}
		}
		Log.trace("---board destroy-- 9");
		if (img_life != null) {
			SceneManager.getInstance().removeImage(img_life);
			img_life = null;
		}
		if (img_endbox != null) {
			SceneManager.getInstance().removeImage(img_endbox);
			img_endbox = null;
		}
		if (img_endPop != null) {
			SceneManager.getInstance().removeImage(img_endPop);
			img_endPop = null;
		}
		Log.trace("---board destroy-- 10");
	}

	public void process_loading() {
		GTime++;
		if(StateValue.isUrlLive) {
			imgurl = StateValue.liveResource + "board/img/";
			imgurl1 = StateValue.liveResource;
			
		}
		else {
			imgurl = StateValue.testResource + "board/img/";
			imgurl1 = StateValue.testResource;
		}
		System.out.println("gtime=" + GTime);
		if (GTime == 1) {
			Imgload_txt();
		} else if (GTime == 2) {
			Imgload_pororo0();
		} else if (GTime == 3) {
			Imgload_pororo1();

		} else if (GTime == 5) {
			Imgload_pororo2();
		} else if (GTime == 6) {
			Imgload_item();
//			Imgload_pororo3();
		} else if (GTime == 7) {
//			Imgload_pororo4();
		} else if (GTime == 9) {
			Imgload_pororo5();

		} else if (GTime == 10) {
			Imgload_pororo6();
		} else if (GTime == 11) {
			Imgload_obj();

		} else if (GTime == 12) {
			Imgload_effect0();
		} else if (GTime == 13) {
			Imgload_effect1();
		} else if (GTime == 14) {
			Imgload_num();
		} else if (GTime == 15) {
			Imgload_endpopup0();
		} else if (GTime == 16) {
			Imgload_endpopup1();
		} else if (GTime == 17) {

			Imgload_res();
		} else if (GTime == 18) {

			Sndload_res();
		} else if (GTime == 19) {
			init_pororo();
			dataload_pororo();
			init_obj();
			effectinit();
			inti_game();
		} else if (GTime == 20) {

			NowTime = GFrame;
			GTime = 0;
			SaveTime = 0;
			g_pause = false;
			if (!img_check) {
				try {
					if(StateValue.isUrlLive) {
						if(!SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.liveResource + "bg/board/game_bg.gif")))
							img_check =true;
					}
					else {
						if(!SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.testResource + "bg/board/game_bg.gif")))
							img_check =true;
					
					}
				} catch (MalformedURLException e) {
					img_check = true;
				}
				if (!img_check)
					GState = STATE_READY;
				else
					GState = STATE_ERROR;
			} else
				GState = STATE_ERROR;

			
			SceneManager.getInstance().load_kind = false;
		}

	}

	public void draw_loading(Graphics g) {
		// System.out.println("img================="+img_load[1].getWidth(null)/(20-GTime));
		g.drawImage(img_load[0], Center_X - 287, 464, null);
		if (GTime < 20)
			// g.drawImage( img_load[1], Center_X-287, 480,
			// Center_X-287+(img_load[1].getWidth(null)/(10-GTime))-0+1,
			// 480+(38-0+1), 0, 0, img_load[1].getWidth(null)/(10-GTime), 38,
			// null);
			DrawImg(g, img_load[1], Center_X - 287, 464, 0, 0, 28 * GTime,
					img_load[1].getHeight(null));
		else
			g.drawImage(img_load[1], Center_X - 287, 464, null);

	}

	public void draw_ready(Graphics g) {
		draw_pororochar(g);
		if (GTime > 5 && GTime <= 21) {
			if (!sound_kind) {
				m_sound.playSound(S_START);
				sound_kind = true;
			}
			g.drawImage(img_txt[0], 373, 213, null);
			g.drawImage(img_txt[1], 506, 226, null);
		} else if (GTime > 21 && GTime <= 37) {
			g.drawImage(img_txt[2], 374, 225, null);
			g.drawImage(img_txt[3], 504, 217, null);
		} else if (GTime > 37) {
			GState = STATE_GAME;
			NowTime = GFrame;
			GTime = 0;
			SaveTime = 0;
		}
	}

	public void key_ready(int key) {
		if (g_pause) {
			switch (key) {
			case HRcEvent.VK_RIGHT:
			case HRcEvent.VK_LEFT:
				if (g_endCur == 0)
					g_endCur = 1;
				else
					g_endCur = 0;
				break;
			case Constant.KEY_PREV:
				g_endCur = 0;
				g_pause = false;
				NowTime = GFrame;
				break;
			case HRcEvent.VK_ENTER:
				if (g_endCur == 0) {
					SceneManager.getInstance().load_kind = true;  //120523_1
					SceneManager.getInstance().setChangeScene(
							SceneManager.SATAE_GAME_MENU);
				} else {
					g_endCur = 0;
					g_pause = false;
					NowTime = GFrame;
					// if(pororo.p_kind>0)
					// pororo.n_time=GTime;

				}
				break;
			}
		} else {
			switch (key) {
			case Constant.KEY_PREV:
				g_endCur = 0;
				g_pause = true;
				SaveTime = GTime;
				m_sound.stopSound();
				// if(pororo.p_kind>0)
				// pororo.b_time=pororo.c_time;

				break;
			}
		}
	}

	public void process_game() {
		if (!g_pause) {
			GTime = SaveTime + (GFrame - NowTime) / 100;
			if (G_Level[PLevel] < GTime) {
				PLevel++;
				if (PLevel > 8)
					PLevel = 8;
			}
			if (GLife <= 0 && p_action == DIE1) {
				gameoverinit();
			}
			if (item_state) {
				if (GTime / 10 != p_btime) {
					p_btime = GTime / 10;
					p_time--;
					if (space_state) {
						e_attribute[attributenum][0] = 0;
						e_attribute[attributenum][1] = p_x;
						e_attribute[attributenum][2] = p_y;
						e_attribute[attributenum][3] = 0;
						e_attribute[attributenum][4] = 0;
						attributenum++;
						m_sound.playSound(S_GOOD0);
					}
					if (p_time == 0) {
						if (!space_state) {
							item_state = false;
							p_btime = 0;
							if (p_state == REVERSE)
								m_sound.playSound(S_PORORO);
							p_ChangeState(BASICS);
						} else {
							space_state = false;
							p_btime = 0;
							p_ChangeAction(END);
							// System.out.println("start end= "+p_action);

						}
					}

				}
			}
			if (cloud_state) {
				if (GTime / 10 != cloud_btime) {
					cloud_btime = GTime / 10;
					cloud_time--;
					if (cloud_time == 0) {
						cloud_state = false;
						cloud_btime = 0;
					}

				}
			}
		}
	}

	public void draw_game(Graphics g) {
		if (GTime > 30)
			draw_Obj(g);
		draw_pororochar(g);
		draw_event(g);
		draw_cloud(g);
	}

	public void key_game(int key) {
		System.out.println("GLife : " + GLife);
		System.out.println("g_pause : " + g_pause);
		if (GLife > 0) {
			if (g_pause) {
				switch (key) {
				case HRcEvent.VK_RIGHT:
				case HRcEvent.VK_LEFT:
					if (g_endCur == 0)
						g_endCur = 1;
					else
						g_endCur = 0;
					break;
				case Constant.KEY_PREV:
					g_endCur = 0;
					g_pause = false;
					NowTime = GFrame;
					break;
				case HRcEvent.VK_ENTER:
					if (g_endCur == 0) {
						SceneManager.getInstance().load_kind = true; //120523_1
						SceneManager.getInstance().setChangeScene(
								SceneManager.SATAE_GAME_MENU);
					} else {
						g_endCur = 0;
						g_pause = false;
						NowTime = GFrame;
						// if(pororo.p_kind>0)
						// pororo.n_time=GTime;

					}
					break;
				}
			} else {
				switch (key) {
				// case HRcEvent.VK_ESCAPE:
				case Constant.KEY_PREV:
					g_endCur = 0;
					g_pause = true;
					SaveTime = GTime;
					m_sound.stopSound();
					// if(pororo.p_kind>0)
					// pororo.b_time=pororo.c_time;

					break;
				case HRcEvent.VK_LEFT:
					System.out.println("HRcEvent.VK_LEFT");
					if (GLife > 0) {
						if (p_state == BASICS || p_state == SHIELD
								|| p_state == SUPER) {
							if (p_x > 360)
								p_ChangeAction(LEFT);

						} else if (p_state == REVERSE) {
							if (p_x < 600)
								p_ChangeAction(LEFT);
						}
					}
					break;
				case HRcEvent.VK_RIGHT:
					System.out.println("HRcEvent.VK_RIGHT");
					if (GLife > 0) {
						if (p_state == BASICS || p_state == SHIELD
								|| p_state == SUPER) {
							if (p_x < 600)
								p_ChangeAction(RIGHT);

						} else if (p_state == REVERSE) {
							if (p_x > 360)
								p_ChangeAction(RIGHT);
						}
					}
					break;

				}
			}
		}
	}

	public void draw_over(Graphics g) {
		// draw_Obj(g);
		if (GTime > 5 && GTime <= 13) {
			if (!sound_kind) {
				m_sound.playSound(S_OVER);
				sound_kind = true;
			}
			g.drawImage(img_txt[4], 319, 216, null);
		} else if (GTime > 13 && GTime <= 20) {
			g.drawImage(img_txt[4], 319, 216, null);
			g.drawImage(img_txt[5], 484, 211, null);
		} else if (GTime > 20) {

			g_endCur = 0;
			ETime = 0;
			NowTime = GFrame;
			if (GScore >= 50000)
				GGrade = 3;
			else if (GScore >= 35000 && GScore < 50000)
				GGrade = 2;
			else if (GScore >= 13000 && GScore < 35000)
				GGrade = 1;
			else
				GGrade = 0;

			GState = STATE_RESULT;
		}
	}

	public void draw_result(Graphics g) {
		g.drawImage(img_endbox, 280, 116, null);

		if (g_endCur == 0) {
			g.drawImage(img_endbtnN[0], 314, 326, null);
			g.drawImage(img_endbtnY[1], 491, 326, null);
		} else {
			g.drawImage(img_endbtnN[1], 314, 326, null);
			g.drawImage(img_endbtnY[0], 491, 326, null);
		}

		switch (ETime) {
		case 0:
			g.drawImage(img_endrank[GGrade], 317, 200 - 6, null);
			break;
		case 1:
			g.drawImage(img_endrank[GGrade], 317, 200 + 2 - 6, null);
			break;
		case 2:
			g.drawImage(img_endrank[GGrade], 317, 200 + 2 + 2 - 6, null);
			break;
		case 3:
			g.drawImage(img_endrank[GGrade], 317, 200 + 2 + 2 + 2 - 6, null);
			break;
		case 4:
			g.drawImage(img_endrank[GGrade], 317, 200 + 2 + 2 - 6, null);
			break;
		case 5:
			g.drawImage(img_endrank[GGrade], 317, 200 + 2 - 6, null);
			break;
		}

		// 점수
		draw_num(g, 1, GScore, 586, 243, 26);

	}

	public void key_result(int key) {
		switch (key) {
		case HRcEvent.VK_RIGHT:
		case HRcEvent.VK_LEFT:
			if (g_endCur == 0)
				g_endCur = 1;
			else
				g_endCur = 0;
			break;

		case HRcEvent.VK_ENTER:
			if (g_endCur == 0) {
				objects = null;
				init_pororo();
				effectinit();
				init_obj();
				inti_game();
				NowTime = GFrame;
				GTime = 0;
				SaveTime = 0;
				g_pause = false;
				GState = STATE_READY;
			} else {
				SceneManager.getInstance().load_kind = true; //120523_1
				SceneManager.getInstance().setChangeScene(
						SceneManager.SATAE_GAME_MENU,
						GameTitle.STATE_TITLE_RANK);
			}
			break;
		}
	}

	public void draw_pororochar(Graphics g) {
		// System.out.println("p_state="+p_state);
		// System.out.println("p_action="+p_action);
		if (p_action == DIE1) {
			charSprite(g, 148, 2);
		} else {
			switch (p_state) {
			case BASICS:
				switch (p_action) {
				case WAIT:
					// charSprite(g, 138, 3);
					charSprite(g, 0, 11);
					break;
				case LEFT:
					charSprite(g, 12, 5);
					break;
				case RIGHT:
					charSprite(g, 18, 5);
					break;
				case NICE:
					charSprite(g, 28, 3);
					break;
				case COLLISION:
					charSprite(g, 24, 3);
					break;
				case DIE:
					charSprite(g, 84, 7);
					break;
				}
				break;
			case SHIELD:
				switch (p_action) {
				case WAIT:
					charSprite(g, 32, 5);
					break;
				case LEFT:
					charSprite(g, 38, 5);
					break;
				case RIGHT:
					charSprite(g, 44, 5);
					break;
				case NICE:
					charSprite(g, 50, 3);
					break;
				}
				break;
			case SUPER:
				switch (p_action) {
				case WAIT:
					charSprite(g, 54, 5);
					break;
				case LEFT:
					charSprite(g, 60, 5);
					break;
				case RIGHT:
					charSprite(g, 66, 5);
					break;
				case NICE:
					charSprite(g, 72, 3);
					break;
				}
				break;
			case ICE:
				switch (p_action) {
				case WAIT:
					charSprite(g, 80, 3);
					break;
				case COLLISION:
					charSprite(g, 76, 3);
					break;
				case NICE:
					charSprite(g, 80, 3);
					break;
				case DIE:
					charSprite(g, 84, 7);
					break;

				}
				break;
			case REVERSE:
				switch (p_action) {
				case WAIT:
					charSprite(g, 92, 7);
					break;
				case LEFT:
					charSprite(g, 100, 5);
					break;
				case RIGHT:
					charSprite(g, 106, 5);
					break;
				case NICE:
					charSprite(g, 116, 3);
					break;
				case COLLISION:
					charSprite(g, 112, 3);
					break;
				case DIE:
					charSprite(g, 128, 7);
					break;
				}
				break;
			case ICEREVERSE:
				switch (p_action) {
				case WAIT:
					charSprite(g, 124, 3);
					break;
				case COLLISION:
					charSprite(g, 120, 3);
					break;
				case NICE:
					charSprite(g, 124, 3);
					break;
				case DIE:
					charSprite(g, 128, 7);
					break;
				}
				break;
			case SPACE:
				switch (p_action) {
				case START:
					charSprite(g, 136, 3);
					break;
				case RUN:
					charSprite(g, 140, 3);
					break;
				case END:
					charSprite(g, 144, 3);
					break;
				}
				break;
			}

		}

	}

	public void charSprite(Graphics g, int nStart, int nEnd) {

		Ani_Frame = nStart + p_frame;
		if (p_action != DIE1) {
			
			if (!g_pause) {
				
				if (p_info[Ani_Frame][4] == 1) {
					if (p_x > 360) {
						p_x -= 20;
						move_direction = 1;
						if (p_position <= 480) {
							if (p_x <= 360) {
								p_frame = nEnd;
								move_direction = 0;
							}
						} else {
							if (p_x <= 480) {
								p_frame = nEnd;
								move_direction = 0;
							}
						}
					}
				}

				else if (p_info[Ani_Frame][4] == 2) {
					if (p_x < 600) {
						p_x += 20;
						move_direction = 2;
						if (p_position >= 480) {
							if (p_x >= 600) {
								p_frame = nEnd;
								move_direction = 0;
							}
						} else {
							if (p_x >= 480) {
								p_frame = nEnd;
								move_direction = 0;
							}
						}
					}
				} else {
					if (p_x > 360 && p_x < 480) {
						if (move_direction == 1) {
							p_x -= 20;
							if (p_x < 360) {
								p_x = 360;
								move_direction = 0;
							}
						} else if (move_direction == 2) {
							p_x += 20;
							if (p_x > 480) {
								p_x = 480;
								move_direction = 0;
							}
						}
					} else if (p_x > 480 && p_x < 600) {
						if (move_direction == 1) {
							p_x -= 20;
							if (p_x < 480) {
								p_x = 480;
								move_direction = 0;
							}
						} else if (move_direction == 2) {
							p_x += 20;
							if (p_x > 600) {
								p_x = 600;
								move_direction = 0;
							}
						}
					}
				}
			}
//			System.out.println("p_x : " + p_x);
			if (p_info[Ani_Frame][5] > 10) { // 우주선 뒷 배경
				g.setColor(new Color(247, 249, 251));
				g.fillRect(p_x - p_info[Ani_Frame][6], 0, p_info[Ani_Frame][5],
						p_y + p_info[Ani_Frame][7]);
				g.fillRect(p_x + p_info[Ani_Frame][6] - p_info[Ani_Frame][5],
						0, p_info[Ani_Frame][5], p_y + p_info[Ani_Frame][7]);
			}
			
			/*** 뽀로로 이미지 리소스 빼기 (38~77) ***/
			int img_idx = p_info[Ani_Frame][0];
			
			
//			int revision;
//			if(Ani_Frame >= 92 && Ani_Frame <= 111){
//				revision = -12;
//			}else{
//				revision = 0;
//			}
			
			if (img_idx > 37 && img_idx < 78) {

				// 0 - 38
				// 1 - 39
				// 2 - 40
				// 3 - 41
				// 4 - 42
				if (img_idx < 43)
					img_idx = img_idx - 38;
				else if (img_idx > 45)
					img_idx = img_idx - 40;

				// 90 - 43
				// 5 - 44
				// 90 - 45
				else if (img_idx == 43)
					img_idx = 1;         // 인덱스 90 을 1로 대체(2012.07.20)
				else if (img_idx == 44)
					img_idx = 5;
				else if (img_idx == 45)
					img_idx = 1;         // 인덱스 90 을 1로 대체(2012.07.20)
				
				DrawImg(g, img_pororo[img_idx], p_x + p_info[Ani_Frame][1],
						p_y + p_info[Ani_Frame][2] - 12,
						SceneManager.BC);

				// 6 (뽀로로 머리에서 거꾸로 표시를 얼마나 위로 올려야 하는가)
				// 7 (오리탄뽀로로에 비해 그냥 뽀로로를 얼마나 밀어야 하는가
				// 150(그냥뽀로로 높이)  160 (오리탄뽀로로 높이)
				// 거꾸로 이동 표시 (오리 대신)
//				DrawImg(g, img_reverse[reverseTic % 2], p_x + p_info[Ani_Frame][1], 
//						p_y + p_info[Ani_Frame][2] - 12 - 150 - 6 ,
//						SceneManager.BC);
				g.drawImage(img_reverse[reverseTic % 2], p_x + p_info[Ani_Frame][1] - (100/2), 
						p_y + p_info[Ani_Frame][2] - 12 - 150 - 6, null);
				if (++reverseTic > 99) 
					reverseTic = 0;
			}
			else
			{
				if (img_idx == 90) {     // 인덱스 90 을 1로 대체(2012.07.20)
					img_idx = 1;
				}
				else if (img_idx == 84) {
					img_idx = 82;
				}
				else if (img_idx == 85) {
					img_idx = 83;
				}
					
				DrawImg(g, img_pororo[img_idx], p_x + p_info[Ani_Frame][1],
						p_y + p_info[Ani_Frame][2],
						SceneManager.BC);
			}
			
			
			if (p_info[Ani_Frame][5] == 1) {
				g.setColor(new Color(125, 78, 159));
				g.fillRect(p_x + p_info[Ani_Frame][6] - 12, p_y
						+ p_info[Ani_Frame][7] - 42, 24, 28);
				g.setColor(new Color(255, 217, 32));
				g.fillRect(p_x + p_info[Ani_Frame][6] - 12, p_y
						+ p_info[Ani_Frame][7] - 42 + ((14 - p_time) * 2), 24,
						28 - ((14 - p_time) * 2));
			}
			if (p_info[Ani_Frame][5] == 0 || p_info[Ani_Frame][5] == 1) {
				DrawImg(g, img_Item[p_info[Ani_Frame][5]], p_x
						+ p_info[Ani_Frame][6], p_y + p_info[Ani_Frame][7],
						SceneManager.BC);
			}
		}
		if (p_frame < nEnd) {
			if (!g_pause) {
				p_frame++;
			}
			if (p_action == DIE && p_frame == 1)
				m_sound.playSound(S_END);
		}

		else {
			switch (p_action) {
			case WAIT:
				p_ChangeAction(WAIT);
				break;
			case LEFT:
				p_ChangeAction(WAIT);
				break;
			case RIGHT:
				p_ChangeAction(WAIT);
				break;
			case COLLISION:
				if (p_state == ICE || p_state == ICEREVERSE) {
					p_ChangeState(BASICS);
				} else
					p_ChangeAction(WAIT);
				break;
			case NICE:
				p_ChangeAction(WAIT);
				break;
			case START:
				p_ChangeAction(RUN);
				break;
			case RUN:
				p_frame = 0;
				break;
			case END:
				m_sound.playSound(S_PORORO);
				p_ChangeState(BASICS);
				break;
			case DIE:
				p_ChangeAction(DIE1);
				break;
			case DIE1:
				// p_ChangeAction(DIE1);
				gameoverinit();
				break;
			}
		}
		// p_position = p_x;
	}

	public void p_ChangeAction(byte St) {
		p_position = p_x;

		p_frame = 0;
		p_action = St;

		if (p_action == RUN) {
			item_state = true;
			// space_state = true;
			p_time = 4;
			p_btime = GTime / 10;
		}
		// System.out.println("ChangeAction p_state="+p_state);
	}

	public void p_ChangeState(byte St) {

		p_position = p_x;

		p_frame = 0;
		p_state = St;
		if (p_state == BASICS) {
			p_action = WAIT;
		} else if (p_state == SHIELD) {
			if (item_state) {
				p_time = 0;
				p_btime = 0;
				item_state = false;
			}
			p_action = WAIT;
		} else if (p_state == SUPER) {
			p_time = 14;
			p_btime = GTime / 10;
			item_state = true;
			p_action = WAIT;
		} else if (p_state == ICE) {
			p_time = 7;
			p_btime = GTime / 10;
			item_state = true;
			p_action = WAIT;

		} else if (p_state == REVERSE) {
			p_time = 10;
			p_btime = GTime / 10;
			item_state = true;
			p_action = WAIT;

		} else if (p_state == ICEREVERSE) {
			p_time = 10;
			p_btime = GTime / 10;
			item_state = true;
			p_action = WAIT;

		} else if (p_state == SPACE) {
			if (item_state) {
				p_time = 0;
				p_btime = 0;
				item_state = false;
			}
			space_state = true;
			p_action = START;

		}
		// System.out.println("p_ChangeState="+p_state);

	}

	public void p_ChangeState1(byte St) {
		p_position = p_x;
		p_state = St;
		// System.out.println("p_ChangeState1="+p_state);
	}

	public void draw_Obj(Graphics g) {

		for (int i = 0; i < objNum; i++) {
			if (!g_pause) {
				if (!space_state)
					objects[i].o_y -= O_Speed[PLevel];
				else
					objects[i].o_y -= 25;
			}
			if (objects[i].o_State != O_NONE && objects[i].o_Kind != ITEM_NONE) {
				DrawImg(g, Img_Obj[objects[i].o_Kind], objects[i].o_x,
						objects[i].o_y, SceneManager.BC);
				if (!g_pause && i != 0) {
					checkCollision(i);
				}
			}

			if (objects[i].o_y < -10) {
				objects[i].o_State = O_NONE;
			}

		}
		range_obj();
		// if(obj_count == 19){
		if (objects[0].o_y < 410) {
			make_obj();
			objects[0].o_y = 630;
		}

	}

	public void checkCollision(int obj) {
		if (p_action < 5) {
			if (GLife > 0) {
				if (isRectCollided(p_x - p_info[Ani_Frame][3], p_y - 60, p_x
						+ p_info[Ani_Frame][3], p_y - 20,
						objects[obj].o_x - 22, objects[obj].o_y - 60,
						objects[obj].o_x + 22, objects[obj].o_y - 20)) {
					collision_Event(obj, p_state, objects[obj].o_Kind);

				}
			}
		}
	}

	public void make_obj() {
		int i;
		int rand_kind;
		int num = 0;
		int postion = 0;
		int obj_pos[] = { 0, 0 };
		obj_kind = randNum(0, 100) % 6;
		postion = randNum(0, 100) % 2;
		for (i = 0; i < 3; i++) {
			if (obj_type[obj_kind][i] == 1) {
				obj_pos[num] = i;
				num++;
			}
		}

		for (i = 0; i < 3; i++) {
			if (obj_type[obj_kind][i] == 1) {
				// System.out.println("i="+i);
				if (GTime < 200) {
					objects[objNum].o_Kind = randNum(0, 1);
				} else {
					if (num == 1) {
						rand_kind = randNum(0, 100);
						if (rand_kind > 30 + (PLevel) * 5)
							objects[objNum].o_Kind = randNum(0, 100) % 3;
						else
							objects[objNum].o_Kind = 7;
					} else {
						if (i == obj_pos[postion]) {
							objects[objNum].o_Kind = 7;
						} else {
							objects[objNum].o_Kind = randNum(0, 100) % 11;

							if (PLevel > 5) {
								if (objects[objNum].o_Kind == 3) {
									rand_kind = randNum(0, 100);
									if (p_state == SHIELD) {
										if (rand_kind > 50)
											objects[objNum].o_Kind = 1;
										else
											objects[objNum].o_Kind = 7;

									} else {
										if (rand_kind < 30 + (PLevel - 5) * 5)
											objects[objNum].o_Kind = 1;
										else if (rand_kind >= 30 + (PLevel - 5) * 5
												&& rand_kind < 60 + (PLevel - 5) * 10)
											objects[objNum].o_Kind = 7;
									}

								} else if (objects[objNum].o_Kind == 4) {
									rand_kind = randNum(0, 100);
									if (p_state == SUPER) {
										if (rand_kind > 50)
											objects[objNum].o_Kind = 1;
										else
											objects[objNum].o_Kind = 7;

									} else {
										if (rand_kind < 30 + (PLevel - 5) * 5)
											objects[objNum].o_Kind = 1;
										else if (rand_kind >= 30 + (PLevel - 5) * 5
												&& rand_kind < 60 + (PLevel - 5) * 10)
											objects[objNum].o_Kind = 7;
									}
								} else if (objects[objNum].o_Kind == 5) {
									rand_kind = randNum(0, 100);
									if (PLevel == 8) {
										if (rand_kind > 50)
											objects[objNum].o_Kind = 1;
										else
											objects[objNum].o_Kind = 7;
									} else {
										if (rand_kind < 30 + (PLevel - 5) * 5)
											objects[objNum].o_Kind = 1;
										else if (rand_kind >= 30 + (PLevel - 5) * 5
												&& rand_kind < 60 + (PLevel - 5) * 10)
											objects[objNum].o_Kind = 7;
									}
								} else if (objects[objNum].o_Kind == 6) {
									rand_kind = randNum(0, 100);
									if (p_state == SPACE) {
										if (rand_kind > 50)
											objects[objNum].o_Kind = 1;
										else
											objects[objNum].o_Kind = 7;

									} else {
										if (rand_kind < 30 + (PLevel - 5) * 5)
											objects[objNum].o_Kind = 1;
										else if (rand_kind >= 30 + (PLevel - 5) * 5
												&& rand_kind < 60 + (PLevel - 5) * 10)
											objects[objNum].o_Kind = 7;
									}
								}

							}

						}
					}
				}
				objects[objNum].o_State = O_MOVE; // 현재 상태
				objects[objNum].o_y = 630;
				if (i == 0)
					objects[objNum].o_x = 360;
				else if (i == 1)
					objects[objNum].o_x = 480;
				else if (i == 2)
					objects[objNum].o_x = 600;

				objNum++;
				// num=0;
				// System.out.println("objNum="+objNum);
			}
		}
	}

	public void range_obj() {

		for (int i = 1; i < objNum - 1; i++) {
			if (objects[i].o_State == O_NONE) {
				for (int j = 2; j < objNum; j++) {
					if (objects[j].o_State != O_NONE) {
						objects[i].o_Kind = objects[j].o_Kind;
						objects[i].o_State = objects[j].o_State;
						objects[i].o_x = objects[j].o_x;
						objects[i].o_y = objects[j].o_y;
					}
				}
				objNum--;
			}
		}

	}

	public void collision_Event(int obj, byte cstate, int item) {
		switch (item) {
		case ITEM_STAR:
			e_attribute[attributenum][0] = 0;
			e_attribute[attributenum][1] = p_x;
			e_attribute[attributenum][2] = p_y;
			e_attribute[attributenum][3] = 0;
			e_attribute[attributenum][4] = 0;
			objects[obj].o_State = O_NONE;
			attributenum++;
			m_sound.playSound(S_GOOD0);
			break;
		case ITEM_GOLD:
			e_attribute[attributenum][0] = 1;
			e_attribute[attributenum][1] = p_x;
			e_attribute[attributenum][2] = p_y;
			e_attribute[attributenum][3] = 0;
			e_attribute[attributenum][4] = 0;
			objects[obj].o_State = O_NONE;
			attributenum++;
			m_sound.playSound(S_GOOD1);
			break;
		case ITME_BOMB:
			for (int i = 1; i < objNum; i++) {
				if (objects[i].o_y > 25 && objects[i].o_y < 625) {
					e_attribute[attributenum][0] = 2;
					e_attribute[attributenum][1] = objects[i].o_x;
					e_attribute[attributenum][2] = objects[i].o_y;
					e_attribute[attributenum][3] = 0;
					e_attribute[attributenum][4] = 0;
					objects[i].o_State = O_NONE;
					attributenum++;
				}
			}
			m_sound.playSound(S_GOOD1);
			break;
		case ITME_SHIELD:
			if (cstate != SHIELD) {
				e_attribute[attributenum][0] = 3;
				e_attribute[attributenum][1] = p_x;
				e_attribute[attributenum][2] = p_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = item;
				attributenum++;
			} else {
				m_sound.playSound(S_GOOD1);
			}
			objects[obj].o_State = O_NONE;
			break;
		case ITME_SUPER:
			if (cstate != SUPER) {
				e_attribute[attributenum][0] = 3;
				e_attribute[attributenum][1] = p_x;
				e_attribute[attributenum][2] = p_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = item;
				objects[obj].o_State = O_NONE;
				attributenum++;
			} else {
				p_time = 14;
				p_btime = GTime / 10;
				objects[obj].o_State = O_NONE;
				m_sound.playSound(S_GOOD1);
			}
			break;
		case ITME_LIFE:
			e_attribute[attributenum][0] = 1;
			e_attribute[attributenum][1] = p_x;
			e_attribute[attributenum][2] = p_y;
			e_attribute[attributenum][3] = 0;
			e_attribute[attributenum][4] = item;
			objects[obj].o_State = O_NONE;
			attributenum++;
			m_sound.playSound(S_GOOD1);
			break;
		case ITME_SPACE:
			if (cstate != SPACE) {
				e_attribute[attributenum][0] = 3;
				e_attribute[attributenum][1] = p_x;
				e_attribute[attributenum][2] = p_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = item;
				objects[obj].o_State = O_NONE;
				attributenum++;
			}
			break;
		case ITME_ICEBERG:
			if (cstate == SHIELD || cstate == SUPER) {
				e_attribute[attributenum][0] = 4;
				e_attribute[attributenum][1] = objects[obj].o_x;
				e_attribute[attributenum][2] = objects[obj].o_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = cstate;
				objects[obj].o_State = O_NONE;
				attributenum++;
			} else {
				e_attribute[attributenum][0] = 5;
				e_attribute[attributenum][1] = objects[obj].o_x;
				e_attribute[attributenum][2] = objects[obj].o_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = 0;
				objects[obj].o_State = O_NONE;
				attributenum++;
			}
			break;
		case ITME_REVERSE:
			if (cstate == REVERSE || cstate == ICEREVERSE) {
				objects[obj].o_State = O_NONE;
				m_sound.playSound(S_BAD1);

			} else {
				e_attribute[attributenum][0] = 3;
				e_attribute[attributenum][1] = p_x;
				e_attribute[attributenum][2] = p_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = item;
				attributenum++;
			}

			break;
		case ITME_ICE:
			if (cstate == SUPER) {
				e_attribute[attributenum][0] = 4;
				e_attribute[attributenum][1] = objects[obj].o_x;
				e_attribute[attributenum][2] = objects[obj].o_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = cstate;
				objects[obj].o_State = O_NONE;
				attributenum++;

			} else if (cstate == ICE || cstate == ICEREVERSE) {
				objects[obj].o_State = O_NONE;
				m_sound.playSound(S_BAD1);
			} else {
				e_attribute[attributenum][0] = 3;
				e_attribute[attributenum][1] = p_x;
				e_attribute[attributenum][2] = p_y;
				e_attribute[attributenum][3] = 0;
				e_attribute[attributenum][4] = item;
				objects[obj].o_State = O_NONE;
				attributenum++;
			}
			break;
		case ITEM_CLOUD:
			if (!cloud_state) {
				// 사운드
				objects[obj].o_State = O_NONE;
				m_sound.playSound(S_CLOUD);
				cloud_btime = GTime / 10;
				cloud_time = 7;
				cloud_state = true;
			} else {
				objects[obj].o_State = O_NONE;
				m_sound.playSound(S_BAD1);
			}
			break;

		}
	}

	public void draw_event(Graphics g) {
		int rand_valuse;
		for (int i = 0; i < attributenum; i++) {

			if (!g_pause)
				e_attribute[i][3]++;
			switch (e_attribute[i][0]) {
			case 0:
				if (e_attribute[i][3] == 1) {

					GScore += 1000;
					if (p_state != SPACE) {
						p_ChangeAction(NICE);
					}

					DrawImg(g, img_effect[16], e_attribute[i][1],
							e_attribute[i][2] - 112, SceneManager.BC);
				} else if (e_attribute[i][3] == 2) {
					DrawImg(g, img_effect[16], e_attribute[i][1],
							e_attribute[i][2] - 112, SceneManager.BC);
				} else if (e_attribute[i][3] == 3) {
					DrawImg(g, img_effect[16], e_attribute[i][1],
							e_attribute[i][2] - 122, SceneManager.BC);
				} else if (e_attribute[i][3] == 4 || e_attribute[i][3] == 5) {
					DrawImg(g, img_effect[16], e_attribute[i][1],
							e_attribute[i][2] - 132, SceneManager.BC);
				} else if (e_attribute[i][3] == 6) {
					DrawImg(g, img_effect[16], e_attribute[i][1],
							e_attribute[i][2] - 142, SceneManager.BC);
					e_attribute[i][0] = ITEM_NONE;
				}
				break;
			case 1:
				if (e_attribute[i][3] == 1) {

					GScore += 500;
					if (e_attribute[i][4] == ITME_LIFE) {
						if (GLife < 3)
							GLife++;
					}
					p_ChangeAction(NICE);
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 112, SceneManager.BC);
				} else if (e_attribute[i][3] == 2) {
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 112, SceneManager.BC);
				} else if (e_attribute[i][3] == 3) {
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 122, SceneManager.BC);
				} else if (e_attribute[i][3] == 4 || e_attribute[i][3] == 5) {
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 132, SceneManager.BC);
				} else if (e_attribute[i][3] == 6) {
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 142, SceneManager.BC);
					e_attribute[i][0] = ITEM_NONE;
				}
				break;
			case 2:
				if (e_attribute[i][3] == 1) {

					GScore += 500;
					p_ChangeAction(NICE);
					DrawImg(g, img_effect[10], e_attribute[i][1],
							e_attribute[i][2], SceneManager.BC);
				} else if (e_attribute[i][3] == 2) {
					DrawImg(g, img_effect[11], e_attribute[i][1],
							e_attribute[i][2], SceneManager.BC);
				} else if (e_attribute[i][3] == 3) {
					DrawImg(g, img_effect[12], e_attribute[i][1],
							e_attribute[i][2], SceneManager.BC);
				} else if (e_attribute[i][3] == 4) {
					DrawImg(g, img_effect[13], e_attribute[i][1],
							e_attribute[i][2], SceneManager.BC);
				} else if (e_attribute[i][3] == 5) {
					DrawImg(g, img_effect[14], e_attribute[i][1],
							e_attribute[i][2], SceneManager.BC);
				} else if (e_attribute[i][3] == 6) {
					DrawImg(g, img_effect[15], e_attribute[i][1],
							e_attribute[i][2], SceneManager.BC);
					e_attribute[i][0] = ITEM_NONE;
				}
				break;
			case 3:
				if (e_attribute[i][3] == 1) {
					if (e_attribute[i][4] == ITME_SHIELD) {
						m_sound.playSound(S_GOOD1);
						p_ChangeState(SHIELD);
					} else if (e_attribute[i][4] == ITME_SUPER) {
						m_sound.playSound(S_GOOD1);
						p_ChangeState(SUPER);
					} else if (e_attribute[i][4] == ITME_SPACE) {
						m_sound.playSound(S_GOOD1);
						p_ChangeState(SPACE);
					} else if (e_attribute[i][4] == ITME_REVERSE) {
						m_sound.playSound(S_BAD1);
						if (p_state == ICE)
							p_ChangeState(ICEREVERSE);
						else
							p_ChangeState(REVERSE);
					} else if (e_attribute[i][4] == ITME_ICE) {
						m_sound.playSound(S_ICE);
						if (p_state == REVERSE)
							p_ChangeState(ICEREVERSE);
						else
							p_ChangeState(ICE);
					}
					DrawImg(g, img_effect[0], e_attribute[i][1],
							e_attribute[i][2] - 25, SceneManager.CC);
				} else if (e_attribute[i][3] == 2) {
					DrawImg(g, img_effect[0], e_attribute[i][1],
							e_attribute[i][2] - 25, SceneManager.CC);
				} else if (e_attribute[i][3] == 3 || e_attribute[i][3] == 4) {
					DrawImg(g, img_effect[1], e_attribute[i][1],
							e_attribute[i][2] - 25, SceneManager.CC);
				} else if (e_attribute[i][3] == 5 || e_attribute[i][3] == 6) {
					DrawImg(g, img_effect[2], e_attribute[i][1],
							e_attribute[i][2] - 25, SceneManager.CC);

				} else if (e_attribute[i][3] == 7) {
					DrawImg(g, img_effect[3], e_attribute[i][1],
							e_attribute[i][2] - 25, SceneManager.CC);
				} else if (e_attribute[i][3] == 8) {
					DrawImg(g, img_effect[3], e_attribute[i][1],
							e_attribute[i][2] - 25, SceneManager.CC);
					e_attribute[i][0] = ITEM_NONE;
				}
				break;
			case 4:
				if (e_attribute[i][3] == 1) {
					m_sound.playSound(S_GOOD1);
					if (e_attribute[i][4] == SHIELD) {
						GScore += 500;
						p_ChangeState(WAIT);
					} else {
						GScore += 500;
						p_ChangeAction(NICE);
					}
					DrawImg(g, img_effect[4], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 112, SceneManager.BC);
				} else if (e_attribute[i][3] == 2) {
					DrawImg(g, img_effect[4], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 112, SceneManager.BC);
				} else if (e_attribute[i][3] == 3 || e_attribute[i][3] == 4) {
					DrawImg(g, img_effect[5], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 122, SceneManager.BC);
				} else if (e_attribute[i][3] == 5 || e_attribute[i][3] == 6) {
					DrawImg(g, img_effect[6], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 132, SceneManager.BC);
				} else if (e_attribute[i][3] == 7 || e_attribute[i][3] == 8) {
					DrawImg(g, img_effect[7], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
					DrawImg(g, img_effect[17], e_attribute[i][1],
							e_attribute[i][2] - 142, SceneManager.BC);
				} else if (e_attribute[i][3] == 9) {
					DrawImg(g, img_effect[8], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
				} else if (e_attribute[i][3] == 10) {
					DrawImg(g, img_effect[9], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
					e_attribute[i][0] = ITEM_NONE;
				}
				break;
			case 5:
				if (e_attribute[i][3] == 1) {
					rand_valuse = randNum(0, 100) % 2;
					if (rand_valuse == 0) {
						m_sound.playSound(S_BAD0);
					} else
						m_sound.playSound(S_BAD1);
					GLife--;
					if (GLife <= 0)
						p_ChangeAction(DIE);
					else
						p_ChangeAction(COLLISION);
					DrawImg(g, img_effect[4], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
				} else if (e_attribute[i][3] == 2) {
					DrawImg(g, img_effect[4], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
				} else if (e_attribute[i][3] == 3 || e_attribute[i][3] == 4) {
					DrawImg(g, img_effect[5], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
				} else if (e_attribute[i][3] == 5 || e_attribute[i][3] == 6) {
					DrawImg(g, img_effect[6], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
				} else if (e_attribute[i][3] == 7 || e_attribute[i][3] == 8) {
					DrawImg(g, img_effect[7], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
				} else if (e_attribute[i][3] == 9) {
					DrawImg(g, img_effect[8], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
				} else if (e_attribute[i][3] == 10) {
					DrawImg(g, img_effect[9], e_attribute[i][1],
							e_attribute[i][2] - 40, SceneManager.CC);
					e_attribute[i][0] = ITEM_NONE;
					// range_event(i);
				}
				break;
			}
		}
		range_event();
	}

	public void range_event() {
		for (int i = 0; i < attributenum - 1; i++) {
			if (e_attribute[i][0] == ITEM_NONE) {
				for (int j = 1; j < attributenum; j++) {
					if (e_attribute[j][0] != ITEM_NONE) {
						e_attribute[i][0] = e_attribute[j][0];
						e_attribute[i][1] = e_attribute[j][1];
						e_attribute[i][2] = e_attribute[j][2];
						e_attribute[i][3] = e_attribute[j][3];
						e_attribute[i][4] = e_attribute[j][4];
					}
				}
				attributenum--;
			}
		}

	}

	public void draw_cloud(Graphics g) {
		if (cloud_state) {
			for (int i = 0; i < 4; i++) {
				if (i != 3)
					DrawImg(g, img_Item[2], Center_X - 140 + (i * 140), 540,
							SceneManager.BC);
				DrawImg(g, img_Item[2], Center_X - 190 + (i * 124), 540 - 70,
						SceneManager.BC);
			}
		}
	}

	// init load

	public void inti_game() {
		GScore = 0;
		GLife = 3;
		PLevel = 0;
		g_endCur = 0;
		g_pause = false;
		cloud_state = false;
		cloud_time = 0;
		cloud_btime = 0;
		space_state = false;
		sound_kind = false;
		SaveTime = 0;
	}

	public void init_pororo() {
		p_state = BASICS;
		p_action = WAIT;
		p_time = 0;
		p_btime = 0;
		item_state = false;
		p_frame = 0;
		Ani_Frame = 0;
		p_x = Center_X;
		p_y = 210;
		p_position = Center_X;
		move_direction = 0;
		;

	}

	public void dataload_pororo() {
		p_info = new int[151][8];
		// System.out.println("dataload pororo");
		p_info = LoadData("char", 151, 8);
	}

	public void init_obj() {
		objects = new Objects[20];
		objNum = 0;
		obj_kind = 0;
		for (int i = 0; i < 20; i++) {
			objects[i] = new Objects();
			Setobj(i);
		}
		objects[0].o_Kind = ITEM_NONE; // 아이템 속성
		objects[0].o_State = O_MOVE; // 현재 상태
		objects[0].o_x = -100; // x좌표
		objects[0].o_y = 630; // y좌표
		objNum++;
		obj_kind = randNum(0, 5);
		for (int i = 0; i < 3; i++) {
			if (obj_type[obj_kind][i] == 1) {
				objects[objNum].o_Kind = ITEM_STAR;
				objects[objNum].o_State = O_MOVE; // 현재 상태
				objects[objNum].o_y = 630;
				if (i == 0)
					objects[objNum].o_x = 360;
				else if (i == 1)
					objects[objNum].o_x = 480;
				else if (i == 2)
					objects[objNum].o_x = 600;
				objNum++;
			}
		}
	}

	public void Setobj(int i) {
		objects[i].o_Kind = ITEM_NONE; // 아이템 속성
		objects[i].o_State = O_NONE; // 현재 상태
		objects[i].o_x = 0; // x좌표
		objects[i].o_y = 0; // y좌표

	}

	public void effectinit() {
		attributenum = 0;
		for (int i = 0; i < 20; i++) {
			e_attribute[i][0] = ITEM_NONE; // 종류
			e_attribute[i][1] = 0; // X
			e_attribute[i][2] = 0; // Y
			e_attribute[i][3] = 0; // frame
			e_attribute[i][4] = 0; // frame
		}
	}

	public void gameoverinit() {
		NowTime = GFrame;
		GTime = 0;
		sound_kind = false;
		SceneManager.getInstance().sendScore_gravity(GScore);
		SceneManager.getInstance().send_gravity(11);
		GState = STATE_OVER;
	}

	// Image LOAD
	public void Imgload_loadingbar() {
		
		img_load = new Image[2];
		img_load[0] = loadImage("img/load1.png");
		img_load[1] = loadImage("img/load2.png");
	}

	public void Imgload_txt() {
		int i;
		img_txt = new Image[6];

		try {

			for (i = 0; i < 6; i++) {
				// System.out.println("img load txt="+i);
				img_txt[i] = loadUrlImage(new URL(imgurl + "txt_" + i + ".png"));
			}

		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("txt  imgae not  load---------------------------------------------");
		}
	}

	public void Imgload_pororo0() {
		int i;
		img_pororo = new Image[91];

		try {
			for (i = 0; i < 10; i++) {
				// System.out.println("img load pororo0="+i);
				img_pororo[i] = loadUrlImage(new URL(imgurl + "char/char_0" + i
						+ ".png"));
			}

		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}

	public void Imgload_pororo1() {

		try {
			for (int i = 10; i < 25; i++) {
				// System.out.println("img load pororo1="+i);
				img_pororo[i] = loadUrlImage(new URL(imgurl + "char/char_" + i
						+ ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}

	public void Imgload_pororo2() {
		try {
			for (int i = 25; i < 38; i++) {   // 원래40
				// System.out.println("img load pororo2="+i);
				img_pororo[i] = loadUrlImage(new URL(imgurl + "char/char_" + i
						+ ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}
	
	public void Imgload_item() {
		
		// 거꾸로 이동 표시 (오리 대신)
		img_reverse = new Image[2];
		try {
			img_reverse[0] = loadUrlImage(new URL(imgurl + "item_3a.png"));
			img_reverse[1] = loadUrlImage(new URL(imgurl + "item_3b.png"));
		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}

	// 로딩안함
	public void Imgload_pororo3() {
		try {
			for (int i = 40; i < 55; i++) {
				// System.out.println("img load pororo3="+i);
				img_pororo[i] = loadUrlImage(new URL(imgurl + "char/char_" + i
						+ ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}

	// 로딩안함
	public void Imgload_pororo4() {
		try {
			for (int i = 55; i < 70; i++) {
				// System.out.println("img load pororo4="+i);
				img_pororo[i] = loadUrlImage(new URL(imgurl + "char/char_" + i
						+ ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}

	public void Imgload_pororo5() {
		try {
			for (int i = 78; i < 84; i++) {   // 원래 70    그리고.. 인덱스84,85 를 82,83 으로 대체함 (2012.05.21)
				// System.out.println("img load pororo5="+i);
				img_pororo[i] = loadUrlImage(new URL(imgurl + "char/char_" + i
						+ ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}

	public void Imgload_pororo6() {
		try {
			for (int i = 0; i < 4; i++) {
				// System.out.println("img load pororo6="+i);
				img_pororo[86 + i] = loadUrlImage(new URL(imgurl
						+ "effect/effect0" + i + ".png"));
			}
//			img_pororo[90] = loadUrlImage(new URL(imgurl + "char/char_04_.png"));  // char_01 로 대체함 (2012.05.21)
		} catch (Exception e) {
			img_check = true;
			e.printStackTrace();
			// System.out.println("char imgae not  load---------------------------------------------");
		}
	}

	public void Imgload_obj() {
		Img_Obj = new Image[11];

		try {
			for (int i = 0; i < 11; i++) {
				// System.out.println("img  load obj="+i);
				if (i < 10)
					Img_Obj[i] = loadUrlImage(new URL(imgurl + "obj/obj_0" + i
							+ ".png"));
				else
					Img_Obj[i] = loadUrlImage(new URL(imgurl + "obj/obj_" + i
							+ ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			// System.out.println("not load obj");
			e.printStackTrace();
		}
	}

	public void Imgload_effect0() {
		img_effect = new Image[18];

		try {
			for (int i = 0; i < 6; i++) {
				// System.out.println("img load effect0="+i);
				img_effect[i] = loadUrlImage(new URL(imgurl + "effect/effect0"
						+ (i + 4) + ".png"));
				// ImgObj[i] = loadImage("img/board/img/obj/obj_0"+i+".png");
			}
		} catch (Exception e) {
			img_check = true;
			// System.out.println("not img load effect");
			e.printStackTrace();
		}
	}

	public void Imgload_effect1() {
		try {
			for (int i = 6; i < 18; i++) {
				// System.out.println("img load effect1="+i);
				img_effect[i] = loadUrlImage(new URL(imgurl + "effect/effect"
						+ (i + 4) + ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			// System.out.println("not img load effect");
			e.printStackTrace();
		}
	}

	public void Imgload_num() {
		img_num = new Image[10];

		try {
			for (int i = 0; i < 10; i++) {
				// System.out.println(" img load num="+i);
				img_num[i] = loadUrlImage(new URL(imgurl + "n" + i + ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			// System.out.println(" not img load num");
			e.printStackTrace();
		}
	}

	public void Imgload_endpopup0() {
		img_endnum = new Image[10];

		try {
			for (int i = 0; i < 10; i++) {
				// System.out.println(" img load endpopup0="+i);
				img_endnum[i] = loadUrlImage(new URL(imgurl1
						+ "pop_result/end_" + i + ".png"));
			}
		} catch (Exception e) {
			img_check = true;
			// System.out.println(" not img load endpopup0");
			e.printStackTrace();
		}
	}

	public void Imgload_endpopup1() {
		img_endbtnY = new Image[2];
		img_endbtnN = new Image[2];
		img_endrank = new Image[4];
		// System.out.println(" img load endpopup1");
		try {
			img_endbtnY[0] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_bt_fin01.png"));
			img_endbtnY[1] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_bt_fin02.png"));
			img_endbtnN[0] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_bt_re01.png"));
			img_endbtnN[1] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_bt_re02.png"));
			img_endrank[0] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_rank_d.png"));
			img_endrank[1] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_rank_c.png"));
			img_endrank[2] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_rank_b.png"));
			img_endrank[3] = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_rank_a.png"));
			img_endbox = loadUrlImage(new URL(imgurl1
					+ "pop_result/end_popup_3.png"));
		} catch (Exception e) {
			img_check = true;
			// System.out.println(" not img load endpopup1");
			e.printStackTrace();
		}
	}

	public void Imgload_res() {
		img_Item = new Image[3];
		img_btnYes = new Image[2];
		img_btnNo = new Image[2];
		// System.out.println(" img load game");
		try {
			img_life = loadUrlImage(new URL(imgurl + "life.png"));
			for (int i = 0; i < 3; i++) {
				img_Item[i] = loadUrlImage(new URL(imgurl + "item" + i + ".png"));
			}
			img_btnYes[0] = loadUrlImage(new URL(imgurl1
					+ "pop_end/btn_yes_on.png"));
			img_btnYes[1] = loadUrlImage(new URL(imgurl1
					+ "pop_end/btn_yes_off.png"));
			img_btnNo[0] = loadUrlImage(new URL(imgurl1
					+ "pop_end/btn_no_on.png"));
			img_btnNo[1] = loadUrlImage(new URL(imgurl1
					+ "pop_end/btn_no_off.png"));
			img_endPop = loadUrlImage(new URL(imgurl1 + "pop_end/pop_end.png"));
		} catch (Exception e) {
			img_check = true;
			// System.out.println(" not img load game");
			e.printStackTrace();
		}
	}

	public void Sndload_res() {
		m_sound = new Sound(10);
		try {
			if (m_sound != null) {
				// System.out.println(" sound load game");

				m_sound.loadSound(new URL(imgurl1 + "board/snd/bad0.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/bad1.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/good0.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/good1.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/pororo.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/ice.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/cloud.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/gamestart.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/end.mp2"));
				m_sound.loadSound(new URL(imgurl1 + "board/snd/gameover.mp2"));
				for (int i = 0; i < m_sound.mn_sndId; i++) {
					if (m_sound.m_player[i] == null)
						img_check = true;
				}

				/*
				 * m_sound.loadSound("snd/board/bad0.mp2");
				 * m_sound.loadSound("snd/board/bad1.mp2");
				 * m_sound.loadSound("snd/board/good0.mp2");
				 * m_sound.loadSound("snd/board/good1.mp2");
				 * m_sound.loadSound("snd/board/pororo.mp2");
				 * m_sound.loadSound("snd/board/ice.mp2");
				 * m_sound.loadSound("snd/board/cloud.mp2");
				 * m_sound.loadSound("snd/board/gamestart.mp2");
				 * m_sound.loadSound("snd/board/end.mp2");
				 * m_sound.loadSound("snd/board/gameover.mp2");
				 */
			} else {
				img_check = true;
				// System.out.println("Sound is null");
			}
		} catch (Exception e) {
			img_check = true;
			// System.out.println(" not sound load game");
			e.printStackTrace();
		}
	}

	public Image loadImage(String s) {

		return SceneManager.getInstance().getImage(s);
	}

	public Image loadUrlImage(URL url) {
		Image img;
		img = SceneManager.getInstance().getImage(url);
		if (img != null) {
			if (img.getWidth(null) <= 0)
				img_check = true;
		} else
			img_check = true;
		return img;
	}

	// return SceneManager.getInstance().getImage(url);
	// }

	public void DrawImg(Graphics g, Image img, int img_x, int img_y, int Align) {
		SceneManager.getInstance().DrawImg(g, img, img_x, img_y, Align);
	}

	public void draw_bgalpha(Graphics g) {
		g.setColor(new DVBColor(0, 0, 0, 80));
//		g.fillRect(0, 0, 960, 540);   // 메모리
	}

	public static final boolean isRectCollided(int ax1, int ay1, int ax2,
			int ay2, int bx1, int by1, int bx2, int by2) {
		return (bx2 >= ax1 && ax2 >= bx1 && by2 >= ay1 && ay2 >= by1);
	}

	public void DrawImg(Graphics g, Image img, int img_x, int img_y, int x,
			int y, int x1, int y1) {
		g.drawImage(img, img_x, img_y, img_x + (x1 - x + 1), img_y
				+ (y1 - y + 1), x, y, x1, y1, null);
	}

	public void draw_num(Graphics g, int img_kind, int score, int x, int y,
			int sw) {
		String scoreStr = String.valueOf(score);
		int num = 0, cnum = 0, dnum = 1;

		num = cnum = scoreStr.length();
		int idwScore[] = new int[num];

		for (int i = 1; i < num; i++)
			dnum = dnum * 10;
		while (cnum > 0) {
			idwScore[num - cnum] = score / dnum;
			if (img_kind == 0)
				DrawImg(g, img_num[idwScore[num - cnum]],
						x - (sw * (cnum - 1)), y, SceneManager.TR);
			else
				DrawImg(g, img_endnum[idwScore[num - cnum]], x
						- (sw * (cnum - 1)), y, SceneManager.TR);
			score = score - dnum * idwScore[num - cnum];
			dnum = dnum / 10;
			cnum--;
		}

	}

	public int[][] LoadData(String str, int idx, int idy) {
		InputStream is = null;
		int[][] rta = new int[idx][idy];
		try {
			//is = getClass().getResourceAsStream("/img/board/" + str + ".dat");
			is = new FileInputStream("img/board/" + str + ".dat");

			DataInputStream dis = new DataInputStream(is);
			for (int i = 0; i < idx; i++) {
				for (int j = 0; j < idy; j++) {
					rta[i][j] = dis.readInt();

				}
			}
			is.close();
		} catch (Exception e) {
		}

		return rta;
	}

	public int randNum(int min, int max) {
		return SceneManager.getInstance().getRandomInt(min, max);
	}

}
