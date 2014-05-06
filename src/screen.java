import javax.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

public class screen {
	public static JPanel mainPanel = new JPanel();
	public static column1 c1 = new column1();
	public static column2 c2 = new column2();
	public static column3 c3 = new column3();
	public static column4 c4 = new column4();
	public static JFrame frame = new JFrame(
			"BB Frame Analyzer by Visma. If you like the program, please donate to edbert.chan@yahoo.com!");

	public screen() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setSize(700, 700);

		mainPanel.setLayout(new GridLayout(1, 4, 5, 5));
		
		mainPanel.add(c1.createColumn1());
		mainPanel.add(c2.createColumn2());
		mainPanel.add(c3.createColumn3());
		mainPanel.add(c4.createColumn4());

		frame.add(mainPanel); // at end
		frame.setVisible(true);
		c1.reload();
	}

	public static void updatec2() {
		mainPanel.remove(1);
		mainPanel.remove(1);
		mainPanel.remove(1);
		c2 = new column2();
		mainPanel.add(c2.createColumn2()); // why?
		updatec3(true);
		reload();

	}

	public static void updatec3(boolean u2) {

		if (!u2) {
			mainPanel.remove(2);
			mainPanel.remove(2);
		}
		c3 = new column3();
		mainPanel.add(c3.createColumn3());
		updatec4(true);
		reload();
	}

	public static void updatec4(boolean u3) {
		if (!u3) {
			mainPanel.remove(3);
		}
		c4 = new column4();
		mainPanel.add(c4.createColumn4());
		reload();
	}

	public static void reload() {
		frame.validate();
	}

	public static void main(String[] args) {
		new screen();
	}

}
