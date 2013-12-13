package pororo.com.framework;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import pororo.com.log.Log;

public class PropertyWork {
	
	private Properties pros = null;
	
	public PropertyWork( URL url ) {  
		this.init( url );  
	}
	
	public PropertyWork( String path ) {
		this.init(path);
	}
	
	public void init( String path ) {
		InputStream in = PropertyWork.class.getClassLoader().getResourceAsStream(path);
		pros = new Properties();
		try {
			if (in == null) {
				in = new FileInputStream(path);
			}
			pros.load(in);   
			
			in.close();
			path = null;
			in = null;
		} catch (Exception e) {
			Log.error(this, e);
		}
	}
	
	public void init( URL url ) {  
		try {
			InputStream in = url.openStream();
			pros = new Properties();  
			pros.load(new BufferedInputStream(in));
			
			in.close();
			url = null;
			in = null;
		} catch (IOException e) { 
			e.printStackTrace();  
		}
	}
	
	public String getProperty(String key) throws IOException {  
		return new String(pros.getProperty(key).trim().getBytes("ISO-8859-1"), "euc-kr");
	}  
	
	public void destroy() {
		pros.clear();
		pros = null;
	}
	
}