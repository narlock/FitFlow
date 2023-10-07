package component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JPanel;

import io.FitFlowIO;
import model.Workout;

public class WorkoutSelectionPanel extends JPanel {

	private static final long serialVersionUID = -4918736426856073544L;
	private GridBagConstraints gbc;
	
	private List<Workout> workouts;
	
	public WorkoutSelectionPanel() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		workouts = FitFlowIO.readWorkouts();
	}
}
