package pororo.com.game.remocon;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import org.dvb.ui.DVBColor;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;

public class RemotePlay extends Scene  {
	
	private Snds snd;
	
	private byte mode = -1;
	
	// game mode
	private final byte MODE_TITLE 				= 0;
	private final byte MODE_GAMEPLAY		= 1;
	private final byte MODE_ENDPOPUP		= 2;
	
	// game set
	int stage = 0;
	int titleFocus = 0;
	int gameFocus = 0;
	int aniCnts = 0;
	int aniX = 0;
	int aniY = 0;
	
	int aniMain;
	
	int aniCnt;	//cursor animation
	int loadingCnt = 0;
	
	boolean loading;		//iframe loading
	
	
	
	// image index
	public static final int IMG_BUTTON_01			= 0;
	public static final int IMG_BUTTON_02			= IMG_BUTTON_01 +1;
	public static final int IMG_BUTTON_A1			= IMG_BUTTON_02 +1;
	public static final int IMG_BUTTON_A2			= IMG_BUTTON_A1 +1;
	public static final int IMG_BUTTON_B1			= IMG_BUTTON_A2 +1;
	public static final int IMG_BUTTON_B2			= IMG_BUTTON_B1 +1;
	public static final int IMG_CURSORARROW	= IMG_BUTTON_B2 +1;
	public static final int IMG_CURSOR_1				= IMG_CURSORARROW +1;
	public static final int IMG_CURSOR_2				= IMG_CURSOR_1 +1;
	public static final int IMG_ENDPOP_2				= IMG_CURSOR_2 +1;
	public static final int IMG_REMOTE_EF			= IMG_ENDPOP_2 +1;
	// image max count
	public static final int IMG_MAX_COUNT 						=  IMG_REMOTE_EF +1;
	
	public String path_imgGame [] = {
			"img/bt_01.png",
			"img/bt_02.png",
			"img/bt_a1.png",
			"img/bt_a2.png",
			"img/bt_b1.png",
			"img/bt_b2.png",
			"img/cs.png",
			"img/cs1.png",
			"img/cs2.png",
			"img/endpop2.png",
			"img/remote_ef.png"
	};
	
	Image imgLoad [] = new Image[2];
	
	
	Image imgGame [] = null;
	
	public RemotePlay() { 
		
		init();
		System.out.println("remotes play initialized");
	}
	
	private void init() {
		try {
			String url = StateValue.liveResource + "handphone/";
			SceneManager.getInstance().loadBackgroundImage( new URL( url + "bg/remote_title.gif" ));
//			if(!SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.liveResource + "bg/board/game_bg.gif")))
			
			imgLoad[0] = SceneManager.getInstance().getImage( new URL(url + "img/load1.png"));
			imgLoad[1] = SceneManager.getInstance().getImage( new URL(url + "img/load2.png"));
			
		} catch (MalformedURLException e) { e.printStackTrace(); }
		SceneManager.getInstance().load_kind = false;
		
		aniMain = 0;
		snd = new Snds();
		snd.loadPlaySound("title/none.mp2");
		loadRes();
	}
	
	public void draw(Graphics2D g) {
		
		if (mode == -1) {
			g.drawImage(imgLoad[1], 495, 402, null);
			g.drawImage(imgLoad[0], 501, 408, 501+loadingCnt*10, 448, 0, 0, loadingCnt*10, 40, null);
		}
		if(loading){
		}else{
			switch ( mode ) {
			case MODE_TITLE:
				drawTitle(g);
				break;
				
			case MODE_GAMEPLAY:
				drawKeyPad(g);
				drawEffect(g);
				break;
				
			case MODE_ENDPOPUP:
				drawKeyPad(g);
				drawPopup_End(g);
				break;
			}
		}
		
	}
	
	int mn_loadTime = 0;
	
	public void loadRes() {
		
		new Thread(){
			public void run() {
				imgGame =  new Image[IMG_MAX_COUNT];
				
				while (true) {
					try {
						if ( mn_loadTime >=  IMG_MAX_COUNT ) { // 로딩 끝나면
							mode = MODE_TITLE;
							break;
						} else {
							try {
								imgGame[mn_loadTime] = SceneManager.getInstance().getImage(
										new URL( StateValue.liveResource + "handphone/" + path_imgGame[mn_loadTime] ));
								loadingCnt = mn_loadTime;
								if ( mn_loadTime%7 == 0 ) SceneManager.getInstance().repaint();
							} catch (MalformedURLException e) { e.printStackTrace(); }
							mn_loadTime++;
							if ( mn_loadTime == (IMG_MAX_COUNT-1) ) {
								SceneManager.getInstance().load_kind = false;
							}
						}
						
					} catch (Exception e1) {	e1.printStackTrace();	}
				}
			}
		}.start();
		
	}
	
	void drawKeyPad(Graphics2D g) {
		int x = 373;
		int y = 106;
		int index = 0;
		
		for (int i = 0; i < 4; i++) {
			y = y + (i*72);
			for (int j = 0; j < 3; j++) {
				x =  x + (j*89);
				if ( index == gameFocus && index <= 11 ) {
					
					drawCover(g, x -16, y -13, 0);
					g.drawImage(imgGame[IMG_BUTTON_A2],
							x , y ,x + 58 , y + 56 , 58*deployDrawKey(index) , 0 , 58*(deployDrawKey(index)+1) , 56 , null  );
					
				} else {
					g.drawImage(imgGame[IMG_BUTTON_A1],
							x , y ,x + 58 , y + 56 , 58*deployDrawKey(index) , 0 , 58*(deployDrawKey(index)+1) , 56 , null  );
				}
				index++;
				x = 373;
			}
			y = 106;
		}
		
		if ( gameFocus == 12 ) {
			drawCover(g, 378 -18, 394 -15, 1);
			g.drawImage(imgGame[IMG_BUTTON_B2],378 , 394 , 378 + 96  , 394 + 40 , 0 , 0 , 96 , 40 , null );
			g.drawImage(imgGame[IMG_BUTTON_B1],508 , 394 , 508 + 96 , 394 + 40 , 96 , 0 , 192 , 40 , null );
		} else if ( gameFocus == 13 ) {
			drawCover(g, 508 -18, 394 -15, 1);
			g.drawImage(imgGame[IMG_BUTTON_B1],378 , 394 , 378 + 96  , 394 + 40 , 0 , 0 , 96 , 40 , null );
			g.drawImage(imgGame[IMG_BUTTON_B2],508 , 394 , 508 + 96 , 394 + 40 , 96 , 0 , 192 , 40 , null );
		} else {
			g.drawImage(imgGame[IMG_BUTTON_B1],378 , 394 , 378 + 96  , 394 + 40 , 0 , 0 , 96 , 40 , null );
			g.drawImage(imgGame[IMG_BUTTON_B1],508 , 394 , 508 + 96 , 394 + 40 , 96 , 0 , 192 , 40 , null );
		}
		
	}
	
	int calcAniXY() {
		int x = 373;
		int y = 106;
		int index = 0;
		
		for (int i = 0; i < 4; i++) {
			y = y + (i*72);
			for (int j = 0; j < 3; j++) {
				x =  x + (j*89);
				if ( index == gameFocus && index <= 11 ) {
					aniX = x;
					aniY = y;
					return 0;
				}
				index++;
				x = 373;
			}
			y = 106;
		}
		
		return 0;
	}
	
	void drawCover( Graphics2D g , int x, int y, int type ) {
		int xx = 0;
		int yy = 0;
		
		if ( type == 0 ) {
			xx = 90;
			yy = 84;
		} else if ( type == 1 ) {
			xx = 132;
			yy = 72;
		}
		
		int aniIndex = aniMain % 6;
//		if ( aniIndex > 4 ) {
//			aniIndex = 2;
//		} else if ( aniIndex > 2 ) {
//			aniIndex = 1;
//		} else {
//			aniIndex = 0;
//		}
		switch(aniIndex){
		case 0:
			aniIndex = 0;
			break;
		case 1:
			aniIndex = 0;
			break;
		case 2:
			aniIndex = 1;
			break;
		case 3:
			aniIndex = 2;
			break;
		case 4:
			aniIndex = 2;
			break;
		case 5:
			aniIndex = 1;
			break;
		}
		
		aniIndex = aniIndex + (stage*3);
		
		if ( type == 0 ) {
			g.drawImage( imgGame[IMG_CURSOR_1], 
					x , y , x + xx , y + yy , xx * aniIndex , 0 , xx * (aniIndex+1) , yy , null );
		} else {
			g.drawImage( imgGame[IMG_CURSOR_2], 
					x , y , x + xx , y + yy , xx * aniIndex , 0 , xx * (aniIndex+1) , yy , null );
		}
		
	}
	
	void drawEffect( Graphics2D g ) {
		if ( aniCnts > 0 ) {
			g.drawImage( imgGame[IMG_REMOTE_EF],
					aniX , aniY , aniX + 77 , aniY + 108 , 77*Math.abs(8-aniCnts) , 0 , 77*((Math.abs(8-aniCnts)) +1) , 108 , null );
			aniCnts--;
		}
	}
	
	void setAniWork() {
		calcAniXY();
		aniX = aniX - 15;
		aniY = aniY - 30;
		aniCnts = 8;
	}
	
	int deployDrawKey( int focus ) {
		int id = 0;
		id = focus;
		if ( focus == 9 ) { 				 id = 10;
		} else if ( focus == 10 ) {  id = 9;
		}
		
		return id;
	}
	
	void drawTitle(Graphics g) {
		
		int tx =  190;
		int ty =  40;
		
		aniCnt = (aniCnt + 1)%2;
		
		if ( titleFocus == 0 ) {
			drawButton(g, 342 + tx, 362 + ty, 0);
			drawButton(g, 522 + tx, 362 + ty, 3);
			g.drawImage(imgGame[IMG_CURSORARROW],
					311 + tx +aniCnt,371 + ty,311 + 28 + tx +aniCnt, 371 +34 + ty , 0, 0, 28, 34, null ); 					
			g.drawImage(imgGame[IMG_CURSORARROW],
					461 + tx -aniCnt,371 + ty,461 + 28 + tx -aniCnt, 371 +34 + ty , 28, 0, 56, 34, null ); 					
		} else {
			drawButton(g, 342 + tx, 362 + ty, 1);
			drawButton(g, 522 + tx, 362 + ty, 2);
			g.drawImage(imgGame[IMG_CURSORARROW],
					311+180 + tx +aniCnt,371 + ty,311+180+28 + tx +aniCnt,371+34 + ty,0,0,28,34, null ); 					
			g.drawImage(imgGame[IMG_CURSORARROW],
					461+180 + tx -aniCnt,371+ ty,461+180+28 + tx -aniCnt,371+34 + ty,28,0,56,34, null ); 					
		}
	}
	
	void drawButton(Graphics g , int x, int y, int index) {
		g.drawImage(imgGame[IMG_BUTTON_01],
				x , y , x +116 , y +52 , index * 116 , 0 , (index +1) * 116 , 52 , null );
	}
	
	void drawButton_2(Graphics g , int x, int y, int index) {
		g.drawImage(imgGame[IMG_BUTTON_02],
				x , y , x +116 , y +52 , index * 116 , 0 , (index +1) * 116 , 52 , null );
	}
	
	public void runMainCnt() {
		if ( aniMain++ > 10000 ) aniMain = 0;
	} 
	
	void changeBackGround( int stg ) {
		try {
			String url = StateValue.liveResource + "handphone/";
			SceneManager.getInstance().loadBackgroundImage( new URL( url + "bg/remote_bg"+(stage+1)+".gif" ));
		} catch (MalformedURLException e) { e.printStackTrace(); }
		
		if ( stg == 0 ) {
			snd.loadPlaySound("change/ch_han.mp2");
		} else if ( stg == 1 ) {
			snd.loadPlaySound("change/ch_eng.mp2");
		} else if ( stg == 2 ) {
			snd.loadPlaySound("change/ch_ani.mp2");
		}
		
	}
	
	void drawPopup_End( Graphics2D g ) {
		g.drawImage(imgGame[IMG_ENDPOP_2], 287, 97, null);
		
		aniCnt = (aniCnt + 1)%2;
		
		if ( titleFocus == 0 ) {
			
			drawButton(g, 347, 342, 4);
			drawButton_2(g, 499, 342, 1);
			g.drawImage(imgGame[IMG_CURSORARROW],
					318+aniCnt,351,318 + 28 +aniCnt, 351 +34 , 0, 0, 28, 34, null ); 					
			g.drawImage(imgGame[IMG_CURSORARROW],
					464-aniCnt,351,464 + 28 -aniCnt, 351 +34 , 28, 0, 56, 34, null ); 	
			
		} else {
			
			drawButton(g, 347, 342, 5);
			drawButton_2(g, 499, 342, 0);
			g.drawImage(imgGame[IMG_CURSORARROW],
					318+152+aniCnt,351,318+152+28+aniCnt,351+34,0,0,28,34, null ); 					
			g.drawImage(imgGame[IMG_CURSORARROW],
					464+152-aniCnt,351,464+152+28-aniCnt,351+34,28,0,56,34, null ); 	
			
		}
	}
	
	void playSound( int gFocus ) {
		String strList [] = {"han" , "eng" , "ani" };
		String path = strList[stage] + "/" + strList[stage];
		
		switch ( gFocus ) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			path = path + (gFocus +1);
			break;
		case 9:
			path = path + "_star";
			break;
		case 10:
			path = path + 0;
			break;
		case 11:
			path = path + "_sharp";
			break;
		}
		
		snd.loadPlaySound( path + ".mp2");
	}
	
	void proKey() {
		
		if ( gameFocus < 12 ) {
			setAniWork();
			playSound( gameFocus );	
		} else {
			if ( gameFocus == 12 ) {
				stage = (stage +1) % 3;
				changeBackGround( stage );
			} else if ( gameFocus == 13 ) {
				mode = MODE_ENDPOPUP;
			}
		}
		
	}
	
	void exitProcess() {
		SceneManager.getInstance().load_kind=true; //120523_1
		SceneManager.getInstance().setChangeScene(SceneManager.SATAE_PORTAL_MENU);
	}
	
	void proPressKey( int gFocus ) {
		this.gameFocus = gFocus;
		proKey();
	}
	
	void proF4Key() {
		switch ( mode ) {
		case MODE_TITLE:
			exitProcess();
			break;
			
		case MODE_GAMEPLAY:
			mode = MODE_ENDPOPUP;
			break;
			
		case MODE_ENDPOPUP:
			mode = MODE_GAMEPLAY;
			titleFocus = 0;
			break;
		}
	}
	
	public void processKey(Object userEvent, int key) {
		
		if ( key == Constant.KEY_PREV ) {
			proF4Key();
			return;
		}
		
		switch ( mode ) {
		
		case MODE_TITLE:
			if ( key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_RIGHT) {
				titleFocus ^= 1;
				
				if ( titleFocus == 0 ) {
					snd.loadPlaySound("title/start.mp2");
				} else if ( titleFocus == 1 ){
					snd.loadPlaySound("title/exit.mp2");
				}
				
			} else if ( key == KeyEvent.VK_ENTER ) {
				if ( titleFocus == 0 ) {
					loading = true;
//					try {
//						String url = SceneManager.getInstance().getRootUrl();
//						SceneManager.getInstance().loadBackgroundImage( new URL( url + "bg/remote_bg1.jpg" ));
//					} catch (MalformedURLException e) { e.printStackTrace(); }
					Thread thread = new Thread(){
						public void run(){
							try {
								
								SceneManager.getInstance().loadBackgroundImage( new URL(
										StateValue.liveResource + "handphone/bg/remote_bg"+(stage+1)+".gif" ));
								
							} catch (MalformedURLException e) { e.printStackTrace(); }

							loading = false;
						}
					};
					thread.start();
					
					if ( SceneManager.getInstance().chkGameState ) {
						SceneManager.getInstance().send_gravity(10);
						SceneManager.getInstance().chkGameState = false;
					}
					
					stage = 0;
					mode = MODE_GAMEPLAY;
					titleFocus = 0;
				} else {
					exitProcess();
				}
			}
			break;
			
		case MODE_GAMEPLAY:
			
			if ( key == KeyEvent.VK_RIGHT ) {
				
				if ( gameFocus > 11 ) {
					int ttr = gameFocus/2; 				
					gameFocus = (gameFocus + 1) % 2;
					gameFocus = gameFocus + (ttr*2);
				} else {
					int ttr = gameFocus/3; 				
					gameFocus = (gameFocus + 1) % 3;
					gameFocus = gameFocus + (ttr*3);
				}
				
			} else if ( key == KeyEvent.VK_LEFT ) {
				
				if ( gameFocus > 11 ) {
					int ttl = gameFocus/2;
					gameFocus = ( gameFocus +1 ) % 2;
					gameFocus = gameFocus + (ttl*2);
				} else {
					int ttl = gameFocus/3;
					gameFocus = ( gameFocus +2 ) % 3;
					gameFocus = gameFocus + (ttl*3);
				}
				
			} else if ( key == KeyEvent.VK_UP ) {
				
				if ( gameFocus > 12 ) {
					gameFocus = 9;
				} else if ( gameFocus > 2 ) {
					gameFocus = (gameFocus +11 ) % 14 ;
				} else if ( gameFocus == 0 || gameFocus == 1 || gameFocus == 2 ) {
					gameFocus = 12;
				}
				
			} else if ( key == KeyEvent.VK_DOWN ) {
				
				if ( gameFocus < 9 ) {
					gameFocus = (gameFocus +3 ) % 14 ;
				} else if ( gameFocus == 9 || gameFocus == 10 || gameFocus == 11 ) {
					gameFocus = 12;
				} else if ( gameFocus > 11 ) {
					gameFocus = 0;
				}
				
			} else if ( key == KeyEvent.VK_ENTER ) {
				proKey();
			} 
			
			break;
			
		case MODE_ENDPOPUP:
			
			if ( key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_RIGHT) {
				titleFocus ^= 1;
				
				if ( titleFocus == 0 ) {
					snd.loadPlaySound("title/retry.mp2");
				} else if ( titleFocus == 1 ){
					snd.loadPlaySound("title/stop.mp2");
				}
				
			} else if ( key == KeyEvent.VK_ENTER ) {
				
				if ( titleFocus == 0 ) {
					mode = MODE_GAMEPLAY;
					titleFocus = 0;
				} else if ( titleFocus == 1 ) {
					loading = true;
					
					SceneManager.getInstance().send_gravity(11);
					
					Thread thread = new Thread(){
						public void run(){
							if(loading){
								try {
									String url = StateValue.liveResource + "handphone/";
									SceneManager.getInstance().loadBackgroundImage( new URL( url + "bg/remote_title.gif" ));
								} catch (MalformedURLException e) { e.printStackTrace(); }

								loading = false;
							}
						}
					};
					thread.start();
					mode = MODE_TITLE;
					titleFocus = 0;
					gameFocus = 0;
					stage = 0;
				}
				
			}
			
			break;
			
		}
		
		switch ( key ) {
		case KeyEvent.VK_0:
			proPressKey(10);
			break;
		case KeyEvent.VK_1:
			proPressKey(0);
			break;
		case KeyEvent.VK_2:
			proPressKey(1);
			break;
		case KeyEvent.VK_3:
			proPressKey(2);
			break;
		case KeyEvent.VK_4:
			proPressKey(3);
			break;
		case KeyEvent.VK_5:
			proPressKey(4);
			break;
		case KeyEvent.VK_6:
			proPressKey(5);
			break;
		case KeyEvent.VK_7:
			proPressKey(6);
			break;
		case KeyEvent.VK_8:
			proPressKey(7);
			break;
		case KeyEvent.VK_9:
			proPressKey(8);
			break;
//		case KeyEvent.VK_F11: // star
		case 120:
			proPressKey(9);
			break;
//		case KeyEvent.VK_F12: // 우물정
		case 121:
			proPressKey(11);
			break;
		}
	} 
	
	public void draw_bgalpha(Graphics g) {
		g.setColor(new DVBColor(0,0,0,80));
		g.fillRect(0,0,960,540);
	}
	
	public void destroyScene() { 
		
		SceneManager.getInstance().load_kind = true;
		
		if ( imgGame != null ) {
			for (int i = 0; i < IMG_MAX_COUNT; i++) {
				if ( imgGame[i] != null) {
					SceneManager.getInstance().removeImage(imgGame[i]);
					imgGame[i] = null;
				}
				if ( i%3 == 0 ) SceneManager.getInstance().repaint();
			}
			imgGame = null;
		}
		if (imgLoad[0] != null ) {
			SceneManager.getInstance().removeImage(imgLoad[0]);
			imgLoad[0] = null;
		}
		if (imgLoad[1] != null ) {
			SceneManager.getInstance().removeImage(imgLoad[1]);
			imgLoad[1] = null;
		}
		imgLoad = null;
		
		if ( snd != null ) {
			snd.dispose();
			snd = null;
		}
	}

	public void process(int elapsedtime) {
		runMainCnt();
	}

	
} // END. HookPlay{}.