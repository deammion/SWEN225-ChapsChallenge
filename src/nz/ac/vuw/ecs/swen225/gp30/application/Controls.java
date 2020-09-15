package nz.ac.vuw.ecs.swen225.gp30.application;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Controls {

    /**
     * Method for if the player presses a key that has been assigned a
     * purpose in the game. Example: UP/DOWN/LEFT/RIGHT (arrow keys) will
     * move the player about the board.
     *
     * @param e - the key the player has pressed.
     */

    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){

            //Movement Keys
            case 37: case 65:   //LEFT, 'A' & 'Arrow left'
                //Move LEFT
                break;
            case 39: case 68:   //RIGHT, 'D' & 'Arrow right'
                //Move RIGHT
                break;
            case 38: case 87:   //UP, 'W' & 'Arrow up'
                //Move UP
                break;
            case 40: case 83:   //DOWN, 'S' & 'Arrow down'
                //Move DOWN
                break;
            case 32:
                //Space - pause the game and display a "game is paused" dialog.
                break;
            case 27:
                //Escape - close the "game is paused" dialog and resume the game.
                break;

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

            switch(e.getActionCommand()){
                //Menu Keys
                case 88:
                    //CTRL-X - exit the game, current game state will be lost, next time game is started will resume from the last unfinished level.
                    break;
                case 83:
                    //CTRL-S - exit the game, saves the game state, game will resume next time the application is started
                    break;
                case 82:
                    //CTRL-R - resume a saved game.
                    break;
                case 80:
                    //CTRL-P - start a new game at the last unfinished level.
                    break;
                case 49:
                    //CTRL-1 - start a new game at level one.
                    break;

            }


        }

    }
}
