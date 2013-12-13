package pororo.com.framework;

//import java.io.File;
//import java.io.FileInputStream;

import java.net.URL;

import org.havi.ui.HSound;

import pororo.com.SceneManager;

public class Sound
{
	public HSound[] m_player;
	public int mn_sndId;
	public int mn_playId;
	//public HSound player;
	//public HSound m_player2;
	//public Byte[][] mb_sndData;
	//public SoundData[] m_sndData;

	public Sound(int soundCnt)
	{
		//System.out.println("Sound init start");
		
		//m_player = new HSound();
		m_player = new HSound[soundCnt];
		mn_sndId = -1;
		mn_playId = -1;
		
		//m_player2 = new HSound();
		
		//mb_sndData = new Byte[soundCnt][];

		//m_sndData = new SoundData[soundCnt];
		
		/*
		for(int i=0; i<soundCnt; i++) {
			m_sndData[i].allocData(10000);
		}
		 */
		
		//System.out.println("Sound init end");
	}
	
	public int loadSound(String url){
		if(SceneManager.isEmul) {
			return -1;	
		}
		//System.out.println("load sound : " + url);
		
		HSound player;
		player = new HSound();
		
/*
		File file = null;
		FileInputStream fis = null;
		byte[] data;
		
		try {
			//file = new File(url);
			file = new File("img/title/snd/move.mp2");
			System.out.println("File created length : " + file.length());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			fis = new FileInputStream(file);
			System.out.println("FileInputStream created available : " + fis.available());
		} catch(Exception e) {
			e.printStackTrace();
		}

		data = new byte[(int) file.length()];
		//m_sndData[mn_sndId].allocData((int) file.length());
		//m_sndData[mn_sndId].data = new byte[10000];
		//data = new byte[(int) file.length()];
		//System.out.println("load sound data length : " + m_sndData[mn_sndId].data.length);
		//System.out.println("load sound data length : " + data.length);
		
		try {
			//fis.read(m_sndData[mn_sndId].data);
			fis.read(data);
			System.out.println("FileInputStream data read");
		} catch(Exception e) {
			e.printStackTrace();
		}
*/

		mn_sndId++;
		//System.out.println("mn_sndId : " + mn_sndId);
        try {
        	//player.load("img/title/snd/move.mp2");
        	player.load(url);
        	m_player[mn_sndId] = player;
        } catch(Exception e) {
            e.printStackTrace();
        }

		/*
        try {
        	m_player2.load("img/title/snd/move.mp2");
        } catch(Exception e) {
            e.printStackTrace();
        }
        */
/*
		mn_sndId++;
		System.out.println("mn_sndId : " + mn_sndId);
		
		m_sndData[mn_sndId].data = data;
	*/
		return mn_sndId;
	}
	
	public int loadSound(URL url){
//		System.out.println("Server Sound File : " + url);
		
		if(SceneManager.isEmul) {
			return -1;	
		}
		
		HSound player;
		player = new HSound();
	
		mn_sndId++;
        try {
        	player.load(url);
        	m_player[mn_sndId] = player;
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        if(m_player[mn_sndId] == null) {
        	for(int i=0; i<10; i++) {
				try {
					player.load(url);
					m_player[mn_sndId] = player;
				} catch(Exception e) {
		            e.printStackTrace();
		        }
				
				if(m_player[mn_sndId] != null)
					break;
			}
        }
        
		return mn_sndId;
	}
	
	
	private long currentTime;
	private long startTime;
	
	public void playSound(int soundId){
		currentTime = System.currentTimeMillis();
		if (currentTime - startTime < 500L) {
			return;
		}
		else {
			startTime = currentTime;
		}
		
		//System.out.println("play sound soundId : " +  soundId);
		if(mn_playId > 0) {
			stopSound(mn_playId);
		}
		
		mn_playId = soundId;
		//m_player.set(m_sndData[soundId].data);
		//if(m_player != null)
		//	m_player.play();
		if(m_player[mn_playId] == null){}
//			System.out.println("play sound soundId : " +  mn_playId + " => null");
		else {
			m_player[mn_playId].play();
		}
	}

	public void stopSound(int soundId){
		if(m_player[soundId] == null){}
//			System.out.println("stop sound soundId : " +  soundId + " => null");
		else {
			m_player[soundId].stop();
			startTime = 0;
		}
		//if(m_player != null)
		//	m_player.stop();
	}
	
	public void stopSound(){
		if(mn_playId > 0) {
			if(m_player[mn_playId] == null){}
//				System.out.println("stop sound soundId : " +  mn_playId + " => null");
			else {
				m_player[mn_playId].stop();
				startTime = 0;
			}
		}
		//if(m_player != null)
		//	m_player.stop();
	}
	
	public void destroySound() {
		/*
		if(m_player != null) {
			m_player.stop();
			m_player.dispose();
			m_player = null;
		}
		*/
		
		for(int i=0; i<=mn_sndId; i++) {
			//m_sndData[i].delete();
			//m_sndData[i] = null;
			if(m_player[i] != null) {
				m_player[i].stop();
				m_player[i].dispose();
				m_player[i] = null;
			}
		}
		//m_player = null;
		//m_sndData = null;
	}
	
//	public void delMutesSound() {
//		if ( m_player[1] != null ) {
//			m_player[1].stop();
//			m_player[1].dispose();
//			m_player[1] = null;
//		}
//	}

	/*
	public class SoundData {
		public byte[] data;
		
		SoundData() {
			data = null;
			//System.out.println("SoundData init start");
			
			//data = new byte[10000];
			
			//System.out.println("SoundData init end");
		}
		
		public void allocData(int size) {
			//data = new byte[size];
			//System.out.println("SoundData allocData start");
			
			//data = new byte[10000];
			
			//System.out.println("SoundData allocData end");
		}

		public void delete() {
			data = null;
		}
	}
	*/
}