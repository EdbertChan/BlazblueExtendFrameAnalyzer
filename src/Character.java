import java.io.*;
import java.util.*;

public class Character {
   private SortedMap<String,MoveTraits> moves = new TreeMap<String, MoveTraits>();
    private String characterName = "Ragna the Bloodedge";
   public Character(String characterData){
       try { 
    	   
			characterName = characterData;
			System.out.println(characterName);
       String fileName = characterData+ "/" + characterData + ".txt";
       BufferedReader br = new BufferedReader(new FileReader(fileName));
       String line = br.readLine();
       
       //data we want to remember
        int[] data = new int[5]; //data will have Level, Startup, Active, Recovery, and guard type. Frame adv, will be calculated after
        //default is 0, cannot crouch will be 1, cannot stand will be 2, air barrier will be 3, unblockable will be 4
        String moveName = "";
        boolean specNor[] = new boolean[3]; //normal, special, super
        for (int i =0; i < 3; i++){
        	specNor[i] = false;
        }
        int level;
        
        //checks for specials or supers
       while (line != null){
           String[] dataArray = line.split("\t");
           if (dataArray[0].equals("Special")){
               specNor[1] = true;
           }
           
           if (dataArray[0].equals("Super")){
               specNor[2] = true;
           }
           //checks if any extraneous information is here. Also serves as the terminator
           while (dataArray.length <= 3 || dataArray[0].equals("Name")){
               line = br.readLine();
               if (dataArray[0].equals("Special")){
                   specNor[1] = true;
               }
               if (dataArray[0].equals("Super")){
                   specNor[2] = true;
                   
               }
               if (line == null){
                   break;
               }
               dataArray = line.split("\t");
           }
           
           //extracts data from the lines and assigns them to our interests 
           moveName = dataArray[0];
           
           data[0] = changeInt(dataArray[7]); //level
           data[1] = changeInt(dataArray[9]); //startup
           data[2] = changeInt(dataArray[10]); //active
           data[3] = changeInt(dataArray[11]); //recovery
           data[4] = guardType(dataArray[6], moveName);//guardType
   
           MoveTraits characteristics = new MoveTraits(characterData, moveName, data, specNor); //calls the constructor
           getMoves().put(moveName, characteristics);
         
           line = br.readLine();
       }

     } catch (Exception e) {
       System.err.println("TXT file cannot be read : " + e);
     }
   }
   
   private int guardType(String type, String name){
	 //default is 0, cannot crouch will be 1, cannot stand will be 2, air barrier will be 3, unblockable will be 4
	   
	    if (type.equals("H") || type.equals("HA")){
		   return 1;
	   }
	   else if (type.equals("L") || type.equals("F")){
		   return 2;
	   }
	   else if(type.equals("HL")){
		   return 3;
	   }
	   else if(name.equals("Air Throw") || name.equals("Forward Throw") || name.equals("Back Throw") || type.contains("throw") || type.equals("unblockable")){
		   return 4;
	   }
		  
	   return 0;
   }
   public String returnCharacterName(){
	   return characterName;
   }
   public static int changeInt(String move){ //Changes a data to int
        move = move.replace(" ", "");
        move = move.replace("\"", "");
        int loader = 0;
        try{
            loader = Integer.parseInt(move);
        }catch(NumberFormatException e){
            return 0;
        }
       return loader;
   }
   public void showData(){
       Set s = getMoves().entrySet();
       Iterator it = s.iterator();
       while(it.hasNext()){
             Map.Entry m =(Map.Entry)it.next();

            // getKey is used to get key of Map
            String key=(String)m.getKey();

            // getValue is used to get value of key in Map
            MoveTraits value=(MoveTraits)m.getValue();

            System.out.println("Key :" + key + "\n Value:");
            value.showData();
       }
   }
   
   public void showMovesOnly(){
         Set s = getMoves().entrySet();
       Iterator it = s.iterator();
       while(it.hasNext()){
             Map.Entry m =(Map.Entry)it.next();

            // getKey is used to get key of Map
            String key=(String)m.getKey();

            System.out.println( key );
   }
   }
   
   public boolean checkMoveExist(String move){
       return getMoves().containsKey(move);
}
   public int calculateHitStun(String opponentMove){
       try{
           String fileName = "LevelData.txt";
       BufferedReader br = new BufferedReader(new FileReader(fileName));
       String line = "";
       
       for(int i = 0; i < 8; i++){
           line = br.readLine();
       }
       String[] tempString = line.split("\t");
      MoveTraits temporary = getMoves().get(opponentMove);
      int level = temporary.returnLevel();
       br.close();
       if (level > 5){
           return level; //allows for customizable hitstun. Just set it in the data file
       }
       
       else{
         return Integer.parseInt(tempString[level+1]);
       }
       }
       catch(Exception e){
           System.err.println("Can't read the level data!");
       }
            return 0;
   }
   
   public int getStartup(String move){
       MoveTraits temporary = getMoves().get(move);
       return temporary.returnStartup();
   }
   public boolean[] getSpecial(String move){
       MoveTraits temporary = getMoves().get(move);
       return temporary.returnSpecial();
   }
   public int getRecovery(String move){
       MoveTraits temporary = getMoves().get(move);
       return temporary.returnRecovery();
   }
   public int getActive(String move){
       MoveTraits temporary = getMoves().get(move);
       return temporary.returnActive();
   }
   public int getGuard(String move){
       MoveTraits temporary = getMoves().get(move);
       return temporary.returnGuardType();
   }
   public Vector<String> finalCalc(int hitStun, int activeFrames, int oppRecovery, int additional){
       Vector<String> temp = new Vector<String>();
       Set s = getMoves().entrySet();
       Iterator it = s.iterator();
       while(it.hasNext()){
           int totalFrames = 0;
             Map.Entry m =(Map.Entry)it.next();

            // getKey is used to get key of Map
            String key=(String)m.getKey();
            MoveTraits temporary = (MoveTraits) m.getValue();
            totalFrames = (hitStun * -1) + additional + activeFrames + (-1 * temporary.returnStartup()) + oppRecovery; //calculates if the opponent just stands there.
           
            if (totalFrames >= 0){
                temp.add(key);
                
            }
           
            
       }
       return temp;
   }
   
   public ArrayList<String> returnCancelability(String opponentMove) {
        Set s = getMoves().entrySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            int totalFrames = 0;
            Map.Entry m = (Map.Entry) it.next();

            // getKey is used to get key of Map
            String key = (String) m.getKey();
            if (key.equals(opponentMove)) {
           
                MoveTraits temporary = (MoveTraits) m.getValue();
                ArrayList<String> tempArrayList = temporary.returnMoveCancel();
                
                return tempArrayList;
            }
        }
        System.out.println("Could not find the move!");
        return null;
    }

public SortedMap<String,MoveTraits> getMoves() {
	return moves;
}

public void setMoves(SortedMap<String,MoveTraits> moves) {
	this.moves = moves;
}

  
}



