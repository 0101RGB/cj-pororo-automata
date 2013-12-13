package pororo.com.game.maze.object;

import java.awt.Graphics2D;
import java.awt.Image;

public class Effect {
	private Image resource[];
	
	private float posX;
	private float posY;
	
	private int maxAniCount = 0;
	
	private int aniIndex = 0;
	
	private boolean	repeat = false;
	
	private boolean	animation = false;
	
	private boolean	process = false;
	
	private boolean	show = false;
	
	private boolean finish = false;
	
//	public void setImage(float nX, float nY, Image img) {// 이동없음 + 에니메이션 없음
//		
//		posX = nX;
//		posY = nY;
//		
//		process = true;
//		
//		show = true;
//		
//		resource = new Image[1];
//		resource[0] = img;
//		finish = false;
//	}
	
	public void setImage(float nX, float nY, Image[] img, boolean r) {// 이동 없음 + 에니메이션 있음
		posX = nX;
		posY = nY;
		
		maxAniCount = img.length;
		aniIndex = 0;
		
		animation = true;
		
		process = true;
		
		show = true;
		
		repeat = r;
		
		resource = img;
		finish = false;
	}
	public void draw(Graphics2D g) {
		if(!show) {
			return;
		}
		if(resource != null) {
			g.drawImage(resource[aniIndex], (int)posX, (int)posY, null);
		}
	}
	
	public boolean process() {
		if(!process) {
			return process;
		}
		//Animation
		if(animation) {
			aniIndex = (aniIndex+1);
			if(aniIndex >= maxAniCount){
				if(repeat){
					aniIndex = aniIndex%maxAniCount;
				}
				else {
					animation = false;
				}
			}
		}
		if(!animation) {
			process = false;
			show = false;
			resource = null;
			finish = true;
		}
		return process;
	}
	public void hide() {
		show = false;	
		process = false;	
		resource = null;
	}
	public boolean getState() {
		return process;
	}
	public boolean getFinish() {
		if(finish) {
			finish = false;
			return true;
		}
		return false;
	}
}
