package pororo.com.framework;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class SocketImageRequester {
	private String HOST_IP;
	private String HOST_NAME;
	
	public static final char[] HTTP_HEADER = "HTTP/1.1 ".toCharArray();
    public static final char[] CONTENT_LENGTH = "Content-Length: ".toCharArray();
    public static final char[] NEW_LINE = "\r\n".toCharArray();
    public static final char[] NEW_LINE_TWICE = "\r\n\r\n".toCharArray();
	
    private int connectionTimeout;
    private int readTimeout;
    
	public SocketImageRequester(int connectionTimeout, int readTimeout, String hostName, String hostIP) {
		this.connectionTimeout = connectionTimeout;
		this.readTimeout = readTimeout;
		HOST_NAME = hostName;
		HOST_IP = hostIP;
	}

	public Image getImage(String name, Toolkit toolkit,	MediaTracker tracker) {
		int len=0;
		boolean isError = true;
		byte buffer[] = null;
		Image image = null;
		
		BufferedInputStream bis = null;
	    ByteArrayOutputStream baos;
	   
	    Socket socket = null;	    
	    //InetSocketAddress socketAddr = new InetSocketAddress(HOST_IP, 80); 
	    OutputStream os = null;

		try {
			System.out.println("' =============> 1");
			socket = new Socket(HOST_IP, 80);
			socket.setSoTimeout(readTimeout);   // SoTimeout 은 소켓이 연결된 상태로 일정시간동안 아무일도 하지 않을때 소켓을 끊기 위한 값이다. readTimeout 이라고 보긴 힘들듯..
			//socket.connect(socketAddr, connectionTimeout);  // connection 타임아웃
			System.out.println("' connected~");
		} catch (Exception e) {
			// SocketException
			// IOException
			System.out.println("Failed to open connection.");
			e.printStackTrace();
			//SceneManager.getInstance().sever_check=100;
			//SceneManager.getInstance().sever_msg =1;
			try {
				if (socket != null) {
					socket.close();
					socket = null;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	    
		String getFile = null;
		
		try {
			os = socket.getOutputStream();
			getFile = name.substring(name.indexOf("/", name.indexOf(HOST_NAME)));
			System.out.println("' name: " + name);
			System.out.println("' getFile: " + getFile);
			/*
            os.write(("GET " + getFile).getBytes());
            //os.write(endPoint.getBytes());
            os.write(" HTTP/1.1\r\n".getBytes());
			
            os.write(("Host: " + HOST_IP + "\r\n").getBytes());
            //os.write(("Last-Access-Time: " + getLastAccessTime() + "\r\n").getBytes());
            
            // Close 대신 Keep-Alive가능!
            //os.write("Connection: Keep-Alive\r\n\r\n".getBytes());
            os.write("Connection: Close\r\n\r\n".getBytes());
            */
            os.write(
            		("GET " + getFile + 
            		" HTTP/1.1\r\nHost: " + HOST_IP +  
            		"\r\nConnection: Close\r\n\r\n").getBytes()
            );
            os.flush();
		} catch (Exception e) {
			// IOException
			System.out.println("Failed to open connection2.");
			e.printStackTrace();
			try {
				if (os != null) {
					os.close();
					os = null;
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			try {
				if (socket != null) {
					socket.close();
					socket = null;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//SceneManager.getInstance().sever_check=100;
			//SceneManager.getInstance().sever_msg =1;
			return null;
		}

	    baos = new ByteArrayOutputStream(2048);
        buffer = new byte[2048];
        StringBuffer strBuf = new StringBuffer();
        char ch;
        int r;
        int step = 0;
		try{
	        bis = new BufferedInputStream(socket.getInputStream(), 2048);
	        while (step < NEW_LINE.length && (r = bis.read()) != -1) {
	            ch = (char) r;
	            if (ch == NEW_LINE[step]) {
	                step++;
	            } else {
	                step = 0;
	                strBuf.append(ch);
	            }
	        }
	        
	        /*
	         * 디버깅용..
	        while (step < 20 && (r = bis.read()) != -1) {
	        	ch = (char) r;
	        	step++;
	        	strBuf.append(ch);
	        }
	        */
	        
	        String bufToString = strBuf.toString();  // HTTP/1.1 200 OK
	        System.out.println("' YJY 3 bufToString: " + bufToString);
	        strBuf = null;

	        if (bufToString.indexOf("200 OK") == -1) {
	        	throw new FileNotFoundException("NOT 200!!!!!!");
	        }

	        step = 0;
	        while (step < CONTENT_LENGTH.length && (r = bis.read()) != -1) {
	            ch = (char) r;
	            step = (Character.toLowerCase(ch) == Character.toLowerCase(CONTENT_LENGTH[step])) ? step + 1 : 0;
	        }

	        step = 0;
	        while (step < NEW_LINE.length && (r = bis.read()) != -1) {
	            ch = (char) r;
	            if (ch == NEW_LINE[step]) {
	                step++;
	            } else {
	                step = 0;
	                len = len * 10 + (ch - '0');
	            }
	        }

	        step = 0;
	        while (step < NEW_LINE_TWICE.length && (r = bis.read()) != -1) {
	            ch = (char) r;
	            step = (ch == NEW_LINE_TWICE[step]) ? step + 1 : 0;
	        }
	     
	        
	        while ((len = bis.read(buffer, 0, 2048)) > 0) {
	        	baos.write(buffer, 0, len);
	        }
	        
	        byte[] bbbb = baos.toByteArray();
	        
//	        byte[] bnew = new byte[1000];
//	        for (int i=0; i<1000; i++)
//	        	bnew[i] = bbbb[i];
//	        System.out.println("' bbbb.len: " + bbbb.length);
//	        System.out.println("' bnew: " + new String(bnew));
	        
            image = toolkit.createImage(bbbb);
      
            tracker.addImage(image, 0);
            tracker.waitForID(0);
            isError = tracker.isErrorAny();
            if (isError)
            	return null;
            return image;
		}
		catch(Exception e) {  // IOException
			e.printStackTrace();
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
				if (os != null) {
					os.close();
					os = null;
				}
				if (socket != null) {
					socket.close();
					socket = null;
				}
				 buffer = null;
		    }
			catch(Exception exception3) { }
			
			if (image != null) {
				tracker.removeImage(image);
			}			
		}
		 return null;
	}
	
	public static String getIPFromDomain(String hostName) {
	    try {
			InetAddress inetAddr = InetAddress.getByName(hostName);
			String hostIP = inetAddr.getHostAddress();
			System.out.println("### hostIP: " + hostIP);
			return hostIP;
		} catch (UnknownHostException e3) {
			e3.printStackTrace();
			return null;
		}
	}
}
