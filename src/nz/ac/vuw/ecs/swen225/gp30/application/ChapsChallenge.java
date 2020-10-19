package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.Record;
import nz.ac.vuw.ecs.swen225.gp30.persistence.Persistence;
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
    private int timerDelay = 1000;
    private Timer timer;

    private GameState state = GameState.RUNNING;
    private GameState prevState = GameState.RUNNING;
    private GameWorld game;
    private GameVisuals renderer;
    private GUI gui;
    Record record;

    public ChapsChallenge() {
        gui = new GUI();
        renderer = new GameVisuals();
        gui.setGamePanel(renderer);
        record = new Record();
        timer = new Timer(timerDelay, gameTimer);

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
            if (timeLeft == 0) {
                state = GameState.TIMEOUT;
                timer.stop();
            }
            gui.setTimeLeft(timeLeft--);
        }
    };

    /**
     * Method to move Chap about the maze.
     *
     * @param move - direction.
     */
    public void move(Move move) {
        if (game.moveChap(move)) {
            record.storePlayerMove(move, timeLeft);
        }
    }

    /**
     * Returns the time left.
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Increase the timer Delay.
     */
    public void decreaseTimerDelay() {
        if (timerDelay > 500) {
            timerDelay = timerDelay / 2;
        } else {
            timerDelay = 500;
        }
    }

    /**
     * Decrease the timer Delay.
     */
    public void increaseTimerDelay() {
        if (timerDelay < 4000) {
            timerDelay = timerDelay * 2;
        } else {
            timerDelay = 4000;
        }
    }

    /**
     * Set the timer Delay speed with the menu buttons.
     */
    public void setTimerDelay(int setTime) {
        timerDelay = setTime;
    }

    /**
     * Save the replay to a file.
     */
    public void saveReplay() {
        record.writeJsonToFile();
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
    public void resume() {
        state = prevState;
    }

    /**
     * Method will load a level for the game.
     */
    public void loadLevel() {
        game = Persistence.readLevel();
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
                long elapsed = 0;
                while (true) {
                    long start = System.currentTimeMillis();
                    switch (state) {
                        //Create a JOptionPane. Stop time countdown.
                        case PAUSED:
                            //Pause game.
                            break;
                        case RUNNING:
                            elapsed += (long) 1000 / (long) 30;
                            // update
                            checkInfo();
                            if (elapsed > 1000) {
                                game.advance();
                                elapsed = 0;
                            }
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
                        case TIMEOUT:
                            renderer.repaint();
                            break;
                        }
                checkGameState();
                updateDashboard();
                try {
                    Thread.sleep(start + (long) 1000 / (long) 30 - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    }
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
