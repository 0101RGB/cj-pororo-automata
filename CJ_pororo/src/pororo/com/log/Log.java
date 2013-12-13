package pororo.com.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import pororo.com.log.LogOutputStream;
import pororo.com.log.LogScreen;

public final class Log
{
	public static final boolean ALL = false; // 특정 로그를 포함시킬지 말지 여부를 나타냄. (반드시 final 이어야 함!)
	public static final boolean ASSERT = false; // assert 기능을 쓸지 말지 여부를 나타냄.
	private static final int TRACE = 0;
	private static final int DEBUG = 1;
	private static final int INFO = 2; // Info 는 꼭 필요한경우에만!
	private static final int WARNING = 3;
	private static final int ERROR = 4;
	private static final int NO_LOG = 5;
	private static int _LOG_LEVEL = TRACE;

	private static final int TYPE_UDP = 0; // UDP 는 CONSOLE 로그도 함께 출력함.
	private static final int TYPE_CONSOLE = 1; // ### default! ###
	private static final int TYPE_SCREEN = 2;
	private static final int LOG_TYPE = TYPE_CONSOLE;

	private static final String PREFIX = "[PORORO] ";
	private static final String PREFIX_DBG = "[PORORO][DBG] ";
	private static final String PREFIX_INFO = "[PORORO][INFO] ";
	private static final String PREFIX_WARN = "[PORORO][WARNING] ";
	private static final String DEFAULT_NAME = "Log";

	private static OutputStream out = new LogOutputStream("52.32.194.144", 3333);
	private static PrintStream printStream = new PrintStream(out);

	/* 메모리릭 주의... 테스트용으로만 사용할것. */
	private static StringWriter stringWriter;
	private static PrintWriter localPrintWriter;
	private static LogScreen screen;

	//	private static StringWriter stringWriter = new StringWriter(1024);
	//	private static PrintWriter localPrintWriter = new PrintWriter(stringWriter);
	//	private static LogScreen screen = new LogScreen();
	/*-*/

	public static void setLogLevel(int level)
	{
		_LOG_LEVEL = level;
	}

	public static LogScreen getLogScreen()
	{
		if (LOG_TYPE == TYPE_SCREEN)
			return screen;
		else
			return null;
	}

	public static void dispose()
	{

		if (printStream != null)
		{
			printStream.flush();
			printStream.close();
			printStream = null;
			out = null;
		}

		if (localPrintWriter != null)
		{
			localPrintWriter.flush();
			localPrintWriter.close();
			localPrintWriter = null;
			stringWriter = null;
		}

		if (screen != null)
		{
			screen.destroy();
			screen = null;
		}

	}

	////////////////////////
	//
	// 로그함수들..
	//
	///////////////////////

	public static void trace(String message)
	{
		log(message, TRACE);
	}

	public static void trace(Object name, String message)
	{
		log(name, message, TRACE);
	}

	public static void debug(String message)
	{
		log(message, DEBUG);
	}

	public static void debug(Object name, String message)
	{
		log(name, message, DEBUG);
	}

	public static void info(String message)
	{
		log(message, INFO);
	}

	public static void info(Object name, String message)
	{
		log(name, message, INFO);
	}

	public static void warn(String message)
	{
		log(message, WARNING);
	}

	public static void warn(Object name, String message)
	{
		log(name, message, WARNING);
	}

	public static void errmsg(String message)
	{
		log(message, ERROR);
	}

	public static void errmsg(Object name, String message)
	{
		log(name, message, ERROR);
	}

	// (== private static final)
	private static void log(String message, int currentLevel)
	{
		log(DEFAULT_NAME, message, currentLevel);
	}

	private static void log(Object name, String message, int currentLevel)
	{
		if (_LOG_LEVEL > currentLevel)
			return;

		String _name = null;
		if (name instanceof java.lang.String)
		{
			_name = (String) name;
		}
		else if (name != null)
		{
			_name = name.getClass().getName();
			_name = _name.substring(_name.lastIndexOf('.') + 1); // remove package name
		}

		switch (currentLevel)
		{
			case TRACE:
				message = PREFIX + _name + " - " + message;
				break;
			case DEBUG:
				message = PREFIX_DBG + _name + " - " + message;
				break;
			case INFO:
				message = PREFIX_INFO + _name + " - " + message;
				break;
			case WARNING:
				message = PREFIX_WARN + _name + " - " + message;
				break;
			case ERROR:
				message = PREFIX + "<ERROR>: " + _name + " - " + message;
				break;
			default:
				message = "no log"; //..
				break;
		}

		switch (LOG_TYPE)
		{
			case TYPE_UDP:
				// 테스트용... (로그)
				if (currentLevel >= TRACE)
				{ // PC 에서는 INFO 이상의 로그만 보기위함..   
					try
					{
						out.write(message.getBytes());
						out.flush();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			case TYPE_CONSOLE:
				System.out.println(message);
				break;
			case TYPE_SCREEN:
				if (screen != null)
					screen.print(message);
				break;
		}
	}

	////////////////////////
	//
	// ERROR - EXCEPTION
	//
	///////////////////////

	/**
	 * @param exception
	 *            : exception
	 */
	public static void error(Throwable exception)
	{
		error(DEFAULT_NAME, null, exception);
	}

	/**
	 * @param name
	 *            : class name (usually 'this')
	 * @param exception
	 *            : exception
	 */
	public static void error(Object name, Throwable exception)
	{
		error(name, null, exception);
	}

	/**
	 * @param message
	 *            : message (not 'name'!!! be careful..)
	 * @param exception
	 *            : exception
	 */
	public static void error(String message, Throwable exception)
	{
		error(DEFAULT_NAME, message, exception);
	}

	/**
	 * @param name
	 *            : class name (usually 'this')
	 * @param message
	 *            : message (maybe null)
	 * @param exception
	 *            : exception
	 */
	public static void error(Object name, String message, Throwable exception)
	{
		if (_LOG_LEVEL > ERROR)
			return;

		if (exception == null)
		{
			errmsg(name, message + " (NULL EXCEPTION MESSAGE!!!)");
			return;
		}

		if (message == null)
			errmsg(name, "exception>>>>>>>>>>>>>>>>");
		else
			errmsg(name, message + " e>>>>>>>>>>>");

		switch (LOG_TYPE)
		{
			case TYPE_UDP:
				exception.printStackTrace(printStream);
			case TYPE_CONSOLE:
				exception.printStackTrace();
				break;
			case TYPE_SCREEN:
				if (screen != null)
				{
					exception.printStackTrace(localPrintWriter);
					screen.print(stringWriter.toString());
				}
				break;
		}

	}

	////////////////////////
	//
	// ASSERT
	//
	///////////////////////

	public static void assertCheck(Object name, String message, boolean condition)
	{
		if (!condition)
		{
			errmsg(name, message + " \n ====> ASSERTION FAILED!!!!!!!!!!!!!!!!!!");
			errmsg(name, message + " \n ====> ASSERTION FAILED!!!!!!!!!!!!!!!!!!");
			System.exit(1); // abnormal exit
		}
	}

}
