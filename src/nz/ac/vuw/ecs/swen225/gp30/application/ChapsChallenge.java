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
    private int TIMER_DELAY = 1000;
    private final int TOTAL_TIME = 100;
    private int ticks = 0;
    private int replayTicks = 0;
    private double UPDATES_PER_SECOND = 30;
    private double FRAMES_PER_SECOND = 40;

    private final double NANO_TO_SECOND = 1000000000;
    private long time = 0;
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
            double updateTime = NANO_TO_SECOND / UPDATES_PER_SECOND;
            double renderTime = NANO_TO_SECOND / FRAMES_PER_SECOND;
            double updateDelta = 0;
            double frameDelta = 0;
            int frames = 0;
            time = System.currentTimeMillis();
            //noinspection InfiniteLoopStatement
            while (true) {
                long currentTime = System.nanoTime();
                updateDelta += (currentTime - start) / updateTime;
                frameDelta += (currentTime - start) / renderTime;
                start = currentTime;
                switch (state) {
                    case PAUSED:
                        time = System.currentTimeMillis();
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
                                    System.out.println("Ticks: " + ticks);
                                    if(replay.endOfReplay()){
                                        replay.toggleAutoPlaying();
                                        replayFinished();
                                    }
                                }
                            }
                            //System.out.println(ticks);
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
                        if (System.currentTimeMillis() - time > TIMER_DELAY) { //1500 2000
                            //System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                            frames = 0;
                            ticks = 0;
                            time += TIMER_DELAY;
                            //System.out.println(TIMER_DELAY);
                            game.decrementTimeLeft();
                            gui.setTimeLeft(game.getTimeLeft());
                            if(game.getTimeLeft() == 0){ state = GameState.TIMEOUT; }
                        }
                        break;
                    case WON:
                        if(!replayMode) {
                            saveReplay();
                            System.out.println("Game: WON");
                            state = GameState.RUNNING;
                            break;
                        }
                    case COMPLETE:
                        System.out.println("COMPLETED GAME");
                        gameComplete();
                        break;
                    case DEAD:
                        if(!replayMode) {
                            System.out.println("Game: DEAD");
                            saveReplay();
                            gameLost();
                            break;
                        }
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
            if(gameLevel < Persistence.NUM_LEVELS) {
                state = GameState.WON;
                loadNextLevel();
            }
            else{
                saveReplay();
                state = GameState.COMPLETE;
            }
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
            state = GameState.PAUSED;
            UIManager.put("OptionPane.okButtonText", "Resume");
            int option = JOptionPane.showOptionDialog(gui, "Game is currently paused!", "Game: Paused", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (option == 0 || option == -1) {
                state = GameState.RUNNING;
            }
        }
    }
    /**
     * The prompt for if the game is won.
     */
    public void gameComplete(){
        UIManager.put("JOptionPane.yesButtonText", "Restart");
        UIManager.put("JOptionPane.noButtonText", "Quit Game");
        int option = JOptionPane.showOptionDialog(gui, "Congratulations you have completed the game!", "Game: Won",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if(option == 0){
            loadLevel(1);
            renderer.setGame(game);
            state = GameState.RUNNING;
            gui.setLevelLeft(gameLevel);
        }
        if(option == 1){
            System.exit(0);
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
        //Waiting on method from Josh to call for loading a game level not saving the state.
    }
    /**
     * Save a gam, game state saved.
     */
    public void loadGameSate(){
        Persistence.saveGame(game, "gameFile.txt");
    }
    /**
     * Load the next game level.
     */
    public void loadNextLevel(){
        System.out.println("LOADNEXTLEVEL " + gameLevel);
        gameLevel++;
        loadLevel(gameLevel);
    }
    /**
     * Method will load a level for the game.
     */
    public void loadLevel(int level) {
        System.out.println("LOADLEVEL " + gameLevel);
        game = Persistence.readLevel(level);
        renderer.setGame(game);
        audio.setGame(game);
        gui.setLevelLeft(level);
        game.setTimeLeft(TOTAL_TIME);
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
        record = new Record();
    }
    /**
     * Put the game in replay mode.
     */
    public void playReplay(){
        ticks = 0;
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
        replayTicks += replay.playerMoveTick();
        if(replayTicks >= 29) {
            replayTicks -= 29;
            game.advance();
        }
        replay.incrementPlayIndex();
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
        state = GameState.PAUSED;
        UIManager.put("OptionPane.yesButtonText", "New Replay");
        UIManager.put("OptionPane.noButtonText", "New Game");
        int option = JOptionPane.showOptionDialog(gui, "The replay is finished!", "Game: Replay", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null, null, null);
        saveReplay();
        if(option == 0){
            replay.loadJsonToReplay();
        }
        else if(option == 1){
            loadLevel(1);
            gui.setLevelLeft(1);
            state = GameState.RUNNING;
        } else {
            System.exit(0);
        }
    }
    /**
     * Increase the timer Delay.
     */
    public void increaseTimerDelay() {
        if (TIMER_DELAY < 4000) {
            TIMER_DELAY = TIMER_DELAY * 2;
            replay.increaseDelay();
        }
    }
    /**
     * Decrease the timer Delay.
     */
    public void decreaseTimerDelay() {
        if(TIMER_DELAY > 1000) {
            TIMER_DELAY = TIMER_DELAY/2;
            replay.decreaseDelay();
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