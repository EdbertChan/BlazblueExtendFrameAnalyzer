import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class column4{
	private Vector<String> opponentMoves = CharacterDataManagement.canceling();
	private Vector<String> yourMoves = (CharacterDataManagement.noCanceling());
	JPanel bottomPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JPanel column4Panel = new JPanel();
	 public JPanel createColumn4(){
		
		 
			column4Panel.setLayout(new BorderLayout());
			topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
			bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
			JPanel rowPanel = new JPanel();
			
			JScrollPane acrossScrollBar;
			//rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
			rowPanel.setLayout(new GridLayout(2,1,0,10));
			//rowPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
			
			
			cancelabilityPopulate(topPanel);
			
			
			
			yourOptions(bottomPanel);
		
			
			rowPanel.add(topPanel, BorderLayout.NORTH);
			rowPanel.add(bottomPanel, BorderLayout.NORTH);
			column4Panel.add(rowPanel);
			
			
	    
		return column4Panel;
	}
	 public void yourOptions(JPanel rowPanel){
		 try{
				JList listofMoves = new JList();
				rowPanel.add(new JLabel("If your opponent cancels, your options to interrupt him: "));
			//another list
				yourMoves = CharacterDataManagement.showOptionsAfterCancel();
				listofMoves.setListData(yourMoves);
				
				
				//listofMoves.setSize(100, 500);
				JScrollPane temp = new JScrollPane(listofMoves);
				
				rowPanel.add(temp);
				
				}
				catch(Exception f){
					  System.err.println("No Move selected " + f);
				}
	 }
	 
	 public void cancelabilityPopulate(JPanel rowPanel){
		 JList listofMoves = new JList();
			rowPanel.add(new JLabel("Your opponent can cancel into: "));
			
			opponentMoves = new Vector<String>(CharacterDataManagement.canceling());
			
			listofMoves.setListData(opponentMoves);
			
			
			listofMoves.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ListSelectionModel listSelectionModel = listofMoves.getSelectionModel();
			listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
			rowPanel.add(new JScrollPane(listofMoves));
			
	 }
	 
	 class SharedListSelectionHandler implements ListSelectionListener{
		 public void valueChanged(ListSelectionEvent e){
			//gets what your opponent will cancel into
			 ListSelectionModel temp = (ListSelectionModel)e.getSource();
				int selected = temp.getMaxSelectionIndex();
				try{
					String getter = opponentMoves.get(selected);
					CharacterDataManagement.setOpponentCancelMove(getter);
					bottomPanel.removeAll();
					screen.reload();
					column4Panel.validate();
					bottomPanel.validate();	
					yourOptions(bottomPanel);
					
					screen.reload();
					
			
			} catch(Exception f){
				System.err.println("ERROR AT COLUMN 4 Handling");
			}
			
		 }
	 }
	 }
	 


	 
	 

