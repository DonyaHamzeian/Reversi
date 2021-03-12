package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Show {

	public int getX() {
		return panel.getX();
	}

	public int getY() {
		return panel.getY();
	}

	public DrawPanel panel;
	public JFrame frame;

	public Show() {
		frame = new JFrame("Reversi");
		panel = new DrawPanel();
		frame.add(panel);
		frame.setSize(500 + 6, 500 + 28);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				//to ask the user if he is sure to quit the game
				if (JOptionPane.showConfirmDialog(frame,
						"Are you sure to close this window?",
						"Really Closing?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.dispose();
				}
			}
		});
		frame.setVisible(true);
	}
}
