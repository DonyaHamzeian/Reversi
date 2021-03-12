package View;

import javax.swing.JOptionPane;

public class ShowError
{
	public static void showError(int e){
		//error is given from the main
		if(e==1) //the user can only accept the dialog
			JOptionPane.showMessageDialog(null, "No Valid Move for you, computer played", "Message", JOptionPane.CLOSED_OPTION);
		else
			JOptionPane.showMessageDialog(null, "No Valid move for computer, you move", "Message", JOptionPane.CLOSED_OPTION);
	}
}
