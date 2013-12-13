package pororo.com;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public final class Lib {
	
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	
	public static int convertStrToInt(String str) throws NumberFormatException{ 
		return (str == null || str.length() == 0) ? 0 : Integer.parseInt(str);
	} 
	
	public static String convertToKSC5601(String str) {
		String result = null;
		
		if (str != null && str.length() != 0) {
			try {
//				result = new String(str.getBytes("KSC5601"));
				result = new String(str.getBytes("KSC5601"), "8859_1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
   public static String removeStart(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)){
            return str.substring(remove.length());
        }
        return str;
    }
   
   public static boolean contains(String str, String searchStr) {
       if (str == null || searchStr == null) {
           return false;
       }
       return str.indexOf(searchStr) >= 0;
   }
	
	
	
   // max: 여러번 -1, 한번 1
   public static String replace(String text, String repl, String with, int max) {
       if (isEmpty(text) || isEmpty(repl) || with == null || max == 0) {
           return text;
       }
       int start = 0;
       int end = text.indexOf(repl, start);
       if (end == -1) {
           return text;
       }
       int replLength = repl.length();
       int increase = with.length() - replLength;
       increase = (increase < 0 ? 0 : increase);
       increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
       StringBuffer buf = new StringBuffer(text.length() + increase);
       while (end != -1) {
           buf.append(text.substring(start, end)).append(with);
           start = end + replLength;
           if (--max == 0) {
               break;
           }
           end = text.indexOf(repl, start);
       }
       buf.append(text.substring(start));
       return buf.toString();
   }
   
	

	public static String substr(String line, int start){
		if(start >= line.length()){return "";}
		try{
			return line.substring(start);
		}
		catch(Exception e){e.printStackTrace();}
		return line;
	}

	
	public static String[] split(String line){
		if(line.length() == 0){return new String[]{""};}

		StringTokenizer st = new StringTokenizer(line);
		String[] str_arr = new String[st.countTokens()];
		for(int i = 0; i < str_arr.length; i++){
			str_arr[i] = st.nextToken();
		}
		return str_arr;
	}
	
	
	public static String[] split(String line, String delim){
		if(line.length() == 0 || delim.length() == 0){return new String[]{""};}

		ArrayList lines = new ArrayList();

		int len = line.length();
		int d_len = delim.length();
		int offset = 0;
		int stop = 0;
		while(offset < len){
			stop = line.indexOf(delim, offset);
			if(stop < 0){break;}
			lines.add(line.substring(offset, stop));
			offset = stop + d_len;
		}
		lines.add(line.substring(offset, len));

		//List -> String Array
		String[] str_arr = new String[lines.size()];
		lines.toArray(str_arr);

		return str_arr;
	}

	
	
}

