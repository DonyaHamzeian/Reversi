package Controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

import Model.Board;
import Model.Choices;
import Model.Game;
import View.FirstShow;
import View.Show;
import View.ShowError;
import View.ShowWinner;

public class Main {
	// makes the relation between Model(the logic of the game) and the View
	// (graphic of the game)
	private static Game game;
	public static Show showBoard;
	public static ArrayList<Board> allBoards;
	// to save the boards to use in "undo" method
	public static String winner;
	public static FirstShow firstshow;
	public static int ans;
	public static boolean usersTurn;
	public static Clip clickSound;
	public static Clip errorSound;
	public static Timer showValid;

	public static int getCell(int x, int y) {
		return game.getCell(x, y);
	}

	public static ArrayList<Choices> getValidMoves() {
		// gets the valid moves from the game to show them to the user
		ArrayList<Choices> ret = new ArrayList<Choices>();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (game.validMove(i, j, ans))
					ret.add(new Choices(i, j));
		return ret;
	}

	public static void set(int x, int y) {
		if (game.hasValidMove(Main.ans)) {
			if (game.validMove(x, y, Main.ans)) {
				allBoards.add(game.getBoard());
				clickSound.setFramePosition(0);
				clickSound.start();
				// if the user choose the right place (valid move) the
				// clickSound plays
				game.insert(x, y, Main.ans);
				showBoard.panel.removeAll();
				showBoard.frame.repaint();
				if (game.isFinish()) {
					ShowWinner.showWinner(game.winner());
				}
			} else {
				if (usersTurn && x >= 0 && x <= 7 && y >= 0 && y <= 7) {
					// if the user chooses the wrong place (only in the board)
					// the errorSound plays and the program waits for the next
					// given place
					errorSound.setFramePosition(0);
					errorSound.start();
					// plays the sound from the beginning
				}
				return;
			}

		} else if (!game.isFinish()) {
			// user has no valid move, computer should play
			ShowError.showError(1);

		}

		if (game.hasValidMove(3 - Main.ans)) {
			usersTurn = false;
			Timer t = new Timer(1000, new ActionListener() {
				// it takes 1000 milliseconds to show the changes that
				// computer's play has developed
				public void actionPerformed(ActionEvent e) {
					showBoard.frame.setCursor(Cursor.getDefaultCursor());
					// in this time the cursor is changed to the rotating cursor
					game.computerMove();
					showBoard.frame.repaint();
					if (game.isFinish())
						ShowWinner.showWinner(game.winner());
					set(-1, -1);
					usersTurn = true;
				}
			});
			showBoard.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			t.setRepeats(false);
			t.start();
			// start is the action
		} else if (!game.isFinish()) {
			// computer has no valid move, user should play
			ShowError.showError(2);
		}
	}

	public static void undo() {
		game.setBoard(allBoards.get(allBoards.size() - 1));
		showBoard.frame.repaint();
		allBoards.remove(allBoards.size() - 1);

	}

	public static int getScore(int ans) {
		return game.score(ans);
	}

	public static void main(String[] args) {
		allBoards = new ArrayList<Board>();
		try {
			errorSound = AudioSystem.getClip();
			AudioInputStream inputStream1 = AudioSystem
					.getAudioInputStream(Main.class
							.getResourceAsStream("/Sounds/error.wav"));
			clickSound = AudioSystem.getClip();
			AudioInputStream inputStream2 = AudioSystem
					.getAudioInputStream(Main.class
							.getResourceAsStream("/Sounds/click.wav"));
			errorSound.open(inputStream1);
			clickSound.open(inputStream2);
		} catch (Exception e) {
		}

		ans = -1;
		firstshow = new FirstShow();
		if (ans != -1) {
			// the user has chosen the colour
			game = new Game();
			if (ans == 2)
				game.computerMove();
			usersTurn = true;
			showBoard = new Show();
			showValid = new Timer(500, new ActionListener() {
				// every 500 milliseconds the whole frame repaints, and in each
				// repainting, the value of showValid changes to blink
				public void actionPerformed(ActionEvent e) {
					showBoard.frame.repaint();
				}
			});
			showValid.start();
			showBoard.frame.repaint();
		}
	}

}