package org.durel.mydooble.ui;

/*
 Copyright © 2011-2013 Bastien Durel

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

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.durel.mydooble.Core;
import org.durel.mydooble.ImageItem;
import org.durel.mydooble.Item;
import org.durel.mydooble.TextItem;
import org.durel.mydooble.Test.PlainFormatter;
import org.durel.mydooble.ui.DoobleListModel.Image;

public class MyDooble extends javax.swing.JFrame {

	{
		// Set Look & Feel
		final String lookAndFeels[] = { "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
				"com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel" };
		for (int i = 0; i < lookAndFeels.length; ++i)
			try {
				javax.swing.UIManager.setLookAndFeel(lookAndFeels[i]);
				break;
			} catch (Exception e) {
				// Ignore it
			}
	}

	private static final long serialVersionUID = -3231189728332798370L;

	private static final String version = "1.1";

	private JList<Item> jListTo;

	private JTextField jTextNewLabel;

	private JButton jButtonLoad;

	private JButton jButtonMake;

	private JSpinner jSpinnerNbItems;

	private JLabel jLabelNbItems;

	private JLabel jLabelTo;

	private JLabel jLabelFrom;

	@SuppressWarnings("rawtypes")
	private JList jListFrom;

	private JRadioButton jRadioGlyph;

	private JRadioButton jRadioText;

	private ButtonGroup grp = new ButtonGroup();

	private final Core core = new Core(4);

	protected DoobleListModel fromModel = new DoobleListModel();

	protected DoobleListModel toModel = new DoobleListModel();

	Logger log = Logger.getLogger("org.durel.mydooble");

	Preferences prefs = Preferences.userNodeForPackage(MyDooble.class);

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		final String[] fargs = args;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				boolean about = false;
				Level log = Level.WARNING;
				for (int i = 0; i < fargs.length; ++i) {
					if (fargs[i].equalsIgnoreCase("-d")) {
						log = Level.INFO;
					}
					if (fargs[i].equalsIgnoreCase("-v")) {
						about = true;
					}
					if (fargs[i].equalsIgnoreCase("--version")) {
						System.out.println("myDobble version " + version);
						System.exit(0);
					}
				}
				MyDooble inst = new MyDooble(log);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				if (about)
					inst.about();
			}
		});
	}

	String lastDirectory = null;

	private JStatusBar jLabelStatusBar;

	public MyDooble(Level loglvl) {
		super();
		initGUI();
		checkValid();
		selectText();

		inlitLog(loglvl);

		// lastDirectory = ".";
		lastDirectory = prefs.get("lastDirectory", ".");
		boolean haveSeeNotice = prefs.getBoolean("haveSeeNotice", false);
		if (!haveSeeNotice)
			about();
	}

	private void inlitLog(Level loglvl) {
		log.setLevel(loglvl);
		log.setUseParentHandlers(false);
		ConsoleHandler h = new ConsoleHandler();
		h.setFormatter(new PlainFormatter());
		log.addHandler(h);
		log.info("Configured");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
			thisLayout.rowHeights = new int[] { 7, 7, 7, 7, 7, 7, 7 };
			thisLayout.columnWeights = new double[] { 0.1, 0.1 };
			thisLayout.columnWidths = new int[] { 7, 7 };
			getContentPane().setLayout(thisLayout);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					doClose();
				}
			});
			{
				jRadioText = new JRadioButton();
				getContentPane().add(
						jRadioText,
						new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
								GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
				jRadioText.setLayout(null);
				jRadioText.setText("Texte");
				jRadioText.setSelected(true);
				jRadioText.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						selectText();
					}
				});
				grp.add(jRadioText);
			}
			{
				jRadioGlyph = new JRadioButton();
				jRadioGlyph.setLayout(null);
				getContentPane().add(
						jRadioGlyph,
						new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
								GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
				jRadioGlyph.setText("Images");
				jRadioGlyph.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						selectGlyph();
					}
				});
				grp.add(jRadioGlyph);
			}
			{
				jLabelFrom = new JLabel();
				getContentPane().add(
						jLabelFrom,
						new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
								GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
				jLabelFrom.setText("À ajouter");
			}
			{
				jLabelTo = new JLabel();
				getContentPane().add(
						jLabelTo,
						new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
								GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
				jLabelTo.setText("Sélectionnés");
			}
			{
				jListFrom = new JList();
				JScrollPane scrollPane = new JScrollPane(jListFrom);
				jLabelFrom.setLabelFor(jListFrom);
				getContentPane().add(
						scrollPane,
						new GridBagConstraints(0, 2, 1, 1, 0.0, 10.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
				jListFrom.setModel(fromModel);
				jListFrom.setCellRenderer(new DoobleCellRenderer());
				jListFrom.setTransferHandler(new DoobleTransferHandler());
				jListFrom.setDragEnabled(true);
				jListFrom.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						switch (e.getKeyChar()) {
						case KeyEvent.VK_SPACE:
						case KeyEvent.VK_ENTER:
							addTo();
							break;
						case KeyEvent.VK_DELETE:
							deleteFrom();
							break;
						default:
							break;
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {
					}

					@Override
					public void keyPressed(KeyEvent e) {
					}
				});
			}
			{
				jListTo = new JList();
				jLabelTo.setLabelFor(jListTo);
				JScrollPane scrollPane = new JScrollPane(jListTo);
				getContentPane().add(
						scrollPane,
						new GridBagConstraints(1, 2, 1, 1, 0.0, 10.0, GridBagConstraints.CENTER,
								GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));
				jListTo.setModel(toModel);
				jListTo.setCellRenderer(new DoobleCellRenderer());
				jListTo.setDropMode(DropMode.INSERT);
				jListTo.setDropTarget(new DropTarget());
				jListTo.getDropTarget().setActive(true);
				jListTo.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						if (e.getKeyChar() == KeyEvent.VK_DELETE) {
							deleteTo();
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {
					}

					@Override
					public void keyPressed(KeyEvent e) {
					}
				});
				jListTo.getDropTarget().addDropTargetListener(new DropTargetListener() {

					@Override
					public void dropActionChanged(DropTargetDragEvent dtde) {
					}

					@Override
					public void drop(DropTargetDropEvent dtde) {
						dropTo(dtde);
					}

					@Override
					public void dragOver(DropTargetDragEvent dtde) {
					}

					@Override
					public void dragExit(DropTargetEvent dte) {
					}

					@Override
					public void dragEnter(DropTargetDragEvent dtde) {
					}
				});
			}
			{
				jTextNewLabel = new JTextField();
				getContentPane().add(
						jTextNewLabel,
						new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
				jTextNewLabel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (jTextNewLabel.getText().length() > 0) {
							if (registerNewLabel(jTextNewLabel.getText())) {
								jTextNewLabel.setText("");
							}
						}
					}
				});
			}
			{
				jButtonLoad = new JButton();
				getContentPane().add(
						jButtonLoad,
						new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
				jButtonLoad.setText("...");
				jButtonLoad.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						chooseFile();
					}
				});
			}
			{
				jLabelNbItems = new JLabel();
				getContentPane().add(
						jLabelNbItems,
						new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
				jLabelNbItems.setText("Nombre d'éléments par carte");
			}
			{
				AbstractSpinnerModel jSpinnerNbItemsModel = new SpinnerNumberModel(4, 2, 16, 1);
				jSpinnerNbItems = new JSpinner();
				getContentPane().add(
						jSpinnerNbItems,
						new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
				jSpinnerNbItems.setModel(jSpinnerNbItemsModel);
				jSpinnerNbItems.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						core.reset((Integer) jSpinnerNbItems.getValue());
						toModel.clear();
						checkValid();
					}
				});
			}
			{
				jButtonMake = new JButton();
				getContentPane().add(
						jButtonMake,
						new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
				jButtonMake.setText("Générer");
				{
					jLabelStatusBar = new JStatusBar();
					jLabelStatusBar.setHorizontalAlignment(SwingConstants.RIGHT);
					GridBagConstraints gbc_jLabelStatusBar = new GridBagConstraints();
					gbc_jLabelStatusBar.weighty = 0.1;
					gbc_jLabelStatusBar.weightx = 0.1;
					gbc_jLabelStatusBar.fill = GridBagConstraints.HORIZONTAL;
					gbc_jLabelStatusBar.gridwidth = 2;
					gbc_jLabelStatusBar.insets = new Insets(0, 0, 0, 5);
					gbc_jLabelStatusBar.gridx = 0;
					gbc_jLabelStatusBar.gridy = 6;
					getContentPane().add(jLabelStatusBar, gbc_jLabelStatusBar);
				}
				jButtonMake.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						doMake();
					}
				});
			}
			setSize(435, 450);
			setTitle("myDobble " + version);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void deleteFrom() {
		fromModel.remove(jListFrom.getSelectedIndex());
		if (!fromModel.isGlyph) {
			fromModel.save(prefs);
		}
	}

	protected void deleteTo() {
		Object o = toModel.getElementAt(jListTo.getSelectedIndex());
		if (o instanceof Image) {
			try {
				String file = lastDirectory + File.separator + ((Image) o).name;
				core.remove(new ImageItem(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (o instanceof String) {
			core.remove(new TextItem((String) o));
		} else
			log.info("cannot remove element ?");
		toModel.remove(jListTo.getSelectedIndex());
		checkValid();
	}

	protected void doClose() {
		try {
			prefs.put("lastDirectory", lastDirectory);
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		this.dispose();
	}

	protected boolean registerNewLabel(String text) {
		for (int i = 0; i < fromModel.model.size(); ++i) {
			if (text == fromModel.model.get(i))
				return false;
		}
		fromModel.add(text);
		fromModel.save(prefs);
		return true;
	}

	protected void dropTo(DropTargetDropEvent dtde) {
		addTo();
	}

	protected void addTo() {
		@SuppressWarnings("rawtypes")
		Iterator it = jListFrom.getSelectedValuesList().iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof Image) {
				try {
					addTo((Image) o);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (o instanceof String) {
				addTo((String) o);
			}
			if (checkValid())
				break;
		}
	}

	private void addTo(String item) {
		if (!toModel.add(item))
			return;
		core.add(new TextItem(item));
		checkValid();
	}

	private void addTo(Image item) throws IOException {
		if (!toModel.add(item))
			return;
		core.add(new ImageItem(lastDirectory + File.separator + item.name));
		checkValid();
	}

	protected void selectGlyph() {
		jTextNewLabel.setEnabled(false);
		jButtonLoad.setEnabled(true);
		core.reset();
		fromModel.isGlyph = true;
		toModel.isGlyph = true;
		fromModel.clear();
		toModel.clear();
		loadDir(new File(lastDirectory));
	}

	protected void selectText() {
		jTextNewLabel.setEnabled(true);
		jButtonLoad.setEnabled(false);
		core.reset();
		fromModel.isGlyph = false;
		toModel.isGlyph = false;
		fromModel.clear();
		toModel.clear();

		byte labels[] = prefs.getByteArray("labels", new byte[0]);
		if (labels.length > 0) {
			InputStream s = new ByteArrayInputStream(labels);
			try {
				ObjectInputStream i = new ObjectInputStream(s);
				fromModel.init(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doMake() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				core.build(chooser.getSelectedFile());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void loadDir(File dir) {
		Cursor old = getCursor();
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		// SwingUtilities.invokeLater(new DobbleLoadDir(old, dir, this));
		new Thread(new DobbleLoadDir(old, dir, this)).start();
	}

	protected void chooseFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setSelectedFile(new File(lastDirectory));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			lastDirectory = chooser.getSelectedFile().getAbsolutePath();
			loadDir(chooser.getSelectedFile());
		}
	}

	protected boolean checkValid() {
		jButtonMake.setEnabled(core.isCoherent());
		updateStatusBar();
		return core.isCoherent();
	}

	private void updateStatusBar() {
		int nb = core.getNbItemsReq();
		String msg = "Éléments requis: " + nb;
		if (nb == 0)
			msg = "Nombre d'éléments invalide";
		jLabelStatusBar.setText(msg);
	}

	public void about() {
		new AboutBox(this).setVisible(true);
		prefs.putBoolean("haveSeeNotice", true);
	}
}
