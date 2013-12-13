package pororo.com.game.maze.object;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;

public class Ticker {
	public Image resource[];

	private float posX;
	private float posY;

	private float speedX;
	private float speedY;

	private float moveX;
	private float moveY;

	private int turn;

	private int maxAniCount = 0;

	private int aniIndex = 0;

	private int movePos[][];

	private boolean repeat = false;

	private boolean animation = false;

	private boolean process = false;

	private boolean move = false;

	private boolean show = false;

	private boolean isFix = false;

	private float alpha = 1F;
	
	private boolean finish = false;

	public void setImage(float nX, float nY, float mX, float mY, int t,
			Image img, boolean r, float a) {// 이동있음 + 에니메이션 없음
		posX = nX;
		posY = nY;

		turn = t;

		// 스피드 계산
		speedX = (mX - nX) / t;
		speedY = (mY - nY) / t;

		moveX = mX;
		moveY = mY;

		aniIndex = 0;

		animation = r;
		
		animation = false;

		process = true;

		show = true;

		move = true;

		repeat = r;

		resource = new Image[1];
		resource[0] = img;
		maxAniCount = resource.length;

		alpha = a;
		
		isFix = false;
		finish = false;
	}

	public void setImage(int nX, int nY, int[][] mP, Image img, boolean r,
			float a) {// 이동있음 + 에니메이션 없음

		movePos = mP;

		posX = nX;
		posY = nY;

		turn = mP[0].length;

		aniIndex = 0;

		animation = r;
		
		animation = false;

		process = true;

		show = true;

		move = true;

		repeat = r;

		resource = new Image[1];
		resource[0] = img;
		maxAniCount = resource.length;

		alpha = a;

		isFix = true;
		finish = false;
	}

	public void setImage(float nX, float nY, float mX, float mY, int t,
			Image[] img, boolean r, float a) {// 이동있음 + 에니메이션 있음
		posX = nX;
		posY = nY;

		turn = t;

		// 스피드 계산

		speedX = (mX - nX) / t;
		speedY = (mY - nY) / t;

		moveX = mX;
		moveY = mY;

		maxAniCount = img.length;

		aniIndex = 0;

		animation = true;

		process = true;

		show = true;

		move = true;

		repeat = r;

		resource = img;

		alpha = a;
		
		isFix = false;
		finish = false;
	}

	public void draw(Graphics2D g) {
		if (!show) {
			return;
		}
		if (alpha < 1) {
			float a;
			if (turn > 0) {
				a = alpha / (float) turn;
			} else {
				a = alpha;
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1 - a));
		}
		if (!isFix) {
			if (resource != null) {
				g.drawImage(resource[aniIndex], (int) posX, (int) posY, null);
			}
		} else {
			if (resource != null && turn > 0 &&turn <= 5 && aniIndex >= 0 && aniIndex < resource.length) {
				g.drawImage(resource[aniIndex], (int) posX - movePos[0][5-turn], (int) posY - movePos[1][5-turn], null);
			}
		}
		if (alpha < 1) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1));
		}
	}

	public boolean process() {
		if (!process) {
			return process;
		}
		// Move
		if (move) {
			if (turn > 0) {
				turn--;
				if (!isFix) {
					// 이동처리
					posX += speedX;
					posY += speedY;
				}
			} else {
				if (!isFix) {
					posX = moveX;
					posY = moveY;
				}
				alpha = 1;
				move = false;
				finish = true;
			}
		}
		// Animation
		if (animation) {
			aniIndex = (aniIndex + 1);
			if (aniIndex >= maxAniCount) {
				if (repeat) {
					if (maxAniCount <= 1) {
						aniIndex = 0;
						animation = false;
					} else {
						aniIndex = aniIndex % maxAniCount;
					}
				} else {
					animation = false;
				}
			}
		}
		if (!move && !animation) {
			if (!repeat) {
				show = false;
				resource = null;
			}
			process = false;
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
	public float getX() {
		return posX;
	}
	public float getY() {
		return posX;
	}
	public boolean getFinish() {
		if(finish) {
			finish = false;
			return true;
		}
		return false;
	}
}
