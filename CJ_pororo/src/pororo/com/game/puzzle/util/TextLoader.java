package pororo.com.game.puzzle.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

// 게임 설정값들을 로딩
public class TextLoader {

	Vector textData = new Vector();;

	public void parserURL(URL url) {
		URLConnection con = null;
		try {
			con = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (con == null) {
			System.out.println("Text URLConnection null");
		}
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new InputStreamReader(con
					.getInputStream(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bufferReader == null) {
			System.out.println("Text bufferReader null");
		}

		try {
			String data = null;
			while ((data = bufferReader.readLine()) != null) {
				textData.addElement(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String search(String key) {
		String value = null;
		String data = null;
		if (textData != null) {
			for (int i = 0; i < textData.size(); i++) {
				data = ((String) textData.elementAt(i)).trim();
				if (data.length() >= key.length()) {
					if (data.substring(0, key.length()).equals(key)) {
						value = data.substring(key.length() + 1, data.length());
						break;
					}
				}
			}
		}
		return value;
	}

	public float getFloat(String key) {
		String data = search(key);
		Double returnValue = null;
		float value = -1F;
		if (data != null) {
			returnValue = new Double(data);
			value = returnValue.floatValue();
		}
		System.out.println(key + " : " + value);
		return value;
	}

	public double getDouble(String key) {
		String data = search(key);
		Double returnValue = null;
		double value = -1;
		if (data != null) {
			returnValue = new Double(data);
			value = returnValue.doubleValue();
		}
		System.out.println(key + " : " + value);
		return value;
	}

	public int getInt(String key) {
		String data = search(key);
		Double returnValue = null;
		int value = -1;
		if (data != null) {
			returnValue = new Double(data);
			value = returnValue.intValue();
		}
		System.out.println(key + " : " + value);
		return value;
	}
	public String getString(String key) {
		String data = search(key);
		System.out.println(key + " : " + data);
		return data;
	}
	
	public boolean getBoolean(String key) {
		String data = search(key);
		if (data != null) {
			if(data.equals("true")) {
				return true;
			}
			else {
				return false;
			}
		}
		System.out.println(key + " : " + data);
		return false;
	}
}