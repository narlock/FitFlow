package component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import io.FitFlowIO;
import model.Exercise;
import model.ExerciseItem;
import model.Workout;
import state.HomeState;
import state.OngoingWorkoutState;

public class OngoingWorkoutPanel extends JPanel {

	private static final long serialVersionUID = 8292009774145003392L;

	private OngoingWorkoutState state;
	
	private boolean isBreak;
	private int currentExerciseIndex; // Index corresponding to the item in exercisesToDo list.
	private List<ExerciseItem> exercisesToDo;
	private List<Exercise> exercises;
	
	private Timer timer;
	private int secondsRemaining;
	private JLabel secondsLabel;
	
	ExerciseItem currentExercise;
	
	public OngoingWorkoutPanel(OngoingWorkoutState state, Workout workout) {
		this.state = state;
		this.exercisesToDo = workout.getExercises();
		this.exercises = FitFlowIO.readExercises(); // We want to read these so we can match indexes

		secondsLabel = new JLabel();
		
		currentExerciseIndex = 0; // Begin with the first exercise in the list
		
		add(secondsLabel);
		setTimer();
	}
	
	public void setTimer() {
		// 1. Set the minutes and seconds based off of current exercise
		currentExercise = exercisesToDo.get(currentExerciseIndex);
		
		secondsRemaining = (int) currentExercise.getWorkTimeInSeconds();
		secondsLabel.setText(String.valueOf(secondsRemaining));
		isBreak = false;
		
		// 2. Begin timer
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(secondsRemaining <= 0) {
					// Stop timer
					timer.stop();
					
					// Is the work out done
					if(currentExerciseIndex == exercisesToDo.size() - 1 && isBreak) {
						// Display JOptionPane.messagedialog "Workout complete!"
						JOptionPane.showMessageDialog(getRootPane(), "Workout complete");
						
						state.getAop().changeState(new HomeState(state.getAop()));
						return;
					}
					
					// Set new attributes then start
					if(isBreak) {
						// Ready to move to next workout
						currentExerciseIndex++;
						
						// Set workout based off of next workout
						currentExercise = exercisesToDo.get(currentExerciseIndex);
						secondsRemaining = (int) currentExercise.getWorkTimeInSeconds();
						isBreak = false;
						
						// Start timer
						timer.start();
					} else {
						// Set break time based off of current workout
						secondsRemaining = (int) currentExercise.getBreakTimeInSeconds();
						isBreak = true;
						
						// Start timer
						timer.start();
					}
				}
				
				secondsRemaining--;
				secondsLabel.setText(String.valueOf(secondsRemaining));
			}
		});
		timer.start();
	}

}
