package chinse_radical_learning;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class Presentation_Panel extends Refreshable_JPanel {

	private int unit;
	private int selected_radical;
	
	protected final int _radical_image_width = 150;
	protected final int _radical_image_height = 200;
	protected final int _radical_stroke_width = 100;
	protected final int _radical_stroke_height = 100;
	protected final int _radical_text_width = 800;
	protected final int _radical_text_height = 100;
	protected final int _radical_example_width = 80;
	protected final int _radical_example_height = 80;
	protected final int _max_examples = 3;
	protected final Font _radical_text_font = new Font("Calibri", Font.BOLD, 30);
	
	private JLabel radical_stroke_img;
	private JLabel radical_meaning_img;
	private JButton play_button;
	private JButton stop_button;
	private JLabel radical_text;
	private JLabel[] radical_example_img = new JLabel[_max_examples];
	private JLabel[] radical_example_text = new JLabel[_max_examples];
	
	private class Play_Button_Action_Listener implements ActionListener{
	
		public void actionPerformed(ActionEvent event) {
			String image_dir = Data.img_dir + "unit_" + String.valueOf(unit+1) + "/radical_stroke/radical_" + String.valueOf(selected_radical+1) + ".gif";
			ImageIcon image_icon = new ImageIcon(canvas.getClass().getResource(image_dir));
			radical_stroke_img.setIcon(image_icon);
		}
	}
	
	private class Stop_Button_Action_Listener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			int unit = canvas.get_unit();
			set_fisrt_frame_stroke_image(unit);
		}
	}
	
	
	public Presentation_Panel(Chinese_Radical_Learning_Alpha c){
		
		super(c);
		
		radical_stroke_img = new JLabel();
		radical_stroke_img.setBorder(LineBorder.createBlackLineBorder());
		radical_stroke_img.setBounds(80, 80, _radical_stroke_width, _radical_stroke_width);
		this.add(radical_stroke_img);
		
		radical_text = new JLabel();
		radical_text.setFont(_radical_text_font);
		radical_text.setForeground(Color.blue);
		radical_text.setBounds(250, 80, _radical_text_width, _radical_text_height);
		this.add(radical_text);
		
		play_button = new JButton("Play");
		play_button.setBounds(60, 190, 65, 30);
		play_button.addActionListener(new Play_Button_Action_Listener());
		this.add(play_button);
		
		stop_button = new JButton("Stop");
		stop_button.setBounds(130, 190, 65, 30);
		stop_button.addActionListener(new Stop_Button_Action_Listener());
		this.add(stop_button);
		
		radical_meaning_img = new JLabel();
		radical_meaning_img.setBorder(LineBorder.createBlackLineBorder());
		radical_meaning_img.setBounds(60, 280, _radical_image_width, _radical_image_height);
		this.add(radical_meaning_img);
		
		JLabel example_label = new JLabel("Examples:");
		example_label.setFont(new Font("Calibri", Font.BOLD, 26));
		example_label.setForeground(Color.RED);
		example_label.setBounds(250, 250, 150, 30);
		this.add(example_label);
		
		for (int i = 0; i < _max_examples; i++){
			radical_example_img[i] = new JLabel();
			radical_example_img[i].setBounds(250, 285 + i*_radical_example_height, _radical_example_width, _radical_example_height);
			//radical_example_img[i].setBorder(LineBorder.createBlackLineBorder());
			
			
			radical_example_text[i] = new JLabel(); 
			radical_example_text[i].setBounds(250+_radical_example_width+20, 285 + i * _radical_example_height, 500, _radical_example_height);
			radical_example_text[i].setFont(new Font("Calibri", Font.BOLD, 24));
			//radical_example_text[i].setBorder(LineBorder.createBlackLineBorder());
			
			this.add(radical_example_img[i]);
			this.add(radical_example_text[i]);
		}
		
	}
	
	private void set_fisrt_frame_stroke_image(int unit) {
		
		try {
			ImageReader reader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next();
			String image_dir = "bin/chinse_radical_learning/img/unit_" + String.valueOf(unit+1) + "/radical_stroke/radical_" + String.valueOf(selected_radical+1) + ".gif";
			InputStream input = new FileInputStream(image_dir);
			ImageInputStream ciis = ImageIO.createImageInputStream(input);
			reader.setInput(ciis, false);
			
			BufferedImage image = reader.read(0);
			
			radical_stroke_img.setIcon(new ImageIcon(image));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() {
		
		unit = canvas.get_unit();
		
		selected_radical = canvas.get_radical();
		
		set_fisrt_frame_stroke_image(unit);
	
		String image_dir = Data.img_dir + "unit_" + String.valueOf(unit+1) + "/radical_meaning/radical_" + String.valueOf(selected_radical+1) + ".jpg";
		ImageIcon image_icon = new ImageIcon(canvas.getClass().getResource(image_dir));
		Image_Resizer resizer = new Image_Resizer();
		Image img = resizer.resizeImage(image_icon.getImage(), _radical_image_width, _radical_image_height);
		image_icon = new ImageIcon(img);
		
		radical_text.setText("<html>Meaning: " + Data.radical_meanings[unit][selected_radical] + "<br>Common Position: " + Data.radical_position[unit][selected_radical]);
		
		radical_meaning_img.setIcon(image_icon);
		
		int examples = Data.radical_examples[unit][selected_radical];
		for (int i = 0; i < examples; i++){
			image_dir = Data.img_dir +  "unit_" + String.valueOf(unit+1) + "/radical_examples/radical_" + String.valueOf(selected_radical+1) +"_" + String.valueOf(i+1) + ".jpg";
			image_icon = new ImageIcon(canvas.getClass().getResource(image_dir));
			image_icon = new ImageIcon(resizer.resizeImage(image_icon.getImage(), _radical_example_width, _radical_example_height));
			
			radical_example_img[i].setIcon(image_icon);
			radical_example_text[i].setText(Data.radical_example_text[unit][selected_radical][i]);
			
			radical_example_img[i].setVisible(true);
			radical_example_text[i].setVisible(true);
		}
		
		for (int i = examples; i < _max_examples; i++){
			radical_example_img[i].setVisible(false);
			radical_example_text[i].setVisible(false);
		}
			
		
	}


}
