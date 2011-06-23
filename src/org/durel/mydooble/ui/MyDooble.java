package org.durel.mydooble.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.durel.mydooble.Core;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MyDooble extends javax.swing.JFrame {

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = -3231189728332798370L;
	private JList jListTo;
	private JTextField jTextNewLabel;
	private JButton jButtonLoad;
	private JButton jButtonMake;
	private JSpinner jSpinnerNbItems;
	private JLabel jLabelNbItems;
	private JSeparator jSeparator1;
	private JLabel jLabelTo;
	private JLabel jLabelFrom;
	private JList jListFrom;
	private JRadioButton jRadioGlyph;
	private JRadioButton jRadioText;

	private ButtonGroup grp = new ButtonGroup();
	private final Core core = new Core(4);
	private SpinnerHandler spinnerHandler = new SpinnerHandler();

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MyDooble inst = new MyDooble();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public MyDooble() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
			thisLayout.rowHeights = new int[] { 7, 7, 7, 7, 7, 7 };
			thisLayout.columnWeights = new double[] { 0.1, 0.1 };
			thisLayout.columnWidths = new int[] { 7, 7 };
			getContentPane().setLayout(thisLayout);
			{
				jRadioText = new JRadioButton();
				getContentPane().add(
						jRadioText,
						new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.WEST,
								GridBagConstraints.NONE,
								new Insets(0, 0, 0, 0), 0, 0));
				jRadioText.setLayout(null);
				jRadioText.setText("Texte");
				grp.add(jRadioText);
			}
			{
				jRadioGlyph = new JRadioButton();
				jRadioGlyph.setLayout(null);
				getContentPane().add(
						jRadioGlyph,
						new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.WEST,
								GridBagConstraints.NONE,
								new Insets(0, 0, 0, 0), 0, 0));
				jRadioGlyph.setText("Images");
				grp.add(jRadioGlyph);
			}
			{
				jLabelFrom = new JLabel();
				getContentPane().add(
						jLabelFrom,
						new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.WEST,
								GridBagConstraints.NONE,
								new Insets(0, 0, 0, 0), 0, 0));
				jLabelFrom.setText("À ajouter");
			}
			{
				jLabelTo = new JLabel();
				getContentPane().add(
						jLabelTo,
						new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.WEST,
								GridBagConstraints.NONE,
								new Insets(0, 0, 0, 0), 0, 0));
				jLabelTo.setText("Sélectionnés");
			}
			{
				ListModel jListFromModel = new DefaultComboBoxModel(
						new String[] {});
				jListFrom = new JList();
				jLabelFrom.setLabelFor(jListFrom);
				getContentPane().add(
						jListFrom,
						new GridBagConstraints(0, 2, 1, 1, 0.0, 10.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
				jListFrom.setModel(jListFromModel);
			}
			{
				ListModel jListToModel = new DefaultComboBoxModel(
						new String[] {});
				jListTo = new JList();
				jLabelTo.setLabelFor(jListTo);
				getContentPane().add(
						jListTo,
						new GridBagConstraints(1, 2, 1, 1, 0.0, 10.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
				jListTo.setModel(jListToModel);
			}
			{
				jTextNewLabel = new JTextField();
				getContentPane().add(
						jTextNewLabel,
						new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0,
										0, 0), 0, 0));
			}
			{
				jButtonLoad = new JButton();
				getContentPane().add(
						jButtonLoad,
						new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0,
										0, 0), 0, 0));
				jButtonLoad.setText("...");
				jButtonLoad.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						chooseFile();
					}
				});
			}
			{
				jSeparator1 = new JSeparator();
				getContentPane().add(
						jSeparator1,
						new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0,
										0, 0), 0, 0));
				jSeparator1.setPreferredSize(new java.awt.Dimension(378, 34));
			}
			{
				jLabelNbItems = new JLabel();
				getContentPane().add(
						jLabelNbItems,
						new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
								GridBagConstraints.EAST,
								GridBagConstraints.NONE,
								new Insets(0, 0, 0, 0), 0, 0));
				jLabelNbItems.setText("Nombre d'éléments par carte");
			}
			{
				AbstractSpinnerModel jSpinnerNbItemsModel = new SpinnerNumberModel(
						4, 2, 16, 1);
				jSpinnerNbItems = new JSpinner();
				getContentPane().add(
						jSpinnerNbItems,
						new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0,
										0, 0), 0, 0));
				jSpinnerNbItems.setModel(jSpinnerNbItemsModel);
				jSpinnerNbItems.addChangeListener(spinnerHandler);
			}
			{
				jButtonMake = new JButton();
				getContentPane().add(
						jButtonMake,
						new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.NONE,
								new Insets(0, 0, 0, 0), 0, 0));
				jButtonMake.setText("Générer");
			}
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void chooseFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// TODO
		}
	}

	protected void checkValid() {
		jButtonMake.setEnabled(core.isCoherent());
	}

	class SpinnerHandler implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			core.reset((Integer) jSpinnerNbItems.getValue());
			checkValid();
		}
	}

}
