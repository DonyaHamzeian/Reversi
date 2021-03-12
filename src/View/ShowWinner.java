package View;

import javax.swing.JOptionPane;

import Controller.Main;

public class ShowWinner {

	public static void showWinner(int result) {// show winner announces the winner
									// result is given from main class
		if (result == 0)
			JOptionPane.showMessageDialog(null, "Tie!", "End of game", JOptionPane.CLOSED_OPTION);
		if (result == Main.ans)
			JOptionPane.showMessageDialog(null, "You won! :(", "Congrats", JOptionPane.CLOSED_OPTION);
		if (result == 3-Main.ans)
			JOptionPane.showMessageDialog(null, "You lost >:)", "Sorry", JOptionPane.CLOSED_OPTION);
		int ans = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Message", JOptionPane.YES_NO_OPTION);
		Main.showBoard.frame.dispose();
		if(ans==0) //User has chosen Yes
			Main.main(null);
		else// to terminate the program if the user chooses No
			System.exit(0);
	}
}
