package pororo.com.game.maze;


public final class KeyEvent {
	private static KeyEvent instance = new KeyEvent();

	// 게임정보
	// private GameData gameData = GameData.getInstance();

	public static KeyEvent getInstance() {
		if (instance == null)
			instance = new KeyEvent();
		return instance;

	}

	public void playKeyEvent(int event) {
		if (GameData.getInstance().pr.isKeyInput()) {
			GameData.getInstance().pr.keyEvent(event);
		}
	}

	public void destroy() {
		instance = null;
	}
}
