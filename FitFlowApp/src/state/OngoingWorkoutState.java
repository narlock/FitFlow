package state;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import component.OngoingWorkoutPanel;
import model.Workout;

public class OngoingWorkoutState extends State {

	private static final long serialVersionUID = -3717625268252499952L;
	private GridBagConstraints gbc;
	private AppOverlayPanel aop;
	
	private JLabel titleWorkoutLabel;
	private OngoingWorkoutPanel ongoingWorkoutPanel;

	public OngoingWorkoutState(AppOverlayPanel aop, Workout workout) {
		super(aop);
		this.aop = aop;
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		titleWorkoutLabel = new JLabel(workout.getName());
		ongoingWorkoutPanel = new OngoingWorkoutPanel(this, workout);
		ongoingWorkoutPanel.setPreferredSize(new Dimension(400, 400));
		
		add(titleWorkoutLabel, gbc);
		add(ongoingWorkoutPanel, gbc);
	}

	public AppOverlayPanel getAop() {
		return aop;
	}
}
