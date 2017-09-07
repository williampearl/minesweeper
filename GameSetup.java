import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameSetup extends JFrame{
	
	private JPanel main, options, buttons;
	private JTextField xSize, ySize, numBomb;
	private JButton start, showHelp;
	private ListenForButtons listener;
	
	
	public GameSetup() {
		setTitle("Minesweeper");
		setSize(500,500);
		main = new JPanel();
		options = new JPanel();

		
		
		JLabel title = new JLabel("Setup");
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));
		
		main.add(title);
		
		listener = new ListenForButtons();
		xSize = new JTextField();
		ySize = new JTextField();
		numBomb = new JTextField();
		showHelp = new JButton("Show help.");
		showHelp.addActionListener(listener);
		start = new JButton("Start!");
		start.addActionListener(listener);
		
		
		
		options.setLayout(new GridLayout(4,2));
		options.add(new JLabel("X size",JLabel.RIGHT));
		options.add(xSize);
		options.add(new JLabel("Y size",JLabel.RIGHT));
		options.add(ySize);
		options.add(new JLabel("# bombs",JLabel.RIGHT));
		options.add(numBomb);
		options.add(showHelp);
		options.add(start);
		
		
		add(main);
		add(options);
		pack();
		setVisible(true);

	}
	
	private class ListenForButtons implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Button pressed");
			if (e.getSource() == start)
			{
				//add 0 < x <= 20
				if(xSize.getText().trim().isEmpty() || ySize.getText().trim().isEmpty() || numBomb.getText().trim().isEmpty() ||
				   !xSize.getText().trim().matches("[0-9]+") || !ySize.getText().trim().matches("[0-9]+") || !xSize.getText().trim().matches("[0-9]+")
				   ) {
					showError();

				} else {
					startGame();
				}
			}
			if (e.getSource() == showHelp)
			{
				HelpPage.open();
			}
			
		}
	}
	public void showError() {
		JOptionPane.showMessageDialog(null, 
				"All input fields MUST\n1)Contain only numbers(0-9)\n2)Be greater than 0\n3)Be less than or equal to 20", 
				"Input Error",
				JOptionPane.ERROR_MESSAGE);
	}
	public void startGame() {
		dispose();
		new GamePage();
	}
	
	public static void main(String[] args){
		new GameSetup();
	}
}
