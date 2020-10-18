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
        WON,
        DEAD,
        TIMEOUT
    }

    /* Timing components for the game */
    private final int TOTAL_TIME = 100;
    private int timeLeft;
    private int TIMER_DELAY = 1000;
    private Timer timer;

    private GameState state = GameState.RUNNING;
    private GameState prevState = GameState.RUNNING;
    private GameWorld game;
    private GameVisuals renderer;
    private GUI gui;
    WriteJSON wj;

    public ChapsChallenge() {
        gui = new GUI();
        renderer = new GameVisuals();
        gui.setGamePanel(renderer);
        wj = new WriteJSON();
        timer = new Timer(TIMER_DELAY, gameTimer);

        gui.init();
        Controls control = new Controls(this);
        gui.addKeyListener(control);
        gui.setActionListeners(control);
        gui.setTimeLeft(timeLeft);

        loadLevel();
        startGame();
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
            wj.storePlayerMove(move, timeLeft);
        };
    }

    /**
     * Save the replay to a file.
     */
    public void saveReplay() {
        wj.writeJsonToFile();
    }

    /**
     * Method which will pause the game.
     */
    public void pause() {
        prevState = state;
        state = GameState.PAUSED;
        JOptionPane.showMessageDialog(gui, "Paused");
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
        renderer.setGame(game);
    }

    /**
     * Method is responsible for the starting of the game, keeps the game
     * in states to keep it running.
     */
    public void startGame() {
        timeLeft = TOTAL_TIME;
        timer.start();

        Runnable runnableGame = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    long start = System.currentTimeMillis();
                    switch(state) {
                        case PAUSED:
                            //Create a JOptionPane. Stop time countdown.
                            break;
                        case RUNNING:
                            // update
                            checkInfo();
                            // render
                            renderer.repaint();
                            break;
                        case WON:
                            // game.loadLevel(xx)
                            // winning logic
                            // next level
                            wonGame();
                            System.out.println("Game: WON");
                            saveReplay();
                            break;
                        case DEAD:
                            // show dead prompt?
                            // restart level
                            //game.loadLevel()
                            System.out.println("Game: DEAD");
                            saveReplay();
                            break;
                    }
                    checkGameState();
                    updateDashboard();

                }
            }
        };
        new Thread(runnableGame).start();
    }

    public void wonGame() {
        loadLevel();
        timeLeft = TOTAL_TIME;
        timer.restart();
        state = GameState.RUNNING;
    }

    public void updateDashboard() {
        InventoryPanel inv = gui.getInventoryPanel();
        inv.setItemsToDisplay(game.getChap().getInventory());
        inv.repaint();
    }

    public void checkInfo() {
        if(game.isChapOnInfo()) {
            renderer.setInfoText(game.getLevelInfo());
            renderer.toggleInfo(true);
        } else {
            renderer.toggleInfo(false);
        }
    }

    /**
     * Check which state the game is currently in. This can either
     * be INFO, WON or DEAD.
     */
    public void checkGameState() {
        if (game.isChapOnExit()) {
            state = GameState.WON;
        }
        if (!game.isChapActive()) {
            state = GameState.DEAD;
        }
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
