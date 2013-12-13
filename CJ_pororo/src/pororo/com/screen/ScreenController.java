package pororo.com.screen;

import java.net.URL;

import org.havi.ui.HScene;


public interface ScreenController {
	
	public void init();
	public void dispose();

	public boolean display(String filename);
	public boolean display(URL filename);
	public boolean display(byte[] filebyte);
}
