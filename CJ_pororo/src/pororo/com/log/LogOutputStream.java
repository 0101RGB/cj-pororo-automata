package pororo.com.log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public final class LogOutputStream extends OutputStream {

	private DatagramSocket sock;
	private InetAddress addr;
	private int port;
	
	public LogOutputStream(String ip, int port) {
		try {
			sock = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			addr = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		this.port = port;
	}
	
	
	public void write(byte[] abyte0, int i, int j) throws IOException {
		byte[] temp = new byte[j];
		for(int k = i, l = 0; k < j; k++, l++) {
			temp[l] = abyte0[k];
		}
		DatagramPacket packet = new DatagramPacket(temp, temp.length, addr, port);
		send(packet);
	}

	public void write(byte[] abyte0) throws IOException {
		DatagramPacket packet = new DatagramPacket(abyte0, abyte0.length, addr, port);
		send(packet);
	}


	public void write(int i) throws IOException {
	}

	public void close() throws IOException {
		if(sock != null) {
			sock.close();
		}
	}

	public void flush() throws IOException {
	}


	private void send(DatagramPacket packet) {
		try {
			sock.send(packet);
		} catch (IOException e) {
		}
	}
}
