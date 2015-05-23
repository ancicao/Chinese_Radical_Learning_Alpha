package chinse_radical_learning;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Feedback_Panel extends JPanel{
	
	private Exercises_Panel canvas;
	
	private int unit;
	private int radical;
	private int question;
	
	private Font feedback_font = new Font("MS Song", Font.BOLD, 22);
	
	private JTextArea feedback_text;
	private JList inference_exercises;
	
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
				
				if (selected < 0)
					return;
				
				if (Data.inf_ex_answers[unit][radical][question] == selected + 1){
					provide_feedback("Correct!");
					inference_exercises.setEnabled(false);
				}
				else
					provide_feedback("Incorrect answer. Please think about the radical meaning and try it again.");
			}
		}
	}
	
	public void provide_feedback(String text){
		feedback_text.setText(text);
	}
	
	public void clear(){
		feedback_text.setText("");
		inference_exercises.setVisible(false);
	}
	
	public void show_inference_questions(int unit, int radical, int question){
		this.unit = unit;
		this.radical = radical;
		this.question = question;
		for (int i=0; i < inference_exercises.getModel().getSize(); i++){
			RadioListItem item = (RadioListItem) inference_exercises.getModel().getElementAt(i);
			item.setLabel(Data.inf_ex_texts[unit][radical][question][i]);
		}
		inference_exercises.clearSelection();
		inference_exercises.setEnabled(true);
		inference_exercises.setVisible(true);
	}
	
	public Feedback_Panel(Exercises_Panel c){
		
		super();
		
		canvas = c;
		
		this.setLayout(null);
		this.setBounds(400, 80, 500, 400);
		this.setBackground(Color.WHITE);
		//this.setBorder(LineBorder.createGrayLineBorder());
		
		feedback_text = new JTextArea();
		feedback_text.setBounds(20, 20, 470, 120);
		//feedback_text.setBorder(LineBorder.createGrayLineBorder());
		feedback_text.setLineWrap(true);
		feedback_text.setWrapStyleWord(true);
		feedback_text.setAlignmentX(LEFT_ALIGNMENT);
		feedback_text.setFont(feedback_font);
		feedback_text.setForeground(Color.RED);
		feedback_text.setEditable(false);
		this.add(feedback_text);
		
		RadioListItem[] items = new RadioListItem[4];
		for (int i = 0; i < items.length; i++)
			items[i] = new RadioListItem("");
		
		inference_exercises = new JList(items);
		inference_exercises.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		inference_exercises.setVisibleRowCount(-1);
		inference_exercises.setCellRenderer(new Radio_Button_List_Renderer());
		//inference_exercises.setBorder(LineBorder.createGrayLineBorder());
		inference_exercises.setBounds(20, 150, 380, 260);
		inference_exercises.setFont(feedback_font);
		inference_exercises.setForeground(Color.DARK_GRAY);
		inference_exercises.setLayoutOrientation(JList.VERTICAL);
		inference_exercises.addListSelectionListener(new RadioListSelectionListener());
		inference_exercises.setVisible(false);
		this.add(inference_exercises);
	}

}
