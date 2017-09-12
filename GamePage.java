import javax.swing.*;


public class GamePage extends JFrame{
	
	private int row, col;
	private JPanel game, main;
	
	public GamePage(int r, int c) {
		setVisible(true);
		setSize(400,400);
		main = new JPanel();
		add(main);
		game = new GameTable(r,c);
		main.add(game);
		
		
		
		
	}
	
	public static void main(String[] args) {
		new GamePage(10, 10);
	}

}
