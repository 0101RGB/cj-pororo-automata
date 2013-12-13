package pororo.com.game.teeth;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;

public class TeethPlay extends Scene {
	pororo.com.game.teeth.Sound snd = new pororo.com.game.teeth.Sound();
	SceneManager sm = SceneManager.getInstance();
	
	int loadingCnt = 0;
	
	final int M_LOADING = 10;
	final int M_TITLE = 20;
	final int M_DEFAULT = 30;
	final int M_PLAY = 40;
	final int M_END = 50;
	
	int mMode = M_LOADING;
	
	public String [] imgPath = {
//			"title/teeth_title.jpg",		//0		타이틀 배경
//			"title/loading1.png",			//1		로딩바
//			"title/loading2.png",			//2		로딩 배경
//			"game/teeth_bg.jpg",			//5		게임 배경
			"end/water.png",				//0		물줄기
			"end/ed_bt.png",				//1		OK버튼을 누르세요
			"end/edf.png",					//2		깨끗해졌어요
			"title/bt.png",					//3		버튼(시작하기,나가기..)
			"title/cs.png",					//4		버튼(화살표)
			"end/endpop.png",				//5		끝내기 팝업
			"game/opf.png",					//6		깨끗이 닦아주세요
			"game/op_bt.png",				//7		OK키(리모콘의 키로 이를닦아주세요)
			"game/gage/teeth_gage_a.png",	//8		게이지 배경
			"game/gage/teeth_gage_b.png",	//9		게이지바
			"game/character/arm_l.png",		//10	왼쪽팔
			"game/character/arm_r.png",		//11	오른쪽팔
			"game/character/body.png",		//12	몸
			"game/character/eye.png",		//13	눈
			"game/character/face.png",		//14	머리
			"game/character/mouth.png",		//15	입
			"game/hand/bb.png",				//16	거품
			"game/hand/hand1.png",			//17	손
			"game/hand/hand2.png",			//18	팔
			"end/success.png",				//19	성공
			"end/twinkle.png"				//20	반짝
//			"end/water.png",				//22	물줄기
//			"end/ed_bt.png",				//23	OK버튼을 누르세요
//			"end/edf.png",					//24	깨끗해졌어요
	};
	
	
	Image imgLoad [] = new Image[2];
	Image imgGame [] = new Image[imgPath.length];
	
	boolean menuCnt = true;
	int aniCnt = 0;
	boolean loading = false;
	
	////////////////////////////////////////////////////////////////////////////
	
	int cnt4 = 0;
	int cnt8 = 0;
	int cnt18 = 0;
	int mode = 0;
	int viewchange = 0;		
	int aniAlphaCnt = 0;	
	int menuCnts = 0; 		
	int btCnt = 0;		
	int frame = 0;
	boolean soundflag = false;
	boolean POPUP = false;
	
	////////////////////////////////////////////////////////////////////////////
	
	int cnt3 = 0;
	int cnt4s = 0;
	int cnt18s = 0;
	int cnt10 = 0;
	int btCnts = 0;
	int menuCntss = 0; 
	int aniAlphaCnts = 0;	
	
	int modes = 0;
	int modeCnt = 0;
	boolean bbStart = false;
	final int UP = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	final int RIGHT = 4;
	
	final int POP = 10;	
	boolean POPUPss = false;
	
	////////////////////////////////////////////////////////////////////////////
	
	int cnt3a = 0;
	int cnt4a = 0;
	int cnt18a = 0;
	int water = 0;
	int hand = 0;
	int twinkle = 0;
	int hand2 = 0;	
	int btCnte = 0;	
	int menuCnte = 0; 
	int eaniAlphaCnt = 0;	
	float aniAlphaCnt2 = 1.0f;	
	
	boolean POPUPe = false;
	
	int aniCntt = 1;	
	////////////////////////////////////////////////////////////////////////////
	
	
	
	public TeethPlay(){
		
		try {
			sm.loadBackgroundImage( new URL(StateValue.liveResource + "teeth/bg/teeth_title.gif"));
			
			imgLoad[0] = sm.getImage( new URL(StateValue.liveResource + "teeth/image/title/loading1.png"));
			imgLoad[1] = sm.getImage( new URL(StateValue.liveResource + "teeth/image/title/loading2.png"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		SceneManager.getInstance().load_kind = false;
		
		snd.playSound("snd/teeth/none.mp2");
		
		final String url = StateValue.liveResource;
		
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < imgPath.length; i++) {
//					if ( sm.alive ) {
						loadingCnt = i;
						try {
							imgGame[i] = sm.getImage(new URL(url + "teeth/image/" + imgPath[i]));
							if ( i %8 == 0 ) SceneManager.getInstance().repaint();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
//					} else {
//						destroyScene();
//						return;
//					}
					
				}
				mMode = M_TITLE; 
				resets(M_TITLE);
				
				sm.load_kind = false;
			}
		}).start();
		
	}
	
	public void draw(Graphics2D g) {
		
		if ( mMode == M_LOADING ) {
			
			g.drawImage(imgLoad[1], 495, 402, null);
			g.drawImage(imgLoad[0], 501, 408, 501+loadingCnt*10, 448, 0, 0, loadingCnt*10, 40, null);
			
		} else if ( mMode == M_TITLE ) {
			
			if(!loading){
				aniCnt = (aniCnt+1)%2;
				
				if(menuCnt){	
					g.drawImage(imgGame[3], 532, 402, 532+116, 402+52, 0, 0, 116, 50, null);
					g.drawImage(imgGame[3], 712, 402, 712+116, 402+52, 116*3, 0, 116*4, 50, null);
					g.drawImage(imgGame[4], 501+aniCnt, 411, 501+28+aniCnt, 445, 0, 0, 28, 34, null);
					g.drawImage(imgGame[4], 651-aniCnt, 411, 651+28-aniCnt, 445, 28, 0, 56, 34, null);
				}else if(!menuCnt){			
					g.drawImage(imgGame[3], 532, 402, 532+116, 402+52, 116, 0, 232, 50, null);
					g.drawImage(imgGame[3], 712, 402, 712+116, 402+52, 232, 0, 348, 50, null);
					g.drawImage(imgGame[4], 681+aniCnt, 411, 681+28+aniCnt, 445, 0, 0, 28, 34, null);
					g.drawImage(imgGame[4], 831-aniCnt, 411, 831+28-aniCnt, 445, 28, 0, 56, 34, null);
				}
			}
			
		} else if ( mMode == M_DEFAULT ) {
			if(!loading){
				if (cnt4 == 4)
					cnt4 = 0;
				
				if (cnt18 == 18)
					cnt18 = 0;
				
				int brushAniCntX[] = { 0, -9, -28, -52, -81, -112, -137, -148 };	
				int brushAniCntY[] = { 0, -9, -26, -42, -57, -67, -72, -73 };		
				
				int brushAniCnt[] = { 0, -1, 0, 1 };
				int bodyAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1 };
				int faceAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1 };
				int handAniCnt[] = { 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 };
				int eyeAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1 };
				int mouthAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1 };
				int mouthAniCnt2[] = { 0, 0, 100, 300, 200, 100, 0, 100, 300, 200, 100, 0, 0, 100, 300, 200, 100, 0 };
				int mouthAniCnt3[] = { 100, 300, 400, 400, 400, 400, 400, 400};
				
				switch(mode){
				case 1:		
					g.drawImage(imgGame[12], 375, 403, 587, 541, 0, 0, 212, 138,	null);
					g.drawImage(imgGame[14], 300, 78, 660, 436, 0, 0, 360, 358, null);
					g.drawImage(imgGame[10], 526, 435, 600, 503, 0, 0, 74, 68, null);
					g.drawImage(imgGame[11], 356, 394, 434, 502, 0, 0, 78, 108, null);
					g.drawImage(imgGame[15], 430, 291, 530, 391,
							0 + mouthAniCnt3[cnt8], 0, 100 + mouthAniCnt3[cnt8], 100, null);
					g.drawImage(imgGame[13], 386, 259, 576, 289, 190, 0, 380, 30, null);
					
					g.drawImage(imgGame[8], 47, 93, 139, 447, 0, 0, 92, 354, null);
					g.drawImage(imgGame[9], 79, 170, 107, 370, 0, 0, 28, 200, null);
					
					g.drawImage(imgGame[17], 586 + brushAniCntX[cnt8],
							357 + brushAniCntY[cnt8], 936 + brushAniCntX[cnt8],
							547 + brushAniCntY[cnt8], 0, 0, 350, 190, null);
					g.drawImage(imgGame[18], 834 + brushAniCntX[cnt8],
							460 + brushAniCntY[cnt8], 1120 + brushAniCntX[cnt8],
							748 + brushAniCntY[cnt8], 0, 0, 286, 288, null);
					
					viewchange++;
					cnt8++;
					
					break;
					
				default:
					g.drawImage(imgGame[12], 375, 403 + bodyAniCnt[cnt18], 587, 541 + bodyAniCnt[cnt18], 0, 0, 212, 138, null);
					g.drawImage(imgGame[14], 300, 78 + faceAniCnt[cnt18], 660, 436 + faceAniCnt[cnt18], 0, 0, 360, 358, null);
					g.drawImage(imgGame[10], 526, 435 + handAniCnt[cnt18], 600, 503 + handAniCnt[cnt18], 0, 0, 74, 68, null);
					g.drawImage(imgGame[11], 356, 394 + handAniCnt[cnt18], 434, 502 + handAniCnt[cnt18], 0, 0, 78, 108, null);
					g.drawImage(imgGame[15], 430, 291 + mouthAniCnt[cnt18], 530,
							391 + mouthAniCnt[cnt18], 0 + mouthAniCnt2[cnt18], 0, 100 + mouthAniCnt2[cnt18], 100, null);
					
					if (cnt18 == 6 || cnt18 == 7 || cnt18 == 14 || cnt18 == 15	
							|| cnt18 == 16) {
						g.drawImage(imgGame[13], 386, 259 + eyeAniCnt[cnt18], 576, 289 + eyeAniCnt[cnt18], 190, 0, 380, 30, null);
					} else {
						g.drawImage(imgGame[13], 386, 259 + eyeAniCnt[cnt18], 576, 289 + eyeAniCnt[cnt18], 0, 0, 190, 30, null);
					}
					
					g.drawImage(imgGame[17], 586 + brushAniCnt[cnt4], 357, 936 + brushAniCnt[cnt4], 547, 0, 0, 350, 190, null);
					g.drawImage(imgGame[18], 834 + brushAniCnt[cnt4], 460, 1120 + brushAniCnt[cnt4], 748, 0, 0, 286, 288, null);
					
					
					if(frame >= 30){
						AlphaImage.setAlpha(g, aniAlphaCnt*0.2f);	
						g.drawImage(imgGame[7], 305, 448, 655, 498, 0, 0, 350, 50, null);
						AlphaImage.setAlpha(g, 1.0f);
						if(aniAlphaCnt<20) aniAlphaCnt++;
					}
					
					switch(cnt4){
					case 0:
						g.drawImage(imgGame[6], 199, 74, 800, 164, 0, 0, 582, 90, null);
						break;
					case 1:
						g.drawImage(imgGame[6], 199, 74, 800, 164, 582, 0, 1164, 90, null);
						break;
					case 2:
						g.drawImage(imgGame[6], 199, 74, 800, 164, 0, 0, 582, 90, null);
						break;
					case 3:
						g.drawImage(imgGame[6], 199, 74, 800, 164, 1164, 0, 1746, 90, null);
						break;
					}
					break;
				}
				
				if(POPUP == true){
					btCnt = (btCnt+1)%2;	
					
					
					switch(menuCnts){
					case 0:
						g.drawImage(imgGame[5], 287, 97, 673, 417, 0, 0, 386, 320, null);
						
						g.drawImage(imgGame[3], 347, 342, 463, 394, 464, 0, 580, 52, null);
						g.drawImage(imgGame[3], 499, 342, 615, 394, 812, 0, 928, 52, null);
						g.drawImage(imgGame[4], 318+btCnt, 351, 346+btCnt, 385, 0, 0, 28, 34, null);
						g.drawImage(imgGame[4], 464-btCnt, 351, 492-btCnt, 385, 28, 0, 56, 34, null);
						break;
						
					case 1:
						g.drawImage(imgGame[5], 287, 97, 673, 417, 0, 0, 386, 320, null);
						
						g.drawImage(imgGame[3], 347, 342, 463, 394, 580, 0, 696, 52, null);
						g.drawImage(imgGame[3], 499, 342, 615, 394, 696, 0, 812, 52, null);
						g.drawImage(imgGame[4], 470+btCnt, 351, 498+btCnt, 385, 0, 0, 28, 34, null);
						g.drawImage(imgGame[4], 616-btCnt, 351, 644-btCnt, 385, 28, 0, 56, 34, null);
						break;
						
					}
				}
				
				g.drawImage(imgGame[8], 47, 93, 139, 447, 0, 0, 92, 354, null);
				
				cnt4++;
				cnt18++;
				frame++;
			}
			
		} else if ( mMode == M_PLAY ) {
			
			if (cnt3 == 3)
				cnt3 = 0;
			
			if (cnt4s == 4)
				cnt4s = 0;
			
			if (cnt18s == 18)
				cnt18s = 0;
			
			if (cnt10 == 10){
				cnt10 = 0;
				modes = 0;
			}
			
			if (cnt10 == 1)
				modeCnt += 1;
			
			int brushAniCnt[] = { 0, -1, 0, 1 };
			
			int rightUp[] = {5, -1, -3, 3};
			int leftUp[] = {-5, 1, 3, -3};
			int rightLeft[] = {-5, 1, 3, -3};
			int leftLeft[] = {5, -1, -3, 3};
			int brushUp[] = {-4, -18, -15, 0, 10, 5, -4, -10, -3, 0};
			int brushDown[] = {4, 18, 15, 0, -10, -5, 4, 10, 3, 0}; 
			int brushLeft[] = {-4, -30, -6, 8, 10, -4, -22, -7, -2, 0};
			int brushRight[] = {4, 30, 6, -8, -10, 4, 22, 7, 2, 0};
			int bbAniCnt[] = {0, 120, 240};
			
			g.drawImage(imgGame[12], 375, 403, 587, 541, 0, 0, 212, 138, null);
			g.drawImage(imgGame[14], 300, 78, 660, 436, 0, 0, 360, 358, null);	
			
			g.drawImage(imgGame[8], 47, 93, 139, 447, 0, 0, 92, 354, null);
			g.drawImage(imgGame[9], 79, 370-((int)(6.6*modeCnt)), 107, 370, 0, 200-((int)(6.6*modeCnt)), 28, 200, null);
			
			
			switch(modes){		
			case UP:
				
				g.drawImage(imgGame[10], 526, 435+leftUp[cnt4s], 600, 503+leftUp[cnt4s], 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356, 394+rightUp[cnt4s], 434, 502+rightUp[cnt4s], 0, 0, 78, 108, null);
				g.drawImage(imgGame[15], 430, 291, 530, 391, 400, 0, 500, 100, null);
				g.drawImage(imgGame[13], 386, 259, 576, 289, 380, 0, 570, 30, null);
				
				AlphaImage.setAlpha(g, aniAlphaCnts*0.5f);		
				g.drawImage(imgGame[16], 421, 305, 541, 385, 0+bbAniCnt[cnt3], 0, 120+bbAniCnt[cnt3], 80, null);
				AlphaImage.setAlpha(g, 1.0f);
				if(aniAlphaCnts<20) aniAlphaCnts++;
				g.drawImage(imgGame[17], 438, 284+brushUp[cnt10], 788, 474+brushUp[cnt10], 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 686, 387+brushUp[cnt10], 972, 675+brushUp[cnt10], 0, 0, 286, 288, null);
				
				cnt18s = 0;
				cnt10++;
				break;
				
			case DOWN:
				
				g.drawImage(imgGame[10], 526, 435+leftUp[cnt4s], 600, 503+leftUp[cnt4s], 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356, 394+rightUp[cnt4s], 434, 502+rightUp[cnt4s], 0, 0, 78, 108, null);
				g.drawImage(imgGame[15], 430, 291, 530, 391, 400, 0, 500, 100, null);
				g.drawImage(imgGame[13], 386, 259, 576, 289, 380, 0, 570, 30, null);
				
				AlphaImage.setAlpha(g, aniAlphaCnts*0.5f);	
				g.drawImage(imgGame[16], 421, 305, 541, 385, 0+bbAniCnt[cnt3], 0, 120+bbAniCnt[cnt3], 80, null);
				AlphaImage.setAlpha(g, 1.0f);
				if(aniAlphaCnts<20) aniAlphaCnts++;
				g.drawImage(imgGame[17], 438, 284+brushDown[cnt10], 788, 474+brushDown[cnt10], 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 686, 387+brushDown[cnt10], 972, 675+brushDown[cnt10], 0, 0, 286, 288, null);
				
				cnt18s = 0;
				cnt10++;
				break;
				
			case LEFT:
				
				g.drawImage(imgGame[10], 526+leftLeft[cnt4s], 435, 600+leftLeft[cnt4s], 503, 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356+rightLeft[cnt4s], 394, 434+rightLeft[cnt4s], 502, 0, 0, 78, 108, null);
				g.drawImage(imgGame[15], 430, 291, 530, 391, 400, 0, 500, 100, null);
				g.drawImage(imgGame[13], 386, 259, 576, 289, 380, 0, 570, 30, null);
				
				AlphaImage.setAlpha(g, aniAlphaCnts*0.5f);	
				g.drawImage(imgGame[16], 421, 305, 541, 385, 0+bbAniCnt[cnt3], 0, 120+bbAniCnt[cnt3], 80, null);
				AlphaImage.setAlpha(g, 1.0f);
				if(aniAlphaCnts<20) aniAlphaCnts++;
				g.drawImage(imgGame[17], 438+brushLeft[cnt10], 284, 788+brushLeft[cnt10], 474, 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 686+brushLeft[cnt10], 387, 972+brushLeft[cnt10], 675, 0, 0, 286, 288, null);
				
				cnt18s = 0;
				cnt10++;
				break;
				
			case RIGHT:
				
				g.drawImage(imgGame[10], 526+leftLeft[cnt4s], 435, 600+leftLeft[cnt4s], 503, 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356+rightLeft[cnt4s], 394, 434+rightLeft[cnt4s], 502, 0, 0, 78, 108, null);
				g.drawImage(imgGame[15], 430, 291, 530, 391, 400, 0, 500, 100, null);
				g.drawImage(imgGame[13], 386, 259, 576, 289, 380, 0, 570, 30, null);
				
				AlphaImage.setAlpha(g, aniAlphaCnts*0.5f);
				g.drawImage(imgGame[16], 421, 305, 541, 385, 0+bbAniCnt[cnt3], 0, 120+bbAniCnt[cnt3], 80, null);
				AlphaImage.setAlpha(g, 1.0f);
				if(aniAlphaCnts<20) aniAlphaCnts++;
				g.drawImage(imgGame[17], 438+brushRight[cnt10], 284, 788+brushRight[cnt10], 474, 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 686+brushRight[cnt10], 387, 972+brushRight[cnt10], 675, 0, 0, 286, 288, null);
				
				cnt18s = 0;
				cnt10++;
				break;
				
			default:
				
				g.drawImage(imgGame[10], 526, 435, 600,
						503, 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356, 394, 434,
						502, 0, 0, 78, 108, null);
				g.drawImage(imgGame[15], 430, 291, 530,
						391, 400, 0, 500, 100, null);
				
				if (cnt18s == 6 || cnt18s == 7 || cnt18s == 14 || cnt18s == 15 || cnt18s == 16) {	
					g.drawImage(imgGame[13], 386, 259, 576, 289, 190, 0, 380, 30, null);
				} else {
					g.drawImage(imgGame[13], 386, 259, 576, 289, 0, 0, 190, 30, null);
				}
				
				if(bbStart == true){	
					g.drawImage(imgGame[16], 421, 305, 541, 385, 0+bbAniCnt[cnt3], 0, 120+bbAniCnt[cnt3], 80, null);
				}
				
				g.drawImage(imgGame[17], 438 + brushAniCnt[cnt4s], 284,
						788 + brushAniCnt[cnt4s], 474, 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 686 + brushAniCnt[cnt4s], 387,
						972 + brushAniCnt[cnt4s], 675, 0, 0, 286, 288, null);
				
				if(modes == POP){		
					
					btCnts = (btCnts+1)%2;	
					switch(menuCntss){
					case 0:
						g.drawImage(imgGame[5], 287, 97, 673, 417, 0, 0, 386, 320, null);
						
						g.drawImage(imgGame[3], 347, 342, 463, 394, 464, 0, 580, 52, null);
						g.drawImage(imgGame[3], 499, 342, 615, 394, 812, 0, 928, 52, null);
						g.drawImage(imgGame[4], 318+btCnts, 351, 346+btCnts, 385, 0, 0, 28, 34, null);
						g.drawImage(imgGame[4], 464-btCnts, 351, 492-btCnts, 385, 28, 0, 56, 34, null);
						break;
						
					case 1:
						g.drawImage(imgGame[5], 287, 97, 673, 417, 0, 0, 386, 320, null);
						
						g.drawImage(imgGame[3], 347, 342, 463, 394, 580, 0, 696, 52, null);
						g.drawImage(imgGame[3], 499, 342, 615, 394, 696, 0, 812, 52, null);
						g.drawImage(imgGame[4], 470+btCnts, 351, 498+btCnts, 385, 0, 0, 28, 34, null);
						g.drawImage(imgGame[4], 616-btCnts, 351, 644-btCnts, 385, 28, 0, 56, 34, null);
						break;
						
					}
				}
			}
			
			cnt3++;
			cnt4s++;		
			cnt18s++;
			
		} else if ( mMode == M_END ) {
			
			if (cnt3a == 3)
				cnt3a = 0;
			
			if (cnt4a == 4)
				cnt4a = 0;
			
			if (aniCnt == 5)	
				hand = 0;
			
			if (aniCnt == 10)	
				water = 0;			
			
			if (aniCnt == 35)	
				twinkle = 0;
			
			if (aniCnt == 35)	
				hand2 = 0;
			
			if (hand2 == 8)
				hand2 = 0;
			
			if (cnt18a == 18)
				cnt18a = 0;
			
			int brushAniCntX[] = { 0, 11, 39, 70, 101, 126, 142, 148 };	
			int brushAniCntY[] = { 0, -8, -17, -9, 10, 37, 61, 73 };	
			
			int handAniCntX[] = { -2, 1, 2, -1};							
			int handAniCntY[] = { 2, -1, -2, 1};							
			
			
			int brushAniCntxx[] = { 0, -1, 0, 1 };
			int bodyAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1,	0, 1, 1 };
			int faceAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1 };
			int handAniCnt[] = { 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0 };		
			int handAniCnt2[] = { 1, 2, 1, 0, -1, -2, -1, 0};	
			int mouthAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1 };
			int eyeAniCnt[] = { 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1 };
			int mouthAniCnt2[] = { 600, 700, 600, 700 };	
			int bbAniCntxx[] = {0, 120, 240};
			
			int waterAniCnt[] = {0, 258, 516, 774, 1032, 1290, 1548};
			int twinkleAniCnt[] = {0, 134, 268, 402, 536, 268, 402, 536, 268, 402, 536, 268, 402, 536, 268,	
					402, 536, 268, 134, 0};
			
			
			if(1 <= aniCnt && aniCnt <= 14){	
				g.drawImage(imgGame[12], 375, 403, 587, 541, 0, 0, 212, 138, null);
				g.drawImage(imgGame[14], 300, 78, 660, 436 , 0, 0, 360, 358, null);
				g.drawImage(imgGame[15], 430, 291, 530, 391, 400, 0, 500, 100, null);
				g.drawImage(imgGame[10], 526, 435, 600, 503, 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356, 394, 434, 502, 0, 0, 78, 108, null);
				if (aniCnt == 6 || aniCnt == 7 ) {	
					g.drawImage(imgGame[13], 386, 259, 576, 289, 190, 0, 380, 30, null);
				} else {
					g.drawImage(imgGame[13], 386, 259, 576, 289, 0, 0, 190, 30, null);
				}
			}
			if(15 <= aniCnt && aniCnt <= 34){		
				g.drawImage(imgGame[12], 375, 403, 587, 541, 0, 0, 212, 138, null);
				g.drawImage(imgGame[14], 300, 78, 660, 436 , 0, 0, 360, 358, null);
				g.drawImage(imgGame[15], 430, 291, 530, 391, 500, 0, 600, 100, null);
				g.drawImage(imgGame[10], 526 - handAniCntX[cnt4a], 435 + handAniCntY[cnt4a], 600 - handAniCntX[cnt4a],
						503 + handAniCntY[cnt4a], 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356 + handAniCntX[cnt4a], 394 + handAniCntY[cnt4a], 434 + handAniCntX[cnt4a],
						502 + handAniCntY[cnt4a], 0, 0, 78, 108, null);
				g.drawImage(imgGame[13], 386, 259, 576, 289 , 570, 0, 760, 30, null);
			}
			if(35 <= aniCnt && aniCnt <= 54){		
				g.drawImage(imgGame[12], 375, 403, 587, 541, 0, 0, 212, 138, null);
				g.drawImage(imgGame[14], 300, 78, 660, 436 , 0, 0, 360, 358, null);
				g.drawImage(imgGame[15], 430, 291, 530, 391, 600, 0, 700, 100, null);
				g.drawImage(imgGame[10], 526, 435 + handAniCnt2[hand2], 600, 503 + handAniCnt2[hand2], 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356, 394 + handAniCnt2[hand2], 434, 502 + handAniCnt2[hand2], 0, 0, 78, 108, null);
				g.drawImage(imgGame[13], 386, 259, 576, 289 , 760, 0, 950, 30, null);
			}
			if(54 <= aniCnt){		
				g.drawImage(imgGame[12], 375, 403 + bodyAniCnt[cnt18a], 587, 541 + bodyAniCnt[cnt18a], 0, 0, 212, 138, null);
				g.drawImage(imgGame[14], 300, 78 + faceAniCnt[cnt18a], 660, 436 + faceAniCnt[cnt18a], 0, 0, 360, 358, null);
				g.drawImage(imgGame[10], 526, 435 + handAniCnt[cnt18a], 600, 503 + handAniCnt[cnt18a], 0, 0, 74, 68, null);
				g.drawImage(imgGame[11], 356, 394 + handAniCnt[cnt18a], 434, 502 + handAniCnt[cnt18a], 0, 0, 78, 108, null);
				g.drawImage(imgGame[15], 430, 291 + mouthAniCnt[cnt18a], 530,
						391 + mouthAniCnt[cnt18a], 0 + mouthAniCnt2[cnt4a], 0, 100 + mouthAniCnt2[cnt4a], 100, null);
				g.drawImage(imgGame[13], 386, 259 + eyeAniCnt[cnt18a], 576, 289 + eyeAniCnt[cnt18a], 760, 0, 950, 30, null);
			}
			
			if(aniCnt <20){			
				g.drawImage(imgGame[16], 421, 305, 541, 385, 0+bbAniCntxx[cnt3a], 0, 120+bbAniCntxx[cnt3a], 80, null);
			}
			
			if(aniCnt == 1){
				g.drawImage(imgGame[19], 408, 281, 554, 407, 0, 0, 146, 126, null);
			}else if(aniCnt== 2){
				g.drawImage(imgGame[19], 408, 281, 554, 407, 146, 0, 292, 126, null);
			}else if(aniCnt == 3){
				g.drawImage(imgGame[19], 408, 281, 554, 407, 292, 0, 438, 126, null);
			}else if(aniCnt == 4){
				g.drawImage(imgGame[19], 408, 281, 554, 407, 438, 0, 584, 126, null);
			}
			
			
			if(10 <= aniCnt && aniCnt <= 16){	
				g.drawImage(imgGame[0], 302, 269, 560, 551, 0+waterAniCnt[water], 0, 258+waterAniCnt[water], 282, null);
			}
			if(17 <= aniCnt && aniCnt <=28){	
				switch (aniCnt%2){
				case 0:
					g.drawImage(imgGame[0], 302, 269, 560, 551, 1548, 0, 1806, 282, null);
					break;
				case 1:
					g.drawImage(imgGame[0], 302, 269, 560, 551, 1290, 0, 1548, 282, null);
					break;
				}
			}
			if(29 <= aniCnt && aniCnt <=33){
				if(aniCnt%2 == 0){
					AlphaImage.setAlpha(g, aniAlphaCnt2);	
					g.drawImage(imgGame[0], 302, 269, 560, 551, 1548, 0, 1806, 282, null);
					AlphaImage.setAlpha(g, 1.0f);
					if(aniAlphaCnt2 > 0)
						aniAlphaCnt2 -= 0.2f;
				}else{
					AlphaImage.setAlpha(g, aniAlphaCnt2);		
					g.drawImage(imgGame[0], 302, 269, 560, 551, 1290, 0, 1548, 282, null);
					AlphaImage.setAlpha(g, 1.0f);
					if(aniAlphaCnt2 > 0)
						aniAlphaCnt2 -= 0.2f;
				}
			}
			
			if(35 <= aniCnt && aniCnt <= 54){	
				g.drawImage(imgGame[20], 412, 302, 546, 406, 0+twinkleAniCnt[twinkle], 0, 134+twinkleAniCnt[twinkle], 104, null);
			}
			
			if( aniCnt <= 4){			
				g.drawImage(imgGame[17], 438, 284, 788, 474, 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 686, 387, 972, 675, 0, 0, 286, 288, null);
			}
			if( 5 <= aniCnt && aniCnt <= 12){
				g.drawImage(imgGame[17], 438 +brushAniCntX[hand] , 284+brushAniCntY[hand]
						, 788+brushAniCntX[hand], 474+brushAniCntY[hand], 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 686 +brushAniCntX[hand] , 387+brushAniCntY[hand]
						, 972+brushAniCntX[hand], 675+brushAniCntY[hand], 0, 0, 286, 288, null);
			}
			if(13 <= aniCnt){				
				g.drawImage(imgGame[17], 586 + brushAniCntxx[cnt4a], 357, 936 + brushAniCntxx[cnt4a], 547, 0, 0, 350, 190, null);
				g.drawImage(imgGame[18], 834 + brushAniCntxx[cnt4a], 460, 1120 + brushAniCntxx[cnt4a], 748, 0, 0, 286, 288, null);
			}
			
			g.drawImage(imgGame[8], 47, 93, 139, 447, 0, 0, 92, 354, null);
			g.drawImage(imgGame[9], 79, 170, 107, 370, 0, 0, 28, 200, null);
			
			if(aniCnt >= 60 && !POPUPe){
				switch(cnt4a) { 
				case 0:
					g.drawImage(imgGame[2], 275, 74, 705, 164, 0, 0, 430, 90, null);
					break;
				case 1:
					g.drawImage(imgGame[2], 275, 74, 705, 164, 430, 0, 860, 90, null);
					break;
				case 2:
					g.drawImage(imgGame[2], 275, 74, 705, 164, 0, 0, 430, 90, null);
					break;
				case 3:
					g.drawImage(imgGame[2], 275, 74, 705, 164, 860, 0, 1290, 90, null);
					break;
				}
			}
			
			if(aniCnt >= 80 && !POPUPe){
				AlphaImage.setAlpha(g, eaniAlphaCnt*0.2f);	
				g.drawImage(imgGame[1], 335, 448, 611, 500, 0, 0, 276, 52, null);
				AlphaImage.setAlpha(g, 1.0f);
				if(eaniAlphaCnt<20) eaniAlphaCnt++;
			}
			
			if(POPUPe == true){
				
				btCnte = (btCnte+1)%2;	
				switch(menuCnte){
				case 0:
					g.drawImage(imgGame[5], 287, 97, 673, 417, 0, 0, 386, 320, null);
					
					g.drawImage(imgGame[3], 347, 342, 463, 394, 464, 0, 580, 52, null);
					g.drawImage(imgGame[3], 499, 342, 615, 394, 812, 0, 928, 52, null);
					g.drawImage(imgGame[4], 318+btCnte, 351, 346+btCnte, 385, 0, 0, 28, 34, null);
					g.drawImage(imgGame[4], 464-btCnte, 351, 492-btCnte, 385, 28, 0, 56, 34, null);
					break;
					
				case 1:
					g.drawImage(imgGame[5], 287, 97, 673, 417, 0, 0, 386, 320, null);
					
					g.drawImage(imgGame[3], 347, 342, 463, 394, 580, 0, 696, 52, null);
					g.drawImage(imgGame[3], 499, 342, 615, 394, 696, 0, 812, 52, null);
					g.drawImage(imgGame[4], 470+btCnte, 351, 498+btCnte, 385, 0, 0, 28, 34, null);
					g.drawImage(imgGame[4], 616-btCnte, 351, 644-btCnte, 385, 28, 0, 56, 34, null);
					break;
					
				}
			}
			if(aniCnt == 1 || aniCnt == 10 || aniCnt == 35 || aniCnt == 60)
				sound();
			
			hand++;
			water++;
			twinkle++;
			hand2++;
			
			cnt3a++;
			cnt4a++;
			cnt18a++;
			aniCnt++;
			
		}
		
	}
	
	public void processKey(Object userEvent, int key) {
		switch (mMode) {
		
		case M_TITLE:
			
			if(!loading){
				switch (key) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_RIGHT:
					menuCnt=!menuCnt;
					if(menuCnt){
						snd.playSound("snd/teeth/common/start.mp2");
					}else{
						snd.playSound("snd/teeth/common/out.mp2");
					}
					break;
					
				case KeyEvent.VK_ENTER:
					if (menuCnt) {
						loading = true;
						
						new Thread( new Runnable() {
							public void run() {
								if(loading){
									try {
										sm.loadBackgroundImage( new URL(StateValue.liveResource + "teeth/bg/teeth_bg.gif"));
									} catch (MalformedURLException e) {
										e.printStackTrace();
									}
									snd.playSound("snd/teeth/voice/op.mp2");
									
									if ( SceneManager.getInstance().chkGameState ) {
										SceneManager.getInstance().send_gravity(10);
										SceneManager.getInstance().chkGameState = false;
									}
									loading = false;
								}
							}
						}).start();
						mMode = M_DEFAULT;
						resets(M_DEFAULT);
						
					} else if (!menuCnt) {
						sm.setChangeScene(SceneManager.SATAE_PORTAL_MENU);
					}
					break;
				case Constant.KEY_PREV:
					sm.setChangeScene(SceneManager.SATAE_PORTAL_MENU);
					break;
				}
			}
			break;
			
		case M_DEFAULT:
			if(!loading){
				switch (key) {
				case KeyEvent.VK_LEFT:
					if(POPUP == true){
						menuCnts = (menuCnts + 1) %2;
						if(!soundflag)
							soundflag = true;
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(POPUP == true){
						menuCnts = (menuCnts + 1) %2;
						if(!soundflag)
							soundflag = true;
					}
					break;
				case KeyEvent.VK_UP:
					break;
				case KeyEvent.VK_DOWN:
					break;
				case KeyEvent.VK_ENTER:
					mode = 1;
					if(!POPUP)
						snd.playSound("snd/teeth/effect/handup.mp2");
					if(POPUP){
						if (menuCnts == 0) {
							mode = 0;
							POPUP = !POPUP;
							//					sm.pushView(new DefaultView());
						} else if (menuCnts == 1) {
							loading = true;
							new Thread(new Runnable() {
								public void run() {
									if(loading){
										try {
											sm.loadBackgroundImage( new URL(StateValue.liveResource + "teeth/bg/teeth_title.gif"));
										} catch (MalformedURLException e) {
											e.printStackTrace();
										}
										loading = false;
									}
								}
							}).start();
							mMode = M_TITLE;
							resets(M_TITLE);
							
						}
					}
					break;
				case Constant.KEY_PREV:
					if(mode == 0){
						POPUP = !POPUP;
					}
					break;
				}
			}
			break;
			
		case M_PLAY:
			switch (key) {
			case KeyEvent.VK_LEFT:
				if(modes != POP && modeCnt < 30){
					if(cnt10 == 0 || cnt10 > 6){
						snd.playSound("snd/teeth/effect/ck_l.mp2");
						modes = LEFT;
						cnt10 = 0;
						bbStart = true;
					}
				}else if(modes == POP){
					menuCntss = (menuCntss+1) % 2;
					switch (menuCntss){
					case 0:
						snd.playSound("snd/teeth/common/retry.mp2");
						break;
					case 1:
						snd.playSound("snd/teeth/common/stop.mp2");
						break;
					}
				}
				
				break;
			case KeyEvent.VK_RIGHT:
				if(modes != POP && modeCnt < 30){
					if(cnt10 == 0 || cnt10 > 6){
						snd.playSound("snd/teeth/effect/cu_r.mp2");
						modes = RIGHT;
						cnt10 = 0;
						bbStart = true;
					}
				}else if(modes == POP){
					menuCntss = (menuCntss+1) % 2;
					switch (menuCntss){
					case 0:
						snd.playSound("snd/teeth/common/retry.mp2");
						break;
					case 1:
						snd.playSound("snd/teeth/common/stop.mp2");
						break;
					}
				}
				break;
			case KeyEvent.VK_UP:
				if(modes != POP && modeCnt < 30){
					if(cnt10 == 0 || cnt10 > 6){
						snd.playSound("snd/teeth/effect/ck_up.mp2");
						modes = UP;
						cnt10 = 0;
						bbStart = true;
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if(modes != POP && modeCnt < 30){
					if(cnt10 == 0 || cnt10 > 6){
						snd.playSound("snd/teeth/effect/ck_down.mp2");
						modes = DOWN;
						cnt10 = 0;
						bbStart = true;
					}
				}
				break;
			case KeyEvent.VK_ENTER:
				if(modes == POP){
					if (menuCntss == 0) {
						mMode = M_PLAY;
						resets(M_PLAY);
					} else if (menuCntss == 1) {
						loading = true;
						new Thread(new Runnable() {
							public void run() {
								if(loading){
									
									try {
										sm.loadBackgroundImage( new URL(StateValue.liveResource + "teeth/bg/teeth_title.gif"));
									} catch (MalformedURLException e) {
										e.printStackTrace();
									}
									
									loading = false;
								}
							}
						}).start();
						mMode = M_TITLE;
						resets(M_TITLE);
						
					}
				}
				
				break;
			case Constant.KEY_PREV:
				if(cnt10 == 0){
					if(POPUPss == false){
						modes = POP;
						POPUPss = true;
						cnt10 = 0;
					}else if(POPUPss == true){
						modes = 0;
						POPUPss = false;
						cnt10 = 0;
					}
				}
				break;
			}
			break;
			
		case M_END:
			switch (key) {
			case KeyEvent.VK_LEFT:
				if(POPUPe == true){
					menuCnte = (menuCnte + 1) %2;
					switch (menuCnte){
					case 0:
						snd.playSound("snd/teeth/common/retry.mp2");
						break;
					case 1:
						snd.playSound("snd/teeth/common/stop.mp2");
						break;
					}
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(POPUPe == true){
					menuCnte = (menuCnte + 1) %2;
					switch (menuCnte){
					case 0:
						snd.playSound("snd/teeth/common/retry.mp2");
						break;
					case 1:
						snd.playSound("snd/teeth/common/stop.mp2");
						break;
					}
				}
				break;
			case KeyEvent.VK_UP:
				break;
			case KeyEvent.VK_DOWN:
				break;
			case KeyEvent.VK_ENTER:
				if(POPUPe == true){
					if (menuCnte == 0) {
						mMode = M_DEFAULT;
						resets(M_DEFAULT);
					} else if (menuCnte == 1) {
						loading = true;
						
						SceneManager.getInstance().send_gravity(11);
						
						new Thread(new Runnable() {
							public void run() {
								if(loading){
									
									try {
										sm.loadBackgroundImage( new URL(StateValue.liveResource + "teeth/bg/teeth_title.gif"));
									} catch (MalformedURLException e) {
										e.printStackTrace();
									}
									
									loading = false;
									aniCnt = 0;
									viewchange = 0;
								}
							}
						}).start();
						mMode = M_TITLE;
						resets(M_TITLE);
						
					}
				}
				if(aniCnt >= 60)
					POPUPe = true;

				break;
			case Constant.KEY_PREV:
				break;
			}
			break;
			
		}
	}
	
	public void process(int elapsedtime) {
		switch (mMode) {
		case M_DEFAULT:
			if(soundflag){
				if(menuCnts == 0){
					snd.playSound("snd/teeth/common/retry.mp2");
				}else if(menuCnts ==1){
					snd.playSound("snd/teeth/common/stop.mp2");
				}
				soundflag=false;
			}
			
			if(viewchange >= 7) {
				mMode = M_PLAY;
				resets(M_PLAY);
			}
			break;
			
		case M_PLAY:
			if(modeCnt >= 30){
				if(cnt10 == 0) {
					mMode = M_END;
					resets(M_END);
				}
			}
			break;
		}
	}
	
	void sound(){
		if(aniCnt == 1)
			snd.playSound("snd/teeth/effect/toc.mp2");
		if(aniCnt == 10)
			snd.playSound("snd/teeth/effect/water.mp2");
		if(aniCnt == 35)
			snd.playSound("snd/teeth/effect/twinkle.mp2");
		if(aniCnt == 60)
			snd.playSound("snd/teeth/voice/ed.mp2");
	}
	
	void resets(int mode) {
		switch (mode) {
		case M_TITLE:
			menuCnt = true;
			aniCnt = 0;
		case M_DEFAULT:
			cnt4 = 0;
			cnt8 = 0;
			cnt18 = 0;
			mode = 0;
			viewchange = 0;		
			aniAlphaCnt = 0;	
			menuCnts = 0; 		
			btCnt = 0;		
			frame = 0;
			soundflag = false;
			POPUP = false;
		case M_PLAY:
			cnt3 = 0;
			cnt4s = 0;
			cnt18s = 0;
			cnt10 = 0;
			btCnts = 0;
			menuCntss = 0; 
			aniAlphaCnts = 0;	
			
			modes = 0;
			modeCnt = 0;
			bbStart = false;
			POPUPss = false;
		case M_END:
			cnt3a = 0;
			cnt4a = 0;
			cnt18a = 0;
			water = 0;
			hand = 0;
			twinkle = 0;
			hand2 = 0;	
			btCnte = 0;	
			menuCnte = 0; 
			eaniAlphaCnt = 0;	
			aniAlphaCnt2 = 1.0f;	
			
			POPUPe = false;
			
			aniCntt = 1;	
			
			
			
			////////////////////////////////////////////////////////////
//			menuCnt = true;
//			aniCnt = 0;
//			cnt4 = 0;
//			cnt8 = 0;
//			cnt18 = 0;
//			mode = 0;
//			viewchange = 0;		
//			aniAlphaCnt = 0;	
//			menuCnts = 0; 		
//			btCnt = 0;		
//			frame = 0;
//			soundflag = false;
//			POPUP = false;
//			cnt3 = 0;
//			cnt4s = 0;
//			cnt18s = 0;
//			cnt10 = 0;
//			btCnts = 0;
//			menuCntss = 0; 
//			aniAlphaCnts = 0;	
//			modes = 0;
//			modeCnt = 0;
//			bbStart = false;
//			POPUPss = false;
//			cnt3a = 0;
//			cnt4a = 0;
//			cnt18a = 0;
//			water = 0;
//			hand = 0;
//			twinkle = 0;
//			hand2 = 0;	
//			btCnte = 0;	
//			menuCnte = 0; 
//			eaniAlphaCnt = 0;	
//			aniAlphaCnt2 = 1.0f;	
//			POPUPe = false;
//			aniCntt = 1;	
//			cnt4 = 0;
//			cnt8 = 0;
//			cnt18 = 0;
//			viewchange = 0;		
//			aniAlphaCnt = 0;	
//			menuCnts = 0; 		
//			btCnt = 0;		
//			frame = 0;
//			soundflag = false;
//			POPUP = false;
//			cnt3 = 0;
//			cnt4s = 0;
//			cnt18s = 0;
//			cnt10 = 0;
//			btCnts = 0;
//			menuCntss = 0; 
//			aniAlphaCnts = 0;	
//			modes = 0;
//			modeCnt = 0;
//			bbStart = false;
//			POPUPss = false;
//			cnt3a = 0;
//			cnt4a = 0;
//			cnt18a = 0;
//			water = 0;
//			hand = 0;
//			twinkle = 0;
//			hand2 = 0;	
//			btCnte = 0;	
//			menuCnte = 0; 
//			eaniAlphaCnt = 0;	
//			aniAlphaCnt2 = 1.0f;	
//			POPUPe = false;
//			aniCntt = 1;	
			break;
		}
	}
	
	public void destroyScene() {
		
		SceneManager.getInstance().load_kind = true;
		
		for (int i = 0; i < imgPath.length; i++) {
			sm.removeImage(imgGame[i]);
			imgGame[i] = null;
			if ( i%5 == 0 ) SceneManager.getInstance().repaint();
		}
		imgGame = null;
		
		if (imgLoad[0] != null ) {
			sm.removeImage(imgLoad[0]);
			imgLoad[0] = null;
		}
		if (imgLoad[1] != null ) {
			sm.removeImage(imgLoad[1]);
			imgLoad[1] = null;
		}
		imgLoad = null;
		
		snd.dispose();
		snd = null;
		
		sm = null;
		
	}

}
