package nz.ac.vuw.ecs.swen225.gp30.application;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.persistence.writeFile;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.*;
import nz.ac.vuw.ecs.swen225.gp30.render.GameVisuals;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChapsChallenge {
    enum GameState {
        RUNNING,
        PAUSED,
        INFO,
        WON,
        DEAD,
        TIMEOUT
    }

    /* Timing components for the game */
    private final int TOTAL_TIME = 100;
    private int timeLeft;
    private final int TIMER_DELAY = 1000;
    private Timer timer;

    private GameState state = GameState.RUNNING;
    private GameState prevState = GameState.RUNNING;
    private GameWorld game;
    private GameVisuals renderer;
    private GUI gui;
    WriteJSON wj;

    public ChapsChallenge() {
        gui = new GUI();
        timeLeft = TOTAL_TIME;
        loadLevel();
        gui.setGamePanel(renderer);
        gui.addKeyListener(new Controls(this));
        gui.init();
        wj = new WriteJSON();
        startGame();
        timer = new Timer(TIMER_DELAY, gameTimer);
        timer.start();
    }

    ActionListener gameTimer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(timeLeft == 0) {
                state = GameState.TIMEOUT;
                timer.stop();
            }
            gui.setTimeLeft(timeLeft--);
        }
    };

    /**
     * Method to move Chap about the maze.
     * @param move - direction.
     */
    public void move(Move move) {
        if(game.moveChap(move)) {
            //Record.recordPlayerAction(); //FIXME
            wj.storePlayerMove(move, 0); //FIXME need to have the time variable from game loop.
        };
    }

    //Call when exiting the game.

    public void saveReplay() {
        wj.writeJsonToFile();
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
                    long start = System.currentTimeMillis();
                    switch(state) {
                        case PAUSED:
                            //Create a JOptionPane. Stop time countdown.
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
                            System.out.println("Game: WON");
                            wj.writeJsonToFile();
                            break;
                        case DEAD:
                            // show dead prompt?
                            // restart level
                            //game.loadLevel()
                            System.out.println("Game: DEAD");
                            wj.writeJsonToFile();
                            break;
                    }
                    checkGameState();

                    try {
                        Thread.sleep(start + (long)1000/30 - System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
