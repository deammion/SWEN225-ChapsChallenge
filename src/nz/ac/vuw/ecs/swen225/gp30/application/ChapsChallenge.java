package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.Record;
import nz.ac.vuw.ecs.swen225.gp30.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.gp30.render.GameVisuals;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author jakeh
 */
public class ChapsChallenge {

    enum GameState {
        RUNNING,
        PAUSED,
        WON,
        DEAD,
        TIMEOUT
    }

    // Timing components for the game
    private int timerDelay = 1000;
    private Timer timer;
    private final int TOTAL_TIME = 100;

    // Game State and Class Components
    private GameState state = GameState.RUNNING;
    private GameState prevState = GameState.RUNNING;
    private GameVisuals renderer;
    private GameWorld game;
    private GUI gui;
    private Record record;
    private Replay replay;

    // Record mode and level
    public Boolean recordMode = false;
    public int gameLevel;

    /**
     * Initialize and Start the Game.
     */
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

        gameLevel = 1;
        loadLevel(gameLevel);
        startGame();
    }

    ActionListener gameTimer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (game.getTimeLeft() == 0) {
                state = GameState.TIMEOUT;
                timer.stop();
            }
            game.decrementTimeLeft();
            gui.setTimeLeft(game.getTimeLeft());
        }
    };



    /**
     * Method is responsible for the starting of the game, keeps the game
     * in states to keep it running.
     */
    public void startGame() {
        game.setTimeLeft(TOTAL_TIME);
        timer.start();

        Runnable runnableGame = () -> {
            long elapsed = 0;
            //noinspection InfiniteLoopStatement
            while (true) {
                long start = System.currentTimeMillis();
                switch (state) {
                    case PAUSED:
                        timer.stop();
                        break;
                    case RUNNING:
                        elapsed += (long) 1000 / (long) 30;
                        checkInfo();
                        if(recordMode){
                            Move nextMove = replay.autoPlay(game.getTimeLeft());
                            if(nextMove != null){
                                move(nextMove);
                            }
                        }
                        if (elapsed > 1000) {
                            game.advance();
                            elapsed = 0;
                        }
                        renderer.repaint();
                        break;
                    case WON:
                        saveReplay();
                        wonGame();
                        System.out.println("Game: WON");
                        break;
                    case DEAD:
                        gameLost();
                        System.out.println("Game: DEAD");
                        saveReplay();
                        break;
                    case TIMEOUT:
                        renderer.repaint();
                        break;
                }
                checkGameState();
                updateDashboard();
                //Remove?
                try {
                    Thread.sleep(start + (long) 1000 / (long) 30 - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnableGame).start();
    }

    /**
     * Update the dashboard to show the inventory keys.
     */
    public void updateDashboard() {
        InventoryPanel inv = gui.getInventoryPanel();
        inv.setItemsToDisplay(game.getChap().getInventory());
        inv.repaint();
        gui.setChipsLeft(game.getChipsLeft());
    }

    /**
     *  Display the info for Chap when on the information tile.
     */
    public void checkInfo() {
        if(game.isChapOnInfo()) {
            renderer.setInfoText(game.getLevelInfo());
            renderer.toggleInfo(true);
        } else {
            renderer.toggleInfo(false);
        }
    }

    /**
     * Toggle between auto and step by step replaying.
     */
    public void togglePlay(){
        replay.toggleAutoPlaying();
    }

    /**
     * Play the next move, update the timer for the replay mode.
     */
    public void replayNextMove(){
        move(replay.playNextMove());
        //timeLeft = replay.updateTimer();
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
     * Method to move Chap about the maze if in record mode it
     * won't make a second copy of the move.
     *
     * @param move - direction.
     */
    public void move(Move move) {
        if (game.moveChap(move) && !recordMode) {
            record.storePlayerMove(move, game.getTimeLeft());
        }
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
        record.storeLevel(gameLevel);
        record.writeJsonToFile();
    }

    /**
     * If the game has been won.
     */
    public void wonGame() {
        loadNextLevel();
        game.setTimeLeft(TOTAL_TIME);
        timer.restart();
        state = GameState.RUNNING;
    }

    /**
     * If Chap has run out of time or collides with a bug.
     */
    public void gameLost(){
        UIManager.put("OptionPane.yesButtonText", "Restart");
        UIManager.put("OptionPane.noButtonText", "Exit");
        int option = JOptionPane.showOptionDialog(gui, "You have lost the current game!", "Game: Lost", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null, null, null);
        if(option == 0){
            game = Persistence.readLevel(gameLevel);
        }
        else{
            System.exit(0);
        }
    }

    /**
     * Method which will resume the game.
     */
    public void resume() {
        state = prevState;
        timer.start();
    }

    /**
     * Toggle between paused and replay game states.
     */
    public void pausedAndRunning(){
        if(state == GameState.PAUSED){
            state = GameState.RUNNING;
            timer.start();
        }
        else{
            state = GameState.PAUSED;
            timer.stop();
        }
    }

    /**
     * Method which will pause the game.
     */
    public void pause() {
        if(game.getTimeLeft() > 0) {
            prevState = state;
            state = GameState.PAUSED;
            //timer.stop();
            UIManager.put("OptionPane.okButtonText", "Resume");
            int option = JOptionPane.showOptionDialog(gui, "Game is currently paused!", "Game: Paused", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (option == 0 || option == -1) {
                state = prevState;
                timer.start();
            }
        }
    }

    /**
     * Method will load a level for the game.
     */
    public void loadLevel(int i) {
        game = Persistence.readLevel(i);
        renderer.setGame(game);
        game.setTimeLeft(TOTAL_TIME);
    }

    /**
     * Put the game in replay mode.
     */
    public void playReplay(){
        recordMode = true;
        state = GameState.PAUSED;
        int replayLevel = loadRecordAndReplayFile();
        game = Persistence.readLevel(replayLevel);
        renderer.setGame(game);
        startGame();
    }

    /**
     * Method to load and pass a file for Record and Replay to use to show
     * a previously recorded game.
     */
    public Integer loadRecordAndReplayFile(){
        int level = 0;
        replay = new Replay();
        //Open the file chooser directory to get file name for Record and Replay.
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int fileReturnValue = fileChooser.showOpenDialog(null);
        if(fileReturnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            level = replay.loadJsonToReplay(selectedFile.getName());
            System.out.println(selectedFile.getName());
        }
        return level;
    }

    /**
     * Load the next game level.
     */
    public void loadNextLevel(){
        gameLevel++;
        loadLevel(gameLevel);
    }


    /**
     * Main method, begins the game.
     * @param args - arguments parsed.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                ChapsChallenge::new
        );
    }
}
