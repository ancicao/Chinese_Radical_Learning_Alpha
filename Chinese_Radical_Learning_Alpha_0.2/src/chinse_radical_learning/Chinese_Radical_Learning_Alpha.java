package chinse_radical_learning;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import java.awt.Font;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractListModel;

import java.awt.Color;

import javax.swing.ListSelectionModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JRadioButton;

import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridLayout;

/*
 * Main class for the system.
 */

public class Chinese_Radical_Learning_Alpha {

	private JFrame frame;
	
	private int selected_unit = -1;
	private int selected_step = -1;
	private int selected_radical = -1;
	
	private int _total_steps = 4;
	
	
	protected JButton[] step_buttons = new JButton[_total_steps];
	public Refreshable_JPanel[] step_panels = new Refreshable_JPanel[_total_steps];
	private JTextArea welcome_text;
	
	/*
	 * Class for action listener of step buttons
	 */
	private class Step_Button_Action_Listener implements ActionListener {

		private int button_index;
		private Chinese_Radical_Learning_Alpha canvas;
		
		public Step_Button_Action_Listener(int i, Chinese_Radical_Learning_Alpha c){
			button_index = i;
			canvas = c;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			canvas.set_step(button_index);
			canvas.highlight_step_button(button_index);
			canvas.display_canvas();
		}

	}
	
	/*
	 * Class for unit list model
	 */
	private class Unit_List_Model extends DefaultListModel{

		String[] values = new String[] {"Unit 1", "Unit 2", "Unit 3", "Unit 4", "Unit 5", "Unit 6"};
		
		public int getSize() {
			return values.length;
		}
		
		public Object getElementAt(int index) {
			return values[index];
		}
	}
	
	
	/*
	 * Class for unit list renderer 
	 */
	private class Unit_List_Renderer implements ListCellRenderer{
		
		protected Border noFocusBorder = LineBorder.createBlackLineBorder();

		protected TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder(),
		      "selected");

		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
			        isSelected, cellHasFocus);
			renderer.setBorder(cellHasFocus ? focusBorder : noFocusBorder);
			return renderer;   
		}
	}
	
	/*
	 * Class for List Selection Listener
	 */
	private class Unit_List_Selection_Listener implements ListSelectionListener {
		
		Chinese_Radical_Learning_Alpha canvas;
		
		public Unit_List_Selection_Listener(Chinese_Radical_Learning_Alpha c){
			this.canvas = c;
		}

		@Override
		public void valueChanged(ListSelectionEvent event) {
			
			if (!event.getValueIsAdjusting()){
				
				JList list = (JList) event.getSource();
				int unit = list.getSelectedIndex();

				canvas.set_step(0);
				canvas.set_unit(unit);
				canvas.highlight_step_button(0);
				canvas.display_canvas();
				
			}
			
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chinese_Radical_Learning_Alpha window = new Chinese_Radical_Learning_Alpha();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Chinese_Radical_Learning_Alpha() {
		initialize();
	}
	
	/*
	 * Accessors and Modifiers
	 */
	
	public void set_unit(int unit){	
		selected_unit = unit;
	}
	
	public void set_step(int step){
		selected_step = step;
	}
	
	public void set_radical(int radical){
		selected_radical = radical;
	}
	
	public int get_unit(){
		return selected_unit;
	}
	
	public int get_step(){
		return selected_step;
	}
	
	public int get_radical(){
		return selected_radical;
	}
	
	public JFrame get_frame(){
		return frame;
	}
	
	/*
	 * Event Handling methods
	 */
	
	public void highlight_step_button(int step){
		
		for (int i = 0; i < _total_steps; i++){
			if (i == step){
				step_buttons[i].setBackground(Color.LIGHT_GRAY);
			}
			else 
				step_buttons[i].setBackground(Color.PINK);
			step_buttons[i].setVisible(true);
		}
		
		if (step == _total_steps-1)
			step_buttons[3].setVisible(true);
		else
			step_buttons[3].setVisible(false);
		
		if (step == 0){
			step_buttons[1].setVisible(false);
			step_buttons[2].setVisible(false);
			step_buttons[3].setVisible(false);
		}
	}
	
	public void display_canvas() {
		welcome_text.setVisible(false);
		for (int i = 0; i < _total_steps; i++){
			if (step_panels[i] != null && i != selected_step)
				step_panels[i].setVisible(false);
			else if (step_panels[i] != null && i== selected_step){
				step_panels[i].setVisible(true);
				step_panels[i].refresh();
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1221, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JList unit_list = new JList();
		unit_list.setVisibleRowCount(6);
		unit_list.setForeground(Color.DARK_GRAY);
		unit_list.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		unit_list.setFont(new Font("Calibri", Font.BOLD, 30));
		unit_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		unit_list.setBackground(Color.PINK);
		unit_list.setBounds(34, 230, 100, 305);
		unit_list.setCellRenderer(new Unit_List_Renderer());
		unit_list.setModel(new Unit_List_Model());
		unit_list.addListSelectionListener(new Unit_List_Selection_Listener(this));
		frame.getContentPane().add(unit_list);
		
		JLabel unit_list_label = new JLabel("Unit List");
		unit_list_label.setFont(new Font("Cooper Black", Font.PLAIN, 24));
		unit_list_label.setBounds(24, 187, 122, 34);
		frame.getContentPane().add(unit_list_label);
		
		JLabel title_label = new JLabel("Chinese Radical Learning System");
		title_label.setForeground(new Color(0, 153, 255));
		title_label.setFont(new Font("Times", Font.BOLD, 40));
		title_label.setBounds(272, 0, 820, 73);
		frame.getContentPane().add(title_label);
		
		JLabel title_label_chinese = new JLabel("中文部首学习系统");
		title_label_chinese.setForeground(new Color(0, 153, 255));
		title_label_chinese.setFont(new Font("MS Song", Font.BOLD, 40));
		title_label_chinese.setBounds(439, 60, 349, 68);
		frame.getContentPane().add(title_label_chinese);
		
		/*
		 * Initialize buttons
		 */
		String[] button_labels = {"Overview", "Presentation", "Exercises", "Review"};
		for (int i = 0; i < _total_steps; i++){
			step_buttons[i] = new JButton(button_labels[i]);
			step_buttons[i].setFont(new Font("Times New Roman", Font.BOLD, 26));
			step_buttons[i].setForeground(Color.DARK_GRAY);
			step_buttons[i].setBounds(200+i*200, 150, 180, 35);
			step_buttons[i].addActionListener(new Step_Button_Action_Listener(i, this));
			step_buttons[i].setVisible(false);
			frame.getContentPane().add(step_buttons[i]);
		}
		
		step_panels[0] = new Overview_Panel(this);
		step_panels[0].setVisible(false);
		frame.getContentPane().add(step_panels[0]);
		
		step_panels[1] = new Presentation_Panel(this);
		step_panels[1].setVisible(false);
		frame.getContentPane().add(step_panels[1]);
		
		step_panels[2] = new Exercises_Panel(this);
		step_panels[2].setVisible(false);
		frame.getContentPane().add(step_panels[2]);
		
		step_panels[3] = new Review_Panel(this);
		step_panels[3].setVisible(false);
		frame.getContentPane().add(step_panels[3]);
		
		welcome_text = new JTextArea();
		welcome_text.setText("Welcome! Please select a unit to start.\n"
							 + "欢迎您！请选择一个单元。");
		welcome_text.setEditable(false);
		welcome_text.setFont(new Font("MS Song", Font.BOLD, 40));
		welcome_text.setOpaque(false);
		welcome_text.setBounds(300, 300, 800, 300);
		welcome_text.setLineWrap(true);
		welcome_text.setWrapStyleWord(true);
		welcome_text.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		welcome_text.setForeground(new Color(0, 153, 255));
		//welcome_text.setBorder(LineBorder.createGrayLineBorder());
		welcome_text.setVisible(true);
		frame.getContentPane().add(welcome_text);
		
		JLabel background_img = new JLabel("");
		background_img.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		Image_Resizer resizer = new Image_Resizer();
		ImageIcon icon = new ImageIcon(this.getClass().getResource(Data.img_dir + "background.jpg"));
		icon = new ImageIcon(resizer.resizeImage(icon.getImage(), background_img.getWidth(), background_img.getHeight()));
		background_img.setIcon(icon);
		frame.getContentPane().add(background_img);
		
		
	}
}
