
package nz.ac.vuw.ecs.swen225.gp30.persistence;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.Game;
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


    //Level level;
    Chap chap;
    int width;
    int cr;
    //String infoTileInfo;


    public static void main(String args[])
    {
        writeFile wf = new writeFile();
        wf.readFile();
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


    public void readFile() {


        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("src/nz/ac/vuw/ecs/swen225/gp30/persistence/level1.json"));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            Game game = new Game();
            int x = (int) (double) Double.valueOf(map.get("boardWidth").toString());
            width = x;
            int y = (int) (double) Double.valueOf(map.get("boardHeight").toString());
            cr = (int) (double) Double.valueOf(map.get("chipsRequired").toString());
            //InfoTileInfo = map.get("InfoTile").toString());
            Maze maze = new Maze(x, y);
            readBoard(map.get("board").toString(), maze);
            game.setMaze(maze);
            game.setChap(chap);
            game.setChapOnMaze(chap.getX(), chap.getY());
            System.out.println(maze.toString());

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey().equals("board")){
                    //readBoard(entry.getValue().toString());

                }
                else System.out.println(entry.getKey() + "=" + entry.getValue());
            }


            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void readBoard(String board, Maze m){
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
                case "C":
                    m.setTileAt(x, y, new FreeTile(x, y));
                    chap = new Chap();
                    chap.setAt(x, y);
                    break;
                case "G":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, null));
                    break;
                case "g":
                    m.setTileAt(x, y, new KeyTile(x, y, null));
                    break;
                case "R":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, null));
                    break;
                case "r":
                    m.setTileAt(x, y, new KeyTile(x, y, null));
                    break;
                case "B":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, null));
                    break;
                case "b":
                    m.setTileAt(x, y, new KeyTile(x, y, null));
                    break;
                case "Y":
                    m.setTileAt(x, y, new LockedDoorTile(x, y, null));
                    break;
                case "y":
                    m.setTileAt(x, y, new KeyTile(x, y, null));
                    break;
                case "i":
                    m.setTileAt(x, y, new InfoTile(x, y));

                    //WIP
                    //InfoTile info = new InfoTile(x, y, *setInfoTileInfoMethod*);

                    /**
                     * InfoTile info = new InfoTile(x, y);
                     * info.setText(infoText);
                     * m.setTileAt(x, y, info);
                     */
                    break;
                case "l":
                    m.setTileAt(x, y, new ExitLockTile(x, y, cr));
                    break;
            }

            x++;
            if( x == width){
                y++;
                x = 0;
            }
        }
    }

}
