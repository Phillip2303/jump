package de.phillip.jumpandrun.models;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public interface Menu {
	public void prepareMenu(GraphicsContext gc); 
	public List<Drawable> getDrawables();
	public void mouseMoved(double x, double y);
	public void mousePressed();
	public void mouseReleased();
}
