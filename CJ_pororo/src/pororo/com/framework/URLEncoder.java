package pororo.com.framework;

import java.io.*;
import java.security.AccessController;
import java.util.BitSet;
import sun.security.action.GetPropertyAction;

public class URLEncoder
{

    private URLEncoder()
    {
    }

    /**
     * @deprecated Method encode is deprecated
     */

    public static String encode(String s)
    {
        String s1 = null;
        try
        {
            s1 = encode(s, dfltEncName);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return s1;
    }

    public static String encode(String s, String s1)
        throws UnsupportedEncodingException
    {
        boolean flag = false;
        boolean flag1 = false;
        byte byte0 = 10;
        StringBuffer stringbuffer = new StringBuffer(s.length());
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(byte0);
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(bytearrayoutputstream, s1);
        for(int i = 0; i < s.length(); i++)
        {
            int j = s.charAt(i);
            if(dontNeedEncoding.get(j))
            {
                if(j == 32)
                {
                    j = 43;
                    flag = true;
                }
                stringbuffer.append((char)j);
                flag1 = true;
                continue;
            }
            try
            {
                if(flag1)
                {
                    outputstreamwriter = new OutputStreamWriter(bytearrayoutputstream, s1);
                    flag1 = false;
                }
                outputstreamwriter.write(j);
                if(j >= 55296 && j <= 56319 && i + 1 < s.length())
                {
                    char c = s.charAt(i + 1);
                    if(c >= '\uDC00' && c <= '\uDFFF')
                    {
                        outputstreamwriter.write(c);
                        i++;
                    }
                }
                outputstreamwriter.flush();
            }
            catch(IOException ioexception)
            {
                bytearrayoutputstream.reset();
                continue;
            }
            byte abyte0[] = bytearrayoutputstream.toByteArray();
            for(int k = 0; k < abyte0.length; k++)
            {
                stringbuffer.append('%');
                char c1 = Character.forDigit(abyte0[k] >> 4 & 0xf, 16);
                if(Character.isLetter(c1))
                    c1 -= ' ';
                stringbuffer.append(c1);
                c1 = Character.forDigit(abyte0[k] & 0xf, 16);
                if(Character.isLetter(c1))
                    c1 -= ' ';
                stringbuffer.append(c1);
            }

            bytearrayoutputstream.reset();
            flag = true;
        }

        return flag ? stringbuffer.toString() : s;
    }

    static BitSet dontNeedEncoding;
    static final int caseDiff = 32;
    static String dfltEncName = null;

    static 
    {
        dontNeedEncoding = new BitSet(256);
        for(int i = 97; i <= 122; i++)
            dontNeedEncoding.set(i);

        for(int j = 65; j <= 90; j++)
            dontNeedEncoding.set(j);

        for(int k = 48; k <= 57; k++)
            dontNeedEncoding.set(k);

        dontNeedEncoding.set(32);
        dontNeedEncoding.set(45);
        dontNeedEncoding.set(95);
        dontNeedEncoding.set(46);
        dontNeedEncoding.set(42);
        dfltEncName = (String)AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
    }
}
