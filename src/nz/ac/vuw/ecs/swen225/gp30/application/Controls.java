package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.Replay;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controls extends KeyAdapter {

    private boolean recordMode = false;
    private ChapsChallenge game;
    private Replay rep;

    public Controls(ChapsChallenge game){
        this.game = game;
    }

    /**
     * Method for is a player presses a key that has a function
     * in the game. If a player is pressing control is listened
     * for to get the correct key press expected.
     *
     * @param e - the key the player has pressed.
     */
    public void keyPressed(KeyEvent e){

        //If in not in record mode.
        if(!recordMode) {
            if (!e.isControlDown()) {
                switch (e.getKeyCode()) {

                    //Movement Keys
                    case 37:
                    case 65:   //LEFT, 'A' & 'Arrow left'
                        System.out.println("You have pressed: A");
                        game.move(Move.LEFT);
                        //Move LEFT
                        break;
                    case 39:
                    case 68:   //RIGHT, 'D' & 'Arrow right'
                        System.out.println("You have pressed: D");
                        game.move(Move.RIGHT);
                        //Move RIGHT
                        break;
                    case 38:
                    case 87:   //UP, 'W' & 'Arrow up'
                        System.out.println("You have pressed: W");
                        game.move(Move.UP);
                        //Move UP
                        break;
                    case 40:
                    case 83:   //DOWN, 'S' & 'Arrow down'
                        System.out.println("You have pressed: S");
                        game.move(Move.DOWN);
                        //Move DOWN
                        break;
                    case 32:
                        System.out.println("You have pressed: SPACE BAR");
                        game.pause();
                        //Space - pause the game and display a "game is paused" dialog.
                        break;
                    case 27:
                        System.out.println("You have pressed: ESCAPE");
                        game.resume();
                        //Escape - close the "game is paused" dialog and resume the game.
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
                        System.out.println("You have pressed: CTRL-P");
                        //CTRL-P - start a new game at the last unfinished level.
                        break;
                    case 49:
                        System.out.println("You have pressed: CTRL-1");
                        //CTRL-1 - start a new game at level one.
                        break;
                }
            }
        }
        else{
            switch(e.getKeyCode()) {
                case 37: //LEFT, '<-' step backwards.
                    rep.getPreviousMove();
                    System.out.println("You are in record mode, step back.");
                    break;
                case 39: //RIGHT, '->' step forwards.
                    rep.playNextMove();
                    System.out.println("You are in record mode, step forward.");
                    break;
                case 38: //Up arrow, increase the speed.
                    rep.decreaseDelay();
                    System.out.println("You are in record mode, speed up.");
                    break;
                case 40: //Down arrow, decrease the speed.
                    rep.increaseDelay();
                    System.out.println("You are in record mode, slow down.");
                    break;
                case 32: //Pause and Resume the auto replay.
                    //Will need a boolean for a toggle.
                    System.out.println("You have paused/resumed");
                    break;
            }
        }
    }
}
