package pororo.com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.dvb.event.UserEvent;
import org.dvb.ui.DVBColor;
import org.havi.ui.event.HRcEvent;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.automata.AutoMataContainer;
import pororo.com.automata.Constant;
import pororo.com.framework.PropertyWork;
import pororo.com.framework.Sound;
import pororo.com.log.Log;

public class MenuScene extends Scene implements AutoMataContainer.TextListener
{

	private static final int PAY_MONTHLY = 0; // 월정액
	private static final int PAY_DAILY = 1; // 일정액
	private int payType = PAY_MONTHLY;

	private Graphics g;
	private Sound m_sound;

	private int State = 0;
	private final int M_NOTICE = 1;
	private final int M_MENU_KID = 20;
	private final int M_MENU_MOM = 25;
	private final int M_GAMEINTORODUCE = 27;
	private final int M_POPUP_IQINTRODUCE = 26;
	private final int M_IDCHANGE = 4;
	private final int M_BUY = 5;
	private final int M_INTRODUCE = 6;
	private final int M_NOTITY = 7;
	private final int M_END = 8;
	private final int M_ERROR = 9;
	private final int M_ERROR1 = 10;
	private final int M_ERROR2 = 11;

	private Font Font0;
	private Font Font1;
	private Font Font2;
	private Font Font3;
	private Font Font4;
	private Font Font5;

	private boolean end_popup = false;
	private int s_page = 0;
	private int p_select = 0;
	private int p_select_old = 0; // 월결재,일결제 메뉴가 늘어나서.. 이전 메뉴를 기억해야함.

	private int pay_activation = 0;

	private static final int PAY_KD_PIN_FAIL = 1;
	private static final int PAY_KD_SERVER_FAIL = 2;
	private static final int PAY_KD_RP_FAIL = 3;
	private int pay_kind = 0;

	private int alpha = 0;

	int noticeMax = 0;
	int noticeIdx = 0;

	int preState = 0;

	int aniMain = 0;
	int mFocus = 0;
	int idxCategori = 0;

	int maxMenuCategori[] = new int[3];

	public ArrayList arrGames = null;

	public PopupIntroduceGame introData;

	private Image img_caution[];
	private Image img_payButton[][];
	private Image img_popup;
	private Image img_button[][];

	private Image imgMenu[];

	private boolean b_checkHDSProc;
	private AutoMataContainer amc;

	// kid menu coord
	public final int coordKidMenu[][] = {
			{
					190, 153
			}, {
					325, 153
			}, {
					460, 153
			}, {
					190, 215
			}, {
					325, 215
			}, {
					460, 215
			}, {
					190, 266
			}, {
					325, 266
			}, {
					460, 266
			}, {
					190, 317
			}, {
					325, 317
			}, {
					460, 317
			}, {
					190, 368
			}, {
					325, 368
			}, {
					460, 368
			}
	};
	// mom menu coord
	public final int coordMomMenu[][] = {
			{
					92, 210
			}, {
					227, 210
			}, {
					362, 210
			}, {
					92, 261
			}, {
					227, 261
			}, {
					362, 261
			}, {
					92, 312
			}, {
					227, 312
			}, {
					362, 312
			}
	};
	public final String strMmenuTitle[][] = {
			{
					"언  어", "언어지능놀이 모음", "언어지능놀이를 통한 다양한 체험으로", "언어지능에 흥미를 높여주세요."
			}, {
					"논리수학", "논리수학지능놀이 모음", "재미있는 놀이를 통해 논리수학지능의", "흥미를 돋우어 주세요."
			}, {
					"신체운동", "신체운동지능놀이 모음", "놀이를 하면서 순발력도 키우고,", "재미있는 동작도 배워보세요."
			}, {
					"음  악", "음악지능놀이 모음", "재미있는 소리와 음악을 통해", "아이들의 창의력을 키워주세요."
			}, {
					"공  간", "공간지능놀이 모음", "놀이를 통해 공간에 대한 생각과", "이해력을 높여주세요."
			}, {
					"자연친화", "자연친화지능놀이 모음", "여러가지 놀이를 하면서, 사물에 대한", "판단력을 높여주세요."
			}, {
					"자기성찰", "자기성찰지능놀이 모음", "놀이를 즐기면서 실력도 비교해보고,", "누가누가 잘하나 맞춰보세요."
			}, {
					"인간친화", "인간친화지능놀이 모음", "친구들과 함께 서로 도와주고, 즐길 수", "있는 놀이들이 모여있어요."
			}, {
					"어린이메뉴", "어린이메뉴로 이동", "어린이메뉴로 이동해요.", " "
			}
	};
	public final String strIQtext[][] = {
			{
					"언어지능", "언어지능이란 말이나 글을 분석하여 인식하고", "이해시키는 능력을 말해요.", "", "대표 직업: 기자, 방송인, 작가, 변호사, 외교관", "대표 놀이: 리모콘", ""
			}, {
					"논리수학지능", "논리수학지능이란 계산과 규칙, 명제 등을", "만들거나 수행하는 능력을 말해요.", "", "대표 직업: 연구원, 과학자, 선생님, 엔지니어", "대표 놀이: 축구놀이, 그림놀이", ""
			}, {
					"신체운동지능", "신체운동지능이란 춤, 운동, 연기 등의 체계를", "쉽게 익히고 조절하며 창조하는 능력을 말해요.", "", "대표 직업: 마술사, 안무가, 무용가, 운동선수", "대표 놀이: 과일농장, 보드놀이, 축구놀이, 낚시놀이,", "미로놀이, 그림놀이"
			}, {
					"음악지능", "음악지능이란 음과 박자를 쉽게 느끼고,", "연주하거나 작곡하는 능력을 말해요.", "", "대표 직업: 작곡가, 성악가, 지휘자, 음악평론가", "애표 놀이: 리모콘", ""
			}, {
					"공간지능", "공간지능이란 도형, 그림, 지도, 입체 설계 등의", "정보를 인지하고 창조하는 능력을 말해요.", "", "대표 직업: 디자이너, 탐험가, 건축가, 설계사", "대표 놀이: 과일농장, 보드놀이, 축구놀이, 낚시놀이,", "미로놀이, 퍼즐놀이"
			}, {
					"자연친화지능", "자연친화지능이란 식물, 동물 등 주변 환경을", "분류하고 분석하여 인지하는 능력을 말해요.", "", "대표 직업: 한의사, 식물학자, 동물학자, 지질학자", "대표 놀이: 과일농장, 보드놀이, 축구놀이, 낚시놀이,", "퍼즐놀이, 색칠하기"
			}, {
					"자기성찰지능", "자기성찰지능이란 자신을 이해하고, 자신의", "감정과 재능을 조절하고 응용하여 인생을", "설계하는 능력을 말해요.", "대표직업: 간호사, 철학자, 성직자", "대표놀이: 보드놀이, 축구놀이, 낚시놀이, 미로놀이,", "유령선"
			}, {
					"인간친화지능", "인간친화지능이란 대인관계와 밀접한 지능으로", "타인을 이해하고 타인과의 관계를 효과적으로", "유지하며 이끌어가는 능력을 말해요.", "", "대표 직업: 교사, 정치인, 심리치료사, 사업가", "대표 놀이: 유령선, 이닦기"
			}
	};

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	public MenuScene()
	{

		initSound();

		b_checkHDSProc = true;
		s_page = 0;

		m_sound.playSound(1);

		init(); // 서버와 아이디 체크 통신, 에러처리

		load_img();
		setGame();

		//공지사항 로딩
		try
		{
			PropertyWork pw = null;
			pw = new PropertyWork(new URL(StateValue.liveResource + "portal/notice/notice.txt"));
			noticeMax = Integer.parseInt(pw.getProperty("total"));
			pw.destroy();
			pw = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		//종합 통계 처리
		SceneManager.getInstance().send_gravity(8);

		if (!SceneManager.getInstance().isShownNotice)
		{ //처음진입이면
			SceneManager.getInstance().send_gravity(0); // 아이디 자동등록 및 조회
			if (noticeMax > 0)
				setNotice(0);
			else
				bgShow(M_MENU_KID);
		}
		else
		{
			bgShow(M_MENU_KID);
			mFocus = SceneManager.getInstance().GameState + 3;
		}

		// 2012.05.22 포털 접속 이력 추가
		new Thread()
		{
			public void run()
			{
				SceneManager.getInstance().sendInsertPotal_gravity(); // HttpConnection time-out 이슈때문에 스레드로 뺌! (들락날락할때마다 실행되는지라...)
			}
		}.start();
		SceneManager.getInstance().load_kind = false;

		//		State = M_BUY;
	}

	public void init()
	{
		boolean payCheckError = false;
		// System.out.println("=============menu img load==========================================");
		// 최초 서버접속~~~~~
		SceneManager.getInstance().sever_check = 100;
		SceneManager.getInstance().send_gravity(4); // <-- 일결재 24시간 체크
		if (SceneManager.getInstance().sever_msg == 0)
		{
			if (SceneManager.getInstance().SeverKind == 1)
			{
				StateValue.paymentCheckOK = true;
			}
		}
		else
		{
			payCheckError = true;
		}

		// 정상일 경우 이 시점에 SeverKind 는 1 이어야 한다!
		if (SceneManager.getInstance().sever_msg == 0 && !StateValue.ERR_AT_CHECK_SUBSCRPT && !payCheckError)
		{

			// TODO: jy.yu 원래는 false. 결재작동시 제거해야함??? no.
			b_checkHDSProc = true;

			Log.setLogLevel(StateValue.play_log_level);
			// ==> 로그레벨은 초기화 다 끝나고 나서 세팅하도록 한다.
		}
		else
		{
			State = M_ERROR;
		}
	}

	void setGame()
	{
		/// 보드  낚시  축구  미로  퍼즐  리모콘  이닦기
		String iconName[] = {
				"icon_board.png", "icon_fishing.png", "icon_soccer.png", "icon_maze.png", "icon_puzzle.png", "icon_handphone.png", "icon_teeth.png"
		};
		arrGames = new ArrayList(iconName.length);

		// 이 시점에는 SeverKind가 변화하지 않으므로, 바로 직전의 SeverKind 값이 유지된다.
		//FreeGame 처리.
		SceneManager.getInstance().send_gravity(7);

		try
		{
			for (int i = 0; i < SceneManager.gameCode.length; i++)
			{

				SceneManager.getInstance().strTmp = SceneManager.gameCode[i];
				SceneManager.getInstance().send_gravity(9);

				arrGames.add(new MenuData(SceneManager.gameCode[i],//game code
						Integer.parseInt(SceneManager.getInstance().strTmp), //play count
						SceneManager.getInstance().freeGames[i], //free game
						loadImage(new URL(StateValue.liveResource + "portal/gameIcon/" + iconName[i])))); // image
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			State = M_ERROR1;
		}

		maxMenuCategori[0] = arrGames.size();
		maxMenuCategori[1] = 4;
		maxMenuCategori[2] = 1;
	}

	void initSound()
	{
		m_sound = new Sound(2);
		m_sound.loadSound("snd/menumove.mp2");
		m_sound.loadSound("snd/none.mp2");
	}

	public void setNotice(int idx)
	{
		if (noticeMax > 0)
		{
			State = M_NOTICE;
			try
			{
				SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.liveResource + "portal/notice/nt" + idx + ".gif"));
			}
			catch (Exception e)
			{
				State = M_ERROR2;
				e.printStackTrace();
			}
		}
	}

	public Image loadImage(String s)
	{
		return SceneManager.getInstance().getImage(s);
	}

	public Image loadImage(URL s)
	{
		return SceneManager.getInstance().getImage(s);
	}

	public void load_img()
	{
		int i;

		img_button = new Image[2][2];
		img_payButton = new Image[2][2];
		img_caution = new Image[2];

		try
		{
			for (i = 0; i < 2; i++)
			{
				img_button[0][i] = loadImage("img/popup/button0" + i + ".png");
				img_button[1][i] = loadImage("img/popup/button1" + i + ".png");
			}

			img_payButton[0][0] = loadImage("img/popup/button02.png"); // 구매
			img_payButton[1][0] = loadImage("img/popup/button12.png"); // 구매 dim
			img_payButton[0][1] = loadImage("img/popup/button06.png"); // 취소
			img_payButton[1][1] = loadImage("img/popup/button16.png"); // 취소 dim

			for (i = 0; i < 2; i++)
			{
				img_caution[i] = loadImage("img/popup/caution" + i + ".png");
			}

			///////////////// Menu Image Loading ////////////////
			imgMenu = new Image[IMG_MAX];

			String imgName[] = {
					"kid_font_a01.png", "kid_font_a02.png", "kid_font_a03.png", "cursor_a.png", "cursor_a_.png", "cursor_a2.png", "kid_font_d01.png", "kid_font_d02.png", "kid_font_d05.png", "kid_font_d03.png", "kid_font_d04.png", "mom_ex.png", "free1.png", "free.png", "pay_month.png",
					"pay_day.png", "text_box3.png", "text_box4.png", "arrow_ud.png", "text_box0.png"
			};

			for (int j = 0; j < imgName.length; j++)
			{
				imgMenu[j] = SceneManager.getInstance().getImage(new URL(StateValue.liveResource + "portal/menu/" + imgName[j]));
				if (j % 3 == 0)
					SceneManager.getInstance().repaint();
			}
			///////////////// Menu Image Loading ////////////////

		}
		catch (Exception e)
		{
			e.printStackTrace();
			// System.out.println("imgae not  load---------------------------------------------");
		}
	}

	private final int IMG_KID_FONT_A01 = 0;
	private final int IMG_KID_FONT_A02 = IMG_KID_FONT_A01 + 1;
	private final int IMG_KID_FONT_A03 = IMG_KID_FONT_A02 + 1;;
	private final int IMG_CURSOR_A = IMG_KID_FONT_A03 + 1;
	private final int IMG_CURSOR_A_ = IMG_CURSOR_A + 1;
	private final int IMG_CURSOR_A2 = IMG_CURSOR_A_ + 1;
	private final int IMG_KID_FONT_D01 = IMG_CURSOR_A2 + 1;
	private final int IMG_KID_FONT_D02 = IMG_KID_FONT_D01 + 1;
	private final int IMG_KID_FONT_D05 = IMG_KID_FONT_D02 + 1;
	private final int IMG_KID_FONT_D03 = IMG_KID_FONT_D05 + 1;
	private final int IMG_KID_FONT_D04 = IMG_KID_FONT_D03 + 1;
	private final int IMG_MOM_EX = IMG_KID_FONT_D04 + 1;
	private final int IMG_FREE1 = IMG_MOM_EX + 1;
	private final int IMG_FREE = IMG_FREE1 + 1;
	private final int IMG_PAY_MONTH = IMG_FREE + 1;
	private final int IMG_PAY_DAY = IMG_PAY_MONTH + 1;
	private final int IMG_TEXT_BOX3 = IMG_PAY_DAY + 1;
	private final int IMG_TEXT_BOX4 = IMG_TEXT_BOX3 + 1;
	private final int IMG_ARROW_UD = IMG_TEXT_BOX4 + 1;
	private final int IMG_TEXT_BOX0 = IMG_ARROW_UD + 1;

	private final int IMG_MAX = IMG_TEXT_BOX0 + 1;

	public void FontType(Graphics g, int j)
	{
		switch (j)
		{
			default:
				break;
			case 0: //
				if (Font0 == null)
					Font0 = new Font("Bold", 0, 19);
				if (g != null)
				{
					g.setFont(Font0);
				}
				break;
			case 1:
				if (Font1 == null)
					Font1 = new Font("Bold", 0, 16);
				if (g != null)
				{
					g.setFont(Font1);
				}
				break;
			case 2:
				if (Font2 == null)
					Font2 = new Font("Bold", 0, 15);
				if (g != null)
				{
					g.setFont(Font2);
				}
				break;
			case 3:
				if (Font3 == null)
					Font3 = new Font("Bold", 0, 18);
				if (g != null)
				{
					g.setFont(Font3);
				}
				break;

			case 4:
				if (Font4 == null)
					Font4 = new Font("Bold", 0, 22);
				if (g != null)
				{
					g.setFont(Font4);

				}
				break;

			case 5:
				if (Font5 == null)
					Font5 = new Font("Bold", 0, 30);
				if (g != null)
				{
					g.setFont(Font5);
				}
				break;

		}
	}

	public void draw(Graphics2D g)
	{

		if (this.g == null)
		{
			this.g = g;
		}
		if (State == M_END || (SceneManager.getInstance().load_kind))
			return;

		switch (State)
		{
			case M_NOTICE:
				break;
			case M_MENU_KID:
			case M_MENU_MOM:
				draw_menubg(g);
				drawMenu(g);
				break;
			case M_IDCHANGE:
				draw_menubg(g);
				draw_bgalpha(g);
				break;
			case M_BUY:
				draw_menubg(g);
				draw_bgalpha(g);
				draw_buy(g);
				break;
			case M_POPUP_IQINTRODUCE:
				draw_IQintro(g);
				break;
			case M_GAMEINTORODUCE:
				draw_menubg(g);
				draw_popupIntroduce(g);
				break;
			case M_INTRODUCE:
				break;
			case M_NOTITY:
				draw_menubg(g);
				draw_bgalpha(g);
				draw_popup0(g, 4);
				break;
			case M_ERROR:

				g.setColor(new DVBColor(0, 0, 0, 80));
				g.fillRect(0, 0, 960, 540);
				g.drawImage(SceneManager.getInstance().imgPopupA, 221, 96, null);
				SceneManager.getInstance().drawButton(g, 0, 420, 360);
				g.setColor(new Color(53, 53, 53));
				g.setFont(new Font("Bold", 0, 20));
				DrawStr(g, "알     림", 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				g.setFont(new Font("Bold", 0, 17));
				g.setColor(Color.red);
				DrawStr(g, "통신상태가 원활하지 않습니다.", 480, 208 + (2 * 22));
				String str_err_cd = "";
				if (StateValue.ERR_AT_CHECK_SUBSCRPT)
					str_err_cd = " (" + String.valueOf(StateValue.PAY_ERR_CD) + ")";
				String strs = "잠시 후 다시 이용해 주시기 바랍니다." + str_err_cd;
				DrawStr(g, strs, 480, 208 + (3 * 22));
				break;
			case M_ERROR1:
				draw_menubg(g);
				draw_bgalpha(g);
				draw_popup0(g, 6);
				break;
			case M_ERROR2:
				draw_menubg(g);
				draw_bgalpha(g);
				draw_popup0(g, 7);
				break;
		}
		if (end_popup)
		{
			draw_bgalpha(g);
			draw_popup0(g, 0);
		}

	}

	public void draw_popupIntroduce(Graphics g)
	{
		g.setColor(new DVBColor(0, 0, 0, 80));
		g.fillRect(0, 0, 960, 540);

		if (introData.img_pc_b_menu != null)
			g.drawImage(introData.img_pc_b_menu, 133, 42, null);
		g.drawImage(img_caution[0], 376, 82, null);

		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("Bold", 0, 20));
		SceneManager.DrawStr(g, "놀이소개", 480, 99);

		//introduce object
		if (introData != null)
		{
			int tmp = 0;

			//list show
			String totNum = Integer.toString(introData.totalList);
			String curNum = Integer.toString(introData.curList + 1);
			if (totNum.length() < 2)
				totNum = "0" + totNum;
			if (curNum.length() < 2)
				curNum = "0" + curNum;
			if (introData.img_pc_font_num != null)
			{
				for (int i = 0; i < totNum.length(); i++)
				{
					tmp = totNum.charAt(i) - '0';
					g.drawImage(introData.img_pc_font_num, 320 + (12 * i), 139, 320 + (12 * (i + 1)), 139 + 15, 12 * tmp, 0, 12 * (tmp + 1), 15, null);
				}
				tmp = 0;
				for (int i = 0; i < curNum.length(); i++)
				{
					tmp = curNum.charAt(i) - '0';
					g.drawImage(introData.img_pc_font_num, 282 + (12 * i), 139, 282 + (12 * (i + 1)), 139 + 15, 12 * tmp, 0, 12 * (tmp + 1), 15, null);
				}
			}

			//cursor
			if (introData.img_cursor_b != null)
			{
				g.drawImage(introData.img_cursor_b, introData.coordIntroTitle[0] - 31, introData.coordIntroTitle[1] - 24 + (introData.curFocus * 30), null);
			}

			tmp = 0;
			for (int j = 0; j < introData.curList; j++)
			{
				tmp += introData.idxList[j];
			}

			//			//icon
			g.drawImage(introData.img_intro[introData.swchShow], introData.coordIntroIcon[0], introData.coordIntroIcon[1], null);

			//title
			g.setFont(new Font("Bold", 0, 17));
			for (int i = 0; i < introData.idxList[introData.curList]; i++)
			{
				g.drawString(introData.cText[i + tmp][0], introData.coordIntroTitle[0], introData.coordIntroTitle[1] + (30 * i));
			}
			g.drawString("팝업 닫기", introData.coordIntroTitle[0], introData.coordIntroTitle[1] + (30 * 8));

			if (introData.img_pc_okbt != null)
			{
				g.drawImage(introData.img_pc_okbt, 334, 410, null);
			}

			//text
			g.setFont(new Font("Bold", 0, 16));
			g.setColor(new Color(0, 106, 57));

			if (introData.curFocus != 8)
			{
				StringTokenizer token = new StringTokenizer(introData.cText[tmp + introData.curFocus][1], "|");
				int cntLine = 0;
				while (true)
				{
					if (!token.hasMoreTokens())
						break;
					String str = (String) token.nextToken();
					g.drawString(str, introData.coordIntroText[0], introData.coordIntroText[1] + (23 * cntLine));
					cntLine++;
				}
			}
			else
			{
				g.drawString("놀이소개 팝업을 닫고, 포탈로 돌아가요.", introData.coordIntroText[0], introData.coordIntroText[1]);
			}

			//icon
			g.drawImage(introData.img_intro[introData.swchShow], introData.coordIntroIcon[0], introData.coordIntroIcon[1], null);

		}

	}

	//////

	void draw_IQintro(Graphics g)
	{
		g.setColor(new DVBColor(0, 0, 0, 80));
		g.fillRect(0, 0, 960, 540);
		draw_popup0(g, 8);
	}

	public void draw_bgalpha(Graphics g)
	{
		g.setColor(new DVBColor(0, 0, 0, alpha));
		g.fillRect(0, 0, 960, 540);
	}

	public void draw_popup0(Graphics g, int pcontent)
	{
		int botton_kind = 0;
		String str[] = new String[6];
		str[0] = str[1] = str[2] = str[3] = str[4] = str[5] = "";
		g.drawImage(SceneManager.getInstance().imgPopupA, 221, 96, null);
		g.setColor(new Color(53, 53, 53));
		g.setFont(new Font("Bold", 0, 17));
		switch (pcontent)
		{
			case 0:
				str[2] = "뽀로로 놀이를";
				str[3] = "종료하겠습니까?";
				DrawStr(g, "놀이종료", 480, 152);
				// DrawImg(g, img_caution[0] , Center_X-76, Center_Y-151, TL);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				botton_kind = 1;
				break;
			case 1:
				str[0] = "안녕하세요~~";
				str[1] = "뽀로로 재능놀이에 오신 것을 환영합니다.";
				str[3] = "뽀로로 재능놀이는 아이들은 물론 온 가족이 모두";
				str[4] = "재미있게 즐길 수 있는 최고의 어린이 프로그램";
				str[5] = "이에요! 지금 바로 확인해 보세요~";

				g.setColor(new Color(0, 0, 0));
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				g.setFont(new Font("Bold", 0, 20));
				DrawStr(g, "알     림", 480, 152);
				break;

			case 2:
				str[0] = "선택한 콘텐츠는 1회 무료 체험 이벤트가 종료되었";
				str[1] = "어요! 놀이를 이용하려면, 놀이구매를 해주세요.";
				str[3] = "놀이를 구매하면 뽀로로 재능놀이의 모든 콘텐츠를";
				str[4] = "제한없이 이용할 수 있어요.";
				g.setColor(new Color(0, 0, 0));
				FontType(g, 6);
				DrawStr(g, "알     림", 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				break;

			case 3:
				//			str[0] = "구매 완료되었습니다.";
				//			str[2] = "온가족과 함께 신나고 재미있는";
				//			str[3] = "뽀로로놀이를 즐겨주세요!";
				//			str[5] = "감사합니다.";
				if (StateValue.g_action == StateValue.G_ACTION_MONTH)
				{
					str[0] = "구매 완료했어요!";
					str[2] = "상품유형 : 월정액";
					str[3] = "상 품 명 : 뽀로로놀이";
					str[4] = "이용금액 : " + StateValue.PRICE_MONTH + "원/월";
					str[5] = "이용기간 : 해지시까지 자동연장";
				}
				else
				{ // if (StateValue.g_action == StateValue.G_ACTION_DAY)
					str[0] = "구매 완료했어요!";
					str[2] = "상품유형 : 일정액";
					str[3] = "상 품 명 : 뽀로로놀이";
					str[4] = "이용금액 : " + StateValue.PRICE_DAY + "원/일";
					str[5] = "이용기간 : 구매 후 24시간 이용";
				}

				g.setFont(new Font("Bold", 0, 20));
				DrawStr(g, "알     림", 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				break;
			case 4:
				str[2] = "업데이트";
				str[3] = "준비 중입니다.";
				g.setFont(new Font("Bold", 0, 20));
				DrawStr(g, "알     림", 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				break;
			case 5:
				str[1] = "이미 가입된 사용자 입니다.";
				g.setFont(new Font("Bold", 0, 20));
				DrawStr(g, "알     림", 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				break;
			case 6:
				str[2] = "통신상태가 원활하지 않습니다.";
				str[3] = "잠시 후 다시 이용해 주시기 바랍니다.";
				DrawStr(g, "알     림", 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				g.setColor(Color.red);
				break;
			case 7: // 결재시 오류 표시용
				str[2] = "통신상태가 원활하지 않습니다.";
				str[3] = "잠시 후 다시 이용해 주시기 바랍니다.";
				DrawStr(g, "알     림", 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				g.setColor(Color.red);
				break;
			case 8:
				str[0] = strIQtext[mFocus][1];
				str[1] = strIQtext[mFocus][2];
				str[2] = strIQtext[mFocus][3];
				str[3] = strIQtext[mFocus][4];
				str[4] = strIQtext[mFocus][5];
				str[5] = strIQtext[mFocus][6];
				DrawStr(g, strIQtext[mFocus][0], 480, 152);
				g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
				botton_kind = 0;
				break;
		}

		g.setFont(new Font("Bold", 0, 16));
		for (int i = 0; i < 6; i++)
		{
			g.setColor(new Color(0, 106, 57));
			g.setFont(new Font("Bold", 0, 17));

			if (pcontent == 1 || pcontent == 7 || pcontent == 2 || pcontent == 3)
			{
				g.drawString(str[i], 288, 204 + (i * 25));
			}
			else if (pcontent == 5)
			{
				DrawStr(g, str[i], 480, 254 + (i * 25));
			}
			else if (pcontent == 6)
			{
				g.setColor(Color.red);
				DrawStr(g, str[i], 480, 194 + (i * 25));
			}
			else if (pcontent == 8)
			{
				g.drawString(str[i], 288, 200 + (i * 25));
			}
			else
			{
				DrawStr(g, str[i], 480, 194 + (i * 22));
			}
		}

		if (botton_kind == 0)
		{
			SceneManager.getInstance().drawButton(g, 0, 420, 360);
		}
		else
		{
			if (p_select == 0)
			{
				SceneManager.getInstance().drawButton(g, 2, 339, 360);
				SceneManager.getInstance().drawButton(g, 6, 501, 360);
			}
			else if (p_select == 1)
			{
				SceneManager.getInstance().drawButton(g, 5, 339, 360);
				SceneManager.getInstance().drawButton(g, 3, 501, 360);
			}
		}

	}

	//	public void draw_paycard(Graphics g, int kind) {
	//		String str[] = new String[4];
	//		DrawImg(g, img_popup[0], 480, 270 - 32, SceneManager.CC);
	//		g.setColor(new Color(53, 53, 53));
	//		FontType(g, 0);
	//		switch (kind) {
	//		case 0:
	//		case 2:
	//			str[0] = "VOD선불권(5000원)에 당첨되었습니다!";
	//			str[1] = "마이메뉴/설정 > 마이박스 > 선불권조회 >";
	//			str[2] = "선불권등록(파란버튼)에서 등록해 주세요!";
	//			str[3] = SceneManager.getInstance().ppcard[0] + " "
	//					+ SceneManager.getInstance().ppcard[1] + " "
	//					+ SceneManager.getInstance().ppcard[2];
	//			DrawStr(g, "선물 이벤트", 480 + 5, 270 - 134);
	//			DrawImg(g, img_caution[0], 480 - 76, 270 - 151,
	//					SceneManager.TL);
	//			break;
	//		case 1:
	//		case 3:
	//			str[0] = "이 팝업창의 내용은 다시 나오지 않습니다.";
	//			str[1] = "위 선불권 코드를 메모해 주신 후 확인 버튼을";
	//			str[2] = "눌러주세요.(유효기간 : 등록 후 3개월)";
	//			str[3] = SceneManager.getInstance().ppcard[0] + " "
	//					+ SceneManager.getInstance().ppcard[1] + " "
	//					+ SceneManager.getInstance().ppcard[2];
	//			DrawStr(g, "알  림", 480 + 5, 270 - 134);
	//			DrawImg(g, img_caution[1], 480 - 76, 270 - 151,
	//					SceneManager.TL);
	//			g.setColor(Color.red);
	//			break;
	//		case 4:
	//			str[0] = "죄송합니다";
	//			str[1] = "뽀로로놀이 오픈 이벤트가";
	//			str[2] = "종료되었습니다.";
	//			str[3] = "이벤트가 종료되었습니다.";
	//			DrawStr(g, "선물 이벤트", 480 + 5, 270 - 134);
	//			DrawImg(g, img_caution[0], 480 - 76, 270 - 151,
	//					SceneManager.TL);
	//			break;
	//
	//		}
	//		if (kind < 2) {
	//			DrawImg(g, img_textbox[2], 480 - 2, 270 - 111,
	//					SceneManager.TC);
	//			DrawImg(g, img_button[1][0], 480 - 72, 270 + 71,
	//					SceneManager.CC);
	//			DrawImg(g, img_button[1][1], 480 + 76, 270 + 71,
	//					SceneManager.CC);
	//		} else {
	//			DrawImg(g, img_textbox[3], 480 - 2, 270 - 111,
	//					SceneManager.TC);
	//			if (kind < 4) {
	//				if (p_select == 0) {
	//					DrawImg(g, img_button[0][0], 480 - 72, 270 + 71,
	//							SceneManager.CC);
	//					DrawImg(g, img_button[1][1], 480 + 76, 270 + 71,
	//							SceneManager.CC);
	//				} else if (p_select == 1) {
	//					DrawImg(g, img_button[1][0], 480 - 72, 270 + 71,
	//							SceneManager.CC);
	//					DrawImg(g, img_button[0][1], 480 + 76, 270 + 71,
	//							SceneManager.CC);
	//				}
	//			} else {
	//				DrawImg(g, img_button[0][0], 480, 270 + 71,
	//						SceneManager.CC);
	//			}
	//		}
	//		DrawImg(g, img_textbox[0], 480 - 2, 270 - 101,
	//				SceneManager.TC);
	//		FontType(g, 1);
	//		for (int i = 0; i < 3; i++) {
	//			DrawStr(g, str[i], 480, 270 - 17 + (i * 22));
	//		}
	//		FontType(g, 3);
	//		g.setColor(new Color(40, 144, 55));
	//		if (kind < 4) {
	//			DrawImg(g, img_textbox[1], 480 + 118, 270 - 85,
	//					SceneManager.TR);
	//			DrawStr(g, str[3], 480 - 10, 270 - 64);
	//		} else
	//			DrawStr(g, str[3], 480, 270 - 64);
	//	}

	public void draw_paypupup(Graphics g)
	{
		String str[] = new String[2];
		g.drawImage(SceneManager.getInstance().imgPopupA, 221, 96, null);
		g.setColor(new Color(53, 53, 53));
		g.setFont(new Font("Bold", 0, 20));
		if (pay_kind == 0)
		{
			str[0] = "구매를 원하시면";
			str[1] = "구매인증 비밀번호를 입력해주세요.";
			DrawStr(g, "구매인증", 480, 152);
			g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
			g.setColor(new Color(0, 106, 57));
		}
		else if (pay_kind == PAY_KD_PIN_FAIL)
		{
			str[0] = "비밀번호가 틀립니다.";
			str[1] = "다시 입력해 주세요";
			DrawStr(g, "구매인증", 480, 152);
			g.drawImage(SceneManager.getInstance().imgCaution, 376, 135, null);
			g.setColor(Color.red);
		}
		else if (pay_kind == PAY_KD_SERVER_FAIL)
		{ // 사용안함.. M_ERROR2로 대체..
			str[0] = "네트워크 오류로 뽀로로놀이의 ";
			str[1] = "접속이 원활하지 않습니다.";
			DrawStr(g, "놀이구매", 480 + 5, 270 - 134);
			DrawImg(g, img_caution[1], 480 - 76, 270 - 151, SceneManager.TL);
			g.setColor(Color.red);
		}
		else if (pay_kind == PAY_KD_RP_FAIL)
		{ // 사용안함.. M_ERROR2로 대체..
			str[0] = "결재 도중 오류가 발생하였습니다.";
			str[1] = "잠시 후 다시 시도해 주세요";
			DrawStr(g, "놀이구매", 480 + 5, 270 - 134);
			DrawImg(g, img_caution[1], 480 - 76, 270 - 151, SceneManager.TL);
			g.setColor(Color.red);
		}

		if (pay_activation == 0)
		{
			g.drawImage(imgMenu[IMG_TEXT_BOX3], 278, 173, null);
			g.drawImage(imgMenu[IMG_ARROW_UD], 291, 245, null);
			SceneManager.getInstance().drawButton(g, 5, 339, 360);
			SceneManager.getInstance().drawButton(g, 6, 501, 360);
		}
		else
		{
			g.drawImage(imgMenu[IMG_TEXT_BOX4], 278, 173, null);
			g.drawImage(imgMenu[IMG_ARROW_UD], 291, 361, null);
			if (p_select == 0)
			{
				SceneManager.getInstance().drawButton(g, 2, 339, 360);
				SceneManager.getInstance().drawButton(g, 6, 501, 360);
			}
			else if (p_select == 1)
			{
				SceneManager.getInstance().drawButton(g, 5, 339, 360);
				SceneManager.getInstance().drawButton(g, 3, 501, 360);
			}
		}
		g.drawImage(imgMenu[IMG_TEXT_BOX0], 353, 189, null);
		g.setFont(new Font("Bold", 0, 17));
		for (int i = 0; i < 2; i++)
		{
			if (str[i] != null)
				DrawStr(g, str[i], 480, 279 + (25 * i));
		}
	}

	public void process(int elapsedtime)
	{
		// System.out.println("change scene soccer process");
		if (end_popup)
			return;

		if (aniMain++ > 10000)
			aniMain = 0;

		if (State != M_BUY)
		{
			if (img_popup != null)
			{
				SceneManager.getInstance().removeImage(img_popup);
				img_popup = null;
			}
		}

		if (SceneManager.getInstance().isErrImg)
		{
			if (!SceneManager.getInstance().isShownNotice)
			{
				State = M_ERROR;
			}
			else
			{
				State = M_ERROR1;
			}
		}

		if (SceneManager.getInstance().sever_check < 100)
		{
			switch (SceneManager.getInstance().sever_check)
			{
				case 1: // <-- CJ에서 이쪽은 수행되지 않음..
					// System.out.println("sever  1 go=====================================");
					SceneManager.getInstance().send_gravity(1); // <--- 아이디 등록
					// System.out.println("sever  1 end ======================================");
					if (SceneManager.getInstance().sever_msg == 0)
					{
						if (SceneManager.getInstance().SeverKind == 1)
						{
							// text_kind=0;
							p_select = 0;
							alpha = 0;
							s_page = 0;
							// mcount =-1;
							State = M_MENU_KID;
						}
						else
						{
							// text_kind=1;
							p_select = 0;
							s_page = 2;
						}
					}
					else
					{
						p_select = 0;
						alpha = 0;
						s_page = 0;
						State = M_ERROR;
					}
					SceneManager.getInstance().sever_check = 100;
					break;
				case 2:
					// System.out.println("sever  2 go=====================================");
					SceneManager.getInstance().send_gravity(2); // <-- 아이디변경

					// System.out.println("sever  2 end ======================================");
					if (SceneManager.getInstance().sever_msg == 0)
					{
						if (SceneManager.getInstance().SeverKind == 1)
						{
							amc.sceneChange();
							amc.destroy();
							alpha = 0;
							s_page = 0;
							if (preState == M_MENU_MOM)
							{
								bgShow(M_MENU_MOM);
							}
							else
							{
								bgShow(M_MENU_KID);
							}
						}
						else
						{
							// text_kind=1;
							//						p_select = 0;
							//						s_page = 2;
							amc.keyComponent.state = amc.keyComponent.ID_ERROR;
							amc.conState = amc.STATE_INPUT;
						}
					}
					else
					{
						p_select = 0;

						// 2011.07.15 jy.yu 아이디변경중 오류발생시 오토마타 팝업닫히게!
						amc.sceneChange();
						amc.destroy();
						alpha = 0;

						// alpha =120;
						s_page = 0;
						State = M_ERROR1;
					}
					SceneManager.getInstance().sever_check = 100;
					break;
				case 3: // 구매완료팝업에서  확인 누른경우 (CJ에서 사용안함)
					SceneManager.getInstance().send_gravity(3); // <-- VOD선불권 이벤트 확인
					// System.out.println("sever  2 end ======================================");
					if (SceneManager.getInstance().sever_msg == 0)
					{
						if (SceneManager.getInstance().SeverKind == 1)
						{
							s_page = 5; // VOD선불권당첨 팝업으로
						}
						else if (SceneManager.getInstance().SeverKind == 0)
						{
							s_page = 9; // 이벤트가 종료되었습니다 팝업으로
						}
						else
						{
							p_select = 0;
							alpha = 0;
							s_page = 0;
							State = M_MENU_KID;
						}
					}
					else
					{
						p_select = 0;
						alpha = 0;
						s_page = 0;
						State = M_MENU_KID;

					}
					SceneManager.getInstance().sever_check = 100;
					break;
			}
		}

		if (b_checkHDSProc)
		{
			switch (State)
			{
				case M_NOTICE:
					break;
				case M_MENU_KID:
					break;
				case M_IDCHANGE:
					// autoBool이 true면 오토마타입력이 완료되었다는 뜻.
					if (SceneManager.autoBool)
					{
						// 입력중 취소한 경우
						if (amc.conState == amc.STATE_INPUT)
						{
							amc.sceneChange();
							amc.destroy();
							alpha = 0;
							if (preState == M_MENU_MOM)
							{
								bgShow(M_MENU_MOM);
							}
							else
							{
								bgShow(M_MENU_KID);
							}
							SceneManager.autoBool = false;
						}
						else
						// 입력 완료한 경우
						{
							SceneManager.getInstance().sever_check = 2;
							SceneManager.autoBool = false;
						}
					}
					break;
				case M_BUY:
					break;
			}
		}
	}

	public void processKey(Object evt, int key)
	{

		//FIXME 테스트용 과금풀기 LIVE 시 제거!
		//		if ( key == Constant.KEY_Red) {
		//			StateValue.g_action = StateValue.G_ACTION_MONTH;
		//		} else if ( key == Constant.KEY_Blue ) {
		//			StateValue.g_action = 0;
		//		}

		if (SceneManager.getInstance().sever_check < 100)
			return;

		if (StateValue.isAutomataVisible && !SceneManager.autoBool)
		{
			if (amc != null)
			{
				amc.keyPressed(new KeyEvent(amc, KeyEvent.KEY_PRESSED, 0L, 0, ((UserEvent) evt).getCode(), ((UserEvent) evt).getKeyChar()));
			}
		}

		if (!end_popup)
		{
			if (b_checkHDSProc)
			{

				if (State != M_NOTICE)
				{
					if (key == HRcEvent.VK_LEFT || key == HRcEvent.VK_RIGHT || key == HRcEvent.VK_UP || key == HRcEvent.VK_DOWN)
					{
						m_sound.playSound(0);
					}
				}

				switch (State)
				{
					case M_NOTICE:
						key_notice(key);
						break;
					case M_MENU_KID:
						key_menu_kid(key);
						break;
					case M_MENU_MOM:
						key_menu_mom(key);
						break;
					case M_GAMEINTORODUCE:
						key_popupIntroduce(key);
						break;
					case M_BUY:
						key_buy(key);
						break;
					case M_INTRODUCE:
						break;
					case M_NOTITY:
						switch (key)
						{
							case HRcEvent.VK_ENTER:
							case Constant.KEY_PREV:
								p_select = 0;
								alpha = 0;
								s_page = 0;
								State = M_MENU_KID;
								break;
						}
						break;
					case M_POPUP_IQINTRODUCE:
						switch (key)
						{
							case HRcEvent.VK_ENTER:
							case Constant.KEY_PREV:
								State = M_MENU_MOM;
								break;
						}
						break;
					case M_ERROR:
						switch (key)
						{
							case HRcEvent.VK_ENTER:
								State = M_END;
								SceneManager.changeChannel();
								break;
						}
						break;
					case M_ERROR1:
					case M_ERROR2:
						switch (key)
						{
							case HRcEvent.VK_ENTER:
							case Constant.KEY_PREV:
								p_select = 0;
								alpha = 0;
								s_page = 0;
								if (preState == M_MENU_MOM)
								{
									bgShow(M_MENU_MOM);
								}
								else
								{
									bgShow(M_MENU_KID);
								}
								State = M_MENU_KID;
								break;
						}
						break;
				}
			}
		}
		else
			end_popupkey(key);
	}

	public void key_popupIntroduce(int key)
	{
		if (introData.key_process(key))
		{
			if (introData != null)
			{
				introData.dispose();
				introData = null;
			}
			State = M_MENU_KID;
		}
	}

	public void key_notice(int key)
	{
		switch (key)
		{
			case HRcEvent.VK_LEFT:
				if (noticeMax <= 1)
					return;
				noticeIdx = (noticeIdx + (noticeMax - 1)) % noticeMax;
				setNotice(noticeIdx);
				break;

			case HRcEvent.VK_RIGHT:
				if (noticeMax <= 1)
					return;
				noticeIdx = (noticeIdx + 1) % noticeMax;
				setNotice(noticeIdx);
				break;
			case HRcEvent.VK_ENTER:
				bgShow(M_MENU_KID);
				if (!SceneManager.getInstance().isShownNotice)
				{
					SceneManager.getInstance().isShownNotice = true;
					mFocus = 0;
					idxCategori = 0;
				}
				else
				{
					mFocus = 3;
					idxCategori = 1;
				}
				break;
			case Constant.KEY_PREV:
				p_select = 0;
				end_popup = true;
				alpha = 80;
				break;
		}
	}

	void key_menu_mom(int key)
	{
		switch (key)
		{
			case HRcEvent.VK_LEFT:
				key_menu_mom_process(0);
				break;
			case HRcEvent.VK_RIGHT:
				key_menu_mom_process(1);
				break;
			case HRcEvent.VK_UP:
				key_menu_mom_process(2);
				break;
			case HRcEvent.VK_DOWN:
				key_menu_mom_process(3);
				break;
			case Constant.KEY_PREV:
				if (State == M_MENU_MOM)
				{
					bgShow(M_MENU_KID);
				}
				//			end_popup = true;
				break;
			case Constant.KEY_Yellow:
				if (State == M_MENU_MOM)
				{
					bgShow(M_MENU_KID);
				}
				break;

			case Constant.KEY_Green:
				s_page = 0;
				alpha = 80;
				State = M_IDCHANGE;
				automata_creat();
				SceneManager.getInstance().sendPV();
				break;

			case HRcEvent.VK_ENTER:

				if (mFocus == 8)
				{
					if (State == M_MENU_MOM)
					{
						bgShow(M_MENU_KID);
					}
				}
				else
				{
					State = M_POPUP_IQINTRODUCE;
					SceneManager.getInstance().sendPV();
				}
				break;
		}
	}

	void key_menu_mom_process(int direction)
	{ // 0 : left , 1 : right , 2 : up , 3 : down
		if (direction == 0)
		{
			if (mFocus == 0)
			{
				mFocus = 5;
			}
			else if (mFocus == 3)
			{
				mFocus = 8;
			}
			else if (mFocus == 6)
			{
				mFocus = 2;
			}
			else
			{
				mFocus = (mFocus + 8) % 9;
			}
		}
		else if (direction == 1)
		{
			mFocus = (mFocus + 1) % 9;
		}
		else if (direction == 2)
		{
			if (mFocus == 0)
			{
				mFocus = 8;
			}
			else if (mFocus == 1)
			{
				mFocus = 6;
			}
			else if (mFocus == 2)
			{
				mFocus = 7;
			}
			else
			{
				mFocus -= 3;
			}
		}
		else if (direction == 3)
		{
			if (mFocus == 6)
			{
				mFocus = 1;
			}
			else if (mFocus == 7)
			{
				mFocus = 2;
			}
			else if (mFocus == 8)
			{
				mFocus = 0;
			}
			else
			{
				mFocus += 3;
			}
		}

	}

	void bgShow(int mode)
	{

		SceneManager.getInstance().load_kind = true;

		if (mode == M_MENU_KID)
		{

			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.liveResource + "portal/bg/kidbg.gif"));
					}
					catch (MalformedURLException e)
					{
						e.printStackTrace();
					}
					State = M_MENU_KID;
					SceneManager.getInstance().load_kind = false;
					preState = M_MENU_KID;
				}
			}).start();

		}
		else if (mode == M_MENU_MOM)
		{

			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						SceneManager.getInstance().loadBackgroundImage(new URL(StateValue.liveResource + "portal/bg/mombg.gif"));
					}
					catch (MalformedURLException e)
					{
						e.printStackTrace();
					}
					State = M_MENU_MOM;
					SceneManager.getInstance().load_kind = false;
					preState = M_MENU_MOM;
				}
			}).start();
		}

		mFocus = 0;
		idxCategori = 0;

	}

	public void end_popupkey(int key)
	{
		// System.out.println("end_popupkey");
		switch (key)
		{
			case HRcEvent.VK_LEFT:
				if (p_select == 0)
					p_select = 1;
				else if (p_select == 1)
					p_select = 0;
				break;
			case HRcEvent.VK_RIGHT:
				if (p_select == 0)
					p_select = 1;
				else if (p_select == 1)
					p_select = 0;
				break;
			case Constant.KEY_PREV:
				// case HRcEvent.VK_ESCAPE:
				p_select = 0;
				end_popup = false;
				alpha = 0;
				break;
			case HRcEvent.VK_ENTER:
				if (p_select == 1)
				{
					p_select = 0;
					end_popup = false;
					alpha = 0;
				}
				else
				{
					// removeListener();
					end_popup = false;
					State = M_END;
					destroyScene();
					SceneManager.changeChannel();
				}
				break;

		}
	}

	public void draw_menubg(Graphics g)
	{

		if (SceneManager.GameID != null && SceneManager.GameID.length() > 0)
		{
			g.setColor(new Color(31, 84, 142));
			g.setFont(new Font("Bold", 0, 17));
			DrawStr(g, SceneManager.GameID, 823, 496);
		}
		if (StateValue.subscriptionCheckOK == true || StateValue.g_action == StateValue.G_ACTION_MONTH)
		{
			g.drawImage(imgMenu[IMG_PAY_MONTH], 630, 479, null);
		}
		else if (StateValue.paymentCheckOK == true || StateValue.g_action == StateValue.G_ACTION_DAY)
		{
			g.drawImage(imgMenu[IMG_PAY_DAY], 630, 479, null);
		}
	}

	public void drawMenu(Graphics g)
	{

		int tmpAni = aniMain % 4;

		if (State == M_MENU_KID)
		{

			// 커서
			if (mFocus < 3)
			{
				g.drawImage(imgMenu[IMG_CURSOR_A2], coordKidMenu[mFocus][0] - 4, coordKidMenu[mFocus][1] + 3, null);
				g.drawImage(imgMenu[IMG_CURSOR_A_], coordKidMenu[mFocus][0] - 4, coordKidMenu[mFocus][1] + 3 + 22, coordKidMenu[mFocus][0] + 142 - 4, coordKidMenu[mFocus][1] + 58 + 3 + 22, tmpAni * 142, 0, (tmpAni + 1) * 142, 58, null);
			}
			else
			{
				g.drawImage(imgMenu[IMG_CURSOR_A], coordKidMenu[mFocus][0] - 4, coordKidMenu[mFocus][1] - 3, null);
				g.drawImage(imgMenu[IMG_CURSOR_A_], coordKidMenu[mFocus][0] - 4, coordKidMenu[mFocus][1] - 3 + 30, coordKidMenu[mFocus][0] + 142 - 4, coordKidMenu[mFocus][1] + 58 - 3 + 30, tmpAni * 142, 0, (tmpAni + 1) * 142, 58, null);
			}

			g.drawImage(imgMenu[IMG_KID_FONT_A01], coordKidMenu[0][0], coordKidMenu[0][1], null);
			g.drawImage(imgMenu[IMG_KID_FONT_A02], coordKidMenu[1][0], coordKidMenu[1][1], null);
			g.drawImage(imgMenu[IMG_KID_FONT_A03], coordKidMenu[2][0], coordKidMenu[2][1], null);

			MenuData md = null;
			if (idxCategori == 0)
			{

				for (int i = 0; i < arrGames.size(); i++)
				{
					md = (MenuData) arrGames.get(i);
					//아이콘 그린다
					g.drawImage(md.getImage(), coordKidMenu[i + 3][0], coordKidMenu[i + 3][1], null);

					if (StateValue.subscriptionCheckOK == true || StateValue.g_action == StateValue.G_ACTION_MONTH)
					{
					}
					else if (StateValue.paymentCheckOK == true || StateValue.g_action == StateValue.G_ACTION_DAY)
					{
					}
					else
					{
						if (md.isFree == 1)
						{
							//기간제 무료 아이콘
							g.drawImage(imgMenu[IMG_FREE], coordKidMenu[i + 3][0], coordKidMenu[i + 3][1], null);
						}
						else if (md.playCnt == 0)
						{
							//1회무료 아이콘
							g.drawImage(imgMenu[IMG_FREE1], coordKidMenu[i + 3][0], coordKidMenu[i + 3][1], null);
						}
					}

				}
			}
			else if (idxCategori == 1)
			{
				for (int i = 0; i < maxMenuCategori[idxCategori]; i++)
				{
					g.drawImage(imgMenu[IMG_KID_FONT_D01 + i], coordKidMenu[i + 3][0], coordKidMenu[i + 3][1], null);
				}
			}
			else if (idxCategori == 2)
			{
				for (int i = 0; i < maxMenuCategori[idxCategori]; i++)
				{
					g.drawImage(imgMenu[IMG_KID_FONT_D04], coordKidMenu[i + 3][0], coordKidMenu[i + 3][1], null);
				}
			}

		}
		else if (State == M_MENU_MOM)
		{

			g.drawImage(imgMenu[IMG_MOM_EX], 112, 128, 112 + 80, 128 + 74, 80 * mFocus, 0, 80 * (mFocus + 1), 74, null);

			//커서
			g.drawImage(imgMenu[IMG_CURSOR_A], coordMomMenu[mFocus][0] - 4, coordMomMenu[mFocus][1] - 3, null);
			g.drawImage(imgMenu[IMG_CURSOR_A_], coordMomMenu[mFocus][0] - 4, coordMomMenu[mFocus][1] - 3 + 30, coordMomMenu[mFocus][0] + 142 - 4, coordMomMenu[mFocus][1] + 58 - 3 + 30, tmpAni * 142, 0, (tmpAni + 1) * 142, 58, null);

			drawInterest(g);

			g.setColor(new Color(77, 138, 98));
			FontType(g, 3);
			g.drawString(strMmenuTitle[mFocus][1], 212, 147);
			FontType(g, 1);
			g.drawString(strMmenuTitle[mFocus][2], 212, 175);
			g.drawString(strMmenuTitle[mFocus][3], 212, 196);

			for (int i = 0; i < 9; i++)
			{
				g.setColor(new Color(46, 33, 39));
				FontType(g, 3);
				DrawStr(g, strMmenuTitle[i][0], coordMomMenu[i][0] + 67, coordMomMenu[i][1] + 32);
			}

		}

	}

	void drawInterest(Graphics g)
	{
		g.setColor(new Color(228, 182, 198));
		g.fillRect(640, 129, SceneManager.getInstance().interest[0] * 2, 7);
		g.setColor(new Color(246, 198, 124));
		g.fillRect(640, 154, SceneManager.getInstance().interest[1] * 2, 7);
		g.setColor(new Color(187, 217, 133));
		g.fillRect(640, 179, SceneManager.getInstance().interest[2] * 2, 7);
		g.setColor(new Color(96, 199, 144));
		g.fillRect(640, 204, SceneManager.getInstance().interest[3] * 2, 7);
		g.setColor(new Color(96, 197, 178));
		g.fillRect(640, 229, SceneManager.getInstance().interest[4] * 2, 7);
		g.setColor(new Color(96, 185, 226));
		g.fillRect(640, 254, SceneManager.getInstance().interest[5] * 2, 7);
		g.setColor(new Color(177, 163, 203));
		g.fillRect(640, 279, SceneManager.getInstance().interest[6] * 2, 7);
		g.setColor(new Color(201, 156, 198));
		g.fillRect(640, 304, SceneManager.getInstance().interest[7] * 2, 7);
	}

	public void key_menu_kid(int key)
	{
		switch (key)
		{
			case HRcEvent.VK_LEFT:
				key_menu_kid_process(0);
				break;
			case HRcEvent.VK_RIGHT:
				key_menu_kid_process(1);
				break;
			case HRcEvent.VK_UP:
				key_menu_kid_process(2);
				break;
			case HRcEvent.VK_DOWN:
				key_menu_kid_process(3);
				break;
			case Constant.KEY_PREV:
				p_select = 0;
				end_popup = true;
				alpha = 80;
				break;
			case Constant.KEY_Blue: // 안씀
				break;
			case Constant.KEY_Green:
				s_page = 0;
				alpha = 80;
				State = M_IDCHANGE;
				automata_creat();
				SceneManager.getInstance().sendPV();
				break;
			case Constant.KEY_Red: // 안씀
				break;
			case Constant.KEY_Yellow:
				if (State == M_MENU_KID)
				{
					bgShow(M_MENU_MOM);
				}
				break;
			case HRcEvent.VK_ENTER:

				if (idxCategori == 0)
				{

					if (mFocus < 3)
						return;

					MenuData md = (MenuData) arrGames.get(mFocus - 3);

					if (md.isFree == 1)
					{

						//무료게임이면 그냥 실행
						setTargetGame(mFocus - 3);
						SceneManager.getInstance().chkGameState = false;

					}
					else if (md.playCnt == 0)
					{

						//1회 무료면 그냥 실행
						setTargetGame(mFocus - 3);
						SceneManager.getInstance().chkGameState = true;

					}
					else
					{

						//이도저도아니면 트리 탄다

						if (StateValue.isCostSkip)
						{
							if (StateValue.g_action == 0)
								StateValue.g_action = StateValue.G_ACTION_DAY;
						}

						if (StateValue.subscriptionCheckOK)
							StateValue.g_action = StateValue.G_ACTION_MONTH;
						else if (StateValue.paymentCheckOK)
							StateValue.g_action = StateValue.G_ACTION_DAY;

						if (StateValue.g_action == 0)
						{
							s_page = 0;
							p_select_old = p_select = 2;
							alpha = 80;
							State = M_BUY;
						}
						else
						{ // 게임 실행
							setTargetGame(mFocus - 3);
							SceneManager.getInstance().chkGameState = false;
						}

					}

				}
				else if (idxCategori == 1)
				{

					switch (mFocus)
					{
						case 3: // 공지사항
							setNotice(0);
							break;
						case 4: // 놀이소개
							State = M_GAMEINTORODUCE;
							if (introData == null)
							{
								SceneManager.getInstance().load_kind = true;
								new Thread(new Runnable()
								{
									public void run()
									{
										introData = new PopupIntroduceGame();
										introData.init();
									}
								}).start();
							}
							SceneManager.getInstance().sendPV();
							break;
						case 5: // 별명변경
							s_page = 0;
							alpha = 80;
							State = M_IDCHANGE;
							automata_creat();
							SceneManager.getInstance().sendPV();
							break;
						case 6: // 놀이구매
							if (StateValue.subscriptionCheckOK)
								StateValue.g_action = StateValue.G_ACTION_MONTH;
							else if (StateValue.paymentCheckOK)
								StateValue.g_action = StateValue.G_ACTION_DAY;

							if (StateValue.g_action == 0)
							{
								s_page = 1;
								p_select_old = p_select = 2;
								alpha = 80;
								State = M_BUY;
							}
							else
							{
								s_page = 4;
								p_select = 0;
								alpha = 80;
								State = M_BUY;
							}
							//					if ( State == M_MENU_KID ) {
							//					bgShow( M_MENU_MOM );
							//				}
							break;
					}

				}
				else if (idxCategori == 2)
				{

					switch (mFocus)
					{
						case 3:
							if (State == M_MENU_KID)
							{
								bgShow(M_MENU_MOM);
							}
							break;
					}
				}
		}

	}

	public void key_menu_kid_process(int direction)
	{ // 0 : left , 1 : right , 2 : up , 3 : down

		if (direction == 0)
		{
			if (mFocus < 3)
			{
				mFocus = (mFocus + 2) % 3;
				idxCategori = mFocus;
			}
			else
			{
				int tmp = (mFocus + (maxMenuCategori[idxCategori] - 1 + 3)) % (maxMenuCategori[idxCategori] + 3);
				if (tmp < 3)
				{
					mFocus = maxMenuCategori[idxCategori] + 2;
				}
				else
				{
					mFocus = tmp;
				}
			}
		}
		else if (direction == 1)
		{
			if (mFocus < 3)
			{
				mFocus = (mFocus + 1) % 3;
				idxCategori = mFocus;
			}
			else
			{
				int tmp = (mFocus + 1) % (maxMenuCategori[idxCategori] + 3);
				if ((tmp < (maxMenuCategori[idxCategori] + 3)) && (tmp != 0))
				{
					mFocus = tmp;
				}
				else
				{
					mFocus = 3;
				}
			}

		}
		else if (direction == 2)
		{

			if (mFocus >= 3)
			{
				int tmp = mFocus - 3;
				if (mFocus == 3 || mFocus == 4 || mFocus == 5)
				{
					mFocus = idxCategori;
				}
				else
				{
					mFocus = tmp;
				}
			}

		}
		else if (direction == 3)
		{
			if (mFocus < 3)
			{
				mFocus = 3;
			}
			else if (maxMenuCategori[idxCategori] < 3)
			{
				mFocus = 3;
			}
			else
			{

				if (maxMenuCategori[0] == 1)
				{

				}
				else if (maxMenuCategori[0] == 2)
				{
					if (mFocus == 3)
						mFocus = 4;
					else if (mFocus == 4)
						mFocus = 3;
				}
				else
				{

					if ((mFocus + 3) < (maxMenuCategori[idxCategori] + 3))
					{
						mFocus = mFocus + 3;
					}
					else
					{
						if (mFocus % 3 == 0)
						{
							mFocus = 4;
						}
						else if (mFocus % 3 == 1)
						{
							mFocus = 5;
						}
						else if (mFocus % 3 == 2)
						{
							mFocus = 3;
						}

					}

				}

			}

		}

	}

	private void setTargetGame(int game)
	{
		// 구매팝업이 아니라 실제 게임타이틀로 진입하는 시점에 sendAccounting 을 true 로 켜준다!
		SceneManager.sendAccounting = true;
		SceneManager.getInstance().GameState = game;
		SceneManager.getInstance().mn_gMenuCur = 0;

		switch (game)
		{
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				SceneManager.getInstance().setChangeScene(SceneManager.SATAE_GAME_MENU);
				break;
			case 5:
				SceneManager.getInstance().setChangeScene(SceneManager.SATAE_REMOTEPLAY);
				break;
			case 6:
				SceneManager.getInstance().setChangeScene(SceneManager.SATAE_TEETHPLAY);
				break;
		}

		SceneManager.getInstance().load_kind = true;
	}

	public void draw_buy(Graphics g)
	{
		if (s_page == 0)
		{
			draw_popup0(g, 2);
		}
		else if (s_page == 1)
		{
			// 524x348
			int ix = 480 - (524 / 2);
			int iy = 270 - 32 - (348 / 2);
			if (img_popup == null)
			{
				img_popup = loadImage("img/popup/box1.png");
				return;
			}
			else
			{
				DrawImg(g, img_popup, 480, 270 - 32, SceneManager.CC);
			}
			g.setColor(new Color(53, 53, 53));
			FontType(g, 0);
			DrawStr(g, "놀이구매", 480 + 5, 270 - 161);
			DrawImg(g, img_caution[0], 480 - 75, 270 - 178, SceneManager.TL);

			// 2011.06.21 jy.yu 월정액구매, 일정액구매, 취소 버튼
			if (p_select == 0)
			{
				DrawImg(g, img_payButton[0][0], ix + 371, iy + 157, SceneManager.TL);
				DrawImg(g, img_payButton[1][0], ix + 371, iy + 228, SceneManager.TL);
				DrawImg(g, img_payButton[1][1], ix + 371, iy + 299, SceneManager.TL);
			}
			else if (p_select == 1)
			{
				DrawImg(g, img_payButton[1][0], ix + 371, iy + 157, SceneManager.TL);
				DrawImg(g, img_payButton[0][0], ix + 371, iy + 228, SceneManager.TL);
				DrawImg(g, img_payButton[1][1], ix + 371, iy + 299, SceneManager.TL);
			}
			else if (p_select == 2)
			{
				DrawImg(g, img_payButton[1][0], ix + 371, iy + 157, SceneManager.TL);
				DrawImg(g, img_payButton[1][0], ix + 371, iy + 228, SceneManager.TL);
				DrawImg(g, img_payButton[0][1], ix + 371, iy + 299, SceneManager.TL);
			}

		}
		else if (s_page == 2)
		{
			draw_paypupup(g);
			draw_number(g);
		}
		else if (s_page == 3) // 성공
		{
			draw_popup0(g, 3);
		}
		else if (s_page == 4) // 이미 가압된 사용자
		{
			draw_popup0(g, 5);
		}
		else if (s_page == 5) // 선물 이벤트
		{
			//			draw_paycard(g, 0);
		}
		else if (s_page == 6) // 선물 이벤트 (선택)
		{
			//			draw_paycard(g, 2);
		}
		else if (s_page == 7) // 선물 이벤트 알림
		{
			//			draw_paycard(g, 1);
		}
		else if (s_page == 8) // 선물 이벤트 알림 (선택)
		{
			//			draw_paycard(g, 3);
		}
		else if (s_page == 9) // 선물 이벤트 상품 X
		{
			//			draw_paycard(g, 4);
		}

	}

	public void key_buy(int key)
	{
		if (s_page == 0)
		{
			switch (key)
			{
				case Constant.KEY_PREV:
					alpha = 0;
					s_page = 0;
					State = M_MENU_KID;
					break;
				case HRcEvent.VK_ENTER:
					s_page = 1;
					p_select_old = p_select = 2;

					break;
			}
		}
		else if (s_page == 1)
		{
			switch (key)
			{
				case Constant.KEY_PREV:
					alpha = 0;
					s_page = 0;
					State = M_MENU_KID;
					break;
				case HRcEvent.VK_UP:
					p_select = (p_select + 2) % 3;
					p_select_old = p_select;
					break;
				case HRcEvent.VK_DOWN:
					p_select = (p_select + 1) % 3;
					p_select_old = p_select;
					break;
				case HRcEvent.VK_ENTER:
					if (p_select == 0)
					{
						if (StateValue.subscriptionCheckOK)
						{
							StateValue.g_action = StateValue.G_ACTION_MONTH;
							// 이미구매했습니다 팝업으로..
							s_page = 4;
							p_select = 0;
							alpha = 80;
							State = M_BUY;
						}
						else
						{
							payType = PAY_MONTHLY;
							s_page = 2;
							p_select = 0;
							pay_activation = 0;
							pay_kind = 0;
							init_number();
						}
					}
					else if (p_select == 1)
					{
						if (StateValue.paymentCheckOK || StateValue.subscriptionCheckOK)
						{
							StateValue.g_action = StateValue.G_ACTION_DAY;
							// 이미구매했습니다 팝업으로..
							s_page = 4;
							p_select = 0;
							alpha = 80;
							State = M_BUY;
						}
						else
						{
							payType = PAY_DAILY;
							s_page = 2;
							p_select = 0;
							pay_activation = 0;
							pay_kind = 0;
							init_number();
						}
					}
					else if (p_select == 2)
					{
						alpha = 0;
						s_page = 0;
						p_select = 0;
						State = M_MENU_KID;
					}
					break;
			}
		}
		else if (s_page == 2)
		{
			if (pay_activation == 1)
			{
				switch (key)
				{
					case HRcEvent.VK_UP:
						pay_activation = 0;
						num_index = number.length();
						break;
					case HRcEvent.VK_LEFT:
					case HRcEvent.VK_RIGHT:
						if (p_select == 0)
							p_select = 1;
						else
							p_select = 0;
						break;
					case Constant.KEY_PREV:
						s_page = 1;
						p_select = p_select_old;
						break;
					case HRcEvent.VK_ENTER:
						if (p_select == 0)
						{// 과금 부여
							if (number.length() > 3)
							{

								// TODO: jy.yu 복구필요.. 원래는 false. 결재작동시 제거해야함???
								b_checkHDSProc = true;
								//b_checkHDSProc = false;

								if (SceneManager.isEmul)
								{
									System.out.println("Cost============================");
									receivedOnlineRegisterSubServiceResult(true, "emul");
								}
								else
								{

									// jy.yu 과금!
									int ckResult = 0; // 0:핀비교실패  1:그라비티실패(핀비교만성공) 2: RP오류 3:성공
									if (SceneManager.getDMCUtil().checkUserPin(number))
										ckResult = 1;

									if (ckResult == 1)
									{
										if (payType == PAY_DAILY)
										{
											if (insertPayment(payType))
											{
												ckResult = 2;
												// 일정액 요청
												if (SceneManager.getDMCUtil().requestPayment())
												{
													ckResult = 3;
												}
											}
										}
										else if (payType == PAY_MONTHLY)
										{
											if (insertPayment(payType))
											{
												ckResult = 2;
												// 월정액 요청
												if (SceneManager.getDMCUtil().requestSubscription())
												{
													ckResult = 3;
												}
											}
										}
									}

									if (ckResult == 3)
									{
										receivedOnlineRegisterSubServiceResult(true, "no use");
									}
									else if (ckResult == 2)
									{ // RP 오류
										receivedOnlineRegisterSubServiceResult(false, "no use");
									}
									else if (ckResult == 1)
									{ // 그라비티 오류
										purchaseFailed(PAY_KD_SERVER_FAIL);
									}
									else
									{ // if (ckResult == 0)
										purchaseFailed(PAY_KD_PIN_FAIL);
									}

								}
							}
							else
							{
								purchaseFailed(PAY_KD_PIN_FAIL);
							}

						}
						else
						{
							//						alpha = 0;
							//						s_page = 0;
							//						p_select = 0;
							//						State = M_MENU;

							// 2011.06.22 jy.yu 팝업이 닫히지 않고 이전단계로 돌아가게함
							s_page = 1;
							p_select = p_select_old;
						}
						break;
				}
			}
			else
			{
				key_number(key);
			}
		}
		else if (s_page == 3)
		{
			switch (key)
			{
				case HRcEvent.VK_ENTER:
				case Constant.KEY_PREV: // 구매 완료 되었을때?
					//				SceneManager.getInstance().sever_check = 3;

					alpha = 0;
					s_page = 0;
					p_select = 0;
					State = M_MENU_KID;

					break;
			}
		}
		else if (s_page == 4)
		{
			switch (key)
			{
				case HRcEvent.VK_ENTER:
				case Constant.KEY_PREV:
					alpha = 0;
					s_page = 0;
					State = M_MENU_KID;
					break;
			}
		}
		else if (s_page == 5)
		{
			switch (key)
			{
				case HRcEvent.VK_ENTER:
				case HRcEvent.VK_DOWN:
					s_page = 6;
					p_select = 0;
					break;
				case Constant.KEY_PREV:
					s_page = 7;
					p_select = 0;
					break;
			}
		}
		else if (s_page == 6)
		{
			switch (key)
			{
				case HRcEvent.VK_LEFT:
				case HRcEvent.VK_RIGHT:
					if (p_select == 0)
						p_select = 1;
					else
						p_select = 0;
					break;
				case Constant.KEY_PREV:
				case HRcEvent.VK_UP:
					s_page = 5;
					break;
				case HRcEvent.VK_ENTER:
					if (p_select == 0)
					{
						s_page = 7;
					}
					else
						s_page = 5;
					break;
			}
		}
		else if (s_page == 7)
		{
			switch (key)
			{
				case HRcEvent.VK_ENTER:
				case HRcEvent.VK_DOWN:
					s_page = 8;
					p_select = 0;
					break;
				case Constant.KEY_PREV:
					s_page = 5;
					break;
			}
		}
		else if (s_page == 8)
		{
			switch (key)
			{
				case HRcEvent.VK_LEFT:
				case HRcEvent.VK_RIGHT:
					if (p_select == 0)
						p_select = 1;
					else
						p_select = 0;
					break;
				case Constant.KEY_PREV:
				case HRcEvent.VK_UP:
					s_page = 7;
					break;
				case HRcEvent.VK_ENTER:
					if (p_select == 0)
					{
						alpha = 0;
						s_page = 0;
						p_select = 0;
						State = M_MENU_KID;
					}
					else
						s_page = 7;
					break;
			}
		}
		else if (s_page == 9)
		{
			switch (key)
			{
				case HRcEvent.VK_ENTER:
				case Constant.KEY_PREV:
					alpha = 0;
					s_page = 0;
					p_select = 0;
					State = M_MENU_KID;
					break;
			}
		}
	}

	public void destroyScene()
	{

		if (imgMenu != null)
		{
			for (int i = 0; i < IMG_MAX; i++)
			{
				if (imgMenu[i] != null)
				{
					SceneManager.getInstance().removeImage(imgMenu[i]);
					imgMenu[i] = null;
				}
			}
			imgMenu = null;
		}
		Log.trace("menu scene - destroyScene 0");

		if (introData != null)
		{
			introData.dispose();
			introData = null;
		}

		if (arrGames != null)
		{
			for (int i = 0; i < arrGames.size(); i++)
			{
				MenuData md = (MenuData) arrGames.get(i);
				md.dispose();
			}
			arrGames.clear();
			arrGames = null;
		}

		if (m_sound != null)
		{
			m_sound.destroySound();
			m_sound = null;
		}
		Log.trace("menu scene - destroyScene 1");

		if (img_popup != null)
		{
			SceneManager.getInstance().removeImage(img_popup);
			img_popup = null;
		}
		if (img_caution != null)
		{
			for (int i = 0; i < 2; i++)
			{
				if (img_caution[i] != null)
				{
					SceneManager.getInstance().removeImage(img_caution[i]);
					img_caution[i] = null;
				}
			}
			img_caution = null;
		}
		Log.trace("menu scene - destroyScene 2");

		if (img_button != null)
		{
			for (int i = 0; i < 2; i++)
			{
				if (img_button[0][i] != null)
				{
					SceneManager.getInstance().removeImage(img_button[0][i]);
					img_button[0][i] = null;
				}
			}
		}
		if (img_button != null)
		{
			for (int i = 0; i < 2; i++)
			{
				if (img_button[1][i] != null)
				{
					SceneManager.getInstance().removeImage(img_button[1][i]);
					img_button[1][i] = null;
				}
			}
		}
		img_button = null;

		if (img_payButton != null)
		{
			for (int i = 0; i < 2; i++)
			{
				if (img_payButton[0][i] != null)
				{
					SceneManager.getInstance().removeImage(img_payButton[0][i]);
					img_payButton[0][i] = null;
				}
			}
		}
		if (img_payButton != null)
		{
			for (int i = 0; i < 2; i++)
			{
				if (img_payButton[1][i] != null)
				{
					SceneManager.getInstance().removeImage(img_payButton[1][i]);
					img_payButton[1][i] = null;
				}
			}
		}
		img_payButton = null;
		Log.trace("menu scene - destroyScene 4");

		amc = null;
	}

	public void DrawImg(Graphics g, Image img, int img_x, int img_y, int Align)
	{
		SceneManager.DrawImg(g, img, img_x, img_y, Align);
	}

	public void DrawStr(Graphics g, String str, int str_x, int str_y)
	{
		SceneManager.DrawStr(g, str, str_x, str_y);
	}

	public void automata_creat()
	{
		if (amc == null)
		{
			amc = new AutoMataContainer();
			amc.setBounds(330, 93, 348, 334);
		}
		amc.addListener(this);
		SceneManager.getInstance().getScene().add(amc, 0);
		amc.initContainer();

		SceneManager.TEMP_ID = null;

		amc.conState = amc.STATE_INPUT;
		amc.keyComponent.state = amc.keyComponent.ID_INPUT;
	}

	String number = null;
	int num_index = 0;
	int num_length;
	boolean isCursor;

	public void init_number()
	{
		number = "";
		num_index = 0;
		num_length = 4;
		isCursor = true;
	}

	public void draw_number(Graphics g)
	{
		g.setColor(Color.black);
		FontType(g, 5);
		for (int i = 0; i < number.length(); i++)
			DrawStr(g, "●", 435 + (31 * i), 270 - 40);
		if (pay_activation == 0)
		{
			int temp = num_index - 1;
			if (temp < 0)
				temp = 0;
			g.fillRect(435 + (temp * 31) - 12, 270 - 40 + 4, 24, 2);
		}
	}

	public void key_number(int key)
	{
		switch (key)
		{
			case HRcEvent.VK_0:
				number = addText(number, "0", num_index);
				break;
			case HRcEvent.VK_1:
				number = addText(number, "1", num_index);
				break;
			case HRcEvent.VK_2:
				number = addText(number, "2", num_index);
				break;
			case HRcEvent.VK_3:
				number = addText(number, "3", num_index);
				break;
			case HRcEvent.VK_4:
				number = addText(number, "4", num_index);
				break;
			case HRcEvent.VK_5:
				number = addText(number, "5", num_index);
				break;
			case HRcEvent.VK_6:
				number = addText(number, "6", num_index);
				break;
			case HRcEvent.VK_7:
				number = addText(number, "7", num_index);
				break;
			case HRcEvent.VK_8:
				number = addText(number, "8", num_index);
				break;
			case HRcEvent.VK_9:
				number = addText(number, "9", num_index);
				break;
			case HRcEvent.VK_ENTER:
			case HRcEvent.VK_DOWN:
				pay_activation = 1;
				break;
			case Constant.KEY_Remove:
			case KeyEvent.VK_LEFT:
				if (num_index > 0)
				{
					number = deleteText(number, num_index);
					num_index--;
				}
				break;
			case Constant.KEY_PREV:
				s_page = 1;
				p_select = p_select_old;
				break;
		}
	}

	public String addText(String src, String text, int addIndex)
	{
		if (src.length() > num_length - 1)
		{
			return src;
		}
		String temp = "";
		if (addIndex == 0)
		{
			temp = text + src;
		}
		else if (src.length() == addIndex)
		{
			temp = src + text;
		}
		else
		{
			temp = src.substring(0, addIndex) + text + src.substring(addIndex, src.length());
		}
		num_index++;
		if (temp.length() == 4)
		{
			pay_activation = 1;
		}
		return temp;
	}

	public String deleteText(String src, int addIndex)
	{
		String temp = "";
		if (addIndex == 1)
		{
			temp = src.substring(1, src.length());
		}
		else if (src.length() == addIndex)
		{
			temp = src.substring(0, src.length() - 1);
		}
		else
		{
			temp = src.substring(0, addIndex - 1) + src.substring(addIndex, src.length());
		}
		return temp;
	}

	// FIX KT 가입
	public void receivedOnlineRegisterSubServiceResult(boolean success, String data)
	{
		if (success)
		{
			Log.trace(this, "receivedOnlineRegisterSubServiceResult - success : " + data);
			//TODO: jy.yu 구매체크하는 코드에서 오류가 발생할 수 있으므로. C&M 코드처럼 수정해야함. (지금처럼 칼라키안쓰는 방식이면 상관은 없음)
			if (payType == PAY_MONTHLY)
				StateValue.g_action = StateValue.G_ACTION_MONTH;
			else
				StateValue.g_action = StateValue.G_ACTION_DAY;
			s_page = 3;
			p_select = 0;

			b_checkHDSProc = true;
		}
		else
		{
			Log.trace(this, "receivedOnlineRegisterSubServiceResult - fail : " + data);
			if (payType == PAY_DAILY)
				deleteDayPayment();

			StateValue.g_action = 0;

			purchaseFailed(PAY_KD_RP_FAIL);

			b_checkHDSProc = true;
		}
	}

	public void receiveString(String str)
	{
		Log.trace(this, "__________str__________ : " + str);
		Log.trace(this, "_________amc.keyComponent.pcontent_________ : " + amc.keyComponent.state);
		SceneManager.TEMP_ID = str;
	}

	private void purchaseFailed(int kind)
	{
		p_select = 0;
		pay_activation = 0;
		pay_kind = kind;

		if (kind != PAY_KD_RP_FAIL)
			StateValue.PAY_ERR_CD = 0;

		// !! PAY_KD_SERVER_FAIL 이나 PAY_KD_RP_FAIL 일때는 
		// 에러팝업으로 대체되었음..
		if (kind == PAY_KD_SERVER_FAIL)
		{
			pay_kind = 0;
			// alpha =120;
			s_page = 0;
			State = M_ERROR1;
		}
		else if (kind == PAY_KD_RP_FAIL)
		{
			pay_kind = 0;
			s_page = 0;
			State = M_ERROR2;
		}
		// 핀번호 틀렸을때는 에러팝업안띄우고 숫자입력팝업만 초기화 시킴.
		else if (kind == PAY_KD_PIN_FAIL)
		{
			pay_activation = 0;
			init_number();
		}
	}

	// 그라비티서버에 결재 기록  요청.
	private boolean insertPayment(int payType)
	{
		//SceneManager.getInstance().sendaccouting_gravity(payType);  // 0:월정액  1:일정액

		StateValue.insertPay = payType;
		SceneManager.getInstance().send_gravity(5);

		if (SceneManager.getInstance().sever_msg == 0)
		{
			Log.trace(this, "##@@------- 1");
			if (SceneManager.getInstance().SeverKind == 1)
			{
				Log.trace(this, "##@@------- 2");
				return true;
			}
		}
		Log.trace(this, "##@@------- 3");

		return false;
	}

	// 그라비티서버에 일결재 삭제  요청.   
	//  (리턴값 0: 성공.  1:실패.  -1:오류)
	private boolean deleteDayPayment()
	{

		SceneManager.getInstance().send_gravity(6);

		if (SceneManager.getInstance().sever_msg == 0)
		{
			Log.trace(this, "##@@$$------- 1");
			if (SceneManager.getInstance().SeverKind == 0)
			{ // 0이면 삭제성공!
				Log.trace(this, "##@@$$------- 2 success: " + SceneManager.getInstance().SeverKind);
				return true;
			}
			else
			{ // 1이면 삭제실패!
				Log.trace(this, "##@@$$------- 2 fail: " + SceneManager.getInstance().SeverKind);
				return false;
			}
		}
		Log.trace(this, "##@@$$------- 3");

		return false;
	}

}