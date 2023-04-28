package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Graphics extends JPanel implements ActionListener {
	private Timer t = new Timer(100, this);
	public String state;

	private Snake s;
	private Food f;
	private Game game;

	private JButton highScoreButton;
	private JButton settingsButton;

	public Graphics(Game g) {
		t.start();
		state = "START";

		game = g;
		s = g.getPlayer();
		f = g.getFood();

		// Add a keyListener
		this.addKeyListener(g);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);

		// Create and add high score button
		highScoreButton = new JButton("View Scores");
		highScoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));
					String line = reader.readLine();
					String highScores = "";
					while (line != null) {
						highScores += line + "\n";
						line = reader.readLine();
					}
					reader.close();
					JOptionPane.showMessageDialog(null, "Scores:\n" + highScores);
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Error reading high scores");
				}
			}
		});
		// Set the location and size of the button to center it in the panel
		highScoreButton.setBounds(Game.width * Game.dimension / 2 - 50, Game.height * Game.dimension / 2 - 45, 100, 30);
		this.add(highScoreButton);

		// Create and add game settings button
		settingsButton = new JButton("Game Settings");
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] colorOptions = {"Green", "Blue", "Red"};
				String selectedColor = (String) JOptionPane.showInputDialog(null, "Select a color", "Game Settings", JOptionPane.PLAIN_MESSAGE, null, colorOptions, colorOptions[0]);

				if (selectedColor != null) {
					switch (selectedColor) {
						case "Green":
							s.setColor(Color.green);
							break;
						case "Blue":
							s.setColor(Color.blue);
							break;
						case "Red":
							s.setColor(Color.red);
							break;
						default:
							break;
					}


					// Request focus for the panel so it can receive key events
					Graphics.this.requestFocusInWindow();
				}
			}
		});
		// Set the location and size of the button to center it in the panel
		settingsButton.setBounds(Game.width * Game.dimension / 2 - 50, Game.height * Game.dimension / 2 + 15, 100, 30);
		this.add(settingsButton);
	}

	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, Game.width * Game.dimension + 5, Game.height * Game.dimension + 5);

		if (state == "START") {
			g2d.setColor(Color.white);
			g2d.drawString("Press Any Key", Game.width / 2 * Game.dimension - 40, Game.height / 2 * Game.dimension - 20);
		} else if (state == "RUNNING") {
			g2d.setColor(Color.red);
			g2d.fillRect(f.getX() * Game.dimension, f.getY() * Game.dimension, Game.dimension, Game.dimension);

			g2d.setColor(Color.green);
			for (Rectangle rect : s.getBody()) {
				g2d.setColor(s.getColor());
				g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
			}


			// Remove high score and settings buttons during gameplay
			this.remove(highScoreButton);
			this.remove(settingsButton);
		} else {
			g2d.setColor(Color.white);
			g2d.drawString("Your Score: " + (s.getBody().size() - 3), Game.width / 2 * Game.dimension - 40, Game.height / 2 * Game.dimension - 20);

			// Add high score and settings buttons after game ends
			this.add(highScoreButton);
			this.add(settingsButton);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		game.update();
	}
}
