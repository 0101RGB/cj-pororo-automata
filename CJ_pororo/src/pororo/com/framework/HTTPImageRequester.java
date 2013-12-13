package pororo.com.framework;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HTTPImageRequester implements Runnable {

	private HttpURLConnection httpConn;
	private BufferedInputStream bis;
	private URL imageUrl;

	private Object sync = new Object();
	private boolean isTimeOut = false;
	private int connectionTimeout;
	
	public HTTPImageRequester(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Image getImage(URL url, Toolkit toolkit,
			MediaTracker tracker) throws IOException {
		imageUrl = url;
		
		int len = 0;
		boolean isError = true;
		byte[] buffer = null;
		ByteArrayOutputStream baos = null;
		Image image = null;
		
		try {
			isTimeOut = false;
			Thread t = new Thread(this);
			t.setDaemon(true);
			t.start();
			//Timeout 까지 기다림 끝나지 않으면 중단.
			//Thread t 가 끝날때까지 기다림(Timeout 시간동안)
			//즉 run 메소드가 수행된 후 기다리는데 정상적인경우 주어진 3초안에
			//소켓접속이 이루어지지만, 실패하는 경우엔 socket 을 null 시킴!
			try {
				// 일단 join 요청해두고 기다리다가, connectionTimeout 이 지나면 실행!
				t.join(connectionTimeout);  // 혹시라도 스레드안에서 소켓생성하기도 전에 치고들어가지는 않는지...?
			}
			catch (InterruptedException ie) {
				t.interrupt();
			}

			synchronized (sync) {
				//TimeOut 기간동안 Socket 접속을 못한 경우
				if (null == bis) {
					isTimeOut = true;
					throw new IOException("YJY socket time out!!");
				}
			}

			buffer = new byte[2048];
			baos = new ByteArrayOutputStream(2048);
			while ((len = bis.read(buffer, 0, 2048)) > 0) {
				baos.write(buffer, 0, len);
			}

			image = toolkit.createImage(baos.toByteArray());
			tracker.addImage(image, 0);
			tracker.waitForID(0);
			isError = tracker.isErrorAny();
            if (isError)
            	return null;
			return image;
		} 
		catch (Exception e) {
			// InterruptedException
			e.printStackTrace();
		}
		finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (httpConn != null) {
					httpConn.disconnect();
					httpConn = null;
				}
				buffer = null;
			} 
			catch (Exception e3) {
			}

			if (image != null) {
				tracker.removeImage(image);
			}
		}
		return null;
	}

	public void run() {
		try {
			httpConn = (HttpURLConnection) imageUrl.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			InputStream inputStream = httpConn.getInputStream();
			bis = new BufferedInputStream(inputStream, 2048);
			//System.out.println("' YJY 1 got bis.");
		} catch (Exception e) {
			// FileNotFoundException
			// ProtocolException
			// IOException
			e.printStackTrace();
		}
		
		synchronized (sync) {
			if (isTimeOut) {
				System.out.println("' already timeout exception occured!");
				dispose_resources();
			}
		}
	}

	// 어차피 getImage 수행시 finally 블럭에서 다 해제한다.
//	public void dispose() {
//		synchronized (sync) {
//			dispose_resources();
//		}		
//	}
	
	private void dispose_resources() {
		if (null != httpConn) {
			httpConn.disconnect();
		}
		if (null != bis) {
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		httpConn = null;
		bis = null;		
	}
}
