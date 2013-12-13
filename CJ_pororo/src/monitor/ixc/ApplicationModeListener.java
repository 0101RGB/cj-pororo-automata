
package monitor.ixc;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <code>ApplicationModeListener</code> concerns application mode changes of
 * enhanced bound application.
 * 
 * @author $Author: ckw0507 $
 * @version $Revision: 1.1 $
 * @since Charles CW Kwak, 2007. 2. 9
 */
public interface ApplicationModeListener extends Remote {
    /**
     * Notifies when enhanced bound application wants to change its mode to
     * new mode. <br>
     * 
     * Implementation object may refuse mode changes by returning false. 
     * When any implementation object refuses, the previous implementation 
     * object which already allows will be notified again with this function 
     * with current mode.
     * 
     * @param newMode new application mode which enhanced application wants to
     *      change to
     * @return <code>true</code> allows enhanced application changes to new
     *      application mode. <code>false</code> refuses changes and stops
     *      notification, and notifies this function again to previous
     *      notifiees with current application mode
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     */
    public boolean modeChanged(int newMode) throws RemoteException;
}

/*
 * $Log: ApplicationModeListener.java,v $
 * Revision 1.1  2009/03/03 04:59:11  ckw0507
 * 3�� ������ ���� ������Ʈ ��.
 *
 * ��������
 * 	1. �ֹ���ҽ� �?��ȣ�� �׽�Ʈ id �� �����Ǿ� ����
 * 	2. connect �� red key ���ķ� ����
 * 	3. connect �� ������(���� �ð������� ���w��. 'biddle ����') ��õ��� �������� 		���� ��û. (���� ȣ���� �߾����� ������ �ȵǳ� ���� ȣ���� ���� �ʰ� 		connect �� �ѻ��¿��� �ٽ� ����ٸ� ������ �߻��ߴ�.)
 * 	4. ���� ��ǰ ���Ž� �ߺ� ���� ��û block
 *
 * Revision 1.7  2008/11/17 08:28:45  20min
 * UTF-8 change....
 *
 * Revision 1.1  2008/11/05 02:22:11  minjung821
 * Application Mode 처리 관련 interface 파일 추가
 *
 * Revision 1.1  2007/03/01 14:34:07  cwkwak
 * [monitor_cj2] initial version
 *
 */