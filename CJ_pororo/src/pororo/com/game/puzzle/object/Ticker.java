package pororo.com.game.puzzle.object;

// 이펙트를 관리
public class Ticker {
	// 이동 + 투명 + 에니메이션 + 반복 + 재생후 사라지기
	static final int NONE = -1; // 완료된것 없음
	static final int MOVE = 0; // 이동 완료후
	static final int ALPHA = 1; // 완전히 투명해진후
	static final int ANIMATION = 2; // 에니메이션 종료 후
	
	public void setNowPos(int x, int y) {
		//현재좌표
	}
	
	public void setAlpha(float[] a) {
		// 투명효과
	}
	
	public void setMovePos(int x, int y) {
		// 이동할 좌표
	}

	public void setAnimation(int[] a) {
		// 에니메이션
	}

	public void setRepeat(boolean r) {
		// 반복
	}
	
	public void setFinishHide(boolean h) {
		// 종료후 사라짐
	}
	
	public void start() {
		// 시작
	}
	
	public void finish() {
		// 종료
	}
	
	public int process() {
		// 종료후 사라짐
		int value = NONE;
			
		return value;
	}

	public void destroy() {
	}
}
