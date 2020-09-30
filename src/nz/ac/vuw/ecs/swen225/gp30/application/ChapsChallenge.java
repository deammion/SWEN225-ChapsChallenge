package nz.ac.vuw.ecs.swen225.gp30.application;
<<<<<<< HEAD
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Move;
import nz.ac.vuw.ecs.swen225.gp30.persistence.writeFile;
=======

>>>>>>> 84fd204695846348da6555df5420fc7bab015165
import nz.ac.vuw.ecs.swen225.gp30.render.GameVisuals;

import javax.swing.*;

public class ChapsChallenge extends GUI {
    enum GameState {
        RUNNING,
        PAUSED,
        INFO,
        WON,
        DEAD
    }

    private GameState state = GameState.RUNNING;
    private GameState prevState = GameState.RUNNING;
    private GameWorld game;
    private GameVisuals renderer;

    public ChapsChallenge() {
        loadLevel();
        setGamePanel(renderer);
        init();
        startGame();
    }

    /**
     * Method to move Chap about the maze.
     * @param move - direction.
     */
    public void move(Move move) {
        System.out.println(move.toString());
        if(game.moveChap(move)) {
            // Recnreplay(move)
        };
        System.out.println("x: " + game.getChap().getX() + " y: " + game.getChap().getY());
    }

    /**
     * Method which will pause the game.
     */
    public void pause() {
        prevState = state;
        state = GameState.PAUSED;
    }

    /**
     * Method which will resume the game.
     */
    public void resume() { state = prevState; }

    /**
     * Method will load a level for the game.
     */
    public void loadLevel() {
        game = writeFile.readLevel();
        renderer = new GameVisuals(game);
    }

    /**
     * Method is responsible for the starting of the game, keeps the game
     * in states to keep it running.
     */
    public void startGame() {
        Runnable runnableGame = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    switch(state) {
                        case PAUSED:
                            break;
                        case INFO:
                            displayInfo();
                        case RUNNING:
                            // update
                            // render
                            renderer.repaint();
                            break;
                        case WON:
                            // game.loadLevel(xx)
                            // winning logic
                            // next level
                            break;
                        case DEAD:
                            // show dead prompt?
                            // restart level
                            //game.loadLevel()
                            break;
                    }
                    checkGameState();
                }
            }
        };
        new Thread(runnableGame).start();
    }

    /**
     * Check which state the game is currently in. This can either
     * be INFO, WON or DEAD.
     */
    public void checkGameState() {
        if (game.isChapOnInfo()) {
            state = GameState.INFO;
        }
        if (game.isChapOnExit()) {
            state = GameState.WON;
        }
        if (!game.isChapActive()) {
            state = GameState.DEAD;
        }
    }

    /**
     * Method displays the information related to the game level.
     */
    public void displayInfo() {
        //JOptionPane.showMessageDialog(this, game.getLevelInfo());
    }

    /**
     * Main method, begins the game.
     * @param args - arguments parsed.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new ChapsChallenge();
                    }
                }
        );
    }
}
