package state;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class State extends JPanel {

	private static final long serialVersionUID = -6036849467313467968L;
	private AppOverlayPanel aop;
	
	public State(AppOverlayPanel aop) {
		this.aop = aop;
		Border deepBlueBorder = BorderFactory.createLineBorder(new Color(0, 85, 255), 5);
		setBorder(deepBlueBorder);
	}
}
