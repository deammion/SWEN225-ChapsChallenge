
package nz.ac.vuw.ecs.swen225.gp30.persistence;

public class writeFile {
	
	public static void main(String[] args) 
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
		    Reader reader = Files.newBufferedReader(Paths.get("src/nz/ac/vuw/ecs/swen225/gp20/persistence/levels/level1.json"));

		    // convert JSON file to map
		    Map<String, String> map = gson.fromJson(reader, Map.class);

		    // print map entries
		    for (Map.Entry<String, String> entry : map.entrySet()) {
		        System.out.println(entry.getKey() + "=" + entry.getValue());
		    }

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
}
