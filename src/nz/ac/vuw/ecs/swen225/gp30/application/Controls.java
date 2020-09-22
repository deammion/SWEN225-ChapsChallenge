package nz.ac.vuw.ecs.swen225.gp30.application;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * test driven design - tdd.
 */

public class Controls extends KeyAdapter {

    /**
     * Method for if the player presses a key that has been assigned a
     * purpose in the game. Example: UP/DOWN/LEFT/RIGHT (arrow keys) will
     * move the player about the board.
     *
     * If the control key is being pressed down then the menu shortcuts will
     * be implemented, will implement other team members methods to load etc.
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

    /**
     * If the control key is being pressed down. Need to figure out how to get the
     * control key and the value associated with it for the key bind.
     *
     * @param e - the Action Event.
     */
    public void actionPerformed(ActionEvent e) {

        //If the control key is being pressed.
        if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
            System.out.println("CTRL KEY PRESSED");

            //isControl




        }

    }
}
