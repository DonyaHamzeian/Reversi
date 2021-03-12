package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import Controller.Main;
// first show is to tell the user the color that he/she should play
public class FirstShow {
public FirstShow(){
	final JDialog frame=new JDialog();
	frame.addWindowListener(new WindowAdapter() {
		// terminates the program if the user refuses to play
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	});
	frame.setTitle("Choose your color");
	//the black always starts the game
	//1 for black
	//2 for white
	frame.setModal(true);
	//the program will not be continued while the user has not chosen 
	frame.setResizable(false);
	//to customise the size
	frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	frame.setSize(300 + 6, 150 + 28);
	frame.setLocationRelativeTo(null);
	JPanel panel = new JPanel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			//to enhance the graphic of the code
			g.setColor(new Color(88, 57, 12));
			int h = getHeight();
			int w = getWidth();
			g.fill3DRect(0, 0, 6, h, true);
			g.fill3DRect(0, 0, w, 6, true);
			g.fill3DRect(w-6, 0, 6, h, true);
			g.fill3DRect(0, h-6, w, 6, true);
		}
	};
	panel.setLayout(null);
	panel.setBackground(new Color(40, 150, 70));
	JButton blackButton = new JButton("Play as Black");
	JButton whiteButton = new JButton("Play as White");
	blackButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Main.ans = 1;
			frame.dispose();
			//it only closes the frame and does not terminates the program
		}
	});
	whiteButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Main.ans = 2;
			frame.dispose();
		}
	});
	blackButton.setBounds(90, 6 + 26, 120, 30);
	whiteButton.setBounds(90, 88, 120, 30);
	whiteButton.setBackground(Color.white);
	blackButton.setBackground(Color.black);
	blackButton.setForeground(Color.white);
	//foreground is for the colour of the font
	panel.add(blackButton);
	panel.add(whiteButton);
	frame.add(panel);
	frame.setVisible(true);
	
}
}
