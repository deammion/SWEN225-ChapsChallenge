
package nz.ac.vuw.ecs.swen225.gp30.persistence;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp30.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp30.maze.MobManager;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;


public class Persistence {

    private final int NUM_LEVELS = 2;



    public static void main(String args[]){
        GameWorld g = readLevel(1);
    }

    public static void saveGame(ChapsChallenge game, String name) {

     //String g = getState(game);
     try {
     BufferedWriter w = new BufferedWriter(new FileWriter(name));
     //w.write(g);
     w.close();
     } catch (IOException e) {
     System.out.println("Error saving game: " + e);
     }

     }


     //public static String getState(ChapsChallenge game){


     //}

    public static GameWorld readLevel(int level) {
        String path = "src/nz/ac/vuw/ecs/swen225/gp30/persistence/levels/level" + level + ".json";
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(path));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            int width = (int) Double.parseDouble(map.get("boardWidth").toString());
            int height = (int) Double.parseDouble(map.get("boardHeight").toString());
            int chipsRequired = (int) Double.parseDouble(map.get("chipsRequired").toString());
            int chapX = (int) Double.parseDouble(((Map)map.get("chap")).get("x").toString());
            int chapY = (int) Double.parseDouble(((Map)map.get("chap")).get("y").toString());
            int mobX = (int) Double.parseDouble(((Map)map.get("mob")).get("x").toString());
            int mobY = (int) Double.parseDouble(((Map)map.get("mob")).get("y").toString());
            //int[] mobPath = map.get("mob".get("path"));


            String levelInfo = map.get("levelInfo").toString();
            String boardString = map.get("board").toString();


            Maze maze = readBoard(chipsRequired, width, height, boardString);
            MobManager mob = new MobManager(maze);
            //mob.addMob(mobX, mobY);
            Chap chap = new Chap(chapX, chapY);
            System.out.println(maze.toString());
            GameWorld game = new GameWorld(maze, mob, chap);
            GameWorld.CHIPS_REQUIRED = chipsRequired;
            game.setLevelInfo(levelInfo);

            reader.close();
            return game;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Maze readBoard(int chipsRequired, int width, int height, String board){
        Maze m = new Maze(width, height);

        Scanner sc = new Scanner(board).useDelimiter(",");
        int x = 0;
        int y = 0;

        while(sc.hasNext()){
            String tile = sc.next();
            switch (tile) {
                case "_":
                    m.setTileAt(x, y, new FreeTile(x, y));
                    break;
                case "#":
                    m.setTileAt(x, y, new WallTile(x, y));
                    break;
                case "T":
                    m.setTileAt(x, y, new TreasureTile(x, y));
                    break;
                case "E":
                    m.setTileAt(x, y, new ExitTile(x, y));
                    break;
                case "G":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, Item.KEY_GREEN));
                    break;
                case "g":
                    m.setTileAt(x, y, new KeyTile(x, y, Item.KEY_GREEN));
                    break;
                case "R":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, Item.KEY_RED));
                    break;
                case "r":
                    m.setTileAt(x, y, new KeyTile(x, y, Item.KEY_RED));
                    break;
                case "B":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, Item.KEY_BLUE));
                    break;
                case "b":
                    m.setTileAt(x, y, new KeyTile(x, y, Item.KEY_BLUE));
                    break;
                case "Y":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, Item.KEY_YELLOW));
                    break;
                case "y":
                    m.setTileAt(x, y, new KeyTile(x, y, Item.KEY_YELLOW));
                    break;
                case "i":
                    m.setTileAt(x, y, new InfoTile(x, y));
                    break;
                case "l":
                    m.setTileAt(x, y, new ExitLockTile(x, y, chipsRequired));
                    break;
            }
            x++;
            if(x == width){
                y++;
                x = 0;
            }
        }
        return m;
    }

    public int getTotalLevels(){
      return NUM_LEVELS;
    }

}
