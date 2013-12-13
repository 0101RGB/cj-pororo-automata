package pororo.com;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.tv.xlet.XletContext;

import org.dvb.event.UserEvent;
import org.dvb.ui.DVBColor;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HSceneTemplate;
import org.havi.ui.event.HRcEvent;

import pororo.com.automata.Constant;
import pororo.com.framework.Httpconnection;
import pororo.com.framework.ImagePool;
import pororo.com.framework.URLEncoder;
import pororo.com.game.GameTitle;
import pororo.com.game.MenuScene;
import pororo.com.game.board.BoardGPlay;
import pororo.com.game.fishing.FishingGPlay;
import pororo.com.game.maze.MazeGPlay;
import pororo.com.game.puzzle.PuzzleGPlay;
import pororo.com.game.remocon.RemotePlay;
import pororo.com.game.soccer.SoccerGPlay;
import pororo.com.game.teeth.TeethPlay;
import pororo.com.log.Log;
import pororo.com.payment.CJ_DMCUtil;
import pororo.com.payment.DMCUtil;
import pororo.com.screen.CJ_ScreenController;
import pororo.com.screen.ScreenController;

import com.alticast.navsuite.service.OverlappedDialogHandler;
import com.alticast.navsuite.service.OverlappedUIManager;

public class SceneManager extends Container implements Runnable,
//FIXME <Emul mode 1.>
		OverlappedDialogHandler
{
	public static final boolean isEmul = false;
	//송출할때 라이브러리 체크하기

	private static final long serialVersionUID = -7381474887420301849L;

	private ImagePool imagePool;

	public boolean isErrImg = false;
	private boolean isHDSTB = true;

	public final static int KEY_NONE = -1;
	public final static int SATAE_NONE = 0;
	public final static int SATAE_PORTAL_MENU = 100;
	public final static int SATAE_GAME_MENU = 110;

	public final static int SATAE_BOARDGAMEPLAY = 200;
	public final static int SATAE_SOCCERGAMEPLAY = 300;
	public final static int SATAE_FISHINGGAMEPLAY = 400;
	public final static int SATAE_MAZEGAMEPLAY = 500;
	public final static int SATAE_PUZZLEGAMEPLAY = 600;
	public final static int SATAE_REMOTEPLAY = 700;
	public final static int SATAE_TEETHPLAY = 800;

	public int GameState;
	public final static int STATE_BOARD = 0;
	public final static int STATE_FISHING = 1;
	public final static int STATE_SOCCER = 2;
	public final static int STATE_MAZE = 3;
	public final static int STATE_PUZZLE = 4;
	public final static int STATE_REMOTE = 5;
	public final static int STATE_TEETH = 6;
	public final static String[] gameCode = {
			"game_board", "game_fishing", "game_soccer", "game_maze", "game_puzzle", "game_handphone", "game_teeth"
	};
	public int[] freeGames = new int[SceneManager.gameCode.length];

	private static SceneManager manager;
	private Container scene;
	private ScreenController screenManager;
	private Thread m_thread;
	private Scene curScene;
	private Random m_rnd;

	private Image img_load;
	public Image imgPopupA;
	public Image imgCaution;
	public Image imgButton;

	public boolean load_kind = false;
	public int nextScene;
	public int mn_keyCode;
	public int elapsedTime;
	public int mn_gMenuCur;
	public int mn_nextState;
	public long curTime;
	public long prevTime;
	public int mn_AniCnt = 0;

	public int interest[] = new int[8];
	public boolean isShownNotice = false;
	public static boolean autoBool = false;

	public static boolean sendAccounting = false;
	// ==> 타이틀화면에서 게임종류 전송할지 여부. 티브로드는 이런것 거칠 필요없이 바로 전송해야함.

	private static String sdata = null;
	public int sever_check = 100;
	// process() 함수 내에서 서버에 요청하고자 하는 내용이 있는 경우  1,2,3 중에 값을 지정하여
	//  다음번 루프진입시 지정된 값에 따라 분기하여 서버요청을 하기 위한 용도.
	public static String GameID = null;
	public static String TEMP_ID = null;
	public int SeverKind = 0;
	private int idKind = 0;

	public static final int SERVER_MSG_OK = 0;
	public static final int SERVER_MSG_ERROR = 1;
	public int sever_msg = SERVER_MSG_OK;

	public String ppcard[] = new String[3];

	public static final int TL = 0; // TOP, LEFT
	public static final int TR = 1;
	public static final int TC = 2;
	public static final int CL = 3; // CENTER, LEFT
	public static final int CR = 4;
	public static final int CC = 5;
	public static final int BL = 6; // BOTTOM, LEFT
	public static final int BR = 7;
	public static final int BC = 8;

	private static DMCUtil dmcUtil;

	private static String STBID = null;
	private static String SUBSID = null;

	public String strTmp = null;
	public boolean chkGameState = true;

	private static final String TEST_ID = "뽀로로";

	public static DMCUtil getDMCUtil()
	{
		return dmcUtil;
	}

	SceneManager()
	{
		System.out.println("======================================================");
		System.out.println("Pororo Application Version : " + StateValue.version);
		System.out.println("Pororo server url: " + StateValue.gravity_server);
		System.out.println("======================================================");
	}

	public static synchronized SceneManager createInstance()
	{
		if (manager == null)
		{
			manager = new SceneManager();
		}
		return manager;
	}

	public static SceneManager getInstance()
	{
		return manager;
	}

	private boolean threadRunning = true;

	public void dispose()
	{
		threadRunning = false;
		Log.trace("dispose 0");
		//		scene.setVisible(false);
		if (scene != null)
		{
			scene.remove(this);
			// kt는 deactive()가 없으므로 이걸해줘야함!
			OverlappedUIManager.getInstance().removeOverlappedUI(scene);
		}
		Log.trace("dispose 1");
		if (curScene != null)
		{
			Scene sc_ = curScene;
			curScene = null; // jy.yu run()에서 curScene이 접근되는것을 미리 방지.. 
			sc_.destroyScene();
			sc_ = null;
			Log.trace("dispose 2");
		}
		sdata = null;
		ppcard = null;

		if (img_load != null)
		{
			removeImage(img_load);
			img_load = null;
		}
		if (imgPopupA != null)
		{
			removeImage(imgPopupA);
			imgPopupA = null;
		}
		if (imgCaution != null)
		{
			removeImage(imgCaution);
			imgCaution = null;
		}
		if (imgButton != null)
		{
			removeImage(imgButton);
			imgButton = null;
		}
		Log.trace("dispose 3");

		imagePool.flushAll();
		imagePool = null;
		if (screenManager != null)
		{
			screenManager.dispose();
			screenManager = null;
		}
		Log.trace("dispose 4");

		// 2011.06.09 jy.yu 추가
		STBID = null;
		SUBSID = null;

		if (dmcUtil != null)
		{
			dmcUtil.dispose();
			dmcUtil = null;
		}

		Log.trace("dispose 5");
		scene = null;
		Log.trace("dispose 6");
		// notifyDestroyed();
	}

	public void run()
	{
		while (threadRunning)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			process();
			repaint();
			if (nextScene != SATAE_NONE)
				changeScene();
		}
	}

	public boolean handleKeyEvent(UserEvent evt)
	{
		if (evt.getType() != KeyEvent.KEY_PRESSED)
		{
			System.out.println(" is not keypressed!");
			return false;
		}

		if (curScene == null || load_kind)
			return false;

		int code = evt.getCode();
		if (OverlappedUIManager.isNumberKey(evt))
		{
			System.out.println("[PORORO] NumberKey #######: " + code);
		}
		if (OverlappedUIManager.isSystemKey(evt, true))
		{
			System.out.println("[PORORO] SystemKey ##############");
		}
		if (OverlappedUIManager.isHotKey(evt))
		{
			System.out.println("[PORORO] HotKey ##############");
		}

		switch (code)
		{
			case HRcEvent.VK_ENTER:
			case HRcEvent.VK_UP:
			case HRcEvent.VK_DOWN:
			case HRcEvent.VK_LEFT:
			case HRcEvent.VK_RIGHT:

			case Constant.KEY_Red:
			case Constant.KEY_Yellow:
			case Constant.KEY_Green:
			case Constant.KEY_Blue:

			case Constant.KEY_PREV:
			case Constant.KEY_InputMode:
			case Constant.KEY_Space:
			case Constant.KEY_Remove:
			case KeyEvent.VK_F9:
				System.out.println("normal keypressed");
				keyPressed(evt);
				return false;
			default:
				System.out.println("number keypressed");
				if (code >= 48 && code <= 57)
				{ // 숫자키
					keyPressed(evt);
				}
				return false;
		}
		//!!!!!! 디폴트 리턴값은 false !!!!!!
		// 리턴값이 true 면, 자기보다 더 낮은 priority 로는 내려보내지 않음! (뽀로로 resident 테스트시는 true 로!)
		// (뽀로로는 독립형 어플이고 priority 도 매우 낮아서.. 실제서비스에선 키를 막을 이유가 없음)

		// NOTE: OverlappedUI 상에서의 일반적인 키처리 방식
		// 보통은 하위로 키입력이 내려가지 않도록 몽땅 true 를 리턴하던지 (경고팝업 경우)
		// 아니면 자기가 쓸 키만 true 를 리턴하고, 나머지는 false 리턴 하는 식으로 로직을 구성함.
	}

	public void keyPressed(UserEvent key)
	{
		// System.out.println("SceneManager keyPressed : " + key.getKeyCode());

		if (isErrImg)
		{
			switch (key.getCode())
			{
				case HRcEvent.VK_ENTER:
				case Constant.KEY_PREV:
					if (!isShownNotice)
					{
						SceneManager.changeChannel();
					}
					else
					{
						isErrImg = false;
					}
					break;
			}
			return;
		}

		if (!isHDSTB)
		{
			int keycode = key.getCode();
			if (keycode == HRcEvent.VK_ENTER || keycode == Constant.KEY_PREV)
			{
				changeChannel();
			}
			return;
		}

		//		int keyCode = key.getCode();
		//		if (keyCode == Constant.KEY_Blue) {
		//			Log.setLogLevel(0);
		//		}

		if (curScene != null)
			curScene.processKey(key, key.getCode());

		// 로그 테스트용!!
		//		if (Log.getLogScreen() != null) {
		//			Log.getLogScreen().processKey(key.getCode());
		//		}

		repaint();
	}

	public void keyPressed(KeyEvent key)
	{

		if (isErrImg)
		{

			switch (key.getKeyCode())
			{
				case HRcEvent.VK_ENTER:
				case Constant.KEY_PREV:

					if (!isShownNotice)
					{
						SceneManager.changeChannel();
					}
					else
					{
						isErrImg = false;
					}
					break;
			}
			return;
		}

		if (!isHDSTB)
		{
			int keycode = key.getKeyCode();
			if (keycode == HRcEvent.VK_ENTER || keycode == Constant.KEY_PREV)
			{
				changeChannel();
			}
			return;
		}
		if (curScene != null)
		{
			curScene.processKey(key, key.getKeyCode());
		}
		repaint();
	}

	public void keyTyped(KeyEvent key)
	{
	}

	public void keyReleased(KeyEvent key)
	{
	}

	public void process()
	{

		if (isErrImg)
			return;

		prevTime = curTime;
		curTime = System.currentTimeMillis();
		if (curTime >= prevTime)
			elapsedTime = (int) (curTime - prevTime);
		else
			elapsedTime = (int) (Long.MAX_VALUE - (prevTime - curTime));
		if (curScene != null)
			curScene.process(elapsedTime);

	}

	public void paint(Graphics g)
	{

		//		if ( pp++ > 1000 ) pp=0;
		//		if ( pp%30 == 0 ) Memory.check();

		Graphics2D g2 = (Graphics2D) g;

		if (isErrImg)
		{
			drawErrors(g2);
			return;
		}

		if (curScene != null)
		{
			if (!load_kind)
			{
				//				g.setColor(new DVBColor(0, 0, 0, 80));
				//				g.fillRect(0, 0, 960, 540);
				//				mn_AniCnt = (mn_AniCnt+1)%6;
				//				g.drawImage(img_load, 422, 196, 422 + 116, 196 + 104, 116*mn_AniCnt,0,116*(mn_AniCnt+1),104, null);
				//			} else {
				curScene.draw(g2);
			}
		}
		if (load_kind)
		{
			g.setColor(new DVBColor(0, 0, 0, 80));
			g.fillRect(0, 0, 960, 540);
			mn_AniCnt = (mn_AniCnt + 1) % 6;
			g.drawImage(img_load, 422, 196, 422 + 116, 196 + 104, 116 * mn_AniCnt, 0, 116 * (mn_AniCnt + 1), 104, null);
		}

		//		Memory.draw(g, 600, 50);

		//		String abc = "STBID= " + STBID;
		////		String abc = "g_a= " + g_action;
		//		FontMetrics fontmetrics = g.getFontMetrics();
		//		int w = fontmetrics.stringWidth(abc);
		//		g.setColor(Color.black);
		//		g.drawString(abc, 100, 50);

		//		String tts = "SUBSID= " + SUBSID;
		////		String abc = "g_a= " + g_action;
		//		FontMetrics fmm = g.getFontMetrics();
		//		int p = fmm.stringWidth(tts);
		//		g.setColor(Color.black);
		//		g.drawString(tts, 100, 70);

		super.paint(g);
	}

	public void init(XletContext xletCtx)
	{
		dmcUtil = new CJ_DMCUtil();
		dmcUtil.setXletContext(xletCtx);
		//!!!!! DMCUtil 을 통해 받아와야한다 !!!!!
		STBID = dmcUtil.getSmartCardId();
		SUBSID = dmcUtil.getSubscriberID();
		TEMP_ID = dmcUtil.getCustomerInfo(DMCUtil.CUSTOMER_NAME);
		if (TEMP_ID == null)
			TEMP_ID = TEST_ID; // 사용자 이름 못받아올경우 임시..
		StateValue.subscriptionCheckOK = dmcUtil.checkSubscription();

		imagePool = new ImagePool(this);

		// SD일때, HD일때 구분
		isHDSTB = dmcUtil.isHD();

		if (isHDSTB)
		{
			initialScreenController();
			super.setBounds(0, 0, 960, 540);
			super.setVisible(true);
			scene = OverlappedUIManager.getInstance().createOverlappedDialog(50, (OverlappedDialogHandler) this, OverlappedUIManager.GRAPHICS_960_540);
			scene.setBounds(0, 0, 960, 540);
		}
		else
		{
			initialScreenController();
			super.setBounds(0, 0, 720, 480);
			super.setVisible(true);
			// priority 1~200 숫자가 높을수록 우선순위가 높다. (default 50)
			scene = OverlappedUIManager.getInstance().createOverlappedDialog(50, (OverlappedDialogHandler) this, OverlappedUIManager.GRAPHICS_720_480);
			scene.setBounds(0, 0, 720, 480);
		}

		try
		{
			if (isHDSTB)
			{
				loadBackgroundImage(new URL(StateValue.liveResource + "portal/bg/loading.gif"));
			}
			else
			{
				loadBackgroundImage(new URL(StateValue.liveResource + "portal/bg/sd.gif"));
			}
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		curScene = null;

		mn_keyCode = KEY_NONE;
		elapsedTime = 0;
		curTime = prevTime = 0;
		mn_gMenuCur = 0;

		nextScene = SATAE_NONE;
		mn_nextState = -1;

		m_rnd = new Random();

		img_load = getImage("img/loading.png");
		imgPopupA = getImage("img/popup/popup_a.png");
		imgCaution = getImage("img/popup/caution1.png");
		imgButton = getImage("img/popup/pop_button.png");

		if (isHDSTB)
			setChangeScene(SATAE_PORTAL_MENU);

		setBounds(scene.getBounds());
		setVisible(true);
		setBackground(Color.black);
		scene.add(this);

		// 로그 테스트용!!!!!
		//		if (Log.getLogScreen() != null) {
		//			System.out.println("' =================== scene.visible: " + scene.isVisible());
		//			Log.getLogScreen().setVisible(true);
		//			Log.getLogScreen().setBounds(0, 0, 960, 540);
		//			scene.add(Log.getLogScreen(), 0);
		//		}

		scene.setVisible(true);
		this.requestFocus();

		try
		{
			m_thread = new Thread(this);
			m_thread.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Container getScene()
	{
		return scene; // 걍 HScene 과 동일하다고 생각하면 됨.
	}

	public void prepareResources()
	{
		HSceneFactory factory = HSceneFactory.getInstance();
		HSceneTemplate template = new HSceneTemplate();
		template.setPreference(HSceneTemplate.SCENE_SCREEN_DIMENSION, new org.havi.ui.HScreenDimension(1, 1), HSceneTemplate.REQUIRED);
		template.setPreference(HSceneTemplate.SCENE_SCREEN_LOCATION, new org.havi.ui.HScreenPoint(0, 0), HSceneTemplate.REQUIRED);
		scene = factory.getBestScene(template);
		this.setBounds(scene.getBounds());
		this.setBackground(Color.black);
		this.setVisible(true);
		scene.add(this);
		scene.setVisible(true);
		this.requestFocus();
		this.addKeyListener((KeyListener) this);
		initialScreenController();
	}

	public void initialScreenController()
	{
		screenManager = new CJ_ScreenController();
		screenManager.init();
	}

	public void drawButton(Graphics g, int index, int x, int y)
	{
		g.drawImage(imgButton, x, y, x + 120, y + 32, 120 * index, 0, 120 * (index + 1), 32, null);
	}

	public void loadBackgroundImage(String url)
	{
		boolean result = false;
		result = screenManager.display(url);
		if (!result)
			screenManager.display(url);
	}

	public boolean loadBackgroundImage(URL url)
	{
		sendPV();
		boolean result = screenManager.display(url);
		if (!result)
			screenManager.display(url);
		if (!result)
			screenManager.display(url);
		return result;
	}

	public void setChangeScene(int sceneId)
	{
		nextScene = sceneId;
		mn_nextState = -1;
	}

	public void setChangeScene(int sceneId, int nextState)
	{
		nextScene = sceneId;
		mn_nextState = nextState;
	}

	public void changeScene()
	{
		if (curScene != null)
		{
			curScene.destroyScene();
			curScene = null;
		}

		switch (nextScene)
		{
			case SATAE_PORTAL_MENU:
				curScene = new MenuScene();
				break;

			case SATAE_GAME_MENU:
				if (mn_nextState > 0)
				{
					curScene = new GameTitle(mn_nextState);
					mn_nextState = -1;
				}
				else
					curScene = new GameTitle();
				break;

			// 선택한 게임으로 이동
			case SATAE_SOCCERGAMEPLAY:
				curScene = new SoccerGPlay();
				break;
			case SATAE_BOARDGAMEPLAY:
				curScene = new BoardGPlay();
				break;
			case SATAE_FISHINGGAMEPLAY:
				curScene = new FishingGPlay();
				break;
			case SATAE_MAZEGAMEPLAY:
				curScene = new MazeGPlay();
				break;
			case SATAE_PUZZLEGAMEPLAY:
				curScene = new PuzzleGPlay();
				break;
			case SATAE_REMOTEPLAY:
				curScene = new RemotePlay();
				break;
			case SATAE_TEETHPLAY:
				curScene = new TeethPlay();
				break;
		}

		nextScene = SATAE_NONE;
	}

	private int offscreenImgSize;

	public Image getImage(String filename)
	{
		// System.out.println("Local Image File : " + filename);
		Image image = imagePool.getImage(filename);
		// !!!!!!!! offscreen image size 테스트 !!!!!!!!!
		if (StateValue.USEDIMG_TEST)
		{
			if (image != null && image.getWidth(null) > 0)
			{
				offscreenImgSize = offscreenImgSize + (image.getWidth(null) * image.getHeight(null) * 4);
				System.out.println("######## [USEDIMG](LOC): " + offscreenImgSize);
			}
		}
		return image;
	}

	public Image getImage(URL url)
	{

		if (isErrImg)
		{
			return null;
		}

		//		Image image = imagePool.getImage(url);
		Image image = imagePool.fromHTTPImageRequester(url);

		if (image == null || image.getWidth(null) <= 0)
		{
			for (int i = 0; i < 2; i++)
			{
				System.out.println("##### getImage retry!!!!");
				//				image = imagePool.getImage(url);
				image = imagePool.fromHTTPImageRequester(url);
				if (image != null && image.getWidth(null) > 0)
					break;
			}
		}
		// !!!!!!!! offscreen image size 테스트 !!!!!!!!!
		if (StateValue.USEDIMG_TEST)
		{
			if (image != null && image.getWidth(null) > 0)
			{
				offscreenImgSize = offscreenImgSize + (image.getWidth(null) * image.getHeight(null) * 4);
				System.out.println("######## [USEDIMG](URL): " + offscreenImgSize);
			}
		}

		// 결국 실패한 경우
		if (image == null)
		{
			System.out.println("##### (T.T) getImage Failed!!!! FINALLY!!!!");
			SceneManager.getInstance().sever_check = 100;
			SceneManager.getInstance().sever_msg = 1;
			isErrImg = true;
		}
		return image;
	}

	public void removeImage(Image image)
	{
		// !!!!!!!! offscreen image size 테스트 !!!!!!!!!
		if (StateValue.USEDIMG_TEST)
		{
			if (image != null && image.getWidth(null) > 0)
			{
				offscreenImgSize = offscreenImgSize - (image.getWidth(null) * image.getHeight(null) * 4);
				System.out.println("######## [USEDIMG](rmv): " + offscreenImgSize);
			}
		}
		imagePool.removeImage(image);
		image = null;
	}

	// 그라비티 서버에 해당 url로 요청하고, 데이터를 받음.
	// 실패하면 server_msg 가 SERVER_MSG_ERROR (1) 로 세팅됨.
	private void server_gravity(String url)
	{

		//		String urls = StateValue.gravity_server + url;
		String urls = StateValue.gravity_server + "cj_portal/" + url;
		//		 urls = gravity_server+ s;
		//		Log.trace(this, "request URL: " + urls);
		Log.trace("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Log.trace("@@@@  GRA_SVR_urls=" + urls);
		Log.trace("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		// 2011.07.01 jy.yu 어차피 에러가 한번나서 sever_msg값이 ERROR가 되면 
		//  더 이상 복구가 안되므로, 괜히서버요청할 필요없이 그냥 여기서 끝낸다.
		// 2012.02.28 jy.yu Httpconnection.ConsultRequest() 내에서 sever_msg 값이
		//  복구되도록 수정함.
		//if (sever_msg == SERVER_MSG_ERROR) {
		//	sever_check=100;
		//	return;
		//}
		try
		{
			sdata = Httpconnection.ConsultRequest(urls, true);
			Log.trace("################################################");
			Log.trace("####  GRA_SVR_RETVALs=" + sdata);
			Log.trace("################################################");
			//			Log.trace(this, "received sdata: " + sdata);
			isErrImg = false;
		}
		catch (Exception e)
		{
			Log.error(this, e);
			sever_msg = SERVER_MSG_ERROR;
			isErrImg = true;
		}
	}

	public void send_gravity(int kind)
	{
		//		 System.out.println("@@@@@@send sever@@@@@@@@@@");
		String urls[] = new String[5];
		urls[0] = urls[1] = urls[2] = urls[3] = "";

		// STBID ="746573745F73634033";
		// sever_check =kind;
		if (kind == 0)
		{ // 아이디 정보
			Log.trace(this, "TEMP_ID: " + TEMP_ID);

			//			urls[0] = "select_lastgameid.asp?STBID=";
			//			urls[1] = STBID;
			//			urls[3] = urls[0] + urls[1];

			urls[0] = "Select_AutoID.asp?gameid=";
			if (TEMP_ID == null)
			{
				urls[1] = "";
			}
			else
			{
				try
				{
					urls[1] = URLEncoder.encode(TEMP_ID, "UTF-8");
				}
				catch (Exception e)
				{
					//UnsupportedEncodingException
					Log.error(this, e);
				}
			}
			urls[2] = "&stbid=" + STBID + "&subsid=" + SUBSID;
			urls[3] = urls[0] + urls[1] + urls[2];
		}
		else if (kind == 1)
		{ // 아이디 등록   (** CJ에서는 이쪽은 더이상 수행되지 않는다.)
			// !!!!!! 테스트용 !!!!!!!! (랭킹10개 채우기 용;)
			//			TEMP_ID = "뽀로로P1" + TEST_RANK_CNT;
			//			STBID = "TESTSTBID1" + TEST_RANK_CNT;
			//			SUBSID = "TESTSUBSID1" + TEST_RANK_CNT;
			//			TEST_RANK_CNT++;

			urls[0] = "Insert_gameid.asp?gameid=";
			if (TEMP_ID == null)
			{
				urls[1] = "";
			}
			else
			{
				try
				{
					urls[1] = URLEncoder.encode(TEMP_ID, "UTF-8");
					//					urls[1] = new String(TEMP_ID.getBytes("8859_1"), "UTF-8");
				}
				catch (Exception e)
				{
					//UnsupportedEncodingException
					Log.error(this, e);
				}
			}
			urls[2] = "&stbid=" + STBID + "&SUBSID=" + SUBSID + "&idtype=1";
			urls[3] = urls[0] + urls[1] + urls[2];

		}
		else if (kind == 2)
		{// 아이디 변경
			urls[0] = "update_gameid.asp?gameid=";
			if (TEMP_ID == null)
				urls[1] = "";
			else
			{
				try
				{
					urls[1] = URLEncoder.encode(TEMP_ID, "UTF-8");
					//					urls[1] = new String(TEMP_ID.getBytes("8859_1"), "UTF-8");
				}
				catch (Exception e)
				{
					//UnsupportedEncodingException
					Log.error(this, e);
				}
			}
			urls[2] = "&stbid=" + STBID + "&subsid=" + SUBSID;
			urls[3] = urls[0] + urls[1] + urls[2];

		}
		else if (kind == 3)
		{// VOD선불권 당첨여부
			urls[0] = "select_prepaid.asp?stbid=";
			urls[1] = STBID;
			urls[2] = "&SUBSID=" + SUBSID;
			urls[3] = urls[0] + urls[1] + urls[2];
		}
		else if (kind == 4)
		{ // 일정액체크
			urls[0] = "Select_OnedayYN.asp?STBID=";
			urls[1] = STBID;
			urls[2] = "&SUBSID=" + SUBSID;
			urls[3] = urls[0] + urls[1] + urls[2];

			Log.trace("### Select_Accounting urls: " + urls[3]);
		}
		else if (kind == 5)
		{ // 결재확인 요청
			urls[0] = "insert_accounting.asp?accflag=";
			urls[1] = Integer.toString(StateValue.insertPay);
			urls[2] = "&STBID=" + STBID + "&SUBSID=" + SUBSID;
			urls[3] = urls[0] + urls[1] + urls[2];

			Log.trace("### insert_accounting urls: " + urls[3]);
		}
		else if (kind == 6)
		{ // 일결제 취소 요청
			urls[0] = "Delete_Accouting.asp?STBID=";
			urls[1] = STBID;
			urls[2] = "&SUBSID=" + SUBSID;
			urls[3] = urls[0] + urls[1] + urls[2];

			Log.trace("### Delete_Accounting urls: " + urls[3]);
		}
		else if (kind == 7)
		{// Free 게임 조회
			urls[0] = "Select_FreeGAME.asp";
			urls[3] = urls[0];
			Log.trace("### Select_FreeGAME urls: " + urls[3]);
		}
		else if (kind == 8)
		{//전체 통계 데이터 조회
			urls[0] = "Select_IQ_TOTAL.asp?stbid=";
			urls[1] = STBID;
			urls[2] = "&SUBSID=" + SUBSID;
			urls[3] = urls[0] + urls[1] + urls[2];
		}
		else if (kind == 9)
		{//게임을 1회 했는지 여부.
			urls[0] = "Select_GAMEYN.asp?stbid=";
			urls[1] = STBID + "&SUBSID=" + SUBSID;
			urls[2] = "&gametype=" + strTmp;
			urls[3] = urls[0] + urls[1] + urls[2];
		}
		else if (kind == 10)
		{//게임을 1회 했다고 기록.
			urls[0] = "Insert_GAMEYN.asp?stbid=";
			urls[1] = STBID + "&SUBSID=" + SUBSID;
			urls[2] = "&gametype=" + gameCode[GameState];
			urls[3] = urls[0] + urls[1] + urls[2];
		}
		else if (kind == 11)
		{ //게임 진행시간 등록
			urls[0] = "Insert_Time.asp?gameid=";
			//			urls[1] = GameID;
			try
			{
				urls[1] = URLEncoder.encode(GameID, "UTF-8");
				//				urls[1] = new String(GameID.getBytes("8859_1"), "UTF-8");
			}
			catch (Exception e)
			{
				Log.error(this, e);
			}
			urls[2] = "&stbid=" + STBID + "&subsid=" + SUBSID + "&gametype=" + gameCode[GameState];
			urls[3] = urls[0] + urls[1] + urls[2] + "&playtime=1";
		}

		server_gravity(urls[3]);
		if (sever_msg == SERVER_MSG_OK)
			gravity_data(kind);

	}

	void drawErrors(Graphics2D g)
	{
		g.setColor(new DVBColor(0, 0, 0, 80));
		g.fillRect(0, 0, 960, 540);
		g.drawImage(imgPopupA, 221, 96, null);
		drawButton(g, 0, 420, 360); //확인버튼
		g.setColor(new Color(53, 53, 53));
		g.setFont(new Font("Bold", 0, 20));
		DrawStr(g, "알     림", 480, 152);
		g.drawImage(imgCaution, 376, 135, null);
		g.setFont(new Font("Bold", 0, 17));
		g.setColor(Color.red);
		DrawStr(g, "통신상태가 원활하지 않습니다.", 480, 208 + (2 * 22));
		DrawStr(g, "잠시 후 다시 이용해 주시기 바랍니다.", 480, 208 + (3 * 22));
	}

	public void sendPV()
	{
		new Thread()
		{
			public void run()
			{
				String url = "Insert_PV.asp?stbid=" + STBID + "&SUBSID=" + SUBSID;
				server_gravity(url);
			}
		}.start();
	}

	public String getRank_gravity()
	{
		// 랭킹 받기
		String urls[] = new String[4];
		urls[0] = urls[1] = urls[2] = urls[3] = "";

		urls[0] = gameCode[GameState];

		urls[1] = "&stbid=" + STBID + "&SUBSID=" + SUBSID;
		try
		{
			urls[2] = URLEncoder.encode(GameID, "UTF-8");
			//			urls[2] = new String(GameID.getBytes("8859_1"), "UTF-8");
		}
		catch (Exception e)
		{
			//UnsupportedEncodingException
			Log.error(this, e);
		}
		urls[3] = "Select_Score.asp?GAMETYPE=" + urls[0] + urls[1] + "&gameid=" + urls[2];

		server_gravity(urls[3]);

		if (sever_msg == SERVER_MSG_ERROR)
			sdata = " ";
		return sdata;
	}

	public void sendScore_gravity(int score)
	{
		// !!!!!! 테스트용 !!!!!!!! (랭킹10개 채우기 용;)
		//		GameID = "뽀로로P1" + TEST_RANK_CNT;
		//		STBID = "TESTSTBID1" + TEST_RANK_CNT;
		//		SUBSID = "TESTSUBSID1" + TEST_RANK_CNT;
		//		score = score + TEST_RANK_CNT;
		//		TEST_RANK_CNT++;

		// 점수를 서버로 세팅
		System.out.println("==========================================sendScore_gravity");
		String urls[] = new String[6];
		urls[0] = urls[1] = urls[2] = urls[3] = urls[4] = urls[5] = "";

		urls[0] = gameCode[GameState];

		try
		{
			urls[1] = URLEncoder.encode(GameID, "UTF-8");
			//			urls[1] = new String(GameID.getBytes("8859_1"), "UTF-8");
		}
		catch (Exception e)
		{
			//UnsupportedEncodingException
			Log.error(this, e);
		}
		urls[2] = Integer.toString(score);
		urls[3] = "&STBID=" + STBID + "&SUBSID=" + SUBSID;
		urls[4] = Integer.toString(0);
		urls[5] = "Insert_Score.asp?GAMETYPE=" + urls[0] + "&GAMEID=" + urls[1] + "&GAMESCORE=" + urls[2] + urls[3] + "&PLAYTIME=" + urls[4];
		// try {
		// urls[3]=
		// utfString("Select_Score.asp?GAMETYPE="+urls[0]+"&stbid="+urls[1]+"&gameid="+urls[2]);
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }

		server_gravity(urls[5]);
	}

	// 어느 게임을 실행했는지
	public void sendaccouting_gravity()
	{
		System.out.println("==========================================sendaccouting_gravity");
		String gameType = gameCode[GameState];

		//		http://server ip/select_gameid.asp?STBID=셋탑아이디&SUBSID=고객아이디&GAMETYPE=게임종류
		//		String url = "select_gameid.asp?STBID=" + STBID + "&SUBSID=" + SUBSID + "&GAMETYPE=" + gameType;
		String url = "Insert_GameSelect.asp?stbid=" + STBID + "&subsid=" + SUBSID + "&gametype=" + gameType;
		//- http://server IP/cj_portal/Insert_GameSelect.asp?stbid=셋탑박스ID값&subsid=고객ID값&gametype=게임종류

		//		http://192.168.97.34/cj_portal/select_gameid.asp?STBID=02120422365&SUBSID=50002295&GAMETYPE=game_soccer
		server_gravity(url);
	}

	// 포털 접속 기록 남기기
	public void sendInsertPotal_gravity()
	{
		System.out.println("==========================================sendInsertPotal_gravity");
		String url = "Insert_Portal.asp?stbid=" + STBID + "&subsid=" + SUBSID;
		//		http://server IP/cj_portal/Insert_Portal.asp?stbid=셋탑박스ID값&subsid=고객ID값
		server_gravity(url);
	}

	public static String decode(String hex, String charset)
	{
		byte[] bytes = new byte[hex.length() / 3];
		int len = hex.length();
		for (int i = 0; i < len;)
		{
			int pos = hex.substring(i).indexOf("%");
			if (pos == 0)
			{
				String hex_code = hex.substring(i + 1, i + 3);
				bytes[i / 3] = (byte) Integer.parseInt(hex_code, 16);
				i += 3;
			}
			else
			{
				i += pos;
			}
		}

		try
		{
			return new String(bytes, charset);
		}
		catch (UnsupportedEncodingException e)
		{
		}//Exception
		return "";
	}

	public String IDtoDecode(String src, int mode)
	{
		String res = "";

		//    	mode = 0;

		if (mode == 0)
		{
			try
			{
				res = pororo.com.framework.URLDecoder.decode(src, "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		else if (mode == 1)
		{
			try
			{
				String opp = new String(src.getBytes(), "UTF-8");
				res = SceneManager.decode(opp, "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}

		return res;
	}

	private void gravity_data(int kind)
	{
		int i, j = 0;
		String[] s = new String[20];
		int idxCnt = 0;
		s[0] = sdata;
		for (i = 0, j = 0; i < sdata.length(); i++)
		{
			if (sdata.charAt(i) != '&')
			{
				for (j = j + 1; j < sdata.length(); j++)
				{
					if (sdata.charAt(j) == '&')
					{
						s[idxCnt] = sdata.substring(i, j);
						// System.out.println("***********s[" + idxCnt + "]="
						// + s[idxCnt]);
						idxCnt++;
						i = j;
						break;
					}
					// 2011.06.28 jy.yu < 값&param= > 형식으로 끝나지 않는 경우가 있어서 추가함
					else if (j == sdata.length() - 1)
					{
						s[idxCnt] = sdata.substring(i, j + 1);
					}
				}
			}
		}
		Log.trace("## check id - s0: " + s[0]);
		Log.trace("## check id - s1: " + s[1]);

		// TODo: jy.yu 단지 인코딩 확인용..삭제하자
		//		InputStreamReader IR = new InputStreamReader(System.in);
		//		Log.trace("## default encodeing: " + IR.getEncoding());
		//		try {
		//			IR.close();
		//		} catch (IOException e1) {
		//			Log.error(this, e1);
		//		}

		if (kind == 0)
		{
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			Log.trace("## check id - serverKind: " + SeverKind);
			idKind = SeverKind;
			if (idKind == 1)
			{

				String tmp = s[1].substring(7, s[1].length()); // 셋탑에서만 보임
				GameID = IDtoDecode(tmp, 0);

				// 인코딩 테스트..
				//				String testid = null;
				//				try {
				//					testid = new String(GameID.getBytes(), "8859_1");
				//					Log.trace("GameID after encoding(default to 8859_1): " + testid);
				//					testid = new String(GameID.getBytes("KSC5601"), "8859_1");
				//					Log.trace("GameID after encoding(KSC to 8859_1): " + testid);
				//					testid = new String(GameID.getBytes("UTF-8"), "KSC5601");
				//					Log.trace("GameID after encoding(utf8 to KSC5601): " + testid);
				//					testid = new String(GameID.getBytes("UTF-8"), "8859_1");
				//					Log.trace("GameID after encoding(utf8 to 8859_1): " + testid);
				//				} catch (UnsupportedEncodingException e) {
				//					Log.error(this, e);
				//				}	
			}
			else
			{
				// TODo: jy.yu 아이디 못받음!! 오류처리해야한다!!
				//   L==>  어차피 통신중 에러나면 에러팝업 뜬니 괜찮을듯...

				// (혹시 이쪽이 수행되더라도 TEST_ID로 아이디가 등록될 뿐 큰 문제는 없다.(아이디 중복체크를 안하기때문에 아이디가 같은 사람이 존재할수는 있음))

				/*
				 * 신규유저 아이디 자동 생성 테스트용.. (현재는 수행되지 않음)
				 * 새 API 가 제공되기 전에 임시로 자동으로 insert 되도록 테스트한것.
				 */
				String urls[] = new String[4];
				urls[0] = urls[1] = urls[2] = urls[3] = "";

				GameID = TEMP_ID = TEST_ID; //s[1].substring(7, s[1].length());

				urls[0] = "insert_gameid.asp?gameid=";
				try
				{
					urls[1] = URLEncoder.encode(TEMP_ID, "UTF-8");
					//urls[1] = new String(TEMP_ID.getBytes(), "UTF-8");
				}
				catch (Exception e)
				{
					//UnsupportedEncodingException
					Log.error(this, e);
				}

				urls[2] = "&stbid=" + STBID + "&SUBSID=" + SUBSID;
				urls[3] = urls[0] + urls[1] + urls[2];

				server_gravity(urls[3]);
			}
		}
		else if (kind == 1)
		{
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			// System.out.println("SeverKind="+SeverKind);
			idKind = SeverKind;
			if (idKind == 1)
				GameID = TEMP_ID;
		}
		else if (kind == 2)
		{
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			// System.out.println("SeverKind="+SeverKind);
			idKind = SeverKind;
			if (idKind == 1)
				GameID = TEMP_ID;
			// idKind = SeverKind;
		}
		else if (kind == 3)
		{
			for (int k = 0; k < s.length; k++)
			{
				System.out.println("s[" + k + "] : " + s[k]);
			}
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			// SeverKind =2;
			if (SeverKind == 1)
			{ // 선불권 당첨시
				// 14761 52810 12044
				// System.out.println("s[1]="+ s[1]);
				ppcard[0] = s[1].substring(7, 12);
				ppcard[1] = s[1].substring(12, 17);
				ppcard[2] = s[1].substring(17, 22);
				// System.out.println("ppcard="+ppcard[0]+" "+
				// ppcard[1]+" "+ppcard[2]);
			}
		}
		else if (kind == 4)
		{ // 일정액 체크
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			Log.trace("### Select_Accounting kind: " + SeverKind);
		}
		else if (kind == 5)
		{ // 결재확인 요청
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			Log.trace("### insert_accounting kind: " + SeverKind);
		}
		else if (kind == 6)
		{ // 일정액 제거 요청
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			Log.trace("### Delete_Accounting kind: " + SeverKind);
		}
		else if (kind == 7)
		{ // Free게임 목록 요청
			// 초기화
			for (int k = 0; k < gameCode.length; k++)
			{
				freeGames[k] = 0;
			}

			// NOTE: 이벤트기간이 아닐때는 retVal=0 을 리턴함.
			String freeVal = null;
			if (s[0] != null && s[0].length() > 0)
			{
				freeVal = s[0].substring(7, s[0].length());
			}
			if (freeVal == null || "0".equals(freeVal.trim()))
			{
				//SeverKind = 0;  // 주의: 괜히 SeverKind 상태 바꾸지 말고.. 조용히 무료게임 없는걸로 처리.
			}
			else
			{
				String[] result = Lib.split(freeVal, "|");
				int resLen = result.length;
				for (int k = 0; k < resLen; k++)
				{

					for (int k2 = 0; k2 < gameCode.length; k2++)
					{
						if (gameCode[k2].equals(result[k]))
						{
							Log.trace("free >> " + result[k] + "!!!");
							freeGames[k2] = 1;
						}
					}

				}
			}

		}
		else if (kind == 8)
		{ //전체 통계데이터
			SeverKind = Integer.parseInt(s[0].substring(7, s[0].length()));
			if (SeverKind == 1)
			{
				//				for (int k = 0; k < s.length; k++) 
				//					Log.out("s[" + k + "] : " + s[k]);

				for (int k = 0; k < s.length; k++)
				{
					if (s[k + 1] == null)
						break;
					float tmp = Float.parseFloat(s[k + 1].substring(4, s[k + 1].length()));
					interest[k] = (int) tmp;
				}
			}
		}
		else if (kind == 9)
		{
			strTmp = s[0].substring(7);
		}
		//end of if (kind)	

		sdata = null;
	}

	public static void DrawImg(Graphics g, Image img, int img_x, int img_y, int Align)
	{
		if (img == null)
		{
			return;
		}
		int tmpW, tmpH;
		tmpW = img.getWidth(null);
		tmpH = img.getHeight(null);
		switch (Align)
		{
			case TR:
				g.drawImage(img, img_x - tmpW, img_y, null);
				break;
			case TC:
				g.drawImage(img, img_x - (tmpW / 2), img_y, null);
				break;
			case CL:
				g.drawImage(img, img_x, img_y - (tmpH / 2), null);
				break;
			case CR:
				g.drawImage(img, img_x - tmpW, img_y - (tmpH / 2), null);
				break;
			case CC:
				g.drawImage(img, img_x - (tmpW / 2), img_y - (tmpH / 2), null);
				break;
			case BL:
				g.drawImage(img, img_x, img_y - tmpH, null);
				break;
			case BR:
				g.drawImage(img, img_x - tmpW, img_y - tmpH, null);
				break;
			case BC:
				g.drawImage(img, img_x - (tmpW / 2), img_y - tmpH, null);
				break;
			default:
				g.drawImage(img, img_x, img_y, null);
				break;
		}
	}

	public static void DrawStr(Graphics g, String str, int str_x, int str_y)
	{
		int str_w = 0;
		FontMetrics fontmetrics = g.getFontMetrics();
		str_w = fontmetrics.stringWidth(str);
		g.drawString(str, str_x - (str_w / 2), str_y);

	}

	public int getRandomInt(int nMin, int nMax)
	{
		int rtn;

		rtn = (m_rnd.nextInt() % ((nMax - nMin) + 1));

		if (rtn < 0)
			rtn = -rtn;
		return rtn + nMin;
	}

	public static void changeChannel()
	{
		if (dmcUtil != null)
			dmcUtil.goToExit();
	}

	/**
	 * @param forced
	 *            : true면 화면 리소스가 모두 제거되어야 함.
	 *            어차피 destroyXlet 에서 모두 제거하는데 필요할까?...
	 */
	public void requestDispose(boolean forced)
	{
		System.out.println("[PORORO] call requestDispose() : " + forced);

	}

}
