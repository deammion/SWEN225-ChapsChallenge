package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Move;
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
        //game = writeFile.readLevel();
//        Maze m = new Maze(2, 1);
//        m.setTileAt(0,0, new FreeTile(0,0));
//        m.setTileAt(1,0, new InfoTile(1,0));
//        game = new GameWorld(m, new Chap(0,0));
        //renderer = new GameVisuals(game);
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
                            // renderer.paintComponents();
                            checkGameState();
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
        JOptionPane.showMessageDialog(this, game.getLevelInfo());
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
