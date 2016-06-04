package com.error22.lychee.editor;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public interface IEditor {
	
	public String getTitle();
	
	public String getCloseToolTip();
	
	public String getHoverToolTip();
	
	public ImageIcon getIcon();
	
	public JComponent getMainPane();

	public void destroy();
}
