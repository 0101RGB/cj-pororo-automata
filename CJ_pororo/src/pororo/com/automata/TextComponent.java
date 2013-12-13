package pororo.com.automata;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import org.havi.ui.HState;
import org.havi.ui.HVisible;

public class TextComponent extends Container implements Constant
{
	public SinglelineEntry hEntry	 = null;
	
	public String autoText;
	public String strCurrent ;
	
	public int maxCount = 6;
	
	public TextComponent()
	{
	}
	public void destroy()
	{
		if(hEntry != null)
		{
			hEntry.setVisible(false);
			hEntry = null;
		}
	}
	 /**
     * 케릭터 수를 설정한다.
     * @param MaxCount : 케릭터수
     * @since 2008. 11. 10
     */
    public void setMaxCount (int MaxCount){
    	this.maxCount = MaxCount-1 ;
    }
	  /**
     * 엔트리 설정.
     * @param x : x 시작 좌표
     * @param y : y 시작좌표
     * @param width : 가로 범위
     * @param height : 세로 범위
     * @param maxChars : 글자수
     * @since 2008. 11. 10
     */
    public void setEntry(int x, int y, int width, int height, int maxChars)
    {
    	try 
    	{
	    	if(hEntry != null){
	    		hEntry = null;
	    	}

	    	hEntry = new SinglelineEntry(maxChars);
	    	hEntry.setMaxChars(maxChars);
	    	setMaxCount(maxChars);
	    	hEntry.setBounds(x, y, width, height);
	    	hEntry.setFont(new Font("tsTvGothic95" , Font.PLAIN, 22 ));
	    	hEntry.setTextContent("", HState.FIRST_STATE);
	    	this.add(hEntry);
	    	hEntry.setVisible(true);
	    	hEntry.setLook();
	    	setFontColor(new Color(0x000000));
	    	hEntry.setHorizontalAlignment(HVisible.VALIGN_TOP);
	    	hEntry.repaint();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
 
    public void setFontColor(Color color) {
    	hEntry.setFontColor(color);
    }

    public SinglelineEntry getEntry() {
    	return hEntry;
    }

    public String getText() 
    {
    	String strResult = null;
    	if (hEntry != null) {
    		strResult = hEntry.getTextContent(HState.NORMAL_STATE);
    	}
    	return strResult;
    }

    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if( hEntry != null){
    		hEntry.setVisible(visible);
    	}
    }
    
//    public void draw(Graphics g)
//    {
//		g.setColor(new Color(128, 128, 128));
//		g.fillRect(0, 0, 252, 87);
//    }
}
