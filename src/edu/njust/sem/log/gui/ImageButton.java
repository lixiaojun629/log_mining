package edu.njust.sem.log.gui;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ImageButton extends JButton {
		public ImageButton(String text,ImageIcon icon) {
			//setSize(icon.getImage().getWidth(null),
			//	icon.getImage().getHeight(null) + 10);
			setSize(64,74);
			setIcon(icon);
			setMargin(new Insets(1,1,1,1));
			//setIconTextGap(0);
			//setBorderPainted(false);
			//setBorder(null);
			//setContentAreaFilled(false);
			setText(text);
			setHorizontalTextPosition(SwingConstants.CENTER);   
			setVerticalTextPosition(SwingConstants.BOTTOM);

		}
	}
