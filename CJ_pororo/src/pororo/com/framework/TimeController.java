package pororo.com.framework;

public class TimeController {
	private boolean isCheck = false;
	private long currentTime;
	private long time;
	
	public void start(long time) {
		isCheck = true;
		this.time = time;
		currentTime = System.currentTimeMillis(); 
	}
	public void reStart() {
		isCheck = true;
		currentTime = System.currentTimeMillis(); 
	}
	public void pause() {
		isCheck = false;
	}
	public boolean process() {
		if(isCheck) {
			long temp = System.currentTimeMillis();
			if(temp - currentTime > time) {
				currentTime = temp;
				return true;
			}
		}
		return false;
	}
}
