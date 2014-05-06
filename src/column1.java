import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class column1{
	JPanel rowPanel = new JPanel();
	private static Checkbox standing = new Checkbox(CharacterDataManagement.returnConditionNames()[0]);
	private static Checkbox crouching = new Checkbox(CharacterDataManagement.returnConditionNames()[1]);
	private static Checkbox air = new Checkbox(CharacterDataManagement.returnConditionNames()[2]);
	private static Checkbox barrier = new Checkbox(CharacterDataManagement.returnConditionNames()[3]);
	private static Checkbox instant =  new Checkbox(CharacterDataManagement.returnConditionNames()[4]);
	JPanel column1Panel = new JPanel();
	private static JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	 public JPanel createColumn1(){
		
		column1Panel.setLayout(new BorderLayout());
	//	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		
		//rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
		//adds conditions
		rowPanel.setLayout(new GridLayout(5,1,0,10));
		
		topPanel.add(new JLabel("Conditions: "));
		   addConditions();
		//adds your character
		bottomPanel.add(new JLabel("Your character: "));
		bottomPanel.add(addYourCharacter());
		
		//adds your opponent
		bottomPanel.add(new JLabel("Opponent: "));
		bottomPanel.add(addOpponent());
		
		
		
		rowPanel.add(topPanel, BorderLayout.NORTH);
		rowPanel.add(bottomPanel, BorderLayout.NORTH);
		column1Panel.add(rowPanel);
		
	    
	    
		return column1Panel;
	}
public void addConditions(){
	standing.addItemListener(new ChangeConditions());
	crouching.addItemListener(new ChangeConditions());
	air.addItemListener(new ChangeConditions());
	barrier.addItemListener(new ChangeConditions());
	instant.addItemListener(new ChangeConditions());
		topPanel.add(standing);
		topPanel.add(crouching);
		topPanel.add(air);
		topPanel.add(barrier);
		topPanel.add(instant);		
}

class ChangeConditions implements ItemListener{
	
	public void itemStateChanged(ItemEvent e){
		
		Checkbox tempBox = (Checkbox)e.getSource();
		String boxName = (String)tempBox.getLabel();
		int j = 0;
		for(int i = 0; i < CharacterDataManagement.returnConditionNames().length; i++){
			if(boxName.equals(CharacterDataManagement.returnConditionNames()[i])){
				j = i;
			}
			
		}
		
		
		if(e.getStateChange() == ItemEvent.SELECTED){
			CharacterDataManagement.setConditions(j, true);

		} else{
			CharacterDataManagement.setConditions(j, false);
		}
		checkLimitations();
		
		
		screen.updatec3(false);
		screen.updatec4(false); 
		
	}

}
public static void checkLimitations(){
	
	if(returnAir() && CharacterDataManagement.yourGuardLimitations() == 3){
		changeBarrier(true);
	}
	
}



//adding character
public static JComboBox addYourCharacter(){
	
	JComboBox listofChar = new JComboBox(CharacterDataManagement.characters);
	listofChar.setEditable(true);
	listofChar.setSelectedIndex(14);
	listofChar.addActionListener(new ActionSetPlayer());
	return listofChar;
}
	static class ActionSetPlayer implements ActionListener{
		public void actionPerformed (ActionEvent e){
			CharacterDataManagement.resetAll();
			JComboBox name = (JComboBox)e.getSource();
			String charName = (String)name.getSelectedItem();
			CharacterDataManagement.setCharacterYou(charName);
			screen.updatec2();
			
			screen.reload();
		}
	}
	

public static JComboBox addOpponent(){
	
	JComboBox listofChar = new JComboBox(CharacterDataManagement.characters);
	listofChar.setEditable(true);
	listofChar.setSelectedIndex(14);
	
	listofChar.addActionListener(new ActionSetOpponent());
	return listofChar;
}
static class ActionSetOpponent implements ActionListener{
	public void actionPerformed (ActionEvent e){
		CharacterDataManagement.resetAll();
		JComboBox name = (JComboBox)e.getSource();
		String charName = (String)name.getSelectedItem();
		
		CharacterDataManagement.setCharacterOpponent(charName);
		
		screen.updatec2();
		screen.reload();
		
	}
}
public static void changeAirColor(Color g){
	air.setForeground(g);
}
public static void changeBarrierColor(Color g){
	barrier.setForeground(g);
}

public static void changeStandingColor(Color g){
	standing.setForeground(g);
}
public static void changeCrouchingColor(Color g){
	crouching.setForeground(g);
}
public static void reEnableAll(){
	barrier.setForeground(Color.BLACK);
	air.setForeground(Color.BLACK);
	barrier.setEnabled(true);
	standing.setEnabled(true);
	crouching.setEnabled(true);
	air.setEnabled(true);
	instant.setEnabled(true);
	topPanel.validate();
}
public static void resetAll(){
	instant.setState(false);
	barrier.setState(false);
	standing.setState(false);
	crouching.setState(false);
	air.setState(false);
}
public static void reload(){
	topPanel.validate();
}

public static boolean returnBarrier(){
	return barrier.getState();
}
public static boolean returnAir(){
	return air.getState();
}
public static boolean returnStanding(){
	return standing.getState();
}
public static boolean returnCrouching(){
	return crouching.getState();
}


public static void changeBarrier(boolean tf){
	barrier.setState(tf);
}

public static void changeStanding(boolean tf){
	standing.setState(tf);
	
}
public static void changeCrouching(boolean tf){
	crouching.setState(tf);
	
	
}
public static void changeAir(boolean tf){
	air.setState(tf);	
}

public static void enableAir(boolean tf){
	air.setEnabled(tf);
}
public static void enableCrouching(boolean tf){
	crouching.setEnabled(tf);
}
public static void enableStanding(boolean tf){
	standing.setEnabled(tf);
}
public static void enableInstant(boolean tf){
	instant.setEnabled(tf);
}
public static void enableBarrier(boolean tf){
	barrier.setEnabled(tf);
}
public static void enableAll(boolean tf){
	enableInstant(tf);
	enableBarrier(tf);
	enableStanding(tf);
	enableCrouching(tf);
	enableAir(tf);
}


}