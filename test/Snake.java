//strategy pattern
package test;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Color;


public class Snake {
	private ArrayList<Rectangle> body;
	private int w = Game.width;
	private int h = Game.height;
	private int d = Game.dimension;

	private int score = 0;
	private Color color;


	public int getScore() {
		return score;
	}

	public void increaseScore() {
		score++;
	}

	private MovementBehavior movementBehavior;

	public int getDimension() {
		return d;
	}

	public Snake() {
		body = new ArrayList<>();

		Rectangle temp = new Rectangle(Game.dimension, Game.dimension);
		temp.setLocation(Game.width / 2 * Game.dimension, Game.height / 2 * Game.dimension);
		body.add(temp);

		temp = new Rectangle(d, d);
		temp.setLocation((w / 2 - 1) * d, (h / 2) * d);
		body.add(temp);

		temp = new Rectangle(d, d);
		temp.setLocation((w / 2 - 2) * d, (h / 2) * d);
		body.add(temp);

		movementBehavior = new NothingMovement();
	}

	public void move() {
		movementBehavior.move(this);
	}

	public void grow() {
		Rectangle first = body.get(0);

		Rectangle temp = new Rectangle(Game.dimension, Game.dimension);

		if (movementBehavior instanceof UpMovement) {
			temp.setLocation(first.x, first.y - Game.dimension);
		} else if (movementBehavior instanceof DownMovement) {
			temp.setLocation(first.x, first.y + Game.dimension);
		} else if (movementBehavior instanceof LeftMovement) {
			temp.setLocation(first.x - Game.dimension, first.y);
		} else if (movementBehavior instanceof RightMovement) {
			temp.setLocation(first.x + Game.dimension, first.y);
		}

		body.add(0, temp);
	}

	public ArrayList<Rectangle> getBody() {
		return body;
	}

	public void setBody(ArrayList<Rectangle> body) {
		this.body = body;
	}

	public int getX() {
		return body.get(0).x;
	}

	public int getY() {
		return body.get(0).y;
	}

	public MovementBehavior getMovementBehavior() {
		return movementBehavior;
	}

	public void setMovementBehavior(MovementBehavior movementBehavior) {
		this.movementBehavior = movementBehavior;
	}

	public void up() {
		setMovementBehavior(new UpMovement());
	}

	public void down() {
		setMovementBehavior(new DownMovement());
	}

	public void left() {
		setMovementBehavior(new LeftMovement());
	}

	public void right() {
		setMovementBehavior(new RightMovement());
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public Rectangle getMove() {
		Rectangle first = body.get(0);
		Rectangle newHead = new Rectangle(first.x, first.y, d, d);
		movementBehavior.move(this);
		if (movementBehavior instanceof UpMovement) {
			newHead.setLocation(first.x, first.y - d);
		} else if (movementBehavior instanceof DownMovement) {
			newHead.setLocation(first.x, first.y + d);
		} else if (movementBehavior instanceof LeftMovement) {
			newHead.setLocation(first.x - d, first.y);
		} else if (movementBehavior instanceof RightMovement) {
			newHead.setLocation(first.x + d, first.y);
		}
		return newHead;
	}
}

interface MovementBehavior {
	void move(Snake snake);
}

class NothingMovement implements MovementBehavior {
	@Override
	public void move(Snake snake) {
		// Do nothing
	}
}

class UpMovement implements MovementBehavior {
	@Override
	public void move(Snake snake) {
		Rectangle first = snake.getBody().get(0);
		snake.getBody().add(0, new Rectangle(first.x, first.y - snake.getDimension(), snake.getDimension(), snake.getDimension()));
		snake.getBody().remove(snake.getBody().size() - 1);
	}
}

class DownMovement implements MovementBehavior {
	@Override
	public void move(Snake snake) {
		Rectangle first = snake.getBody().get(0);
		snake.getBody().add(0, new Rectangle(first.x, first.y + snake.getDimension(), snake.getDimension(), snake.getDimension()));
		snake.getBody().remove(snake.getBody().size() - 1
		);
	}
}

class LeftMovement implements MovementBehavior {
	@Override
	public void move(Snake snake) {
		Rectangle first = snake.getBody().get(0);
		snake.getBody().add(0, new Rectangle(first.x - snake.getDimension(), first.y, snake.getDimension(), snake.getDimension()));
		snake.getBody().remove(snake.getBody().size() - 1);
	}
}

class RightMovement implements MovementBehavior {
	@Override
	public void move(Snake snake) {
		Rectangle first = snake.getBody().get(0);
		snake.getBody().add(0, new Rectangle(first.x + snake.getDimension(), first.y, snake.getDimension(), snake.getDimension()));
		snake.getBody().remove(snake.getBody().size() - 1);
	}
}