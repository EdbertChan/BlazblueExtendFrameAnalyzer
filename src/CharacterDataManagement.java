import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

 public class CharacterDataManagement{
   private static String[] conditionNames = {"Standing", "Crouching", "Air", "Barrier", "Instant"}; //stand, crouch, air, barrier, instant, wiff
    private static Character CharacterYou = new Character("Ragna the Bloodedge");  
    private static Character CharacterOpponent = new Character("Ragna the Bloodedge");
    private static String opponentMove = "5A";
    private static String opponentCancelInto = "";
    private static boolean[] conditions = new boolean[6]; //conditions: stand, crouch, air, barrier, instant, wiff
    private static int[] conditionsAdded = {0, 2, 2, 1, -3}; //note: negative is in CharacterYou's favor.
    public static String[] characters = {"Arakune", "Bang Shishigami", "Carl Clover", "Hakumen", "Hazama", "Iron Tager", "Jin Kisaragi", "Lambda-11", "Litchi Faye-Ling", "Makoto Nanaya", "Mu-12", "Noel Vermillion", "Platinum the Trinity", "Rachel Alucard", "Ragna the Bloodedge", "Relius Clover", "Taokaka", "Tsubaki Yayoi", "Valkenhayn R. Hellsing"};
	public static void resetAll(){
		opponentMove = "5A";
		opponentCancelInto = "";
	}
    public static void checkGuardingLimitations(String move){
    	int temp = CharacterOpponent.getGuard(move);
    	//default is 0, cannot crouch will be 1, cannot stand will be 2, air barrier will be 3, unblockable will be 4
    	column1.reload();
    	column1.reEnableAll();
    	
    	if(temp == 1){
    		column1.changeCrouching(false);
    		//column1.changeStanding(true);
    		column1.enableCrouching(false);
    		
    	}
    	else if (temp == 2){
    		//column1.changeCrouching(true);
    		column1.changeStanding(false);
    		column1.enableStanding(false);
    		
    	}
    	else if (temp == 3){
    		column1.changeAirColor(Color.blue);
    		column1.changeBarrierColor(Color.blue);
    		if(column1.returnAir()){
    			column1.changeBarrier(true);
    		}
    	}
    	else if (temp == 4){
    		column1.enableAll(false);
    	}
    	column1.reload();
	}
    public static int yourGuardLimitations(){
    	return CharacterOpponent.getGuard(opponentMove);
    }
    public static String returnOpponentMove(){
    	return opponentMove;
    }
    public static void setConditions(int i, boolean tf){
    	conditions[i] = tf;
    	//future, put in updates for standing and crouching
    }
    public static void setOpponentMove(String move){
    	opponentMove = move;
    }
    public static void setCharacterYou(String newChar){
    	CharacterYou = new Character(newChar);
    }
    public static void setOpponentCancelMove(String move){
    	opponentCancelInto =move; 
    }
    public static void setCharacterOpponent(String newChar){
    	CharacterOpponent = new Character(newChar);
    }
    public static String[] returnConditionNames(){
    	return conditionNames;
    }
    public static Character returnCharacterYou(){
    	return CharacterYou;
    }
    public static Character returnCharacterOpponent(){
    	return CharacterOpponent;
    }
    public static Vector<String> noCanceling() {
    	  int hitStun = CharacterOpponent.calculateHitStun(opponentMove);
          int oppRecovery = CharacterOpponent.getRecovery(opponentMove);
          int additional = calculateAdditionalConditions();
          int activeFrames = CharacterOpponent.getActive(opponentMove);
        Vector<String> temp = CharacterYou.finalCalc(hitStun, activeFrames, oppRecovery, additional);
        return temp;
    }
    
    private static int calculateAdditionalConditions() {
        int additional = 0;
        //crouching will increase your recovery time by 2 frames, etc.
        for (int i = 0; i < conditions.length; i++) {
            if (conditions[i] == true) {

                additional -= (conditionsAdded[i]);
            }
            if (conditions[2] && conditions[4]) { //instant air block
                additional += 5; 
                // air increases by 2 and instant decreases by 3 (+1). Instant air will decrease by 6 (1+ 5)
            }
        }
       
        return additional;
    }
    
    public static Vector<String> canceling() {

        
        ArrayList<String> moveCancels = CharacterOpponent.returnCancelability(opponentMove);
        Collections.sort(moveCancels);
        
        Vector<String> opponentOptions = new Vector();
        for (int i = 0; i < moveCancels.size(); i++) { //iterates through all the cancel 

            if (moveCancels.get(i).equals("Special")) { //spits out special attacks
                //want to get all the special moves and interate through them
                SortedMap<String, MoveTraits> specialMoves = new TreeMap<String, MoveTraits>();


                Set s = CharacterOpponent.getMoves().entrySet();
                Iterator it = s.iterator();
                while (it.hasNext()) {
                    int totalFrames = 0;
                    Map.Entry m = (Map.Entry) it.next();

                    // getKey is used to get key of Map
                    String key = (String) m.getKey();
                    
                    if (CharacterOpponent.getSpecial(key)[1]) {    
                        opponentOptions.add(key);
                    }
                    
                }
            }
            
            if (moveCancels.get(i).equals("Super")) { //spits out super attacks
                //want to get all the special moves and interate through them
                SortedMap<String, MoveTraits> specialMoves = new TreeMap<String, MoveTraits>();


                Set s = CharacterOpponent.getMoves().entrySet();
                Iterator it = s.iterator();
                while (it.hasNext()) {
                    int totalFrames = 0;
                    Map.Entry m = (Map.Entry) it.next();

                    // getKey is used to get key of Map
                    String key = (String) m.getKey();
                    
                    if (CharacterOpponent.getSpecial(key)[2]) {    
                   
                        opponentOptions.add(key);
                    }
                    
                }
            }

            if (CharacterOpponent.checkMoveExist(moveCancels.get(i))) {
                
                opponentOptions.add(moveCancels.get(i));
                
           
                // (THis is what we need to achieve) CharacterYou.finalCalc(hitStun, activeFrames, opponentStartup, additional); (Still up for debate as a viable function)
                
                //CharacterYou.finalCalc(hitStun, 0, opponentStartup, additional);
               
            }
        }
        return opponentOptions;
    }
    
    public static Vector<String> showOptionsAfterCancel(){
    	//this time our opponent's recovery will be the next move's startup.
    	 int hitStun = CharacterOpponent.calculateHitStun(opponentMove);
         int startup = CharacterOpponent.getStartup(opponentCancelInto);
         int additional = calculateAdditionalConditions();
         int activeFrames = CharacterOpponent.getActive(opponentMove);
    	Vector<String> temp = CharacterYou.finalCalc(hitStun, activeFrames, startup, additional);
    	//System.out.println(temp.size());
    	return temp;
    }
 }