package pororo.com.game.maze.object;

import java.awt.Graphics2D;


public abstract class GameObject
{
    public abstract boolean isKeyInput();
    public abstract void keyEvent(int event);
    public abstract void process();
    public abstract void draw(Graphics2D g);
    public abstract void start();
    public abstract void reStart();
    public abstract void pause();
    public abstract void destroy();
}
