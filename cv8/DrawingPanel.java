/**
 * 
 */
package cv8;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Class represents visualisation of algorithms
 * @author Lukas Runt
 * @version 1.0 (14-04-2022)
 */
public class DrawingPanel extends Component{
	private final int LINE_LENGTH = 5;

	/**
	 *Constructor of drawing panel 
	 */
	public DrawingPanel() {
		this.setPreferredSize(new Dimension(800, 600));
	}
	
	/**
	 *Method which paints to a drawing panel 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		Main.importTrainData("kMeansTrainData.txt");
		for(int i = 0; i < Main.points.length; i++) {
			drawPoint(g2, Main.points[i].x * 10, Main.points[i].y * 10, Main.points[i].center);
		}
		for(int i = 0; i < Main.centers.length; i++) {
			drawCenter(g2, Main.centers[i].x * 10, Main.centers[i].y * 10, Main.centers[i].center);
		}
		//nearestNeighbor(g2, Main.point);
		//nearestKNeighbors(g2, Main.point);
		//nearestCenter(g2, Main.point);
		averageDistance(g2, Main.point);
	}
	
	/**
	 * Visualization of point
	 * @param g2 graphics
	 * @param x x coord
	 * @param y y coord
	 * @param color color of cluster - point
	 */
	private void drawPoint(Graphics2D g2, int x, int y, int color) {
		if(color % 2 == 0) {
			g2.setColor(Color.BLUE);
		}else if(color != 3){
			g2.setColor(Color.RED);
		}else {
			g2.setColor(Color.BLACK);
		}
		g2.drawLine(x, y, x + LINE_LENGTH, y);
		g2.drawLine(x, y, x - LINE_LENGTH, y);
		g2.drawLine(x, y, x, y + LINE_LENGTH);
		g2.drawLine(x, y, x, y - LINE_LENGTH);
	}
	
	/**
	 * Visualization of center of cluster
	 * @param g2 graphics
	 * @param x x coordination
	 * @param y y coordination
	 * @param color color of cluster
	 */
	private void drawCenter(Graphics2D g2, int x, int y, int color) {
		if(color % 2 == 0) {
			g2.setColor(Color.BLUE);
		}else {
			g2.setColor(Color.RED);
		}
		g2.fillOval(x - LINE_LENGTH/2, y - LINE_LENGTH/2, LINE_LENGTH, LINE_LENGTH);
	}
	
	/**
	 * Visualization of method nearest neighbor
	 * @param g2 graphics
	 * @param point point which will be assigned
	 */
	public void nearestNeighbor(Graphics2D g2, Point point) {
		drawPoint(g2, point.x * 10, point.y * 10, point.center);
		Point neig = Main.nearestNeighbor(point);
		g2.drawLine(point.x * 10, point.y * 10, neig.x * 10, neig.y * 10);	
	}
	
	/**
	 * Visualization of method k nearest neighbor
	 * @param g2 graphics
	 * @param point point which will be assigned
	 */
	public void nearestKNeighbors(Graphics2D g2, Point point) {
		drawPoint(g2, point.x * 10, point.y * 10, point.center);
		Point[] neigs = Main.kNearestNeighbor(point, 3, 2);
		for(int i = 0; i < neigs.length;i++) {
			g2.drawLine(point.x * 10, point.y * 10, neigs[i].x * 10, neigs[i].y * 10);
		}
	}
	
	/**
	 * Visualization of method nearest center
	 * @param g2 graphics
	 * @param point point which will be assigned
	 */
	public void nearestCenter(Graphics2D g2, Point point) {
		drawPoint(g2, point.x * 10, point.y * 10, point.center);
		Point center = Main.centerMethod(point);
		g2.drawLine(point.x * 10, point.y * 10, center.x * 10, center.y * 10);
	}
	
	/**
	 * Visualization of method average distance
	 * @param g2 graphics
	 * @param point point which will be assigned
	 */
	public void averageDistance(Graphics2D g2, Point point) {
		drawPoint(g2, point.x * 10, point.y * 10, point.center);
		int cluster = Main.averageDistance(point);
		for(int i = 0; i < Main.points.length; i++) {
			if(Main.points[i].center == cluster) {
				g2.drawLine(point.x * 10, point.y * 10, Main.points[i].x * 10, Main.points[i].y * 10);
			}
		}
	}
}
