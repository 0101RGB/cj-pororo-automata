package pororo.com.automata;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.havi.ui.HInvalidLookException;
import org.havi.ui.HSinglelineEntry;
import org.havi.ui.HState;
import org.havi.ui.HVisible;

/**
 * CJ SinglenlineEntry
 *
 * @author guess
 * @since 2011. 05. 11
 */
public class SinglelineEntry extends HSinglelineEntry implements Constant
{
	public CjLook look;
	private int removeKeys[];					// 방향키 좌우 (커서의 움직임 때문에 삭제한다.)
	private int oldPos;

	/**
	 *
	 * @param maxChars
	 */
	public SinglelineEntry(int maxChars) {
		super(maxChars);
	}

	public void removeKey(int keys[]) {
		removeKeys = keys;
	}

	public void setFontColor(Color color) {
		look.setFontColor(color);
	}

	public void setLook() {
		if (look == null) {
			look = new CjLook(this);
		}
		try {
			super.setLook(look);
			this.setFont(new Font("tsTvGothic95" , Font.PLAIN, 22 ));
			this.setBackground(new Color(99, 99, 99));
		} catch (HInvalidLookException e) {
			e.printStackTrace();
		}
	}

	public void setEchoChar() {
		super.setEchoChar('＊');
	}

	private String textContent()
	{
		return this.getTextContent(HState.NORMAL_STATE);
	}

	public class CjLook extends org.havi.ui.HSinglelineEntryLook 
	{
		//		private HVisible visi;
		private Color fontColor;				// 글자 색
		private FontMetrics fontMetrics;

		private String textStr;
		private int strWidth;
		private int strHeight;
		
		private int charW;

		public void setFontColor(Color color) {
			fontColor = color;
		}

		public CjLook(HVisible visi) {
			//			this.visi = visi;
		}

		public void renderBorders(Graphics g, HVisible visible, int state) {
		}

		public void showLook(Graphics g, HVisible visible, int state)
		{
//			g.setColor(new Color(128, 128, 128));
//			g.fillRect(0, 0, 160, 30);

			textStr = textContent();
			if(fontMetrics == null){
				fontMetrics = g.getFontMetrics();
			}
			strWidth = fontMetrics.stringWidth(textStr);
			strHeight = fontMetrics.getHeight();
			
			g.setColor(fontColor);
			if(textStr.length() < 1)
			{
				charW = fontMetrics.stringWidth("A");
				g.fillRect(6, strHeight+6, charW, 3);
			}
			else
			{
				char charCnt = textStr.charAt(textStr.length()-1);		    
				charW = fontMetrics.stringWidth(String.valueOf(charCnt));
				g.fillRect(strWidth+6-charW, strHeight+6, charW, 3);
				
//				Log.trace(this, "===========charCnt========= : "+charCnt);
			}
			
			//			visible.setBackground(cl); // 커서바탕
			//			visible.setForeground(new Color(255, 0, 0)); 		// fontcolor 기본색

			if (fontColor != null) {
				visible.setForeground(fontColor);					// fontcolor 지정색
			}

			super.showLook(g, visible, state);
		}
	}
}
