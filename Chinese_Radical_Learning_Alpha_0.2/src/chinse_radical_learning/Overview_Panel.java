package chinse_radical_learning;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Overview_Panel extends Refreshable_JPanel{

	private int unit;
	
	protected final int _radical_image_width = 150;
	protected final int _radical_image_height = 200;
	protected int radicals = 4;
	
	protected JButton[] radical_buttons = new JButton[radicals];
	protected JLabel[] radical_imgs = new JLabel[radicals];
	protected JLabel[] radical_meanings = new JLabel[radicals];
	protected JLabel unit_title;
	
	private class Raidcal_Button_Action_Listener implements ActionListener{

		public int button_index;
		
		public Raidcal_Button_Action_Listener(int i){
			button_index = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			canvas.set_step(1);
			canvas.highlight_step_button(1);
			canvas.set_radical(button_index);
			canvas.display_canvas();
		}
		
	}
	
	public Overview_Panel(Chinese_Radical_Learning_Alpha c){
		
		super(c);
		
		Font title_font = new Font("Calibri", Font.BOLD, 32);
		Font radical_font = new Font("MS Song", Font.BOLD, 20);
		
		unit_title = new JLabel();
		unit_title.setBounds(30, 10, 200, 40);
		unit_title.setFont(title_font);
		this.add(unit_title);
		
		for (int i=0; i<radicals; i++){
			
			radical_buttons[i] = new JButton();
			radical_buttons[i].setBackground(Color.PINK);
			radical_buttons[i].setBounds(100+i*220, 70, 55, 55);
			radical_buttons[i].setForeground(Color.DARK_GRAY);
			radical_buttons[i].setFont(radical_font);
			radical_buttons[i].addActionListener(new Raidcal_Button_Action_Listener(i));
			this.add(radical_buttons[i]);
			
			radical_imgs[i] = new JLabel();
			radical_imgs[i].setBorder(LineBorder.createBlackLineBorder());
			radical_imgs[i].setBounds(60+i*220, 150, _radical_image_width, _radical_image_height);
			this.add(radical_imgs[i]);
			
			radical_meanings[i] = new JLabel();
			radical_meanings[i].setBounds(80+i*220, 400, 150, 40);
			radical_meanings[i].setFont(radical_font);
			this.add(radical_meanings[i]);
		}
	}
	
	public void refresh(){
		
		unit = canvas.get_unit();
		
		unit_title.setText(Data.unit_titles[unit]);
		
		for (int i=0; i<radicals; i++){
			
			radical_buttons[i].setText(Data.radicals[unit][i]);
			
			String image_dir = Data.img_dir + "unit_" + String.valueOf(unit+1) + "/radical_meaning/radical_" + String.valueOf(i+1) + ".jpg";
			ImageIcon image_icon = new ImageIcon(canvas.getClass().getResource(image_dir));
			Image_Resizer resizer = new Image_Resizer();
			Image img = resizer.resizeImage(image_icon.getImage(), _radical_image_width, _radical_image_height);
			image_icon = new ImageIcon(img);
					
			radical_imgs[i].setIcon(image_icon);
			
			radical_meanings[i].setText(Data.radical_meanings[unit][i]);
		}
		
	}

}
