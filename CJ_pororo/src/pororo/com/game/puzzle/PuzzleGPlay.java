package pororo.com.game.puzzle;

import pororo.com.Scene;
import pororo.com.SceneManager;
import pororo.com.StateValue;
import pororo.com.game.puzzle.util.TextLoader;

import java.awt.Graphics2D;
import java.net.MalformedURLException;
import java.net.URL;


public class PuzzleGPlay extends Scene {

	public PuzzleGPlay() {
		// 세팅값 로딩
		 TextLoader rl = new TextLoader();
		try {
			if(StateValue.isUrlLive) {
				rl.parserURL(new URL(StateValue.liveResource + "state/puzzle/state.txt"));
			}else {
//				rl.parserLocal("state/puzzle/state.txt");
				rl.parserURL(new URL(StateValue.testResource + "state/puzzle/state.txt"));
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		GameData.getInstance().maxStage = rl.getInt("maxStage");
		
		GameData.getInstance().time = rl.getInt("totalTime");
		GameData.getInstance().totalTime = rl.getInt("totalTime");
		GameData.getInstance().failTime = rl.getInt("failTime");
		
		ImageDraw.getInstance().timeGageX = rl.getInt("timeGageX");
		ImageDraw.getInstance().timeGageY = rl.getInt("timeGageY");
		ImageDraw.getInstance().timeGageW = rl.getInt("timeGageW");
		ImageDraw.getInstance().timeGageH = rl.getInt("timeGageH");
		
		ImageDraw.getInstance().puzzleX = rl.getInt("puzzleX");
		ImageDraw.getInstance().puzzleY = rl.getInt("puzzleY");
		
		ImageDraw.getInstance().collisionRect = false;//rl.getBoolean("collisionRect");
		
		GameData.getInstance().pororo.posX = rl.getInt("pororoX");
		GameData.getInstance().pororo.posY = rl.getInt("pororoY");
		
		ImageDraw.getInstance().miniX = rl.getInt("miniX");
		ImageDraw.getInstance().miniY = rl.getInt("miniY");
		
		ImageDraw.getInstance().scoreX = rl.getInt("scoreX");
		ImageDraw.getInstance().scoreY = rl.getInt("scoreY");
		ImageDraw.getInstance().scoreOffset = rl.getInt("scoreOffset");
		
		ImageDraw.getInstance().stageX = rl.getInt("stageX");
		ImageDraw.getInstance().stageY = rl.getInt("stageY");
		ImageDraw.getInstance().stageOffset = rl.getInt("stageOffset");
	}

	public void draw(Graphics2D g) {
		// 화면을 그린다
		ImageDraw.getInstance().draw(GameData.getInstance().gameState, g);
	}

	public void process(int i) {
		// 게임 로직 처리
		GameData.getInstance().process();
	}

	public void processKey(Object nn, int event) {
		// 키이벤트 처리
		KeyEvent.getInstance().processKey(event);
	}

	public void destroyScene() {
		// 모튼 Class를 삭제
		ImageDraw.getInstance().destroy();
		GameData.getInstance().destroy();
		KeyEvent.getInstance().destroy();
		Resource.getInstance().destroy1();
		Resource.getInstance().destroy2();
	}
}