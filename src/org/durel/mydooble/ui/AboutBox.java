package org.durel.mydooble.ui;

/*
Copyright © 2011 Bastien Durel

This file is part of myDobble.

myDobble is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

myDobble is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with myDobble.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutBox extends JDialog {
	private static final long serialVersionUID = 3812992492924178178L;
	
	public AboutBox(JFrame parent) {
		super(parent, "About myDobble", true);
		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		b.add(new JLabel("myDobble Copyright © 2011-2013  Bastien Durel"));
		b.add(new JLabel("This program comes with ABSOLUTELY NO WARRANTY"));
		b.add(new JLabel("This is free software, and you are welcome to redistribute it"));
		b.add(new JLabel("under certain conditions; see file 'gpl.txt' for details."));
		b.add(Box.createGlue());
		getContentPane().add(b, "Center");
		JPanel p2 = new JPanel();
		JButton ok = new JButton("Ok");
		p2.add(ok);
		getContentPane().add(p2, "South");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
        setVisible(false);
        dispose();
      }
    });
		setSize(400, 150);
	}

}
