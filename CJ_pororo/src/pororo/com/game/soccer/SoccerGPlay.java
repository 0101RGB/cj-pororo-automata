package pororo.com.game.soccer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import org.dvb.ui.DVBColor;
import org.havi.ui.event.HRcEvent;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;
import pororo.com.framework.Sound;
import pororo.com.game.GameTitle;


public class SoccerGPlay extends Scene {
	private static final byte STATE_LODAING = 5;
	private static final byte STATE_READY = 10;
	private static final byte STATE_PLAY_EXAMPLEAPPEAR = 30;
	private static final byte STATE_PLAY_READY = 31;
	private static final byte STATE_PLAY_SOLVE = 32;
	private static final byte STATE_PLAY_NEXTQUESTION = 33;
	private static final byte STATE_PLAY_SELECT = 34;
	private static final byte STATE_PLAY_CORRECT = 35;
	private static final byte STATE_PLAY_INCORRECT = 36;
	private static final byte STATE_PLAY_MISS = 37;
	private static final byte STATE_GAMEEND_SAY = 40;
	private static final byte STATE_POPUP_RESULT = 50;
	private static final byte STATE_POPUP_END = 51;
	private static final byte STATE_ERROR= 60;

	private static final byte CHAR_ACT_WAIT = 0;
	private static final byte CHAR_ACT_SHOT = 1;
	private static final byte CHAR_ACT_ASSA = 2;
	private static final byte CHAR_ACT_EHUE = 3;
	private static final byte CHAR_ACT_TIMEOVER = 4;
	private static final byte CHAR_ACT_GAMEOVER = 5;
	
	private static final byte QUESTION_COLORMATCH = 0;
	private static final byte QUESTION_ASSOCIATE = 1;
	private static final byte QUESTION_COUNT = 2;
	private static final byte QUESTION_ADDCOUNT = 3;
	
	private static final byte EXAMPLESTATE_APPEAR = 0;
	private static final byte EXAMPLESTATE_ARRIVE = 1;
	private static final byte EXAMPLESTATE_SHAKE = 2;
	private static final byte EXAMPLESTATE_READY = 3;
	private static final byte EXAMPLESTATE_DISAPPEAR = 4;
	
	//private static final byte ARROWDIRECTION_STOP = 0;
	private static final byte ARROWDIRECTION_UP = 1;
	private static final byte ARROWDIRECTION_DOWN = 2;
	
	private static final byte SOUND_ID_ASSA = 0;
	private static final byte SOUND_ID_CORRECT = 1;
	private static final byte SOUND_ID_EHUE = 2;
	private static final byte SOUND_ID_GAMEEND = 3;
	private static final byte SOUND_ID_KICKBALL = 4;
	private static final byte SOUND_ID_MISS = 5;
	private static final byte SOUND_ID_READYGO = 6;
	private static final byte SOUND_ID_TIMEOVER = 7;
	
	private byte mb_charActState;
	private byte mb_aniId;
	private byte mb_aniStep;
	private byte mb_qType;
	private byte mb_qId;
	private byte mb_ansId;
	private byte mb_arrId;
	private byte mb_arrDir;
	private byte mb_userSltId;
	private byte mb_boomAniId;
	private byte mb_minus1AniId;
	private byte mb_btnAniId;
	private byte mb_rsltCur;
	private byte mb_rsltStep;
	private byte mb_rsltRank;
	private byte mb_endCur;
	private boolean mb_correct;
	private boolean mb_pause;
	private byte mb_effectStep;
	private byte mb_drawstep[];
	private byte mb_examState[];
	
	private int mn_aniTime1;
	private int mn_qCnt;
	private int mn_arrowTime;
	private int mn_slvTime;
	private int mn_OXposX;
	private int mn_OXposY;
	private int mn_OXaniStep;
	private int mn_aniTime2;
	private int mn_ballCnt;
	private int mn_Score;
	private int mn_LFrame;
	private int mn_arrowPos[][];
	private int mn_examPos[];
	
	private Sound m_sound;
	
	private boolean img_check=false;
	private Font myFont;

	//private Image img_gameBg;
	private Image img_txt_geim;
	private Image img_txt_jongryo;
	private Image img_txt_jun;
	private Image img_txt_bi;
	private Image img_txt_si;
	private Image img_txt_jak;
	private Image img_qClrMatchTxt;
	private Image img_qAssctTxt;
	private Image img_qCntTxt;
	private Image img_time;
	private Image img_OX_O;
	private Image img_OX_X;
	private Image img_minus1;
	private Image img_Ball;
	private Image img_rsltPop;
	private Image img_endPop;

	private Image img_Arrow[];
	private Image img_Boom[];
	private Image img_chr_a[];
	private Image img_chr_b[];
	private Image img_chr_c[];
	private Image img_chr_d[];
	private Image img_chr_e[];
	private Image img_no_l[];
	private Image img_no_s[];
	private Image img_qClrMatch[];
	private Image img_qAssct[];
	private Image img_qCnt[];
	private Image img_qAddCnt[];
	private Image img_btn_ok[];
	private Image img_no_end[];
	private Image img_btnFin[];
	private Image img_btnRe[];
	private Image img_rank[];
	private Image img_btnYes[];
	private Image img_btnNo[];
	private Image img_load[];
	private Image img_Effect[];
	
	private Image img_Exam1;
	private Image img_Exam2;
	private Image img_Exam3;
	private Image img_Exam4;

	private Ball ball;
	
	public class myVector {
		int varX, varY;
		
		public myVector() {
			varX = varY = 0;
		}
		
		public void setVector(int varx, int vary) {
			varX = varx;
			varY = vary;
		}
		
		public void calcVarXY(int scalar, int angle) {
			varX = (int) (scalar*Math.cos(Math.toRadians(angle)));
			varY = (int) (scalar*Math.sin(Math.toRadians(angle)));
			
			//varX = (int) (scalar*Math.cos(Math.toRadians(0)));
			//varY = (int) (scalar*Math.sin(Math.toRadians(0)));
			
			/*
			System.out.println("calcVarXY angle : " + angle);
			System.out.println("calcVarXY scalar : " + scalar);
			System.out.println("calcVarXY varX : " + varX);
			System.out.println("calcVarXY varY : " + varY);
			*/
			
			/*
			varX = (int) (scalar*Math.cos(Math.toRadians(90)));
			varY = (int) (scalar*Math.sin(Math.toRadians(90)));
			
			System.out.println("calcVarXY angle : " + 90);
			System.out.println("calcVarXY scalar : " + scalar);
			System.out.println("calcVarXY varX : " + varX);
			System.out.println("calcVarXY varY : " + varY);
			
			varX = (int) (scalar*Math.cos(Math.toRadians(180)));
			varY = (int) (scalar*Math.sin(Math.toRadians(180)));
			
			System.out.println("calcVarXY angle : " + 180);
			System.out.println("calcVarXY scalar : " + scalar);
			System.out.println("calcVarXY varX : " + varX);
			System.out.println("calcVarXY varY : " + varY);
			
			varX = (int) (scalar*Math.cos(Math.toRadians(270)));
			varY = (int) (scalar*Math.sin(Math.toRadians(270)));
			
			System.out.println("calcVarXY angle : " + 270);
			System.out.println("calcVarXY scalar : " + scalar);
			System.out.println("calcVarXY varX : " + varX);
			System.out.println("calcVarXY varY : " + varY);
			*/
		}
	}
	
	public class Ball {
		public static final byte ACT_READY = 0;
		public static final byte ACT_SHOT = 1;
		public static final byte ACT_BOUNCE_DOWN = 2;
		public static final byte ACT_BOUNCE_UP = 3;
		public static final byte ACT_COLLISON = 4;
		public static final byte ACT_GRAVITY = 2;	// 중력
		public static final byte ACT_MYU = 2;	// 마찰계수
		public static final byte ACT_N = 3;	// 수직항력 계산용 계수
		public static final byte ACT_POWER = 60; //80;
		
		private int x, y;
		private int actTimeCnt;	// 시간 계수
		private int actScalar;	// 이동량
		private int actAngle;	// 각도
		private int actFric;	// 마찰력
		private byte state;
		private Image img;
		
		private myVector vector;
		
		public Ball() {
			// 로컬에서 로딩
			String url = null;
			
			x = 660;
			//y = 357;
			y = 357-50;
			actScalar = 0;
			actFric = 0;
			actAngle = 90;
			
			vector = new myVector();

			actTimeCnt = 0;
			state = ACT_BOUNCE_DOWN;
			
			try
			{
				if(StateValue.isUrlLive) {
					url = StateValue.liveResource + "soccer/img/";
				}
				else {
					url = StateValue.testResource + "soccer/img/";
				}
							
				img = loadUrlImage(new URL(url+"ball.png"));
				
			} catch(Exception ue) {
				img_check=true;
				ue.printStackTrace();
				
			}
		}
		
		public void init() {
			x = 660;
			//y = 357;
			y = 357-50;
			actScalar = 0;
			actAngle = 90;
			actTimeCnt = 0;
			actFric = 0;
			state = ACT_BOUNCE_DOWN;
			
			vector.varX = 0;
			vector.varY = 0;
		}
		
		public void destroy()
		{
			SceneManager.getInstance().removeImage(img);
			img = null;
			vector = null;
		}
		
		public void Draw(Graphics g) {
			g.drawImage(img, x, y, null);
		}
		
		public void Process() {
			switch(state) {
			case ACT_READY :
				//System.out.println("Ball state ACT_READY");
				break;
				
			case ACT_BOUNCE_DOWN :
				//System.out.println("Ball state ACT_BOUNCE_DOWN");
				actBall(actScalar, actAngle);
				
				if(y >= 357) {
					state = ACT_BOUNCE_UP;
					actTimeCnt = 0;
					actAngle = 270;
					
					//System.out.println("ball ACT_BOUNCE_UP change actScalar : " + actScalar);
					//actFric = ACT_MYU*actScalar/ACT_N;
					//System.out.println("ball ACT_BOUNCE_UP change actFric : " + actFric);
					//actScalar -= actFric;
					//System.out.println("ball ACT_BOUNCE_UP change actScalar-actFric : " + actScalar);
					
					actScalar -= (y-357)*4/5;	// 이동거리에 비례하여 반발력이 작도록 설정
					//System.out.println("ball ACT_BOUNCE_UP change 반발력 보정 : " + (y-357)/5);
					
					if(actScalar < 10) {	// 일정 수준 이하로 떨어지면 0으로 설정하여 움직이지 않도록 고정
						actScalar = 0;
						mn_state = STATE_PLAY_READY;	// 문제 풀기로 이동
						state = ACT_READY;
						//System.out.println("STATE_PLAY_READY move");
					}
					y = 357;
					
					/*
					System.out.println("ball posx : " + x + " , " + "ball posy : " + y);
					System.out.println("ball actScalar : " + actScalar);
					System.out.println("ball actFric : " + actFric);
					System.out.println("ball actTimeCnt : " + actTimeCnt);
					*/
				}
				else {
					actTimeCnt++;
					actScalar += (int)(ACT_GRAVITY*0.5*actTimeCnt*actTimeCnt);
					actFric = ACT_MYU*actScalar/8;//ACT_N;
					actScalar -= actFric;
				}

				/*
				System.out.println("ball posx : " + x + " , " + "ball posy : " + y);
				System.out.println("ball actScalar : " + actScalar);
				System.out.println("ball actFric : " + actFric);
				System.out.println("ball actTimeCnt : " + actTimeCnt);
				*/
				break;
				
			case ACT_BOUNCE_UP :
				//System.out.println("Ball state ACT_BOUNCE_UP");
				if(actScalar <= 0) {
					actScalar = 0;
					actAngle = 90;
					state = ACT_BOUNCE_DOWN;
					
					actTimeCnt = 0;
					actFric = 0;
					actScalar = 0;
				}
				else {
					actBall(actScalar, actAngle);
					actTimeCnt++;
					
					actScalar -= (int)(ACT_GRAVITY*0.5*actTimeCnt*actTimeCnt);
					
					actFric = ACT_MYU*actScalar/ACT_N;
					actScalar -= actFric;
				}

				/*
				System.out.println("ball posx : " + x + " , " + "ball posy : " + y);
				System.out.println("ball actScalar : " + actScalar);
				System.out.println("ball actFric : " + actFric);
				System.out.println("ball actTimeCnt : " + actTimeCnt);
				*/
				break;
				
			case ACT_SHOT :
			case ACT_COLLISON :
				//System.out.println("Ball state ACT_SHOT & ACT_COLLISON actAngle : " + actAngle);
				actBall(actScalar, actAngle);
				
				actTimeCnt++;
				
				actFric = ACT_MYU*actScalar/ACT_N;
				actScalar -= actFric/22;
				
				y += (int)(ACT_GRAVITY*0.5*actTimeCnt*actTimeCnt/6);
				
				/*
				actFric = ACT_MYU*actScalar/ACT_N;
				actScalar -= actFric/15;
				
				y += (int)(ACT_GRAVITY*0.5*actTimeCnt*actTimeCnt/4);
				*/

				/*
				System.out.println("ball posx : " + x + " , " + "ball posy : " + y);
				System.out.println("ball actScalar : " + actScalar);
				System.out.println("ball actFric : " + actFric);
				System.out.println("ball actAngle : " + actAngle);
				System.out.println("ball accel gravity : " + ACT_GRAVITY*0.5*actTimeCnt*actTimeCnt/4);
				System.out.println("ball actFric : " + actFric);
				System.out.println("ball actTimeCnt : " + actTimeCnt);
				*/
				break;
			}
		}
		
		public void actBall(int scalar, int angle) {
			vector.calcVarXY(scalar, angle);
			x += vector.varX;
			y += vector.varY;
		}
	}
	
	public SoccerGPlay() {
		//System.out.println("SoccerGPlay creator start");
		
		img_chr_a = new Image[5];
		img_chr_b = new Image[8];
		img_chr_c = new Image[3];
		img_chr_d = new Image[3];
		img_chr_e = new Image[3];
		img_no_l = new Image[10];
		img_no_s = new Image[10];
		img_qClrMatch = new Image[10];
		img_qAssct = new Image[16];
		img_qCnt = new Image[10];
		img_qAddCnt = new Image[10];
		img_Arrow = new Image[42];
		img_Boom = new Image[5];
		img_btn_ok = new Image[2];
		img_no_end = new Image[10];
		img_btnFin = new Image[2];
		img_btnRe = new Image[2];
		img_rank = new Image[4];
		img_btnYes = new Image[2];
		img_btnNo = new Image[2];
		img_Effect = new Image[4];
		
		img_load = new Image[2];
		img_load[0]= SceneManager.getInstance().getImage("img/load1.png");
		img_load[1]= SceneManager.getInstance().getImage("img/load2.png");
		
		mn_state = STATE_LODAING;
		mb_charActState = CHAR_ACT_WAIT;
		mb_aniId = mb_aniStep = 0;
		mn_qCnt = 0;
		
		mn_ballCnt = 10;
		mn_Score = 0;
		
		mb_minus1AniId = 0;
		mb_btnAniId = 0;
				
		mn_examPos = new int[4];
		mb_examState = new byte[4];
		
		ball = new Ball();
		//System.out.println("Ball created");
		
		mn_arrowPos = new int[42][2];
		mn_arrowPos[0][0] = 20; mn_arrowPos[0][1] = -30;
		mn_arrowPos[1][0] = 18; mn_arrowPos[1][1] = -29;
		mn_arrowPos[2][0] = 14; mn_arrowPos[2][1] = -27;
		mn_arrowPos[3][0] = 13; mn_arrowPos[3][1] = -26;
		mn_arrowPos[4][0] = 11; mn_arrowPos[4][1] = -24;
		mn_arrowPos[5][0] = 10; mn_arrowPos[5][1] = -22;
		mn_arrowPos[6][0] = 8; mn_arrowPos[6][1] = -21;
		mn_arrowPos[7][0] = 6; mn_arrowPos[7][1] = -19;
		mn_arrowPos[8][0] = 5; mn_arrowPos[8][1] = -17;
		mn_arrowPos[9][0] = 4; mn_arrowPos[9][1] = -15;
		mn_arrowPos[10][0] = 3; mn_arrowPos[10][1] = -13;
		mn_arrowPos[11][0] = 1; mn_arrowPos[11][1] = -10;
		mn_arrowPos[12][0] = 1; mn_arrowPos[12][1] = -8;
		mn_arrowPos[13][0] = 0; mn_arrowPos[13][1] = -5;
		mn_arrowPos[14][0] = 0; mn_arrowPos[14][1] = -2;
		mn_arrowPos[15][0] = 0; mn_arrowPos[15][1] = 0;
		mn_arrowPos[16][0] = 1; mn_arrowPos[16][1] = 3;
		mn_arrowPos[17][0] = 1; mn_arrowPos[17][1] = 5;
		mn_arrowPos[18][0] = 2; mn_arrowPos[18][1] = 7;
		mn_arrowPos[19][0] = 2; mn_arrowPos[19][1] = 10;
		mn_arrowPos[20][0] = 3; mn_arrowPos[20][1] = 12;
		mn_arrowPos[21][0] = 4; mn_arrowPos[21][1] = 14;
		mn_arrowPos[22][0] = 5; mn_arrowPos[22][1] = 17;
		mn_arrowPos[23][0] = 7; mn_arrowPos[23][1] = 19;
		mn_arrowPos[24][0] = 8; mn_arrowPos[24][1] = 21;
		mn_arrowPos[25][0] = 10; mn_arrowPos[25][1] = 24;
		mn_arrowPos[26][0] = 11; mn_arrowPos[26][1] = 26;
		mn_arrowPos[27][0] = 13; mn_arrowPos[27][1] = 29;
		mn_arrowPos[28][0] = 15; mn_arrowPos[28][1] = 31;
		mn_arrowPos[29][0] = 18; mn_arrowPos[29][1] = 34;
		mn_arrowPos[30][0] = 20; mn_arrowPos[30][1] = 36;
		mn_arrowPos[31][0] = 22; mn_arrowPos[31][1] = 38;
		mn_arrowPos[32][0] = 25; mn_arrowPos[32][1] = 41;
		mn_arrowPos[33][0] = 27; mn_arrowPos[33][1] = 43;
		mn_arrowPos[34][0] = 30; mn_arrowPos[34][1] = 45;
		mn_arrowPos[35][0] = 32; mn_arrowPos[35][1] = 46;
		mn_arrowPos[36][0] = 35; mn_arrowPos[36][1] = 48;
		mn_arrowPos[37][0] = 37; mn_arrowPos[37][1] = 50;
		mn_arrowPos[38][0] = 39; mn_arrowPos[38][1] = 51;
		mn_arrowPos[39][0] = 42; mn_arrowPos[39][1] = 52;
		mn_arrowPos[40][0] = 44; mn_arrowPos[40][1] = 53;
		mn_arrowPos[41][0] = 46; mn_arrowPos[41][1] = 54;

		mb_arrId = 15;
		mb_arrDir = ARROWDIRECTION_UP;
		
		mb_userSltId = 0;
		mb_boomAniId = 0;
		
		mn_slvTime = 15000;	// 15초
		
		mb_correct = false;
		
		mb_rsltCur = mb_rsltStep = mb_rsltRank = 0;
		
		mb_endCur = 0;
		mb_pause = false;
		
		mn_LFrame = 0;
		mb_drawstep = new byte[4];
		for(int i=0; i<4; i++)
			mb_drawstep[i] = 0;
		
		mb_effectStep = 0;
		
		m_sound = new Sound(8);

		//System.out.println("soccer play initialized");
	}
	
	public void destroyScene() {
		int i;

		mb_examState = null;
		mn_arrowPos = null;
		mn_examPos = null;

		SceneManager.getInstance().removeImage(img_txt_geim);
		img_txt_geim = null;
		SceneManager.getInstance().removeImage(img_txt_jongryo);
		img_txt_jongryo = null;
		SceneManager.getInstance().removeImage(img_txt_jun);
		img_txt_jun = null;
		SceneManager.getInstance().removeImage(img_txt_bi);
		img_txt_bi = null;
		SceneManager.getInstance().removeImage(img_txt_si);
		img_txt_si = null;
		SceneManager.getInstance().removeImage(img_txt_jak);
		img_txt_jak = null;
		SceneManager.getInstance().removeImage(img_qClrMatchTxt);
		img_qClrMatchTxt = null;
		SceneManager.getInstance().removeImage(img_qAssctTxt);
		img_qAssctTxt = null;
		SceneManager.getInstance().removeImage(img_qCntTxt);
		img_qCntTxt = null;
		SceneManager.getInstance().removeImage(img_time);
		img_time = null;
		SceneManager.getInstance().removeImage(img_OX_O);
		img_OX_O = null;
		SceneManager.getInstance().removeImage(img_OX_X);
		img_OX_X = null;
		SceneManager.getInstance().removeImage(img_minus1);
		img_minus1 = null;
		SceneManager.getInstance().removeImage(img_Ball);
		img_Ball = null;
		SceneManager.getInstance().removeImage(img_rsltPop);
		img_rsltPop = null;
		SceneManager.getInstance().removeImage(img_endPop);
		img_endPop = null;

		if(img_Arrow != null)
		{
			for(i=0; i<42; i++) {
				SceneManager.getInstance().removeImage(img_Arrow[i]);
				img_Arrow[i] = null;
			}
			img_Arrow = null;
		}

		if(img_Boom != null)
		{
			for(i=0; i<5; i++) {
				SceneManager.getInstance().removeImage(img_Boom[i]);
				img_Boom[i] = null;
			}
			img_Boom = null;
		}

		if(img_chr_a != null)
		{
			for(i=0; i<5; i++) {
				SceneManager.getInstance().removeImage(img_chr_a[i]);
				img_chr_a[i] = null;
			}
			img_chr_a = null;
		}

		if(img_chr_b != null)
		{
			for(i=0; i<8; i++) {
				SceneManager.getInstance().removeImage(img_chr_b[i]);
				img_chr_b[i] = null;
			}
			img_chr_b = null;
		}

		if(img_chr_c != null)
		{
			for(i=0; i<3; i++) {
				SceneManager.getInstance().removeImage(img_chr_c[i]);
				img_chr_c[i] = null;
			}
			img_chr_c = null;
		}

		if(img_chr_d != null)
		{
			for(i=0; i<3; i++) {
				SceneManager.getInstance().removeImage(img_chr_d[i]);
				img_chr_d[i] = null;
			}
			img_chr_d = null;
		}

		if(img_chr_e != null)
		{
			for(i=0; i<3; i++) {
				SceneManager.getInstance().removeImage(img_chr_e[i]);
				img_chr_e[i] = null;
			}
			img_chr_e = null;
		}
		
		if(img_no_l != null)
		{
			for(i=0; i<10; i++) {
				SceneManager.getInstance().removeImage(img_no_l[i]);
				img_no_l[i] = null;
			}
			img_no_l = null;
		}

		if(img_no_s != null)
		{
			for(i=0; i<10; i++) {
				SceneManager.getInstance().removeImage(img_no_s[i]);
				img_no_s[i] = null;
			}
			img_no_s = null;
		}

		if(img_qClrMatch != null)
		{
			for(i=0; i<10; i++) {
				SceneManager.getInstance().removeImage(img_qClrMatch[i]);
				img_qClrMatch[i] = null;
			}
			img_qClrMatch = null;
		}

		if(img_qAssct != null)
		{
			for(i=0; i<16; i++) {
				SceneManager.getInstance().removeImage(img_qAssct[i]);
				img_qAssct[i] = null;
			}
			img_qAssct = null;
		}

		if(img_qCnt != null)
		{
			for(i=0; i<10; i++) {
				SceneManager.getInstance().removeImage(img_qCnt[i]);
				img_qCnt[i] = null;
			}
			img_qCnt = null;
		}

		if(img_qAddCnt != null)
		{
			for(i=0; i<10; i++) {
				SceneManager.getInstance().removeImage(img_qAddCnt[i]);
				img_qAddCnt[i] = null;
			}
			img_qAddCnt = null;
		}

		if(img_btn_ok != null)
		{
			for(i=0; i<2; i++) {
				SceneManager.getInstance().removeImage(img_btn_ok[i]);
				img_btn_ok[i] = null;
			}
			img_btn_ok = null;
		}

		if(img_no_end != null)
		{
			for(i=0; i<10; i++) {
				SceneManager.getInstance().removeImage(img_no_end[i]);
				img_no_end[i] = null;
			}
			img_no_end = null;
		}

		if(img_btnFin != null)
		{
			for(i=0; i<2; i++) {
				SceneManager.getInstance().removeImage(img_btnFin[i]);
				img_btnFin[i] = null;
			}
			img_btnFin = null;
		}

		if(img_btnRe != null)
		{
			for(i=0; i<2; i++) {
				SceneManager.getInstance().removeImage(img_btnRe[i]);
				img_btnRe[i] = null;
			}
			img_btnRe = null;
		}

		if(img_rank != null)
		{
			for(i=0; i<4; i++) {
				SceneManager.getInstance().removeImage(img_rank[i]);
				img_rank[i] = null;
			}
			img_rank = null;
		}

		if(img_btnYes != null)
		{
			for(i=0; i<2; i++) {
				SceneManager.getInstance().removeImage(img_btnYes[i]);
				img_btnYes[i] = null;
			}
			img_btnYes = null;
		}

		if(img_btnNo != null)
		{
			for(i=0; i<2; i++) {
				SceneManager.getInstance().removeImage(img_btnNo[i]);
				img_btnNo[i] = null;
			}
			img_btnNo = null;
		}

		if(img_load != null)
		{
			for(i=0; i<2; i++) {
				SceneManager.getInstance().removeImage(img_load[i]);
				img_load[i] = null;
			}
			img_load = null;
		}

		if(img_Effect != null)
		{
			for(i=0; i<4; i++) {
				SceneManager.getInstance().removeImage(img_Effect[i]);
				img_Effect[i] = null;
			}
			img_Effect = null;
		}

		removeExamImages();

		if(ball != null)
		{
			ball.destroy();
			ball = null;
		}

		m_sound.destroySound(); 
		m_sound = null;
	}

	private void removeExamImages() {
		if (null != img_Exam1) {
			SceneManager.getInstance().removeImage(img_Exam1);
			img_Exam1 = null;
		}
		if (null != img_Exam2) {
			SceneManager.getInstance().removeImage(img_Exam2);
			img_Exam2 = null;
		}
		if (null != img_Exam3) {
			SceneManager.getInstance().removeImage(img_Exam3);
			img_Exam3 = null;
		}
		if (null != img_Exam4) {
			SceneManager.getInstance().removeImage(img_Exam4);
			img_Exam4 = null;
		}
	}
	
	public void draw(Graphics2D g) {
		//System.out.println("SoccerGPlay draw mn_state : " + mn_state);
		
		//g.drawImage(img_gameBg, 0, 0, null);
		if(mn_state == STATE_ERROR){
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
			return;
		}

		
		if(mn_state != STATE_LODAING) {
			// 공 개수
			if(mn_ballCnt >= 0) {
				g.drawImage(img_no_s[mn_ballCnt/10], 673, 478, null);
				g.drawImage(img_no_s[mn_ballCnt%10], 673+16, 478, null);
			}
			
			// 점수
			//System.out.println("draw mn_Score : " + mn_Score);
			g.drawImage(img_no_s[mn_Score%10], 822, 478, null);
			if(mn_Score/10 > 0)
				g.drawImage(img_no_s[(mn_Score/10)%10], 822-16, 478, null);
			if(mn_Score/100 > 0)
				g.drawImage(img_no_s[(mn_Score/100)%10], 822-16*2, 478, null);
			if(mn_Score/1000 > 0)
				g.drawImage(img_no_s[(mn_Score/1000)%10], 822-16*3, 478, null);
			if(mn_Score/10000 > 0)
				g.drawImage(img_no_s[(mn_Score/10000)%10], 822-16*4, 478, null);
			if(mn_Score/100000 > 0)
				g.drawImage(img_no_s[(mn_Score/100000)%10], 822-16*5, 478, null);
			if(mn_Score/1000000 > 0)
				g.drawImage(img_no_s[(mn_Score/1000000)%10], 822-16*6, 478, null);
			if(mn_Score/10000000 > 0)
				g.drawImage(img_no_s[(mn_Score/10000000)%10], 822-16*7, 478, null);
			
			if(mn_qCnt < 11)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/98, 419+19, 0, 0, mn_slvTime/98, 19, null);
			else if(mn_qCnt < 21)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/91, 419+19, 0, 0, mn_slvTime/91, 19, null);
			else if(mn_qCnt < 31)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/85, 419+19, 0, 0, mn_slvTime/85, 19, null);
			else if(mn_qCnt < 41)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/78, 419+19, 0, 0, mn_slvTime/78, 19, null);
			else if(mn_qCnt < 51)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/72, 419+19, 0, 0, mn_slvTime/72, 19, null);
			else if(mn_qCnt < 61)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/65, 419+19, 0, 0, mn_slvTime/65, 19, null);
			else if(mn_qCnt < 71)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/59, 419+19, 0, 0, mn_slvTime/59, 19, null);
			else if(mn_qCnt < 81)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/52, 419+19, 0, 0, mn_slvTime/52, 19, null);
			else if(mn_qCnt < 91)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/46, 419+19, 0, 0, mn_slvTime/46, 19, null);
			else if(mn_qCnt < 101)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/39, 419+19, 0, 0, mn_slvTime/39, 19, null);
			else if(mn_qCnt < 111)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/32, 419+19, 0, 0, mn_slvTime/32, 19, null);
			else if(mn_qCnt < 121)
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/27, 419+19, 0, 0, mn_slvTime/27, 19, null);
			else
				g.drawImage(img_time, 639, 419, 639+mn_slvTime/23, 419+19, 0, 0, mn_slvTime/23, 19, null);
			
			DrawCharAct(g);
			DrawQCnt(g);
			DrawArrow(g);
		}
		
		switch(mn_state) {
		case STATE_LODAING :
			draw_loading(g);
			break;
		case STATE_READY :
			if(mn_aniTime1 < 1600) {
				g.drawImage(img_txt_jun, 373, 213, null);
				g.drawImage(img_txt_bi, 508, 226, null);
			}
			else if(mn_aniTime1 < 3200) {
				g.drawImage(img_txt_si, 374, 225, null);
				g.drawImage(img_txt_jak, 504, 217, null);
			}
			break;

		case STATE_PLAY_EXAMPLEAPPEAR :
		case STATE_PLAY_READY :
		case STATE_PLAY_SOLVE :
		case STATE_PLAY_SELECT :
			DrawQuestion(g);
			ball.Draw(g);
			DrawExample(g);			
			break;
			
		case STATE_PLAY_NEXTQUESTION :
			DrawQuestion(g);
			//ball.Draw(g);
			DrawExample(g);
			if(mb_correct)
				g.drawImage(img_btn_ok[mb_btnAniId], 179, 456, null);				
			break;
			
		case STATE_PLAY_CORRECT :
			DrawQuestion(g);
			ball.Draw(g);
			DrawExample(g);
			drawOX(g, 0);
			break;
			
		case STATE_PLAY_INCORRECT :
			DrawQuestion(g);
			ball.Draw(g);
			DrawExample(g);
			drawOX(g, 1);
			drawMinus1(g);
			break;
			
		case STATE_PLAY_MISS :
			DrawQuestion(g);
			DrawExample(g);
			drawBallDisappear(g);
			drawMinus1(g);
			break;
			
		case STATE_GAMEEND_SAY :
			DrawQuestion(g);
			DrawExample(g);

			if(mn_aniTime1 < 800) {
				g.drawImage(img_txt_geim, 319, 216, null);
			}
			else {
				g.drawImage(img_txt_geim, 319, 216, null);
				g.drawImage(img_txt_jongryo, 484, 211, null);
			}
			break;
			
		case STATE_POPUP_RESULT :
			DrawQuestion(g);
			DrawExample(g);
			drawRsltPop(g);
			break;
		}
		
		if(mn_state != STATE_LODAING) {
			if(mb_pause)
				drawEndPop(g);
		}
	}

	public void process(int elapsedtime) {
		//System.out.println("SoccerGPlay process mn_state : " + mn_state);
		
		if(!mb_pause) {
			mn_aniTime1 += elapsedtime;
			mn_aniTime2 += elapsedtime;
			
			switch(mn_state) {
			case STATE_LODAING :
				mn_LFrame++;
				loadRes(mn_LFrame);
				if(mn_LFrame == 11){
					if( !img_check){
						try {
							if(StateValue.isUrlLive) {
								if(!SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.liveResource + "bg/soccer/game_bg.gif")))
									img_check =true;
							}
							else {
								if(!SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.testResource + "bg/soccer/game_bg.gif")))
									img_check =true;
							
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else if(mn_LFrame > 11) {
					if( ! img_check){
						mn_state = STATE_READY;
						mn_aniTime1 = mn_aniTime2 = 0;
						
						m_sound.playSound(SOUND_ID_READYGO);
					}
					else {
						mn_state = STATE_ERROR ;
						mn_aniTime1 = mn_aniTime2 = 0;
					}
				}
				break;
			case STATE_READY :
				//System.out.println("STATE_READY");
				if(mn_aniTime1 >= 3200) {
					mn_state = STATE_PLAY_EXAMPLEAPPEAR;
					mn_aniTime1 = 0;
					mn_qCnt++;
					mb_arrDir = ARROWDIRECTION_UP;
					mb_userSltId = 0;
					MakeQuestion();
					MakeExample();
				}
				break;
				
			case STATE_PLAY_EXAMPLEAPPEAR :
				//System.out.println("STATE_PLAY_EXAMPLEAPPEAR");
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ProcExample();
					ball.Process();
				}
				/*
				if(mb_examState[0] == EXAMPLESTATE_READY &&
						mb_examState[1] == EXAMPLESTATE_READY &&
						mb_examState[2] == EXAMPLESTATE_READY &&
						mb_examState[3] == EXAMPLESTATE_READY) {
					mn_state = STATE_PLAY_READY;
				}
				*/
				break;
				
			case STATE_PLAY_READY :
				//System.out.println("STATE_PLAY_READY");
				if(ball.state == Ball.ACT_READY)
					mn_slvTime -= elapsedtime;
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ProcArrow(elapsedtime);
					ball.Process();
					if(mn_slvTime < 0) {
						mn_state = STATE_PLAY_MISS;
						mb_charActState = CHAR_ACT_TIMEOVER;
						mb_aniId = mb_aniStep =0;
						mn_ballCnt--;
						
						mb_minus1AniId = 0;
						mb_correct = false;
						
						m_sound.playSound(SOUND_ID_TIMEOVER);
					}
				}
				break;
				
			case STATE_PLAY_SOLVE :
				//System.out.println("STATE_PLAY_SOLVE");
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ball.Process();
					mb_userSltId = checkCollision();
					//System.out.println("user select : " + mb_userSltId);
					if(mb_userSltId > 0 && mb_userSltId < 5) {
						mn_state = STATE_PLAY_SELECT;
						//ball.state = Ball.ACT_COLLISON;
						ball.actAngle += 180;
					}
					else if(mb_userSltId == 5) {
						mn_state = STATE_PLAY_MISS;
						mb_charActState = CHAR_ACT_EHUE;
						mb_aniId = mb_aniStep =0;
						mn_ballCnt--;
						/*
						for(int i=0; i<4; i++) {
							if(i == mb_userSltId-1 )
								mb_examState[i] = EXAMPLESTATE_SHAKE;
						}
						*/
						
						mb_minus1AniId = 0;
						mb_correct = false;
						
						m_sound.playSound(SOUND_ID_EHUE);
					}
				}
				break;
	
			case STATE_PLAY_SELECT :
				//System.out.println("STATE_PLAY_SELECT mb_userSltId : " + mb_userSltId);
				//System.out.println("STATE_PLAY_SELECT mb_ansId : " + mb_ansId);
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ball.Process();
					if(mb_userSltId == mb_ansId) {
						mn_state = STATE_PLAY_CORRECT;
		
						for(int i=0; i<4; i++) {
							if(i == mb_userSltId-1 )
								mb_examState[i] = EXAMPLESTATE_SHAKE;
						}
						
						mb_boomAniId = 0;
						
						mn_OXposX = 205-70;
						mn_OXposY = 119+94*(mb_userSltId-1)-60;
						
						addScore(500);
						mn_OXaniStep = 0;
						mb_correct = true;
						
						m_sound.playSound(SOUND_ID_CORRECT);
					}
					else {
						mn_state = STATE_PLAY_INCORRECT;
						for(int i=0; i<4; i++) {
							if(i == mb_userSltId-1 )
								mb_examState[i] = EXAMPLESTATE_SHAKE;
						}
						
						mn_OXposX = 205-70;
						mn_OXposY = 119+94*(mb_userSltId-1)-60;
						
						mn_ballCnt--;
						addScore(100);
						
						mn_OXaniStep = 0;
						mb_minus1AniId = 0;
						mb_correct = false;
						
						m_sound.playSound(SOUND_ID_MISS);
					}
				}
				break;
				
			case STATE_PLAY_CORRECT :
				//System.out.println("STATE_PLAY_CORRECT");
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ball.Process();
					ProcExample();
					
					procOX();
					
					if(mn_OXaniStep == 7) {
						mb_charActState = CHAR_ACT_ASSA;
						mb_aniId = mb_aniStep =0;
						
						m_sound.playSound(SOUND_ID_ASSA);
					}
				}
				break;
				
			case STATE_PLAY_INCORRECT :
				//System.out.println("STATE_PLAY_INCORRECT mn_OXaniStep : " + mn_OXaniStep);
				//System.out.println("STATE_PLAY_INCORRECT mb_minus1AniId : " + mb_minus1AniId);
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ball.Process();
					ProcExample();
					procOX();
					
					if(mn_OXaniStep == 7) {
						mb_charActState = CHAR_ACT_EHUE;
						mb_aniId = mb_aniStep = 0;
						
						m_sound.playSound(SOUND_ID_EHUE);
					}
					
					if(mb_minus1AniId < 8)
						mb_minus1AniId++;
				}
				break;
				
			case STATE_PLAY_MISS :
				//System.out.println("STATE_PLAY_MISS");
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ball.Process();
					ProcExample();
					
					if(mb_effectStep < 5) {
						mb_effectStep++;
						//System.out.println("mb_effectStep : " + mb_effectStep);
					}
					
					if(mb_minus1AniId < 8)
						mb_minus1AniId++;
				}
				break;
				
			case STATE_PLAY_NEXTQUESTION :
				//System.out.println("STATE_PLAY_NEXTQUESTION");
				if(mn_aniTime1 > 50) {
					mn_aniTime1 = 0;
					ball.Process();
	
					if(!mb_correct) {
						//System.out.println("STATE_PLAY_NEXTQUESTION incorrect");
						if(mn_ballCnt > 0) {
							mn_state = STATE_PLAY_READY;
							mb_userSltId = 0;
							ball.init();
							if(mn_qCnt < 11)
								mn_slvTime = 15000;
							else if(mn_qCnt < 21)
								mn_slvTime = 14000;
							else if(mn_qCnt < 31)
								mn_slvTime = 13000;
							else if(mn_qCnt < 41)
								mn_slvTime = 12000;
							else if(mn_qCnt < 51)
								mn_slvTime = 11000;
							else if(mn_qCnt < 61)
								mn_slvTime = 10000;
							else if(mn_qCnt < 71)
								mn_slvTime = 9000;
							else if(mn_qCnt < 81)
								mn_slvTime = 8000;
							else if(mn_qCnt < 91)
								mn_slvTime = 7000;
							else if(mn_qCnt < 101)
								mn_slvTime = 6000;
							else if(mn_qCnt < 111)
								mn_slvTime = 5000;
							else if(mn_qCnt < 121)
								mn_slvTime = 4200;
							else
								mn_slvTime = 3500;
							mb_effectStep = 0;
						}
						else {
							mn_state = STATE_GAMEEND_SAY;
							mb_charActState = CHAR_ACT_GAMEOVER;
							mb_aniId = mb_aniStep = 0;
							mb_effectStep = 0;
							
							m_sound.playSound(SOUND_ID_GAMEEND);
							
							if(mn_Score < 50000)
								mb_rsltRank = 0;
							else if(mn_Score < 100000)
								mb_rsltRank = 1;
							else if(mn_Score < 150000)
								mb_rsltRank = 2;
							else
								mb_rsltRank = 3;
							
							SceneManager.getInstance().sendScore_gravity(mn_Score);
							SceneManager.getInstance().send_gravity(11);
						}
					}
				}
				
				if(mn_aniTime2 > 100) {
					if(mb_correct) {
						mb_btnAniId++;
						if(mb_btnAniId > 1)
							mb_btnAniId = 0;
					}
				}
				break;
				
			case STATE_GAMEEND_SAY :
				if(mn_aniTime1 > 2000) {
					mn_state = STATE_POPUP_RESULT;
				}
				break;
				
			case STATE_POPUP_RESULT :
				if(mn_aniTime2 > 100) {
					mb_rsltStep++;
					if(mb_rsltStep > 5)
						mb_rsltStep = 0;
				}
				break;
			}
	
			if(mn_aniTime2 > 100) {
				mn_aniTime2 = 0;
				ProcCharAct();
			}
		}
	}

	public void processKey(Object nn, int key) {
		//System.out.println("SoccerGPlay processKey mn_state : " + mn_state +", key :"+ key);
		if(mn_state == STATE_ERROR){
			switch (key) {
				case HRcEvent.VK_ENTER:
				case Constant.KEY_PREV:
					SceneManager.getInstance().load_kind=true; //120523_1
					SceneManager.getInstance().setChangeScene(SceneManager.SATAE_PORTAL_MENU);
					break;
				}
		}
		
		if(mb_pause) {
			switch (key) {
			case HRcEvent.VK_RIGHT :
			case HRcEvent.VK_LEFT :
				if(mb_endCur == 0)
					mb_endCur = 1;
				else
					mb_endCur = 0;
				break;
				
			case HRcEvent.VK_ENTER:
				if(mb_endCur == 0) {
					SceneManager.getInstance().load_kind=true; //120523_1
					//SceneManager.getInstance().setChangeScene(SceneManager.SATAE_GAMETITLE, GameTitle.STATE_TITLE_RANK);
					SceneManager.getInstance().setChangeScene(SceneManager.SATAE_GAME_MENU);
				}
				else {
					mb_endCur = 0;
					mb_pause = false;
				}
				break;
				
			case HRcEvent.VK_ESCAPE:
			case Constant.KEY_PREV :
				mb_endCur = 0;
				mb_pause = false;
				break;
			}
		}
		else {
			if(key == HRcEvent.VK_UP)
				mn_qCnt++;
			
			if(key == HRcEvent.VK_ESCAPE || key == Constant.KEY_PREV) {
				if(mn_state != STATE_LODAING) {
					mb_endCur = 0;
					mb_pause = true;
					
					m_sound.stopSound();
				}
			}
			else {
				switch(mn_state) {
				case STATE_READY :
					break;
		
				case STATE_PLAY_EXAMPLEAPPEAR :
					break;
		
				case STATE_PLAY_READY :
					switch (key) {
					case HRcEvent.VK_ENTER:
						if(mb_charActState == CHAR_ACT_WAIT && ball.state == Ball.ACT_READY) {
							//System.out.println("STATE_PLAY_READY -> change CHAR_ACT_SHOT");
							mb_charActState = CHAR_ACT_SHOT;
							mb_aniId = mb_aniStep =0;
							//mb_arrDir = ARROWDIRECTION_STOP;
							mn_state = STATE_PLAY_SOLVE;
						}
						break;
					}
					break;
		
				case STATE_PLAY_SOLVE :
					break;
					
				case STATE_PLAY_SELECT :
					break;
					
				case STATE_PLAY_CORRECT :
					break;
					
				case STATE_PLAY_INCORRECT :
					break;
					
				case STATE_PLAY_NEXTQUESTION :
					if(mb_correct) {
						MakeQuestionInit();
						if(mn_qCnt < 11)
							addScore((mn_slvTime/98)*10);
						else if(mn_qCnt < 21)
							addScore((mn_slvTime/91)*10);
						else if(mn_qCnt < 31)
							addScore((mn_slvTime/85)*10);
						else if(mn_qCnt < 41)
							addScore((mn_slvTime/78)*10);
						else if(mn_qCnt < 51)
							addScore((mn_slvTime/72)*10);
						else if(mn_qCnt < 61)
							addScore((mn_slvTime/65)*10);
						else if(mn_qCnt < 71)
							addScore((mn_slvTime/59)*10);
						else if(mn_qCnt < 81)
							addScore((mn_slvTime/52)*10);
						else if(mn_qCnt < 91)
							addScore((mn_slvTime/46)*10);
						else if(mn_qCnt < 101)
							addScore((mn_slvTime/39)*10);
						else if(mn_qCnt < 111)
							addScore((mn_slvTime/32)*10);
						else if(mn_qCnt < 121)
							addScore((mn_slvTime/27)*10);
						else
							addScore((mn_slvTime/23)*10);
					}
					break;
					
				case STATE_GAMEEND_SAY :
					break;
					
				case STATE_POPUP_RESULT :
					switch (key) {
					case HRcEvent.VK_RIGHT :
					case HRcEvent.VK_LEFT :
						if(mb_rsltCur == 0)
							mb_rsltCur = 1;
						else
							mb_rsltCur = 0;
						break;
						
					case HRcEvent.VK_ENTER:
						if(mb_rsltCur == 0) {
							mn_state = STATE_READY;
							mn_aniTime1 = mn_aniTime2 = 0;
							
							mb_charActState = CHAR_ACT_WAIT;
							mb_aniId = mb_aniStep = 0;
							mn_qCnt = 0;
							
							mn_ballCnt = 10;
							mn_Score = 0;
							
							mb_minus1AniId = 0;
							mb_btnAniId = 0;
							
							mb_arrId = 15;
							mb_arrDir = ARROWDIRECTION_UP;
							
							mb_userSltId = 0;
							mb_boomAniId = 0;
							
							if(mn_qCnt < 11)
								mn_slvTime = 15000;
							else if(mn_qCnt < 21)
								mn_slvTime = 14000;
							else if(mn_qCnt < 31)
								mn_slvTime = 13000;
							else if(mn_qCnt < 41)
								mn_slvTime = 12000;
							else if(mn_qCnt < 51)
								mn_slvTime = 11000;
							else if(mn_qCnt < 61)
								mn_slvTime = 10000;
							else if(mn_qCnt < 71)
								mn_slvTime = 9000;
							else if(mn_qCnt < 81)
								mn_slvTime = 8000;
							else if(mn_qCnt < 91)
								mn_slvTime = 7000;
							else if(mn_qCnt < 101)
								mn_slvTime = 6000;
							else if(mn_qCnt < 111)
								mn_slvTime = 5000;
							else if(mn_qCnt < 121)
								mn_slvTime = 4200;
							else
								mn_slvTime = 3500;
							
							mb_correct = false;
							
							mb_rsltCur = mb_rsltStep = mb_rsltRank = 0;
							
							ball.init();
							
							m_sound.playSound(SOUND_ID_READYGO);
						}
						else {
							SceneManager.getInstance().load_kind=true; //120523_1
							SceneManager.getInstance().setChangeScene(SceneManager.SATAE_GAME_MENU, GameTitle.STATE_TITLE_RANK);
						}
						break;
					}
					break;
				}
			}
		}
	}
	
	public void loadRes(int step)
	{
		int i;
		String url = null;
		
		//System.out.println("loadRes step : " + step);

		try
		{
			
			if(StateValue.isUrlLive) {
				url = StateValue.liveResource + "soccer/img/";
			}
			else {
				url = StateValue.testResource + "soccer/img/";
			}
							
			switch(step) {
			case 1 :
				//img_gameBg = loadUrlImage(new URL(url+"game_bg.png"));
				img_txt_geim = loadUrlImage(new URL(url+"txt_geim.png"));
				img_txt_jongryo = loadUrlImage(new URL(url+"txt_jongryo.png"));
				img_txt_jun = loadUrlImage(new URL(url+"txt_jun.png"));
				img_txt_bi = loadUrlImage(new URL(url+"txt_bi.png"));
				img_txt_si = loadUrlImage(new URL(url+"txt_si.png"));
				img_txt_jak = loadUrlImage(new URL(url+"txt_jak.png"));
				img_time = loadUrlImage(new URL(url+"time.png"));
				//System.out.println("img_time");
				
				img_chr_a[0] = loadUrlImage(new URL(url+"char/chr_a_00.png"));
				img_chr_a[1] = loadUrlImage(new URL(url+"char/chr_a_01.png"));
				img_chr_a[2] = loadUrlImage(new URL(url+"char/chr_a_02.png"));
				img_chr_a[3] = loadUrlImage(new URL(url+"char/chr_a_03.png"));
				img_chr_a[4] = loadUrlImage(new URL(url+"char/chr_a_04.png"));
				//System.out.println("img_chr_a");
				
				img_chr_b[0] = loadUrlImage(new URL(url+"char/chr_b_01.png"));
				img_chr_b[1] = loadUrlImage(new URL(url+"char/chr_b_02.png"));
				img_chr_b[2] = loadUrlImage(new URL(url+"char/chr_b_03.png"));
				img_chr_b[3] = loadUrlImage(new URL(url+"char/chr_b_04.png"));
				img_chr_b[4] = loadUrlImage(new URL(url+"char/chr_b_05.png"));
				img_chr_b[5] = loadUrlImage(new URL(url+"char/chr_b_06.png"));
				img_chr_b[6] = loadUrlImage(new URL(url+"char/chr_b_07.png"));
				img_chr_b[7] = loadUrlImage(new URL(url+"char/chr_b_08.png"));
				//System.out.println("img_chr_b");
				break;
			case 2 :
				img_chr_c[0] = loadUrlImage(new URL(url+"char/chr_c_01.png"));
				img_chr_c[1] = loadUrlImage(new URL(url+"char/chr_c_02.png"));
				img_chr_c[2] = loadUrlImage(new URL(url+"char/chr_c_03.png"));
				//System.out.println("img_chr_c");
				
				img_chr_d[0] = loadUrlImage(new URL(url+"char/chr_d_01.png"));
				img_chr_d[1] = loadUrlImage(new URL(url+"char/chr_d_02.png"));
				img_chr_d[2] = loadUrlImage(new URL(url+"char/chr_d_03.png"));
				//System.out.println("img_chr_d");
				
				img_chr_e[0] = loadUrlImage(new URL(url+"char/chr_e_01.png"));
				img_chr_e[1] = loadUrlImage(new URL(url+"char/chr_e_02.png"));
				img_chr_e[2] = loadUrlImage(new URL(url+"char/chr_e_03.png"));
				//System.out.println("img_chr_e");
		
				for(i=0; i<10; i++) {
					//System.out.println("img large load id : "+i);
					img_no_l[i] = loadUrlImage(new URL(url+"num/no_l_"+i+".png"));
				}
				break;
			case 3 :
				for(i=0; i<10; i++) {
					img_no_s[i] = loadUrlImage(new URL(url+"num/no_s_"+i+".png"));
				}
				//System.out.println("img_no_s");
				
				for(i=0; i<9; i++) {
					img_qClrMatch[i] = loadUrlImage(new URL(url+"question/c0"+(i+1)+"_00.png"));
				}
				img_qClrMatch[9] = loadUrlImage(new URL(url+"question/c10_00.png"));
				//System.out.println("img_qClrMatch");
				break;
			case 4 :
				for(i=0; i<9; i++) {
					img_qAssct[i] = loadUrlImage(new URL(url+"question/g0"+(i+1)+"_00.png"));
				}
				for(i=9; i<16; i++) {
					img_qAssct[i] = loadUrlImage(new URL(url+"question/g"+(i+1)+"_00.png"));
				}
				//System.out.println("img_qAssct");
				break;
			case 5 :
				for(i=0; i<9; i++) {
					img_qCnt[i] = loadUrlImage(new URL(url+"question/n0"+(i+1)+"_00.png"));
				}
				img_qCnt[9] = loadUrlImage(new URL(url+"question/n10_00.png"));
				//System.out.println("img_qCnt");
				
				for(i=0; i<9; i++) {
					img_qAddCnt[i] = loadUrlImage(new URL(url+"question/nn0"+(i+1)+"_00.png"));
				}
				img_qAddCnt[9] = loadUrlImage(new URL(url+"question/nn10_00.png"));
				//System.out.println("img_qAddCnt");
				break;
			case 6 :
				img_qClrMatchTxt = loadUrlImage(new URL(url+"question/q_color.png"));
				img_qAssctTxt = loadUrlImage(new URL(url+"question/q_remind.png"));
				img_qCntTxt = loadUrlImage(new URL(url+"question/q_number.png"));
				//System.out.println("img_qCntTxt");
				
				img_Arrow[0] = loadUrlImage(new URL(url+"arrow/arrow_d45.png"));
				img_Arrow[1] = loadUrlImage(new URL(url+"arrow/arrow_d42.png"));
				img_Arrow[2] = loadUrlImage(new URL(url+"arrow/arrow_d39.png"));
				img_Arrow[3] = loadUrlImage(new URL(url+"arrow/arrow_d36.png"));
				img_Arrow[4] = loadUrlImage(new URL(url+"arrow/arrow_d33.png"));
				img_Arrow[5] = loadUrlImage(new URL(url+"arrow/arrow_d30.png"));
				img_Arrow[6] = loadUrlImage(new URL(url+"arrow/arrow_d27.png"));
				img_Arrow[7] = loadUrlImage(new URL(url+"arrow/arrow_d24.png"));
				img_Arrow[8] = loadUrlImage(new URL(url+"arrow/arrow_d21.png"));
				img_Arrow[9] = loadUrlImage(new URL(url+"arrow/arrow_d18.png"));
				img_Arrow[10] = loadUrlImage(new URL(url+"arrow/arrow_d15.png"));
				img_Arrow[11] = loadUrlImage(new URL(url+"arrow/arrow_d12.png"));
				img_Arrow[12] = loadUrlImage(new URL(url+"arrow/arrow_d09.png"));
				img_Arrow[13] = loadUrlImage(new URL(url+"arrow/arrow_d06.png"));
				img_Arrow[14] = loadUrlImage(new URL(url+"arrow/arrow_d03.png"));
				img_Arrow[15] = loadUrlImage(new URL(url+"arrow/arrow_00.png"));
				img_Arrow[16] = loadUrlImage(new URL(url+"arrow/arrow_u03.png"));
				img_Arrow[17] = loadUrlImage(new URL(url+"arrow/arrow_u06.png"));
				break;
			case 7 :
				img_Arrow[18] = loadUrlImage(new URL(url+"arrow/arrow_u09.png"));
				img_Arrow[19] = loadUrlImage(new URL(url+"arrow/arrow_u12.png"));
				img_Arrow[20] = loadUrlImage(new URL(url+"arrow/arrow_u15.png"));
				img_Arrow[21] = loadUrlImage(new URL(url+"arrow/arrow_u18.png"));
				img_Arrow[22] = loadUrlImage(new URL(url+"arrow/arrow_u21.png"));
				img_Arrow[23] = loadUrlImage(new URL(url+"arrow/arrow_u24.png"));
				img_Arrow[24] = loadUrlImage(new URL(url+"arrow/arrow_u27.png"));
				img_Arrow[25] = loadUrlImage(new URL(url+"arrow/arrow_u30.png"));
				img_Arrow[26] = loadUrlImage(new URL(url+"arrow/arrow_u33.png"));
				img_Arrow[27] = loadUrlImage(new URL(url+"arrow/arrow_u36.png"));
				img_Arrow[28] = loadUrlImage(new URL(url+"arrow/arrow_u39.png"));
				img_Arrow[29] = loadUrlImage(new URL(url+"arrow/arrow_u42.png"));
				img_Arrow[30] = loadUrlImage(new URL(url+"arrow/arrow_u45.png"));
				img_Arrow[31] = loadUrlImage(new URL(url+"arrow/arrow_u48.png"));
				img_Arrow[32] = loadUrlImage(new URL(url+"arrow/arrow_u51.png"));
				img_Arrow[33] = loadUrlImage(new URL(url+"arrow/arrow_u54.png"));
				img_Arrow[34] = loadUrlImage(new URL(url+"arrow/arrow_u57.png"));
				img_Arrow[35] = loadUrlImage(new URL(url+"arrow/arrow_u60.png"));
				img_Arrow[36] = loadUrlImage(new URL(url+"arrow/arrow_u63.png"));
				img_Arrow[37] = loadUrlImage(new URL(url+"arrow/arrow_u66.png"));
				img_Arrow[38] = loadUrlImage(new URL(url+"arrow/arrow_u69.png"));
				break;
			case 8 :
				img_Arrow[39] = loadUrlImage(new URL(url+"arrow/arrow_u72.png"));
				img_Arrow[40] = loadUrlImage(new URL(url+"arrow/arrow_u75.png"));
				img_Arrow[41] = loadUrlImage(new URL(url+"arrow/arrow_u78.png"));
				//System.out.println("img_Arrow");
				
				for(i=0; i<5; i++)
					img_Boom[i] = loadUrlImage(new URL(url+"effect/boom_0"+(i+1)+".png"));
				//System.out.println("img_Boom");
				
				img_OX_O = loadUrlImage(new URL(url+"effect/ox_o_01.png"));
				img_OX_X = loadUrlImage(new URL(url+"effect/ox_x_01.png"));
				//System.out.println("img_OX_X");
				
				img_minus1 = loadUrlImage(new URL(url+"effect/minus_1.png"));
				
				for(i=0; i<4; i++)
					img_Effect[i] = loadUrlImage(new URL(url+"effect/effect_b0"+(i+1)+".png"));

				img_btn_ok[0] = loadUrlImage(new URL(url+"btn_ok_up.png"));
				img_btn_ok[1] = loadUrlImage(new URL(url+"btn_ok_push.png"));
				//System.out.println("img_btn_ok");
				break;
			case 9 :
				if(StateValue.isUrlLive) {
					url = StateValue.liveResource + "pop_result/";
				}
				else {
					url = StateValue.testResource + "pop_result/";
				}
				for(i=0; i<10; i++) {
					img_no_end[i] = loadUrlImage(new URL(url+"end_"+i+".png"));
				}
				//System.out.println("img_no_end");
				
				img_btnFin[0] = loadUrlImage(new URL(url+"end_bt_fin01.png"));
				img_btnFin[1] = loadUrlImage(new URL(url+"end_bt_fin02.png"));
				img_btnRe[0] = loadUrlImage(new URL(url+"end_bt_re01.png"));
				img_btnRe[1] = loadUrlImage(new URL(url+"end_bt_re02.png"));
				//System.out.println("img_btnRe");
				
				img_rank[0] = loadUrlImage(new URL(url+"end_rank_d.png"));
				img_rank[1] = loadUrlImage(new URL(url+"end_rank_c.png"));
				img_rank[2] = loadUrlImage(new URL(url+"end_rank_b.png"));
				img_rank[3] = loadUrlImage(new URL(url+"end_rank_a.png"));
				//System.out.println("img_rank");
				
				img_rsltPop = loadUrlImage(new URL(url+"end_popup.png"));
				//System.out.println("img_rsltPop");
				break;
			case 10 :
				if(StateValue.isUrlLive) {
					url = StateValue.liveResource + "pop_end/";
				}
				else {
					url = StateValue.testResource + "pop_end/";
				}
				img_btnYes[0] = loadUrlImage(new URL(url+"btn_yes_on.png"));
				img_btnYes[1] = loadUrlImage(new URL(url+"btn_yes_off.png"));
				img_btnNo[0] = loadUrlImage(new URL(url+"btn_no_on.png"));
				img_btnNo[1] = loadUrlImage(new URL(url+"btn_no_off.png"));
				//System.out.println("img_btnNo");
				
				img_endPop = loadUrlImage(new URL(url+"pop_end.png"));
				
				if(m_sound != null) {
					
					if(StateValue.isUrlLive) {
						url = StateValue.liveResource + "soccer/snd/";
					}
					else {
						url = StateValue.testResource + "soccer/snd/";
					}
					
					m_sound.loadSound(new URL(url+"assa.mp2"));
					m_sound.loadSound(new URL(url+"correct.mp2"));
					m_sound.loadSound(new URL(url+"ehue.mp2"));
					m_sound.loadSound(new URL(url+"gameend.mp2"));
					m_sound.loadSound(new URL(url+"kickball.mp2"));
					m_sound.loadSound(new URL(url+"miss.mp2"));
					m_sound.loadSound(new URL(url+"readygo.mp2"));
					m_sound.loadSound(new URL(url+"timeover.mp2"));
					for(i=0; i<m_sound.mn_sndId; i++){
						if(m_sound.m_player[i] == null)
							img_check=true;
					}
				}
				else{
					img_check=true;
					//System.out.println("Sound is null");
				}
				
				SceneManager.getInstance().load_kind = false;
					
				break;
			}
		} catch(Exception ue) {
			img_check=true;
			ue.printStackTrace();
		}
		
		//System.out.println("soccer play load res complete");
	}
	
	public void ProcCharAct() {
		switch(mb_charActState) {
		case CHAR_ACT_WAIT :
			//System.out.println("CHAR_ACT_WAIT : " + mb_aniStep);
			if(mb_aniStep > 11)
				mb_aniStep = 0;
			switch(mb_aniStep) {
			case 0 :
			case 1 :
			case 2 :
				mb_aniId = mb_aniStep;
				break;
			case 3 :
				mb_aniId = 1;
				break;
			case 4 :
			case 5 :
			case 6 :
				mb_aniId = (byte) (mb_aniStep-4);
				break;
			case 7 :
				mb_aniId = 1;
				break;
			case 8 :
				mb_aniId = 0;
				break;
			case 9 :
				mb_aniId = 3;
				break;
			case 10 :
				mb_aniId = 4;
				break;
			case 11 :
				mb_aniId = 1;
				break;
			}
			break;
			
		case CHAR_ACT_SHOT :
			//System.out.println("CHAR_ACT_SHOT : " + mb_aniStep);
			if(mb_aniStep == 2) {
				//System.out.println("CHAR_ACT_SHOT -> Ball.ACT_SHOT");
				ball.state = Ball.ACT_SHOT;
				ball.actTimeCnt = 0;
				ball.actScalar = Ball.ACT_POWER;
				
				if(mb_arrId <= 15)
					ball.actAngle = -45+3*mb_arrId+180;
				else
					ball.actAngle = 3*(mb_arrId-15)+180;
				
				m_sound.playSound(SOUND_ID_KICKBALL);
			}
			if(mb_aniStep > 7) {
				mb_charActState = CHAR_ACT_WAIT;
				mb_aniId = mb_aniStep =0;
			}
			mb_aniId = mb_aniStep;
			break;
			
		case CHAR_ACT_ASSA :
			//System.out.println("CHAR_ACT_ASSA : " + mb_aniStep);
			switch(mb_aniStep) {
			case 0 :
			case 3 :
				mb_aniId = 1;
				break;
			case 1 :
			case 2 :
				mb_aniId = 2;
				break;
			case 4 :
				mb_aniId = 0;
				break;
			case 5 :
				mb_charActState = CHAR_ACT_WAIT;
				mb_aniId = mb_aniStep =0;
				mn_state = STATE_PLAY_NEXTQUESTION;
				break;
			}
			break;
			
		case CHAR_ACT_EHUE :
			//System.out.println("CHAR_ACT_EHUE : " + mb_aniStep);
			switch(mb_aniStep) {
			case 0 :
				mb_aniId = 0;
				break;
			case 1 :
				mb_aniId = 1;
				break;
			case 2 :
				mb_aniId = 2;
				break;
			case 3 :
				mb_aniId = 1;
				break;
			case 4 :
				mb_aniId = 0;
				break;
			case 5 :
				mb_charActState = CHAR_ACT_WAIT;
				mb_aniId = mb_aniStep =0;
				
				mn_state = STATE_PLAY_NEXTQUESTION;
				break;
			}
			break;
			
		case CHAR_ACT_TIMEOVER :
			//System.out.println("CHAR_ACT_TIMEOVER : " + mb_aniStep);
			if(mb_aniStep > 6) {
				mb_charActState = CHAR_ACT_WAIT;
				mb_aniId = mb_aniStep =0;
				
				mn_state = STATE_PLAY_NEXTQUESTION;
			}
			break;
			
		case CHAR_ACT_GAMEOVER :
			//System.out.println("CHAR_ACT_GAMEOVER : " + mb_aniStep);
			if(mb_aniStep < 8)
				mb_aniId = 0;
			else {
				switch(mb_aniStep) {
				case 8 :
				case 10 :
					mb_aniId = 1;
					break;
				case 9 :
					mb_aniId = 2;
					break;
				case 11 :
					mb_aniId = 0;
					mb_aniStep = 7;
					break;
				}
			}
			break;
		}
		
		mb_aniStep++;
	}
	
	public void DrawCharAct(Graphics2D g) {
		//System.out.println("mb_aniId : " + mb_aniId);
		switch(mb_charActState) {
		case CHAR_ACT_WAIT :
			g.drawImage(img_chr_a[mb_aniId], 669, 299, null);
			break;
			
		case CHAR_ACT_SHOT :
			g.drawImage(img_chr_b[mb_aniId], 669, 299, null);
			break;
			
		case CHAR_ACT_ASSA :
			g.drawImage(img_chr_c[mb_aniId], 669, 299, null);
			break;
			
		case CHAR_ACT_EHUE :
			g.drawImage(img_chr_d[mb_aniId], 669, 299, null);
			break;

		case CHAR_ACT_TIMEOVER :
			g.drawImage(img_chr_e[0], 669, 299, null);
			break;
			
		case CHAR_ACT_GAMEOVER :
			g.drawImage(img_chr_e[mb_aniId], 669, 299, null);
			break;
		}
	}
	
	public void DrawQCnt(Graphics g) {
		g.drawImage(img_no_l[mn_qCnt/100], 693, 71, null);
		g.drawImage(img_no_l[(mn_qCnt%100)/10], 693+26, 71, null);
		g.drawImage(img_no_l[mn_qCnt%10], 693+26*2, 71, null);
	}
	
	public void MakeQuestion() {
		mb_qType = (byte)SceneManager.getInstance().getRandomInt(0, 3);
		//mb_qType = QUESTION_ADDCOUNT;
		
		switch(mb_qType) {
		case QUESTION_COLORMATCH :
		case QUESTION_COUNT :
		case QUESTION_ADDCOUNT :
			mb_qId = (byte)SceneManager.getInstance().getRandomInt(0, 9);
			break;
			
		case QUESTION_ASSOCIATE :
			mb_qId = (byte)SceneManager.getInstance().getRandomInt(0, 15);
			break;
		}
	}
	
	public void DrawQuestion(Graphics g) {
		switch(mb_qType) {
		case QUESTION_COLORMATCH :	//(653,112), (632,137)
			g.drawImage(img_qClrMatchTxt, 653, 109, null);
			g.drawImage(img_qClrMatch[mb_qId], 631, 133, null);
			break;
		case QUESTION_ASSOCIATE :
			g.drawImage(img_qAssctTxt, 653, 109, null);
			g.drawImage(img_qAssct[mb_qId], 631, 133, null);
			break;
		case QUESTION_COUNT :
			g.drawImage(img_qCntTxt, 653, 109, null);
			g.drawImage(img_qCnt[mb_qId], 631, 133, null);
			break;
		case QUESTION_ADDCOUNT :
			g.drawImage(img_qCntTxt, 653, 109, null);
			g.drawImage(img_qAddCnt[mb_qId], 631, 133, null);
			break;
		}
	}
	
	public void MakeExample() {
		String url;
		
		int examList[];
		Image examimg[];
		
		examList = new int[4];
		for(int i=0; i<4; i++)
			examList[i] = 0;
		
		examimg = new Image[4];
		
		url = null;
		try
		{
			
			if(StateValue.isUrlLive) {
				url = StateValue.liveResource + "soccer/img/";
			}
			else {
				url = StateValue.testResource + "soccer/img/";
			}
						
		} catch(Exception ue) {
			//img_check=true;
			ue.printStackTrace();
		}
		
		try
		{
			switch(mb_qType) {
			case QUESTION_COLORMATCH :
				switch(mb_qId) {
				case 0 :
					mb_ansId = 2;
					examList = makeExamId((int)mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/c01_01_g13_03.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c01_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c01_03_c05_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c01_04_c06_02.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 1 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/c02_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c02_02_g04_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c02_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c02_04_g02_02_g03_03.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 2 :
					mb_ansId = 3;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/c03_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c03_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c03_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c03_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 3 :
					mb_ansId = 4;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/c04_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c04_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c04_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c04_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 4 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/c05_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c05_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c01_03_c05_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c05_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 5 :
					mb_ansId = 2;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/c06_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c01_04_c06_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c06_03_g09_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c06_04_c09_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 6 :
					mb_ansId = 3;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/c07_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c07_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c07_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c07_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 7 :
					mb_ansId = 2;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/c08_01_g06_03.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c08_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c08_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c08_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 8 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/c09_01_g14_04.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c09_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c09_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c06_04_c09_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 9 :
					mb_ansId = 3;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/c10_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c10_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c10_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c10_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				}
				break;
				
			case QUESTION_COUNT :
			case QUESTION_ADDCOUNT :
				mb_ansId = (byte)SceneManager.getInstance().getRandomInt(1, 4);
				examList = makeExamId(mb_qId+1, (int)mb_ansId, 10);
				removeExamImages();
				if(examList[0] < 10)
					img_Exam1 = loadUrlImage(new URL(url+"answer/num_0"+examList[0]+".png"));
				else
					img_Exam1 = loadUrlImage(new URL(url+"answer/num_10.png"));
				
				if(examList[1] < 10)
					img_Exam2 = loadUrlImage(new URL(url+"answer/num_0"+examList[1]+".png"));
				else
					img_Exam2 = loadUrlImage(new URL(url+"answer/num_10.png"));
				
				if(examList[2] < 10)
					img_Exam3 = loadUrlImage(new URL(url+"answer/num_0"+examList[2]+".png"));
				else
					img_Exam3 = loadUrlImage(new URL(url+"answer/num_10.png"));
				
				if(examList[3] < 10)
					img_Exam4 = loadUrlImage(new URL(url+"answer/num_0"+examList[3]+".png"));
				else
					img_Exam4 = loadUrlImage(new URL(url+"answer/num_10.png"));
				break;
				
			case QUESTION_ASSOCIATE :
				switch(mb_qId) {
				case 0 :
					mb_ansId = 2;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
				
					examimg[0] = loadUrlImage(new URL(url+"answer/g01_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g01_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g01_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g01_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 1 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/g02_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c02_04_g02_02_g03_03.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g02_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g02_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 2 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/g03_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g03_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c02_04_g02_02_g03_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g03_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 3 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/g04_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/c02_02_g04_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g04_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g04_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 4 :
					mb_ansId = 3;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/g05_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g05_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g05_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g05_04_g07_01.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 5 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/g06_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g06_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c08_01_g06_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g06_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 6 :
					mb_ansId = 2;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/g05_04_g07_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g07_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g07_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g07_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 7 :
					mb_ansId = 4;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/g08_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g08_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g08_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g08_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 8 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
				
					examimg[0] = loadUrlImage(new URL(url+"answer/g09_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g09_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c06_03_g09_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g09_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 9 :
					mb_ansId = 3;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/g10_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g10_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g10_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g10_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 10 :
					mb_ansId = 2;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/g11_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g11_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g11_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g11_04_g12_01.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 11 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
				
					examimg[0] = loadUrlImage(new URL(url+"answer/g11_04_g12_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g12_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g12_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g12_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 12 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
				
					examimg[0] = loadUrlImage(new URL(url+"answer/g13_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g13_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/c01_01_g13_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g13_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 13 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/g14_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g14_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g14_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/c09_01_g14_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 14 :
					mb_ansId = 1;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);

					examimg[0] = loadUrlImage(new URL(url+"answer/g15_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g15_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g15_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g15_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				case 15 :
					mb_ansId = 2;
					examList = makeExamId(mb_ansId, (int)mb_ansId, 4);
					
					examimg[0] = loadUrlImage(new URL(url+"answer/g16_01.png"));
					examimg[1] = loadUrlImage(new URL(url+"answer/g16_02.png"));
					examimg[2] = loadUrlImage(new URL(url+"answer/g16_03.png"));
					examimg[3] = loadUrlImage(new URL(url+"answer/g16_04.png"));
					removeExamImages();
					img_Exam1 = examimg[examList[0]-1];
					img_Exam2 = examimg[examList[1]-1];
					img_Exam3 = examimg[examList[2]-1];
					img_Exam4 = examimg[examList[3]-1];
					break;
				}
				break;
			}
		} catch(Exception e) {
			//img_check=true;
			e.printStackTrace();
		}
		
		MakeExamOrder();
		
		//System.out.println("make example");
	}
	
	public int[] makeExamId(int answer, int answerid, int itemCnt) {
		int idlist[];
		int idex[];
		int examList[];
		int i, id;
		int idlistLen, idexLen;
		int sltId1, sltId2;
		
		examList = new int[4];
		idlist = new int[itemCnt-1];
		idlistLen = itemCnt-1;
		idex = new int[4];
		idexLen =4;
		sltId1 = sltId2 = 0;
		
		for(i=0; i<4; i++)
			examList[i] = 0;
		
		id = 0;
		for(i=1; i<itemCnt; i++) {
			if(answer != i) {
				idlist[id] = i;
				id++;
			}
		}
		if(answer != itemCnt)
			idlist[itemCnt-2] = itemCnt;
		
		for(i=1; i<5; i++) {
			idex[i-1] = i;
		}
		
		// example 1
		sltId1 = SceneManager.getInstance().getRandomInt(0, idlistLen-1);
		sltId2 = SceneManager.getInstance().getRandomInt(0, idexLen-1);
		
		examList[idex[sltId2]-1] = idlist[sltId1];
		if(sltId1 != idlistLen-1) {
			for(i=sltId1; i<idlistLen-1; i++) {
				idlist[i] = idlist[i+1];
			}
		}
		idlistLen--;
		
		if(sltId2 != idexLen-1) {
			for(i=sltId2; i<idexLen-1; i++) {
				idex[i] = idex[i+1];
			}
		}
		idexLen--;
		
		// example 2
		sltId1 = SceneManager.getInstance().getRandomInt(0, idlistLen-1);
		sltId2 = SceneManager.getInstance().getRandomInt(0, idexLen-1);
		
		examList[idex[sltId2]-1] = idlist[sltId1];
		if(sltId1 != idlistLen-1) {
			for(i=sltId1; i<idlistLen-1; i++) {
				idlist[i] = idlist[i+1];
			}
		}
		idlistLen--;
		
		if(sltId2 != idexLen-1) {
			for(i=sltId2; i<idexLen-1; i++) {
				idex[i] = idex[i+1];
			}
		}
		idexLen--;
		
		// example 3 
		sltId1 = SceneManager.getInstance().getRandomInt(0, idlistLen-1);
		sltId2 = SceneManager.getInstance().getRandomInt(0, idexLen-1);
		
		examList[idex[sltId2]-1] = idlist[sltId1];
		
		for(i=0; i<4; i++) {
			if(examList[i] == 0) {
				examList[i] = answer;
				mb_ansId = (byte)(i+1);
				break;
			}
		}
		
		/*
		System.out.println("exam 1 : " + examList[0]);
		System.out.println("exam 2 : " + examList[1]);
		System.out.println("exam 3 : " + examList[2]);
		System.out.println("exam 4 : " + examList[3]);
		System.out.println("answer : " + examList[mb_ansId-1]);
		*/

		return examList;
	}
	
	public void DrawExample(Graphics g) {
		/*
		System.out.println("DrawExample");
		if(img_Exam1 == null)
			System.out.println("img_Exam1 null");
		if(img_Exam2 == null)
			System.out.println("img_Exam2 null");
		if(img_Exam3 == null)
			System.out.println("img_Exam3 null");
		if(img_Exam4 == null)
			System.out.println("img_Exam4 null");
			*/

		if(mb_examState[0] == EXAMPLESTATE_DISAPPEAR) {
			if(mb_boomAniId < 5)
				g.drawImage(img_Boom[mb_boomAniId], 160, 74, null);
		}
		else
			g.drawImage(img_Exam1, 160+mn_examPos[0], 74, null);
		
		if(mb_examState[1] == EXAMPLESTATE_DISAPPEAR) {
			if(mb_boomAniId < 5)
				g.drawImage(img_Boom[mb_boomAniId], 160, 74+101, null);
		}
		else
			g.drawImage(img_Exam2, 160+mn_examPos[1], 74+101, null);
		
		if(mb_examState[2] == EXAMPLESTATE_DISAPPEAR) {
			if(mb_boomAniId < 5)
				g.drawImage(img_Boom[mb_boomAniId], 160, 74+101*2, null);
		}
		else
			g.drawImage(img_Exam3, 160+mn_examPos[2], 74+101*2, null);
		
		if(mb_examState[3] == EXAMPLESTATE_DISAPPEAR) {
			if(mb_boomAniId < 5)
				g.drawImage(img_Boom[mb_boomAniId], 160, 74+101*3, null);
		}
		else
			g.drawImage(img_Exam4, 160+mn_examPos[3], 74+101*3, null);
	}
	
	public void ProcExample() {
		
		for(int i=0; i<4; i++) {
			switch(mb_examState[i]) {
			case EXAMPLESTATE_APPEAR :
				//System.out.println("exam " + i+" pos:" + mn_examPos[i]);
				if(mn_examPos[i] < 0) {
					if(mn_examPos[i] < -180)
						mn_examPos[i] += 120;
					else if(mn_examPos[i] < -90)
						mn_examPos[i] += 60;
					else if(mn_examPos[i] < -45)
						mn_examPos[i] += 30;
					else
						mn_examPos[i] += 20;
				}
				else if(mn_examPos[i] == 0) {
					mb_examState[i] = EXAMPLESTATE_ARRIVE;
					mb_drawstep[i] = 0;
				}
				else
					mn_examPos[i] = 0;
				break;
				
			case EXAMPLESTATE_ARRIVE :
				//System.out.println("exam " + i+" drawstep:" + mb_drawstep[i]);
				switch(mb_drawstep[i]) {
				case 0 :
					mn_examPos[i] = 8;
					break;
				case 1 :
					mn_examPos[i] = 1;
					break;
				case 2 :
					mn_examPos[i] = -3;
					break;
				case 3 :
					mn_examPos[i] = -2;
					break;
				case 4 :
					mn_examPos[i] = 0;
					mb_examState[i] = EXAMPLESTATE_READY;
					mb_drawstep[i] = 0;
					break;
				}
				
				mb_drawstep[i]++;
				break;
				
			case EXAMPLESTATE_READY :
				break;
				
			case EXAMPLESTATE_SHAKE :
				if(mn_examPos[i] == 0)
					mn_examPos[i] -= 10;
				else if(mn_examPos[i] < 0)
					mn_examPos[i] += 15;
				else {
					mn_examPos[i] -= 5;
					if(mb_correct) {
						for(int j=0; j<4; j++) {
							if(j == mb_ansId-1 )
								mb_examState[j] = EXAMPLESTATE_READY;
							else
								mb_examState[j] = EXAMPLESTATE_DISAPPEAR;
						}
						break;
					}
					else
						mb_examState[i] = EXAMPLESTATE_READY;
				}
				break;
				
			case EXAMPLESTATE_DISAPPEAR :
				if(mb_boomAniId < 5)
					mb_boomAniId++;
				break;
			}
		}

		//SceneManager.getInstance().getRandomInt(0, 3);
	}
	
	public void MakeExamOrder() {
		int idlist[];
		int idSltList[];
		int sltId;
		int idLen;
		
		idlist = new int[4];
		for(int i=0; i<4; i++) {
			idlist[i] = i;
		}
		idLen = 4;
		
		idSltList = new int[4];
		for(int i=0; i<4; i++) {
			idSltList[i] = 0;
		}
		
		sltId = SceneManager.getInstance().getRandomInt(0, idLen-1);
		
		idSltList[0] = idlist[sltId];
		if(sltId != idLen-1) {
			for(int i=sltId; i<idLen-1; i++) {
				idlist[i] = idlist[i+1];
			}
		}
		idLen--;
		
		sltId = SceneManager.getInstance().getRandomInt(0, idLen-1);
		
		idSltList[1] = idlist[sltId];
		if(sltId != idLen-1) {
			for(int i=sltId; i<idLen-1; i++) {
				idlist[i] = idlist[i+1];
			}
		}
		idLen--;
		
		sltId = SceneManager.getInstance().getRandomInt(0, idLen-1);
		
		idSltList[2] = idlist[sltId];
		if(sltId != idLen-1) {
			for(int i=sltId; i<idLen-1; i++) {
				idlist[i] = idlist[i+1];
			}
		}
		idLen--;
		
		idSltList[3] = idlist[0];
		
		mn_examPos[0] = -270-(270/2)*(idSltList[0]);
		mn_examPos[1] = -270-(270/2)*(idSltList[1]);
		mn_examPos[2] = -270-(270/2)*(idSltList[2]);
		mn_examPos[3] = -270-(270/2)*(idSltList[3]);
		
		for(int i=0; i<4; i++)
			mb_examState[i] = EXAMPLESTATE_APPEAR;
		
		//System.out.println("idSltList[0] : "+idSltList[0]);
		//System.out.println("idSltList[1] : "+idSltList[1]);
		//System.out.println("idSltList[2] : "+idSltList[2]);
		//System.out.println("idSltList[3] : "+idSltList[3]);
		
		//System.out.println("mn_examPos[0] : "+mn_examPos[0]);
		//System.out.println("mn_examPos[1] : "+mn_examPos[1]);
		//System.out.println("mn_examPos[2] : "+mn_examPos[2]);
		//System.out.println("mn_examPos[3] : "+mn_examPos[3]);
	}
	
	public void DrawArrow(Graphics g) {
		g.drawImage(img_Arrow[mb_arrId], 613+mn_arrowPos[mb_arrId][0], 365-mn_arrowPos[mb_arrId][1], null);
		//System.out.println("DrawArrow mb_arrId : "+mb_arrId);
	}
	
	public void ProcArrow(int elapsedtime) {
		if(ball.state == Ball.ACT_READY) {
			mn_arrowTime += elapsedtime;
			if(mn_arrowTime > 50) {
				mn_arrowTime = 0;
				if(mb_arrDir == ARROWDIRECTION_UP) {
					mb_arrId++;
					if(mb_arrId >= 33) {
						mb_arrId = 33;
						mb_arrDir = ARROWDIRECTION_DOWN;
					}
					/*
					if(mb_arrId >= 41) {
						mb_arrId = 41;
						mb_arrDir = ARROWDIRECTION_DOWN;
					}
					*/
				}
				else if(mb_arrDir == ARROWDIRECTION_DOWN) {
					mb_arrId--;
					if(mb_arrId <= 8) {
						mb_arrId = 8;
						mb_arrDir = ARROWDIRECTION_UP;
					}
					/*
					if(mb_arrId <= 0) {
						mb_arrId = 0;
						mb_arrDir = ARROWDIRECTION_UP;
					}
					*/
				}
			}
		}
	}
	
	public byte checkCollision() {
		byte colId[];
		int colId_id;
		int examPosC[][];
		int checkLen;
		int dist[];
		
		colId = new byte[3];
		colId[0] = colId[1] = colId[2] = 0;
		colId_id = 0;
		
		dist = new int[4];
		
		examPosC = new int[4][2];
		for (int i=0; i<4; i++) {
			examPosC[i][0] = 160+65+11; // 이미지의 모양에 맞춰 중심에서 우,하단으로 이동
			examPosC[i][1] = 74+40+101*i+3;
			dist[i] = 0;
		}

		checkLen = 32+20;//50+20;
		
		for(int i=0; i<4; i++) {
			/*
			System.out.println("x distance : "+ ((ball.x+20 - examPosC[i][0])^2));
			System.out.println("x distance : "+ ((ball.x+20 - examPosC[i][0])*(ball.x+20 - examPosC[i][0])));
			System.out.println("y distance : "+ ((ball.y+20 - examPosC[i][1])^2));
			*/
			//System.out.println("distance : " + i + " = " + Math.sqrt((ball.x+20 - examPosC[i][0])*(ball.x+20 - examPosC[i][0]) + (ball.y+20 - examPosC[i][1])*(ball.y+20 - examPosC[i][1])));

			dist[i] = (int) Math.sqrt((ball.x+20 - examPosC[i][0])*(ball.x+20 - examPosC[i][0]) + (ball.y+20 - examPosC[i][1])*(ball.y+20 - examPosC[i][1]));
			
			if(checkLen > dist[i]) {
				colId[colId_id] = (byte)i;
				colId_id++;
				
				//System.out.println("colId_id : " + colId_id);
			}
		}
		
		if(colId_id > 0) {	// 충돌, 가장 근접한 충돌을 선택
			switch(colId_id) {
			case 1 :
				//System.out.println("dist[colId[0]] : " + dist[colId[0]]);
				return (byte) (colId[0]+1);
			case 2 :
				//System.out.println("dist[colId[0]] : " + dist[colId[0]]);
				//System.out.println("dist[colId[1]] : " + dist[colId[1]]);
				if(dist[colId[0]] > dist[colId[1]])
					return (byte) (colId[1]+1);
				else
					return (byte) (colId[0]+1);
			case 3 :
				//System.out.println("dist[colId[0]] : " + dist[colId[0]]);
				//System.out.println("dist[colId[1]] : " + dist[colId[1]]);
				//System.out.println("dist[colId[1]] : " + dist[colId[2]]);
				if(dist[colId[0]] > dist[colId[1]]) {
					if(dist[colId[1]] > dist[colId[2]])
						return (byte) (colId[2]+1);
					else
						return (byte) (colId[1]+1);
				}
				else if(dist[colId[0]] > dist[colId[2]]) {
					return (byte) (colId[2]+1);
				}
				else
					return (byte) (colId[0]+1);
				
			default :
				return 0;
			}
		}
		else if(ball.x+40 < 0 || ball.x > 960 || ball.y > 540) {
			return 5;	// 아무것도 못 맞추고 지나간 경우
		}
		else {
			return 0;
		}
	}
	
	public void MakeQuestionInit() {
		mn_state = STATE_PLAY_EXAMPLEAPPEAR;
		mn_aniTime1 = 0;
		mn_qCnt++;
		//mb_arrDir = ARROWDIRECTION_UP;
		mb_userSltId = 0;
		ball.init();
		
		if(mn_qCnt < 11)
			mn_slvTime = 15000;
		else if(mn_qCnt < 21)
			mn_slvTime = 14000;
		else if(mn_qCnt < 31)
			mn_slvTime = 13000;
		else if(mn_qCnt < 41)
			mn_slvTime = 12000;
		else if(mn_qCnt < 51)
			mn_slvTime = 11000;
		else if(mn_qCnt < 61)
			mn_slvTime = 10000;
		else if(mn_qCnt < 71)
			mn_slvTime = 9000;
		else if(mn_qCnt < 81)
			mn_slvTime = 8000;
		else if(mn_qCnt < 91)
			mn_slvTime = 7000;
		else if(mn_qCnt < 101)
			mn_slvTime = 6000;
		else if(mn_qCnt < 111)
			mn_slvTime = 5000;
		else if(mn_qCnt < 121)
			mn_slvTime = 4200;
		else
			mn_slvTime = 3500;
		
		mb_correct = false;
		
		MakeQuestion();
		MakeExample();
	}
	
	public void procOX() {
		if(mn_OXaniStep < 8)
			mn_OXaniStep++;
		switch(mn_OXaniStep) {
		case 2 :
			mn_OXposX -= 8;
			mn_OXposY -= 23;
			break;
		case 3 :
			mn_OXposX -= 11;
			mn_OXposY -= 6;
			break;
		case 4 :
			mn_OXposX -= 13;
			mn_OXposY += 9;
			break;
		case 5 :
			mn_OXposX -= 15;
			mn_OXposY += 29;
			break;
		case 6 :
			mn_OXposX -= 22;
			mn_OXposY += 35;
			break;
		}
	}
	
	public void drawOX(Graphics g, int type) {
		if(mn_OXaniStep < 7) {
			if(type == 0)
				g.drawImage(img_OX_O, mn_OXposX, mn_OXposY, null);
			else
				g.drawImage(img_OX_X, mn_OXposX, mn_OXposY, null);
		}
	}
	
	public void addScore(int score) {
		mn_Score += score;
		if(mn_Score > 99999999) {	// 여덟자리 까지만
			mn_Score = 99999999;
		}
		
		//mn_Score = 99999999;
		
		//System.out.println("mn_Score : " + mn_Score);
	}
	
	public void drawMinus1(Graphics g) {
		switch(mb_minus1AniId) {
		case 1:
			g.drawImage(img_minus1, 621, 462, null);
			break;
		case 2 :
			g.drawImage(img_minus1, 621, 451, null);
			break;
		case 3 :
			g.drawImage(img_minus1, 621, 444, null);
			break;
		case 4 :
			g.drawImage(img_minus1, 621, 439, null);
			break;
		case 5 :
			g.drawImage(img_minus1, 621, 438, null);
			break;
		case 6 :
			g.drawImage(img_minus1, 621, 437, null);
			break;
		case 7 :
			g.drawImage(img_minus1, 621, 436, null);
			break;
		}
	}
	
	public void drawRsltPop(Graphics g) {
		g.drawImage(img_rsltPop, 280, 116, null);
		
		if(mb_rsltCur == 0) {
			g.drawImage(img_btnRe[0], 314, 326, null);
			g.drawImage(img_btnFin[1], 491, 326, null);
		}
		else {
			g.drawImage(img_btnRe[1], 314, 326, null);
			g.drawImage(img_btnFin[0], 491, 326, null);
		}

		switch(mb_rsltStep) {
		case 0 :
			g.drawImage(img_rank[mb_rsltRank], 317, 200-6, null);
			break;
		case 1 :
			g.drawImage(img_rank[mb_rsltRank], 317, 200+2-6, null);
			break;
		case 2 :
			g.drawImage(img_rank[mb_rsltRank], 317, 200+2+2-6, null);
			break;
		case 3 :
			g.drawImage(img_rank[mb_rsltRank], 317, 200+2+2+2-6, null);
			break;
		case 4 :
			g.drawImage(img_rank[mb_rsltRank], 317, 200+2+2-6, null);
			break;
		case 5 :
			g.drawImage(img_rank[mb_rsltRank], 317, 200+2-6, null);
			break;
		}
		
		// 점수
		//System.out.println("drawRsltPop mn_Score : " + mn_Score);
		g.drawImage(img_no_end[mn_Score%10], 567, 243, null);
		if(mn_Score/10 > 0)
			g.drawImage(img_no_end[(mn_Score/10)%10], 567-26, 243, null);
		if(mn_Score/100 > 0)
			g.drawImage(img_no_end[(mn_Score/100)%10], 567-26*2, 243, null);
		if(mn_Score/1000 > 0)
			g.drawImage(img_no_end[(mn_Score/1000)%10], 567-26*3, 243, null);
		if(mn_Score/10000 > 0)
			g.drawImage(img_no_end[(mn_Score/10000)%10], 567-26*4, 243, null);
		if(mn_Score/100000 > 0)
			g.drawImage(img_no_end[(mn_Score/100000)%10], 567-26*5, 243, null);
		if(mn_Score/1000000 > 0)
			g.drawImage(img_no_end[(mn_Score/1000000)%10], 567-26*6, 243, null);
		if(mn_Score/10000000 > 0)
			g.drawImage(img_no_end[mn_Score/10000000], 567-26*7, 243, null);
	}
	
	public void drawEndPop(Graphics g) {
		g.setColor(new DVBColor(0,0,0,80));
//		g.fillRect(0,0,960,540);  //메모리
		
		g.drawImage(img_endPop, 304, 123, null);
		
		if(mb_endCur == 0) {
			g.drawImage(img_btnYes[0], 356, 325, null);
			g.drawImage(img_btnNo[1], 477, 325, null);
		}
		else {
			g.drawImage(img_btnYes[1], 356, 325, null);
			g.drawImage(img_btnNo[0], 477, 325, null);
		}
	}
	
	public void draw_loading(Graphics g) {
		int frame = mn_LFrame;
		if(frame > 9)
			frame = 9;
		g.drawImage( img_load[0], 480-287, 464, null);
		g.drawImage(img_load[1], 480-287, 464, 480-287+(574/10)*frame, 464+38, 0, 0, (574/10)*frame, 38, null);
	}
	
	public void drawBallDisappear(Graphics g) {
		switch(mb_effectStep) {
		case 0 :
			g.drawImage(img_Effect[0], 660, 357, null);
			g.drawImage(img_Effect[1], 660+20, 357-1, null);
			g.drawImage(img_Effect[2], 660+1, 357+21, null);
			g.drawImage(img_Effect[3], 660+21, 357+21, null);
			break;
		case 1 :
			g.drawImage(img_Effect[0], 660-3, 357-4, null);
			g.drawImage(img_Effect[1], 660+20+3, 357-1-4, null);
			g.drawImage(img_Effect[2], 660+1-4, 357+21+4, null);
			g.drawImage(img_Effect[3], 660+21+4, 357+21+3, null);
			break;
		case 2 :
			g.drawImage(img_Effect[0], 660-3-3, 357-4-2, null);
			g.drawImage(img_Effect[1], 660+20+3+3, 357-1-4-2, null);
			g.drawImage(img_Effect[2], 660+1-4-2, 357+21+4+2, null);
			g.drawImage(img_Effect[3], 660+21+4+2, 357+21+3+2, null);
			break;
		case 3 :
			g.drawImage(img_Effect[0], 660-3-3-1, 357-4-2-1, null);
			g.drawImage(img_Effect[1], 660+20+3+3+1, 357-1-4-2-1, null);
			g.drawImage(img_Effect[2], 660+1-4-2-1, 357+21+4+2+1, null);
			g.drawImage(img_Effect[3], 660+21+4+2+1, 357+21+3+2+1, null);
			break;
		}
	}
	
	public Image loadUrlImage(URL url)
    {
		Image img;
		img = SceneManager.getInstance().getImage(url);
		if(img != null) {
			if(img.getWidth(null) <= 0)
				img_check=true;
		}
		else img_check=true;
        return img;
    }
}
