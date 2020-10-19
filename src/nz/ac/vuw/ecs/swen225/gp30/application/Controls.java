package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.Replay;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.*;
import java.io.File;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Controls extends KeyAdapter implements ActionListener {

    private boolean recordMode = false;
    private ChapsChallenge game;
    private Replay replay;

    public Controls(ChapsChallenge game) {
        this.game = game;
        replay = new Replay();
    }

    /**
     * Method for is a player presses a key that has a function
     * in the game. If a player is pressing control is listened
     * for to get the correct key press expected.
     *
     * @param e - the key the player has pressed.
     */
    public void keyPressed(KeyEvent e) {
        if (!recordMode) {
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
        } else {
            switch (e.getKeyCode()) {
                case 37: //LEFT, '<-'
                    System.out.println("You are in record mode, step back.");
                    break;
                case 39: //RIGHT, '->' step forwards.
                    game.move(replay.playNextMove());
                    System.out.println("You are in record mode, step forward.");
                    break;
                case 38: //Up arrow, increase the speed.
                    replay.decreaseDelay();
                    System.out.println("You are in record mode, speed up.");
                    break;
                case 40: //Down arrow, decrease the speed.
                    replay.increaseDelay();
                    System.out.println("You are in record mode, slow down.");
                    break;
                case 32: //Space Bar, Pause and Resume the auto replay.
                    //Will need a boolean for a toggle. Iterate between step-by-step and auto replay.
                    replay.toggleAutoPlaying();
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
                game.loadLevel();
                System.out.println("You are in the One\n");
                break;
            case "Two":
                //Invoke two method.
                break;
            case "Three":
                //Invoke three method.
                break;
            //Replay Menu Items.
            case "Load File":
                //Load a file for Record and Replay.
                recordMode = true;
                loadRecordAndReplayFile();
                System.out.println("You are in load file\n");
                break;
            case "0.5x speed":
                //Set speed to 0.5.
                game.setTimerDelay(2000);
                System.out.println("You are in 0.5x speed");
            case "1.0x speed":
                //Set speed to 1.0.
                game.setTimerDelay(1000);
                System.out.println("You are in 1.0x speed");
            case "1.5x speed":
                //Set speed to 1.5.
                game.setTimerDelay(750);
                System.out.println("You are in 1.5x speed");
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

    /**
     * Method to load and pass a file for Record and Replay to use to show
     * a previously recorded game.
     */
    public void loadRecordAndReplayFile(){
        //Open the file chooser directory to get file name for Record and Replay.
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int fileReturnValue = fileChooser.showOpenDialog(null);
        if(fileReturnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            replay.loadJsonToReplay(selectedFile.getName());
            System.out.println(selectedFile.getName());
        }
        replay.autoPlay(game.getTimeLeft());
    }
}
