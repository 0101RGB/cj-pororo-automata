package pororo.com.game.teeth;
import org.havi.ui.HSound;

import pororo.com.SceneManager;

public class Sound {
    private HSound gameSound = new HSound();
	public void playSound(String soundFile) {
		if ( SceneManager.isEmul ) return;
	    gameSound.stop();
		try {
		    gameSound.load(soundFile);
		} catch (java.io.IOException ie){
		}
		gameSound.play();
	}
	public void stopSound() {
		if ( SceneManager.isEmul ) return;
		gameSound.stop();
	}
	
	public void dispose() {
	    if( gameSound != null ) {
	        gameSound.stop();
	        gameSound.dispose();
	        gameSound = null;
	    }
	}
}
