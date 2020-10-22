package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.Record;
import nz.ac.vuw.ecs.swen225.gp30.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp30.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.gp30.render.Audio;
import nz.ac.vuw.ecs.swen225.gp30.render.GameVisuals;

import javax.swing.*;

/**
 * @author jakeh
 */
public class ChapsChallenge {

    enum GameState {
        RUNNING,
        PAUSED,
        WON,
        DEAD,
        TIMEOUT,
        COMPLETE
    }

    // Timing components for the game
    private final int TIMER_DELAY = 1000;
    private final int TOTAL_TIME = 100;
    private int ticks = 0;
    private double UPDATES_PER_SECOND = 30;
    private double FRAMES_PER_SECOND = 40;
    private double REPLAY_SPEED = 1;
    private final double REPLAY_INCREMENT = 0.25;
    private final double MAX_REPLAY_SPEED = 2;
    private final double MIN_REPLAY_SPEED = 0.25;
    private final double NANO_TO_SECOND = 1000000000;

    // Game State and Class Components
    private GameState state = GameState.RUNNING;
    private GameState prevState = GameState.RUNNING;
    private GameVisuals renderer;
    private Audio audio;
    private GameWorld game;
    private GUI gui;
    private Record record;
    private Replay replay;
    PlayerControls playerControls;

    // Record mode and level
    public Boolean replayMode = false;
    public int gameLevel;

    /**
     * Initialize and Start the Game.
     */
    public ChapsChallenge() {

        gameLevel = 1;
        gui = new GUI();
        renderer = new GameVisuals();
        audio = new Audio();
        gui.setGamePanel(renderer);
        record = new Record();

        gui.init();
        Controls control = new Controls(this);
        playerControls = new PlayerControls();
        gui.addKeyListener(playerControls);
        gui.addKeyListener(control);
        gui.setLevelLeft(gameLevel);
        gui.setActionListeners(control);

        loadLevel(gameLevel);
        startGame();
    }


    /**
     * Method is responsible for the starting of the game, keeps the game
     * in states to keep it running.
     */
    public void startGame() {
        game.setTimeLeft(TOTAL_TIME);

        Runnable runnableGame = () -> {
            long start = System.nanoTime();

            double updateTime = NANO_TO_SECOND / (UPDATES_PER_SECOND * REPLAY_SPEED);
            double renderTime = NANO_TO_SECOND / (FRAMES_PER_SECOND * REPLAY_SPEED);
            double updateDelta = 0;
            double frameDelta = 0;
            int frames = 0;
            long time = System.currentTimeMillis();

            //noinspection InfiniteLoopStatement
            while (true) {
                long currentTime = System.nanoTime();
                updateDelta += (currentTime - start) / updateTime;
                frameDelta += (currentTime - start) / renderTime;
                start = currentTime;

                switch (state) {
                    case PAUSED:
                        break;
                    case RUNNING:
                        if (updateDelta >= 1) {
                            Move m = processInput();
                            if (!replayMode) {
                                playerControls.releaseKeys();
                                if(m != null) {
                                    move(m);
                                    record.storePlayerMove(m, ticks);
                                }
                                checkInfo();
                            } else {
                                Move nextMove = replay.autoPlay(ticks);
                                if (nextMove != null) {
                                    move(nextMove);
                                    if(replay.endOfReplay()){
                                        replay.toggleAutoPlaying();
                                        replayFinished();
                                    }
                                }
                            }

                            ticks++;
                            if (ticks == UPDATES_PER_SECOND) {
                                game.advance();
                            }
                            updateDelta--;
                        }
                        if (frameDelta >= 1) {
                            renderer.repaint();
                            frames++;
                            frameDelta--;
                        }

                        if (System.currentTimeMillis() - time > 1000) {
                            System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                            frames = 0;
                            ticks = 0;
                            time += 1000;
                            game.decrementTimeLeft();
                            gui.setTimeLeft(game.getTimeLeft());
                            if(game.getTimeLeft() == 0){ state = GameState.TIMEOUT; }
                        }
                        break;
                    case WON:
                        if(!replayMode) {
                            saveReplay();
                            wonGame();
                            System.out.println("Game: WON");
                        }
                        break;
                    case COMPLETE:
                        System.out.println("COMPLETED GAME");
                        break;
                    case DEAD:
                        if(!replayMode) {
                            gameLost();
                            System.out.println("Game: DEAD");
                            saveReplay();
                        }
                        break;
                    case TIMEOUT:
                        renderer.repaint();
                        gameLost();
                        break;
                }
                checkGameState();
                updateDashboard();
            }
        };
        new Thread(runnableGame).start();
    }

    public Move processInput() {
        if(Key.up.pressed) { return Move.UP; }
        if(Key.down.pressed) { return Move.DOWN; }
        if(Key.left.pressed) { return Move.LEFT; }
        if(Key.right.pressed) { return Move.RIGHT; }
        return null;
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
        game.moveChap(move);
        audio.playSound();
    }

    /**
     * Increase the timer Delay.
     */
    public void decreaseTimerDelay() {

    }

    /**
     * Decrease the timer Delay.
     */
    public void increaseTimerDelay() {

    }

    /**
     * Set the timer Delay speed with the menu buttons.
     */
    public void setTimerDelay(int setTime) {

    }

    /**
     * If the game has been won.
     */
    public void wonGame() {
        loadNextLevel();
        game.setTimeLeft(TOTAL_TIME);
        state = GameState.RUNNING;
    }

    /**
     * If Chap has run out of time or collides with a bug, see if the player
     * wants to reset the game to level 1 or exit.
     */
    public void gameLost(){
        UIManager.put("OptionPane.yesButtonText", "Restart");
        UIManager.put("OptionPane.noButtonText", "Exit Game");
        int option = JOptionPane.showOptionDialog(gui, "You have lost the current game!", "Game: Lost", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null, null, null);
        saveReplay();
        if(option == 0){
            loadLevel(1);
            renderer.setGame(game);
            state = GameState.RUNNING;
            gui.setLevelLeft(gameLevel);
        }
        else{
            System.exit(0);
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
            }
        }
    }

    /**
     * Resumes a saved Game.
     */
    public void resumeGame(){
        Persistence.loadSave();
    }

    /**
     * Method which will resume the game.
     */
    public void resume() {
        state = prevState;
    }

    /**
     * Toggle between paused and replay game states.
     */
    public void pausedAndRunning(){
        state = state == GameState.PAUSED ? GameState.RUNNING : GameState.PAUSED;
    }

    /**
     * Save a game, game state lost.
     */
    public void loadGameStateless(){

    }

    /**
     * Save a gam, game state saved.
     */
    public void loadGameSate(){
        Persistence.saveGame(game, "gameFile.txt");
    }

    /**
     * Method will load a level for the game.
     */
    public void loadLevel(int level) {
        if(level > Persistence.NUM_LEVELS) {
            state = GameState.COMPLETE;
        }
        game = Persistence.readLevel(level);
        renderer.setGame(game);
        audio.setGame(game);
        gui.setLevelLeft(level);
        game.setTimeLeft(TOTAL_TIME);
    }

    /**
     * Load the next game level.
     */
    public void loadNextLevel(){
        gameLevel++;
        loadLevel(gameLevel);
    }

    /**
     * Get the game level.
     */
    public int getGameLevel(){
        return gameLevel;
    }

    /**
     * Set the level of the game.
     *
     * @param setLevel - the level for the game to be set too.
     */
    public void setGameLevel(int setLevel){
        gameLevel = setLevel;
    }
    /**
     * Save the replay to a file.
     */
    public void saveReplay() {
        record.storeLevel(gameLevel);
        record.writeJsonToFile();
    }

    /**
     * Put the game in replay mode.
     */
    public void playReplay(){
        replay = new Replay();
        replayMode = true;
        state = GameState.PAUSED;
        replay.loadJsonToReplay();
        game = Persistence.readLevel(replay.level);
        renderer.setGame(game);
        renderer.repaint();
        startGame();
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
        ticks = replay.updateTimer();
        renderer.repaint();
        if(replay.endOfReplay()){
            replayFinished();
        }
    }

    /**
     * Method to take care of what happens to the game when a replay is finished.
     */
    public void replayFinished(){
        replayMode = false;
        replay.resetAutoPlay();
        UIManager.put("OptionPane.yesButtonText", "New Replay");
        UIManager.put("OptionPane.noButtonText", "New Game");
        int option = JOptionPane.showOptionDialog(gui, "The replay is finished!", "Game: Replay", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null, null, null);
        saveReplay();
        if(option == 0){
            replay.loadJsonToReplay();
        }
        else{
            loadLevel(1);
            state = GameState.RUNNING;
            gui.setLevelLeft(gameLevel);
        }
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
