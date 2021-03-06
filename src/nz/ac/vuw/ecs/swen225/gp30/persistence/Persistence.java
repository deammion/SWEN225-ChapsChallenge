package nz.ac.vuw.ecs.swen225.gp30.persistence;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp30.maze.*;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/**
 *
 */
public class Persistence {

  public static int NUM_LEVELS = 2;


  /**
   * Saves the current gamestate to a file.
   *
   * @param game GameWorld to save
   * @param fileName File to save
   * @return String fileName
   */
  public static String saveGame(GameWorld game, String fileName) {

    String gameSave;

    StringBuilder iString = new StringBuilder();
    for (Item i : game.getChap().getInventory()) {
      iString.append(i + "/");
    }
    ArrayList<Mob> mobs = (ArrayList<Mob>) game.getMobManager().getMobs();


    HashMap<String, Integer> config = new HashMap();
    JsonBuilderFactory factory = Json.createBuilderFactory(config);
    JsonObjectBuilder b;
    if (mobs.size() > 0) {
      b = Json.createObjectBuilder()
          .add("levelInfo", game.getLevelInfo())
          .add("board", game.getMaze().toString(false))
          .add("inventory", iString.toString())

          .add("chap", factory.createObjectBuilder()
              .add("x", game.getChap().getX())
              .add("y", game.getChap().getY())
          )
          .add("mobs", factory.createObjectBuilder()
              .add("bug-1", factory.createObjectBuilder()
                  .add("x", mobs.get(0).getX())
                  .add("y", mobs.get(0).getY())
                  .add("path", factory.createArrayBuilder()
                      .add(mobs.get(0).getPath()[0])
                      .add(mobs.get(0).getPath()[1])
                      .add(mobs.get(0).getPath()[2])
                      .add(mobs.get(0).getPath()[3])
                      .add(mobs.get(0).getPath()[4])
                      .add(mobs.get(0).getPath()[5])
                      .add(mobs.get(0).getPath()[6])
                      .add(mobs.get(0).getPath()[7])
                      .add(mobs.get(0).getPath()[8])
                      .add(mobs.get(0).getPath()[9])
                      .add(mobs.get(0).getPath()[10])
                      .add(mobs.get(0).getPath()[11]))


              )
              .add("bug-2", factory.createObjectBuilder()
                  .add("x", mobs.get(1).getX())
                  .add("y", mobs.get(1).getY())
                  .add("path", factory.createArrayBuilder()
                      .add(mobs.get(1).getPath()[0])
                      .add(mobs.get(1).getPath()[1])
                      .add(mobs.get(1).getPath()[2])
                      .add(mobs.get(1).getPath()[3])
                      .add(mobs.get(1).getPath()[4])
                      .add(mobs.get(1).getPath()[5]))
              )
              .add("boardWidth", game.getMaze().getCols())
              .add("boardHeight", game.getMaze().getRows())
              .add("chapChips", game.getChap().getChipsCollected())
              .add("chipsRequired", 11)
              .add("time", game.getTimeLeft()));
    } else {
      b = Json.createObjectBuilder()
          .add("levelInfo", game.getLevelInfo())
          .add("board", game.getMaze().toString(false))
          .add("inventory", iString.toString())

          .add("chap", factory.createObjectBuilder()
              .add("x", game.getChap().getX())
              .add("y", game.getChap().getY())
          )
          .add("mobs", factory.createObjectBuilder()
              .add("bug-1", factory.createObjectBuilder()
                  .add("x", "")
                  .add("y", "")
                  .add("path", "")
              )
              .add("bug-2", factory.createObjectBuilder()
                  .add("x", "")
                  .add("y", "")
                  .add("path", "")
              )
              .add("boardWidth", game.getMaze().getCols())
              .add("boardHeight", game.getMaze().getRows())
              .add("chapChips", game.getChap().getChipsCollected())
              .add("chipsRequired", 11)
              .add("time", game.getTimeLeft()));
    }


    try {
      Writer w = new StringWriter();
      Json.createWriter(w).write(b.build());
      gameSave = w.toString();
      int saveLength = gameSave.length();
      w.close();

      Writer writer = new BufferedWriter(new FileWriter("src/" + fileName));

      for (int i = 0; i < saveLength; i++) {
        char next = gameSave.charAt(i);
        if (next == ',' || next == '{') writer.write(next + "\n\t");
        else if (next == '}') writer.write("\n" + next);
        else writer.write(next);
      }

      writer.close();

    } catch (IOException e) {
      System.out.printf("Error saving game: " + e);
    }
    return fileName;

  }


  /**
   * Loads the saved game file json and returns the GameWorld.
   *
   * @return Gameworld object to load
   */
  public static GameWorld loadSave() {

    JFileChooser chooser = new JFileChooser("src/");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JSON files", "json");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
        // create Gson instance
        Gson gson = new Gson();

        // create a reader
        Reader reader = Files.newBufferedReader(Paths.get(chooser.getSelectedFile().getPath()));

        // convert JSON file to map
        Map<?, ?> map = gson.fromJson(reader, Map.class);

        int width = (int) Double.parseDouble(map.get("boardWidth").toString());
        int height = (int) Double.parseDouble(map.get("boardHeight").toString());
        int chipsRequired = (int) Double.parseDouble(map.get("chipsRequired").toString());
        String boardString = map.get("board").toString();
        Maze maze = readBoard(width, height, boardString);

        int chapX = (int) Double.parseDouble(((Map) map.get("chap")).get("x").toString());
        int chapY = (int) Double.parseDouble(((Map) map.get("chap")).get("y").toString());
        Chap chap = new Chap(chapX, chapY);


        MobManager mobMgr = new MobManager(maze);
        if (map.containsKey("mobs")) {
          List<Mob> mobs = readMobs((Map) map.get("mobs"));
          mobMgr.setMobs(mobs);
        }

        int time;
        if (map.containsKey("time")) {
          time = (int) Double.parseDouble((map.get("time")).toString());
        }

        int currentChips;
        if (map.containsKey("chapChips")) {
          currentChips = (int) Double.parseDouble((map.get("chapChips")).toString());
        }

        String inventory;
        if (map.containsKey("inventory")) {
          inventory = map.get("inventory").toString();
        }

        GameWorld game = new GameWorld(maze, chap);
        String levelInfo = map.get("levelInfo").toString();

        game.setMobManager(mobMgr);
        game.setLevelInfo(levelInfo);

        reader.close();
        return game;
      } catch (Exception ex) {
        System.out.println("This file is invalid");
      }
    }
    return null;
  }

  /**
   * Reads the level file and returns a GameWorld object.
   *
   * @param level number
   * @return GameWorld object
   */
  public static GameWorld readLevel(int level) {
    if (level > 2){
      return null;
    }
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
      String boardString = map.get("board").toString();
      Maze maze = readBoard(width, height, boardString);
      ExitLockTile.setChipsRequired(chipsRequired);

      int chapX = (int) Double.parseDouble(((Map) map.get("chap")).get("x").toString());
      int chapY = (int) Double.parseDouble(((Map) map.get("chap")).get("y").toString());
      Chap chap = new Chap(chapX, chapY);


      MobManager mobMgr = new MobManager(maze);
      if (map.containsKey("mobs")) {
        List<Mob> mobs = readMobs((Map) map.get("mobs"));
        mobMgr.setMobs(mobs);
      }

      int time;
      if (map.containsKey("time")) {
        time = (int) Double.parseDouble((map.get("time")).toString());
      }

      int currentChips;
      if (map.containsKey("chapChips")) {
        currentChips = (int) Double.parseDouble((map.get("chapChips")).toString());
      }

      String inventory;
      if (map.containsKey("inventory")) {
        inventory = map.get("inventory").toString();
      }

      GameWorld game = new GameWorld(maze, chap);
      String levelInfo = map.get("levelInfo").toString();

      game.setMobManager(mobMgr);
      game.setLevelInfo(levelInfo);

      reader.close();
      return game;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * Reads the mob info into a List.
   *
   * @param mobs Map of mobs
   * @return A list of mobs
   */
  public static List<Mob> readMobs(Map mobs) {
    List<Mob> mobsList = new ArrayList<>();
    int mobX;
    int mobY;
    int[] path;
    for (Object entry : mobs.values()) {
      mobX = (int) Double.parseDouble(((Map) entry).get("x").toString());
      mobY = (int) Double.parseDouble(((Map) entry).get("y").toString());
      List<Double> pathArr = ((List<Double>) ((Map) entry).get("path"));
      path = new int[pathArr.size()];
      for (int i = 0; i < pathArr.size(); i++) {
        path[i] = pathArr.get(i).intValue();
      }

      mobsList.add(new Bug(mobX, mobY, path));
    }

    return mobsList;
  }

  /**
   * Loads the previous level.
   * @return null calls methods
   */
  public static GameWorld  loadPrevious(){

    String path = "src/nz/ac/vuw/ecs/swen225/gp30/persistence/levels/level/currentLevel.json";
    try {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(path));
      Map<?, ?> map = gson.fromJson(reader, Map.class);
      int level = (int) Double.parseDouble((map.get("level").toString()));

      if(level == 1){
        readLevel(1);
      }
      else if(level == 2){
        readLevel(2);
      }
      else return null;

    }
    catch (IOException E){
      System.out.println("Error loading previous level");
    }

    return null;
  }

  /**
   * Saves the previous level number into a new json file.
   * @param game Game to be saved
   */
  public static void saveLevel(GameWorld game) {

    String gameSave;

    ArrayList<Mob> mobs = (ArrayList<Mob>) game.getMobManager().getMobs();
    JsonObjectBuilder b = Json.createObjectBuilder();
    if (mobs.size() > 0){
      b.add("level", 2);
    } else b.add("level", 1);

    try {
      Writer w = new StringWriter();
      Json.createWriter(w).write(b.build());
      gameSave = w.toString();
      int saveLength = gameSave.length();
      w.close();

      Writer writer = new BufferedWriter(new FileWriter("src/nz/ac/vuw/ecs/swen225/gp30/persistence/levels/currentLevel.json"));

      for (int i = 0; i < saveLength; i++) {
        char next = gameSave.charAt(i);
        if (next == ',' || next == '{') writer.write(next + "\n\t");
        else if (next == '}') writer.write("\n" + next);
        else writer.write(next);
      }

      writer.close();

    } catch (IOException e) {
      System.out.printf("Error saving game: " + e);
    }

  }

  /**
   * Reads the board and sets the tiles from Maze module.
   *
   * @param width of board.
   * @param height of board.
   * @param board String.
   * @return Maze object
   */
  public static Maze readBoard(int width, int height, String board) {
    Maze m = new Maze(width, height);

    Scanner sc = new Scanner(board).useDelimiter(",");
    int x = 0;
    int y = 0;

    while (sc.hasNext()) {
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
          m.setTileAt(x, y, new ExitLockTile(x, y));
          break;
      }
      x++;
      if (x == width) {
        y++;
        x = 0;
      }
    }
    return m;
  }
}