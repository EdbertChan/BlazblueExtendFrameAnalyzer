import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MoveTraits {

	int[] data = new int[5]; // data will have Level, Startup, Active, Recovery.
								// We use level for
	// determining blockstun (We will calculate frame advantage later in
	// conditional)
	// Determined by bottom chart

	boolean[] classification = new boolean[3]; // 1 is normal, 2 is special, 3 is super
	ArrayList<String> moveCancel = new ArrayList<String>(); // array list of
															// moves includes
															// throw and dash
	
	static int alpha = 0;
	
	public MoveTraits(String characterName, String passedMoveName,
			int[] passedData, boolean specNor[]) {
		System.arraycopy(passedData, 0, data, 0, 5);
		
		System.arraycopy(specNor, 0, classification, 0, 3);
		cancelableCheck(characterName, passedMoveName);

	}

	public void cancelableCheck(String characterName, String passedMoveName) {
		try {
			String fileName = characterName + "/" + characterName + " Revolver.csv";
			ArrayList<String> tempMoveCancel = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int lineNumber = 1;
			String line = br.readLine();
			while (line != null) {
				String[] cancelArray = line.split(",");
				if (cancelArray.length == 0) {
					break;
				} else if (cancelArray[0].equals(passedMoveName)) {

					for (int i = 1; i < cancelArray.length; i++) {
						String[] temporaryline = cancelArray[i].split(",");
						for (int j = 0; j < temporaryline.length; j++) {
							temporaryline[j] = temporaryline[j].replace("\"", "");
							if (temporaryline[j].startsWith(" ")){
								temporaryline[j] = temporaryline[j].replaceFirst(" ", "");
							}
							
							System.out.println(temporaryline[j] + j);
							addToMoveCancel(temporaryline[j]);
						}
					}

				}

				line = br.readLine();
			}

			br.close();

		} catch (Exception e) {
			System.err.println("error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	public void addToMoveCancel(String move) {
		//String modMove = move.replaceAll("[^\\p{L}\\p{N}\\.\\ ]", "");
		//String modMove = move;
		String modMove = move.replace("\"", "");
		if (modMove.startsWith(" ")) {
			modMove = modMove.substring(1);
		}
		moveCancel.add(modMove);
	}

	public void showData() {
		System.out.println("Level: " + data[0]);
		System.out.println("Startup: " + data[1]);
		System.out.println("Active: " + data[2]);
		System.out.println("Recovery: " + data[3]);
		if (classification[1] == true) {
			System.out.println("This is a special attack");
		} else {
			System.out.println("This is a normal attack");
		}
		System.out.println();
	}
	
	

	public int returnLevel() {
		return data[0];
	}

	public int returnStartup() {
		return data[1];
	}

	public int returnActive() {
		return data[2];
	}

	public int returnRecovery() {
		return data[3];
	}
	public int returnGuardType() {
		return data[4];
	}

	public ArrayList<String> returnMoveCancel() {
		return moveCancel;
	}

	public boolean[] returnSpecial() {
		return classification;
	}
}
