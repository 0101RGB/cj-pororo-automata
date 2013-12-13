package pororo.com.game.puzzle.object;

public class TileData {
	// 퍼즐조각정보 4개까지 저장할수 잇음 
	public int[] piece = new int[4];
	public int[] posX = new int[4];
	public int[] posY = new int[4];

	public TileData() {
		for (int i = 0; i < piece.length; i++) {
			piece[i] = -1;
			posX[i] = -1;
			posY[i] = -1;
		}
	}
}
