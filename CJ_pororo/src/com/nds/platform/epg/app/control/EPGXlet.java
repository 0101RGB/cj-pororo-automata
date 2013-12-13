/* =================================================================
 * Copyright (c) 2006 -2010: NDS Ltd.
 * P R O P R I E T A R Y C O N F I D E N T I A L
 * The copyright of this document is vested in NDS Ltd. without
 * whose prior written permission its contents must not be published,adapted
 * or reproduced in any form or disclosed or issued to any third party.
 * =================================================================
 */
package com.nds.platform.epg.app.control;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.acetel.cj.vod.ixc.SearchResultModel;

/**
 */
public interface EPGXlet extends Remote
{
	/**
	 * Smart Card ID 의 변경 여부를 notify 받고자 할때 사용하는 mask 값.<br>
	 */
	public static final int MASK_CARD_ID = 0x01;
	/**
	 * Subscriber ID 의 변경 여부를 notify 받고자 할때 사용하는 mask 값.<br>
	 */
	public static final int MASK_SUBS_ID = 0x02;
	/**
	 * SO Code 의 변경 여부를 notify 받고자 할때 사용하는 mask 값.<br>
	 */
	public static final int MASK_SO_CODE = 0x04;
	/**
	 * Region Bytes 의 변경 여부를 notify 받고자 할때 사용하는 mask 값.<br>
	 */
	public static final int MASK_REGION_BYTE = 0x08;
	/**
	 * CAS ID 의 변경 여부를 notify 받고자 할때 사용하는 mask 값.<br>
	 */
	public static final int MASK_CAS_ID = 0x10;
	/**
	 * 시청연령제한 설정값의 변경 여부를 notify 받고자 할때 사용하는 mask 값.<br>
	 */
	public static final int MASK_PR = 0x20;

	/**
	 * Standby, 독립형 어플실행중에 해당하는 상태.<br>
	 */
	public static final int EPG_PAUSE = 1;
	/**
	 * 채널시정중인 상태.<br>
	 */
	public static final int EPG_TV_WATCHING = 2;
	/**
	 * VOD시청중인 상태.<br>
	 */
	public static final int VOD_WATCHING = 3;
	/**
	 * EPG 홈메뉴 실행중인 상태.<br>
	 */
	public static final int EPG_MENU = 4;
	/**
	 * VOD 홈메뉴 실행중인 상태.<br>
	 */
	public static final int VOD_MENU = 5;
	/**
	 * @deprecated
	 */
	public static final int PARAM_LCW = 6;
	/**
	 * @deprecated
	 */
	public static final int PARAM_FAV = 7;
	/**
	 * @deprecated
	 */
	public static final int PARAM_NULL = 8;
	/**
	 * @deprecated
	 */
	public static final int PARAM_SEARCH = 13;
	/**
	 * @deprecated
	 */
	public static final int FOCUS_FIRST = 9;
	/**
	 * @deprecated
	 */
	public static final int FOCUS_VOD_CATEGORY = 10;
	/**
	 * @deprecated
	 */
	public static final int FOCUS_VOD_HELP = 11;
	/**
	 * @deprecated
	 */
	public static final int FOCUS_NULL = 12;
	/**
	 * @deprecated
	 */
	public static final int FOCUS_VOD_PREMIERE = 13;
	/**
	 * @deprecated
	 */
	public static final int FOCUS_VOD_TOP = 14;
	/**
	 * @deprecated
	 */
	public static final int VOD_STOPPED = 1;
	/**
	 * @deprecated
	 */
	public static final int VOD_ERROR = 2;

	/**
	 * @deprecated
	 */
	public static final int VOD_CONFIRM_OK = 1;
	/**
	 * @deprecated
	 */
	public static final int VOD_CONFIRM_CANCEL = 0;

	/**
	 * EPG Organization ID.<br>
	 */
	public static final String EPG_OID = "10000000";

	/**
	 * EPG Application ID.<br>
	 */
	public static final String EPG_APP_ID = "3000";

	/**
	 * EPG RemoteApplicationName.
	 */
	public static final String RMI_APP_NAME = "NDSEPGXLET";

	/**
	 */
	public int VOD_LINK_ASSET = 1;

	/**
	 */
	public int VOD_LINK_FOLDER = 2;

	/**
	 * SO를 구분하기 위해 사용하는 LVCT 로 전달되는 ID 값.<br>
	 */
	public int mapId = -1;

	public static final String[] MAP_ID_NAME = { "양천", // 0
	"북인천", // 1
	"해운대", // 2
	"중부산", // 3
	"경남", // 4
	"가야", // 5
	"아름", // 6
	"푸른", // 7
	"남인천", // 8
	"중앙", // 9
	"금정", // 10
	"영남", // 11
	"충남", // 12
	"부천", // 13
	"은평", // 14
	"대구", // 15
	"영동", // 16
	"아라", // 17
	"신라", // 18
	};

	/**
	 * EPG 의 상태 전환을 요청한다. Monitor App 만 사용해야 한다.<br>
	 * 
	 * @param state
	 *            전환해야할 state
	 * @param sourceid
	 *            state 전환시 채널 전환이 필요한 경우, 해당 채널의 source ID. 채널 전환이 필요 없을 경우, 0
	 * @throws RemoteException
	 */
	public void EPG_changeState(int state, int sourceid) throws RemoteException;

	/**
	 * @deprecated
	 */
	public void EPG_VOD_changeState(int newState, int param, int focus) throws RemoteException;

	/**
	 * @deprecated
	 */
	public void showEpgMenu() throws RemoteException;

	/**
	 * @deprecated
	 */
	public void hideEpgMenu() throws RemoteException;

	/**
	 * EPG의 현재 state 를 얻어온다.<br>
	 */
	public int EPG_getState() throws RemoteException;

	/**
	 * @deprecated
	 */
	public int EPG_getActualState() throws RemoteException;

	/**
	 * 특정 package 에 대한 가입 여부를 확인 한다.<br>
	 * 
	 * @param packageID
	 * @return 가입되어 있는 경우 true
	 * @throws RemoteException
	 */
	public boolean isPackageSubscribed(int packageID) throws RemoteException;

	/**
	 * 현재 셋팅된 시청연령 제한 값을 확인 한다.<br>
	 * 
	 * @return "0" - All<br>
	 *         "7" - 7세<br>
	 *         "12" - 12세<br>
	 *         "15" - 15세<br>
	 *         "19" - 19세<br>
	 * @throws RemoteException
	 */
	public String getCurrentParentalRating() throws RemoteException;

	/**
	 * Smart Card ID 를 리턴한다.<br>
	 * 0, null, 혹은 음수인 경우는 사용해서는 안된다.<br>
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getSmartCardID() throws RemoteException;

	/**
	 * Subscriber ID 를 리턴한다.<br>
	 * 0, null, 혹은 음수인 경우는 사용해서는 안된다.<br>
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getSubscriberID() throws RemoteException;

	/**
	 * CAS ID 를 리턴한다.<br>
	 * 0, null, 혹은 음수인 경우는 사용해서는 안된다.<br>
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getCASID() throws RemoteException;

	/**
	 * Super CAS ID 를 리턴한다.<br>
	 * 0, null, 혹은 음수인 경우는 사용해서는 안된다.<br>
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getSuperCASID() throws RemoteException;

	/**
	 * Region Bytes 를 리턴한다.<br>
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public byte[] getRegionBits() throws RemoteException;

	/**
	 * 특정 범위의 Region Bytes 를 리턴한다.<br>
	 * 
	 * @param from
	 *            시작 index
	 * @param to
	 *            끝 index
	 * @return
	 * @throws RemoteException
	 */
	public byte[] getRegionBits(int from, int to) throws RemoteException;

	/**
	 * SO Code 를 리턴한다.<br>
	 * 0, 혹은 음수인 경우는 사용해서는 안된다.<br>
	 * 
	 * @return 가야: 40<br>
	 *         마산: 41<br>
	 *         양천: 43<br>
	 *         경남: 44<br>
	 *         중부산: 45<br>
	 *         북인천: 46<br>
	 *         아름: 47<br>
	 *         푸른:48<br>
	 *         남인천: 49<br>
	 *         영남: 50<br>
	 *         충남: 51<br>
	 *         중앙: 52<br>
	 *         금정: 53<br>
	 *         부천/김포: 54<br>
	 *         은평: 55<br>
	 *         영동: 56<br>
	 *         동구/수성(대구): 57<br>
	 *         아라(순천): 58<br>
	 *         신라: 59<br>
	 * @throws RemoteException
	 */
	public int getSoNumber() throws RemoteException;

	/**
	 * 비밀번호가 정확한지 확인한다.<br>
	 * 단, 사용자가 입력한 비밀번호가 4자리 인지는 개별 어플에서 미리 체크해야 한다. ("0" 과 "0000" 모두 integer 로는 '0' 이지만, "0"은 올바른 비밀번호가 아니기 때문)<br>
	 * 
	 * @param pin
	 * @return
	 * @throws RemoteException
	 */
	public boolean checkPinCode(int pin) throws RemoteException;

	/**
	 * 구매비밀번호가 정확한지 확인한다.<br>
	 * 단, 사용자가 입력한 비밀번호가 4자리 인지는 개별 어플에서 미리 체크해야 한다. ("0" 과 "0000" 모두 integer 로는 '0' 이지만, "0"은 올바른 비밀번호가 아니기 때문)<br>
	 * 
	 * @param pin
	 * @return
	 * @throws RemoteException
	 */
	public boolean checkPurchasePinCode(int pin) throws RemoteException;

	/**
	 * {@link CardInfoUpdateListener}를 등록 한다.<br>
	 * Listener 를 등록한 어플은, 종료될때 반드시 {@link #removeCardInfoUpdateLIstener(CardInfoUpdateListener)}를 호출해야 한다.<br>
	 * 
	 * @param listener
	 *            등록할 listener
	 * @param mask
	 *            notify 받고자 하는 정보의 mask 값. {@link #MASK_CARD_ID} | {@link #MASK_SUBS_ID} | {@link #MASK_SO_CODE} | {@link #MASK_REGION_BYTE} | {@link #MASK_CAS_ID} | {@link #MASK_PR}
	 * @throws RemoteException
	 */
	public void addCardInfoUpdateListener(CardInfoUpdateListener listener, int mask) throws RemoteException;

	/**
	 * {@link CardInfoUpdateListener}를 제거 한다.<br> {@link #addCardInfoUpdateListener(CardInfoUpdateListener, int)}로 listener 를 등록한 어플은, 종료될때 반드시 호출해야 한다.<br>
	 * 
	 * @param listener
	 *            제거할 listener
	 * @throws RemoteException
	 */
	public void removeCardInfoUpdateLIstener(CardInfoUpdateListener listener) throws RemoteException;

	/**
	 * Display iTV I-Frame which is stored at FFS. Must be called by only Monitor Application.<br>
	 * 
	 * @throws RemoteException
	 */
	public void displayLoadingIframe() throws RemoteException;

	/**
	 * {@link EPGAppStateNotificationListener}를 등록 한다.<br>
	 */
	public void addStateChangeListener(EPGAppStateNotificationListener listener) throws RemoteException;

	/**
	 * {@link EPGAppStateNotificationListener}를 제거 한다.<br>
	 */
	public void removeStateChangeListener(EPGAppStateNotificationListener listener) throws RemoteException;

	/**
	 * @deprecated
	 */
	public void returnVODSearchResult(SearchResultModel[] resultList) throws RemoteException;

	/**
	 * @deprecated
	 */
	public void requestShowVODResultPage(String assetID) throws RemoteException;

	/**
	 * iAD FULL_MODE 에서 이전 EPG상태로 돌아갈때 호출됨.<br>
	 */
	public void backFromiAD() throws RemoteException;

	/**
	 * @deprecated
	 */
	public void confirmVODStop(int parameter) throws RemoteException;

	/**
	 * @deprecated
	 */
	public void notifyVODevent(int vodEvent) throws RemoteException;

	/**
	 * @deprecated
	 */
	public int getChannelStatus() throws RemoteException;

	/**
	 * @deprecated
	 */
	public void addChannelStatusListener(ChannelStatusListener listener) throws RemoteException;

	/**
	 * @deprecated
	 */
	public void removeChannelStatusListener(ChannelStatusListener listener) throws RemoteException;

	/**
	 * iAD에서 VOD 링크로 이동하고자 할때 호출됨.<br>
	 * 
	 * @param id
	 *            asset 혹은 folder ID
	 * @param folder
	 *            {@link #VOD_LINK_ASSET} 혹은 {@link #VOD_LINK_FOLDER}
	 * @throws RemoteException
	 */
	public void requestVODLinkFromIAD(String id, int folder) throws RemoteException;

	/**
	 * EPG가 LVCT에서 parsing 한 MAP ID 를 얻어온다.<br>
	 * EPG도 MAP ID를 모를 경우(section filter 아직 올라오지 않은 경우나, parsing 시 에러가 난 경우)는 -1을 리턴한다.<br>
	 * 리턴값이 -1이면, 추후에 다시 물어봐야 한다.<br>
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getMapId() throws RemoteException;

	/**
	 * EPG Client 가 HD EPG인지 확인.<br>
	 * 
	 * @return HD EPG인경우 true. HD EPG가 아닌경우, 본 method 가 지원되지 않기 때문에 NoSuchMethodError 를 catch 하여야 함.
	 * @throws RemoteException
	 */
	public boolean isHDEpg() throws RemoteException;

	/**
	 * EPG Client의 UI VBM 기능 동작 여부 확인.<br>
	 * 
	 * @return UI VBM 기능이 동작하는 경우 true.
	 * @throws RemoteException
	 */
	public boolean isUiVbmEnabled() throws RemoteException;

	/**
	 * @deprecated
	 */
	public int COUPON_MENU_SHOP = 1;
	/**
	 * @deprecated
	 */
	public int COUPON_MENU_MY_COUPON = 2;
	/**
	 * @deprecated
	 */
	public int COUPON_MENU_REGISTER = 3;

	/**
	 * @deprecated
	 */
	public void showCouponMenu(int menu) throws RemoteException;

	/**
	 * @deprecated
	 */
	public void showCouponShop() throws RemoteException;

	/**
	 */
	public void invalidateCouponCache() throws RemoteException;
}
