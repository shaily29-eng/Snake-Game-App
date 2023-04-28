package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdapter implements ActionListener {

    private Game game;

    public GameAdapter(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
    }
}

