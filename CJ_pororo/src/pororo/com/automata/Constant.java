package pororo.com.automata;

import java.awt.event.KeyEvent;

import org.havi.ui.event.HRcEvent;

import pororo.com.SceneManager;

public interface Constant {
	public static final int KEY_Red = HRcEvent.VK_COLORED_KEY_0;      	//적색
	public static final int KEY_Yellow = HRcEvent.VK_COLORED_KEY_1;   	//노랑
	public static final int KEY_Green = HRcEvent.VK_COLORED_KEY_2;    	//녹색
	public static final int KEY_Blue = HRcEvent.VK_COLORED_KEY_3;     	//파랑

//	public static final int KEY_DVowel    = OCRcEvent.VK_RESERVE_2;
//	public static final int KEY_Remove = KeyEvent.VK_BACK_SPACE ;		//지우기.
	public static final int KEY_Remove = KeyEvent.VK_F8 ;		//CJ 지우기.
	public static final int KEY_Space     = KeyEvent.VK_F10;
	public static final int KEY_InputMode = KeyEvent.VK_F7;

//	public static final int KEY_PREV = 608;    // T-BROAD & CnM
//	public static final int KEY_PREV = 607;    // CJ
	public static final int KEY_PREV = 607;
}
