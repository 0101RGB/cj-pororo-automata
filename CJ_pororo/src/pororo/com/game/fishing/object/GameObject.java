
package pororo.com.game.fishing.object;

import java.awt.Graphics2D;

import pororo.com.framework.TimeController;
import pororo.com.game.fishing.Resource;


public abstract class GameObject
{
//	public Resource resource = Resource.getInstance();
    public abstract boolean isKeyInput();
    public abstract void keyEvent(int event);
    public abstract void process();
    public abstract void draw(Graphics2D g);
    public abstract void setAnimation(int aniState);
    public abstract void start();
    public abstract void reStart();
    public abstract void pause();
    public abstract void destroy();
}
