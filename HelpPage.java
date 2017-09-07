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
	
	public HelpPage() {
		
	}
	
	public static void open() {
		new HelpPage();
	}
	
	public static void main(String[] args){
		open();
	}
}
