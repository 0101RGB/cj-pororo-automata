package pororo.com.framework;

import java.io.UnsupportedEncodingException;


public class URLDecoder
{

  public static String decode(String paramString1, String paramString2)
    throws UnsupportedEncodingException
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    int j = paramString1.length();
    int k = 0;
    if (paramString2.length() == 0)
      throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
    while (k < j)
    {
      char c = paramString1.charAt(k);
      switch (c)
      {
      case '+':
        localStringBuffer.append(' ');
        k++;
        i = 1;
        break;
      case '%':
        try
        {
          byte[] arrayOfByte = new byte[(j - k) / 3];
          int m = 0;
          while ((k + 2 < j) && (c == '%'))
          {
            arrayOfByte[(m++)] = (byte)Integer.parseInt(paramString1.substring(k + 1, k + 3), 16);
            k += 3;
            if (k >= j)
              continue;
            c = paramString1.charAt(k);
          }
          if ((k < j) && (c == '%'))
            throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
          localStringBuffer.append(new String(arrayOfByte, 0, m, paramString2));
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + localNumberFormatException.getMessage());
        }
        i = 1;
        break;
      default:
        localStringBuffer.append(c);
        k++;
      }
    }
    return i != 0 ? localStringBuffer.toString() : paramString1;
  }
}