package de.phillip.jumpandrun.models;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public interface CanvasLayer {
	public List<Drawable> getDrawables();

	public GraphicsContext getGraphicsContext2D();

	public void prepareLayer();

	public void updateLayer(float secondsSinceLastFrame);

	public void buildLevel(boolean nextLevel);

	public void listenToEvents(boolean value);
}
