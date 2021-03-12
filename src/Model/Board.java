package Model;

public class Board {
	// 1 black 2 white

	private int[][] board;
	public final int inf = 1000;

	public Board() {
		board = new int[8][8];

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				board[i][j] = 0;

		board[4][4] = 2;
		board[3][3] = 2;
		board[3][4] = 1;
		board[4][3] = 1;
		// these 4 places are the default property of reversi
	}

	public void setBoard(Board b) {
		// to set the board(it is used in back track)
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				board[i][j] = b.getCell(i, j);
	}

	public int getCell(int x, int y) {
		// to give the value of each cell to the game
		if (isInBoard(x, y))
			return board[x][y];
		return -1;
	}

	public void setCell(int x, int y, int ans) {
		// set the value of the cell with the given ans value
		// it is called from the main
		board[x][y] = ans;
	}

	public int score(int ans) {
		// calculates the score of the players
		int score = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (board[i][j] == ans)
					score++;
		return score;
	}

	public boolean isInBoard(int x, int y) { // just in case!
		if (x >= 0 && x < 8 && y >= 0 && y < 8)
			return true;
		return false;
	}

	protected Board clone(){
		Board ret=new Board();
		for(int i = 0; i<8; i++)
			for(int j = 0; j<8; j++ )
				ret.board[i][j]=this.board[i][j];
		return ret;
	}
	
}
