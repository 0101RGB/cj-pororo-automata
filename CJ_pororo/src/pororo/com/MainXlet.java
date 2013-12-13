package pororo.com;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import pororo.com.log.Log;

public class MainXlet implements Xlet, Runnable
{

	public SceneManager sceneManager = null;
	private XletContext xletCtx;

	public void initXlet(XletContext ctx) throws XletStateChangeException
	{
		xletCtx = ctx;
		Log.trace("initXlet=============================1");
	}

	public void startXlet() throws XletStateChangeException
	{
		Log.trace("startXlet============================0");

		Properties prop = null;
		prop = new Properties();
		InputStream in = MainXlet.class.getClassLoader().getResourceAsStream("config/xlet.properties");
		try
		{
			if (in == null)
			{
				in = new FileInputStream("config/xlet.properties");
			}
			prop.load(in); // 만약에 in이 null이면 NullPointer에러남..
		}
		catch (Exception e)
		{
			// IOException
			Log.error(this, e);
		}

		// (Properties의 load에 실패했거나, 해당값이 없는경우엔 null 값이 리턴됨)
		StateValue.play_log_level = getPropertyAsInt(prop, "log.level");
		StateValue.PRICE_DAY = getPropertyAsInt(prop, "price.day");
		StateValue.PRICE_MONTH = getPropertyAsInt(prop, "price.month");
		StateValue.gravity_server = getProperty(prop, "service.urls");
		//		StateValue.testResource = StateValue.gravity_server + "cj_portal/"; 
		StateValue.testResource = StateValue.gravity_server + "cj_pororo_s2/";
		StateValue.liveResource = StateValue.testResource;
		StateValue.version = getPropertyVersion(prop, "version");
		// 결제용
		StateValue.EXPIRE_DATE = getProperty(prop, "expire");
		StateValue.SERVICE_ID = getProperty(prop, "service.id");
		StateValue.PRODUCT_DAY_ID = getProperty(prop, "product.day.id");
		StateValue.PRODUCT_MONTH_ID = getProperty(prop, "product.month.id");
		StateValue.RP_SERVER_LIVE = getProperty(prop, "rp.live.url");
		StateValue.RP_SERVER_TEST = getProperty(prop, "rp.test.url");
		StateValue.isLive = getPropertyAsBoolean(prop, "islive");

		prop.clear();
		prop = null;
		try
		{
			in.close();
		}
		catch (Exception e)
		{
			// IOException
		}
		in = null;

		// !!!!! resident 테스트 할때 !!!!!
		/* (TEST) */
		new Thread(this, "testMainThread").start();

		/* (NORMAL) */
		//		System.out.println("######## pororo init start #########");
		//		this.sceneManager = SceneManager.createInstance();
		//		this.sceneManager.init(xletCtx);
		/*-*/

		Log.trace("startXlet============================1");
	}

	// resident 테스트 할때..
	public void run()
	{
		try
		{
			Thread.sleep(150000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("######## pororo thread start #######");
		this.sceneManager = SceneManager.createInstance();
		this.sceneManager.init(xletCtx);
	}

	public void pauseXlet()
	{
	}

	public void destroyXlet(boolean unconditional) throws XletStateChangeException
	{
		if (sceneManager != null)
		{
			sceneManager.setVisible(false);
			sceneManager.dispose();
			sceneManager = null;
		}

		xletCtx = null;

		System.out.println("destroyXlet==========================1");
		Log.dispose();

		StateValue.gravity_server = null;
		StateValue.testResource = null;
		StateValue.liveResource = null;
		StateValue.version = null;
		// 결제용
		StateValue.EXPIRE_DATE = null;
		StateValue.SERVICE_ID = null;
		StateValue.PRODUCT_DAY_ID = null;
		StateValue.PRODUCT_MONTH_ID = null;
		StateValue.RP_SERVER_LIVE = null;
		StateValue.RP_SERVER_TEST = null;
	}

	private String getPropertyVersion(Properties p, String name)
	{
		String value = p.getProperty(name);
		Log.info(name + " >>> " + value);
		return value;
	}

	private String getProperty(Properties p, String name)
	{
		String value = p.getProperty(name);
		if (Log.ALL)
			Log.trace(name + " >>> " + value);
		return value;
	}

	private int getPropertyAsInt(Properties p, String name)
	{
		String value = p.getProperty(name);
		if (Log.ALL)
			Log.trace(name + " >>> " + value);
		if (value != null)
		{
			return Integer.parseInt(value);
		}
		return -1;
	}

	private boolean getPropertyAsBoolean(Properties p, String name)
	{
		String value = p.getProperty(name);
		if (Log.ALL)
			Log.trace(name + " >>> " + value);
		if (value != null)
		{
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

}