package state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ManageWorkoutState extends State {

	private static final long serialVersionUID = -7964344136669578628L;

	public ManageWorkoutState(AppOverlayPanel aop) {
		super(aop);
		JButton button = new JButton("Switch to home");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aop.changeState(new HomeState(aop));
			}
		});
		add(button);
	}

}
