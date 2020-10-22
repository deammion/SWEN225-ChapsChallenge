package nz.ac.vuw.ecs.swen225.gp30.application;

import java.awt.event.*;

public class Controls extends KeyAdapter implements ActionListener {

    private ChapsChallenge game;

    public Controls(ChapsChallenge game) {
        this.game = game;

    }

    /**
     * Method for is a player presses a key that has a function
     * in the game. If a player is pressing control is listened
     * for to get the correct key press expected.
     *
     * @param e - the key the player has pressed.
     */
    public void keyPressed(KeyEvent e) {
        if (!game.replayMode) {
            if (!e.isControlDown()) {
                switch (e.getKeyCode()) {
                    case 32:    //Space Bar - Pause Game
                        game.pause();
                        break;
                    case 27:    //Escape - Resume the Game
                        game.resume();
                        break;
                }
            } else {
                switch (e.getKeyCode()) {
                    //Menu Keys
                    case 88:
                        game.loadGameStateless();
                        System.exit(0);
                        //CTRL-X - exit the game, current game state will be lost, next time game is started will resume from the last unfinished level.
                        break;
                    case 83:
                        game.loadGameSate();
                        System.exit(0);
                        //CTRL-S - exit the game, saves the game state, game will resume next time the application is started
                        break;
                    case 82:
                        game.resumeGame();
                        //CTRL-R - resume a saved game.
                        break;
                    case 80:
                        game.loadLevel(game.getGameLevel());
                        //CTRL-P - start a new game at the last unfinished level.
                        break;
                    case 49:
                        //CTRL-1 - start a new game at level one.
                        game.loadLevel(1);
                        game.gameLevel = 1;
                        break;
                }
            }
        } else {
            switch (e.getKeyCode()) {
                case 39: //RIGHT, '->' step forwards.
                    game.replayNextMove();
                    break;
                case 38: //Up arrow, increase the speed.
                    game.decreaseTimerDelay();
                    break;
                case 40: //Down arrow, decrease the speed.
                    game.increaseTimerDelay();
                    break;
                case 32: //Space Bar, Pause and Resume the auto replay.
                    game.togglePlay();
                    game.pausedAndRunning();
                    break;
            }
        }
    }

    /**
     * The user of the game clicks on a menu item, this will invoke a method
     * which will Pause, Resume etc.
     *
     * @param actionEvent - event happening.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            //Game Menu Items.
            case "Pause":
                //Invoke pause method.
                game.pause();
                break;
            case "Resume":
                //Invoke resume method.
                game.resume();
                break;
            case "Exit":
                //Invoke exit method.
                System.exit(0);
                break;
            //Options Menu Items.
            case "Save":
                //Invoke save method.
                game.loadGameSate();
                break;
            case "Load":
                //Invoke load method.
                game.resumeGame();
                break;
            //Level Menu Items.
            case "One":
                //Invoke one method.
                game.loadLevel(1);
                break;
            case "Two":
                //Invoke two method.
                game.loadLevel(2);
                game.setGameLevel(2);
                break;
            //Replay Menu Items.
            case "Load File":
                //Load a file for Record and Replay.
                game.playReplay();
                break;
                //Help Menu Items.
            case "Help":
                //Invoke help method (dialog box).
        }
    }
}
