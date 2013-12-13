package pororo.com.payment;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.tv.xlet.XletContext;

import monitor.ixc.ApplicationCoordinator;

import org.dvb.io.ixc.IxcRegistry;

import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.log.Log;

import com.alticast.lrpp.client.LrppException;
import com.alticast.lrpp.client.LrppSession;
import com.cj.spi.common.iTVUser;
import com.cj.spi.common.client.CommonPrdtScrptSpiClientStub;
import com.cj.spi.common.client.CommonSpiClientStub;
import com.cj.spi.common.client.CommonUsageSpiClientStub;
import com.cj.spi.common.domain.ItvUsageParam;
import com.cj.spi.common.domain.ItvUser;
import com.cj.spi.common.domain.ProductSubscriptionInfo;
import com.cj.spi.common.exception.CommonException;
import com.nds.platform.epg.app.control.EPGXlet;

public class CJ_DMCUtil implements DMCUtil
{

	private SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMdd");

	private String smartCardId;
	private EPGXlet epgXlet;
	private XletContext xletCtx;
	private String subscriberID;
	private String casID;
	private iTVUser iTvUser;
	private String spiSvrUrl;

	public boolean isHD()
	{
		String highdef = System.getProperty("ocap.system.highdef");
		Log.trace(this, "highdef ????? : " + highdef);
		boolean isHD = "true".equalsIgnoreCase(highdef);
		if (SceneManager.isEmul)
		{
			isHD = true;
		}
		return isHD;
	}

	public CJ_DMCUtil()
	{
		Log.trace(this, "constructor!");
	}

	public void dispose()
	{
		smartCardId = null;
		epgXlet = null;
		xletCtx = null;
		subscriberID = null;
		iTvUser = null;
		spiSvrUrl = null;
	}

	public boolean isLive()
	{
		return StateValue.isLive;
	}

	public void setXletContext(XletContext ctx)
	{
		this.xletCtx = ctx;
	}

	public String getSmartCardId()
	{
		Log.trace(this, "getSmartCardId ============== 0");
		try
		{
			if (smartCardId == null)
			{
				EPGXlet epg = getEpgXlet();
				if (epg == null)
				{
					Log.errmsg(this, "received epg is null~~~!");
					return smartCardId;
				}

				String scid = null;
				try
				{
					scid = epg.getSmartCardID();
					Log.trace(this, "EPGXlet.getSmartCardID() --> " + scid);
				}
				catch (RemoteException e)
				{
					Log.error(this, e);
				}

				if ((scid == null) || (scid.length() == 0))
				{
					Log.errmsg(this, "scid is empty: " + scid);
				}
				else if (scid.equalsIgnoreCase("0"))
				{
					Log.errmsg(this, "scid is 0: " + scid);
				}
				else if (scid.equalsIgnoreCase("-1"))
				{
					Log.errmsg(this, "scid is -1: " + scid);
				}
				else if (scid.length() < 11)
				{
					int cnt = 11 - scid.length();
					for (int i = 0; i < cnt; ++i)
					{
						scid = "0" + scid;
					}
					Log.trace(this, "EPGXlet.getSmartCardID() fixed --> " + scid);
				}
				this.smartCardId = scid;
			}// end of if
		}
		catch (Exception e)
		{
			Log.error(this, e);
		}
		Log.trace(this, "getSmartCardId ============== 1");
		return this.smartCardId;
	}

	// STB Code, Unique ID 등으로 사용됨..  유니크한 셋탑아이디라고 생각하면 될듯.
	public String getSubscriberID()
	{
		if (this.subscriberID == null)
		{
			Log.trace(this, "Set subscriberID");

			EPGXlet epg = getEpgXlet();
			if (epg == null)
				return subscriberID;

			String sId = null;
			try
			{
				sId = epg.getSubscriberID();
				Log.trace(this, "EPGXlet.getSubscriberID() --> " + sId);
			}
			catch (RemoteException e)
			{
				Log.error(this, e);
			}

			if ((sId == null) || (sId.length() == 0))
				Log.errmsg(this, "subscriberID is empty" + sId);
			else if (sId.equalsIgnoreCase("0"))
				Log.errmsg(this, "subscriberID is 0" + sId);
			else if (sId.equalsIgnoreCase("-1"))
				Log.errmsg(this, "subscriberID is -1" + sId);

			this.subscriberID = sId;
		}

		return this.subscriberID;
	}

	// 고객식별용 아닌듯.. 사용하지 말것.
	public String getCASID()
	{
		if (this.casID == null)
		{
			Log.trace(this, "Set casID");

			EPGXlet epg = getEpgXlet();
			if (epg == null)
				return casID;

			String idTmp = null;
			try
			{
				idTmp = epg.getCASID();
				Log.trace(this, "EPGXlet.getCasID() --> " + idTmp);
			}
			catch (RemoteException e)
			{
				Log.error(this, e);
			}

			if ((idTmp == null) || (idTmp.length() == 0))
				Log.errmsg(this, "casID is empty" + idTmp);
			else if (idTmp.equalsIgnoreCase("0"))
				Log.errmsg(this, "casID is 0" + idTmp);
			else if (idTmp.equalsIgnoreCase("-1"))
				Log.errmsg(this, "casID is -1" + idTmp);

			this.casID = idTmp;
		}

		return this.casID;
	}

	// 해당 상품을 구매했는지 판단하기 위함인듯.
	public boolean checkPurchasePin(String pin)
	{
		return false;
	}

	// 본인확인용인듯..
	public boolean checkUserPin(String pin)
	{
		if ((pin == null) || (pin.length() < 4))
			return false;

		EPGXlet epg = getEpgXlet();
		if (epg == null)
			return false;

		try
		{
			int p = Integer.parseInt(pin);
			//			boolean res = epg.checkPinCode(p);
			boolean res = epg.checkPurchasePinCode(p);
			Log.trace(this, "EPGXlet.checkPinCode(" + p + ") --> " + res);
			return res;
		}
		catch (NumberFormatException e)
		{
			Log.error(this, "pin: " + pin, e);
		}
		catch (RemoteException e)
		{
			Log.error(this, e);
		}
		return false;
	}

	/**
	 * 고객정보조회
	 * 
	 * @param kind
	 *            조회할 정보종류 (CustomerCode, Address, Name...)
	 * @return 조회된 정보 결과값
	 */
	public String getCustomerInfo(int kind)
	{
		Log.trace(this, "=getCustomerInfo=");
		// TODo: 여기서 iTVUser를 얻어서 원하는 값을 리턴하자!!
		iTVUser user = null;
		try
		{
			user = getITVUser();
		}
		catch (Exception e)
		{
			Log.error(this, e);
		}

		if (user == null)
			return null;

		Log.trace(this, "__User Code: " + user.getCode());
		Log.trace(this, "__User Customer Code: " + user.getCustomerCode());
		Log.trace(this, "__User Name: " + user.getName());
		Log.trace(this, "__User SoCode: " + user.getSoCode());

		switch (kind)
		{
			case CUSTOMER_NAME:
				String name = user.getName();
				Log.trace(this, "User Name: " + name);
				//Log.trace(this, "User Name len: " + name.length());

				//String ksName = null;
				//ksName = Lib.convertToKSC5601(name);
				//Log.trace(this, "KS User Name: " + ksName);
				//Log.trace(this, "KS User Name len: " + ksName.length());		

				if (name.length() > 6)
				{
					name = name.substring(0, 6);
					Log.trace(this, "fixed User Name: " + name);
					//Log.trace(this, "fixed User Name len: " + name.length());

					//ksName = Lib.convertToKSC5601(name);
					//Log.trace(this, "fixed KS User Name: " + ksName);
					//Log.trace(this, "fixed KS User Name len: " + ksName.length());						
				}

				return name;
		}

		// 원하는 값을 리턴해야함!
		return null;
	}

	/**
	 * TODO: 만약 그냥 CommonSpi로 값을 제대로 획득하지 못하면, CommonPrdtScrptSpi로 교체한다!!!
	 * 
	 * 여기서 리턴받은 iTVUser 로 부터 CustomerCode, Address, Name 등을 획득한다.
	 * 
	 * @return iTVUser
	 */
	private iTVUser getITVUser()
	{
		// !!! ItvUser 가 아니다! 대소문자 헷갈리지말자 !!!

		if (this.iTvUser == null)
		{
			CommonSpiClientStub client = createCommonSpiClient();
			LrppSession session = lrppLogin(client.getSession());
			if (session == null)
				return this.iTvUser;

			String scid = getSmartCardId();
			iTVUser user = null;
			try
			{
				Log.trace(this, "CommonSpiClientStub.readUser()");
				Log.trace(this, "terminalCode: " + scid);
				user = client.readUser(scid);
				if (user == null)
					Log.errmsg(this, "readUser returns null!!");
				this.iTvUser = user;
			}
			catch (CommonException e)
			{
				printCommonException(e);
			}
			catch (LrppException e)
			{
				printLrppException(e);
			}
			catch (Exception e)
			{
				Log.error(this, e);
			}
			finally
			{
				lrppLogout(session);
			}
		}
		return this.iTvUser;
	}

	// 종량제
	public boolean requestPayment()
	{
		return createPayment(false, StateValue.PRICE_DAY, null, null);
	}

	// 정액제
	// 상품코드, 데이터서비스코드 필요.. 요청하자!!
	public boolean requestSubscription()
	{
		Date start = new Date();
		String startTime = this.dateForm.format(start);
		String endTime = StateValue.EXPIRE_DATE;
		return createPayment(true, StateValue.PRICE_MONTH, startTime, endTime);
	}

	private boolean createPayment(boolean isSubscription, int price, String startDate, String endDate)
	{
		String svcId = getServiceId();
		if ((svcId == null) || (svcId.length() == 0))
			Log.errmsg(this, "serviceId: " + svcId);

		String productId = null;
		if (isSubscription)
		{// 정액제
			productId = getProductMonthId();
			if ((productId == null) || (productId.length() == 0))
			{
				Log.errmsg(this, "productMonthId: " + productId);
			}

			return createPrdtSubscrpt(productId, svcId, price, startDate, endDate);
		}
		else
		{ // 종량제
			final int count = 1;
			productId = getProductDayId();
			if ((productId == null) || (productId.length() == 0))
			{
				Log.errmsg(this, "productDayId: " + productId);
			}

			return createPrdtUsage(productId, svcId, price, count);
		}
	}

	// 종량제
	private boolean createPrdtUsage(String prdtCode, String dataSvcCode, int price, int count)
	{

		// 혹시 여기도.. 안쓰더라도 이걸 거쳐야 하나..(정액제 체크처럼)?
		// ItvUser user = client.readUser(scid);

		CommonUsageSpiClientStub client = createCommonUsageSpiClient();
		LrppSession session = lrppLogin(client.getSession());
		if (session == null)
			return false;

		String scid = getSmartCardId();
		String txSvcCode = "spi.common.usage";
		ItvUsageParam usage = new ItvUsageParam();
		usage.setCnt(count);
		usage.setDataServiceCode(dataSvcCode);
		usage.setProductCode(prdtCode);
		usage.setSmartCardId(scid);
		usage.setUsePrice(price);
		usage.setTxServiceCode(txSvcCode);
		int res = -9999;
		try
		{
			res = client.createUsage(usage);
			StateValue.PAY_ERR_CD = res;
		}
		catch (CommonException e)
		{
			printCommonException(e);
			return false;
		}
		catch (LrppException e)
		{
			printLrppException(e);
			return false;
		}
		catch (Exception e)
		{
			Log.error(this, e);
			return false;
		}
		finally
		{
			lrppLogout(session);
			Log.trace(this, "CommonUsageSpiClientStub.createUsage() --> " + res);
			Log.trace(this, "Cnt            : " + count);
			Log.trace(this, "DataServiceCode: " + dataSvcCode);
			Log.trace(this, "ProductCode    : " + prdtCode);
			Log.trace(this, "SmartCardId    : " + scid);
			Log.trace(this, "UsePrice       : " + price);
			Log.trace(this, "TxServiceCode  : " + txSvcCode);
		}
		return true;
	}

	// 정액제
	private boolean createPrdtSubscrpt(String prdtCode, String dataSvcCode, int price, String startDate, String endDate)
	{

		CommonPrdtScrptSpiClientStub client = createCommonPrdtScrptSpiClient();
		LrppSession session = lrppLogin(client.getSession());
		if (session == null)
			return false;

		String scid = getSmartCardId();
		String txSvcCode = "spi.common.prdtscrpt";

		// FIXMe: ItvUser를 PrdtSubscrpt 용으로 교체해야함!!!
		ItvUser user = null;
		try
		{
			user = client.readUser(scid);
		}
		catch (CommonException e1)
		{
			printCommonException(e1);
			lrppLogout(session);
			return false;
		}
		catch (LrppException e1)
		{
			printLrppException(e1);
			lrppLogout(session);
			return false;
		}

		ProductSubscriptionInfo scrpt = new ProductSubscriptionInfo();
		String cscd = user.getSoCode() + user.getCustomerCode();
		scrpt.setCS_CD(cscd);
		scrpt.setPRDT_CD(prdtCode);
		scrpt.setTM_CD(scid);
		scrpt.setPRDT_SCRPT_STR_DATE(startDate); // "20101109"
		scrpt.setPRDT_SCRPT_END_DATE(endDate); // "20101231"
		scrpt.setTRANS_SVC_CD(txSvcCode);
		scrpt.setDATA_SVC_CD(dataSvcCode);
		scrpt.setUSE_PRICE(price);

		int res = -9999;
		try
		{
			res = client.createProductSubscription(scrpt);
			StateValue.PAY_ERR_CD = res;
		}
		catch (CommonException e)
		{
			printCommonException(e);
			return false;
		}
		catch (LrppException e)
		{
			printLrppException(e);
			return false;
		}
		catch (Exception e)
		{
			Log.error(this, e);
			return false;
		}
		finally
		{
			lrppLogout(session);
			Log.trace(this, "CommonPrdtScrptSpiClientStub.createProductSubscription() --> " + res);
			Log.trace(this, "startDate      : " + startDate);
			Log.trace(this, "endDate        : " + endDate);
			Log.trace(this, "CS_CD          : " + cscd);
			Log.trace(this, "DataServiceCode: " + dataSvcCode);
			Log.trace(this, "ProductCode    : " + prdtCode);
			Log.trace(this, "SmartCardId    : " + scid);
			Log.trace(this, "UsePrice       : " + price);
			Log.trace(this, "TxServiceCode  : " + txSvcCode);
		}
		return true;
	}

	// 종량제 상품구매 체크
	// 리턴값이 false 일 경우   << "가입되지 않은 사용자 입니다." >> 에러 문구 표시!!!
	public boolean checkPayment()
	{
		String productId = getProductDayId();
		if ((productId == null) || (productId.length() == 0))
			Log.errmsg(this, "productId: " + productId);

		return checkPrdtUsage(productId);
	}

	// 정액제 상품가입 체크
	// 리턴값이 false 일 경우   << "가입되지 않은 사용자 입니다." >> 에러 문구 표시!!!
	public boolean checkSubscription()
	{
		String productId = getProductMonthId();
		if ((productId == null) || (productId.length() == 0))
			Log.errmsg(this, "productId: " + productId);

		return checkPrdtSubscrpt(productId);
	}

	private boolean checkPrdtUsage(String prdtCode)
	{
		boolean result = false;

		CommonUsageSpiClientStub client = createCommonUsageSpiClient();
		LrppSession session = lrppLogin(client.getSession());
		if (session == null)
			return result;

		String scid = getSmartCardId();
		String txSvcCode = "spi.common.usage";

		// FIXMe: ItvUser를 PrdtSubscrpt 용으로 교체해야함!!!
		try
		{
			ItvUser user = client.readUser(scid);
			Log.trace(this, "user.getCustomerCode: " + user.getCustomerCode());
			Log.trace(this, "user.getStatus: " + String.valueOf(user.getStatus()));

			// TODo: jy.yu 종량제 체크 결과에 따라서 result값을 바꿔줘야  한다!!!

		}
		catch (CommonException e1)
		{
			printCommonException(e1);
		}
		catch (LrppException e1)
		{
			printLrppException(e1);
		}
		finally
		{
			lrppLogout(session);
			Log.trace(this, "CommonUsageSpiClientStub.check.. --> " + result);
			Log.trace(this, "ProductCode    : " + prdtCode);
			Log.trace(this, "SmartCardId    : " + scid);
			Log.trace(this, "TxServiceCode  : " + txSvcCode);
		}

		return result;
	}

	// 정액제 상품가입 체크
	private boolean checkPrdtSubscrpt(String prdtCode)
	{

		boolean result = false;

		CommonPrdtScrptSpiClientStub client = createCommonPrdtScrptSpiClient();
		LrppSession session = lrppLogin(client.getSession());
		if (session == null)
			return result;

		String scid = getSmartCardId();
		String txSvcCode = "spi.common.prdtscrpt";

		// FIXMe: ItvUser를 PrdtSubscrpt 용으로 교체해야함!!!
		// 쓰는곳은 없지만 가이드에 하라고 되어있다;; 뭘까;; 안해도 될듯??
		ItvUser user = null;
		try
		{
			Log.trace(this, "~~~readUser~~~");
			user = client.readUser(scid);
		}
		catch (CommonException e1)
		{
			printCommonException(e1);
			lrppLogout(session);
			return result;
		}
		catch (LrppException e1)
		{
			printLrppException(e1);
			lrppLogout(session);
			return result;
		}
		Log.trace(this, "~~~user.getCode()    : " + user.getCode());
		Log.trace(this, "~~~user.getName()    : " + user.getName());
		Log.trace(this, "~~~user.getCustomerCode()    : " + user.getCustomerCode());
		Log.trace(this, "~~~user.getRegNum()    : " + user.getRegNum());

		try
		{
			result = client.checkProductSubscription(scid, prdtCode);
		}
		catch (LrppException e)
		{
			StateValue.ERR_AT_CHECK_SUBSCRPT = true;
			printLrppException(e);
			result = false;
		}
		catch (Exception e)
		{
			StateValue.ERR_AT_CHECK_SUBSCRPT = true;
			Log.error(this, e);
			result = false;
		}
		finally
		{
			lrppLogout(session);
			Log.trace(this, "CommonPrdtScrptSpiClientStub.checkProductSubscription() --> " + result);
			Log.trace(this, "ProductCode    : " + prdtCode);
			Log.trace(this, "SmartCardId    : " + scid);
			Log.trace(this, "TxServiceCode  : " + txSvcCode);
		}

		return result;

	}

	////////////////////////////////////
	//
	// Lrpp, Spi 관련 private 함수들..
	//
	////////////////////////////////////

	private String getServiceId()
	{
		return StateValue.SERVICE_ID;
	}

	private String getProductDayId()
	{
		return StateValue.PRODUCT_DAY_ID;
	}

	private String getProductMonthId()
	{
		return StateValue.PRODUCT_MONTH_ID;
	}

	private LrppSession lrppLogin(LrppSession session)
	{

		String scid = getSmartCardId();
		try
		{
			if (!session.terminalLogin(0, scid))
			{
				Log.errmsg(this, "terminalLogin failed: " + scid);
				session = null;
			}
			else
			{
				Log.trace(this, "LrppSession.terminalLogin(0, " + scid + ") SUCCESS");
			}
		}
		catch (Exception e)
		{
			// LoginException
			// LrppException
			Log.error(this, "LrppSession.terminalLogin() FAILED!! scid: " + scid, e);
			session = null;
		}
		return session;
	}

	private void lrppLogout(LrppSession session)
	{
		try
		{
			session.logout();
			Log.trace(this, "LrppSession.logout() SUCCESS");
		}
		catch (LrppException e)
		{
			printLrppException(e);
		}
		catch (Exception e)
		{
			Log.error(this, "LrppSession.logout() FAILED!!", e);
		}
	}

	private String getSpiSvrUrl()
	{
		if (this.spiSvrUrl == null)
		{
			this.spiSvrUrl = ("http://" + ((isLive()) ? StateValue.RP_SERVER_LIVE : StateValue.RP_SERVER_TEST) + "/LrppDispatcher/dispatcher");
			Log.trace(this, "getSpiServerUrl() --> " + this.spiSvrUrl);
		}
		return this.spiSvrUrl;
	}

	// 기본정보 조회용
	private CommonSpiClientStub createCommonSpiClient()
	{
		LrppSession session = new LrppSession(getSpiSvrUrl());
		CommonSpiClientStub c = new CommonSpiClientStub(getSpiSvrUrl(), "spi.common");
		c.setSession(session);
		return c;
	}

	// 종량제용 (사용할때마다 입력한금액을 과금시키는 방식)
	private CommonUsageSpiClientStub createCommonUsageSpiClient()
	{
		LrppSession session = new LrppSession(getSpiSvrUrl());
		CommonUsageSpiClientStub c = new CommonUsageSpiClientStub(getSpiSvrUrl(), "spi.common.usage");
		c.setSession(session);
		return c;
	}

	// 정액제용..
	private CommonPrdtScrptSpiClientStub createCommonPrdtScrptSpiClient()
	{
		LrppSession session = new LrppSession(getSpiSvrUrl());
		CommonPrdtScrptSpiClientStub c = new CommonPrdtScrptSpiClientStub(getSpiSvrUrl(), "spi.common.prdtscrpt");
		c.setSession(session);
		return c;
	}

	////////////////////////////////////
	//
	// Epg 관련 private 함수들..
	//
	////////////////////////////////////

	private EPGXlet getEpgXlet()
	{
		if (this.epgXlet == null)
		{
			String name = "/10000000/3000/NDSEPGXLET";
			Remote rmt = null;
			try
			{
				rmt = IxcRegistry.lookup(this.xletCtx, name);
			}
			catch (RemoteException e)
			{
				Log.error(this, "name: " + name, e);
			}
			catch (NotBoundException e)
			{
				Log.error(this, "name: " + name, e);
			}
			if (rmt != null)
			{
				try
				{
					this.epgXlet = ((EPGXlet) rmt);
				}
				catch (ClassCastException e)
				{
					Log.error(this, "classname: " + rmt.getClass().getName(), e);
				}
			}
			else
			{
				Log.errmsg(this, "EPG is NULL!!! (name: " + name);
			}
		}
		return this.epgXlet;
	}

	////////////////////////////////////
	//
	// 나가기 함수
	//
	////////////////////////////////////

	public boolean changeService(XletContext xletContext, int src_id)
	{
		return false;
	};

	public void goToExit()
	{
		Log.trace("Exit start~~");
		ApplicationCoordinator applicationCoordinator = null;
		if (applicationCoordinator == null)
		{
			try
			{
				applicationCoordinator = ((ApplicationCoordinator) IxcRegistry.lookup(this.xletCtx, "/10000000/3fff/ApplicationCoordinator"));
			}
			catch (NotBoundException nbe)
			{
				Log.error(this, nbe);
			}
			catch (RemoteException ex)
			{
				Log.error(this, ex);
			}
			try
			{
				applicationCoordinator.requestTransition(65538, -1);
			}
			catch (RemoteException ex)
			{
				Log.error(this, ex);
			}
		}

		// 종종 destroyXlet 보다 늦게 수행되는 경우가 있다! nullpointer남.. 막자!
		//Log.trace(this, "Exit end~~!");
	}

	////////////////////////////////////
	//
	// 특수 예외 출력용 함수들..
	//
	////////////////////////////////////

	private void printLrppException(LrppException e)
	{
		int err_code = e.getCode();
		StateValue.PAY_ERR_CD = err_code;

		switch (err_code)
		{
			case 0:
				Log.error("lrpp error 0", e);
				break;
			case 1:
				Log.error("lrpp error 1", e);
				break;
			case 11:
				Log.error("lrpp error 11", e);
				break;
			case 12:
				Log.error("lrpp error 12", e);
				break;
			case 13:
				Log.error("lrpp error 13", e);
				break;
			case 14:
				Log.error("lrpp error 14", e);
				break;
			case 15:
				Log.error("lrpp error 15", e);
				break;
			case 21:
				Log.error("lrpp error 21", e);
				break;
			default:
				Log.error("??? unknown error", e);
		}
	}

	private void printCommonException(CommonException e)
	{
		int err_code = e.getCode();
		StateValue.PAY_ERR_CD = err_code;

		switch (err_code)
		{
			case -99:
				Log.error("undefined error", e);
				break;
			case -1:
				Log.error("duplicated data error", e);
				break;
			case -2:
				Log.error("data isn't exist", e);
				break;
			case -3:
				Log.error("input parameter error", e);
				break;
			case -5:
				// can not find the valid STB by customer Code and smart card ID.(-5)
				Log.error("superior data isn't exist", e);
				break;
			case -10:
				Log.error("terminal(user) info is disabled", e);
				break;
			case -11:
				Log.error("have no permission", e);
				break;
			case -12:
				Log.error("product is not available", e);
				break;
			case -13:
				Log.error("escaped the available limits", e);
				break;
			case -14:
				Log.error("expiry date has passed", e);
				break;
			case -15:
				Log.error("not subscribed", e);
				break;
			case -98:
				Log.error("unsupported function", e);
				break;
			case -97:
				Log.error("remote call error", e);
				break;
			case 201:
				Log.error("wrong URI", e);
				break;
			case 202:
				Log.error("wrong host", e);
				break;
			case 203:
				Log.error("connection error", e);
				break;
			case 204:
				Log.error("DB error", e);
				break;
			case 205:
				Log.error("unsupported encoding", e);
				break;
			case 300:
				Log.error("error in SPI supporting Component", e);
				break;
			default: // -96, 301
				Log.error("??? unknown error", e);
		}
	}

}
