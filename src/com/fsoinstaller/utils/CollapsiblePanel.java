/*
 * This file is part of the FreeSpace Open Installer
 * Copyright (C) 2010 The FreeSpace 2 Source Code Project
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.fsoinstaller.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import com.fsoinstaller.wizard.GUIConstants;


public class CollapsiblePanel extends JPanel
{
	private static final ImageIcon arrow_right = new ImageIcon(GraphicsUtils.getResourceImage("arrow_right.png"));
	private static final ImageIcon arrow_down = new ImageIcon(GraphicsUtils.getResourceImage("arrow_down.png"));
	
	private boolean collapsed;
	private final JButton toggleButton;
	private final JPanel disappearingPanel;
	
	public CollapsiblePanel(String headerText, JComponent component)
	{
		toggleButton = new JButton(arrow_down);
		toggleButton.setMargin(new Insets(0, 0, 0, 0));
		toggleButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setCollapsed(!collapsed);
			}
		});
		
		JLabel headerLabel = new JLabel(headerText);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
		headerPanel.add(toggleButton);
		headerPanel.add(Box.createRigidArea(new Dimension(GUIConstants.SMALL_MARGIN, 0)));
		headerPanel.add(headerLabel);
		headerPanel.add(Box.createHorizontalGlue());
		
		disappearingPanel = new JPanel(new BorderLayout());
		disappearingPanel.setBorder(BorderFactory.createEmptyBorder(GUIConstants.SMALL_MARGIN, GUIConstants.SMALL_MARGIN + arrow_right.getIconWidth() + 2, 0, 0));
		disappearingPanel.add(component, BorderLayout.CENTER);
		
		// and now put everything together
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(headerPanel);
		add(disappearingPanel);
	}
	
	public void setCollapsed(boolean collapse)
	{
		// keep track of state
		if (collapse == collapsed)
			return;
		collapsed = collapse;
		
		// set button image
		toggleButton.setIcon(collapsed ? arrow_right : arrow_down);
		
		// change the GUI
		disappearingPanel.setVisible(!collapsed);
		
		// repaint
		JRootPane rootPane = SwingUtilities.getRootPane(this);
		if (rootPane != null)
			rootPane.repaint();
	}
}
