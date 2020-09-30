package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.application.GUI;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.FreeTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.InfoTile;
import nz.ac.vuw.ecs.swen225.gp30.persistence.writeFile;
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

    public void move(Move move) {
        System.out.println(move.toString());
        if(game.moveChap(move)) {
            // Recnreplay(move)
        };
    }

    public void pause() {
        prevState = state;
        state = GameState.PAUSED;
    }

    public void resume() { state = prevState; }

    public void loadLevel() {
        //game = writeFile.readLevel();
//        Maze m = new Maze(2, 1);
//        m.setTileAt(0,0, new FreeTile(0,0));
//        m.setTileAt(1,0, new InfoTile(1,0));
//        game = new GameWorld(m, new Chap(0,0));
        //renderer = new GameVisuals(game);
    }

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

    public void displayInfo() {
        JOptionPane.showMessageDialog(this, game.getLevelInfo());
    }

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
