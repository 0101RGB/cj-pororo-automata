package pororo.com.screen;

import java.awt.Dimension;
import java.net.URL;

import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.havi.ui.*;

import pororo.com.SceneManager;
import pororo.com.log.Log;


public class CJ_ScreenController implements ScreenController, ResourceClient {
	
	private HBackgroundDevice hBackDev;
	private HStillImageBackgroundConfiguration hBackConf;
	private HBackgroundImage hImage;
	
	private boolean reserved = false;
	
	public void init() {
		HScreen defaultScreen = HScreen.getDefaultHScreen();
		SceneManager.getInstance().isErrImg = false;
		
		try {
			HBackgroundDevice hBgDeivce = defaultScreen.getDefaultHBackgroundDevice();
			
			if(reserved = hBgDeivce.reserveDevice(this)){
			
				HBackgroundConfigTemplate hBGCfg = new HBackgroundConfigTemplate();
				hBGCfg.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, HBackgroundConfigTemplate.REQUIRED);
				hBGCfg.setPreference(HBackgroundConfigTemplate.PIXEL_RESOLUTION, new Dimension(960, 540), HBackgroundConfigTemplate.REQUIRED);

				HBackgroundConfiguration backconfig = hBgDeivce.getBestConfiguration(hBGCfg);
				if (backconfig == null) {
					backconfig = hBgDeivce.getDefaultConfiguration();
					Log.trace("HBackgroundConfiguration::get DEFAULT Configuration!!");
				}
				hBgDeivce.setBackgroundConfiguration(backconfig);
				
				HBackgroundConfiguration currConfig = hBgDeivce.getCurrentConfiguration();
				Dimension currentResolution = currConfig.getPixelResolution();
				Log.trace("HBackgroundDevice::currentResolution: "+ currentResolution);
				
				if (backconfig instanceof HStillImageBackgroundConfiguration) {
					hBackConf = (HStillImageBackgroundConfiguration)backconfig;
					hBackDev = hBgDeivce;
				}
			} else {
				Log.errmsg(this, "reserveBackgroundDevice failed");
			}
		}catch(Exception e) {
			e.printStackTrace();
			SceneManager.getInstance().isErrImg = true;
			Log.errmsg(this, "backgroundConfig failed");
			Log.error(this, e);
		}

		if(!reserved)
			dispose();
	}
	
	public void dispose() {
		if (hBackDev != null) {
			hBackDev.releaseDevice();
			hBackDev = null;
			hBackConf = null;
		}
		if (hImage != null) {
			hImage.flush();
			hImage = null;
		}
	}

	private void flush_and_keep(HBackgroundImage keep) {
		if (hImage != null) {
			hImage.flush();
			hImage = null;
		}
		hImage = keep;
	}
	
	public boolean display(String filename) {
		if(hBackConf != null) {
			HBackgroundImage backimage = new HBackgroundImage(filename);
			try {
				SceneManager.getInstance().isErrImg = false;
				hBackConf.displayImage(backimage);
			} catch (Exception e) {
				SceneManager.getInstance().isErrImg = true;
				Log.error(this, e);
				return false;
			}
			flush_and_keep(backimage);
		}
		return true;
	}
	
	public boolean display(URL filename) {
		if(hBackConf != null) {
			HBackgroundImage backimage = new HBackgroundImage(filename);
			try {
				SceneManager.getInstance().isErrImg = false;
				hBackConf.displayImage(backimage);
			} catch (Exception e) {
				Log.error(this, e);
				SceneManager.getInstance().isErrImg = true;
				return false;
			}
			flush_and_keep(backimage);
		}
		return true;
	}
	
	public boolean display(byte[] filebyte) {
		if(hBackConf != null) {
			HBackgroundImage backimage = new HBackgroundImage(filebyte);
			try {
				hBackConf.displayImage(backimage);
			} catch (Exception e) {
				Log.error(this, e);
				return false;
			}
			flush_and_keep(backimage);
		}
		return true;
	}
	
	public void notifyRelease(ResourceProxy arg0) {
		if (hBackDev != null)
			hBackDev.releaseDevice();
	}
	public void release(ResourceProxy arg0) {
		if (hBackDev != null)
			hBackDev.releaseDevice();
	}
	public boolean requestRelease(ResourceProxy arg0, Object arg1) {
		return false;
	}

}