package com.error22.lychee.editor;

import javax.swing.Icon;
import javax.swing.JComponent;

public interface IEditor {
	
	public String getTitle();
	
	public String getCloseToolTip();
	
	public String getHoverToolTip();
	
	public Icon getIcon();
	
	public JComponent getMainPane();

	public void destroy();
}
