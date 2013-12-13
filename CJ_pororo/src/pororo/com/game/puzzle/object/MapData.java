package pororo.com.game.puzzle.object;

import pororo.com.StateValue;
import pororo.com.game.puzzle.GameData;
import pororo.com.game.puzzle.ImageDraw;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;


public class MapData {

	// 전체 조각 갯수
	public int pieceCount;
	// 맵정보
	public TileData[][] map = new TileData[8][7];

	public void loadMap(int stage) {
		// 맵을 로딩하여 세팅
		System.out.println("Load Map");
		String data = "";
		// FILE IO
		// 파일 읽기
		// try {
		URL url = null;
		try {
			if (!StateValue.isUrlLive) {
				url = new URL(StateValue.testResource
						+ "puzzle/puzzle_map/map/map_" + stage + ".txt");
			} else {
				url = new URL(StateValue.liveResource
						+ "puzzle/puzzle_map/map/map_" + stage + ".txt");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		URLConnection con = null;
		try {
			con = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// FileReader reader;
		// reader = new FileReader("map/map" + index + ".txt");

		if (con == null) {
			System.out.println("MAP URLConnection null");
		}
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new InputStreamReader(con
					.getInputStream(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bufferReader == null) {
			System.out.println("MAP bufferReader null");
			return;
		}
		String cnt;
		try {
			while ((cnt = bufferReader.readLine()) != null) {
				data = cnt;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int start = 0;
		int end = 0;
		Vector temp = new Vector();
		for (int i = 0; i < data.length(); i++) {
			if (data.substring(i, i + 1).equals("◈")) {
				Integer count = new Integer(data.substring(0, i));
				pieceCount = count.intValue();
				data = data.substring(i, data.length());
				break;
			}
		}
		for (int i = 0; i < data.length(); i++) {
			if (data.substring(i, i + 1).equals("◈")) {
				if (i != 0) {
					end = i;
					temp.addElement(data.substring(start, end));
					start = (end + 1);
				}
				if (i == 0) {
					start = 1;
				}
			}
		}
		temp.addElement(data.substring(start, data.length()));

		Vector temp1[] = new Vector[temp.size()];

		String line[] = new String[temp.size()];
		for (int i = 0; i < line.length; i++) {
			line[i] = ((String) (temp.elementAt(i)));
			temp1[i] = new Vector();
			start = 0;
			end = 0;
			for (int j = 0; j < line[i].length(); j++) {
				if (line[i].substring(j, j + 1).equals("◎")) {
					end = j;
					temp1[i].addElement(line[i].substring(start, end));
					start = (end + 1);
				}
			}
			temp1[i].addElement(line[i].substring(start, line[i].length()));
		}
		String line1[][] = new String[temp1.length][];
		Vector temp2[][] = new Vector[temp1.length][];

		for (int i = 0; i < temp1.length; i++) {
			line1[i] = new String[temp1[i].size()];
			temp2[i] = new Vector[temp1[i].size()];
			for (int j = 0; j < temp1[i].size(); j++) {
				line1[i][j] = (String) (temp1[i].elementAt(j));
				temp2[i][j] = new Vector();
				start = 0;
				end = 0;
				for (int k = 0; k < line1[i][j].length(); k++) {
					if (line1[i][j].substring(k, k + 1).equals("▣")) {
						end = k;
						temp2[i][j].addElement(line1[i][j]
								.substring(start, end));
						start = (end + 1);
					}
				}
				temp2[i][j].addElement(line1[i][j].substring(start, line1[i][j]
						.length()));
			}
		}

		String line2[][][] = new String[temp1.length][][];
		for (int i = 0; i < temp2.length; i++) {
			line2[i] = new String[temp2[i].length][];
			for (int j = 0; j < temp2[i].length; j++) {
				line2[i][j] = new String[temp2[i][j].size()];
				for (int k = 0; k < temp2[i][j].size(); k++) {
					line2[i][j][k] = (String) (temp2[i][j].elementAt(k));
				}
			}
		}

		map = new TileData[8][7];
		for (int i = 0; i < line2.length; i++) {
			for (int j = 0; j < line2[i].length; j++) {
				map[i][j] = new TileData();
			}
		}

		for (int i = 0; i < line2.length; i++) {
			for (int j = 0; j < line2[i].length; j++) {
				Integer parserPiece1 = new Integer(line2[i][j][0]);
				Integer parserPiece2 = new Integer(line2[i][j][1]);
				Integer parserPiece3 = new Integer(line2[i][j][2]);
				Integer parserPiece4 = new Integer(line2[i][j][3]);
				Integer parserPosX1 = new Integer(line2[i][j][4]);
				Integer parserPosY1 = new Integer(line2[i][j][5]);
				Integer parserPosX2 = new Integer(line2[i][j][6]);
				Integer parserPosY2 = new Integer(line2[i][j][7]);
				Integer parserPosX3 = new Integer(line2[i][j][8]);
				Integer parserPosY3 = new Integer(line2[i][j][9]);
				Integer parserPosX4 = new Integer(line2[i][j][10]);
				Integer parserPosY4 = new Integer(line2[i][j][11]);

				map[i][j].piece[0] = parserPiece1.intValue();
				map[i][j].piece[1] = parserPiece2.intValue();
				map[i][j].piece[2] = parserPiece3.intValue();
				map[i][j].piece[3] = parserPiece4.intValue();

				
				map[i][j].posX[0] = parserPosX1.intValue();
				map[i][j].posY[0] = parserPosY1.intValue();
				map[i][j].posX[1] = parserPosX2.intValue();
				map[i][j].posY[1] = parserPosY2.intValue();
				map[i][j].posX[2] = parserPosX3.intValue();
				map[i][j].posY[2] = parserPosY3.intValue();
				map[i][j].posX[3] = parserPosX4.intValue();
				map[i][j].posY[3] = parserPosY4.intValue();
			}
		}
		if (GameData.getInstance().piece != null) {
			GameData.getInstance().piece.removeAllElements();
			GameData.getInstance().piece = null;
		}
		GameData.getInstance().piece = new Vector();

		for (int i = 0; i < pieceCount; i++) {
			PieceData pieceData = new PieceData();
			for (int k = 0; k < 8; k++) {
				for (int j = 0; j < 7; j++) {
					for (int l = 0; l < 4; l++) {
						if (GameData.getInstance().mapData.map[k][j].piece[l] - 1 == i) {
							pieceData.posX = GameData.getInstance().mapData.map[k][j].posX[l];
							pieceData.posY = GameData.getInstance().mapData.map[k][j].posY[l];
							k = 8;
							j = 7;
							break;
						}
					}
				}
			}
			GameData.getInstance().piece.addElement(pieceData);
		}
		temp.removeAllElements();
		temp = null;
		for (int i = 0; i < temp2.length; i++) {
			temp1[i].removeAllElements();
			temp1[i] = null;
			for (int j = 0; j < temp2[i].length; j++) {
				temp2[i][j].removeAllElements();
				temp2[i][j] = null;
			}
			temp2[i] = null;
		}
		temp1 = null;
		temp2 = null;
	}

	public boolean collision(int pieceIndex, Rectangle r) {
		// 50% 이상 collision 시에만 collision 처리
		// false = collision 안됨
		// true = collision 됨
		float totalCount = 0;
		float collisionCount = 0;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				for (int k = 0; k < 4; k++) {
					if (GameData.getInstance().mapData.map[i][j].piece[k] == GameData
							.getInstance().pororo.puzzleIndex) {
						totalCount += 1F;
						// collision 처리 영역
						switch (collision(r, new Rectangle(ImageDraw
								.getInstance().puzzleX
								+ (i * 40) + 1, ImageDraw.getInstance().puzzleY
								+ (j * 40) + 1, 38, 38))) {
						case 0:
							break;
						case 1:
							collisionCount += 0.5F;
							break;
						case 2:
							collisionCount += 1F;
							break;
						}
					}
				}
			}
		}
		if (collisionCount >= (totalCount / 2)) {
			return true;
		}
		return false;
	}

	public int collision(Rectangle piece, Rectangle tile) {
		
		// 50% 이상 collision 시에만 collision 처리
		// 0 = collision 안됨
		// 1 = collision 반만 됨
		// 2 = collision 됨
		int collisionX = 0;
		int collisionY = 0;

		if (piece.x < tile.x + tile.width && piece.x + piece.width > tile.x) {
			if (piece.x + piece.width > tile.x + tile.width) {
				// collision 됨
				collisionX = 2;
			} else {
				// collision 반만 됨
				collisionX = 1;
			}
		}
		else {
			return 0;
		}
		
		if (piece.y < tile.y + tile.height && piece.y + piece.height > tile.y) {
			if (piece.y + piece.height > tile.y + tile.height) {
				// collision 됨
				collisionY = 2;
			} else {
				// collision 반만 됨
				collisionY = 1;
			}
		}
		else {
			return 0;
		}
		
		if (collisionX == 2 && collisionY == 2) {
			return 2;
		}
		return 1;
	}
}
