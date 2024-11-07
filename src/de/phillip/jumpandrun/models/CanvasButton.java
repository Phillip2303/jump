package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CanvasButton implements Drawable {

	private Image image;
	private Rectangle2D rectangle;
	private boolean isActive;
	private boolean isClicked;
	private int index;
	private int defaultWidth;
	private int defaultHeight;

	public CanvasButton(Image image, int posX, int posY, int defaultWidth, int defaultHeight, int index) {
		this.image = image;
		this.index = index;
		this.defaultWidth = defaultWidth;
		this.defaultHeight = defaultHeight;
		rectangle = new Rectangle2D(posX, posY, defaultWidth * Game.SCALE, defaultHeight * Game.SCALE);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		// gc.drawImage(image, rectangle.getMinX(), rectangle.getMinY());
		if (isActive) {
			if (isClicked) {
				gc.drawImage(image, defaultWidth * 2, index * defaultHeight, defaultWidth, defaultHeight,
						rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
			} else {
				gc.drawImage(image, 0, index * defaultHeight, defaultWidth, defaultHeight, rectangle.getMinX(),
						rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
			}
		} else {
			gc.drawImage(image, defaultWidth, index * defaultHeight, defaultWidth, defaultHeight, rectangle.getMinX(),
					rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
		}
		gc.restore();
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public boolean contains(Point2D point) {
		return rectangle.contains(point);
	}

	public void setPosition(int posX, int posY) {
		rectangle = new Rectangle2D(posX, posY, rectangle.getWidth(), rectangle.getHeight());
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

}
