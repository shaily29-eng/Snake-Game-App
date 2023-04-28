//factory pattern:
package test;

import java.awt.Rectangle;

public interface Food {
    void spawn(Snake player);
    int getX();
    int getY();
    void randomSpawn(Snake player);
}

class FoodFactory {
    public static Food createFood() {
        return new NormalFood();
    }
}

class NormalFood implements Food {
    private int x;
    private int y;

    @Override
    public void spawn(Snake player) {
        boolean onSnake = true;
        while(onSnake) {
            onSnake = false;
            x = (int)(Math.random() * Game.width - 1);
            y = (int)(Math.random() * Game.height - 1);

            for(Rectangle r : player.getBody()){
                if(r.x == x && r.y == y) {
                    onSnake = true;
                    break;
                }
            }
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void randomSpawn(Snake player) {
        spawn(player);
    }
}
