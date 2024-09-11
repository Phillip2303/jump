package de.phillip.jumpandrun.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Cloud extends Actor {
	
	private double x;
	private double y;
	private Image image;

	public Cloud(double x, double y, double width, double height, Image image) {
		super(width, height);
		this.x = x;
		this.y = y;
		this.image = image;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(image, x, y, getWidth(), getHeight());
	}

	@Override
	public void debugOut() {

	}

}
