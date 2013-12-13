package pororo.com.framework;

//import java.io.*;
import java.io.IOException;
//import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pororo.com.Lib;
import pororo.com.SceneManager;
import pororo.com.log.Log;



public final class Httpconnection
{
	public Httpconnection()
	{
	}
	
	public static String ConsultRequest(String urlStr, boolean isUTF)
	{
		//System.out.println("sever  urlStr~~~~="+ urlStr);
		boolean isError = false;
		HttpURLConnection con = null;
//		BufferedReader br = null;
		String data = null;
		//if(!SceneManager.getInstance().emul)
		//{
			con = connect(urlStr);
			Log.trace("## connect ???");
			if(con == null) {
				Log.errmsg("Httpconnection", "openConnection() Failed!!!");
//				System.out.println("Failed to open connection.");
				SceneManager.getInstance().sever_check=100;
				SceneManager.getInstance().sever_msg =1;
				isError = true;
				return null;
			}
			
			try {
				Log.trace("Httpconnection", "try connect..");
				SceneManager.getInstance().isErrImg = false;
				con.connect();
				Log.trace("Httpconnection", "connected!!");
			} catch (Exception e1) {
				// IOException (SocketException)
				Log.error("Httpconnection", e1);
				SceneManager.getInstance().sever_check=100;
				SceneManager.getInstance().sever_msg =1;
				isError = true;
				SceneManager.getInstance().isErrImg = true;
				return null;
			}
			
			// ?????????? page data?? ??��?.
//			System.out.println("===================Get data from server.============================");
			//if(SceneManager.getInstance().sever_msg == 0)
				data = getData(con, isUTF);
				Log.trace("## data: " + data);
			//else data = null;
//			System.out.println("===================data============================"+data);
			if (Lib.isEmpty(data)) {
				Log.trace("## isEmpty!!!!");
				SceneManager.getInstance().sever_check=100;
				SceneManager.getInstance().sever_msg =1;
				SceneManager.getInstance().isErrImg = true;
				isError = true;
				//System.out.println("===================Failed to read data from server.========================");
			}
			
			// ???????? ?????? ???��?.
			//System.out.println("=====================Close connection=======================");
			con.disconnect();
	//	}
		//else {
			//data="retVal=1&GameID=뽀로로 테스트&CType=pororo&CLevel=1&CPoint=0&IDType=1&CExp=0&EExp=300";
		//}
		// 2012.02.28 jy.yu 서버로부터 값을 받는데 특별히 이상이 없었으면 sever_msg 가 OK 가 되도록 개선함.
		if ( !isError) {
			SceneManager.getInstance().sever_msg = 0;
		} else {
			SceneManager.getInstance().isErrImg = true;
		}
		return data ;
	}
	
	private static HttpURLConnection connect(String url) {
		try {
			SceneManager.getInstance().isErrImg = false;
			HttpURLConnection con = (HttpURLConnection)(new URL(url).openConnection());
			
			return con;

		} catch (Exception e) {
			// MalformedURLException
			// IOException
			SceneManager.getInstance().sever_check=100;
			SceneManager.getInstance().sever_msg =1;
			//System.out.println("==========HttpURLConnection==========IIOException, "+e.getMessage());
			Log.error("Httpconnection", e);
			SceneManager.getInstance().isErrImg = true;
		}
		
		return null;
	}
	

	
	private static String getData(HttpURLConnection c, boolean isUTF) {
		//System.out.println("=======getData start==============");
		SceneManager.getInstance().isErrImg = false;
		BufferedReader br = null;
		String readStr = null;
		try {
			InputStreamReader isr = null;
			if (isUTF)
				isr = new InputStreamReader(c.getInputStream(),"UTF-8");
			else
				isr = new InputStreamReader(c.getInputStream(),"ks_c_5601-1987");
            //System.out.println("=======getData start==222===");
            br = new BufferedReader(isr);
            //System.out.println("=======getData start==333===");
            readStr = br.readLine();
//            System.out.println("=====readStr1="+readStr );
            
		} catch (Exception e) {  // IOException
			//System.out.println("=============getData====IIOException, "+e.getMessage());
			SceneManager.getInstance().sever_check=100;
			SceneManager.getInstance().sever_msg =1;
			SceneManager.getInstance().isErrImg = true;
			readStr = null;
			e.printStackTrace();
		}	
		finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		   
		}
		
		return readStr;
	}
}