import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
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

public class column3 {
	private static Vector<String> moves = new Vector<String>(
			CharacterDataManagement.noCanceling());

	public JPanel createColumn3() {
		JPanel column3Panel = new JPanel();
		column3Panel.setLayout(new BorderLayout());

		JPanel rowPanel = new JPanel();
		JScrollPane acrossScrollBar;
		rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));

		rowPanel.add(new JLabel("Your moves if he doesn't cancel: "));
		try {
			JList listofMoves = new JList();

			moves = new Vector<String>(CharacterDataManagement.noCanceling());

			listofMoves.setListData(moves);

			listofMoves.setVisibleRowCount(25);

			rowPanel.add(new JScrollPane(listofMoves));

		} catch (Exception f) {
			System.err.println("No Move selected " + f);
		}

		column3Panel.add(rowPanel, BorderLayout.NORTH);

		return column3Panel;
	}

}
