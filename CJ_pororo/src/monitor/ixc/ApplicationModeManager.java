
package monitor.ixc;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <code>ApplicationModeManager</code> provides APIs to avoid the conflict
 * bewteen enhanced bound application and system application. It gives
 * central and general way to solve the conflict between them instead of 
 * calling APIs between themselves one-to-one.
 * <ul>
 * <li>All enhanced bound application which needs full mode, should register
 * {@link ApplicationModeRequestHandler} and respond againt 
 * the request of mode change.</li>
 * <li>All enhanced bound application should call {@link #changeMode(int)}
 * to ask if system application allows, before it really changes</li>
 * <li>All system application which needs negotiation with enhanced bound
 * application, should register {@link ApplicationModeListener}
 * and respond on mode changes.</li>
 * <li>All system application should call {@link #requestModeChange(int)}
 * to ask if enhanced bound application allows, before it really changes</li>
 * </ul>
 * 
 * @author $Author: ckw0507 $
 * @version $Revision: 1.1 $
 * @since Charles CW Kwak, 2007. 2. 9
 */
public interface ApplicationModeManager extends Remote {
    /**
     * IXC stub object name
     */
    public static final String IXC_OBJECT_NAME = "ApplicationModeManager";

    /**
     * Application mode constant indicating enhanced bound application shows
     * nothing.
     */
    public static final int APP_MODE_NONE = 0;
    /**
     * Application mode constant indicating enhanced bound application just
     * shows small-sized UI not interfering UI of system applications.
     */
    public static final int APP_MODE_ICON = 1;
    /**
     * Application mode constant indicating enhanced bound application ocuppies
     * resources such as Graphics, Video or Background plane interfering
     * system application UI in some degree or all.
     */
    public static final int APP_MODE_FULL = 2;

    /**
     * Adds {@link ApplicationModeListener}.<br>
     * 
     * <b>API for system application.</b>
     * 
     * @param l Implementation object to start listening application mode changes
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     */
    public void addListener(ApplicationModeListener l) throws RemoteException;

    /**
     * Removes {@link ApplicationModeListener}.<br>
     * 
     * <b>API for system application</b>
     * 
     * @param l Implementation object to stop listening application mode changes
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     */
    public void removeListener(ApplicationModeListener l)
        throws RemoteException;

    /**
     * Requests application mode to the given mode for requesting application
     * to avoid resource conflict such as Graphics, Video, or Background plane.
     * <br>
     * 
     * <b>API for system application</b>
     * 
     * @param newMode new application mode which enhanced bound application
     *      is requested to be
     * @return <code>true</code> means enhanced bound application changes or has
     *      already changed to the request mode. <code>false</code> means
     *      enhanced bound application refuses.
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     */
    public boolean requestModeChange(int newMode) throws RemoteException;

    /**
     * Registers {@link ApplicationModeRequestHandler}.<br>
     * 
     * <b>API for enhanced bound application</b>
     * 
     * @param handler Implementation object to respond to mode change request.
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     * @deprecated use #addRequestHandler(ApplicationModeRequestHandler) instead
     */
    public void registerRequestHandler(ApplicationModeRequestHandler handler)
        throws RemoteException;

    /**
     * Adds {@link ApplicationModeRequestHandler}.<br>
     * 
     * <b>API for enhanced bound application and SMS</b>
     * <br>
     * <b>REMARK: </b>{@link ApplicationModeRequestHandler} must be removed.
     * 
     * @param handler Implementation object to respond to mode change request.
     * @throws RemoteException when any exception occurs during processing
     *      this function
     */
    public void addRequestHandler(ApplicationModeRequestHandler handler)
        throws RemoteException;

    /**
     * Removes {@link ApplicationModeRequestHandler}.<br>
     * 
     * <b>API for enhanced bound application and SMS</b>
     * 
     * @param handler Implementation object to respond to mode change request.
     * @throws RemoteException when any exception occurs during processing
     *      this function
     */
    public void removeRequestHandler(ApplicationModeRequestHandler handler)
        throws RemoteException;

    /**
     * Tries to change application mode to the given mode.<br>
     * 
     * <b>API for enhanced bound application</b>
     * 
     * @param newMode new application mode which enhanced bound application 
     *      wants to be changed to
     * @return <code>true</code> means no system application refuses. 
     *      <code>false</code> means more than one system applications refuse.
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     */
    public boolean changeMode(int newMode) throws RemoteException;

    /**
     * Returns current application mode.
     * 
     * @return Application mode constant.
     * @throws RemoteException when any exception occurs during processing 
     *      this function
     */
    public int getMode() throws RemoteException;
}

/*
 * $Log: ApplicationModeManager.java,v $
 * Revision 1.1  2009/03/03 04:59:11  ckw0507
 * 3�� ������ ���� ������Ʈ ��.
 *
 * ��������
 * 	1. �ֹ���ҽ� �?��ȣ�� �׽�Ʈ id �� �����Ǿ� ����
 * 	2. connect �� red key ���ķ� ����
 * 	3. connect �� ������(���� �ð������� ���w��. 'biddle ����') ��õ��� �������� 		���� ��û. (���� ȣ���� �߾����� ������ �ȵǳ� ���� ȣ���� ���� �ʰ� 		connect �� �ѻ��¿��� �ٽ� ����ٸ� ������ �߻��ߴ�.)
 * 	4. ���� ��ǰ ���Ž� �ߺ� ���� ��û block
 *
 * Revision 1.12  2008/11/17 08:28:45  20min
 * UTF-8 change....
 *
 * Revision 1.1  2008/11/05 02:22:11  minjung821
 * Application Mode 처리 관련 interface 파일 추가
 *
 * Revision 1.3  2007/03/28 14:10:33  cwkwak
 * [monitor_cj2] javadoc 수정
 *
 * Revision 1.2  2007/03/20 14:44:06  cwkwak
 * [monitor_cj2] 1차적으로 완성
 *
 * Revision 1.1  2007/03/01 14:34:07  cwkwak
 * [monitor_cj2] initial version
 *
 */