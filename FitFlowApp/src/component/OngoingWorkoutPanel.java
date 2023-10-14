package component;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JButton;
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
import util.FitFlowUtils;

public class OngoingWorkoutPanel extends JPanel {

	private static final long serialVersionUID = 8292009774145003392L;
	private GridBagConstraints gbc;

	private OngoingWorkoutState state;
	
	private boolean isBreak;
	private int currentExerciseIndex; // Index corresponding to the item in exercisesToDo list.
	private List<ExerciseItem> exercisesToDo;
	
	private Timer timer;
	private int secondsRemaining;
	
	private JLabel currentExerciseNameLabel;
	private Image currentExerciseImage;
	private JLabel secondsLabel;
	
	private JButton giveUpButton;
	
	ExerciseItem currentExercise;
	
	public OngoingWorkoutPanel(OngoingWorkoutState state, Workout workout) {
		JPanel northPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		setLayout(new BorderLayout());
		this.state = state;
		this.exercisesToDo = workout.getExercises();
		
		currentExerciseNameLabel = new JLabel();
		currentExerciseNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
		currentExerciseImage = null;
		secondsLabel = new JLabel();
		secondsLabel.setFont(new Font("Arial", Font.BOLD, 48));
		
		giveUpButton = new JButton("Give Up");
		giveUpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning()) {
					timer.stop();
				}
				
				state.getAop().changeState(new HomeState(state.getAop()));
				return;
			}
		});
		
		currentExerciseIndex = 0; // Begin with the first exercise in the list
		
		northPanel.add(currentExerciseNameLabel, gbc);
		northPanel.add(secondsLabel, gbc);
		
		add(northPanel, BorderLayout.NORTH);
		add(giveUpButton, BorderLayout.SOUTH);
		setTimer();
	}
	
	public void setTimer() {
		// 1. Set the minutes and seconds based off of current exercise
		currentExercise = exercisesToDo.get(currentExerciseIndex);
		
		secondsRemaining = (int) currentExercise.getWorkTimeInSeconds();
		secondsLabel.setText(String.valueOf(secondsRemaining));
		isBreak = false;
		
		// 2. Set visuals
		currentExerciseNameLabel.setText(currentExercise.getExercise().getName());
		currentExerciseImage = FitFlowUtils.getScaledImageForCurrentWorkout(currentExercise.getExercise().getImagePath());
		
		// 3. Begin timer
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Timer is running... " + secondsRemaining);
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
						
						// Reset visuals
						currentExerciseNameLabel.setText(currentExercise.getExercise().getName());
						currentExerciseImage = FitFlowUtils.getScaledImageForCurrentWorkout(currentExercise.getExercise().getImagePath());
						
						isBreak = false;
						
						// Start timer
						timer.start();
					} else {
						// Set break time based off of current workout
						secondsRemaining = (int) currentExercise.getBreakTimeInSeconds();
						isBreak = true;
						
						// Set visual to break
						currentExerciseNameLabel.setText("Break");
						
						// Start timer
						timer.start();
					}
					
					revalidate();
					repaint();
				}
				
				secondsRemaining--;
				secondsLabel.setText(String.valueOf(secondsRemaining));
			}
		});
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) { 
		g.drawImage(currentExerciseImage, 75, 100, null);
	}
}
