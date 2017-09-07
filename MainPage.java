import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPage extends JFrame{
	
	private JPanel title, main;
	private JButton startGame;
	private ListenForButtons listener;
	
	public MainPage() {
		setTitle("Minesweeper");
		setSize(1000,500);
		
		main = new JPanel();
		add(main);
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		listener = new ListenForButtons();
		title = new JPanel();
		title.setLayout(new BoxLayout(title,BoxLayout.PAGE_AXIS));
		ImageIcon i = new ImageIcon("icons/minesweeperLogo.jpg");
		JLabel logo = new JLabel(i);
		main.add(logo);
		JLabel banner = new JLabel("Welcome to Minesweeper!");
		banner.setFont(new Font(banner.getFont().getName(), Font.PLAIN, 20));
		
		startGame = new JButton("Start game.");
		startGame.addActionListener(listener);
		
		title.add(banner);
		title.add(new JLabel("Made by Will Pearl"));
		
		main.add(title);
		main.add(startGame);
		
		pack();
		setVisible(true);
		
	}
	
	private class ListenForButtons implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Button pressed");
			if (e.getSource() == startGame)
			{
				dispose();
				new GameSetup();
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		new MainPage();
	}
}
