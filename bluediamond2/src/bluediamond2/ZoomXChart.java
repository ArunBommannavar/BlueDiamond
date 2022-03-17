package bluediamond2;


import com.klg.jclass.chart.JCAxis;
import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart.JCChartArea;
import com.klg.jclass.chart.JCDataCoord;
import com.klg.jclass.chart.JCDataIndex;

import java.awt.*;
import java.awt.event.*;

public class ZoomXChart extends JCChart {

protected JCDataCoord  mouseStart;
protected MyChartArea  myChartArea = null;
protected Point        DragStart = null;
protected Rectangle    rect = null;
protected Rectangle    oldRect = null;

public ZoomXChart() 	{
    myChartArea = new MyChartArea(this);

	// Tell the dataView about the new chart area
	getDataView(0).setParent(this);
}

/**
 * Intercept MousePressed events on the graph.
 * If the click occured in the chart area, initialize a drag/zoom.
 */
public void mousePressed(MouseEvent event)
{
    super.mousePressed(event);
	int x = event.getX();
	int y = event.getY();
    // System.out.println("mouseDown("+x+","+y+")");

    DragStart = new Point(x, y);
    JCDataIndex clicked = this.pick(DragStart, this.getDataView(0));

    // Make sure that the click was in the Chart Area
    if (clicked != null && clicked.getObject() == this.getChartArea()) {
		startDrag(x, y);
    }
	else {
		DragStart = null;
    }
}

/**
 *  Animate the mouse drag.
 */
public void mouseDragged(MouseEvent event)
{
	int x = event.getX();
	int y = event.getY();
    // System.out.println("mouseDrag("+x+","+y+")");

    if (DragStart != null) {
		showDrag(x, y);
    }
}

/**
 *  Use the MouseReleased event to complete the zoom operation.
 */
public void mouseReleased(MouseEvent event)
{
	int x = event.getX();
	int y = event.getY();
    // System.out.println("mouseUp("+x+","+y+")");

    if (DragStart != null) {
		endDrag(x, y);
    }
	else if (rect != null) {
		// Clean up
		myChartArea.repaint();
        rect = oldRect = null;
    }
}

/**
 * Initialize the zoom, saving the position and value of
 * the initial mouse click.
 */
public void startDrag(int x, int y)
{
    mouseStart = this.getDataView(0).map(x, y);
    rect = oldRect = null;
}

/**
 * Animate the zoom by XORing the selected region of the chart.
 */
public void showDrag(int x, int y)
{
    if (x == DragStart.x) {
		return;
    }

    // Select left-to-right or right-to-left
    int left, width;
    if (x > DragStart.x) {
		left = DragStart.x;
		width = x - DragStart.x;
    }
	else {
		left = x;
		width = DragStart.x - x;
    }
	left -= (int)getChartArea().getLocation().getX();

    JCAxis yaxis = this.getDataView(0).getYAxis();

    // Get the region bounded by the Plot area and the selected area
    JCDataCoord coord = this.getDataView(0).map(x, y);
    Point topY        = this.getDataView(0).unmap(coord.getX(), yaxis.getMax());
    Point bottomY     = this.getDataView(0).unmap(coord.getY(), yaxis.getMin());
    int top           = topY.y - (int)getChartArea().getLocation().getY();
    int height        = bottomY.y - topY.y;

	// Update the rectangle and paint it on the graph
    rect = new Rectangle(left, top, width, height);
	myChartArea.repaint();
}

/**
 * Collect the bounds of the selected region and re-set the axis bounds.
 */
public void endDrag(int x, int y)
{
    // Ignore tiny (inadvertent) zoom requests
    if (Math.abs(x - DragStart.x) < 3) {
		if (rect != null) {
			myChartArea.repaint();
            rect = oldRect = null;
		}
		return;
    }
    rect = oldRect = null;

    // Get zoom bounds
    JCDataCoord mouseEnd = this.getDataView(0).map(x, y);
    double startx, endx;
    if (mouseEnd.getX() > mouseStart.getX()) {
		startx = mouseStart.getX();
		endx   = mouseEnd.getX();
    }
	else {
		startx = mouseEnd.getX();
		endx   = mouseStart.getX();
    }
    // System.out.println("zoom("+startx+","+endx+")");

    // Set new bounds
    this.setBatched(true);
    JCAxis xaxis = this.getDataView(0).getXAxis();
    xaxis.setMin(startx);
    xaxis.setMax(endx);
    this.setBatched(false);
}

class MyChartArea extends JCChartArea
{
	public MyChartArea(JCChart chart)
	{
		super();
	    chart.setChartArea(this);

		// Tell the current axes about the new chart area
		setParentOnAxes(chart);
	}

	public void paintComponent(Graphics g)
	{
		// Don't paint the component while dragging
		if (oldRect == null) {
	        super.paintComponent(g);
		}

		Graphics g2 = g.create();
		g2.setColor(Color.blue);
		g2.setXORMode(Color.white);

        // If there was a previous selected region, XOR it again to return it
		// to "normal"
		if (oldRect != null) {
		    g2.fillRect(oldRect.x, oldRect.y, oldRect.width, oldRect.height);
		}

        // XOR the selected region
		if (rect != null) {
		    g2.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		oldRect = rect;
		g2.dispose();
	}
};

}

