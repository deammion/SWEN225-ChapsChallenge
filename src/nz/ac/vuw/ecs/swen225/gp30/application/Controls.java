package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.Move;

import java.awt.event.*;

/**
 * @author jakeh
 */
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
        if (!game.recordMode) {
            if (!e.isControlDown()) {
                switch (e.getKeyCode()) {
                    //Movement Keys
                    case 37:
                    case 65:   //LEFT, 'A' & 'Arrow left'
                        game.move(Move.LEFT);
                        break;
                    case 39:
                    case 68:   //RIGHT, 'D' & 'Arrow right'
                        game.move(Move.RIGHT);
                        break;
                    case 38:
                    case 87:   //UP, 'W' & 'Arrow up'
                        game.move(Move.UP);
                        break;
                    case 40:
                    case 83:   //DOWN, 'S' & 'Arrow down'
                        game.move(Move.DOWN);
                        break;
                    case 32:    //Space Bar - Pause Game
                        game.pause();
                        break;
                    case 27:    //Escape - Resume the Game
                        game.resume();
                        break;
                }
            } else {
                switch (e.getKeyCode()) {
                    case 88: //CTRL-X - exit the game, current game state will be lost, next time game.
                        System.out.println("You have pressed: CTRL-X");
                        break;
                    case 83:  //CTRL-S - exit the game, saves the game state, game will resume next time app started.
                        System.out.println("You have pressed: CTRL-S");
                        break;
                    case 82: //CTRL-R - resume a saved game.
                        System.out.println("You have pressed: CTRL-R");
                        break;
                    case 80: //CTRL-P - start a new game at the last unfinished level.
                        System.out.println("You have pressed: CTRL-P");
                        break;
                    case 49: //CTRL-1 - start a new game at level one.
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
            case "Pause":
                game.pause();
                System.out.println("You are in the Pause\n");
                break;
            case "Resume":
                game.resume();
                System.out.println("You are in the Resume\n");
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Save":
                //Invoke save method.
                System.out.println("You are in the Save\n");
                break;
            case "Load":
                //Invoke load method.
                System.out.println("You are in the Load\n");
                break;
            case "One":
                game.loadLevel(1);
                System.out.println("You are in the level One\n");
                break;
            case "Two":
                game.loadLevel(2);
                System.out.println("You are in level Two\n");
                break;
            case "Load File":
                game.playReplay();
                System.out.println("You are in load file\n");
                break;
            case "0.25x speed":
                game.setTimerDelay(4000);
                System.out.println("You are in 0.25x speed");
                break;
            case "0.5x speed":
                game.setTimerDelay(2000);
                System.out.println("You are in 0.5x speed");
                break;
            case "1.0x speed":
                game.setTimerDelay(1000);
                System.out.println("You are in 1.0x speed");
                break;
            case "2.0x speed":
                game.setTimerDelay(500);
                System.out.println("You are in 2.0x speed");
                break;
            case "Help":
                System.out.println("You are in the Help");
                break;

        }
    }
}
