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
                        System.out.println("You have pressed: CTRL-X");
                        //CTRL-X - exit the game, current game state will be lost, next time game is started will resume from the last unfinished level.
                        break;
                    case 83:
                        System.out.println("You have pressed: CTRL-S");
                        //CTRL-S - exit the game, saves the game state, game will resume next time the application is started
                        break;
                    case 82:
                        System.out.println("You have pressed: CTRL-R");
                        //CTRL-R - resume a saved game.
                        break;
                    case 80:
                        System.out.println("You are in level:" + game.getGameLevel());
                        game.loadLevel(game.getGameLevel());
                        System.out.println("You have pressed: CTRL-P");
                        //CTRL-P - start a new game at the last unfinished level.
                        break;
                    case 49:
                        //CTRL-1 - start a new game at level one.
                        game.loadLevel(1);
                        System.out.println("You have pressed: CTRL-1");
                        break;
                }
            }
        } else {
            switch (e.getKeyCode()) {
                case 37: //LEFT, '<-'
                    System.out.println("You are in record mode, step back.");
                    break;
                case 39: //RIGHT, '->' step forwards.
                    game.replayNextMove();
                    System.out.println("You are in record mode, step forward.");
                    break;
                case 38: //Up arrow, increase the speed.
                    game.decreaseTimerDelay();
                    System.out.println("You are in record mode, speed up.");
                    break;
                case 40: //Down arrow, decrease the speed.
                    game.increaseTimerDelay();
                    System.out.println("You are in record mode, slow down.");
                    break;
                case 32: //Space Bar, Pause and Resume the auto replay.
                    //Will need a boolean for a toggle. Iterate between step-by-step and auto replay.
                    game.togglePlay();
                    game.pausedAndRunning();
                    System.out.println("You have paused/resumed");
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
                System.out.println("You are in the Pause\n");
                break;
            case "Resume":
                //Invoke resume method.
                game.resume();
                System.out.println("You are in the Resume\n");
                break;
            case "Exit":
                //Invoke exit method.
                System.exit(0);
                break;
            //Options Menu Items.
            case "Save":
                //Invoke save method.
                System.out.println("You are in the Save\n");
                break;
            case "Load":
                //Invoke load method.
                break;
            //Level Menu Items.
            case "One":
                //Invoke one method.
                game.loadLevel(1);
                System.out.println("You are in the level One\n");
                break;
            case "Two":
                //Invoke two method.
                game.loadLevel(2);
                game.setGameLevel(2);
                System.out.println("You are in level Two\n");
                break;
            //Replay Menu Items.
            case "Load File":
                //Load a file for Record and Replay.
                game.playReplay();
                System.out.println("You are in load file\n");
                break;
            case "0.25x speed":
                //Set speed to 0.5.
                game.setTimerDelay(4000);
                System.out.println("You are in 0.25x speed");
            case "0.5x speed":
                //Set speed to 1.0.
                game.setTimerDelay(2000);
                System.out.println("You are in 0.5x speed");
            case "1.0x speed":
                //Set speed to 1.5.
                game.setTimerDelay(1000);
                System.out.println("You are in 1.0x speed");
            case "2.0x speed":
                //Set speed to 2.0.
                game.setTimerDelay(500);
                System.out.println("You are in 2.0x speed");
                //Help Menu Items.
            case "Help":
                //Invoke help method (dialog box).
                System.out.println("You are in the Help");

        }
    }
}
