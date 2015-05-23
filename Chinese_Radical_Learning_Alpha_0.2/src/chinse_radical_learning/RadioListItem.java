package chinse_radical_learning;

public class RadioListItem{
	private String label;
	private boolean isSelected = false;
	
	public RadioListItem(String label){
		this.label = label;
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}
	
	public String toString(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
}