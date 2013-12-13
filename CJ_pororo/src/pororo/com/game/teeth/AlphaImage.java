package pororo.com.game.teeth;

import java.awt.Graphics;

import org.dvb.ui.DVBAlphaComposite;
import org.dvb.ui.DVBGraphics;
import org.dvb.ui.UnsupportedDrawingOperationException;

public class AlphaImage
{
    public static void setAlpha(Graphics g, float depth)
    {
    	if(depth>=1.0) depth = 1.0f;
        try
        {
            ((DVBGraphics)g).setDVBComposite(DVBAlphaComposite.getInstance(DVBAlphaComposite.SRC_OVER, depth));
        }catch(UnsupportedDrawingOperationException e)
        {
            e.printStackTrace();
        }
    }
}
