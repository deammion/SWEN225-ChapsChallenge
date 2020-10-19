package nz.ac.vuw.ecs.swen225.gp30.persistence;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp30.application.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp30.maze.*;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.ItemType;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Persistence {

    public static void main(String args[]){
        GameWorld g = readLevel();
    }

    public static void saveGame(ChapsChallenge game, String name) {

        String g = getState(game);
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(name));
            w.write(g);
            w.close();
        } catch (IOException e) {
            System.out.println("Error saving game: " + e);
        }

    }


    public static String getState(ChapsChallenge game){
        return "";
    }

    public static GameWorld readLevel() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("src/nz/ac/vuw/ecs/swen225/gp30/persistence/levels/level2.json"));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            int width = (int) Double.parseDouble(map.get("boardWidth").toString());
            int height = (int) Double.parseDouble(map.get("boardHeight").toString());
            int chipsRequired = (int) Double.parseDouble(map.get("chipsRequired").toString());
            String boardString = map.get("board").toString();
            Maze maze = readBoard(chipsRequired, width, height, boardString);

            int chapX = (int) Double.parseDouble(((Map)map.get("chap")).get("x").toString());
            int chapY = (int) Double.parseDouble(((Map)map.get("chap")).get("y").toString());
            Chap chap = new Chap(chapX, chapY);

            MobManager mobMgr = new MobManager(maze);
            if(map.containsKey("mobs")) {
                List<Mob> mobs = readMobs((Map)map.get("mobs"));
                mobMgr.setMobs(mobs);
            }

            GameWorld game = new GameWorld(maze, chap);
            String levelInfo = map.get("levelInfo").toString();

            game.setMobManager(mobMgr);
            game.setLevelInfo(levelInfo);
            GameWorld.CHIPS_REQUIRED = chipsRequired;

            reader.close();
            return game;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<Mob> readMobs(Map mobs) {
        List<Mob> mobsList = new ArrayList<>();
        int mobX;
        int mobY;
        int[] path;
        for(Object entry : mobs.values()) {
            mobX = (int) Double.parseDouble(((Map) entry).get("x").toString());
            mobY = (int) Double.parseDouble(((Map) entry).get("y").toString());
            List<Double> pathArr = ((List<Double>) ((Map) entry).get("path"));
            path = new int[pathArr.size()];
            for(int i=0; i<pathArr.size(); i++) {
                path[i] = pathArr.get(i).intValue();
            }

            mobsList.add(new Bug(mobX, mobY, path));
        }

        return mobsList;
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
