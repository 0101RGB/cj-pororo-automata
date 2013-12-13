
package pororo.com.framework;

import java.awt.Color;
import java.awt.Graphics;

public class Memory {
	
	private Memory() {}

	public static void check() {
		long totalMem = Runtime.getRuntime().totalMemory();
		long freeMem = Runtime.getRuntime().freeMemory();
		long useMem = totalMem - freeMem;
//		System.out.println("\n\tTotal Memory : " + totalMem + " Bytes("+ (totalMem / 1024) + "K)");
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\tUse   Memory : " + useMem + " Bytes("+ (useMem / 1024) + "K)");
		System.out.println("\tFree  Memory : " + freeMem + " Bytes("+ (freeMem / 1024) + "K)");
		System.out.println("---------------------------------------------------------------------------");
	}

	public static void draw(Graphics g, int x, int y) {
		g.setColor(Color.black);
//		String strMem = "free mem : "+ Runtime.getRuntime().freeMemory()/ 1024+ "k  \n"+ "used mem : "+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + "k";
		String strMem = "free mem : "+ Runtime.getRuntime().freeMemory()/ 1024+ "k ";// "used mem : "+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + "k";
		g.drawString(strMem, x, y);
	}
}
