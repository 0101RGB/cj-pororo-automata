package pororo.com.log;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.havi.ui.HComponent;

public class LogScreen extends Component {
	
	private Vector syncList; 
	private int viewIndex;
	
	public void destroy() {
		if (syncList != null) {
			syncList.clear();
			syncList = null;
		}
	}
	
	public LogScreen() {
		syncList = new Vector();
	}
	
	public void paint(Graphics g) {
		
		g.setColor(new Color(0, 0, 0, 170));  // gray alpha color
		g.fillRect(0, 0, 720, 480);
		
		Object[] strArray = syncList.toArray();
		
		String sss = null;
		g.setColor(new Color(255, 0, 0));   // red color
		g.setFont(new Font(null, Font.PLAIN, 14));
		//g.fillRect(0, 0, 720, 480);
		
		int size = strArray.length;
		int cnt = 0;
		for (int i=viewIndex; i<size; i++) {
			 sss = (String) strArray[i];
			 //System.out.println("' sss: " + sss);
			 g.drawString(sss, 50, 50 + cnt*20);
			 cnt++;
			 if ((cnt+1) * 20 > 400)
				 break;
		}
	}
	
	public void print(String msg) {
		//System.out.println("' ////////////// msg: " + msg);
		syncList.add(msg);
		
		viewIndex = syncList.size() - 20;  // 대략 한 화면에 20줄이니 적당히..
		if (viewIndex < 0) viewIndex = 0;
		
		if (this.isDisplayable()) {
			//System.out.println("' isDisplayable: " + this.isDisplayable());
			repaint();
		}
	}
	
	public void processKey(int keyCode) {
		switch(keyCode) {
		case KeyEvent.VK_DOWN:
			viewIndex = viewIndex + 10;
			if (viewIndex > syncList.size())
				viewIndex = syncList.size() - 1;
			break;
		case KeyEvent.VK_UP:
			viewIndex = viewIndex - 10;
			if (viewIndex < 0)
				viewIndex = 0;
			
			break;

		}
	}
}
