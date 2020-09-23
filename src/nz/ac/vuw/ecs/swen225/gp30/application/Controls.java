package nz.ac.vuw.ecs.swen225.gp30.application;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controls extends KeyAdapter {

    /**
     * Method for is a player presses a key that has a function
     * in the game. If a player is pressing control is listened
     * for to get the correct key press expected.
     *
     * @param e - the key the player has pressed.
     */

    public void keyPressed(KeyEvent e){

        if(!e.isControlDown()) {
            switch (e.getKeyCode()) {

                //Movement Keys
                case 37: case 65:   //LEFT, 'A' & 'Arrow left'
                    System.out.println("You have pressed: A");
                    //Move LEFT
                    break;
                case 39: case 68:   //RIGHT, 'D' & 'Arrow right'
                    System.out.println("You have pressed: D");
                    //Move RIGHT
                    break;
                case 38: case 87:   //UP, 'W' & 'Arrow up'
                    System.out.println("You have pressed: W");
                    //Move UP
                    break;
                case 40: case 83:   //DOWN, 'S' & 'Arrow down'
                    System.out.println("You have pressed: S");
                    //Move DOWN
                    break;
                case 32:
                    System.out.println("You have pressed: SPACE BAR");
                    //Space - pause the game and display a "game is paused" dialog.
                    break;
                case 27:
                    System.out.println("You have pressed: ESCAPE");
                    //Escape - close the "game is paused" dialog and resume the game.
                    break;

            }
        }
        else{
            switch(e.getKeyCode()){
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
}
