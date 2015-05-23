package chinse_radical_learning;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;

public class Review_Panel extends Refreshable_JPanel {
	
	private JLabel image;
	protected int img_index = 1;
	protected int images = 4;
	
	/*private int _image_width = 700;
	private int _image_height = 500;
	
	private JLabel image;
	private JTextArea feedback_text;
	private JLabel[] radicals = new JLabel[4];
	private JLabel[] target_area = new JLabel[6];
	
	private int unit;
	
	private Font Chinese_font = new Font("MS Song", Font.BOLD, 40);
	private Font English_font = new Font("Calibri", Font.BOLD, 20);
	
	private class Radical_Mouse_Listener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			JComponent comp = (JComponent) e.getSource();
	        TransferHandler handler = comp.getTransferHandler();
	        handler.exportAsDrag(comp, e, TransferHandler.COPY);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}*/
	
	
	public Review_Panel(Chinese_Radical_Learning_Alpha c) {
		
		super(c);
		
		JButton next_button = new JButton("Next");
		next_button.setBounds(820, 500, 80, 50);
		
		class Next_Button_Listener implements ActionListener{

			private Review_Panel c;
			
			public Next_Button_Listener(Review_Panel c){
				this.c = c;
			}
			
			public void actionPerformed(ActionEvent e) {
				c.img_index++;
				if (c.img_index > images)
					c.img_index = 1;
				c.refresh();
			}
			
		}
		
		next_button.addActionListener(new Next_Button_Listener(this));
		this.add(next_button);
		
		Image_Resizer resizer = new Image_Resizer();
		image = new JLabel();
		image.setBounds(20, 20, 850, 550);
		this.add(image);
		
		
		/*image = new JLabel();
		image.setBounds(20, 20, _image_width, _image_height);
		image.setBorder(LineBorder.createGrayLineBorder());
		this.add(image);
		
		JTextArea question = new JTextArea("Drag the radical to other components in the picture to form a character:");
		question.setEditable(false);
		question.setBounds(725, 20, 200, 120);
		question.setBorder(LineBorder.createGrayLineBorder());
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(English_font);
		this.add(question);
		
		String[] radical_text = {"Øé", "Å®", "¸¸", "×Ó"};
		
		for (int i=0; i < 4; i++){
			radicals[i] = new JLabel(radical_text[i]);
			radicals[i].setBounds(725+55*i, 150, 50, 50);
			radicals[i].setBorder(LineBorder.createGrayLineBorder());
			radicals[i].setFont(Chinese_font);
			radicals[i].setTransferHandler(new TransferHandler(radical_text[i]));
			radicals[i].addMouseListener(new Radical_Mouse_Listener());
			//radicals[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			this.add(radicals[i]);
		}
		
		feedback_text = new JTextArea();
		feedback_text.setEditable(false);
		feedback_text.setBounds(725, 220, 200, 300);
		feedback_text.setBorder(LineBorder.createGrayLineBorder());
		feedback_text.setLineWrap(true);
		feedback_text.setWrapStyleWord(true);
		feedback_text.setFont(English_font);
		this.add(feedback_text);*/
		
	}

	@Override
	public void refresh() {
		
		int unit = canvas.get_unit();
		
		Image_Resizer resizer = new Image_Resizer();
		String image_dir = Data.img_dir + "unit_" + String.valueOf(unit+1) + "/review/"+String.valueOf(img_index)+".jpg";
		ImageIcon icon = new ImageIcon(canvas.getClass().getResource(image_dir));
		icon = new ImageIcon(resizer.resizeImage(icon.getImage(), image.getWidth(), image.getHeight()));
		image.setIcon(icon);
		
		
	}

}
