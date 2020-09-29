
package nz.ac.vuw.ecs.swen225.gp30.persistence;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class writeFile {

    public void runRead()
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

    //Found online
    public void readFile() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("src/nz/ac/vuw/ecs/swen225/gp30/persistence/level1.json"));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey().equals("board")){
                    readBoard(entry.getValue().toString());
                }
                else System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void readBoard(String maze){
        Scanner sc = new Scanner(maze).useDelimiter(",");
        int count = 0;

        while(sc.hasNext()){
            System.out.print("|");
            System.out.print(sc.next());
            count++;
            if( count == 15){
                System.out.print("|");
                System.out.println();
                count = 0;
            }
        }
    }

}
