/*
class GuiHelpers

A collection of static methods to facilitate the creation of fluid, yet precise layouts.
The helpers support the idea of building a layout by adding components to a nested
structure of rows and columns. By nesting components and glue (invisible,
flexible-width/height components) inside of nested rows and columns, you can achieve
fluidity and preserve natural alignments to create a usable interface. If you are
familiar with old-school HTML table layouts, you should be able to easily conceptualize
how to use the GuiHelper components.
*/

import javax.swing.*;
import java.awt.*;

public class GuiHelpers {
	// wrap a JComponent in a JPanel
	public static JPanel wrap(JComponent c) {
		return wrap(c, Component.LEFT_ALIGNMENT);
	}
	
	// wrap a JComponent in a JPanel with the specified x-alignment
	// choices are Component.LEFT_ALIGNMENT, Component.CENTER_ALIGNMENT, and
	// Component.RIGHT_ALIGNMENT
	public static JPanel wrap(JComponent c, float x_align) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		c.setAlignmentX(x_align);
		jp.add(c);
		return jp;
	}
	
	// add a rigid margin of the specified dimensions
	// for use with BoxLayout to add space between components
	public static Component margin(int x, int y) {
		return Box.createRigidArea(new Dimension(x, y));
	}
	
	// Create a horizontal row for adding components
	public static JPanel row() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
		return jp;
	}

	// Create a vertical column for positioning components	
	public static JPanel column() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		return jp;
	}
}

