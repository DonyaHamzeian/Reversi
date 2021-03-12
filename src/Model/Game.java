package Model;

import java.util.ArrayList;

import Controller.Main;

public class Game implements Cloneable{

	private Board brd;
	Choices choice = new Choices();
	public ArrayList<Choices> choices = new ArrayList<Choices>();

	public Game() {
		brd = new Board();

	}

	public int getCell(int x, int y) {
		//to give the value of each cell to the Main class
		return brd.getCell(x, y);
	}

	public void setBoard(Board board) {
		//calls this method in board class
		brd.setBoard(board);
	}

	public Board getBoard() {
		//gets the board from board class
		return brd.clone();
	}

	public int maxScore(int k, int ans) {
		//backtrack to 1 to 7 stages (optional)
		int score = -1;
		if (isFinish() && winner() == ans) {
			score = brd.inf;
			//being the winner is the best score!
			return score;
		}
		if (k % 2 == 0)
			choosing(ans);
		//in the even stages the arraylist is filled with the computer's choices 
		if (k % 2 == 1)
			choosing(ans % 2 + 1);
		//in the even stages the arraylist is filled with the user's choices 


		if (k == 5 || choices.size() == 0) {
			score = score(ans);
			//if  it is  the final stage or there is no other choice, the score should be returned
			return score;
		}

		if (isFinish() && winner() == (ans % 2 + 1)){
			//for the opponent being the winner, the score is minimum(0)
			return 0;
		}

		for (int i = 0; i < choices.size(); i++) {

			Game g = new Game();
			Board b = new Board();
			b.setBoard(brd);
			//to set the temp board 
			insert(choices.get(i).x, choices.get(i).y, choices.get(i).ans);

			g.setBoard(b);
			if (k < 5) {

				if (k % 2 == 0 && (g.maxScore(k + 1, ans) >= score))
					score = g.maxScore(k + 1, ans);
				//if it is the computers turn, the  score should be max
				if ((k % 2 == 1 && (g.maxScore(k + 1, ans) <= score) || score == -1))
					score = g.maxScore(k + 1, ans);
				//if it is the users turn the score should be min (the best play of the user)
			}
		}

		return score;
	}

	public int score(int ans) {
		//score is calculated in Board class
		return brd.score(ans);
	}

	public Choices bestChoice(int k, int ans) {
		//this method is the same as maxScore but it returns the best choice 
		if (k % 2 == 0)
			choosing(ans);
		if (k % 2 == 1)
			choosing(ans % 2 + 1);
		int score = -1;
		Choices ch = new Choices();

		for (int i = 0; i < choices.size(); i++) {

			Game g = new Game();
			Board b = new Board();
			insert(choices.get(i).x, choices.get(i).y, choices.get(i).ans);
			g.setBoard(b);
			if (k < 5) {
				if (k % 2 == 0 && (g.maxScore(k + 1, ans) >= score)) {
					score = g.maxScore(k + 1, ans);
					ch.x = choices.get(i).x;
					ch.y = choices.get(i).y;

				}
				if ((k % 2 == 1 && (g.maxScore(k + 1, ans) <= score))
						|| score == -1) {

					score = g.maxScore(k + 1, ans);
					ch.x = choices.get(i).x;
					ch.y = choices.get(i).y;

				}

			}
		}
		return ch;
	}

	public void computerMove() {
		Choices ch = new Choices();
		ch = bestChoice(0, 3 - Main.ans);
		//to get the best choice
		insert(ch.x, ch.y, 3 - Main.ans);
		//to insert the best given choice
	}

 	public void choosing(int ans) {
 		
		choices.clear();
		boolean cmp = false;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i == 0 && j == 0) || (i == 7 && j == 7)
						|| (i == 0 && j == 7) || (i == 7 && j == 0)) {
					if (validMove(i, j, ans)) {
						choice.x = i;
						choice.y = j;
						choice.ans = ans;
						choices.add(choice);
						cmp = true;

					}
				}
			}
		}
		//the corners are the best choices, so if the computer has any corner, it fills the arraylist with them and decides only between the corners 
		if (cmp)
			return;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i == 0 && (j != 1 || j != 6))
						|| (i == 7 && (j != 1 || j != 6))
						|| (j == 0 && (i != 1 || i != 6))
						|| (j == 7 && (i != 1 || i != 6))) {
					if (validMove(i, j, ans)) {
						choice.x = i;
						choice.y = j;
						choice.ans = ans;
						choices.add(choice);
						cmp = true;
					}
				}
			}
		}
		//after corners, sides are the best choices but not the sides closest to the corners
		if (cmp)
			return;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (validMove(i, j, ans)) {

					choice.x = i;
					choice.y = j;
					choice.ans = ans;
					choices.add(choice);
				}

			}
		}
		//computer fills the arrayList with the available choices

	}

	public boolean validMove(int x, int y, int ans) {
		if (brd.getCell(x, y) != 0)
			return false;
		//it is only possible to insert a disc into an empty room
		for (int kx = -1; kx <= 1; kx++) {
			for (int ky = -1; ky <= 1; ky++) {
				if (kx == 0 && ky == 0)
					continue;
				if (isValidDirection(x, y, kx, ky, ans))
					return true;
			}
		}
		return false;
	}

	public void insert(int x, int y, int ans) {
		brd.setCell(x, y, ans);
		for (int kx = -1; kx <= 1; kx++) {
			for (int ky = -1; ky <= 1; ky++) {
				if (kx == 0 && ky == 0)
					continue;
				if (isValidDirection(x, y, kx, ky, ans)) {
					int zx = x + kx;
					int zy = y + ky;
					while (brd.getCell(zx, zy) == ans % 2 + 1) {
						brd.setCell(zx, zy, ans);

						zx += kx;
						zy += ky;
					}
				}
			}
		}

	}

	public boolean isValidDirection(int x, int y, int kx, int ky, int ans) {
		
		int zx = x + kx, zy = y + ky;
		if (!(brd.isInBoard(zx, zy) && brd.getCell(zx, zy) == ans % 2 + 1))
			return false;
		while (brd.isInBoard(zx, zy)) {
			if (brd.getCell(zx, zy) == ans)
				return true;
			if (brd.getCell(zx, zy) == 0)
				return false;
			zx += kx;
			zy += ky;
		}
		return false;
	}

	public boolean hasValidMove(int ans) {
		//if the arrayList of choices is empty, it means there is no valid move for the player(computer or the user)
		choosing(ans);
		if (choices.size() != 0)
			return true;

		return false;
	}

	public boolean isFinish() {
		// if none of the players has no valid move, the game is finish
		if (hasValidMove(1) || hasValidMove(2))
			return false;

		return true;
	}

	public int winner() {
		//calculates the black and white scores to decide the winner
		int white = 0, black = 0;
		for (int i = 0; i < 8; i++)

		{
			for (int j = 0; j < 8; j++) {
				if (brd.getCell(i, j) == 1)
					black++;
				if (brd.getCell(i, j) == 2)
					white++;
			}
		}
		if (white > black)
			return 2;
		if (black > white)
			return 1;
		return 0;
	}

}