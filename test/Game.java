//Implemented using Observer Pattern:
package test;

		import javax.swing.*;
		import java.awt.event.KeyEvent;
		import java.awt.event.KeyListener;
		import java.io.BufferedWriter;
		import java.io.FileWriter;
		import java.io.IOException;
		import java.util.ArrayList;
		import java.util.List;

public class Game implements KeyListener {
	private Snake player;
	private Food food;
	private Graphics graphics;
	private JFrame window;
	private Observer observer;

	public static final int width = 30;
	public static final int height = 30;
	public static final int dimension = 20;

	private List<Integer> scores = new ArrayList<Integer>();

	public Game() {
		window = new JFrame();
		player = new Snake();
		food = FoodFactory.createFood();
		food.randomSpawn(player);
		graphics = new Graphics(this);
		window.add(graphics);
		window.setTitle("Snake");
		window.setSize(width * dimension + 2, height * dimension + dimension + 4);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void start() {
		graphics.state = "RUNNING";
		notifyObserver();
	}


	private void writeScoresToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true));
			for (int i = 0; i < scores.size(); i++) {
				writer.write("player score"  + ":" + scores.get(i)  + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing scores to file: " + e.getMessage());
		}
	}

	public void end() {
		graphics.state = "END";
		scores.add(player.getScore()*2);
		writeScoresToFile();
		notifyObserver();
	}


	public void update() {
		if (graphics.state.equals("RUNNING")) {
			if (checkFoodCollision()) {
				player.grow();
				food.randomSpawn(player);
				notifyObserver();
			} else if (checkWallCollision() || checkSelfCollision()) {
				end(); // call end() when the player collides with wall or itself
				notifyObserver();
			} else {
				player.move();
				notifyObserver();
			}
		}
	}

	private boolean checkWallCollision() {
		if (player.getX() < 0 || player.getX() >= width * dimension || player.getY() < 0
				|| player.getY() >= height * dimension) {
			return true;
		}
		return false;
	}

	private boolean checkFoodCollision() {
		if (player.getX() == food.getX() * dimension && player.getY() == food.getY() * dimension) {
			player.grow();
			player.increaseScore(); // add this line to increase the score
			food.randomSpawn(player);
			notifyObserver();
			return true;
		}
		return false;
	}


	private boolean checkSelfCollision() {
		for (int i = 1; i < player.getBody().size(); i++) {
			if (player.getX() == player.getBody().get(i).x &&
					player.getY() == player.getBody().get(i).y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (graphics.state.equals("RUNNING")) {
			if (keyCode == KeyEvent.VK_W && !player.getMove().equals("DOWN")) {
				player.up();
			}

			if (keyCode == KeyEvent.VK_S && !player.getMove().equals("UP")) {
				player.down();
			}

			if (keyCode == KeyEvent.VK_A && !player.getMove().equals("RIGHT")) {
				player.left();
			}

			if (keyCode == KeyEvent.VK_D && !player.getMove().equals("LEFT")) {
				player.right();
			}
		} else {
			this.start();
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {}

	public Snake getPlayer() {
		return player;
	}

	public void setPlayer(Snake player) {
		this.player = player;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public JFrame getWindow() {
		return window;
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	private void notifyObserver() {
		if (observer != null) {
			observer.update();
		}
	}
}

