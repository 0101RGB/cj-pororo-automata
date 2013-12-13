package pororo.com.framework;


import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pororo.com.StateValue;
import pororo.com.log.Log;


public final class ImagePool {

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private MediaTracker tracker;
	private Map imageMap;
	
	private int imgCnt;
	
	//public boolean img_check= false;
	
	public ImagePool(Component component) {
		tracker = new MediaTracker(component);
		imageMap = new HashMap();
	}
	
	/**
	 * 등록한 모든 이미지들을 flush 시킨다.
	 * 
	 */
	
	//public void img
	public void flushAll() {
		Iterator iter = imageMap.values().iterator();
		Image image;
		while (iter.hasNext()) {
			image = ((Image)iter.next());
			image.flush();
		}
		imageMap.clear();
	}
	
	/**
	 * 파일명에 해당하는 이미지를 가져온다.
	 * 기존에 사용한 이미지가 존재할 경우 재사용한다.
	 * 파일이 존재하지 않거나, 이미지를 가져오는 중 문제가 발생하면 <code>null</code>를 반환한다.
	 * 
	 * @param filename 파일명
	 * @return 이미지
	 */
	public Image getImage(String filename) {
		if (filename == null) {
			return null;
		}
		Image image = (Image)imageMap.get(filename);
		if (image == null) {
			image = toolkit.createImage( filename);
			tracker.addImage(image, 0);
			try {
				tracker.waitForAll();
			} catch (InterruptedException e) {
				tracker.removeImage(image);
				image = null;
			} finally {
				if (image != null) {
					tracker.removeImage(image);
				}
				if (tracker.isErrorAny()) {
					tracker.removeImage(image);
					image = null;
				}
			}
			if (image != null) {
				imageMap.put(filename, image);
			}
		}
		return image;
	}
	
//	/**
//	 * URL에 해당하는 이미지를 가져온다.
//	 * 기존에 사용한 이미지가 존재할 경우 재사용한다.
//	 * URL이 존재하지 않거나, 이미지를 가져오는 중 문제가 발생하면 <code>null</code>를 반환한다.
//	 * 
//	 * @param url 이미지 주소
//	 * @return 이미지
//	 */
//	public Image getImage(URL url) {
//		// !!!!!!!!!!!! 이 함수는 사용하지않음 !!!!!!!!!!!!!!
//		// 소켓접속중 가끔씩 1분30초간 Blocking 되는 현상때문에.. 
//		// 대신 fromHTTPImageRequester 을 사용하도록!!!
//		if (url == null) {
//			return null;
//		}
//		String name = url.toString();
//		Image image = (Image)imageMap.get(name);
//		boolean isError = true;
//		if (image == null) {
//			
//			try {
//				image = toolkit.createImage(url);
//				tracker.addImage(image, 0);
//				tracker.waitForAll();
//				isError = tracker.isErrorAny();
//			} catch (InterruptedException e) {
//				//tracker.removeImage(image);
//				//image = null;
//			} finally {
//				if (image != null) {
//					tracker.removeImage(image);
//				}
//				if(isError){
//					System.out.println("###### error is occured when getting image from MediaTracker!!!");
//					 tracker.removeImage(image);
//		             image = null;
//		             image = fromHTTPImageRequester(url);
//		             //image = getImage_retry(url);
//				}
//			}
//			if (image != null) {
//				imageMap.put(name, image);
//			}
//		}
//		return image;
//	}

	public Image fromHTTPImageRequester(URL url){
		if (null == url) {
			return null;
		}
		String name = url.toString();
		/* 정상코드 */
		Image image = (Image) imageMap.get(name);
		if (null != image) {
			return image;
		}
		/* 테스트코드... */
//		Image image = null;
		
		HTTPImageRequester requester = new HTTPImageRequester(3000);  // 타임아웃3초 주었음!
		try {
			image = requester.getImage(url, toolkit, tracker);
		} catch (IOException e) {
			System.out.println("##### error in fromHTTPImageRequester - requester.getImage()");
			System.out.println("##### image: " + image);  // 이 시점에 image 가 null 이어야 함!
			e.printStackTrace();
		}
		if (null != image) {
			imageMap.put(name, image);
		}
		return image;
	}
	
	

	// http://pororogames.com/test/tb720/img/notice.png
	public Image fromSocketImageRequester(URL url){
		if (null == url) {
			return null;
		}
		String name = url.toString();
		/* 정상코드 */
		Image image = (Image) imageMap.get(name);
		if (null != image) {
			return image;
		}
		SocketImageRequester requester = new SocketImageRequester(50, 1000, StateValue.gravity_server, StateValue.gravity_server);
//		SocketImageRequester requester = new SocketImageRequester(10, 1000, "pororogames.com", "112.175.131.115");
		
		/* 테스트코드... */
//		Image image = null;
//		String ip = SocketImageRequester.getIPFromDomain("soip.ollehmarket.com");
//		SocketImageRequester requester = new SocketImageRequester("soip.ollehmarket.com", ip);
		
		image = requester.getImage(name, toolkit, tracker);
		if (null != image) {
			imageMap.put(name, image);
		}		
		return image;
	}

	

/*
	public Image getImage_retry(URL url){
		if (url == null) {
			return null;
		}
		//System.out.println("getImage_retry");
		String name = url.toString();
		Image image = null;
		boolean isError = true;
		byte buffer[] = null;
		 int len=0;
		HttpURLConnection httpConn;
		BufferedInputStream bis;
	    ByteArrayOutputStream baos;
	    httpConn = null;
	    bis =null;
	   
	    //baos = null;
	    baos= new ByteArrayOutputStream(2048);
        buffer = new byte[2048];
		try{
			httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("GET");
	        httpConn.connect();
	        
	        bis = new BufferedInputStream(httpConn.getInputStream(), 2048);
	 
	        while((len = bis.read(buffer, 0, 2048)) > 0) 
	        	baos.write(buffer, 0, len);
            
            image = toolkit.createImage(baos.toByteArray());
      
            tracker.addImage(image, 0);
            tracker.waitForID(0);
            isError = tracker.isErrorAny();
		}
		catch(Exception e)  // IOException
        {
			Log.error(this, e);
			//e.printStackTrace();
			try
	        {
				if(baos != null){
					baos.close();
					baos =null;
	        	}
				if(bis != null){
		          bis.close();
		          bis =null;
				}
				if(httpConn != null){
		          httpConn.disconnect();
		          httpConn = null;
				}
				 buffer = null;
				 
	        }
			catch(Exception exception2) { }
        }
		 finally {
			 try
		     {
				 if(baos != null){
					baos.close();
					baos =null;
		        }
				if(bis != null){
			         bis.close();
			         bis =null;
				}
				if(httpConn != null){
			         httpConn.disconnect();
			         httpConn = null;
				}
				 buffer = null;
		    }
			catch(Exception exception3) { }
			
			if (image != null) {
				tracker.removeImage(image);
			}
			if(isError){
				 tracker.removeImage(image);
		         image = null;
			}
			
		}
		 if (image != null) {
			imageMap.put(name, image);
		}
		
		return image;
	}
*/
	
	public void removeImage(Image image) {
		Iterator iter = imageMap.keySet().iterator();
		String name = null;
		while (iter.hasNext()) {
			name = (String)iter.next();
			if (image == imageMap.get(name)) {
				imageMap.remove(name);
				image.flush();
				break;
			}
		}
	}
	
	/**
	 * 파일명에 해당하는 이미지를 삭제한다.
	 * 
	 * @param filename
	 */
	public void removeImage(String filename) {
		Image image = (Image)imageMap.get(filename);
		if (image != null) {
			image.flush();
			imageMap.remove(filename);
		}
	}
	
	/**
	 * URL에 해당하는 이미지를 삭제한다.
	 * 
	 * @param url 이미지 주소
	 */
	public void removeImage(URL url) {
		String name = url.toString();
		Image image = (Image)imageMap.get(name);
		if (image != null) {
			image.flush();
			imageMap.remove(name);
		}
	}
	
	
}
