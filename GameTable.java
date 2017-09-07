import javax.swing.*;
class GameTable extends JFrame {
	public static void main(String[] args) {
		new GameTable();
	}
	public GameTable () {
		setSize(500,500);
		add(new show(10,10));
		setVisible(true);
	}
	public class show extends JTable{
		
		private Minesweeper game;
		
		
		
	}

}
