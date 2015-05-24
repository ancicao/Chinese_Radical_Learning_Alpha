package chinse_radical_learning;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * This class manages the panel showing exercises.
 */

public class Exercises_Panel extends Refreshable_JPanel {
	
	private int _question_width = 520;
	private int _question_height = 35;
	private int _ex_image_width = 300;
	private int _ex_image_height = 300;
	private int _max_questions = 8;
	
	private int unit;
	private int selected_radical;
	private int question_index = 0;
	private String image_dir;
	private int[] correct_area = {0, _ex_image_width/2, 0, _ex_image_height};
	
	private JLabel question;
	private JList yes_no_buttons;
	private JLabel ex_image;
	private JButton next_button;
	private JButton previous_button;
	private Feedback_Panel feedback_panel;
	
	private Font question_font =
			new Font("MS Song", Font.BOLD, 20);
	private Font question_english_font = new Font("Calibri", Font.BOLD, 18);
	
	private class RadioListSelectionListener implements ListSelectionListener{
		
		public void valueChanged(ListSelectionEvent event) {
			if (!event.getValueIsAdjusting()){
				
				JList list = (JList) event.getSource();
				int selected = list.getSelectedIndex();

				for (int i=0; i<list.getModel().getSize(); i++){
					RadioListItem item = (RadioListItem) list.getModel().getElementAt(i);
					if (i == selected)
						item.setSelected(true);
					else
						item.setSelected(false);
				}
				
				int correct_answer = Data.ex_answers[unit][selected_radical][question_index];
				String text = "";
				
				if ((correct_answer == 0 && selected == 0) || (correct_answer != 0 && selected ==  1))
					text += "Correct answer!";
				else
					text += "Sorry, incorrect answer!";
					
				if (correct_answer == 0){
					ex_image.setCursor(new Cursor(Cursor.HAND_CURSOR));
					if (ex_image.getMouseListeners().length == 0){
						Ex_Image_Mouse_Listener listener = new Ex_Image_Mouse_Listener();
						ex_image.addMouseListener(listener);
						ex_image.addMouseMotionListener(listener);
					}
					text += "\nIt contains the radical.\nPlease identify the radical by clicking it.";
				}
				else if (correct_answer == 1)
						text += "\nIt does not contain the radical.";
				else
						text += "\nAlthough it contains the " + Data.radicals[unit][selected_radical] +
								" component, it does not constitute as a radical, as it appears at a wrong position.";
				
				feedback_panel.provide_feedback(text);
				yes_no_buttons.setEnabled(false);
			}
		}
	}
	
	private class Ex_Image_Mouse_Listener implements MouseMotionListener, MouseListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {}

		@Override
		public void mouseMoved(MouseEvent event) {
			Image_Resizer resizer = new Image_Resizer();
			ImageIcon icon;
			if (event.getX() > correct_area[0] && event.getX() < correct_area[1] 
				&& event.getY() > correct_area[2] && event.getY() < correct_area[3]){
				icon = new ImageIcon(canvas.getClass().getResource(image_dir + "2.jpg"));	
				icon = new ImageIcon(resizer.resizeImage(icon.getImage(), _ex_image_width, _ex_image_height));
			}
			else{
				icon = new ImageIcon(canvas.getClass().getResource(image_dir + "3.jpg"));	
				icon = new ImageIcon(resizer.resizeImage(icon.getImage(), _ex_image_width, _ex_image_height));
			}
			ex_image.setIcon(icon);
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			if (Data.ex_answers[unit][selected_radical][question_index] != 0)
				return;
			
			Image_Resizer resizer = new Image_Resizer();
			ImageIcon icon;
			
			if (event.getX() > correct_area[0] && event.getX() < correct_area[1] 
				&& event.getY() > correct_area[2] && event.getY() < correct_area[3]){
				icon = new ImageIcon(canvas.getClass().getResource(image_dir + "4.jpg"));
				icon = new ImageIcon(resizer.resizeImage(icon.getImage(), _ex_image_width, _ex_image_height));
				ex_image.setIcon(icon);
				remove_ex_image_liseners();
				
				feedback_panel.provide_feedback("Correct!\nPlease infer the meaning of the character:");
				feedback_panel.show_inference_questions(unit, selected_radical, question_index);
			}
			else
				feedback_panel.provide_feedback("Sorry, incorrect!\nPlease try it again.");
		}

		@Override
		public void mouseEntered(MouseEvent event) {}

		@Override
		public void mouseExited(MouseEvent event) {
			Image_Resizer resizer = new Image_Resizer();
			ImageIcon icon = new ImageIcon(canvas.getClass().getResource(image_dir + "1.jpg"));	
			icon = new ImageIcon(resizer.resizeImage(icon.getImage(), _ex_image_width, _ex_image_height));
			ex_image.setIcon(icon);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}	
	}
	
	
	private class Previous_Next_Button_Listener implements ActionListener{
		private boolean next_button;
		
		public Previous_Next_Button_Listener(boolean f){
			next_button = f;
		}
		
		public void actionPerformed(ActionEvent event) {
			if (next_button)
				refresh((question_index + 1) % _max_questions);
			else{
				if ((question_index - 1) < 0)
					refresh(_max_questions-1);
				else
					refresh(question_index - 1);
			}
		}
	}
	
	private void remove_ex_image_liseners(){
		MouseListener[] listeners = ex_image.getMouseListeners();
		MouseMotionListener[] motion_listeners = ex_image.getMouseMotionListeners();
		
		for (int i=0; i<listeners.length; i++)
			ex_image.removeMouseListener(listeners[i]);
		
		for (int i=0; i<motion_listeners.length; i++)
			ex_image.removeMouseMotionListener(motion_listeners[i]);
		
		ex_image.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public Exercises_Panel(Chinese_Radical_Learning_Alpha c){
		
		super(c);
		
		question = new JLabel();
		question.setFont(question_font);
		question.setBounds(30, 30, _question_width, _question_height);
		//question.setBorder(LineBorder.createGrayLineBorder());
		this.add(question);
		
		yes_no_buttons = new JList(new RadioListItem[] {new RadioListItem("Yes"), new RadioListItem("No")});
		//yes_no_buttons.setBorder(LineBorder.createGrayLineBorder());
		yes_no_buttons.setBounds(35+_question_width, 30, 200, _question_height);
		yes_no_buttons.setCellRenderer(new Radio_Button_List_Renderer());
		yes_no_buttons.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		yes_no_buttons.setVisibleRowCount(-1);
		yes_no_buttons.setFont(question_english_font);
		yes_no_buttons.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		yes_no_buttons.addListSelectionListener(new RadioListSelectionListener());
		this.add(yes_no_buttons);
		
		ex_image = new JLabel();
		ex_image.setBounds(30 , 80, _ex_image_width, _ex_image_height);
		ex_image.setBorder(LineBorder.createGrayLineBorder());
		this.add(ex_image);
		
		next_button = new JButton("Next");
		next_button.setFont(question_english_font);
		next_button.setForeground(Color.BLUE);
		next_button.addActionListener(new Previous_Next_Button_Listener(true));
		next_button.setBounds(800, 500, 80, 40);
		this.add(next_button);
		
		previous_button = new JButton("Previous");
		previous_button.setFont(question_english_font);
		previous_button.setForeground(Color.BLUE);
		previous_button.addActionListener(new Previous_Next_Button_Listener(false));
		previous_button.setBounds(650, 500, 120, 40);
		this.add(previous_button);
		
		feedback_panel = new Feedback_Panel(this);
		this.add(feedback_panel);
		
	}

	public void refresh(int qi) {
		
		if (qi >= 0)
			question_index = qi;
		
		unit = canvas.get_unit();
		selected_radical = canvas.get_unit();
		
		yes_no_buttons.clearSelection();
		yes_no_buttons.setEnabled(true);
		
		if (question_index == 0)
			previous_button.setVisible(false);
		else
			previous_button.setVisible(true);
		
		if (question_index == _max_questions - 1){
			next_button.setVisible(false);
			canvas.step_buttons[3].setVisible(true);
		}
		else{
			next_button.setVisible(true);
			canvas.step_buttons[3].setVisible(false);
		}
		
		remove_ex_image_liseners();
		
		question.setText("Does the following character contain " + Data.radicals[unit][selected_radical] + " radical?");
		
		Image_Resizer resizer = new Image_Resizer();
		image_dir = Data.img_dir + "unit_" + String.valueOf(unit+1) + 
						   "/radical_exercises/radical_" + String.valueOf(selected_radical+1) +
						   "/ex" + String.valueOf(question_index+1) + "/";
		
		ImageIcon icon = new ImageIcon(canvas.getClass().getResource(image_dir + "1.jpg"));	
		icon = new ImageIcon(resizer.resizeImage(icon.getImage(), _ex_image_width, _ex_image_height));
		ex_image.setIcon(icon);
		
		feedback_panel.clear();
	}

	@Override
	public void refresh() {
		refresh(-1);
	}

}
