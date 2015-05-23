package chinse_radical_learning;

import java.awt.Color;

import javax.swing.JPanel;

public abstract class Refreshable_JPanel extends JPanel{
	
	protected Chinese_Radical_Learning_Alpha canvas;
	
	public Refreshable_JPanel(Chinese_Radical_Learning_Alpha c){
		
		super();
		
		canvas = c;
		
		this.setBounds(200, 200, 960, 560);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
	}
	
	public abstract void refresh();
	
}
