
package nz.ac.vuw.ecs.swen225.gp30.persistence;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.ItemType;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;


public class writeFile {

    public static void main(String args[]){
        GameWorld g = readLevel();
    }

    /**public static void saveGame(ChapsChallenge game, String name) {

     //String game = getState(game);
     try {
     BufferedWriter w = new BufferedWriter(new FileWriter(name));
     w.write(game);
     w.close();
     } catch (IOException e) {
     System.out.println("Error saving game: " + e);
     }

     }**/


    public static GameWorld readLevel() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("src/nz/ac/vuw/ecs/swen225/gp30/persistence/level1.json"));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            int width = (int) Double.parseDouble(map.get("boardWidth").toString());
            int height = (int) Double.parseDouble(map.get("boardHeight").toString());
            int chipsRequired = (int) Double.parseDouble(map.get("chipsRequired").toString());
            int chapX = (int) Double.parseDouble(((Map)map.get("chap")).get("x").toString());
            int chapY = (int) Double.parseDouble(((Map)map.get("chap")).get("y").toString());

            String levelInfo = map.get("levelInfo").toString();
            String boardString = map.get("board").toString();

            Maze maze = readBoard(chipsRequired, width, height, boardString);
            Chap chap = new Chap(chapX, chapY);
            System.out.println(maze.toString());
            GameWorld game = new GameWorld(maze, chap);
            game.setChipsRequired(chipsRequired);
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
}
