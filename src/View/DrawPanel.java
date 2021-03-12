package View;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Main;
import Model.Choices;

public class DrawPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean showValid;

	public DrawPanel() {
		showValid = true;
		//it is used for blinking the valid moves of the user
		addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				// to change the scale of the components to our set
				// scale
				x = x / 50 - 1;
				y = y / 50 - 1;
				// it transfers the components to the controller
				if (Main.usersTurn) {
					//if it is the users turn, then the valid moves blink
					Main.showValid.stop();
					//to stops the time of blinking
					Main.set(x, y);
					//to set the board and also the game with the given components
					Main.showValid.start();
					//to start the time of blinking
				}

			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(Main.usersTurn)
			showValid = !showValid;
		this.removeAll();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Color color1 = new Color(64, 209, 173);
		Color color2 = color1.darker();
		color1 = Color.lightGray.brighter();
		int w = getWidth();
		int h = getHeight();
		//to get width and height of the panel
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
		g2d.setPaint(gp);		
		g2d.fillRect(0, 0, w, h);
		
		g.setColor(new Color(88, 57, 12));

		g.fill3DRect(43, 43, 413, 413, true);
		g.setColor(new Color(50, 175, 75));
		
		g.fillRect(50, 50, 400, 400);
		g.setColor(new Color(88, 57, 12));

	
		
		for (int i = 2; i < 9; ++i)
			g.drawLine(i * 50, 50, i * 50, 450);

		for (int j = 2; j < 9; ++j)
			g.drawLine(50, j * 50, 450, j * 50);
		//the board of the game is made up of lines

		// every time the whole 2D array is drawn
		for (int i = 0; i < 8; ++i)
			for (int j = 0; j < 8; ++j) {
				if (Main.getCell(i, j) == 1) {
					//if 1(black) draws a circle(oval) in the colour of black with the specified scale
					g.setColor(Color.black);
					g.fillOval((i + 1) * 50 + 10, (j + 1) * 50 + 10, 30, 30);
				}
				if (Main.getCell(i, j) == 2) {
					//if 1(black) draws a circle(oval) in the colour of black with the specified scale
					g.setColor(Color.white);
					g.fillOval((i + 1) * 50 + 10, (j + 1) * 50 + 10, 30, 30);
				}
			}
		if (Main.usersTurn && showValid)
			for (Choices ch : Main.getValidMoves()) {
				//it shows the valid moves of the user in his turn
				g.setColor(new Color(150,186,189));
				g.fillOval((ch.x + 1) * 50 + 17, (ch.y + 1) * 50 + 17, 16, 16);
			}
		Main.showBoard.frame.setTitle(Main.usersTurn ? "You should play"
				: "Computer playing");
		//announces the turn of the players
		JLabel whites = new JLabel("Whites: " + Main.getScore(2));
		JLabel blacks = new JLabel("Blacks: " + Main.getScore(1));
		//shows the score of the players in each stage of the game
		whites.setBounds(50, 25, 120, 15);
		blacks.setBounds(395, 25, 120, 15);
		this.add(whites);
		this.add(blacks);
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new game 
				Main.showValid.stop();
				Main.showBoard.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Main.showBoard.frame.dispose();
				Main.main(null);
			}
		});
		reset.setBounds(140, 10, 100, 30);
		this.add(reset);
		
		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			//undo the last player's game
			public void actionPerformed(ActionEvent e) {
				Main.undo();
			}
		});
		if(Main.allBoards.size()==0)
			undo.setEnabled(false);
		undo.setBounds(260, 10, 100, 30);
		this.add(undo);
	}
}
