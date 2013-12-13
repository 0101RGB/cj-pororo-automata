package pororo.com.game;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.dvb.ui.DVBColor;
import org.havi.ui.event.HRcEvent;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;
import pororo.com.framework.Sound;

public class GameTitle extends Scene { 
	
	public final static int STATE_TITLE_LODING = 1;
	public final static int STATE_TITLE_MENU = 2;
	public final static int STATE_TITLE_HELP = 3;
	public final static int STATE_TITLE_RANK = 4;
	public final static int STATE_TITLE_ERROR= 5;
	public final static int STATE_TITLE_ERROR1= 6;
	
	public boolean img_check=false;
	// title image
	public Image img_LR[];
	public Image img_start[];
	public Image img_help[];
	public Image img_rank[];
	public Image img_exit[];
	public Image img_helpBg;
	public Image img_rankBg;
	
	public int mn_state;
	public int mn_curAniVal;
	public int mn_aniTime;
	public int mn_frame;
	public int mn_nextState;
	public boolean mb_getRank;
	
	public String ms_rankData;
	public String ms_ID[];
	public String ms_score[];
	public String ms_myRankID;
	public String ms_myScore;
	public String ms_myRank;
	public String ms_myBestRankID;
	public String ms_myBestScore;
	public String ms_myBestRank;
	
	public Font myFont;

	public Sound m_sound;
	
	public GameTitle() {
		
		//System.out.println("GameTitle start");
		mn_state = STATE_TITLE_LODING;
		img_check=false;
		mn_curAniVal = 0;
		mn_aniTime = 0;
		mn_frame =0;
		mn_nextState = -1;
		mb_getRank = false;
		
		ms_ID = new String[10];
		ms_score = new String[10];
		m_sound = new Sound(4);
		
	}
	
	public GameTitle(int nextState) {
		mn_state = STATE_TITLE_LODING;
		mn_curAniVal = 0;
		mn_aniTime = 0;
		mn_frame =0;
		mn_nextState = nextState;
		
		ms_ID = new String[10];
		ms_score = new String[10];
		m_sound = new Sound(4);
		
	}
	
	public void draw(Graphics2D g) {
		if(mn_state == STATE_TITLE_LODING)
			return;
		
		g.drawImage(img_rank[0], 217,464, null);
		g.drawImage(img_start[0], 345,464, null);
		g.drawImage(img_help[0], 473,464, null);
		g.drawImage(img_exit[0], 602,464, null);
		
		switch (mn_state) {
		case STATE_TITLE_MENU :			
		
			switch(SceneManager.getInstance().mn_gMenuCur) {
			case 0 :
				g.drawImage(img_LR[0], 217-20-mn_curAniVal,471, null);
				g.drawImage(img_LR[1], 217+102+2+mn_curAniVal,471, null);
				g.drawImage(img_rank[1], 217,464, null);
				break;
			case 1 :
				g.drawImage(img_LR[0], 345-20-mn_curAniVal,471, null);
				g.drawImage(img_LR[1], 345+102+2+mn_curAniVal,471, null);
				g.drawImage(img_start[1], 345,464, null);
				break;
			case 2 :
				g.drawImage(img_LR[0], 473-20-mn_curAniVal,471, null);
				g.drawImage(img_LR[1], 473+102+2+mn_curAniVal,471, null);
				g.drawImage(img_help[1], 473,464, null);
				break;
			case 3 :
				g.drawImage(img_LR[0], 602-20-mn_curAniVal,471, null);
				g.drawImage(img_LR[1], 602+140+2+mn_curAniVal,471, null);
				g.drawImage(img_exit[1], 602,464, null);
				break;			
			}
			break;
			
		case STATE_TITLE_HELP :
			draw_bgalpha(g);
			if(img_helpBg != null) {
				g.drawImage(img_helpBg, 171,54, null);
			}
			break;
			
		case STATE_TITLE_RANK :
			draw_bgalpha(g);
			g.drawImage(img_rankBg, 171,54, null);
			if(mb_getRank) {
				g.setColor(new Color(255, 255, 255));
				myFont = new Font("Bold", 0, 19);
				g.setFont(myFont);
	            
				//g.drawString(SceneManager.getInstance().GameID, 250, 120);
				if(ms_myRankID != null) {
					if (ms_myRank.length() <= 2) {
						g.drawString(ms_myRank, 518, 390-14);	
					}
					else {
						g.drawString(ms_myRank, (554-12)-(ms_myRank.length()*9), 390-14);
					}
					g.drawString(ms_myRankID, 566-9, 390-14);
					g.drawString(ms_myScore, 675+(6-ms_myScore.length())*9, 390-14);
				}
				
				if(ms_myBestRankID != null) {
					if (ms_myBestRank.length() <=2) {
						g.drawString(ms_myBestRank, 246, 390-14);	
					}
					else {
						g.drawString(ms_myBestRank, (282-12)-(ms_myBestRank.length()*9), 390-14);
					}
					g.drawString(ms_myBestRankID, 292-9, 390-14);
					g.drawString(ms_myBestScore, 401+(6-ms_myBestScore.length())*9, 390-14);
				}
				
				for(int i=0; i<5; i++) {
					g.drawString(Integer.toString(i+1), 246, 184+i*35-14);
					if(ms_ID[i] != null) {
						g.drawString(ms_ID[i], 292-9, 184+i*35-14);
						if(ms_score[i] != null)
							g.drawString(ms_score[i], 401+(6-ms_score[i].length())*9, 184+i*35-14);
					}
				}
				
				for(int i=5; i<10; i++) {
					if(i < 9)
						g.drawString(Integer.toString(i+1), 518, 184+(i-5)*35-14);
					else
						g.drawString(Integer.toString(i+1), 513, 184+(i-5)*35-14);
					if(ms_ID[i] != null) {
						g.drawString(ms_ID[i], 566-9, 184+(i-5)*35-14);
						if(ms_score[i] != null)
							g.drawString(ms_score[i], 675+(6-ms_score[i].length())*9, 184+(i-5)*35-14);
					}
				}
			}
			break;
		case STATE_TITLE_ERROR:
		case STATE_TITLE_ERROR1:	
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
	}

	public void process(int elapsedtime) {
		
		mn_aniTime += elapsedtime;
		if(mn_state == STATE_TITLE_LODING)
		{
			mn_frame++;
			if(mn_frame ==1){
				// 메뉴에서 진입할때 한번만 전송하도록 하였음.
				if (SceneManager.sendAccounting) {
					new Thread() {
						public void run() {
							SceneManager.getInstance().sendaccouting_gravity(); // HttpConnection time-out 문제 때문에 스레드로 뺌!		
						}
					}.start();
					SceneManager.sendAccounting = false;
				}
			}
			loadRes();
		}
		else {
			if(mn_aniTime < 150)
				mn_curAniVal += 1;
			else if (mn_aniTime < 300) {
				mn_curAniVal -= 1;
				if(mn_curAniVal < 0)
					mn_curAniVal = 0;
			}
			else {
				mn_aniTime = 0;
				mn_curAniVal = 0;
			}
		}
		
		
	}
	
	public void processKey(Object nn, int key) {
//		System.out.println("GameTitle processKey mn_state : " + mn_state +", key :"+ key);
		
		switch (mn_state) {
		case STATE_TITLE_MENU :
			switch (key) {
			case HRcEvent.VK_ENTER:
				//System.out.println("soccerGTitle processKey : VK_ENTER");
				switch(SceneManager.getInstance().mn_gMenuCur) {
				case 0 :
					getRankData();
					if(SceneManager.getInstance().sever_msg ==0 ){
						mn_state = STATE_TITLE_RANK;
					}
					else {
						mn_state = STATE_TITLE_ERROR1;
					}
					SceneManager.getInstance().sendPV();
					break;
					
				case 1 :
					// 선택한 게임으로 이동하게 세팅
					if ( SceneManager.getInstance().chkGameState ) {
						SceneManager.getInstance().send_gravity(10);
						SceneManager.getInstance().chkGameState = false;
					}
					if(SceneManager.getInstance().GameState == SceneManager.STATE_BOARD)
						SceneManager.getInstance().setChangeScene(SceneManager.SATAE_BOARDGAMEPLAY);
					else if(SceneManager.getInstance().GameState == SceneManager.STATE_SOCCER)
						SceneManager.getInstance().setChangeScene(SceneManager.SATAE_SOCCERGAMEPLAY);
					else if(SceneManager.getInstance().GameState == SceneManager.STATE_FISHING)
						SceneManager.getInstance().setChangeScene(SceneManager.SATAE_FISHINGGAMEPLAY);
					else if(SceneManager.getInstance().GameState == SceneManager.STATE_MAZE)
						SceneManager.getInstance().setChangeScene(SceneManager.SATAE_MAZEGAMEPLAY);
					else if(SceneManager.getInstance().GameState == SceneManager.STATE_PUZZLE)
						SceneManager.getInstance().setChangeScene(SceneManager.SATAE_PUZZLEGAMEPLAY);
					break;
					
				case 2 :
					mn_state = STATE_TITLE_HELP;
					SceneManager.getInstance().sendPV();
					break;
					
				case 3 :
					SceneManager.getInstance().load_kind=true;
					SceneManager.getInstance().setChangeScene(SceneManager.SATAE_PORTAL_MENU);
					break;
				}
				break;
				
			case HRcEvent.VK_ESCAPE:
				//System.out.println("userEventReceived() : VK_ESCAPE");
				//SceneManager.getInstance().setChangeScene(SceneManager.getInstance().SATAE_MENU);
				break;
				
			case HRcEvent.VK_LEFT:
				//System.out.println("userEventReceived() : VK_LEFT");
				SceneManager.getInstance().mn_gMenuCur--;
				if(SceneManager.getInstance().mn_gMenuCur < 0)
					SceneManager.getInstance().mn_gMenuCur = 3;
				//if(SceneManager.getInstance().mn_gMenuCur != 0)
				m_sound.playSound(SceneManager.getInstance().mn_gMenuCur);
					//m_sound.playSound(S_play);
				break;	
				
			case HRcEvent.VK_RIGHT:
				//System.out.println("userEventReceived() : VK_RIGHT");
				SceneManager.getInstance().mn_gMenuCur++;
				//System.out.println("SceneManager.getInstance().mn_gMenuCur : " + SceneManager.getInstance().mn_gMenuCur);
				
				if(SceneManager.getInstance().mn_gMenuCur > 3)
					SceneManager.getInstance().mn_gMenuCur = 0;
				//if(SceneManager.getInstance().mn_gMenuCur != 0)
				m_sound.playSound(SceneManager.getInstance().mn_gMenuCur);
					//m_sound.playSound(S_help);
				break;
			case Constant.KEY_PREV:
				SceneManager.getInstance().load_kind=true;
				SceneManager.getInstance().setChangeScene(SceneManager.SATAE_PORTAL_MENU);
				break;
			}
			break;
			
		case STATE_TITLE_HELP :
			switch (key) {
			case HRcEvent.VK_ENTER:
			case Constant.KEY_PREV:
			//case HRcEvent.VK_ESCAPE:
				mn_state = STATE_TITLE_MENU;
				break;
			}
			break;
			
		case STATE_TITLE_RANK :
			switch (key) {
			case HRcEvent.VK_ENTER:
			case Constant.KEY_PREV:
				mn_state = STATE_TITLE_MENU;
				break;
			}
			break;
		case STATE_TITLE_ERROR:
			switch (key) {
			case HRcEvent.VK_ENTER:
			case Constant.KEY_PREV:
				SceneManager.getInstance().setChangeScene(SceneManager.SATAE_PORTAL_MENU);
				break;
			}
			break;
		case STATE_TITLE_ERROR1:
			switch (key) {
			case HRcEvent.VK_ENTER:
			case Constant.KEY_PREV:
				mn_state = STATE_TITLE_MENU;
				break;
			}
			break;
		}
	}

	public void destroyScene() {
//		if (treeMap != null) {
//			treeMap.clear();
//			treeMap = null;
//		}
		ms_ID = null;
		ms_score = null;
		myFont =null;
		if (m_sound != null) {
			m_sound.destroySound(); 
			m_sound = null;
		}
		
		if (img_LR != null) {
			for(int i=0; i<2; i++) {
				if(img_LR[i] != null){
					SceneManager.getInstance().removeImage(img_LR[i]);
					img_LR[i] =null;
				}							
			}
			img_LR = null;
		}
		
		if (img_start != null) {
			for(int i=0; i<2; i++) {
				if(img_start[i] != null){
					SceneManager.getInstance().removeImage(img_start[i]);
					img_start[i] =null;
				}							
			}
			img_start = null;
		}

		if (img_help != null) {
			for(int i=0; i<2; i++) {
				if(img_help[i] != null){
					SceneManager.getInstance().removeImage(img_help[i]);
					img_help[i] =null;
				}							
			}
			img_help = null;
		}
		
		if (img_rank != null) {
			for(int i=0; i<2; i++) {
				if(img_rank[i] != null){
					SceneManager.getInstance().removeImage(img_rank[i]);
					img_rank[i] =null;
				}							
			}
			img_rank = null;
		}

		if (img_exit != null) {
			for(int i=0; i<2; i++) {
				if(img_exit[i] != null){
					SceneManager.getInstance().removeImage(img_exit[i]);
					img_exit[i] =null;
				}							
			}
			img_exit = null;
		}
		
		if(img_helpBg != null){
			SceneManager.getInstance().removeImage(img_helpBg);
			img_helpBg =null;
		}
		if(img_rankBg !=null){
			SceneManager.getInstance().removeImage(img_rankBg);
			img_rankBg=null;
		}
		
	}

	public void loadRes()
	{
		String url;
//		System.out.println("mn_frame : " + mn_frame);
		
		if(StateValue.isUrlLive) {
			url = StateValue.liveResource + "title/img/";
		}
		else {
			url = StateValue.testResource + "title/img/";
		}
						
		try
		{
			if(mn_frame ==1){
				//System.out.println(" =================mn_frame : " +mn_frame);
				img_LR = new Image[2];
				img_LR[0] = loadUrlImage(new URL(url+"left.png"));
				img_LR[1] = loadUrlImage(new URL(url+"right.png"));
			}
			else if(mn_frame ==2){
				//System.out.println(" =================mn_frame : " +mn_frame);
				img_start = new Image[2];
				img_start[0] =loadUrlImage(new URL(url+"start_off.png"));
				img_start[1] = loadUrlImage(new URL(url+"start_on.png"));
			}
			else if(mn_frame ==3){
				//System.out.println(" =================loadRes : " +mn_frame);
				img_help = new Image[2];
				img_help[0] = loadUrlImage(new URL(url+"how_off.png"));
				img_help[1] = loadUrlImage(new URL(url+"how_on.png"));
			}
			else if(mn_frame ==4){
				//System.out.println(" =================loadRes : " +mn_frame);
				img_rank = new Image[2];
				img_rank[0] = loadUrlImage(new URL(url+"rank_off.png"));
				img_rank[1] = loadUrlImage(new URL(url+"rank_on.png"));
			}
			else if(mn_frame ==5){
				//System.out.println(" =================loadRes : " +mn_frame);
				img_exit = new Image[2];
				img_exit[0] = loadUrlImage(new URL(url+"exit_off.png"));
				img_exit[1] = loadUrlImage(new URL(url+"exit_on.png"));
			}
			else if(mn_frame ==6){
				//System.out.println(" =================loadRes : " +mn_frame);
				img_rankBg = loadUrlImage(new URL(url+"rank.png"));
				img_helpBg = loadUrlImage(new URL(url+"help"+(SceneManager.getInstance().GameState +1)+".png"));
			}
			else if(mn_frame ==7){
				//System.out.println(" =================loadRes : " +mn_frame);
			//	Sound.SetSound(3);
			//	loadImage("img/popup/text_select"+i+".png");
				if(m_sound != null) {
					
					if(StateValue.isUrlLive) {
						url = StateValue.liveResource + "title/snd/";
					}
					else {
						url = StateValue.testResource + "title/snd/";
					}
					
					m_sound.loadSound("snd/menumove.mp2");
					//m_sound.loadSound(new URL(url+"rank.mp2"));
					m_sound.loadSound(new URL(url+"start.mp2"));
					m_sound.loadSound(new URL(url+"how.mp2"));
					m_sound.loadSound(new URL(url+"exit.mp2"));
					for(int i=0; i<m_sound.mn_sndId; i++){
						if(m_sound.m_player[i] == null)
							img_check=true;
					}
				}
				else{
					img_check=true;
					//System.out.println("Sound is null");
				}
			}
			else if(mn_frame ==8){
				//System.out.println(" =================loadRes : " +mn_frame);
				if(! img_check)
				{
					if(StateValue.isUrlLive) {
						url = StateValue.liveResource + "bg/";
					}
					else {
						url = StateValue.testResource + "bg/";
					}
					
					try {
						// 게임 배경 이미지 로딩
						if(SceneManager.getInstance().GameState == SceneManager.STATE_BOARD){
							if(!SceneManager.getInstance().loadBackgroundImage(new URL(url+"board/ttl_bg.gif")))
								img_check=true;
						}	
						else if(SceneManager.getInstance().GameState == SceneManager.STATE_SOCCER){
							if(!SceneManager.getInstance().loadBackgroundImage(new URL(url+"soccer/ttl_bg.gif")))
								img_check=true;
						}
						else if(SceneManager.getInstance().GameState == SceneManager.STATE_FISHING){
							if(!SceneManager.getInstance().loadBackgroundImage(new URL(url+"fishing/ttl_bg.gif")))
								img_check=true;
						}
						else if(SceneManager.getInstance().GameState == SceneManager.STATE_MAZE){
							if(!SceneManager.getInstance().loadBackgroundImage(new URL(url+"maze/ttl_bg.gif")))
								img_check=true;
						}
						else if(SceneManager.getInstance().GameState == SceneManager.STATE_PUZZLE){
							if(!SceneManager.getInstance().loadBackgroundImage(new URL(url+"puzzle/ttl_bg.gif")))
								img_check=true;
						}
							
					} catch (MalformedURLException e) {
						img_check=true;
						e.printStackTrace();
					}
					mn_aniTime = 0;
					mn_curAniVal =0;
					mn_frame=0;
					if(!img_check) {
						if(mn_nextState > 0) {
							if(mn_nextState == STATE_TITLE_RANK)
								getRankData();
							if(SceneManager.getInstance().sever_msg ==0 ){
								mn_state = mn_nextState;
							}
							else mn_state = STATE_TITLE_ERROR1;
						}
						else
							mn_state = STATE_TITLE_MENU;
					}
					else {
						mn_state = STATE_TITLE_ERROR;
					}
				}
				else {
					mn_state = STATE_TITLE_ERROR;
				}
				SceneManager.getInstance().load_kind = false;
			}
			
			/*
			img_LR[0] = loadImage(url+"left.png");
			img_LR[1] = loadImage(url+"right.png");
			img_start[0] = loadImage(url+"start_off.png");
			img_start[1] =loadImage(url+"start_on.png");
			img_help[0] = loadImage(url+"how_off.png");
			img_help[1] = loadImage(url+"how_on.png");
			img_rank[0] = loadImage(url+"rank_off.png");
			img_rank[1] = loadImage(url+"rank_on.png");
			img_exit[0] = loadImage(url+"exit_off.png");
			img_exit[1] = loadImage(url+"exit_on.png");
			img_rankBg =loadImage(url+"rank.png");
			img_helpBg = loadImage(url+"help"+SceneManager.getInstance().GameState+".png");
			*/
					
		}
		catch (Exception e)
		{
			img_check=true;
			e.printStackTrace();
			//System.out.println("title imgae not  load---------------------------------------------="+mn_aniTime);
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
	
	public Image loadImage(String s)
    {
        return SceneManager.getInstance().getImage( s);
    }
	
	public void getRankData() {
		// 랭킹 받아 오기
		mb_getRank = false;
		ms_rankData = new String(SceneManager.getInstance().getRank_gravity());
		
		//System.out.println("Game title rank data : " + ms_rankData);
		
		////System.out.println("ms_rankData.substring(7, 8) : " + ms_rankData.substring(7, 8));
		if(SceneManager.getInstance().sever_msg ==0 ){
			if(ms_rankData.substring(7, 8).equals("1")) {	// get Rank Data success
				int n, m, l, k, i, j = 0;
	
				// get my best rank
				for (i = 19; i < ms_rankData.length(); i++) {
					if (ms_rankData.charAt(i) == '|') {
						ms_myRankID = ms_rankData.substring(19, i);
						
//						ms_myRankID = SceneManager.getInstance().IDtoDecode(ms_myRankID, 1);
						ms_myRankID = SceneManager.GameID;
	
						/*
						try {
							ms_myRankID = URLDecoder.decode(ms_rankData.substring(19, i), "UTF-8");
						} catch (Exception e) {
							e.printStackTrace();
						}
						*/
						
						for (j = i + 1; j < ms_rankData.length(); j++) {
							if (ms_rankData.charAt(j) == '|') {
								ms_myScore = ms_rankData.substring(i+1, j);
								break;
							}
						}
						
						for (i = j + 1; i < ms_rankData.length(); i++) {
							if (ms_rankData.charAt(i) == '&') {
								ms_myRank = ms_rankData.substring(j+1, i);
								break;
							}
						}
						break;
					}
					else if (ms_rankData.charAt(i) == '&') {
						break;					
					}
				}
				
				////System.out.println("ms_myRankID : " + ms_myRankID);
				//System.out.println("ms_myScore : " + ms_myScore);
				//System.out.println("ms_myRank : " + ms_myRank);
				
				// get rank list
				n = i+1;
				for(k=0; k<10; k++) {
					for (l = n+1; l < ms_rankData.length(); l++) {
						if (ms_rankData.charAt(l) == '=') {
							for (m = l+1; m < ms_rankData.length(); m++) {
								if (ms_rankData.charAt(m) == '|') {
									ms_ID[k] = ms_rankData.substring(l+1, m);
									
									
									ms_ID[k] = SceneManager.getInstance().IDtoDecode(ms_ID[k], 0);
									
									
									for (n = m + 1; n < ms_rankData.length(); n++) {
										if (ms_rankData.charAt(n) == '&') {
											ms_score[k] = ms_rankData.substring(m+1, n);
											break;
										}
									}
									break;
								}
								else if (ms_rankData.charAt(m) == '&') {
									n = m+1;
									break;
								}
							}
							break;
						}
					}
					
					//System.out.println("ms_ID["+k+"] : " + ms_ID[k]);
					//System.out.println("ms_score["+k+"] : " + ms_score[k]);
				}
				
				/* 2012.05.18 YJY 클라이언트에서 점수정렬하는 코드 추가 (서버처리로 필요없어짐)*/
				/*
				treeMap.clear();
				int loopLen = ms_ID.length;  // ms_ID 배열 크기는 반드시 10 이어야 함.
				for (int z=0; z<loopLen; z++) {
					if (ms_ID[z] != null) {
						if (ms_score[z] != null) {
							treeMap.put(Integer.valueOf(ms_score[z].trim()), ms_ID[z]);
						}
					}
				}
				Integer key = null;
				String value = null;
				int arrIdx = 0;
				for (Iterator it = treeMap.keySet().iterator(); it.hasNext(); ) {
					key = (Integer) it.next();
					ms_score[arrIdx] = key.toString();
					value = (String) treeMap.get(key);
					ms_ID[arrIdx] = value;
					arrIdx++;
				}
				*/
				
				// get my rank
				for (i = n+1; i < ms_rankData.length(); i++) {
					if (ms_rankData.charAt(i) == '=') {
						for (j = i+1; j < ms_rankData.length(); j++) {
							if (ms_rankData.charAt(j) == '|') {
								ms_myBestRankID = ms_rankData.substring(i+1, j);
								
//								ms_myBestRankID = SceneManager.getInstance().IDtoDecode(ms_myBestRankID, 1);
								ms_myBestRankID = SceneManager.GameID;
									
								for (i = j + 1; i < ms_rankData.length(); i++) {
									if (ms_rankData.charAt(i) == '|') {
										ms_myBestScore = ms_rankData.substring(j+1, i);
										break;
									}
								}
								
								ms_myBestRank = ms_rankData.substring(i+1, ms_rankData.length());
								break;
							}
						}
						break;
					}
				}
	
			//	System.out.println("ms_myBestRankID : " + ms_myBestRankID);
			//	System.out.println("ms_myBestScore : " + ms_myBestScore);
			//	System.out.println("ms_myBestRank : " + ms_myBestRank);		
				
				mb_getRank = true;
			}
		}
	}
	
	public void draw_bgalpha(Graphics g) {
		g.setColor(new DVBColor(0,0,0,80));
		g.fillRect(0,0,960,540);
	}
	
	// Nested class..
	/*
	 * 리버스 정렬용 클래스 (서버에서 정렬을 수정하여 필요없어졌음..)
	public static class ReverseCompare implements Comparator {		
		public int compare(Object a, Object b) {
			int asc = ((Integer) a).compareTo((Integer) b);
			if (asc == Integer.MIN_VALUE) {
				return Integer.MAX_VALUE;
			}
			else {
				return -asc;
			}
		}
	}
	*/
}
