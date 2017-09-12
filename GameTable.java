import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
class GameTable extends JPanel {
	
	private JTable board;
	private Minesweeper game;
	
	public GameTable(int r, int c) {
		setVisible(true);
		game = new Minesweeper(r, c);
		board = new JTable(r,c);
		add(new JLabel("Test"));
		add(board);
		
	}
	
	public void makeBoard(Tile[][] oBoard) {
		for(int r=0; r<oBoard.length; r++)
		{
			for(int c=0; c<oBoard[r].length; c++)
			{
				JButton to;
				//add all icons
				if(oBoard[r][c].isFlagged())
					to = new JButton(new ImageIcon("icons/flagIcon.jpg"));
			}
		}
	}

}
