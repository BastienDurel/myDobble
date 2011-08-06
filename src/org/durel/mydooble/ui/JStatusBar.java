package org.durel.mydooble.ui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JStatusBar extends JPanel {
	private JLabel lblNewLabel = null;
	
	public JStatusBar() {
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		Dimension dim = new Dimension(getMaximumSize().width, 28);
		setPreferredSize(dim);
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 1, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 1, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -1, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -1, SpringLayout.EAST, this);
		add(lblNewLabel);
	}
	private static final long serialVersionUID = -2675032146928071690L;
	
	public void setText(String text)	{
		lblNewLabel.setText(text);
	}
	
	public void setHorizontalAlignment(int alignment) {
		lblNewLabel.setHorizontalAlignment(alignment);
	}
	
	public JLabel getLabel() {
		return lblNewLabel;
	}
}
