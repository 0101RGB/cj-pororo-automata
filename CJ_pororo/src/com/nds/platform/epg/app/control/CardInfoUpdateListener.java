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
import java.util.EventListener;

/**
 * Smart Card 정보의 변경 여부를 전달 받기 위한 listener.<br>
 */
public interface CardInfoUpdateListener extends EventListener, Remote
{
	/**
	 * 변경된 Smart Card 정보의 mask 값들을 전달한다.<br>
	 * 단, 해당 어플이 등록한 mask 에 해당하는 값이 변경됐을때만 호출되며, 이때 전달되는 mask 에는 어플이 등록하지 않은 mask 는 포함되지 않는다.<br>
	 * 호출받은 어플은 mask 에 해당하는 Smart Card 정보를 다시 확인 하여야 한다.<br>
	 * 
	 * @param mask
	 *            {@link EPGXlet#addCardInfoUpdateListener(CardInfoUpdateListener, int)} 에서 전달한 mask 중에, 변경된 값들의 mask
	 * @throws RemoteException
	 */
	public void notifyCardInfoUpdated(int mask) throws RemoteException;
}
