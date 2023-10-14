package state;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import component.CreateWorkoutPanel;
import component.ManageWorkoutsPanel;
import io.FitFlowIO;
import model.Workout;

public class ManageWorkoutState extends State {

	private static final long serialVersionUID = -7964344136669578628L;
	private GridBagConstraints gbc;
	private AppOverlayPanel aop;

	private JLabel manageWorkoutsLabel = new JLabel("Manage Workouts");
	private JButton addWorkoutButton = new JButton(new ImageIcon(getClass().getResource("/ADD.png")));

	private JScrollPane scrollPane;
	private ManageWorkoutsPanel manageWorkoutsPanel;

	private JButton returnToHomeButton = new JButton("Return to Home");

	public ManageWorkoutState(AppOverlayPanel aop) {
		super(aop);
		this.aop = aop;

		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		manageWorkoutsPanel = new ManageWorkoutsPanel(this);

		JPanel titlePanel = new JPanel();
		titlePanel.add(manageWorkoutsLabel);
		titlePanel.add(addWorkoutButton);
		add(titlePanel, gbc);

		scrollPane = new JScrollPane(manageWorkoutsPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);

		add(returnToHomeButton, gbc);
		addActions();
	}

	public void addActions() {
		addWorkoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Workout workout = new Workout();
				CreateWorkoutPanel createWorkoutPanel = new CreateWorkoutPanel(workout);

				int option = JOptionPane.showConfirmDialog(getRootPane(), createWorkoutPanel,
						"Create Workout", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				
				if(option == JOptionPane.YES_OPTION) {
					// Create the workout
					workout.setName(createWorkoutPanel.getWorkoutNameTextField().getText());
					
					List<Workout> workouts = FitFlowIO.readWorkouts();
					workouts.add(workout);
					FitFlowIO.upsertWorkouts(workouts);
					manageWorkoutsPanel.addNewWorkoutPanel(workout);
				}
			}
		});

		returnToHomeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aop.changeState(new HomeState(aop));
			}
		});
	}

	public void refreshWorkouts() {
		remove(scrollPane);
		remove(returnToHomeButton);

		manageWorkoutsPanel = new ManageWorkoutsPanel(this);
		scrollPane = new JScrollPane(manageWorkoutsPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);

		add(returnToHomeButton, gbc);
		repaint();
		revalidate();
	}

}
