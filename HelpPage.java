import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HelpPage extends JFrame{
	
	JPanel main;
	JScrollPane scroll;
	
	public HelpPage() {
		setSize(300,300);
		main = new JPanel();
		scroll = new JScrollPane();
		scroll.createHorizontalScrollBar();
		scroll.setPreferredSize(new Dimension(100, 100));
		add(main);
		main.add(scroll);
		
		scroll.add(new JTextArea(1000,1000));
		scroll.createHorizontalScrollBar();
		scroll.createVerticalScrollBar();
		setVisible(true);
	}
		
	public static void main(String[] args){
		new HelpPage();
	}
}
