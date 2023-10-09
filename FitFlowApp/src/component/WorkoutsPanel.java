package component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import io.FitFlowIO;
import model.Workout;
import state.AppOverlayPanel;

public class WorkoutsPanel extends JPanel {

	private static final long serialVersionUID = -4649030831087608695L;
	private GridBagConstraints gbc;
	
	private AppOverlayPanel aop;
	private List<Workout> workouts;
	
	public WorkoutsPanel(AppOverlayPanel aop) {
		this.aop = aop;
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		workouts = FitFlowIO.readWorkouts();
		
		for(Workout workout : workouts) {
			JButton workoutButton = new JButton(workout.getName());
			
			workoutButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO add functionality to start workout
					
					// Prompt user are you ready? with JOptionPane
					
					// Begin workout timer by switching to OngoingExerciseState with the current workout
//					OngoingExerciseState(workout);
					
				}
			});
			
			add(workoutButton, gbc);
		}
	}
}
