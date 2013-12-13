package pororo.com.automata;

import java.awt.Component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import com.mobience.automata.Automata;

import pororo.com.SceneManager;

public class KeyboardComponent extends Component
{
	private int X = 330;
	private int Y = 71;
	
	public final int ID_INPUT = 0;
	public final int ID_ERROR = 1;
	
	public int automata_Mode;
	public int XPos;
	public int YPos;
	public int cursorPos;
	
	private int Center_X = 960/2-330;
	
	private Image img_bg = null;
	private Image img_ime[] = new Image[4];
	private Image img_ime_cursor = null;
	private Image img_imeMode[] = new Image[4];
	
	public int state=0;

	public void destroy()
	{
		if(img_bg != null){
			SceneManager.getInstance().removeImage(img_bg);
			img_bg = null;
		}
		if(img_ime != null)
		{
			for (int i = 0; i < 4; i++) 
			{
				if(img_ime[i] != null)
				{
					SceneManager.getInstance().removeImage(img_ime[i]);
					img_ime[i] = null;
				}
			}
		}
		if(img_ime_cursor != null)
		{
			SceneManager.getInstance().removeImage(img_ime_cursor);
			img_ime_cursor = null;
		}
		
		if(img_imeMode != null)
		{
			for (int i = 0; i < 4; i++) 
			{
				if(img_imeMode[i] != null)
				{
					SceneManager.getInstance().removeImage(img_imeMode[i]);
					img_imeMode[i] = null;
				}
			}
		}
	}
	
	public void draw_imepupup(Graphics g) 
	{
		g.setColor(new Color(53, 53, 53));
		g.setFont(new Font("Bold", 0, 19));
		DrawStr(g, "아이디입력", Center_X + 1, 120-71);
		
		g.setFont(new Font("Bold", 0, 15));
		switch (state)
		{
		case ID_INPUT:
			g.setColor(new Color(0x436819));
			DrawStr(g, "최대 6자까지 입력 가능합니다", Center_X, 204-71);
			break;
		case ID_ERROR:
			g.setColor(new Color(0xb70052));
			DrawStr(g, "사용할 수 없는 아이디 입니다 ", Center_X, 204-71);
			break;
		}
	}

	public Image getImage(String s) {
		return SceneManager.getInstance().getImage(s);
	}
	public void DrawImg(Graphics g, Image img, int img_x, int img_y, int Align) {
		SceneManager.getInstance().DrawImg(g, img, img_x, img_y, Align);
	}

	public void DrawStr(Graphics g, String str, int str_x, int str_y) {
		SceneManager.getInstance().DrawStr(g, str, str_x, str_y);
	}

	public void paint(Graphics g)
	{
		//	    	if (entry != null)								// 실명 인증에서 처음에 entry가 보이지 않아서 추가
		//	    		entry.paint(g);

		if(img_bg == null){
			img_bg = getImage("img/automata/imepop.png");
		}
		g.drawImage(img_bg, 0, 0, null);
		switch (automata_Mode)
		{
		case Automata.KOREAN_MODE:
			if(img_ime[0] == null){
				img_ime[0] = getImage("img/automata/ime_b0.png");
			}
			if(img_imeMode[0] == null){
				img_imeMode[0] = getImage("img/automata/ime_a0.png");
			}
			g.drawImage(img_ime[0], 369-X, 225-Y, null);
			g.drawImage(img_imeMode[0], 377-X, 149-Y, null);
			break;
		case Automata.ENGLISH_LARGE_MODE:
			if(img_ime[1] == null){
				img_ime[1] = getImage("img/automata/ime_b1.png");
			}
			if(img_imeMode[1] == null){
				img_imeMode[1] = getImage("img/automata/ime_a1.png");
			}
			g.drawImage(img_ime[1], 369-X, 225-Y, null);
			g.drawImage(img_imeMode[1], 377-X, 149-Y, null);
			break;
		case Automata.ENGLISH_SMALL_MODE:
			if(img_ime[2] == null){
				img_ime[2] = getImage("img/automata/ime_b2.png");
			}
			if(img_imeMode[2] == null){
				img_imeMode[2] = getImage("img/automata/ime_a2.png");
			}
			g.drawImage(img_ime[2], 369-X, 225-Y, null);
			g.drawImage(img_imeMode[2], 377-X, 149-Y, null);
			break;
		case Automata.NUMERIC_MODE:
			if(img_ime[3] == null){
				img_ime[3] = getImage("img/automata/ime_b3.png");
			}
			if(img_imeMode[3] == null){
				img_imeMode[3] = getImage("img/automata/ime_a3.png");
			}
			g.drawImage(img_ime[3], 369-X, 225-Y, null);
			g.drawImage(img_imeMode[3], 377-X, 149-Y, null);
			break;
		}
		
		if(img_ime_cursor == null){
			img_ime_cursor = getImage("img/automata/ime_cursor.png");
		}
		g.drawImage(img_ime_cursor, (366-X)+78*XPos, (222-Y)+33*YPos, this);
		
		draw_imepupup(g);
	}
}
