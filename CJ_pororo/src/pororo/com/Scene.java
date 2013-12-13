package pororo.com;

//import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Scene {
	
	public int mn_state;
	
	//public abstract void draw(Graphics g);
	
	public abstract void draw(Graphics2D g);

	public abstract void process(int elapsedtime);

	public abstract void processKey(Object userEvent, int key);

	public abstract void destroyScene();
}
