package pororo.com.game.maze.object;

import java.awt.Graphics2D;

import pororo.com.game.maze.Resource;


public class Crong extends GameObject {
	
	public static final int CRONG_IDEL = 0;
	public static final int CRONG_CHEERING = 1;
	public static final int CRONG_GAMEOVER = 2;
	public static final int CRONG_GIVEITEM = 3;
	public static final int CRONG_SUCCESS= 4;
	
	// 크롱 에니메이션 정의
	private int orderIdel[] = {0,1,0,1,0,1,0,1,0,2,3,4};
	private int orderCheering[] = {0,1,2,3,4,5};
	private int orderGameover[] = {0,1};
	private int orderSuccess[] = {0,1,0,2};
	
//	private Resource resource = Resource.getInstance();
	
	// 크롱위치
	public int posX = 821;
	public int posY = 328;
	
	// 그롱 에니메이션 상태
	public int aniState;
	public int aniIndex = 0;
	
	public void destroy() {
	}

	public void draw(Graphics2D g) {
		switch(aniState){
		case CRONG_IDEL:
			g.drawImage(Resource.getInstance().cr[orderIdel[aniIndex]], posX, posY, null);
			break;
		case CRONG_CHEERING:
			g.drawImage(Resource.getInstance().crCheering[orderCheering[aniIndex]], posX, posY, null);
			break;
		case CRONG_GAMEOVER:
			g.drawImage(Resource.getInstance().crGameover[orderGameover[aniIndex]], posX, posY, null);
			break;
		case CRONG_GIVEITEM:
			g.drawImage(Resource.getInstance().crGameover[orderGameover[aniIndex]], posX, posY, null);
			break;
		case CRONG_SUCCESS:
			g.drawImage(Resource.getInstance().crSuccess[orderSuccess[aniIndex]], posX, posY, null);
			break;
		}
	}

	public boolean isKeyInput() {
		return false;
	}

	public void keyEvent(int event) {
		
	}

	public void process() {
		aniIndex++;
		switch(aniState){
		case CRONG_IDEL:
			aniIndex = aniIndex%orderIdel.length;
			break;
		case CRONG_CHEERING:
			if(aniIndex >= orderCheering.length) {
				aniState = CRONG_IDEL;
				aniIndex = 0;
			}
			break;
		case CRONG_GAMEOVER:
		case CRONG_GIVEITEM:
			aniIndex = aniIndex%orderGameover.length;
			break;
		case CRONG_SUCCESS:
			aniIndex = aniIndex%orderSuccess.length;
			break;
		}
	}

	public void setAnimation(int aniState) {
		this.aniState = aniState;
		aniIndex = 0;
	}

	public void pause() {
	}

	public void reStart() {
	}

	public void start() {
		aniState = CRONG_IDEL;
		aniIndex = 0;
	}
	public void setState(int state) {
		//크롱상태 변경
		switch(state){
		case CRONG_IDEL:
			break;
		case CRONG_CHEERING:
			aniState = CRONG_CHEERING;
			aniIndex = 0;
			break;
		case CRONG_GAMEOVER:
			break;
		case CRONG_SUCCESS:
			aniState = CRONG_SUCCESS;
			aniIndex = 0;
			break;
		}
	}
}
