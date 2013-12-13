package pororo.com.game.remocon;

import org.havi.ui.HSound;

public class Snds {
	
    HSound gameSound = null;
    
    public Snds()
    {
    	gameSound = new HSound();
    }

	public void loadPlaySound(String soundFile)
	{
		try 
		{
			gameSound.stop();
		    gameSound.load("snd/handphone/" + soundFile);
		    gameSound.play();
		    System.out.println( " PLAY : sound =  " + soundFile );
		    
		} catch (java.io.IOException ie){ System.out.println(ie.getMessage()); }
	}
	
	public void dispose()
	{
	    if(gameSound != null)
	    {
	        gameSound.stop();
	        gameSound.dispose();
	        gameSound = null;
	    }
	}
}
/*-> End Of File <------------------------------------------------------------*/