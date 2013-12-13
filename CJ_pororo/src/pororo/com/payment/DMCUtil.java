package pororo.com.payment;

import javax.tv.xlet.XletContext;

public interface DMCUtil {
	// CUSTOMER정보 종류
	public static final int CUSTOMER_NAME = 0;
	
	public boolean isHD();
	
	public void setXletContext(XletContext ctx);
	
	public void dispose();
	
	public boolean isLive();

	public String getSmartCardId();

	public String getSubscriberID();   // 가입자 아이디

	// 고객식별용으로 쓸 수 없다함.. 사용하지말것! (형식도 확인해봐야한다 4자리인지 헥사 8자리인지..)
	public String getCASID();
	
	// 해당 상품을 구매했는지 판단하기 위함인듯.
	public boolean checkPurchasePin(String pin);
	
	// 단순 본인확인용인듯.. 뽀로로에서는 이걸사용하는게 맞을듯한데? 구매 전에 확인하니까..
	// 음.. 성인컨텐츠이용시나.. 뭐 지울때라든가 중요한 상황에 본인  확인용인듯
	public boolean checkUserPin(String pin);


	/**
	 * 고객정보조회
	 * @param kind 조회할 정보종류 (CustomerCode, Address, Name...)
	 * @return 조회된 정보 결과값
	 */
	public String getCustomerInfo(int kind);

	public boolean requestPayment();
	public boolean requestSubscription();
	
	
	// 정액제 상품가입 체크
	public boolean checkPayment();
	public boolean checkSubscription();
	 
	// 채널 변경
	public boolean changeService(XletContext xletContext, int src_id);	
	
	// 나가기
	public void goToExit();

}
