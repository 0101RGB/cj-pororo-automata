
package monitor.ixc;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <code>ApplicationModeRequestHandler</code> concerns application mode
 * change request from system applicaiton such as iEPG.
 * All enhanced bound application having full mode should implements this
 * interface.
 * 
 * @author $Author: ckw0507 $
 * @version $Revision: 1.1 $
 * @since Charles CW Kwak, 2007. 2. 9
 */
public interface ApplicationModeRequestHandler extends Remote {
    /**
     * Notifies when system application askes enhanced bound application
     * to be changed to new application mode. enhanced bound application may
     * refuse to change.
     * 
     * @param newMode new application mode which system application wants 
     *      enhanced bound application to change to
     * @param forced <code>true</code> means enhanced bound application should
     *      change to the requested application mode. <code>false</code>
     *      depends on enhanced bound application's decision.
     * @return <code>true</code> means bound application changes to 
     *      the requested application mode. <code>false</code> means enhanced
     *      bound application refuses.
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     */
    public boolean modeChangeRequested(int newMode, boolean forced)
        throws RemoteException;
}

/*
 * $Log: ApplicationModeRequestHandler.java,v $
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
 * Revision 1.3  2007/03/28 14:10:56  cwkwak
 * [monitor_cj2] mode를 int가 아닌 boolean으로 잘못 정의한 것 수정
 *
 * Revision 1.2  2007/03/20 14:44:06  cwkwak
 * [monitor_cj2] 1차적으로 완성
 *
 * Revision 1.1  2007/03/01 14:34:07  cwkwak
 * [monitor_cj2] initial version
 *
 */