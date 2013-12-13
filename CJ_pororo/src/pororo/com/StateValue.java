package pororo.com;

public class StateValue {
	
	///////////////////////////////////
	// 결제용
	///////////////////////////////////
	public static String EXPIRE_DATE;// = "20991231";  // 월정액 만기일
	public static String SERVICE_ID;// = "DS11215";
	public static String PRODUCT_DAY_ID;// = "W0011N00075";
	public static String PRODUCT_MONTH_ID;// = "R0011N00013";
	public static String RP_SERVER_LIVE;// = "10.10.20.40:8080";
	public static String RP_SERVER_TEST;// = "10.9.4.140:8080";
	public static boolean isLive;// = false;
	
	// 가격
	public static int PRICE_MONTH;// = 3500; 
	public static int PRICE_DAY;// = 1000;

	// Test용이라 게임 실행시 인증부분 넘어 갈꺼냐??
//	public static boolean isCostSkip = true;
	public static boolean isCostSkip = false;

	// 서버 연결이 Live 이냐??
	public static final boolean isUrlLive = false;  // 항상 false 로 두면 됨.
	
	public static String gravity_server;// = "http://192.168.97.34/";
	public static String testResource;// = "http://192.168.97.34/cj_pororo_s1/";
	public static String liveResource;// = "http://192.168.97.34/cj_pororo_s1/";

	public static String version;// = "1.0.1";
	public static final boolean USEDIMG_TEST = false;  // 이미지 offscreen 메모리 테스트 (이걸로 체크되진 않지만 i-frame 도 메모리를 차지하고, fillRect 할때도 순간적으로 메모리를 사용하는 것에 주의!)
	
	///////////////////////////////////
	//
	// static 설정값
	//
	///////////////////////////////////
	public static int play_log_level = 0;
	
	public static final int G_ACTION_DAY = 1;
	public static final int G_ACTION_MONTH = 2;
	public static int g_action = 0; // 과금 여부
	
	// 땜빵.. SceneManager에서 그라비티서버에 결재확인 요청시 사용.. 
	public static int insertPay = 0;   // 월정액: 0  일정액: 1

	// 일정액 가입여부 체크.
	public static boolean paymentCheckOK = false;
	// 월정액 가입여부 체크.
	public static boolean subscriptionCheckOK = false;
	
	// 결재시 에러체크..
	public static boolean ERR_AT_CHECK_SUBSCRPT = false; 
	public static int PAY_ERR_CD = 0;  // 결재관련 에러코드
	
	// OverlappedUI 방식땜에..
	public static boolean isAutomataVisible = false;
	
}