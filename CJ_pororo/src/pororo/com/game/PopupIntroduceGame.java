package pororo.com.game;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.havi.ui.event.HRcEvent;

import pororo.com.Lib;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.Constant;
import pororo.com.framework.PropertyWork;

public class PopupIntroduceGame {
	
	public int [] idxList;
	public int totalList;
	public int curList = 0;
	public int curFocus = 0;
	public String [][] cText;
	public String [] name_img;
	public Image img_intro [] = new Image[2];
	public Image img_pc_b_menu = null;
	public Image img_pc_font_num = null;
	public Image img_pc_okbt = null;
	public Image img_cursor_b = null;
	
	public int swchLoad = 0;
	public int swchShow = 0;
	boolean isLoaded = true;
	public  int [] coordIntroTitle = { 205 , 188 };
	public  int [] coordIntroText = { 405 , 265 };
	public  int [] coordIntroIcon = { 397 , 132 };
	
	public void init() {
		PropertyWork pw = null;
			try {
				pw = new PropertyWork( new URL(StateValue.liveResource + "portal/introduceGame/introduceGame.txt"));
				totalList = Integer.parseInt( pw.getProperty("totalTab") );
				idxList = new int[totalList];
								
				int tmpCnt = 0;
				for (int i = 0; i < totalList; i++) {
					idxList[i] = Integer.parseInt(pw.getProperty("tab" + (i +1) + ".count"));
					tmpCnt += idxList[i];
				}
				
				cText = new String[tmpCnt][2];
				name_img = new String[tmpCnt];
				
				int cnt = 0;
				for (int i = 0; i < totalList; i++) {
					for (int j = 0; j < idxList[i]; j++) {
						
						cText[cnt][0] = pw.getProperty("tab" + (i+1) + ".title" + (j+1)); 
						cText[cnt][1] = pw.getProperty("tab" + (i+1) + ".text" + (j+1));
						name_img[cnt] = pw.getProperty("tab" + (i+1) + ".iconName" + (j+1));
						
						cnt++;
					}
				}
				
				img_pc_b_menu = SceneManager.getInstance().getImage( 
						new URL(StateValue.liveResource + "portal/menu/pc_b_menu.png"));
				
				img_pc_font_num = SceneManager.getInstance().getImage(
						new URL(StateValue.liveResource + "portal/menu/pc_font_num.png"));
				
				img_pc_okbt = SceneManager.getInstance().getImage(
						new URL(StateValue.liveResource + "portal/menu/pc_okbt.png") );
				
				img_cursor_b = SceneManager.getInstance().getImage(
						new URL(StateValue.liveResource + "portal/menu/cursor_b.png") );
				
				setImage();
				
				pw.destroy();
				pw = null;
				SceneManager.getInstance().load_kind = false;
				
			} catch (IOException e) { e.printStackTrace();
			}
	}
	
	public void setImage() {
		isLoaded = false;
		
		new Thread(new Runnable() {
			public void run() {
				
				int index = 0;
				for (int i = 0; i < curList; i++) {
					index += idxList[i];
				}
				
				try {
					if ( img_intro[swchLoad] != null ) {
						SceneManager.getInstance().removeImage( img_intro[swchLoad] );
						img_intro[swchLoad] = null;
					}
					
					if ( curFocus == 8 ) {
						img_intro[swchLoad] = SceneManager.getInstance().getImage( 
								new URL( StateValue.liveResource + "portal/introduceGame/intro_closepopup.png" ) );
					} else {
						img_intro[swchLoad] = SceneManager.getInstance().getImage( 
								new URL( StateValue.liveResource + "portal/introduceGame/" + name_img[ index + curFocus] ) );
					}
					
					swchShow = swchLoad;
					swchLoad = swchShow ^ 1;
					
					isLoaded = true;
				} catch (MalformedURLException e) { e.printStackTrace(); }
			}
		}).start();
		
	}
	
	public boolean key_process(int key) {
		if ( !isLoaded ) return false;
		
		if ( key == HRcEvent.VK_LEFT ) {
			curList = (curList +(totalList -1)) % totalList;
			curFocus = 0;
			setImage();
		} else if ( key == HRcEvent.VK_RIGHT ) {
			curList = (curList +1) % totalList;
			curFocus = 0;
			setImage();
		} else if ( key == HRcEvent.VK_UP ) {
			if ( curFocus == 0 ) {
				curFocus = 8;
			} else if ( curFocus == 8 ) {
				curFocus = idxList[curList] -1;
			} else {
				curFocus = (curFocus + (idxList[curList] -1))% idxList[curList];
			}
			setImage();
		} else if ( key == HRcEvent.VK_DOWN ) {
			if ( curFocus == (idxList[curList] -1) ) {
				curFocus = 8;
			} else if ( curFocus == 8 ) {
				curFocus = 0;
			} else {
				curFocus = (curFocus +1)% idxList[curList];
			}
			setImage();
		} else if ( key == HRcEvent.VK_ENTER ) {
			if ( curFocus == 8 ) {
				return true;
			}
		} else if ( key == Constant.KEY_PREV ) {
			return true;
		}
		
		return false;
	}
	
	public void dispose() {
		
		for (int i = 0; i < img_intro.length; i++) {
			if ( img_intro[i] != null ) {
				SceneManager.getInstance().removeImage( img_intro[i] );
				img_intro[i] = null;
			}
		}
		img_intro = null;
		
		if ( img_pc_b_menu != null ) {
			SceneManager.getInstance().removeImage(img_pc_b_menu);
			img_pc_b_menu = null;
		}
		
		if ( img_pc_font_num != null ) {
			SceneManager.getInstance().removeImage(img_pc_font_num);
			img_pc_font_num = null;
		}
		
		if ( img_pc_okbt != null ) {
			SceneManager.getInstance().removeImage(img_pc_okbt);
			img_pc_okbt = null;
		}
		
		if ( img_cursor_b != null ) {
			SceneManager.getInstance().removeImage(img_cursor_b);
			img_cursor_b = null;
		}
		
		if ( idxList != null ) idxList = null;
		if ( cText != null ) cText = null;
		if ( coordIntroIcon != null ) coordIntroIcon = null;
		if ( coordIntroText != null ) coordIntroText = null;
		if ( coordIntroTitle != null ) coordIntroTitle = null;
		if ( name_img != null ) name_img = null;
	}

}
