import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import java.io.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class column2 {
	private static Vector<String> moves = new Vector<String>(
			CharacterDataManagement.returnCharacterOpponent().getMoves().keySet());
	private JPanel column2Panel = new JPanel();
	private JPanel rowPanel = new JPanel();
	private JScrollPane acrossScrollBar;
	//JLabel picture = new JLabel();
	private JPanel topPanel = new JPanel();
	private ImagePanel bottomPanel = new ImagePanel();
	public JPanel createColumn2() {
		
		
		column2Panel.setLayout(new BorderLayout());

		
	
		rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		bottomPanel.setLayout(new GridLayout(1,1));
		
		System.out.println(bottomPanel.getMaximumSize().height);
		
		addOpponentMoves(topPanel);
		
		 
		   
	   updatePicture(bottomPanel, CharacterDataManagement.returnOpponentMove());
	
		//bottomPanel.add(picture);
		rowPanel.add(topPanel);
		rowPanel.add(bottomPanel);
		
		column2Panel.add(rowPanel, BorderLayout.NORTH);
		
		return column2Panel;
	}
	
	
	
	public void updatePicture(ImagePanel panel, String name){
		rowPanel.remove(bottomPanel);
		String newName = fileNameProcess(name);
		String filename = CharacterDataManagement.returnCharacterOpponent().returnCharacterName()+ "/"+ newName +".png";
		panel.setImage(filename);
		rowPanel.add(panel);
		panel.revalidate();
		//ImageIcon icon = new ImageIcon();
			//Dimension d = picture.getMaximumSize();
		//	Image originalImage = icon.getImage();
		//	Image scaledImage = originalImage.getScaledInstance(panel.getWidth(),panel.getHeight(),Image.SCALE_SMOOTH);
		//double ratio = (double) icon.getIconWidth() /icon.getIconHeight();
		//int scaledWidth = (int)(ratio * panel.getHeight());
		
		
		//picture.setIcon(icon);
	    
	}
	public String fileNameProcess(String name){
		String temp = name;
		if(name.contains(">")){
			 temp = name.replace(">", "&gt;");
		
		}
		return temp;
	}
	public void addOpponentMoves(JPanel panel){
		
		JList listofMoves = new JList();
		panel.add(new JLabel("Your opponent's moves: "));
		moves = new Vector<String>(
				CharacterDataManagement.returnCharacterOpponent().getMoves()
						.keySet());

		listofMoves.setListData(moves);

		listofMoves.setVisibleRowCount(10);
		listofMoves.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = listofMoves.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new SharedListSelectionHandler());

		panel.add(new JScrollPane(listofMoves));

	}
	class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel temp = (ListSelectionModel) e.getSource();
			int selected = temp.getMaxSelectionIndex();
			
			try {
				
				String getter = moves.get(selected);
				CharacterDataManagement.setOpponentMove(getter);
								//topPanel.validate();
				//add the condition modifiers here
				
				updatePicture(bottomPanel, getter);
			    
				screen.updatec3(false); 
				screen.updatec4(false); 
				CharacterDataManagement.checkGuardingLimitations(getter);
			} catch (Exception f) {
				System.err.println("Move not found in column2" + f);
			}
		}
	}

}
