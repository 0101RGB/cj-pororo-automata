package pororo.com.game.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import pororo.com.StateValue;

public final class MapData {
	private static MapData instance = new MapData();

	public Tile map[][][];

	public int item[][];

	public static MapData getInstance() {
		if (instance == null)
			instance = new MapData();
		return instance;
	}

	public void mapDataCreate(int index[]) {
		// System.out.println("Map init");
		// 맵정보 5개 로딩
		if (map != null) {
			map = null;
		}
		map = new Tile[index.length][][];
		for (int i = 0; i < map.length; i++) {
			map[i] = loadMap((index[i] + 1));
		}
		item = new int[5][];
		for (int i = 0; i < item.length; i++) {
			item[i] = new int[3];
			for (int j = 0; j < item[i].length; j++) {
				item[i][j] = GameData.ITEM_NONE;
			}
		}
	}

	public void removeScore(int mapIndex) {
		if (map != null && map[mapIndex] != null) {
			for (int i = 0; i < map[mapIndex].length; i++) {
				if (map[mapIndex][i] != null) {
					for (int j = 0; j < map[mapIndex][i].length; j++) {
						switch (map[mapIndex][i][j].itemIndex) {
						case GameData.ITEM_SCORE_50:
						case GameData.ITEM_SCORE_100:
						case GameData.ITEM_SCORE_200:
							map[mapIndex][i][j].itemIndex = GameData.ITEM_NONE;
							map[mapIndex][i][j].isItemHide = false;
							break;
						}
					}
				}
			}
		}
	}

	public boolean checkMap(int mapIndex, int indexX, int indexY, int item) {
		if (!map[mapIndex][indexX][indexY].isItemMake) {
			return false;
		}
		if (map[mapIndex][indexX][indexY].itemIndex != GameData.ITEM_NONE) {
			return false;
		}
		// System.out.println("mapIndex : " + mapIndex + ", indexX :" + indexX +
		// ", indexY :" + indexY);
		if (item == GameData.ITEM_TRAP_B || item == GameData.ITEM_TRAP_R) {
			if (indexX > 0) {
				if (map[mapIndex][indexX - 1][indexY].itemIndex == GameData.ITEM_TRAP_B
						|| map[mapIndex][indexX - 1][indexY].itemIndex == GameData.ITEM_TRAP_R) {
					// 왼쪽 아이템 체크
					return false;
				}
			}
			if (indexX < 20 - 1) {
				if (map[mapIndex][indexX + 1][indexY].itemIndex == GameData.ITEM_TRAP_B
						|| map[mapIndex][indexX + 1][indexY].itemIndex == GameData.ITEM_TRAP_R) {
					// 오른쪽 아이템 체크
					return false;
				}
			}
			if (indexY > 0) {
				if (map[mapIndex][indexX][indexY - 1].itemIndex == GameData.ITEM_TRAP_B
						|| map[mapIndex][indexX][indexY - 1].itemIndex == GameData.ITEM_TRAP_R) {
					// 위 아이템 체크
					return false;
				}
			}
			if (indexY < 11 - 1) {
				if (map[mapIndex][indexX][indexY + 1].itemIndex == GameData.ITEM_TRAP_B
						|| map[mapIndex][indexX][indexY + 1].itemIndex == GameData.ITEM_TRAP_R) {
					// 아래 아이템 체크
					return false;
				}
			}
		}
		map[mapIndex][indexX][indexY].isItemHide = false;
		map[mapIndex][indexX][indexY].itemIndex = item;
		return true;
	}

	public int getItem(int x, int y) {
		if (map[GameData.getInstance().getMapIndex()][x][y].isItemHide)
			return GameData.ITEM_NONE;

		return map[GameData.getInstance().getMapIndex()][x][y].itemIndex;
	}

	public void setItem(int x, int y, int index) {
		if (index == GameData.ITEM_NONE)
			map[GameData.getInstance().getMapIndex()][x][y].isItemHide = true;
		else
			map[GameData.getInstance().getMapIndex()][x][y].itemIndex = index;
	}

	public void mapReset() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				for (int k = 0; k < map[i][j].length; k++) {
					map[i][j][k].isItemHide = false;
				}
			}
		}
	}

	public void destroy() {
		instance = null;
	}

	private Tile[][] loadMap(int index) {
		Tile mapTile[][] = new Tile[21][11];
		for (int i = 0; i < mapTile.length; i++) {
			for (int j = 0; j < mapTile[i].length; j++) {
				mapTile[i][j] = new Tile();
			}
		}
		String data = "";
		// FILE IO
		// 파일 읽기
		//try {
		URL url = null;
		try {
			if (!StateValue.isUrlLive) {
				url = new URL(StateValue.testResource + "maze/map/map" + index
							+ ".txt");
			} else {
				url = new URL(StateValue.liveResource + "maze/map/map" + index
						+ ".txt");
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
				for (int i = 0; i < mapTile.length; i++) {
					for (int j = 0 ; j < mapTile[i].length ; j++) {

						mapTile[i][j].right = false;
						mapTile[i][j].left = false;
						mapTile[i][j].up = false;
						mapTile[i][j].down = false;
						mapTile[i][j].isItemMake = false;
						return null;
					}
				}
			}
			BufferedReader bufferReader = null;
			try {
				bufferReader = new BufferedReader(
						new InputStreamReader(con.getInputStream(),"UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (bufferReader == null) {
				System.out.println("MAP bufferReader null");
				for (int i = 0; i < mapTile.length; i++) {
					for (int j = 0; j < mapTile[i].length; j++) {

						mapTile[i][j].right = false;
						mapTile[i][j].left = false;
						mapTile[i][j].up = false;
						mapTile[i][j].down = false;
						mapTile[i][j].isItemMake = false;
					}
				}
				return null;
			}
			String cnt;
			try {
				while ((cnt = bufferReader.readLine()) != null) {
					data = cnt;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//} 
		/*catch (Throwable e) {
			System.out.println("MAP Throwable null");
			for (int i = 0; i < mapTile.length; i++) {
				for (int j = 0; j < mapTile[i].length; j++) {
					mapTile[i][j].right = false;
					mapTile[i][j].left = false;
					mapTile[i][j].up = false;
					mapTile[i][j].down = false;
					mapTile[i][j].isItemMake = false;
					return null;
				}
			}
			e.printStackTrace();
		}*/
		int start = 0;
		int end = 0;
		Vector temp = new Vector();
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
				if (line2[i][j][0].equals("0")) {
					mapTile[i][j].up = true;
				} else {
					mapTile[i][j].up = false;
				}
				if (line2[i][j][1].equals("0")) {
					mapTile[i][j].left = true;
				} else {
					mapTile[i][j].left = false;
				}
				if (line2[i][j][2].equals("0")) {
					mapTile[i][j].right = true;
				} else {
					mapTile[i][j].right = false;
				}
				if (line2[i][j][3].equals("0")) {
					mapTile[i][j].down = true;
				} else {
					mapTile[i][j].down = false;
				}
				if (line2[i][j][4].equals("0")) {
					mapTile[i][j].isItemMake = true;
				} else {
					mapTile[i][j].isItemMake = false;
				}

			}
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
//		System.out.println("Map load Finish");
		return mapTile;
	}
}
